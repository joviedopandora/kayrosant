/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.base;

import com.icesoft.faces.context.Resource;
import com.icesoft.faces.context.Resource.Options;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author extmchlfchacon
 */
public class RecursosOut implements Resource, Serializable {

    private String nombRecurso;
    private InputStream inputStream;
    private final Date lastModified;
    ByteArrayOutputStream outputStream;

    public RecursosOut(String nombRecurso, ByteArrayOutputStream outputStream) {
        this.nombRecurso = nombRecurso;
        this.lastModified = new Date();
        this.outputStream = outputStream;
    }

    @Override
    public String calculateDigest() {
        return nombRecurso;
    }

    @Override
    public InputStream open() throws IOException {
        if (inputStream == null) {
            byte[] byteArray = toByteArrayOut(outputStream);
            inputStream = new ByteArrayInputStream(byteArray);
        }
        return inputStream;
    }
    
      public  byte[] toByteArrayOut(ByteArrayOutputStream os) throws IOException {
        byte[] buf = os.toByteArray();

        return buf;
    }

    @Override
    public Date lastModified() {
        return lastModified;
    }

    @Override
    public void withOptions(Options optns) throws IOException {
    }
}
