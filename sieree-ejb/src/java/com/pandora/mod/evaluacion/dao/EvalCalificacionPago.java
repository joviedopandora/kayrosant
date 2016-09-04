/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.evaluacion.dao;

import adm.sys.dao.AdmCargo;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author breyner.robles
 */
@Entity
@Table(name = "eval_calificacion_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvalCalificacionPago.findByCalificacion", query = "SELECT e FROM EvalCalificacionPago e WHERE e.evalCalificacion.calificacionId = :calificacionId AND e.admCargo.crgId =:crgId"),
    @NamedQuery(name = "EvalCalificacionPago.findAll", query = "SELECT e FROM EvalCalificacionPago e"),
    @NamedQuery(name = "EvalCalificacionPago.findByPagoId", query = "SELECT e FROM EvalCalificacionPago e WHERE e.pagoId = :pagoId"),
    @NamedQuery(name = "EvalCalificacionPago.findByPagoValorLocal", query = "SELECT e FROM EvalCalificacionPago e WHERE e.pagoValorLocal = :pagoValorLocal"),
    @NamedQuery(name = "EvalCalificacionPago.findByPagoValorVisitante", query = "SELECT e FROM EvalCalificacionPago e WHERE e.pagoValorVisitante = :pagoValorVisitante")})
public class EvalCalificacionPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pago_id")
    private Integer pagoId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "pago_valor_local")
    private BigDecimal pagoValorLocal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pago_valor_visitante")
    private BigDecimal pagoValorVisitante;
    @JoinColumn(name = "calificacion_id", referencedColumnName = "calificacion_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EvalCalificacion evalCalificacion;
    @JoinColumn(name = "crg_id", referencedColumnName = "crg_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AdmCargo admCargo;

    public EvalCalificacionPago() {
    }

    public EvalCalificacionPago(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public EvalCalificacionPago(Integer pagoId, BigDecimal pagoValorLocal, BigDecimal pagoValorVisitante) {
        this.pagoId = pagoId;
        this.pagoValorLocal = pagoValorLocal;
        this.pagoValorVisitante = pagoValorVisitante;
    }

    public Integer getPagoId() {
        return pagoId;
    }

    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public BigDecimal getPagoValorLocal() {
        return pagoValorLocal;
    }

    public void setPagoValorLocal(BigDecimal pagoValorLocal) {
        this.pagoValorLocal = pagoValorLocal;
    }

    public BigDecimal getPagoValorVisitante() {
        return pagoValorVisitante;
    }

    public void setPagoValorVisitante(BigDecimal pagoValorVisitante) {
        this.pagoValorVisitante = pagoValorVisitante;
    }

    public EvalCalificacion getEvalCalificacion() {
        return evalCalificacion;
    }

    public void setEvalCalificacion(EvalCalificacion evalCalificacion) {
        this.evalCalificacion = evalCalificacion;
    }

    public AdmCargo getAdmCargo() {
        return admCargo;
    }

    public void setAdmCargo(AdmCargo admCargo) {
        this.admCargo = admCargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagoId != null ? pagoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvalCalificacionPago)) {
            return false;
        }
        EvalCalificacionPago other = (EvalCalificacionPago) object;
        if ((this.pagoId == null && other.pagoId != null) || (this.pagoId != null && !this.pagoId.equals(other.pagoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.mod.evaluacion.dao.EvalCalificacionPago[ pagoId=" + pagoId + " ]";
    }

}
