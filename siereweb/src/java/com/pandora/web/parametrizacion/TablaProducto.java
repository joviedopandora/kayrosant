/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.parametrizacion;

import com.pandora.adm.dao.InvProducto;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author Administrador
 */
public class TablaProducto  extends TablaBaseGrid {
    private InvProducto producto = new InvProducto();

    public TablaProducto() {
    }

    public InvProducto getProducto() {
        return producto;
    }

    public void setProducto(InvProducto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.producto);
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
        final TablaProducto other = (TablaProducto) obj;
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        return true;
    }
    
    
}
