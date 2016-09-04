/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.ordenprod.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "pop_cxccitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PopCxccitacion.findAll", query = "SELECT p FROM PopCxccitacion p"),
    @NamedQuery(name = "PopCxccitacion.findByCxciId", query = "SELECT p FROM PopCxccitacion p WHERE p.cxciId = :cxciId"),
    @NamedQuery(name = "PopCxccitacion.findByCxciCitacion", query = "SELECT p FROM PopCxccitacion p WHERE p.cxciCitacion = :cxciCitacion"),
    @NamedQuery(name = "PopCxccitacion.findByCxciEstado", query = "SELECT p FROM PopCxccitacion p WHERE p.cxciEstado = :cxciEstado ORDER BY p.cxciCitacion")})
public class PopCxccitacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cxci_id")
    private Integer cxciId;
    @Size(max = 2147483647)
    @Column(name = "cxci_citacion")
    private String cxciCitacion;
    @Column(name = "cxci_estado")
    private Boolean cxciEstado;

    public PopCxccitacion() {
    }

    public PopCxccitacion(Integer cxciId) {
        this.cxciId = cxciId;
    }

    public Integer getCxciId() {
        return cxciId;
    }

    public void setCxciId(Integer cxciId) {
        this.cxciId = cxciId;
    }

    public String getCxciCitacion() {
        return cxciCitacion;
    }

    public void setCxciCitacion(String cxciCitacion) {
        this.cxciCitacion = cxciCitacion;
    }

    public Boolean getCxciEstado() {
        return cxciEstado;
    }

    public void setCxciEstado(Boolean cxciEstado) {
        this.cxciEstado = cxciEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxciId != null ? cxciId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PopCxccitacion)) {
            return false;
        }
        PopCxccitacion other = (PopCxccitacion) object;
        if ((this.cxciId == null && other.cxciId != null) || (this.cxciId != null && !this.cxciId.equals(other.cxciId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "voo.PopCxccitacion[ cxciId=" + cxciId + " ]";
    }
    
}
