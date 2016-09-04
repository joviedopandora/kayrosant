/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.ByteArrayOutputStream;

/**
 *
 * @author luis
 */
public class BinarioInforme {

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private String nombreArchivo;

    public BinarioInforme(ByteArrayOutputStream pBaos, String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        this.baos = pBaos;
    }

    /**
     * @return the baos
     */
    public ByteArrayOutputStream getBaos() {
        return baos;
    }

    /**
     * @param baos the baos to set
     */
    public void setBaos(ByteArrayOutputStream baos) {
        this.baos = baos;
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
