/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.InvCatprod;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author carlos
 */
public class TablaCategoriaProd extends TablaBaseGrid{
    
    private InvCatprod invCatprod = new InvCatprod();

    public TablaCategoriaProd() {
    }
    
    public TablaCategoriaProd(InvCatprod pInvCatprod){
        this.invCatprod = pInvCatprod;
    }

    /**
     * @return the invCatprod
     */
    public InvCatprod getInvCatprod() {
        return invCatprod;
    }

    /**
     * @param invCatprod the invCatprod to set
     */
    public void setInvCatprod(InvCatprod invCatprod) {
        this.invCatprod = invCatprod;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaCategoriaProd other = (TablaCategoriaProd) obj;
        if (!Objects.equals(this.invCatprod, other.invCatprod)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.invCatprod);
        return hash;
    }
}
