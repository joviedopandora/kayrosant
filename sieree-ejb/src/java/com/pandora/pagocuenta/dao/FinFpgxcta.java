/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "fin_fpgxcta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinFpgxcta.findAll", query = "SELECT f FROM FinFpgxcta f"),
    @NamedQuery(name = "FinFpgxcta.findByFxcId", query = "SELECT f FROM FinFpgxcta f WHERE f.fxcId = :fxcId"),
    @NamedQuery(name = "FinFpgxcta.findByFxcValor", query = "SELECT f FROM FinFpgxcta f WHERE f.fxcValor = :fxcValor")})
public class FinFpgxcta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fxc_id")
    private Long fxcId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fxc_valor")
    private BigDecimal fxcValor;
    @OneToMany(mappedBy = "fxcId")
    private List<FinCronogramapago> finCronogramapagoList;
    @OneToMany(mappedBy = "fxcId")
    private List<FinSolicitudcheque> finSolicitudchequeList;
    @JoinColumn(name = "fpg_id", referencedColumnName = "fpg_id")
    @ManyToOne
    private FinFormapago fpgId;
    @JoinColumn(name = "apb_id", referencedColumnName = "apb_id")
    @ManyToOne
    private FinAprobado apbId;

    public FinFpgxcta() {
    }

    public FinFpgxcta(Long fxcId) {
        this.fxcId = fxcId;
    }

    public Long getFxcId() {
        return fxcId;
    }

    public void setFxcId(Long fxcId) {
        this.fxcId = fxcId;
    }

    public BigDecimal getFxcValor() {
        return fxcValor;
    }

    public void setFxcValor(BigDecimal fxcValor) {
        this.fxcValor = fxcValor;
    }

    @XmlTransient
    public List<FinCronogramapago> getFinCronogramapagoList() {
        return finCronogramapagoList;
    }

    public void setFinCronogramapagoList(List<FinCronogramapago> finCronogramapagoList) {
        this.finCronogramapagoList = finCronogramapagoList;
    }

    @XmlTransient
    public List<FinSolicitudcheque> getFinSolicitudchequeList() {
        return finSolicitudchequeList;
    }

    public void setFinSolicitudchequeList(List<FinSolicitudcheque> finSolicitudchequeList) {
        this.finSolicitudchequeList = finSolicitudchequeList;
    }

    public FinFormapago getFpgId() {
        return fpgId;
    }

    public void setFpgId(FinFormapago fpgId) {
        this.fpgId = fpgId;
    }

    public FinAprobado getApbId() {
        return apbId;
    }

    public void setApbId(FinAprobado apbId) {
        this.apbId = apbId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fxcId != null ? fxcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinFpgxcta)) {
            return false;
        }
        FinFpgxcta other = (FinFpgxcta) object;
        if ((this.fxcId == null && other.fxcId != null) || (this.fxcId != null && !this.fxcId.equals(other.fxcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinFpgxcta[ fxcId=" + fxcId + " ]";
    }
    
}
