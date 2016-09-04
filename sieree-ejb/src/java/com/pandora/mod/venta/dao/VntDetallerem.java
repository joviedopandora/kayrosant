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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sandra
 */
@Entity
@Table(name = "vnt_detallerem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntDetallerem.findAll", query = "SELECT v FROM VntDetallerem v"),
    @NamedQuery(name = "VntDetallerem.findByVdrmId", query = "SELECT v FROM VntDetallerem v WHERE v.vdrmId = :vdrmId"),
    @NamedQuery(name = "VntDetallerem.findByVdrmEstado", query = "SELECT v FROM VntDetallerem v WHERE v.vdrmEstado = :vdrmEstado")})
public class VntDetallerem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vdrm_id")
    private Long vdrmId;
    @Column(name = "vdrm_estado")
    private Boolean vdrmEstado;
    @JoinColumn(name = "vrms_id", referencedColumnName = "vrms_id")
    @ManyToOne
    private VntRemision vrmsId;
    @Column(name = "vdrm_servico")
    private String vdrmServico;
    @Column(name = "vdrm_cantidad")
    private Integer vdrmCantidad;

    @Column(name = "vdrm_costounitario")
    private BigDecimal vdrmCostounitario;
    @Column(name = "vdrm_costototal")
    private BigDecimal vdrmCostototal;

    @Column(name = "vdrm_subservico")
    private String vdrmSubservico;
    @JoinColumn(name = "vsrv_id", referencedColumnName = "vsrv_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntServicio vsrvId;
    @Column(name = "vdrm_especificaciones")
    private String vdrmEspecificaciones;
    @Column(name = "vdrm_descuento")
    private BigDecimal vdrmDescuento;

    public VntDetallerem() {
    }

    public VntDetallerem(Long vdrmId) {
        this.vdrmId = vdrmId;
    }

    public Long getVdrmId() {
        return vdrmId;
    }

    public void setVdrmId(Long vdrmId) {
        this.vdrmId = vdrmId;
    }

    public Boolean getVdrmEstado() {
        return vdrmEstado;
    }

    public void setVdrmEstado(Boolean vdrmEstado) {
        this.vdrmEstado = vdrmEstado;
    }

    public VntRemision getVrmsId() {
        return vrmsId;
    }

    public void setVrmsId(VntRemision vrmsId) {
        this.vrmsId = vrmsId;
    }

    public String getVdrmServico() {
        return vdrmServico;
    }

    public void setVdrmServico(String vdrmServico) {
        this.vdrmServico = vdrmServico;
    }

    public Integer getVdrmCantidad() {
        return vdrmCantidad;
    }

    public void setVdrmCantidad(Integer vdrmCantidad) {
        this.vdrmCantidad = vdrmCantidad;
    }

    public BigDecimal getVdrmCostounitario() {
        return vdrmCostounitario;
    }

    public void setVdrmCostounitario(BigDecimal vdrmCostounitario) {
        this.vdrmCostounitario = vdrmCostounitario;
    }

    public BigDecimal getVdrmCostototal() {
        return vdrmCostototal;
    }

    public void setVdrmCostototal(BigDecimal vdrmCostototal) {
        this.vdrmCostototal = vdrmCostototal;
    }

    public String getVdrmSubservico() {
        return vdrmSubservico;
    }

    public void setVdrmSubservico(String vdrmSubservico) {
        this.vdrmSubservico = vdrmSubservico;
    }

    public VntServicio getVsrvId() {
        return vsrvId;
    }

    public void setVsrvId(VntServicio vsrvId) {
        this.vsrvId = vsrvId;
    }

    public BigDecimal getVdrmDescuento() {
        return vdrmDescuento;
    }

    public void setVdrmDescuento(BigDecimal vdrmDescuento) {
        this.vdrmDescuento = vdrmDescuento;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vdrmId != null ? vdrmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntDetallerem)) {
            return false;
        }
        VntDetallerem other = (VntDetallerem) object;
        if ((this.vdrmId == null && other.vdrmId != null) || (this.vdrmId != null && !this.vdrmId.equals(other.vdrmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntDetallerem[ vdrmId=" + vdrmId + " ]";
    }

    /**
     * @return the vdrmEspecificaciones
     */
    public String getVdrmEspecificaciones() {
        return vdrmEspecificaciones;
    }

    /**
     * @param vdrmEspecificaciones the vdrmEspecificaciones to set
     */
    public void setVdrmEspecificaciones(String vdrmEspecificaciones) {
        this.vdrmEspecificaciones = vdrmEspecificaciones;
    }

}
