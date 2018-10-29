/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.venta;

import com.pandora.mod.venta.dao.VntServicio;
import com.pandora.web.base.TablaBaseGrid;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaVntServicio extends TablaBaseGrid {

    private VntServicio vntServicio = new VntServicio();
    private BigDecimal bigdPrecioCliente;
    private BigDecimal bigdProcentajeDesc;

    public TablaVntServicio() {
    }

    public TablaVntServicio(VntServicio pVntServicio) {
        this.vntServicio = pVntServicio;
    }

    private Integer cantidadSrv;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.vntServicio);
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
        final TablaVntServicio other = (TablaVntServicio) obj;
        if (!Objects.equals(this.vntServicio, other.vntServicio)) {
            return false;
        }
        return true;
    }

    /**
     * @return the vntServicio
     */
    public VntServicio getVntServicio() {
        return vntServicio;
    }

    /**
     * @param vntServicio the vntServicio to set
     */
    public void setVntServicio(VntServicio vntServicio) {
        this.vntServicio = vntServicio;
    }

    /**
     * @return the bigdPrecioCliente
     */
    public BigDecimal getBigdPrecioCliente() {
        return bigdPrecioCliente;
    }

    /**
     * @param bigdPrecioCliente the bigdPrecioCliente to set
     */
    public void setBigdPrecioCliente(BigDecimal bigdPrecioCliente) {
        this.bigdPrecioCliente = bigdPrecioCliente;
    }

    /**
     * @return the cantidadSrv
     */
    public Integer getCantidadSrv() {
        return cantidadSrv;
    }

    /**
     * @param cantidadSrv the cantidadSrv to set
     */
    public void setCantidadSrv(Integer cantidadSrv) {
        this.cantidadSrv = cantidadSrv;
    }

    public BigDecimal getBigdProcentajeDesc() {
        return bigdProcentajeDesc;
    }

    public void setBigdProcentajeDesc(BigDecimal bigdProcentajeDesc) {
        this.bigdProcentajeDesc = bigdProcentajeDesc;
    }
}
