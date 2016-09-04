/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.ordenprod;

import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.SysTarea;
import com.pandora.adm.dao.InvProducto;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.ordenprod.OrdenProduccionSFBean;
import com.pandora.mod.ordenprod.dao.PopCxccitacion;
import com.pandora.mod.ordenprod.dao.PopCxcevento;
import com.pandora.mod.ordenprod.dao.PopCxcrespon;
import com.pandora.mod.ordenprod.dao.PopCxcrol;
import com.pandora.mod.ordenprod.dao.PopCxcuniforme;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.mod.venta.VentaSFBean;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntServxventa;
import com.pandora.web.adm.param.TablaAdmCrgXCol;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.procs.comp.TablaInvent;
import com.pandora.web.procs.comp.TablaProducto;
import com.pandora.web.venta.TablaVntSrvXVenta;
import com.pandora.web.venta.TablaVntRegistroventa;
import java.io.Serializable;
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
 * @author byrobles
 */
@Named
@SessionScoped
public class OrdenProduccionJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    OrdenProduccionSFBean opsfb;
    private VentaSFBean vsfb;

    private boolean blnMostrarPanel;
    private boolean blnMostrarProductos;
    private List<TablaVntRegistroventa> lstTablaVntRegistroventa = new ArrayList<>();
    private List<TablaVntSrvXVenta> lstTablaVntSrvXVenta = new ArrayList<>();
    private TablaVntSrvXVenta tablaVntSrvXVentaSel = new TablaVntSrvXVenta();
    private List<TablaAdmCrgXCol> lstTablaAdmCrgXCol = new ArrayList<>();
    private List<TablaInvent> lstTablaInvent = new ArrayList<>();
    private List<TablaPopOrdenProduccion> lstTablaPopOrdenProduccion = new ArrayList<>();
    private TablaPopOrdenProduccion tablaPopOrdenProduccionSel = new TablaPopOrdenProduccion();
    private List<TablaPopServXOp> lstTablaPopServXOp = new ArrayList<>();
    private TablaPopServXOp tablaPopServXOpSel = new TablaPopServXOp();
    private List<TablaVntRegistroventa> lstTablaVntRegistroventas = new ArrayList<>();
    private TablaVntRegistroventa tablaVntRegistroventaSel = new TablaVntRegistroventa();
    private List<TablaPopProdXServXOp> lstTablaPopProdXServXOp = new ArrayList<>();
    private List<TablaProducto> lstTablaProducto = new ArrayList<>();
    private TablaProducto tablaProductoSel = new TablaProducto();

   

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">
    @Override
    public void init() {
        opsfb = lookupOrdenProduccionSFBean();
        vsfb = lookupVentaSFBeanBean();
        numPanel = 1;
        blnMostrarPanel = false;
        blnMostrarProductos = false;
        cargarOrdenProdXProcXTarea();
        cargarRegVentaXPasoXEstVenta();
        
    }

    @Override
    public void limpiarVariables() {
        opsfb.remove();
        vsfb.remove();
        lstTablaPopProdXServXOp.clear();
    }

    public void limpiarCampos() {

    }

   
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">    
    private void cargarOrdenProdXProcXTarea() {
        SysTarea sysTarea = pjsfb.getMssfb().getSysSegtareaActual().getStrId();
        lstTablaPopOrdenProduccion.clear();
        for (PopOrdenprod ordenprod : opsfb.ggetLstPopOrdenprodXProcXStrId(false, sysTarea.getStrId())) {
            TablaPopOrdenProduccion pop = new TablaPopOrdenProduccion(ordenprod);
            lstTablaPopOrdenProduccion.add(pop);
        }
    }

    private void cargarServXOrdenProd() {
        lstTablaPopServXOp.clear();
        for (PopServxop servxop : opsfb.getLstPopServxOp(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId())) {
            TablaPopServXOp tpsxo = new TablaPopServXOp();
            tpsxo.setPopServxop(servxop);
            lstTablaPopServXOp.add(tpsxo);
        }
    }

    private void cargarListaProductoXOrdenProd() {
        SysTarea sysTarea = pjsfb.getMssfb().getSysSegtareaActual().getStrId();
        lstTablaPopProdXServXOp.clear();
        for (PopProdxservxop prodxservxop : opsfb.getlstPopProdxservxopXOrdenProd(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId(), tablaPopOrdenProduccionSel.getPopOrdenprod().getStrId())) {
            TablaPopProdXServXOp tppxsxo = new TablaPopProdXServXOp();
            tppxsxo.setPopProdxservxop(prodxservxop);
            lstTablaPopProdXServXOp.add(tppxsxo);
        }
    }

//    private void cargarListaProductoXServicio() {
//        lstTablaPopProdXServXOp.clear();
//        for (PopProdxservxop prodxservxop : opsfb.getlstPopProdxservxopXServicio(tablaPopServXOpSel.getPopServxop().getVsrvId().getVsrvId())) {
//            TablaPopProdXServXOp tppxsxo = new TablaPopProdXServXOp();
//            tppxsxo.setPopProdxservxop(prodxservxop);
//            lstTablaPopProdXServXOp.add(tppxsxo);
//        }
//    }
//    private void cargarListaProductoXServ() {
//        lstTablaVntProdxsrv.clear();
//        for (VntProdxsrv prodxsrv : opsfb.getLstVntProdXServXOp(tablaPopServXOpSel.getPopServxop().getSxoId())) {
//            TablaVntProdxsrv tvpxs = new TablaVntProdxsrv();
//            tvpxs.setVntProdxsrv(prodxsrv);
//            lstTablaVntProdxsrv.add(tvpxs);
//        }
//    }
    private void cargarListaColXCrgXEstado() {
        lstTablaAdmCrgXCol.clear();
        for (AdmCrgxcol crgxcol : opsfb.getLstAdmCrgxcolXEstado(true)) {
            TablaAdmCrgXCol tacxc = new TablaAdmCrgXCol(crgxcol);
            lstTablaAdmCrgXCol.add(tacxc);
        }
    }

    private void cargarListaProductosVarios() {
        lstTablaProducto.clear();
        for (InvProducto producto : opsfb.getLstInvProductoXVarios(true)) {
            TablaProducto tp = new TablaProducto();
            tp.setInvProducto(producto);
            lstTablaProducto.add(tp);
        }
    }

    private void cargarListaServicioXVenta() {
        SysTarea sysTarea = pjsfb.getMssfb().getSysSegtareaActual().getStrId();
        lstTablaVntSrvXVenta.clear();
        VntRegistroventa vrv = opsfb.getLstVntRegistroventaXStrId(sysTarea.getStrId());
        if (vrv != null) {
            for (VntServxventa servxventa : opsfb.getLstVntServxventa(vrv.getRgvtId())) {
                TablaVntSrvXVenta tvsxv = new TablaVntSrvXVenta();
                tvsxv.setVntServxventa(servxventa);
                lstTablaVntSrvXVenta.add(tvsxv);
            }
        } else {
            mostrarError("Error al cargar proceso", 1);
        }
    }

    private void cargarRegVentaXPasoXEstVenta() {
        lstTablaVntRegistroventas.clear();
        for (VntRegistroventa vr : vsfb.getLstRegistroventaXPasoXEstVenta(31L, Boolean.TRUE, 3)) {
            TablaVntRegistroventa tvr = new TablaVntRegistroventa(vr);
            lstTablaVntRegistroventas.add(tvr);
        }
    }

    private void grabarOrdenProduccion() {
        if (validarForm()) {
            SysTarea sysTarea = pjsfb.getMssfb().getSysSegtareaActual().getStrId();
            List<PopProdxservxop> lstPopProdxservxopGrabar = new ArrayList<>();
            List<PopCxcevento> lstPopCxceventoGrabar = new ArrayList<>();
            PopOrdenprod pop = tablaPopOrdenProduccionSel.getPopOrdenprod();
            pop.setOprFechaproceso(new Date());
            pop.setFechacreacionop(new Date());
            pop.setOprProcesado(Boolean.TRUE);
            pop.setCxcId(pjsfb.getAdmCrgxcolActivo());
            pop = opsfb.editarOrdenProd(pop);

            for (PopServxop servxop : opsfb.getLstPopServxOp(tablaPopOrdenProduccionSel.getPopOrdenprod().getOprId())) {
                servxop.setSxoFechaproceso(new Date());
                servxop = opsfb.editarServXOrdenProd(servxop);
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
                opsfb.grabarLstProdxservxops(lstPopProdxservxopGrabar);
                opsfb.grabarLstCxceventos(lstPopCxceventoGrabar);
            }
            astslb.crearSeguimientoTareaPasoOrigenDest(sysTarea, 36L, 32L);
            mostrarError("Grabación exitosa, ir a despacho de evento...!", 3);
        } else {
            mostrarError("Error al grabar", 1);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnMostrarPanel = false;
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {

    }

    public void rowDtOrdenProd_ActionEvent(ActionEvent ae) {
        tablaPopOrdenProduccionSel = (TablaPopOrdenProduccion) ae.getComponent().getAttributes().get("tops");
        blnMostrarPanel = true;
        cargarServXOrdenProd();
        cargarListaProductoXOrdenProd();
        cargarListaColXCrgXEstado();
    }

    public void rowDtServXOrdenProd_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaPopServXOpSel = (TablaPopServXOp) map.get("tsxos");
    }

    public void btnGrabarOrdenProduccion_ActionEvent(ActionEvent ae) {
        grabarOrdenProduccion();
    }

    public void btnVolverOrdenProduccion_ActionEvent(ActionEvent ae) {
        blnMostrarPanel = false;
    }

    public void btnAgregarProductosVarios_ActionEvent(ActionEvent ae) {
        blnMostrarProductos = true;
        cargarListaProductosVarios();
    }

    public void btnAgregarProducto_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaProductoSel = (TablaProducto) map.get("tps");
        PopProdxservxop pxsxo = new PopProdxservxop();
//        pxsxo.setSxoId(tablaPopServXOpSel.getPopServxop());
        pxsxo.setPrdId(tablaProductoSel.getInvProducto());
        pxsxo.setPxsoCantprod(tablaProductoSel.getCantProds());
        pxsxo.setPxsoEstado(Boolean.TRUE);
        pxsxo.setPxsoEstadosalida(Boolean.FALSE);
        pxsxo.setPxsoEstadoentrada(Boolean.FALSE);
        pxsxo.setStrId(tablaPopOrdenProduccionSel.getPopOrdenprod().getStrId());
        pxsxo = opsfb.editarProdxservxop(pxsxo);
        cargarListaProductoXOrdenProd();
    }

//    public void btnAgregarProducto_ActionEvent(ActionEvent ae) {
//        Map map = ae.getComponent().getAttributes();
//        tablaProductoSel = (TablaProducto) map.get("tps");
//        PopProdxservxop pp = new PopProdxservxop();//Obtener el pp     
////        if (!lstTablaPopProdXServXOp.contains(new TablaPopProdXServXOp(pp))) {
////            
////        }
//        for (TablaPopProdXServXOp tpxsxo : lstTablaPopProdXServXOp) {
////            if (tpxsxo.getPopProdxservxop().getPrdId().getPrdId() == tablaProductoSel.getInvProducto().getPrdId()) {
////                tpxsxo.getPopProdxservxop().setPxsoCantprod(tablaProductoSel.getCantProds());
////              //  tpxsxo = opsfb.editarProdxservxop(tpxsxo.);
////            } else {
//            PopProdxservxop pxsxo = new PopProdxservxop();
//            pxsxo.setSxoId(tablaPopServXOpSel.getPopServxop());
//            pxsxo.setPrdId(tablaProductoSel.getInvProducto());
//            pxsxo.setPxsoCantprod(tablaProductoSel.getCantProds());
//            pxsxo.setPxsoEstado(Boolean.TRUE);
//            pxsxo.setPxsoEstadosalida(Boolean.FALSE);
//            pxsxo.setPxsoEstadoentrada(Boolean.FALSE);
//            pxsxo.setStrId(tablaPopServXOpSel.getPopServxop().getStrId());
//            pxsxo = opsfb.editarProdxservxop(pxsxo);
////            }
//            cargarListaProductoXOrdenProd();
//        }
//    }
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
        return true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">
    private OrdenProduccionSFBean lookupOrdenProduccionSFBean() {
        try {
            Context c = new InitialContext();
            return (OrdenProduccionSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/OrdenProduccionSFBean!com.pandora.mod.ordenprod.OrdenProduccionSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private VentaSFBean lookupVentaSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (VentaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/VentaSFBean!com.pandora.mod.venta.VentaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">

    
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
     * @return the lstTablaInvent
     */
    public List<TablaInvent> getLstTablaInvent() {
        return lstTablaInvent;
    }

    /**
     * @param lstTablaInvent the lstTablaInvent to set
     */
    public void setLstTablaInvent(List<TablaInvent> lstTablaInvent) {
        this.lstTablaInvent = lstTablaInvent;
    }

    /**
     * @return the tablaVntSrvXVentaSel
     */
    public TablaVntSrvXVenta getTablaVntSrvXVentaSel() {
        return tablaVntSrvXVentaSel;
    }

    /**
     * @param tablaVntSrvXVentaSel the tablaVntSrvXVentaSel to set
     */
    public void setTablaVntSrvXVentaSel(TablaVntSrvXVenta tablaVntSrvXVentaSel) {
        this.tablaVntSrvXVentaSel = tablaVntSrvXVentaSel;
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
}
