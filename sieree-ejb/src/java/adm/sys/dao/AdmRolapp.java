/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "adm_rolapp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmRolapp.findAll", query = "SELECT a FROM AdmRolapp a"),
    @NamedQuery(name = "AdmRolapp.findByRapId", query = "SELECT a FROM AdmRolapp a WHERE a.rapId = :rapId"),
    @NamedQuery(name = "AdmRolapp.findByRapNombre", query = "SELECT a FROM AdmRolapp a WHERE a.rapNombre = :rapNombre"),
    @NamedQuery(name = "AdmRolapp.findByRapDes", query = "SELECT a FROM AdmRolapp a WHERE a.rapDes = :rapDes"),
    @NamedQuery(name = "AdmRolapp.findByRapEst", query = "SELECT a FROM AdmRolapp a WHERE a.rapEst = :rapEst"),
    @NamedQuery(name = "AdmRolapp.findByIndversion", query = "SELECT a FROM AdmRolapp a WHERE a.indversion = :indversion")})
public class AdmRolapp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "rap_id")
    private String rapId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "rap_nombre")
    private String rapNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "rap_des")
    private String rapDes;
    @Column(name = "rap_est")
    private Boolean rapEst;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rapId")
    private List<AdmSubmodappxrolapp> admSubmodappxrolappList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rapId")
    private List<AdmInfxrolapp> admInfxrolappList;

    public AdmRolapp() {
    }

    public AdmRolapp(String rapId) {
        this.rapId = rapId;
    }

    public AdmRolapp(String rapId, String rapNombre, String rapDes) {
        this.rapId = rapId;
        this.rapNombre = rapNombre;
        this.rapDes = rapDes;
    }

    public String getRapId() {
        return rapId;
    }

    public void setRapId(String rapId) {
        this.rapId = rapId;
    }

    public String getRapNombre() {
        return rapNombre;
    }

    public void setRapNombre(String rapNombre) {
        this.rapNombre = rapNombre;
    }

    public String getRapDes() {
        return rapDes;
    }

    public void setRapDes(String rapDes) {
        this.rapDes = rapDes;
    }

    public Boolean getRapEst() {
        return rapEst;
    }

    public void setRapEst(Boolean rapEst) {
        this.rapEst = rapEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<AdmSubmodappxrolapp> getAdmSubmodappxrolappList() {
        return admSubmodappxrolappList;
    }

    public void setAdmSubmodappxrolappList(List<AdmSubmodappxrolapp> admSubmodappxrolappList) {
        this.admSubmodappxrolappList = admSubmodappxrolappList;
    }

    @XmlTransient
    public List<AdmInfxrolapp> getAdmInfxrolappList() {
        return admInfxrolappList;
    }

    public void setAdmInfxrolappList(List<AdmInfxrolapp> admInfxrolappList) {
        this.admInfxrolappList = admInfxrolappList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rapId != null ? rapId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmRolapp)) {
            return false;
        }
        AdmRolapp other = (AdmRolapp) object;
        if ((this.rapId == null && other.rapId != null) || (this.rapId != null && !this.rapId.equals(other.rapId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmRolapp[ rapId=" + rapId + " ]";
    }
    
}
