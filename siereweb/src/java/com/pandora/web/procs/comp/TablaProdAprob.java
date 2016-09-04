/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.CmpPxraprob;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaProdAprob extends TablaBaseGrid {

    private CmpPxraprob cmpPxraprob = new CmpPxraprob();

    public TablaProdAprob() {
    }

    public TablaProdAprob(CmpPxraprob pCmpPxraprob) {
        this.cmpPxraprob = pCmpPxraprob;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaProdAprob other = (TablaProdAprob) obj;
        if (!Objects.equals(this.cmpPxraprob, other.cmpPxraprob)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.cmpPxraprob);
        return hash;
    }

    /**
     * @return the cmpPxraprob
     */
    public CmpPxraprob getCmpPxraprob() {
        return cmpPxraprob;
    }

    /**
     * @param cmpPxraprob the cmpPxraprob to set
     */
    public void setCmpPxraprob(CmpPxraprob cmpPxraprob) {
        this.cmpPxraprob = cmpPxraprob;
    }
}
