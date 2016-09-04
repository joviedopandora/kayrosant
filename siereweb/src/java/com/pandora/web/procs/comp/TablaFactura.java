/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.CmpFactura;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaFactura extends TablaBaseGrid{
    
    private CmpFactura cmpFactura = new CmpFactura();

    public TablaFactura() {
    }
    
    public TablaFactura(CmpFactura pCmpFactura){
        this.cmpFactura = pCmpFactura;
    }

    /**
     * @return the cmpFactura
     */
    public CmpFactura getCmpFactura() {
        return cmpFactura;
    }

    /**
     * @param cmpFactura the cmpFactura to set
     */
    public void setCmpFactura(CmpFactura cmpFactura) {
        this.cmpFactura = cmpFactura;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.cmpFactura);
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
        final TablaFactura other = (TablaFactura) obj;
        if (!Objects.equals(this.cmpFactura, other.cmpFactura)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaFactura{" + "cmpFactura=" + cmpFactura + '}';
    }
}
