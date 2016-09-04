/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procesos;

import com.icesoft.faces.context.Resource;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.web.base.TablaBaseGrid;

/**
 *
 * @author Administrador
 */
public class TablaDetalleVentaDTO extends TablaBaseGrid {
    
    private VntDetevento detevento;
    private com.icesoft.faces.context.Resource jaspResource;
    private String nombreArchivo="reporte";

    public TablaDetalleVentaDTO() {
    }

    public VntDetevento getDetevento() {
        return detevento;
    }

    public void setDetevento(VntDetevento detevento) {
        this.detevento = detevento;
    }

    public Resource getJaspResource() {
        return jaspResource;
    }

    public void setJaspResource(Resource jaspResource) {
        this.jaspResource = jaspResource;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    
    
    
    
}
