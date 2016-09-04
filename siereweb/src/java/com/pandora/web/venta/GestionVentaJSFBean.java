/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.venta;

import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.adm.rf.dao.RfMotivoevento;
import com.pandora.mod.venta.VentaSFBean;
import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntColxventa;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntServicio;
import com.pandora.mod.venta.dao.VntServxventa;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.entrada.BandejaEntradaJSFBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author luis
 */
@Named
@SessionScoped
public class GestionVentaJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    private VentaSFBean vsfb;

    private VentaSFBean lookupVentaSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (VentaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/VentaSFBean!com.pandora.mod.venta.VentaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private boolean blnMostrarPanel;
    private List<TablaVntCliente> lstTablaVntClientes = new ArrayList<>();
    private String strBuscarCln;
    private TablaVntCliente tablaVntClienteSel = new TablaVntCliente();
    private List<TablaVntServicio> lstTablaVntServicios = new ArrayList<>();
    private TablaVntServicio tablaVntServicioSel = new TablaVntServicio();
    private List<TablaVntRegistroventa> lstTablaVntRegistroventas = new ArrayList<>();
    private TablaVntRegistroventa tablaVntRegistroventaSel = new TablaVntRegistroventa();
    private List<TablaVntSrvXVenta> lstTablaSrvXVentas = new ArrayList<>();
    private TablaVntSrvXVenta tablaSrvXVentaSel = new TablaVntSrvXVenta();
    private VntDetevento vntDeteventoSel = new VntDetevento();
    private String depId;
    private Long ciuId;
    private List<SelectItem> lstItemsCiudadXDep = new ArrayList<>();
    private TablaVntDetalleCliente tablaVntDetalleclienteSel = new TablaVntDetalleCliente();
    private boolean blnNuevoCliente = false;
    private List<SelectItem> lstMotivoEvento = new ArrayList<>();
    private String strDevNombres;
    private String strDevApellidos;
    private Integer idMotivoEvento = -1;
    private String strDevDireccion;
    private Date datDevFecha;
    private Date datDevHoraInicio;
    private Date datDevHoraFinal;
    private String strDevTelefono1;
    private String strDevTelefono2;
    private String strDevCelular1;
    private String strDevCelular2;
    private String strDevObservacion;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">
    @Override
    public void init() {
        vsfb = getBandejaEntradaJSFBean().getVsfb();
        blnMostrarPanel = false;
        depId = "25";
        cargarCiuXDept();
        cargarListaMotivoEvento();
        ciuId = 11L;
        idMotivoEvento = -1;
        tablaVntClienteSel = new TablaVntCliente();
        tablaVntServicioSel = new TablaVntServicio();
        cargarServicios();
        VntRegistroventa vr = vsfb.getVntRegistroventaSel();
        tablaVntRegistroventaSel.setVntRegistroventa(vr);
        lstTablaVntClientes.clear();
        if (vr.getEstrvntId().getEstrvntId() == 2) {
            tablaVntClienteSel.setVntCliente(vsfb.getVntClienteXId(vr.getClnId().getClnId()));
            VntDetevento vd = vsfb.getDeteventoXRegVenta(vr.getRgvtId());
            lstTablaVntClientes.add(tablaVntClienteSel);
            if (vd != null) {
                vntDeteventoSel = vd;
            }
            cargarSrvXVenta();
            numPanel = 2;
        } else {
            numPanel = 1;
        }
    }

    @Override
    public void limpiarVariables() {
        vsfb.remove();
        blnMostrarPanel = false;
        lstTablaVntClientes.clear();
        lstTablaVntServicios.clear();
        strBuscarCln = "";
        tablaVntClienteSel = null;
        tablaVntServicioSel = null;
    }

    private void limpiarCampos() {

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">    
    private void cargarSrvXVenta() {
        lstTablaSrvXVentas.clear();
        for (VntServxventa vs : vsfb.getLstServxventaXVnt(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            TablaVntSrvXVenta tsxv = new TablaVntSrvXVenta(vs);
            lstTablaSrvXVentas.add(tsxv);
        }
    }

//    private void cargarRegistroVentaXClienteXEstado(){
//        lstTablaVntRegistroventas.clear();
//        for (VntRegistroventa registroventa : vsfb.getLstVntRegistroventaXCliente(tablaVntClienteSel.getVntCliente().getClnId(), 2)) {
//            TablaVntRegistroventa tvrv = new TablaVntRegistroventa();
//            tvrv.setVntRegistroventa(registroventa);
//            lstTablaVntRegistroventas.add(tvrv);
//        }
//    }
    private void cargarListaServVentaXClienteXEstVnt() {
        lstTablaSrvXVentas.clear();
        for (VntServxventa servxventa : vsfb.getLstVntServxventaXCteYEstV(tablaVntClienteSel.getVntCliente().getClnId(), 2)) {
            TablaVntSrvXVenta tvsxv = new TablaVntSrvXVenta();
            tvsxv.setVntServxventa(servxventa);
            lstTablaSrvXVentas.add(tvsxv);
            tablaVntRegistroventaSel.getVntRegistroventa().getVntServxventaList().add(servxventa);
        }
    }

//    private void cargarListaRegVentaXClienteXEstVnt(){
//        lstTablaVntRegistroventas.clear();
//        for (VntRegistroventa registroventa : vsfb.getLstVntRegistroventaXCteYEstV(tablaVntClienteSel.getVntCliente().getClnId(), 2)) {
//            TablaVntRegistroventa tvrv = new TablaVntRegistroventa();
//            tvrv.setVntRegistroventa(registroventa);
//            lstTablaVntRegistroventas.add(tvrv);
//        }
//    }
    private void cargarCiuXDept() {
        lstItemsCiudadXDep.clear();
        lstItemsCiudadXDep.add(itemSeleccioneLong);
        for (RfCiudad rc : getAppBean().getDgslb().getLstCiuXDep(depId)) {
            lstItemsCiudadXDep.add(new SelectItem(rc.getCiuId(), rc.getCiuDesc()));
        }
    }

    private void cargarListaMotivoEvento() {
        lstMotivoEvento.clear();
        lstMotivoEvento.add(new SelectItem(-1, "SELECCIONE >>"));
        for (RfMotivoevento rme : vsfb.getLstRfMotivoeventoXEstado(true)) {
            lstMotivoEvento.add(new SelectItem(rme.getMteId(), rme.getMteNombre()));
        }
    }

    private void asociarServicioVenta() {
        
        VntRegistroventa vr = tablaVntRegistroventaSel.getVntRegistroventa();
        List<VntServxventa> lstVntServxventasGrabar = new ArrayList<>();
        if (vr.getEstrvntId().getEstrvntId() == 1) {
            vr.setClnId(tablaVntClienteSel.getVntCliente());
            vr.setRgvtAnulado(Boolean.FALSE);
        }
        //Colocar el estado de la venta en proceso
        vr.setEstrvntId(vsfb.getEstventaXId(2));
        vr.setRgvtEstconfirmada(Boolean.FALSE);
        VntColxventa vntColxventa = new VntColxventa();
        vntColxventa.setRgvtId(vr);
        vntColxventa.setCxcId(getPrincipalJSFBean().getMssfb().getCrgxcolActual());
        vntColxventa.setColxvDechaproc(new Date());
        if (vr.getVntColxventaList() != null) {
            vr.getVntColxventaList().add(vntColxventa);
        } else {
            vr.setVntColxventaList(new ArrayList<VntColxventa>());
            vr.getVntColxventaList().add(vntColxventa);
        }
        for (TablaVntServicio tablaVntServicio : lstTablaVntServicios) {
            if (tablaVntServicio.isSeleccionado()) {
                VntServxventa servxventa = new VntServxventa();
                servxventa.setRgvtId(vr);
                servxventa.setSrvxventCantidad(tablaVntServicio.getCantidadSrv());
                servxventa.setVsrvId(tablaVntServicio.getVntServicio());
                servxventa.setSrvxventEst(Boolean.TRUE);
                servxventa.setSrvxventPrecioventa(tablaVntServicio.getBigdPrecioCliente());
                servxventa.setSrvxventValtotalclnt(tablaVntServicio.getBigdPrecioCliente().multiply(new BigDecimal(tablaVntServicio.getCantidadSrv())));
                lstVntServxventasGrabar.add(servxventa);
            }
        }
        vsfb.editarRegVentaConServ(vr, lstVntServxventasGrabar);
        tablaVntRegistroventaSel.setVntRegistroventa(vr);
        cargarSrvXVenta();
    }

    private void cerrarVenta() {
        VntRegistroventa vr = tablaVntRegistroventaSel.getVntRegistroventa();
        if(!lstTablaSrvXVentas.isEmpty()){
            if (vr.getEstrvntId().getEstrvntId() == 2) {
                vr.setEstrvntId(vsfb.getEstventaXId(3));
                BigDecimal bdValorVenta = BigDecimal.ZERO;
                for (VntServxventa vs : vsfb.getLstServxventaXVnt(vr.getRgvtId())) {
                    bdValorVenta = bdValorVenta.add(vs.getSrvxventValtotalclnt());
                }
                vr.setRgvtValorventa(bdValorVenta);
                vsfb.editarRegVenta(vr);
                tablaVntRegistroventaSel.setVntRegistroventa(vsfb.getVntRegistroventaSel());
            }
            astslb.crearSeguimientoTareaPasoOrigenDest(getPrincipalJSFBean().getMssfb().getSysSegtareaActual().getStrId(), 30L, 31L);
            mostrarError("Cotización finalizada, ir a confirmación de pago...!", 3);
        }else{
            mostrarError("Esta cotización no tiene servicios asociados");
        }
                
//        if (!vr.getVntServxventaList().isEmpty()) {
//            if (vr.getEstrvntId().getEstrvntId() == 2) {
//                vr.setEstrvntId(vsfb.getEstventaXId(3));
//                BigDecimal bdValorVenta = BigDecimal.ZERO;
//                for (VntServxventa vs : vsfb.getLstServxventaXVnt(vr.getRgvtId())) {
//                    bdValorVenta = bdValorVenta.add(vs.getSrvxventValtotalclnt());
//                }
//                vr.setRgvtValorventa(bdValorVenta);
//                vsfb.editarRegVenta(vr);
//                tablaVntRegistroventaSel.setVntRegistroventa(vsfb.getVntRegistroventaSel());
//            }
//            astslb.crearSeguimientoTareaPasoOrigenDest(getPrincipalJSFBean().getMssfb().getSysSegtareaActual().getStrId(), 30L, 31L);
//            mostrarError("Cotización finalizada, ir a confirmación de pago...!", 3);
//        } else {
//            mostrarError("Esta cotización no tiene servicios asociados");
//        }
    }

    private void cargarVentaXCliente() {
        lstTablaVntRegistroventas.clear();
        for (VntRegistroventa registroventa : vsfb.getLstVntRegistroventaXCliente(tablaVntClienteSel.getVntCliente().getClnId(), 6)) {
            TablaVntRegistroventa tvrv = new TablaVntRegistroventa();
            tvrv.setVntRegistroventa(registroventa);
            lstTablaVntRegistroventas.add(tvrv);
        }
    }

    private void cargarServicioXCliente() {
        lstTablaVntServicios.clear();
        for (VntServicio vntServicio : vsfb.getLstVntServicioXCliente(tablaVntRegistroventaSel.getVntRegistroventa().getRgvtId())) {
            TablaVntServicio tvs = new TablaVntServicio();
            tvs.setVntServicio(vntServicio);
            lstTablaVntServicios.add(tvs);
        }
    }

    private void asociarDetalleEvento() {
        if (validarForm()) {
            VntRegistroventa vr = tablaVntRegistroventaSel.getVntRegistroventa();
            if (vr.getEstrvntId().getEstrvntId() == 1) {
                vr.setClnId(tablaVntClienteSel.getVntCliente());
                vr.setRgvtAnulado(Boolean.FALSE);
            }
            vr.setEstrvntId(vsfb.getEstventaXId(2));
            vntDeteventoSel.setCiuId(new RfCiudad(ciuId));
            vntDeteventoSel.setVdeNombrescontacto(strDevNombres);
            vntDeteventoSel.setVdeApellidoscontacto(strDevApellidos);
            vntDeteventoSel.setMteId(vsfb.motivoEventoXId(idMotivoEvento));
            vntDeteventoSel.setVdeDireccionevt(strDevDireccion);
            vntDeteventoSel.setVdeFechaevt(new Date());
            vntDeteventoSel.setVdeHorainicio(datDevHoraInicio);
            vntDeteventoSel.setVdeHorafinal(datDevHoraFinal);
            vntDeteventoSel.setVdeTelefono1(strDevTelefono1);
            vntDeteventoSel.setVdeTelefono2(strDevTelefono2);
            vntDeteventoSel.setVdeCelular1(strDevCelular1);
            vntDeteventoSel.setVdeCelular2(strDevCelular2);
            vntDeteventoSel.setVdeObsr(strDevObservacion);
            vntDeteventoSel.setDclnId(tablaVntDetalleclienteSel.getVntDetallecliente());
            vntDeteventoSel.setRgvtId(vr);
            vr.setVdeId(vntDeteventoSel);
            vsfb.editarRegVenta(vr);
            tablaVntRegistroventaSel.setVntRegistroventa(vsfb.getVntRegistroventaSel());
            numPanel = 2;
        } else {
            return;
        }
    }

    private void cargarServicios() {
        lstTablaVntServicios.clear();
        for (VntServicio vntServicio : vsfb.getLstServicios()) {
            TablaVntServicio tvs = new TablaVntServicio(vntServicio);
            tvs.setBigdPrecioCliente(tvs.getVntServicio().getVsrvValunitcliente());
            tvs.setCantidadSrv(1);
            lstTablaVntServicios.add(tvs);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">    
    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        if (numPanel != 4) {
            //getManejoClienteJSFBean().limpiarVariables();
        }
        getManejoClienteJSFBean().setNumPanel(numPanel);
        switch (numPanel) {
            case 2:
                cargarListaServVentaXClienteXEstVnt();
//                cargarRegistroVentaXClienteXEstado();
//                cargarSrvXVenta();
                break;
            case 4:
                getManejoClienteJSFBean().init();
                break;
        }
    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {

    }

    //<editor-fold defaultstate="collapsed" desc="Buscar cliente">
    public void rowDtSelCliente_ActionEvent(ActionEvent ae) {
        tablaVntClienteSel = (TablaVntCliente) ae.getComponent().getAttributes().get("tvcs");
        vsfb.setVntClienteSel(tablaVntClienteSel.getVntCliente());
        getManejoClienteJSFBean().init();
        getManejoClienteJSFBean().setVntCliente(tablaVntClienteSel.getVntCliente());
        getManejoClienteJSFBean().asignarDetallesCliente();
        getManejoClienteJSFBean().setTablaVntClienteSel(tablaVntClienteSel);
        getManejoClienteJSFBean().cargarListaDetalleCliente();
        blnNuevoCliente = false;
//        cargarVentaXCliente();
        numPanel = 4;
    }

    public void rowDtDetalleCliente_ActionEvent(ActionEvent ae) {
        numPanel = 3;
        strDevNombres = getManejoClienteJSFBean().getStrCltNombres();
      
        strDevTelefono1 = getManejoClienteJSFBean().getStrCltTelFijo();
        strDevCelular1 = getManejoClienteJSFBean().getStrCltCelular();
    }

    public void rowDtVerDetalleCliente_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntClienteSel = (TablaVntCliente) map.get("tcds");
        blnMostrarPanel = true;
    }

    public void btnVolverCliente_ActionEvent(ActionEvent ae) {
        blnMostrarPanel = false;
    }
    //</editor-fold>

    public void btnCerrarVenta_ActionEvent(ActionEvent ae) {
        cerrarVenta();
    }

    public void rwDtSelServicio_ActionEvent(ActionEvent ae) {
        tablaVntServicioSel = (TablaVntServicio) ae.getComponent().getAttributes().get("tablaVntServicioSel");
    }

    public void rowDtRegistroVenta_ActionEvent(ActionEvent ae) {
        tablaVntRegistroventaSel = (TablaVntRegistroventa) ae.getComponent().getAttributes().get("tablaVntRegistroventaSel");
        cargarServicioXCliente();
    }

    public void selTodoRegVenta_ValueChange(ValueChangeEvent vce) {
        Boolean bln = (Boolean) vce.getNewValue();
        selTodoLst(lstTablaVntRegistroventas, bln);
    }

    public void ddlDep_ValueChangeEvent(ValueChangeEvent vce) {
        depId = (String) vce.getNewValue();
        cargarCiuXDept();
    }

    public void selTodoServicio_ValueChange(ValueChangeEvent vce) {
        Boolean bln = (Boolean) vce.getNewValue();
        selTodoLst(lstTablaVntServicios, bln);
    }

    public void btnGrabarVenta_ActionEvent(ActionEvent ae) {
        switch (numPanel) {
            case 2:
                asociarServicioVenta();
                break;
            case 3:
                asociarDetalleEvento();
                break;
        }
    }

    public TablaVntCliente getTablaVntClienteSel() {
        return tablaVntClienteSel;
    }

    public void btnNuevoCliente_ActionEvent(ActionEvent ae) {
        numPanel = 4;
        getManejoClienteJSFBean().init();
        getManejoClienteJSFBean().limpiarFormulario();
        getManejoClienteJSFBean().getLstTablaVntDetalleCliente().clear();
        lstTablaSrvXVentas.clear();
        blnNuevoCliente = true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">    
    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        lstTablaVntClientes.clear();
        for (VntCliente vntCliente : vsfb.getLstClienteXTexto(strBuscarCln.toUpperCase())) {
            lstTablaVntClientes.add(new TablaVntCliente(vntCliente));
        }
    }

    @Override
    public boolean grabarPaso() {
        return false;
    }

    @Override
    public boolean validarForm() {
        return true;
    }
    
    public boolean validarServicioVnt(){
        
        return true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">    
    private BandejaEntradaJSFBean getBandejaEntradaJSFBean() {
        fc = FacesContext.getCurrentInstance();
        elc = fc.getELContext();
        return (BandejaEntradaJSFBean) elc.getELResolver().getValue(elc, null, "bandejaEntradaJSFBean");
    }

    private ManejoClienteJSFBean getManejoClienteJSFBean() {
        fc = FacesContext.getCurrentInstance();
        elc = fc.getELContext();
        return (ManejoClienteJSFBean) elc.getELResolver().getValue(elc, null, "manejoClienteJSFBean");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
    /**
     * @return the lstTablaVntClientes
     */
    public List<TablaVntCliente> getLstTablaVntClientes() {
        return lstTablaVntClientes;
    }

    /**
     * @param lstTablaVntClientes the lstTablaVntClientes to set
     */
    public void setLstTablaVntClientes(List<TablaVntCliente> lstTablaVntClientes) {
        this.lstTablaVntClientes = lstTablaVntClientes;
    }

    /**
     * @return the strBuscarCln
     */
    public String getStrBuscarCln() {
        return strBuscarCln;
    }

    /**
     * @param strBuscarCln the strBuscarCln to set
     */
    public void setStrBuscarCln(String strBuscarCln) {
        this.strBuscarCln = strBuscarCln;
    }

    public void setTablaVntClienteSel(TablaVntCliente tablaVntClienteSel) {
        this.tablaVntClienteSel = tablaVntClienteSel;
    }

    /**
     * @return the lstTablaVntServicios
     */
    public List<TablaVntServicio> getLstTablaVntServicios() {
        return lstTablaVntServicios;
    }

    /**
     * @param lstTablaVntServicios the lstTablaVntServicios to set
     */
    public void setLstTablaVntServicios(List<TablaVntServicio> lstTablaVntServicios) {
        this.lstTablaVntServicios = lstTablaVntServicios;
    }

    /**
     * @return the tablaVntServicioSel
     */
    public TablaVntServicio getTablaVntServicioSel() {
        return tablaVntServicioSel;
    }

    /**
     * @param tablaVntServicioSel the tablaVntServicioSel to set
     */
    public void setTablaVntServicioSel(TablaVntServicio tablaVntServicioSel) {
        this.tablaVntServicioSel = tablaVntServicioSel;
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
     * @return the lstTablaVntSrvXVentas
     */
    public List<TablaVntSrvXVenta> getLstTablaSrvXVentas() {
        return lstTablaSrvXVentas;
    }

    /**
     * @param lstTablaSrvXVentas the lstTablaVntSrvXVentas to set
     */
    public void setLstTablaSrvXVentas(List<TablaVntSrvXVenta> lstTablaSrvXVentas) {
        this.lstTablaSrvXVentas = lstTablaSrvXVentas;
    }

    /**
     * @return the tablaSrvXVentaSel
     */
    public TablaVntSrvXVenta getTablaSrvXVentaSel() {
        return tablaSrvXVentaSel;
    }

    /**
     * @param tablaSrvXVentaSel the tablaSrvXVentaSel to set
     */
    public void setTablaSrvXVentaSel(TablaVntSrvXVenta tablaSrvXVentaSel) {
        this.tablaSrvXVentaSel = tablaSrvXVentaSel;
    }

    /**
     * @return the vntDeteventoSel
     */
    public VntDetevento getVntDeteventoSel() {
        return vntDeteventoSel;
    }

    /**
     * @param vntDeteventoSel the vntDeteventoSel to set
     */
    public void setVntDeteventoSel(VntDetevento vntDeteventoSel) {
        this.vntDeteventoSel = vntDeteventoSel;
    }

    /**
     * @return the depId
     */
    public String getDepId() {
        return depId;
    }

    /**
     * @param depId the depId to set
     */
    public void setDepId(String depId) {
        this.depId = depId;
    }

    /**
     * @return the lstItemsCiudadXDep
     */
    public List<SelectItem> getLstItemsCiudadXDep() {
        return lstItemsCiudadXDep;
    }

    /**
     * @param lstItemsCiudadXDep the lstItemsCiudadXDep to set
     */
    public void setLstItemsCiudadXDep(List<SelectItem> lstItemsCiudadXDep) {
        this.lstItemsCiudadXDep = lstItemsCiudadXDep;
    }

    /**
     * @return the ciuId
     */
    public Long getCiuId() {
        return ciuId;
    }

    /**
     * @param ciuId the ciuId to set
     */
    public void setCiuId(Long ciuId) {
        this.ciuId = ciuId;
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
     * @return the tablaVntDetalleclienteSel
     */
    public TablaVntDetalleCliente getTablaVntDetalleclienteSel() {
        return tablaVntDetalleclienteSel;
    }

    /**
     * @param tablaVntDetalleclienteSel the tablaVntDetalleclienteSel to set
     */
    public void setTablaVntDetalleclienteSel(TablaVntDetalleCliente tablaVntDetalleclienteSel) {
        this.tablaVntDetalleclienteSel = tablaVntDetalleclienteSel;
    }

    /**
     * @return the blnNuevoCliente
     */
    public boolean isBlnNuevoCliente() {
        return blnNuevoCliente;
    }

    /**
     * @param blnNuevoCliente the blnNuevoCliente to set
     */
    public void setBlnNuevoCliente(boolean blnNuevoCliente) {
        this.blnNuevoCliente = blnNuevoCliente;
    }

    /**
     * @return the idMotivoEvento
     */
    public Integer getIdMotivoEvento() {
        return idMotivoEvento;
    }

    /**
     * @param idMotivoEvento the idMotivoEvento to set
     */
    public void setIdMotivoEvento(Integer idMotivoEvento) {
        this.idMotivoEvento = idMotivoEvento;
    }

    /**
     * @return the lstMotivoEvento
     */
    public List<SelectItem> getLstMotivoEvento() {
        return lstMotivoEvento;
    }

    /**
     * @param lstMotivoEvento the lstMotivoEvento to set
     */
    public void setLstMotivoEvento(List<SelectItem> lstMotivoEvento) {
        this.lstMotivoEvento = lstMotivoEvento;
    }

    /**
     * @return the strDevNombres
     */
    public String getStrDevNombres() {
        return strDevNombres;
    }

    /**
     * @param strDevNombres the strDevNombres to set
     */
    public void setStrDevNombres(String strDevNombres) {
        this.strDevNombres = strDevNombres;
    }

    /**
     * @return the strDevApellidos
     */
    public String getStrDevApellidos() {
        return strDevApellidos;
    }

    /**
     * @param strDevApellidos the strDevApellidos to set
     */
    public void setStrDevApellidos(String strDevApellidos) {
        this.strDevApellidos = strDevApellidos;
    }

    /**
     * @return the strDevDireccion
     */
    public String getStrDevDireccion() {
        return strDevDireccion;
    }

    /**
     * @param strDevDireccion the strDevDireccion to set
     */
    public void setStrDevDireccion(String strDevDireccion) {
        this.strDevDireccion = strDevDireccion;
    }

    /**
     * @return the datDevFecha
     */
    public Date getDatDevFecha() {
        return datDevFecha;
    }

    /**
     * @param datDevFecha the datDevFecha to set
     */
    public void setDatDevFecha(Date datDevFecha) {
        this.datDevFecha = datDevFecha;
    }

    /**
     * @return the datDevHoraInicio
     */
    public Date getDatDevHoraInicio() {
        return datDevHoraInicio;
    }

    /**
     * @param datDevHoraInicio the datDevHoraInicio to set
     */
    public void setDatDevHoraInicio(Date datDevHoraInicio) {
        this.datDevHoraInicio = datDevHoraInicio;
    }

    /**
     * @return the datDevHoraFinal
     */
    public Date getDatDevHoraFinal() {
        return datDevHoraFinal;
    }

    /**
     * @param datDevHoraFinal the datDevHoraFinal to set
     */
    public void setDatDevHoraFinal(Date datDevHoraFinal) {
        this.datDevHoraFinal = datDevHoraFinal;
    }

    /**
     * @return the strDevTelefono1
     */
    public String getStrDevTelefono1() {
        return strDevTelefono1;
    }

    /**
     * @param strDevTelefono1 the strDevTelefono1 to set
     */
    public void setStrDevTelefono1(String strDevTelefono1) {
        this.strDevTelefono1 = strDevTelefono1;
    }

    /**
     * @return the strDevTelefono2
     */
    public String getStrDevTelefono2() {
        return strDevTelefono2;
    }

    /**
     * @param strDevTelefono2 the strDevTelefono2 to set
     */
    public void setStrDevTelefono2(String strDevTelefono2) {
        this.strDevTelefono2 = strDevTelefono2;
    }

    /**
     * @return the strDevCelular1
     */
    public String getStrDevCelular1() {
        return strDevCelular1;
    }

    /**
     * @param strDevCelular1 the strDevCelular1 to set
     */
    public void setStrDevCelular1(String strDevCelular1) {
        this.strDevCelular1 = strDevCelular1;
    }

    /**
     * @return the strDevCelular2
     */
    public String getStrDevCelular2() {
        return strDevCelular2;
    }

    /**
     * @param strDevCelular2 the strDevCelular2 to set
     */
    public void setStrDevCelular2(String strDevCelular2) {
        this.strDevCelular2 = strDevCelular2;
    }

    /**
     * @return the strDevObservacion
     */
    public String getStrDevObservacion() {
        return strDevObservacion;
    }

    /**
     * @param strDevObservacion the strDevObservacion to set
     */
    public void setStrDevObservacion(String strDevObservacion) {
        this.strDevObservacion = strDevObservacion;
    }
    //</editor-fold>
}
