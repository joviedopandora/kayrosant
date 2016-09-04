/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import adm.sys.dao.AdmColxemp;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "adm_cpexsubmodapp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmCpexsubmodapp.findAll", query = "SELECT a FROM AdmCpexsubmodapp a"),
    @NamedQuery(name = "AdmCpexsubmodapp.findByCxmId", query = "SELECT a FROM AdmCpexsubmodapp a WHERE a.cxmId = :cxmId"),
    @NamedQuery(name = "AdmCpexsubmodapp.findByCxmEst", query = "SELECT a FROM AdmCpexsubmodapp a WHERE a.cxmEst = :cxmEst"),
    @NamedQuery(name = "AdmCpexsubmodapp.findByIndversion", query = "SELECT a FROM AdmCpexsubmodapp a WHERE a.indversion = :indversion")})
public class AdmCpexsubmodapp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cxm_id")
    private Integer cxmId;
    @Column(name = "cxm_est")
    private Boolean cxmEst;
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "smd_id", referencedColumnName = "smd_id")
    @ManyToOne
    private AdmSubmodapp smdId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    public AdmCpexsubmodapp() {
    }

    public AdmCpexsubmodapp(Integer cxmId) {
        this.cxmId = cxmId;
    }

    public Integer getCxmId() {
        return cxmId;
    }

    public void setCxmId(Integer cxmId) {
        this.cxmId = cxmId;
    }

    public Boolean getCxmEst() {
        return cxmEst;
    }

    public void setCxmEst(Boolean cxmEst) {
        this.cxmEst = cxmEst;
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
        hash += (cxmId != null ? cxmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmCpexsubmodapp)) {
            return false;
        }
        AdmCpexsubmodapp other = (AdmCpexsubmodapp) object;
        if ((this.cxmId == null && other.cxmId != null) || (this.cxmId != null && !this.cxmId.equals(other.cxmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmCpexsubmodapp[ cxmId=" + cxmId + " ]";
    }

    /**
     * @return the cxcId
     */
    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    /**
     * @param cxcId the cxcId to set
     */
    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }
}
