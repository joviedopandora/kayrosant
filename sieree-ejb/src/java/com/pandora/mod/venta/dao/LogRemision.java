/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import adm.sys.dao.AdmCrgxcol;
import com.pandora.adm.rf.dao.RfEstadofactura;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Garcia Bosso
 */
@Entity
@Table(name = "log_remision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogRemision.findByFactura", query = "SELECT v FROM LogRemision v WHERE v.vntRemision.vrmsId = :vrmsId ORDER BY v.lrFechaproceso DESC")})
public class LogRemision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lr_id")
    private Long lrId;
    @NotNull
    @Column(name = "lr_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lrFechaproceso;
    @JoinColumn(name = "eft_id", referencedColumnName = "eft_id")
    @ManyToOne
    private RfEstadofactura rfEstadofactura;
    @JoinColumn(name = "vrms_id", referencedColumnName = "vrms_id")
    @ManyToOne
    private VntRemision vntRemision;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol admCrgxcol;

    public LogRemision() {
    }

    public LogRemision(Long lrId) {
        this.lrId = lrId;
    }

    public LogRemision(Long lrId, Date lrFechaproceso) {
        this.lrId = lrId;
        this.lrFechaproceso = lrFechaproceso;
    }

    public LogRemision(Long lrId, Date lrFechaproceso, RfEstadofactura rfEstadofactura, VntRemision vntRemision, AdmCrgxcol admCrgxcol) {
        this.lrId = lrId;
        this.lrFechaproceso = lrFechaproceso;
        this.rfEstadofactura = rfEstadofactura;
        this.vntRemision = vntRemision;
        this.admCrgxcol = admCrgxcol;
    }

    public Long getLrId() {
        return lrId;
    }

    public void setLrId(Long lrId) {
        this.lrId = lrId;
    }

    public Date getLrFechaproceso() {
        return lrFechaproceso;
    }

    public void setLrFechaproceso(Date lrFechaproceso) {
        this.lrFechaproceso = lrFechaproceso;
    }

    public RfEstadofactura getRfEstadofactura() {
        return rfEstadofactura;
    }

    public void setRfEstadofactura(RfEstadofactura rfEstadofactura) {
        this.rfEstadofactura = rfEstadofactura;
    }

    public VntRemision getVntRemision() {
        return vntRemision;
    }

    public void setVntRemision(VntRemision vntRemision) {
        this.vntRemision = vntRemision;
    }

    public AdmCrgxcol getAdmCrgxcol() {
        return admCrgxcol;
    }

    public void setAdmCrgxcol(AdmCrgxcol admCrgxcol) {
        this.admCrgxcol = admCrgxcol;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.lrId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogRemision other = (LogRemision) obj;
        if (!Objects.equals(this.lrId, other.lrId)) {
            return false;
        }
        return true;
    }

  
}
