/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.base;

import com.icesoft.faces.context.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import javax.faces.context.ExternalContext;

/**
 *
 * @author extmchlfchacon
 */
public class RecursosIn implements Resource, Serializable {

    private String nombRecurso;
    private InputStream inputStream;
    private final Date lastModified;
    private ExternalContext extContext;
    OutputStream outputStream;
    private String ruta_recursos;

    public RecursosIn(String nombRecurso, ExternalContext extContext) {
        this.nombRecurso = nombRecurso;
        this.lastModified = new Date();
        this.extContext = extContext;
    }

    @Override
    public String calculateDigest() {
        return nombRecurso;
    }

    @Override
    public InputStream open() throws IOException {
        if (inputStream == null) {
            InputStream stream = extContext.getResourceAsStream(ruta_recursos + nombRecurso);

            byte[] byteArray = toByteArray(stream);
            inputStream = new ByteArrayInputStream(byteArray);
        }
        return inputStream;
    }
    
     public  byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int len = 0;
        while ((len = input.read(buf)) > -1) {
            output.write(buf, 0, len);
        }
        return output.toByteArray();
    }

    @Override
    public Date lastModified() {
        return lastModified;
    }

    @Override
    public void withOptions(Options optns) throws IOException {
    }

    /**
     * @return the ruta_recursos
     */
    public String getRuta_recursos() {
        return ruta_recursos;
    }

    /**
     * @param ruta_recursos the ruta_recursos to set
     */
    public void setRuta_recursos(String ruta_recursos) {
        this.ruta_recursos = ruta_recursos;
    }
}
