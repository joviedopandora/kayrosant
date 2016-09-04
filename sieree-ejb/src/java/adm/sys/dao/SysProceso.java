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
@Table(name = "sys_proceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysProceso.findAll", query = "SELECT s FROM SysProceso s"),
    @NamedQuery(name = "SysProceso.findBySprId", query = "SELECT s FROM SysProceso s WHERE s.sprId = :sprId"),
    @NamedQuery(name = "SysProceso.findBySprNombre", query = "SELECT s FROM SysProceso s WHERE s.sprNombre = :sprNombre"),
    @NamedQuery(name = "SysProceso.findBySprDesc", query = "SELECT s FROM SysProceso s WHERE s.sprDesc = :sprDesc"),
    @NamedQuery(name = "SysProceso.findBySprEst", query = "SELECT s FROM SysProceso s WHERE s.sprEst = :sprEst"),
    @NamedQuery(name = "SysProceso.findByIndversion", query = "SELECT s FROM SysProceso s WHERE s.indversion = :indversion")})
public class SysProceso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "spr_id")
    private String sprId;
    @Size(max = 200)
    @Column(name = "spr_nombre")
    private String sprNombre;
    @Size(max = 2147483647)
    @Column(name = "spr_desc")
    private String sprDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "spr_est")
    private boolean sprEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @OneToMany(mappedBy = "sprId")
    private List<SysTarea> sysTareaList;
    @OneToMany(mappedBy = "sprId")
    private List<SysPropaso> sysPropasoList;

    public SysProceso() {
    }

    public SysProceso(String sprId) {
        this.sprId = sprId;
    }

    public SysProceso(String sprId, boolean sprEst, int indversion) {
        this.sprId = sprId;
        this.sprEst = sprEst;
        this.indversion = indversion;
    }

    public String getSprId() {
        return sprId;
    }

    public void setSprId(String sprId) {
        this.sprId = sprId;
    }

    public String getSprNombre() {
        return sprNombre;
    }

    public void setSprNombre(String sprNombre) {
        this.sprNombre = sprNombre;
    }

    public String getSprDesc() {
        return sprDesc;
    }

    public void setSprDesc(String sprDesc) {
        this.sprDesc = sprDesc;
    }

    public boolean getSprEst() {
        return sprEst;
    }

    public void setSprEst(boolean sprEst) {
        this.sprEst = sprEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<SysTarea> getSysTareaList() {
        return sysTareaList;
    }

    public void setSysTareaList(List<SysTarea> sysTareaList) {
        this.sysTareaList = sysTareaList;
    }

    @XmlTransient
    public List<SysPropaso> getSysPropasoList() {
        return sysPropasoList;
    }

    public void setSysPropasoList(List<SysPropaso> sysPropasoList) {
        this.sysPropasoList = sysPropasoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprId != null ? sprId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysProceso)) {
            return false;
        }
        SysProceso other = (SysProceso) object;
        if ((this.sprId == null && other.sprId != null) || (this.sprId != null && !this.sprId.equals(other.sprId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.SysProceso[ sprId=" + sprId + " ]";
    }
    
}
