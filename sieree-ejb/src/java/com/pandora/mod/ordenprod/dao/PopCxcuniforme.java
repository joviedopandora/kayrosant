/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.ordenprod.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "pop_cxcuniforme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PopCxcuniforme.findAll", query = "SELECT p FROM PopCxcuniforme p"),
    @NamedQuery(name = "PopCxcuniforme.findByCxuId", query = "SELECT p FROM PopCxcuniforme p WHERE p.cxuId = :cxuId"),
    @NamedQuery(name = "PopCxcuniforme.findByCxuUniforme", query = "SELECT p FROM PopCxcuniforme p WHERE p.cxuUniforme = :cxuUniforme"),
    @NamedQuery(name = "PopCxcuniforme.findByCxuEstado", query = "SELECT p FROM PopCxcuniforme p WHERE p.cxuEstado = :cxuEstado ORDER BY p.cxuUniforme")})
public class PopCxcuniforme implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cxu_id")
    private Integer cxuId;
    @Size(max = 2147483647)
    @Column(name = "cxu_uniforme")
    private String cxuUniforme;
    @Column(name = "cxu_estado")
    private Boolean cxuEstado;

    public PopCxcuniforme() {
    }

    public PopCxcuniforme(Integer cxuId) {
        this.cxuId = cxuId;
    }

    public Integer getCxuId() {
        return cxuId;
    }

    public void setCxuId(Integer cxuId) {
        this.cxuId = cxuId;
    }

    public String getCxuUniforme() {
        return cxuUniforme;
    }

    public void setCxuUniforme(String cxuUniforme) {
        this.cxuUniforme = cxuUniforme;
    }

    public Boolean getCxuEstado() {
        return cxuEstado;
    }

    public void setCxuEstado(Boolean cxuEstado) {
        this.cxuEstado = cxuEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxuId != null ? cxuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PopCxcuniforme)) {
            return false;
        }
        PopCxcuniforme other = (PopCxcuniforme) object;
        if ((this.cxuId == null && other.cxuId != null) || (this.cxuId != null && !this.cxuId.equals(other.cxuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "voo.PopCxcuniforme[ cxuId=" + cxuId + " ]";
    }
    
}
