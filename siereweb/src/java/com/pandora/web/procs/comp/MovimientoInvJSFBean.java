/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.cmp.TransaccionInvSFBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author lfchacon
 */
@Named
public class MovimientoInvJSFBean extends BaseJSFBean
implements IPasos{

    TransaccionInvSFBean tisfb ;
     private TransaccionInvSFBean lookupTransaccionInvBean() {
        try {
            Context c = new InitialContext();
            return (TransaccionInvSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/TransaccionInvSFBean!com.pandora.adm.cmp.TransaccionInvSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    @Inject
    PrincipalJSFBean pjsfb;
    
    
    @Override
    public void init() {
        tisfb = lookupTransaccionInvBean();        
    }

    @Override
    public void limpiarVariables() {        
        tisfb.remove();        
    }

    @Override
    public boolean grabarPaso() {
        return true;
    }

    @Override
    public boolean validarForm() {
        return true;
    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.parseInt(String.valueOf(ae.getComponent().getAttributes().get("numPanel")));
    }
    
    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
}
