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
@Table(name = "adm_actxcargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmActxcargo.findAll", query = "SELECT a FROM AdmActxcargo a"),
    @NamedQuery(name = "AdmActxcargo.findByAxcId", query = "SELECT a FROM AdmActxcargo a WHERE a.axcId = :axcId"),
    @NamedQuery(name = "AdmActxcargo.findByAxcEst", query = "SELECT a FROM AdmActxcargo a WHERE a.axcEst = :axcEst"),
    @NamedQuery(name = "AdmActxcargo.findByIndversion", query = "SELECT a FROM AdmActxcargo a WHERE a.indversion = :indversion")})
public class AdmActxcargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "axc_id")
    private Integer axcId;
    @Column(name = "axc_est")
    private Boolean axcEst;
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "crg_id", referencedColumnName = "crg_id")
    @ManyToOne
    private AdmCargo crgId;
    @JoinColumn(name = "act_id", referencedColumnName = "act_id")
    @ManyToOne
    private AdmActividad actId;

    public AdmActxcargo() {
    }

    public AdmActxcargo(Integer axcId) {
        this.axcId = axcId;
    }

    public Integer getAxcId() {
        return axcId;
    }

    public void setAxcId(Integer axcId) {
        this.axcId = axcId;
    }

    public Boolean getAxcEst() {
        return axcEst;
    }

    public void setAxcEst(Boolean axcEst) {
        this.axcEst = axcEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public AdmCargo getCrgId() {
        return crgId;
    }

    public void setCrgId(AdmCargo crgId) {
        this.crgId = crgId;
    }

    public AdmActividad getActId() {
        return actId;
    }

    public void setActId(AdmActividad actId) {
        this.actId = actId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (axcId != null ? axcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmActxcargo)) {
            return false;
        }
        AdmActxcargo other = (AdmActxcargo) object;
        if ((this.axcId == null && other.axcId != null) || (this.axcId != null && !this.axcId.equals(other.axcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmActxcargo[ axcId=" + axcId + " ]";
    }
    
}
