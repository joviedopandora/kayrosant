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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "fin_solicitudcheque")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinSolicitudcheque.findAll", query = "SELECT f FROM FinSolicitudcheque f"),
    @NamedQuery(name = "FinSolicitudcheque.findBySolId", query = "SELECT f FROM FinSolicitudcheque f WHERE f.solId = :solId"),
    @NamedQuery(name = "FinSolicitudcheque.findBySolConcepto", query = "SELECT f FROM FinSolicitudcheque f WHERE f.solConcepto = :solConcepto"),
    @NamedQuery(name = "FinSolicitudcheque.findBySolFechaproceso", query = "SELECT f FROM FinSolicitudcheque f WHERE f.solFechaproceso = :solFechaproceso"),
    @NamedQuery(name = "FinSolicitudcheque.findByStrId", query = "SELECT f FROM FinSolicitudcheque f WHERE f.strId = :strId"),
    //Cargar solicitudes de cheque por forma de pago
    @NamedQuery(name = "FinSolicitudcheque.findAllXFormaPagoXEstado", query = "SELECT f FROM FinSolicitudcheque f JOIN f.fxcId fxc JOIN fxc.fpgId fpg "
        + " WHERE f.solEstado = :solEstado AND fpg.fpgId = :fpgId ORDER BY fxc.apbId.ctaId.ctaFechapago"),
    //Cargar solicitudes de cheque por forma de pago y estado
    @NamedQuery(name = "FinSolicitudcheque.FormaPagoXEstado", query = "SELECT f FROM FinSolicitudcheque f JOIN f.fxcId fxc "
        + " JOIN fxc.fpgId fpg JOIN fxc.apbId apb JOIN apb.ctaId cta "
        + " WHERE f.solEstado = :solEstado AND fpg.fpgId = :fpgId ORDER BY cta.ctaFechapago DESC"),
    @NamedQuery(name = "FinSolicitudcheque.cantXStrId", query = "SELECT COUNT(f.solId) FROM FinSolicitudcheque f WHERE f.strId = :strId")
})
public class FinSolicitudcheque implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "fin_solicitudcheque_sol_id_seq", name = "fin_solicitudcheque_sol_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "fin_solicitudcheque_sol_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @Column(name = "sol_id")
    private Long solId;
    @Size(max = 2147483647)
    @Column(name = "sol_concepto")
    private String solConcepto;
    @Column(name = "sol_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date solFechaproceso;
    @Column(name = "sol_estado")
    @NotNull
    private Boolean solEstado;
    @Column(name = "str_id")
    private Long strId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solId")
    private List<FinCheque> finChequeList;
    @JoinColumn(name = "fxc_id", referencedColumnName = "fxc_id")
    @ManyToOne
    private FinFpgxcta fxcId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    public FinSolicitudcheque() {
    }

    public FinSolicitudcheque(Long solId) {
        this.solId = solId;
    }

    public Long getSolId() {
        return solId;
    }

    public void setSolId(Long solId) {
        this.solId = solId;
    }

    public String getSolConcepto() {
        return solConcepto;
    }

    public void setSolConcepto(String solConcepto) {
        this.solConcepto = solConcepto;
    }

    public Date getSolFechaproceso() {
        return solFechaproceso;
    }

    public void setSolFechaproceso(Date solFechaproceso) {
        this.solFechaproceso = solFechaproceso;
    }

    @XmlTransient
    public List<FinCheque> getFinChequeList() {
        return finChequeList;
    }

    public void setFinChequeList(List<FinCheque> finChequeList) {
        this.finChequeList = finChequeList;
    }

    public FinFpgxcta getFxcId() {
        return fxcId;
    }

    public void setFxcId(FinFpgxcta fxcId) {
        this.fxcId = fxcId;
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
        hash += (solId != null ? solId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinSolicitudcheque)) {
            return false;
        }
        FinSolicitudcheque other = (FinSolicitudcheque) object;
        if ((this.solId == null && other.solId != null) || (this.solId != null && !this.solId.equals(other.solId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinSolicitudcheque[ solId=" + solId + " ]";
    }

    /**
     * @return the solEstado
     */
    public Boolean getSolEstado() {
        return solEstado;
    }

    /**
     * @param solEstado the solEstado to set
     */
    public void setSolEstado(Boolean solEstado) {
        this.solEstado = solEstado;
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
