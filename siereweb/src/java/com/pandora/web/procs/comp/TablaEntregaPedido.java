/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.CmpEntregapedido;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaEntregaPedido extends TablaBaseGrid{
    
    private CmpEntregapedido cmpEntregapedido = new CmpEntregapedido();
    private String strObservacion;
    private Integer intCantRecibida;

    public TablaEntregaPedido() {
    }
    
    public TablaEntregaPedido(CmpEntregapedido pCmpEntregapedido){
        this.cmpEntregapedido = pCmpEntregapedido;
    }

    /**
     * @return the cmpEntregapedido
     */
    public CmpEntregapedido getCmpEntregapedido() {
        return cmpEntregapedido;
    }

    /**
     * @param cmpEntregapedido the cmpEntregapedido to set
     */
    public void setCmpEntregapedido(CmpEntregapedido cmpEntregapedido) {
        this.cmpEntregapedido = cmpEntregapedido;
    }

    /**
     * @return the strObservacion
     */
    public String getStrObservacion() {
        return strObservacion;
    }

    /**
     * @param strObservacion the strObservacion to set
     */
    public void setStrObservacion(String strObservacion) {
        this.strObservacion = strObservacion;
    }
    
    /**
     * @return the intCantRecibida
     */
    public Integer getIntCantRecibida() {
        return intCantRecibida;
    }

    /**
     * @param intCantRecibida the intCantRecibida to set
     */
    public void setIntCantRecibida(Integer intCantRecibida) {
        this.intCantRecibida = intCantRecibida;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.cmpEntregapedido);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaEntregaPedido other = (TablaEntregaPedido) obj;
        if (!Objects.equals(this.cmpEntregapedido, other.cmpEntregapedido)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaEntregaPedido{" + "cmpEntregapedido=" + cmpEntregapedido + '}';
    }
}
