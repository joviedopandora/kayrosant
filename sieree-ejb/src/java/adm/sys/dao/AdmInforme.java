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
@Table(name = "adm_informe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmInforme.findAll", query = "SELECT a FROM AdmInforme a"),
    @NamedQuery(name = "AdmInforme.findByInfId", query = "SELECT a FROM AdmInforme a WHERE a.infId = :infId"),
    @NamedQuery(name = "AdmInforme.findByInfNombre", query = "SELECT a FROM AdmInforme a WHERE a.infNombre = :infNombre"),
    @NamedQuery(name = "AdmInforme.findByInfDetalle", query = "SELECT a FROM AdmInforme a WHERE a.infDetalle = :infDetalle"),
    @NamedQuery(name = "AdmInforme.findByInfJasper", query = "SELECT a FROM AdmInforme a WHERE a.infJasper = :infJasper"),
    @NamedQuery(name = "AdmInforme.findByInfJasperruta", query = "SELECT a FROM AdmInforme a WHERE a.infJasperruta = :infJasperruta"),
    @NamedQuery(name = "AdmInforme.findByInfEst", query = "SELECT a FROM AdmInforme a WHERE a.infEst = :infEst"),
    @NamedQuery(name = "AdmInforme.findByIndversion", query = "SELECT a FROM AdmInforme a WHERE a.indversion = :indversion"),
    @NamedQuery(name = "AdmInforme.informXCxc", query = "SELECT inf FROM AdmInforme inf WHERE inf.infId IN(SELECT a.infId FROM AdmInforme a JOIN a.admInfxusrList ixu JOIN ixu.cxcId cxc WHERE cxc.cxcId = :cxcId)")
})
public class AdmInforme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "inf_id")
    private Integer infId;
    @Size(max = 100)
    @Column(name = "inf_nombre")
    private String infNombre;
    @Size(max = 2147483647)
    @Column(name = "inf_detalle")
    private String infDetalle;
    @Size(max = 150)
    @Column(name = "inf_jasper")
    private String infJasper;
    @Size(max = 500)
    @Column(name = "inf_jasperruta")
    private String infJasperruta;
    @Column(name = "inf_est")
    private Boolean infEst;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infId")
    private List<AdmInfxusr> admInfxusrList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "infId")
    private List<AdmInfxrolapp> admInfxrolappList;

    public AdmInforme() {
    }

    public AdmInforme(Integer infId) {
        this.infId = infId;
    }

    public Integer getInfId() {
        return infId;
    }

    public void setInfId(Integer infId) {
        this.infId = infId;
    }

    public String getInfNombre() {
        return infNombre;
    }

    public void setInfNombre(String infNombre) {
        this.infNombre = infNombre;
    }

    public String getInfDetalle() {
        return infDetalle;
    }

    public void setInfDetalle(String infDetalle) {
        this.infDetalle = infDetalle;
    }

    public String getInfJasper() {
        return infJasper;
    }

    public void setInfJasper(String infJasper) {
        this.infJasper = infJasper;
    }

    public String getInfJasperruta() {
        return infJasperruta;
    }

    public void setInfJasperruta(String infJasperruta) {
        this.infJasperruta = infJasperruta;
    }

    public Boolean getInfEst() {
        return infEst;
    }

    public void setInfEst(Boolean infEst) {
        this.infEst = infEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<AdmInfxusr> getAdmInfxusrList() {
        return admInfxusrList;
    }

    public void setAdmInfxusrList(List<AdmInfxusr> admInfxusrList) {
        this.admInfxusrList = admInfxusrList;
    }

    @XmlTransient
    public List<AdmInfxrolapp> getAdmInfxrolappList() {
        return admInfxrolappList;
    }

    public void setAdmInfxrolappList(List<AdmInfxrolapp> admInfxrolappList) {
        this.admInfxrolappList = admInfxrolappList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (infId != null ? infId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmInforme)) {
            return false;
        }
        AdmInforme other = (AdmInforme) object;
        if ((this.infId == null && other.infId != null) || (this.infId != null && !this.infId.equals(other.infId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmInforme[ infId=" + infId + " ]";
    }
}
