/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.adm.dao;

import adm.sys.dao.AdmCrgxcol;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "inv_grupotransac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvGrupotransac.findAll", query = "SELECT i FROM InvGrupotransac i"),
    @NamedQuery(name = "InvGrupotransac.findByGtrId", query = "SELECT i FROM InvGrupotransac i WHERE i.gtrId = :gtrId"),
    @NamedQuery(name = "InvGrupotransac.findByGtrObservacion", query = "SELECT i FROM InvGrupotransac i WHERE i.gtrObservacion = :gtrObservacion"),
    @NamedQuery(name = "InvGrupotransac.findByGtrFechaproceso", query = "SELECT i FROM InvGrupotransac i WHERE i.gtrFechaproceso = :gtrFechaproceso"),
    @NamedQuery(name = "InvGrupotransac.findByGtrEstado", query = "SELECT i FROM InvGrupotransac i WHERE i.gtrEstado = :gtrEstado"),
    @NamedQuery(name = "InvGrupotransac.findByIndversion", query = "SELECT i FROM InvGrupotransac i WHERE i.indversion = :indversion"),
    @NamedQuery(name = "InvGrupotransac.findByStrId", query = "SELECT i FROM InvGrupotransac i WHERE i.strId = :strId")})
public class InvGrupotransac implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gtr_id")
    private Long gtrId;
    @Size(max = 2147483647)
    @Column(name = "gtr_observacion")
    private String gtrObservacion;
    @Column(name = "gtr_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gtrFechaproceso;
    @Column(name = "gtr_estado")
    private Boolean gtrEstado;
    @Column(name = "indversion")
    private Integer indversion;
    @Column(name = "str_id")
    private BigInteger strId;
    @JoinColumn(name = "itr_id", referencedColumnName = "itr_id")
    @ManyToOne
    private InvTransac itrId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;
    @OneToMany(mappedBy = "gtrId")
    private List<PopProdxservxop> popProdxservxopList;

    public InvGrupotransac() {
    }

    public InvGrupotransac(Long gtrId) {
        this.gtrId = gtrId;
    }

    public Long getGtrId() {
        return gtrId;
    }

    public void setGtrId(Long gtrId) {
        this.gtrId = gtrId;
    }

    public String getGtrObservacion() {
        return gtrObservacion;
    }

    public void setGtrObservacion(String gtrObservacion) {
        this.gtrObservacion = gtrObservacion;
    }

    public Date getGtrFechaproceso() {
        return gtrFechaproceso;
    }

    public void setGtrFechaproceso(Date gtrFechaproceso) {
        this.gtrFechaproceso = gtrFechaproceso;
    }

    public Boolean getGtrEstado() {
        return gtrEstado;
    }

    public void setGtrEstado(Boolean gtrEstado) {
        this.gtrEstado = gtrEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public BigInteger getStrId() {
        return strId;
    }

    public void setStrId(BigInteger strId) {
        this.strId = strId;
    }

    public InvTransac getItrId() {
        return itrId;
    }

    public void setItrId(InvTransac itrId) {
        this.itrId = itrId;
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    @XmlTransient
    public List<PopProdxservxop> getPopProdxservxopList() {
        return popProdxservxopList;
    }

    public void setPopProdxservxopList(List<PopProdxservxop> popProdxservxopList) {
        this.popProdxservxopList = popProdxservxopList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gtrId != null ? gtrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvGrupotransac)) {
            return false;
        }
        InvGrupotransac other = (InvGrupotransac) object;
        if ((this.gtrId == null && other.gtrId != null) || (this.gtrId != null && !this.gtrId.equals(other.gtrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.InvGrupotransac[ gtrId=" + gtrId + " ]";
    }
    
}
