/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

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
@Table(name = "fin_transferencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinTransferencia.findAll", query = "SELECT f FROM FinTransferencia f"),
    @NamedQuery(name = "FinTransferencia.findByTnfId", query = "SELECT f FROM FinTransferencia f WHERE f.tnfId = :tnfId"),
    @NamedQuery(name = "FinTransferencia.findByTnfFechaproceso", query = "SELECT f FROM FinTransferencia f WHERE f.tnfFechaproceso = :tnfFechaproceso"),
    @NamedQuery(name = "FinTransferencia.findByTnfNumtransaccion", query = "SELECT f FROM FinTransferencia f WHERE f.tnfNumtransaccion = :tnfNumtransaccion"),
    @NamedQuery(name = "FinTransferencia.findByTnfNumcuentaorigen", query = "SELECT f FROM FinTransferencia f WHERE f.tnfNumcuentaorigen = :tnfNumcuentaorigen"),
    @NamedQuery(name = "FinTransferencia.findByTnfNumcuentadestino", query = "SELECT f FROM FinTransferencia f WHERE f.tnfNumcuentadestino = :tnfNumcuentadestino")})
public class FinTransferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "fin_transferencia_tnf_id_seq", name = "fin_transferencia_tnf_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "fin_transferencia_tnf_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @Column(name = "tnf_id")
    private Long tnfId;
    @Column(name = "tnf_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tnfFechaproceso;
    @Size(max = 2147483647)
    @Column(name = "tnf_numtransaccion")
    private String tnfNumtransaccion;
    @Size(max = 100)
    @Column(name = "tnf_numcuentaorigen")
    private String tnfNumcuentaorigen;
    @Size(max = 100)
    @Column(name = "tnf_numcuentadestino")
    private String tnfNumcuentadestino;
    @Column(name = "tnf_estado")
    private Boolean tnfEstado;
    @Column(name = "str_id")
    private Long strId;
    @JoinColumn(name = "bnc_idorigen", referencedColumnName = "bnc_id")
    @ManyToOne
    private RfBanco bncIdorigen;
    @JoinColumn(name = "bnc_iddestino", referencedColumnName = "bnc_id")
    @ManyToOne
    private RfBanco bncIddestino;
    @JoinColumn(name = "cpg_id", referencedColumnName = "cpg_id")
    @ManyToOne(optional = false)
    private FinCronogramapago cpgId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    public FinTransferencia() {
    }

    public FinTransferencia(Long tnfId) {
        this.tnfId = tnfId;
    }

    public Long getTnfId() {
        return tnfId;
    }

    public void setTnfId(Long tnfId) {
        this.tnfId = tnfId;
    }

    public Date getTnfFechaproceso() {
        return tnfFechaproceso;
    }

    public void setTnfFechaproceso(Date tnfFechaproceso) {
        this.tnfFechaproceso = tnfFechaproceso;
    }

    public String getTnfNumtransaccion() {
        return tnfNumtransaccion;
    }

    public void setTnfNumtransaccion(String tnfNumtransaccion) {
        this.tnfNumtransaccion = tnfNumtransaccion;
    }

    public String getTnfNumcuentaorigen() {
        return tnfNumcuentaorigen;
    }

    public void setTnfNumcuentaorigen(String tnfNumcuentaorigen) {
        this.tnfNumcuentaorigen = tnfNumcuentaorigen;
    }

    public String getTnfNumcuentadestino() {
        return tnfNumcuentadestino;
    }

    public void setTnfNumcuentadestino(String tnfNumcuentadestino) {
        this.tnfNumcuentadestino = tnfNumcuentadestino;
    }

    public Boolean getTnfEstado() {
        return tnfEstado;
    }

    public void setTnfEstado(Boolean tnfEstado) {
        this.tnfEstado = tnfEstado;
    }
    public RfBanco getBncIdorigen() {
        return bncIdorigen;
    }

    public void setBncIdorigen(RfBanco bncIdorigen) {
        this.bncIdorigen = bncIdorigen;
    }

    public RfBanco getBncIddestino() {
        return bncIddestino;
    }

    public void setBncIddestino(RfBanco bncIddestino) {
        this.bncIddestino = bncIddestino;
    }

    public FinCronogramapago getCpgId() {
        return cpgId;
    }

    public void setCpgId(FinCronogramapago cpgId) {
        this.cpgId = cpgId;
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
        hash += (tnfId != null ? tnfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinTransferencia)) {
            return false;
        }
        FinTransferencia other = (FinTransferencia) object;
        if ((this.tnfId == null && other.tnfId != null) || (this.tnfId != null && !this.tnfId.equals(other.tnfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinTransferencia[ tnfId=" + tnfId + " ]";
    }

    /**
     * @return the strId
     */
    public Long getStrId() {
        return strId;
    }

    /**
     * @param strId the strId to set
     */
    public void setStrId(Long strId) {
        this.strId = strId;
    }
}
