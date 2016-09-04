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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "serv_rf_tipocliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServRfTipocliente.findByCliente", query = "SELECT s FROM ServRfTipocliente s WHERE s.vntRfTipocliente.tclId =:tclId AND s.servtcEst =:servtcEst  ORDER BY s.vntServicio.vsrvNombre"),
    @NamedQuery(name = "ServRfTipocliente.findByServicio", query = "SELECT s FROM ServRfTipocliente s WHERE s.vntServicio.vsrvId = :vsrvId"),
    @NamedQuery(name = "ServRfTipocliente.findAll", query = "SELECT s FROM ServRfTipocliente s"),
    @NamedQuery(name = "ServRfTipocliente.findByServtcId", query = "SELECT s FROM ServRfTipocliente s WHERE s.servtcId = :servtcId"),
    @NamedQuery(name = "ServRfTipocliente.findByServtcEst", query = "SELECT s FROM ServRfTipocliente s WHERE s.servtcEst = :servtcEst")})
public class ServRfTipocliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "servtc_id")
    private Long servtcId;
    @Column(name = "servtc_est")
    private Boolean servtcEst;
    @JoinColumn(name = "vsrv_id", referencedColumnName = "vsrv_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntServicio vntServicio;
    @JoinColumn(name = "tcl_id", referencedColumnName = "tcl_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntRfTipocliente vntRfTipocliente;

    public ServRfTipocliente() {
    }

    public ServRfTipocliente(Long servtcId) {
        this.servtcId = servtcId;
    }

    public Long getServtcId() {
        return servtcId;
    }

    public void setServtcId(Long servtcId) {
        this.servtcId = servtcId;
    }

    public Boolean getServtcEst() {
        return servtcEst;
    }

    public void setServtcEst(Boolean servtcEst) {
        this.servtcEst = servtcEst;
    }

    public VntServicio getVntServicio() {
        return vntServicio;
    }

    public void setVntServicio(VntServicio vntServicio) {
        this.vntServicio = vntServicio;
    }

    public VntRfTipocliente getVntRfTipocliente() {
        return vntRfTipocliente;
    }

    public void setVntRfTipocliente(VntRfTipocliente vntRfTipocliente) {
        this.vntRfTipocliente = vntRfTipocliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (servtcId != null ? servtcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServRfTipocliente)) {
            return false;
        }
        ServRfTipocliente other = (ServRfTipocliente) object;
        if ((this.servtcId == null && other.servtcId != null) || (this.servtcId != null && !this.servtcId.equals(other.servtcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wilsini.ServRfTipocliente[ servtcId=" + servtcId + " ]";
    }
    
}
