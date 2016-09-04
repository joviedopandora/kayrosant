/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.CmpRemisioninv;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaRemision extends TablaBaseGrid{
    
    private CmpRemisioninv cmpRemisioninv = new CmpRemisioninv();

    public TablaRemision() {
    }
    
    public TablaRemision(CmpRemisioninv pRemisioninv){
        this.cmpRemisioninv = pRemisioninv;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.cmpRemisioninv);
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
        final TablaRemision other = (TablaRemision) obj;
        if (!Objects.equals(this.cmpRemisioninv, other.cmpRemisioninv)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaRemision{" + "cmpRemisioninv=" + cmpRemisioninv + '}';
    }
}
