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
public enum EnEstadoFactura {
    
    EMITIDO(1),
    ANULADO(2),
    VENCIDA(3),
    PENDIENTE_DE_PAGO(4),
    PAGADA(5),
    RADICADO(6),
    EN_PROCESO(7);
    


    
    Integer id;

   private  EnEstadoFactura(Integer id) {
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
