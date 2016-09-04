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
@Table(name = "sys_esttarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysEsttarea.findAll", query = "SELECT s FROM SysEsttarea s"),
    @NamedQuery(name = "SysEsttarea.findByEtrId", query = "SELECT s FROM SysEsttarea s WHERE s.etrId = :etrId"),
    @NamedQuery(name = "SysEsttarea.findByEtrNombre", query = "SELECT s FROM SysEsttarea s WHERE s.etrNombre = :etrNombre"),
    @NamedQuery(name = "SysEsttarea.findByEtrDesc", query = "SELECT s FROM SysEsttarea s WHERE s.etrDesc = :etrDesc"),
    @NamedQuery(name = "SysEsttarea.findByEtrEst", query = "SELECT s FROM SysEsttarea s WHERE s.etrEst = :etrEst"),
    @NamedQuery(name = "SysEsttarea.findByIndversion", query = "SELECT s FROM SysEsttarea s WHERE s.indversion = :indversion")})
public class SysEsttarea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "etr_id")
    private Integer etrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "etr_nombre")
    private String etrNombre;
    @Size(max = 2147483647)
    @Column(name = "etr_desc")
    private String etrDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "etr_est")
    private boolean etrEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @OneToMany(mappedBy = "etrId")
    private List<SysTarea> sysTareaList;

    public SysEsttarea() {
    }

    public SysEsttarea(Integer etrId) {
        this.etrId = etrId;
    }

    public SysEsttarea(Integer etrId, String etrNombre, boolean etrEst, int indversion) {
        this.etrId = etrId;
        this.etrNombre = etrNombre;
        this.etrEst = etrEst;
        this.indversion = indversion;
    }

    public Integer getEtrId() {
        return etrId;
    }

    public void setEtrId(Integer etrId) {
        this.etrId = etrId;
    }

    public String getEtrNombre() {
        return etrNombre;
    }

    public void setEtrNombre(String etrNombre) {
        this.etrNombre = etrNombre;
    }

    public String getEtrDesc() {
        return etrDesc;
    }

    public void setEtrDesc(String etrDesc) {
        this.etrDesc = etrDesc;
    }

    public boolean getEtrEst() {
        return etrEst;
    }

    public void setEtrEst(boolean etrEst) {
        this.etrEst = etrEst;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (etrId != null ? etrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysEsttarea)) {
            return false;
        }
        SysEsttarea other = (SysEsttarea) object;
        if ((this.etrId == null && other.etrId != null) || (this.etrId != null && !this.etrId.equals(other.etrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.SysEsttarea[ etrId=" + etrId + " ]";
    }
    
}
