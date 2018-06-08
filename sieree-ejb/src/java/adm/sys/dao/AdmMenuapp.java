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
@Table(name = "adm_menuapp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmMenuapp.findAll", query = "SELECT a FROM AdmMenuapp a ORDER BY a.menNombre"),
    @NamedQuery(name = "AdmMenuapp.findByMenId", query = "SELECT a FROM AdmMenuapp a WHERE a.menId = :menId"),
    @NamedQuery(name = "AdmMenuapp.findByMenNombre", query = "SELECT a FROM AdmMenuapp a WHERE a.menNombre = :menNombre"),
    @NamedQuery(name = "AdmMenuapp.findByMenDesc", query = "SELECT a FROM AdmMenuapp a WHERE a.menDesc = :menDesc"),
    @NamedQuery(name = "AdmMenuapp.findByMenEst", query = "SELECT a FROM AdmMenuapp a WHERE a.menEst = :menEst"),
    @NamedQuery(name = "AdmMenuapp.findByIndversion", query = "SELECT a FROM AdmMenuapp a WHERE a.indversion = :indversion"),
    @NamedQuery(name = "AdmMenuapp.menuAppXCol", query = "SELECT menu FROM AdmMenuapp menu WHERE "
            + "menu.menId IN(SELECT DISTINCT m.menId FROM AdmMenuapp m JOIN m.admModappList modApp JOIN "
            + "modApp.admSubmodappList smApp JOIN smApp.admCpexsubmodappList cpeXSApp JOIN cpeXSApp.cxcId cxc "
            + "WHERE cxc.cxcId = :cxcId  )")})
public class AdmMenuapp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "men_id")
    private Integer menId;
    @Size(max = 20)
    @Column(name = "men_nombre")
    private String menNombre;
    @Size(max = 100)
    @Column(name = "men_desc")
    private String menDesc;
    @Column(name = "men_est")
    private Boolean menEst;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(mappedBy = "menId")
    private List<AdmModapp> admModappList;

    public AdmMenuapp() {
    }

    public AdmMenuapp(Integer menId) {
        this.menId = menId;
    }

    public Integer getMenId() {
        return menId;
    }

    public void setMenId(Integer menId) {
        this.menId = menId;
    }

    public String getMenNombre() {
        return menNombre;
    }

    public void setMenNombre(String menNombre) {
        this.menNombre = menNombre;
    }

    public String getMenDesc() {
        return menDesc;
    }

    public void setMenDesc(String menDesc) {
        this.menDesc = menDesc;
    }

    public Boolean getMenEst() {
        return menEst;
    }

    public void setMenEst(Boolean menEst) {
        this.menEst = menEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<AdmModapp> getAdmModappList() {
        return admModappList;
    }

    public void setAdmModappList(List<AdmModapp> admModappList) {
        this.admModappList = admModappList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menId != null ? menId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmMenuapp)) {
            return false;
        }
        AdmMenuapp other = (AdmMenuapp) object;
        if ((this.menId == null && other.menId != null) || (this.menId != null && !this.menId.equals(other.menId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmMenuapp[ menId=" + menId + " ]";
    }

}
