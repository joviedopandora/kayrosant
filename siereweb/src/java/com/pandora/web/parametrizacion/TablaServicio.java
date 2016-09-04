/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.parametrizacion;

import com.pandora.mod.venta.dao.VntServicio;
import com.pandora.web.base.TablaBaseGrid;

/**
 *
 * @author HP
 */
public class TablaServicio extends TablaBaseGrid{
    
    private VntServicio servicio;

    public VntServicio getServicio() {
        return servicio;
    }

    public void setServicio(VntServicio servicio) {
        this.servicio = servicio;
    }
    
    
    
    
    
}
