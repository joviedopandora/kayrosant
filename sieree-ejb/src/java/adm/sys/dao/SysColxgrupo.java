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
@Table(name = "sys_colxgrupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysColxgrupo.findAll", query = "SELECT s FROM SysColxgrupo s"),
    @NamedQuery(name = "SysColxgrupo.findByCxgId", query = "SELECT s FROM SysColxgrupo s WHERE s.cxgId = :cxgId"),
    @NamedQuery(name = "SysColxgrupo.findByCxgEst", query = "SELECT s FROM SysColxgrupo s WHERE s.cxgEst = :cxgEst"),
    @NamedQuery(name = "SysColxgrupo.findByIndversion", query = "SELECT s FROM SysColxgrupo s WHERE s.indversion = :indversion"),
//Obtener colxgrupo a partir de grupo
    @NamedQuery(name="SysColxgrupo.cxgXGrupo",query="SELECT s FROM SysColxgrupo s JOIN s.sgrId sgr WHERE sgr.sgrId = :sgrId")})
public class SysColxgrupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cxg_id")
    private Integer cxgId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cxg_est")
    private boolean cxgEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @JoinColumn(name = "sgr_id", referencedColumnName = "sgr_id")
    @ManyToOne
    private SysGrupo sgrId;
   @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    public SysColxgrupo() {
    }

    public SysColxgrupo(Integer cxgId) {
        this.cxgId = cxgId;
    }

    public SysColxgrupo(Integer cxgId, boolean cxgEst, int indversion) {
        this.cxgId = cxgId;
        this.cxgEst = cxgEst;
        this.indversion = indversion;
    }

    public Integer getCxgId() {
        return cxgId;
    }

    public void setCxgId(Integer cxgId) {
        this.cxgId = cxgId;
    }

    public boolean getCxgEst() {
        return cxgEst;
    }

    public void setCxgEst(boolean cxgEst) {
        this.cxgEst = cxgEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    public SysGrupo getSgrId() {
        return sgrId;
    }

    public void setSgrId(SysGrupo sgrId) {
        this.sgrId = sgrId;
    }

  
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxgId != null ? cxgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysColxgrupo)) {
            return false;
        }
        SysColxgrupo other = (SysColxgrupo) object;
        if ((this.cxgId == null && other.cxgId != null) || (this.cxgId != null && !this.cxgId.equals(other.cxgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.SysColxgrupo[ cxgId=" + cxgId + " ]";
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
