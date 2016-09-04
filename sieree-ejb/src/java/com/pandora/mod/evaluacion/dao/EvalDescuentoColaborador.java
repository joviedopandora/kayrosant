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
@Table(name = "eval_descuento_colaborador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvalDescuentoColaborador.findAll", query = "SELECT e FROM EvalDescuentoColaborador e"),
    @NamedQuery(name = "EvalDescuentoColaborador.findByDescxcolId", query = "SELECT e FROM EvalDescuentoColaborador e WHERE e.descxcolId = :descxcolId")})
public class EvalDescuentoColaborador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "descxcol_id")
    private Long descxcolId;
    @JoinColumn(name = "cxe_id", referencedColumnName = "cxe_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PopCxcevento popCxcevento;
    @JoinColumn(name = "descuento_id", referencedColumnName = "descuento_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EvalDescuento evalDescuento;

    public EvalDescuentoColaborador() {
    }

    public EvalDescuentoColaborador(Long descxcolId) {
        this.descxcolId = descxcolId;
    }

    public Long getDescxcolId() {
        return descxcolId;
    }

    public void setDescxcolId(Long descxcolId) {
        this.descxcolId = descxcolId;
    }

    public PopCxcevento getPopCxcevento() {
        return popCxcevento;
    }

    public void setPopCxcevento(PopCxcevento popCxcevento) {
        this.popCxcevento = popCxcevento;
    }

    public EvalDescuento getEvalDescuento() {
        return evalDescuento;
    }

    public void setEvalDescuento(EvalDescuento evalDescuento) {
        this.evalDescuento = evalDescuento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (descxcolId != null ? descxcolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvalDescuentoColaborador)) {
            return false;
        }
        EvalDescuentoColaborador other = (EvalDescuentoColaborador) object;
        if ((this.descxcolId == null && other.descxcolId != null) || (this.descxcolId != null && !this.descxcolId.equals(other.descxcolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EvalDescuentoColaborador[ descxcolId=" + descxcolId + " ]";
    }
    
}
