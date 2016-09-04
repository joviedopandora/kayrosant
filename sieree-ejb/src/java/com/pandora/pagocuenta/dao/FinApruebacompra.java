/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

import com.pandora.adm.dao.CmpConsolcompra;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author byrobles
 */
@Entity
@Table(name = "fin_apruebacompra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinApruebacompra.findAll", query = "SELECT f FROM FinApruebacompra f"),
    @NamedQuery(name = "FinApruebacompra.findByApcoId", query = "SELECT f FROM FinApruebacompra f WHERE f.apcoId = :apcoId"),
    @NamedQuery(name = "FinApruebacompra.findByApcoVerificaimpuesto", query = "SELECT f FROM FinApruebacompra f WHERE f.apcoVerificaimpuesto = :apcoVerificaimpuesto"),
    @NamedQuery(name = "FinApruebacompra.findByApcoEst", query = "SELECT f FROM FinApruebacompra f WHERE f.apcoEst = :apcoEst"),
    @NamedQuery(name = "FinApruebacompra.findByIndversion", query = "SELECT f FROM FinApruebacompra f WHERE f.indversion = :indversion"),
    @NamedQuery(name = "FinApruebacompra.findByApcoIva", query = "SELECT f FROM FinApruebacompra f WHERE f.apcoIva = :apcoIva"),
    @NamedQuery(name = "FinApruebacompra.findByApcoNuevaformapago", query = "SELECT f FROM FinApruebacompra f WHERE f.apcoNuevaformapago = :apcoNuevaformapago"),
    @NamedQuery(name = "FinApruebacompra.findByApcoApruebacompra", query = "SELECT f FROM FinApruebacompra f WHERE f.apcoApruebacompra = :apcoApruebacompra")})
public class FinApruebacompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "apco_id")
    private Integer apcoId;
    @Column(name = "apco_verificaimpuesto")
    private Integer apcoVerificaimpuesto;
    @Column(name = "apco_est")
    private Boolean apcoEst;
    @Version
    @Column(name = "indversion")
    private Integer indversion;
    @Column(name = "apco_iva")
    private BigInteger apcoIva;
    @Column(name = "apco_nuevaformapago")
    private Integer apcoNuevaformapago;
    @Column(name = "apco_apruebacompra")
    private Integer apcoApruebacompra;
    @Column(name = "apco_reqcheque")
    private Boolean apcoReqcheque;
    @JoinColumn(name = "ccm_id", referencedColumnName = "ccm_id")
    @ManyToOne
    private CmpConsolcompra ccmId;

    public FinApruebacompra() {
    }

    public FinApruebacompra(Integer apcoId) {
        this.apcoId = apcoId;
    }

    public Integer getApcoId() {
        return apcoId;
    }

    public void setApcoId(Integer apcoId) {
        this.apcoId = apcoId;
    }

    public Integer getApcoVerificaimpuesto() {
        return apcoVerificaimpuesto;
    }

    public void setApcoVerificaimpuesto(Integer apcoVerificaimpuesto) {
        this.apcoVerificaimpuesto = apcoVerificaimpuesto;
    }

    public Boolean getApcoEst() {
        return apcoEst;
    }

    public void setApcoEst(Boolean apcoEst) {
        this.apcoEst = apcoEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public BigInteger getApcoIva() {
        return apcoIva;
    }

    public void setApcoIva(BigInteger apcoIva) {
        this.apcoIva = apcoIva;
    }

    public Integer getApcoNuevaformapago() {
        return apcoNuevaformapago;
    }

    public void setApcoNuevaformapago(Integer apcoNuevaformapago) {
        this.apcoNuevaformapago = apcoNuevaformapago;
    }

    public Integer getApcoApruebacompra() {
        return apcoApruebacompra;
    }

    public void setApcoApruebacompra(Integer apcoApruebacompra) {
        this.apcoApruebacompra = apcoApruebacompra;
    }

    public CmpConsolcompra getCcmId() {
        return ccmId;
    }

    public void setCcmId(CmpConsolcompra ccmId) {
        this.ccmId = ccmId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apcoId != null ? apcoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinApruebacompra)) {
            return false;
        }
        FinApruebacompra other = (FinApruebacompra) object;
        if ((this.apcoId == null && other.apcoId != null) || (this.apcoId != null && !this.apcoId.equals(other.apcoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "compras.dao.FinApruebacompra[ apcoId=" + apcoId + " ]";
    }

    /**
     * @return the apcoReqcheque
     */
    public Boolean getApcoReqcheque() {
        return apcoReqcheque;
    }

    /**
     * @param apcoReqcheque the apcoReqcheque to set
     */
    public void setApcoReqcheque(Boolean apcoReqcheque) {
        this.apcoReqcheque = apcoReqcheque;
    }
}
