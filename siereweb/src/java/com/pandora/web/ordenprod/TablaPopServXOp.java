/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.ordenprod;

import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author sandra
 */
public class TablaPopServXOp extends TablaBaseGrid {

    private PopServxop popServxop = new PopServxop();

    public TablaPopServXOp() {
    }

    public TablaPopServXOp(PopServxop pPopServxop) {
        this.popServxop = pPopServxop;
    }

    /**
     * @return the popServxop
     */
    public PopServxop getPopServxop() {
        return popServxop;
    }

    /**
     * @param popServxop the popServxop to set
     */
    public void setPopServxop(PopServxop popServxop) {
        this.popServxop = popServxop;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.popServxop);
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
        final TablaPopServXOp other = (TablaPopServXOp) obj;
        if (!Objects.equals(this.popServxop, other.popServxop)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaPopServXOp{" + "popServxop=" + popServxop + '}';
    }
}
