/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "adm_tarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmTarea.findAll", query = "SELECT a FROM AdmTarea a"),
    @NamedQuery(name = "AdmTarea.findByTarId", query = "SELECT a FROM AdmTarea a WHERE a.tarId = :tarId"),
    @NamedQuery(name = "AdmTarea.findByTarNombre", query = "SELECT a FROM AdmTarea a WHERE a.tarNombre = :tarNombre"),
    @NamedQuery(name = "AdmTarea.findByTarDesc", query = "SELECT a FROM AdmTarea a WHERE a.tarDesc = :tarDesc"),
    @NamedQuery(name = "AdmTarea.findByTarEst", query = "SELECT a FROM AdmTarea a WHERE a.tarEst = :tarEst"),
    @NamedQuery(name = "AdmTarea.findByIndversion", query = "SELECT a FROM AdmTarea a WHERE a.indversion = :indversion"),
//Consultas propias
    @NamedQuery(name="AdmTarea.tarXAct",query="SELECT a FROM AdmTarea a  JOIN a.actId act "
        + "WHERE act.actId = :actId ORDER BY a.tarNombre ")
})
public class AdmTarea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "tar_id")
    private String tarId;
    @Size(max = 2000)
    @Column(name = "tar_nombre")
    private String tarNombre;
    @Size(max = 2147483647)
    @Column(name = "tar_desc")
    private String tarDesc;
    @Column(name = "tar_est")
    private Boolean tarEst;
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "act_id", referencedColumnName = "act_id")
    @ManyToOne
    private AdmActividad actId;

    public AdmTarea() {
    }

    public AdmTarea(String tarId) {
        this.tarId = tarId;
    }

    public String getTarId() {
        return tarId;
    }

    public void setTarId(String tarId) {
        this.tarId = tarId;
    }

    public String getTarNombre() {
        return tarNombre;
    }

    public void setTarNombre(String tarNombre) {
        this.tarNombre = tarNombre;
    }

    public String getTarDesc() {
        return tarDesc;
    }

    public void setTarDesc(String tarDesc) {
        this.tarDesc = tarDesc;
    }

    public Boolean getTarEst() {
        return tarEst;
    }

    public void setTarEst(Boolean tarEst) {
        this.tarEst = tarEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
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
        hash += (tarId != null ? tarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmTarea)) {
            return false;
        }
        AdmTarea other = (AdmTarea) object;
        if ((this.tarId == null && other.tarId != null) || (this.tarId != null && !this.tarId.equals(other.tarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmTarea[ tarId=" + tarId + " ]";
    }
    
}
