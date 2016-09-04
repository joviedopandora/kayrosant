/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.adm.rf.dao;

import com.pandora.mod.venta.dao.VntFactura;
import com.pandora.mod.venta.dao.VntRemision;
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
@Table(name = "rf_estadofactura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfEstadofactura.findAll", query = "SELECT r FROM RfEstadofactura r ORDER BY r.eftNombre"),
    @NamedQuery(name = "RfEstadofactura.findByEftId", query = "SELECT r FROM RfEstadofactura r WHERE r.eftId = :eftId"),
    @NamedQuery(name = "RfEstadofactura.findByEftNombre", query = "SELECT r FROM RfEstadofactura r WHERE r.eftNombre = :eftNombre"),
    @NamedQuery(name = "RfEstadofactura.findByEftDescripcion", query = "SELECT r FROM RfEstadofactura r WHERE r.eftDescripcion = :eftDescripcion"),
    @NamedQuery(name = "RfEstadofactura.findByEftEstado", query = "SELECT r FROM RfEstadofactura r WHERE r.eftEstado = :eftEstado"),
    @NamedQuery(name = "RfEstadofactura.findByIndversion", query = "SELECT r FROM RfEstadofactura r WHERE r.indversion = :indversion")})
public class RfEstadofactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "eft_id")
    private Integer eftId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "eft_nombre")
    private String eftNombre;
    @Size(max = 2147483647)
    @Column(name = "eft_descripcion")
    private String eftDescripcion;
    @Column(name = "eft_estado")
    private Boolean eftEstado;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(mappedBy = "eftId")
    private List<VntFactura> vntFacturaList;
    @OneToMany(mappedBy = "eftId")
    private List<VntRemision> vntRemisionList;

    public RfEstadofactura() {
    }

    public RfEstadofactura(Integer eftId) {
        this.eftId = eftId;
    }

    public RfEstadofactura(Integer eftId, String eftNombre) {
        this.eftId = eftId;
        this.eftNombre = eftNombre;
    }

    public Integer getEftId() {
        return eftId;
    }

    public void setEftId(Integer eftId) {
        this.eftId = eftId;
    }

    public String getEftNombre() {
        return eftNombre;
    }

    public void setEftNombre(String eftNombre) {
        this.eftNombre = eftNombre;
    }

    public String getEftDescripcion() {
        return eftDescripcion;
    }

    public void setEftDescripcion(String eftDescripcion) {
        this.eftDescripcion = eftDescripcion;
    }

    public Boolean getEftEstado() {
        return eftEstado;
    }

    public void setEftEstado(Boolean eftEstado) {
        this.eftEstado = eftEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<VntFactura> getVntFacturaList() {
        return vntFacturaList;
    }

    public void setVntFacturaList(List<VntFactura> vntFacturaList) {
        this.vntFacturaList = vntFacturaList;
    }

    @XmlTransient
    public List<VntRemision> getVntRemisionList() {
        return vntRemisionList;
    }

    public void setVntRemisionList(List<VntRemision> vntRemisionList) {
        this.vntRemisionList = vntRemisionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eftId != null ? eftId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfEstadofactura)) {
            return false;
        }
        RfEstadofactura other = (RfEstadofactura) object;
        if ((this.eftId == null && other.eftId != null) || (this.eftId != null && !this.eftId.equals(other.eftId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.RfEstadofactura[ eftId=" + eftId + " ]";
    }
    
}
