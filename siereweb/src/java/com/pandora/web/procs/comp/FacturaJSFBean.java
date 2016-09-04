/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import adm.sys.dao.SysSegtarea;
import com.icesoft.faces.context.effects.JavascriptContext;
import com.pandora.adm.cmp.FacturaSFBean;
import com.pandora.adm.cmp.TransaccionInvSFBean;
import com.pandora.adm.dao.CmpDetallefact;
import com.pandora.adm.dao.CmpFactura;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.pagocuenta.bean.RevisaPagoCuentaSFBean;
import com.pandora.pagocuenta.dao.FinCuenta;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author luis
 */
@SessionScoped
@Named
public class FacturaJSFBean extends BaseJSFBean implements IPasos, Serializable {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    private FacturaSFBean fsfb;
    TransaccionInvSFBean tisfb;
    RevisaPagoCuentaSFBean rpcsfb;

    private RevisaPagoCuentaSFBean lookupRevisaPagoCuentaSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (RevisaPagoCuentaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/RevisaPagoCuentaSFBean!com.pandora.pagocuenta.bean.RevisaPagoCuentaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private TransaccionInvSFBean lookupTransaccionInvSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (TransaccionInvSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/TransaccionInvSFBean!com.pandora.adm.cmp.TransaccionInvSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private FacturaSFBean lookupFacturaSFBean() {
        try {
            Context c = new InitialContext();
            return (FacturaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/FacturaSFBean!com.pandora.adm.cmp.FacturaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private boolean blnMostrarBotonGrabar;
    private boolean blnMostrarCerrarProceso = false;
    //Factura
    private List<TablaFactura> lstTablaFactura = new ArrayList<>();
    private TablaFactura tablaFacturaSel = new TablaFactura();
    //Detalle factura
    private List<TablaDetalleFactura> lstTablaDetalleFactura = new ArrayList<>();
    private TablaDetalleFactura tablaDetalleFacturaSel = new TablaDetalleFactura();
    private BigDecimal bdTotalValorDetFact = new BigDecimal(BigInteger.ZERO);
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">    

    @Override
    public void init() {
        fsfb = lookupFacturaSFBean();
        tisfb = lookupTransaccionInvSFBeanBean();
        rpcsfb = lookupRevisaPagoCuentaSFBeanBean();
        fsfb.setAdmCrgxcolActual(getPrincipalJSFBean().getAdmCrgxcolActivo());
        numPanel = 1;
        blnMostrarCerrarProceso = false;
        cargarListaFacturas();
    }

    @Override
    public void limpiarVariables() {
        fsfb.remove();
        tisfb.remove();
        rpcsfb.remove();
        lstTablaFactura.clear();
        lstTablaDetalleFactura.clear();
    }

    public void limpiarCampos() {
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">

    private void cargarListaFacturas() {
        lstTablaFactura.clear();
        for (CmpFactura cmpFactura : fsfb.getLstCmpFacturasXPrefactXTareaConDetPendiente(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId(), true)) {
            cmpFactura.setFactValorbruto(cmpFactura.getFactPrevalorbruto());
            TablaFactura tf = new TablaFactura(cmpFactura);
            lstTablaFactura.add(tf);
        }
    }
    
    private void cargarListaDetallefact() {
        lstTablaDetalleFactura.clear();
        for (CmpDetallefact detallefact : fsfb.getLstCmpDetallefacts(tablaFacturaSel.getCmpFactura().getFactId())) {
            detallefact.setDetfCantrecibida(detallefact.getCcmId().getCcmCant());
            detallefact.setDetfValorunitario(detallefact.getCcmId().getCcmPrecio());
            TablaDetalleFactura tdf = new TablaDetalleFactura(detallefact);
            lstTablaDetalleFactura.add(tdf);
        }
    }

    private void cargarListaDetallefactXFactProcesada() {
        lstTablaDetalleFactura.clear();
        Long strId = pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId();
        bdTotalValorDetFact = new BigDecimal(BigInteger.ZERO);
        for (CmpDetallefact detallefact : fsfb.getLstDetalleFactXTareaXProcesada(strId, Boolean.FALSE)) {

            TablaDetalleFactura tdf = new TablaDetalleFactura(detallefact);
            tdf.setBdValorTotalDetFact(detallefact.getDetfValorunitario().multiply(new BigDecimal(detallefact.getDetfCantrecibida())));
            bdTotalValorDetFact = bdTotalValorDetFact.add(tdf.getBdValorTotalDetFact());
            lstTablaDetalleFactura.add(tdf);
        }
        if (fsfb.getCantPrefactXStrIdXPrefact(strId, Boolean.TRUE) == 0L) {
            blnMostrarCerrarProceso = true;
        } else {
            blnMostrarCerrarProceso = false;
        }
    }

    private void grabarFactura() {
        if (tablaFacturaSel.getCmpFactura().getFactNumfact().equals("")
                || tablaFacturaSel.getCmpFactura().getFactValorbruto().toString().equals("")){ //|| tablaFacturaSel.getCmpFactura().getFactValorneto().toString().equals(""))         
            mostrarError("Numero de factura, valor bruto y valor neto obligatorios");
        } else {
            if (fsfb.getCantDetalleFactXFactXBlnProcesar(tablaFacturaSel.getCmpFactura().getFactId(), Boolean.FALSE) > 0L) {
                tablaFacturaSel.getCmpFactura().setFactPrefact(Boolean.FALSE);
            } else {
                tablaFacturaSel.getCmpFactura().setFactPrefact(Boolean.TRUE);
            }
            tablaFacturaSel.getCmpFactura().setFactProcesadorecibido(Boolean.TRUE);
            tablaFacturaSel.getCmpFactura().setFactEnvpagocuenta(Boolean.FALSE);
            fsfb.editarFactura(tablaFacturaSel.getCmpFactura());

            blnMostrarBotonGrabar = true;
        }
    }

    private void grabarDetalleFact() {
        List<CmpDetallefact> lstCmpDetallefactsGrabar = new ArrayList<>();
        for (TablaDetalleFactura tdf : lstTablaDetalleFactura) {
            CmpDetallefact cd = tdf.getCmpDetallefact();
            if (cd.getDetfRecibido().equals(Boolean.TRUE)) {
                if (!(cd.getDetfCantrecibida() == null || cd.getDetfValorunitario().toString().equals(""))) {
                    cd.setFactId(tablaFacturaSel.getCmpFactura());
                    cd.setDetfProcesadorecibido(Boolean.TRUE);
                    cd.setDetfFecharecibido(new Date());
                    lstCmpDetallefactsGrabar.add(cd);
                }
            } else {
                cd.setDetfProcesadorecibido(Boolean.TRUE);
                cd.setDetfFecharecibido(new Date());
                cd.setDetfRecibido(Boolean.FALSE);
//                cd.setDetfDevolucion(tdf.getCmpDetallefact().getDetfDevolucion());
//                cd.setDetfObservacion(tdf.getCmpDetallefact().getDetfObservacion());
                lstCmpDetallefactsGrabar.add(cd);
            }
        }
        BigDecimal bdSumaValorDetalle = new BigDecimal(BigInteger.ZERO);
        for (CmpDetallefact cmpDetallefact : lstCmpDetallefactsGrabar) {
            bdSumaValorDetalle = bdSumaValorDetalle.add(cmpDetallefact.getDetfValorunitario().multiply(new BigDecimal(cmpDetallefact.getDetfCantrecibida())));
        }
        BigDecimal bdValorBrutoFact = tablaFacturaSel.getCmpFactura().getFactValorbruto();
        if (bdSumaValorDetalle.compareTo(bdValorBrutoFact) != 0) {
            mostrarError("La suma del detalle: " + bdSumaValorDetalle + " no concide con el valor " + bdValorBrutoFact + " de la factura");
            CmpFactura cmpFactura = fsfb.getCmpFacturaSel();
            cmpFactura.setFactPrefact(Boolean.TRUE);
            cmpFactura.setFactProcesadorecibido(Boolean.FALSE);
            cmpFactura.setFactEnvpagocuenta(Boolean.FALSE);
            fsfb.grabarLstDetalleFacturaIncompleto(lstCmpDetallefactsGrabar);
            fsfb.editarFactura(cmpFactura);
            return;
        }
        fsfb.grabarLstDetalleFactura(lstCmpDetallefactsGrabar);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">

    public void btnGrabarCerrarProceso_ActionEvent(ActionEvent ae) {

        SysSegtarea sysSegtarea = getPrincipalJSFBean().getMssfb().getSysSegtareaActual();
        Long strId = sysSegtarea.getStrId().getStrId();
        if (fsfb.getCantPrefactXStrIdXPrefact(strId, Boolean.TRUE) == 0L) {
            List<FinCuenta> lstFinCuentasGrabar = new ArrayList<>();
            for (CmpFactura cmpFactura : fsfb.getLstCmpFacturasXTareaXEnvPagoCuenta(strId, Boolean.FALSE)) {
                FinCuenta finCuenta = new FinCuenta();
                finCuenta.setCtaFechaproceso(new Date());
                finCuenta.setCtaRevisado(Boolean.FALSE);
                cmpFactura.setFactEnvpagocuenta(Boolean.TRUE);
                fsfb.editarFactura(cmpFactura);
                finCuenta.setFactId(cmpFactura);
                finCuenta.setStrId(strId);
                lstFinCuentasGrabar.add(finCuenta);

            }
            rpcsfb.grabarLstCuentasXOrdenCompra(lstFinCuentasGrabar);
        }
        astslb.crearTareaInicioXProc("PRLOG02", 2, 4L);

        astslb.crearSeguimientoTareaPasoOrigenDest(sysSegtarea.getStrId(), sysSegtarea.getSpsId().getSpsId(), 3L);

        mostrarError("Facturas enviadas a pago de cuentas, ir a despacho de pedido.", 3);
        blnMostrarCerrarProceso = false;

    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnMostrarBotonGrabar = false;
        switch (numPanel) {
            case 1:
                break;
            case 2:
                cargarListaDetallefactXFactProcesada();
                break;
        }
    }
    
    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void rowDtFacturaCompra_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaFacturaSel = (TablaFactura) map.get("tfc");
        fsfb.setCmpFacturaSel(tablaFacturaSel.getCmpFactura());
        blnMostrarBotonGrabar = true;
        cargarListaDetallefact();
    }

    public void rowDtFactRecibirDetCompra_ActionEvent(ValueChangeEvent vce) {

        TablaDetalleFactura tdfSel = (TablaDetalleFactura) vce.getComponent().getAttributes().get("tdfc");
        tdfSel.getCmpDetallefact().setDetfRecibido(Boolean.TRUE);
//        if (!(tdfSel.getCmpDetallefact().getDetfCantrecibida() == null || tdfSel.getCmpDetallefact().getDetfValorunitario().toString().equals(""))) {
//            tdfSel.getCmpDetallefact().setFactId(tablaFacturaSel.getCmpFactura());
//            tdfSel.getCmpDetallefact().setDetfProcesadorecibido(Boolean.TRUE);
//            tdfSel.getCmpDetallefact().setDetfFecharecibido(new Date());
//            fsfb.editarDetalleFactura(tdfSel.getCmpDetallefact());
//
//        }

    }

//    public void rowDtDetalleFactura_ActionEvent(ActionEvent ae) {
//        Map map = ae.getComponent().getAttributes();
//        tablaDetalleFacturaSel = (TablaDetalleFactura) map.get("tdf");
//    }
    public void btnVerDetalleFactura_ActionEvent(ActionEvent ae) {
    }

    public void btnGrabarFacturaCompra_ActionEvent(ActionEvent ae) {
        tablaFacturaSel = (TablaFactura) ae.getComponent().getAttributes().get("tfc");
        fsfb.setCmpFacturaSel(tablaFacturaSel.getCmpFactura());
        grabarFactura();
        blnMostrarBotonGrabar = true;
        cargarListaDetallefact();
    }

    public void rowDtFactGrabarFacturaCompra_ActionEvent(ActionEvent ae) {
        tablaFacturaSel = (TablaFactura) ae.getComponent().getAttributes().get("tfc");
        grabarFactura();
        blnMostrarBotonGrabar = true;
        cargarListaDetallefact();
    }

    public void btnGrabarDetalleFactura_ActionEvent(ActionEvent ae) {
        Integer cantNoRecibidos = 0;
        for (TablaDetalleFactura tdf : lstTablaDetalleFactura) {
            if (tdf.getCmpDetallefact().getDetfRecibido() == false) {
                cantNoRecibidos += 1;
            }
        }
        if (cantNoRecibidos > 0) {
            JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "confirmarGrabarDetalleFact.show()");
        } else {
            grabarDetalleFact();
        }
    }

    public void btnConfirmarGrabarDetalleFactura(ActionEvent ae) {
        grabarDetalleFact();
    }

    public void btnVolverFactura_ActionEvent(ActionEvent ae) {
        blnMostrarBotonGrabar = false;
        lstTablaDetalleFactura.clear();
        lstTablaFactura.clear();
        cargarListaFacturas();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">

    @Override
    public boolean grabarPaso() {
        return false;
    }

    @Override
    public boolean validarForm() {
        return false;
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">

    public CompAprbSolsJSFBean getCompAprbSolsJSFBean() {
        fc = FacesContext.getCurrentInstance();
        elc = fc.getELContext();
        return (CompAprbSolsJSFBean) elc.getELResolver().getValue(elc, null, "compAprbSolsJSFBean");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">

    /**
     * @return the blnMostrarBotonGrabar
     */
    public boolean isBlnMostrarBotonGrabar() {
        return blnMostrarBotonGrabar;
    }

    /**
     * @param blnMostrarBotonGrabar the blnMostrarBotonGrabar to set
     */
    public void setBlnMostrarBotonGrabar(boolean blnMostrarBotonGrabar) {
        this.blnMostrarBotonGrabar = blnMostrarBotonGrabar;
    }

    /**
     * @return the lstTablaFactura
     */
    public List<TablaFactura> getLstTablaFactura() {
        return lstTablaFactura;
    }

    /**
     * @param lstTablaFactura the lstTablaFactura to set
     */
    public void setLstTablaFactura(List<TablaFactura> lstTablaFactura) {
        this.lstTablaFactura = lstTablaFactura;
    }

    /**
     * @return the lstTablaDetalleFactura
     */
    public List<TablaDetalleFactura> getLstTablaDetalleFactura() {
        return lstTablaDetalleFactura;
    }

    /**
     * @param lstTablaDetalleFactura the lstTablaDetalleFactura to set
     */
    public void setLstTablaDetalleFactura(List<TablaDetalleFactura> lstTablaDetalleFactura) {
        this.lstTablaDetalleFactura = lstTablaDetalleFactura;
    }

    /**
     * @return the tablaFacturaSel
     */
    public TablaFactura getTablaFacturaSel() {
        return tablaFacturaSel;
    }

    /**
     * @param tablaFacturaSel the tablaFacturaSel to set
     */
    public void setTablaFacturaSel(TablaFactura tablaFacturaSel) {
        this.tablaFacturaSel = tablaFacturaSel;
    }

    /**
     * @return the tablaDetalleFacturaSel
     */
    public TablaDetalleFactura getTablaDetalleFacturaSel() {
        return tablaDetalleFacturaSel;
    }

    /**
     * @param tablaDetalleFacturaSel the tablaDetalleFacturaSel to set
     */
    public void setTablaDetalleFacturaSel(TablaDetalleFactura tablaDetalleFacturaSel) {
        this.tablaDetalleFacturaSel = tablaDetalleFacturaSel;
    }
    //</editor-fold>

    /**
     * @return the bdTotalValorDetFact
     */
    public BigDecimal getBdTotalValorDetFact() {
        return bdTotalValorDetFact;
    }

    /**
     * @param bdTotalValorDetFact the bdTotalValorDetFact to set
     */
    public void setBdTotalValorDetFact(BigDecimal bdTotalValorDetFact) {
        this.bdTotalValorDetFact = bdTotalValorDetFact;
    }

    /**
     * @return the blnMostrarCerrarProceso
     */
    public boolean isBlnMostrarCerrarProceso() {
        return blnMostrarCerrarProceso;
    }

    /**
     * @param blnMostrarCerrarProceso the blnMostrarCerrarProceso to set
     */
    public void setBlnMostrarCerrarProceso(boolean blnMostrarCerrarProceso) {
        this.blnMostrarCerrarProceso = blnMostrarCerrarProceso;
    }
}
