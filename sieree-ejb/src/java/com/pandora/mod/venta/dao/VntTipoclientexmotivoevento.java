/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.mod.venta.dao;

import com.pandora.adm.rf.dao.RfMotivoevento;
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
 * @author Javier
 */
@Entity
@Table(name = "vnt_tipoclientexmotivoevento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntTipoclientexmotivoevento.findAll", query = "SELECT v FROM VntTipoclientexmotivoevento v"),
    @NamedQuery(name = "VntTipoclientexmotivoevento.findByTpcxmId", query = "SELECT v FROM VntTipoclientexmotivoevento v WHERE v.tpcxmId = :tpcxmId"),
    @NamedQuery(name = "VntTipoclientexmotivoevento.findByTpcxmEstado", query = "SELECT v FROM VntTipoclientexmotivoevento v WHERE v.tpcxmEstado = :tpcxmEstado")})
public class VntTipoclientexmotivoevento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tpcxm_id")
    private Integer tpcxmId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tpcxm_estado")
    private boolean tpcxmEstado;
    @JoinColumn(name = "tcl_id", referencedColumnName = "tcl_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntRfTipocliente vntRfTipocliente;
    @JoinColumn(name = "mte_id", referencedColumnName = "mte_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RfMotivoevento rfMotivoevento;

    public VntTipoclientexmotivoevento() {
    }

    public VntTipoclientexmotivoevento(Integer tpcxmId) {
        this.tpcxmId = tpcxmId;
    }

    public VntTipoclientexmotivoevento(Integer tpcxmId, boolean tpcxmEstado) {
        this.tpcxmId = tpcxmId;
        this.tpcxmEstado = tpcxmEstado;
    }

    public Integer getTpcxmId() {
        return tpcxmId;
    }

    public void setTpcxmId(Integer tpcxmId) {
        this.tpcxmId = tpcxmId;
    }

    public boolean getTpcxmEstado() {
        return tpcxmEstado;
    }

    public void setTpcxmEstado(boolean tpcxmEstado) {
        this.tpcxmEstado = tpcxmEstado;
    }

    public VntRfTipocliente getVntRfTipocliente() {
        return vntRfTipocliente;
    }

    public void setVntRfTipocliente(VntRfTipocliente vntRfTipocliente) {
        this.vntRfTipocliente = vntRfTipocliente;
    }

    public RfMotivoevento getRfMotivoevento() {
        return rfMotivoevento;
    }

    public void setRfMotivoevento(RfMotivoevento rfMotivoevento) {
        this.rfMotivoevento = rfMotivoevento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpcxmId != null ? tpcxmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntTipoclientexmotivoevento)) {
            return false;
        }
        VntTipoclientexmotivoevento other = (VntTipoclientexmotivoevento) object;
        if ((this.tpcxmId == null && other.tpcxmId != null) || (this.tpcxmId != null && !this.tpcxmId.equals(other.tpcxmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.VntTipoclientexmotivoevento[ tpcxmId=" + tpcxmId + " ]";
    }
    
}
