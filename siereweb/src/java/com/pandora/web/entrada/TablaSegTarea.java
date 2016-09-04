/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.entrada;

import adm.sys.dao.SysSegtarea;

import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaSegTarea extends TablaBaseGrid {

    private SysSegtarea sysSegtarea = new SysSegtarea();
    private String claseSegTarea = "";

    public TablaSegTarea() {
    }

    public TablaSegTarea(SysSegtarea pSegtarea) {
        this.sysSegtarea = pSegtarea;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaSegTarea other = (TablaSegTarea) obj;
        if (!Objects.equals(this.sysSegtarea, other.sysSegtarea)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.sysSegtarea);
        return hash;
    }

    /**
     * @return the sysSegtarea
     */
    public SysSegtarea getSysSegtarea() {
        return sysSegtarea;
    }

    /**
     * @param sysSegtarea the sysSegtarea to set
     */
    public void setSysSegtarea(SysSegtarea sysSegtarea) {
        this.sysSegtarea = sysSegtarea;
    }

    /**
     * @return the claseSegTarea
     */
    public String getClaseSegTarea() {
        return claseSegTarea;
    }

    /**
     * @param claseSegTarea the claseSegTarea to set
     */
    public void setClaseSegTarea(String claseSegTarea) {
        this.claseSegTarea = claseSegTarea;
    }
}
