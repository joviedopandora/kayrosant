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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "fin_firmacheque")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinFirmacheque.findAll", query = "SELECT f FROM FinFirmacheque f"),
    @NamedQuery(name = "FinFirmacheque.findByFchId", query = "SELECT f FROM FinFirmacheque f WHERE f.fchId = :fchId"),
    @NamedQuery(name = "FinFirmacheque.findByFchEntregado", query = "SELECT f FROM FinFirmacheque f WHERE f.fchEntregado = :fchEntregado"),
    @NamedQuery(name = "FinFirmacheque.findByFchEstado", query = "SELECT f FROM FinFirmacheque f WHERE f.fchEstado = :fchEstado"),
    @NamedQuery(name = "FinFirmacheque.findByIndversion", query = "SELECT f FROM FinFirmacheque f WHERE f.indversion = :indversion"),
    @NamedQuery(name = "FinFirmacheque.findByFchFechaproceso", query = "SELECT f FROM FinFirmacheque f WHERE f.fchFechaproceso = :fchFechaproceso"),
@NamedQuery(name = "FinFirmacheque.findByfchObservacion", query = "SELECT f FROM FinFirmacheque f WHERE f.fchObservacion = :fchObservacion")})
public class FinFirmacheque implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "fin_firmacheque_fch_id_seq", name = "fin_firmacheque_fch_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "fin_firmacheque_fch_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @Column(name = "fch_id")
    private Long fchId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fch_Entregado")
    private boolean fchEntregado;
    @Column(name = "fch_estado")
    private boolean fchEstado;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "indversion")
    private int indversion;
    @Column(name = "fch_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchFechaproceso;
    @Size(min = 1, max = 2147483647)
    @Column(name = "fch_observacion")
    private String fchObservacion;
    @Column(name = "str_id")
    private Long strId;
    @JoinColumn(name = "chq_id", referencedColumnName = "chq_id")
    @ManyToOne
    private FinCheque chqId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    public FinFirmacheque() {
    }

    public FinFirmacheque(Long fchId) {
        this.fchId = fchId;
    }

    public FinFirmacheque(Long fchId, boolean fchEntregado, boolean fchEstado, int indversion) {
        this.fchId = fchId;
        this.fchEntregado = fchEntregado;
        this.fchEstado = fchEstado;
        this.indversion = indversion;
    }

    public Long getFchId() {
        return fchId;
    }

    public void setFchId(Long fchId) {
        this.fchId = fchId;
    }

    public boolean getFchEntregado() {
        return fchEntregado;
    }

    public void setFchEntregado(boolean fchEntregado) {
        this.fchEntregado = fchEntregado;
    }

    public boolean getFchEstado() {
        return fchEstado;
    }

    public void setFchEstado(boolean fchEstado) {
        this.fchEstado = fchEstado;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    public Date getFchFechaproceso() {
        return fchFechaproceso;
    }

    public void setFchFechaproceso(Date fchFechaproceso) {
        this.fchFechaproceso = fchFechaproceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fchId != null ? fchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinFirmacheque)) {
            return false;
        }
        FinFirmacheque other = (FinFirmacheque) object;
        if ((this.fchId == null && other.fchId != null) || (this.fchId != null && !this.fchId.equals(other.fchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinFirmacheque[ fchId=" + fchId + " ]";
    }

    /**
     * @return the chqId
     */
    public FinCheque getChqId() {
        return chqId;
    }

    /**
     * @param chqId the chqId to set
     */
    public void setChqId(FinCheque chqId) {
        this.chqId = chqId;
    }

    /**
     * @return the cxcId
     */
    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    /**
     * @param cxcId the cxcId to set
     */
    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
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
     * @return the fchObservacion
     */
    public String getFchObservacion() {
        return fchObservacion;
    }

    /**
     * @param fchObservacion the fchObservacion to set
     */
    public void setFchObservacion(String fchObservacion) {
        this.fchObservacion = fchObservacion;
    }
}
