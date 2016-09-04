/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlos
 */
@Entity
@Table(name = "fin_rf_tipoimpuesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinRfTipoimpuesto.findAll", query = "SELECT f FROM FinRfTipoimpuesto f"),
    @NamedQuery(name = "FinRfTipoimpuesto.findByTimId", query = "SELECT f FROM FinRfTipoimpuesto f WHERE f.timId = :timId"),
    @NamedQuery(name = "FinRfTipoimpuesto.findByTimNombre", query = "SELECT f FROM FinRfTipoimpuesto f WHERE f.timNombre = :timNombre"),
    @NamedQuery(name = "FinRfTipoimpuesto.findByTimDescripcion", query = "SELECT f FROM FinRfTipoimpuesto f WHERE f.timDescripcion = :timDescripcion"),
    @NamedQuery(name = "FinRfTipoimpuesto.findByTimEstado", query = "SELECT f FROM FinRfTipoimpuesto f WHERE f.timEstado = :timEstado"),
    @NamedQuery(name = "FinRfTipoimpuesto.findByIndversion", query = "SELECT f FROM FinRfTipoimpuesto f WHERE f.indversion = :indversion")})
public class FinRfTipoimpuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tim_id")
    private Long timId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "tim_nombre")
    private String timNombre;
    @Size(max = 2147483647)
    @Column(name = "tim_descripcion")
    private String timDescripcion;
    @Column(name = "tim_estado")
    private Boolean timEstado;
    @Column(name = "indversion")
    private Integer indversion;

    public FinRfTipoimpuesto() {
    }

    public FinRfTipoimpuesto(Long timId) {
        this.timId = timId;
    }

    public FinRfTipoimpuesto(Long timId, String timNombre) {
        this.timId = timId;
        this.timNombre = timNombre;
    }

    public Long getTimId() {
        return timId;
    }

    public void setTimId(Long timId) {
        this.timId = timId;
    }

    public String getTimNombre() {
        return timNombre;
    }

    public void setTimNombre(String timNombre) {
        this.timNombre = timNombre;
    }

    public String getTimDescripcion() {
        return timDescripcion;
    }

    public void setTimDescripcion(String timDescripcion) {
        this.timDescripcion = timDescripcion;
    }

    public Boolean getTimEstado() {
        return timEstado;
    }

    public void setTimEstado(Boolean timEstado) {
        this.timEstado = timEstado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (timId != null ? timId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinRfTipoimpuesto)) {
            return false;
        }
        FinRfTipoimpuesto other = (FinRfTipoimpuesto) object;
        if ((this.timId == null && other.timId != null) || (this.timId != null && !this.timId.equals(other.timId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinRfTipoimpuesto[ timId=" + timId + " ]";
    }
    
}
