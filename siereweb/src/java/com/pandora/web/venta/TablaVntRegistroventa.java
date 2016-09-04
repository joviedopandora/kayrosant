/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.venta;

import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaVntRegistroventa extends TablaBaseGrid {

    private VntRegistroventa vntRegistroventa = new VntRegistroventa();
    private PopOrdenprod ordenprod = new PopOrdenprod();
    private com.icesoft.faces.context.Resource jrResourceRetorna = null;

    public TablaVntRegistroventa() {
    }

    public TablaVntRegistroventa(VntRegistroventa pVntRegistroventa) {
        this.vntRegistroventa = pVntRegistroventa;
    }

    public TablaVntRegistroventa(PopOrdenprod ordenprod) {
        this.ordenprod = ordenprod;
        if (ordenprod != null) {
            this.vntRegistroventa = ordenprod.getRgvtId();
        }
    }

    public PopOrdenprod getOrdenprod() {
        return ordenprod;
    }

    public void setOrdenprod(PopOrdenprod ordenprod) {
        this.ordenprod = ordenprod;
    }

    /**
     * @return the vntRegistroventa
     */
    public VntRegistroventa getVntRegistroventa() {
        return vntRegistroventa;
    }

    /**
     * @param vntRegistroventa the vntRegistroventa to set
     */
    public void setVntRegistroventa(VntRegistroventa vntRegistroventa) {
        this.vntRegistroventa = vntRegistroventa;
    }

    /**
     * @return the jrResourceRetorna
     */
    public com.icesoft.faces.context.Resource getJrResourceRetorna() {
        return jrResourceRetorna;
    }

    /**
     * @param jrResourceRetorna the jrResourceRetorna to set
     */
    public void setJrResourceRetorna(com.icesoft.faces.context.Resource jrResourceRetorna) {
        this.jrResourceRetorna = jrResourceRetorna;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.ordenprod);
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
        final TablaVntRegistroventa other = (TablaVntRegistroventa) obj;
        return Objects.equals(this.ordenprod, other.ordenprod);
    }

}
