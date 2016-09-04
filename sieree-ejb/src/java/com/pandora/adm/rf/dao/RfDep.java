/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.adm.rf.dao;

import adm.sys.dao.AdmColaborador;
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
 * @author luis
 */
@Entity
@Table(name = "rf_dep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfDep.findAll", query = "SELECT r FROM RfDep r ORDER BY r.depDesc ASC"),
    @NamedQuery(name = "RfDep.findByDepId", query = "SELECT r FROM RfDep r WHERE r.depId = :depId"),
    @NamedQuery(name = "RfDep.findByDepDesc", query = "SELECT r FROM RfDep r WHERE r.depDesc = :depDesc")})
public class RfDep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "dep_id")
    private String depId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "dep_desc")
    private String depDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depId")
    private List<RfCiudad> rfCiudadList;

    public RfDep() {
    }

    public RfDep(String depId) {
        this.depId = depId;
    }

    public RfDep(String depId, String depDesc) {
        this.depId = depId;
        this.depDesc = depDesc;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepDesc() {
        return depDesc;
    }

    public void setDepDesc(String depDesc) {
        this.depDesc = depDesc;
    }

    @XmlTransient
    public List<RfCiudad> getRfCiudadList() {
        return rfCiudadList;
    }

    public void setRfCiudadList(List<RfCiudad> rfCiudadList) {
        this.rfCiudadList = rfCiudadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depId != null ? depId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfDep)) {
            return false;
        }
        RfDep other = (RfDep) object;
        if ((this.depId == null && other.depId != null) || (this.depId != null && !this.depId.equals(other.depId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.RfDep[ depId=" + depId + " ]";
    }


    
}
