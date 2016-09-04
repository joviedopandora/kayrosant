/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.ordenprod.dao;

import adm.sys.dao.AdmCrgxcol;
import java.io.Serializable;
import java.math.BigInteger;
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
 * @author HOGAR PC
 */
@Entity
@Table(name = "log_ordenprod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogOrdenprod.findAll", query = "SELECT l FROM LogOrdenprod l"),
    @NamedQuery(name = "LogOrdenprod.findByOprlId", query = "SELECT l FROM LogOrdenprod l WHERE l.oprlId = :oprlId"),
    @NamedQuery(name = "LogOrdenprod.findByOprId", query = "SELECT l FROM LogOrdenprod l WHERE l.oprId = :oprId"),
    @NamedQuery(name = "LogOrdenprod.findByOprlFechaproceso", query = "SELECT l FROM LogOrdenprod l WHERE l.oprlFechaproceso = :oprlFechaproceso"),
    @NamedQuery(name = "LogOrdenprod.findByCxcIdold", query = "SELECT l FROM LogOrdenprod l WHERE l.cxcIdold = :cxcIdold"),
    @NamedQuery(name = "LogOrdenprod.findByCxcIdnew", query = "SELECT l FROM LogOrdenprod l WHERE l.cxcIdnew = :cxcIdnew"),
    @NamedQuery(name = "LogOrdenprod.findByEopIdold", query = "SELECT l FROM LogOrdenprod l WHERE l.eopIdold = :eopIdold"),
    @NamedQuery(name = "LogOrdenprod.findByOprlEvento", query = "SELECT l FROM LogOrdenprod l WHERE l.oprlEvento = :oprlEvento")})
public class LogOrdenprod implements Serializable {

    @Column(name = "opr_id")
    private Long oprId;
    @JoinColumn(name = "cxc_idnew", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcIdnew;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "oprl_id")
    private Long oprlId;
    @Column(name = "oprl_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oprlFechaproceso;
    @Column(name = "cxc_idold")
    private Integer cxcIdold;
    @Column(name = "eop_idold")
    private Integer eopIdold;
    @Size(max = 500)
    @Column(name = "oprl_evento")
    private String oprlEvento;
    @JoinColumn(name = "eop_idnew", referencedColumnName = "eop_id")
    @ManyToOne
    private RfEstadoOP eopIdnew;

    public LogOrdenprod() {
    }

    public LogOrdenprod(Long oprlId) {
        this.oprlId = oprlId;
    }

    public Long getOprlId() {
        return oprlId;
    }

    public void setOprlId(Long oprlId) {
        this.oprlId = oprlId;
    }

    public Long getOprId() {
        return oprId;
    }

    public void setOprId(Long oprId) {
        this.oprId = oprId;
    }

    public Date getOprlFechaproceso() {
        return oprlFechaproceso;
    }

    public void setOprlFechaproceso(Date oprlFechaproceso) {
        this.oprlFechaproceso = oprlFechaproceso;
    }

    public Integer getCxcIdold() {
        return cxcIdold;
    }

    public void setCxcIdold(Integer cxcIdold) {
        this.cxcIdold = cxcIdold;
    }

    public Integer getEopIdold() {
        return eopIdold;
    }

    public void setEopIdold(Integer eopIdold) {
        this.eopIdold = eopIdold;
    }

    public String getOprlEvento() {
        return oprlEvento;
    }

    public void setOprlEvento(String oprlEvento) {
        this.oprlEvento = oprlEvento;
    }

    public RfEstadoOP getEopIdnew() {
        return eopIdnew;
    }

    public void setEopIdnew(RfEstadoOP eopIdnew) {
        this.eopIdnew = eopIdnew;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oprlId != null ? oprlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogOrdenprod)) {
            return false;
        }
        LogOrdenprod other = (LogOrdenprod) object;
        if ((this.oprlId == null && other.oprlId != null) || (this.oprlId != null && !this.oprlId.equals(other.oprlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.mod.ordenprod.dao.LogOrdenprod[ oprlId=" + oprlId + " ]";
    }


    public AdmCrgxcol getCxcIdnew() {
        return cxcIdnew;
    }

    public void setCxcIdnew(AdmCrgxcol cxcIdnew) {
        this.cxcIdnew = cxcIdnew;
    }
    
}
