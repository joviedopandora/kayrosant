/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "vnt_rango_facturacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntRangoFacturacion.findByTipoCliente", query = "SELECT v FROM VntRangoFacturacion v WHERE v.vntRfTipocliente.tclId = :tclId"),
    @NamedQuery(name = "VntRangoFacturacion.findAll", query = "SELECT v FROM VntRangoFacturacion v"),
    @NamedQuery(name = "VntRangoFacturacion.findByIdRango", query = "SELECT v FROM VntRangoFacturacion v WHERE v.idRango = :idRango"),
    @NamedQuery(name = "VntRangoFacturacion.findByNumActual", query = "SELECT v FROM VntRangoFacturacion v WHERE v.numActual = :numActual")})
public class VntRangoFacturacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rango")
    private Long idRango;
    @Column(name = "num_actual")
    private Integer numActual;
    @JoinColumn(name = "tcl_id", referencedColumnName = "tcl_id")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private VntRfTipocliente vntRfTipocliente;

    public VntRangoFacturacion() {
    }

    public VntRangoFacturacion(Long idRango) {
        this.idRango = idRango;
    }

    public Long getIdRango() {
        return idRango;
    }

    public void setIdRango(Long idRango) {
        this.idRango = idRango;
    }

    public Integer getNumActual() {
        return numActual;
    }

    public void setNumActual(Integer numActual) {
        this.numActual = numActual;
    }

    public VntRfTipocliente getVntRfTipocliente() {
        return vntRfTipocliente;
    }

    public void setVntRfTipocliente(VntRfTipocliente vntRfTipocliente) {
        this.vntRfTipocliente = vntRfTipocliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRango != null ? idRango.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntRangoFacturacion)) {
            return false;
        }
        VntRangoFacturacion other = (VntRangoFacturacion) object;
        if ((this.idRango == null && other.idRango != null) || (this.idRango != null && !this.idRango.equals(other.idRango))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.VntRangoFacturacion[ idRango=" + idRango + " ]";
    }
    
}
