/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.icesoft.faces.context.effects.JavascriptContext;
import com.pandora.adm.cmp.CmpAprobarSolsSFBean;
import com.pandora.adm.cmp.CmpRecepPedidoSFBean;
import com.pandora.adm.cmp.FacturaSFBean;
import com.pandora.adm.dao.*;
import com.pandora.pagocuenta.bean.RevisaPagoCuentaSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.base.RecursosOut;
import com.pandora.web.procs.comp.cualif.CompApruebaSolQLF;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import utilidades.BinarioInforme;
//import org.primefaces.context.RequestContext;
//import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named
@CompApruebaSolQLF
public class CompAprbSolsJSFBean extends CompSolReqJSFBean implements IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    private TablaReqComp tablaReqCompSel = new TablaReqComp();
    private List<TablaInvent> lstTablaInvenXProd = new ArrayList<>();
    private List<TablaProdAprob> lstTablaProdAprobs = new ArrayList<>();
    private List<TablaProdAprob> lstProdAprobsGen = new ArrayList<>();
    private List<TablaConsolidadoInv> lstTablaConsolidadoInv = new ArrayList<>();
    private Integer intInvMarIdSel;
    private Integer intInvPspIdSel;
    private Integer intCantAprob;
    private Integer totalArpbXProd;
    private boolean blnSelTodoprodAprov = false;
    private List<TablaProdAprobNativa> lstTablaProdAprobNativas = new ArrayList<>();
    private List<TablaProdAprobNativa> lstTablaProdAprobNativasXSolicitud = new ArrayList<>();
    private String strEvtJsDialogo = "";
    private boolean renderConfirm = false;
    private List<TablaConsolCompra> lstTablaConsolCompra = new ArrayList<>();
    private List<TablaProdXReq> lstTablaProdXReq = new ArrayList<>();
    private List<TablaProdxProv> lstTablaProdxProv = new ArrayList<>();
    private TablaProdXReq tablaProdXReq = new TablaProdXReq();
    private List<TablaConsComp> lstConsComps = new ArrayList<>();
    private List<TablaConsPedido> lstConsPedidos = new ArrayList<>();
    private TablaConsComp tablaConsCompSel = new TablaConsComp();
    private TablaConsPedido tablaConsPedidoSel = new TablaConsPedido();
    private List<SelectItem> lstItemsCantidadSolicitada = new ArrayList<>();
    private boolean blnMostrarItemCantidadSelecionada = false;
    private List<TablaFactura> lstTablaFacturas = new ArrayList<>();
    private FacturaSFBean fsfb = lookupFacturaSFBeanBean();
    private TablaFactura tablaFacturaSel = new TablaFactura();
    private List<TablaDetalleFactura> lstTablaDetalleFactXFactura = new ArrayList<>();
    private boolean blnMostrarCerrarProceso;
    private com.icesoft.faces.context.Resource jasperResourceZip;
    private List<TablaFactura> lstTablaFactura = new ArrayList<>();

    private FacturaSFBean lookupFacturaSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (FacturaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/FacturaSFBean!com.pandora.adm.cmp.FacturaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    CmpRecepPedidoSFBean crpsfb;

    private CmpRecepPedidoSFBean lookupCmpRecepPedidoSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (CmpRecepPedidoSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/CmpRecepPedidoSFBean!com.pandora.adm.cmp.CmpRecepPedidoSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    CmpAprobarSolsSFBean cassfb;

    private CmpAprobarSolsSFBean lookupCmpAprobarSolsSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (CmpAprobarSolsSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/CmpAprobarSolsSFBean!com.pandora.adm.cmp.CmpAprobarSolsSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    RevisaPagoCuentaSFBean rpcsfb;

    private RevisaPagoCuentaSFBean lookupPagoCuentaSgcSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (RevisaPagoCuentaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/RevisaPagoCuentaSFBean!com.pandora.pagocuenta.bean.RevisaPagoCuentaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private List<TablaReqComp> lstTablaReqComps = new ArrayList<>();
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">

    @Override
    public void init() {
        super.init();
        cassfb = lookupCmpAprobarSolsSFBeanBean();
        crpsfb = lookupCmpRecepPedidoSFBeanBean();
        rpcsfb = lookupPagoCuentaSgcSFBeanBean();
        fsfb = lookupFacturaSFBeanBean();
        cargarSolsXEst();
        cargarListInventario();
        lstInvMarcas.clear();
        lstPresentprods.clear();
        lstTablaProdAprobs.clear();
        lstTablaProdSel.clear();
        lstTablaConsolCompra.clear();
//        lstTablaConsolidadoInv.clear();
        lstTablaDetalleFactXFactura.clear();
        lstTablaFacturas.clear();
        blnMostrarCerrarProceso = false;
    }

    @Override
    public void limpiarVariables() {
        super.limpiarVariables();
        cassfb.remove();
        crpsfb.remove();
        rpcsfb.remove();
        fsfb.remove();
        lstInvMarcas.clear();
        lstPresentprods.clear();
        lstTablaProdAprobs.clear();
        lstTablaProdSel.clear();
        lstTablaConsolCompra.clear();
//        lstTablaConsolidadoInv.clear();
        lstTablaDetalleFactXFactura.clear();
        lstTablaFacturas.clear();
        blnMostrarCerrarProceso = false;    
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">

    private void crearSegTareaSolicitudCompraFinanciera() {
        astslb.crearTareaInicioXProc("PRLOG02", 2, 4L);
    }

    private void cargarLstCantidadSolicitada(Integer cantidad) {
        lstItemsCantidadSolicitada.clear();
        for (int i = 0; i <= cantidad; i++) {
            lstItemsCantidadSolicitada.add(new SelectItem(i, "" + i));
        }
    }

    private void cargarLstTablaConsPedido() {
        lstConsPedidos.clear();
        for (CmpConspedido cc : cassfb.getLstConspedidosXTarea(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId())) {
            TablaConsPedido tcc = new TablaConsPedido(cc);
            lstConsPedidos.add(tcc);
        }
    }

    private void cargarLstTablaConsCompra() {
        lstConsComps.clear();
        for (CmpConsolcompra cc : cassfb.getLstCmpConsolcompraXTarea(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId())) {
            TablaConsComp tcc = new TablaConsComp(cc);
            lstConsComps.add(tcc);
        }
    }

    private void cargarLstProdAprob() {
        lstTablaProdAprobNativas.clear();
        for (Object prodAprob : cassfb.getLstConsolidadoProdAprobXStrId(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId())) {
            Object[] o = (Object[]) prodAprob;
            TablaProdAprobNativa tablaProdAprob = new TablaProdAprobNativa();
            tablaProdAprob.setPrdId((Integer) o[0]);
            tablaProdAprob.setPrdNombre((String) o[1]);
            tablaProdAprob.setPspId((Integer) o[2]);
            tablaProdAprob.setPspNombre((String) o[3]);
            tablaProdAprob.setMarId((Integer) o[4]);
            tablaProdAprob.setMarNombre((String) o[5]);
            Long cantAprobada = (Long) o[6];
            Long cantInventario = cassfb.cantidadInvXPrdXPspXMar(tablaProdAprob.getPrdId(), tablaProdAprob.getPspId(), tablaProdAprob.getMarId());
            if (cantInventario==null) {
                cantInventario=0L;
            }
            Long cantAComprar = 0L;
            if (cantInventario.intValue() >= cantAprobada.intValue()) {
                cantInventario = cantAprobada;
            } else {
                cantAComprar = cantAprobada - cantInventario;
            }
            tablaProdAprob.setCantAprob(cantAprobada);
            tablaProdAprob.setCantInventario(cantInventario);
            tablaProdAprob.setCantAComprar(cantAComprar);
            lstTablaProdAprobNativas.add(tablaProdAprob);
            //  pjsfb.getColxempLog()
        }
    }

    private void cargarLstProdAprobXSolicitud() {
        lstTablaProdAprobNativasXSolicitud.clear();
        totalArpbXProd = 0;
        for (Object prodAprob : cassfb.getLstConsolidadoProdAprobXCrqId(tablaReqCompSel.getCmpRequiscomp().getCrqId())) {
            Object[] o = (Object[]) prodAprob;
            TablaProdAprobNativa tablaProdAprob = new TablaProdAprobNativa();
            tablaProdAprob.setPrdId((Integer) o[0]);
            tablaProdAprob.setPrdNombre((String) o[1]);
            tablaProdAprob.setPspId((Integer) o[2]);
            tablaProdAprob.setPspNombre((String) o[3]);
            tablaProdAprob.setMarId((Integer) o[4]);
            tablaProdAprob.setMarNombre((String) o[5]);
            Long cantAprovada = (Long) o[6];
            Long cantInventario = cassfb.cantidadInvXPrdXPspXMar(tablaProdAprob.getPrdId(), tablaProdAprob.getPspId(), tablaProdAprob.getMarId());
            Long cantAComprar = 0L;
            if (cantInventario.intValue() >= cantAprovada.intValue()) {
                cantInventario = cantAprovada;
            } else {
                cantAComprar = cantAprovada - cantInventario;
            }
            tablaProdAprob.setCantAprob(cantAprovada);
            tablaProdAprob.setCantInventario(cantInventario);
            tablaProdAprob.setCantAComprar(cantAComprar);
            lstTablaProdAprobNativasXSolicitud.add(tablaProdAprob);
            totalArpbXProd = +cantAprovada.intValue();
            //  pjsfb.getColxempLog()
        }
    }

    private void cargarSolsXEst() {
        lstTablaReqComps.clear();
        for (CmpRequiscomp cmpRequiscomp : cassfb.getLstReqsXEst(Boolean.FALSE)) {
            if (cmpRequiscomp.getStrId() == null) {
                cmpRequiscomp.setStrId(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId());
                cmpRequiscomp = cassfb.editarRequisicion(cmpRequiscomp);
            }
            TablaReqComp tablaReqComp = new TablaReqComp(cmpRequiscomp);
            lstTablaReqComps.add(tablaReqComp);
        }
    }

    private void resetAsig() {
        intInvMarIdSel = -1;
        intInvPspIdSel = -1;
        intCantAprob = null;
    }

    private boolean valiadarConsolidadoCompraXTarea(Long tarea) {
        Long cantidaConsolidado = cassfb.getCantidadConsolidadoCompra(tarea);
        if (cantidaConsolidado.intValue() == 0) {
            return false;
        }
        return true;
    }

    private boolean valiadarConsolidadoXTarea(Long tarea) {
        Long cantidaConsolidado = cassfb.getCantidadConsolidadoAprobado(tarea);
        if (cantidaConsolidado.intValue() == 0) {
            return false;
        }
        return true;
    }

    private boolean validarSolGest() {
        for (CmpProdxreq cmpProdxreq : csrsfb.getLstCmpProdxreq(tablaReqCompSel.getCmpRequiscomp().getCrqId())) {
            if (cassfb.getCantPxrNoAsigNoRech(cmpProdxreq.getPxrId()) == 0 && !cmpProdxreq.getPxrRechaza()) {
                return false;
            }
        }
        return true;
    }

    private void cargarProdXReq() {
        lstTablaProdSel.clear();
        for (CmpProdxreq cmpProdxreq : csrsfb.getLstCmpProdxreq(tablaReqCompSel.getCmpRequiscomp().getCrqId())) {
            TablaProdXReq prodXReq = new TablaProdXReq(cmpProdxreq);
            //   prodXReq.setLstInvXProd(cassfb.getLstInvInventXProd(cmpProdxreq.getPrdId().getPrdId()));

            for (InvMarcxprod invMarcxprod : csrsfb.getLstMarcxprod(cmpProdxreq.getPrdId().getPrdId(), Boolean.TRUE)) {
                //  lstInvMarcas.add(invMarcxprod.getMarId());

                //prodXReq.getLstInvMarcas().add(   invMarcxprod.getMarId());
                prodXReq.getLstItemsMarcas().clear();
                prodXReq.getLstItemsMarcas().add(itemSeleccioneInt);
                prodXReq.getLstItemsMarcas().add(new SelectItem(invMarcxprod.getMarId().getMarId(), invMarcxprod.getMarId().getMarNombre()));
            }
            for (InvPresntxprod invPresntxprod : csrsfb.getLstPresntxprod(cmpProdxreq.getPrdId().getPrdId(), Boolean.TRUE)) {
                // lstPresentprods.add(invPresntxprod.getPspId());
                // prodXReq.getLstPresentprod().add(invPresntxprod.getPspId());//
                prodXReq.getLstItemsPresentacionprod().clear();
                prodXReq.getLstItemsPresentacionprod().add(itemSeleccioneInt);
                prodXReq.getLstItemsPresentacionprod().add(new SelectItem(invPresntxprod.getPspId().getPspId(), invPresntxprod.getPspId().getPspNombre()));

            }

            prodXReq.setCantProdInv(cassfb.getCantProdXPrdId(cmpProdxreq.getPrdId().getPrdId()));
            if (cassfb.getCantPxrNoAsigNoRech(cmpProdxreq.getPxrId()) == 0 && !cmpProdxreq.getPxrRechaza()) {
                prodXReq.setEstiloFila("filaPendiente");
            } else {
                prodXReq.setEstiloFila("");
            }
            lstTablaProdSel.add(prodXReq);
        }
    }

    private void cargarInvXProd() {
        lstTablaInvenXProd.clear();
        for (InvInvent invInvent : cassfb.getLstInvInventXProd(tablaProdXReqSel.getCmpProdxreq().getPrdId().getPrdId())) {
            TablaInvent ti = new TablaInvent(invInvent);
            lstTablaInvenXProd.add(ti);
        }
    }

    private void cargarListInventario() {
        lstTablaConsolidadoInv.clear();
        for (Object[] obj : cassfb.getLstInvInventXProdXPsp()) {
            TablaConsolidadoInv tci = new TablaConsolidadoInv();
            tci.setPrdNombre((String) obj[0]);
            tci.setPspNombre((String) obj[1]);
            tci.setInvCant((Long) obj[2]);
            tci.setPrdypsp(tci.getPrdNombre() + tci.getPspNombre());
            lstTablaConsolidadoInv.add(tci);
        }
    }

    public void cargarProdXReqAprobados() {
        lstTablaProdAprobs.clear();
        for (CmpPxraprob pxraprob : cassfb.getLstCmpPxraprobsXCrq(tablaReqCompSel.getCmpRequiscomp().getCrqId())) {
            TablaProdAprob tpa = new TablaProdAprob(pxraprob);
            lstTablaProdAprobs.add(tpa);
        }
    }
    //<editor-fold defaultstate="collapsed" desc="Facturas">

    private void cargarTablaPrefacturasGen() {
        lstTablaFacturas.clear();
        for (CmpFactura cmpFactura : fsfb.getLstCmpFacturasXPrefactXTarea(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId(), Boolean.TRUE)) {
            TablaFactura tf = new TablaFactura(cmpFactura);
            lstTablaFacturas.add(tf);
        }
    }

    private void cargarDetalleFactXFactura() {
        lstTablaDetalleFactXFactura.clear();
        for (CmpDetallefact detallefact : fsfb.getLstCmpDetallefacts(tablaFacturaSel.getCmpFactura().getFactId())) {
            TablaDetalleFactura tdf = new TablaDetalleFactura(detallefact);
            lstTablaDetalleFactXFactura.add(tdf);
        }
    }
    
    private void cargarListaFacturas() {
        lstTablaFactura.clear();
        for (CmpFactura cmpFactura : fsfb.getLstCmpFacturasXPrefactXTareaConDetPendiente(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId(), true)) {
            cmpFactura.setFactValorbruto(cmpFactura.getFactPrevalorbruto());
            TablaFactura tf = new TablaFactura(cmpFactura);
            getLstTablaFactura().add(tf);
        }
    }
    
    public void btnGrabarFactura_ActionEvent(ActionEvent ae) {
        tablaFacturaSel = (TablaFactura) ae.getComponent().getAttributes().get("tfc");        
        fsfb.editarFactura(tablaFacturaSel.getCmpFactura());
    }

    /**
     * Cargar detalle factura por tarea de la factura
     */
    private void cargarDetalleFacturaXtareaFact() {
        lstTablaDetalleFactXFactura.clear();
        for (CmpDetallefact detallefact : fsfb.getLstCmpDetallefactsXTarea(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId())) {
            TablaDetalleFactura tdf = new TablaDetalleFactura(detallefact);
            lstTablaDetalleFactXFactura.add(tdf);
        }

    }
    //</editor-fold>
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">

    public void rowDtFacturaCompra_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaFacturaSel = (TablaFactura) map.get("tfc");
        cargarDetalleFactXFactura();
    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        int panelNum = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        if (panelNum != 4) {
            if (pjsfb.getMssfb().getSysSegtareaActual().getSgtEstpaso() != null) {
                if (!pjsfb.getMssfb().getSysSegtareaActual().getSgtEstpaso().booleanValue()) {
                    resumenMsg = "Cerrar proceso";
                    mensaje = "El Proceso se cerro no se puede modificar";
                    cargarMsg(FacesMessage.SEVERITY_ERROR);
                    panelNum = 4;
                }
            }
        }

        if (numPanel != panelNum) {
            numPanel = panelNum;
            lstProdAprobsGen.clear();
            lstTablaProdxProv.clear();
            lstItemsCantidadSolicitada.clear();
            if (numPanel == 2) {
                cargarLstProdAprob();
            }
            if (numPanel == 3) {
                tablaConsPedidoSel = null;
                cargarLstTablaConsPedido();
                cargarLstTablaConsCompra();


            }
            if (numPanel == 4) {
                cargarLstTablaConsCompra();
                //cargarTablaPrefacturasGen();
                cargarDetalleFacturaXtareaFact();
                cargarListaFacturas();
            }
        }
    }

    public void dtProdAprXProv_RowSel(ActionEvent ae) {
        resetearInputs();
        tablaConsPedidoSel = (TablaConsPedido) ae.getComponent().getAttributes().get("itemConsPedido");
        resaltarFilaTabla(lstConsPedidos, tablaConsPedidoSel);

        cargarProdXProv();
    }

    private void cargarProdXProv() {
        lstTablaProdxProv.clear();
        cargarLstCantidadSolicitada(tablaConsPedidoSel.getCmpConspedido().getCcpCantcomprar());
        for (InvProdxprov ip : cassfb.getLstInvProdxprov(tablaConsPedidoSel.getCmpConspedido().getPrdId().getPrdId())) {
            TablaProdxProv prodxProv = new TablaProdxProv(ip);
            prodxProv.setCantidad(0);
            lstTablaProdxProv.add(prodxProv);
        }
//        if (!lstTablaProdxProv.isEmpty()) {
//            blnMostrarItemCantidadSelecionada = true;
//            if (lstTablaProdxProv.size() == 1) {
//                lstTablaProdxProv.get(0).setCantidad(tablaConsPedidoSel.getCmpConspedido().getCcpCantcomprar());
//                blnMostrarItemCantidadSelecionada = false;
//            }
//        }
    }

    public void dtProdAprovXReq_ValueChange(ValueChangeEvent vce) {
        blnSelTodoprodAprov = (boolean) vce.getNewValue();
        selTodoLst(lstTablaProdAprobs, blnSelTodoprodAprov);
    }

    public void btnRetProd_ActionEvent(ActionEvent ae) {
        List<TablaProdAprob> lstProdAprobsRetirar = retirarElemTabla(lstTablaProdAprobs);
        List<CmpPxraprob> lstPxraprobsBorrar = new ArrayList<>();
        for (TablaProdAprob tablaProdAprob : lstProdAprobsRetirar) {
            CmpPxraprob cp = tablaProdAprob.getCmpPxraprob();
            lstPxraprobsBorrar.add(cp);
        }
        cassfb.borrarProdAprob(lstPxraprobsBorrar);
    }

    public void btnAsigProd_ActionEvent(ActionEvent ae) {
        tablaProdXReqSel = (TablaProdXReq) ae.getComponent().getAttributes().get("itemSel");
        if (validarForm()) {
            if (tablaProdXReqSel.getCmpProdxreq().getPxrRechaza()) {
                mostrarError("Solicitud de producto rechazada", 1);
//                resumenMsg = "Rechazo";
//                mensaje = "Solicitud de producto rechazada";
//                cargarMsg(FacesMessage.SEVERITY_INFO);
//                return;
            }
            CmpPxraprob cmpPxraprob = new CmpPxraprob();
            cmpPxraprob.setPxrId(tablaProdXReqSel.getCmpProdxreq());
            cmpPxraprob.setMarId(new InvMarca(tablaProdXReqSel.getMarcaSel()));
            cmpPxraprob.setPspId(new InvPresentprod(tablaProdXReqSel.getPresentSel()));
            cmpPxraprob.setCantAprob(tablaProdXReqSel.getCantProds());
            cmpPxraprob.setStrId(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId());
            cassfb.grabarProdAprob(cmpPxraprob);
            cargarProdXReqAprobados();
            // cargarLstProdAprobXSolicitud();
//            mostrarError("Solicitud actualizada...!", 3);
//            resumenMsg = "Actualización";
//            mensaje = "Solicitud actualizada";
//            cargarMsg(FacesMessage.SEVERITY_INFO);
            //   JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "msgGrabExitoso.show();");
        }
    }

    public void dtResSolicitudes_RowSel(ActionEvent se) {
        tablaReqCompSel = (TablaReqComp) se.getComponent().getAttributes().get("itemSel");
        resaltarFilaTabla(lstTablaReqComps, tablaReqCompSel);
        cargarProdXReq();
        lstTablaProdAprobs.clear();
        lstTablaInvenXProd.clear();
        resetAsig();
        cargarProdXReqAprobados();
        cargarLstProdAprobXSolicitud();
        tablaProdXReqSel.setCantProdInv(null);

    }

    public void dtProdXReqInv_RowSel(ActionEvent e) {
        tablaProdXReqSel = (TablaProdXReq) e.getComponent().getAttributes().get("itemSel");
        resaltarFilaTabla(lstTablaProdSel, tablaProdXReqSel);
        lstInvMarcas.clear();
        lstPresentprods.clear();
//        for (InvMarcxprod invMarcxprod : csrsfb.getLstMarcxprod(tablaProdXReqSel.getCmpProdxreq().getPrdId().getPrdId(), Boolean.TRUE)) {
//            lstInvMarcas.add(invMarcxprod.getMarId());
//            tablaProdXReqSel.getLstInvMarcas().add(invMarcxprod.getMarId());
//        }
//        for (InvPresntxprod invPresntxprod : csrsfb.getLstPresntxprod(tablaProdXReqSel.getCmpProdxreq().getPrdId().getPrdId(), Boolean.TRUE)) {
//            lstPresentprods.add(invPresntxprod.getPspId());
//            tablaProdXReqSel.getLstPresentprod().add(invPresntxprod.getPspId());
//
//        }
//        cargarInvXProd();
//        cargarListInventario();
        cargarProdXReqAprobados();
        resetAsig();
    }

    public void btnConfRecz_ActionEvent(ActionEvent ae) {
        cassfb.actualizarPxrRechaza(tablaProdXReqSel.getCmpProdxreq().getPxrId(),
                renderConfirm, tablaProdXReqSel.getCmpProdxreq().getPxrObsrrechza());
        if (!lstTablaProdAprobs.isEmpty()) {
            List<CmpPxraprob> lstPxraprobsBorrar = new ArrayList<>();
            for (TablaProdAprob tablaProdAprob : lstTablaProdAprobs) {
                CmpPxraprob cp = tablaProdAprob.getCmpPxraprob();
                lstPxraprobsBorrar.add(cp);
            }
            cassfb.borrarProdAprob(lstPxraprobsBorrar);
            lstTablaProdAprobs.clear();
        }
        cargarProdXReq();
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "confirmation.hide();");
    }

    public void btnConfDeclinar_ActionEvent(ActionEvent ae) {
        tablaProdXReqSel.getCmpProdxreq().setPxrRechaza(Boolean.FALSE);
    }

    public void dtdtProdXReqInvChkRechzr(ValueChangeEvent vce) {
        tablaProdXReqSel = (TablaProdXReq) vce.getComponent().getAttributes().get("tablaProdXReqSel");
        renderConfirm = (Boolean) vce.getComponent().getAttributes().get("value");
        if (renderConfirm) {
            JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "confirmation.show()");
        }
    }

    public void btnEliminarConsolidadoRequisiciones_ActionEvent(ActionEvent ae) {
        Long tarea = pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId();
        if (this.valiadarConsolidadoXTarea(tarea)) {
            if (this.valiadarConsolidadoCompraXTarea(tarea)) {
                mostrarError("Existen ordenes de compra para este concolidado. No se puede eliminar.", 1);
//                resumenMsg = "Eliminación consolidado";
//                mensaje = "Se generaron ordenes de compra para este consolidado no se puede eliminar";
//                cargarMsg(FacesMessage.SEVERITY_INFO);
            } else {
                int cantRegEliminados = cassfb.eliminarConsolidadoPedido(tarea);
                if (cantRegEliminados == -1) {
                    mostrarError("Error al eliminar", 1);
//                    resumenMsg = "Eliminación consolidado";
//                    mensaje = "Error Eliminación";
//                    cargarMsg(FacesMessage.SEVERITY_INFO);
                } else {
                    if (cantRegEliminados == 0) {
                        mostrarError("No se eliminó ningún registro", 3);
//                        resumenMsg = "Eliminación consolidado";
//                        mensaje = "No se eliminó ningún registro";
//                        cargarMsg(FacesMessage.SEVERITY_WARN);
                    } else {
                        mostrarError("Eliminación exitosa...!", 3);
//                        resumenMsg = "Grabación Eliminación";
//                        mensaje = cantRegEliminados + " Eliminados exitosamente";
//                        cargarMsg(FacesMessage.SEVERITY_INFO);
                    }
                }
            }

        } else {
            mostrarError("Consolidado no aprobado", 1);
//            resumenMsg = "Eliminación consolidado";
//            mensaje = "No hay consolidado aprobado";
//            cargarMsg(FacesMessage.SEVERITY_INFO);
        }
    }

    public void btnGrabarConsolidadoRequisiciones_ActionEvent(ActionEvent ae) {
        cargarLstProdAprob();
        if (lstTablaProdAprobNativas.isEmpty()) {
            mostrarError("No hay requisiciones aprobadas", 1);
//            resumenMsg = "Grabar consolidado";
//            mensaje = "No hay requisiciones aprovadas";
//            cargarMsg(FacesMessage.SEVERITY_INFO);
        } else {
            if (this.validarSolGest()) {
                Long tarea = pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId();
                if (this.valiadarConsolidadoXTarea(tarea)) {
                    mostrarError("Consolidado aprobado...!", 3);
//                    resumenMsg = "Grabación consolidado";
//                    mensaje = "Consolidado esta aprobado";
//                    cargarMsg(FacesMessage.SEVERITY_INFO);
                } else {
                    StringBuilder strBsql = new StringBuilder();
                    strBsql.append(" INSERT INTO cmp_conspedido( ");
                    strBsql.append(" str_id, prd_id, psp_id, mar_id, ccp_cantaprob, ccp_cantinv,  ");
                    strBsql.append(" ccp_cantcomprar,  cpe_id )");
                    strBsql.append(" VALUES ");
                    Integer usuario = pjsfb.getMssfb().getColxempLog().getCpeId();
                    for (TablaProdAprobNativa tb : lstTablaProdAprobNativas) {
                        strBsql.append("(");
                        strBsql.append(tarea);
                        strBsql.append(",");
                        strBsql.append(tb.getPrdId());
                        strBsql.append(",");
                        strBsql.append(tb.getPspId());
                        strBsql.append(",");
                        strBsql.append(tb.getMarId());
                        strBsql.append(",");
                        strBsql.append(tb.getCantAprob());
                        strBsql.append(",");
                        strBsql.append(tb.getCantInventario());
                        strBsql.append(",");
                        strBsql.append(tb.getCantAComprar());
                        strBsql.append(",");
                        strBsql.append(usuario);
                        strBsql.append("),");
                    }
                    strBsql.replace(strBsql.length() - 1, strBsql.length(), "");
                    int cantRegInsertdados = cassfb.grabarConsolidadoPedido(strBsql.toString(), tarea);
                    if (cantRegInsertdados == -1) {
                        mostrarError("Error al grabar", 1);
//                        resumenMsg = "Grabación consolidado";
//                        mensaje = "Error Grabación";
//                        cargarMsg(FacesMessage.SEVERITY_INFO);
                    } else {
                        if (cantRegInsertdados == 0) {
                            mostrarError("No se grabo ningún registro", 1);
//                            resumenMsg = "Grabación consolidado";
//                            mensaje = "No se grabó ningún registro";
//                            cargarMsg(FacesMessage.SEVERITY_WARN);
                        } else {

                            Long sumCantidadAComprar = 0L;
                            for (TablaProdAprobNativa aprobNativa : lstTablaProdAprobNativas) {
                                sumCantidadAComprar = sumCantidadAComprar + aprobNativa.getCantAComprar();
                            }
                            if (sumCantidadAComprar == 0L) {
                                astslb.crearSeguimientoTareaPasoOrigenDest(pjsfb.getMssfb()
                                        .getSysSegtareaActual().getStrId(), 2L, 3L);
                                mostrarError("Grabación exitosa...!\n Ir a despacho", 3);
                            } else {
                                mostrarError("Grabación exitosa...!", 3);
                            }


                        }
                    }
                }
            } else {
                mostrarError("Existen requisiciones pendientes por revisión", 1);
//                resumenMsg = "Grabación consolidado";
//                mensaje = "Hay requisiciones pendientes de revisión";
//                cargarMsg(FacesMessage.SEVERITY_INFO);
            }
        }
    }

    public void btnGrabarConsolidadoCompras_ActionEvent(ActionEvent ae) {
        if (lstTablaProdxProv.isEmpty()) {
            mostrarError("Debe seleccionar un producto", 1);
        } else {
            if (tablaConsPedidoSel != null) {
                List<CmpConsolcompra> lstCC = new ArrayList<>();
                int sumCantidadPedido = 0;
                for (TablaProdxProv tpp : lstTablaProdxProv) {
                    if (tpp.getCantidad() > 0) {
                        sumCantidadPedido += tpp.getCantidad();
                        CmpConsolcompra consolcompra = new CmpConsolcompra();
                        consolcompra.setCcmCant(tpp.getCantidad());
                        consolcompra.setCcmFecproc(new java.util.Date());
                        consolcompra.setCcmPrecio(tpp.getInvProdxprov().getPxvPrecioact());
                        consolcompra.setCcpId(tablaConsPedidoSel.getCmpConspedido());
                        consolcompra.setMarId(tablaConsPedidoSel.getCmpConspedido().getMarId());
                        consolcompra.setPrdId(tablaConsPedidoSel.getCmpConspedido().getPrdId());
                        consolcompra.setPrvId(tpp.getInvProdxprov().getPrvId());
                        consolcompra.setPspId(tablaConsPedidoSel.getCmpConspedido().getPspId());
                        consolcompra.setStrId(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId());
                        consolcompra.setFpgId(tpp.getInvProdxprov().getPrvId().getFpgId());
                        consolcompra.setCxcId(pjsfb.getAdmCrgxcolActivo());
                        consolcompra.setCcmPreciototal(tpp.getInvProdxprov().getPxvPrecioact().multiply(new BigDecimal(tpp.getCantidad().intValue())));
                        lstCC.add(consolcompra);
                    }
                }
                if (sumCantidadPedido > 0) {
//                    Integer cantPed = tablaConsPedidoSel.getCmpConspedido().getCcpCantcomprar();
                    cassfb.grabarConsolidadoCompras(lstCC);

                    cargarLstTablaConsPedido();
                    cargarLstTablaConsCompra();
                    lstTablaProdxProv.clear();
                    if (lstConsPedidos.isEmpty()) {
                        cassfb.grabarFacturaXConsCompraXTarea(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId(), getPrincipalJSFBean().getAdmCrgxcolActivo());
                        cargarDetalleFacturaXtareaFact();
                        numPanel = 4;
                    }

                } else {
                    mostrarError("Debe diligenciar la cantidad", 1);
                }
            }
        }
    }

    public void btnGenInfOrdenCompra_ACtionEvent(ActionEvent ae) {
        try {
            List<BinarioInforme> lstBinarioInformes = new ArrayList<>();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            try (Connection con = jdbcProcAud.getConnection()) {
                for (TablaDetalleFactura tf : lstTablaDetalleFactXFactura) {

                    HashMap hmParametros = new HashMap();
                    hmParametros.put("p_factId", tf.getCmpDetallefact().getFactId().getFactId());
                    hmParametros.put("SUBREPORT_DIR", ec.getRealPath("/WEB-INF/classes/")
                            + File.separator + "reportes" + File.separator + "cmp" + File.separator);

                    InputStream reporteStreamFer = ec.getResourceAsStream("/WEB-INF/classes/"
                            + "reportes" + File.separator + "cmp" + File.separator + "OrdenPedido.jasper");

                    JasperPrint jp = JasperFillManager.fillReport(reporteStreamFer, hmParametros, con);
                    ByteArrayOutputStream outputStreamfer = new ByteArrayOutputStream();
                    JasperExportManager.exportReportToPdfStream(jp, outputStreamfer);
                    BinarioInforme binarioInforme = new BinarioInforme(outputStreamfer, tf.getCmpDetallefact().getFactId().getFactId() + ".pdf");
                    lstBinarioInformes.add(binarioInforme);

                }

                if (generarZipLstPdf(lstBinarioInformes)) {
                    jasperResourceZip = new RecursosOut("ÓrdenesCompra", baosManejoZip);
                    blnMostrarCerrarProceso = true;
                }
            }
        } catch (SQLException | JRException ex) {
            Logger.getLogger(CompAprbSolsJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            blnMostrarCerrarProceso = false;
        }


    }

    public void btnGrabarCerrarProceso_ActionEvent(ActionEvent ae) {

        //Grabar paso recepción compra inventario
        astslb.crearSeguimientoTareaPasoOrigenDest(pjsfb.getMssfb().getSysSegtareaActual().getStrId(), 2L, 10L);
        mostrarError("Grabacion exitosa", 3);
//                numPanel = 4;
        //Crear la tarea en finaciera
        //crearSegTareaSolicitudCompraFinanciera();
        // crpsfb.grabarEntregaPedidoXTarea(pjsfb.getMssfbl().getSysSegtareaActual().getStrId().getStrId());
//        pjsfb.getMssfbl().setSysSegtareaActual(cassfb.getSysSegTareaXId(pjsfb.getMssfbl().getSysSegtareaActual().getSgtId()));
//        if (pjsfb.getMssfbl().getSysSegtareaActual().getSgtEstpaso() != null) {
//            if (!pjsfb.getMssfbl().getSysSegtareaActual().getSgtEstpaso().booleanValue()) {
//                //Grabar cuentas a partir de consolidado de compra
////                List<FinCuenta> lstFinCuentaGrabar = new ArrayList<>();
////                for (TablaFactura factura : lstTablaFacturas) {
////                    FinCuenta fcGrabar = new FinCuenta();
////                    fcGrabar.setFactId(factura.getCmpFactura());
////                    fcGrabar.setCtaFechaproceso(new Date());
////                    fcGrabar.setCtaRevisado(Boolean.FALSE);
////                    lstFinCuentaGrabar.add(fcGrabar);
////
////                }
////                rpcsfb.grabarLstCuentasXOrdenCompra(lstFinCuentaGrabar);
////                    for (TablaConsolCompra cmpConsolcompra : lstTablaConsolCompra) {
//
////                        fcGrabar.setCcmId(cmpConsolcompra);
////                        fcGrabar.setCtaFechaproceso(new Date());
////                        fcGrabar.setCtaRevisado(Boolean.FALSE);
////                        lstFinCuentaGrabar.add(fcGrabar);
////                    }//
////                    pcssfb.grabarLstCuentasXOrdenCompra(lstFinCuentaGrabar);
//
//                mostrarError("Grabacion exitosa, se han generado las ordenes de compra", 3);
//                numPanel = 4;
//            }
//        }
//        cargarLstTablaConsPedido();
//        if (lstConsPedidos.isEmpty()) {
//            cassfb.crearSeguimientoTareaPasoMovimientoInventario(pjsfb.getMssfbl().getSysSegtareaActual().getStrId());
//            mostrarError("Grabación exitosa...!", 3);
////            resumenMsg = "Cerrar proceso";
////            mensaje = "Paso enviado exitosamente";
////            cargarMsg(FacesMessage.SEVERITY_INFO);
//            pjsfb.getMssfbl().setSysSegtareaActual(cassfb.getSysSegTareaXId(pjsfb.getMssfbl().getSysSegtareaActual().getSgtId()));
//        } else {
//            mostrarError("No existe orden de compra", 1);
////            resumenMsg = "Cerrar proceso";
////            mensaje = "Falta de generación de orden de compra";
////            cargarMsg(FacesMessage.SEVERITY_INFO);
//        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">

    @Override
    public boolean grabarPaso() {
        return true;
    }

    @Override
    public boolean validarForm() {
//         converterMessage="Debe digitar la cantidad"
//         validatorMessage="La cantidad debe estar entre 1 y 5000"
//         required="true" requiredMessage="Valor cantidad obligatorio"
        mensaje = "";
        resumenMsg = "";
        boolean valida = true;
        if (tablaProdXReqSel.getCantProds() == null) {
            mostrarError("Diligencie la cantidad", 1);

            return false;
        }
        if (tablaProdXReqSel.getCantProds() < 1 && tablaProdXReqSel.getCantProds() > 5000) {
            mensaje += "La cantidad debe estar entre 1 y 5000";
            valida = false;
        }
        if (tablaProdXReqSel.getMarcaSel().equals(-1)) {
            mensaje += "Seleccione la marca";
            valida = false;
        }

        if (tablaProdXReqSel.getPresentSel().equals(-1)) {
            mensaje += ", seleccione la presentación\n";
            valida = false;
        }
        if (valida == false) {
            mensaje += ": No se ha grabado el dato";
            resumenMsg = "Error de validación";
            mostrarError(mensaje);
        }

        return valida;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
    /**
     * @return the lstTablaReqComps
     */
    public List<TablaReqComp> getLstTablaReqComps() {
        return lstTablaReqComps;
    }

    /**
     * @param lstTablaReqComps the lstTablaReqComps to set
     */
    public void setLstTablaReqComps(List<TablaReqComp> lstTablaReqComps) {
        this.lstTablaReqComps = lstTablaReqComps;
    }

    /**
     * @return the tablaReqCompSel
     */
    public TablaReqComp getTablaReqCompSel() {
        return tablaReqCompSel;
    }

    /**
     * @param tablaReqCompSel the tablaReqCompSel to set
     */
    public void setTablaReqCompSel(TablaReqComp tablaReqCompSel) {
        this.tablaReqCompSel = tablaReqCompSel;
    }

    /**
     * @return the lstTablaInvenXProd
     */
    public List<TablaInvent> getLstTablaInvenXProd() {
        return lstTablaInvenXProd;
    }

    /**
     * @param lstTablaInvenXProd the lstTablaInvenXProd to set
     */
    public void setLstTablaInvenXProd(List<TablaInvent> lstTablaInvenXProd) {
        this.lstTablaInvenXProd = lstTablaInvenXProd;
    }

    /**
     * @return the lstTablaProdAprobs
     */
    public List<TablaProdAprob> getLstTablaProdAprobs() {
        return lstTablaProdAprobs;
    }

    /**
     * @param lstTablaProdAprobs the lstTablaProdAprobs to set
     */
    public void setLstTablaProdAprobs(List<TablaProdAprob> lstTablaProdAprobs) {
        this.lstTablaProdAprobs = lstTablaProdAprobs;
    }

    /**
     * @return the intInvMarIdSel
     */
    public Integer getIntInvMarIdSel() {
        return intInvMarIdSel;
    }

    /**
     * @param intInvMarIdSel the intInvMarIdSel to set
     */
    public void setIntInvMarIdSel(Integer intInvMarIdSel) {
        this.intInvMarIdSel = intInvMarIdSel;
    }

    /**
     * @return the intInvPspIdSel
     */
    public Integer getIntInvPspIdSel() {
        return intInvPspIdSel;
    }

    /**
     * @param intInvPspIdSel the intInvPspIdSel to set
     */
    public void setIntInvPspIdSel(Integer intInvPspIdSel) {
        this.intInvPspIdSel = intInvPspIdSel;
    }

    /**
     * @return the intCantAprob
     */
    public Integer getIntCantAprob() {
        return intCantAprob;
    }

    /**
     * @param intCantAprob the intCantAprob to set
     */
    public void setIntCantAprob(Integer intCantAprob) {
        this.intCantAprob = intCantAprob;
    }

    /**
     * @return the blnSelTodoprodAprov
     */
    public boolean isBlnSelTodoprodAprov() {
        return blnSelTodoprodAprov;
    }

    /**
     * @param blnSelTodoprodAprov the blnSelTodoprodAprov to set
     */
    public void setBlnSelTodoprodAprov(boolean blnSelTodoprodAprov) {
        this.blnSelTodoprodAprov = blnSelTodoprodAprov;
    }

    /**
     * @return the lstProdAprobsGen
     */
    public List<TablaProdAprob> getLstProdAprobsGen() {
        return lstProdAprobsGen;
    }

    /**
     * @param lstProdAprobsGen the lstProdAprobsGen to set
     */
    public void setLstProdAprobsGen(List<TablaProdAprob> lstProdAprobsGen) {
        this.lstProdAprobsGen = lstProdAprobsGen;
    }

    /**
     * @return the lstTablaProdAprobNativas
     */
    public List<TablaProdAprobNativa> getLstTablaProdAprobNativas() {
        return lstTablaProdAprobNativas;
    }

    /**
     * @param lstTablaProdAprobNativas the lstTablaProdAprobNativas to set
     */
    public void setLstTablaProdAprobNativas(List<TablaProdAprobNativa> lstTablaProdAprobNativas) {
        this.lstTablaProdAprobNativas = lstTablaProdAprobNativas;
    }

    /**
     * @return the strEvtJsDialogo
     */
    public String getStrEvtJsDialogo() {
        return strEvtJsDialogo;
    }

    /**
     * @param strEvtJsDialogo the strEvtJsDialogo to set
     */
    public void setStrEvtJsDialogo(String strEvtJsDialogo) {
        this.strEvtJsDialogo = strEvtJsDialogo;
    }

    /**
     * @return the renderConfirm
     */
    public boolean isRenderConfirm() {
        return renderConfirm;
    }

    /**
     * @param renderConfirm the renderConfirm to set
     */
    public void setRenderConfirm(boolean renderConfirm) {
        this.renderConfirm = renderConfirm;
    }

    /**
     * @return the lstTablaConsolCompra
     */
    public List<TablaConsolCompra> getLstTablaConsolCompra() {
        return lstTablaConsolCompra;
    }

    /**
     * @param lstTablaConsolCompra the lstTablaConsolCompra to set
     */
    public void setLstTablaConsolCompra(List<TablaConsolCompra> lstTablaConsolCompra) {
        this.lstTablaConsolCompra = lstTablaConsolCompra;
    }

    /**
     * @return the tablaProdXReq
     */
    public TablaProdXReq getTablaProdXReq() {
        return tablaProdXReq;
    }

    /**
     * @param tablaProdXReq the tablaProdXReq to set
     */
    public void setTablaProdXReq(TablaProdXReq tablaProdXReq) {
        this.tablaProdXReq = tablaProdXReq;
    }

    /**
     * @return the lstTablaProdXReq
     */
    public List<TablaProdXReq> getLstTablaProdXReq() {
        return lstTablaProdXReq;
    }

    /**
     * @param lstTablaProdXReq the lstTablaProdXReq to set
     */
    public void setLstTablaProdXReq(List<TablaProdXReq> lstTablaProdXReq) {
        this.lstTablaProdXReq = lstTablaProdXReq;
    }

    /**
     * @return the lstTablaProdxProv
     */
    public List<TablaProdxProv> getLstTablaProdxProv() {
        return lstTablaProdxProv;
    }

    /**
     * @param lstTablaProdxProv the lstTablaProdxProv to set
     */
    public void setLstTablaProdxProv(List<TablaProdxProv> lstTablaProdxProv) {
        this.lstTablaProdxProv = lstTablaProdxProv;
    }

    /**
     * @return the lstConsComps
     */
    public List<TablaConsComp> getLstConsComps() {
        return lstConsComps;
    }

    /**
     * @param lstConsComps the lstConsComps to set
     */
    public void setLstConsComps(List<TablaConsComp> lstConsComps) {
        this.lstConsComps = lstConsComps;
    }

    /**
     * @return the lstConsPedidos
     */
    public List<TablaConsPedido> getLstConsPedidos() {
        return lstConsPedidos;
    }

    /**
     * @param lstConsPedidos the lstConsPedidos to set
     */
    public void setLstConsPedidos(List<TablaConsPedido> lstConsPedidos) {
        this.lstConsPedidos = lstConsPedidos;
    }

    /**
     * @return the tablaConsCompSel
     */
    public TablaConsComp getTablaConsCompSel() {
        return tablaConsCompSel;
    }

    /**
     * @param tablaConsCompSel the tablaConsCompSel to set
     */
    public void setTablaConsCompSel(TablaConsComp tablaConsCompSel) {
        this.tablaConsCompSel = tablaConsCompSel;
    }

    /**
     * @return the tablaConsPedidoSel
     */
    public TablaConsPedido getTablaConsPedidoSel() {
        return tablaConsPedidoSel;
    }

    /**
     * @param tablaConsPedidoSel the tablaConsPedidoSel to set
     */
    public void setTablaConsPedidoSel(TablaConsPedido tablaConsPedidoSel) {
        this.tablaConsPedidoSel = tablaConsPedidoSel;
    }

    /**
     * @return the lstItemsCantidadSolicitada
     */
    public List<SelectItem> getLstItemsCantidadSolicitada() {
        return lstItemsCantidadSolicitada;
    }

    /**
     * @param lstItemsCantidadSolicitada the lstItemsCantidadSolicitada to set
     */
    public void setLstItemsCantidadSolicitada(List<SelectItem> lstItemsCantidadSolicitada) {
        this.lstItemsCantidadSolicitada = lstItemsCantidadSolicitada;
    }

    /**
     * @return the blnMostrarItemCantidadSelecionada
     */
    public boolean isBlnMostrarItemCantidadSelecionada() {
        return blnMostrarItemCantidadSelecionada;
    }

    /**
     * @param blnMostrarItemCantidadSelecionada the
     * blnMostrarItemCantidadSelecionada to set
     */
    public void setBlnMostrarItemCantidadSelecionada(boolean blnMostrarItemCantidadSelecionada) {
        this.blnMostrarItemCantidadSelecionada = blnMostrarItemCantidadSelecionada;
    }

    /**
     * @return the lstTablaProdAprobNativasXSolicitud
     */
    public List<TablaProdAprobNativa> getLstTablaProdAprobNativasXSolicitud() {
        return lstTablaProdAprobNativasXSolicitud;
    }

    /**
     * @param lstTablaProdAprobNativasXSolicitud the
     * lstTablaProdAprobNativasXSolicitud to set
     */
    public void setLstTablaProdAprobNativasXSolicitud(List<TablaProdAprobNativa> lstTablaProdAprobNativasXSolicitud) {
        this.lstTablaProdAprobNativasXSolicitud = lstTablaProdAprobNativasXSolicitud;
    }

    /**
     * @return the totalArpbXProd
     */
    public Integer getTotalArpbXProd() {
        return totalArpbXProd;
    }

    /**
     * @param totalArpbXProd the totalArpbXProd to set
     */
    public void setTotalArpbXProd(Integer totalArpbXProd) {
        this.totalArpbXProd = totalArpbXProd;
    }

    /**
     * @return the lstTablaFacturas
     */
    public List<TablaFactura> getLstTablaFacturas() {
        return lstTablaFacturas;
    }

    /**
     * @param lstTablaFacturas the lstTablaFacturas to set
     */
    public void setLstTablaFacturas(List<TablaFactura> lstTablaFacturas) {
        this.lstTablaFacturas = lstTablaFacturas;
    }

    public TablaFactura getTablaFacturaSel() {
        return tablaFacturaSel;
    }

    public void setTablaFacturaSel(TablaFactura tablaFacturaSel) {
        this.tablaFacturaSel = tablaFacturaSel;
    }

    /**
     * @return the lstTablaDetalleFactXFactura
     */
    public List<TablaDetalleFactura> getLstTablaDetalleFactXFactura() {
        return lstTablaDetalleFactXFactura;
    }

    /**
     * @param lstTablaDetalleFactXFactura the lstTablaDetalleFactXFactura to set
     */
    public void setLstTablaDetalleFactXFactura(List<TablaDetalleFactura> lstTablaDetalleFactXFactura) {
        this.lstTablaDetalleFactXFactura = lstTablaDetalleFactXFactura;
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

    /**
     * @return the jasperResourceZip
     */
    public com.icesoft.faces.context.Resource getJasperResourceZip() {
        return jasperResourceZip;
    }

    /**
     * @param jasperResourceZip the jasperResourceZip to set
     */
    public void setJasperResourceZip(com.icesoft.faces.context.Resource jasperResourceZip) {
        this.jasperResourceZip = jasperResourceZip;
    }

    /**
     * @return the lstTablaConsolidadoInv
     */
    public List<TablaConsolidadoInv> getLstTablaConsolidadoInv() {
        return lstTablaConsolidadoInv;
    }

    /**
     * @param lstTablaConsolidadoInv the lstTablaConsolidadoInv to set
     */
    public void setLstTablaConsolidadoInv(List<TablaConsolidadoInv> lstTablaConsolidadoInv) {
        this.lstTablaConsolidadoInv = lstTablaConsolidadoInv;
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
    //</editor-fold>
}
