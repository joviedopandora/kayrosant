/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.liquidacion;

import com.pandora.mod.liquidacion.dao.PgLiquidacion;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author Garcia Bosso
 */
public class TablaPendientePago extends TablaBaseGrid{
    private PgLiquidacion liquidacion;

    public TablaPendientePago() {
    }

    public TablaPendientePago(PgLiquidacion liquidacion) {
        this.liquidacion = liquidacion;
    }

    public PgLiquidacion getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(PgLiquidacion liquidacion) {
        this.liquidacion = liquidacion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.liquidacion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaPendientePago other = (TablaPendientePago) obj;
        if (!Objects.equals(this.liquidacion, other.liquidacion)) {
            return false;
        }
        return true;
    }

    
    
    
}
