/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.venta;

import com.pandora.mod.venta.dao.VntRemision;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author sandra
 */
public class TablaVntRemision extends TablaBaseGrid {

    private VntRemision vntRemision = new VntRemision();

    public TablaVntRemision() {
    }

    public TablaVntRemision(VntRemision pVntRemision) {
        this.vntRemision = pVntRemision;
    }

    /**
     * @return the vntRemision
     */
    public VntRemision getVntRemision() {
        return vntRemision;
    }

    /**
     * @param vntRemision the vntRemision to set
     */
    public void setVntRemision(VntRemision vntRemision) {
        this.vntRemision = vntRemision;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.vntRemision);
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
        final TablaVntRemision other = (TablaVntRemision) obj;
        if (!Objects.equals(this.vntRemision, other.vntRemision)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaVntRemision{" + "vntRemision=" + vntRemision + '}';
    }
}
