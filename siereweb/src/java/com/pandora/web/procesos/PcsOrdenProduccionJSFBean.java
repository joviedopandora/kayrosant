/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procesos;

import adm.sys.dao.AdmColaborador;
import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.AdmInforme;
import com.icesoft.faces.context.Resource;
import com.pandora.adm.dao.InvProducto;
import com.pandora.consulta.bean.PcsOrdenProduccionSFBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.logistica.dao.LgtEstadoevento;
import com.pandora.mod.ordenprod.CronogramaSLBean;
import com.pandora.mod.ordenprod.dao.PopCxccitacion;
import com.pandora.mod.ordenprod.dao.PopCxcevento;
import com.pandora.mod.ordenprod.dao.PopCxcrespon;
import com.pandora.mod.ordenprod.dao.PopCxcrol;
import com.pandora.adm.rf.dao.RfDep;
import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.adm.rf.dao.RfEstadofactura;
import com.pandora.bussiness.util.EnEstadoFactura;
import com.pandora.bussiness.util.EnEstadoLogistica;
import com.pandora.bussiness.util.EnEstadosOp;
import com.pandora.consulta.bean.ConsultaOPDTO;
import com.pandora.mod.ordenprod.dao.PopCxcuniforme;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.mod.ordenprod.dao.RfEstadoOP;
import com.pandora.mod.venta.dao.VntCronograma;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntServicioxservicio;
import com.pandora.mod.venta.dao.VntServxventa;
import com.pandora.web.adm.param.TablaAdmCrgXCol;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.ordenprod.OrdenProduccionJSFBean;
import com.pandora.web.ordenprod.TablaPopOrdenProduccion;
import com.pandora.web.ordenprod.TablaPopProdXServXOp;
import com.pandora.web.ordenprod.TablaPopServXOp;
import com.pandora.web.procs.comp.TablaProducto;
import com.pandora.web.venta.TablaVntRegistroventa;
import com.pandora.web.venta.TablaVntSrvXVenta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import utilidades.EnFormatDate;
import utilidades.EnInforme;
import utilidades.EnOpcionFactura;
import utilidades.EnTipoCliente;
import utilidades.ManejoFecha;

/**
 *
 * @author sandra
 */
@Named
@SessionScoped
public class PcsOrdenProduccionJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    @EJB
    private PcsOrdenProduccionSFBean popsfb;
    @EJB
    private CronogramaSLBean cronogramaSLBean;

    private List<TablaVntRegistroventa> lstTablaVntRegistroventa = new ArrayList<>();
    private Integer tipoClienteSel = 1;
    private TablaVntRegistroventa tablaVntRegistroventaSel = new TablaVntRegistroventa();
    private List<TablaVntSrvXVenta> lstTablaServiciosPendientes = new ArrayList<>();

    private List<TablaVntSrvXVenta> lstTablaVntSrvXVenta = new ArrayList<>();

    //Manejo de paneles
    private static final int PANEL_CONSULTA_VENTAS = 1;
    private static final int PANEL_CONSULTA_OP = 2;
    private static final int CRONOGRAMA = 3;
    private static final int PANEL_CONSULTA_PANEL_CONTROL = 4;
    private boolean blnNuevo;
    private boolean blnMostrarPanel;
    private boolean blnMostrarProductos;
    private Integer tipoConsulta;
//<editor-fold defaultstate="collapsed" desc="Cronograma">
    private boolean blnMostrarTodoCron = false;

//</editor-fold>
    private String opDepartamento = "-1";

    private Long opCiudad;
    private List<TablaPopOrdenProduccion> lstTablaPopOrdenProduccion = new ArrayList<>();
    private TablaPopOrdenProduccion tablaPopOrdenProduccionSel = new TablaPopOrdenProduccion();
    private List<TablaPopServXOp> lstTablaPopServXOp = new ArrayList<>();
    private List<TablaPopProdXServXOp> lstTablaPopProdXServXOp = new ArrayList<>();
    private List<TablaProducto> lstTablaProducto = new ArrayList<>();
    private List<TablaAdmCrgXCol> lstTablaAdmCrgXCol = new ArrayList<>();
    private TablaProducto tablaProductoSel = new TablaProducto();
    private com.icesoft.faces.context.Resource jasperResource = null;
    private List<SelectItem> lstCitacion = new ArrayList<>();
    private List<SelectItem> lstDepartamentos = new ArrayList<>();
    private List<SelectItem> lstCiudades = new ArrayList<>();
    private List<SelectItem> lstResponsable = new ArrayList<>();
    private List<SelectItem> lstRoles = new ArrayList<>();
    private List<SelectItem> lstUniformes = new ArrayList<>();
    private String oprTitulo;

    private HashMap<Integer, PopCxccitacion> mapCitacion = new HashMap<>();
    private HashMap<Integer, PopCxcrespon> mapResponsable = new HashMap<>();
    private HashMap<Integer, PopCxcrol> mapRoles = new HashMap<>();
    private HashMap<Integer, PopCxcuniforme> mapUniforme = new HashMap<>();

    private List<RfEstadoOP> listaEstadoOP = new ArrayList<>();
    private ConsultaOPDTO consultaOPDTO;

    private MessageConfirmationOP confirmation = new MessageConfirmationOP();

    public Resource getJasperResource() {
        return jasperResource;
    }

    public void setJasperResource(Resource jasperResource) {
        this.jasperResource = jasperResource;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">
    private void loadEstadoOP() {
        listaEstadoOP.clear();
        listaEstadoOP = popsfb.getLstRfEstadoOP(true);
    }

    @Override
    public void init() {
        popsfb = lookupPcsOrdenProduccionSFBean();
        loadEstadoOP();
        blnNuevo = true;
        numPanel = PANEL_CONSULTA_PANEL_CONTROL;
        blnMostrarPanel = false;
        blnMostrarProductos = false;
        tipoConsulta = 2;
        consultaOPDTO = new ConsultaOPDTO();
        tipoClienteSel = EnTipoCliente.COORPORATIVO.getId();
        if (getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente() != null) {
            tipoClienteSel = getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente().getTclId();
        }
        ///   cargarListaVentasAplicarOP();
        cargarParametrosXColaborador();

        cargarListaDepartamentos();
        cargarListaCiudades(null);
        lstTablaPopOrdenProduccion.clear();

    }

    public void selDepartamento_ValueChangeEvent(ValueChangeEvent vce) {

        opDepartamento = (String) vce.getNewValue();
        cargarListaCiudades(opDepartamento);

    }

    @Override
    public void limpiarVariables() {
        /// popsfb.remove();
    }

    private void limpiarCampos() {
        lstTablaPopServXOp.clear();
        lstTablaPopProdXServXOp.clear();
        lstTablaAdmCrgXCol.clear();
        lstTablaProducto.clear();
        opCiudad = -1L;
        opDepartamento = "-1";
        cargarListaCiudades(null);
        //lstCiudades.clear();
        //  lstDepartamentos.clear();
    }

    public void rowDtRegistroVentaSelServicios_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntRegistroventaSel = (TablaVntRegistroventa) map.get("trvs");
        blnMostrarPanel = true;
        //TODO: Cargar servicios por venta
        cargarServiciosXventa();

    }

    private void cargarServiciosXventa() {

        lstTablaServiciosPendientes.clear();
        for (VntServxventa x : popsfb.getLstServxventaXVnt(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            TablaVntSrvXVenta s = new TablaVntSrvXVenta(x);
            Integer pendiente = x.getSrvxventCantidad() - x.getSrvxventaProcesadaOP();
            s.setCantidadPendiente(pendiente);
            s.setCantidadSeleccionada(pendiente);
            if (pendiente != null && pendiente > 0) {
                s.setBlnEditar(true);
            }
            lstTablaServiciosPendientes.add(s);

        }

    }

    public void cambioCantidadXServicio(ValueChangeEvent vce) {
        TablaVntSrvXVenta tsxvs = (TablaVntSrvXVenta) vce.getComponent().getAttributes().get("tsxvs");
        tsxvs.setCantidadSeleccionada(null);
        Integer pend = tsxvs.getVntServxventa().getSrvxventCantidad() - tsxvs.getVntServxventa().getSrvxventaProcesadaOP();
        try {
            Long cant = (Long) vce.getNewValue();

            if (cant != null && cant.intValue() > pend) {
                mostrarError("Cantidad debe ser mayor que " + pend, 1);
            } else {
                tsxvs.setCantidadSeleccionada(cant == null ? null : cant.intValue());
            }
        } catch (ClassCastException ex) {
            Integer cant = (Integer) vce.getNewValue();
            if (cant != null && cant > pend) {
                mostrarError("Cantidad debe ser mayor que " + pend, 1);
            } else {
                tsxvs.setCantidadSeleccionada(cant == null ? null : cant.intValue());
            }

        }

        if (tsxvs.getCantidadSeleccionada() != null) {

            Integer pendiente = pend - tsxvs.getCantidadSeleccionada();
            tsxvs.setCantidadPendiente(pendiente);

        }

    }

    public void irGenerarOP(ActionEvent ae) {
        List<TablaVntSrvXVenta> lstSelFact = new ArrayList<>();
        List<TablaVntSrvXVenta> lstSelAnul = new ArrayList<>();
        List<VntServxventa> lstSelAnulS = new ArrayList<>();
        tablaPopOrdenProduccionSel = new TablaPopOrdenProduccion(new PopOrdenprod());
        boolean validar = true;
        for (TablaVntSrvXVenta tsxvs : lstTablaServiciosPendientes) {
            Integer pend = tsxvs.getVntServxventa().getSrvxventCantidad() - tsxvs.getVntServxventa().getSrvxventaProcesadaOP();
            if (tsxvs.getProcesar() == null) {
                tsxvs.setProcesar(EnOpcionFactura.NO_PROCESAR.getCodigo());
            }

            if (tsxvs.getCantidadSeleccionada() != null) {

                Integer pendiente = pend - tsxvs.getCantidadSeleccionada();
                tsxvs.setCantidadPendiente(pendiente);

            }
            switch (tsxvs.getProcesar()) {
                case "N":

                    break;
                case "P":
                    if (tsxvs.getCantidadSeleccionada() == null || tsxvs.getCantidadSeleccionada() > pend) {
                        validar = false;
                    }
                    lstSelFact.add(tsxvs);
                    break;
                case "A":
                    if (tsxvs.getCantidadSeleccionada() == null || tsxvs.getCantidadSeleccionada() > pend) {
                        validar = false;
                    }
                    lstSelAnul.add(tsxvs);
                    break;
            }
        }
        if (!validar) {
            mostrarError("Error al procesar los servicios.", 1);
            return;
        }
        if (lstSelFact.isEmpty() && lstSelAnul.isEmpty()) {
            mostrarError("Debe seleccionar para procesar los servicios", 1);
            return;
        }

        for (TablaVntSrvXVenta g : lstSelAnul) {
            tablaVntRegistroventaSel.getVntRegistroventa().setRgvtCantserviciosProcesados(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtCantserviciosProcesados() + g.getCantidadSeleccionada());
            g.getVntServxventa().setSrvxventaProcesadaOP(g.getVntServxventa().getSrvxventaProcesada() + g.getCantidadSeleccionada());
            lstSelAnulS.add(g.getVntServxventa());
        }
        if (!lstSelAnulS.isEmpty()) {
            popsfb.editarRegVentaConServ(lstSelAnulS);
            tablaVntRegistroventaSel.setVntRegistroventa(popsfb.editarRegVenta(tablaVntRegistroventaSel.getVntRegistroventa()));

            mostrarError("Se han procesado los servicios anulados de la ondén de producción", 3);
        }
           //Cargar datos de la venta en la orden de producción
          VntRegistroventa rv= tablaVntRegistroventaSel.getVntRegistroventa();
        tablaPopOrdenProduccionSel.getPopOrdenprod().setRgvtId(rv);
        Calendar calFechaEvtIni= Calendar.getInstance();
        calFechaEvtIni.setTime(rv.getVdeId().getVdeFechaevt());
         Calendar calFechaEvtFin= Calendar.getInstance();
        calFechaEvtFin.setTime(rv.getVdeId().getVdeFechaevt());
          Calendar calHoraEvtIni= Calendar.getInstance();
        calHoraEvtIni.setTime(rv.getVdeId().getVdeHorainicio());
        Calendar calHoraEvtFin= Calendar.getInstance();
        calHoraEvtFin.setTime(rv.getVdeId().getVdeHorafinal());
        
        calFechaEvtIni.set(calFechaEvtIni.get(Calendar.YEAR), calFechaEvtIni.get(Calendar.MONTH), calFechaEvtIni.get(Calendar.DAY_OF_MONTH),
                calHoraEvtIni.get(Calendar.HOUR_OF_DAY), calHoraEvtIni.get(Calendar.MINUTE));
        calFechaEvtFin.set(calFechaEvtIni.get(Calendar.YEAR), calFechaEvtIni.get(Calendar.MONTH), calFechaEvtIni.get(Calendar.DAY_OF_MONTH),
                calHoraEvtFin.get(Calendar.HOUR_OF_DAY), calHoraEvtFin.get(Calendar.MINUTE));
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprFechaevtini(calFechaEvtIni.getTime());
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprFechaevtfin(calFechaEvtFin.getTime());
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprDireccionevento(rv.getVdeId().getVdeDireccionevt());
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprContactoevento(rv.getVdeId().getVdeNombrescontacto());
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprCelularcontacto(rv.getVdeId().getVdeCelular1());
        
        cargarListaCiudades(null);
        if (tablaVntRegistroventaSel.getVntRegistroventa().getVdeId() != null && tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getCiuId() != null
                && tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getCiuId().getCiuId() != null) {

            opDepartamento = tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getCiuId().getDepId().getDepId();
            cargarListaCiudades(opDepartamento);
            opCiudad = tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getCiuId().getCiuId();
         
          
         
        }

        lstTablaVntSrvXVenta = lstSelFact;
        //cargarDatosFactura(lstTablaVntSrvXVenta);
        if (lstSelFact.isEmpty()) {
            numPanel = PANEL_CONSULTA_VENTAS;
            cargarServiciosXventa();
        } else {
            numPanel = PANEL_CONSULTA_OP;
            cargarServXOrdenProd(lstTablaVntSrvXVenta);
            cargarListaColXCrgXEstado();

        }

    }

    public void cancelarCronograma(ActionEvent ae) {
        lstTablaVntSrvXVenta = new ArrayList<>();
        TablaVntSrvXVenta tsxvs = (TablaVntSrvXVenta) ae.getComponent().getAttributes().get("cronosel");
        tsxvs.getCronogramaSel().setCronogramaEstado(false);
        tsxvs.setCantidadPendiente(tsxvs.getCantidadPendiente() - 1);
        tsxvs.setCantidadSeleccionada(1);
        tsxvs.setCronogramaSel(cronogramaSLBean.editarCronograma(tsxvs.getCronogramaSel()));
        tsxvs.getVntServxventa().setSrvxventaProcesadaOP((tsxvs.getVntServxventa().getSrvxventaProcesadaOP() == null ? 1 : tsxvs.getVntServxventa().getSrvxventaProcesadaOP()) - 1);
        tsxvs.getVntServxventa().setSrvxventaProcesada((tsxvs.getVntServxventa().getSrvxventaProcesada() == null ? 1 : tsxvs.getVntServxventa().getSrvxventaProcesada()) - 1);
        tsxvs.setVntServxventa(this.popsfb.editarVntServxventa(tsxvs.getVntServxventa()));
        cargarCronograma();

    }

    public void irGenerarOPCronograma(ActionEvent ae) {
        lstTablaVntSrvXVenta = new ArrayList<>();
        TablaVntSrvXVenta tsxvs = (TablaVntSrvXVenta) ae.getComponent().getAttributes().get("cronosel");
        tablaPopOrdenProduccionSel = new TablaPopOrdenProduccion(new PopOrdenprod());
        opDepartamento = "-1";
        opCiudad = -1L;
        blnMostrarPanel = true;
        tsxvs.setCantidadPendiente(tsxvs.getCantidadPendiente() - 1);
        tsxvs.setCantidadSeleccionada(1);
        lstTablaVntSrvXVenta.add(tsxvs);
        tablaVntRegistroventaSel = new TablaVntRegistroventa(tsxvs.getVntServxventa().getRgvtId());
        tablaPopOrdenProduccionSel.getPopOrdenprod().setRgvtId(tablaVntRegistroventaSel.getVntRegistroventa());
        cargarListaCiudades(null);
        if (tablaVntRegistroventaSel.getVntRegistroventa().getVdeId() != null && tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getCiuId() != null
                && tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getCiuId().getCiuId() != null) {

            opDepartamento = tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getCiuId().getDepId().getDepId();
            cargarListaCiudades(opDepartamento);
            opCiudad = tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getCiuId().getCiuId();
        }

        tablaPopOrdenProduccionSel.setCronograma(tsxvs.getCronogramaSel());

        numPanel = PANEL_CONSULTA_OP;
        cargarServXOrdenProd(lstTablaVntSrvXVenta);
        cargarListaColXCrgXEstado();

    }

    private void cargarServXOrdenProd(List<TablaVntSrvXVenta> lstSelFact) {
        lstTablaPopProdXServXOp.clear();

        ///
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprEstado(Boolean.TRUE);
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprProcesado(Boolean.FALSE);
        tablaPopOrdenProduccionSel.getPopOrdenprod().setEevId(new LgtEstadoevento(1));
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprEstadodespacho(0);
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprDescripcion("");
//        for (TablaVntSrvXVenta vs : lstSelFact) {
//            tablaPopOrdenProduccionSel.getPopOrdenprod().setOprDescripcion(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprDescripcion()
//                    + " " + vs.getVntServxventa().getVsrvId().getVsrvNombre());
//            PopServxop ps = new PopServxop();
//            ps.setVsrvId(vs.getVntServxventa().getVsrvId());
//            ps.setSxoCantsrv(vs.getCantidadSeleccionada());
//            ps.setOprId(tablaPopOrdenProduccionSel.getPopOrdenprod());
//            ps.setSxoEstado(Boolean.TRUE);
//            if (tablaPopOrdenProduccionSel.getPopOrdenprod().getPopServxopList() == null) {
//                tablaPopOrdenProduccionSel.getPopOrdenprod().setPopServxopList(new ArrayList<PopServxop>());
//            }
//            tablaPopOrdenProduccionSel.getPopOrdenprod().getPopServxopList().add(ps);
//            List<VntProdxsrv> pxs = popsfb.getLstVntProdxsrvXServicio(vs.getVntServxventa().getVsrvId().getVsrvId());
//
//            for (VntProdxsrv vpxs : pxs) {
//                PopProdxservxop pxsxo = new PopProdxservxop();
//                pxsxo.setPrdId(vpxs.getPrdId());
//                pxsxo.setPxsoCantprod(vpxs.getProdxsrvCantidad());
//                pxsxo.setPxsoEstado(Boolean.TRUE);
//                pxsxo.setPxsoEstadosalida(Boolean.FALSE);
//                pxsxo.setPxsoEstadoentrada(Boolean.FALSE);
//                pxsxo.setSxoId(ps);
//
//                if (ps.getPopProdxservxopList() == null) {
//                    ps.setPopProdxservxopList(new ArrayList<PopProdxservxop>());
//                }
//                ps.getPopProdxservxopList().add(pxsxo);
//                TablaPopProdXServXOp tppxsxo = new TablaPopProdXServXOp();
//                tppxsxo.setPopProdxservxop(pxsxo);
//                lstTablaPopProdXServXOp.add(tppxsxo);
//
//            }
//        }
        for (TablaVntSrvXVenta vs : lstSelFact) {
            tablaPopOrdenProduccionSel.getPopOrdenprod().setOprDescripcion(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprDescripcion()
                    + " " + vs.getVntServxventa().getVsrvId().getVsrvNombre());
            PopServxop ps = new PopServxop();
            ps.setVsrvId(vs.getVntServxventa().getVsrvId());
            ps.setSxoCantsrv(vs.getCantidadSeleccionada());
            ps.setOprId(tablaPopOrdenProduccionSel.getPopOrdenprod());
            ps.setSxoEstado(Boolean.TRUE);
            if (tablaPopOrdenProduccionSel.getPopOrdenprod().getPopServxopList() == null) {
                tablaPopOrdenProduccionSel.getPopOrdenprod().setPopServxopList(new ArrayList<PopServxop>());
            }
            tablaPopOrdenProduccionSel.getPopOrdenprod().getPopServxopList().add(ps);
            List<VntProdxsrv> pxs = popsfb.getLstVntProdxsrvXServicio(vs.getVntServxventa().getVsrvId().getVsrvId());

            for (VntProdxsrv vpxs : pxs) {
                PopProdxservxop pxsxo = new PopProdxservxop();
                pxsxo.setPrdId(vpxs.getPrdId());
                pxsxo.setPxsoCantprod(vpxs.getProdxsrvCantidad());
                //  jaor Abril 24 - modifica carga variable estado default True por valor del registro de producto //
                //  pxsxo.setPxsoEstado(Boolean.TRUE);    //
                pxsxo.setPxsoEstado(vpxs.getProdxsrvEst());
                pxsxo.setPxsoEstadosalida(Boolean.FALSE);
                pxsxo.setPxsoEstadoentrada(Boolean.FALSE);
                pxsxo.setSxoId(ps);
                pxsxo.setServicioAsociadoId(vs.getVntServxventa().getVsrvId().getVsrvId());
                pxsxo.setServicioAsociadoDesc(vs.getVntServxventa().getVsrvId().getVsrvNombre());
                if (ps.getPopProdxservxopList() == null) {
                    ps.setPopProdxservxopList(new ArrayList<PopProdxservxop>());
                }
                ps.getPopProdxservxopList().add(pxsxo);
                TablaPopProdXServXOp tppxsxo = new TablaPopProdXServXOp();
                tppxsxo.setPopProdxservxop(pxsxo);
                lstTablaPopProdXServXOp.add(tppxsxo);

            }
            List<VntServicioxservicio> listaSxS = popsfb.getLstVntServicioxservicio(vs.getVntServxventa().getVsrvId().getVsrvId());

            for (VntServicioxservicio vc : listaSxS) {
                pxs = popsfb.getLstVntProdxsrvXServicio(vc.getVntServicioHijo().getVsrvId());

                for (VntProdxsrv vpxs : pxs) {
                    PopProdxservxop pxsxo = new PopProdxservxop();
                    pxsxo.setPrdId(vpxs.getPrdId());
                    pxsxo.setPxsoCantprod(vpxs.getProdxsrvCantidad());
                    // jaor Abril 24 - modifica carga variable estado default True por valor del registro de producto //
                    // pxsxo.setPxsoEstado(Boolean.TRUE);   //
                    pxsxo.setPxsoEstado(vpxs.getProdxsrvEst());
                    pxsxo.setPxsoEstadosalida(Boolean.FALSE);
                    pxsxo.setPxsoEstadoentrada(Boolean.FALSE);
                    pxsxo.setSxoId(ps);
                    pxsxo.setServicioAsociadoId(vc.getVntServicioHijo().getVsrvId());
                    pxsxo.setServicioAsociadoDesc(vc.getVntServicioHijo().getVsrvNombre());
                    if (ps.getPopProdxservxopList() == null) {
                        ps.setPopProdxservxopList(new ArrayList<PopProdxservxop>());
                    }
                    ps.getPopProdxservxopList().add(pxsxo);
                    TablaPopProdXServXOp tppxsxo = new TablaPopProdXServXOp();
                    tppxsxo.setPopProdxservxop(pxsxo);
                    lstTablaPopProdXServXOp.add(tppxsxo);

                }
            }

        }

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">
    private void cargarParametrosXColaborador() {
        mapCitacion.clear();
        mapResponsable.clear();
        mapRoles.clear();
        mapUniforme.clear();
        lstCitacion.clear();
        lstResponsable.clear();
        lstRoles.clear();
        lstUniformes.clear();
        lstCitacion.add(itemSeleccioneInt);
        lstResponsable.add(itemSeleccioneInt);
        lstRoles.add(itemSeleccioneInt);
        lstUniformes.add(itemSeleccioneInt);

        for (PopCxccitacion c : popsfb.getLstPopCxccitacion(true)) {
            lstCitacion.add(new SelectItem(c.getCxciId(), c.getCxciCitacion()));
            mapCitacion.put(c.getCxciId(), c);
        }

        for (PopCxcrespon c : popsfb.getLstPopPopCxcrespon(true)) {
            lstResponsable.add(new SelectItem(c.getCxreId(), c.getCxreRespon()));
            mapResponsable.put(c.getCxreId(), c);
        }

        for (PopCxcrol c : popsfb.getLstPopCxcrol(true)) {
            lstRoles.add(new SelectItem(c.getCxrId(), c.getCxrRol()));
            mapRoles.put(c.getCxrId(), c);
        }

        for (PopCxcuniforme c : popsfb.getLstPopCxcuniforme(true)) {
            lstUniformes.add(new SelectItem(c.getCxuId(), c.getCxuUniforme()));
            mapUniforme.put(c.getCxuId(), c);
        }

    }

    private void cargarListaDepartamentos() {
        lstDepartamentos.clear();
        lstDepartamentos.add(itemSeleccione);
        for (RfDep ld : popsfb.getLstDepartamentos()) {
            getLstDepartamentos().add(new SelectItem(ld.getDepId(), ld.getDepDesc()));
        }
    }

    private void cargarListaCiudades(String dptoSel) {
        lstCiudades.clear();
        lstCiudades.add(itemSeleccioneLong);
        opCiudad = -1L;

        if (!(dptoSel == null || dptoSel.isEmpty() || dptoSel.equals("-1"))) {
            for (RfCiudad ec : popsfb.getLstCiudades(dptoSel)) {
                lstCiudades.add(new SelectItem(ec.getCiuId(), ec.getCiuDesc()));
            }
        }
    }

    private void cargarListaVentasAplicarOP() {
        lstTablaVntRegistroventa.clear();
        lstTablaVntRegistroventa.clear();
        for (VntRegistroventa registroventa : popsfb.getLstVntRegistroventaPendienteOP(tipoClienteSel, true, false)) {
            List<VntDetevento> lst = popsfb.getLstVntDetevento(registroventa.getRgvtId());
            for (VntDetevento d : lst) {
                registroventa.setVdeId(d);
                break;
            }
            TablaVntRegistroventa tvr = new TablaVntRegistroventa();
            tvr.setVntRegistroventa(registroventa);

            lstTablaVntRegistroventa.add(tvr);
        }
    }

    private void cargarListaOrdenProdSinAutorizar() {
        lstTablaPopOrdenProduccion.clear();
        for (PopOrdenprod ordenprod : popsfb.getLstPopOrdenprodXProcXEstDesp(false, 1)) {
            TablaPopOrdenProduccion tpop = new TablaPopOrdenProduccion();
            List<VntDetevento> lst = popsfb.getLstVntDetevento(ordenprod.getRgvtId().getRgvtId());
            for (VntDetevento d : lst) {
                ordenprod.getRgvtId().setVdeId(d);
                break;
            }
            tpop.setPopOrdenprod(ordenprod);

            lstTablaPopOrdenProduccion.add(tpop);
        }
    }

    private void cargarListaOrdenProdAutorizado() {
        lstTablaPopOrdenProduccion.clear();
        for (PopOrdenprod ordenprod : popsfb.getLstPopOrdenprodXProcesado(true)) {
            TablaPopOrdenProduccion tpop = new TablaPopOrdenProduccion();
            List<VntDetevento> lst = popsfb.getLstVntDetevento(ordenprod.getRgvtId().getRgvtId());
            for (VntDetevento d : lst) {
                ordenprod.getRgvtId().setVdeId(d);
                break;
            }
            tpop.setPopOrdenprod(ordenprod);

            lstTablaPopOrdenProduccion.add(tpop);
        }
    }

    private void cargarListaOrdenProduccion() {
        lstTablaPopOrdenProduccion.clear();
        for (PopOrdenprod ordenprod : popsfb.getLstPopOrdenprod()) {
            TablaPopOrdenProduccion tpop = new TablaPopOrdenProduccion();
            tpop.setPopOrdenprod(ordenprod);
            lstTablaPopOrdenProduccion.add(tpop);
        }
    }

    private void cargarServXOrdenProd() {
        lstTablaPopServXOp.clear();
        for (PopServxop servxop : popsfb.getLstPopServxOp(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId())) {
            TablaPopServXOp tpsxo = new TablaPopServXOp();
            tpsxo.setPopServxop(servxop);
            lstTablaPopServXOp.add(tpsxo);
        }
    }

    private void cargarListaProductoXOrdenProd() {
        lstTablaPopProdXServXOp.clear();
        for (PopProdxservxop prodxservxop : popsfb.getlstPopProdxservxopXOrdenProd(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId())) {
            TablaPopProdXServXOp tppxsxo = new TablaPopProdXServXOp();
            tppxsxo.setPopProdxservxop(prodxservxop);
            lstTablaPopProdXServXOp.add(tppxsxo);
        }
    }

    private void cargarListaColXCrgXEstado() {
        lstTablaAdmCrgXCol.clear();
        for (AdmCrgxcol crgxcol : popsfb.getLstAdmCrgxcolXEstado(true)) {
            TablaAdmCrgXCol tacxc = new TablaAdmCrgXCol(crgxcol);
            lstTablaAdmCrgXCol.add(tacxc);
        }
    }

    private void cargarListaProductosVarios() {
        lstTablaProducto.clear();
        for (InvProducto producto : popsfb.getLstInvProductoXVarios(true)) {
            TablaProducto tp = new TablaProducto();
            tp.setInvProducto(producto);
            lstTablaProducto.add(tp);
        }
    }

    private void grabarOrdenProduccion() {
        if (validarForm()) {
            List<PopProdxservxop> lstPopProdxservxopGrabar = new ArrayList<>();
            List<PopCxcevento> lstPopCxceventoGrabar = new ArrayList<>();
            PopOrdenprod pop = tablaPopOrdenProduccionSel.getPopOrdenprod();
            pop.setOprFechaproceso(new Date());
            pop.setOprProcesado(Boolean.TRUE);
            pop.setCxcId(pjsfb.getAdmCrgxcolActivo());
            pop.setEevId(popsfb.getLgtEstadoeventoById(EnEstadoLogistica.EVENTO_SIN_DESPACHAR.getId()));
            pop.setRfEstadoOP(popsfb.getRfEstadoOPById(EnEstadosOp.ACTIVO.getId()));
            pop = popsfb.editarOrdenProd(pop);
            pop.setFechacreacionop(new Date());

            for (PopServxop servxop : popsfb.getLstPopServxOp(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId())) {
                servxop.setSxoFechaproceso(new Date());
                servxop = popsfb.editarServXOrdenProd(servxop);
            }
//            PopServxop psxo = tablaPopServXOpSel.getPopServxop();
//            psxo.setSxoFechaproceso(new Date());
//            psxo = opsfb.editarServXOrdenProd(psxo);

//            for (TablaVntProdxsrv tvpxs : lstTablaVntProdxsrv) {
//                PopProdxservxop pxsxo = new PopProdxservxop();
//                pxsxo.setPxsoId(psxo.getSxoId());
//                pxsxo.setPxsoEstado(Boolean.TRUE);
//                pxsxo.setPxsoFechaproceso(new Date());
//                pxsxo.setStrId(pop.getStrId());
//                lstPopProdxservxopGrabar.add(pxsxo);
//            }
            for (TablaAdmCrgXCol tacxc : lstTablaAdmCrgXCol) {
                if (tacxc.isSeleccionado()) {
                    PopCxcevento cxce = new PopCxcevento();
                    cxce.setCxeEstado(Boolean.TRUE);
                    cxce.setCxeFechaproceso(new Date());
                    cxce.setOprId(tablaPopOrdenProduccionSel.getPopOrdenprod());
                    cxce.setCxcId(tacxc.getAdmCrgxcol());
                    lstPopCxceventoGrabar.add(cxce);

                }
            }

            if (!lstPopProdxservxopGrabar.isEmpty() || !lstPopCxceventoGrabar.isEmpty()) {
                popsfb.grabarLstProdxservxops(lstPopProdxservxopGrabar);
                popsfb.grabarLstCxceventos(lstPopCxceventoGrabar);
            }
            mostrarError("Grabación exitosa, ir a despacho de evento...!", 3);
        } else {
            mostrarError("Error al grabar", 1);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    public void hdDtCronogramaMostrarTodo_VCE(ValueChangeEvent vce) {
        blnMostrarTodoCron = ((Boolean) vce.getNewValue()).booleanValue();
        cargarCronograma();

    }

    private void cargarCronograma() {
        lstTablaServiciosPendientes.clear();
        int contador = 0;
        VntRegistroventa venta = null;
        for (VntCronograma c : this.cronogramaSLBean.getLstVntCronogramaPendienteOP(tipoClienteSel, true)) {

            TablaVntSrvXVenta s = new TablaVntSrvXVenta(c.getVntServxventa());
            c.getVntServxventa().getRgvtId().getVdeId();
            Integer pendiente = c.getVntServxventa().getSrvxventCantidad() - c.getVntServxventa().getSrvxventaProcesadaOP();
            s.setCantidadPendiente(pendiente);
            s.setCantidadSeleccionada(pendiente);
            s.setCronogramaSel(c);
            if (venta == null || venta.equals(c.getVntServxventa().getRgvtId())) {
                contador++;
            } else {
                venta = c.getVntServxventa().getRgvtId();
                contador = 1;
            }
            s.setInidceFila(contador);
//            lstTablaServiciosPendientes.add(s);
            if (blnMostrarTodoCron) {
                lstTablaServiciosPendientes.add(s);
            } else {
                if (s.getCronogramaSel().getNumeroOP() == null) {
                    lstTablaServiciosPendientes.add(s);
                }
            }
        }

    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnMostrarPanel = false;
        jasperResource = null;
        limpiarCampos();
        consultaOPDTO = new ConsultaOPDTO();
        lstTablaPopOrdenProduccion.clear();
        switch (numPanel) {
            case PANEL_CONSULTA_VENTAS:
                cargarListaVentasAplicarOP();
                cargarListaProductosVarios();
                blnNuevo = true;
                break;
            case PANEL_CONSULTA_PANEL_CONTROL:
                //TODO : load liata
                break;

            case CRONOGRAMA:
                cargarCronograma();
                break;
        }

    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
    }

    public void rbtCambiarPanel_ValueChangeListener(ValueChangeEvent vce) {
        String strValSel = String.valueOf(vce.getNewValue());
        tipoConsulta = Integer.parseInt(strValSel);
        switch (tipoConsulta) {
            case 1:
                cargarListaOrdenProdSinAutorizar();
                break;
            case 2:
                cargarListaOrdenProdAutorizado();
                break;
            case 3:
                cargarListaOrdenProduccion();
                break;
        }
    }

    public void rowDtOrdenProd_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaPopOrdenProduccionSel = (TablaPopOrdenProduccion) map.get("tops");
        blnMostrarPanel = true;
        cargarServXOrdenProd();
        cargarListaProductoXOrdenProd();
        cargarListaColXCrgXEstado();
    }

    public void editDtOrdenProd_ActionEvent(ActionEvent ae) {
        tablaPopOrdenProduccionSel = (TablaPopOrdenProduccion) ae.getComponent().getAttributes().get("tabla");
        blnMostrarPanel = true;
        blnNuevo = false;
        cargarServXOrdenProd();
        cargarListaProductoXOrdenProd();
        // cargarListaColXCrgXEstado();
        tablaVntRegistroventaSel = new TablaVntRegistroventa(tablaPopOrdenProduccionSel.getPopOrdenprod().getRgvtId());
        cargarServiciosXventa();
        for (PopCxcevento crgxcol : popsfb.getLstAdmCrgxcolXOrdenProduccion(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId())) {
            TablaAdmCrgXCol tacxc = new TablaAdmCrgXCol(crgxcol.getCxcId());
            tacxc.setSeleccionado(true);
            tacxc.seleccionarAdmCrgXCol(true);
            try {
                tacxc.setRolSeleccionado(crgxcol.getPopCxcrol().getCxrId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tacxc.setUniformeSeleccionado(crgxcol.getPopCxcuniforme().getCxuId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tacxc.setRespondableSeleccionado(crgxcol.getPopCxcrespon().getCxreId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tacxc.setCitacionSeleccionado(crgxcol.getPopCxccitacion().getCxciId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!lstTablaAdmCrgXCol.contains(tacxc)) {
                lstTablaAdmCrgXCol.add(tacxc);
            }
        }

        for (AdmCrgxcol crgxcol : popsfb.getLstAdmCrgxcolXEstado(true)) {
            TablaAdmCrgXCol tacxc = new TablaAdmCrgXCol(crgxcol);
            if (!lstTablaAdmCrgXCol.contains(tacxc)) {
                lstTablaAdmCrgXCol.add(tacxc);
            }

        }

        opDepartamento = tablaPopOrdenProduccionSel.getPopOrdenprod().getRgvtId().getVdeId().getCiuId().getDepId().getDepId();
        cargarListaCiudades(opDepartamento);
        opCiudad = tablaPopOrdenProduccionSel.getPopOrdenprod().getRgvtId().getVdeId().getCiuId().getCiuId();

    }

    public void rowDtOrdenProdConsulta_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaPopOrdenProduccionSel = (TablaPopOrdenProduccion) map.get("tops");
        blnMostrarPanel = true;
        cargarServXOrdenProd();
        // cargarListaProductoXOrdenProd();
        // cargarListaColXCrgXEstado();

        lstTablaAdmCrgXCol.clear();
        for (PopCxcevento crgxcol : popsfb.getLstAdmCrgxcolXOrdenProduccion(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId())) {
            TablaAdmCrgXCol tacxc = new TablaAdmCrgXCol(crgxcol.getCxcId());
            lstTablaAdmCrgXCol.add(tacxc);
        }
    }

    public void btnGrabarOrdenProd_ActionEvent(ActionEvent ae) {
        grabarOrdenProduccion();
        cargarListaOrdenProdSinAutorizar();
    }

    public void btnGenerarReporteOrdenProduccion_ActionEvent(ActionEvent ae) {
        boolean validar = false;
        //jasperResource = null;
        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechacitaini() == null) {
            mostrarError("Ingresar la Fecha y Hora de Citación en Maximus", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprDireccionevento() == null) {
            mostrarError("Ingresar Direccion del Evento", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechaevtini() == null) {
            mostrarError("Ingresar Inicio Fecha y Hora del evento", 1);
            validar = true;
        }
        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprOc() == null) {
            mostrarError("Ingresar la  Orden de Compra", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprTitulo() == null) {
            mostrarError("Ingresar el titulo de OP", 1);
            validar = true;
        }
        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechaevtfin() == null) {
            mostrarError("Ingresar Finalizacion del evento Fecha  y Hora ", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechamonini() == null) {
            mostrarError("Ingresar Fecha y Hora de Montaje ", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprProcesado() == null) {
            mostrarError("Ingresar Fecha y Hora de entrega a Logistica", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprContactoevento() == null) {
            mostrarError("Ingresar Contacto en el Evento", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprIndicacionllegada() == null) {
            mostrarError("Ingresar Indicación de LLegada", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprTransporte() == null) {
            mostrarError("Ingresar Indicación de Transporte", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechasal() == null) {
            mostrarError("Fecha y hora de salida de Maximus", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprProcesado() == null) {
            mostrarError("Fecha y hora de entrega a Logistica", 1);
            validar = true;
        }

        if (tablaPopOrdenProduccionSel.getPopOrdenprod().getOprTitulo() == null) {
            mostrarError("Ingrese Titulo de la OP", 1);
            validar = true;
        }

        if (this.opDepartamento == null || this.opDepartamento.isEmpty() || this.opDepartamento.equals("-1")) {
            mostrarError("Ingrese Departamento", 1);
            validar = true;
        }

        if (this.opCiudad == null || this.opCiudad.equals(-1L)) {
            mostrarError("Ingrese Ciudad", 1);
            validar = true;
        }


        /* if (ManejoFecha.compararDates(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechaevtini(), tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechaevtfin()) == 1) {
            mostrarError("Fecha Inicial del Evento no debe  mayor que la  Ficha Final del Evento", 1);
            validar = true;
        }
        if (ManejoFecha.compararDates(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechamonini(), tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechamonfin()) == 1) {
            mostrarError("Fecha Inicial del Montaje Evento no debe  mayor que la  Fecha Final del Montaje Evento", 1);
            validar = true;
        }

        if (ManejoFecha.compararDates(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechaevtini(), tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechamonini()) == 1) {
            mostrarError("Fecha Inicial del Evento no debe  mayor que la  Ficha del Montaje", 1);
            validar = true;
        }

        if (ManejoFecha.compararDates(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechacitaini(), tablaPopOrdenProduccionSel.getPopOrdenprod().getOprFechasal()) == 1) {
            mostrarError("Fecha Inicial de Citación no debe  mayor que la  Ficha  Final de Citación", 1);
            validar = true;
        }*/
        if (validar) {
            return;
        }

        int cantidadProcesa = 0;

        List<VntServxventa> lstSelAnulS = new ArrayList<>();
        for (TablaVntSrvXVenta g : lstTablaVntSrvXVenta) {
            cantidadProcesa += g.getCantidadSeleccionada();
            g.getVntServxventa().setSrvxventaProcesadaOP(g.getVntServxventa().getSrvxventaProcesadaOP() + g.getCantidadSeleccionada());
            lstSelAnulS.add(g.getVntServxventa());

        }
        tablaVntRegistroventaSel.getVntRegistroventa().setRgvtCantidadservprocesadosOp(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtCantidadservprocesadosOp() + cantidadProcesa);
        tablaVntRegistroventaSel.setVntRegistroventa(popsfb.editarRegVenta(tablaVntRegistroventaSel.getVntRegistroventa()));
        tablaVntRegistroventaSel.setVntRegistroventa(tablaVntRegistroventaSel.getVntRegistroventa());
        if (!lstSelAnulS.isEmpty()) {
            popsfb.editarRegVentaConServ(lstSelAnulS);
        }
        tablaPopOrdenProduccionSel.getPopOrdenprod().setCxcId(pjsfb.getAdmCrgxcolActivo());
        tablaPopOrdenProduccionSel.getPopOrdenprod().setPopCxceventoList(null);
        tablaPopOrdenProduccionSel.getPopOrdenprod().setRfEstadoOP(popsfb.getRfEstadoOPById(EnEstadosOp.ACTIVO.getId()));
        tablaPopOrdenProduccionSel.getPopOrdenprod().setFechacreacionop(new Date());
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprDepart(opDepartamento == null || opDepartamento.isEmpty() || opDepartamento.equals("-1") ? null : popsfb.getRfDepartament(opDepartamento));
        tablaPopOrdenProduccionSel.getPopOrdenprod().setOprCiudad(opCiudad == null || opCiudad.equals(-1L) ? null : popsfb.getRfCiudad(opCiudad));
        tablaPopOrdenProduccionSel.setPopOrdenprod(popsfb.editarPopOrdenprod(tablaPopOrdenProduccionSel.getPopOrdenprod()));
        List<PopCxcevento> lstPopCxceventoGrabar = new ArrayList<>();

        for (TablaAdmCrgXCol tacxc : lstTablaAdmCrgXCol) {
            if (tacxc.isSeleccionado()) {
                PopCxcevento cxce = new PopCxcevento();
                cxce.setCxeEstado(Boolean.TRUE);
                cxce.setCxeFechaproceso(new Date());

                cxce.setOprId(tablaPopOrdenProduccionSel.getPopOrdenprod());
                cxce.setCxcId(tacxc.getAdmCrgxcol());
                cxce.setPopCxccitacion(mapCitacion.get(tacxc.getCitacionSeleccionado()));
                cxce.setPopCxcrespon(mapResponsable.get(tacxc.getRespondableSeleccionado()));
                cxce.setPopCxcrol(mapRoles.get(tacxc.getRolSeleccionado()));
                cxce.setPopCxcuniforme(mapUniforme.get(tacxc.getUniformeSeleccionado()));
                lstPopCxceventoGrabar.add(cxce);

            }
        }

        popsfb.eliminarPopCxceventoPorOp(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId());
        if (!lstPopCxceventoGrabar.isEmpty()) {

            popsfb.grabarLstCxceventos(lstPopCxceventoGrabar);
        }

        if (tablaPopOrdenProduccionSel != null && tablaPopOrdenProduccionSel.getPopOrdenprod() != null && tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId() != null) {
            if (tablaPopOrdenProduccionSel.getCronograma() != null) {
                tablaPopOrdenProduccionSel.getCronograma().setPopOrdenprod(tablaPopOrdenProduccionSel.getPopOrdenprod());
                tablaPopOrdenProduccionSel.setCronograma(cronogramaSLBean.editarCronograma(tablaPopOrdenProduccionSel.getCronograma()));
            }
            jasperResource = generarInformeOP(tablaPopOrdenProduccionSel.getPopOrdenprod());
        }

        mostrarError("Guardado Exitoso!!", 3);
    }

    private com.icesoft.faces.context.Resource generarInformeOP(PopOrdenprod op) {
        HashMap hmParametros = new HashMap();
        hmParametros.put("p_idorden", op.getOprId());
        AdmColaborador admColLog = pjsfb.getColxempLog().getColCedula();

        String nombreColLog = admColLog.getColNombre1() + " " + admColLog.getColNombre2() + " " + admColLog.getColApellido1() + " " + admColLog.getColApellido2();
        hmParametros.put("strSolicitanteProd", nombreColLog);
        AdmInforme informe = astslb.getAdmInformeXId(EnInforme.ORDEN_DE_PRODUCCION_DE_CLIENTES_COORPORATIVOS.getId());

        String rutaLogo = informe.getInfJasperruta() + "/logos/maximus_corporativo.jpg";
        if (op.getRgvtId().getClnId().getTclId().getTclId().equals(EnTipoCliente.KIDS.getId())) {
            informe = astslb.getAdmInformeXId(EnInforme.ORDEN_DE_PRODUCCION_KIDS.getId());
            rutaLogo = informe.getInfJasperruta() + "/logos/maximus_kids.jpg";
            hmParametros.put("rutaFoto", rutaLogo);
        }
        return genInfRecurso(hmParametros, informe, 2, rutaLogo);

    }

    public void guardarCronograma_ActionEvent(ActionEvent ae) {

        List<VntCronograma> listaGrabar = new ArrayList<>();
        for (TablaVntSrvXVenta s : lstTablaServiciosPendientes) {
            listaGrabar.add(s.getCronogramaSel());
        }
        cronogramaSLBean.editarListCronograma(listaGrabar);
        mostrarError("Cronograma actualizado correctamente", 3);

    }

    public void btnRegresarOrdenProd_ActionEvent(ActionEvent ae) {

        numPanel = PANEL_CONSULTA_VENTAS;
        blnMostrarPanel = false;
        blnMostrarProductos = false;
        cargarListaVentasAplicarOP();
    }

    public void btnRegresarOrdenProdProc_ActionEvent(ActionEvent ae) {
        blnMostrarPanel = false;
    }

    public void btnAgregarProductosVarios_ActionEvent(ActionEvent ae) {
        blnMostrarProductos = true;
        cargarListaProductosVarios();
    }

    public void btnAgregarProducto_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaProductoSel = (TablaProducto) map.get("tps");
        if (tablaProductoSel.getCantProds() == null || tablaProductoSel.getCantProds() <= 0) {
            mostrarError("Debe digitar cantidad de producto a adicionar", 1);
            return;
        }
        boolean nuevo = true;
        for (PopServxop k : tablaPopOrdenProduccionSel.getPopOrdenprod().getPopServxopList()) {
            //  if (k.getVsrvId().getVsrvId().equals(UtilidadConstantes.SERVICIO_OTROS)) {

            if (k.getPopProdxservxopList() == null) {
                k.setPopProdxservxopList(new ArrayList<PopProdxservxop>());
            }
            for (PopProdxservxop p : k.getPopProdxservxopList()) {
                if (p.getPrdId().equals(tablaProductoSel.getInvProducto())) {
                    p.setPxsoCantprod(p.getPxsoCantprod() + tablaProductoSel.getCantProds());
                    for (TablaPopProdXServXOp tpxsxo : lstTablaPopProdXServXOp) {
                        if (tpxsxo.getPopProdxservxop().getPrdId().equals(tablaProductoSel.getInvProducto())) {
                            tpxsxo.getPopProdxservxop().setPxsoCantprod(p.getPxsoCantprod());

                            break;
                        }
                    }
                    nuevo = false;
                    break;
                }
            }
            //  break;
            //}
        }

        if (nuevo) {
            for (PopServxop psxo : tablaPopOrdenProduccionSel.getPopOrdenprod().getPopServxopList()) {

                PopProdxservxop pxsxo = new PopProdxservxop();
                pxsxo.setSxoId(psxo);
                pxsxo.setPrdId(tablaProductoSel.getInvProducto());
                pxsxo.setPxsoCantprod(tablaProductoSel.getCantProds());
                pxsxo.setPxsoEstado(Boolean.TRUE);
                pxsxo.setPxsoEstadosalida(Boolean.FALSE);
                pxsxo.setPxsoEstadoentrada(Boolean.FALSE);

                psxo.getPopProdxservxopList().add(pxsxo);
                tablaPopOrdenProduccionSel.getPopOrdenprod().getPopServxopList().add(psxo);
                TablaPopProdXServXOp tppxsxo = new TablaPopProdXServXOp();
                tppxsxo.setPopProdxservxop(pxsxo);
                if (lstTablaPopProdXServXOp.isEmpty()) {
                    lstTablaPopProdXServXOp.add(tppxsxo);
                } else {
                    lstTablaPopProdXServXOp.add(0, tppxsxo);
                }
                break;
            }
        }

        //psxo = popsfb.editarServXOrdenProd(psxo);
        mostrarError("Producto adicionados exitosamente", 3);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">
    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        consultarByParametros();
    }

    private void consultarByParametros() {
        List<PopOrdenprod> lista = popsfb.consultaOPByParametros(consultaOPDTO);
        lstTablaPopOrdenProduccion.clear();
        for (PopOrdenprod op : lista) {
            TablaPopOrdenProduccion t = new TablaPopOrdenProduccion(op);
            lstTablaPopOrdenProduccion.add(t);
        }
    }

    @Override
    public boolean grabarPaso() {
        return false;
    }

    @Override
    public boolean validarForm() {

        if (getOprTitulo() == null || getOprTitulo().equals("")) {
            mostrarError("Ingrese titulo OP", 1);
            return false;
        }

        return true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">
    private PcsOrdenProduccionSFBean lookupPcsOrdenProduccionSFBean() {
        try {
            Context c = new InitialContext();
            return (PcsOrdenProduccionSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/PcsOrdenProduccionSFBean!com.pandora.consulta.bean.PcsOrdenProduccionSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private OrdenProduccionJSFBean getOrdenProduccionJSFBean() {
        fc = FacesContext.getCurrentInstance();
        elc = fc.getELContext();
        return (OrdenProduccionJSFBean) elc.getELResolver().getValue(elc, null, "ordenProduccionJSFBean");
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
    /**
     * @return the blnMostrarPanel
     */
    public boolean isBlnMostrarPanel() {
        return blnMostrarPanel;
    }

    /**
     * @param blnMostrarPanel the blnMostrarPanel to set
     */
    public void setBlnMostrarPanel(boolean blnMostrarPanel) {
        this.blnMostrarPanel = blnMostrarPanel;
    }

    /**
     * @return the lstTablaPopOrdenProduccion
     */
    public List<TablaPopOrdenProduccion> getLstTablaPopOrdenProduccion() {
        return lstTablaPopOrdenProduccion;
    }

    /**
     * @param lstTablaPopOrdenProduccion the lstTablaPopOrdenProduccion to set
     */
    public void setLstTablaPopOrdenProduccion(List<TablaPopOrdenProduccion> lstTablaPopOrdenProduccion) {
        this.lstTablaPopOrdenProduccion = lstTablaPopOrdenProduccion;
    }

    /**
     * @return the tablaPopOrdenProduccionSel
     */
    public TablaPopOrdenProduccion getTablaPopOrdenProduccionSel() {
        return tablaPopOrdenProduccionSel;
    }

    /**
     * @param tablaPopOrdenProduccionSel the tablaPopOrdenProduccionSel to set
     */
    public void setTablaPopOrdenProduccionSel(TablaPopOrdenProduccion tablaPopOrdenProduccionSel) {
        this.tablaPopOrdenProduccionSel = tablaPopOrdenProduccionSel;
    }

    /**
     * @return the lstTablaPopServXOp
     */
    public List<TablaPopServXOp> getLstTablaPopServXOp() {
        return lstTablaPopServXOp;
    }

    /**
     * @param lstTablaPopServXOp the lstTablaPopServXOp to set
     */
    public void setLstTablaPopServXOp(List<TablaPopServXOp> lstTablaPopServXOp) {
        this.lstTablaPopServXOp = lstTablaPopServXOp;
    }

    /**
     * @return the lstTablaPopProdXServXOp
     */
    public List<TablaPopProdXServXOp> getLstTablaPopProdXServXOp() {
        return lstTablaPopProdXServXOp;
    }

    /**
     * @param lstTablaPopProdXServXOp the lstTablaPopProdXServXOp to set
     */
    public void setLstTablaPopProdXServXOp(List<TablaPopProdXServXOp> lstTablaPopProdXServXOp) {
        this.lstTablaPopProdXServXOp = lstTablaPopProdXServXOp;
    }

    /**
     * @return the lstTablaProducto
     */
    public List<TablaProducto> getLstTablaProducto() {
        return lstTablaProducto;
    }

    /**
     * @param lstTablaProducto the lstTablaProducto to set
     */
    public void setLstTablaProducto(List<TablaProducto> lstTablaProducto) {
        this.lstTablaProducto = lstTablaProducto;
    }

    /**
     * @return the blnMostrarProductos
     */
    public boolean isBlnMostrarProductos() {
        return blnMostrarProductos;
    }

    /**
     * @param blnMostrarProductos the blnMostrarProductos to set
     */
    public void setBlnMostrarProductos(boolean blnMostrarProductos) {
        this.blnMostrarProductos = blnMostrarProductos;
    }

    /**
     * @return the lstTablaAdmCrgXCol
     */
    public List<TablaAdmCrgXCol> getLstTablaAdmCrgXCol() {
        return lstTablaAdmCrgXCol;
    }

    /**
     * @param lstTablaAdmCrgXCol the lstTablaAdmCrgXCol to set
     */
    public void setLstTablaAdmCrgXCol(List<TablaAdmCrgXCol> lstTablaAdmCrgXCol) {
        this.lstTablaAdmCrgXCol = lstTablaAdmCrgXCol;
    }

    /**
     * @return the tablaProductoSel
     */
    public TablaProducto getTablaProductoSel() {
        return tablaProductoSel;
    }

    /**
     * @param tablaProductoSel the tablaProductoSel to set
     */
    public void setTablaProductoSel(TablaProducto tablaProductoSel) {
        this.tablaProductoSel = tablaProductoSel;
    }
    //</editor-fold>

    /**
     * @return the tipoConsulta
     */
    public Integer getTipoConsulta() {
        return tipoConsulta;
    }

    /**
     * @param tipoConsulta the tipoConsulta to set
     */
    public void setTipoConsulta(Integer tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public List<TablaVntRegistroventa> getLstTablaVntRegistroventa() {
        return lstTablaVntRegistroventa;
    }

    public void setLstTablaVntRegistroventa(List<TablaVntRegistroventa> lstTablaVntRegistroventa) {
        this.lstTablaVntRegistroventa = lstTablaVntRegistroventa;
    }

    public TablaVntRegistroventa getTablaVntRegistroventaSel() {
        return tablaVntRegistroventaSel;
    }

    public void setTablaVntRegistroventaSel(TablaVntRegistroventa tablaVntRegistroventaSel) {
        this.tablaVntRegistroventaSel = tablaVntRegistroventaSel;
    }

    public List<TablaVntSrvXVenta> getLstTablaServiciosPendientes() {
        return lstTablaServiciosPendientes;
    }

    public void setLstTablaServiciosPendientes(List<TablaVntSrvXVenta> lstTablaServiciosPendientes) {
        this.lstTablaServiciosPendientes = lstTablaServiciosPendientes;
    }

    public List<TablaVntSrvXVenta> getLstTablaVntSrvXVenta() {
        return lstTablaVntSrvXVenta;
    }

    public void setLstTablaVntSrvXVenta(List<TablaVntSrvXVenta> lstTablaVntSrvXVenta) {
        this.lstTablaVntSrvXVenta = lstTablaVntSrvXVenta;
    }

    public Integer getTipoClienteSel() {
        return tipoClienteSel;
    }

    public void setTipoClienteSel(Integer tipoClienteSel) {
        this.tipoClienteSel = tipoClienteSel;
    }

    public List<SelectItem> getLstCitacion() {
        return lstCitacion;
    }

    public void setLstCitacion(List<SelectItem> lstCitacion) {
        this.lstCitacion = lstCitacion;
    }

    public List<SelectItem> getLstResponsable() {
        return lstResponsable;
    }

    public void setLstResponsable(List<SelectItem> lstResponsable) {
        this.lstResponsable = lstResponsable;
    }

    public List<SelectItem> getLstRoles() {
        return lstRoles;
    }

    public void setLstRoles(List<SelectItem> lstRoles) {
        this.lstRoles = lstRoles;
    }

    public List<SelectItem> getLstUniformes() {
        return lstUniformes;
    }

    public void setLstUniformes(List<SelectItem> lstUniformes) {
        this.lstUniformes = lstUniformes;
    }

    /**
     * @return the oprTitulo
     */
    public String getOprTitulo() {
        return oprTitulo;
    }

    /**
     * @param oprTitulo the oprTitulo to set
     */
    public void setOprTitulo(String oprTitulo) {
        this.oprTitulo = oprTitulo;
    }

    public String getNombreArchivoGeneracionOp() {
        String salida = "";
        if (tablaPopOrdenProduccionSel != null && tablaPopOrdenProduccionSel.getPopOrdenprod() != null && tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId() != null) {
            salida = "" + tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId() + "_";
            String name = (tablaPopOrdenProduccionSel.getPopOrdenprod().
                    getRgvtId().getClnId().getClnAlias() == null
                    || tablaPopOrdenProduccionSel.getPopOrdenprod().
                            getRgvtId().getClnId().getClnAlias().isEmpty() ? (tablaPopOrdenProduccionSel.
                                    getPopOrdenprod().getRgvtId().getClnId().
                                    getNombres() == null ? ""
                                    : tablaPopOrdenProduccionSel.getPopOrdenprod().
                                            getRgvtId().getClnId().
                                            getNombres().replace(" ", "_")) : tablaPopOrdenProduccionSel.getPopOrdenprod().
                            getRgvtId().getClnId().getClnAlias());
            salida += name;
            salida += (tablaPopOrdenProduccionSel.getPopOrdenprod().getRgvtId().getVdeId().getVdeObsr() == null ? "" : "_" + tablaPopOrdenProduccionSel.getPopOrdenprod().getRgvtId().getVdeId().getVdeObsr().replace(" ", "_"));
            salida += "_" + convertDateToStringFormat(tablaPopOrdenProduccionSel.getPopOrdenprod().
                    getOprFechaevtini(), EnFormatDate.FORMAT_DATE_DD_MM_YYYY);
        }
        return salida;
    }

    /**
     * @return the lstDepartamentos
     */
    public List<SelectItem> getLstDepartamentos() {
        return lstDepartamentos;
    }

    /**
     * @param lstDepartamentos the lstDepartamentos to set
     */
    public void setLstDepartamentos(List<SelectItem> lstDepartamentos) {
        this.lstDepartamentos = lstDepartamentos;
    }

    /**
     * @return the lstCiudades
     */
    public List<SelectItem> getLstCiudades() {
        return lstCiudades;
    }

    /**
     * @param lstCiudades the lstCiudades to set
     */
    public void setLstCiudades(List<SelectItem> lstCiudades) {
        this.lstCiudades = lstCiudades;
    }

    /**
     * @return the OpDepartamento
     */
    public String getOpDepartamento() {
        return opDepartamento;
    }

    /**
     * @param opDepartamento the OpDepartamento to set
     */
    public void setOpDepartamento(String opDepartamento) {
        this.opDepartamento = opDepartamento;
    }

    /**
     * @return the OpCiudad
     */
    public Long getOpCiudad() {
        return opCiudad;
    }

    /**
     * @param OpCiudad the OpCiudad to set
     */
    public void setOpCiudad(Long OpCiudad) {
        this.opCiudad = OpCiudad;
    }

    public ConsultaOPDTO getConsultaOPDTO() {
        return consultaOPDTO;
    }

    public void setConsultaOPDTO(ConsultaOPDTO consultaOPDTO) {
        this.consultaOPDTO = consultaOPDTO;
    }

    public List<RfEstadoOP> getListaEstadoOP() {
        return listaEstadoOP;
    }

    public void setListaEstadoOP(List<RfEstadoOP> listaEstadoOP) {
        this.listaEstadoOP = listaEstadoOP;
    }

    /**
     * @return the blnNuevo
     */
    public boolean isBlnNuevo() {
        return blnNuevo;
    }

    /**
     * @param blnNuevo the blnNuevo to set
     */
    public void setBlnNuevo(boolean blnNuevo) {
        this.blnNuevo = blnNuevo;
    }

    public class MessageConfirmationOP implements Serializable {

        private String message;

        private TablaPopOrdenProduccion respuesta = new TablaPopOrdenProduccion();

        public MessageConfirmationOP() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public TablaPopOrdenProduccion getRespuesta() {
            return respuesta;
        }

        public void setRespuesta(TablaPopOrdenProduccion respuesta) {
            this.respuesta = respuesta;
        }

        public void update(ActionEvent ae) {
            respuesta.getPopOrdenprod().setCxcId(pjsfb.getAdmCrgxcolActivo());
            respuesta.getPopOrdenprod().setRfEstadoOP(popsfb.getRfEstadoOPById(EnEstadosOp.ANULADO.getId()));
            respuesta.setPopOrdenprod(popsfb.editarOrdenProd(respuesta.getPopOrdenprod()));
            consultarByParametros();
        }

        public void loadLogs(ActionEvent ae) {
            respuesta = (TablaPopOrdenProduccion) ae.getComponent().getAttributes().get("tabla");
            respuesta.loadLogFactura(popsfb.consultarLogByOP(respuesta.getPopOrdenprod().getOprId()));
        }

        public void generarReporte(ActionEvent ae) {
            this.respuesta = (TablaPopOrdenProduccion) ae.getComponent().getAttributes().get("tabla");

            this.respuesta.setResource(generarInformeOP(this.respuesta.getPopOrdenprod()));

        }

    }

    public MessageConfirmationOP getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(MessageConfirmationOP confirmation) {
        this.confirmation = confirmation;
    }

    public boolean isBlnMostrarTodoCron() {
        return blnMostrarTodoCron;
    }

    public void setBlnMostrarTodoCron(boolean blnMostrarTodoCron) {
        this.blnMostrarTodoCron = blnMostrarTodoCron;
    }

}
