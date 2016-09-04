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
@Table(name = "log_factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogFactura.findByFactura", query = "SELECT v FROM LogFactura v WHERE v.vntFactura.vfctId = :vfctId ORDER BY v.lfFechaproceso DESC")})
public class LogFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lf_id")
    private Long lfId;
    @NotNull
    @Column(name = "lf_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lfFechaproceso;
    @JoinColumn(name = "eft_id", referencedColumnName = "eft_id")
    @ManyToOne
    private RfEstadofactura rfEstadofactura;
    @JoinColumn(name = "vfct_id", referencedColumnName = "vfct_id")
    @ManyToOne
    private VntFactura vntFactura;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol admCrgxcol;

    public LogFactura() {
    }

    public LogFactura(Long lfId) {
        this.lfId = lfId;
    }

    public LogFactura(Long lfId, Date lfFechaproceso) {
        this.lfId = lfId;
        this.lfFechaproceso = lfFechaproceso;
    }

    public LogFactura(Long lfId, Date lfFechaproceso, RfEstadofactura rfEstadofactura, VntFactura vntFactura, AdmCrgxcol admCrgxcol) {
        this.lfId = lfId;
        this.lfFechaproceso = lfFechaproceso;
        this.rfEstadofactura = rfEstadofactura;
        this.vntFactura = vntFactura;
        this.admCrgxcol = admCrgxcol;
    }

    public Long getLfId() {
        return lfId;
    }

    public void setLfId(Long lfId) {
        this.lfId = lfId;
    }

    public Date getLfFechaproceso() {
        return lfFechaproceso;
    }

    public void setLfFechaproceso(Date lfFechaproceso) {
        this.lfFechaproceso = lfFechaproceso;
    }

    public RfEstadofactura getRfEstadofactura() {
        return rfEstadofactura;
    }

    public void setRfEstadofactura(RfEstadofactura rfEstadofactura) {
        this.rfEstadofactura = rfEstadofactura;
    }

    public VntFactura getVntFactura() {
        return vntFactura;
    }

    public void setVntFactura(VntFactura vntFactura) {
        this.vntFactura = vntFactura;
    }

    public AdmCrgxcol getAdmCrgxcol() {
        return admCrgxcol;
    }

    public void setAdmCrgxcol(AdmCrgxcol admCrgxcol) {
        this.admCrgxcol = admCrgxcol;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.lfId);
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
        final LogFactura other = (LogFactura) obj;
        return Objects.equals(this.lfId, other.lfId);
    }
    
    
    
    

}
