/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.adm.param;

import adm.sys.bean.ControlBandEntradaSFBean;
import adm.sys.dao.AdmCargo;
import com.pandora.adm.param.UsuarioSFBean;
import adm.sys.dao.AdmColaborador;
import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.RfTipodoc;
import com.pandora.adm.param.AdmParametrizacionSLBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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
public class UsuarioJSFBean extends BaseJSFBean implements Serializable, IPasos {

    /**
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    UsuarioSFBean usfb;
    @EJB
    AdmParametrizacionSLBean admParametrizacionSLBean;

    private UsuarioSFBean lookupUsuarioSFBean() {
        try {
            Context c = new InitialContext();
            return (UsuarioSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/UsuarioSFBean!com.pandora.adm.param.UsuarioSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    ControlBandEntradaSFBean cbesfb;

    private ControlBandEntradaSFBean lookupControlBandEntradaSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (ControlBandEntradaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/ControlBandEntradaSFBean!adm.sys.bean.ControlBandEntradaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private boolean blnMostrarPanel;
    private boolean blnNuevo;
    private boolean blnHabilitar;
    private Integer cpeId;
    private AdmColaborador admColaborador = new AdmColaborador();
    private List<SelectItem> lstTipoDoc = new ArrayList<>();
    private List<TablaAdmColaborador> lstTablaAdmColaborador = new ArrayList<>();
    private TablaAdmColaborador tablaAdmColaboradorSel = new TablaAdmColaborador();
    private List<TablaAdmColXEmp> lstTablaAdmColXEmp = new ArrayList<>();
    private TablaAdmColXEmp tablaAdmColXEmpSel = new TablaAdmColXEmp();
    private List<TablaAdmCargo> lstTablaAdmCargo = new ArrayList<>();
    private List<TablaAdmCrgXCol> lstTablaAdmCrgXCol = new ArrayList<>();
    private TablaAdmCrgXCol tablaAdmCrgXCol = new TablaAdmCrgXCol();
    private String idTipoDoc = "-1";
    private String strColDocumento;
    private String strColExpedicion;
    private String strColNombre1;
    private String strColNombre2;
    private String strColApellido1;
    private String strColApellido2;
    private String strColDireccion;
    private String strColTelefono1;
    private String strColTelefono2;
    private String strColCelular;
    private String strColEmail;
    private boolean blnColEstado;
    private String strBuscarUsr;
    private Date fechaNacimiento;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">
    @Override
    public void init() {
        usfb = lookupUsuarioSFBean();
        cbesfb = lookupControlBandEntradaSFBeanBean();
        cbesfb.setAdmColxempLog(pjsfb.getMssfb().getColxempLog());
        numPanel = 1;
        blnNuevo = true;
        blnHabilitar = false;
        blnMostrarPanel = false;
        idTipoDoc = "-1";
        cargarListaTipoDoc();
        cargarListaUsuario();
    }

    @Override
    public void limpiarVariables() {
        usfb.remove();
        blnMostrarPanel = false;
        lstTablaAdmColaborador.clear();
        limpiarCampos();
    }

    public void limpiarCampos() {
        idTipoDoc = "-1";
        strColDocumento = "";
        strColExpedicion = "";
        strColNombre1 = "";
        strColNombre2 = "";
        strColApellido1 = "";
        strColApellido2 = "";
        strColDireccion = "";
        strColTelefono1 = "";
        strColTelefono2 = "";
        strColCelular = "";
        strColEmail = "";
        blnColEstado = false;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">
    private void cargarListaTipoDoc() {
        lstTipoDoc.clear();
        lstTipoDoc.add(new SelectItem("-1", "SELECCIONE >>"));
        for (RfTipodoc rt : usfb.getLstRfTipodoc(true)) {
            lstTipoDoc.add(new SelectItem(rt.getTdcId(), rt.getTdcNombre()));
        }
    }

    private void cargarListaUsuario() {
        lstTablaAdmColaborador.clear();
        for (AdmColaborador colaborador : usfb.getLstAdmColaborador()) {
            TablaAdmColaborador tac = new TablaAdmColaborador();
            tac.setAdmColaborador(colaborador);
            lstTablaAdmColaborador.add(tac);
        }
    }

    private void cargarListaUsuarioXText() {
        lstTablaAdmColaborador.clear();
        for (AdmColaborador colaborador : admParametrizacionSLBean.getLstColaboradorXText(strBuscarUsr)) {
            TablaAdmColaborador tac = new TablaAdmColaborador();
            tac.setAdmColaborador(colaborador);
            lstTablaAdmColaborador.add(tac);
        }
    }

    private void cargarListaUsrXEmp() {
        lstTablaAdmColXEmp.clear();
        for (AdmColxemp colxemp : usfb.getLstAdmColxemp(cbesfb.getAdmColxempLog().getEmpId())) {
            TablaAdmColXEmp tacxe = new TablaAdmColXEmp();
            tacxe.setAdmColxemp(colxemp);
            lstTablaAdmColXEmp.add(tacxe);
        }
    }

    private void cargarListaCargoXEstado() {
        lstTablaAdmCargo.clear();
        for (AdmCargo cargo : usfb.getLstAdmCargoXEstado(true)) {
            TablaAdmCargo tac = new TablaAdmCargo();
            tac.setAdmCargo(cargo);
            lstTablaAdmCargo.add(tac);
        }
    }

    private void cargarListaCrgXUsr() {
        lstTablaAdmCrgXCol.clear();
        for (AdmCrgxcol crgxcol : usfb.getLstAdmCrgxcol(cpeId)) {
            TablaAdmCrgXCol tacxc = new TablaAdmCrgXCol();
            tacxc.setAdmCrgxcol(crgxcol);
            lstTablaAdmCrgXCol.add(tacxc);
        }
    }

    private void grabarUsuario() {
        if (validarForm()) {
            if (blnNuevo == true) {
                admColaborador = new AdmColaborador();
                admColaborador.setTdcId(usfb.getRfTipodocXId(idTipoDoc));
                admColaborador.setColCedula(strColDocumento);
                admColaborador.setColExpcedula(strColExpedicion);
                admColaborador.setColNombre1(strColNombre1);
                admColaborador.setColNombre2(strColNombre2);
                admColaborador.setColApellido1(strColApellido1);
                admColaborador.setColApellido2(strColApellido2);
                admColaborador.setColDireccion(strColDireccion);
                admColaborador.setColTelefono1(strColTelefono1);
                admColaborador.setColTelefono2(strColTelefono2);
                admColaborador.setColCelular(strColCelular);
                admColaborador.setColEmail(strColEmail);
                admColaborador.setColEst(blnColEstado);
                admColaborador.setColFechaNacimiento(fechaNacimiento);
                usfb.editarColaborador(admColaborador);
            } else {
                AdmColaborador ac = tablaAdmColaboradorSel.getAdmColaborador();
                ac.setTdcId(usfb.getRfTipodocXId(idTipoDoc));
//                ac.setColCedula(strColDocumento);
                ac.setColExpcedula(strColExpedicion);
                ac.setColNombre1(strColNombre1);
                ac.setColNombre2(strColNombre2);
                ac.setColApellido1(strColApellido1);
                ac.setColApellido2(strColApellido2);
                ac.setColDireccion(strColDireccion);
                ac.setColTelefono1(strColTelefono1);
                ac.setColTelefono2(strColTelefono2);
                ac.setColCelular(strColCelular);
                ac.setColEmail(strColEmail);
                ac.setColEst(blnColEstado);
                usfb.editarColaborador(ac);
            }
        } else {
            mostrarError("Error al grabar", 3);
            return;
        }
    }

    private void desactivarCargoXCol() {
        for (TablaAdmCrgXCol tacxc : lstTablaAdmCrgXCol) {
            tacxc.setAdmCrgxcol(usfb.editarCrgxcol(tablaAdmCrgXCol.getAdmCrgxcol()));

        }
    }

    private void eliminarCrgXUsr() {
        if (validarForm()) {
            for (TablaAdmCrgXCol tacxc : lstTablaAdmCrgXCol) {
                if (tacxc.isSeleccionado()) {
                        
                }
            }
        }
    }

    private void grabarCrgXUsr() {
        if (validarForm()) {

            for (TablaAdmCargo tac : lstTablaAdmCargo) {
                if (tac.isSeleccionado()) {
                    tablaAdmCrgXCol.getAdmCrgxcol().setCpeId(tablaAdmColXEmpSel.getAdmColxemp());
                    tablaAdmCrgXCol.getAdmCrgxcol().setCrgId(tac.getAdmCargo());
                    tablaAdmCrgXCol.getAdmCrgxcol().setCxcEst(true);
                    tablaAdmCrgXCol.getAdmCrgxcol().setCxcFcre(new Date());
                    boolean cargo = false;
                    for (TablaAdmCrgXCol tacxc : lstTablaAdmCrgXCol) {
                        if (tacxc.getAdmCrgxcol().getCrgId() != null
                                && tacxc.getAdmCrgxcol().getCrgId().getCrgId().equals(tablaAdmCrgXCol.getAdmCrgxcol().getCrgId().getCrgId())) {
                            cargo = true;
                            break;
                        }
                    }
                    if (!cargo) {
                        lstTablaAdmCrgXCol.add(tablaAdmCrgXCol);
                        usfb.editarCrgxcol(tablaAdmCrgXCol.getAdmCrgxcol());
                        tablaAdmCrgXCol = new TablaAdmCrgXCol();
                    }
                }
            }
            for (TablaAdmCargo tac : lstTablaAdmCargo) {
                if (tac.isSeleccionado()) {
                    tac.setSeleccionado(false);
                }
            }
        } else {
            mostrarError("Error al grabar", 1);

        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        switch (numPanel) {
            case 1:
                idTipoDoc = "-1";
                cargarListaTipoDoc();
                cargarListaUsuario();
                break;
            case 2:
                cargarListaUsrXEmp();
                break;
        }
        blnNuevo = true;
        blnHabilitar = false;
        blnMostrarPanel = false;
        admColaborador = new AdmColaborador();
        limpiarCampos();
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {

    }

    public void btnGrabarUsuario_ActionEvent(ActionEvent ae) {
        grabarUsuario();
        cargarListaUsuario();
        limpiarCampos();
        blnNuevo = true;
        blnHabilitar = false;
        admColaborador = new AdmColaborador();
    }

    public void btnNuevoUsuario_ActionEvent(ActionEvent ae) {
        limpiarCampos();
        blnNuevo = true;
        blnHabilitar = false;
        admColaborador = new AdmColaborador();
    }

    public void rowDtUsuario_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaAdmColaboradorSel = (TablaAdmColaborador) map.get("tcs");
        idTipoDoc = tablaAdmColaboradorSel.getAdmColaborador().getTdcId().getTdcId();
        strColDocumento = tablaAdmColaboradorSel.getAdmColaborador().getColCedula();
        strColExpedicion = tablaAdmColaboradorSel.getAdmColaborador().getColExpcedula();
        strColNombre1 = tablaAdmColaboradorSel.getAdmColaborador().getColNombre1();
        strColNombre2 = tablaAdmColaboradorSel.getAdmColaborador().getColNombre2();
        strColApellido1 = tablaAdmColaboradorSel.getAdmColaborador().getColApellido1();
        strColApellido2 = tablaAdmColaboradorSel.getAdmColaborador().getColApellido2();
        strColDireccion = tablaAdmColaboradorSel.getAdmColaborador().getColDireccion();
        strColTelefono1 = tablaAdmColaboradorSel.getAdmColaborador().getColTelefono1();
        strColTelefono2 = tablaAdmColaboradorSel.getAdmColaborador().getColTelefono2();
        strColCelular = tablaAdmColaboradorSel.getAdmColaborador().getColCelular();
        strColEmail = tablaAdmColaboradorSel.getAdmColaborador().getColEmail();
        blnColEstado = tablaAdmColaboradorSel.getAdmColaborador().getColEst();
        fechaNacimiento = tablaAdmColaboradorSel.getAdmColaborador().getColFechaNacimiento();
        blnNuevo = false;
        blnHabilitar = true;
    }

    public void btnAgregarCrgXUsr_ActionEvent(ActionEvent ae) {
        grabarCrgXUsr();
        cargarListaCrgXUsr();
    }

    public void btnVolverCrgXUsr_ActionEvent(ActionEvent ae) {
        cargarListaUsrXEmp();
        blnMostrarPanel = false;
    }

    public void headDtColXempBuscar_AE(ActionEvent ae) {

    }

    public void rowDtColXEmp_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaAdmColXEmpSel = (TablaAdmColXEmp) map.get("tcxe");
        cpeId = tablaAdmColXEmpSel.getAdmColxemp().getCpeId();
        blnMostrarPanel = true;
        cargarListaCargoXEstado();
        cargarListaCrgXUsr();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">
    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        cargarListaUsuarioXText();

    }

    @Override
    public boolean grabarPaso() {
        return false;
    }

    @Override
    public boolean validarForm() {
        switch (numPanel) {
            case 1:
                if (idTipoDoc.equals("-1")) {
                    mostrarError("Debe seleccionar el tipo de documento", 1);
                    return false;
                }
                if (strColDocumento == null || strColDocumento.equals("")) {
                    mostrarError("Ingrese el número de documento", 1);
                    return false;
                }
                if (strColNombre1 == null || strColNombre1.equals("")) {
                    mostrarError("Ingrese el primer nombre", 1);
                    return false;
                }
                if (strColApellido1 == null || strColApellido1.equals("")) {
                    mostrarError("Ingrese el primer apellido", 1);
                    return false;
                }
                if (strColDireccion == null || strColDireccion.equals("")) {
                    mostrarError("Ingrese la dirección", 1);
                    return false;
                }
                if (strColTelefono1 == null || strColTelefono1.equals("")) {
                    mostrarError("Ingrese el número de teléfono", 1);
                    return false;
                }
                break;
            case 2:
                int cargo = 0;
                for (TablaAdmCargo tac : lstTablaAdmCargo) {
                    if (tac.isSeleccionado()) {
                        cargo++;
                    }
                }
                if (cargo == 0) {
                    mostrarError("Debe seleccionar mínimo un cargo para asociar al usuario", 1);
                    return false;
                }
                break;
        }
        return true;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">    
    /**
     * @return the admColaborador
     */
    public AdmColaborador getAdmColaborador() {
        return admColaborador;
    }

    /**
     * @param admColaborador the admColaborador to set
     */
    public void setAdmColaborador(AdmColaborador admColaborador) {
        this.admColaborador = admColaborador;
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
     * @return the lstTablaAdmColaborador
     */
    public List<TablaAdmColaborador> getLstTablaAdmColaborador() {
        return lstTablaAdmColaborador;
    }

    /**
     * @param lstTablaAdmColaborador the lstTablaAdmColaborador to set
     */
    public void setLstTablaAdmColaborador(List<TablaAdmColaborador> lstTablaAdmColaborador) {
        this.lstTablaAdmColaborador = lstTablaAdmColaborador;
    }

    /**
     * @return the tablaAdmColaboradorSel
     */
    public TablaAdmColaborador getTablaAdmColaboradorSel() {
        return tablaAdmColaboradorSel;
    }

    /**
     * @param tablaAdmColaboradorSel the tablaAdmColaboradorSel to set
     */
    public void setTablaAdmColaboradorSel(TablaAdmColaborador tablaAdmColaboradorSel) {
        this.tablaAdmColaboradorSel = tablaAdmColaboradorSel;
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
     * @return the strColDocumento
     */
    public String getStrColDocumento() {
        return strColDocumento;
    }

    /**
     * @param strColDocumento the strColDocumento to set
     */
    public void setStrColDocumento(String strColDocumento) {
        this.strColDocumento = strColDocumento;
    }

    /**
     * @return the strColExpedicion
     */
    public String getStrColExpedicion() {
        return strColExpedicion;
    }

    /**
     * @param strColExpedicion the strColExpedicion to set
     */
    public void setStrColExpedicion(String strColExpedicion) {
        this.strColExpedicion = strColExpedicion;
    }

    /**
     * @return the strColNombre1
     */
    public String getStrColNombre1() {
        return strColNombre1;
    }

    /**
     * @param strColNombre1 the strColNombre1 to set
     */
    public void setStrColNombre1(String strColNombre1) {
        this.strColNombre1 = strColNombre1;
    }

    /**
     * @return the strColNombre2
     */
    public String getStrColNombre2() {
        return strColNombre2;
    }

    /**
     * @param strColNombre2 the strColNombre2 to set
     */
    public void setStrColNombre2(String strColNombre2) {
        this.strColNombre2 = strColNombre2;
    }

    /**
     * @return the strColApellido1
     */
    public String getStrColApellido1() {
        return strColApellido1;
    }

    /**
     * @param strColApellido1 the strColApellido1 to set
     */
    public void setStrColApellido1(String strColApellido1) {
        this.strColApellido1 = strColApellido1;
    }

    /**
     * @return the strColApellido2
     */
    public String getStrColApellido2() {
        return strColApellido2;
    }

    /**
     * @param strColApellido2 the strColApellido2 to set
     */
    public void setStrColApellido2(String strColApellido2) {
        this.strColApellido2 = strColApellido2;
    }

    /**
     * @return the strColDireccion
     */
    public String getStrColDireccion() {
        return strColDireccion;
    }

    /**
     * @param strColDireccion the strColDireccion to set
     */
    public void setStrColDireccion(String strColDireccion) {
        this.strColDireccion = strColDireccion;
    }

    /**
     * @return the strColTelefono1
     */
    public String getStrColTelefono1() {
        return strColTelefono1;
    }

    /**
     * @param strColTelefono1 the strColTelefono1 to set
     */
    public void setStrColTelefono1(String strColTelefono1) {
        this.strColTelefono1 = strColTelefono1;
    }

    /**
     * @return the strColTelefono2
     */
    public String getStrColTelefono2() {
        return strColTelefono2;
    }

    /**
     * @param strColTelefono2 the strColTelefono2 to set
     */
    public void setStrColTelefono2(String strColTelefono2) {
        this.strColTelefono2 = strColTelefono2;
    }

    /**
     * @return the strColCelular
     */
    public String getStrColCelular() {
        return strColCelular;
    }

    /**
     * @param strColCelular the strColCelular to set
     */
    public void setStrColCelular(String strColCelular) {
        this.strColCelular = strColCelular;
    }

    /**
     * @return the strColEmail
     */
    public String getStrColEmail() {
        return strColEmail;
    }

    /**
     * @param strColEmail the strColEmail to set
     */
    public void setStrColEmail(String strColEmail) {
        this.strColEmail = strColEmail;
    }

    /**
     * @return the blnColEstado
     */
    public boolean isBlnColEstado() {
        return blnColEstado;
    }

    /**
     * @param blnColEstado the blnColEstado to set
     */
    public void setBlnColEstado(boolean blnColEstado) {
        this.blnColEstado = blnColEstado;
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
     * @return the lstTablaAdmColXEmp
     */
    public List<TablaAdmColXEmp> getLstTablaAdmColXEmp() {
        return lstTablaAdmColXEmp;
    }

    /**
     * @param lstTablaAdmColXEmp the lstTablaAdmColXEmp to set
     */
    public void setLstTablaAdmColXEmp(List<TablaAdmColXEmp> lstTablaAdmColXEmp) {
        this.lstTablaAdmColXEmp = lstTablaAdmColXEmp;
    }

    /**
     * @return the cpeId
     */
    public Integer getCpeId() {
        return cpeId;
    }

    /**
     * @param cpeId the cpeId to set
     */
    public void setCpeId(Integer cpeId) {
        this.cpeId = cpeId;
    }

    /**
     * @return the tablaAdmColXEmpSel
     */
    public TablaAdmColXEmp getTablaAdmColXEmpSel() {
        return tablaAdmColXEmpSel;
    }

    /**
     * @param tablaAdmColXEmpSel the tablaAdmColXEmpSel to set
     */
    public void setTablaAdmColXEmpSel(TablaAdmColXEmp tablaAdmColXEmpSel) {
        this.tablaAdmColXEmpSel = tablaAdmColXEmpSel;
    }

    /**
     * @return the tablaAdmCrgXCol
     */
    public TablaAdmCrgXCol getTablaAdmCrgXCol() {
        return tablaAdmCrgXCol;
    }

    /**
     * @param tablaAdmCrgXCol the tablaAdmCrgXCol to set
     */
    public void setTablaAdmCrgXCol(TablaAdmCrgXCol tablaAdmCrgXCol) {
        this.tablaAdmCrgXCol = tablaAdmCrgXCol;
    }
    //</editor-fold>

    public String getStrBuscarUsr() {
        return strBuscarUsr;
    }

    public void setStrBuscarUsr(String strBuscarUsr) {
        this.strBuscarUsr = strBuscarUsr;
    }
}
