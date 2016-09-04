/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.liquidacion;

import java.util.Date;

/**
 *
 * @author Garcia Bosso
 */
public class ConsultaNominaDTO {

    private Integer liquidacionConsolidadoId;
    private String numeroDocumento;
    private Date fechaInicialNomina;
    private Date fechaFinal;
    private Integer tipoPago;

    public ConsultaNominaDTO() {
    }

    public ConsultaNominaDTO(Integer liquidacionConsolidadoId, String numeroDocumento, Date fechaInicialNomina, Date fechaFinal, Integer tipoPago) {
        this.liquidacionConsolidadoId = liquidacionConsolidadoId;
        this.numeroDocumento = numeroDocumento;
        this.fechaInicialNomina = fechaInicialNomina;
        this.fechaFinal = fechaFinal;
        this.tipoPago = tipoPago;
    }

   

    public Integer getLiquidacionConsolidadoId() {
        return liquidacionConsolidadoId;
    }

    public void setLiquidacionConsolidadoId(Integer liquidacionConsolidadoId) {
        this.liquidacionConsolidadoId = liquidacionConsolidadoId;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaInicialNomina() {
        return fechaInicialNomina;
    }

    public void setFechaInicialNomina(Date fechaInicialNomina) {
        this.fechaInicialNomina = fechaInicialNomina;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Integer getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Integer tipoPago) {
        this.tipoPago = tipoPago;
    }
    
    

}
