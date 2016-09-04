/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.liquidacion.dao;


import adm.sys.dao.AdmCrgxcol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sistemasmaximus
 */
@Entity
@Table(name = "pg_liquidacionconsolidado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PgLiquidacionconsolidado.findByMacFecha", query = "SELECT MAX(p.fechafLiquidaconso) FROM PgLiquidacionconsolidado p"),
    @NamedQuery(name = "PgLiquidacionconsolidado.findAll", query = "SELECT p FROM PgLiquidacionconsolidado p ORDER BY p.fechafLiquidaconso DESC"),
    @NamedQuery(name = "PgLiquidacionconsolidado.findByIdLiquidaconso", query = "SELECT p FROM PgLiquidacionconsolidado p WHERE p.idLiquidaconso = :idLiquidaconso"),
    @NamedQuery(name = "PgLiquidacionconsolidado.findByFechaiLiquidaconso", query = "SELECT p FROM PgLiquidacionconsolidado p WHERE p.fechaiLiquidaconso = :fechaiLiquidaconso"),
    @NamedQuery(name = "PgLiquidacionconsolidado.findByFechafLiquidaconso", query = "SELECT p FROM PgLiquidacionconsolidado p WHERE p.fechafLiquidaconso = :fechafLiquidaconso"),
    @NamedQuery(name = "PgLiquidacionconsolidado.findByNombreLiquidaconso", query = "SELECT p FROM PgLiquidacionconsolidado p WHERE p.nombreLiquidaconso = :nombreLiquidaconso"),
    @NamedQuery(name = "PgLiquidacionconsolidado.findByFechapLiquidaconso", query = "SELECT p FROM PgLiquidacionconsolidado p WHERE p.fechapLiquidaconso = :fechapLiquidaconso")})
public class PgLiquidacionconsolidado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(sequenceName = "pg_liquidacionconsolidado_id_liquidaconso_seq", name = "pg_liquidacionconsolidado_id_liquidaconso_seq", allocationSize = 1)
    @GeneratedValue(generator = "pg_liquidacionconsolidado_id_liquidaconso_seq", strategy = GenerationType.SEQUENCE)
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_liquidaconso")
    private Integer idLiquidaconso;
    @Column(name = "fechai_liquidaconso")
    @Temporal(TemporalType.DATE)
    private Date fechaiLiquidaconso;
    @Column(name = "fechaf_liquidaconso")
    @Temporal(TemporalType.DATE)
    private Date fechafLiquidaconso;
    @Size(max = 2147483647)
    @Column(name = "nombre_liquidaconso")
    private String nombreLiquidaconso;
    @Column(name = "fechap_liquidaconso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapLiquidaconso;
    @OneToMany(mappedBy = "idLiquidaconso", fetch = FetchType.LAZY)
    private List<PgLiquidacion> pgLiquidacionList = new ArrayList<>();
    
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdmCrgxcol cxcId;

    public PgLiquidacionconsolidado() {
    }

    public PgLiquidacionconsolidado(Integer idLiquidaconso) {
        this.idLiquidaconso = idLiquidaconso;
    }

    public Integer getIdLiquidaconso() {
        return idLiquidaconso;
    }

    public void setIdLiquidaconso(Integer idLiquidaconso) {
        this.idLiquidaconso = idLiquidaconso;
    }

    public Date getFechaiLiquidaconso() {
        return fechaiLiquidaconso;
    }

    public void setFechaiLiquidaconso(Date fechaiLiquidaconso) {
        this.fechaiLiquidaconso = fechaiLiquidaconso;
    }

    public Date getFechafLiquidaconso() {
        return fechafLiquidaconso;
    }

    public void setFechafLiquidaconso(Date fechafLiquidaconso) {
        this.fechafLiquidaconso = fechafLiquidaconso;
    }

    public String getNombreLiquidaconso() {
        return nombreLiquidaconso;
    }

    public void setNombreLiquidaconso(String nombreLiquidaconso) {
        this.nombreLiquidaconso = nombreLiquidaconso;
    }

    public Date getFechapLiquidaconso() {
        return fechapLiquidaconso;
    }

    public void setFechapLiquidaconso(Date fechapLiquidaconso) {
        this.fechapLiquidaconso = fechapLiquidaconso;
    }

    @XmlTransient
    public List<PgLiquidacion> getPgLiquidacionList() {
        return pgLiquidacionList;
    }

    public void setPgLiquidacionList(List<PgLiquidacion> pgLiquidacionList) {
        this.pgLiquidacionList = pgLiquidacionList;
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLiquidaconso != null ? idLiquidaconso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PgLiquidacionconsolidado)) {
            return false;
        }
        PgLiquidacionconsolidado other = (PgLiquidacionconsolidado) object;
        return !((this.idLiquidaconso == null && other.idLiquidaconso != null) || (this.idLiquidaconso != null && !this.idLiquidaconso.equals(other.idLiquidaconso)));
    }

    @Override
    public String toString() {
        return "newpackage.PgLiquidacionconsolidado[ idLiquidaconso=" + idLiquidaconso + " ]";
    }
    
}
