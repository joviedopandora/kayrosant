/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 * @author breyner.robles
 */
@Entity
@Table(name = "rf_cargocontacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfCargocontacto.findAll", query = "SELECT r FROM RfCargocontacto r ORDER BY r.cargoDesc"),
    @NamedQuery(name = "RfCargocontacto.findByCargoId", query = "SELECT r FROM RfCargocontacto r WHERE r.cargoId = :cargoId"),
    @NamedQuery(name = "RfCargocontacto.findByCargoDesc", query = "SELECT r FROM RfCargocontacto r WHERE r.cargoDesc = :cargoDesc"),
    @NamedQuery(name = "RfCargocontacto.findByCargoEst", query = "SELECT r FROM RfCargocontacto r WHERE r.cargoEst = :cargoEst ORDER BY r.cargoDesc "),
    @NamedQuery(name = "RfCargocontacto.findByIndVerison", query = "SELECT r FROM RfCargocontacto r WHERE r.indVerison = :indVerison")})
public class RfCargocontacto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cargo_id")
    private Integer cargoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "cargo_desc")
    private String cargoDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cargo_est")
    private boolean cargoEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ind_verison")
    private int indVerison;
    @OneToMany(mappedBy = "rfCargocontacto", fetch = FetchType.LAZY)
    private List<VntDetallecliente> vntDetalleclienteList;

    public RfCargocontacto() {
    }

    public RfCargocontacto(Integer cargoId) {
        this.cargoId = cargoId;
    }

    public RfCargocontacto(Integer cargoId, String cargoDesc, boolean cargoEst, int indVerison) {
        this.cargoId = cargoId;
        this.cargoDesc = cargoDesc;
        this.cargoEst = cargoEst;
        this.indVerison = indVerison;
    }

    public Integer getCargoId() {
        return cargoId;
    }

    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoDesc() {
        return cargoDesc;
    }

    public void setCargoDesc(String cargoDesc) {
        this.cargoDesc = cargoDesc;
    }

    public boolean getCargoEst() {
        return cargoEst;
    }

    public void setCargoEst(boolean cargoEst) {
        this.cargoEst = cargoEst;
    }

    public int getIndVerison() {
        return indVerison;
    }

    public void setIndVerison(int indVerison) {
        this.indVerison = indVerison;
    }

    @XmlTransient
    public List<VntDetallecliente> getVntDetalleclienteList() {
        return vntDetalleclienteList;
    }

    public void setVntDetalleclienteList(List<VntDetallecliente> vntDetalleclienteList) {
        this.vntDetalleclienteList = vntDetalleclienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cargoId != null ? cargoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfCargocontacto)) {
            return false;
        }
        RfCargocontacto other = (RfCargocontacto) object;
        if ((this.cargoId == null && other.cargoId != null) || (this.cargoId != null && !this.cargoId.equals(other.cargoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.RfCargocontacto[ cargoId=" + cargoId + " ]";
    }
    
}
