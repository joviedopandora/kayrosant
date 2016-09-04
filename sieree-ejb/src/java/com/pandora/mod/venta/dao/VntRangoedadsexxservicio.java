/*
 * To change this template, choose Tools | Templates
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
 * @author Breyner Robles
 */
@Entity
@Table(name = "vnt_rangoedadsexxservicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntRangoedadsexxservicio.findByVntRangoedadsexo", query = "SELECT v FROM VntRangoedadsexxservicio v WHERE v.vntRangoedadsexo.rgedadsexId = :rgedadsexId  AND v.vntServicio.vsrvArchivo IS NOT NULL AND v.vntServicio.vsrvEst=:vsrvEst ORDER BY v.vntServicio.vsrvNombre"),
    @NamedQuery(name = "VntRangoedadsexxservicio.findAll", query = "SELECT v FROM VntRangoedadsexxservicio v"),
    @NamedQuery(name = "VntRangoedadsexxservicio.findByRngsId", query = "SELECT v FROM VntRangoedadsexxservicio v WHERE v.rngsId = :rngsId"),
    @NamedQuery(name = "VntRangoedadsexxservicio.findByRngsEstado", query = "SELECT v FROM VntRangoedadsexxservicio v WHERE v.rngsEstado = :rngsEstado")})
public class VntRangoedadsexxservicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rngs_id")
    private Integer rngsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rngs_estado")
    private boolean rngsEstado;
    @JoinColumn(name = "vsrv_id", referencedColumnName = "vsrv_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntServicio vntServicio;
    @JoinColumn(name = "rgedadsex_id", referencedColumnName = "rgedadsex_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntRangoedadsexo vntRangoedadsexo;

    public VntRangoedadsexxservicio() {
    }

    public VntRangoedadsexxservicio(Integer rngsId) {
        this.rngsId = rngsId;
    }

    public VntRangoedadsexxservicio(Integer rngsId, boolean rngsEstado) {
        this.rngsId = rngsId;
        this.rngsEstado = rngsEstado;
    }

    public Integer getRngsId() {
        return rngsId;
    }

    public void setRngsId(Integer rngsId) {
        this.rngsId = rngsId;
    }

    public boolean getRngsEstado() {
        return rngsEstado;
    }

    public void setRngsEstado(boolean rngsEstado) {
        this.rngsEstado = rngsEstado;
    }

    public VntServicio getVntServicio() {
        return vntServicio;
    }

    public void setVntServicio(VntServicio vntServicio) {
        this.vntServicio = vntServicio;
    }

    public VntRangoedadsexo getVntRangoedadsexo() {
        return vntRangoedadsexo;
    }

    public void setVntRangoedadsexo(VntRangoedadsexo vntRangoedadsexo) {
        this.vntRangoedadsexo = vntRangoedadsexo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rngsId != null ? rngsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntRangoedadsexxservicio)) {
            return false;
        }
        VntRangoedadsexxservicio other = (VntRangoedadsexxservicio) object;
        if ((this.rngsId == null && other.rngsId != null) || (this.rngsId != null && !this.rngsId.equals(other.rngsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.VntRangoedadsexxservicio[ rngsId=" + rngsId + " ]";
    }
}
