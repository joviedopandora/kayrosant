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
public enum EnTipoPagoNomina {
    
    TRANSFERENCIA(1),
    EFECTIVO(2),
    CHEQUE(3);
    
    Integer id;

   private  EnTipoPagoNomina(Integer id) {
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
