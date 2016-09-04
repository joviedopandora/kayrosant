/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.mod.logistica.dao;

import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sandra
 */
@Entity
@Table(name = "lgt_estadoevento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LgtEstadoevento.findAll", query = "SELECT l FROM LgtEstadoevento l"),
    @NamedQuery(name = "LgtEstadoevento.findByEevId", query = "SELECT l FROM LgtEstadoevento l WHERE l.eevId = :eevId"),
    @NamedQuery(name = "LgtEstadoevento.findByEevNombre", query = "SELECT l FROM LgtEstadoevento l WHERE l.eevNombre = :eevNombre"),
    @NamedQuery(name = "LgtEstadoevento.findByEevDescripcion", query = "SELECT l FROM LgtEstadoevento l WHERE l.eevDescripcion = :eevDescripcion"),
    @NamedQuery(name = "LgtEstadoevento.findByEevEstado", query = "SELECT l FROM LgtEstadoevento l WHERE l.eevEstado = :eevEstado"),
    @NamedQuery(name = "LgtEstadoevento.findByIndversion", query = "SELECT l FROM LgtEstadoevento l WHERE l.indversion = :indversion")})
public class LgtEstadoevento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "eev_id")
    private Integer eevId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "eev_nombre")
    private String eevNombre;
    @Size(max = 2147483647)
    @Column(name = "eev_descripcion")
    private String eevDescripcion;
    @Column(name = "eev_estado")
    private Boolean eevEstado;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(mappedBy = "eevId")
    private List<PopOrdenprod> popOrdenprodList;

    public LgtEstadoevento() {
    }

    public LgtEstadoevento(Integer eevId) {
        this.eevId = eevId;
    }

    public LgtEstadoevento(Integer eevId, String eevNombre) {
        this.eevId = eevId;
        this.eevNombre = eevNombre;
    }

    public Integer getEevId() {
        return eevId;
    }

    public void setEevId(Integer eevId) {
        this.eevId = eevId;
    }

    public String getEevNombre() {
        return eevNombre;
    }

    public void setEevNombre(String eevNombre) {
        this.eevNombre = eevNombre;
    }

    public String getEevDescripcion() {
        return eevDescripcion;
    }

    public void setEevDescripcion(String eevDescripcion) {
        this.eevDescripcion = eevDescripcion;
    }

    public Boolean getEevEstado() {
        return eevEstado;
    }

    public void setEevEstado(Boolean eevEstado) {
        this.eevEstado = eevEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<PopOrdenprod> getPopOrdenprodList() {
        return popOrdenprodList;
    }

    public void setPopOrdenprodList(List<PopOrdenprod> popOrdenprodList) {
        this.popOrdenprodList = popOrdenprodList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eevId != null ? eevId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LgtEstadoevento)) {
            return false;
        }
        LgtEstadoevento other = (LgtEstadoevento) object;
        if ((this.eevId == null && other.eevId != null) || (this.eevId != null && !this.eevId.equals(other.eevId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.LgtEstadoevento[ eevId=" + eevId + " ]";
    }
    
}
