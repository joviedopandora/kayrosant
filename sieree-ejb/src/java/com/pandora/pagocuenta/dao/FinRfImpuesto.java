/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author carlos
 */
@Entity
@Table(name = "fin_rf_impuesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinRfImpuesto.findAll", query = "SELECT f FROM FinRfImpuesto f"),
    @NamedQuery(name = "FinRfImpuesto.findByImpId", query = "SELECT f FROM FinRfImpuesto f WHERE f.impId = :impId"),
    @NamedQuery(name = "FinRfImpuesto.findByImpNombre", query = "SELECT f FROM FinRfImpuesto f WHERE f.impNombre = :impNombre"),
    @NamedQuery(name = "FinRfImpuesto.findByImpDescripcion", query = "SELECT f FROM FinRfImpuesto f WHERE f.impDescripcion = :impDescripcion"),
    @NamedQuery(name = "FinRfImpuesto.findByImpBaseuvt", query = "SELECT f FROM FinRfImpuesto f WHERE f.impBaseuvt = :impBaseuvt"),
    @NamedQuery(name = "FinRfImpuesto.findByImpBasepesos", query = "SELECT f FROM FinRfImpuesto f WHERE f.impBasepesos = :impBasepesos"),
    @NamedQuery(name = "FinRfImpuesto.findByImpTarifa", query = "SELECT f FROM FinRfImpuesto f WHERE f.impTarifa = :impTarifa"),
    @NamedQuery(name = "FinRfImpuesto.findByImpEstado", query = "SELECT f FROM FinRfImpuesto f WHERE f.impEstado = :impEstado"),
    @NamedQuery(name = "FinRfImpuesto.findByIndversion", query = "SELECT f FROM FinRfImpuesto f WHERE f.indversion = :indversion"),
    @NamedQuery(name = "FinRfImpuesto.findByImpEstadoXTipoImp", query = "SELECT f FROM FinRfImpuesto f JOIN f.timId t "
        + " WHERE t.timId = :timId AND f.impEstado = :impEstado ORDER BY t.timNombre")})
public class FinRfImpuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "imp_id")
    private Long impId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "imp_nombre")
    private String impNombre;
    @Size(max = 2147483647)
    @Column(name = "imp_descripcion")
    private String impDescripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "imp_baseuvt")
    private BigDecimal impBaseuvt;
    @Column(name = "imp_basepesos")
    private BigDecimal impBasepesos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "imp_tarifa")
    private BigDecimal impTarifa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "imp_estado")
    private boolean impEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @Column(name = "imp_orden")
    private Integer impOrden;
    @OneToMany(mappedBy = "impId")
    private List<FinImpxcta> finImpxctaList;
    @JoinColumn(name = "tim_id", referencedColumnName = "tim_id")
    @ManyToOne
    private FinRfTipoimpuesto timId;

    public FinRfImpuesto() {
    }

    public FinRfImpuesto(Long impId) {
        this.impId = impId;
    }

    public FinRfImpuesto(Long impId, String impNombre, BigDecimal impTarifa, boolean impEstado, int indversion) {
        this.impId = impId;
        this.impNombre = impNombre;
        this.impTarifa = impTarifa;
        this.impEstado = impEstado;
        this.indversion = indversion;
    }

    public Long getImpId() {
        return impId;
    }

    public void setImpId(Long impId) {
        this.impId = impId;
    }

    public String getImpNombre() {
        return impNombre;
    }

    public void setImpNombre(String impNombre) {
        this.impNombre = impNombre;
    }

    public String getImpDescripcion() {
        return impDescripcion;
    }

    public void setImpDescripcion(String impDescripcion) {
        this.impDescripcion = impDescripcion;
    }

    public BigDecimal getImpBaseuvt() {
        return impBaseuvt;
    }

    public void setImpBaseuvt(BigDecimal impBaseuvt) {
        this.impBaseuvt = impBaseuvt;
    }

    public BigDecimal getImpBasepesos() {
        return impBasepesos;
    }

    public void setImpBasepesos(BigDecimal impBasepesos) {
        this.impBasepesos = impBasepesos;
    }

    public BigDecimal getImpTarifa() {
        return impTarifa;
    }

    public void setImpTarifa(BigDecimal impTarifa) {
        this.impTarifa = impTarifa;
    }

    public boolean getImpEstado() {
        return impEstado;
    }

    public void setImpEstado(boolean impEstado) {
        this.impEstado = impEstado;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    public Integer getImpOrden() {
        return impOrden;
    }

    public void setImpOrden(Integer impOrden) {
        this.impOrden = impOrden;
    }

    @XmlTransient
    public List<FinImpxcta> getFinImpxctaList() {
        return finImpxctaList;
    }

    public void setFinImpxctaList(List<FinImpxcta> finImpxctaList) {
        this.finImpxctaList = finImpxctaList;
    }

    public FinRfTipoimpuesto getTimId() {
        return timId;
    }

    public void setTimId(FinRfTipoimpuesto timId) {
        this.timId = timId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (impId != null ? impId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinRfImpuesto)) {
            return false;
        }
        FinRfImpuesto other = (FinRfImpuesto) object;
        if ((this.impId == null && other.impId != null) || (this.impId != null && !this.impId.equals(other.impId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinRfImpuesto[ impId=" + impId + " ]";
    }
}
