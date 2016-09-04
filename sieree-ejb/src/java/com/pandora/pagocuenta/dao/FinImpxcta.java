/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "fin_impxcta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinImpxcta.findAll", query = "SELECT f FROM FinImpxcta f"),
    @NamedQuery(name = "FinImpxcta.findByIxcId", query = "SELECT f FROM FinImpxcta f WHERE f.ixcId = :ixcId"),
    @NamedQuery(name = "FinImpxcta.impXCta", query = "SELECT f FROM FinImpxcta f JOIN f.ctaId cta WHERE cta.ctaId = :ctaId")
})
public class FinImpxcta implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "fin_impxcta_ixc_id_seq", name = "fin_impxcta_ixc_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "fin_impxcta_ixc_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @Column(name = "ixc_id")
    private Long ixcId;
    @Column(name = "ixc_estado")
    private Boolean ixcEstado;
    @Column(name= "ixc_valor")
    private BigDecimal ixcValor;
    @Version
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "imp_id", referencedColumnName = "imp_id")
    @ManyToOne
    private FinRfImpuesto impId;
    @JoinColumn(name = "cta_id", referencedColumnName = "cta_id")
    @ManyToOne
    private FinCuenta ctaId;

    public FinImpxcta() {
    }

    public FinImpxcta(Long ixcId) {
        this.ixcId = ixcId;
    }

    public Long getIxcId() {
        return ixcId;
    }

    public void setIxcId(Long ixcId) {
        this.ixcId = ixcId;
    }

    public Boolean getIxcEstado() {
        return ixcEstado;
    }

    public void setIxcEstado(Boolean ixcEstado) {
        this.ixcEstado = ixcEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public FinRfImpuesto getImpId() {
        return impId;
    }

    public void setImpId(FinRfImpuesto impId) {
        this.impId = impId;
    }

    public FinCuenta getCtaId() {
        return ctaId;
    }

    public void setCtaId(FinCuenta ctaId) {
        this.ctaId = ctaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ixcId != null ? ixcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinImpxcta)) {
            return false;
        }
        FinImpxcta other = (FinImpxcta) object;
        if ((this.ixcId == null && other.ixcId != null) || (this.ixcId != null && !this.ixcId.equals(other.ixcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinImpxcta[ ixcId=" + ixcId + " ]";
    }

    /**
     * @return the ixcValor
     */
    public BigDecimal getIxcValor() {
        return ixcValor;
    }

    /**
     * @param ixcValor the ixcValor to set
     */
    public void setIxcValor(BigDecimal ixcValor) {
        this.ixcValor = ixcValor;
    }
}
