/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.web.venta;

import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author fernando
 */
public class TablaVntProdxsrv extends TablaBaseGrid{
    
    private VntProdxsrv vntProdxsrv = new VntProdxsrv();

    public TablaVntProdxsrv() {
    }

     public TablaVntProdxsrv(VntProdxsrv pVntProdxsrv) {
         this.vntProdxsrv = pVntProdxsrv;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.vntProdxsrv);
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
        final TablaVntProdxsrv other = (TablaVntProdxsrv) obj;
        if (!Objects.equals(this.vntProdxsrv, other.vntProdxsrv)) {
            return false;
        }
        return true;
    }

    /**
     * @return the vntProdxsrv
     */
    public VntProdxsrv getVntProdxsrv() {
        return vntProdxsrv;
    }

    /**
     * @param vntProdxsrv the vntProdxsrv to set
     */
    public void setVntProdxsrv(VntProdxsrv vntProdxsrv) {
        this.vntProdxsrv = vntProdxsrv;
    }
    
}
