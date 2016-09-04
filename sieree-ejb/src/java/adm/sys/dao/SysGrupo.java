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
@Table(name = "sys_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysGrupo.findAll", query = "SELECT s FROM SysGrupo s"),
    @NamedQuery(name = "SysGrupo.findBySgrId", query = "SELECT s FROM SysGrupo s WHERE s.sgrId = :sgrId"),
    @NamedQuery(name = "SysGrupo.findBySgrNombre", query = "SELECT s FROM SysGrupo s WHERE s.sgrNombre = :sgrNombre"),
    @NamedQuery(name = "SysGrupo.findBySgrDesc", query = "SELECT s FROM SysGrupo s WHERE s.sgrDesc = :sgrDesc"),
    @NamedQuery(name = "SysGrupo.findBySgrEst", query = "SELECT s FROM SysGrupo s WHERE s.sgrEst = :sgrEst"),
    @NamedQuery(name = "SysGrupo.findByIndversion", query = "SELECT s FROM SysGrupo s WHERE s.indversion = :indversion"),
@NamedQuery(name="SysGrupo.grupoXPaso",query="SELECT s FROM SysGrupo s JOIN s.sysPropasoList p WHERE p.spsId = :spsId")})
public class SysGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "sys_grupo_sgr_id_seq", name = "sys_grupo_sgr_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "sys_grupo_sgr_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sgr_id")
    private Integer sgrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "sgr_nombre")
    private String sgrNombre;
    @Size(max = 2147483647)
    @Column(name = "sgr_desc")
    private String sgrDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sgr_est")
    private boolean sgrEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @JoinColumn(name = "epm_id", referencedColumnName = "emp_id")
    @ManyToOne
    private AdmEmpresa epmId;
    @OneToMany(mappedBy = "sgrId")
    private List<SysColxgrupo> sysColxgrupoList;
    @OneToMany(mappedBy = "sgrId")
    private List<SysPropaso> sysPropasoList;

    public SysGrupo() {
    }

    public SysGrupo(Integer sgrId) {
        this.sgrId = sgrId;
    }

    public SysGrupo(Integer sgrId, String sgrNombre, boolean sgrEst, int indversion) {
        this.sgrId = sgrId;
        this.sgrNombre = sgrNombre;
        this.sgrEst = sgrEst;
        this.indversion = indversion;
    }

    public Integer getSgrId() {
        return sgrId;
    }

    public void setSgrId(Integer sgrId) {
        this.sgrId = sgrId;
    }

    public String getSgrNombre() {
        return sgrNombre;
    }

    public void setSgrNombre(String sgrNombre) {
        this.sgrNombre = sgrNombre;
    }

    public String getSgrDesc() {
        return sgrDesc;
    }

    public void setSgrDesc(String sgrDesc) {
        this.sgrDesc = sgrDesc;
    }

    public boolean getSgrEst() {
        return sgrEst;
    }

    public void setSgrEst(boolean sgrEst) {
        this.sgrEst = sgrEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    public AdmEmpresa getEpmId() {
        return epmId;
    }

    public void setEpmId(AdmEmpresa epmId) {
        this.epmId = epmId;
    }

    @XmlTransient
    public List<SysColxgrupo> getSysColxgrupoList() {
        return sysColxgrupoList;
    }

    public void setSysColxgrupoList(List<SysColxgrupo> sysColxgrupoList) {
        this.sysColxgrupoList = sysColxgrupoList;
    }

    @XmlTransient
    public List<SysPropaso> getSysPropasoList() {
        return sysPropasoList;
    }

    public void setSysPropasoList(List<SysPropaso> sysPropasoList) {
        this.sysPropasoList = sysPropasoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgrId != null ? sgrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysGrupo)) {
            return false;
        }
        SysGrupo other = (SysGrupo) object;
        if ((this.sgrId == null && other.sgrId != null) || (this.sgrId != null && !this.sgrId.equals(other.sgrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.SysGrupo[ sgrId=" + sgrId + " ]";
    }
}
