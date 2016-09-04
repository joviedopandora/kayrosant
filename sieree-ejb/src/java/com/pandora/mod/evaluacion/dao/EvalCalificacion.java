/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.evaluacion.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "eval_calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvalCalificacion.findAll", query = "SELECT e FROM EvalCalificacion e"),
    @NamedQuery(name = "EvalCalificacion.findByCalificacionId", query = "SELECT e FROM EvalCalificacion e WHERE e.calificacionId = :calificacionId"),
    @NamedQuery(name = "EvalCalificacion.findByCalificacionDesc", query = "SELECT e FROM EvalCalificacion e WHERE e.calificacionDesc = :calificacionDesc"),
    @NamedQuery(name = "EvalCalificacion.findByCalificacionEstado", query = "SELECT e FROM EvalCalificacion e WHERE e.calificacionEstado = :calificacionEstado ORDER BY e.calificacionDesc ASC ")})
public class EvalCalificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "calificacion_id")
    private Integer calificacionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "calificacion_desc")
    private String calificacionDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "calificacion_estado")
    private boolean calificacionEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evalCalificacion", fetch = FetchType.LAZY)
    private List<EvalCalificacionPago> evalCalificacionPagoList;

    public EvalCalificacion() {
    }

    public EvalCalificacion(Integer calificacionId) {
        this.calificacionId = calificacionId;
    }

    public EvalCalificacion(Integer calificacionId, String calificacionDesc, boolean calificacionEstado) {
        this.calificacionId = calificacionId;
        this.calificacionDesc = calificacionDesc;
        this.calificacionEstado = calificacionEstado;
    }

    public Integer getCalificacionId() {
        return calificacionId;
    }

    public void setCalificacionId(Integer calificacionId) {
        this.calificacionId = calificacionId;
    }

    public String getCalificacionDesc() {
        return calificacionDesc;
    }

    public void setCalificacionDesc(String calificacionDesc) {
        this.calificacionDesc = calificacionDesc;
    }

    public boolean getCalificacionEstado() {
        return calificacionEstado;
    }

    public void setCalificacionEstado(boolean calificacionEstado) {
        this.calificacionEstado = calificacionEstado;
    }

    @XmlTransient
    public List<EvalCalificacionPago> getEvalCalificacionPagoList() {
        return evalCalificacionPagoList;
    }

    public void setEvalCalificacionPagoList(List<EvalCalificacionPago> evalCalificacionPagoList) {
        this.evalCalificacionPagoList = evalCalificacionPagoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calificacionId != null ? calificacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvalCalificacion)) {
            return false;
        }
        EvalCalificacion other = (EvalCalificacion) object;
        if ((this.calificacionId == null && other.calificacionId != null) || (this.calificacionId != null && !this.calificacionId.equals(other.calificacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.mod.evaluacion.dao.EvalCalificacion[ calificacionId=" + calificacionId + " ]";
    }
    
}
