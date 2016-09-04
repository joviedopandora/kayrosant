/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.liquidacion;
import com.pandora.mod.liquidacion.dao.PgLiquidacion;
import com.pandora.mod.liquidacion.dao.PgLiquidacionconsolidado;
import com.pandora.mod.liquidacion.dao.PgLiquidacionxcolaborador;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;
/**
 *
 * @author Garcia Bosso
 */
public class TablaPgLiquidacion extends TablaBaseGrid {
    
    
       private PgLiquidacion pgLiquidacion = new PgLiquidacion();
       private Integer procesarpin= null;
       
     public TablaPgLiquidacion() {
    }

     
      public TablaPgLiquidacion(PgLiquidacion pgLiquidacion) {
        this.pgLiquidacion = pgLiquidacion  ;
    }
    /**
     * @return the pgLiquidacion
     */
    public PgLiquidacion getPgLiquidacion() {
        return pgLiquidacion;
    }

    /**
     * @param pgLiquidacion the pgLiquidacion to set
     */
    public void setPgLiquidacion(PgLiquidacion pgLiquidacion) {
        this.pgLiquidacion = pgLiquidacion;
    }

    /**
     * @return the procesarpin
     */
    public Integer getProcesarpin() {
        return procesarpin;
    }

    /**
     * @param procesarpin the procesarpin to set
     */
    public void setProcesarpin(Integer procesarpin) {
        this.procesarpin = procesarpin;
    }
    
    
    
     @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.pgLiquidacion);
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
        final TablaPgLiquidacion other = (TablaPgLiquidacion) obj;
        return Objects.equals(this.pgLiquidacion, other.pgLiquidacion);
    }

}
