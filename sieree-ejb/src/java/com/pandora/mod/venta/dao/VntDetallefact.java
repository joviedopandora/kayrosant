/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sandra
 */
@Entity
@Table(name = "vnt_detallefact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntDetallefact.findAll", query = "SELECT v FROM VntDetallefact v"),
    @NamedQuery(name = "VntDetallefact.findByVdftId", query = "SELECT v FROM VntDetallefact v WHERE v.vdftId = :vdftId"),
    @NamedQuery(name = "VntDetallefact.findByVdftEstado", query = "SELECT v FROM VntDetallefact v WHERE v.vdftEstado = :vdftEstado")})
public class VntDetallefact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vdft_id")
    private Long vdftId;
    @Column(name = "vdft_estado")
    private Boolean vdftEstado;
   /* @JoinColumn(name = "pxso_id", referencedColumnName = "pxso_id")
    @ManyToOne
    private PopProdxservxop pxsoId;*/
    @JoinColumn(name = "vfct_id", referencedColumnName = "vfct_id")
    @ManyToOne
    private VntFactura vfctId;
    @Column(name = "vdft_servico")
    private String vdftServico;
    @Column(name = "vdft_cantidad")
    private Integer vdftCantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vdft_costounitario")
    private BigDecimal vdftCostounitario;
    @Column(name = "vdft_costototal")
    private BigDecimal vdftCostototal;
    @Column(name = "vdft_descuento")
    private BigDecimal vdftDescuento;
    
     @Column(name = "factdc_porcentajeiva")
    private BigDecimal vdftPorcentajeIva;
    
   @Column(name = "factdc_valoriva")
    private BigDecimal vdftValorIva;
   
   
    
    
    @Column(name = "vdft_subtotal")
    private BigDecimal vdftSubtotal;
    
     @JoinColumn(name = "vsrv_id", referencedColumnName = "vsrv_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntServicio vsrvId;

    public VntServicio getVsrvId() {
        return vsrvId;
    }

    public void setVsrvId(VntServicio vsrvId) {
        this.vsrvId = vsrvId;
    }

    public VntDetallefact() {
    }

    public VntDetallefact(Long vdftId) {
        this.vdftId = vdftId;
    }

    public Long getVdftId() {
        return vdftId;
    }

    public void setVdftId(Long vdftId) {
        this.vdftId = vdftId;
    }

    public Boolean getVdftEstado() {
        return vdftEstado;
    }

    public void setVdftEstado(Boolean vdftEstado) {
        this.vdftEstado = vdftEstado;
    }

   

    public VntFactura getVfctId() {
        return vfctId;
    }

    public void setVfctId(VntFactura vfctId) {
        this.vfctId = vfctId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vdftId != null ? vdftId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntDetallefact)) {
            return false;
        }
        VntDetallefact other = (VntDetallefact) object;
        if ((this.vdftId == null && other.vdftId != null) || (this.vdftId != null && !this.vdftId.equals(other.vdftId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntDetallefact[ vdftId=" + vdftId + " ]";
    }

    /**
     * @return the vdftServico
     */
    public String getVdftServico() {
        return vdftServico;
    }

    /**
     * @param vdftServico the vdftServico to set
     */
    public void setVdftServico(String vdftServico) {
        this.vdftServico = vdftServico;
    }

    /**
     * @return the vdftCantidad
     */
    public Integer getVdftCantidad() {
        return vdftCantidad;
    }

    /**
     * @param vdftCantidad the vdftCantidad to set
     */
    public void setVdftCantidad(Integer vdftCantidad) {
        this.vdftCantidad = vdftCantidad;
    }

    /**
     * @return the vdftCostounitario
     */
    public BigDecimal getVdftCostounitario() {
        return vdftCostounitario;
    }

    /**
     * @param vdftCostounitario the vdftCostounitario to set
     */
    public void setVdftCostounitario(BigDecimal vdftCostounitario) {
        this.vdftCostounitario = vdftCostounitario;
    }

    /**
     * @return the vdftCostototal
     */
    public BigDecimal getVdftCostototal() {
        return vdftCostototal;
    }

    /**
     * @param vdftCostototal the vdftCostototal to set
     */
    public void setVdftCostototal(BigDecimal vdftCostototal) {
        this.vdftCostototal = vdftCostototal;
    }

    /**
     * @return the vdftDescuento
     */
    public BigDecimal getVdftDescuento() {
        return vdftDescuento;
    }

    /**
     * @param vdftDescuento the vdftDescuento to set
     */
    public void setVdftDescuento(BigDecimal vdftDescuento) {
        this.vdftDescuento = vdftDescuento;
    }

    /**
     * @return the vdftSubtotal
     */
    public BigDecimal getVdftSubtotal() {
        return vdftSubtotal;
    }

    /**
     * @param vdftSubtotal the vdftSubtotal to set
     */
    public void setVdftSubtotal(BigDecimal vdftSubtotal) {
        this.vdftSubtotal = vdftSubtotal;
    }

    /**
     * @return the vdftPorcentajeIva
     */
    public BigDecimal getVdftPorcentajeIva() {
        return vdftPorcentajeIva;
    }

    /**
     * @param vdftPorcentajeIva the vdftPorcentajeIva to set
     */
    public void setVdftPorcentajeIva(BigDecimal vdftPorcentajeIva) {
        this.vdftPorcentajeIva = vdftPorcentajeIva;
    }

    /**
     * @return the vdftValorIva
     */
    public BigDecimal getVdftValorIva() {
        return vdftValorIva;
    }

    /**
     * @param vdftValorIva the vdftValorIva to set
     */
    public void setVdftValorIva(BigDecimal vdftValorIva) {
        this.vdftValorIva = vdftValorIva;
    }
}
