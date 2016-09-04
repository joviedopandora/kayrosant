/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "fin_cheque")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinCheque.findAll", query = "SELECT f FROM FinCheque f"),
    @NamedQuery(name = "FinCheque.findByChqId", query = "SELECT f FROM FinCheque f WHERE f.chqId = :chqId"),
    @NamedQuery(name = "FinCheque.findByChqNumcheque", query = "SELECT f FROM FinCheque f WHERE f.chqNumcheque = :chqNumcheque"),
    @NamedQuery(name = "FinCheque.findByChqCompegreso", query = "SELECT f FROM FinCheque f WHERE f.chqCompegreso = :chqCompegreso"),
    @NamedQuery(name = "FinCheque.findByChqFechaproceso", query = "SELECT f FROM FinCheque f WHERE f.chqFechaproceso = :chqFechaproceso")})
public class FinCheque implements Serializable {
    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "fin_cheque_chq_id_seq", name = "fin_cheque_chq_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "fin_cheque_chq_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @Column(name = "chq_id")
    private Long chqId;
    @Column(name = "chq_numcheque")
    private String chqNumcheque;
    @Size(min = 1, max = 20)
    @Column(name = "chq_compegreso")
    private String chqCompegreso;
    @Size(min = 1, max = 100)
    @Column(name = "chq_numcuenta")
    private String chqNumcuenta;       
    @Column(name = "chq_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date chqFechaproceso;
    @JoinColumn(name = "bnc_id", referencedColumnName = "bnc_id")
    @ManyToOne
    private RfBanco bncId;
    @JoinColumn(name = "sol_id", referencedColumnName = "sol_id")
    @ManyToOne(optional = false)
    private FinSolicitudcheque solId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;
     @OneToMany(mappedBy = "chqId")
    private List<FinFirmacheque> finFirmachequeList;

    public FinCheque() {
    }

    public FinCheque(Long chqId) {
        this.chqId = chqId;
    }

    public FinCheque(Long chqId, String chqNumcheque, String chqCompegreso, Date chqFechaegreso) {
        this.chqId = chqId;
        this.chqNumcheque = chqNumcheque;
        this.chqCompegreso = chqCompegreso;
    }

    public Long getChqId() {
        return chqId;
    }

    public void setChqId(Long chqId) {
        this.chqId = chqId;
    }

    public String getChqNumcheque() {
        return chqNumcheque;
    }

    public void setChqNumcheque(String chqNumcheque) {
        this.chqNumcheque = chqNumcheque;
    }

    public String getChqCompegreso() {
        return chqCompegreso;
    }

    public void setChqCompegreso(String chqCompegreso) {
        this.chqCompegreso = chqCompegreso;
    }

    public Date getChqFechaproceso() {
        return chqFechaproceso;
    }

    public void setChqFechaproceso(Date chqFechaproceso) {
        this.chqFechaproceso = chqFechaproceso;
    }

    public RfBanco getBncId() {
        return bncId;
    }

    public void setBncId(RfBanco bncId) {
        this.bncId = bncId;
    }

    public FinSolicitudcheque getSolId() {
        return solId;
    }

    public void setSolId(FinSolicitudcheque solId) {
        this.solId = solId;
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
        hash += (chqId != null ? chqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinCheque)) {
            return false;
        }
        FinCheque other = (FinCheque) object;
        if ((this.chqId == null && other.chqId != null) || (this.chqId != null && !this.chqId.equals(other.chqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinCheque[ chqId=" + chqId + " ]";
    }

    /**
     * @return the chqNumcuenta
     */
    public String getChqNumcuenta() {
        return chqNumcuenta;
    }

    /**
     * @param chqNumcuenta the chqNumcuenta to set
     */
    public void setChqNumcuenta(String chqNumcuenta) {
        this.chqNumcuenta = chqNumcuenta;
    }

    /**
     * @return the finFirmachequeList
     */
    public List<FinFirmacheque> getFinFirmachequeList() {
        return finFirmachequeList;
    }

    /**
     * @param finFirmachequeList the finFirmachequeList to set
     */
    public void setFinFirmachequeList(List<FinFirmacheque> finFirmachequeList) {
        this.finFirmachequeList = finFirmachequeList;
    }
    
}
