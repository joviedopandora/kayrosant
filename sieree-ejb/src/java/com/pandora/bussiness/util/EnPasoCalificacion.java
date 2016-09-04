/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.bussiness.util;

/**
 *
 * @author breyner.robles
 */
public enum EnPasoCalificacion {
    INICIAL(0),PARCIAL_CARGO(1),
    PARCIAL_CALIFICACION(2),
    PARCIAL_BONIFICACION(3),
    PARCIAL_ADMINISTRATIVA(4),
    FINAL(5);
    
    
     private EnPasoCalificacion(Integer id) {
        this.id = id;
    }

    private Integer id;

    /**
     * @return
     */
    public Integer getId() {
        return id;
    }
}
