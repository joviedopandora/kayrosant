/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.evaluacion.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author breyner.robles
 */
@Entity
@Table(name = "eval_bonificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvalBonificacion.findAll", query = "SELECT e FROM EvalBonificacion e"),
    @NamedQuery(name = "EvalBonificacion.findByBonificacionId", query = "SELECT e FROM EvalBonificacion e WHERE e.bonificacionId = :bonificacionId"),
    @NamedQuery(name = "EvalBonificacion.findByBonificacionNombre", query = "SELECT e FROM EvalBonificacion e WHERE e.bonificacionNombre = :bonificacionNombre"),
    @NamedQuery(name = "EvalBonificacion.findByBonificacionEstado", query = "SELECT e FROM EvalBonificacion e WHERE e.bonificacionEstado = :bonificacionEstado ORDER BY e.bonificacionNombre"),
    @NamedQuery(name = "EvalBonificacion.findByBonificacionValor", query = "SELECT e FROM EvalBonificacion e WHERE e.bonificacionValor = :bonificacionValor")})
public class EvalBonificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bonificacion_id")
    private Integer bonificacionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "bonificacion_nombre")
    private String bonificacionNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bonificacion_estado")
    private boolean bonificacionEstado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "bonificacion_valor")
    private BigDecimal bonificacionValor;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "bonificacion_tipo_antiguedad")
    private boolean bonificacionTipoAntiguedad;
    @Column(name = "bonificacion_multiplcable")
    private boolean bonificacionMultiplcable;
    
    

    public EvalBonificacion() {
    }

    public EvalBonificacion(Integer bonificacionId) {
        this.bonificacionId = bonificacionId;
    }

    public EvalBonificacion(Integer bonificacionId, String bonificacionNombre, boolean bonificacionEstado, BigDecimal bonificacionValor) {
        this.bonificacionId = bonificacionId;
        this.bonificacionNombre = bonificacionNombre;
        this.bonificacionEstado = bonificacionEstado;
        this.bonificacionValor = bonificacionValor;
    }

    public EvalBonificacion(Integer bonificacionId, String bonificacionNombre, boolean bonificacionEstado, BigDecimal bonificacionValor, boolean bonificacionTipoAntiguedad) {
        this.bonificacionId = bonificacionId;
        this.bonificacionNombre = bonificacionNombre;
        this.bonificacionEstado = bonificacionEstado;
        this.bonificacionValor = bonificacionValor;
        this.bonificacionTipoAntiguedad = bonificacionTipoAntiguedad;
    }
    
    

    public Integer getBonificacionId() {
        return bonificacionId;
    }

    public void setBonificacionId(Integer bonificacionId) {
        this.bonificacionId = bonificacionId;
    }

    public String getBonificacionNombre() {
        return bonificacionNombre;
    }

    public void setBonificacionNombre(String bonificacionNombre) {
        this.bonificacionNombre = bonificacionNombre;
    }

    public boolean getBonificacionEstado() {
        return bonificacionEstado;
    }

    public void setBonificacionEstado(boolean bonificacionEstado) {
        this.bonificacionEstado = bonificacionEstado;
    }

    public BigDecimal getBonificacionValor() {
        return bonificacionValor;
    }

    public void setBonificacionValor(BigDecimal bonificacionValor) {
        this.bonificacionValor = bonificacionValor;
    }

    public boolean isBonificacionTipoAntiguedad() {
        return bonificacionTipoAntiguedad;
    }

    public void setBonificacionTipoAntiguedad(boolean bonificacionTipoAntiguedad) {
        this.bonificacionTipoAntiguedad = bonificacionTipoAntiguedad;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bonificacionId != null ? bonificacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvalBonificacion)) {
            return false;
        }
        EvalBonificacion other = (EvalBonificacion) object;
        if ((this.bonificacionId == null && other.bonificacionId != null) || (this.bonificacionId != null && !this.bonificacionId.equals(other.bonificacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EvalBonificacion[ bonificacionId=" + bonificacionId + " ]";
    }

    /**
     * @return the bonificacionMultiplcable
     */
    public boolean isBonificacionMultiplcable() {
        return bonificacionMultiplcable;
    }

    /**
     * @param bonificacionMultiplcable the bonificacionMultiplcable to set
     */
    public void setBonificacionMultiplcable(boolean bonificacionMultiplcable) {
        this.bonificacionMultiplcable = bonificacionMultiplcable;
    }
    
}
