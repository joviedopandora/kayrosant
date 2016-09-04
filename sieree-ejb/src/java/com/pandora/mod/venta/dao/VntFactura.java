/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import adm.sys.dao.AdmCrgxcol;
import com.pandora.adm.rf.dao.RfEstadofactura;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sandra
 */
@Entity
@Table(name = "vnt_factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntFactura.findAll", query = "SELECT v FROM VntFactura v ORDER BY v.vfctFechafactura DESC"),
    @NamedQuery(name = "VntFactura.findByVfctId", query = "SELECT v FROM VntFactura v WHERE v.vfctId = :vfctId"),
    @NamedQuery(name = "VntFactura.findByVfctNumfactura", query = "SELECT v FROM VntFactura v WHERE v.vfctNumfactura = :vfctNumfactura"),
    @NamedQuery(name = "VntFactura.findByVfctAnticipo", query = "SELECT v FROM VntFactura v WHERE v.vfctAnticipo = :vfctAnticipo"),
    @NamedQuery(name = "VntFactura.findByVfctValortotal", query = "SELECT v FROM VntFactura v WHERE v.vfctValortotal = :vfctValortotal"),
    @NamedQuery(name = "VntFactura.findByVfctObservacion", query = "SELECT v FROM VntFactura v WHERE v.vfctObservacion = :vfctObservacion"),
    @NamedQuery(name = "VntFactura.findByVfctFechaproceso", query = "SELECT v FROM VntFactura v WHERE v.vfctFechaproceso = :vfctFechaproceso"),
    //Lista de facturas por registro de venta confirmada
    @NamedQuery(name = "VntFactura.regVentaConf", query = "SELECT v FROM VntFactura v JOIN v.rgvtId r "
            + " WHERE r.rgvtEstconfirmada = :rgvtEstconfirmada AND v.eftId.eftId = :eftId ORDER BY r.rgvtFechaconfirma DESC"),
    //Obtener factura por tarea
    @NamedQuery(name = "VntFactura.tareaId", query = "SELECT v FROM VntFactura v WHERE v.strId = :strId"),

    @NamedQuery(name = "VntFactura.findByregVenta", query = "SELECT v FROM VntFactura v JOIN v.rgvtId r "
            + " WHERE r.rgvtId = :rgvtId AND v.eftId.eftId <> :eftId  "),
   // @NamedQuery(name = "VntFactura.findByregVenta", query = "SELECT v FROM VntFactura v WHERE 1= 1 v.eftId.eftId")

})
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "VntFactura.countfactfecha", query = "SELECT to_char(vnt_factura.vfct_fechafactura,'Mon'), count(vnt_factura.vfct_id) FROM vnt_factura group by to_char(vnt_factura.vfct_fechafactura,'Mon') order by 1")

})

public class VntFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vfct_id")
    private Long vfctId;
    @Size(max = 100)
    @Column(name = "vfct_numfactura")
    private String vfctNumfactura;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vfct_anticipo")
    private BigDecimal vfctAnticipo;
    @Column(name = "vfct_valortotal")
    private BigDecimal vfctValortotal;
    @Size(max = 2147483647)
    @Column(name = "vfct_observacion")
    private String vfctObservacion;
    @Column(name = "vfct_fechafactura")
    @Temporal(TemporalType.DATE)
    private Date vfctFechafactura;
    @Column(name = "vfct_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vfctFechaproceso;

    @JoinColumn(name = "rgvt_id", referencedColumnName = "rgvt_id")
    @ManyToOne
    private VntRegistroventa rgvtId;
    @JoinColumn(name = "eft_id", referencedColumnName = "eft_id")
    @ManyToOne
    private RfEstadofactura eftId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;
    @Column(name = "str_id")
    private Long strId;

    @Column(name = "tcl_id")
    private Integer tclid;

    @OneToMany(mappedBy = "vfctId")
    private List<VntDetallefact> vntDetallefactList;
    //cambios
    @Column(name = "factcl_nombres")
    private String factclNombres;
    // @Column(name = "factcl_apellidos")
    //private String factclApellidos;
    @Size(max = 10)
    @Column(name = "factcl_tipodocuemento")
    private String factclTipodocuemento;
    @Size(max = 25)
    @Column(name = "factcl_identificacion")
    private String factclIdentificacion;
    @Column(name = "factcl_diereccion")
    private String factclDiereccion;

    @Column(name = "factcl_celular")
    private String factclCelular;

    @Column(name = "factcl_fijo")
    private String factclFijo;

    @Column(name = "factcl_email")
    private String factclEmail;

    @Column(name = "factcl_formapago")
    private String factclFormapago;

    @Column(name = "factsr_detalle")
    private String factsrDetalle;

    @Column(name = "factsr_protagonista")
    private String factsrProtagonista;

    @Column(name = "factsr_fecha")
    @Temporal(TemporalType.DATE)
    private Date factsrFecha;

    @Column(name = "factsr_direccionevento")
    private String factsrDireccionevento;

    @Column(name = "factsr_motivo")
    private String factsrMotivo;

    @Column(name = "factsr_hora")
    private String factsrHora;

    @Column(name = "factdc_descripcion")
    private String factdcDescripcion;

    @Column(name = "vfct_descuento")
    private BigDecimal vfctDescuento;
    @Column(name = "vfct_saldo")
    private BigDecimal vfctSaldo;

    @Column(name = "factsr_ordencliente")
    private String factsrOrdencliente;

    @Column(name = "vfct_fechapago")
    @Temporal(TemporalType.DATE)
    private Date vfctfechapago;

    @Column(name = "vfct_fecharadica")
    @Temporal(TemporalType.DATE)
    private Date vfctfecharadica;

    @Column(name = "vfct_numdiaspago")
    private Integer vfctnumdiaspago;

    public String getFactsrOrdencliente() {
        return factsrOrdencliente;
    }

    public void setFactsrOrdencliente(String factsrOrdencliente) {
        this.factsrOrdencliente = factsrOrdencliente;
    }

    public BigDecimal getVfctSaldo() {
        return vfctSaldo;
    }

    public void setVfctSaldo(BigDecimal vfctSaldo) {
        this.vfctSaldo = vfctSaldo;
    }

    public BigDecimal getVfctDescuento() {
        return vfctDescuento;
    }

    public void setVfctDescuento(BigDecimal vfctDescuento) {
        this.vfctDescuento = vfctDescuento;
    }

    public BigDecimal getVfctSubtotal() {
        return vfctSubtotal;
    }

    public void setVfctSubtotal(BigDecimal vfctSubtotal) {
        this.vfctSubtotal = vfctSubtotal;
    }

    public BigDecimal getVfctPorcentajeiva() {
        return vfctPorcentajeiva;
    }

    public void setVfctPorcentajeiva(BigDecimal vfctPorcentajeiva) {
        this.vfctPorcentajeiva = vfctPorcentajeiva;
    }

    public BigDecimal getVfctIva() {
        return vfctIva;
    }

    public void setVfctIva(BigDecimal vfctIva) {
        this.vfctIva = vfctIva;
    }
    @Column(name = "vfct_subtotal")
    private BigDecimal vfctSubtotal;
    @Column(name = "vfct_porcentajeiva")
    private BigDecimal vfctPorcentajeiva;
    @Column(name = "vfct_iva")
    private BigDecimal vfctIva;

    @Size(max = 300)
    @Column(name = "factdc_observacion")
    private String factdcObservacion;

    public VntFactura() {

    }

    public VntFactura(Long vfctId) {
        this.vfctId = vfctId;
    }

    public Long getVfctId() {
        return vfctId;
    }

    public void setVfctId(Long vfctId) {
        this.vfctId = vfctId;
    }

    public String getVfctNumfactura() {
        return vfctNumfactura;
    }

    public void setVfctNumfactura(String vfctNumfactura) {
        this.vfctNumfactura = vfctNumfactura;
    }

    public BigDecimal getVfctAnticipo() {
        return vfctAnticipo;
    }

    public void setVfctAnticipo(BigDecimal vfctAnticipo) {
        this.vfctAnticipo = vfctAnticipo;
    }

    public BigDecimal getVfctValortotal() {
        return vfctValortotal;
    }

    public void setVfctValortotal(BigDecimal vfctValortotal) {
        this.vfctValortotal = vfctValortotal;
    }

    public String getVfctObservacion() {
        return vfctObservacion;
    }

    public void setVfctObservacion(String vfctObservacion) {
        this.vfctObservacion = vfctObservacion;
    }

    public Date getVfctFechafactura() {
        return vfctFechafactura;
    }

    public void setVfctFechafactura(Date vfctFechafactura) {
        this.vfctFechafactura = vfctFechafactura;
    }

    public Date getVfctFechaproceso() {
        return vfctFechaproceso;
    }

    public void setVfctFechaproceso(Date vfctFechaproceso) {
        this.vfctFechaproceso = vfctFechaproceso;
    }

    public VntRegistroventa getRgvtId() {
        return rgvtId;
    }

    public void setRgvtId(VntRegistroventa rgvtId) {
        this.rgvtId = rgvtId;
    }

    public RfEstadofactura getEftId() {
        return eftId;
    }

    public void setEftId(RfEstadofactura eftId) {
        this.eftId = eftId;
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    @XmlTransient
    public List<VntDetallefact> getVntDetallefactList() {
        return vntDetallefactList;
    }

    public void setVntDetallefactList(List<VntDetallefact> vntDetallefactList) {
        this.vntDetallefactList = vntDetallefactList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vfctId != null ? vfctId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntFactura)) {
            return false;
        }
        VntFactura other = (VntFactura) object;
        if ((this.vfctId == null && other.vfctId != null) || (this.vfctId != null && !this.vfctId.equals(other.vfctId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntFactura[ vfctId=" + vfctId + " ]";
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
     * @return the factclNombres
     */
    public String getFactclNombres() {
        return factclNombres;
    }

    /**
     * @param factclNombres the factclNombres to set
     */
    public void setFactclNombres(String factclNombres) {
        this.factclNombres = factclNombres;
    }

    /**
     * @return the factclTipodocuemento
     */
    public String getFactclTipodocuemento() {
        return factclTipodocuemento;
    }

    /**
     * @param factclTipodocuemento the factclTipodocuemento to set
     */
    public void setFactclTipodocuemento(String factclTipodocuemento) {
        this.factclTipodocuemento = factclTipodocuemento;
    }

    /**
     * @return the factclIdentificacion
     */
    public String getFactclIdentificacion() {
        return factclIdentificacion;
    }

    /**
     * @param factclIdentificacion the factclIdentificacion to set
     */
    public void setFactclIdentificacion(String factclIdentificacion) {
        this.factclIdentificacion = factclIdentificacion;
    }

    /**
     * @return the factclDiereccion
     */
    public String getFactclDiereccion() {
        return factclDiereccion;
    }

    /**
     * @param factclDiereccion the factclDiereccion to set
     */
    public void setFactclDiereccion(String factclDiereccion) {
        this.factclDiereccion = factclDiereccion;
    }

    /**
     * @return the factclCelular
     */
    public String getFactclCelular() {
        return factclCelular;
    }

    /**
     * @param factclCelular the factclCelular to set
     */
    public void setFactclCelular(String factclCelular) {
        this.factclCelular = factclCelular;
    }

    /**
     * @return the factclFijo
     */
    public String getFactclFijo() {
        return factclFijo;
    }

    /**
     * @param factclFijo the factclFijo to set
     */
    public void setFactclFijo(String factclFijo) {
        this.factclFijo = factclFijo;
    }

    /**
     * @return the factclEmail
     */
    public String getFactclEmail() {
        return factclEmail;
    }

    /**
     * @param factclEmail the factclEmail to set
     */
    public void setFactclEmail(String factclEmail) {
        this.factclEmail = factclEmail;
    }

    /**
     * @return the factclFormapago
     */
    public String getFactclFormapago() {
        return factclFormapago;
    }

    /**
     * @param factclFormapago the factclFormapago to set
     */
    public void setFactclFormapago(String factclFormapago) {
        this.factclFormapago = factclFormapago;
    }

    /**
     * @return the factsrDetalle
     */
    public String getFactsrDetalle() {
        return factsrDetalle;
    }

    /**
     * @param factsrDetalle the factsrDetalle to set
     */
    public void setFactsrDetalle(String factsrDetalle) {
        this.factsrDetalle = factsrDetalle;
    }

    /**
     * @return the factsrProtagonista
     */
    public String getFactsrProtagonista() {
        return factsrProtagonista;
    }

    /**
     * @param factsrProtagonista the factsrProtagonista to set
     */
    public void setFactsrProtagonista(String factsrProtagonista) {
        this.factsrProtagonista = factsrProtagonista;
    }

    /**
     * @return the factsrFecha
     */
    public Date getFactsrFecha() {
        return factsrFecha;
    }

    /**
     * @param factsrFecha the factsrFecha to set
     */
    public void setFactsrFecha(Date factsrFecha) {
        this.factsrFecha = factsrFecha;
    }

    /**
     * @return the factsrDireccionevento
     */
    public String getFactsrDireccionevento() {
        return factsrDireccionevento;
    }

    /**
     * @param factsrDireccionevento the factsrDireccionevento to set
     */
    public void setFactsrDireccionevento(String factsrDireccionevento) {
        this.factsrDireccionevento = factsrDireccionevento;
    }

    /**
     * @return the factsrMotivo
     */
    public String getFactsrMotivo() {
        return factsrMotivo;
    }

    /**
     * @param factsrMotivo the factsrMotivo to set
     */
    public void setFactsrMotivo(String factsrMotivo) {
        this.factsrMotivo = factsrMotivo;
    }

    /**
     * @return the factsrHora
     */
    public String getFactsrHora() {
        return factsrHora;
    }

    /**
     * @param factsrHora the factsrHora to set
     */
    public void setFactsrHora(String factsrHora) {
        this.factsrHora = factsrHora;
    }

    /**
     * @return the factdcDescripcion
     */
    public String getFactdcDescripcion() {
        return factdcDescripcion;
    }

    /**
     * @param factdcDescripcion the factdcDescripcion to set
     */
    public void setFactdcDescripcion(String factdcDescripcion) {
        this.factdcDescripcion = factdcDescripcion;
    }

    /**
     * @return the tclid
     */
    public Integer getTclid() {
        return tclid;
    }

    /**
     * @param tclid the tclid to set
     */
    public void setTclid(Integer tclid) {
        this.tclid = tclid;
    }

    /**
     * @return the factdcObservacion
     */
    public String getFactdcObservacion() {
        return factdcObservacion;
    }

    /**
     * @param factdcObservacion the factdcObservacion to set
     */
    public void setFactdcObservacion(String factdcObservacion) {
        this.factdcObservacion = factdcObservacion;
    }

    /**
     * @return the vfctfechapago
     */
    public Date getVfctfechapago() {
        return vfctfechapago;
    }

    /**
     * @param vfctfechapago the vfctfechapago to set
     */
    public void setVfctfechapago(Date vfctfechapago) {
        this.vfctfechapago = vfctfechapago;
    }

    /**
     * @return the vfctfecharadica
     */
    public Date getVfctfecharadica() {
        return vfctfecharadica;
    }

    /**
     * @param vfctfecharadica the vfctfecharadica to set
     */
    public void setVfctfecharadica(Date vfctfecharadica) {
        this.vfctfecharadica = vfctfecharadica;
    }

    /**
     * @return the vfctnumdiaspago
     */
    public Integer getVfctnumdiaspago() {
        return vfctnumdiaspago;
    }

    /**
     * @param vfctnumdiaspago the vfctnumdiaspago to set
     */
    public void setVfctnumdiaspago(Integer vfctnumdiaspago) {
        this.vfctnumdiaspago = vfctnumdiaspago;
    }
}
