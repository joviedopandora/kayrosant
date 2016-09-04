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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "fin_aprobado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinAprobado.findAll", query = "SELECT f FROM FinAprobado f"),
    @NamedQuery(name = "FinAprobado.findByApbId", query = "SELECT f FROM FinAprobado f WHERE f.apbId = :apbId"),
    @NamedQuery(name = "FinAprobado.findByApbAprobado", query = "SELECT f FROM FinAprobado f WHERE f.apbAprobado = :apbAprobado"),
    @NamedQuery(name = "FinAprobado.findByApbFechaproceso", query = "SELECT f FROM FinAprobado f WHERE f.apbFechaproceso = :apbFechaproceso"),
})  @NamedQuery(name = "FinAprobado.findByAprobadoXStrId", query = "SELECT COUNT(f.apbId) FROM FinAprobado f WHERE f.apbAprobado = :apbAprobado AND f.strId = :strId")
public class FinAprobado implements Serializable {
    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "fin_aprobado_apb_id_seq", name = "fin_aprobado_apb_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "fin_aprobado_apb_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @Column(name = "apb_id")
    private Long apbId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "apb_aprobado")
    private boolean apbAprobado;
    @Column(name = "apb_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date apbFechaproceso;
    @Column(name = "str_id")
    private Long strId;
    @JoinColumn(name = "cta_id", referencedColumnName = "cta_id")
    @ManyToOne(optional = false)
    private FinCuenta ctaId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;
    @OneToMany(mappedBy = "apbId")
    private List<FinFpgxcta> finFpgxctaList;

    public FinAprobado() {
    }

    public FinAprobado(Long apbId) {
        this.apbId = apbId;
    }

    public FinAprobado(Long apbId, boolean apbAprobado) {
        this.apbId = apbId;
        this.apbAprobado = apbAprobado;
    }

    public Long getApbId() {
        return apbId;
    }

    public void setApbId(Long apbId) {
        this.apbId = apbId;
    }

    public boolean getApbAprobado() {
        return apbAprobado;
    }

    public void setApbAprobado(boolean apbAprobado) {
        this.apbAprobado = apbAprobado;
    }

    public Date getApbFechaproceso() {
        return apbFechaproceso;
    }

    public void setApbFechaproceso(Date apbFechaproceso) {
        this.apbFechaproceso = apbFechaproceso;
    }

    public FinCuenta getCtaId() {
        return ctaId;
    }

    public void setCtaId(FinCuenta ctaId) {
        this.ctaId = ctaId;
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    @XmlTransient
    public List<FinFpgxcta> getFinFpgxctaList() {
        return finFpgxctaList;
    }

    public void setFinFpgxctaList(List<FinFpgxcta> finFpgxctaList) {
        this.finFpgxctaList = finFpgxctaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apbId != null ? apbId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinAprobado)) {
            return false;
        }
        FinAprobado other = (FinAprobado) object;
        if ((this.apbId == null && other.apbId != null) || (this.apbId != null && !this.apbId.equals(other.apbId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinAprobado[ apbId=" + apbId + " ]";
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
