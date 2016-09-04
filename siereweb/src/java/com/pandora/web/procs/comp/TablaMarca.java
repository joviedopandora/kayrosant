/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.InvMarca;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author carlos
 */
public class TablaMarca extends TablaBaseGrid {

    private InvMarca invMarca = new InvMarca();

    public TablaMarca() {
    }

    public TablaMarca(InvMarca pInvMarca) {
        this.invMarca = pInvMarca;
    }

    /**
     * @return the invMarca
     */
    public InvMarca getInvMarca() {
        return invMarca;
    }

    /**
     * @param invMarca the invMarca to set
     */
    public void setInvMarca(InvMarca invMarca) {
        this.invMarca = invMarca;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaMarca other = (TablaMarca) obj;
        if (!Objects.equals(this.invMarca, other.invMarca)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.invMarca);
        return hash;
    }
}
