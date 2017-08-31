/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procesos;

import com.pandora.web.base.TablaBaseGrid;
import java.io.File;
import java.util.Objects;

/**
 *
 * @author Luis Fernando
 */
public class TablaArchivosAdjunto extends TablaBaseGrid{
    private File archivo;
    private String nombreArchivo;

    public TablaArchivosAdjunto() {
    }

    public TablaArchivosAdjunto(File archivo, String nombreArchivo) {
        this.archivo = archivo;
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.nombreArchivo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaArchivosAdjunto other = (TablaArchivosAdjunto) obj;
        if (!Objects.equals(this.nombreArchivo, other.nombreArchivo)) {
            return false;
        }
        return true;
    }

    
    /**
     * @return the archivo
     */
    public File getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(File archivo) {
        this.archivo = archivo;
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
