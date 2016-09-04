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
public class TablaConsultaInventario implements Serializable {

    private String codigoBarras = null;
    private Integer idProducto = null;
    private String nombreProducto = null;
    private Integer marca = null;
    private Integer presentacion = null;
   

    public TablaConsultaInventario() {
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

    public Integer getMarca() {
        return marca;
    }

    public void setMarca(Integer marca) {
        this.marca = marca;
    }

    public Integer getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Integer presentacion) {
        this.presentacion = presentacion;
    }
}
