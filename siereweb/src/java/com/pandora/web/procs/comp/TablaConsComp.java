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
 * @author lfchacon
 */
public class TablaConsComp extends TablaBaseGrid {

   private  CmpConsolcompra cmpConsolcompra = new CmpConsolcompra();

    public TablaConsComp() {
    }

    public TablaConsComp(CmpConsolcompra pCmpConsolcompra) {
        this.cmpConsolcompra = pCmpConsolcompra;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaConsComp other = (TablaConsComp) obj;
        if (!Objects.equals(this.cmpConsolcompra, other.cmpConsolcompra)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.cmpConsolcompra);
        return hash;
    }

    public CmpConsolcompra getCmpConsolcompra() {
        return cmpConsolcompra;
    }

    public void setCmpConsolcompra(CmpConsolcompra cmpConsolcompra) {
        this.cmpConsolcompra = cmpConsolcompra;
    }
}
