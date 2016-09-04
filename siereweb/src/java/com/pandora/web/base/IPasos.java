/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.base;

import javax.faces.event.ActionEvent;

/**
 *
 * @author luis
 */
public interface IPasos {

    public boolean grabarPaso();

    public boolean validarForm();
    
    public void navegacionLateral_ActionEvent(ActionEvent ae);
}
