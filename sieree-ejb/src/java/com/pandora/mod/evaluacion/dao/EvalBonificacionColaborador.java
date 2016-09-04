/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.evaluacion.dao;

import com.pandora.mod.ordenprod.dao.PopCxcevento;
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
 * @author breyner.robles
 */
@Entity
@Table(name = "eval_bonificacion_colaborador")
@XmlRootElement
@NamedQueries({
     @NamedQuery(name = "EvalBonificacionColaborador.eliminarByColaborador", query = "DELETE  FROM EvalBonificacionColaborador e WHERE e.popCxcevento.cxeId = :cxeId  "),
    @NamedQuery(name = "EvalBonificacionColaborador.findByColaborador", query = "SELECT e FROM EvalBonificacionColaborador e WHERE e.popCxcevento.cxeId = :cxeId ORDER BY e.evalBonificacion.bonificacionNombre "),
    @NamedQuery(name = "EvalBonificacionColaborador.findAll", query = "SELECT e FROM EvalBonificacionColaborador e"),
    @NamedQuery(name = "EvalBonificacionColaborador.findByBonifxcolId", query = "SELECT e FROM EvalBonificacionColaborador e WHERE e.bonifxcolId = :bonifxcolId")})
public class EvalBonificacionColaborador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bonifxcol_id")
    private Long bonifxcolId;
    @JoinColumn(name = "cxe_id", referencedColumnName = "cxe_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PopCxcevento popCxcevento;
    @JoinColumn(name = "bonificacion_id", referencedColumnName = "bonificacion_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EvalBonificacion evalBonificacion;

    public EvalBonificacionColaborador() {
    }

    public EvalBonificacionColaborador(Long bonifxcolId) {
        this.bonifxcolId = bonifxcolId;
    }

    public Long getBonifxcolId() {
        return bonifxcolId;
    }

    public void setBonifxcolId(Long bonifxcolId) {
        this.bonifxcolId = bonifxcolId;
    }

    public PopCxcevento getPopCxcevento() {
        return popCxcevento;
    }

    public void setPopCxcevento(PopCxcevento popCxcevento) {
        this.popCxcevento = popCxcevento;
    }

    public EvalBonificacion getEvalBonificacion() {
        return evalBonificacion;
    }

    public void setEvalBonificacion(EvalBonificacion evalBonificacion) {
        this.evalBonificacion = evalBonificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bonifxcolId != null ? bonifxcolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvalBonificacionColaborador)) {
            return false;
        }
        EvalBonificacionColaborador other = (EvalBonificacionColaborador) object;
        if ((this.bonifxcolId == null && other.bonifxcolId != null) || (this.bonifxcolId != null && !this.bonifxcolId.equals(other.bonifxcolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EvalBonificacionColaborador[ bonifxcolId=" + bonifxcolId + " ]";
    }

}
