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
@Table(name = "pop_cxcrespon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PopCxcrespon.findAll", query = "SELECT p FROM PopCxcrespon p"),
    @NamedQuery(name = "PopCxcrespon.findByCxreId", query = "SELECT p FROM PopCxcrespon p WHERE p.cxreId = :cxreId"),
    @NamedQuery(name = "PopCxcrespon.findByCxreRespon", query = "SELECT p FROM PopCxcrespon p WHERE p.cxreRespon = :cxreRespon"),
    @NamedQuery(name = "PopCxcrespon.findByCxreEstado", query = "SELECT p FROM PopCxcrespon p WHERE p.cxreEstado = :cxreEstado ORDER BY p.cxreRespon")})
public class PopCxcrespon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cxre_id")
    private Integer cxreId;
    @Size(max = 2147483647)
    @Column(name = "cxre_respon")
    private String cxreRespon;
    @Column(name = "cxre_estado")
    private Boolean cxreEstado;

    public PopCxcrespon() {
    }

    public PopCxcrespon(Integer cxreId) {
        this.cxreId = cxreId;
    }

    public Integer getCxreId() {
        return cxreId;
    }

    public void setCxreId(Integer cxreId) {
        this.cxreId = cxreId;
    }

    public String getCxreRespon() {
        return cxreRespon;
    }

    public void setCxreRespon(String cxreRespon) {
        this.cxreRespon = cxreRespon;
    }

    public Boolean getCxreEstado() {
        return cxreEstado;
    }

    public void setCxreEstado(Boolean cxreEstado) {
        this.cxreEstado = cxreEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxreId != null ? cxreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PopCxcrespon)) {
            return false;
        }
        PopCxcrespon other = (PopCxcrespon) object;
        if ((this.cxreId == null && other.cxreId != null) || (this.cxreId != null && !this.cxreId.equals(other.cxreId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "voo.PopCxcrespon[ cxreId=" + cxreId + " ]";
    }
    
}
