/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

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
 * @author carlos
 */
@Entity
@Table(name = "cmp_rf_tiporequisicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmpRfTiporequisicion.findAll", query = "SELECT c FROM CmpRfTiporequisicion c"),
    @NamedQuery(name = "CmpRfTiporequisicion.findByTrqId", query = "SELECT c FROM CmpRfTiporequisicion c WHERE c.trqId = :trqId"),
    @NamedQuery(name = "CmpRfTiporequisicion.findByTrqNombre", query = "SELECT c FROM CmpRfTiporequisicion c WHERE c.trqNombre = :trqNombre"),
    @NamedQuery(name = "CmpRfTiporequisicion.findByTrqDescripcion", query = "SELECT c FROM CmpRfTiporequisicion c WHERE c.trqDescripcion = :trqDescripcion"),
    @NamedQuery(name = "CmpRfTiporequisicion.findByTrqEstado", query = "SELECT c FROM CmpRfTiporequisicion c WHERE c.trqEstado = :trqEstado"),
    @NamedQuery(name = "CmpRfTiporequisicion.findByIndversion", query = "SELECT c FROM CmpRfTiporequisicion c WHERE c.indversion = :indversion")})
public class CmpRfTiporequisicion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "trq_id")
    private Integer trqId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "trq_nombre")
    private String trqNombre;
    @Size(max = 2147483647)
    @Column(name = "trq_descripcion")
    private String trqDescripcion;
    @Column(name = "trq_estado")
    private Boolean trqEstado;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(mappedBy = "trqId")
    private List<CmpRequiscomp> cmpRequiscompList;

    public CmpRfTiporequisicion() {
    }

    public CmpRfTiporequisicion(Integer trqId) {
        this.trqId = trqId;
    }

    public CmpRfTiporequisicion(Integer trqId, String trqNombre) {
        this.trqId = trqId;
        this.trqNombre = trqNombre;
    }

    public Integer getTrqId() {
        return trqId;
    }

    public void setTrqId(Integer trqId) {
        this.trqId = trqId;
    }

    public String getTrqNombre() {
        return trqNombre;
    }

    public void setTrqNombre(String trqNombre) {
        this.trqNombre = trqNombre;
    }

    public String getTrqDescripcion() {
        return trqDescripcion;
    }

    public void setTrqDescripcion(String trqDescripcion) {
        this.trqDescripcion = trqDescripcion;
    }

    public Boolean getTrqEstado() {
        return trqEstado;
    }

    public void setTrqEstado(Boolean trqEstado) {
        this.trqEstado = trqEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<CmpRequiscomp> getCmpRequiscompList() {
        return cmpRequiscompList;
    }

    public void setCmpRequiscompList(List<CmpRequiscomp> cmpRequiscompList) {
        this.cmpRequiscompList = cmpRequiscompList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trqId != null ? trqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmpRfTiporequisicion)) {
            return false;
        }
        CmpRfTiporequisicion other = (CmpRfTiporequisicion) object;
        if ((this.trqId == null && other.trqId != null) || (this.trqId != null && !this.trqId.equals(other.trqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.CmpRfTiporequisicion[ trqId=" + trqId + " ]";
    }
    
}
