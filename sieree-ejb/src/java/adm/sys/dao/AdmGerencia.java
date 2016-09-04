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
@Table(name = "adm_gerencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmGerencia.findAll", query = "SELECT a FROM AdmGerencia a"),
    @NamedQuery(name = "AdmGerencia.findByGerId", query = "SELECT a FROM AdmGerencia a WHERE a.gerId = :gerId"),
    @NamedQuery(name = "AdmGerencia.findByGerNombre", query = "SELECT a FROM AdmGerencia a WHERE a.gerNombre = :gerNombre"),
    @NamedQuery(name = "AdmGerencia.findByGerDesc", query = "SELECT a FROM AdmGerencia a WHERE a.gerDesc = :gerDesc"),
    @NamedQuery(name = "AdmGerencia.findByGerEst", query = "SELECT a FROM AdmGerencia a WHERE a.gerEst = :gerEst"),
    @NamedQuery(name = "AdmGerencia.findByIndversion", query = "SELECT a FROM AdmGerencia a WHERE a.indversion = :indversion"),
//Consultas propias
    @NamedQuery( name="AdmGerencia.gerXEmp",query="SELECT a FROM AdmGerencia a JOIN a.empId e WHERE e.empId = :empId ORDER BY a.gerNombre ")
})
public class AdmGerencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ger_id")
    private String gerId;
    @Size(max = 200)
    @Column(name = "ger_nombre")
    private String gerNombre;
    @Size(max = 2147483647)
    @Column(name = "ger_desc")
    private String gerDesc;
    @Column(name = "ger_est")
    private Boolean gerEst;
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne
    private AdmEmpresa empId;
    @OneToMany(mappedBy = "gerId")
    private List<AdmProceso> admProcesoList;

    public AdmGerencia() {
    }

    public AdmGerencia(String gerId) {
        this.gerId = gerId;
    }

    public String getGerId() {
        return gerId;
    }

    public void setGerId(String gerId) {
        this.gerId = gerId;
    }

    public String getGerNombre() {
        return gerNombre;
    }

    public void setGerNombre(String gerNombre) {
        this.gerNombre = gerNombre;
    }

    public String getGerDesc() {
        return gerDesc;
    }

    public void setGerDesc(String gerDesc) {
        this.gerDesc = gerDesc;
    }

    public Boolean getGerEst() {
        return gerEst;
    }

    public void setGerEst(Boolean gerEst) {
        this.gerEst = gerEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public AdmEmpresa getEmpId() {
        return empId;
    }

    public void setEmpId(AdmEmpresa empId) {
        this.empId = empId;
    }

    @XmlTransient
    public List<AdmProceso> getAdmProcesoList() {
        return admProcesoList;
    }

    public void setAdmProcesoList(List<AdmProceso> admProcesoList) {
        this.admProcesoList = admProcesoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gerId != null ? gerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmGerencia)) {
            return false;
        }
        AdmGerencia other = (AdmGerencia) object;
        if ((this.gerId == null && other.gerId != null) || (this.gerId != null && !this.gerId.equals(other.gerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmGerencia[ gerId=" + gerId + " ]";
    }
    
}
