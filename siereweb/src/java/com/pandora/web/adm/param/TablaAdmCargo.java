/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.adm.param;

import adm.sys.dao.AdmCargo;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author byrobles
 */
public class TablaAdmCargo extends TablaBaseGrid {

    private AdmCargo admCargo = new AdmCargo();

    public TablaAdmCargo() {
    }

    public TablaAdmCargo(AdmCargo pAdmCargo) {
        this.admCargo = pAdmCargo;
    }

    /**
     * @return the admCargo
     */
    public AdmCargo getAdmCargo() {
        return admCargo;
    }

    /**
     * @param admCargo the admCargo to set
     */
    public void setAdmCargo(AdmCargo admCargo) {
        this.admCargo = admCargo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.admCargo);
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
        final TablaAdmCargo other = (TablaAdmCargo) obj;
        if (!Objects.equals(this.admCargo, other.admCargo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaAdmCargo{" + "admCargo=" + admCargo + '}';
    }
}
