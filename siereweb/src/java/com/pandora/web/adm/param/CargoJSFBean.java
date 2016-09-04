/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.adm.param;

import adm.sys.dao.AdmCargo;
import com.pandora.adm.param.CargoSFBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
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
public class CargoJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    CargoSFBean csfb;

    private CargoSFBean lookupCargoSFBean() {
        try {
            Context c = new InitialContext();
            return (CargoSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/CargoSFBean!com.pandora.adm.param.CargoSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private boolean blnNuevo;
    private boolean blnHabilitar;
    private AdmCargo admCargo = new AdmCargo();
    private List<TablaAdmCargo> lstTablaAdmCargo = new ArrayList<>();
    private TablaAdmCargo tablaAdmCargoSel = new TablaAdmCargo();
    private String strCrgId;
    private String strCrgNombre;
    private String strCrgDescripcion;
    private boolean blnCrgEstado;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">
    @Override
    public void init() {
        csfb = lookupCargoSFBean();
        numPanel = 1;
        blnNuevo = true;
        blnHabilitar = false;
        cargarListaCargo();
    }

    @Override
    public void limpiarVariables() {
        csfb.remove();
        lstTablaAdmCargo.clear();
        limpiarCampos();
    }

    public void limpiarCampos() {
        strCrgId = "";
        strCrgNombre = "";
        strCrgDescripcion = "";
        blnCrgEstado = false;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">
    private void cargarListaCargo() {
        lstTablaAdmCargo.clear();
        for (AdmCargo cargo : csfb.getLstAdmCargo()) {
            TablaAdmCargo tac = new TablaAdmCargo();
            tac.setAdmCargo(cargo);
            lstTablaAdmCargo.add(tac);
        }
    }

    private void grabarCargo() {
        if (validarForm()) {
            if (blnNuevo == true) {
                admCargo = new AdmCargo();
                admCargo.setCrgId(strCrgId);
                admCargo.setCrgNombre(strCrgNombre);
                admCargo.setCrgDesc(strCrgDescripcion);
                admCargo.setCrgEst(blnCrgEstado);
                csfb.editarCargo(admCargo);
            } else {
                AdmCargo ac = tablaAdmCargoSel.getAdmCargo();
                ac.setCrgId(strCrgId);
                ac.setCrgNombre(strCrgNombre);
                ac.setCrgDesc(strCrgDescripcion);
                ac.setCrgEst(blnCrgEstado);
                csfb.editarCargo(ac);
            }
        } else {
            mostrarError("Error al grabar", 1);
            return;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnNuevo = true;
        blnHabilitar = false;
        admCargo = new AdmCargo();
        limpiarCampos();
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        
    }

    public void btnGrabarCargo_ActionEvent(ActionEvent ae) {
        grabarCargo();
        cargarListaCargo();
        limpiarCampos();
        blnNuevo = true;
        blnHabilitar = false;
        admCargo = new AdmCargo();
    }

    public void btnNuevoCargo_ActionEvent(ActionEvent ae) {
        limpiarCampos();
        blnNuevo = true;
        blnHabilitar = false;
        admCargo = new AdmCargo();
    }

    public void rowDtCargo_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaAdmCargoSel = (TablaAdmCargo) map.get("tcs");
        strCrgId = tablaAdmCargoSel.getAdmCargo().getCrgId();
        strCrgNombre = tablaAdmCargoSel.getAdmCargo().getCrgNombre();
        strCrgDescripcion = tablaAdmCargoSel.getAdmCargo().getCrgDesc();
        blnCrgEstado = tablaAdmCargoSel.getAdmCargo().getCrgEst();
        blnNuevo = false;
        blnHabilitar = true;
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
        if(strCrgId == null || strCrgId.equals("")){
            mostrarError("Ingrese el id", 1);
            return false;
        }
        if(strCrgNombre == null || strCrgDescripcion.equals("")){
            mostrarError("Ingrese el nombre", 1);
            return false;
        }
        return true;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
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
     * @return the blnHabilitar
     */
    public boolean isBlnHabilitar() {
        return blnHabilitar;
    }

    /**
     * @param blnHabilitar the blnHabilitar to set
     */
    public void setBlnHabilitar(boolean blnHabilitar) {
        this.blnHabilitar = blnHabilitar;
    }

    /**
     * @return the admCargo
     */
    public AdmCargo getAdmCargo() {
        return admCargo;
    }

    /**
     * @param admCargo the admCargo to set
     */
    public void setAdmCargo(AdmCargo admCargo) {
        this.admCargo = admCargo;
    }

    /**
     * @return the lstTablaAdmCargo
     */
    public List<TablaAdmCargo> getLstTablaAdmCargo() {
        return lstTablaAdmCargo;
    }

    /**
     * @param lstTablaAdmCargo the lstTablaAdmCargo to set
     */
    public void setLstTablaAdmCargo(List<TablaAdmCargo> lstTablaAdmCargo) {
        this.lstTablaAdmCargo = lstTablaAdmCargo;
    }

    /**
     * @return the tablaAdmCargoSel
     */
    public TablaAdmCargo getTablaAdmCargoSel() {
        return tablaAdmCargoSel;
    }

    /**
     * @param tablaAdmCargoSel the tablaAdmCargoSel to set
     */
    public void setTablaAdmCargoSel(TablaAdmCargo tablaAdmCargoSel) {
        this.tablaAdmCargoSel = tablaAdmCargoSel;
    }

    /**
     * @return the strCrgId
     */
    public String getStrCrgId() {
        return strCrgId;
    }

    /**
     * @param strCrgId the strCrgId to set
     */
    public void setStrCrgId(String strCrgId) {
        this.strCrgId = strCrgId;
    }

    /**
     * @return the strCrgNombre
     */
    public String getStrCrgNombre() {
        return strCrgNombre;
    }

    /**
     * @param strCrgNombre the strCrgNombre to set
     */
    public void setStrCrgNombre(String strCrgNombre) {
        this.strCrgNombre = strCrgNombre;
    }

    /**
     * @return the strCrgDescripcion
     */
    public String getStrCrgDescripcion() {
        return strCrgDescripcion;
    }

    /**
     * @param strCrgDescripcion the strCrgDescripcion to set
     */
    public void setStrCrgDescripcion(String strCrgDescripcion) {
        this.strCrgDescripcion = strCrgDescripcion;
    }

    /**
     * @return the blnCrgEstado
     */
    public boolean isBlnCrgEstado() {
        return blnCrgEstado;
    }

    /**
     * @param blnCrgEstado the blnCrgEstado to set
     */
    public void setBlnCrgEstado(boolean blnCrgEstado) {
        this.blnCrgEstado = blnCrgEstado;
    }
    //</editor-fold>
}
