/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import adm.sys.dao.AdmCrgxcol;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "cmp_remisioninv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmpRemisioninv.findAll", query = "SELECT c FROM CmpRemisioninv c"),
    @NamedQuery(name = "CmpRemisioninv.findByRmiId", query = "SELECT c FROM CmpRemisioninv c WHERE c.rmiId = :rmiId"),
    @NamedQuery(name = "CmpRemisioninv.findByRmiFechaproceso", query = "SELECT c FROM CmpRemisioninv c WHERE c.rmiFechaproceso = :rmiFechaproceso"),
    @NamedQuery(name = "CmpRemisioninv.findByRmiFechaentrega", query = "SELECT c FROM CmpRemisioninv c WHERE c.rmiFechaentrega = :rmiFechaentrega"),
    @NamedQuery(name = "CmpRemisioninv.findByRmiEst", query = "SELECT c FROM CmpRemisioninv c WHERE c.rmiEst = :rmiEst"),
    @NamedQuery(name = "CmpRemisioninv.findByIndversion", query = "SELECT c FROM CmpRemisioninv c WHERE c.indversion = :indversion"),
    @NamedQuery(name = "CmpRemisioninv.findByStrId", query = "SELECT c FROM CmpRemisioninv c WHERE c.strId = :strId")})
public class CmpRemisioninv implements Serializable {
    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "cmp_remisioninv_rmi_id_seq", allocationSize = 1, name = "cmp_remisioninv_rmi_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cmp_remisioninv_rmi_id_seq")
    @Id
    @Basic(optional = false)
    @Column(name = "rmi_id")
    private Long rmiId;
    @Column(name = "rmi_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rmiFechaproceso;
    @Column(name = "rmi_fechaentrega")
    @Temporal(TemporalType.DATE)
    private Date rmiFechaentrega;
    @Column(name = "rmi_obsrentrega")
    private String rmiObsrentrega;
    @Column(name = "rmi_est")
    private Boolean rmiEst;
    @Version
    @Column(name = "indversion")
    private Integer indversion;
    @Column(name = "str_id")
    private Long strId;
    @OneToMany(mappedBy = "rmiId")
    private List<CmpDetremision> cmpDetremisionList;
    @JoinColumn(name = "cxc_idorigen", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcIdorigen;
    @JoinColumn(name = "cxc_iddestino", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcIddestino;
    @OneToMany(mappedBy = "rmiId")
    private List<InvTransac> invTransacList;

    public CmpRemisioninv() {
    }

    public CmpRemisioninv(Long rmiId) {
        this.rmiId = rmiId;
    }

    public Long getRmiId() {
        return rmiId;
    }

    public void setRmiId(Long rmiId) {
        this.rmiId = rmiId;
    }

    public Date getRmiFechaproceso() {
        return rmiFechaproceso;
    }

    public void setRmiFechaproceso(Date rmiFechaproceso) {
        this.rmiFechaproceso = rmiFechaproceso;
    }

    public Date getRmiFechaentrega() {
        return rmiFechaentrega;
    }

    public void setRmiFechaentrega(Date rmiFechaentrega) {
        this.rmiFechaentrega = rmiFechaentrega;
    }

    public Boolean getRmiEst() {
        return rmiEst;
    }

    public void setRmiEst(Boolean rmiEst) {
        this.rmiEst = rmiEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public Long getStrId() {
        return strId;
    }

    public void setStrId(Long strId) {
        this.strId = strId;
    }

    @XmlTransient
    public List<CmpDetremision> getCmpDetremisionList() {
        return cmpDetremisionList;
    }

    public void setCmpDetremisionList(List<CmpDetremision> cmpDetremisionList) {
        this.cmpDetremisionList = cmpDetremisionList;
    }

    public AdmCrgxcol getCxcIdorigen() {
        return cxcIdorigen;
    }

    public void setCxcIdorigen(AdmCrgxcol cxcIdorigen) {
        this.cxcIdorigen = cxcIdorigen;
    }

    public AdmCrgxcol getCxcIddestino() {
        return cxcIddestino;
    }

    public void setCxcIddestino(AdmCrgxcol cxcIddestino) {
        this.cxcIddestino = cxcIddestino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rmiId != null ? rmiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmpRemisioninv)) {
            return false;
        }
        CmpRemisioninv other = (CmpRemisioninv) object;
        if ((this.rmiId == null && other.rmiId != null) || (this.rmiId != null && !this.rmiId.equals(other.rmiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.CmpRemisioninv[ rmiId=" + rmiId + " ]";
    }

    /**
     * @return the rmiObsrentrega
     */
    public String getRmiObsrentrega() {
        return rmiObsrentrega;
    }

    /**
     * @param rmiObsrentrega the rmiObsrentrega to set
     */
    public void setRmiObsrentrega(String rmiObsrentrega) {
        this.rmiObsrentrega = rmiObsrentrega;
    }

    /**
     * @return the invTransacList
     */
    public List<InvTransac> getInvTransacList() {
        return invTransacList;
    }

    /**
     * @param invTransacList the invTransacList to set
     */
    public void setInvTransacList(List<InvTransac> invTransacList) {
        this.invTransacList = invTransacList;
    }
    
}
