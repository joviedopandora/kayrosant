/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import com.pandora.pagocuenta.dao.FinCentrcost;
import java.io.Serializable;
import java.util.Date;
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
@Table(name = "adm_colxemp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmColxemp.findAll", query = "SELECT a FROM AdmColxemp a"),
    @NamedQuery(name = "AdmColxemp.findByCpeId", query = "SELECT a FROM AdmColxemp a WHERE a.cpeId = :cpeId"),
    @NamedQuery(name = "AdmColxemp.findByCpeUsuario", query = "SELECT a FROM AdmColxemp a WHERE a.cpeUsuario = :cpeUsuario"),
    @NamedQuery(name = "AdmColxemp.findByCpeClave", query = "SELECT a FROM AdmColxemp a WHERE a.cpeClave = :cpeClave"),
    @NamedQuery(name = "AdmColxemp.findByCpeEstcop", query = "SELECT a FROM AdmColxemp a WHERE a.cpeEstcop = :cpeEstcop"),
    @NamedQuery(name = "AdmColxemp.findByCpeEmail", query = "SELECT a FROM AdmColxemp a WHERE a.cpeEmail = :cpeEmail"),
    @NamedQuery(name = "AdmColxemp.findByCpeTel", query = "SELECT a FROM AdmColxemp a WHERE a.cpeTel = :cpeTel"),
    @NamedQuery(name = "AdmColxemp.validarCol", query = "SELECT a FROM AdmColxemp a "
            + "WHERE a.cpeUsuario = :cpeUsuario AND a.cpeClave = :cpeClave"),
    //Personas por grupo por paso
    //Colaboradores por empresa
    @NamedQuery(name = "AdmColxemp.findByEmpresa", query = "SELECT a FROM AdmColxemp a WHERE a.empId = :empId "
            + " ORDER BY a.colCedula.colApellido1, a.colCedula.colNombre1"),
    @NamedQuery(name = "AdmColxemp.findByEmpresaXColaborador", query = "SELECT a FROM AdmColxemp a WHERE a.empId.empId = :empId AND a.colCedula.colCedula = :colCedula"),
      @NamedQuery(name = "AdmColxemp.validarColUsuario", query = "SELECT a FROM AdmColxemp a "
            + "WHERE a.cpeUsuario = :cpeUsuario "),
})
public class AdmColxemp implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "cpe_estcop")
    private Boolean cpeEstcop;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "colxemp_cpe_id_seq", name = "colxemp_cpe_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "colxemp_cpe_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cpe_id")
    private Integer cpeId;
    @Size(max = 100)
    @Column(name = "cpe_usuario")
    private String cpeUsuario;
    @Size(max = 1500)
    @Column(name = "cpe_clave")
    private String cpeClave;
    @Size(max = 100)
    @Column(name = "cpe_email")
    private String cpeEmail;
    @Size(max = 25)
    @Column(name = "cpe_tel")
    private String cpeTel;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cpe_fcre")
    private Date cpeFcre;
    @OneToMany(mappedBy = "cpeId")
    private List<AdmCrgxcol> admCrgxcolList;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne
    private AdmEmpresa empId;
    @JoinColumn(name = "col_cedula", referencedColumnName = "col_cedula")
    @ManyToOne
    private AdmColaborador colCedula;
    @JoinColumn(name = "cop_id", referencedColumnName = "cop_id")
    @ManyToOne
    private AdmCentrooper copId;
    @JoinColumn(name = "cct_id", referencedColumnName = "cct_id")
    @ManyToOne
    private FinCentrcost cctId;
    @OneToMany(mappedBy = "cpeId")
    private List<SysCpextprm> sysCpextprmList;

    public AdmColxemp() {
    }

    public AdmColxemp(Integer cpeId) {
        this.cpeId = cpeId;
    }

    public Integer getCpeId() {
        return cpeId;
    }

    public void setCpeId(Integer cpeId) {
        this.cpeId = cpeId;
    }

    public String getCpeUsuario() {
        return cpeUsuario;
    }

    public void setCpeUsuario(String cpeUsuario) {
        this.cpeUsuario = cpeUsuario;
    }

    public String getCpeClave() {
        return cpeClave;
    }

    public void setCpeClave(String cpeClave) {
        this.cpeClave = cpeClave;
    }

    public Boolean getCpeEstcop() {
        return cpeEstcop;
    }

    public void setCpeEstcop(Boolean cpeEstcop) {
        this.cpeEstcop = cpeEstcop;
    }

    public String getCpeEmail() {
        return cpeEmail;
    }

    public void setCpeEmail(String cpeEmail) {
        this.cpeEmail = cpeEmail;
    }

    public String getCpeTel() {
        return cpeTel;
    }

    public void setCpeTel(String cpeTel) {
        this.cpeTel = cpeTel;
    }

    @XmlTransient
    public List<AdmCrgxcol> getAdmCrgxcolList() {
        return admCrgxcolList;
    }

    public void setAdmCrgxcolList(List<AdmCrgxcol> admCrgxcolList) {
        this.admCrgxcolList = admCrgxcolList;
    }

    public AdmEmpresa getEmpId() {
        return empId;
    }

    public void setEmpId(AdmEmpresa empId) {
        this.empId = empId;
    }

    public AdmColaborador getColCedula() {
        return colCedula;
    }

    public void setColCedula(AdmColaborador colCedula) {
        this.colCedula = colCedula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpeId != null ? cpeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmColxemp)) {
            return false;
        }
        AdmColxemp other = (AdmColxemp) object;
        if ((this.cpeId == null && other.cpeId != null) || (this.cpeId != null && !this.cpeId.equals(other.cpeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmColxemp[ cpeId=" + cpeId + " ]";
    }

    /**
     * @return the copId
     */
    public AdmCentrooper getCopId() {
        return copId;
    }

    /**
     * @param copId the copId to set
     */
    public void setCopId(AdmCentrooper copId) {
        this.copId = copId;
    }

    /**
     * @return the cctId
     */
    public FinCentrcost getCctId() {
        return cctId;
    }

    /**
     * @param cctId the cctId to set
     */
    public void setCctId(FinCentrcost cctId) {
        this.cctId = cctId;
    }

    /**
     * @return the cpeFcre
     */
    public Date getCpeFcre() {
        return cpeFcre;
    }

    /**
     * @param cpeFcre the cpeFcre to set
     */
    public void setCpeFcre(Date cpeFcre) {
        this.cpeFcre = cpeFcre;
    }


    /**
     * @return the sysCpextprmList
     */
    public List<SysCpextprm> getSysCpextprmList() {
        return sysCpextprmList;
    }

    /**
     * @param sysCpextprmList the sysCpextprmList to set
     */
    public void setSysCpextprmList(List<SysCpextprm> sysCpextprmList) {
        this.sysCpextprmList = sysCpextprmList;
    }


    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }
}
