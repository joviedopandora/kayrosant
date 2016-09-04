/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "rf_tipocuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfTipocuenta.findAll", query = "SELECT r FROM RfTipocuenta r"),
    @NamedQuery(name = "RfTipocuenta.findByTctId", query = "SELECT r FROM RfTipocuenta r WHERE r.tctId = :tctId"),
    @NamedQuery(name = "RfTipocuenta.findByTctNombre", query = "SELECT r FROM RfTipocuenta r WHERE r.tctNombre = :tctNombre"),
    @NamedQuery(name = "RfTipocuenta.findByTctDescripcion", query = "SELECT r FROM RfTipocuenta r WHERE r.tctDescripcion = :tctDescripcion"),
    @NamedQuery(name = "RfTipocuenta.findByTctEstado", query = "SELECT r FROM RfTipocuenta r WHERE r.tctEstado = :tctEstado"),
    @NamedQuery(name = "RfTipocuenta.findByIndversion", query = "SELECT r FROM RfTipocuenta r WHERE r.indversion = :indversion")})
public class RfTipocuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tct_id")
    private Integer tctId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "tct_nombre")
    private String tctNombre;
    @Size(max = 2147483647)
    @Column(name = "tct_descripcion")
    private String tctDescripcion;
    @Column(name = "tct_estado")
    private Boolean tctEstado;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tctId")
    private List<InvBancoxprov> invBancoxprovList;

    public RfTipocuenta() {
    }

    public RfTipocuenta(Integer tctId) {
        this.tctId = tctId;
    }

    public RfTipocuenta(Integer tctId, String tctNombre) {
        this.tctId = tctId;
        this.tctNombre = tctNombre;
    }

    public Integer getTctId() {
        return tctId;
    }

    public void setTctId(Integer tctId) {
        this.tctId = tctId;
    }

    public String getTctNombre() {
        return tctNombre;
    }

    public void setTctNombre(String tctNombre) {
        this.tctNombre = tctNombre;
    }

    public String getTctDescripcion() {
        return tctDescripcion;
    }

    public void setTctDescripcion(String tctDescripcion) {
        this.tctDescripcion = tctDescripcion;
    }

    public Boolean getTctEstado() {
        return tctEstado;
    }

    public void setTctEstado(Boolean tctEstado) {
        this.tctEstado = tctEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<InvBancoxprov> getInvBancoxprovList() {
        return invBancoxprovList;
    }

    public void setInvBancoxprovList(List<InvBancoxprov> invBancoxprovList) {
        this.invBancoxprovList = invBancoxprovList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tctId != null ? tctId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfTipocuenta)) {
            return false;
        }
        RfTipocuenta other = (RfTipocuenta) object;
        if ((this.tctId == null && other.tctId != null) || (this.tctId != null && !this.tctId.equals(other.tctId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.RfTipocuenta[ tctId=" + tctId + " ]";
    }
    
}
