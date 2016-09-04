/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.InvPresntxprod;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaPresentXProd extends TablaBaseGrid {

    private InvPresntxprod invPresntxprod = new InvPresntxprod();

    public TablaPresentXProd() {
    }

    public TablaPresentXProd(InvPresntxprod pInvPresntxprod) {
        this.invPresntxprod = pInvPresntxprod;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaPresentXProd other = (TablaPresentXProd) obj;
        if (!Objects.equals(this.invPresntxprod, other.invPresntxprod)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.invPresntxprod);
        return hash;
    }

    /**
     * @return the invPresntxprod
     */
    public InvPresntxprod getInvPresntxprod() {
        return invPresntxprod;
    }

    /**
     * @param invPresntxprod the invPresntxprod to set
     */
    public void setInvPresntxprod(InvPresntxprod invPresntxprod) {
        this.invPresntxprod = invPresntxprod;
    }
}
