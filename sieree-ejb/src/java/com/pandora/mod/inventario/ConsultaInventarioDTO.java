/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.inventario;

import java.io.Serializable;

/**
 *
 * @author breyner.robles
 */
public class ConsultaInventarioDTO implements Serializable {

    private String codigoBarras = null;
    private Integer idProducto = null;
    private String nombreProducto = null;
    private Integer marNombre = null;
    private Integer pspNombre = null;
   

    public ConsultaInventarioDTO() {
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getMarNombre() {
        return marNombre;
    }

    public void setMarNombre(Integer marNombre) {
        this.marNombre = marNombre;
    }

    public Integer getPspNombre() {
        return pspNombre;
    }

    public void setPspNombre(Integer pspNombre) {
        this.pspNombre = pspNombre;
    }
}
