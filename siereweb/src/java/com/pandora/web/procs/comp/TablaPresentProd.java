/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.InvPresentprod;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author carlos
 */
public class TablaPresentProd extends TablaBaseGrid {

    private InvPresentprod invPresentprod = new InvPresentprod();

    public TablaPresentProd() {
    }

    public TablaPresentProd(InvPresentprod pInvPresentprod) {
        this.invPresentprod = pInvPresentprod;
    }

    /**
     * @return the invPresentprod
     */
    public InvPresentprod getInvPresentprod() {
        return invPresentprod;
    }

    /**
     * @param invPresentprod the invPresentprod to set
     */
    public void setInvPresentprod(InvPresentprod invPresentprod) {
        this.invPresentprod = invPresentprod;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaPresentProd other = (TablaPresentProd) obj;
        if (!Objects.equals(this.invPresentprod, other.invPresentprod)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.invPresentprod);
        return hash;
    }
}
