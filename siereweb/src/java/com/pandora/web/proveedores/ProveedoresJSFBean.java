/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.proveedores;

import com.pandora.web.base.BaseJSFBean;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Administrador
 */
@Named(value = "proveedoresJSFBean")
@SessionScoped
public class ProveedoresJSFBean extends BaseJSFBean  implements Serializable {

    /**
     * Creates a new instance of ProveedoresJSFBean
     */
    public ProveedoresJSFBean() {
    }

    @Override
    public void init() {
        
    }

    @Override
    public void limpiarVariables() {
       
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
       
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
       
    }
    
}
