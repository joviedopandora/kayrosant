/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.ordenprod.dao;

import com.pandora.mod.venta.dao.VntServicio;
import java.io.Serializable;
import java.math.BigInteger;
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
 * @author luis
 */
@Entity
@Table(name = "pop_servxop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PopServxop.findAll", query = "SELECT p FROM PopServxop p"),
    @NamedQuery(name = "PopServxop.findBySxoId", query = "SELECT p FROM PopServxop p WHERE p.sxoId = :sxoId"),
    @NamedQuery(name = "PopServxop.findBySxoObservacion", query = "SELECT p FROM PopServxop p WHERE p.sxoObservacion = :sxoObservacion"),
    @NamedQuery(name = "PopServxop.findBySxoFechaproceso", query = "SELECT p FROM PopServxop p WHERE p.sxoFechaproceso = :sxoFechaproceso"),
    @NamedQuery(name = "PopServxop.findBySxoEstado", query = "SELECT p FROM PopServxop p WHERE p.sxoEstado = :sxoEstado"),
    @NamedQuery(name = "PopServxop.findByIndversion", query = "SELECT p FROM PopServxop p WHERE p.indversion = :indversion"),
    @NamedQuery(name = "PopServxop.findByStrId", query = "SELECT p FROM PopServxop p WHERE p.strId = :strId"),
    //Lista de servicios por orden de producción
    @NamedQuery(name = "PopServxop.ordenProd", query = "SELECT p FROM PopServxop p JOIN p.oprId o WHERE o.oprId = :oprId")
})
public class PopServxop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sxo_id")
    private Long sxoId;
    @Size(max = 2147483647)
    @Column(name = "sxo_observacion")
    private String sxoObservacion;
    @Column(name = "sxo_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sxoFechaproceso;
    @Column(name = "sxo_estado")
    private Boolean sxoEstado;
    @Column(name = "indversion")
    private Integer indversion;
    @Column(name = "str_id")
    private Long strId;
    @Column(name = "sxo_cantsrv")
    private Integer sxoCantsrv;
    @JoinColumn(name = "vsrv_id", referencedColumnName = "vsrv_id")
    @ManyToOne
    private VntServicio vsrvId;
    @JoinColumn(name = "opr_id", referencedColumnName = "opr_id")
    @ManyToOne
    private PopOrdenprod oprId;
    @OneToMany(mappedBy = "sxoId")
    private List<PopProdxservxop> popProdxservxopList;

    public PopServxop() {
    }

    public PopServxop(Long sxoId) {
        this.sxoId = sxoId;
    }

    public Long getSxoId() {
        return sxoId;
    }

    public void setSxoId(Long sxoId) {
        this.sxoId = sxoId;
    }

    public String getSxoObservacion() {
        return sxoObservacion;
    }

    public void setSxoObservacion(String sxoObservacion) {
        this.sxoObservacion = sxoObservacion;
    }

    public Date getSxoFechaproceso() {
        return sxoFechaproceso;
    }

    public void setSxoFechaproceso(Date sxoFechaproceso) {
        this.sxoFechaproceso = sxoFechaproceso;
    }

    public Boolean getSxoEstado() {
        return sxoEstado;
    }

    public void setSxoEstado(Boolean sxoEstado) {
        this.sxoEstado = sxoEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public Long getStrId() {
        return strId;
    }

    public void setStrId(Long strId) {
        this.strId = strId;
    }

    public Integer getSxoCantsrv() {
        return sxoCantsrv;
    }

    public void setSxoCantsrv(Integer sxoCantsrv) {
        this.sxoCantsrv = sxoCantsrv;
    }
    
    public VntServicio getVsrvId() {
        return vsrvId;
    }

    public void setVsrvId(VntServicio vsrvId) {
        this.vsrvId = vsrvId;
    }

    public PopOrdenprod getOprId() {
        return oprId;
    }

    public void setOprId(PopOrdenprod oprId) {
        this.oprId = oprId;
    }

    @XmlTransient
    public List<PopProdxservxop> getPopProdxservxopList() {
        return popProdxservxopList;
    }

    public void setPopProdxservxopList(List<PopProdxservxop> popProdxservxopList) {
        this.popProdxservxopList = popProdxservxopList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sxoId != null ? sxoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PopServxop)) {
            return false;
        }
        PopServxop other = (PopServxop) object;
        if ((this.sxoId == null && other.sxoId != null) || (this.sxoId != null && !this.sxoId.equals(other.sxoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.PopServxop[ sxoId=" + sxoId + " ]";
    }
}
