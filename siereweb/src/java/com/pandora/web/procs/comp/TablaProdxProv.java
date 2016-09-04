/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.InvProdxprov;
import com.pandora.web.base.TablaBaseGrid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author carlos
 */
public class TablaProdxProv extends TablaBaseGrid {

    private InvProdxprov invProdxprov = new InvProdxprov();
    private Integer cantidad = 0;
    private BigDecimal anticipo;
    private Date fechaMaximaEntrega;

    public TablaProdxProv() {
    }

    public TablaProdxProv(InvProdxprov pInvProdxprov) {
        this.invProdxprov = pInvProdxprov;
    }

    /**
     * @return the invProdxprov
     */
    public InvProdxprov getInvProdxprov() {
        return invProdxprov;
    }

    /**
     * @param invProdxprov the invProdxprov to set
     */
    public void setInvProdxprov(InvProdxprov invProdxprov) {
        this.invProdxprov = invProdxprov;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the anticipo
     */
    public BigDecimal getAnticipo() {
        return anticipo;
    }

    /**
     * @param anticipo the anticipo to set
     */
    public void setAnticipo(BigDecimal anticipo) {
        this.anticipo = anticipo;
    }

    /**
     * @return the fechaMaximaEntrega
     */
    public Date getFechaMaximaEntrega() {
        return fechaMaximaEntrega;
    }

    /**
     * @param fechaMaximaEntrega the fechaMaximaEntrega to set
     */
    public void setFechaMaximaEntrega(Date fechaMaximaEntrega) {
        this.fechaMaximaEntrega = fechaMaximaEntrega;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaProdxProv other = (TablaProdxProv) obj;
        if (!Objects.equals(this.invProdxprov, other.invProdxprov)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.invProdxprov);
        return hash;
    }
}
