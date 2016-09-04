/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.web.venta;

import com.pandora.mod.venta.dao.VntFactura;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author sandra
 */
public class TablaVntFactura extends TablaBaseGrid{
    
    private VntFactura vntFactura = new VntFactura();
    private String vtnFecFactura;
    private Long countFactura;

    public TablaVntFactura() {
    }
    
    public TablaVntFactura(VntFactura pVntFactura){
        this.vntFactura = pVntFactura;
    }

    /**
     * @return the vntFactura
     */
    public VntFactura getVntFactura() {
        return vntFactura;
    }

    /**
     * @param vntFactura the vntFactura to set
     */
    public void setVntFactura(VntFactura vntFactura) {
        this.vntFactura = vntFactura;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.vntFactura);
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
        final TablaVntFactura other = (TablaVntFactura) obj;
        if (!Objects.equals(this.vntFactura, other.vntFactura)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaVntFactura{" + "vntFactura=" + vntFactura + '}';
    }

    /**
     * @return the vtnFecFactura
     */
    public String getVtnFecFactura() {
        return vtnFecFactura;
    }

    /**
     * @param vtnFecFactura the vtnFecFactura to set
     */
    public void setVtnFecFactura(String vtnFecFactura) {
        this.vtnFecFactura = vtnFecFactura;
    }

    /**
     * @return the countFactura
     */
    public Long getCountFactura() {
        return countFactura;
    }

    /**
     * @param countFactura the countFactura to set
     */
    public void setCountFactura(Long countFactura) {
        this.countFactura = countFactura;
    }
}
