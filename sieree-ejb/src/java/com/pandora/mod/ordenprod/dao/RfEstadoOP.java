/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.ordenprod.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Garcia Bosso
 */
@Entity
@Table(name = "rf_estadop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfEstadoOP.findByEstado", query = "SELECT v FROM RfEstadoOP v WHERE v.eopEstado =:eopEstado  ORDER BY v.eopNombre DESC")})
public class RfEstadoOP implements Serializable {

    @Column(name = "eop_estado")
    private Boolean eopEstado;
    @OneToMany(mappedBy = "eopIdnew")
    private List<LogOrdenprod> logOrdenprodList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "eop_id")
    private Integer eopId;
    @NotNull
    @Column(name = "eop_nombre")
    private String eopNombre;
    @Column(name = "eop_descripcion")
    private String eopDescripcion;
    @Column(name = "indversion")
    private Integer indversion;

    public RfEstadoOP() {
    }

    public RfEstadoOP(Integer eopId, String eopNombre, String eopDescripcion, boolean eopEstado, Integer indversion) {
        this.eopId = eopId;
        this.eopNombre = eopNombre;
        this.eopDescripcion = eopDescripcion;
        this.eopEstado = eopEstado;
        this.indversion = indversion;
    }

    public Integer getEopId() {
        return eopId;
    }

    public void setEopId(Integer eopId) {
        this.eopId = eopId;
    }

    public String getEopNombre() {
        return eopNombre;
    }

    public void setEopNombre(String eopNombre) {
        this.eopNombre = eopNombre;
    }

    public String getEopDescripcion() {
        return eopDescripcion;
    }

    public void setEopDescripcion(String eopDescripcion) {
        this.eopDescripcion = eopDescripcion;
    }

    public boolean isEopEstado() {
        return eopEstado;
    }

    public void setEopEstado(boolean eopEstado) {
        this.eopEstado = eopEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.eopId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RfEstadoOP other = (RfEstadoOP) obj;
        return Objects.equals(this.eopId, other.eopId);
    }

    public Boolean getEopEstado() {
        return eopEstado;
    }

    public void setEopEstado(Boolean eopEstado) {
        this.eopEstado = eopEstado;
    }

    @XmlTransient
    public List<LogOrdenprod> getLogOrdenprodList() {
        return logOrdenprodList;
    }

    public void setLogOrdenprodList(List<LogOrdenprod> logOrdenprodList) {
        this.logOrdenprodList = logOrdenprodList;
    }
    
    
}
