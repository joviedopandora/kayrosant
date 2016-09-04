/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.adm.param;

import adm.sys.dao.AdmCrgxcol;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author byrobles
 */
public class TablaAdmCrgXCol extends TablaBaseGrid {

    private AdmCrgxcol admCrgxcol = new AdmCrgxcol();
    private Integer rolSeleccionado = -1;
    private Integer uniformeSeleccionado = -1;
    private Integer respondableSeleccionado = -1;
    private Integer citacionSeleccionado = -1;
    private Boolean selecciona;

    public TablaAdmCrgXCol() {
    }

    public TablaAdmCrgXCol(AdmCrgxcol pAdmCrgxcol) {
        this.admCrgxcol = pAdmCrgxcol;
    }

    public void seleccionarAdmCrgXCol(Boolean selec) {
        this.selecciona = selec;
    }

    public void seleccionarAdmCrgXCol(ValueChangeEvent vc) {
        seleccionado = (Boolean) vc.getNewValue();
        selecciona = (Boolean) vc.getNewValue();
    }

    /**
     * @return the admCrgxcol
     */
    public AdmCrgxcol getAdmCrgxcol() {
        return admCrgxcol;
    }

    /**
     * @param admCrgxcol the admCrgxcol to set
     */
    public void setAdmCrgxcol(AdmCrgxcol admCrgxcol) {
        this.admCrgxcol = admCrgxcol;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.admCrgxcol);
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
        final TablaAdmCrgXCol other = (TablaAdmCrgXCol) obj;
        if (!Objects.equals(this.admCrgxcol, other.admCrgxcol)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaAdmCrgXCol{" + "admCrgxcol=" + admCrgxcol + '}';
    }

    public Integer getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Integer rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public Integer getUniformeSeleccionado() {
        return uniformeSeleccionado;
    }

    public void setUniformeSeleccionado(Integer uniformeSeleccionado) {
        this.uniformeSeleccionado = uniformeSeleccionado;
    }

    public Integer getRespondableSeleccionado() {
        return respondableSeleccionado;
    }

    public void setRespondableSeleccionado(Integer respondableSeleccionado) {
        this.respondableSeleccionado = respondableSeleccionado;
    }

    public Integer getCitacionSeleccionado() {
        return citacionSeleccionado;
    }

    public void setCitacionSeleccionado(Integer citacionSeleccionado) {
        this.citacionSeleccionado = citacionSeleccionado;
    }

    /**
     * @return the selecciona
     */
    public Boolean getSelecciona() {
        return selecciona;
    }

    /**
     * @param selecciona the selecciona to set
     */
    public void setSelecciona(Boolean selecciona) {
        // this.selecciona = selecciona;
    }

}
