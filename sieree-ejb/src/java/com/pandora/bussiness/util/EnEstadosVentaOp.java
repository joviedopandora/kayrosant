/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.bussiness.util;

/**
 *
 * @author HP
 */
public enum EnEstadosVentaOp {
    
    ANULADO(1),
    PENDIENTE(2),
    ACTIVOOP(4),
    FACTURAR_Y_OP(5),
    ACTIVO(3);
    
    Integer id;

   private  EnEstadosVentaOp(Integer id) {
        this.id = id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return
     */
    public Integer getId() {
        return id;
    }
    
}
