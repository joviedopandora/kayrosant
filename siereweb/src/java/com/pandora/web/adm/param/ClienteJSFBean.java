/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.adm.param;

import adm.sys.dao.RfTipodoc;
import com.pandora.adm.param.AdmParametrizacionSLBean;
import com.pandora.adm.param.ClienteSFBean;
import com.pandora.consulta.bean.PcsCotizacionSFBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.venta.dao.RfCargocontacto;
import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntDetallecliente;
import com.pandora.mod.venta.dao.VntRfTipocliente;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.venta.TablaVntDetalleCliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
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
public class ClienteJSFBean extends BaseJSFBean implements Serializable, IPasos {

    /**
     * @return the crgContactoEst
     */
    public boolean isCrgContactoEst() {
        return crgContactoEst;
    }

    /**
     * @param crgContactoEst the crgContactoEst to set
     */
    public void setCrgContactoEst(boolean crgContactoEst) {
        this.crgContactoEst = crgContactoEst;
    }

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    ClienteSFBean csfb;
    @EJB
    private AdmParametrizacionSLBean admParametrizacionSLBean;

    private ClienteSFBean lookupClienteSFBean() {
        try {
            Context c = new InitialContext();
            return (ClienteSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/ClienteSFBean!com.pandora.adm.param.ClienteSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private boolean blnNuevo;
    private boolean blnHabilitar;
    private VntCliente vntCliente = new VntCliente();
    private List<SelectItem> lstTipoDoc = new ArrayList<>();
    private List<SelectItem> lstTipoCliente = new ArrayList<>();
    private List<TablaVntCliente> lstTablaVntCliente = new ArrayList<>();
    private TablaVntCliente tablaVntClienteSel = new TablaVntCliente();
    private List<TablaVntDetalleCliente> lstTablaVntDetalleClientes = new ArrayList<>();
    private TablaVntDetalleCliente tablaVntDetalleClienteSel = new TablaVntDetalleCliente();
    private String idTipoDoc = "-1";
    private Integer idTipoCliente = -1;
    private String strCltDocumento;
    private String strCltNombres;
    private String strCltApellidos;
    private String strCltDireccion;
    private String strCltTelefono;
    private String strCltCelular;
    private String strCltEmail;
    private String strCltContacto;
    private boolean blnCltEstado;
    private boolean blnCltNuevo;

    private Integer crgConcattoId;
    private String crgContactoDesc;
    private boolean crgContactoEst;
    private List<TablaRfCargoContacto> lstTablaCargocontactos = new ArrayList<>();
    private TablaRfCargoContacto tablaRfCargoContactoSel;
    private String strBuscarCln;

    public String getStrBuscarCln() {
        return strBuscarCln;
    }

    public void setStrBuscarCln(String strBuscarCln) {
        this.strBuscarCln = strBuscarCln;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">
    @Override
    public void init() {
        csfb = lookupClienteSFBean();
        numPanel = 1;
        blnNuevo = true;
        blnHabilitar = false;
        idTipoDoc = "-1";
        idTipoCliente = -1;
        cargarListaTipoDoc();
        cargarListaTipoCliente();
        cargarListaCliente();
        cargarListaCargoscontacto();
    }

    @Override
    public void limpiarVariables() {
        csfb.remove();
        lstTablaVntCliente.clear();
        limpiarCampos();
    }

    public void limpiarCampos() {
        idTipoDoc = "-1";
        strCltDocumento = "";
        strCltNombres = "";
        strCltApellidos = "";
        strCltDireccion = "";
        strCltTelefono = "";
        strCltCelular = "";
        strCltEmail = "";
        strCltContacto = "";
        blnCltEstado = false;
        blnCltNuevo = false;
        idTipoCliente = -1;

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">
    private void cargarListaTipoDoc() {
        lstTipoDoc.clear();
        lstTipoDoc.add(new SelectItem("-1", "SELECCIONE >>"));
        for (RfTipodoc td : csfb.getLstRfTipodoc(true)) {
            lstTipoDoc.add(new SelectItem(td.getTdcId(), td.getTdcNombre()));
        }
    }

    private void cargarListaTipoCliente() {
        lstTipoCliente.clear();
        lstTipoCliente.add(new SelectItem(-1, "SELECCIONE >>"));
        for (VntRfTipocliente tc : csfb.getLstVntRfTipocliente(true)) {
            lstTipoCliente.add(new SelectItem(tc.getTclId(), tc.getTclNombre()));
        }
    }

    private void cargarListaCliente() {
        lstTablaVntCliente.clear();
        for (VntCliente cliente : csfb.getLstAdmCliente()) {
            TablaVntCliente tvc = new TablaVntCliente();
            tvc.setVntCliente(cliente);
            lstTablaVntCliente.add(tvc);
        }
    }

    private void cargarListaClienteXTexto() {
        lstTablaVntCliente.clear();
        for (VntCliente cliente : admParametrizacionSLBean.getLstClienteXTexto(strBuscarCln)) {
            TablaVntCliente tvc = new TablaVntCliente();
            tvc.setVntCliente(cliente);
            lstTablaVntCliente.add(tvc);
        }
    }

    private void cargarListaCargoscontacto() {
        lstTablaCargocontactos.clear();
        for (RfCargocontacto rc : admParametrizacionSLBean.getLstCargocontactos()) {
            lstTablaCargocontactos.add(new TablaRfCargoContacto(rc));
        }
    }

    private void grabarCliente() {
        if (validarForm()) {
            if (blnCltNuevo == true) {
                vntCliente = new VntCliente();
                vntCliente.setTdcId(csfb.getRfTipodocXId(idTipoDoc));
                vntCliente.setClnIdentificacion(strCltDocumento);
                vntCliente.setClnNombres(strCltNombres);

                vntCliente.setClnDiereccion(strCltDireccion);
                vntCliente.setClnFijo(strCltTelefono);
                vntCliente.setClnCelular(strCltCelular);
                vntCliente.setClnCorreoe(strCltEmail);
                vntCliente.setClnContacto(strCltContacto);
                vntCliente.setClnEstado(blnCltEstado);
                vntCliente.setClnNuevo(blnCltNuevo);
                vntCliente.setTclId(csfb.getVntRfTipocliente(idTipoCliente));
                vntCliente.setClnFechaproceso(new Date());
                csfb.editarCliente(vntCliente);
            } else {
                VntCliente vc = tablaVntClienteSel.getVntCliente();
                vc.setTdcId(csfb.getRfTipodocXId(idTipoDoc));
//                vntCliente.setClnIdentificacion(strCltDocumento);
                vc.setClnNombres(strCltNombres);

                vc.setClnDiereccion(strCltDireccion);
                vc.setClnFijo(strCltTelefono);
                vc.setClnCelular(strCltCelular);
                vc.setClnCorreoe(strCltEmail);
                vc.setClnContacto(strCltContacto);
                vc.setClnEstado(blnCltEstado);
                vc.setClnNuevo(blnCltNuevo);
                vc.setTclId(csfb.getVntRfTipocliente(idTipoCliente));
                vc.setClnFechaproceso(new Date());
                csfb.editarCliente(vc);
            }
        } else {
            mostrarError("Error al grabar", 1);

        }
    }

    //<editor-fold defaultstate="collapsed" desc="Detalle cliente">
    private void cargarDetalleClnXCliente() {
        lstTablaVntDetalleClientes.clear();
        for (VntDetallecliente vdc : admParametrizacionSLBean.getLstDetalleClnXCln(tablaVntClienteSel.getVntCliente().getClnId())) {
            TablaVntDetalleCliente tvdc = new TablaVntDetalleCliente(vdc);
            lstTablaVntDetalleClientes.add(tvdc);
        }
    }
//</editor-fold>

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    //<editor-fold defaultstate="collapsed" desc="Eventos contacto">
    public void rowDtCargo_ActionEvent(ActionEvent ae) {
        tablaRfCargoContactoSel = (TablaRfCargoContacto) ae.getComponent().getAttributes().get("itemCargos");
        crgConcattoId = tablaRfCargoContactoSel.getCargocontacto().getCargoId();
        crgContactoDesc = tablaRfCargoContactoSel.getCargocontacto().getCargoDesc();
        crgContactoEst = tablaRfCargoContactoSel.getCargocontacto().getCargoEst();
        blnHabilitar = false;
        blnCltNuevo = false;

    }

//</editor-fold>
    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnNuevo = true;
        blnHabilitar = false;
        vntCliente = new VntCliente();
        limpiarCampos();
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {

    }

    public void btnGrabarCargoConct_ActionEvent(ActionEvent ae) {
        if (blnCltNuevo) {
            if (crgContactoDesc != null && crgContactoDesc.trim().length() > 5) {
                RfCargocontacto rc = new RfCargocontacto();
                rc.setCargoDesc(crgContactoDesc);
                rc.setCargoEst(crgContactoEst);
                rc = admParametrizacionSLBean.grabarCargoCliente(rc);
                tablaRfCargoContactoSel.setCargocontacto(rc);
                lstTablaCargocontactos.add(tablaRfCargoContactoSel);
                mostrarError("Cargo grabado exitosamente", 3);
                crgConcattoId = null;
                crgContactoDesc = "";
                crgContactoEst = false;
                blnCltNuevo = true;
            } else {
                mostrarError("Debe colocar la descripción del nuevo cargo");
            }

        } else {
            tablaRfCargoContactoSel.getCargocontacto().setCargoDesc(crgContactoDesc);
            tablaRfCargoContactoSel.getCargocontacto().setCargoEst(crgContactoEst);
            tablaRfCargoContactoSel.setCargocontacto(admParametrizacionSLBean.grabarCargoCliente(tablaRfCargoContactoSel.getCargocontacto()));
            mostrarError("Cargo actualizado exitosamente", 3);
        }

    }

    public void btnNuevoCargoConct_ActionEvent(ActionEvent ae) {
        crgConcattoId = null;
        crgContactoDesc = "";
        crgContactoEst = false;
        tablaRfCargoContactoSel = new TablaRfCargoContacto();
        blnCltNuevo = true;
        blnHabilitar = true;
    }

    public void btnGrabarCliente_ActionEvent(ActionEvent ae) {
        grabarCliente();
        cargarListaCliente();
        limpiarCampos();
        blnNuevo = true;
        blnHabilitar = false;
        vntCliente = new VntCliente();
    }

    public void btnNuevoCliente_ActionEvent(ActionEvent ae) {
        limpiarCampos();
        blnNuevo = true;
        blnHabilitar = false;
        vntCliente = new VntCliente();
    }

    public void rowDtDetCln_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntDetalleClienteSel = (TablaVntDetalleCliente) map.get("dclnsel");
    }

    public void rowDtDetClnEstado_AE(ValueChangeEvent ae) {
          Map map = ae.getComponent().getAttributes();
        tablaVntDetalleClienteSel = (TablaVntDetalleCliente) map.get("dclnchk");
        
       if(tablaVntDetalleClienteSel!=null){
           tablaVntDetalleClienteSel.getVntDetallecliente().setDclnEstado((Boolean)ae.getNewValue());
         tablaVntDetalleClienteSel.setVntDetallecliente(admParametrizacionSLBean.
                editarDetalleCliente(tablaVntDetalleClienteSel.getVntDetallecliente()));
       }
      
    }

    public void rowDtCliente_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntClienteSel = (TablaVntCliente) map.get("tcs");
        idTipoDoc = tablaVntClienteSel.getVntCliente().getTdcId().getTdcId();
        strCltDocumento = tablaVntClienteSel.getVntCliente().getClnIdentificacion();
        strCltNombres = tablaVntClienteSel.getVntCliente().getClnNombres();

        strCltDireccion = tablaVntClienteSel.getVntCliente().getClnDiereccion();
        strCltTelefono = tablaVntClienteSel.getVntCliente().getClnFijo();
        strCltCelular = tablaVntClienteSel.getVntCliente().getClnCelular();
        strCltEmail = tablaVntClienteSel.getVntCliente().getClnCorreoe();
        strCltContacto = tablaVntClienteSel.getVntCliente().getClnContacto();
        idTipoCliente = tablaVntClienteSel.getVntCliente().getTclId().getTclId();
        blnCltEstado = tablaVntClienteSel.getVntCliente().getClnEstado();
        blnCltNuevo = tablaVntClienteSel.getVntCliente().getClnNuevo();
        blnCltNuevo = false;
        blnHabilitar = true;
        switch (numPanel) {
            case 1:
                cargarDetalleClnXCliente();
                break;
        }

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">
    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        cargarListaClienteXTexto();
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
            mostrarError("Ingrese el número de documento", 1);
            return false;
        }
        if (strCltNombres == null || strCltNombres.equals("")) {
            mostrarError("Ingrese los nombres", 1);
            return false;
        }
        if (strCltApellidos == null || strCltApellidos.equals("")) {
            mostrarError("Ingrese los apellidos", 1);
            return false;
        }
        if (strCltDireccion == null || strCltDireccion.equals("")) {
            mostrarError("Ingrese la dirección", 1);
            return false;
        }
        if (strCltTelefono == null || strCltTelefono.equals("")) {
            mostrarError("Ingrese el número de teléfono", 1);
            return false;
        }
        if (idTipoCliente.equals(-1)) {
            mostrarError("Seleccione el tipo de cliente", 1);
            return false;
        }
        return true;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
    public List<TablaVntDetalleCliente> getLstTablaVntDetalleClientes() {
        return lstTablaVntDetalleClientes;
    }

    public void setLstTablaVntDetalleClientes(List<TablaVntDetalleCliente> lstTablaVntDetalleClientes) {
        this.lstTablaVntDetalleClientes = lstTablaVntDetalleClientes;
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
     * @return the tablaVntClienteSel
     */
    public TablaVntCliente getTablaVntClienteSel() {
        return tablaVntClienteSel;
    }

    /**
     * @param tablaVntClienteSel the tablaVntClienteSel to set
     */
    public void setTablaVntClienteSel(TablaVntCliente tablaVntClienteSel) {
        this.tablaVntClienteSel = tablaVntClienteSel;
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
     * @return the strCltApellidos
     */
    public String getStrCltApellidos() {
        return strCltApellidos;
    }

    /**
     * @param strCltApellidos the strCltApellidos to set
     */
    public void setStrCltApellidos(String strCltApellidos) {
        this.strCltApellidos = strCltApellidos;
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
     * @return the strCltTelefono
     */
    public String getStrCltTelefono() {
        return strCltTelefono;
    }

    /**
     * @param strCltTelefono the strCltTelefono to set
     */
    public void setStrCltTelefono(String strCltTelefono) {
        this.strCltTelefono = strCltTelefono;
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
     * @return the strCltEmail
     */
    public String getStrCltEmail() {
        return strCltEmail;
    }

    /**
     * @param strCltEmail the strCltEmail to set
     */
    public void setStrCltEmail(String strCltEmail) {
        this.strCltEmail = strCltEmail;
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

    /**
     * @return the blnCltEstado
     */
    public boolean isBlnCltEstado() {
        return blnCltEstado;
    }

    /**
     * @param blnCltEstado the blnCltEstado to set
     */
    public void setBlnCltEstado(boolean blnCltEstado) {
        this.blnCltEstado = blnCltEstado;
    }

    /**
     * @return the blnCltNuevo
     */
    public boolean isBlnCltNuevo() {
        return blnCltNuevo;
    }

    /**
     * @param blnCltNuevo the blnCltNuevo to set
     */
    public void setBlnCltNuevo(boolean blnCltNuevo) {
        this.blnCltNuevo = blnCltNuevo;
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
     * @return the TablaVntDetalleClienteSel
     */
    public TablaVntDetalleCliente getTablaVntDetalleClienteSel() {
        return tablaVntDetalleClienteSel;
    }

    /**
     * @param TablaVntDetalleClienteSel the TablaVntDetalleClienteSel to set
     */
    public void setTablaVntDetalleClienteSel(TablaVntDetalleCliente TablaVntDetalleClienteSel) {
        this.tablaVntDetalleClienteSel = TablaVntDetalleClienteSel;
    }
    //</editor-fold>

    /**
     * @return the lstTablaCargocontactos
     */
    public List<TablaRfCargoContacto> getLstTablaCargocontactos() {
        return lstTablaCargocontactos;
    }

    /**
     * @param lstTablaCargocontactos the lstTablaCargocontactos to set
     */
    public void setLstTablaCargocontactos(List<TablaRfCargoContacto> lstTablaCargocontactos) {
        this.lstTablaCargocontactos = lstTablaCargocontactos;
    }

    /**
     * @return the crgContactoDesc
     */
    public String getCrgContactoDesc() {
        return crgContactoDesc;
    }

    /**
     * @param crgContactoDesc the crgContactoDesc to set
     */
    public void setCrgContactoDesc(String crgContactoDesc) {
        this.crgContactoDesc = crgContactoDesc;
    }

    /**
     * @return the crgConcattoId
     */
    public Integer getCrgConcattoId() {
        return crgConcattoId;
    }

    /**
     * @param crgConcattoId the crgConcattoId to set
     */
    public void setCrgConcattoId(Integer crgConcattoId) {
        this.crgConcattoId = crgConcattoId;
    }

    /**
     * @return the tablaRfCargoContactoSel
     */
    public TablaRfCargoContacto getTablaRfCargoContactoSel() {
        return tablaRfCargoContactoSel;
    }

    /**
     * @param tablaRfCargoContactoSel the tablaRfCargoContactoSel to set
     */
    public void setTablaRfCargoContactoSel(TablaRfCargoContacto tablaRfCargoContactoSel) {
        this.tablaRfCargoContactoSel = tablaRfCargoContactoSel;
    }
}
