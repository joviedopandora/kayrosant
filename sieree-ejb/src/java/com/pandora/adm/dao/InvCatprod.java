/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "inv_catprod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvCatprod.findAll", query = "SELECT i FROM InvCatprod i ORDER BY i.cpdNombre"),
    @NamedQuery(name = "InvCatprod.findByCpdId", query = "SELECT i FROM InvCatprod i WHERE i.cpdId = :cpdId"),
    @NamedQuery(name = "InvCatprod.findByCpdNombre", query = "SELECT i FROM InvCatprod i WHERE i.cpdNombre = :cpdNombre"),
    @NamedQuery(name = "InvCatprod.findByCpdDesc", query = "SELECT i FROM InvCatprod i WHERE i.cpdDesc = :cpdDesc"),
    @NamedQuery(name = "InvCatprod.findByCpdEst", query = "SELECT i FROM InvCatprod i WHERE i.cpdEst = :cpdEst ORDER BY i.cpdNombre")})
public class InvCatprod implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cpd_id")
    private Integer cpdId;
    @Size(max = 200)
    @Column(name = "cpd_nombre")
    private String cpdNombre;
    @Size(max = 2147483647)
    @Column(name = "cpd_desc")
    private String cpdDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cpd_est")
    private boolean cpdEst;
    @OneToMany(mappedBy = "cpdId")
    private List<InvProducto> invProductoList;

    public InvCatprod() {
    }

    public InvCatprod(Integer cpdId) {
        this.cpdId = cpdId;
    }

    public InvCatprod(Integer cpdId, boolean cpdEst) {
        this.cpdId = cpdId;
        this.cpdEst = cpdEst;
    }

    public Integer getCpdId() {
        return cpdId;
    }

    public void setCpdId(Integer cpdId) {
        this.cpdId = cpdId;
    }

    public String getCpdNombre() {
        return cpdNombre;
    }

    public void setCpdNombre(String cpdNombre) {
        this.cpdNombre = cpdNombre;
    }

    public String getCpdDesc() {
        return cpdDesc;
    }

    public void setCpdDesc(String cpdDesc) {
        this.cpdDesc = cpdDesc;
    }

    public boolean getCpdEst() {
        return cpdEst;
    }

    public void setCpdEst(boolean cpdEst) {
        this.cpdEst = cpdEst;
    }

    @XmlTransient
    public List<InvProducto> getInvProductoList() {
        return invProductoList;
    }

    public void setInvProductoList(List<InvProducto> invProductoList) {
        this.invProductoList = invProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpdId != null ? cpdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvCatprod)) {
            return false;
        }
        InvCatprod other = (InvCatprod) object;
        if ((this.cpdId == null && other.cpdId != null) || (this.cpdId != null && !this.cpdId.equals(other.cpdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.InvCatprod[ cpdId=" + cpdId + " ]";
    }
    
}
