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
@Table(name = "adm_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmActividad.findAll", query = "SELECT a FROM AdmActividad a"),
    @NamedQuery(name = "AdmActividad.findByActId", query = "SELECT a FROM AdmActividad a WHERE a.actId = :actId"),
    @NamedQuery(name = "AdmActividad.findByActNombre", query = "SELECT a FROM AdmActividad a WHERE a.actNombre = :actNombre"),
    @NamedQuery(name = "AdmActividad.findByActDesc", query = "SELECT a FROM AdmActividad a WHERE a.actDesc = :actDesc"),
    @NamedQuery(name = "AdmActividad.findByActEst", query = "SELECT a FROM AdmActividad a WHERE a.actEst = :actEst"),
    @NamedQuery(name = "AdmActividad.findByIndversion", query = "SELECT a FROM AdmActividad a WHERE a.indversion = :indversion"),
    @NamedQuery(name = "AdmActividad.findByActOrden", query = "SELECT a FROM AdmActividad a WHERE a.actOrden = :actOrden"),
    @NamedQuery(name = "AdmActividad.actXPdc", query = "SELECT a FROM AdmActividad a JOIN a.pcdId pdc "
    + "WHERE pdc.pcdId = :pcdId ORDER BY a.actNombre "),
    //Actividades por procedimiento por proceso por usuario
    @NamedQuery(name = "AdmActividad.findByActXProcXProXUsu", query = "SELECT a FROM AdmActividad a JOIN a.pcdId proc "
    + "JOIN proc.proId pro JOIN a.admActxcargoList axc JOIN axc.crgId crg JOIN crg.admCrgxcolList cxc JOIN cxc.cpeId cpe "
    + "WHERE proc.pcdId = :pcdId AND cpe.cpeId = :cpeId")})
public class AdmActividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "act_id")
    private String actId;
    @Size(max = 2147483647)
    @Column(name = "act_nombre")
    private String actNombre;
    @Size(max = 2147483647)
    @Column(name = "act_desc")
    private String actDesc;
    @Column(name = "act_est")
    private Boolean actEst;
    @Column(name = "indversion")
    private Integer indversion;
    @Column(name = "act_orden")
    private Integer actOrden;
    @OneToMany(mappedBy = "actId")
    private List<AdmTarea> admTareaList;
    @OneToMany(mappedBy = "actId")
    private List<AdmActxcargo> admActxcargoList;
    @JoinColumn(name = "pcd_id", referencedColumnName = "pcd_id")
    @ManyToOne
    private AdmProcedimiento pcdId;

    public AdmActividad() {
    }

    public AdmActividad(String actId) {
        this.actId = actId;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActNombre() {
        return actNombre;
    }

    public void setActNombre(String actNombre) {
        this.actNombre = actNombre;
    }

    public String getActDesc() {
        return actDesc;
    }

    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }

    public Boolean getActEst() {
        return actEst;
    }

    public void setActEst(Boolean actEst) {
        this.actEst = actEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public Integer getActOrden() {
        return actOrden;
    }

    public void setActOrden(Integer actOrden) {
        this.actOrden = actOrden;
    }

    @XmlTransient
    public List<AdmTarea> getAdmTareaList() {
        return admTareaList;
    }

    public void setAdmTareaList(List<AdmTarea> admTareaList) {
        this.admTareaList = admTareaList;
    }

    @XmlTransient
    public List<AdmActxcargo> getAdmActxcargoList() {
        return admActxcargoList;
    }

    public void setAdmActxcargoList(List<AdmActxcargo> admActxcargoList) {
        this.admActxcargoList = admActxcargoList;
    }

    public AdmProcedimiento getPcdId() {
        return pcdId;
    }

    public void setPcdId(AdmProcedimiento pcdId) {
        this.pcdId = pcdId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actId != null ? actId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmActividad)) {
            return false;
        }
        AdmActividad other = (AdmActividad) object;
        if ((this.actId == null && other.actId != null) || (this.actId != null && !this.actId.equals(other.actId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmActividad[ actId=" + actId + " ]";
    }
}
