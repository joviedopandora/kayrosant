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
@Table(name = "pop_cxcrol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PopCxcrol.findAll", query = "SELECT p FROM PopCxcrol p"),
    @NamedQuery(name = "PopCxcrol.findByCxrId", query = "SELECT p FROM PopCxcrol p WHERE p.cxrId = :cxrId"),
    @NamedQuery(name = "PopCxcrol.findByCxrRol", query = "SELECT p FROM PopCxcrol p WHERE p.cxrRol = :cxrRol"),
    @NamedQuery(name = "PopCxcrol.findByCxrEstado", query = "SELECT p FROM PopCxcrol p WHERE p.cxrEstado = :cxrEstado ORDER BY p.cxrRol")})
public class PopCxcrol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cxr_id")
    private Integer cxrId;
    @Size(max = 2147483647)
    @Column(name = "cxr_rol")
    private String cxrRol;
    @Column(name = "cxr_estado")
    private Boolean cxrEstado;

    public PopCxcrol() {
    }

    public PopCxcrol(Integer cxrId) {
        this.cxrId = cxrId;
    }

    public Integer getCxrId() {
        return cxrId;
    }

    public void setCxrId(Integer cxrId) {
        this.cxrId = cxrId;
    }

    public String getCxrRol() {
        return cxrRol;
    }

    public void setCxrRol(String cxrRol) {
        this.cxrRol = cxrRol;
    }

    public Boolean getCxrEstado() {
        return cxrEstado;
    }

    public void setCxrEstado(Boolean cxrEstado) {
        this.cxrEstado = cxrEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxrId != null ? cxrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PopCxcrol)) {
            return false;
        }
        PopCxcrol other = (PopCxcrol) object;
        if ((this.cxrId == null && other.cxrId != null) || (this.cxrId != null && !this.cxrId.equals(other.cxrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "voo.PopCxcrol[ cxrId=" + cxrId + " ]";
    }
    
}
