/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.InvProovedor;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author carlos
 */
public class TablaProveedor extends TablaBaseGrid{
    
    private InvProovedor invProveedor = new InvProovedor();

    public TablaProveedor() {
    }
    
    public TablaProveedor(InvProovedor pInvProovedor){
        this.invProveedor = pInvProovedor;
    }

    /**
     * @return the invProveedor
     */
    public InvProovedor getInvProveedor() {
        return invProveedor;
    }

    /**
     * @param invProveedor the invProveedor to set
     */
    public void setInvProveedor(InvProovedor invProveedor) {
        this.invProveedor = invProveedor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaProveedor other = (TablaProveedor) obj;
        if (!Objects.equals(this.invProveedor, other.invProveedor)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.invProveedor);
        return hash;
    }    
}