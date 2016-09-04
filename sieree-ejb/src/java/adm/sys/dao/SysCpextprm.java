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
 * @author lfchacon
 */
@Entity
@Table(name = "sys_cpextprm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysCpextprm.findAll", query = "SELECT s FROM SysCpextprm s"),
    @NamedQuery(name = "SysCpextprm.findByCxpId", query = "SELECT s FROM SysCpextprm s WHERE s.cxpId = :cxpId"),
    @NamedQuery(name = "SysCpextprm.findByCxpEst", query = "SELECT s FROM SysCpextprm s WHERE s.cxpEst = :cxpEst"),
    @NamedQuery(name = "SysCpextprm.findByIndversion", query = "SELECT s FROM SysCpextprm s WHERE s.indversion = :indversion")})
public class SysCpextprm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cxp_id")
    private Integer cxpId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cxp_est")
    private boolean cxpEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @JoinColumn(name = "spm_id", referencedColumnName = "spm_id")
    @ManyToOne
    private SysTablapararm spmId;
    @JoinColumn(name = "cpe_id", referencedColumnName = "cpe_id")
    @ManyToOne
    private AdmColxemp cpeId;

    public SysCpextprm() {
    }

    public SysCpextprm(Integer cxpId) {
        this.cxpId = cxpId;
    }

    public SysCpextprm(Integer cxpId, boolean cxpEst, int indversion) {
        this.cxpId = cxpId;
        this.cxpEst = cxpEst;
        this.indversion = indversion;
    }

    public Integer getCxpId() {
        return cxpId;
    }

    public void setCxpId(Integer cxpId) {
        this.cxpId = cxpId;
    }

    public boolean getCxpEst() {
        return cxpEst;
    }

    public void setCxpEst(boolean cxpEst) {
        this.cxpEst = cxpEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    public SysTablapararm getSpmId() {
        return spmId;
    }

    public void setSpmId(SysTablapararm spmId) {
        this.spmId = spmId;
    }

    public AdmColxemp getCpeId() {
        return cpeId;
    }

    public void setCpeId(AdmColxemp cpeId) {
        this.cpeId = cpeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxpId != null ? cxpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysCpextprm)) {
            return false;
        }
        SysCpextprm other = (SysCpextprm) object;
        if ((this.cxpId == null && other.cxpId != null) || (this.cxpId != null && !this.cxpId.equals(other.cxpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.SysCpextprm[ cxpId=" + cxpId + " ]";
    }
    
}
