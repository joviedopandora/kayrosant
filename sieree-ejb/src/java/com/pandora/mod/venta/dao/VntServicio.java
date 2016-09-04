/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import com.pandora.mod.ordenprod.dao.PopServxop;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "vnt_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntServicio.findByArchivo", query = "SELECT v FROM VntServicio v WHERE v.vsrvArchivo IS NOT NULL AND v.vsrvEst=:vsrvEst ORDER BY v.vsrvNombre"),
    @NamedQuery(name = "VntServicio.findAll", query = "SELECT v FROM VntServicio v ORDER BY v.vsrvNombre ASC "),
    @NamedQuery(name = "VntServicio.findByVsrvId", query = "SELECT v FROM VntServicio v WHERE v.vsrvId = :vsrvId"),
    @NamedQuery(name = "VntServicio.findByVsrvNombre", query = "SELECT v FROM VntServicio v WHERE v.vsrvNombre = :vsrvNombre"),
    @NamedQuery(name = "VntServicio.findByVsrvDesc", query = "SELECT v FROM VntServicio v WHERE v.vsrvDesc = :vsrvDesc"),
    @NamedQuery(name = "VntServicio.findByVsrvValunitempresa", query = "SELECT v FROM VntServicio v WHERE v.vsrvValunitempresa = :vsrvValunitempresa"),
    @NamedQuery(name = "VntServicio.findByVsrvValunitcliente", query = "SELECT v FROM VntServicio v WHERE v.vsrvValunitcliente = :vsrvValunitcliente"),
    @NamedQuery(name = "VntServicio.findByVsrvEst", query = "SELECT v FROM VntServicio v WHERE v.vsrvEst = :vsrvEst ORDER BY v.vsrvNombre ASC"),
    @NamedQuery(name = "VntServicio.findByVsrvFechacre", query = "SELECT v FROM VntServicio v WHERE v.vsrvFechacre = :vsrvFechacre"),
    @NamedQuery(name = "VntServicio.findByVsrvObsr", query = "SELECT v FROM VntServicio v WHERE v.vsrvObsr = :vsrvObsr"),
    @NamedQuery(name = "VntServicio.servXCliente", query = "SELECT v FROM VntServicio v JOIN v.vntServxventaList sxv JOIN sxv.rgvtId r WHERE r.rgvtId = :rgvtId"),
   @NamedQuery(name = "VntServicio.servXServicoHijos", query = "SELECT v FROM VntServicio v WHERE v.vsrvEst = :vsrvEst AND  v.vsrvId NOT IN (SELECT b.vntServicioPadre.vsrvId FROM VntServicioxservicio b ) ORDER BY v.vsrvNombre ASC "),
   @NamedQuery(name = "VntServicio.servXServicoHijosSinId", query = "SELECT v FROM VntServicio v WHERE v.vsrvEst = :vsrvEst AND v.vsrvId <> :vsrvId AND  v.vsrvId NOT IN (SELECT b.vntServicioPadre.vsrvId FROM VntServicioxservicio b ) ORDER BY v.vsrvNombre ASC ")     
})
public class VntServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vsrv_id")
    private Long vsrvId;
    @Size(max = 500)
    @Column(name = "vsrv_nombre")
    private String vsrvNombre;
    @Size(max = 2147483647)
    @Column(name = "vsrv_desc")
    private String vsrvDesc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vsrv_valunitempresa")
    private BigDecimal vsrvValunitempresa;
    @Column(name = "vsrv_valunitcliente")
    private BigDecimal vsrvValunitcliente;
    @Column(name = "vsrv_est")
    private Boolean vsrvEst;

    @Column(name = "vsrv_porcentajeiva")
    private BigDecimal vsrvPorcentajeiva;
    @Column(name = "vsrv_valoriva")
    private BigDecimal vsrvValoriva;

    @Column(name = "vsrv_fechacre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vsrvFechacre;
    @Size(max = 2147483647)
    @Column(name = "vsrv_obsr")
    private String vsrvObsr;
    @JoinColumn(name = "tsrv_id", referencedColumnName = "tsrv_id")
    @ManyToOne
    private VntRfTiposervicio tsrvId;
    @OneToMany(mappedBy = "vsrvId")
    
  
    
    private List<VntServxventa> vntServxventaList;
    @OneToMany(mappedBy = "vsrvId")
    private List<VntProdxsrv> vntProdxsrvList;
    @OneToMany(mappedBy = "vsrvId")
    private List<PopServxop> popServxopList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vsrvId", fetch = FetchType.LAZY)
    private List<VntDetallefact> vntDetallefactList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vsrvId", fetch = FetchType.LAZY)
    private List<VntDetallerem> vntDetalleremList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vntServicio", fetch = FetchType.LAZY)
    public List<VntRangoedadsexxservicio> vntRangoedadsexxservicioList;
    @Column(name = "vsrv_archivo")
    private String vsrvArchivo;

    public List<VntDetallefact> getVntDetallefactList() {
        return vntDetallefactList;
    }

    public void setVntDetallefactList(List<VntDetallefact> vntDetallefactList) {
        this.vntDetallefactList = vntDetallefactList;
    }

    public VntServicio() {
    }

    public VntServicio(Long vsrvId) {
        this.vsrvId = vsrvId;
    }

    public Long getVsrvId() {
        return vsrvId;
    }

    public void setVsrvId(Long vsrvId) {
        this.vsrvId = vsrvId;
    }

    public String getVsrvNombre() {
        return vsrvNombre;
    }

    public void setVsrvNombre(String vsrvNombre) {
        this.vsrvNombre = vsrvNombre;
    }

    public String getVsrvDesc() {
        return vsrvDesc;
    }

    public void setVsrvDesc(String vsrvDesc) {
        this.vsrvDesc = vsrvDesc;
    }

    public BigDecimal getVsrvValunitempresa() {
        return vsrvValunitempresa;
    }

    public void setVsrvValunitempresa(BigDecimal vsrvValunitempresa) {
        this.vsrvValunitempresa = vsrvValunitempresa;
    }

    public BigDecimal getVsrvValunitcliente() {
        return vsrvValunitcliente;
    }

    public void setVsrvValunitcliente(BigDecimal vsrvValunitcliente) {
        this.vsrvValunitcliente = vsrvValunitcliente;
    }

    public Boolean getVsrvEst() {
        return vsrvEst;
    }

    public void setVsrvEst(Boolean vsrvEst) {
        this.vsrvEst = vsrvEst;
    }

    public Date getVsrvFechacre() {
        return vsrvFechacre;
    }

    public void setVsrvFechacre(Date vsrvFechacre) {
        this.vsrvFechacre = vsrvFechacre;
    }

    public String getVsrvObsr() {
        return vsrvObsr;
    }

    public void setVsrvObsr(String vsrvObsr) {
        this.vsrvObsr = vsrvObsr;
    }

    public VntRfTiposervicio getTsrvId() {
        return tsrvId;
    }

    public void setTsrvId(VntRfTiposervicio tsrvId) {
        this.tsrvId = tsrvId;
    }

    public List<VntProdxsrv> getVntProdxsrvList() {
        return vntProdxsrvList;
    }

    public void setVntProdxsrvList(List<VntProdxsrv> vntProdxsrvList) {
        this.vntProdxsrvList = vntProdxsrvList;
    }

    public List<PopServxop> getPopServxopList() {
        return popServxopList;
    }

    public void setPopServxopList(List<PopServxop> popServxopList) {
        this.popServxopList = popServxopList;
    }

    @XmlTransient
    public List<VntServxventa> getVntServxventaList() {
        return vntServxventaList;
    }

    public void setVntServxventaList(List<VntServxventa> vntServxventaList) {
        this.vntServxventaList = vntServxventaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vsrvId != null ? vsrvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntServicio)) {
            return false;
        }
        VntServicio other = (VntServicio) object;
        if ((this.vsrvId == null && other.vsrvId != null) || (this.vsrvId != null && !this.vsrvId.equals(other.vsrvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntServicio[ vsrvId=" + vsrvId + " ]";
    }

    /**
     * @return the vntDetalleremList
     */
    public List<VntDetallerem> getVntDetalleremList() {
        return vntDetalleremList;
    }

    /**
     * @param vntDetalleremList the vntDetalleremList to set
     */
    public void setVntDetalleremList(List<VntDetallerem> vntDetalleremList) {
        this.vntDetalleremList = vntDetalleremList;
    }

    /**
     * @return the vntRangoedadsexxservicioList
     */
    public List<VntRangoedadsexxservicio> getVntRangoedadsexxservicioList() {
        return vntRangoedadsexxservicioList;
    }

    /**
     * @param vntRangoedadsexxservicioList the vntRangoedadsexxservicioList to
     * set
     */
    public void setVntRangoedadsexxservicioList(List<VntRangoedadsexxservicio> vntRangoedadsexxservicioList) {
        this.vntRangoedadsexxservicioList = vntRangoedadsexxservicioList;
    }

    /**
     * @return the vsrvArchivo
     */
    public String getVsrvArchivo() {
        return vsrvArchivo;
    }

    /**
     * @param vsrvArchivo the vsrvArchivo to set
     */
    public void setVsrvArchivo(String vsrvArchivo) {
        this.vsrvArchivo = vsrvArchivo;
    }

    /**
     * @return the vsrvPorcentajeiva
     */
    public BigDecimal getVsrvPorcentajeiva() {
        return vsrvPorcentajeiva;
    }

    /**
     * @param vsrvPorcentajeiva the vsrvPorcentajeiva to set
     */
    public void setVsrvPorcentajeiva(BigDecimal vsrvPorcentajeiva) {
        this.vsrvPorcentajeiva = vsrvPorcentajeiva;
    }

    /**
     * @return the vsrvValoriva
     */
    public BigDecimal getVsrvValoriva() {
        return vsrvValoriva;
    }

    /**
     * @param vsrvValoriva the vsrvValoriva to set
     */
    public void setVsrvValoriva(BigDecimal vsrvValoriva) {
        this.vsrvValoriva = vsrvValoriva;
    }

   
}
