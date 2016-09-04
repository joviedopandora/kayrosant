/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

import adm.sys.dao.AdmCrgxcol;
import com.pandora.adm.dao.CmpFactura;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "fin_cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinCuenta.findAll", query = "SELECT f FROM FinCuenta f"),
    @NamedQuery(name = "FinCuenta.findByCtaId", query = "SELECT f FROM FinCuenta f WHERE f.ctaId = :ctaId"),
    @NamedQuery(name = "FinCuenta.findByCtaValorbase", query = "SELECT f FROM FinCuenta f WHERE f.ctaValorbase = :ctaValorbase"),
    @NamedQuery(name = "FinCuenta.findByCtaRevisado", query = "SELECT f FROM FinCuenta f WHERE f.ctaRevisado = :ctaRevisado ORDER BY f.ctaFechaproceso"),
    @NamedQuery(name = "FinCuenta.findByCtaFechaproceso", query = "SELECT f FROM FinCuenta f WHERE f.ctaFechaproceso = :ctaFechaproceso"),
    @NamedQuery(name = "FinCuenta.findByCantXRevXStrId", query = "SELECT COUNT(f.ctaId) FROM FinCuenta f WHERE f.ctaRevisado = :ctaRevisado AND f.strId = :strId")
})
public class FinCuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "fin_cuenta_cta_id_seq", name = "fin_cuenta_cta_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "fin_cuenta_cta_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @Column(name = "cta_id")
    private Long ctaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cta_valorbase")
    private BigDecimal ctaValorbase;
    @Column(name = "cta_valorretenciones")
    private BigDecimal ctaValorretenciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cta_revisado")
    private boolean ctaRevisado;
    @Column(name = "cta_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctaFechaproceso;
    @Column(name = "cta_fechapago")
    @Temporal(TemporalType.DATE)
    private Date ctaFechapago;
    @Column(name = "cta_concepto")
    private String ctaConcepto;
    @Column(name = "indversion")
    private Integer Indversion;
    @Column(name = "str_id")
    private Long strId;
    @JoinColumn(name = "fact_id", referencedColumnName = "fact_id")
    @ManyToOne(optional = false)
    private CmpFactura factId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne(optional = false)
    private AdmCrgxcol cxcId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ctaId")
    private List<FinAprobado> finAprobadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ctaId")
    private List<FinImpxcta> finImpxctaList;

    public FinCuenta() {
    }

    public FinCuenta(Long ctaId) {
        this.ctaId = ctaId;
    }

    public FinCuenta(Long ctaId, BigDecimal ctaValorbase, boolean ctaRevisado) {
        this.ctaId = ctaId;
        this.ctaValorbase = ctaValorbase;
        this.ctaRevisado = ctaRevisado;
    }

    public Long getCtaId() {
        return ctaId;
    }

    public void setCtaId(Long ctaId) {
        this.ctaId = ctaId;
    }

    public BigDecimal getCtaValorbase() {
        return ctaValorbase;
    }

    public void setCtaValorbase(BigDecimal ctaValorbase) {
        this.ctaValorbase = ctaValorbase;
    }

    public boolean getCtaRevisado() {
        return ctaRevisado;
    }

    public void setCtaRevisado(boolean ctaRevisado) {
        this.ctaRevisado = ctaRevisado;
    }

    public Date getCtaFechaproceso() {
        return ctaFechaproceso;
    }

    public void setCtaFechaproceso(Date ctaFechaproceso) {
        this.ctaFechaproceso = ctaFechaproceso;
    }

    public CmpFactura getFactId() {
        return factId;
    }

    public void setFactId(CmpFactura factId) {
        this.factId = factId;
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    @XmlTransient
    public List<FinAprobado> getFinAprobadoList() {
        return finAprobadoList;
    }

    public void setFinAprobadoList(List<FinAprobado> finAprobadoList) {
        this.finAprobadoList = finAprobadoList;
    }

    @XmlTransient
    public List<FinImpxcta> getFinImpxctaList() {
        return finImpxctaList;
    }

    public void setFinImpxctaList(List<FinImpxcta> finImpxctaList) {
        this.finImpxctaList = finImpxctaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctaId != null ? ctaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinCuenta)) {
            return false;
        }
        FinCuenta other = (FinCuenta) object;
        if ((this.ctaId == null && other.ctaId != null) || (this.ctaId != null && !this.ctaId.equals(other.ctaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinCuenta[ ctaId=" + ctaId + " ]";
    }

    /**
     * @return the Indversion
     */
    public Integer getIndversion() {
        return Indversion;
    }

    /**
     * @param Indversion the Indversion to set
     */
    public void setIndversion(Integer Indversion) {
        this.Indversion = Indversion;
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

    /**
     * @return the ctaFechapago
     */
    public Date getCtaFechapago() {
        return ctaFechapago;
    }

    /**
     * @param ctaFechapago the ctaFechapago to set
     */
    public void setCtaFechapago(Date ctaFechapago) {
        this.ctaFechapago = ctaFechapago;
    }

    /**
     * @return the ctaConcepto
     */
    public String getCtaConcepto() {
        return ctaConcepto;
    }

    /**
     * @param ctaConcepto the ctaConcepto to set
     */
    public void setCtaConcepto(String ctaConcepto) {
        this.ctaConcepto = ctaConcepto;
    }

    /**
     * @return the ctaValorretenciones
     */
    public BigDecimal getCtaValorretenciones() {
        return ctaValorretenciones;
    }

    /**
     * @param ctaValorretenciones the ctaValorretenciones to set
     */
    public void setCtaValorretenciones(BigDecimal ctaValorretenciones) {
        this.ctaValorretenciones = ctaValorretenciones;
    }
}
