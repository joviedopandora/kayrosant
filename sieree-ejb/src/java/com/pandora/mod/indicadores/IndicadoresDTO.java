/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.mod.indicadores;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Javier
 */
public class IndicadoresDTO implements Serializable{
    private String periodo;
    private Long cantidad;

    public IndicadoresDTO() {
    }

    public IndicadoresDTO(String periodo, Long cantidad) {
        this.periodo = periodo;
        this.cantidad = cantidad;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Long getCantidad() {
        return cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.periodo);
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
        final IndicadoresDTO other = (IndicadoresDTO) obj;
        if (!Objects.equals(this.periodo, other.periodo)) {
            return false;
        }
        return true;
    }
    
    

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
    
}
