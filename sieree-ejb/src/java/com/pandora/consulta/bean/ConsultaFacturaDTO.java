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
public class ConsultaFacturaDTO  implements  Serializable{
    
    private String numeroDocumento;
    private String nombreCliente;
    private String numeroFactura;
    private Long idVenta;
    private Date fechaCreacion;
    private String nombreEvento;
    private Integer estadoFactura;
    private Boolean consultarTodo;

    public ConsultaFacturaDTO() {
    }

    public ConsultaFacturaDTO(String numeroDocumento, String nombreCliente, String numeroFactura, Long idVenta, Date fechaCreacion, String nombreEvento, Integer estadoFactura) {
        this.numeroDocumento = numeroDocumento;
        this.nombreCliente = nombreCliente;
        this.numeroFactura = numeroFactura;
        this.idVenta = idVenta;
        this.fechaCreacion = fechaCreacion;
        this.nombreEvento = nombreEvento;
        this.estadoFactura = estadoFactura;
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

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
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

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public Integer getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(Integer estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    /**
     * @return the consultarTodo
     */
    public Boolean getConsultarTodo() {
        return consultarTodo;
    }

    /**
     * @param consultarTodo the consultarTodo to set
     */
    public void setConsultarTodo(Boolean consultarTodo) {
        this.consultarTodo = consultarTodo;
    }
    
    
}
