/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.CmpDetremision;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaDetalleRemision extends TablaBaseGrid{
    
    private CmpDetremision cmpDetremision = new CmpDetremision();

    public TablaDetalleRemision() {
    }
    
    public TablaDetalleRemision(CmpDetremision pDetremision){
        this.cmpDetremision = pDetremision;
    }

    /**
     * @return the cmpDetremision
     */
    public CmpDetremision getCmpDetremision() {
        return cmpDetremision;
    }

    /**
     * @param cmpDetremision the cmpDetremision to set
     */
    public void setCmpDetremision(CmpDetremision cmpDetremision) {
        this.cmpDetremision = cmpDetremision;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.cmpDetremision);
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
        final TablaDetalleRemision other = (TablaDetalleRemision) obj;
        if (!Objects.equals(this.cmpDetremision, other.cmpDetremision)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaDetalleRemision{" + "cmpDetremision=" + cmpDetremision + '}';
    }
}
