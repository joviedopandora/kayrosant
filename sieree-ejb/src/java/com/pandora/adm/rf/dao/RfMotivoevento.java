/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.rf.dao;

import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.mod.venta.dao.VntRegistroLlamada;
import com.pandora.mod.venta.dao.VntTipoclientexmotivoevento;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sandra
 */
@Entity
@Table(name = "rf_motivoevento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfMotivoevento.findAll", query = "SELECT r FROM RfMotivoevento r"),
    @NamedQuery(name = "RfMotivoevento.findByMteId", query = "SELECT r FROM RfMotivoevento r WHERE r.mteId = :mteId"),
    @NamedQuery(name = "RfMotivoevento.findByMteNombre", query = "SELECT r FROM RfMotivoevento r WHERE r.mteNombre = :mteNombre"),
    @NamedQuery(name = "RfMotivoevento.findByMteDescripcion", query = "SELECT r FROM RfMotivoevento r WHERE r.mteDescripcion = :mteDescripcion"),
    @NamedQuery(name = "RfMotivoevento.findByMteEstado", query = "SELECT r FROM RfMotivoevento r WHERE r.mteEstado = :mteEstado ORDER BY r.mteNombre"),
    @NamedQuery(name = "RfMotivoevento.findByIndversion", query = "SELECT r FROM RfMotivoevento r WHERE r.indversion = :indversion"),
    @NamedQuery(name = "RfMotivoevento.findByMteEstadoXTipoCliente", query = "SELECT r FROM RfMotivoevento r  JOIN r.vntTipoclientexmotivoeventoList mv WHERE r.mteEstado = :mteEstado AND mv.tpcxmEstado = :tpcxmEstado AND mv.vntRfTipocliente.tclId =:tclId  ORDER BY r.mteNombre")})
public class RfMotivoevento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mte_id")
    private Integer mteId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "mte_nombre")
    private String mteNombre;
    @Size(max = 2147483647)
    @Column(name = "mte_descripcion")
    private String mteDescripcion;
    @Column(name = "mte_estado")
    private Boolean mteEstado;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(mappedBy = "mteId")
    private List<VntDetevento> vntDeteventoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rfMotivoevento", fetch = FetchType.LAZY)
    private List<VntTipoclientexmotivoevento> vntTipoclientexmotivoeventoList;
    @OneToMany(mappedBy = "rfMotivoevento", fetch = FetchType.LAZY)
    private List<VntRegistroLlamada> vntRegistroLlamadaList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mte_defecto")
    private boolean mteDefecto;

    public RfMotivoevento() {
    }

    public RfMotivoevento(Integer mteId) {
        this.mteId = mteId;
    }

    public RfMotivoevento(Integer mteId, String mteNombre) {
        this.mteId = mteId;
        this.mteNombre = mteNombre;
    }

    public Integer getMteId() {
        return mteId;
    }

    public void setMteId(Integer mteId) {
        this.mteId = mteId;
    }

    public String getMteNombre() {
        return mteNombre;
    }

    public void setMteNombre(String mteNombre) {
        this.mteNombre = mteNombre;
    }

    public String getMteDescripcion() {
        return mteDescripcion;
    }

    public void setMteDescripcion(String mteDescripcion) {
        this.mteDescripcion = mteDescripcion;
    }

    public Boolean getMteEstado() {
        return mteEstado;
    }

    public void setMteEstado(Boolean mteEstado) {
        this.mteEstado = mteEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<VntDetevento> getVntDeteventoList() {
        return vntDeteventoList;
    }

    public void setVntDeteventoList(List<VntDetevento> vntDeteventoList) {
        this.vntDeteventoList = vntDeteventoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mteId != null ? mteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfMotivoevento)) {
            return false;
        }
        RfMotivoevento other = (RfMotivoevento) object;
        if ((this.mteId == null && other.mteId != null) || (this.mteId != null && !this.mteId.equals(other.mteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.RfMotivoevento[ mteId=" + mteId + " ]";
    }

    /**
     * @return the vntTipoclientexmotivoeventoList
     */
    public List<VntTipoclientexmotivoevento> getVntTipoclientexmotivoeventoList() {
        return vntTipoclientexmotivoeventoList;
    }

    /**
     * @param vntTipoclientexmotivoeventoList the
     * vntTipoclientexmotivoeventoList to set
     */
    public void setVntTipoclientexmotivoeventoList(List<VntTipoclientexmotivoevento> vntTipoclientexmotivoeventoList) {
        this.vntTipoclientexmotivoeventoList = vntTipoclientexmotivoeventoList;
    }

    /**
     * @return the vntRegistroLlamadaList
     */
    public List<VntRegistroLlamada> getVntRegistroLlamadaList() {
        return vntRegistroLlamadaList;
    }

    /**
     * @param vntRegistroLlamadaList the vntRegistroLlamadaList to set
     */
    public void setVntRegistroLlamadaList(List<VntRegistroLlamada> vntRegistroLlamadaList) {
        this.vntRegistroLlamadaList = vntRegistroLlamadaList;
    }

    /**
     * @return the mteDefecto
     */
    public boolean isMteDefecto() {
        return mteDefecto;
    }

    /**
     * @param mteDefecto the mteDefecto to set
     */
    public void setMteDefecto(boolean mteDefecto) {
        this.mteDefecto = mteDefecto;
    }
}
