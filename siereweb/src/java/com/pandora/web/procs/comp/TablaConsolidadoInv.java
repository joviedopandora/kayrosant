/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author carlos
 */
public class TablaConsolidadoInv extends TablaBaseGrid {

    private String prdNombre;
    private String pspNombre;
    private Long invCant;
    private String prdypsp;

    public TablaConsolidadoInv() {
        this.prdypsp = this.prdNombre + this.pspNombre;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.prdypsp);
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
        final TablaConsolidadoInv other = (TablaConsolidadoInv) obj;
        if (!Objects.equals(this.prdypsp, other.prdypsp)) {
            return false;
        }
        return true;
    }

    public String getPrdNombre() {
        return prdNombre;
    }

    public void setPrdNombre(String prdNombre) {
        this.prdNombre = prdNombre;
    }

    public String getPspNombre() {
        return pspNombre;
    }

    public void setPspNombre(String pspNombre) {
        this.pspNombre = pspNombre;
    }

    public Long getInvCant() {
        return invCant;
    }

    public void setInvCant(Long invCant) {
        this.invCant = invCant;
    }

    public String getPrdypsp() {
        return prdypsp;
    }

    public void setPrdypsp(String prdypsp) {
        this.prdypsp = prdypsp;
    }
}
