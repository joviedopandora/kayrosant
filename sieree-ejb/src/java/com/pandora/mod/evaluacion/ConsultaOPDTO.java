/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.evaluacion;

import java.util.Date;

/**
 *
 * @author breyner.robles
 */
public class ConsultaOPDTO {

    private Long idOp;
    private Long idVenta;
    private String numeroDocumento;
    private String nombre;
    private Date fechaOp;
    private Integer tipoCliente;
    private Integer cantidadEvaluaciones;

    public ConsultaOPDTO(Long idOp, Long idVenta, String numeroDocumento, String nombre, Date fechaOp, Integer tipoCliente,Integer cantidadEvaluaciones) {
        this.idOp = idOp;
        this.idVenta = idVenta;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.fechaOp = fechaOp;
        this.tipoCliente = tipoCliente;
        this.cantidadEvaluaciones=cantidadEvaluaciones;
    }

   
   
   
   

    public ConsultaOPDTO() {
    }

    public Long getIdOp() {
        return idOp;
    }

    public void setIdOp(Long idOp) {
        this.idOp = idOp;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaOp() {
        return fechaOp;
    }

    public void setFechaOp(Date fechaOp) {
        this.fechaOp = fechaOp;
    }

    public Integer getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(Integer tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Integer getCantidadEvaluaciones() {
        return cantidadEvaluaciones;
    }

    public void setCantidadEvaluaciones(Integer cantidadEvaluaciones) {
        this.cantidadEvaluaciones = cantidadEvaluaciones;
    }
    
    

}
