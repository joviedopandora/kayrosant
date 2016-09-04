/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.liquidacion;

import com.icesoft.faces.context.Resource;
import com.pandora.mod.liquidacion.dao.PgLiquidacionconsolidado;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author Garcia Bosso
 */
public class TablaConsolidadoLiquidacion extends TablaBaseGrid {

    private PgLiquidacionconsolidado liquidacionconsolidado;
    private com.icesoft.faces.context.Resource jasperResourcePDF;
    public String getNameFile(){
        return liquidacionconsolidado.getNombreLiquidaconso().replaceAll("/", "-");
    }

    public TablaConsolidadoLiquidacion() {
    }

    public TablaConsolidadoLiquidacion(PgLiquidacionconsolidado liquidacionconsolidado) {
        this.liquidacionconsolidado = liquidacionconsolidado;
    }

    public PgLiquidacionconsolidado getLiquidacionconsolidado() {
        return liquidacionconsolidado;
    }

    public void setLiquidacionconsolidado(PgLiquidacionconsolidado liquidacionconsolidado) {
        this.liquidacionconsolidado = liquidacionconsolidado;
    }

    public Resource getJasperResourcePDF() {
        return jasperResourcePDF;
    }

    public void setJasperResourcePDF(Resource jasperResourcePDF) {
        this.jasperResourcePDF = jasperResourcePDF;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.liquidacionconsolidado);
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
        final TablaConsolidadoLiquidacion other = (TablaConsolidadoLiquidacion) obj;
        if (!Objects.equals(this.liquidacionconsolidado, other.liquidacionconsolidado)) {
            return false;
        }
        return true;
    }

    
}
