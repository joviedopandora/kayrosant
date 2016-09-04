/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.evaluacion;

import com.pandora.mod.evaluacion.dao.EvalBonificacion;
import com.pandora.web.base.TablaBaseGrid;

/**
 *
 * @author breyner.robles
 */
public class TablaBonificacion extends TablaBaseGrid {

    private EvalBonificacion bonificacion = null;

    public TablaBonificacion() {
         this.seleccionado = false;
    }

    public TablaBonificacion(EvalBonificacion bonificacion) {
        this.bonificacion =bonificacion;
        this.seleccionado = false;
    }
    
    

    public EvalBonificacion getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(EvalBonificacion bonificacion) {
        this.bonificacion = bonificacion;
    }

    
}
