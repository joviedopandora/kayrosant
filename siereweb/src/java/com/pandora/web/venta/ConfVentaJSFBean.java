/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.venta;

import adm.sys.dao.SysTarea;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.ordenprod.OrdenProduccionSFBean;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.mod.venta.VentaSFBean;
import com.pandora.adm.rf.dao.RfEstadofactura;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
 * @author luis
 */
@SessionScoped
@Named
public class ConfVentaJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    private VentaSFBean vsfb;
    private OrdenProduccionSFBean opsfb;

    private boolean blnMostrarPanel;
    private List<TablaVntRegistroventa> lstTablaVntRegistroventas = new ArrayList<>();
    private List<TablaVntSrvXVenta> lstTablaVntSrvXVenta = new ArrayList<>();
    private TablaVntRegistroventa tablaVntRegistroventaSel = new TablaVntRegistroventa();
    private TablaVntPagoVenta tablaVntPagoVentaSel = new TablaVntPagoVenta();
    private List<SelectItem> lstFormaPago = new ArrayList<>();
    private Integer idFormaPago = -1;
    private BigDecimal bdValorPago;
    private BigDecimal bdTotalPagado;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">
    @Override
    public void init() {
        vsfb = lookupVentaSFBeanBean();
        opsfb = lookupOrdenProduccionSFBean();
        blnMostrarPanel = false;
        idFormaPago = -1;
        cargarRegVentaXPasoXEstVenta();
    }

    @Override
    public void limpiarVariables() {
        vsfb.remove();
        opsfb.remove();
    }

    private void limpiarCampos() {
        idFormaPago = -1;
        bdValorPago = null;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">
    private void cargarRegVentaXPasoXEstVenta() {
        lstTablaVntRegistroventas.clear();
        for (VntRegistroventa vr : vsfb.getLstRegistroventaXPasoXEstVenta(31L, Boolean.TRUE, 3)) {
            TablaVntRegistroventa tvr = new TablaVntRegistroventa(vr);
            lstTablaVntRegistroventas.add(tvr);
        }
    }

    private void cargarListaFormaPago() {
        lstFormaPago.clear();
        lstFormaPago.add(new SelectItem(-1, "SELECCIONE >>"));
        for (FinFormapago ff : vsfb.getLstFinFormapagoXEstado(true)) {
            lstFormaPago.add(new SelectItem(ff.getFpgId(), ff.getFpgNombre()));
        }
    }

    private void cargarListaServXVenta() {
        lstTablaVntSrvXVenta.clear();
        for (VntServxventa servxventa : vsfb.getLstServxventaXVnt(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            TablaVntSrvXVenta tvsxv = new TablaVntSrvXVenta();
            tvsxv.setVntServxventa(servxventa);
            lstTablaVntSrvXVenta.add(tvsxv);
        }
    }

    private void confirmarPago() {
        if (validarForm()) {
            List<VntRegistroventa> lstVntRegistroventaGrabar = new ArrayList<>();
            List<PopOrdenprod> lstPopOrdenprod = new ArrayList<>();
            SysTarea st = getPrincipalJSFBean().getMssfb().getSysSegtareaActual().getStrId();
            for (TablaVntRegistroventa tvr : lstTablaVntRegistroventas) {
                //if (tvr.isSeleccionado()) {
                PopOrdenprod po = new PopOrdenprod();
                VntRegistroventa vr = tvr.getVntRegistroventa();
                vr.setRgvtPagado(Boolean.TRUE);
                vr.setRgvtValorpagado(bdValorPago);
                vr.setFpgId(vsfb.getFormapagoXId(idFormaPago));
                vr.setRgvtEstconfirmada(Boolean.TRUE);
                vr.setRgvtFechaconfirma(new Date());
                vsfb.editarRegVenta(vr);
                po.setRgvtId(vr);
                po.setOprEstado(Boolean.TRUE);
                po.setOprProcesado(Boolean.FALSE);
                po.setStrId(st.getStrId());
                for (VntServxventa vs : vr.getVntServxventaList()) {
                    PopServxop ps = new PopServxop();
                    ps.setVsrvId(vs.getVsrvId());
                    ps.setSxoCantsrv(vs.getSrvxventCantidad());
                    ps.setOprId(po);
                    ps.setSxoEstado(Boolean.TRUE);
                    ps.setStrId(st.getStrId());
                    if (po.getPopServxopList() == null) {
                        po.setPopServxopList(new ArrayList<PopServxop>());
                    }
                    po.getPopServxopList().add(ps);
                    List<VntProdxsrv> pxs = vsfb.getLstVntProdxsrvXServicio(vs.getVsrvId().getVsrvId());
                    for (VntProdxsrv vpxs : pxs) {
                        PopProdxservxop pxsxo = new PopProdxservxop();
                        pxsxo.setPrdId(vpxs.getPrdId());
                        pxsxo.setPxsoCantprod(vpxs.getProdxsrvCantidad());
                        pxsxo.setPxsoEstado(Boolean.TRUE);
                        pxsxo.setPxsoEstadosalida(Boolean.FALSE);
                        pxsxo.setPxsoEstadoentrada(Boolean.FALSE);
                        pxsxo.setStrId(ps.getStrId());
                        pxsxo.setSxoId(ps);
                        if (ps.getPopProdxservxopList() == null) {
                            ps.setPopProdxservxopList(new ArrayList<PopProdxservxop>());
                        }
                        ps.getPopProdxservxopList().add(pxsxo);
                    }
                }
                lstPopOrdenprod.add(po);
                tvr.setVntRegistroventa(vsfb.getVntRegistroventaSel());
                astslb.crearSeguimientoTareaPasoOrigenDest(astslb.getSysTareaXId(tvr.getVntRegistroventa().getStrId()), 31L, 36L);
                // }
            }
            if (!lstPopOrdenprod.isEmpty()) {
                lstPopOrdenprod = opsfb.grabarLstOrdenprod(lstPopOrdenprod);
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
                            vf = vsfb.grabarFactura(vf);
                            List<PopProdxservxop> ppsxo = vsfb.getLstPopProdxservxopXServicio(popServxop.getVsrvId().getVsrvId());
                            for (PopProdxservxop ppxs : ppsxo) {
                                VntDetallefact vdf = new VntDetallefact();
                                vdf.setVfctId(vf);
                            //    vdf.setPxsoId(ppxs);
                                vdf.setVdftEstado(Boolean.TRUE);
                                vdf = vsfb.grabarDetalleFact(vdf);
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
                            vr = vsfb.grabarRemision(vr);
                            List<PopProdxservxop> ppsxo = vsfb.getLstPopProdxservxopXServicio(popServxop.getVsrvId().getVsrvId());
                            for (PopProdxservxop pxsxo : ppsxo) {
                                VntDetallerem vdr = new VntDetallerem();
                                vdr.setVrmsId(vr);
                                //vdr.setPxsoId(pxsxo);
                                vdr.setVdrmEstado(Boolean.TRUE);
                                vdr = vsfb.grabarDetalleRem(vdr);
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
    public void navLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnMostrarPanel = false;
        limpiarCampos();
    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {

    }

    public void rowDtRegventaPagar_ActionEvent(ActionEvent ae) {
        tablaVntRegistroventaSel = (TablaVntRegistroventa) ae.getComponent().getAttributes().get("tablaVntRegistroventaSel");
        numPanel = 2;
        cargarListaFormaPago();
        cargarListaServXVenta();
    }

    public void btnConfPago_ActionEvent(ActionEvent ae) {
        confirmarPago();
    }

    public void btnVolverPago_ActionEvent(ActionEvent ae) {
        numPanel = 1;
    }

    public void btnCerrarConfPago_ActionEvent(ActionEvent ae) {

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
//        if (tablaVntRegistroventaSel.getVntRegistroventa().getClnId().getTclId().getTclId() == 2) {
//            if (idFormaPago.equals(-1)) {
//                mostrarError("Seleccione la forma de pago", 1);
//                return false;
//            }
//            if (bdValorPago == null) {
//                mostrarError("Ingrese el pago o anticipo realizado", 1);
//                return false;
//            }
//        } else {
//            if (!idFormaPago.equals(-1)) {
//                if (bdValorPago == null) {
//                    mostrarError("Ingrese el pago o anticipo realizado", 1);
//                    return false;
//                }
//                return false;
//            }
//        }
        return true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">
    private VentaSFBean lookupVentaSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (VentaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/VentaSFBean!com.pandora.mod.venta.VentaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private OrdenProduccionSFBean lookupOrdenProduccionSFBean() {
        try {
            Context c = new InitialContext();
            return (OrdenProduccionSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/OrdenProduccionSFBean!com.pandora.mod.ordenprod.OrdenProduccionSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
    /**
     * @return the lstTablaVntRegistroventas
     */
    public List<TablaVntRegistroventa> getLstTablaVntRegistroventas() {
        return lstTablaVntRegistroventas;
    }

    /**
     * @param lstTablaVntRegistroventas the lstTablaVntRegistroventas to set
     */
    public void setLstTablaVntRegistroventas(List<TablaVntRegistroventa> lstTablaVntRegistroventas) {
        this.lstTablaVntRegistroventas = lstTablaVntRegistroventas;
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
     * @return the tablaVntPagoVentaSel
     */
    public TablaVntPagoVenta getTablaVntPagoVentaSel() {
        return tablaVntPagoVentaSel;
    }

    /**
     * @param tablaVntPagoVentaSel the tablaVntPagoVentaSel to set
     */
    public void setTablaVntPagoVentaSel(TablaVntPagoVenta tablaVntPagoVentaSel) {
        this.tablaVntPagoVentaSel = tablaVntPagoVentaSel;
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
    //</editor-fold>
}
