/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author Breyner Robles
 */
@Entity
@Table(name = "rf_tipocierrellamada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfTipocierrellamada.findAll", query = "SELECT r FROM RfTipocierrellamada r"),
    @NamedQuery(name = "RfTipocierrellamada.findByTipocierreId", query = "SELECT r FROM RfTipocierrellamada r WHERE r.tipocierreId = :tipocierreId"),
    @NamedQuery(name = "RfTipocierrellamada.findByTipocierreNombre", query = "SELECT r FROM RfTipocierrellamada r WHERE r.tipocierreNombre = :tipocierreNombre"),
    @NamedQuery(name = "RfTipocierrellamada.findByTipocierreEstado", query = "SELECT r FROM RfTipocierrellamada r WHERE r.tipocierreEstado = :tipocierreEstado")})
public class RfTipocierrellamada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipocierre_id")
    private Integer tipocierreId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "tipocierre_nombre")
    private String tipocierreNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipocierre_estado")
    private boolean tipocierreEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rfTipocierrellamada", fetch = FetchType.LAZY)
    private List<VntRegistroLlamada> vntRegistroLlamadaList;

    public RfTipocierrellamada() {
    }

    public RfTipocierrellamada(Integer tipocierreId) {
        this.tipocierreId = tipocierreId;
    }

    public RfTipocierrellamada(Integer tipocierreId, String tipocierreNombre, boolean tipocierreEstado) {
        this.tipocierreId = tipocierreId;
        this.tipocierreNombre = tipocierreNombre;
        this.tipocierreEstado = tipocierreEstado;
    }

    public Integer getTipocierreId() {
        return tipocierreId;
    }

    public void setTipocierreId(Integer tipocierreId) {
        this.tipocierreId = tipocierreId;
    }

    public String getTipocierreNombre() {
        return tipocierreNombre;
    }

    public void setTipocierreNombre(String tipocierreNombre) {
        this.tipocierreNombre = tipocierreNombre;
    }

    public boolean getTipocierreEstado() {
        return tipocierreEstado;
    }

    public void setTipocierreEstado(boolean tipocierreEstado) {
        this.tipocierreEstado = tipocierreEstado;
    }

    @XmlTransient
    public List<VntRegistroLlamada> getVntRegistroLlamadaList() {
        return vntRegistroLlamadaList;
    }

    public void setVntRegistroLlamadaList(List<VntRegistroLlamada> vntRegistroLlamadaList) {
        this.vntRegistroLlamadaList = vntRegistroLlamadaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipocierreId != null ? tipocierreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfTipocierrellamada)) {
            return false;
        }
        RfTipocierrellamada other = (RfTipocierrellamada) object;
        if ((this.tipocierreId == null && other.tipocierreId != null) || (this.tipocierreId != null && !this.tipocierreId.equals(other.tipocierreId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.RfTipocierrellamada[ tipocierreId=" + tipocierreId + " ]";
    }
    
}
