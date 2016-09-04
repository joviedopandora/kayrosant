/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "adm_pagina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmPagina.findAll", query = "SELECT a FROM AdmPagina a"),
    @NamedQuery(name = "AdmPagina.findByPagId", query = "SELECT a FROM AdmPagina a WHERE a.pagId = :pagId"),
    @NamedQuery(name = "AdmPagina.findByPagNombre", query = "SELECT a FROM AdmPagina a WHERE a.pagNombre = :pagNombre"),
    @NamedQuery(name = "AdmPagina.findByPagDesc", query = "SELECT a FROM AdmPagina a WHERE a.pagDesc = :pagDesc"),
    @NamedQuery(name = "AdmPagina.findByPagRuta", query = "SELECT a FROM AdmPagina a WHERE a.pagRuta = :pagRuta"),
    @NamedQuery(name = "AdmPagina.findByPagEst", query = "SELECT a FROM AdmPagina a WHERE a.pagEst = :pagEst"),
    @NamedQuery(name = "AdmPagina.findByIndversion", query = "SELECT a FROM AdmPagina a WHERE a.indversion = :indversion")})
public class AdmPagina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pag_id")
    private Integer pagId;
    @Size(max = 150)
    @Column(name = "pag_nombre")
    private String pagNombre;
    @Size(max = 2147483647)
    @Column(name = "pag_desc")
    private String pagDesc;
    @Size(max = 1024)
    @Column(name = "pag_ruta")
    private String pagRuta;
    @Column(name = "pag_est")
    private Boolean pagEst;
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "smd_id", referencedColumnName = "smd_id")
    @ManyToOne
    private AdmSubmodapp smdId;

    public AdmPagina() {
    }

    public AdmPagina(Integer pagId) {
        this.pagId = pagId;
    }

    public Integer getPagId() {
        return pagId;
    }

    public void setPagId(Integer pagId) {
        this.pagId = pagId;
    }

    public String getPagNombre() {
        return pagNombre;
    }

    public void setPagNombre(String pagNombre) {
        this.pagNombre = pagNombre;
    }

    public String getPagDesc() {
        return pagDesc;
    }

    public void setPagDesc(String pagDesc) {
        this.pagDesc = pagDesc;
    }

    public String getPagRuta() {
        return pagRuta;
    }

    public void setPagRuta(String pagRuta) {
        this.pagRuta = pagRuta;
    }

    public Boolean getPagEst() {
        return pagEst;
    }

    public void setPagEst(Boolean pagEst) {
        this.pagEst = pagEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public AdmSubmodapp getSmdId() {
        return smdId;
    }

    public void setSmdId(AdmSubmodapp smdId) {
        this.smdId = smdId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagId != null ? pagId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmPagina)) {
            return false;
        }
        AdmPagina other = (AdmPagina) object;
        if ((this.pagId == null && other.pagId != null) || (this.pagId != null && !this.pagId.equals(other.pagId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmPagina[ pagId=" + pagId + " ]";
    }
    
}
