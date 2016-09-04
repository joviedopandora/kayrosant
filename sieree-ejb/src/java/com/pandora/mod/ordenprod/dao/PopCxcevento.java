/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.ordenprod.dao;

import adm.sys.dao.AdmCargo;
import adm.sys.dao.AdmCrgxcol;
import com.pandora.mod.evaluacion.dao.EvalBonificacionColaborador;
import com.pandora.mod.evaluacion.dao.EvalCalificacion;
import com.pandora.mod.evaluacion.dao.EvalDescuentoColaborador;
import com.pandora.mod.liquidacion.dao.PgLiquidacionxcolaborador;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "pop_cxcevento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PopCxcevento.findByLiquidacion", query = "SELECT DISTINCT(p) FROM PopCxcevento p  WHERE p.oprId.oprCantidadEvaluacion = :calificacionId AND p.cxeId NOT IN (SELECT l.cxeId.cxeId FROM PgLiquidacionxcolaborador l WHERE l.cxeId.cxeId = p.cxeId) AND p.oprId.oprFechaevtini <=:oprFechaevtini ORDER BY p.cxcId.crgId.crgNombre ASC"),
    @NamedQuery(name = "PopCxcevento.findByOrdenProduccionPorPopCxcevento", query = "SELECT DISTINCT(p) FROM PopCxcevento p WHERE p.oprId.oprId = :oprId AND p.cxeEstado = :cxeEstado ORDER BY p.cxcId.crgId.crgNombre"),
    @NamedQuery(name = "PopCxcevento.findDeleteXOP", query = "DELETE FROM PopCxcevento p WHERE p.oprId.oprId = :oprId"),
    @NamedQuery(name = "PopCxcevento.findByOrdenProduccion", query = "SELECT DISTINCT(p.cxcId) FROM PopCxcevento p WHERE p.oprId.oprId = :oprId AND p.cxeEstado = :cxeEstado ORDER BY p.cxcId.crgId.crgNombre"),
    @NamedQuery(name = "PopCxcevento.findAll", query = "SELECT p FROM PopCxcevento p"),
    @NamedQuery(name = "PopCxcevento.findByCxeId", query = "SELECT p FROM PopCxcevento p WHERE p.cxeId = :cxeId"),
    @NamedQuery(name = "PopCxcevento.findByCxeObservacion", query = "SELECT p FROM PopCxcevento p WHERE p.cxeObservacion = :cxeObservacion"),
    @NamedQuery(name = "PopCxcevento.findByCxeFechaproceso", query = "SELECT p FROM PopCxcevento p WHERE p.cxeFechaproceso = :cxeFechaproceso"),
    @NamedQuery(name = "PopCxcevento.findByCxeEstado", query = "SELECT p FROM PopCxcevento p WHERE p.cxeEstado = :cxeEstado"),
    @NamedQuery(name = "PopCxcevento.findByIndversion", query = "SELECT p FROM PopCxcevento p WHERE p.indversion = :indversion"),
    @NamedQuery(name = "PopCxcevento.findByOrdenProd", query = "SELECT p FROM PopCxcevento p WHERE p.oprId.oprId = :oprId ORDER BY p.cxcId.cpeId.colCedula.colApellido1,p.cxcId.cpeId.colCedula.colNombre1")
})
public class PopCxcevento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cxe_id")
    private Long cxeId;
    @Size(max = 2147483647)
    @Column(name = "cxe_observacion")
    private String cxeObservacion;
    @Column(name = "cxe_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cxeFechaproceso;
    @Column(name = "cxe_estado")
    private Boolean cxeEstado;
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "opr_id", referencedColumnName = "opr_id")
    @ManyToOne
    private PopOrdenprod oprId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    @JoinColumn(name = "cxu_id", referencedColumnName = "cxu_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PopCxcuniforme popCxcuniforme;
    @JoinColumn(name = "cxr_id", referencedColumnName = "cxr_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PopCxcrol popCxcrol;
    @JoinColumn(name = "cxre_id", referencedColumnName = "cxre_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PopCxcrespon popCxcrespon;
    @JoinColumn(name = "cxci_id", referencedColumnName = "cxci_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PopCxccitacion popCxccitacion;

    @JoinColumn(name = "crg_id", referencedColumnName = "crg_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdmCargo admCargo;

    @JoinColumn(name = "calificacion_id", referencedColumnName = "calificacion_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EvalCalificacion evalCalificacion;
    @Column(name = "cxe_valor_pagar")
    private BigDecimal cxeValorPagar;

    @Column(name = "cxe_valor_bonificacion")
    private BigDecimal cxeValorBonificacion;

    @Column(name = "cxe_valor_descuento")
    private BigDecimal cxeValorDescuento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "popCxcevento", fetch = FetchType.LAZY)
    private List<EvalBonificacionColaborador> evalBonificacionColaboradorList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "popCxcevento", fetch = FetchType.LAZY)
    private List<EvalDescuentoColaborador> evalDescuentoColaboradorList;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "cxeId", fetch = FetchType.LAZY)
    private List<PgLiquidacionxcolaborador> pgLiquidacionxcolaboradorList;

    public PopCxcevento() {
        this.cxeEstado = true;
        cxeValorBonificacion = BigDecimal.ZERO;
    }

    public PopCxcevento(Long cxeId) {
        this();
        this.cxeId = cxeId;

    }

    public Long getCxeId() {
        return cxeId;
    }

    public void setCxeId(Long cxeId) {
        this.cxeId = cxeId;
    }

    public String getCxeObservacion() {
        return cxeObservacion;
    }

    public void setCxeObservacion(String cxeObservacion) {
        this.cxeObservacion = cxeObservacion;
    }

    public Date getCxeFechaproceso() {
        return cxeFechaproceso;
    }

    public void setCxeFechaproceso(Date cxeFechaproceso) {
        this.cxeFechaproceso = cxeFechaproceso;
    }

    public Boolean getCxeEstado() {
        return cxeEstado;
    }

    public void setCxeEstado(Boolean cxeEstado) {
        this.cxeEstado = cxeEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public PopOrdenprod getOprId() {
        return oprId;
    }

    public void setOprId(PopOrdenprod oprId) {
        this.oprId = oprId;
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    public PopCxcuniforme getPopCxcuniforme() {
        return popCxcuniforme;
    }

    public void setPopCxcuniforme(PopCxcuniforme popCxcuniforme) {
        this.popCxcuniforme = popCxcuniforme;
    }

    public PopCxcrol getPopCxcrol() {
        return popCxcrol;
    }

    public void setPopCxcrol(PopCxcrol popCxcrol) {
        this.popCxcrol = popCxcrol;
    }

    public PopCxcrespon getPopCxcrespon() {
        return popCxcrespon;
    }

    public void setPopCxcrespon(PopCxcrespon popCxcrespon) {
        this.popCxcrespon = popCxcrespon;
    }

    public PopCxccitacion getPopCxccitacion() {
        return popCxccitacion;
    }

    public void setPopCxccitacion(PopCxccitacion popCxccitacion) {
        this.popCxccitacion = popCxccitacion;
    }

    public AdmCargo getAdmCargo() {
        return admCargo;
    }

    public void setAdmCargo(AdmCargo admCargo) {
        this.admCargo = admCargo;
    }

    public EvalCalificacion getEvalCalificacion() {
        return evalCalificacion;
    }

    public void setEvalCalificacion(EvalCalificacion evalCalificacion) {
        this.evalCalificacion = evalCalificacion;
    }

    public BigDecimal getCxeValorPagar() {
        return cxeValorPagar;
    }

    public void setCxeValorPagar(BigDecimal cxeValorPagar) {
        this.cxeValorPagar = cxeValorPagar;
    }

    public BigDecimal getCxeValorBonificacion() {
        return cxeValorBonificacion;
    }

    public void setCxeValorBonificacion(BigDecimal cxeValorBonificacion) {
        this.cxeValorBonificacion = cxeValorBonificacion;
    }

    public BigDecimal getCxeValorDescuento() {
        return cxeValorDescuento;
    }

    public void setCxeValorDescuento(BigDecimal cxeValorDescuento) {
        this.cxeValorDescuento = cxeValorDescuento;
    }

    public List<EvalDescuentoColaborador> getEvalDescuentoColaboradorList() {
        return evalDescuentoColaboradorList;
    }

    public void setEvalDescuentoColaboradorList(List<EvalDescuentoColaborador> evalDescuentoColaboradorList) {
        this.evalDescuentoColaboradorList = evalDescuentoColaboradorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxeId != null ? cxeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PopCxcevento)) {
            return false;
        }
        PopCxcevento other = (PopCxcevento) object;
        if ((this.cxcId == null && other.cxcId != null) || (this.cxcId != null && !this.cxcId.equals(other.cxcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.PopCxcevento[ cxeId=" + cxeId + " ]";
    }

    public List<EvalBonificacionColaborador> getEvalBonificacionColaboradorList() {
        return evalBonificacionColaboradorList;
    }

    public void setEvalBonificacionColaboradorList(List<EvalBonificacionColaborador> evalBonificacionColaboradorList) {
        this.evalBonificacionColaboradorList = evalBonificacionColaboradorList;
    }

    public List<PgLiquidacionxcolaborador> getPgLiquidacionxcolaboradorList() {
        return pgLiquidacionxcolaboradorList;
    }

    public void setPgLiquidacionxcolaboradorList(List<PgLiquidacionxcolaborador> pgLiquidacionxcolaboradorList) {
        this.pgLiquidacionxcolaboradorList = pgLiquidacionxcolaboradorList;
    }
    
    

}
