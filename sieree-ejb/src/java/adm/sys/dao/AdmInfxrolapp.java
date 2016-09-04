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
@Table(name = "adm_infxrolapp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmInfxrolapp.findAll", query = "SELECT a FROM AdmInfxrolapp a"),
    @NamedQuery(name = "AdmInfxrolapp.findByInrId", query = "SELECT a FROM AdmInfxrolapp a WHERE a.inrId = :inrId"),
    @NamedQuery(name = "AdmInfxrolapp.findByInrEst", query = "SELECT a FROM AdmInfxrolapp a WHERE a.inrEst = :inrEst"),
    @NamedQuery(name = "AdmInfxrolapp.findByIndversion", query = "SELECT a FROM AdmInfxrolapp a WHERE a.indversion = :indversion")})
public class AdmInfxrolapp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "inr_id")
    private Integer inrId;
    @Column(name = "inr_est")
    private Boolean inrEst;
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "rap_id", referencedColumnName = "rap_id")
    @ManyToOne(optional = false)
    private AdmRolapp rapId;
    @JoinColumn(name = "inf_id", referencedColumnName = "inf_id")
    @ManyToOne(optional = false)
    private AdmInforme infId;

    public AdmInfxrolapp() {
    }

    public AdmInfxrolapp(Integer inrId) {
        this.inrId = inrId;
    }

    public Integer getInrId() {
        return inrId;
    }

    public void setInrId(Integer inrId) {
        this.inrId = inrId;
    }

    public Boolean getInrEst() {
        return inrEst;
    }

    public void setInrEst(Boolean inrEst) {
        this.inrEst = inrEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public AdmRolapp getRapId() {
        return rapId;
    }

    public void setRapId(AdmRolapp rapId) {
        this.rapId = rapId;
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
        hash += (inrId != null ? inrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmInfxrolapp)) {
            return false;
        }
        AdmInfxrolapp other = (AdmInfxrolapp) object;
        if ((this.inrId == null && other.inrId != null) || (this.inrId != null && !this.inrId.equals(other.inrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmInfxrolapp[ inrId=" + inrId + " ]";
    }
    
}
