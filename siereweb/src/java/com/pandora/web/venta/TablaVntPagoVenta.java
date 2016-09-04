/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.venta;

import com.pandora.mod.venta.dao.VntPagoventa;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaVntPagoVenta extends TablaBaseGrid {

    private VntPagoventa vntPagoventa = new VntPagoventa();

    public TablaVntPagoVenta() {
    }

    public TablaVntPagoVenta(VntPagoventa pVntPagoventa) {
        this.vntPagoventa = pVntPagoventa;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.vntPagoventa);
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
        final TablaVntPagoVenta other = (TablaVntPagoVenta) obj;
        if (!Objects.equals(this.vntPagoventa, other.vntPagoventa)) {
            return false;
        }
        return true;
    }

    /**
     * @return the vntPagoventa
     */
    public VntPagoventa getVntPagoventa() {
        return vntPagoventa;
    }

    /**
     * @param vntPagoventa the vntPagoventa to set
     */
    public void setVntPagoventa(VntPagoventa vntPagoventa) {
        this.vntPagoventa = vntPagoventa;
    }

}
