/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.adm.rf.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "rf_barrio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfBarrio.findAll", query = "SELECT r FROM RfBarrio r"),
    @NamedQuery(name = "RfBarrio.findByBarId", query = "SELECT r FROM RfBarrio r WHERE r.barId = :barId"),
    @NamedQuery(name = "RfBarrio.findByBarDesc", query = "SELECT r FROM RfBarrio r WHERE r.barDesc = :barDesc"),
    @NamedQuery(name = "RfBarrio.findByBarCod", query = "SELECT r FROM RfBarrio r WHERE r.barCod = :barCod")})
public class RfBarrio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "bar_id")
    private Integer barId;
    @Size(max = 100)
    @Column(name = "bar_desc")
    private String barDesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "bar_cod")
    private String barCod;
    @JoinColumn(name = "loc_id", referencedColumnName = "loc_id")
    @ManyToOne(optional = false)
    private RfLocalidad locId;

    public RfBarrio() {
    }

    public RfBarrio(Integer barId) {
        this.barId = barId;
    }

    public RfBarrio(Integer barId, String barCod) {
        this.barId = barId;
        this.barCod = barCod;
    }

    public Integer getBarId() {
        return barId;
    }

    public void setBarId(Integer barId) {
        this.barId = barId;
    }

    public String getBarDesc() {
        return barDesc;
    }

    public void setBarDesc(String barDesc) {
        this.barDesc = barDesc;
    }

    public String getBarCod() {
        return barCod;
    }

    public void setBarCod(String barCod) {
        this.barCod = barCod;
    }

    public RfLocalidad getLocId() {
        return locId;
    }

    public void setLocId(RfLocalidad locId) {
        this.locId = locId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (barId != null ? barId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfBarrio)) {
            return false;
        }
        RfBarrio other = (RfBarrio) object;
        if ((this.barId == null && other.barId != null) || (this.barId != null && !this.barId.equals(other.barId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.RfBarrio[ barId=" + barId + " ]";
    }
    
}
