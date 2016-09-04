/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procesos;

import adm.sys.dao.AdmInforme;
import com.pandora.adm.rf.dao.RfEstadofactura;
import com.pandora.bussiness.util.EnEstadoFactura;
import com.pandora.bussiness.util.EnEstadosVentaOp;
import com.pandora.consulta.bean.ConsultaFacturaDTO;
import com.pandora.consulta.bean.PcsVentaSFBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.logistica.dao.LgtEstadoevento;
import com.pandora.mod.ordenprod.CronogramaSLBean;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.mod.venta.dao.VntCronograma;
import com.pandora.mod.venta.dao.VntDetallefact;
import com.pandora.mod.venta.dao.VntDetallerem;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.mod.venta.dao.VntEstventa;
import com.pandora.mod.venta.dao.VntFactura;
import com.pandora.mod.venta.dao.VntPagoventa;
import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.mod.venta.dao.VntRangoFacturacion;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntRemision;
import com.pandora.mod.venta.dao.VntServxventa;
import com.pandora.pagocuenta.dao.FinFormapago;
import com.pandora.pagocuenta.dao.RfBanco;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.venta.TablaVntFactura;
import com.pandora.web.venta.TablaVntRegistroventa;
import com.pandora.web.venta.TablaVntRemision;
import com.pandora.web.venta.TablaVntSrvXVenta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
public class PcsVentasJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;

    @EJB
    private PcsVentaSFBean pvsfb;
    @EJB
    private CronogramaSLBean cronogramaSLBean;
    private com.icesoft.faces.context.Resource jaspResource; //jasperResourceZip
    private List<RfEstadofactura> listaEstados = new ArrayList<>();
    //variables de consulta
    private ConsultaFacturaDTO consultaFacturaDTO = new ConsultaFacturaDTO();
    private List<TablaRespuestaFacturaDTO> listaConsultaFactura = new ArrayList<>();
    //Manejo de paneles
    private static final int PANEL_CONFIRMAR_PAGO = 1;
    private static final int PANEL_FACTURA_VENTA = 2;
    private static final int PANEL_REMISION_VENTA = 3;
    private static final int PANEL_CONSULTA_VENTA = 4;

    private boolean blnMostrarPanel;
    private boolean blnMostrarInf;
    //Pagos venta
    private Integer tipoConsulta;
    private List<TablaVntRegistroventa> lstTablaVntRegistroventa = new ArrayList<>();
    private TablaVntRegistroventa tablaVntRegistroventaSel = new TablaVntRegistroventa();
    private List<TablaVntSrvXVenta> lstTablaVntSrvXVenta = new ArrayList<>();
    private List<VntCronograma> lstCronogramas = new ArrayList<>();
    private List<SelectItem> lstFormaPago = new ArrayList<>();
    private Integer idFormaPago = -1;
    private BigDecimal bdValorPago;
    private BigDecimal bdTotalPagado;
    private boolean blnAplicaBanco = false;
    private Long idBanco = -1L;
    private List<SelectItem> lstBancos = new ArrayList<>();
    //Factura venta
    private List<TablaVntFactura> lstTablaVntFactura = new ArrayList<>();
    private TablaVntFactura tablaVntFacturaSel = new TablaVntFactura();
    private Date datFechaFactura = new Date();
    private String strObservacionFact;
    //variables factura
    private VntFactura factura = new VntFactura();
    private List<VntDetallefact> lstDetalleXFact = new ArrayList<>();
    private VntRemision remision = new VntRemision();
    private List<VntDetallerem> lstDetalleXRemision = new ArrayList<>();
    //variables de pagos
    private VntPagoventa pago = new VntPagoventa();
    private List<VntPagoventa> lstVntPagoventa = new ArrayList<>();
    private List<SelectItem> lstTiposConsulta = new ArrayList<>();
    private String numFact = "";
    private Long idFactrura = null;
    private boolean remisionOFactura = true;
    private boolean blnMostrarPanelRemision = true;

    private BigDecimal porcentajeIVA = new BigDecimal(16);
    private BigDecimal valorVentaInicial = new BigDecimal(0);
    private BigDecimal valorVentaSubTotal = new BigDecimal(0);
    private boolean blnMostrarPanelSeleccion = true;
    private List<TablaVntSrvXVenta> lstTablaServiciosPendientes = new ArrayList<>();
    private MessageConfirmation confirmation = new MessageConfirmation();

    public MessageConfirmation getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(MessageConfirmation confirmation) {
        this.confirmation = confirmation;
    }

    public String getNumFact() {
        return numFact;
    }

    public void setNumFact(String numFact) {
        this.numFact = numFact;
    }

    public List<SelectItem> getLstTiposConsulta() {
        return lstTiposConsulta;
    }

    public void setLstTiposConsulta(List<SelectItem> lstTiposConsulta) {
        this.lstTiposConsulta = lstTiposConsulta;
    }

    public List<VntPagoventa> getLstVntPagoventa() {
        return lstVntPagoventa;
    }

    public void setLstVntPagoventa(List<VntPagoventa> lstVntPagoventa) {
        this.lstVntPagoventa = lstVntPagoventa;
    }

    public List<VntDetallefact> getLstDetalleXFact() {
        return lstDetalleXFact;
    }

    public void setLstDetalleXFact(List<VntDetallefact> lstDetalleXFact) {
        this.lstDetalleXFact = lstDetalleXFact;
    }

    public VntFactura getFactura() {
        return factura;
    }

    public void setFactura(VntFactura factura) {
        this.factura = factura;
    }
    //Remisión venta
    private List<TablaVntRemision> lstTablaVntRemision = new ArrayList<>();
    private TablaVntRemision tablaVntRemisionSel = new TablaVntRemision();
    private Date datFechaRemision = new Date();
    private String strObservacionRem;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Metodos del Bean">
    @Override
    public void init() {
        confirmation = new MessageConfirmation();
        pvsfb = lookupPcsVentaSFBean();
        numPanel = PANEL_CONSULTA_VENTA;
        blnMostrarPanel = false;
        blnMostrarInf = false;
        idFormaPago = -1;
        tipoConsulta = 2;

        lstDetalleXFact.clear();

        remision = new VntRemision();
        lstDetalleXRemision.clear();
        lstVntPagoventa.clear();

        lstTiposConsulta.clear();
        lstTiposConsulta.add(new SelectItem(2, "Confirmadas"));
        lstTiposConsulta.add(new SelectItem(1, "No Confirmadas"));
        lstTiposConsulta.add(new SelectItem(3, "Todas"));
        blnMostrarPanelRemision = false;
        cargarBancos();
        cargarListaFormaPago();
        if (getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente() != null
                && getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente().getTclId().equals(EnTipoCliente.KIDS.getId())) {

            blnMostrarPanelRemision = true;
        }
        porcentajeIVA = (getPrincipalJSFBean().getColxempLog().getEmpId().getEmpIva() == null ? new BigDecimal(16) : getPrincipalJSFBean().getColxempLog().getEmpId().getEmpIva());
        consultaFacturaDTO = new ConsultaFacturaDTO();
        listaConsultaFactura = new ArrayList<>();
    }

    @Override
    public void limpiarVariables() {
        pvsfb.remove();
    }

    private void limpiarCampos() {
        consultaFacturaDTO = new ConsultaFacturaDTO();
        listaConsultaFactura = new ArrayList<>();
        switch (numPanel) {
            case PANEL_CONFIRMAR_PAGO:
                idFormaPago = -1;
                lstVntPagoventa.clear();
                bdValorPago = null;
                break;
            case PANEL_FACTURA_VENTA:
                strObservacionFact = "";
                lstTablaVntSrvXVenta.clear();
                break;
            case PANEL_REMISION_VENTA:
                strObservacionRem = "";
                lstTablaVntSrvXVenta.clear();
                break;
        }
    }

    public void generarOrdenProduccion(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntRegistroventaSel = (TablaVntRegistroventa) map.get("trvs");
        PopOrdenprod po = new PopOrdenprod();
        po.setRgvtId(tablaVntRegistroventaSel.getVntRegistroventa());
        po.setOprEstado(Boolean.TRUE);
        po.setOprProcesado(Boolean.FALSE);
        po.setEevId(new LgtEstadoevento(1));
        po.setOprEstadodespacho(0);
        for (VntServxventa vs : pvsfb.getLstServxventaXVnt(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            PopServxop ps = new PopServxop();
            ps.setVsrvId(vs.getVsrvId());
            ps.setSxoCantsrv(vs.getSrvxventCantidad());
            ps.setOprId(po);
            ps.setSxoEstado(Boolean.TRUE);
            if (po.getPopServxopList() == null) {
                po.setPopServxopList(new ArrayList<PopServxop>());
            }
            po.getPopServxopList().add(ps);
            List<VntProdxsrv> pxs = pvsfb.getLstVntProdxsrvXServicio(vs.getVsrvId().getVsrvId());
            for (VntProdxsrv vpxs : pxs) {
                PopProdxservxop pxsxo = new PopProdxservxop();
                pxsxo.setPrdId(vpxs.getPrdId());
                pxsxo.setPxsoCantprod(vpxs.getProdxsrvCantidad());
                pxsxo.setPxsoEstado(Boolean.TRUE);
                pxsxo.setPxsoEstadosalida(Boolean.FALSE);
                pxsxo.setPxsoEstadoentrada(Boolean.FALSE);
                pxsxo.setSxoId(ps);
                if (ps.getPopProdxservxopList() == null) {
                    ps.setPopProdxservxopList(new ArrayList<PopProdxservxop>());
                }
                ps.getPopProdxservxopList().add(pxsxo);
            }
        }
        po = pvsfb.editarPopOrdenprod(po);
        tablaVntRegistroventaSel.getVntRegistroventa().setRgvtIdordenprod(po.getOprId());
        pvsfb.editarRegVenta(tablaVntRegistroventaSel.getVntRegistroventa());
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">
    //<editor-fold defaultstate="collapsed" desc="Pagos venta">
    private void cargarPagosXVentas(Long idVenta) {
        lstVntPagoventa.clear();
        for (VntPagoventa c : pvsfb.getLstVntPagoventaXVenta(idVenta)) {
            lstVntPagoventa.add(c);

        }

    }

    private void cargarListaRegVentaSinConfirmar() {
        lstTablaVntRegistroventa.clear();
        for (VntRegistroventa r : pvsfb.getLstVntRegistroventaXPagoConf(3, 4, false, true)) {
            List<VntDetevento> lst = pvsfb.getLstVntDetevento(r.getRgvtId());
            for (VntDetevento d : lst) {
                r.setVdeId(d);
                break;
            }
            if (blnMostrarPanelRemision) {
                if (r.getClnId().getTclId().getTclId() == EnTipoCliente.KIDS.getId()) {
                    TablaVntRegistroventa tvr = new TablaVntRegistroventa();

                    tvr.setVntRegistroventa(r);
                    lstTablaVntRegistroventa.add(tvr);
                    if (r.getEstrvntId().getEstrvntId().equals(3)) {
                        tvr.setEstiloFila("textTableError");
                    }
                }
            } else if (r.getClnId().getTclId().getTclId() == EnTipoCliente.COORPORATIVO.getId()) {
                TablaVntRegistroventa tvr = new TablaVntRegistroventa();
                tvr.setVntRegistroventa(r);
                if (r.getEstrvntId().getEstrvntId().equals(3)) {
                    tvr.setEstiloFila("textTableError");
                }
                lstTablaVntRegistroventa.add(tvr);
            }
        }
    }

    private void cargarListaRegVentaConfirmado() {
        lstTablaVntRegistroventa.clear();
        for (VntRegistroventa r : pvsfb.getLstVntRegistroventaXPagoConf(5, 5, true, true)) {
            List<VntDetevento> lst = pvsfb.getLstVntDetevento(r.getRgvtId());
            for (VntDetevento d : lst) {
                r.setVdeId(d);
                break;
            }
            if (blnMostrarPanelRemision) {
                if (r.getClnId().getTclId().getTclId() == EnTipoCliente.KIDS.getId()) {
                    TablaVntRegistroventa tvr = new TablaVntRegistroventa();
                    tvr.setVntRegistroventa(r);
                    if (r.getEstrvntId().getEstrvntId().equals(3)) {
                        tvr.setEstiloFila("textTableError");
                    }
                    lstTablaVntRegistroventa.add(tvr);
                }
            } else if (r.getClnId().getTclId().getTclId() == EnTipoCliente.COORPORATIVO.getId()) {
                TablaVntRegistroventa tvr = new TablaVntRegistroventa();
                tvr.setVntRegistroventa(r);
                if (r.getEstrvntId().getEstrvntId().equals(3)) {
                    tvr.setEstiloFila("textTableError");
                }
                lstTablaVntRegistroventa.add(tvr);
            }
        }
    }

    private void cargarListaRegistroVenta() {
        lstTablaVntRegistroventa.clear();
        for (VntRegistroventa r : pvsfb.getLstVntRegistroventa()) {
            if (blnMostrarPanelRemision) {
                if (r.getClnId().getTclId().getTclId() == EnTipoCliente.KIDS.getId()) {
                    TablaVntRegistroventa tvr = new TablaVntRegistroventa();
                    tvr.setVntRegistroventa(r);
                    if (r.getEstrvntId().getEstrvntId().equals(3)) {
                        tvr.setEstiloFila("textTableError");
                    }
                    lstTablaVntRegistroventa.add(tvr);
                }
            } else if (r.getClnId().getTclId().getTclId() == EnTipoCliente.COORPORATIVO.getId()) {
                TablaVntRegistroventa tvr = new TablaVntRegistroventa();
                tvr.setVntRegistroventa(r);
                if (r.getEstrvntId().getEstrvntId().equals(3)) {
                    tvr.setEstiloFila("textTableError");
                }
                lstTablaVntRegistroventa.add(tvr);
            }
        }
    }

    private void cargarBancos() {
        lstBancos.clear();
        lstBancos.add(new SelectItem(-1L, "SELECCIONE >>"));
        for (RfBanco b : pvsfb.getLstRfBancoXEstado(true)) {
            lstBancos.add(new SelectItem(b.getBncId(), b.getBncNombre()));
        }
    }

    public void cargarListaFormaPago() {
        lstFormaPago.clear();
        lstFormaPago.add(new SelectItem(-1, "SELECCIONE >>"));
        for (FinFormapago ff : pvsfb.getLstFinFormapagoXEstado(true)) {
            lstFormaPago.add(new SelectItem(ff.getFpgId(), ff.getFpgNombre()));
        }
    }

    private void cargarListaServXVenta() {
        lstTablaVntSrvXVenta.clear();
        for (VntServxventa servxventa : pvsfb.getLstServxventaXVnt(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            TablaVntSrvXVenta tvsxv = new TablaVntSrvXVenta();
            tvsxv.setVntServxventa(servxventa);
            lstTablaVntSrvXVenta.add(tvsxv);
        }
    }

    private void confirmarPago() {
        //validación 
        FinFormapago f = null;
        RfBanco banco = null;
        if (idFormaPago == null || idFormaPago == -1) {
            mostrarError("Forma de Pago es requerido.", 1);
            return;

        } else {
            f = pvsfb.getFinFormapagoXID(idFormaPago);
            if (f == null) {
                mostrarError("Forma de Pago es requerido.", 1);
                return;
            } else if (f.isFpgAplicabanco()) {
                if (idBanco == -1) {
                    mostrarError("Banco requerido.", 1);
                    return;
                } else {
                    banco = pvsfb.getRfBancoXID(idBanco);

                }
            }
        }
        if (pago.getRgpcValorpago() == null) {
            mostrarError("Valor a pagar requerido.", 1);
            return;
        }
        if (pago.getRgpcValorpago().compareTo(new BigDecimal(0)) != 1) {
            mostrarError("El Valor del pago debe ser mayor a cero ", 1);

            return;
        }
        if (pago.getRgpcValorpago().compareTo(bdValorPago) == 1) {
            mostrarError("El Valor del pago no puede exceder el valor pendiente de pago ", 1);

            return;
        }
        pago.setFinFormapago(f);
        pago.setRgvtId(tablaVntRegistroventaSel.getVntRegistroventa());
        pago.setBncId(banco);
        if (tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorpagado() == null) {
            tablaVntRegistroventaSel.getVntRegistroventa().setRgvtValorpagado(new BigDecimal(0));
        }
        tablaVntRegistroventaSel.getVntRegistroventa().setRgvtValorpagado(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorpagado().add(pago.getRgpcValorpago()));
        tablaVntRegistroventaSel.getVntRegistroventa().setEstrvntId(new VntEstventa(4));
        tablaVntRegistroventaSel.getVntRegistroventa().setRgvtPagado(false);
        if (tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorpagado().compareTo(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa()) == 0) {
            tablaVntRegistroventaSel.getVntRegistroventa().setEstrvntId(new VntEstventa(5));
            tablaVntRegistroventaSel.getVntRegistroventa().setRgvtPagado(true);
        }
        pago = pvsfb.editarPago(pago);
        pvsfb.editarRegVenta(tablaVntRegistroventaSel.getVntRegistroventa());
        mostrarError("Grabación exitosa, ir a orden de producción...!", 3);
        blnMostrarPanel = false;
        cargarListaRegVentaSinConfirmar();

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Factura venta">
    private void cargarListaRegVentaXClienteCorp() {
        lstTablaVntRegistroventa.clear();
        for (VntRegistroventa registroventa : pvsfb.getLstVntRegistroventaXClientePendienteProcesar(EnTipoCliente.COORPORATIVO.getId(), EnEstadosVentaOp.FACTURAR_Y_OP.getId(), EnEstadosVentaOp.ACTIVO.getId())) {
            List<VntDetevento> lst = pvsfb.getLstVntDetevento(registroventa.getRgvtId());
            for (VntDetevento d : lst) {
                registroventa.setVdeId(d);
                break;
            }
            TablaVntRegistroventa tvr = new TablaVntRegistroventa();
            tvr.setVntRegistroventa(registroventa);
            if (registroventa.getEstrvntId().getEstrvntId().equals(3)) {
                tvr.setEstiloFila("textTableError");
            }
            lstTablaVntRegistroventa.add(tvr);
        }

        /* for (VntRegistroventa registroventa : pvsfb.getLstVntRegistroventaXCliente(1, 4, true)) {
         List<VntDetevento> lst = pvsfb.getLstVntDetevento(registroventa.getRgvtId());
         for (VntDetevento d : lst) {
         registroventa.setVdeId(d);
         break;
         }
         TablaVntRegistroventa tvr = new TablaVntRegistroventa();
         tvr.setVntRegistroventa(registroventa);
         if (registroventa.getEstrvntId().getEstrvntId().equals(3)) {
         tvr.setEstiloFila("textTableError");
         }
         lstTablaVntRegistroventa.add(tvr);
         }
         */
    }

    private void cargarListaFacturaProcesada() {
        lstTablaVntFactura.clear();
        for (VntFactura factura : pvsfb.getLstVntFacturaXRegVentaConf(true, 2)) {
            TablaVntFactura tvf = new TablaVntFactura();
            tvf.setVntFactura(factura);
            lstTablaVntFactura.add(tvf);
        }
    }

    private void cargarListaServicioXVentaXFactura() {
        lstTablaVntSrvXVenta.clear();
        for (VntServxventa servxventa : pvsfb.getLstVntServxventaXFactura(tablaVntFacturaSel.getVntFactura().getVfctId())) {
            TablaVntSrvXVenta tvsxv = new TablaVntSrvXVenta();
            tvsxv.setVntServxventa(servxventa);
            lstTablaVntSrvXVenta.add(tvsxv);
        }
    }

//    private void cargarListaRegVentaXPagoConf() {
//        lstTablaVntRegistroventa.clear();
//        for (VntRegistroventa registroventa : pvsfb.getLstVntRegistroventaXConf(true)) {
//            TablaVntRegistroventa tvrv = new TablaVntRegistroventa();
//            tvrv.setVntRegistroventa(registroventa);
//            lstTablaVntRegistroventa.add(tvrv);
//        }
//    }
    private boolean validarFactura() {
        boolean validador = true;
        if (factura.getFactclNombres() == null || factura.getFactclNombres().trim().equals("")) {
            mostrarError("Nombres del cliente es requerido", 1);
            validador = false;
        }

        if (factura.getFactclIdentificacion() == null || factura.getFactclIdentificacion().trim().equals("")) {
            mostrarError("Documento del cliente es requerido", 1);
            validador = false;
        }
        if (factura.getFactclEmail() == null || factura.getFactclEmail().trim().equals("")) {
            mostrarError("Documento del cliente es requerido", 1);
            validador = false;
        }

        if (factura.getFactclFijo() == null || factura.getFactclFijo().trim().equals("")) {
            mostrarError("Telefono del cliente es requerido", 1);
            validador = false;
        }

        if (factura.getFactclFormapago() == null || factura.getFactclFormapago().trim().equals("")) {
            mostrarError("Forma de Pago requerido", 1);
            validador = false;
        }

        if (factura.getVfctnumdiaspago() == null || factura.getVfctnumdiaspago() <= 0) {
            mostrarError("el numero de  dias  no puede  nulo tampoco puede ser igual o menor a  0", 1);
            validador = false;
        }

        if (factura.getFactsrOrdencliente() == null || factura.getFactsrOrdencliente().trim().equals("")) {
            mostrarError("Orden de trabajo es requerido", 1);
            validador = false;
        }

        if (factura.getFactdcObservacion() == null || factura.getFactdcObservacion().trim().equals("")) {
            mostrarError("Protagonista es  requerido", 1);
            validador = false;
        }
        if (factura.getFactsrDetalle() == null || factura.getFactsrDetalle().trim().equals("")) {
            mostrarError("Detalle es requerido", 1);
            validador = false;
        }

        if (factura.getFactsrProtagonista() == null || factura.getFactsrProtagonista().trim().equals("")) {
            mostrarError("Protagonista es requerido", 1);
            validador = false;
        }

        if (factura.getFactsrFecha() == null) {
            mostrarError("Fecha evento es requerido", 1);
            validador = false;
        }

        if (factura.getFactdcDescripcion() == null) {
            mostrarError("Protagonista es requerido", 1);
            validador = false;
        }

        if (factura.getFactsrDireccionevento() == null || factura.getFactsrDireccionevento().trim().equals("")) {
            mostrarError("Dirección evento es requerido", 1);
            validador = false;
        }

        if (factura.getFactsrMotivo() == null || factura.getFactsrMotivo().trim().equals("")) {
            mostrarError("Motivo evento es requerido", 1);
            validador = false;
        }

        if (factura.getFactsrHora() == null || factura.getFactsrHora().trim().equals("")) {
            mostrarError("Hora evento es requerido", 1);
            validador = false;
        }

        if (factura.getFactdcDescripcion() == null || factura.getFactdcDescripcion().trim().equals("")) {
            mostrarError("Dirección evento es requerido", 1);
            validador = false;
        }

        if (factura.getVfctSaldo().compareTo(BigDecimal.ZERO) == -1) {
            mostrarError("El Saldo de la factura no debe ser menor que 0.", 1);
            validador = false;
        }
        if (valorVentaInicial.compareTo(factura.getVfctDescuento()) == -1) {
            mostrarError("Descuento no debe ser mayor que el total de la factura", 1);
            factura.setVfctDescuento(new BigDecimal(0));
            validador = false;
        }
        for (VntDetallefact vdf : lstDetalleXFact) {
            if (vdf.getVdftServico() == null || vdf.getVdftServico().trim().equals("")) {
                mostrarError("Faltan servicios por completar.", 1);
                validador = false;
            }
        }

        return validador;
    }

    private boolean editarFactura() {

        try {

            factura.setVfctFechafactura(new Date());
            factura.setVfctFechaproceso(new Date());

            factura.setEftId(pvsfb.getRfEstadofacturaXID(EnEstadoFactura.EMITIDO.getId()));
            factura.setCxcId(pjsfb.getAdmCrgxcolActivo());
            //factura.setVfctNumfactura("0");
            if (factura.getVfctId() == null) {
                VntRangoFacturacion rango
                        = pvsfb.getVntRangoFacturacionXTipoCliente(getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente().getTclId());
                if (rango != null) {
                    String numero = null;
                    if (rango.getNumActual() <= 9) {
                        numero = "000" + rango.getNumActual();
                    } else if (rango.getNumActual() <= 99) {
                        numero = "00" + rango.getNumActual();
                    } else if (rango.getNumActual() <= 999) {
                        numero = "0" + rango.getNumActual();
                    }
                    factura.setVfctNumfactura(numero);
                    rango.setNumActual(rango.getNumActual() + 1);
                    rango = pvsfb.editarVntRangoFacturacion(rango);
                } else {
                    factura.setVfctNumfactura("0000");
                }

            }
            factura.setTclid(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getTclId().getTclId());
            factura = pvsfb.editarFactura(factura, false);
            //factura.setVfctNumfactura(String.valueOf(factura.getVfctId()));
            tablaVntRegistroventaSel.getVntRegistroventa().setRgvtFacturada(true);
            tablaVntRegistroventaSel.getVntRegistroventa().setRgvtVlrfactura(factura.getVfctValortotal());
            pvsfb.editarRegVenta(tablaVntRegistroventaSel.getVntRegistroventa());
            factura.setRgvtId(tablaVntRegistroventaSel.getVntRegistroventa());
            factura = pvsfb.editarFactura(factura, true);

            int cantidadProcesa = 0;
            for (VntDetallefact vdf : lstDetalleXFact) {

                vdf.setVdftEstado(Boolean.TRUE);
                vdf.setVfctId(factura);
                cantidadProcesa += vdf.getVdftCantidad();
                vdf = pvsfb.grabarDetalleFact(vdf);
            }
            List<VntServxventa> lstSelAnulS = new ArrayList<>();
            for (TablaVntSrvXVenta g : lstTablaVntSrvXVenta) {

                g.getVntServxventa().setSrvxventaProcesada(g.getVntServxventa().getSrvxventaProcesada() + g.getCantidadSeleccionada());
                lstSelAnulS.add(g.getVntServxventa());

            }
            tablaVntRegistroventaSel.getVntRegistroventa().setRgvtCantserviciosProcesados(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtCantserviciosProcesados() + cantidadProcesa);
            pvsfb.editarRegVenta(tablaVntRegistroventaSel.getVntRegistroventa());
            tablaVntRegistroventaSel.setVntRegistroventa(pvsfb.getVntRegistroventaSel());
            if (!lstSelAnulS.isEmpty()) {
                pvsfb.editarRegVentaConServ(lstSelAnulS);
            }
            if (!lstCronogramas.isEmpty()) {
                for (VntCronograma c : lstCronogramas) {
                    c.setVntFactura(factura);
                }
                cronogramaSLBean.editarListCronograma(lstCronogramas);
            }

            mostrarError("Datos Guardados exitosamente ", 3);
            return true;
        } catch (Exception e) {
            mostrarError("Error al grabar la factura " + e.getMessage(), 1);
            return false;
        }

    }

    public class MessageConfirmation implements Serializable {

        private String message;
        private Integer accion;
        private TablaRespuestaFacturaDTO respuestaFacturaDTO = new TablaRespuestaFacturaDTO();

        public MessageConfirmation() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public TablaRespuestaFacturaDTO getRespuestaFacturaDTO() {
            return respuestaFacturaDTO;
        }

        public void setRespuestaFacturaDTO(TablaRespuestaFacturaDTO respuestaFacturaDTO) {
            this.respuestaFacturaDTO = respuestaFacturaDTO;
        }

        public Integer getAccion() {
            return accion;
        }

        public void setAccion(Integer accion) {
            this.accion = accion;
        }

        private boolean validarFechaRadicacion() {

            if (respuestaFacturaDTO.getFechaRadicacion() == null) {
                mostrarError("Debe digitar la fecha radicación", 1);
                return false;
            } else if (ManejoFecha.compararDates(respuestaFacturaDTO.getFechaRadicacion(), new Date()) == 1) {
                mostrarError("La fecha radicación debe ser menor que la fecha actual.", 1);
                return false;
            }
            return true;
        }

        public void loadLogs(ActionEvent ae) {
            this.respuestaFacturaDTO = (TablaRespuestaFacturaDTO) ae.getComponent().getAttributes().get("tabla");
            if (respuestaFacturaDTO.getFactura() != null) {
                respuestaFacturaDTO.loadLogFactura(pvsfb.consultarLogByFactura(respuestaFacturaDTO.getFactura().getVfctId()));
            } else {
                respuestaFacturaDTO.loadLogRemision(pvsfb.consultarLogByRemision(respuestaFacturaDTO.getRemision().getVrmsId()));
            }

        }

        public void generarReporte(ActionEvent ae) {
            this.respuestaFacturaDTO = (TablaRespuestaFacturaDTO) ae.getComponent().getAttributes().get("tabla");
            if (this.respuestaFacturaDTO.getFactura() != null) {
                this.respuestaFacturaDTO.setResource(getReporteByFactura(respuestaFacturaDTO.getFactura()));
            } else {
                this.respuestaFacturaDTO.setResource(getReporteByRemision(respuestaFacturaDTO.getRemision()));
            }
        }

        public void update(ActionEvent ae) {
            RfEstadofactura estado = null;
            switch (accion) {
                case 1:
                    estado = pvsfb.getRfEstadofacturaXID(EnEstadoFactura.ANULADO.getId());
                    break;
                case 2:
                    if (validarFechaRadicacion()) {
                        estado = pvsfb.getRfEstadofacturaXID(EnEstadoFactura.PAGADA.getId());
                    }
                    break;
                case 3:
                    if (validarFechaRadicacion()) {
                        estado = pvsfb.getRfEstadofacturaXID(EnEstadoFactura.PENDIENTE_DE_PAGO.getId());
                    }
                    break;
                case 4:
                    if (validarFechaRadicacion()) {
                        estado = pvsfb.getRfEstadofacturaXID(EnEstadoFactura.RADICADO.getId());
                    }
                    break;
            }
            if (estado != null) {
                if (respuestaFacturaDTO.getFactura() == null) {
                    respuestaFacturaDTO.getRemision().setEftId(estado);
                    respuestaFacturaDTO.getRemision().setCxcId(getPrincipalJSFBean().getAdmCrgxcolActivo());
                    respuestaFacturaDTO.getRemision().setVrmsFechaproceso(new Date());
                    respuestaFacturaDTO.getRemision().setVfctfecharadica(respuestaFacturaDTO.getFechaRadicacion());
                    if (respuestaFacturaDTO.getRemision().getVfctfecharadica() != null
                            && respuestaFacturaDTO.getRemision().getVrmsnumdiaspago() != null) {
                        respuestaFacturaDTO.getRemision().setVrmsfechapago(getDateFromDateMasDias(respuestaFacturaDTO.getRemision().getVfctfecharadica(),
                                respuestaFacturaDTO.getRemision().getVrmsnumdiaspago()));
                    }
                    respuestaFacturaDTO.setRemision(pvsfb.editarRemision(respuestaFacturaDTO.getRemision(), true));
                } else {
                    respuestaFacturaDTO.getFactura().setEftId(estado);
                    respuestaFacturaDTO.getFactura().setCxcId(getPrincipalJSFBean().getAdmCrgxcolActivo());
                    respuestaFacturaDTO.getFactura().setVfctFechaproceso(new Date());
                    respuestaFacturaDTO.getFactura().setVfctfecharadica(respuestaFacturaDTO.getFechaRadicacion());
                    if (respuestaFacturaDTO.getFactura().getVfctfecharadica() != null
                            && respuestaFacturaDTO.getFactura().getVfctnumdiaspago() != null) {
                        respuestaFacturaDTO.getFactura().setVfctfechapago(getDateFromDateMasDias(respuestaFacturaDTO.getFactura().getVfctfecharadica(),
                                respuestaFacturaDTO.getFactura().getVfctnumdiaspago()));
                    }
                    respuestaFacturaDTO.setFactura(pvsfb.editarFactura(respuestaFacturaDTO.getFactura(), true));
                }
                mostrarError(respuestaFacturaDTO.getMessage() + " # : " + respuestaFacturaDTO.getNumeroFactura() + " guardada exitosamente", 3);
                buscarFacturas();
            }

        }

        private Date getDateFromDateMasDias(Date date, Integer cantidadDias) {
            return new Date(date.getYear(), date.getMonth(), date.getDate() + cantidadDias);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Remisión venta">
    private void cargarListaRegVentaXClienteKids() {
        lstTablaVntRegistroventa.clear();
        for (VntRegistroventa registroventa : pvsfb.getLstVntRegistroventaXClientePendienteProcesar(EnTipoCliente.KIDS.getId(), EnEstadosVentaOp.FACTURAR_Y_OP.getId(), EnEstadosVentaOp.ACTIVO.getId())) {
            List<VntDetevento> lst = pvsfb.getLstVntDetevento(registroventa.getRgvtId());
            for (VntDetevento d : lst) {
                registroventa.setVdeId(d);
                break;
            }
            TablaVntRegistroventa tvr = new TablaVntRegistroventa();
            tvr.setVntRegistroventa(registroventa);
            lstTablaVntRegistroventa.add(tvr);
        }

        /* for (VntRegistroventa registroventa : pvsfb.getLstVntRegistroventaXCliente(2, 4, true)) {
         List<VntDetevento> lst = pvsfb.getLstVntDetevento(registroventa.getRgvtId());
         for (VntDetevento d : lst) {
         registroventa.setVdeId(d);
         break;
         }
         TablaVntRegistroventa tvr = new TablaVntRegistroventa();
         tvr.setVntRegistroventa(registroventa);
         lstTablaVntRegistroventa.add(tvr);
         }
         */
    }

    private void cargarListaRemisionProcesada() {
        lstTablaVntRemision.clear();
        for (VntRemision remision : pvsfb.getLstVntRemisionXRegVentaConf(true, 2)) {
            TablaVntRemision tvr = new TablaVntRemision();
            tvr.setVntRemision(remision);
            lstTablaVntRemision.add(tvr);
        }
    }

    private void cargarListaServicioXVentaXRemision() {
        lstTablaVntSrvXVenta.clear();
        for (VntServxventa servxventa : pvsfb.getLstVntServxventaXRemision(tablaVntRemisionSel.getVntRemision().getVrmsId())) {
            TablaVntSrvXVenta tvsxv = new TablaVntSrvXVenta();
            tvsxv.setVntServxventa(servxventa);
            lstTablaVntSrvXVenta.add(tvsxv);
        }
    }

    private boolean editarRemision() {

        boolean validador = true;
        if (remision.getVrmsclNombres() == null || remision.getVrmsclNombres().trim().equals("")) {
            mostrarError("Nombre del Cliente requerido", 1);
            validador = false;
        }
        if (remision.getVrmsclIdentificacion() == null || remision.getVrmsclIdentificacion().trim().equals("")) {
            mostrarError("Identificación del Cliente requerido", 1);
            validador = false;
        }
        if (remision.getVrmsclDiereccion() == null || remision.getVrmsclDiereccion().trim().equals("")) {
            mostrarError("Dirección del Cliente requerido", 1);
            validador = false;
        }
        if (remision.getVrmsclFijo() == null || remision.getVrmsclFijo().trim().equals("")) {
            mostrarError("Teléfono del Cliente requerido", 1);
            validador = false;
        }

        if (remision.getVrmsFecha() == null) {
            mostrarError("Fecha evento requerido", 1);
            validador = false;
        }
        if (remision.getVrmsDireccionevento() == null || remision.getVrmsDireccionevento().trim().equals("")) {
            mostrarError("Dirección del evento requerido", 1);
            validador = false;
        }
        if (remision.getVrmsSaldo().compareTo(BigDecimal.ZERO) == -1) {
            mostrarError("El Saldo de la remisión no debe ser menor que 0.", 1);
            validador = false;
        }

        for (VntDetallerem vdf : lstDetalleXRemision) {

            if (vdf.getVdrmServico() == null || vdf.getVdrmServico().trim().equals("")) {
                mostrarError("Servicio por cada detalle es requerido", 1);
                validador = false;
            }
        }
        if (validador) {
            try {

                remision.setVrmsFecharemision(new Date());
                remision.setVrmsFechaproceso(new Date());
                remision.setEftId(pvsfb.getRfEstadofacturaXID(EnEstadoFactura.EMITIDO.getId()));
                remision.setCxcId(pjsfb.getAdmCrgxcolActivo());
                remision.setVrmsNumremision("0");

                remision = pvsfb.editarRemision(remision, false);
                remision.setVrmsNumremision(String.valueOf(remision.getVrmsId()));
                tablaVntRegistroventaSel.getVntRegistroventa().setRgvtFacturada(true);
                tablaVntRegistroventaSel.getVntRegistroventa().setRgvtVlrfactura(remision.getVrmsValortotal());
                pvsfb.editarRegVenta(tablaVntRegistroventaSel.getVntRegistroventa());
                remision.setRgvtId(tablaVntRegistroventaSel.getVntRegistroventa());
                remision = pvsfb.editarRemision(remision, true);

                int cantidadProcesa = 0;
                for (VntDetallerem vdf : lstDetalleXRemision) {

                    vdf.setVdrmEstado(Boolean.TRUE);
                    vdf.setVrmsId(remision);
                    cantidadProcesa += vdf.getVdrmCantidad();
                    vdf = pvsfb.grabarDetalleRem(vdf);
                }

                //
                List<VntServxventa> lstSelAnulS = new ArrayList<>();
                for (TablaVntSrvXVenta g : lstTablaVntSrvXVenta) {

                    g.getVntServxventa().setSrvxventaProcesada(g.getVntServxventa().getSrvxventaProcesada() + g.getCantidadSeleccionada());
                    lstSelAnulS.add(g.getVntServxventa());

                }
                tablaVntRegistroventaSel.getVntRegistroventa().setRgvtCantserviciosProcesados(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtCantserviciosProcesados() + cantidadProcesa);
                pvsfb.editarRegVenta(tablaVntRegistroventaSel.getVntRegistroventa());
                tablaVntRegistroventaSel.setVntRegistroventa(pvsfb.getVntRegistroventaSel());
                if (!lstSelAnulS.isEmpty()) {
                    pvsfb.editarRegVentaConServ(lstSelAnulS);
                }
                //

                return true;
            } catch (Exception e) {
                mostrarError("Error al grabar la remision" + e.getMessage(), 1);
                return false;
            }
        } else {
            return false;
        }

    }

    //</editor-fold>
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnMostrarPanel = false;
        limpiarCampos();
        tipoConsulta = 2;
        blnMostrarPanelSeleccion = true;
        switch (numPanel) {
            case PANEL_CONFIRMAR_PAGO:
                cargarListaRegVentaXClienteKids();
                break;
            case PANEL_FACTURA_VENTA:
                cargarListaRegVentaXClienteCorp();
                break;
            case PANEL_REMISION_VENTA:
                cargarListaRegVentaXClienteKids();
                break;
            case PANEL_CONSULTA_VENTA:

                consultaFacturaDTO = new ConsultaFacturaDTO();
                listaConsultaFactura = new ArrayList<>();
                break;

        }
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
    }

    public void cambioFormaPago(ValueChangeEvent vce) {
        idFormaPago = (Integer) vce.getNewValue();
        idBanco = -1L;
        if (idFormaPago == null || idFormaPago == -1) {
            blnAplicaBanco = false;
        } else {
            FinFormapago f = pvsfb.getFinFormapagoXID(idFormaPago);
            if (f == null) {
                blnAplicaBanco = false;
            } else {
                blnAplicaBanco = f.isFpgAplicabanco();
            }
        }

    }

    public void cambioDescuento(ValueChangeEvent vce) {
        factura.setVfctDescuento((BigDecimal) vce.getNewValue());
        if (factura.getVfctDescuento() == null) {
            factura.setVfctDescuento(new BigDecimal(0));
        }
        //BigDecimal vlr = valorVentaInicial;
        // BigDecimal vlr = factura.getVfctSubtotal();
        if (valorVentaInicial.compareTo(factura.getVfctDescuento()) == -1) {
            mostrarError("Descuento no debe ser mayor que el total de la factura", 1);
            factura.setVfctDescuento(new BigDecimal(0));
        }

        //factura.setVfctValortotal(factura.getVfctSubtotal().add(factura.getVfctIva()).subtract(factura.getVfctDescuento()));
        //factura.setVfctSaldo(factura.getVfctValortotal().subtract(factura.getVfctAnticipo()));
        // IVA
        BigDecimal vlrPago = valorVentaInicial.subtract(factura.getVfctDescuento());
        factura.setVfctSubtotal(vlrPago);
        BigDecimal operacion = vlrPago.multiply(factura.getVfctPorcentajeiva()).divide(new BigDecimal(100));
        factura.setVfctIva(operacion);
        factura.setVfctValortotal(operacion.add(factura.getVfctSubtotal()));
        factura.setVfctSaldo(factura.getVfctValortotal().subtract(factura.getVfctAnticipo()));
    }

    public void cambioPorcentajeIva(ValueChangeEvent vce) {
        factura.setVfctPorcentajeiva((BigDecimal) vce.getNewValue());

        if (factura.getVfctPorcentajeiva() == null) {
            factura.setVfctPorcentajeiva(porcentajeIVA);
        }

        factura.setVfctIva(factura.getVfctSubtotal().multiply(factura.getVfctPorcentajeiva()).divide(new BigDecimal(100)));
        factura.setVfctValortotal(factura.getVfctSubtotal().add(factura.getVfctIva()).subtract(factura.getVfctDescuento()));
        factura.setVfctSaldo(factura.getVfctValortotal().subtract(factura.getVfctAnticipo()));
    }

    //<editor-fold defaultstate="collapsed" desc="Pagos venta"> 
    public void rbtCambiarPanel_ValueChangeListener(ValueChangeEvent vce) {
        // String strValSel = String.valueOf(vce.getNewValue());
        //  tipoConsulta = Integer.parseInt(strValSel);
        tipoConsulta = (Integer) vce.getNewValue();
        switch (tipoConsulta) {
            case 1:
                cargarListaRegVentaSinConfirmar();
                break;
            case 2:
                cargarListaRegVentaConfirmado();
                break;
            case 3:
                cargarListaRegistroVenta();
                break;
        }
    }

    public void cargarRegistroVenta() {
        // Map map = ae.getComponent().getAttributes();
        //tablaVntRegistroventaSel = (TablaVntRegistroventa) map.get("trvs");
        //blnMostrarPanel = true;
        cargarListaFormaPago();
        cargarListaServXVenta();
        cargarBancos();
        pago = new VntPagoventa();
        pago.setRgpcEst(true);
        pago.setPgpcFechaproc(new Date());
        pago.setRgpcFechapago(new Date());
        pago.setRgvtId(tablaVntRegistroventaSel.getVntRegistroventa());
        bdValorPago = null;
        blnAplicaBanco = false;
        idFormaPago = -1;
        idBanco = -1L;
        cargarPagosXVentas(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId());
        jaspResource = null;

        if (tablaVntRegistroventaSel.getVntRegistroventa().isRgvtFacturada()) {
            bdTotalPagado = tablaVntRegistroventaSel.getVntRegistroventa().getRgvtVlrfactura();
            bdValorPago = bdTotalPagado.subtract(
                    tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorpagado() == null ? new BigDecimal(0) : tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorpagado());

            if (numPanel == 4) {
                numFact = "";
                if (tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getTclId().getTclId().equals(1)) {
                    remisionOFactura = true;
                    for (VntFactura v : pvsfb.getLstVntFacturaXventa(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId(), 3)) {
                        numFact = v.getVfctNumfactura();
                        idFactrura = v.getVfctId();
                        break;
                    }
                } else {
                    for (VntRemision v : pvsfb.getLstVntRemisionXventa(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId(), 3)) {
                        remisionOFactura = false;
                        idFactrura = v.getVrmsId();
                        numFact = v.getVrmsNumremision();
                        break;
                    }
                }
            }

        } else {
            bdValorPago = tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa().subtract(
                    tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorpagado() == null ? new BigDecimal(0) : tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorpagado());
            bdTotalPagado = tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa();

        }
        pago.setRgpcValorpago(bdValorPago);
    }

    public void rowDtRegVentaConfirmado_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntRegistroventaSel = (TablaVntRegistroventa) map.get("trvcs");
        blnMostrarPanel = true;
    }

    public void btnGrabarConfPago_ActionEvent(ActionEvent ae) {
        confirmarPago();
    }

    public void btnRegresarConfPago_ActionEvent(ActionEvent ae) {
        blnMostrarPanel = false;
        cargarListaRegVentaXClienteKids();
        // cargarListaRegVentaSinConfirmar();
    }

    public void generarInforme_ActionEvent(ActionEvent ae) {
        if (remisionOFactura) {
            HashMap hmParametros = new HashMap();
            hmParametros.put("p_FacturaVentaCorp", idFactrura);
            AdmInforme informe = astslb.getAdmInformeXId(1);
            String rutaLogo = informe.getInfJasperruta() + "/logos/maximus_corporativo.jpg";
            jaspResource = genInfRecurso(hmParametros, informe, 2, rutaLogo);

        } else {
            HashMap hmParametros = new HashMap();
            hmParametros.put("p_FacturaVentaKids", idFactrura);
            AdmInforme informe = astslb.getAdmInformeXId(2);
            String rutaLogo = informe.getInfJasperruta() + "/logos/maximus_kids.jpg";
            jaspResource = genInfRecurso(hmParametros, informe, 2, rutaLogo);
        }
    }

    public void btnRegresarConsVenta_ActionEvent(ActionEvent ae) {
        blnMostrarPanel = false;
        switch (tipoConsulta) {
            case 1:
                cargarListaRegVentaSinConfirmar();
                break;
            case 2:
                cargarListaRegVentaConfirmado();
                break;
            case 3:
                cargarListaRegistroVenta();
                break;
        }
    }

    public void btnRegresarPagoConf_ActionEvent(ActionEvent ae) {
        blnMostrarPanel = false;
        cargarListaRegVentaConfirmado();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Seleccion servicios faactura remision">
    public void irFactura_ActionEvent(ActionEvent ae) {
        List<TablaVntSrvXVenta> lstSelFact = new ArrayList<>();
        List<TablaVntSrvXVenta> lstSelAnul = new ArrayList<>();
        List<VntServxventa> lstSelAnulS = new ArrayList<>();
        List<VntCronograma> lstCronogramaAnul = new ArrayList<>();
        HashMap<Long, TablaVntSrvXVenta> mapaAnular = new HashMap<>();
        HashMap<Long, TablaVntSrvXVenta> mapaFactura = new HashMap<>();
        lstCronogramas.clear();
        boolean validar = true;
        if (this.tablaVntRegistroventaSel.getVntRegistroventa().isRgvtActCronograma()) {
            for (TablaVntSrvXVenta tsxvs : lstTablaServiciosPendientes) {
                if (tsxvs.getProcesar() == null) {
                    tsxvs.setProcesar(EnOpcionFactura.NO_PROCESAR.getCodigo());
                }
                tsxvs.setCantidadSeleccionada(1);
                TablaVntSrvXVenta servicio = null;
                switch (tsxvs.getProcesar()) {
                    case "N":

                        break;
                    case "P":
                        lstCronogramas.add(tsxvs.getCronogramaSel());
                        servicio = mapaFactura.get(tsxvs.getVntServxventa().getSrvxventId());
                        if (servicio == null) {
                            servicio = tsxvs;
                        } else {
                            servicio.setCantidadSeleccionada(servicio.getCantidadSeleccionada() + 1);
                        }
                        mapaFactura.put(servicio.getVntServxventa().getSrvxventId(), servicio);
                        break;
                    case "A":
                        tsxvs.getCronogramaSel().setCronogramaEstadoFactura(false);
                        lstCronogramaAnul.add(tsxvs.getCronogramaSel());
                        servicio = mapaAnular.get(tsxvs.getVntServxventa().getSrvxventId());
                        if (servicio == null) {
                            servicio = tsxvs;
                        } else {
                            servicio.setCantidadSeleccionada(servicio.getCantidadSeleccionada() + 1);
                        }
                        mapaAnular.put(servicio.getVntServxventa().getSrvxventId(), servicio);
                        // lstSelAnul.add(tsxvs);
                        break;
                }
            }
            for (Map.Entry<Long, TablaVntSrvXVenta> v : mapaAnular.entrySet()) {
                Integer pend = v.getValue().getVntServxventa().getSrvxventCantidad() - v.getValue().getVntServxventa().getSrvxventaProcesada();
                Integer pendiente = pend - v.getValue().getCantidadSeleccionada();
                v.getValue().setCantidadPendiente(pendiente);

                BigDecimal valor = BigDecimal.ZERO;
                v.getValue().setValorDescuento(BigDecimal.ZERO);
                v.getValue().setValorFinal(BigDecimal.ZERO);

                valor = v.getValue().getVntServxventa().getSrvxventValtotalclnt().divide(new BigDecimal(v.getValue().getVntServxventa().getSrvxventCantidad())).multiply(new BigDecimal(v.getValue().getCantidadSeleccionada()));
                if (v.getValue().getVntServxventa().getSrvxventPorcentajeDesc() != null && (v.getValue().getVntServxventa().getSrvxventPorcentajeDesc().compareTo(BigDecimal.ZERO) != 0)) {
                    v.getValue().setValorDescuento(valor.multiply(v.getValue().getVntServxventa().getSrvxventPorcentajeDesc()).divide(new BigDecimal(100)));
                    v.getValue().setValorFinal(valor.subtract(v.getValue().getValorDescuento()));
                } else {
                    v.getValue().setValorFinal(valor);
                }

                v.getValue().setValorProcesado(valor);

                lstSelAnul.add(v.getValue());
            }

            for (Map.Entry<Long, TablaVntSrvXVenta> v : mapaFactura.entrySet()) {
                Integer pend = v.getValue().getVntServxventa().getSrvxventCantidad() - v.getValue().getVntServxventa().getSrvxventaProcesada();
                Integer pendiente = pend - v.getValue().getCantidadSeleccionada();
                v.getValue().setCantidadPendiente(pendiente);

                BigDecimal valor = BigDecimal.ZERO;
                v.getValue().setValorDescuento(BigDecimal.ZERO);
                v.getValue().setValorFinal(BigDecimal.ZERO);

                valor = v.getValue().getVntServxventa().getSrvxventValtotalclnt().divide(new BigDecimal(v.getValue().getVntServxventa().getSrvxventCantidad())).multiply(new BigDecimal(v.getValue().getCantidadSeleccionada()));
                if (v.getValue().getVntServxventa().getSrvxventPorcentajeDesc() != null && (v.getValue().getVntServxventa().getSrvxventPorcentajeDesc().compareTo(BigDecimal.ZERO) != 0)) {
                    v.getValue().setValorDescuento(valor.multiply(v.getValue().getVntServxventa().getSrvxventPorcentajeDesc()).divide(new BigDecimal(100)));
                    v.getValue().setValorFinal(valor.subtract(v.getValue().getValorDescuento()));
                } else {
                    v.getValue().setValorFinal(valor);
                }

                v.getValue().setValorProcesado(valor);

                lstSelFact.add(v.getValue());
            }
        } else {
            for (TablaVntSrvXVenta tsxvs : lstTablaServiciosPendientes) {
                Integer pend = tsxvs.getVntServxventa().getSrvxventCantidad() - tsxvs.getVntServxventa().getSrvxventaProcesada();
                if (tsxvs.getProcesar() == null) {
                    tsxvs.setProcesar(EnOpcionFactura.NO_PROCESAR.getCodigo());
                }

                if (tsxvs.getCantidadSeleccionada() != null) {

                    Integer pendiente = pend - tsxvs.getCantidadSeleccionada();
                    tsxvs.setCantidadPendiente(pendiente);

                    BigDecimal valor = BigDecimal.ZERO;
                    tsxvs.setValorDescuento(BigDecimal.ZERO);
                    tsxvs.setValorFinal(BigDecimal.ZERO);

                    valor = tsxvs.getVntServxventa().getSrvxventValtotalclnt().divide(new BigDecimal(tsxvs.getVntServxventa().getSrvxventCantidad())).multiply(new BigDecimal(tsxvs.getCantidadSeleccionada()));
                    if (tsxvs.getVntServxventa().getSrvxventPorcentajeDesc() != null && (tsxvs.getVntServxventa().getSrvxventPorcentajeDesc().compareTo(BigDecimal.ZERO) != 0)) {
                        tsxvs.setValorDescuento(valor.multiply(tsxvs.getVntServxventa().getSrvxventPorcentajeDesc()).divide(new BigDecimal(100)));
                        tsxvs.setValorFinal(valor.subtract(tsxvs.getValorDescuento()));
                    } else {
                        tsxvs.setValorFinal(valor);
                    }

                    tsxvs.setValorProcesado(valor);
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
        }
        if (!validar) {
            mostrarError("Error al procesar los servicios.", 1);
            return;
        }

        if (lstSelFact.isEmpty()
                && lstSelAnul.isEmpty()) {
            mostrarError("Debe seleccionar para procesar los servicios", 1);
            return;
        }

        for (TablaVntSrvXVenta g : lstSelAnul) {
            tablaVntRegistroventaSel.getVntRegistroventa().setRgvtCantserviciosProcesados(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtCantserviciosProcesados() + g.getCantidadSeleccionada());
            g.getVntServxventa().setSrvxventaProcesada(g.getVntServxventa().getSrvxventaProcesada() + g.getCantidadSeleccionada());
            lstSelAnulS.add(g.getVntServxventa());
        }

        if (!lstSelAnulS.isEmpty()) {
            cronogramaSLBean.editarListCronograma(lstCronogramaAnul);
            pvsfb.editarRegVentaConServ(lstSelAnulS);
            pvsfb.editarRegVenta(tablaVntRegistroventaSel.getVntRegistroventa());
            tablaVntRegistroventaSel.setVntRegistroventa(pvsfb.getVntRegistroventaSel());
            mostrarError("Se han procesado los servicios anulados", 3);
        }

        if (numPanel == 2) {
            lstTablaVntSrvXVenta = lstSelFact;
            cargarDatosFactura(lstTablaVntSrvXVenta);
            if (lstSelFact.isEmpty()) {
                blnMostrarPanel = true;
                cargarListaRegVentaXClienteCorp();
            } else {
                blnMostrarPanel = false;
            }
        } else {
            lstTablaVntSrvXVenta = lstSelFact;
            cargarDatosRemision(lstTablaVntSrvXVenta);
            if (lstSelFact.isEmpty()) {
                blnMostrarPanel = true;
                cargarListaRegVentaXClienteKids();
            } else {
                blnMostrarPanel = false;

            }
        }

    }

    private void cargarDatosFactura(List<TablaVntSrvXVenta> lstSelFact) {

        datFechaFactura = new Date();
        factura = new VntFactura();
        factura.setVfctFechafactura(datFechaFactura);
        factura.setRgvtId(tablaVntRegistroventaSel.getVntRegistroventa());
        factura.setVfctFechaproceso(new Date());
        factura.setVfctFechafactura(new Date());
        factura.setEftId(new RfEstadofactura(2));

        factura.setVfctValortotal(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa());
        factura.setFactclNombres(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnNombres());

        factura.setFactclDiereccion(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnDiereccion());
        factura.setFactclTipodocuemento(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getTdcId().getTdcId());
        factura.setFactclIdentificacion(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnIdentificacion());
        factura.setFactclEmail(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnCorreoe());
        factura.setFactclFijo(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnFijo());
        factura.setFactclCelular(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnCelular());
        factura.setFactsrDetalle(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getMteId().getMteNombre());
        factura.setFactsrMotivo(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getMteId().getMteNombre());
        factura.setFactsrProtagonista(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getDclnId().getDclnNombres());
        factura.setFactsrFecha(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeFechaevt());
        factura.setFactsrHora(this.getHoraFromDate(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeHorainicio()));
        factura.setFactsrDireccionevento(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeDireccionevt());
        //consultar anticipos

        factura.setVfctAnticipo(new BigDecimal(0));

        factura.setVfctDescuento(BigDecimal.ZERO);
        valorVentaInicial = BigDecimal.ZERO;
        this.valorVentaSubTotal = BigDecimal.ZERO;
        factura.setVfctIva(BigDecimal.ZERO);
        lstDetalleXFact.clear();
        factura.setFactdcDescripcion("");

        for (TablaVntSrvXVenta r : lstSelFact) {
            VntServxventa s = r.getVntServxventa();
            VntDetallefact d = new VntDetallefact();
            d.setVsrvId(s.getVsrvId());
            d.setVdftServico(s.getVsrvId().getVsrvNombre());
            d.setVdftEstado(true);
            d.setVdftCantidad(r.getCantidadSeleccionada());
            d.setVdftCostototal(r.getValorProcesado());
            d.setVdftCostounitario(s.getSrvxventPrecioventa());
            d.setVdftDescuento(r.getValorDescuento());
            factura.setVfctDescuento(factura.getVfctDescuento().add(r.getValorDescuento()));
            valorVentaInicial = valorVentaInicial.add(r.getValorProcesado());
            d.setVdftSubtotal(r.getValorFinal());
            d.setVdftPorcentajeIva(s.getSrvxventPrecioventa() == null ? porcentajeIVA : s.getVsrvId().getVsrvPorcentajeiva());
            d.setVdftValorIva(d.getVdftPorcentajeIva().multiply(d.getVdftSubtotal()).divide(new BigDecimal(100)));

            factura.setVfctIva(factura.getVfctIva().add(d.getVdftValorIva()));
            d.setVdftSubtotal(d.getVdftSubtotal().add(d.getVdftValorIva()));
            lstDetalleXFact.add(d);
            factura.setFactdcDescripcion(factura.getFactdcDescripcion() + s.getVsrvId().getVsrvNombre() + " ");

        }
        String datosCrono = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        for (VntCronograma v : lstCronogramas) {
            datosCrono += "-";
            datosCrono += (v.getCronogramaDistribuidor() == null ? "" : v.getCronogramaDistribuidor());
            datosCrono += " ";
            datosCrono += (v.getCronogramaFecha() == null ? "" : sdf.format(v.getCronogramaFecha()));
            datosCrono += " ";
            datosCrono += (v.getCronogramacop() == null ? "" : v.getCronogramacop());

        }
        datosCrono = datosCrono.replaceFirst("-", "");
        if (this.tablaVntRegistroventaSel.getVntRegistroventa().isRgvtActCronograma()) {
            factura.setFactdcDescripcion(datosCrono);
        }

        factura.setVfctSubtotal(valorVentaInicial);
        valorVentaSubTotal = valorVentaInicial.subtract(factura.getVfctDescuento());
        factura.setVfctPorcentajeiva(BigDecimal.ZERO);
        // BigDecimal operacion = valorVentaInicial.multiply(factura.getVfctPorcentajeiva()).divide(new BigDecimal(100));
        //factura.setVfctIva(operacion);
        factura.setVfctValortotal(valorVentaSubTotal.add(factura.getVfctIva()));
        factura.setVfctSaldo(factura.getVfctValortotal().subtract(factura.getVfctAnticipo()));

    }

    private void cargarDatosRemision(List<TablaVntSrvXVenta> lstSelFact) {
        datFechaRemision = new Date();

        remision = new VntRemision();
        lstDetalleXRemision.clear();

        blnMostrarPanel = true;

        remision.setVrmsFecharemision(datFechaFactura);
        remision.setRgvtId(tablaVntRegistroventaSel.getVntRegistroventa());
        remision.setVrmsFechaproceso(new Date());
        remision.setEftId(new RfEstadofactura(2));

        remision.setVrmsValortotal(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa());
        remision.setVrmsclNombres(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnNombres());

        remision.setVrmsclDiereccion(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnDiereccion());
        remision.setVrmsclTipodocuemento(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getTdcId().getTdcId());
        remision.setVrmsclIdentificacion(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnIdentificacion());
        remision.setVrmsclEmail(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnCorreoe());
        remision.setVrmsclFijo(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnFijo());
        remision.setVrmsclCelular(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnCelular());
        remision.setVrmsDetalle(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getMteId().getMteNombre());
        remision.setVrmsMotivo(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getMteId().getMteNombre());
        remision.setVrmsProtagonista(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getDclnId().getDclnNombres());

        remision.setVrmsFecha(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeFechaevt());
        remision.setVrmsHora(this.getHoraFromDate(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeHorainicio()));
        remision.setVrmsDireccionevento(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeDireccionevt());
        //consultar anticipos

        remision.setVrmsAnticipo(new BigDecimal(0));

        remision.setVrmsDescuento(BigDecimal.ZERO);
        valorVentaInicial = BigDecimal.ZERO;

        blnMostrarPanel = true;
        lstDetalleXRemision.clear();
        remision.setVrmsObservacion("");
        for (TablaVntSrvXVenta r : lstSelFact) {
            VntDetallerem d = new VntDetallerem();
            VntServxventa s = r.getVntServxventa();
            d.setVsrvId(s.getVsrvId());
            d.setVdrmServico(s.getVsrvId().getVsrvNombre());
            d.setVdrmEstado(true);
            d.setVdrmCantidad(r.getCantidadSeleccionada());
            d.setVdrmCostototal(r.getValorProcesado());
            d.setVdrmCostounitario(s.getSrvxventPrecioventa());
            d.setVdrmDescuento(r.getValorDescuento());
            remision.setVrmsDescuento(remision.getVrmsDescuento().add(r.getValorDescuento()));
            d.setVdrmSubservico("");
            d.setVdrmEspecificaciones("");
            valorVentaInicial = valorVentaInicial.add(r.getValorFinal());
            remision.setVrmsObservacion(remision.getVrmsObservacion() + s.getVsrvId().getVsrvNombre() + " ");
            lstDetalleXRemision.add(d);

        }
        for (VntPagoventa p : pvsfb.getLstVntPagoventaXVenta(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            remision.setVrmsAnticipo(remision.getVrmsAnticipo().add(p.getRgpcValorpago()));
        }
        remision.setVrmsValortotal(valorVentaInicial);
        remision.setVrmsSaldo(remision.getVrmsValortotal().subtract(remision.getVrmsAnticipo()));

    }

    public void rowDtRegistroVentaFactura_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntRegistroventaSel = (TablaVntRegistroventa) map.get("trvs");
        blnMostrarPanel = true;
        blnMostrarPanelSeleccion = false;
        if (numPanel == 1) {
            idFormaPago = -1;
            cargarRegistroVenta();
        } else {
            blnMostrarPanel = true;
            cargarServiciosXventa();
        }
    }

    private void cargarServiciosXventa() {

        lstTablaServiciosPendientes.clear();

        if (tablaVntRegistroventaSel.getVntRegistroventa().isRgvtActCronograma()) {
            int contador = 0;

            for (VntCronograma c : this.cronogramaSLBean.getLstVntCronogramaPendienteFactura(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId(), true)) {

                TablaVntSrvXVenta s = new TablaVntSrvXVenta(c.getVntServxventa());
                s.setCronogramaSel(c);
                s.setCantidadPendiente(1);
                s.setCantidadSeleccionada(1);
                BigDecimal valor = BigDecimal.ZERO;
                s.setValorDescuento(BigDecimal.ZERO);
                s.setValorFinal(BigDecimal.ZERO);
                s.setCronogramaSel(c);
                contador += 1;
                s.setInidceFila(contador);

                s.setBlnEditar(true);
                valor = c.getVntServxventa().getSrvxventValtotalclnt().divide(new BigDecimal(c.getVntServxventa().getSrvxventCantidad())).multiply(BigDecimal.ONE);
                if (c.getVntServxventa().getSrvxventPorcentajeDesc() != null && (c.getVntServxventa().getSrvxventPorcentajeDesc().compareTo(BigDecimal.ZERO) != 0)) {
                    s.setValorDescuento(valor.multiply(c.getVntServxventa().getSrvxventPorcentajeDesc()).divide(new BigDecimal(100)));
                    s.setValorFinal(valor.subtract(s.getValorDescuento()));
                } else {
                    s.setValorFinal(valor);
                }
                lstTablaServiciosPendientes.add(s);
            }

        } else {
            for (VntServxventa x : pvsfb.getLstServxventaXVnt(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
                TablaVntSrvXVenta s = new TablaVntSrvXVenta(x);
                Integer pendiente = x.getSrvxventCantidad() - x.getSrvxventaProcesada();
                s.setCantidadPendiente(pendiente);
                s.setCantidadSeleccionada(pendiente);
                BigDecimal valor = BigDecimal.ZERO;
                s.setValorDescuento(BigDecimal.ZERO);
                s.setValorFinal(BigDecimal.ZERO);

                if (pendiente > 0) {
                    s.setBlnEditar(true);
                    valor = x.getSrvxventValtotalclnt().divide(new BigDecimal(x.getSrvxventCantidad())).multiply(new BigDecimal(pendiente));
                    if (x.getSrvxventPorcentajeDesc() != null && (x.getSrvxventPorcentajeDesc().compareTo(BigDecimal.ZERO) != 0)) {
                        s.setValorDescuento(valor.multiply(x.getSrvxventPorcentajeDesc()).divide(new BigDecimal(100)));
                        s.setValorFinal(valor.subtract(s.getValorDescuento()));
                    } else {
                        s.setValorFinal(valor);
                    }
                }
                if (blnMostrarPanelRemision) {
                    s.setProcesar("P");
                }
                s.setValorProcesado(valor);
                lstTablaServiciosPendientes.add(s);
            }
        }

    }

    public void cambioCantidadXServicio(ValueChangeEvent vce) {
        TablaVntSrvXVenta tsxvs = (TablaVntSrvXVenta) vce.getComponent().getAttributes().get("tsxvs");
        tsxvs.setCantidadSeleccionada(null);
        Integer pend = tsxvs.getVntServxventa().getSrvxventCantidad() - tsxvs.getVntServxventa().getSrvxventaProcesada();
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

            BigDecimal valor = BigDecimal.ZERO;
            tsxvs.setValorDescuento(BigDecimal.ZERO);
            tsxvs.setValorFinal(BigDecimal.ZERO);
            if (pendiente > 0) {
                valor = tsxvs.getVntServxventa().getSrvxventValtotalclnt().divide(new BigDecimal(tsxvs.getVntServxventa().getSrvxventCantidad())).multiply(new BigDecimal(tsxvs.getCantidadSeleccionada()));
                if (tsxvs.getVntServxventa().getSrvxventPorcentajeDesc() != null && (tsxvs.getVntServxventa().getSrvxventPorcentajeDesc().compareTo(BigDecimal.ZERO) != 0)) {
                    tsxvs.setValorDescuento(valor.multiply(tsxvs.getVntServxventa().getSrvxventPorcentajeDesc()).divide(new BigDecimal(100)));
                    tsxvs.setValorFinal(valor.subtract(tsxvs.getValorDescuento()));
                } else {
                    tsxvs.setValorFinal(valor);
                }
            }
            tsxvs.setValorProcesado(valor);
        }

    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Factura venta">

    public void rowDtFacturaRegVenta_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntRegistroventaSel = (TablaVntRegistroventa) map.get("tvfs");

        datFechaFactura = new Date();
        factura = new VntFactura();
        factura.setVfctFechafactura(datFechaFactura);
        factura.setRgvtId(tablaVntRegistroventaSel.getVntRegistroventa());
        factura.setVfctFechaproceso(new Date());
        factura.setVfctFechafactura(new Date());
        factura.setEftId(new RfEstadofactura(2));

        factura.setVfctValortotal(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa());
        factura.setFactclNombres(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnNombres());

        factura.setFactclDiereccion(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnDiereccion());
        factura.setFactclTipodocuemento(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getTdcId().getTdcId());
        factura.setFactclIdentificacion(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnIdentificacion());
        factura.setFactclEmail(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnCorreoe());
        factura.setFactclFijo(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnFijo());
        factura.setFactclCelular(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnCelular());
        factura.setFactsrDetalle(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getMteId().getMteNombre());
        factura.setFactsrMotivo(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getMteId().getMteNombre());
        factura.setFactsrProtagonista(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getDclnId().getDclnNombres());
        factura.setFactsrFecha(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeFechaevt());
        factura.setFactsrHora(this.getHoraFromDate(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeHorainicio()));
        factura.setFactsrDireccionevento(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeDireccionevt());
        //consultar anticipos

        factura.setVfctAnticipo(new BigDecimal(0));
        for (VntPagoventa p : pvsfb.getLstVntPagoventaXVenta(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            factura.setVfctAnticipo(factura.getVfctAnticipo().
                    add((p.getRgpcValorpago() == null ? BigDecimal.ZERO : p.getRgpcValorpago())));
        }
        factura.setVfctDescuento(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtDescuento() == null ? new BigDecimal(0) : tablaVntRegistroventaSel.getVntRegistroventa().getRgvtDescuento());
        valorVentaInicial = tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa() == null ? new BigDecimal(0) : tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa();
        factura.setVfctSubtotal(valorVentaInicial);
        // factura.setVfctSubtotal(valorVentaInicial.subtract(factura.getVfctDescuento()));
        factura.setVfctPorcentajeiva(porcentajeIVA);
        BigDecimal operacion = valorVentaInicial.multiply(factura.getVfctPorcentajeiva()).divide(new BigDecimal(100));
        factura.setVfctIva(operacion);
        factura.setVfctValortotal(operacion.add(factura.getVfctSubtotal()));
        factura.setVfctSaldo(factura.getVfctValortotal().subtract(factura.getVfctAnticipo()));
        blnMostrarPanel = true;
        lstDetalleXFact.clear();
        factura.setFactdcDescripcion("");
        for (VntServxventa s : pvsfb.getLstServxventaXVnt(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            VntDetallefact d = new VntDetallefact();
            d.setVsrvId(s.getVsrvId());
            d.setVdftServico(s.getVsrvId().getVsrvNombre());
            d.setVdftEstado(true);
            d.setVdftCantidad(s.getSrvxventCantidad());
            d.setVdftCostototal(s.getSrvxventValtotalclnt());
            d.setVdftCostounitario(s.getSrvxventPrecioventa());
            d.setVdftDescuento(new BigDecimal(0));
            d.setVdftSubtotal(s.getSrvxventValtotalclnt());
            lstDetalleXFact.add(d);
            factura.setFactdcDescripcion(factura.getFactdcDescripcion() + s.getVsrvId().getVsrvNombre() + " ");

        }

        //tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeId();
        //  mostrarError("hey aqui estoy", 3);
//        cargarListaServicioXVentaXFactura();
    }

    private String getRutaLogoByEstado(int estado) {

        String out = "/logos/blancosello.png";
        switch (estado) {
            case 2:
                out = "/logos/anuladasello.png";
                break;
            case 5:
                out = "/logos/pagadasello.png";
                break;
        }
        return out;
    }

    public void btnEditarFactura_ActionEvent(ActionEvent ae) {
        if (validarFactura()) {
            if (editarFactura()) {
                numPanel = PANEL_FACTURA_VENTA;
                jaspResource = getReporteByFactura(factura);
            }
        }
    }

    private com.icesoft.faces.context.Resource getReporteByFactura(VntFactura vf) {
        HashMap hmParametros = new HashMap();
        hmParametros.put("p_FacturaVentaCorp", vf.getVfctId());
        Integer tipoReporte = EnInforme.FACTURA_VENTA.getId();
        String rutaLogoReporte = "/logos/maximus_corporativo.jpg";

        if (getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente() != null
                && getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente().getTclId().equals(EnTipoCliente.SAS.getId())) {
            tipoReporte = EnInforme.FACTURA_SAS.getId();
            rutaLogoReporte = "/logos/maximus_sas.jpg";
        }
        AdmInforme informe = astslb.getAdmInformeXId(tipoReporte);

        String rutaLogo = informe.getInfJasperruta() + rutaLogoReporte;

        String rutaFirma = informe.getInfJasperruta() + "/logos/espaciosello.png";
        hmParametros.put("rutaFirma", rutaFirma);
        fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        hmParametros.put("rutafondo", ec.getRealPath(getRuta_recursos() + informe.getInfJasperruta() + getRutaLogoByEstado(vf.getEftId().getEftId())));
        return genInfRecurso(hmParametros, informe, 2, rutaLogo);
    }

    public void btnRegresarFactura_ActionEvent(ActionEvent ae) {
        int num = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));

        limpiarCampos();

        switch (num) {
            case 1:
                blnMostrarPanel = true;
                blnMostrarPanelSeleccion = false;
                cargarServiciosXventa();
                break;
            case 2:
                blnMostrarPanelSeleccion = true;
                blnMostrarPanel = false;
                cargarListaRegVentaXClienteCorp();
                break;

        }

    }

    public void btnGenInfFactura_ActionEvent(ActionEvent ae) {

        VntFactura vf = tablaVntFacturaSel.getVntFactura();
        vf.setVfctFechaproceso(new Date());
        vf.setVfctFechafactura(new Date());
        vf.setEftId(new RfEstadofactura(2));
        vf = pvsfb.editarFactura(vf, true);
        HashMap hmParametros = new HashMap();
//        hmParametros.put("p_str_id", sysTarea.getStrId());
        hmParametros.put("p_FacturaVentaCorp", tablaVntFacturaSel.getVntFactura().getVfctId());
        AdmInforme informe = astslb.getAdmInformeXId(EnInforme.FACTURA_VENTA.getId());
        String rutaLogo = informe.getInfJasperruta() + "/logos/";
        jaspResource = genInfRecurso(hmParametros, informe, 2, rutaLogo);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Remision venta">    
    public void rowDtRemisionRegVenta_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntRegistroventaSel = (TablaVntRegistroventa) map.get("tvrs");

        datFechaRemision = new Date();

        remision = new VntRemision();
        lstDetalleXRemision.clear();

        blnMostrarPanel = true;

        remision.setVrmsFecharemision(datFechaFactura);
        remision.setRgvtId(tablaVntRegistroventaSel.getVntRegistroventa());
        remision.setVrmsFechaproceso(new Date());
        // remision.setVrmsFecha(new Date());
        remision.setEftId(new RfEstadofactura(2));

        remision.setVrmsValortotal(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa());
        remision.setVrmsclNombres(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnNombres());

        remision.setVrmsclDiereccion(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnDiereccion());
        remision.setVrmsclTipodocuemento(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getTdcId().getTdcId());
        remision.setVrmsclIdentificacion(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnIdentificacion());
        remision.setVrmsclEmail(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnCorreoe());
        remision.setVrmsclFijo(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnFijo());
        remision.setVrmsclCelular(tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getClnCelular());
        remision.setVrmsDetalle(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getMteId().getMteNombre());
        remision.setVrmsMotivo(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getMteId().getMteNombre());
        remision.setVrmsProtagonista(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getDclnId().getDclnNombres());

        remision.setVrmsFecha(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeFechaevt());
        remision.setVrmsHora(this.getHoraFromDate(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeHorainicio()));
        remision.setVrmsDireccionevento(tablaVntRegistroventaSel.getVntRegistroventa().getVdeId().getVdeDireccionevt());
        //consultar anticipos

        remision.setVrmsAnticipo(new BigDecimal(0));
        for (VntPagoventa p : pvsfb.getLstVntPagoventaXVenta(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            remision.setVrmsAnticipo(remision.getVrmsAnticipo().
                    add((p.getRgpcValorpago() == null ? BigDecimal.ZERO : p.getRgpcValorpago())));
        }
        remision.setVrmsDescuento(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtDescuento() == null ? new BigDecimal(0) : tablaVntRegistroventaSel.getVntRegistroventa().getRgvtDescuento());
        // remision.setVrms(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa() == null ? new BigDecimal(0) : tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa());
        // remision.setVfctPorcentajeiva(new BigDecimal(16));
        //  BigDecimal operacion = factura.getVfctSubtotal().multiply(factura.getVfctPorcentajeiva()).divide(new BigDecimal(100));
        //  remision.setVfctIva(operacion);
        remision.setVrmsValortotal(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa() == null ? new BigDecimal(0) : tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa());
        remision.setVrmsSaldo(remision.getVrmsValortotal().subtract(remision.getVrmsAnticipo()));
        blnMostrarPanel = true;
        lstDetalleXRemision.clear();
        remision.setVrmsObservacion("");
        for (VntServxventa s : pvsfb.getLstServxventaXVnt(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            VntDetallerem d = new VntDetallerem();
            d.setVsrvId(s.getVsrvId());
            d.setVdrmServico(s.getVsrvId().getVsrvNombre());
            d.setVdrmEstado(true);
            d.setVdrmCantidad(s.getSrvxventCantidad());
            d.setVdrmCostototal(s.getSrvxventValtotalclnt());
            d.setVdrmCostounitario(s.getSrvxventPrecioventa());
            d.setVdrmSubservico("");
            d.setVdrmEspecificaciones("");
            for (VntProdxsrv l : pvsfb.getLstVntProdxsrvXServicio(s.getVsrvId().getVsrvId())) {
                d.setVdrmSubservico(d.getVdrmSubservico() + " " + l.getPrdId().getPrdNombre());
                if (l.getPrdId().getPrdDesc() != null && l.getPrdId().getPrdDesc().trim().equals("")) {
                    d.setVdrmEspecificaciones(d.getVdrmEspecificaciones() + " " + l.getPrdId().getPrdDesc());
                }
            }
            remision.setVrmsObservacion(remision.getVrmsObservacion() + s.getVsrvId().getVsrvNombre() + " ");
            lstDetalleXRemision.add(d);
            //  factura.setFactdcDescripcion(s.getVsrvId().getVsrvDesc());

        }

    }

    private com.icesoft.faces.context.Resource getReporteByRemision(VntRemision vr) {
        HashMap hmParametros = new HashMap();
        hmParametros.put("p_FacturaVentaKids", vr.getVrmsId());
        AdmInforme informe = astslb.getAdmInformeXId(EnInforme.REMISION_VENTA.getId());
        String rutaLogo = informe.getInfJasperruta() + "/logos/maximus_kids.jpg";
        hmParametros.put("rutaEstado", informe.getInfJasperruta() + getRutaLogoByEstado(vr.getEftId().getEftId()));
        return genInfRecurso(hmParametros, informe, 2, rutaLogo);
    }

    public void btnEditarRemision_ActionEvent(ActionEvent ae) {
        if (editarRemision()) {
            mostrarError("Remisión guardada correctamente", 3);

            jaspResource = getReporteByRemision(remision);

        }
    }

    public void btnRegresarRemision_ActionEvent(ActionEvent ae) {
        int num = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));

        limpiarCampos();

        switch (num) {
            case 1:
                blnMostrarPanel = true;
                blnMostrarPanelSeleccion = false;
                cargarServiciosXventa();
                break;
            case 2:
                blnMostrarPanelSeleccion = true;
                blnMostrarPanel = false;
                cargarListaRegVentaXClienteKids();
                break;

        }

    }

    public void btnGenInfRemision_ActionEvent(ActionEvent ae) {

        VntRemision vr = tablaVntRemisionSel.getVntRemision();
        vr.setVrmsFechaproceso(new Date());
        vr.setVrmsFecharemision(new Date());
        vr = pvsfb.editarRemision(vr, false);
        HashMap hmParametros = new HashMap();
//        hmParametros.put("p_str_id", sysTarea.getStrId());
        hmParametros.put("p_FacturaVentaKids", tablaVntRemisionSel.getVntRemision().getVrmsId());
        AdmInforme informe = astslb.getAdmInformeXId(2);
        String rutaLogo = informe.getInfJasperruta() + "/logos/";
        jaspResource = genInfRecurso(hmParametros, informe, 2, rutaLogo);
    }
    //</editor-fold>

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">
    @PostConstruct
    public void load() {
        listaEstados = pvsfb.consultarRfEstadofactura();
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        buscarFacturas();
    }

    private void buscarFacturas() {
        listaConsultaFactura.clear();
        if (blnMostrarPanelRemision) {
            for (VntRemision r : pvsfb.consultarRemisionPorParemtros(consultaFacturaDTO)) {
                TablaRespuestaFacturaDTO t = new TablaRespuestaFacturaDTO(r);
                listaConsultaFactura.add(t);
            }

        } else {
            // consulta factura
            for (VntFactura f : pvsfb.consultarFacturasPorParemtros(consultaFacturaDTO)) {
                TablaRespuestaFacturaDTO t = new TablaRespuestaFacturaDTO(f);
                listaConsultaFactura.add(t);
            }
        }
    }

    @Override
    public boolean grabarPaso() {
        return false;
    }

    @Override
    public boolean validarForm() {
        if (tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getTclId().getTclId() == 2) {
            if (idFormaPago.equals(-1)) {
                mostrarError("Seleccione la forma de pago", 1);
                return false;
            }
            if (bdValorPago == null) {
                mostrarError("Ingrese el pago o anticipo realizado", 1);
                return false;
            }
            if (bdValorPago.compareTo(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa()) > 0) {
                mostrarError("El valor ingresado es superior al valor total de la venta", 1);
                return false;
            }
        } else if (!idFormaPago.equals(-1)) {
            if (bdValorPago == null) {
                mostrarError("Ingrese el pago o anticipo realizado", 1);
                return false;
            }
            if (bdValorPago.compareTo(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa()) > 0) {
                mostrarError("El valor ingresado es superior al valor total de la venta", 1);
                return false;
            }
            return false;
        }
        return true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">
    private PcsVentaSFBean lookupPcsVentaSFBean() {
        try {
            Context c = new InitialContext();
            return (PcsVentaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/PcsVentaSFBean!com.pandora.consulta.bean.PcsVentaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">

    public List<RfEstadofactura> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<RfEstadofactura> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public List<TablaRespuestaFacturaDTO> getListaConsultaFactura() {
        return listaConsultaFactura;
    }

    public void setListaConsultaFactura(List<TablaRespuestaFacturaDTO> listaConsultaFactura) {
        this.listaConsultaFactura = listaConsultaFactura;
    }

    public ConsultaFacturaDTO getConsultaFacturaDTO() {
        return consultaFacturaDTO;
    }

    public void setConsultaFacturaDTO(ConsultaFacturaDTO consultaFacturaDTO) {
        this.consultaFacturaDTO = consultaFacturaDTO;
    }

    public BigDecimal getValorVentaSubTotal() {
        return valorVentaSubTotal;
    }

    public void setValorVentaSubTotal(BigDecimal valorVentaSubTotal) {
        this.valorVentaSubTotal = valorVentaSubTotal;
    }

    public boolean isBlnMostrarPanelSeleccion() {
        return blnMostrarPanelSeleccion;
    }

    public void setBlnMostrarPanelSeleccion(boolean blnMostrarPanelSeleccion) {
        this.blnMostrarPanelSeleccion = blnMostrarPanelSeleccion;
    }

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
     * @return the lstTablaVntRegistroventa
     */
    public List<TablaVntRegistroventa> getLstTablaVntRegistroventa() {
        return lstTablaVntRegistroventa;
    }

    /**
     * @param lstTablaVntRegistroventa the lstTablaVntRegistroventa to set
     */
    public void setLstTablaVntRegistroventa(List<TablaVntRegistroventa> lstTablaVntRegistroventa) {
        this.lstTablaVntRegistroventa = lstTablaVntRegistroventa;
    }

    /**
     * @return the tablaVntRegistroventaSel
     */
    public TablaVntRegistroventa getTablaVntRegistroventaSel() {
        return tablaVntRegistroventaSel;
    }

    /**
     * @param tablaVntRegistroventaSel the tablaVntRegistroventaSel to set
     */
    public void setTablaVntRegistroventaSel(TablaVntRegistroventa tablaVntRegistroventaSel) {
        this.tablaVntRegistroventaSel = tablaVntRegistroventaSel;
    }

    /**
     * @return the lstTablaVntFactura
     */
    public List<TablaVntFactura> getLstTablaVntFactura() {
        return lstTablaVntFactura;
    }

    /**
     * @param lstTablaVntFactura the lstTablaVntFactura to set
     */
    public void setLstTablaVntFactura(List<TablaVntFactura> lstTablaVntFactura) {
        this.lstTablaVntFactura = lstTablaVntFactura;
    }

    /**
     * @return the tablaVntFacturaSel
     */
    public TablaVntFactura getTablaVntFacturaSel() {
        return tablaVntFacturaSel;
    }

    /**
     * @param tablaVntFacturaSel the tablaVntFacturaSel to set
     */
    public void setTablaVntFacturaSel(TablaVntFactura tablaVntFacturaSel) {
        this.tablaVntFacturaSel = tablaVntFacturaSel;
    }

    /**
     * @return the datFechaFactura
     */
    public Date getDatFechaFactura() {
        return datFechaFactura;
    }

    /**
     * @param datFechaFactura the datFechaFactura to set
     */
    public void setDatFechaFactura(Date datFechaFactura) {
        this.datFechaFactura = datFechaFactura;
    }

    /**
     * @return the lstTablaVntSrvXVenta
     */
    public List<TablaVntSrvXVenta> getLstTablaVntSrvXVenta() {
        return lstTablaVntSrvXVenta;
    }

    /**
     * @param lstTablaVntSrvXVenta the lstTablaVntSrvXVenta to set
     */
    public void setLstTablaVntSrvXVenta(List<TablaVntSrvXVenta> lstTablaVntSrvXVenta) {
        this.lstTablaVntSrvXVenta = lstTablaVntSrvXVenta;
    }

    /**
     * @return the strObservacionFact
     */
    public String getStrObservacionFact() {
        return strObservacionFact;
    }

    /**
     * @param strObservacionFact the strObservacionFact to set
     */
    public void setStrObservacionFact(String strObservacionFact) {
        this.strObservacionFact = strObservacionFact;
    }

    /**
     * @return the jaspResource
     */
    public com.icesoft.faces.context.Resource getJaspResource() {
        return jaspResource;
    }

    /**
     * @param jaspResource the jaspResource to set
     */
    public void setJaspResource(com.icesoft.faces.context.Resource jaspResource) {
        this.jaspResource = jaspResource;
    }

    /**
     * @return the blnMostrarInf
     */
    public boolean isBlnMostrarInf() {
        return blnMostrarInf;
    }

    /**
     * @param blnMostrarInf the blnMostrarInf to set
     */
    public void setBlnMostrarInf(boolean blnMostrarInf) {
        this.blnMostrarInf = blnMostrarInf;
    }

    /**
     * @return the lstTablaVntRemision
     */
    public List<TablaVntRemision> getLstTablaVntRemision() {
        return lstTablaVntRemision;
    }

    /**
     * @param lstTablaVntRemision the lstTablaVntRemision to set
     */
    public void setLstTablaVntRemision(List<TablaVntRemision> lstTablaVntRemision) {
        this.lstTablaVntRemision = lstTablaVntRemision;
    }

    /**
     * @return the tablaVntRemisionSel
     */
    public TablaVntRemision getTablaVntRemisionSel() {
        return tablaVntRemisionSel;
    }

    /**
     * @param tablaVntRemisionSel the tablaVntRemisionSel to set
     */
    public void setTablaVntRemisionSel(TablaVntRemision tablaVntRemisionSel) {
        this.tablaVntRemisionSel = tablaVntRemisionSel;
    }

    /**
     * @return the datFechaRemision
     */
    public Date getDatFechaRemision() {
        return datFechaRemision;
    }

    /**
     * @param datFechaRemision the datFechaRemision to set
     */
    public void setDatFechaRemision(Date datFechaRemision) {
        this.datFechaRemision = datFechaRemision;
    }

    /**
     * @return the strObservacionRem
     */
    public String getStrObservacionRem() {
        return strObservacionRem;
    }

    /**
     * @param strObservacionRem the strObservacionRem to set
     */
    public void setStrObservacionRem(String strObservacionRem) {
        this.strObservacionRem = strObservacionRem;
    }

    /**
     * @return the lstFormaPago
     */
    public List<SelectItem> getLstFormaPago() {
        return lstFormaPago;
    }

    /**
     * @param lstFormaPago the lstFormaPago to set
     */
    public void setLstFormaPago(List<SelectItem> lstFormaPago) {
        this.lstFormaPago = lstFormaPago;
    }

    /**
     * @return the idFormaPago
     */
    public Integer getIdFormaPago() {
        return idFormaPago;
    }

    /**
     * @param idFormaPago the idFormaPago to set
     */
    public void setIdFormaPago(Integer idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    /**
     * @return the bdValorPago
     */
    public BigDecimal getBdValorPago() {
        return bdValorPago;
    }

    /**
     * @param bdValorPago the bdValorPago to set
     */
    public void setBdValorPago(BigDecimal bdValorPago) {
        this.bdValorPago = bdValorPago;
    }

    /**
     * @return the bdTotalPagado
     */
    public BigDecimal getBdTotalPagado() {
        return bdTotalPagado;
    }

    /**
     * @param bdTotalPagado the bdTotalPagado to set
     */
    public void setBdTotalPagado(BigDecimal bdTotalPagado) {
        this.bdTotalPagado = bdTotalPagado;
    }

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

    /**
     * @return the remision
     */
    public VntRemision getRemision() {
        return remision;
    }

    /**
     * @param remision the remision to set
     */
    public void setRemision(VntRemision remision) {
        this.remision = remision;
    }

    /**
     * @return the lstDetalleXRemision
     */
    public List<VntDetallerem> getLstDetalleXRemision() {
        return lstDetalleXRemision;
    }

    /**
     * @param lstDetalleXRemision the lstDetalleXRemision to set
     */
    public void setLstDetalleXRemision(List<VntDetallerem> lstDetalleXRemision) {
        this.lstDetalleXRemision = lstDetalleXRemision;
    }

    /**
     * @return the pago
     */
    public VntPagoventa getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(VntPagoventa pago) {
        this.pago = pago;
    }

    /**
     * @return the blnAplicaBanco
     */
    public boolean isBlnAplicaBanco() {
        return blnAplicaBanco;
    }

    /**
     * @param blnAplicaBanco the blnAplicaBanco to set
     */
    public void setBlnAplicaBanco(boolean blnAplicaBanco) {
        this.blnAplicaBanco = blnAplicaBanco;
    }

    /**
     * @return the idBanco
     */
    public Long getIdBanco() {
        return idBanco;
    }

    /**
     * @param idBanco the idBanco to set
     */
    public void setIdBanco(Long idBanco) {
        this.idBanco = idBanco;
    }

    /**
     * @return the lstBancos
     */
    public List<SelectItem> getLstBancos() {
        return lstBancos;
    }

    /**
     * @param lstBancos the lstBancos to set
     */
    public void setLstBancos(List<SelectItem> lstBancos) {
        this.lstBancos = lstBancos;
    }

    /**
     * @return the blnMostrarPanelRemision
     */
    public boolean isBlnMostrarPanelRemision() {
        return blnMostrarPanelRemision;
    }

    /**
     * @param blnMostrarPanelRemision the blnMostrarPanelRemision to set
     */
    public void setBlnMostrarPanelRemision(boolean blnMostrarPanelRemision) {
        this.blnMostrarPanelRemision = blnMostrarPanelRemision;
    }

    public List<TablaVntSrvXVenta> getLstTablaServiciosPendientes() {
        return lstTablaServiciosPendientes;
    }

    public void setLstTablaServiciosPendientes(List<TablaVntSrvXVenta> lstTablaServiciosPendientes) {
        this.lstTablaServiciosPendientes = lstTablaServiciosPendientes;
    }

    //</editor-fold>
}
