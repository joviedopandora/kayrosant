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
@Table(name = "vnt_remision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntRemision.findAll", query = "SELECT v FROM VntRemision v"),
    @NamedQuery(name = "VntRemision.findByVrmsId", query = "SELECT v FROM VntRemision v WHERE v.vrmsId = :vrmsId"),
    @NamedQuery(name = "VntRemision.findByVrmsNumremision", query = "SELECT v FROM VntRemision v WHERE v.vrmsNumremision = :vrmsNumremision"),
    @NamedQuery(name = "VntRemision.findByVrmsAnticipo", query = "SELECT v FROM VntRemision v WHERE v.vrmsAnticipo = :vrmsAnticipo"),
    @NamedQuery(name = "VntRemision.findByVrmsValortotal", query = "SELECT v FROM VntRemision v WHERE v.vrmsValortotal = :vrmsValortotal"),
    @NamedQuery(name = "VntRemision.findByVrmsObservacion", query = "SELECT v FROM VntRemision v WHERE v.vrmsObservacion = :vrmsObservacion"),
    @NamedQuery(name = "VntRemision.findByVrmsFecharemision", query = "SELECT v FROM VntRemision v WHERE v.vrmsFecharemision = :vrmsFecharemision"),
    @NamedQuery(name = "VntRemision.findByVrmsFechaproceso", query = "SELECT v FROM VntRemision v WHERE v.vrmsFechaproceso = :vrmsFechaproceso"),
    @NamedQuery(name = "VntRemision.findByStrId", query = "SELECT v FROM VntRemision v WHERE v.strId = :strId"),
    //Lista de remisiones por registro de venta confirmada
    @NamedQuery(name = "VntRemision.regVentaConf", query = "SELECT v FROM VntRemision v JOIN v.rgvtId r "
            + " WHERE r.rgvtEstconfirmada = :rgvtEstconfirmada AND v.eftId.eftId = :eftId ORDER BY r.rgvtFechaconfirma DESC"),
      @NamedQuery(name = "VntRemision.findByRegVenta", query = "SELECT v FROM VntRemision v JOIN v.rgvtId r "
            + " WHERE r.rgvtId = :rgvtId AND v.eftId.eftId <> :eftId ")
})
public class VntRemision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vrms_id")
    private Long vrmsId;
    @Size(max = 100)
    @Column(name = "vrms_numremision")
    private String vrmsNumremision;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vrms_anticipo")
    private BigDecimal vrmsAnticipo;
    @Column(name = "vrms_valortotal")
    private BigDecimal vrmsValortotal;
    @Size(max = 2147483647)
    @Column(name = "vrms_observacion")
    private String vrmsObservacion;
    @Column(name = "vrms_fecharemision")
    @Temporal(TemporalType.DATE)
    private Date vrmsFecharemision;
    @Column(name = "vrms_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrmsFechaproceso;
    @Column(name = "str_id")
    private Long strId;
    @OneToMany(mappedBy = "vrmsId")
    private List<VntDetallerem> vntDetalleremList;
    @JoinColumn(name = "rgvt_id", referencedColumnName = "rgvt_id")
    @ManyToOne
    private VntRegistroventa rgvtId;
    @JoinColumn(name = "eft_id", referencedColumnName = "eft_id")
    @ManyToOne
    private RfEstadofactura eftId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;
    
    @Column(name = "vrms_detalle")
    private String vrmsDetalle;
    
    @Column(name = "vrms_fechapago")
    @Temporal(TemporalType.DATE)
    private Date vrmsfechapago;

    @Column(name = "vrms_fecharadica")
    @Temporal(TemporalType.DATE)
    private Date vfctfecharadica;

    @Column(name = "vrms_numdiaspago")
    private Integer vrmsnumdiaspago;
    

       @Column(name = "vrms_protagonista")
    private String vrmsProtagonista;
    @Column(name = "vrms_fecha")
    @Temporal(TemporalType.DATE)
    private Date vrmsFecha;
    
    @Column(name = "vrms_direccionevento")
    private String vrmsDireccionevento;
 
    @Column(name = "vrms_motivo")
    private String vrmsMotivo;
 
    @Column(name = "vrms_hora")
    private String vrmsHora;
 
    @Column(name = "vrmscl_nombres")
    private String vrmsclNombres;
    @Size(max = 10)
    @Column(name = "vrmscl_tipodocuemento")
    private String vrmsclTipodocuemento;
    @Size(max = 25)
    @Column(name = "vrmscl_identificacion")
    private String vrmsclIdentificacion;
 
    @Column(name = "vrmscl_diereccion")
    private String vrmsclDiereccion;
 
    @Column(name = "vrmscl_celular")
    private String vrmsclCelular;
 
    @Column(name = "vrmscl_fijo")
    private String vrmsclFijo;
 
    @Column(name = "vrmscl_email")
    private String vrmsclEmail;
 
   
    @Column(name = "vrms_saldo")
    private BigDecimal vrmsSaldo;
    @Column(name = "vrms_descuento")
    private BigDecimal vrmsDescuento;

    public VntRemision() {
    }

    public VntRemision(Long vrmsId) {
        this.vrmsId = vrmsId;
    }

    public Long getVrmsId() {
        return vrmsId;
    }

    public void setVrmsId(Long vrmsId) {
        this.vrmsId = vrmsId;
    }

    public String getVrmsNumremision() {
        return vrmsNumremision;
    }

    public void setVrmsNumremision(String vrmsNumremision) {
        this.vrmsNumremision = vrmsNumremision;
    }

    public BigDecimal getVrmsAnticipo() {
        return vrmsAnticipo;
    }

    public void setVrmsAnticipo(BigDecimal vrmsAnticipo) {
        this.vrmsAnticipo = vrmsAnticipo;
    }

    public BigDecimal getVrmsValortotal() {
        return vrmsValortotal;
    }

    public void setVrmsValortotal(BigDecimal vrmsValortotal) {
        this.vrmsValortotal = vrmsValortotal;
    }

    public String getVrmsObservacion() {
        return vrmsObservacion;
    }

    public void setVrmsObservacion(String vrmsObservacion) {
        this.vrmsObservacion = vrmsObservacion;
    }

    public Date getVrmsFecharemision() {
        return vrmsFecharemision;
    }

    public void setVrmsFecharemision(Date vrmsFecharemision) {
        this.vrmsFecharemision = vrmsFecharemision;
    }

    public Date getVrmsFechaproceso() {
        return vrmsFechaproceso;
    }

    public void setVrmsFechaproceso(Date vrmsFechaproceso) {
        this.vrmsFechaproceso = vrmsFechaproceso;
    }

    public Long getStrId() {
        return strId;
    }

    public void setStrId(Long strId) {
        this.strId = strId;
    }

    @XmlTransient
    public List<VntDetallerem> getVntDetalleremList() {
        return vntDetalleremList;
    }

    public void setVntDetalleremList(List<VntDetallerem> vntDetalleremList) {
        this.vntDetalleremList = vntDetalleremList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vrmsId != null ? vrmsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntRemision)) {
            return false;
        }
        VntRemision other = (VntRemision) object;
        if ((this.vrmsId == null && other.vrmsId != null) || (this.vrmsId != null && !this.vrmsId.equals(other.vrmsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntRemision[ vrmsId=" + vrmsId + " ]";
    }

    public String getVrmsDetalle() {
        return vrmsDetalle;
    }

    public void setVrmsDetalle(String vrmsDetalle) {
        this.vrmsDetalle = vrmsDetalle;
    }

    public String getVrmsProtagonista() {
        return vrmsProtagonista;
    }

    public void setVrmsProtagonista(String vrmsProtagonista) {
        this.vrmsProtagonista = vrmsProtagonista;
    }

    public Date getVrmsFecha() {
        return vrmsFecha;
    }

    public void setVrmsFecha(Date vrmsFecha) {
        this.vrmsFecha = vrmsFecha;
    }

    public String getVrmsDireccionevento() {
        return vrmsDireccionevento;
    }

    public void setVrmsDireccionevento(String vrmsDireccionevento) {
        this.vrmsDireccionevento = vrmsDireccionevento;
    }

    public String getVrmsMotivo() {
        return vrmsMotivo;
    }

    public void setVrmsMotivo(String vrmsMotivo) {
        this.vrmsMotivo = vrmsMotivo;
    }

    public String getVrmsHora() {
        return vrmsHora;
    }

    public void setVrmsHora(String vrmsHora) {
        this.vrmsHora = vrmsHora;
    }

    public String getVrmsclNombres() {
        return vrmsclNombres;
    }

    public void setVrmsclNombres(String vrmsclNombres) {
        this.vrmsclNombres = vrmsclNombres;
    }

    public String getVrmsclTipodocuemento() {
        return vrmsclTipodocuemento;
    }

    public void setVrmsclTipodocuemento(String vrmsclTipodocuemento) {
        this.vrmsclTipodocuemento = vrmsclTipodocuemento;
    }

    public String getVrmsclIdentificacion() {
        return vrmsclIdentificacion;
    }

    public void setVrmsclIdentificacion(String vrmsclIdentificacion) {
        this.vrmsclIdentificacion = vrmsclIdentificacion;
    }

    public String getVrmsclDiereccion() {
        return vrmsclDiereccion;
    }

    public void setVrmsclDiereccion(String vrmsclDiereccion) {
        this.vrmsclDiereccion = vrmsclDiereccion;
    }

    public String getVrmsclCelular() {
        return vrmsclCelular;
    }

    public void setVrmsclCelular(String vrmsclCelular) {
        this.vrmsclCelular = vrmsclCelular;
    }

    public String getVrmsclFijo() {
        return vrmsclFijo;
    }

    public void setVrmsclFijo(String vrmsclFijo) {
        this.vrmsclFijo = vrmsclFijo;
    }

    public String getVrmsclEmail() {
        return vrmsclEmail;
    }

    public void setVrmsclEmail(String vrmsclEmail) {
        this.vrmsclEmail = vrmsclEmail;
    }



    public BigDecimal getVrmsSaldo() {
        return vrmsSaldo;
    }

    public void setVrmsSaldo(BigDecimal vrmsSaldo) {
        this.vrmsSaldo = vrmsSaldo;
    }

    public BigDecimal getVrmsDescuento() {
        return vrmsDescuento;
    }

    public void setVrmsDescuento(BigDecimal vrmsDescuento) {
        this.vrmsDescuento = vrmsDescuento;
    }

    /**
     * @return the vrmsfechapago
     */
    public Date getVrmsfechapago() {
        return vrmsfechapago;
    }

    /**
     * @param vrmsfechapago the vrmsfechapago to set
     */
    public void setVrmsfechapago(Date vrmsfechapago) {
        this.vrmsfechapago = vrmsfechapago;
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
     * @return the vrmsnumdiaspago
     */
    public Integer getVrmsnumdiaspago() {
        return vrmsnumdiaspago;
    }

    /**
     * @param vrmsnumdiaspago the vrmsnumdiaspago to set
     */
    public void setVrmsnumdiaspago(Integer vrmsnumdiaspago) {
        this.vrmsnumdiaspago = vrmsnumdiaspago;
    }
 
}
