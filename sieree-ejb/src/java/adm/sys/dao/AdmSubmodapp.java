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
@Table(name = "adm_submodapp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmSubmodapp.findAll", query = "SELECT a FROM AdmSubmodapp a")
    ,
    @NamedQuery(name = "AdmSubmodapp.findBySmdId", query = "SELECT a FROM AdmSubmodapp a WHERE a.smdId = :smdId")
    ,
    @NamedQuery(name = "AdmSubmodapp.findBySmdNombre", query = "SELECT a FROM AdmSubmodapp a WHERE a.smdNombre = :smdNombre")
    ,
    @NamedQuery(name = "AdmSubmodapp.findBySmdDesc", query = "SELECT a FROM AdmSubmodapp a WHERE a.smdDesc = :smdDesc")
    ,
    @NamedQuery(name = "AdmSubmodapp.findBySmdEst", query = "SELECT a FROM AdmSubmodapp a WHERE a.smdEst = :smdEst")
    ,
    @NamedQuery(name = "AdmSubmodapp.findByIndversion", query = "SELECT a FROM AdmSubmodapp a WHERE a.indversion = :indversion")
    ,
    @NamedQuery(name = "AdmSubmodapp.findBySmdReglanav", query = "SELECT a FROM AdmSubmodapp a WHERE a.smdReglanav = :smdReglanav")
    ,
    @NamedQuery(name = "AdmSubmodapp.findBySmdJsfbean", query = "SELECT a FROM AdmSubmodapp a WHERE a.smdJsfbean = :smdJsfbean")
    ,
    @NamedQuery(name = "AdmSubmodapp.findBySmdRutarecrs", query = "SELECT a FROM AdmSubmodapp a WHERE a.smdRutarecrs = :smdRutarecrs")
    ,
    @NamedQuery(name = "AdmSubmodapp.getSubmodXModXCpe", 
            query = "SELECT s FROM AdmSubmodapp s JOIN s.admCpexsubmodappList cpexs JOIN cpexs.cxcId cxc JOIN s.modId m WHERE cxc.cxcId = :cxcId AND m.modId = :modId AND cxc.cxcPrincipal = :cxcPrincipal")
    ,
@NamedQuery(name = "AdmSubmodapp.findBySmdXMod", query = "SELECT a FROM AdmSubmodapp a  JOIN a.modId modulo WHERE modulo.modId = :modId ORDER BY modulo.modNombre "),
@NamedQuery(name = "AdmSubmodapp.getSubmodXCpe", query = "SELECT s FROM AdmSubmodapp s JOIN s.modId modulo "
        + " JOIN s.admCpexsubmodappList cpexs JOIN cpexs.cxcId cxc JOIN s.modId m WHERE cxc.cpeId.cpeId = :cpeId ORDER BY modulo.modNombre, s.smdNombre")
})
public class AdmSubmodapp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "smd_id")
    private Integer smdId;
    @Size(max = 100)
    @Column(name = "smd_nombre")
    private String smdNombre;
    @Size(max = 100)
    @Column(name = "smd_desc")
    private String smdDesc;
    @Column(name = "smd_est")
    private Boolean smdEst;
    @Column(name = "indversion")
    private Integer indversion;
    @Size(max = 50)
    @Column(name = "smd_reglanav")
    private String smdReglanav;
    @Size(max = 50)
    @Column(name = "smd_jsfbean")
    private String smdJsfbean;
    @Size(max = 150)
    @Column(name = "smd_rutarecrs")
    private String smdRutarecrs;
    @JoinColumn(name = "mod_id", referencedColumnName = "mod_id")
    @ManyToOne
    private AdmModapp modId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "smdId")
    private List<AdmSubmodappxrolapp> admSubmodappxrolappList;
    @OneToMany(mappedBy = "smdId")
    private List<AdmPagina> admPaginaList;
    @OneToMany(mappedBy = "smdId")
    private List<AdmCpexsubmodapp> admCpexsubmodappList;

    public AdmSubmodapp() {
    }

    public AdmSubmodapp(Integer smdId) {
        this.smdId = smdId;
    }

    public Integer getSmdId() {
        return smdId;
    }

    public void setSmdId(Integer smdId) {
        this.smdId = smdId;
    }

    public String getSmdNombre() {
        return smdNombre;
    }

    public void setSmdNombre(String smdNombre) {
        this.smdNombre = smdNombre;
    }

    public String getSmdDesc() {
        return smdDesc;
    }

    public void setSmdDesc(String smdDesc) {
        this.smdDesc = smdDesc;
    }

    public Boolean getSmdEst() {
        return smdEst;
    }

    public void setSmdEst(Boolean smdEst) {
        this.smdEst = smdEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public String getSmdReglanav() {
        return smdReglanav;
    }

    public void setSmdReglanav(String smdReglanav) {
        this.smdReglanav = smdReglanav;
    }

    public String getSmdJsfbean() {
        return smdJsfbean;
    }

    public void setSmdJsfbean(String smdJsfbean) {
        this.smdJsfbean = smdJsfbean;
    }

    public String getSmdRutarecrs() {
        return smdRutarecrs;
    }

    public void setSmdRutarecrs(String smdRutarecrs) {
        this.smdRutarecrs = smdRutarecrs;
    }

    public AdmModapp getModId() {
        return modId;
    }

    public void setModId(AdmModapp modId) {
        this.modId = modId;
    }

    @XmlTransient
    public List<AdmSubmodappxrolapp> getAdmSubmodappxrolappList() {
        return admSubmodappxrolappList;
    }

    public void setAdmSubmodappxrolappList(List<AdmSubmodappxrolapp> admSubmodappxrolappList) {
        this.admSubmodappxrolappList = admSubmodappxrolappList;
    }

    @XmlTransient
    public List<AdmPagina> getAdmPaginaList() {
        return admPaginaList;
    }

    public void setAdmPaginaList(List<AdmPagina> admPaginaList) {
        this.admPaginaList = admPaginaList;
    }

    @XmlTransient
    public List<AdmCpexsubmodapp> getAdmCpexsubmodappList() {
        return admCpexsubmodappList;
    }

    public void setAdmCpexsubmodappList(List<AdmCpexsubmodapp> admCpexsubmodappList) {
        this.admCpexsubmodappList = admCpexsubmodappList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (smdId != null ? smdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmSubmodapp)) {
            return false;
        }
        AdmSubmodapp other = (AdmSubmodapp) object;
        if ((this.smdId == null && other.smdId != null) || (this.smdId != null && !this.smdId.equals(other.smdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmSubmodapp[ smdId=" + smdId + " ]";
    }
}
