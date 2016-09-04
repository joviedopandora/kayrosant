/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.mod.venta.dao;

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
 * @author juandavid
 */
@Entity
@Table(name = "vnt_acteconomica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntActeconomica.findAll", query = "SELECT v FROM VntActeconomica v"),
    @NamedQuery(name = "VntActeconomica.findByAteId", query = "SELECT v FROM VntActeconomica v WHERE v.ateId = :ateId"),
    @NamedQuery(name = "VntActeconomica.findByAteNombre", query = "SELECT v FROM VntActeconomica v WHERE v.ateNombre = :ateNombre"),
    @NamedQuery(name = "VntActeconomica.findByAteDescripcion", query = "SELECT v FROM VntActeconomica v WHERE v.ateDescripcion = :ateDescripcion"),
    @NamedQuery(name = "VntActeconomica.findByAteEstado", query = "SELECT v FROM VntActeconomica v WHERE v.ateEstado = :ateEstado ORDER BY v.ateNombre"),
    @NamedQuery(name = "VntActeconomica.findByIndversion", query = "SELECT v FROM VntActeconomica v WHERE v.indversion = :indversion")})
public class VntActeconomica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ate_id")
    private Integer ateId;
    @Size(max = 300)
    @Column(name = "ate_nombre")
    private String ateNombre;
    @Size(max = 2147483647)
    @Column(name = "ate_descripcion")
    private String ateDescripcion;
    @Column(name = "ate_estado")
    private Boolean ateEstado;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(mappedBy = "ateId")
    private List<VntCliente> vntClienteList;

    public VntActeconomica() {
    }

    public VntActeconomica(Integer ateId) {
        this.ateId = ateId;
    }

    public Integer getAteId() {
        return ateId;
    }

    public void setAteId(Integer ateId) {
        this.ateId = ateId;
    }

    public String getAteNombre() {
        return ateNombre;
    }

    public void setAteNombre(String ateNombre) {
        this.ateNombre = ateNombre;
    }

    public String getAteDescripcion() {
        return ateDescripcion;
    }

    public void setAteDescripcion(String ateDescripcion) {
        this.ateDescripcion = ateDescripcion;
    }

    public Boolean getAteEstado() {
        return ateEstado;
    }

    public void setAteEstado(Boolean ateEstado) {
        this.ateEstado = ateEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<VntCliente> getVntClienteList() {
        return vntClienteList;
    }

    public void setVntClienteList(List<VntCliente> vntClienteList) {
        this.vntClienteList = vntClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ateId != null ? ateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntActeconomica)) {
            return false;
        }
        VntActeconomica other = (VntActeconomica) object;
        if ((this.ateId == null && other.ateId != null) || (this.ateId != null && !this.ateId.equals(other.ateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntActeconomica[ ateId=" + ateId + " ]";
    }
    
}
