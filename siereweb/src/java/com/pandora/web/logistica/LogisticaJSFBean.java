/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.logistica;

import adm.sys.dao.AdmInforme;
import com.icesoft.faces.context.Resource;
import com.pandora.adm.dao.InvTipotransc;
import com.pandora.bussiness.util.EnEstadoLogistica;
import com.pandora.bussiness.util.EnEstadosOp;
import com.pandora.consulta.bean.PcsOrdenProduccionSFBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.logistica.LogisticaSFBean;
import com.pandora.mod.logistica.dao.LgtDespachoevento;
import com.pandora.mod.logistica.dao.LgtEstadoevento;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.base.RecursoDescarga;
import com.pandora.web.ordenprod.TablaPopOrdenProduccion;
import com.pandora.web.ordenprod.TablaPopProdXServXOp;
import com.pandora.web.ordenprod.TablaPopServXOp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author patricia
 */
@Named
@SessionScoped
public class LogisticaJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    @EJB
    private LogisticaSFBean lsfb;
    @EJB
    private PcsOrdenProduccionSFBean ordenProduccionSFBean;
    private Integer valorRadioconsulta = 1;
    private boolean blnMostrarPanel;
    private List<TablaPopOrdenProduccion> lstTablaPopOrdenProduccion = new ArrayList<>();
    private TablaPopOrdenProduccion tablaPopOrdenProduccionSel = new TablaPopOrdenProduccion();
    private List<TablaPopServXOp> lstTablaPopServXOp = new ArrayList<>();
    private TablaPopServXOp tablaPopServXOpSel = new TablaPopServXOp();
    private List<TablaPopProdXServXOp> lstTablaPopProdXServXOp = new ArrayList<>();
    
    private PopOrdenprod popOrdenprodSel = null;
    private InvTipotransc TIPO_TRANSACCION_SALIDA = null;
    private InvTipotransc TIPO_TRANSACCION_ENTRADA = null;

    //<editor-fold defaultstate="collapsed" desc="Variables movimiento inventario">
    
    private String codigBarrasMov;
//</editor-fold>

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del bean">
    @Override
    public void init() {
        lsfb = lookupLogisticaSFBean();
        numPanel = 1;
        blnMostrarPanel = false;
        cargarListaOrdenProdXProcesado(numPanel - 1);
        TIPO_TRANSACCION_ENTRADA = null;
        TIPO_TRANSACCION_SALIDA = null;
        for (InvTipotransc t : lsfb.getLstInvTipotransc(true, true)) {
            if (TIPO_TRANSACCION_ENTRADA == null && t.getTtrMultiplica() == -1) {
                TIPO_TRANSACCION_ENTRADA = t;
            }
            if (TIPO_TRANSACCION_SALIDA == null && t.getTtrMultiplica() == 1) {
                TIPO_TRANSACCION_SALIDA = t;
            }
        }
    }

    @Override
    public void limpiarVariables() {
    }

    private void limpiarCampos() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">
    //<editor-fold defaultstate="collapsed" desc="Funciones movimiento inventario">
     
//</editor-fold>
    private void cargarListaOrdenProdXProcesado(Integer estadoProcesado) {
        lstTablaPopOrdenProduccion.clear();

        for (PopOrdenprod ordenprod : lsfb.getLstOrdenProdXProcesado(EnEstadosOp.ANULADO.getId(), estadoProcesado)) {
            TablaPopOrdenProduccion tpop = new TablaPopOrdenProduccion();
            List<VntDetevento> lst = lsfb.getLstVntDetevento(ordenprod.getRgvtId().getRgvtId());
            for (VntDetevento d : lst) {
                ordenprod.getRgvtId().setVdeId(d);
                break;
            }
            tpop.setPopOrdenprod(ordenprod);
            lstTablaPopOrdenProduccion.add(tpop);
        }
    }

    private void cargarListaServXOrdenProd() {
        lstTablaPopServXOp.clear();
        for (PopServxop servxop : lsfb.getLstPopServxopXOrdenProd(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId())) {
            TablaPopServXOp tpsxo = new TablaPopServXOp();
            popOrdenprodSel = servxop.getOprId();
            tpsxo.setPopServxop(servxop);
            lstTablaPopServXOp.add(tpsxo);
        }
    }

//    private void cargarListaProdxservxopXServ() {
//        lstTablaPopProdXServXOp.clear();
//        for (PopProdxservxop popProdxservxop : lsfb.getLstPopProdxservxopXServ(tablaPopServXOpSel.getPopServxop().getVsrvId().getVsrvId())) {
//            TablaPopProdXServXOp tppxsxo = new TablaPopProdXServXOp();
//            tppxsxo.setPopProdxservxop(popProdxservxop);
//            lstTablaPopProdXServXOp.add(tppxsxo);
//        }
//    }
    private void cargarListaProdxservxopXOp() {
        lstTablaPopProdXServXOp.clear();
        for (PopProdxservxop popProdxservxop : lsfb.getLstPopProdxservxopXOp(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId())) {
            TablaPopProdXServXOp tppxsxo = new TablaPopProdXServXOp();
            tppxsxo.setCantSalida(popProdxservxop.getPxsoCantsalida());
            if (popProdxservxop.getPxsoCantsalida() == null) {
                tppxsxo.setCantSalida(popProdxservxop.getPxsoCantprod());
            } else {
                tppxsxo.setCantSalida(popProdxservxop.getPxsoCantsalida());
            }
            tppxsxo.setCantEntrada(popProdxservxop.getPxsoCantentrada());
            if (popProdxservxop.getPxsoCantentrada() == null) {
                tppxsxo.setCantEntrada(popProdxservxop.getPxsoCantsalida());
            } else {
                tppxsxo.setCantEntrada(popProdxservxop.getPxsoCantentrada());
            }
            tppxsxo.setObservSalida(popProdxservxop.getPxsoObservsalida());
            tppxsxo.setObservEntrada(popProdxservxop.getPxsoObserventrada());
            tppxsxo.setPopProdxservxop(popProdxservxop);
            lstTablaPopProdXServXOp.add(tppxsxo);
        }

    }

    private void grabarDespachoEvento() {
        if (validarForm()) {
            boolean salida = false;
            List<PopProdxservxop> lstPopProdxservxopGrabar = new ArrayList<>();
            switch (numPanel) {
                case 1:
                    salida = true;
                    for (TablaPopProdXServXOp tppsxo : lstTablaPopProdXServXOp) {
                        PopProdxservxop pxsxo = tppsxo.getPopProdxservxop();

                        pxsxo.setPxsoCantsalida(tppsxo.getCantSalida());
                        pxsxo.setPxsoEstadosalida(tppsxo.isEstSalida());
                        pxsxo.setPxsoObservsalida(tppsxo.getObservSalida());
                        lstPopProdxservxopGrabar.add(pxsxo);

                    }
                    break;
                case 2:
                    salida = false;
                    for (TablaPopProdXServXOp tppsxo : lstTablaPopProdXServXOp) {
                        PopProdxservxop pxsxo = tppsxo.getPopProdxservxop();

                        pxsxo.setPxsoCantentrada(tppsxo.getCantEntrada());
                        pxsxo.setPxsoEstadoentrada(tppsxo.isEstEntrada());
                        pxsxo.setPxsoObserventrada(tppsxo.getObservEntrada());
                        lstPopProdxservxopGrabar.add(pxsxo);

                    }
                    break;
            }
            lsfb.editarProdxServxOp(lstPopProdxservxopGrabar, salida, (salida ? TIPO_TRANSACCION_SALIDA : TIPO_TRANSACCION_ENTRADA), pjsfb.getAdmCrgxcolActivo());

            LgtDespachoevento lde = new LgtDespachoevento();
            lde.setOprId(tablaPopOrdenProduccionSel.getPopOrdenprod());
            lde.setDevFechaprocsalida(new Date());
            lde.setCxcIdregsalida(pjsfb.getAdmCrgxcolActivo().getCxcId());
            lde.setDevProcsalida(Boolean.TRUE);
            lde.setDevEstado(Boolean.TRUE);
            lde.setStrId(tablaPopOrdenProduccionSel.getPopOrdenprod().getStrId());
            lde = lsfb.grabarDespachoEvento(lde);

            PopOrdenprod prp = tablaPopOrdenProduccionSel.getPopOrdenprod();
            prp.setEevId(ordenProduccionSFBean.getLgtEstadoeventoById(EnEstadoLogistica.EVENTO_DESPACHADO.getId()));
            prp.setOprEstadodespacho(numPanel);
            if (prp.getOprEstadodespacho() == 2) {
                prp.setEevId(ordenProduccionSFBean.getLgtEstadoeventoById(EnEstadoLogistica.EVENTO_RECIBIDO.getId()));
            }
            prp = lsfb.editarPopOrdenprod(prp);
            cargarListaServXOrdenProd();
            cargarListaProdxservxopXOp();
            blnMostrarPanel = false;
            cargarListaOrdenProdXProcesado(numPanel - 1);
            // PopOrdenprod prp = tablaPopOrdenProduccionSel.getPopOrdenprod();

        } else {
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="eventos">
    //<editor-fold defaultstate="collapsed" desc="Eventos movimiento inventario">
    
//</editor-fold>
    
    public void btnGenerarReporteOrdenProduccion_ActionEvent(ActionEvent ae) {
        if (tablaPopOrdenProduccionSel != null && tablaPopOrdenProduccionSel.getPopOrdenprod() != null && tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId() != null) {
            HashMap hmParametros = new HashMap();

//        hmParametros.put("p_str_id", sysTarea.getStrId());
            hmParametros.put("p_idorden", tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId());
            AdmInforme informe = astslb.getAdmInformeXId(3);
            String rutaLogo = informe.getInfJasperruta() + "/logos/maximus_corporativo.jpg";
            if (tablaPopOrdenProduccionSel.getPopOrdenprod().getRgvtId().getClnId().getTclId().getTclId() == 2) {
                informe = astslb.getAdmInformeXId(4);
                rutaLogo = informe.getInfJasperruta() + "/logos/maximus_kids.jpg";

                hmParametros.put("rutaFoto", rutaLogo);

            }

            // jasperResource =
            RecursoDescarga rd = genInfRecurso(hmParametros, informe, 2, rutaLogo);
            if (rd != null) {
                irAServletDescarga(rd);
            }else{
                mostrarError("Error al generar reporte, consulte con el administrador");
            }
        }
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnMostrarPanel = false;
    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnMostrarPanel = false;
        valorRadioconsulta = 0;
        switch (numPanel) {
            case 1:
            case 2:
                cargarListaOrdenProdXProcesado(numPanel - 1);
                break;
            case 3:
                cargarListaOrdenProdXProcesado(valorRadioconsulta);
                break;

        }

    }

    public void rowDtOrdenProd_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaPopOrdenProduccionSel = (TablaPopOrdenProduccion) map.get("tpops");
        //  numPanel = tablaPopOrdenProduccionSel.getPopOrdenprod().getEevId().getEevId() + 1;
        numPanel = tablaPopOrdenProduccionSel.getPopOrdenprod().getOprEstadodespacho() + 1;

        blnMostrarPanel = true;
        cargarListaServXOrdenProd();
        cargarListaProdxservxopXOp();
    }

//    public void rowDtServXOrdenProd_ActionEvent(ActionEvent ae) {
//        Map map = ae.getComponent().getAttrites();
//        tablaPopServXOpSel = (TablaPopServXOp) map.get("tpsxos");
//    }
    public void btnGrabarDespachoEvento_ActionEvent(ActionEvent ae) {
        grabarDespachoEvento();
    }

    public void btnRegresarOrdenProd_ActionEvent(ActionEvent ae) {
        blnMostrarPanel = false;
    }

    public void executeConsultaXFiltro(ValueChangeEvent vce) {
        valorRadioconsulta = (Integer) vce.getNewValue();
        cargarListaOrdenProdXProcesado(valorRadioconsulta);

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones Heredadas">
    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
    }

    @Override
    public boolean grabarPaso() {
        return false;
    }

    @Override
    public boolean validarForm() {
        boolean cantSalida = true;
        switch (numPanel) {
            case 1:
                for (TablaPopProdXServXOp tpxsxo : lstTablaPopProdXServXOp) {
                    if (tpxsxo.getCantSalida() == null) {
                        cantSalida = false;
                    }

                }
                if (cantSalida == false) {
                    mostrarError("Ingrese las cantidades de salida para cada producto", 1);
                    return false;
                }
                break;

            case 2:
                for (TablaPopProdXServXOp tpxsxo : lstTablaPopProdXServXOp) {
                    if (tpxsxo.getCantEntrada() == null) {
                        cantSalida = false;
                    }

                }
                if (cantSalida == false) {
                    mostrarError("Ingrese las cantidades de Entrada para cada producto", 1);
                    return false;
                }
                break;

        }

        for (TablaPopProdXServXOp tpxsxo : lstTablaPopProdXServXOp) {
            if (tpxsxo.isEstSalida() == true) {
                if (tpxsxo.getObservSalida() == null || tpxsxo.getObservSalida().trim().equals("")) {
                    mostrarError("Ha marcado que un producto está en mal estado o tiene alguna deficiencia. Explique el motivo", 1);
                    return false;
                }
            }
        }
        return true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="refencias a otros Beans">
    private LogisticaSFBean lookupLogisticaSFBean() {
        try {
            Context c = new InitialContext();
            return (LogisticaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/LogisticaSFBean!com.pandora.mod.logistica.LogisticaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">

    /**
     * @return the valorRadioconsulta
     */
    public Integer getValorRadioconsulta() {
        return valorRadioconsulta;
    }

    /**
     * @param valorRadioconsulta the valorRadioconsulta to set
     */
    public void setValorRadioconsulta(Integer valorRadioconsulta) {
        this.valorRadioconsulta = valorRadioconsulta;
    }

    /**
     * @return the blnMostrarPanel
     */
    public boolean isBlnMostrarPanel() {
        return blnMostrarPanel;
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
     * @param blnMostrarPanel the blnMostrarPanel to set
     */
    public void setBlnMostrarPanel(boolean blnMostrarPanel) {
        this.blnMostrarPanel = blnMostrarPanel;
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
     * @return the tablaPopServXOpSel
     */
    public TablaPopServXOp getTablaPopServXOpSel() {
        return tablaPopServXOpSel;
    }

    /**
     * @param tablaPopServXOpSel the tablaPopServXOpSel to set
     */
    public void setTablaPopServXOpSel(TablaPopServXOp tablaPopServXOpSel) {
        this.tablaPopServXOpSel = tablaPopServXOpSel;
    }

    /**
     * @return the lsfb
     */
    public LogisticaSFBean getLsfb() {
        return lsfb;
    }

    /**
     * @param lsfb the lsfb to set
     */
    public void setLsfb(LogisticaSFBean lsfb) {
        this.lsfb = lsfb;
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
    //</editor-fold>

    /**
     * @return the popOrdenprodSel
     */
    public PopOrdenprod getPopOrdenprodSel() {
        return popOrdenprodSel;
    }

    /**
     * @param popOrdenprodSel the popOrdenprodSel to set
     */
    public void setPopOrdenprodSel(PopOrdenprod popOrdenprodSel) {
        this.popOrdenprodSel = popOrdenprodSel;
    }

    public String getCodigBarrasMov() {
        return codigBarrasMov;
    }

    public void setCodigBarrasMov(String codigBarrasMov) {
        this.codigBarrasMov = codigBarrasMov;
    }
}
