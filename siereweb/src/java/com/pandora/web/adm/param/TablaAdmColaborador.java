/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.adm.param;

import adm.sys.dao.AdmColaborador;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author byrobles
 */
public class TablaAdmColaborador extends TablaBaseGrid {

    private AdmColaborador admColaborador = new AdmColaborador();

    public TablaAdmColaborador() {
    }

    public TablaAdmColaborador(AdmColaborador pAdmColaborador) {
        this.admColaborador = pAdmColaborador;
    }

    /**
     * @return the admColaborador
     */
    public AdmColaborador getAdmColaborador() {
        return admColaborador;
    }

    /**
     * @param admColaborador the admColaborador to set
     */
    public void setAdmColaborador(AdmColaborador admColaborador) {
        this.admColaborador = admColaborador;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.admColaborador);
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
        final TablaAdmColaborador other = (TablaAdmColaborador) obj;
        if (!Objects.equals(this.admColaborador, other.admColaborador)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaAdmColaborador{" + "admColaborador=" + admColaborador + '}';
    }
}
