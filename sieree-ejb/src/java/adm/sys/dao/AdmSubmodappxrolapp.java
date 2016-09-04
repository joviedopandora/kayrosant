/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "adm_submodappxrolapp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmSubmodappxrolapp.findAll", query = "SELECT a FROM AdmSubmodappxrolapp a"),
    @NamedQuery(name = "AdmSubmodappxrolapp.findBySxrId", query = "SELECT a FROM AdmSubmodappxrolapp a WHERE a.sxrId = :sxrId"),
    @NamedQuery(name = "AdmSubmodappxrolapp.findBySxrEst", query = "SELECT a FROM AdmSubmodappxrolapp a WHERE a.sxrEst = :sxrEst"),
    @NamedQuery(name = "AdmSubmodappxrolapp.findByIndversion", query = "SELECT a FROM AdmSubmodappxrolapp a WHERE a.indversion = :indversion")})
public class AdmSubmodappxrolapp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sxr_id")
    private Integer sxrId;
    @Column(name = "sxr_est")
    private Boolean sxrEst;
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "smd_id", referencedColumnName = "smd_id")
    @ManyToOne(optional = false)
    private AdmSubmodapp smdId;
    @JoinColumn(name = "rap_id", referencedColumnName = "rap_id")
    @ManyToOne(optional = false)
    private AdmRolapp rapId;

    public AdmSubmodappxrolapp() {
    }

    public AdmSubmodappxrolapp(Integer sxrId) {
        this.sxrId = sxrId;
    }

    public Integer getSxrId() {
        return sxrId;
    }

    public void setSxrId(Integer sxrId) {
        this.sxrId = sxrId;
    }

    public Boolean getSxrEst() {
        return sxrEst;
    }

    public void setSxrEst(Boolean sxrEst) {
        this.sxrEst = sxrEst;
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

    public AdmRolapp getRapId() {
        return rapId;
    }

    public void setRapId(AdmRolapp rapId) {
        this.rapId = rapId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sxrId != null ? sxrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmSubmodappxrolapp)) {
            return false;
        }
        AdmSubmodappxrolapp other = (AdmSubmodappxrolapp) object;
        if ((this.sxrId == null && other.sxrId != null) || (this.sxrId != null && !this.sxrId.equals(other.sxrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmSubmodappxrolapp[ sxrId=" + sxrId + " ]";
    }
    
}
