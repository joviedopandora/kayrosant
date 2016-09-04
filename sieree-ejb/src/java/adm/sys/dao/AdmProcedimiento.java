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
@Table(name = "adm_procedimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmProcedimiento.findAll", query = "SELECT a FROM AdmProcedimiento a "
    + "ORDER BY a.proId.proId, a.pcdNombre "),
    @NamedQuery(name = "AdmProcedimiento.findByPcdId", query = "SELECT a FROM AdmProcedimiento a WHERE a.pcdId = :pcdId"),
    @NamedQuery(name = "AdmProcedimiento.findByPcdNombre", query = "SELECT a FROM AdmProcedimiento a WHERE a.pcdNombre = :pcdNombre"),
    @NamedQuery(name = "AdmProcedimiento.findByPcdDesc", query = "SELECT a FROM AdmProcedimiento a WHERE a.pcdDesc = :pcdDesc"),
    @NamedQuery(name = "AdmProcedimiento.findByPcdEst", query = "SELECT a FROM AdmProcedimiento a WHERE a.pcdEst = :pcdEst"),
    @NamedQuery(name = "AdmProcedimiento.prodXProc", query = "SELECT a FROM AdmProcedimiento a JOIN a.proId pro "
    + "WHERE pro.proId = :proId ORDER BY a.pcdNombre "),
    @NamedQuery(name = "AdmProcedimiento.pdcXNomDesc", query = "SELECT a FROM AdmProcedimiento a "
    + " WHERE a.pcdNombre LIKE :pcdTexto OR a.pcdDesc LIKE :pcdTexto "),
    //Procedimientos por proceso por usuario
    @NamedQuery(name = "AdmProcedimiento.findByProcXProXUsu", query = "SELECT a FROM AdmProcedimiento a WHERE a.pcdId IN "
    + "(SELECT DISTINCT proc.pcdId FROM AdmProcedimiento proc JOIN proc.proId pro JOIN proc.admActividadList act "
    + "JOIN act.admActxcargoList axc JOIN axc.crgId crg JOIN crg.admCrgxcolList cxc JOIN cxc.cpeId cpe "
    + "WHERE pro.proId = :proId AND cpe.cpeId = :cpeId)")})
public class AdmProcedimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "pcd_id")
    private String pcdId;
    @Size(max = 2000)
    @Column(name = "pcd_nombre")
    private String pcdNombre;
    @Size(max = 2147483647)
    @Column(name = "pcd_desc")
    private String pcdDesc;
    @Column(name = "pcd_est")
    private Boolean pcdEst; 
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    @ManyToOne
    private AdmProceso proId;
    @OneToMany(mappedBy = "pcdId")
    private List<AdmActividad> admActividadList;

    public AdmProcedimiento() {
    }

    public AdmProcedimiento(String pcdId) {
        this.pcdId = pcdId;
    }

    public String getPcdId() {
        return pcdId;
    }

    public void setPcdId(String pcdId) {
        this.pcdId = pcdId;
    }

    public String getPcdNombre() {
        return pcdNombre;
    }

    public void setPcdNombre(String pcdNombre) {
        this.pcdNombre = pcdNombre;
    }

    public String getPcdDesc() {
        return pcdDesc;
    }

    public void setPcdDesc(String pcdDesc) {
        this.pcdDesc = pcdDesc;
    }

    public Boolean getPcdEst() {
        return pcdEst;
    }

    public void setPcdEst(Boolean pcdEst) {
        this.pcdEst = pcdEst;
    }


    public AdmProceso getProId() {
        return proId;
    }

    public void setProId(AdmProceso proId) {
        this.proId = proId;
    }

    @XmlTransient
    public List<AdmActividad> getAdmActividadList() {
        return admActividadList;
    }

    public void setAdmActividadList(List<AdmActividad> admActividadList) {
        this.admActividadList = admActividadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pcdId != null ? pcdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmProcedimiento)) {
            return false;
        }
        AdmProcedimiento other = (AdmProcedimiento) object;
        if ((this.pcdId == null && other.pcdId != null) || (this.pcdId != null && !this.pcdId.equals(other.pcdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.AdmProcedimiento[ pcdId=" + pcdId + " ]";
    }
}
