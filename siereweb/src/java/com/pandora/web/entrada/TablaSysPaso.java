/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.entrada;

import adm.sys.dao.SysPropaso;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaSysPaso extends TablaBaseGrid {

    private SysPropaso sysPropaso = new SysPropaso();

    public TablaSysPaso() {
    }

    private Date fechaFinPaso;
    public TablaSysPaso(SysPropaso pSysPropaso) {
        this.sysPropaso = pSysPropaso;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaSysPaso other = (TablaSysPaso) obj;
        if (!Objects.equals(this.sysPropaso, other.sysPropaso)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.sysPropaso);
        return hash;
    }

    /**
     * @return the sysPropaso
     */
    public SysPropaso getSysPropaso() {
        return sysPropaso;
    }

    /**
     * @param sysPropaso the sysPropaso to set
     */
    public void setSysPropaso(SysPropaso sysPropaso) {
        this.sysPropaso = sysPropaso;
    }

    /**
     * @return the fechaFinPaso
     */
    public Date getFechaFinPaso() {
        return fechaFinPaso;
    }

    /**
     * @param fechaFinPaso the fechaFinPaso to set
     */
    public void setFechaFinPaso(Date fechaFinPaso) {
        this.fechaFinPaso = fechaFinPaso;
    }
}
