/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.venta;

import com.pandora.mod.venta.dao.VntCronograma;
import com.pandora.mod.venta.dao.VntServxventa;
import com.pandora.web.base.TablaBaseGrid;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaVntSrvXVenta extends TablaBaseGrid {

    private VntServxventa vntServxventa = new VntServxventa();
    private int cantidadPendiente =0;
    private Integer cantidadSeleccionada= null;
    private String procesar= null;
    private BigDecimal valorProcesado = BigDecimal.ZERO;
    private BigDecimal valorDescuento = BigDecimal.ZERO;
    private BigDecimal valorFinal = BigDecimal.ZERO;
    private VntCronograma cronogramaSel= null;

    public TablaVntSrvXVenta() {
    }

    public TablaVntSrvXVenta(VntServxventa pVntServxventa) {
        this.vntServxventa = pVntServxventa;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.vntServxventa);
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
        final TablaVntSrvXVenta other = (TablaVntSrvXVenta) obj;
        if (!Objects.equals(this.vntServxventa, other.vntServxventa)) {
            return false;
        }
        return true;
    }

    /**
     * @return the vntServxventa
     */
    public VntServxventa getVntServxventa() {
        return vntServxventa;
    }

    /**
     * @param vntServxventa the vntServxventa to set
     */
    public void setVntServxventa(VntServxventa vntServxventa) {
        this.vntServxventa = vntServxventa;
    }

    public int getCantidadPendiente() {
        return cantidadPendiente;
    }

    public void setCantidadPendiente(int cantidadPendiente) {
        this.cantidadPendiente = cantidadPendiente;
    }

    public Integer getCantidadSeleccionada() {
        return cantidadSeleccionada;
    }

    public void setCantidadSeleccionada(Integer cantidadSeleccionada) {
        this.cantidadSeleccionada = cantidadSeleccionada;
    }

    public String getProcesar() {
        return procesar;
    }

    public void setProcesar(String procesar) {
        this.procesar = procesar;
    }

    public BigDecimal getValorProcesado() {
        return valorProcesado;
    }

    public void setValorProcesado(BigDecimal valorProcesado) {
        this.valorProcesado = valorProcesado;
    }

    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public VntCronograma getCronogramaSel() {
        return cronogramaSel;
    }

    public void setCronogramaSel(VntCronograma cronogramaSel) {
        this.cronogramaSel = cronogramaSel;
    }
    
    

}
