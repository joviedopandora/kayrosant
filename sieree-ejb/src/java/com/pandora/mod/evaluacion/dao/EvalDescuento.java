/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.evaluacion.dao;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "eval_descuento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvalDescuento.findAll", query = "SELECT e FROM EvalDescuento e"),
    @NamedQuery(name = "EvalDescuento.findByDescuentoId", query = "SELECT e FROM EvalDescuento e WHERE e.descuentoId = :descuentoId"),
    @NamedQuery(name = "EvalDescuento.findByDescuentoDesc", query = "SELECT e FROM EvalDescuento e WHERE e.descuentoDesc = :descuentoDesc"),
    @NamedQuery(name = "EvalDescuento.findByDescuentoEstado", query = "SELECT e FROM EvalDescuento e WHERE e.descuentoEstado = :descuentoEstado ORDER BY e.descuentoDesc"),
    @NamedQuery(name = "EvalDescuento.findByDescuentoValor", query = "SELECT e FROM EvalDescuento e WHERE e.descuentoValor = :descuentoValor")})
public class EvalDescuento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "descuento_id")
    private Integer descuentoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descuento_desc")
    private String descuentoDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "descuento_estado")
    private boolean descuentoEstado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "descuento_valor")
    private BigDecimal descuentoValor;
    @Column(name = "descuenta_multiplicable")
    private boolean descuentaMultiplicable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evalDescuento", fetch = FetchType.LAZY)
    private List<EvalDescuentoColaborador> evalDescuentoColaboradorList;

    public EvalDescuento() {
    }

    public EvalDescuento(Integer descuentoId) {
        this.descuentoId = descuentoId;
    }

    public EvalDescuento(Integer descuentoId, String descuentoDesc, boolean descuentoEstado, BigDecimal descuentoValor) {
        this.descuentoId = descuentoId;
        this.descuentoDesc = descuentoDesc;
        this.descuentoEstado = descuentoEstado;
        this.descuentoValor = descuentoValor;
    }

    public Integer getDescuentoId() {
        return descuentoId;
    }

    public void setDescuentoId(Integer descuentoId) {
        this.descuentoId = descuentoId;
    }

    public String getDescuentoDesc() {
        return descuentoDesc;
    }

    public void setDescuentoDesc(String descuentoDesc) {
        this.descuentoDesc = descuentoDesc;
    }

    public boolean getDescuentoEstado() {
        return descuentoEstado;
    }

    public void setDescuentoEstado(boolean descuentoEstado) {
        this.descuentoEstado = descuentoEstado;
    }

    public BigDecimal getDescuentoValor() {
        return descuentoValor;
    }

    public void setDescuentoValor(BigDecimal descuentoValor) {
        this.descuentoValor = descuentoValor;
    }

    @XmlTransient
    public List<EvalDescuentoColaborador> getEvalDescuentoColaboradorList() {
        return evalDescuentoColaboradorList;
    }

    public void setEvalDescuentoColaboradorList(List<EvalDescuentoColaborador> evalDescuentoColaboradorList) {
        this.evalDescuentoColaboradorList = evalDescuentoColaboradorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (descuentoId != null ? descuentoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvalDescuento)) {
            return false;
        }
        EvalDescuento other = (EvalDescuento) object;
        if ((this.descuentoId == null && other.descuentoId != null) || (this.descuentoId != null && !this.descuentoId.equals(other.descuentoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EvalDescuento[ descuentoId=" + descuentoId + " ]";
    }

    /**
     * @return the descuentaMultiplicable
     */
    public boolean isDescuentaMultiplicable() {
        return descuentaMultiplicable;
    }

    /**
     * @param descuentaMultiplicable the descuentaMultiplicable to set
     */
    public void setDescuentaMultiplicable(boolean descuentaMultiplicable) {
        this.descuentaMultiplicable = descuentaMultiplicable;
    }
    
}
