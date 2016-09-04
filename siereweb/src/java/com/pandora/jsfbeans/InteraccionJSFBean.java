/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.jsfbeans;

import adm.sys.bean.ManejoSessionSFBean;
import com.pandora.web.base.BaseJSFBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import utilidades.Seguridad;

/**
 *
 * @author breyner.robles
 */
@SessionScoped
@Named
public class InteraccionJSFBean extends BaseJSFBean implements Serializable {

    private String strClaveAnterior = null;
    private String strClave = null;
    private String strClaveConfimar = null;

    private ManejoSessionSFBean lookupManejoSessionSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (ManejoSessionSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/ManejoSessionSFBean!adm.sys.bean.ManejoSessionSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    @EJB
    private ManejoSessionSFBean mssfb;

    @Override
    public void init() {
        mssfb = lookupManejoSessionSFBeanBean();
        strClaveAnterior = null;
        strClave = null;
        strClaveConfimar = null;
    }

    @Override
    public void limpiarVariables() {
        strClaveAnterior = null;
        strClave = null;
        strClaveConfimar = null;
        //  Seguridad.hashPasswordSha512(strClave)
    }

    public String navegacion() {
        return strClaveAnterior;
    }

    public void guardar_ActionEvent(ActionEvent ae) {
        boolean validador = false;
        strClaveAnterior = "";
        /* if (strClaveAnterior == null || strClaveAnterior.equals("")) {
         validador = true;
         mostrarError("Contraseña Anterior es requerida", 1);
         }*/
        if (strClave == null || strClave.equals("")) {
            mostrarError("Nueva Contraseña es requerida", 1);
            validador = true;
        }
        if (strClaveConfimar == null || strClaveConfimar.equals("")) {
            mostrarError("Confirmación de Contraseña es requerida", 1);
            validador = true;
        }
        if (validador) {
            return;
        }
        /* if (!Seguridad.hashPasswordSha512(strClaveAnterior).equals(getPrincipalJSFBean().getColxempLog().getCpeClave())) {
         mostrarError("Contraseña anterior no es valida", 1);
         validador = true;
         }
         */
        if (!strClave.equals(strClaveConfimar)) {
            mostrarError("Nueva contraseña y confirmación de contraseña  no coinciden", 1);
            validador = true;
        }
        if (validador) {
            return;
        }
        getPrincipalJSFBean().getColxempLog().setCpeClave(Seguridad.hashPasswordSha512(strClave));
        getPrincipalJSFBean().setColxempLog(mssfb.editarAdmColxemp(getPrincipalJSFBean().getColxempLog()));
        mostrarError("Cambio Contraseña exitoso", 3);
        strClaveAnterior = "bandEntrada";
        getPrincipalJSFBean().navRiesgos_ActionEvent(ae);
        //return "bandEntrada";
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the strClaveAnterior
     */
    public String getStrClaveAnterior() {
        return strClaveAnterior;
    }

    /**
     * @param strClaveAnterior the strClaveAnterior to set
     */
    public void setStrClaveAnterior(String strClaveAnterior) {
        this.strClaveAnterior = strClaveAnterior;
    }

    /**
     * @return the strClave
     */
    public String getStrClave() {
        return strClave;
    }

    /**
     * @param strClave the strClave to set
     */
    public void setStrClave(String strClave) {
        this.strClave = strClave;
    }

    /**
     * @return the strClaveConfimar
     */
    public String getStrClaveConfimar() {
        return strClaveConfimar;
    }

    /**
     * @param strClaveConfimar the strClaveConfimar to set
     */
    public void setStrClaveConfimar(String strClaveConfimar) {
        this.strClaveConfimar = strClaveConfimar;
    }
}
