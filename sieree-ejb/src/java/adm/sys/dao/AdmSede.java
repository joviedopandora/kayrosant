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
@Table(name = "adm_sede")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmSede.findAll", query = "SELECT a FROM AdmSede a"),
    @NamedQuery(name = "AdmSede.findBySedId", query = "SELECT a FROM AdmSede a WHERE a.sedId = :sedId"),
    @NamedQuery(name = "AdmSede.findBySedNombre", query = "SELECT a FROM AdmSede a WHERE a.sedNombre = :sedNombre"),
    @NamedQuery(name = "AdmSede.findBySedDesc", query = "SELECT a FROM AdmSede a WHERE a.sedDesc = :sedDesc"),
    @NamedQuery(name = "AdmSede.findBySedEst", query = "SELECT a FROM AdmSede a WHERE a.sedEst = :sedEst"),
    @NamedQuery(name = "AdmSede.findBySedDireccion", query = "SELECT a FROM AdmSede a WHERE a.sedDireccion = :sedDireccion"),
    @NamedQuery(name = "AdmSede.findBySedTelefono", query = "SELECT a FROM AdmSede a WHERE a.sedTelefono = :sedTelefono"),
    @NamedQuery(name = "AdmSede.findBySedPrincipal", query = "SELECT a FROM AdmSede a WHERE a.sedPrincipal = :sedPrincipal")})
public class AdmSede implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sed_id")
    private Integer sedId;
    @Size(max = 500)
    @Column(name = "sed_nombre")
    private String sedNombre;
    @Size(max = 2147483647)
    @Column(name = "sed_desc")
    private String sedDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sed_est")
    private boolean sedEst;
    @Size(max = 500)
    @Column(name = "sed_direccion")
    private String sedDireccion;
    @Size(max = 20)
    @Column(name = "sed_telefono")
    private String sedTelefono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sed_principal")
    private boolean sedPrincipal;
    @OneToMany(mappedBy = "sedId")
    private List<AdmCentrooper> admCentrooperList;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne
    private AdmEmpresa empId;

    public AdmSede() {
    }

    public AdmSede(Integer sedId) {
        this.sedId = sedId;
    }

    public AdmSede(Integer sedId, boolean sedEst, boolean sedPrincipal) {
        this.sedId = sedId;
        this.sedEst = sedEst;
        this.sedPrincipal = sedPrincipal;
    }

    public Integer getSedId() {
        return sedId;
    }

    public void setSedId(Integer sedId) {
        this.sedId = sedId;
    }

    public String getSedNombre() {
        return sedNombre;
    }

    public void setSedNombre(String sedNombre) {
        this.sedNombre = sedNombre;
    }

    public String getSedDesc() {
        return sedDesc;
    }

    public void setSedDesc(String sedDesc) {
        this.sedDesc = sedDesc;
    }

    public boolean getSedEst() {
        return sedEst;
    }

    public void setSedEst(boolean sedEst) {
        this.sedEst = sedEst;
    }

    public String getSedDireccion() {
        return sedDireccion;
    }

    public void setSedDireccion(String sedDireccion) {
        this.sedDireccion = sedDireccion;
    }

    public String getSedTelefono() {
        return sedTelefono;
    }

    public void setSedTelefono(String sedTelefono) {
        this.sedTelefono = sedTelefono;
    }

    public boolean getSedPrincipal() {
        return sedPrincipal;
    }

    public void setSedPrincipal(boolean sedPrincipal) {
        this.sedPrincipal = sedPrincipal;
    }

    @XmlTransient
    public List<AdmCentrooper> getAdmCentrooperList() {
        return admCentrooperList;
    }

    public void setAdmCentrooperList(List<AdmCentrooper> admCentrooperList) {
        this.admCentrooperList = admCentrooperList;
    }

    public AdmEmpresa getEmpId() {
        return empId;
    }

    public void setEmpId(AdmEmpresa empId) {
        this.empId = empId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sedId != null ? sedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmSede)) {
            return false;
        }
        AdmSede other = (AdmSede) object;
        if ((this.sedId == null && other.sedId != null) || (this.sedId != null && !this.sedId.equals(other.sedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.AdmSede[ sedId=" + sedId + " ]";
    }
    
}
