/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.InvInvent;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaInvent extends TablaBaseGrid{

    private InvInvent invent = new InvInvent();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaInvent other = (TablaInvent) obj;
        if (!Objects.equals(this.invent, other.invent)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.invent);
        return hash;
    }
    
    
    public TablaInvent() {
    }
      public TablaInvent(InvInvent pInvInvent) {
          this.invent = pInvInvent;
    }

    /**
     * @return the invent
     */
    public InvInvent getInvent() {
        return invent;
    }

    /**
     * @param invent the invent to set
     */
    public void setInvent(InvInvent invent) {
        this.invent = invent;
    }
    
    
}
