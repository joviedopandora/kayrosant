/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import adm.sys.dao.AdmCrgxcol;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "vnt_colxventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntColxventa.findAll", query = "SELECT v FROM VntColxventa v"),
    @NamedQuery(name = "VntColxventa.findByColxvId", query = "SELECT v FROM VntColxventa v WHERE v.colxvId = :colxvId"),
    @NamedQuery(name = "VntColxventa.findByColxvObsr", query = "SELECT v FROM VntColxventa v WHERE v.colxvObsr = :colxvObsr"),
    @NamedQuery(name = "VntColxventa.findByColxvEst", query = "SELECT v FROM VntColxventa v WHERE v.colxvEst = :colxvEst"),
    @NamedQuery(name = "VntColxventa.findByIndversion", query = "SELECT v FROM VntColxventa v WHERE v.indversion = :indversion")})
public class VntColxventa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "colxv_id")
    private Long colxvId;
    @Size(max = 2147483647)
    @Column(name = "colxv_obsr")
    private String colxvObsr;
    @Column(name = "colxv_est")
    private Boolean colxvEst;
    @Column(name = "indversion")
    private Integer indversion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "colxv_fechaproc")           
    private Date colxvDechaproc;
    @JoinColumn(name = "rgvt_id", referencedColumnName = "rgvt_id")
    @ManyToOne
    private VntRegistroventa rgvtId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    public VntColxventa() {
    }

    public VntColxventa(Long colxvId) {
        this.colxvId = colxvId;
    }

    public Long getColxvId() {
        return colxvId;
    }

    public void setColxvId(Long colxvId) {
        this.colxvId = colxvId;
    }

    public String getColxvObsr() {
        return colxvObsr;
    }

    public void setColxvObsr(String colxvObsr) {
        this.colxvObsr = colxvObsr;
    }

    public Boolean getColxvEst() {
        return colxvEst;
    }

    public void setColxvEst(Boolean colxvEst) {
        this.colxvEst = colxvEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public VntRegistroventa getRgvtId() {
        return rgvtId;
    }

    public void setRgvtId(VntRegistroventa rgvtId) {
        this.rgvtId = rgvtId;
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (colxvId != null ? colxvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntColxventa)) {
            return false;
        }
        VntColxventa other = (VntColxventa) object;
        if ((this.colxvId == null && other.colxvId != null) || (this.colxvId != null && !this.colxvId.equals(other.colxvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntColxventa[ colxvId=" + colxvId + " ]";
    }

    /**
     * @return the colxvDechaproc
     */
    public Date getColxvDechaproc() {
        return colxvDechaproc;
    }

    /**
     * @param colxvDechaproc the colxvDechaproc to set
     */
    public void setColxvDechaproc(Date colxvDechaproc) {
        this.colxvDechaproc = colxvDechaproc;
    }
    
}
