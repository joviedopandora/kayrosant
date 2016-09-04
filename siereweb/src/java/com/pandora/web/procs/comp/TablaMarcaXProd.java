/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.InvMarcxprod;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaMarcaXProd extends TablaBaseGrid {

    private InvMarcxprod invMarcxprod = new InvMarcxprod();

    public TablaMarcaXProd() {
    }

    public TablaMarcaXProd(InvMarcxprod pInvMarcxprod) {
        this.invMarcxprod = pInvMarcxprod;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaMarcaXProd other = (TablaMarcaXProd) obj;
        if (!Objects.equals(this.invMarcxprod, other.invMarcxprod)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.invMarcxprod);
        return hash;
    }

    /**
     * @return the invMarcxprod
     */
    public InvMarcxprod getInvMarcxprod() {
        return invMarcxprod;
    }

    /**
     * @param invMarcxprod the invMarcxprod to set
     */
    public void setInvMarcxprod(InvMarcxprod invMarcxprod) {
        this.invMarcxprod = invMarcxprod;
    }
}
