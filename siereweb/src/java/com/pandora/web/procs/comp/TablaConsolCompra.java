/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.CmpConsolcompra;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author carlos
 */
public class TablaConsolCompra extends TablaBaseGrid{
    
    private CmpConsolcompra cmpConsolcompra = new CmpConsolcompra();

    public TablaConsolCompra() {
    }
    
    public TablaConsolCompra(CmpConsolcompra pConsolcompra){
        this.cmpConsolcompra = pConsolcompra;
    }

    /**
     * @return the cmpConsolcompra
     */
    public CmpConsolcompra getCmpConsolcompra() {
        return cmpConsolcompra;
    }

    /**
     * @param cmpConsolcompra the cmpConsolcompra to set
     */
    public void setCmpConsolcompra(CmpConsolcompra cmpConsolcompra) {
        this.cmpConsolcompra = cmpConsolcompra;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaConsolCompra other = (TablaConsolCompra) obj;
        if (!Objects.equals(this.cmpConsolcompra, other.cmpConsolcompra)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.cmpConsolcompra);
        return hash;
    }    
}
