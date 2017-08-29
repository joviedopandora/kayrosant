/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.web.venta;

import com.pandora.mod.venta.dao.VntDetallecliente;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author byrobles
 */
public class TablaVntDetalleCliente extends TablaBaseGrid{
    
    private VntDetallecliente vntDetallecliente = new VntDetallecliente();
    private Integer edad;
    private boolean contactoCopia=false;
   

    public TablaVntDetalleCliente() {
    }
    
    TablaVntDetalleCliente(VntDetallecliente pVntDetallecliente){
        this.vntDetallecliente = pVntDetallecliente;
    }

    /**
     * @return the vntDetallecliente
     */
    public VntDetallecliente getVntDetallecliente() {
        return vntDetallecliente;
    }

    /**
     * @param vntDetallecliente the vntDetallecliente to set
     */
    public void setVntDetallecliente(VntDetallecliente vntDetallecliente) {
        this.vntDetallecliente = vntDetallecliente;
    }

    /**
     * @return the edad
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.vntDetallecliente);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaVntDetalleCliente other = (TablaVntDetalleCliente) obj;
        if (!Objects.equals(this.vntDetallecliente, other.vntDetallecliente)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaDetalleCliente{" + "vntDetallecliente=" + vntDetallecliente + '}';
    }

    /**
     * @return the contactoCopia
     */
    public boolean isContactoCopia() {
        return contactoCopia;
    }

    /**
     * @param contactoCopia the contactoCopia to set
     */
    public void setContactoCopia(boolean contactoCopia) {
        this.contactoCopia = contactoCopia;
    }
}
