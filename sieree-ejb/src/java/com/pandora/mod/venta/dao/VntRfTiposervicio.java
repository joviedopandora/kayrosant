/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author luis
 */
@Entity
@Table(name = "vnt_rf_tiposervicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntRfTiposervicio.findAll", query = "SELECT v FROM VntRfTiposervicio v"),
    @NamedQuery(name = "VntRfTiposervicio.findByTsrvId", query = "SELECT v FROM VntRfTiposervicio v WHERE v.tsrvId = :tsrvId"),
    @NamedQuery(name = "VntRfTiposervicio.findByTsrvNombre", query = "SELECT v FROM VntRfTiposervicio v WHERE v.tsrvNombre = :tsrvNombre"),
    @NamedQuery(name = "VntRfTiposervicio.findByTsrvDesc", query = "SELECT v FROM VntRfTiposervicio v WHERE v.tsrvDesc = :tsrvDesc"),
    @NamedQuery(name = "VntRfTiposervicio.findByTsrvEst", query = "SELECT v FROM VntRfTiposervicio v WHERE v.tsrvEst = :tsrvEst ORDER BY v.tsrvNombre "),
    @NamedQuery(name = "VntRfTiposervicio.findByIndversion", query = "SELECT v FROM VntRfTiposervicio v WHERE v.indversion = :indversion")})
public class VntRfTiposervicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tsrv_id")
    private Integer tsrvId;
    @Size(max = 500)
    @Column(name = "tsrv_nombre")
    private String tsrvNombre;
    @Size(max = 2147483647)
    @Column(name = "tsrv_desc")
    private String tsrvDesc;
    @Column(name = "tsrv_est")
    private Boolean tsrvEst;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(mappedBy = "tsrvId")
    private List<VntServicio> vntServicioList;

    public VntRfTiposervicio() {
    }

    public VntRfTiposervicio(Integer tsrvId) {
        this.tsrvId = tsrvId;
    }

    public Integer getTsrvId() {
        return tsrvId;
    }

    public void setTsrvId(Integer tsrvId) {
        this.tsrvId = tsrvId;
    }

    public String getTsrvNombre() {
        return tsrvNombre;
    }

    public void setTsrvNombre(String tsrvNombre) {
        this.tsrvNombre = tsrvNombre;
    }

    public String getTsrvDesc() {
        return tsrvDesc;
    }

    public void setTsrvDesc(String tsrvDesc) {
        this.tsrvDesc = tsrvDesc;
    }

    public Boolean getTsrvEst() {
        return tsrvEst;
    }

    public void setTsrvEst(Boolean tsrvEst) {
        this.tsrvEst = tsrvEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<VntServicio> getVntServicioList() {
        return vntServicioList;
    }

    public void setVntServicioList(List<VntServicio> vntServicioList) {
        this.vntServicioList = vntServicioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tsrvId != null ? tsrvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntRfTiposervicio)) {
            return false;
        }
        VntRfTiposervicio other = (VntRfTiposervicio) object;
        if ((this.tsrvId == null && other.tsrvId != null) || (this.tsrvId != null && !this.tsrvId.equals(other.tsrvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntRfTiposervicio[ tsrvId=" + tsrvId + " ]";
    }
    
}
