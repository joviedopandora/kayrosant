/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "rf_comocontacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfComocontacto.findAll", query = "SELECT r FROM RfComocontacto r"),
    @NamedQuery(name = "RfComocontacto.findByCmcId", query = "SELECT r FROM RfComocontacto r WHERE r.cmcId = :cmcId"),
    @NamedQuery(name = "RfComocontacto.findByCmcNombre", query = "SELECT r FROM RfComocontacto r WHERE r.cmcNombre = :cmcNombre"),
    @NamedQuery(name = "RfComocontacto.findByCmcEstado", query = "SELECT r FROM RfComocontacto r WHERE r.cmcEstado = :cmcEstado")})
public class RfComocontacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cmc_id")
    private Integer cmcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "cmc_nombre")
    private String cmcNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cmc_estado")
    private boolean cmcEstado;
    @OneToMany(mappedBy = "rfComocontacto", fetch = FetchType.LAZY)
    private List<VntRegistroLlamada> vntRegistroLlamadaList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cmc_defecto")
    private boolean cmcDefecto;

    public RfComocontacto() {
    }

    public RfComocontacto(Integer cmcId) {
        this.cmcId = cmcId;
    }

    public RfComocontacto(Integer cmcId, String cmcNombre, boolean cmcEstado) {
        this.cmcId = cmcId;
        this.cmcNombre = cmcNombre;
        this.cmcEstado = cmcEstado;
    }

    public Integer getCmcId() {
        return cmcId;
    }

    public void setCmcId(Integer cmcId) {
        this.cmcId = cmcId;
    }

    public String getCmcNombre() {
        return cmcNombre;
    }

    public void setCmcNombre(String cmcNombre) {
        this.cmcNombre = cmcNombre;
    }

    public boolean getCmcEstado() {
        return cmcEstado;
    }

    public void setCmcEstado(boolean cmcEstado) {
        this.cmcEstado = cmcEstado;
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
        hash += (cmcId != null ? cmcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfComocontacto)) {
            return false;
        }
        RfComocontacto other = (RfComocontacto) object;
        if ((this.cmcId == null && other.cmcId != null) || (this.cmcId != null && !this.cmcId.equals(other.cmcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.RfComocontacto[ cmcId=" + cmcId + " ]";
    }

    /**
     * @return the cmcDefecto
     */
    public boolean isCmcDefecto() {
        return cmcDefecto;
    }

    /**
     * @param cmcDefecto the cmcDefecto to set
     */
    public void setCmcDefecto(boolean cmcDefecto) {
        this.cmcDefecto = cmcDefecto;
    }
}
