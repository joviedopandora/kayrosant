/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "vnt_servicioxservicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntServicioxservicio.findByServiciosAsociados", query = "SELECT v FROM VntServicioxservicio v WHERE v.vntServicioPadre.vsrvId = :vsrvId  ORDER BY v.vntServicioHijo.vsrvNombre  "),
    @NamedQuery(name = "VntServicioxservicio.findByServicios", query = "SELECT v FROM VntServicioxservicio v WHERE v.vntServicioPadre.vsrvId = :vsrvId AND v.vsvxsEstado = :vsvxsEstado ORDER BY v.vntServicioHijo.vsrvNombre  "),
    @NamedQuery(name = "VntServicioxservicio.findAll", query = "SELECT v FROM VntServicioxservicio v"),
    @NamedQuery(name = "VntServicioxservicio.findByVsrvxsId", query = "SELECT v FROM VntServicioxservicio v WHERE v.vsrvxsId = :vsrvxsId"),
    @NamedQuery(name = "VntServicioxservicio.findByVsvxsEstado", query = "SELECT v FROM VntServicioxservicio v WHERE v.vsvxsEstado = :vsvxsEstado")})
public class VntServicioxservicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vsrvxs_id")
    private Long vsrvxsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vsvxs_estado")
    private boolean vsvxsEstado;
    @JoinColumn(name = "vsrv_hijo", referencedColumnName = "vsrv_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VntServicio vntServicioHijo;
    @JoinColumn(name = "vsrv_padre", referencedColumnName = "vsrv_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VntServicio vntServicioPadre;

    public VntServicioxservicio() {
    }

    public VntServicioxservicio(Long vsrvxsId) {
        this.vsrvxsId = vsrvxsId;
    }

    public VntServicioxservicio(Long vsrvxsId, boolean vsvxsEstado) {
        this.vsrvxsId = vsrvxsId;
        this.vsvxsEstado = vsvxsEstado;
    }

    public Long getVsrvxsId() {
        return vsrvxsId;
    }

    public void setVsrvxsId(Long vsrvxsId) {
        this.vsrvxsId = vsrvxsId;
    }

    public boolean getVsvxsEstado() {
        return vsvxsEstado;
    }

    public void setVsvxsEstado(boolean vsvxsEstado) {
        this.vsvxsEstado = vsvxsEstado;
    }

    public VntServicio getVntServicioHijo() {
        return vntServicioHijo;
    }

    public void setVntServicioHijo(VntServicio vntServicioHijo) {
        this.vntServicioHijo = vntServicioHijo;
    }

    public VntServicio getVntServicioPadre() {
        return vntServicioPadre;
    }

    public void setVntServicioPadre(VntServicio vntServicioPadre) {
        this.vntServicioPadre = vntServicioPadre;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vsrvxsId != null ? vsrvxsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
       
        if (!(object instanceof VntServicioxservicio)) {
            return false;
        }
        VntServicioxservicio other = (VntServicioxservicio) object;
        if(this == null  ){
            return false;
        }
        if(other == null  ){
            return false;
        }
        if(this.vntServicioHijo == null ||  other.vntServicioHijo == null){
             return true;
        }
        if(this.vntServicioHijo.getVsrvId() == null ||  other.vntServicioHijo.getVsrvId() == null){
             return true;
        }
        return (this.vntServicioHijo.getVsrvId().equals( other.vntServicioHijo.getVsrvId()));
            
      //  return !((this.vsrvxsId == null && other.vsrvxsId != null) || (this.vsrvxsId != null && !this.vsrvxsId.equals(other.vsrvxsId)));
    }

    @Override
    public String toString() {
        return "com.VntServicioxservicio[ vsrvxsId=" + vsrvxsId + " ]";
    }
    
}
