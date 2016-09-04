/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.CmpConspedido;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author lfchacon
 */
public class TablaConsPedido extends TablaBaseGrid {

    private CmpConspedido cmpConspedido = new CmpConspedido();

    public TablaConsPedido() {
    }

    public TablaConsPedido(CmpConspedido pCmpConspedido) {
        this.cmpConspedido = pCmpConspedido;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaConsPedido other = (TablaConsPedido) obj;
        if (!Objects.equals(this.cmpConspedido, other.cmpConspedido)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.cmpConspedido);
        return hash;
    }

    /**
     * @return the cmpConspedido
     */
    public CmpConspedido getCmpConspedido() {
        return cmpConspedido;
    }

    /**
     * @param cmpConspedido the cmpConspedido to set
     */
    public void setCmpConspedido(CmpConspedido cmpConspedido) {
        this.cmpConspedido = cmpConspedido;
    }
}
