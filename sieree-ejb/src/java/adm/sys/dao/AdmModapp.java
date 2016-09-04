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
@Table(name = "adm_modapp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmModapp.findAll", query = "SELECT a FROM AdmModapp a"),
    @NamedQuery(name = "AdmModapp.findByModId", query = "SELECT a FROM AdmModapp a WHERE a.modId = :modId"),
    @NamedQuery(name = "AdmModapp.findByModNombre", query = "SELECT a FROM AdmModapp a WHERE a.modNombre = :modNombre"),
    @NamedQuery(name = "AdmModapp.findByModDes", query = "SELECT a FROM AdmModapp a WHERE a.modDes = :modDes"),
    @NamedQuery(name = "AdmModapp.findByModEst", query = "SELECT a FROM AdmModapp a WHERE a.modEst = :modEst"),
    @NamedQuery(name = "AdmModapp.findByIndversion", query = "SELECT a FROM AdmModapp a WHERE a.indversion = :indversion"),
    @NamedQuery(name = "AdmModapp.findByModCarp", query = "SELECT a FROM AdmModapp a WHERE a.modCarp = :modCarp"),
//Obtener modulos de la aplicación por menú
    @NamedQuery(name = "AdmModapp.getModappXMenu", query = "SELECT m FROM AdmModapp m JOIN m.menId menu "
    + " WHERE menu.menId = :menId "
    + " ORDER BY m.modNombre ")})
public class AdmModapp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mod_id")
    private Integer modId;
    @Size(max = 40)
    @Column(name = "mod_nombre")
    private String modNombre;
    @Size(max = 100)
    @Column(name = "mod_des")
    private String modDes;
    @Column(name = "mod_est")
    private Boolean modEst;
    @Column(name = "indversion")
    private Integer indversion;
    @Size(max = 300)
    @Column(name = "mod_carp")
    private String modCarp;
    @OneToMany(mappedBy = "modId")
    private List<AdmSubmodapp> admSubmodappList;
    @JoinColumn(name = "men_id", referencedColumnName = "men_id")
    @ManyToOne
    private AdmMenuapp menId;

    public AdmModapp() {
    }

    public AdmModapp(Integer modId) {
        this.modId = modId;
    }

    public Integer getModId() {
        return modId;
    }

    public void setModId(Integer modId) {
        this.modId = modId;
    }

    public String getModNombre() {
        return modNombre;
    }

    public void setModNombre(String modNombre) {
        this.modNombre = modNombre;
    }

    public String getModDes() {
        return modDes;
    }

    public void setModDes(String modDes) {
        this.modDes = modDes;
    }

    public Boolean getModEst() {
        return modEst;
    }

    public void setModEst(Boolean modEst) {
        this.modEst = modEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public String getModCarp() {
        return modCarp;
    }

    public void setModCarp(String modCarp) {
        this.modCarp = modCarp;
    }

    @XmlTransient
    public List<AdmSubmodapp> getAdmSubmodappList() {
        return admSubmodappList;
    }

    public void setAdmSubmodappList(List<AdmSubmodapp> admSubmodappList) {
        this.admSubmodappList = admSubmodappList;
    }

    public AdmMenuapp getMenId() {
        return menId;
    }

    public void setMenId(AdmMenuapp menId) {
        this.menId = menId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modId != null ? modId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmModapp)) {
            return false;
        }
        AdmModapp other = (AdmModapp) object;
        if ((this.modId == null && other.modId != null) || (this.modId != null && !this.modId.equals(other.modId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmModapp[ modId=" + modId + " ]";
    }
    
}
