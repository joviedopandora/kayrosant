/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.adm.param;

import com.pandora.mod.venta.dao.RfCargocontacto;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author Luis Fernando
 */
public class TablaRfCargoContacto extends TablaBaseGrid{
    private RfCargocontacto cargocontacto = new RfCargocontacto();

    public TablaRfCargoContacto(RfCargocontacto cargocontacto) {
        this.cargocontacto = cargocontacto;
    }

    public TablaRfCargoContacto() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.cargocontacto);
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
        final TablaRfCargoContacto other = (TablaRfCargoContacto) obj;
        if (!Objects.equals(this.cargocontacto, other.cargocontacto)) {
            return false;
        }
        return true;
    }

    /**
     * @return the cargocontacto
     */
    public RfCargocontacto getCargocontacto() {
        return cargocontacto;
    }

    /**
     * @param cargocontacto the cargocontacto to set
     */
    public void setCargocontacto(RfCargocontacto cargocontacto) {
        this.cargocontacto = cargocontacto;
    }
}
