/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.consulta.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Garcia Bosso
 */
public class ConsultaOPDTO implements Serializable {

    private String numeroDocumento;
    private String nombreCliente;
    private Long idOp;
    private Long idVenta;
    private Date fechaCreacion;
    private String tituloOp;
    private Integer estadoOp;
    private Integer estadoLogistica;

    public ConsultaOPDTO() {
    }

    public ConsultaOPDTO(String numeroDocumento, String nombreCliente, Long idOp, Long idVenta, Date fechaCreacion, String tituloOp, Integer estadoOp, Integer estadoLogistica) {
        this.numeroDocumento = numeroDocumento;
        this.nombreCliente = nombreCliente;
        this.idOp = idOp;
        this.idVenta = idVenta;
        this.fechaCreacion = fechaCreacion;
        this.tituloOp = tituloOp;
        this.estadoOp = estadoOp;
        this.estadoLogistica = estadoLogistica;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTituloOp() {
        return tituloOp;
    }

    public void setTituloOp(String tituloOp) {
        this.tituloOp = tituloOp;
    }

    public Integer getEstadoOp() {
        return estadoOp;
    }

    public void setEstadoOp(Integer estadoOp) {
        this.estadoOp = estadoOp;
    }

    public Integer getEstadoLogistica() {
        return estadoLogistica;
    }

    public void setEstadoLogistica(Integer estadoLogistica) {
        this.estadoLogistica = estadoLogistica;
    }
    
    
}
