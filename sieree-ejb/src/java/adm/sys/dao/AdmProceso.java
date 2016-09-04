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
@Table(name = "adm_proceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmProceso.findAll", query = "SELECT a FROM AdmProceso a"),
    @NamedQuery(name = "AdmProceso.findByProId", query = "SELECT a FROM AdmProceso a WHERE a.proId = :proId"),
    @NamedQuery(name = "AdmProceso.findByProNombre", query = "SELECT a FROM AdmProceso a WHERE a.proNombre = :proNombre"),
    @NamedQuery(name = "AdmProceso.findByProDesc", query = "SELECT a FROM AdmProceso a WHERE a.proDesc = :proDesc"),
    @NamedQuery(name = "AdmProceso.findByProEst", query = "SELECT a FROM AdmProceso a WHERE a.proEst = :proEst"),
    @NamedQuery(name = "AdmProceso.findByIndversion", query = "SELECT a FROM AdmProceso a WHERE a.indversion = :indversion"),
    @NamedQuery(name = "AdmProceso.proXGer", query = "SELECT a FROM AdmProceso a JOIN a.gerId g WHERE g.gerId = :gerId ORDER BY a.proNombre "),
    //Procesos por usuario
    @NamedQuery(name = "AdmProceso.findByProXCol", query = "SELECT a FROM AdmProceso a WHERE a.proId IN "
    + "(SELECT DISTINCT pro.proId FROM AdmProceso pro JOIN pro.admProcedimientoList proc JOIN proc.admActividadList act "
    + "JOIN act.admActxcargoList axc JOIN axc.crgId crg JOIN crg.admCrgxcolList cxc JOIN cxc.cpeId cpe WHERE cpe.cpeId = :cpeId)"),
    @NamedQuery(name = "AdmProceso.procesoXCargo", query = "SELECT a FROM AdmProceso a WHERE a.proId IN(SELECT DISTINCT pro.proId FROM AdmProceso pro "
        + "JOIN pro.admProcedimientoList pdc JOIN pdc.admActividadList act JOIN act.admActxcargoList axc JOIN axc.crgId crg WHERE crg.crgId = :crgId )")
})
public class AdmProceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "pro_id")
    private String proId;
    @Size(max = 500)
    @Column(name = "pro_nombre")
    private String proNombre;
    @Size(max = 2147483647)
    @Column(name = "pro_desc")
    private String proDesc;
    @Column(name = "pro_est")
    private Boolean proEst;
    @Column(name = "indversion")
    @Version
    private Integer indversion;
    @OneToMany(mappedBy = "proId")
    private List<AdmProcedimiento> admProcedimientoList;
    @JoinColumn(name = "ger_id", referencedColumnName = "ger_id")
    @ManyToOne
    private AdmGerencia gerId;

    public AdmProceso() {
    }

    public AdmProceso(String proId) {
        this.proId = proId;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    public Boolean getProEst() {
        return proEst;
    }

    public void setProEst(Boolean proEst) {
        this.proEst = proEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public AdmGerencia getGerId() {
        return gerId;
    }

    public void setGerId(AdmGerencia gerId) {
        this.gerId = gerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proId != null ? proId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmProceso)) {
            return false;
        }
        AdmProceso other = (AdmProceso) object;
        if ((this.proId == null && other.proId != null) || (this.proId != null && !this.proId.equals(other.proId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmProceso[ proId=" + proId + " ]";
    }

    /**
     * @return the admProcedimientoList
     */
    @XmlTransient
    public List<AdmProcedimiento> getAdmProcedimientoList() {
        return admProcedimientoList;
    }

    /**
     * @param admProcedimientoList the admProcedimientoList to set
     */
    public void setAdmProcedimientoList(List<AdmProcedimiento> admProcedimientoList) {
        this.admProcedimientoList = admProcedimientoList;
    }
}
