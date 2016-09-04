/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.evaluacion;

import com.pandora.mod.evaluacion.dao.EvalDescuento;
import com.pandora.web.base.TablaBaseGrid;

/**
 *
 * @author breyner.robles
 */
public class TablaDescuento extends TablaBaseGrid {

    private EvalDescuento descuento;

    public TablaDescuento() {
        this.seleccionado = false;
    }

    public TablaDescuento(EvalDescuento descuento) {
        this.descuento = descuento;
        this.seleccionado = false;
    }

    public EvalDescuento getDescuento() {
        return descuento;
    }

    public void setDescuento(EvalDescuento descuento) {
        this.descuento = descuento;
    }
    
    

}
