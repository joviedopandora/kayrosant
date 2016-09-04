/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

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
 * @author Breyner Robles
 */
@Entity
@Table(name = "vnt_rangoedadsexo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntRangoedadsexo.findRangoEdadYSexo", query = "SELECT v FROM VntRangoedadsexo v WHERE :edad >= v.rgedadsexEdadmin   AND :edad <= v.rgedadsexEdadmax AND v.rgedadsexSex = :rgedadsexSex ORDER BY v.rgedadsexEdadmin,v.rgedadsexEdadmax  "),
    @NamedQuery(name = "VntRangoedadsexo.findAll", query = "SELECT v FROM VntRangoedadsexo v"),
    @NamedQuery(name = "VntRangoedadsexo.findByRgedadsexId", query = "SELECT v FROM VntRangoedadsexo v WHERE v.rgedadsexId = :rgedadsexId"),
    @NamedQuery(name = "VntRangoedadsexo.findByRgedadsexSex", query = "SELECT v FROM VntRangoedadsexo v WHERE v.rgedadsexSex = :rgedadsexSex"),
    @NamedQuery(name = "VntRangoedadsexo.findByRgedadsexPdf", query = "SELECT v FROM VntRangoedadsexo v WHERE v.rgedadsexPdf = :rgedadsexPdf"),
    @NamedQuery(name = "VntRangoedadsexo.findByRgedadsexEdadmin", query = "SELECT v FROM VntRangoedadsexo v WHERE v.rgedadsexEdadmin = :rgedadsexEdadmin"),
    @NamedQuery(name = "VntRangoedadsexo.findByRgedadsexEdadmax", query = "SELECT v FROM VntRangoedadsexo v WHERE v.rgedadsexEdadmax = :rgedadsexEdadmax")})
public class VntRangoedadsexo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rgedadsex_id")
    private Integer rgedadsexId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "rgedadsex_sex")
    private String rgedadsexSex;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "rgedadsex_pdf")
    private String rgedadsexPdf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rgedadsex_edadmin")
    private int rgedadsexEdadmin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rgedadsex_edadmax")
    private int rgedadsexEdadmax;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vntRangoedadsexo", fetch = FetchType.LAZY)
    private List<VntRangoedadsexxservicio> vntRangoedadsexxservicioList;

    public VntRangoedadsexo() {
    }

    public VntRangoedadsexo(Integer rgedadsexId) {
        this.rgedadsexId = rgedadsexId;
    }

    public VntRangoedadsexo(Integer rgedadsexId, String rgedadsexSex, String rgedadsexPdf, int rgedadsexEdadmin, int rgedadsexEdadmax) {
        this.rgedadsexId = rgedadsexId;
        this.rgedadsexSex = rgedadsexSex;
        this.rgedadsexPdf = rgedadsexPdf;
        this.rgedadsexEdadmin = rgedadsexEdadmin;
        this.rgedadsexEdadmax = rgedadsexEdadmax;
    }

    public Integer getRgedadsexId() {
        return rgedadsexId;
    }

    public void setRgedadsexId(Integer rgedadsexId) {
        this.rgedadsexId = rgedadsexId;
    }

    public String getRgedadsexSex() {
        return rgedadsexSex;
    }

    public void setRgedadsexSex(String rgedadsexSex) {
        this.rgedadsexSex = rgedadsexSex;
    }

    public String getRgedadsexPdf() {
        return rgedadsexPdf;
    }

    public void setRgedadsexPdf(String rgedadsexPdf) {
        this.rgedadsexPdf = rgedadsexPdf;
    }

    public int getRgedadsexEdadmin() {
        return rgedadsexEdadmin;
    }

    public void setRgedadsexEdadmin(int rgedadsexEdadmin) {
        this.rgedadsexEdadmin = rgedadsexEdadmin;
    }

    public int getRgedadsexEdadmax() {
        return rgedadsexEdadmax;
    }

    public void setRgedadsexEdadmax(int rgedadsexEdadmax) {
        this.rgedadsexEdadmax = rgedadsexEdadmax;
    }

    @XmlTransient
    public List<VntRangoedadsexxservicio> getVntRangoedadsexxservicioList() {
        return vntRangoedadsexxservicioList;
    }

    public void setVntRangoedadsexxservicioList(List<VntRangoedadsexxservicio> vntRangoedadsexxservicioList) {
        this.vntRangoedadsexxservicioList = vntRangoedadsexxservicioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rgedadsexId != null ? rgedadsexId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntRangoedadsexo)) {
            return false;
        }
        VntRangoedadsexo other = (VntRangoedadsexo) object;
        if ((this.rgedadsexId == null && other.rgedadsexId != null) || (this.rgedadsexId != null && !this.rgedadsexId.equals(other.rgedadsexId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.VntRangoedadsexo[ rgedadsexId=" + rgedadsexId + " ]";
    }
}
