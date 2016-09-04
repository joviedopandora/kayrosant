
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
public enum EnEstadoLogistica {
    
    EVENTO_SIN_DESPACHAR(1),
    EVENTO_DESPACHADO(2),
    EVENTO_RECIBIDO(3);
    
    Integer id;

   private  EnEstadoLogistica(Integer id) {
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
