/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.adm.rf.dao.RfMotivoevento;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "vnt_detevento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntDetevento.findByDetalleCliente", query = "SELECT v FROM VntDetevento v WHERE v.dclnId.dclnId=:dclnId ORDER BY v.rgvtId.rgvtFechacre DESC"),    
    @NamedQuery(name = "VntDetevento.findByVentaConOrdenProduccion", query = "SELECT v FROM VntDetevento v JOIN v.rgvtId rv "
            + " WHERE rv.rgvtIdordenprod IS NOT NULL AND v.vdeFechaevt >= :fechaInicial AND v.vdeFechaevt <= :fechaFinal ORDER BY v.vdeFechaevt ASC "),
    
    @NamedQuery(name = "VntDetevento.findAll", query = "SELECT v FROM VntDetevento v"),
      @NamedQuery(name = "VntDetevento.findListaVentas", query = "SELECT v FROM VntDetevento v WHERE v.rgvtId.clnId.tclId.tclId= :tclId  ORDER BY v.rgvtId.rgvtId DESC"),
    
    @NamedQuery(name = "VntDetevento.findByVdeId", query = "SELECT v FROM VntDetevento v WHERE v.vdeId = :vdeId"),
    @NamedQuery(name = "VntDetevento.findByVdeDireccionevt", query = "SELECT v FROM VntDetevento v WHERE v.vdeDireccionevt = :vdeDireccionevt"),
    @NamedQuery(name = "VntDetevento.findByVdeLatitud", query = "SELECT v FROM VntDetevento v WHERE v.vdeLatitud = :vdeLatitud"),
    @NamedQuery(name = "VntDetevento.findByVdeLongitud", query = "SELECT v FROM VntDetevento v WHERE v.vdeLongitud = :vdeLongitud"),
    @NamedQuery(name = "VntDetevento.findByVdeTelefono1", query = "SELECT v FROM VntDetevento v WHERE v.vdeTelefono1 = :vdeTelefono1"),
    @NamedQuery(name = "VntDetevento.findByVdeTelefono2", query = "SELECT v FROM VntDetevento v WHERE v.vdeTelefono2 = :vdeTelefono2"),
    @NamedQuery(name = "VntDetevento.findByVdeTelefono3", query = "SELECT v FROM VntDetevento v WHERE v.vdeTelefono3 = :vdeTelefono3"),
    @NamedQuery(name = "VntDetevento.findByVdeCelular1", query = "SELECT v FROM VntDetevento v WHERE v.vdeCelular1 = :vdeCelular1"),
    @NamedQuery(name = "VntDetevento.findByVdeCelular2", query = "SELECT v FROM VntDetevento v WHERE v.vdeCelular2 = :vdeCelular2"),
    @NamedQuery(name = "VntDetevento.findByVdeNombrescontacto", query = "SELECT v FROM VntDetevento v WHERE v.vdeNombrescontacto = :vdeNombrescontacto"),
    @NamedQuery(name = "VntDetevento.findByVdeApellidoscontacto", query = "SELECT v FROM VntDetevento v WHERE v.vdeApellidoscontacto = :vdeApellidoscontacto"),
    @NamedQuery(name = "VntDetevento.findByVdeObsr", query = "SELECT v FROM VntDetevento v WHERE v.vdeObsr = :vdeObsr"),
    @NamedQuery(name = "VntDetevento.detEvtXVenta", query = "SELECT v FROM VntDetevento v WHERE v.rgvtId.rgvtId = :rgvtId")
})
public class VntDetevento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vde_id")
    private Long vdeId;
    @Size(max = 2500)
    @Column(name = "vde_direccionevt")
    private String vdeDireccionevt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vde_latitud")
    private BigDecimal vdeLatitud;
    @Column(name = "vde_longitud")
    private BigDecimal vdeLongitud;
    @Size(max = 25)
    @Column(name = "vde_telefono1")
    private String vdeTelefono1;
    @Size(max = 25)
    @Column(name = "vde_telefono2")
    private String vdeTelefono2;
    @Size(max = 25)
    @Column(name = "vde_telefono3")
    private String vdeTelefono3;
    
    @Column(name = "vde_numero_dias")
    private Integer vdeNumdias;
    @Size(max = 25)
    @Column(name = "vde_celular1")
    private String vdeCelular1;
    @Size(max = 25)
    @Column(name = "vde_celular2")
    private String vdeCelular2;
    @Size(max = 200)
    @Column(name = "vde_nombrescontacto")
    private String vdeNombrescontacto;
    @Size(max = 200)
    @Column(name = "vde_apellidoscontacto")
    private String vdeApellidoscontacto;
    @Size(max = 2147483647)
    @Column(name = "vde_obsr")
    private String vdeObsr;
    @Column(name = "vde_fechaevt")
    @Temporal(TemporalType.DATE)
    private Date vdeFechaevt;
    @Column(name = "vde_horainicio")
    @Temporal(TemporalType.TIME)
    private Date vdeHorainicio;
    @Column(name = "vde_horafinal")
    @Temporal(TemporalType.TIME)
    private Date vdeHorafinal;
    @JoinColumn(name = "rgvt_id", referencedColumnName = "rgvt_id")
    @OneToOne
    private VntRegistroventa rgvtId;
    @JoinColumn(name = "dcln_id", referencedColumnName = "dcln_id")
    @OneToOne
    private VntDetallecliente dclnId;
    @JoinColumn(name = "ciu_id", referencedColumnName = "ciu_id")
    @ManyToOne
    private RfCiudad ciuId;
    @JoinColumn(name = "mte_id", referencedColumnName = "mte_id")
    @ManyToOne
    private RfMotivoevento mteId;

    public VntDetevento() {
    }

    public VntDetevento(Long vdeId) {
        this.vdeId = vdeId;
    }

    public Long getVdeId() {
        return vdeId;
    }

    public void setVdeId(Long vdeId) {
        this.vdeId = vdeId;
    }

    public String getVdeDireccionevt() {
        return vdeDireccionevt;
    }

    public void setVdeDireccionevt(String vdeDireccionevt) {
        this.vdeDireccionevt = vdeDireccionevt;
    }

    public BigDecimal getVdeLatitud() {
        return vdeLatitud;
    }

    public void setVdeLatitud(BigDecimal vdeLatitud) {
        this.vdeLatitud = vdeLatitud;
    }

    public BigDecimal getVdeLongitud() {
        return vdeLongitud;
    }

    public void setVdeLongitud(BigDecimal vdeLongitud) {
        this.vdeLongitud = vdeLongitud;
    }

    public String getVdeTelefono1() {
        return vdeTelefono1;
    }

    public void setVdeTelefono1(String vdeTelefono1) {
        this.vdeTelefono1 = vdeTelefono1;
    }

    public String getVdeTelefono2() {
        return vdeTelefono2;
    }

    public void setVdeTelefono2(String vdeTelefono2) {
        this.vdeTelefono2 = vdeTelefono2;
    }

    public String getVdeTelefono3() {
        return vdeTelefono3;
    }

    public void setVdeTelefono3(String vdeTelefono3) {
        this.vdeTelefono3 = vdeTelefono3;
    }

    public String getVdeCelular1() {
        return vdeCelular1;
    }

    public void setVdeCelular1(String vdeCelular1) {
        this.vdeCelular1 = vdeCelular1;
    }

    public String getVdeCelular2() {
        return vdeCelular2;
    }

    public void setVdeCelular2(String vdeCelular2) {
        this.vdeCelular2 = vdeCelular2;
    }

    public String getVdeNombrescontacto() {
        return vdeNombrescontacto;
    }

    public void setVdeNombrescontacto(String vdeNombrescontacto) {
        this.vdeNombrescontacto = vdeNombrescontacto;
    }

    public String getVdeApellidoscontacto() {
        return vdeApellidoscontacto;
    }

    public void setVdeApellidoscontacto(String vdeApellidoscontacto) {
        this.vdeApellidoscontacto = vdeApellidoscontacto;
    }

    public String getVdeObsr() {
        return vdeObsr;
    }

    public void setVdeObsr(String vdeObsr) {
        this.vdeObsr = vdeObsr;
    }

    public VntRegistroventa getRgvtId() {
        return rgvtId;
    }

    public void setRgvtId(VntRegistroventa rgvtId) {
        this.rgvtId = rgvtId;
    }

    public RfCiudad getCiuId() {
        return ciuId;
    }

    public void setCiuId(RfCiudad ciuId) {
        this.ciuId = ciuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vdeId != null ? vdeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntDetevento)) {
            return false;
        }
        VntDetevento other = (VntDetevento) object;
        if ((this.vdeId == null && other.vdeId != null) || (this.vdeId != null && !this.vdeId.equals(other.vdeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntDetevento[ vdeId=" + vdeId + " ]";
    }

    /**
     * @return the vdeFechaevt
     */
    public Date getVdeFechaevt() {
        return vdeFechaevt;
    }

    /**
     * @param vdeFechaevt the vdeFechaevt to set
     */
    public void setVdeFechaevt(Date vdeFechaevt) {
        this.vdeFechaevt = vdeFechaevt;
    }

    /**
     * @return the vdeHorainicio
     */
    public Date getVdeHorainicio() {
        return vdeHorainicio;
    }

    /**
     * @param vdeHorainicio the vdeHorainicio to set
     */
    public void setVdeHorainicio(Date vdeHorainicio) {
        this.vdeHorainicio = vdeHorainicio;
    }

    /**
     * @return the vdeHorafinal
     */
    public Date getVdeHorafinal() {
        return vdeHorafinal;
    }

    /**
     * @param vdeHorafinal the vdeHorafinal to set
     */
    public void setVdeHorafinal(Date vdeHorafinal) {
        this.vdeHorafinal = vdeHorafinal;
    }

    /**
     * @return the dclnId
     */
    public VntDetallecliente getDclnId() {
        return dclnId;
    }

    /**
     * @param dclnId the dclnId to set
     */
    public void setDclnId(VntDetallecliente dclnId) {
        this.dclnId = dclnId;
    }

    /**
     * @return the mteId
     */
    public RfMotivoevento getMteId() {
        return mteId;
    }

    /**
     * @param mteId the mteId to set
     */
    public void setMteId(RfMotivoevento mteId) {
        this.mteId = mteId;
    }

    /**
     * @return the vdeNumdias
     */
    public Integer getVdeNumdias() {
        return vdeNumdias;
    }

    /**
     * @param vdeNumdias the vdeNumdias to set
     */
    public void setVdeNumdias(Integer vdeNumdias) {
        this.vdeNumdias = vdeNumdias;
    }

}
