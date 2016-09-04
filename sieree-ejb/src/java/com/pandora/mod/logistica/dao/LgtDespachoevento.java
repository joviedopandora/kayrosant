/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.mod.logistica.dao;

import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author patricia
 */
@Entity
@Table(name = "lgt_despachoevento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LgtDespachoevento.findAll", query = "SELECT l FROM LgtDespachoevento l"),
    @NamedQuery(name = "LgtDespachoevento.findByDevId", query = "SELECT l FROM LgtDespachoevento l WHERE l.devId = :devId"),
    @NamedQuery(name = "LgtDespachoevento.findByDevFechaprocsalida", query = "SELECT l FROM LgtDespachoevento l WHERE l.devFechaprocsalida = :devFechaprocsalida"),
    @NamedQuery(name = "LgtDespachoevento.findByCxcIdregsalida", query = "SELECT l FROM LgtDespachoevento l WHERE l.cxcIdregsalida = :cxcIdregsalida"),
    @NamedQuery(name = "LgtDespachoevento.findByDevProcsalida", query = "SELECT l FROM LgtDespachoevento l WHERE l.devProcsalida = :devProcsalida"),
    @NamedQuery(name = "LgtDespachoevento.findByDevFechaprocentrada", query = "SELECT l FROM LgtDespachoevento l WHERE l.devFechaprocentrada = :devFechaprocentrada"),
    @NamedQuery(name = "LgtDespachoevento.findByCxcIdregentrada", query = "SELECT l FROM LgtDespachoevento l WHERE l.cxcIdregentrada = :cxcIdregentrada"),
    @NamedQuery(name = "LgtDespachoevento.findByDevProcentrada", query = "SELECT l FROM LgtDespachoevento l WHERE l.devProcentrada = :devProcentrada"),
    @NamedQuery(name = "LgtDespachoevento.findByDevEstado", query = "SELECT l FROM LgtDespachoevento l WHERE l.devEstado = :devEstado"),
    @NamedQuery(name = "LgtDespachoevento.findByStrId", query = "SELECT l FROM LgtDespachoevento l WHERE l.strId = :strId")})
public class LgtDespachoevento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dev_id")
    private Long devId;
    @Column(name = "dev_fechaprocsalida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date devFechaprocsalida;
    @Column(name = "cxc_idregsalida")
    private Integer cxcIdregsalida;
    @Column(name = "dev_procsalida")
    private Boolean devProcsalida;
    @Column(name = "dev_fechaprocentrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date devFechaprocentrada;
    @Column(name = "cxc_idregentrada")
    private Integer cxcIdregentrada;
    @Column(name = "dev_procentrada")
    private Boolean devProcentrada;
    @Column(name = "dev_estado")
    private Boolean devEstado;
    @Column(name = "str_id")
    private Long strId;
    @JoinColumn(name = "opr_id", referencedColumnName = "opr_id")
    @ManyToOne
    private PopOrdenprod oprId;

    public LgtDespachoevento() {
    }

    public LgtDespachoevento(Long devId) {
        this.devId = devId;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public Date getDevFechaprocsalida() {
        return devFechaprocsalida;
    }

    public void setDevFechaprocsalida(Date devFechaprocsalida) {
        this.devFechaprocsalida = devFechaprocsalida;
    }

    public Integer getCxcIdregsalida() {
        return cxcIdregsalida;
    }

    public void setCxcIdregsalida(Integer cxcIdregsalida) {
        this.cxcIdregsalida = cxcIdregsalida;
    }

    public Boolean getDevProcsalida() {
        return devProcsalida;
    }

    public void setDevProcsalida(Boolean devProcsalida) {
        this.devProcsalida = devProcsalida;
    }

    public Date getDevFechaprocentrada() {
        return devFechaprocentrada;
    }

    public void setDevFechaprocentrada(Date devFechaprocentrada) {
        this.devFechaprocentrada = devFechaprocentrada;
    }

    public Integer getCxcIdregentrada() {
        return cxcIdregentrada;
    }

    public void setCxcIdregentrada(Integer cxcIdregentrada) {
        this.cxcIdregentrada = cxcIdregentrada;
    }

    public Boolean getDevProcentrada() {
        return devProcentrada;
    }

    public void setDevProcentrada(Boolean devProcentrada) {
        this.devProcentrada = devProcentrada;
    }

    public Boolean getDevEstado() {
        return devEstado;
    }

    public void setDevEstado(Boolean devEstado) {
        this.devEstado = devEstado;
    }

    public Long getStrId() {
        return strId;
    }

    public void setStrId(Long strId) {
        this.strId = strId;
    }

    public PopOrdenprod getOprId() {
        return oprId;
    }

    public void setOprId(PopOrdenprod oprId) {
        this.oprId = oprId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (devId != null ? devId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LgtDespachoevento)) {
            return false;
        }
        LgtDespachoevento other = (LgtDespachoevento) object;
        if ((this.devId == null && other.devId != null) || (this.devId != null && !this.devId.equals(other.devId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.LgtDespachoevento[ devId=" + devId + " ]";
    }
    
}
