/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.adm.param;

import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author byrobles
 */
public class TablaVntCliente extends TablaBaseGrid {

    private VntCliente vntCliente = new VntCliente();

    public TablaVntCliente() {
    }

    public TablaVntCliente(VntCliente pVntCliente) {
        this.vntCliente = pVntCliente;
    }

    /**
     * @return the vntCliente
     */
    public VntCliente getVntCliente() {
        return vntCliente;
    }

    /**
     * @param vntCliente the vntCliente to set
     */
    public void setVntCliente(VntCliente vntCliente) {
        this.vntCliente = vntCliente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.vntCliente);
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
        final TablaVntCliente other = (TablaVntCliente) obj;
        if (!Objects.equals(this.vntCliente, other.vntCliente)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaVntCliente{" + "vntCliente=" + vntCliente + '}';
    }
}
