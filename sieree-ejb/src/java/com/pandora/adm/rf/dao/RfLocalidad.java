/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.adm.rf.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author luis
 */
@Entity
@Table(name = "rf_localidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfLocalidad.findAll", query = "SELECT r FROM RfLocalidad r"),
    @NamedQuery(name = "RfLocalidad.findByLocId", query = "SELECT r FROM RfLocalidad r WHERE r.locId = :locId"),
    @NamedQuery(name = "RfLocalidad.findByLocDesc", query = "SELECT r FROM RfLocalidad r WHERE r.locDesc = :locDesc"),
    @NamedQuery(name = "RfLocalidad.findByLocCod", query = "SELECT r FROM RfLocalidad r WHERE r.locCod = :locCod")})
public class RfLocalidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "loc_id")
    private Long locId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "loc_desc")
    private String locDesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "loc_cod")
    private String locCod;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locId")
    private List<RfBarrio> rfBarrioList;
    @JoinColumn(name = "ciu_id", referencedColumnName = "ciu_id")
    @ManyToOne(optional = false)
    private RfCiudad ciuId;

    public RfLocalidad() {
    }

    public RfLocalidad(Long locId) {
        this.locId = locId;
    }

    public RfLocalidad(Long locId, String locDesc, String locCod) {
        this.locId = locId;
        this.locDesc = locDesc;
        this.locCod = locCod;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public String getLocDesc() {
        return locDesc;
    }

    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    public String getLocCod() {
        return locCod;
    }

    public void setLocCod(String locCod) {
        this.locCod = locCod;
    }

    @XmlTransient
    public List<RfBarrio> getRfBarrioList() {
        return rfBarrioList;
    }

    public void setRfBarrioList(List<RfBarrio> rfBarrioList) {
        this.rfBarrioList = rfBarrioList;
    }

    public RfCiudad getCiuId() {
        return ciuId;
    }

    public void setCiuId(RfCiudad ciuId) {
        this.ciuId = ciuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locId != null ? locId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfLocalidad)) {
            return false;
        }
        RfLocalidad other = (RfLocalidad) object;
        if ((this.locId == null && other.locId != null) || (this.locId != null && !this.locId.equals(other.locId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.RfLocalidad[ locId=" + locId + " ]";
    }
    
}
