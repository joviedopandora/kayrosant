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
 * @author luis
 */
@Entity
@Table(name = "vnt_estventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntEstventa.findAll", query = "SELECT v FROM VntEstventa v"),
    @NamedQuery(name = "VntEstventa.findByEstrvntId", query = "SELECT v FROM VntEstventa v WHERE v.estrvntId = :estrvntId"),
    @NamedQuery(name = "VntEstventa.findByEstrvntNombre", query = "SELECT v FROM VntEstventa v WHERE v.estrvntNombre = :estrvntNombre"),
    @NamedQuery(name = "VntEstventa.findByEstrvntDesc", query = "SELECT v FROM VntEstventa v WHERE v.estrvntDesc = :estrvntDesc"),
    @NamedQuery(name = "VntEstventa.findByEstrvntEst", query = "SELECT v FROM VntEstventa v WHERE v.estrvntEst = :estrvntEst"),
    @NamedQuery(name = "VntEstventa.findByIndversion", query = "SELECT v FROM VntEstventa v WHERE v.indversion = :indversion")})
public class VntEstventa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "estrvnt_id")
    private Integer estrvntId;
    @Size(max = 100)
    @Column(name = "estrvnt_nombre")
    private String estrvntNombre;
    @Size(max = 2147483647)
    @Column(name = "estrvnt_desc")
    private String estrvntDesc;
    @Column(name = "estrvnt_est")
    private Boolean estrvntEst;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(mappedBy = "estrvntId")
    private List<VntRegistroventa> vntRegistroventaList;

    public VntEstventa() {
    }

    public VntEstventa(Integer estrvntId) {
        this.estrvntId = estrvntId;
    }

    public Integer getEstrvntId() {
        return estrvntId;
    }

    public void setEstrvntId(Integer estrvntId) {
        this.estrvntId = estrvntId;
    }

    public String getEstrvntNombre() {
        return estrvntNombre;
    }

    public void setEstrvntNombre(String estrvntNombre) {
        this.estrvntNombre = estrvntNombre;
    }

    public String getEstrvntDesc() {
        return estrvntDesc;
    }

    public void setEstrvntDesc(String estrvntDesc) {
        this.estrvntDesc = estrvntDesc;
    }

    public Boolean getEstrvntEst() {
        return estrvntEst;
    }

    public void setEstrvntEst(Boolean estrvntEst) {
        this.estrvntEst = estrvntEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<VntRegistroventa> getVntRegistroventaList() {
        return vntRegistroventaList;
    }

    public void setVntRegistroventaList(List<VntRegistroventa> vntRegistroventaList) {
        this.vntRegistroventaList = vntRegistroventaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estrvntId != null ? estrvntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntEstventa)) {
            return false;
        }
        VntEstventa other = (VntEstventa) object;
        if ((this.estrvntId == null && other.estrvntId != null) || (this.estrvntId != null && !this.estrvntId.equals(other.estrvntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntEstventa[ estrvntId=" + estrvntId + " ]";
    }
    
}
