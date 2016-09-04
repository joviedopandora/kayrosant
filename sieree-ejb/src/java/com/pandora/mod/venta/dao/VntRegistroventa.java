/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import com.pandora.bussiness.util.EnEstadosVentaOp;
import com.pandora.mod.indicadores.IndicadoresDTO;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.pagocuenta.dao.FinFormapago;
import com.pandora.pagocuenta.dao.RfBanco;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "vnt_registroventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntRegistroventa.findAll", query = "SELECT v FROM VntRegistroventa v"),
    //SELECT * FROM vnt_registroventa rv where rv.rgvt_facturada=true
    @NamedQuery(name = "VntRegistroventa.findByRgvtId", query = "SELECT v FROM VntRegistroventa v WHERE v.rgvtId = :rgvtId"),
    @NamedQuery(name = "VntRegistroventa.findByRgvtFechacre", query = "SELECT v FROM VntRegistroventa v WHERE v.rgvtFechacre = :rgvtFechacre"),
    @NamedQuery(name = "VntRegistroventa.findByRgvtEst", query = "SELECT v FROM VntRegistroventa v WHERE v.rgvtEst = :rgvtEst"),
    @NamedQuery(name = "VntRegistroventa.findByRgvtObsr", query = "SELECT v FROM VntRegistroventa v WHERE v.rgvtObsr = :rgvtObsr"),
    @NamedQuery(name = "VntRegistroventa.findByRgvtAnulado", query = "SELECT v FROM VntRegistroventa v WHERE v.rgvtAnulado = :rgvtAnulado"),
    @NamedQuery(name = "VntRegistroventa.findByIndversion", query = "SELECT v FROM VntRegistroventa v WHERE v.indversion = :indversion"),
    @NamedQuery(name = "VntRegistroventa.revVentaXStrId", query = "SELECT v FROM VntRegistroventa v WHERE v.strId = :strId"),
    @NamedQuery(name = "VntRegistroventa.revVentaXPspIdXColXEst", query = "SELECT v FROM VntRegistroventa v "
            + " WHERE v.strId IN (SELECT DISTINCT s.strId FROM SysTarea s JOIN s.sysSegtareaList seg JOIN seg.spsId sps "
            + " WHERE sps.spsId = :spsId AND seg.sgtEstpaso = :sgtEstpaso AND seg.cxcId.cxcId = :cxcId)"),
    @NamedQuery(name = "VntRegistroventa.revVentaXStrIdXEstAnulado", query = "SELECT v FROM VntRegistroventa v WHERE v.strId = :strId AND v.rgvtAnulado <> :rgvtAnulado"),
    @NamedQuery(name = "VntRegistroventa.revVentaXPspIdXEstventa", query = "SELECT v FROM VntRegistroventa v "
            + " WHERE v.estrvntId.estrvntId = :estrvntId AND v.strId IN "
            + " (SELECT DISTINCT s.strId FROM SysTarea s JOIN s.sysSegtareaList seg JOIN seg.spsId sps "
            + " WHERE sps.spsId = :spsId AND seg.sgtEstpaso = :sgtEstpaso ) ORDER BY v.rgvtFechacre ASC"),
    @NamedQuery(name = "VntRegistroventa.revVentaXClienteXEstventa", query = "SELECT v FROM VntRegistroventa v JOIN v.clnId cln "
            + " WHERE cln.clnId = :clnId AND v.estrvntId.estrvntId = :estrvntId  ORDER BY v.rgvtFechacre DESC"),
    //Cliente por estado de venta
    @NamedQuery(name = "VntRegistroventa.clienteXEstVnt", query = "SELECT v FROM VntRegistroventa v JOIN v.clnId c JOIN v.estrvntId e "
            + " WHERE c.clnId = :clnId AND e.estrvntId = :estrvntId"),
    //Lista de resgistros de venta por confirmación de pago
    @NamedQuery(name = "VntRegistroventa.pagoConfirmado", query = "SELECT v FROM VntRegistroventa v "
            + " WHERE v.rgvtEstconfirmada = :rgvtEstconfirmada ORDER BY v.rgvtFechaconfirma DESC"),
    //Lista de registros de venta sin confirmación y pago
    @NamedQuery(name = "VntRegistroventa.pagoXConf", query = "SELECT v FROM VntRegistroventa v JOIN v.estrvntId e "
            + " WHERE v.rgvtPagado = :rgvtPagado AND v.rgvtEstconfirmada = :rgvtEstconfirmada AND e.estrvntId IN (:estrvntId,:estrvntId2) ORDER BY v.vdeId.vdeFechaevt ASC"),
    //Lista de registros de venta con confirmación y pago
    @NamedQuery(name = "VntRegistroventa.pagoConf", query = "SELECT v FROM VntRegistroventa v "
            + " WHERE v.rgvtPagado = :rgvtPagado AND v.rgvtEstconfirmada = :rgvtEstconfirmada ORDER BY v.vdeId.vdeFechaevt DESC"),
    //Lista de registros de venta por tipo de cliente, estado de venta y confirmación de pago
    @NamedQuery(name = "VntRegistroventa.clienteXEstXConfVnt", query = "SELECT v FROM VntRegistroventa v JOIN v.clnId c JOIN c.tclId t JOIN v.estrvntId e "
            + " WHERE t.tclId = :tclId AND e.estrvntId IN( :estrvntId,:estrvntId2,:estrvntId3) AND v.rgvtEstconfirmada = :rgvtEstconfirmada AND v.rgvtFacturada = :rgvtFacturada"),
    //Lista de todos los registros de venta
    @NamedQuery(name = "VntRegistroventa.regVentas", query = "SELECT v FROM VntRegistroventa v ORDER BY v.vdeId.vdeFechaevt ASC"),
    
    //Lista de todos los registros de venta por colaborador
    @NamedQuery(name = "VntRegistroventa.regVentasXColaborador",
        query = "SELECT v FROM VntRegistroventa v  JOIN v.popOrdenprodList op JOIN op.popCxceventoList ps JOIN ps.cxcId c WHERE c.cxcId = :cxcId ORDER BY v.vdeId.vdeFechaevt ASC"),
    @NamedQuery(name = "VntRegistroventa.clienteXServiciosPendientesFacturar", query = "SELECT v FROM VntRegistroventa v JOIN v.clnId c JOIN c.tclId t "
            + " WHERE t.tclId = :tclId AND v.rgvtCantserviciosAsociados > v.rgvtCantserviciosProcesados AND v.rgvtEstados IN (:estado1,:estado2)  ORDER BY v.vdeId.vdeFechaevt ASC"),
        
        
         @NamedQuery(name = "VntRegistroventa.clienteXServiciosPendientesOdenProduccion", 
                 query = "SELECT v FROM VntRegistroventa v JOIN v.clnId c JOIN c.tclId t  WHERE t.tclId = :tclId AND v.rgvtActivarOp =:rgvtActivarOp   AND v.rgvtCantidadservasociadosOp > v.rgvtCantidadservprocesadosOp AND v.rgvtActCronograma = :rgvtActCronograma ORDER BY v.vdeId.vdeFechaevt ASC")
    
})
@NamedNativeQueries({
@NamedNativeQuery(
name = "VntRegistroventa.findCountVentasXPeriodo", query ="SELECT to_char(rgvt_fechacre,'YYYYMMDD') periodo, count(rgvt_id) cantidad FROM vnt_registroventa,vnt_cliente "
       +" WHERE vnt_cliente.tcl_id= ? AND vnt_cliente.cln_id = vnt_registroventa.cln_id AND rgvt_anulado= ? "
        + " AND  to_date(to_char(rgvt_fechacre,'YYYY-MM-DD'),'YYYY-MM-DD')>=? AND to_date(to_char(rgvt_fechacre,'YYYY-MM-DD'),'YYYY-MM-DD')<=? GROUP BY to_char(rgvt_fechacre,'YYYYMMDD') order by 1"),

@NamedNativeQuery(name = "VntRegistroventa.findCountVentasXPromedio", query ="SELECT COALESCE(ROUND(AVG(cantidad)),0.0) promedio FROM (SELECT to_char(rgvt_fechacre,'YYYYMMDD') periodo, count(rgvt_id) cantidad FROM vnt_registroventa,vnt_cliente "
       +" WHERE vnt_cliente.tcl_id= ? AND vnt_cliente.cln_id = vnt_registroventa.cln_id AND rgvt_anulado= ? "
        + " AND  to_date(to_char(rgvt_fechacre,'YYYY-MM-DD'),'YYYY-MM-DD')>=? AND to_date(to_char(rgvt_fechacre,'YYYY-MM-DD'),'YYYY-MM-DD')<=?  GROUP BY to_char(rgvt_fechacre,'YYYYMMDD')) AS a ")

})
public class VntRegistroventa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rgvt_id")
    private Long rgvtId;
    @Column(name = "rgvt_fechacre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rgvtFechacre;
    @Column(name = "rgvt_est")
    private Boolean rgvtEst;
    @Size(max = 2147483647)
    @Column(name = "rgvt_obsr")
    private String rgvtObsr;
    @Column(name = "rgvt_anulado")
    private Boolean rgvtAnulado;
    @Column(name = "rgvt_estconfirmada")
    private Boolean rgvtEstconfirmada;
    @Column(name = "rgvt_fechaconfirma")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rgvtFechaconfirma;
    
    @NotNull
    @Column(name = "rgvt_estados")
    private int rgvtEstados;
    
    @Column(name = "indversion")
    private Integer indversion;
    
    
    @Column(name = "str_id")
    private Long strId;
    @Column(name = "rgvt_numordenejec")
    private String rgvtNumordenejec;
    @Size(max = 200)
    @Column(name = "rgvt_transacbanco")
    private String rgvtTransacbanco;
    @Column(name = "rgvt_valorventa")
    private BigDecimal rgvtValorventa;
    @Column(name = "rgvt_pagado")
    private Boolean rgvtPagado;
    @Column(name = "rgvt_valorpagado")
    private BigDecimal rgvtValorpagado;
    @Column(name = "rgvt_descuento")
    private BigDecimal rgvtDescuento;
    @OneToMany(mappedBy = "rgvtId")
    private List<VntColxventa> vntColxventaList;
    @OneToMany(mappedBy = "rgvtId")
    private List<VntServxventa> vntServxventaList;
    @JoinColumn(name = "cln_id", referencedColumnName = "cln_id")
    @ManyToOne
    private VntCliente clnId;
    @JoinColumn(name = "estrvnt_id", referencedColumnName = "estrvnt_id")
    @ManyToOne
    private VntEstventa estrvntId;
    @JoinColumn(name = "bnc_id", referencedColumnName = "bnc_id")
    @ManyToOne
    private RfBanco bncId;
    @JoinColumn(name = "fpg_id", referencedColumnName = "fpg_id")
    @ManyToOne
    private FinFormapago fpgId;
    @OneToOne(mappedBy = "rgvtId" )
    private VntDetevento vdeId;
    @OneToMany(mappedBy = "rgvtId")
    private List<PopOrdenprod> popOrdenprodList;
    @OneToMany(mappedBy = "rgvtId")
    private List<VntFactura> vntFacturaList;
    @OneToMany(mappedBy = "rgvtId")
    private List<VntRemision> vntRemisionList;
    //cambio a facturado
    @NotNull
    @Column(name = "rgvt_facturada")
    private boolean rgvtFacturada;
    @Column(name = "rgvt_vlrfactura")
    private BigDecimal rgvtVlrfactura;
    @NotNull
    @Column(name = "rgvt_cantserviciosasociados")
    private int rgvtCantserviciosAsociados;
    
    @NotNull
    @Column(name = "rgvt_cantserviciosprocesados")
    private int rgvtCantserviciosProcesados;
    
    
    @NotNull
    @Column(name = "rgvt_activar_op")
    private boolean rgvtActivarOp;
    
    @NotNull
    @Column(name = "rgvt_cronograma")
    private boolean rgvtActCronograma;
    
    @NotNull
    @Column(name = "rgvt_cantidadservasociados_op")
    private int rgvtCantidadservasociadosOp;
    
     @NotNull
    @Column(name = "rgvt_cantidadservprocesados_op")
    private int rgvtCantidadservprocesadosOp;
    
    @Column(name = "rgvt_idordenprod")
    private Long rgvtIdordenprod;

    public Long getRgvtIdordenprod() {
        return rgvtIdordenprod;
    }

    public void setRgvtIdordenprod(Long rgvtIdordenprod) {
        this.rgvtIdordenprod = rgvtIdordenprod;
    }
    

    public VntRegistroventa() {
        rgvtFacturada = false;
         this.rgvtEstados = EnEstadosVentaOp.PENDIENTE.getId();
    }

    public boolean isRgvtFacturada() {
        return rgvtFacturada;
    }

    public void setRgvtFacturada(boolean rgvtFacturada) {
        this.rgvtFacturada = rgvtFacturada;
    }

    public VntRegistroventa(Long rgvtId) {
        this.rgvtId = rgvtId;
    }

    public Long getRgvtId() {
        return rgvtId;
    }

    public void setRgvtId(Long rgvtId) {
        this.rgvtId = rgvtId;
    }

    public Date getRgvtFechacre() {
        return rgvtFechacre;
    }

    public void setRgvtFechacre(Date rgvtFechacre) {
        this.rgvtFechacre = rgvtFechacre;
    }

    public Boolean getRgvtEst() {
        return rgvtEst;
    }

    public void setRgvtEst(Boolean rgvtEst) {
        this.rgvtEst = rgvtEst;
    }

    public String getRgvtObsr() {
        return rgvtObsr;
    }

    public void setRgvtObsr(String rgvtObsr) {
        this.rgvtObsr = rgvtObsr;
    }

    public Boolean getRgvtAnulado() {
        return rgvtAnulado;
    }

    public void setRgvtAnulado(Boolean rgvtAnulado) {
        this.rgvtAnulado = rgvtAnulado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<VntColxventa> getVntColxventaList() {
        return vntColxventaList;
    }

    public void setVntColxventaList(List<VntColxventa> vntColxventaList) {
        this.vntColxventaList = vntColxventaList;
    }

    @XmlTransient
    public List<VntServxventa> getVntServxventaList() {
        return vntServxventaList;
    }

    public void setVntServxventaList(List<VntServxventa> vntServxventaList) {
        this.vntServxventaList = vntServxventaList;
    }

    public VntCliente getClnId() {
        return clnId;
    }

    public void setClnId(VntCliente clnId) {
        this.clnId = clnId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rgvtId != null ? rgvtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntRegistroventa)) {
            return false;
        }
        VntRegistroventa other = (VntRegistroventa) object;
        if ((this.rgvtId == null && other.rgvtId != null) || (this.rgvtId != null && !this.rgvtId.equals(other.rgvtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntRegistroventa[ rgvtId=" + rgvtId + " ]";
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
     * @return the estrvntId
     */
    public VntEstventa getEstrvntId() {
        return estrvntId;
    }

    /**
     * @param estrvntId the estrvntId to set
     */
    public void setEstrvntId(VntEstventa estrvntId) {
        this.estrvntId = estrvntId;
    }

    /**
     * @return the rgvtTransacbanco
     */
    public String getRgvtTransacbanco() {
        return rgvtTransacbanco;
    }

    /**
     * @param rgvtTransacbanco the rgvtTransacbanco to set
     */
    public void setRgvtTransacbanco(String rgvtTransacbanco) {
        this.rgvtTransacbanco = rgvtTransacbanco;
    }

    /**
     * @return the rgvtNumordenejec
     */
    public String getRgvtNumordenejec() {
        return rgvtNumordenejec;
    }

    /**
     * @param rgvtNumordenejec the rgvtNumordenejec to set
     */
    public void setRgvtNumordenejec(String rgvtNumordenejec) {
        this.rgvtNumordenejec = rgvtNumordenejec;
    }

    /**
     * @return the bncId
     */
    public RfBanco getBncId() {
        return bncId;
    }

    /**
     * @param bncId the bncId to set
     */
    public void setBncId(RfBanco bncId) {
        this.bncId = bncId;
    }

    /**
     * @return the rgvtValorventa
     */
    public BigDecimal getRgvtValorventa() {
        return rgvtValorventa;
    }

    /**
     * @param rgvtValorventa the rgvtValorventa to set
     */
    public void setRgvtValorventa(BigDecimal rgvtValorventa) {
        this.rgvtValorventa = rgvtValorventa;
    }

    /**
     * @return the rgvtPagado
     */
    public Boolean getRgvtPagado() {
        return rgvtPagado;
    }

    /**
     * @param rgvtPagado the rgvtPagado to set
     */
    public void setRgvtPagado(Boolean rgvtPagado) {
        this.rgvtPagado = rgvtPagado;
    }

    /**
     * @return the vdeId
     */
    public VntDetevento getVdeId() {
        return vdeId;
    }

    /**
     * @param vdeId the vdeId to set
     */
    public void setVdeId(VntDetevento vdeId) {
        this.vdeId = vdeId;
    }

    /**
     * @return the rgvtValorpagado
     */
    public BigDecimal getRgvtValorpagado() {
        return rgvtValorpagado;
    }

    /**
     * @param rgvtValorpagado the rgvtValorpagado to set
     */
    public void setRgvtValorpagado(BigDecimal rgvtValorpagado) {
        this.rgvtValorpagado = rgvtValorpagado;
    }

    /**
     * @return the popOrdenprodList
     */
    public List<PopOrdenprod> getPopOrdenprodList() {
        return popOrdenprodList;
    }

    /**
     * @param popOrdenprodList the popOrdenprodList to set
     */
    public void setPopOrdenprodList(List<PopOrdenprod> popOrdenprodList) {
        this.popOrdenprodList = popOrdenprodList;
    }

    /**
     * @return the vntFacturaList
     */
    public List<VntFactura> getVntFacturaList() {
        return vntFacturaList;
    }

    /**
     * @param vntFacturaList the vntFacturaList to set
     */
    public void setVntFacturaList(List<VntFactura> vntFacturaList) {
        this.vntFacturaList = vntFacturaList;
    }

    /**
     * @return the vntRemisionList
     */
    public List<VntRemision> getVntRemisionList() {
        return vntRemisionList;
    }

    /**
     * @param vntRemisionList the vntRemisionList to set
     */
    public void setVntRemisionList(List<VntRemision> vntRemisionList) {
        this.vntRemisionList = vntRemisionList;
    }

    /**
     * @return the rgvtEstconfirmada
     */
    public Boolean getRgvtEstconfirmada() {
        return rgvtEstconfirmada;
    }

    /**
     * @param rgvtEstconfirmada the rgvtEstconfirmada to set
     */
    public void setRgvtEstconfirmada(Boolean rgvtEstconfirmada) {
        this.rgvtEstconfirmada = rgvtEstconfirmada;
    }

    /**
     * @return the rgvtFechaconfirma
     */
    public Date getRgvtFechaconfirma() {
        return rgvtFechaconfirma;
    }

    /**
     * @param rgvtFechaconfirma the rgvtFechaconfirma to set
     */
    public void setRgvtFechaconfirma(Date rgvtFechaconfirma) {
        this.rgvtFechaconfirma = rgvtFechaconfirma;
    }

    /**
     * @return the fpgId
     */
    public FinFormapago getFpgId() {
        return fpgId;
    }

    /**
     * @param fpgId the fpgId to set
     */
    public void setFpgId(FinFormapago fpgId) {
        this.fpgId = fpgId;
    }

    /**
     * @return the rgvtDescuento
     */
    public BigDecimal getRgvtDescuento() {
        return rgvtDescuento;
    }

    /**
     * @param rgvtDescuento the rgvtDescuento to set
     */
    public void setRgvtDescuento(BigDecimal rgvtDescuento) {
        this.rgvtDescuento = rgvtDescuento;
    }

    /**
     * @return the rgvtVlrfactura
     */
    public BigDecimal getRgvtVlrfactura() {
        return rgvtVlrfactura;
    }

    /**
     * @param rgvtVlrfactura the rgvtVlrfactura to set
     */
    public void setRgvtVlrfactura(BigDecimal rgvtVlrfactura) {
        this.rgvtVlrfactura = rgvtVlrfactura;
    }

    public int getRgvtCantserviciosAsociados() {
        return rgvtCantserviciosAsociados;
    }

    public void setRgvtCantserviciosAsociados(int rgvtCantserviciosAsociados) {
        this.rgvtCantserviciosAsociados = rgvtCantserviciosAsociados;
    }

    public int getRgvtCantserviciosProcesados() {
        return rgvtCantserviciosProcesados;
    }

    public void setRgvtCantserviciosProcesados(int rgvtCantserviciosProcesados) {
        this.rgvtCantserviciosProcesados = rgvtCantserviciosProcesados;
    }

    public boolean isRgvtActivarOp() {
        return rgvtActivarOp;
    }

    public void setRgvtActivarOp(boolean rgvtActivarOp) {
        this.rgvtActivarOp = rgvtActivarOp;
    }

    public int getRgvtCantidadservasociadosOp() {
        return rgvtCantidadservasociadosOp;
    }

    public void setRgvtCantidadservasociadosOp(int rgvtCantidadservasociadosOp) {
        this.rgvtCantidadservasociadosOp = rgvtCantidadservasociadosOp;
    }

    public int getRgvtCantidadservprocesadosOp() {
        return rgvtCantidadservprocesadosOp;
    }

    public void setRgvtCantidadservprocesadosOp(int rgvtCantidadservprocesadosOp) {
        this.rgvtCantidadservprocesadosOp = rgvtCantidadservprocesadosOp;
    }

    public int getRgvtEstados() {
        return rgvtEstados;
    }

    public void setRgvtEstados(int rgvtEstados) {
        this.rgvtEstados = rgvtEstados;
    }

    /**
     * @return the rgvtActCronograma
     */
    public boolean isRgvtActCronograma() {
        return rgvtActCronograma;
    }

    /**
     * @param rgvtActCronograma the rgvtActCronograma to set
     */
    public void setRgvtActCronograma(boolean rgvtActCronograma) {
        this.rgvtActCronograma = rgvtActCronograma;
    }

  
    
    
    
    
}
