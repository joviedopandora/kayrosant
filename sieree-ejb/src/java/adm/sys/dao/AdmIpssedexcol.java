/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "adm_ipssedexcol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmIpssedexcol.findAll", query = "SELECT a FROM AdmIpssedexcol a"),
    @NamedQuery(name = "AdmIpssedexcol.findByIsexcolId", query = "SELECT a FROM AdmIpssedexcol a WHERE a.isexcolId = :isexcolId"),
    @NamedQuery(name = "AdmIpssedexcol.findByIsexcolEst", query = "SELECT a FROM AdmIpssedexcol a WHERE a.isexcolEst = :isexcolEst"),
    @NamedQuery(name = "AdmIpssedexcol.findByIndversion", query = "SELECT a FROM AdmIpssedexcol a WHERE a.indversion = :indversion")})
public class AdmIpssedexcol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "isexcol_id")
    private Long isexcolId;
    @Column(name = "isexcol_est")
    private Boolean isexcolEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
  
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    public AdmIpssedexcol() {
    }

    public AdmIpssedexcol(Long isexcolId) {
        this.isexcolId = isexcolId;
    }

    public AdmIpssedexcol(Long isexcolId, int indversion) {
        this.isexcolId = isexcolId;
        this.indversion = indversion;
    }

    public Long getIsexcolId() {
        return isexcolId;
    }

    public void setIsexcolId(Long isexcolId) {
        this.isexcolId = isexcolId;
    }

    public Boolean getIsexcolEst() {
        return isexcolEst;
    }

    public void setIsexcolEst(Boolean isexcolEst) {
        this.isexcolEst = isexcolEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
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
        hash += (isexcolId != null ? isexcolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmIpssedexcol)) {
            return false;
        }
        AdmIpssedexcol other = (AdmIpssedexcol) object;
        if ((this.isexcolId == null && other.isexcolId != null) || (this.isexcolId != null && !this.isexcolId.equals(other.isexcolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.AdmIpssedexcol[ isexcolId=" + isexcolId + " ]";
    }
    
}
