/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.entrada;

import adm.sys.dao.AdmNotificacion;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author breyner.robles
 */
public class TablaNotifiacion extends TablaBaseGrid {
    private AdmNotificacion notificacion = null;

    public TablaNotifiacion() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.notificacion);
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
        final TablaNotifiacion other = (TablaNotifiacion) obj;
        if (!Objects.equals(this.notificacion, other.notificacion)) {
            return false;
        }
        return true;
    }
    
    

    public AdmNotificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(AdmNotificacion notificacion) {
        this.notificacion = notificacion;
    }
    
    
    
}
