/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmEmpresa;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "fin_centrcost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinCentrcost.findAll", query = "SELECT f FROM FinCentrcost f"),
    @NamedQuery(name = "FinCentrcost.findByCctId", query = "SELECT f FROM FinCentrcost f WHERE f.cctId = :cctId"),
    @NamedQuery(name = "FinCentrcost.findByCctNombre", query = "SELECT f FROM FinCentrcost f WHERE f.cctNombre = :cctNombre"),
    @NamedQuery(name = "FinCentrcost.findByCctDesc", query = "SELECT f FROM FinCentrcost f WHERE f.cctDesc = :cctDesc"),
    @NamedQuery(name = "FinCentrcost.findByCctEst", query = "SELECT f FROM FinCentrcost f WHERE f.cctEst = :cctEst")})
public class FinCentrcost implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cct_id")
    private Integer cctId;
    @Column(name = "cct_nombre")
    private Integer cctNombre;
    @Size(max = 2147483647)
    @Column(name = "cct_desc")
    private String cctDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cct_est")
    private boolean cctEst;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne
    private AdmEmpresa empId;
    @OneToMany(mappedBy = "cctId")
    private List<AdmColxemp> admColxempList;

    public FinCentrcost() {
    }

    public FinCentrcost(Integer cctId) {
        this.cctId = cctId;
    }

    public FinCentrcost(Integer cctId, boolean cctEst) {
        this.cctId = cctId;
        this.cctEst = cctEst;
    }

    public Integer getCctId() {
        return cctId;
    }

    public void setCctId(Integer cctId) {
        this.cctId = cctId;
    }

    public Integer getCctNombre() {
        return cctNombre;
    }

    public void setCctNombre(Integer cctNombre) {
        this.cctNombre = cctNombre;
    }

    public String getCctDesc() {
        return cctDesc;
    }

    public void setCctDesc(String cctDesc) {
        this.cctDesc = cctDesc;
    }

    public boolean getCctEst() {
        return cctEst;
    }

    public void setCctEst(boolean cctEst) {
        this.cctEst = cctEst;
    }

    public AdmEmpresa getEmpId() {
        return empId;
    }

    public void setEmpId(AdmEmpresa empId) {
        this.empId = empId;
    }

    @XmlTransient
    public List<AdmColxemp> getAdmColxempList() {
        return admColxempList;
    }

    public void setAdmColxempList(List<AdmColxemp> admColxempList) {
        this.admColxempList = admColxempList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cctId != null ? cctId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinCentrcost)) {
            return false;
        }
        FinCentrcost other = (FinCentrcost) object;
        if ((this.cctId == null && other.cctId != null) || (this.cctId != null && !this.cctId.equals(other.cctId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinCentrcost[ cctId=" + cctId + " ]";
    }
    
}
