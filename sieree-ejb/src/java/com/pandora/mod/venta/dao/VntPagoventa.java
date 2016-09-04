/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import com.pandora.pagocuenta.dao.FinFormapago;
import com.pandora.pagocuenta.dao.RfBanco;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "vnt_pagoventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntPagoventa.findAll", query = "SELECT v FROM VntPagoventa v"),
    @NamedQuery(name = "VntPagoventa.findBYVenta", query = "SELECT v FROM VntPagoventa v WHERE v.rgvtId.rgvtId = :rgvtId ORDER BY v.rgpcFechapago DESC"),
    @NamedQuery(name = "VntPagoventa.findByRgpcId", query = "SELECT v FROM VntPagoventa v WHERE v.rgpcId = :rgpcId"),
    @NamedQuery(name = "VntPagoventa.findByRgpcValorpago", query = "SELECT v FROM VntPagoventa v WHERE v.rgpcValorpago = :rgpcValorpago"),
    @NamedQuery(name = "VntPagoventa.findByRgpcEst", query = "SELECT v FROM VntPagoventa v WHERE v.rgpcEst = :rgpcEst"),
    @NamedQuery(name = "VntPagoventa.findByRgpcFechapago", query = "SELECT v FROM VntPagoventa v WHERE v.rgpcFechapago = :rgpcFechapago"),
    @NamedQuery(name = "VntPagoventa.findByPgpcFechaproc", query = "SELECT v FROM VntPagoventa v WHERE v.pgpcFechaproc = :pgpcFechaproc")})
public class VntPagoventa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rgpc_id")
    private Long rgpcId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rgpc_valorpago")
    private BigDecimal rgpcValorpago;
    @Column(name = "rgpc_est")
    private Boolean rgpcEst;
    @Column(name = "rgpc_fechapago")
    @Temporal(TemporalType.DATE)
    private Date rgpcFechapago;
    @Column(name = "pgpc_fechaproc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pgpcFechaproc;
    @JoinColumn(name = "rgvt_id", referencedColumnName = "rgvt_id")
    @ManyToOne
    private VntRegistroventa rgvtId;
    @JoinColumn(name = "bnc_id", referencedColumnName = "bnc_id")
    @ManyToOne
    private RfBanco bncId;
    @Column(name = "rgpc_observacion")
    private Boolean rgpcObservacion;
    @JoinColumn(name = "fpg_id", referencedColumnName = "fpg_id")
    @ManyToOne
    private FinFormapago finFormapago;
    

    public VntPagoventa() {
    }

    public VntPagoventa(Long rgpcId) {
        this.rgpcId = rgpcId;
    }

    public Long getRgpcId() {
        return rgpcId;
    }

    public void setRgpcId(Long rgpcId) {
        this.rgpcId = rgpcId;
    }

    public BigDecimal getRgpcValorpago() {
        return rgpcValorpago;
    }

    public void setRgpcValorpago(BigDecimal rgpcValorpago) {
        this.rgpcValorpago = rgpcValorpago;
    }

    public Boolean getRgpcEst() {
        return rgpcEst;
    }

    public void setRgpcEst(Boolean rgpcEst) {
        this.rgpcEst = rgpcEst;
    }

    public Date getRgpcFechapago() {
        return rgpcFechapago;
    }

    public void setRgpcFechapago(Date rgpcFechapago) {
        this.rgpcFechapago = rgpcFechapago;
    }

    public Date getPgpcFechaproc() {
        return pgpcFechaproc;
    }

    public void setPgpcFechaproc(Date pgpcFechaproc) {
        this.pgpcFechaproc = pgpcFechaproc;
    }

    public VntRegistroventa getRgvtId() {
        return rgvtId;
    }

    public void setRgvtId(VntRegistroventa rgvtId) {
        this.rgvtId = rgvtId;
    }

    public RfBanco getBncId() {
        return bncId;
    }

    public void setBncId(RfBanco bncId) {
        this.bncId = bncId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rgpcId != null ? rgpcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntPagoventa)) {
            return false;
        }
        VntPagoventa other = (VntPagoventa) object;
        if ((this.rgpcId == null && other.rgpcId != null) || (this.rgpcId != null && !this.rgpcId.equals(other.rgpcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntPagoventa[ rgpcId=" + rgpcId + " ]";
    }

    /**
     * @return the rgpcObservacion
     */
    public Boolean getRgpcObservacion() {
        return rgpcObservacion;
    }

    /**
     * @param rgpcObservacion the rgpcObservacion to set
     */
    public void setRgpcObservacion(Boolean rgpcObservacion) {
        this.rgpcObservacion = rgpcObservacion;
    }

    /**
     * @return the finFormapago
     */
    public FinFormapago getFinFormapago() {
        return finFormapago;
    }

    /**
     * @param finFormapago the finFormapago to set
     */
    public void setFinFormapago(FinFormapago finFormapago) {
        this.finFormapago = finFormapago;
    }
}
