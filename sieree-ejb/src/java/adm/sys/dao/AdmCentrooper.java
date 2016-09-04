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
@Table(name = "adm_centrooper")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmCentrooper.findAll", query = "SELECT a FROM AdmCentrooper a"),
    @NamedQuery(name = "AdmCentrooper.findByCopId", query = "SELECT a FROM AdmCentrooper a WHERE a.copId = :copId"),
    @NamedQuery(name = "AdmCentrooper.findByCopNombre", query = "SELECT a FROM AdmCentrooper a WHERE a.copNombre = :copNombre"),
    @NamedQuery(name = "AdmCentrooper.findByCopDesc", query = "SELECT a FROM AdmCentrooper a WHERE a.copDesc = :copDesc"),
    @NamedQuery(name = "AdmCentrooper.findByCopEst", query = "SELECT a FROM AdmCentrooper a WHERE a.copEst = :copEst")})
public class AdmCentrooper implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cop_id")
    private Integer copId;
    @Size(max = 500)
    @Column(name = "cop_nombre")
    private String copNombre;
    @Size(max = 2147483647)
    @Column(name = "cop_desc")
    private String copDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cop_est")
    private boolean copEst;
    @JoinColumn(name = "sed_id", referencedColumnName = "sed_id")
    @ManyToOne
    private AdmSede sedId;
    @OneToMany(mappedBy = "copId")
    private List<AdmColxemp> admColxempList;

    public AdmCentrooper() {
    }

    public AdmCentrooper(Integer copId) {
        this.copId = copId;
    }

    public AdmCentrooper(Integer copId, boolean copEst) {
        this.copId = copId;
        this.copEst = copEst;
    }

    public Integer getCopId() {
        return copId;
    }

    public void setCopId(Integer copId) {
        this.copId = copId;
    }

    public String getCopNombre() {
        return copNombre;
    }

    public void setCopNombre(String copNombre) {
        this.copNombre = copNombre;
    }

    public String getCopDesc() {
        return copDesc;
    }

    public void setCopDesc(String copDesc) {
        this.copDesc = copDesc;
    }

    public boolean getCopEst() {
        return copEst;
    }

    public void setCopEst(boolean copEst) {
        this.copEst = copEst;
    }

    public AdmSede getSedId() {
        return sedId;
    }

    public void setSedId(AdmSede sedId) {
        this.sedId = sedId;
    }

    @XmlTransient
    public List<AdmColxemp> getAdmColxempList() {
        return admColxempList;
    }

    public void setAdmColxempList(List<AdmColxemp> admColxempList) {
        this.admColxempList = admColxempList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (copId != null ? copId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmCentrooper)) {
            return false;
        }
        AdmCentrooper other = (AdmCentrooper) object;
        if ((this.copId == null && other.copId != null) || (this.copId != null && !this.copId.equals(other.copId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.AdmCentrooper[ copId=" + copId + " ]";
    }
    
}
