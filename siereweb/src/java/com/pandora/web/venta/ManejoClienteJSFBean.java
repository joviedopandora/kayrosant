/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.venta;

import adm.sys.dao.RfParentezco;
import adm.sys.dao.RfSexo;
import adm.sys.dao.RfTipodoc;
import com.pandora.mod.venta.ManejoClienteSFBean;
import com.pandora.mod.venta.dao.VntActeconomica;
import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntDetallecliente;
import com.pandora.mod.venta.dao.VntRfTipocliente;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.NoSuchEJBException;
import javax.ejb.NoSuchObjectLocalException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
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
public class ManejoClienteJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    private ManejoClienteSFBean mcsfb;

    private ManejoClienteSFBean lookupManejoClienteSFBean() {
        try {
            Context c = new InitialContext();
            return (ManejoClienteSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/ManejoClienteSFBean!com.pandora.mod.venta.ManejoClienteSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private GestionVentaJSFBean getGestionVentaJSFBean() {
        fc = FacesContext.getCurrentInstance();
        elc = fc.getELContext();
        return (GestionVentaJSFBean) elc.getELResolver().getValue(elc, null, "gestionVentaJSFBean");
    }

    private boolean blnMostrarPanel;
    private boolean blnNuevo;
    private boolean blnMostrarActEconomica;
    private VntCliente vntCliente = new VntCliente();
    private String idTipoDoc;
    private String strCltDocumento;
    private String strCltNombres;
    
    private String idSexo;
    private Integer intCltNumHijos;
    private Date datCltFechaNac;
    private String strCltDireccion;
    private String strCltTelFijo;
    private String strCltCelular;
    private String strCltCorreo;
    private String strCltContacto;
    private Integer idTipoCliente;
    private Integer idActEco;
    private List<SelectItem> lstTipoDoc = new ArrayList<>();
    private List<SelectItem> lstSexo = new ArrayList<>();
    private List<SelectItem> lstSexoDet = new ArrayList<>();
    private List<SelectItem> lstTipoCliente = new ArrayList<>();
    private List<TablaVntCliente> lstTablaVntCliente = new ArrayList<>();
    private TablaVntCliente TablaVntClienteSel = new TablaVntCliente();
    private VntDetallecliente vntDetallecliente = new VntDetallecliente();
    private List<TablaVntDetalleCliente> lstTablaVntDetalleCliente = new ArrayList<>();
    private List<SelectItem> lstActividadEconomica = new ArrayList<>();
    private TablaVntDetalleCliente tablaVntDetalleclienteSel = new TablaVntDetalleCliente();
    private List<SelectItem> lstParentesco = new ArrayList<>();
    private String strDetNombre1;
    
    private String idSexoDet;
    private Integer idParentesco;
    private Date datDetFechaNac;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">    
    @Override
    public void init() {
        mcsfb = lookupManejoClienteSFBean();
        blnMostrarPanel = false;
        blnNuevo = true;
        blnMostrarActEconomica = false;
        cargarListaTipoDoc();
        cargarListaSexo();
        cargarListaTipoCliente();
        cargarListaActividadEconomica();
        cargarListaClientes();
        cargarListaParentesco();
        cargarListaSexoDetalle();
        lstTablaVntDetalleCliente.clear();
    }

    public void limpiarFormulario() {
        idTipoDoc = "-1";
        strCltDocumento = "";
        strCltNombres = "";
        
        idSexo = "-1";
        intCltNumHijos = null;
        datCltFechaNac = null;
        strCltDireccion = "";
        strCltTelFijo = "";
        strCltCelular = "";
        strCltCorreo = "";
        strCltContacto = "";
        idTipoCliente = -1;
        idActEco = -1;
        strDetNombre1 = "";
        
        idParentesco = -1;
        idSexoDet = "-1";
        datDetFechaNac.equals(null);
//        vntDetallecliente = new VntDetallecliente();
    }

    @Override
    public void limpiarVariables() {
        limpiarFormulario();
        try {
            if (mcsfb != null) {
                mcsfb.remove();
            }
        } catch (NoSuchEJBException | NoSuchObjectLocalException ex) {

        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">
    public void cargarListaTipoDoc() {
        lstTipoDoc.clear();
        lstTipoDoc.add(itemSeleccione);
        for (RfTipodoc rt : mcsfb.getLstRfTipodoc(true)) {
            lstTipoDoc.add(new SelectItem(rt.getTdcId(), rt.getTdcNombre()));
        }
    }

    public void asignarDetallesCliente() {
        idTipoDoc = vntCliente.getTdcId().getTdcId();
        strCltDocumento = vntCliente.getClnIdentificacion();
        strCltNombres = vntCliente.getClnNombres();
       
        idSexo = vntCliente.getSexId().getSexId();
        intCltNumHijos = vntCliente.getClnNumhijos();
        datCltFechaNac = vntCliente.getClnFechanace();
        strCltDireccion = vntCliente.getClnDiereccion();
        strCltTelFijo = vntCliente.getClnFijo();
        strCltCelular = vntCliente.getClnCelular();
        strCltCorreo = vntCliente.getClnCorreoe();
        strCltContacto = vntCliente.getClnContacto();
        idTipoCliente = vntCliente.getTclId().getTclId();
        if (idTipoCliente == 1) {
            blnMostrarActEconomica = true;
            idActEco = vntCliente.getAteId().getAteId();
        } else {
            blnMostrarActEconomica = false;
            idActEco = -1;
        }
    }

    public void asignarDetallesEvento() {

    }

    public void cargarListaSexo() {
        lstSexo.clear();
        lstSexo.add(itemSeleccione);
        for (RfSexo rt : mcsfb.getLstRfSexo()) {
            lstSexo.add(new SelectItem(rt.getSexId(), rt.getSexDesc()));
        }
    }

    public void cargarListaSexoDetalle() {
        lstSexoDet.clear();
        lstSexoDet.add(itemSeleccione);
        for (RfSexo rt : mcsfb.getLstRfSexo()) {
            lstSexoDet.add(new SelectItem(rt.getSexId(), rt.getSexDesc()));
        }
    }

    public void cargarListaTipoCliente() {
        lstTipoCliente.clear();
        lstTipoCliente.add(itemSeleccioneInt);
        for (VntRfTipocliente vtc : mcsfb.getLstVntRfTipocliente(true)) {
            lstTipoCliente.add(new SelectItem(vtc.getTclId(), vtc.getTclNombre()));
        }
    }

    public void selTipoCliente_ActionEvent(ActionEvent ae) {
        if (idTipoCliente == 1) {
            blnMostrarActEconomica = true;
        } else {
            blnMostrarActEconomica = false;
        }
    }

    public void cargarListaActividadEconomica() {
        lstActividadEconomica.clear();
        lstActividadEconomica.add(new SelectItem(-1, "SELECCIONE >>"));
        for (VntActeconomica vae : mcsfb.getLstVntActeconomicaXEstado(true)) {
            lstActividadEconomica.add(new SelectItem(vae.getAteId(), vae.getAteNombre()));
        }
    }

    public void cargarListaParentesco() {
        lstParentesco.clear();
        lstParentesco.add(itemSeleccioneInt);
        for (RfParentezco par : mcsfb.getLstRfParentesco(true)) {
            lstParentesco.add(new SelectItem(par.getPrtId(), par.getPrtNombre()));
        }
    }

    public void cargarListaClientes() {
        lstTablaVntCliente.clear();
        for (VntCliente vc : mcsfb.getLstVntCliente()) {
            TablaVntCliente tvc = new TablaVntCliente(vc);
            lstTablaVntCliente.add(tvc);
        }
    }

    public void cargarListaDetalleCliente() {
        lstTablaVntDetalleCliente.clear();
        for (VntDetallecliente vdc : mcsfb.getLstVntDetallecliente(TablaVntClienteSel.getVntCliente().getClnId())) {
            TablaVntDetalleCliente tvdc = new TablaVntDetalleCliente(vdc);
            tvdc.setEdad(getAnioDiferencia(tvdc.getVntDetallecliente().getDclnFechanace(), new Date()));
            lstTablaVntDetalleCliente.add(tvdc);
        }
    }

    private void grabarCliente() {
        if (validarForm()) {
            if (getGestionVentaJSFBean().isBlnNuevoCliente() == true) {
                vntCliente = new VntCliente();
                vntCliente.setTdcId(mcsfb.getRfTipodocXId(idTipoDoc));
                vntCliente.setClnIdentificacion(strCltDocumento);
                vntCliente.setClnNombres(strCltNombres);
                
                vntCliente.setSexId(mcsfb.getRfSexo(idSexo));
                vntCliente.setClnNumhijos(intCltNumHijos);
                vntCliente.setClnFechanace(datCltFechaNac);
                vntCliente.setClnDiereccion(strCltDireccion);
                vntCliente.setClnFijo(strCltTelFijo);
                vntCliente.setClnCelular(strCltCelular);
                vntCliente.setClnCorreoe(strCltCorreo);
                vntCliente.setClnContacto(strCltContacto);
                vntCliente.setTclId(mcsfb.getVntRfTipoclienteXId(idTipoCliente));
                vntCliente.setAteId(mcsfb.getVntActeconomicaXId(idActEco));
                vntCliente.setClnEstado(Boolean.TRUE);
                vntCliente.setClnFechaproceso(new Date());
                vntCliente = mcsfb.editarCliente(vntCliente);
                mostrarError("Grabación exitosa...!", 3);
//                vntCliente = mcsfb.getVntClienteSel();
//                TablaVntClienteSel.setVntCliente(vntCliente);
            } else {
                VntCliente vc = TablaVntClienteSel.getVntCliente();
                vc.setTdcId(mcsfb.getRfTipodocXId(idTipoDoc));
                vc.setClnIdentificacion(strCltDocumento);
                vc.setClnNombres(strCltNombres);
                
                vc.setSexId(mcsfb.getRfSexo(idSexo));
                vc.setClnNumhijos(intCltNumHijos);
                vc.setClnFechanace(datCltFechaNac);
                vc.setClnDiereccion(strCltDireccion);
                vc.setClnFijo(strCltTelFijo);
                vc.setClnCelular(strCltCelular);
                vc.setClnCorreoe(strCltCorreo);
                vc.setClnContacto(strCltContacto);
                vc.setTclId(mcsfb.getVntRfTipoclienteXId(idTipoCliente));
                vc.setAteId(mcsfb.getVntActeconomicaXId(idActEco));
                vc = mcsfb.editarCliente(vc);
                mostrarError("Información actualizada...!", 3);
            }
        } else {
            return;
        }
    }

    private void grabarDetalleCliente() {
        if (validaFormDetalleCliente()) {
            vntDetallecliente = new VntDetallecliente();
            if (getGestionVentaJSFBean().isBlnNuevoCliente() == true) {
                vntDetallecliente.setClnId(vntCliente);
            } else {
                vntDetallecliente.setClnId(TablaVntClienteSel.getVntCliente());
            }
            vntDetallecliente.setDclnNombres(strDetNombre1);
            
            vntDetallecliente.setSexId(mcsfb.getRfSexo(idSexoDet));
            vntDetallecliente.setPrtId(mcsfb.getRfParentesco(idParentesco));
            vntDetallecliente.setDclnFechanace(datDetFechaNac);
            vntDetallecliente = mcsfb.editarDetalleCliente(vntDetallecliente);
            limpiarFormulario();
            cargarListaDetalleCliente();
//            vntDetallecliente = mcsfb.getVntDetalleclienteSel();
        } else {
            return;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {

    }

    public void btnGrabarCliente_ActionEvent(ActionEvent ae) {
        grabarCliente();
        cargarListaClientes();
    }

    public void btnGrabarDetalleCliente_ActionEvent(ActionEvent ae) {
        grabarDetalleCliente();
        cargarListaDetalleCliente();
    }

//    public void btnVolverCliente_ActionEvent(ActionEvent ae) {
//        blnMostrarPanel = false;
//        cargarListaClientes();
//    }
    public void rowDtMostrarDetalle_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        TablaVntClienteSel = (TablaVntCliente) map.get("tvcs");
        blnMostrarPanel = true;
        cargarListaDetalleCliente();
        cargarListaParentesco();
        cargarListaSexoDetalle();
    }

    public void rowDtDetalleCliente_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntDetalleclienteSel = (TablaVntDetalleCliente) map.get("tvdcs");
        mcsfb.setVntDetalleclienteSel(tablaVntDetalleclienteSel.getVntDetallecliente());
        GestionVentaJSFBean gv = getGestionVentaJSFBean();
        if (gv != null) {
            gv.setTablaVntDetalleclienteSel(tablaVntDetalleclienteSel);
            gv.rowDtDetalleCliente_ActionEvent(ae);
        }
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
        if (idTipoDoc.equals("-1")) {
            mostrarError("Seleccione el tipo de documento", 1);
            return false;
        }
        if (strCltDocumento == null || strCltDocumento.equals("")) {
            mostrarError("Ingrese el número de identificación", 1);
            return false;
        }
        for (TablaVntCliente tvc : lstTablaVntCliente) {
            if (tvc.getVntCliente().getClnIdentificacion() == strCltDocumento) {
                mostrarError("El número de identificación ya existe", 1);
                return false;
            }
        }
        if (strCltNombres == null || strCltNombres.equals("")) {
            mostrarError("Ingrese los nombres del cliente", 1);
            return false;
        }
       
        if (idSexo.equals("-1")) {
            mostrarError("Seleccione el sexo del cliente", 1);
            return false;
        }
        if (strCltDireccion == null || strCltDireccion.equals("")) {
            mostrarError("Ingrese la dirección", 1);
            return false;
        }
        if (strCltCelular == null || strCltCelular.equals("")) {
            mostrarError("Ingrese un número celular", 1);
            return false;
        }
        if (idTipoCliente.equals(-1)) {
            mostrarError("Seleccione un tipo de cliente", 1);
            return false;
        }
        if (idActEco.equals(-1)) {
            mostrarError("Seleccione la actividad económica", 1);
            return false;
        }
        if (idTipoCliente.equals(1)) {
            if (idActEco.equals(5)) {
                mostrarError("El tipo de cliente es Corporativo, por lo cual debe seleccionar la actividad económica que aplique", 1);
                return false;
            }
        } else {
            if (!idActEco.equals(5)) {
                mostrarError("El tipo de cliente es Kids, para el cual no aplica actividad económica", 1);
                return false;
            }
        }
        return true;
    }

    private boolean validaFormDetalleCliente() {
        boolean error = false;
        if (strDetNombre1 == null || strDetNombre1.equals("")) {
            mostrarError("Ingrese el primer nombre", 1);
            error = true;
        }
        
        if (idParentesco.equals(-1)) {
            mostrarError("Seleccione el parentesco", 1);
            error = true;
        }
        if (idSexoDet.equals("-1")) {
            mostrarError("Seleccione el sexo", 1);
            error = true;
        }
        if (datDetFechaNac == null) {
            mostrarError("Ingrese la fecha de nacimiento", 1);
            error = true;
        }
        return !error;
    }

//    private boolean validarsDetalleCliente() {
//        boolean error = false;
//        if (stringIsEmpty(vntDetallecliente.getDclnNomb1())) {
//            mostrarError("detCliente", "Ingrese el nombre");
//            error = true;
//        }
//        if (stringIsEmpty(vntDetallecliente.getDclnApe1())) {
//            mostrarError("detCliente", "Ingrese el primer apellido");
//            error = true;
//        }
//        if (itemNotSelected(idParentesco)) {
//            mostrarError("detCliente", "Seleccione el parentezco");
//            error = true;
//        }
//        if (itemNotSelected(idSexoDet)) {
//            mostrarError("detCliente", "Seleccione el género");
//            error = true;
//        }
//        if (vntDetallecliente.getDclnFechanace() == null) {
//            mostrarError("detCliente", "Ingrese la fecha de nacimiento");
//            error = true;
//        }
//        return !error;
//    }
    protected boolean stringIsEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    protected boolean itemNotSelected(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Integer) {
            return (Integer) object == Integer.valueOf("-1");
        }
        if (object instanceof Long) {
            return (Long) object == Long.valueOf("-1");
        }
        if (object instanceof Double) {
            return (Double) object == Double.valueOf("-1");
        }
        return stringIsEmpty(String.valueOf(object)) || String.valueOf(object).equals("-1");
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">    
    /**
     * @return the vntCliente
     */
    public VntCliente getVntCliente() {
        return vntCliente;
    }

    /**
     * @param vntCliente the vntCliente to set
     */
    public void setVntCliente(VntCliente vntCliente) {
        this.vntCliente = vntCliente;
    }

    /**
     * @return the idTipoDoc
     */
    public String getIdTipoDoc() {
        return idTipoDoc;
    }

    /**
     * @param idTipoDoc the idTipoDoc to set
     */
    public void setIdTipoDoc(String idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    /**
     * @return the lstTipoDoc
     */
    public List<SelectItem> getLstTipoDoc() {
        return lstTipoDoc;
    }

    /**
     * @param lstTipoDoc the lstTipoDoc to set
     */
    public void setLstTipoDoc(List<SelectItem> lstTipoDoc) {
        this.lstTipoDoc = lstTipoDoc;
    }

    /**
     * @return the idTipoCliente
     */
    public Integer getIdTipoCliente() {
        return idTipoCliente;
    }

    /**
     * @param idTipoCliente the idTipoCliente to set
     */
    public void setIdTipoCliente(Integer idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    /**
     * @return the lstTipoCliente
     */
    public List<SelectItem> getLstTipoCliente() {
        return lstTipoCliente;
    }

    /**
     * @param lstTipoCliente the lstTipoCliente to set
     */
    public void setLstTipoCliente(List<SelectItem> lstTipoCliente) {
        this.lstTipoCliente = lstTipoCliente;
    }

    /**
     * @return the lstTablaVntCliente
     */
    public List<TablaVntCliente> getLstTablaVntCliente() {
        return lstTablaVntCliente;
    }

    /**
     * @param lstTablaVntCliente the lstTablaVntCliente to set
     */
    public void setLstTablaVntCliente(List<TablaVntCliente> lstTablaVntCliente) {
        this.lstTablaVntCliente = lstTablaVntCliente;
    }

    /**
     * @return the TablaVntClienteSel
     */
    public TablaVntCliente getTablaVntClienteSel() {
        return TablaVntClienteSel;
    }

    /**
     * @param TablaVntClienteSel the TablaVntClienteSel to set
     */
    public void setTablaVntClienteSel(TablaVntCliente TablaVntClienteSel) {
        this.TablaVntClienteSel = TablaVntClienteSel;
    }

    /**
     * @return the VntDetallecliente
     */
    public VntDetallecliente getVntDetallecliente() {
        return vntDetallecliente;
    }

    /**
     * @param VntDetallecliente the VntDetallecliente to set
     */
    public void setVntDetallecliente(VntDetallecliente VntDetallecliente) {
        this.vntDetallecliente = VntDetallecliente;
    }

    /**
     * @return the lstTablaVntDetalleCliente
     */
    public List<TablaVntDetalleCliente> getLstTablaVntDetalleCliente() {
        return lstTablaVntDetalleCliente;
    }

    /**
     * @param lstTablaVntDetalleCliente the lstTablaVntDetalleCliente to set
     */
    public void setLstTablaVntDetalleCliente(List<TablaVntDetalleCliente> lstTablaVntDetalleCliente) {
        this.lstTablaVntDetalleCliente = lstTablaVntDetalleCliente;
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

    public String getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(String idSexo) {
        this.idSexo = idSexo;
    }

    public List<SelectItem> getLstSexo() {
        return lstSexo;
    }

    public void setLstSexo(List<SelectItem> lstSexo) {
        this.lstSexo = lstSexo;
    }

    public Integer getIdParentesco() {
        return idParentesco;
    }

    public void setIdParentesco(Integer idParentesco) {
        this.idParentesco = idParentesco;
    }

    public List<SelectItem> getLstParentesco() {
        return lstParentesco;
    }

    public void setLstParentesco(List<SelectItem> lstParentesco) {
        this.lstParentesco = lstParentesco;
    }

    public String getIdSexoDet() {
        return idSexoDet;
    }

    public void setIdSexoDet(String idSexoDet) {
        this.idSexoDet = idSexoDet;
    }

    public List<SelectItem> getLstSexoDet() {
        return lstSexoDet;
    }

    public void setLstSexoDet(List<SelectItem> lstSexoDet) {
        this.lstSexoDet = lstSexoDet;
    }

    /**
     * @return the idActEco
     */
    public Integer getIdActEco() {
        return idActEco;
    }

    /**
     * @param idActEco the idActEco to set
     */
    public void setIdActEco(Integer idActEco) {
        this.idActEco = idActEco;
    }

    /**
     * @return the lstActividadEconomica
     */
    public List<SelectItem> getLstActividadEconomica() {
        return lstActividadEconomica;
    }

    /**
     * @param lstActividadEconomica the lstActividadEconomica to set
     */
    public void setLstActividadEconomica(List<SelectItem> lstActividadEconomica) {
        this.lstActividadEconomica = lstActividadEconomica;
    }

    /**
     * @return the blnMostrarActEconomica
     */
    public boolean isBlnMostrarActEconomica() {
        return blnMostrarActEconomica;
    }

    /**
     * @param blnMostrarActEconomica the blnMostrarActEconomica to set
     */
    public void setBlnMostrarActEconomica(boolean blnMostrarActEconomica) {
        this.blnMostrarActEconomica = blnMostrarActEconomica;
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

    /**
     * @return the strDetNombre1
     */
    public String getStrDetNombre1() {
        return strDetNombre1;
    }

    /**
     * @param strDetNombre1 the strDetNombre1 to set
     */
    public void setStrDetNombre1(String strDetNombre1) {
        this.strDetNombre1 = strDetNombre1;
    }

    
    /**
     * @return the datDetFechaNac
     */
    public Date getDatDetFechaNac() {
        return datDetFechaNac;
    }

    /**
     * @param datDetFechaNac the datDetFechaNac to set
     */
    public void setDatDetFechaNac(Date datDetFechaNac) {
        this.datDetFechaNac = datDetFechaNac;
    }

    /**
     * @return the strCltDocumento
     */
    public String getStrCltDocumento() {
        return strCltDocumento;
    }

    /**
     * @param strCltDocumento the strCltDocumento to set
     */
    public void setStrCltDocumento(String strCltDocumento) {
        this.strCltDocumento = strCltDocumento;
    }

    /**
     * @return the strCltNombres
     */
    public String getStrCltNombres() {
        return strCltNombres;
    }

    /**
     * @param strCltNombres the strCltNombres to set
     */
    public void setStrCltNombres(String strCltNombres) {
        this.strCltNombres = strCltNombres;
    }

   
    /**
     * @return the intCltNumHijos
     */
    public Integer getIntCltNumHijos() {
        return intCltNumHijos;
    }

    /**
     * @param intCltNumHijos the intCltNumHijos to set
     */
    public void setIntCltNumHijos(Integer intCltNumHijos) {
        this.intCltNumHijos = intCltNumHijos;
    }

    /**
     * @return the datCltFechaNac
     */
    public Date getDatCltFechaNac() {
        return datCltFechaNac;
    }

    /**
     * @param datCltFechaNac the datCltFechaNac to set
     */
    public void setDatCltFechaNac(Date datCltFechaNac) {
        this.datCltFechaNac = datCltFechaNac;
    }

    /**
     * @return the strCltDireccion
     */
    public String getStrCltDireccion() {
        return strCltDireccion;
    }

    /**
     * @param strCltDireccion the strCltDireccion to set
     */
    public void setStrCltDireccion(String strCltDireccion) {
        this.strCltDireccion = strCltDireccion;
    }

    /**
     * @return the strCltTelFijo
     */
    public String getStrCltTelFijo() {
        return strCltTelFijo;
    }

    /**
     * @param strCltTelFijo the strCltTelFijo to set
     */
    public void setStrCltTelFijo(String strCltTelFijo) {
        this.strCltTelFijo = strCltTelFijo;
    }

    /**
     * @return the strCltCelular
     */
    public String getStrCltCelular() {
        return strCltCelular;
    }

    /**
     * @param strCltCelular the strCltCelular to set
     */
    public void setStrCltCelular(String strCltCelular) {
        this.strCltCelular = strCltCelular;
    }

    /**
     * @return the strCltCorreo
     */
    public String getStrCltCorreo() {
        return strCltCorreo;
    }

    /**
     * @param strCltCorreo the strCltCorreo to set
     */
    public void setStrCltCorreo(String strCltCorreo) {
        this.strCltCorreo = strCltCorreo;
    }

    /**
     * @return the strCltContacto
     */
    public String getStrCltContacto() {
        return strCltContacto;
    }

    /**
     * @param strCltContacto the strCltContacto to set
     */
    public void setStrCltContacto(String strCltContacto) {
        this.strCltContacto = strCltContacto;
    }
    //</editor-fold>
}
