/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procesos;

import com.pandora.adm.rf.dao.RfEstadofactura;
import com.pandora.consulta.bean.PcsPagoSFBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.logistica.dao.LgtEstadoevento;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.mod.venta.dao.VntDetallefact;
import com.pandora.mod.venta.dao.VntDetallerem;
import com.pandora.mod.venta.dao.VntFactura;
import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntRemision;
import com.pandora.mod.venta.dao.VntServxventa;
import com.pandora.pagocuenta.dao.FinFormapago;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.venta.TablaVntRegistroventa;
import com.pandora.web.venta.TablaVntSrvXVenta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author sandra
 */
@Named
@SessionScoped
public class PcsConfirmaPagoJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    PcsPagoSFBean ppsfb;
    //Manejo de paneles
    private static final int PANEL_CONFIRMAR_PAGO = 1;
    private static final int PANEL_PAGO_CONFIRMADO = 2;
    private boolean blnMostrarPanel;

    private List<TablaVntRegistroventa> lstTablaVntRegistroventa = new ArrayList<>();
    private TablaVntRegistroventa tablaVntRegistroventaSel = new TablaVntRegistroventa();
    private List<TablaVntSrvXVenta> lstTablaVntSrvXVenta = new ArrayList<>();
    private List<SelectItem> lstFormaPago = new ArrayList<>();
    private Integer idFormaPago = -1;
    private BigDecimal bdValorPago;
    private BigDecimal bdTotalPagado;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">
    @Override
    public void init() {
        ppsfb = lookupPcsPagoSFBean();
        numPanel = PANEL_CONFIRMAR_PAGO;
        blnMostrarPanel = false;
        idFormaPago = -1;
        cargarListaRegVentaSinConf();
    }

    @Override
    public void limpiarVariables() {
        ppsfb.remove();
    }

    private void limpiarCampos() {
        idFormaPago = -1;
        bdValorPago = null;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">
    private void cargarListaRegVentaSinConf() {
        lstTablaVntRegistroventa.clear();
        for (VntRegistroventa registroventa : ppsfb.getLstVntRegistroventaXPagoConf(3, false, false)) {
            TablaVntRegistroventa tvr = new TablaVntRegistroventa();
            tvr.setVntRegistroventa(registroventa);
            lstTablaVntRegistroventa.add(tvr);
        }
    }

    private void cargarListaRegVentaConf() {
        lstTablaVntRegistroventa.clear();
        for (VntRegistroventa registroventa : ppsfb.getLstVntRegistroventaXPagoConf(3, true, true)) {
            TablaVntRegistroventa tvr = new TablaVntRegistroventa();
            tvr.setVntRegistroventa(registroventa);
            lstTablaVntRegistroventa.add(tvr);
        }
    }

    public void cargarListaFormaPago() {
        lstFormaPago.clear();
        lstFormaPago.add(new SelectItem(-1, "SELECCIONE >>"));
        for (FinFormapago ff : ppsfb.getLstFinFormapagoXEstado(true)) {
            lstFormaPago.add(new SelectItem(ff.getFpgId(), ff.getFpgNombre()));
        }
    }

    private void cargarListaServXVenta() {
        lstTablaVntSrvXVenta.clear();
        for (VntServxventa servxventa : ppsfb.getLstServxventaXVnt(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            TablaVntSrvXVenta tvsxv = new TablaVntSrvXVenta();
            tvsxv.setVntServxventa(servxventa);
            lstTablaVntSrvXVenta.add(tvsxv);
        }
    }

    private void confirmarPago() {
        if (validarForm()) {
            List<VntRegistroventa> lstVntRegistroventaGrabar = new ArrayList<>();
            List<PopOrdenprod> lstPopOrdenprod = new ArrayList<>();
//            VntRegistroventa vr = tablaVntRegistroventaSel.getVntRegistroventa();
//            vr.setRgvtPagado(Boolean.TRUE);
//            vr.setRgvtValorpagado(bdValorPago);
//            vr.setFpgId(ppsfb.getFormapagoXId(idFormaPago));
//            vr.setRgvtEstconfirmada(Boolean.TRUE);
//            vr.setRgvtFechaconfirma(new Date());
//            ppsfb.editarRegVenta(vr);
//
//            PopOrdenprod po = new PopOrdenprod();
//            po.setRgvtId(vr);
//            po.setOprEstado(Boolean.TRUE);
//            po.setOprProcesado(Boolean.FALSE);
//            po.setOprEstadodespacho(1);
//            
//            
//            for (VntServxventa vs : vr.getVntServxventaList()) {
//                PopServxop ps = new PopServxop();
//                ps.setVsrvId(vs.getVsrvId());
//                ps.setSxoCantsrv(vs.getSrvxventCantidad());
//                ps.setOprId(po);
//                ps.setSxoEstado(Boolean.TRUE);
//                if (po.getPopServxopList() == null) {
//                    po.setPopServxopList(new ArrayList<PopServxop>());
//                }
//                po.getPopServxopList().add(ps);
//                List<VntProdxsrv> pxs = ppsfb.getLstVntProdxsrvXServicio(vs.getVsrvId().getVsrvId());
//                for (VntProdxsrv vpxs : pxs) {
//                    PopProdxservxop pxsxo = new PopProdxservxop();
//                    pxsxo.setPrdId(vpxs.getPrdId());
//                    pxsxo.setPxsoCantprod(vpxs.getProdxsrvCantidad());
//                    pxsxo.setPxsoEstado(Boolean.TRUE);
//                    pxsxo.setPxsoEstadosalida(Boolean.FALSE);
//                    pxsxo.setPxsoEstadoentrada(Boolean.FALSE);
//                    pxsxo.setSxoId(ps);
//                    if (ps.getPopProdxservxopList() == null) {
//                        ps.setPopProdxservxopList(new ArrayList<PopProdxservxop>());
//                    }
//                    ps.getPopProdxservxopList().add(pxsxo);
//                }
//            }
            for (TablaVntRegistroventa tvr : lstTablaVntRegistroventa) {
                PopOrdenprod po = new PopOrdenprod();
                VntRegistroventa vr = tvr.getVntRegistroventa();
                vr.setRgvtPagado(Boolean.TRUE);
                vr.setRgvtValorpagado(bdValorPago);
                vr.setFpgId(ppsfb.getFormapagoXId(idFormaPago));
                vr.setRgvtEstconfirmada(Boolean.TRUE);
                vr.setRgvtFechaconfirma(new Date());
                ppsfb.editarRegVenta(vr);
                po.setRgvtId(vr);
                po.setOprEstado(Boolean.TRUE);
                po.setOprProcesado(Boolean.FALSE);
                po.setEevId(new LgtEstadoevento(1));
                for (VntServxventa vs : vr.getVntServxventaList()) {
                    PopServxop ps = new PopServxop();
                    ps.setVsrvId(vs.getVsrvId());
                    ps.setSxoCantsrv(vs.getSrvxventCantidad());
                    ps.setOprId(po);
                    ps.setSxoEstado(Boolean.TRUE);
                    if (po.getPopServxopList() == null) {
                        po.setPopServxopList(new ArrayList<PopServxop>());
                    }
                    po.getPopServxopList().add(ps);
                    List<VntProdxsrv> pxs = ppsfb.getLstVntProdxsrvXServicio(vs.getVsrvId().getVsrvId());
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
                lstPopOrdenprod.add(po);
                tvr.setVntRegistroventa(ppsfb.getVntRegistroventaSel());
            }
            if (!lstPopOrdenprod.isEmpty()) {
                lstPopOrdenprod = ppsfb.grabarLstOrdenprod(lstPopOrdenprod);
                for (PopOrdenprod po : lstPopOrdenprod) {
                    for (PopServxop popServxop : po.getPopServxopList()) {
                        if (po.getRgvtId().getClnId().getTclId().getTclId() == 1) {
                            VntFactura vf = new VntFactura();
                            vf.setVfctAnticipo(bdValorPago);
                            if (bdValorPago != null) {
                                vf.setVfctValortotal(po.getRgvtId().getRgvtValorventa().subtract(bdValorPago));
                            }
                            vf.setRgvtId(po.getRgvtId());
                            vf.setEftId(new RfEstadofactura(1));
                            vf.setCxcId(pjsfb.getAdmCrgxcolActivo());
                            vf.setVfctValortotal(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa());
                            vf.setStrId(po.getStrId());
                            vf = ppsfb.grabarFactura(vf);
                            List<PopProdxservxop> ppsxo = ppsfb.getLstPopProdxservxopXServicio(popServxop.getVsrvId().getVsrvId());
                            for (PopProdxservxop ppxs : ppsxo) {
                                VntDetallefact vdf = new VntDetallefact();
                                vdf.setVfctId(vf);
                              //  vdf.setPxsoId(ppxs);
                                vdf.setVdftEstado(Boolean.TRUE);
                                vdf = ppsfb.grabarDetalleFact(vdf);
                            }
                        } else {
                            VntRemision vr = new VntRemision();
                            vr.setVrmsAnticipo(bdValorPago);
                            if (bdValorPago != null) {
                                vr.setVrmsValortotal(po.getRgvtId().getRgvtValorventa().subtract(bdValorPago));
                            }
                            vr.setRgvtId(po.getRgvtId());
                            vr.setEftId(new RfEstadofactura(1));
                            vr.setCxcId(pjsfb.getAdmCrgxcolActivo());
                            vr.setVrmsValortotal(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa());
                            vr.setStrId(po.getStrId());
                            vr = ppsfb.grabarRemision(vr);
                            List<PopProdxservxop> ppsxo = ppsfb.getLstPopProdxservxopXServicio(popServxop.getVsrvId().getVsrvId());
                            for (PopProdxservxop pxsxo : ppsxo) {
                                VntDetallerem vdr = new VntDetallerem();
                                vdr.setVrmsId(vr);
                               // vdr.setPxsoId(pxsxo);
                                vdr.setVdrmEstado(Boolean.TRUE);
                                vdr = ppsfb.grabarDetalleRem(vdr);
                            }
                        }
                    }
                }
            }
            mostrarError("Grabación exitosa, ir a orden de producción...!", 3);
        } else {
            return;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnMostrarPanel = false;
        limpiarCampos();
        switch (numPanel) {
            case PANEL_CONFIRMAR_PAGO:
                cargarListaRegVentaSinConf();
                break;
            case PANEL_PAGO_CONFIRMADO:
                cargarListaRegVentaConf();
                break;
        }
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {

    }

    public void rowDtRegistroVenta_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntRegistroventaSel = (TablaVntRegistroventa) map.get("trvs");
        blnMostrarPanel = true;
        cargarListaFormaPago();
        cargarListaServXVenta();
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
        cargarListaRegVentaSinConf();
    }

    public void btnRegresarPagoConf_ActionEvent(ActionEvent ae) {
        blnMostrarPanel = false;
        cargarListaRegVentaConf();
    }
    
     public void btnGrabarFactura_ActionEvent(ActionEvent ae) {
       
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">
    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {

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
        } else {
            if (!idFormaPago.equals(-1)) {
                if (bdValorPago == null) {
                    mostrarError("Ingrese el pago o anticipo realizado", 1);
                    return false;
                }
                return false;
            }
        }
        if (bdValorPago.compareTo(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtValorventa()) > 0) {
            mostrarError("El valor ingresado es superior al valor total de la venta", 1);
            return false;
        }
        return true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">
    private PcsPagoSFBean lookupPcsPagoSFBean() {
        try {
            Context c = new InitialContext();
            return (PcsPagoSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/PcsPagoSFBean!com.pandora.consulta.bean.PcsPagoSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
    //</editor-fold>
}
