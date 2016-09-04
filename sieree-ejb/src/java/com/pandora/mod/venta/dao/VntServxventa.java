/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

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
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "vnt_servxventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntServxventa.findAll", query = "SELECT v FROM VntServxventa v"),
    @NamedQuery(name = "VntServxventa.findBySrvxventId", query = "SELECT v FROM VntServxventa v WHERE v.srvxventId = :srvxventId"),
    @NamedQuery(name = "VntServxventa.findBySrvxventEst", query = "SELECT v FROM VntServxventa v WHERE v.srvxventEst = :srvxventEst"),
    @NamedQuery(name = "VntServxventa.findByIndversion", query = "SELECT v FROM VntServxventa v WHERE v.indversion = :indversion"),
    //Lista de servicios por registro de venta    
    @NamedQuery(name = "VntServxventa.srvXRegVnt", query = "SELECT v FROM VntServxventa v JOIN v.rgvtId r WHERE r.rgvtId = :rgvtId"),//codigo query para modificar las facturas que ya se hicieron
    
    // lista de  servicios replica
    @NamedQuery(name = "VntServxventa.servxventa", query = "SELECT v FROM VntServxventa v JOIN v.rgvtId r WHERE r.rgvtId = :rgvtId ORDER BY r.rgvtId ASC"),
    //Lista de servicios por venta por cliente
    @NamedQuery(name = "VntServxventa.srvXCteYEstV", query = "SELECT v FROM VntServxventa v JOIN v.rgvtId r JOIN r.estrvntId e JOIN r.clnId c WHERE c.clnId = :clnId AND e.estrvntId = :estrvntId"),
    //Lista de servicios de venta por factura
    @NamedQuery(name = "VntServxventa.regVntXfact", query = "SELECT v FROM VntServxventa v JOIN v.rgvtId r JOIN r.vntFacturaList f WHERE f.vfctId = :vfctId"),
    //Lista de servicios de venta por remisión
    @NamedQuery(name = "VntServxventa.regVntXRem", query = "SELECT v FROM VntServxventa v JOIN v.rgvtId r JOIN r.vntRemisionList rm WHERE rm.vrmsId = :vrmsId")
})
public class VntServxventa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "srvxvent_id")
    private Long srvxventId;
    @Column(name = "srvxvent_est")
    private Boolean srvxventEst;
    @Column(name = "indversion")
    @Version
    private Integer indversion;
    @Column(name = "srvxvent_precioventa")
    private BigDecimal srvxventPrecioventa;
    @Column(name = "srvxvent_cantidad")
    private Integer srvxventCantidad;
    @Column(name = "srvxvent_valtotalclnt")
    private BigDecimal srvxventValtotalclnt;
    @JoinColumn(name = "vsrv_id", referencedColumnName = "vsrv_id")
    @ManyToOne
    private VntServicio vsrvId;
    @JoinColumn(name = "rgvt_id", referencedColumnName = "rgvt_id")
    @ManyToOne
    private VntRegistroventa rgvtId;
    @Column(name = "srvxvent_descuento")
    private BigDecimal srvxventDescuento;
    @Column(name = "srvxvent_porcentajedesc")
    private BigDecimal srvxventPorcentajeDesc;
    @NotNull
    @Column(name = "srvxventa_procesada")
    private Integer srvxventaProcesada;

    @NotNull
    @Column(name = "srvxventa_procesada_op")
    private Integer srvxventaProcesadaOP;

    public VntServxventa() {
        this.srvxventaProcesada = 0;
        srvxventaProcesadaOP=0;
    }

    public VntServxventa(Long srvxventId) {
        this.srvxventId = srvxventId;
    }

    public Long getSrvxventId() {
        return srvxventId;
    }

    public void setSrvxventId(Long srvxventId) {
        this.srvxventId = srvxventId;
    }

    public Boolean getSrvxventEst() {
        return srvxventEst;
    }

    public void setSrvxventEst(Boolean srvxventEst) {
        this.srvxventEst = srvxventEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public VntServicio getVsrvId() {
        return vsrvId;
    }

    public void setVsrvId(VntServicio vsrvId) {
        this.vsrvId = vsrvId;
    }

    public VntRegistroventa getRgvtId() {
        return rgvtId;
    }

    public void setRgvtId(VntRegistroventa rgvtId) {
        this.rgvtId = rgvtId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (srvxventId != null ? srvxventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntServxventa)) {
            return false;
        }
        VntServxventa other = (VntServxventa) object;
        if ((this.srvxventId == null && other.srvxventId != null) || (this.srvxventId != null && !this.srvxventId.equals(other.srvxventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntServxventa[ srvxventId=" + srvxventId + " ]";
    }

    /**
     * @return the srvxventPrecioventa
     */
    public BigDecimal getSrvxventPrecioventa() {
        return srvxventPrecioventa;
    }

    /**
     * @param srvxventPrecioventa the srvxventPrecioventa to set
     */
    public void setSrvxventPrecioventa(BigDecimal srvxventPrecioventa) {
        this.srvxventPrecioventa = srvxventPrecioventa;
    }

    /**
     * @return the srvxventCantidad
     */
    public Integer getSrvxventCantidad() {
        return srvxventCantidad;
    }

    /**
     * @param srvxventCantidad the srvxventCantidad to set
     */
    public void setSrvxventCantidad(Integer srvxventCantidad) {
        this.srvxventCantidad = srvxventCantidad;
    }

    /**
     * @return the srvxventValtotalclnt
     */
    public BigDecimal getSrvxventValtotalclnt() {
        return srvxventValtotalclnt;
    }

    /**
     * @param srvxventValtotalclnt the srvxventValtotalclnt to set
     */
    public void setSrvxventValtotalclnt(BigDecimal srvxventValtotalclnt) {
        this.srvxventValtotalclnt = srvxventValtotalclnt;
    }

    public BigDecimal getSrvxventDescuento() {
        return srvxventDescuento;
    }

    public void setSrvxventDescuento(BigDecimal srvxventDescuento) {
        this.srvxventDescuento = srvxventDescuento;
    }

    public BigDecimal getSrvxventPorcentajeDesc() {
        return srvxventPorcentajeDesc;
    }

    public void setSrvxventPorcentajeDesc(BigDecimal srvxventPorcentajeDesc) {
        this.srvxventPorcentajeDesc = srvxventPorcentajeDesc;
    }

    public Integer getSrvxventaProcesada() {
        return srvxventaProcesada;
    }

    public void setSrvxventaProcesada(Integer srvxventaProcesada) {
        this.srvxventaProcesada = srvxventaProcesada;
    }

    public Integer getSrvxventaProcesadaOP() {
        return srvxventaProcesadaOP;
    }

    public void setSrvxventaProcesadaOP(Integer srvxventaProcesadaOP) {
        this.srvxventaProcesadaOP = srvxventaProcesadaOP;
    }
    
    

}
