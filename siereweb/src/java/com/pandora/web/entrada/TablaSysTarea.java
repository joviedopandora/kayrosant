/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.entrada;

import adm.sys.dao.SysTarea;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaSysTarea extends TablaBaseGrid {

    private SysTarea sysTarea = new SysTarea();

    public TablaSysTarea() {
    }

    public TablaSysTarea(SysTarea pSystarea) {
        this.sysTarea = pSystarea;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaSysTarea other = (TablaSysTarea) obj;
        if (!Objects.equals(this.sysTarea, other.sysTarea)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.sysTarea);
        return hash;
    }

    /**
     * @return the sysTarea
     */
    public SysTarea getSysTarea() {
        return sysTarea;
    }

    /**
     * @param sysTarea the sysTarea to set
     */
    public void setSysTarea(SysTarea sysTarea) {
        this.sysTarea = sysTarea;
    }
}
