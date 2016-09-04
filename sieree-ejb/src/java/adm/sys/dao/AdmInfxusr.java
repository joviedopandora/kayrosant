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
@Table(name = "adm_infxusr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmInfxusr.findAll", query = "SELECT a FROM AdmInfxusr a"),
    @NamedQuery(name = "AdmInfxusr.findByInuId", query = "SELECT a FROM AdmInfxusr a WHERE a.inuId = :inuId"),
    @NamedQuery(name = "AdmInfxusr.findByInuEst", query = "SELECT a FROM AdmInfxusr a WHERE a.inuEst = :inuEst"),
    @NamedQuery(name = "AdmInfxusr.findByIndversion", query = "SELECT a FROM AdmInfxusr a WHERE a.indversion = :indversion")})
public class AdmInfxusr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "inu_id")
    private Integer inuId;
    @Column(name = "inu_est")
    private Boolean inuEst;
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "inf_id", referencedColumnName = "inf_id")
    @ManyToOne(optional = false)
    private AdmInforme infId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    public AdmInfxusr() {
    }

    public AdmInfxusr(Integer inuId) {
        this.inuId = inuId;
    }

    public Integer getInuId() {
        return inuId;
    }

    public void setInuId(Integer inuId) {
        this.inuId = inuId;
    }

    public Boolean getInuEst() {
        return inuEst;
    }

    public void setInuEst(Boolean inuEst) {
        this.inuEst = inuEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public AdmInforme getInfId() {
        return infId;
    }

    public void setInfId(AdmInforme infId) {
        this.infId = infId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inuId != null ? inuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmInfxusr)) {
            return false;
        }
        AdmInfxusr other = (AdmInfxusr) object;
        if ((this.inuId == null && other.inuId != null) || (this.inuId != null && !this.inuId.equals(other.inuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmInfxusr[ inuId=" + inuId + " ]";
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
