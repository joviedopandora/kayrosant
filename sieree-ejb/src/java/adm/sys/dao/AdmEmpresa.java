/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.pagocuenta.dao.FinCentrcost;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "adm_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmEmpresa.findAll", query = "SELECT a FROM AdmEmpresa a"),
    @NamedQuery(name = "AdmEmpresa.findByEmpId", query = "SELECT a FROM AdmEmpresa a WHERE a.empId = :empId"),
    @NamedQuery(name = "AdmEmpresa.findByEmpNombre", query = "SELECT a FROM AdmEmpresa a WHERE a.empNombre = :empNombre"),
    @NamedQuery(name = "AdmEmpresa.findByEmpDesc", query = "SELECT a FROM AdmEmpresa a WHERE a.empDesc = :empDesc"),
    @NamedQuery(name = "AdmEmpresa.findByEmpEst", query = "SELECT a FROM AdmEmpresa a WHERE a.empEst = :empEst"),
    @NamedQuery(name = "AdmEmpresa.findByEmpDominio", query = "SELECT a FROM AdmEmpresa a WHERE a.empDominio = :empDominio")})
public class AdmEmpresa implements Serializable {

    @Size(max = 800)
    @Column(name = "emp_direccion")
    private String empDireccion;
    @Size(max = 50)
    @Column(name = "emp_tel1")
    private String empTel1;
    @Size(max = 50)
    @Column(name = "emp_tel2")
    private String empTel2;
    @Size(max = 100)
    @Column(name = "emp_email")
    private String empEmail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "emp_iva")
    private BigDecimal empIva;
    @OneToMany(mappedBy = "empId")
    private List<FinCentrcost> finCentrcostList;
    @OneToMany(mappedBy = "empId")
    private List<AdmSede> admSedeList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "emp_id")
    private Integer empId;
    @Size(max = 100)
    @Column(name = "emp_nombre")
    private String empNombre;
    @Size(max = 2147483647)
    @Column(name = "emp_desc")
    private String empDesc;
    @Column(name = "emp_est")
    private Boolean empEst;
    @Size(max = 100)
    @Column(name = "emp_dominio")
    private String empDominio;
    @Column(name = "emp_nit")
    private String empNit;
    @Column(name = "emp_digver")
    private Integer empDigver;
    @Size(max = 300)
    @Column(name = "emp_contacto")
    private String empContacto;
    @Size(max = 20)
    @Column(name = "emp_telcontacto")
    private String empTelcontacto;
    @OneToMany(mappedBy = "empId")
    private List<AdmGerencia> admGerenciaList;
    @OneToMany(mappedBy = "epmId")
    private List<SysGrupo> sysGrupoList;
    @OneToMany(mappedBy = "empId")
    private List<AdmColxemp> admColxempList;
    @Column(name = "emp_usuariocorreo")
    private String empUsuariocorreo;
    @Column(name = "emp_hostcorreo")
    private String empHostcorreo;
    @Column(name = "emp_clavecorreo")
    private String empClavecorreo;
    @Column(name = "emp_puertocorreo")
    private String empPuertocorreo;
    @Column(name = "emp_rutaspdfscorreo")
    private String empRutaspdfscorreo;
    @JoinColumn(name = "ciu_id", referencedColumnName = "ciu_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RfCiudad rfCiudad;

    public AdmEmpresa() {
    }

    public AdmEmpresa(Integer empId) {
        this.empId = empId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpNombre() {
        return empNombre;
    }

    public void setEmpNombre(String empNombre) {
        this.empNombre = empNombre;
    }

    public String getEmpDesc() {
        return empDesc;
    }

    public void setEmpDesc(String empDesc) {
        this.empDesc = empDesc;
    }

    public Boolean getEmpEst() {
        return empEst;
    }

    public void setEmpEst(Boolean empEst) {
        this.empEst = empEst;
    }

    public String getEmpDominio() {
        return empDominio;
    }

    public void setEmpDominio(String empDominio) {
        this.empDominio = empDominio;
    }

    public String getEmpNit() {
        return empNit;
    }

    public void setEmpNit(String empNit) {
        this.empNit = empNit;
    }

    public Integer getEmpDigver() {
        return empDigver;
    }

    public void setEmpDigver(Integer empDigver) {
        this.empDigver = empDigver;
    }

    public String getEmpContacto() {
        return empContacto;
    }

    public void setEmpContacto(String empContacto) {
        this.empContacto = empContacto;
    }

    public String getEmpTelcontacto() {
        return empTelcontacto;
    }

    public void setEmpTelcontacto(String empTelcontacto) {
        this.empTelcontacto = empTelcontacto;
    }

    @XmlTransient
    public List<AdmGerencia> getAdmGerenciaList() {
        return admGerenciaList;
    }

    public void setAdmGerenciaList(List<AdmGerencia> admGerenciaList) {
        this.admGerenciaList = admGerenciaList;
    }

    @XmlTransient
    public List<SysGrupo> getSysGrupoList() {
        return sysGrupoList;
    }

    public void setSysGrupoList(List<SysGrupo> sysGrupoList) {
        this.sysGrupoList = sysGrupoList;
    }

    @XmlTransient
    public List<AdmColxemp> getAdmColxempList() {
        return admColxempList;
    }

    public void setAdmColxempList(List<AdmColxemp> admColxempList) {
        this.admColxempList = admColxempList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmEmpresa)) {
            return false;
        }
        AdmEmpresa other = (AdmEmpresa) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmEmpresa[ empId=" + empId + " ]";
    }

    /**
     * @return the empUsuariocorreo
     */
    public String getEmpUsuariocorreo() {
        return empUsuariocorreo;
    }

    /**
     * @param empUsuariocorreo the empUsuariocorreo to set
     */
    public void setEmpUsuariocorreo(String empUsuariocorreo) {
        this.empUsuariocorreo = empUsuariocorreo;
    }

    /**
     * @return the empHostcorreo
     */
    public String getEmpHostcorreo() {
        return empHostcorreo;
    }

    /**
     * @param empHostcorreo the empHostcorreo to set
     */
    public void setEmpHostcorreo(String empHostcorreo) {
        this.empHostcorreo = empHostcorreo;
    }

    /**
     * @return the empClavecorreo
     */
    public String getEmpClavecorreo() {
        return empClavecorreo;
    }

    /**
     * @param empClavecorreo the empClavecorreo to set
     */
    public void setEmpClavecorreo(String empClavecorreo) {
        this.empClavecorreo = empClavecorreo;
    }

    /**
     * @return the empPuertocorreo
     */
    public String getEmpPuertocorreo() {
        return empPuertocorreo;
    }

    /**
     * @param empPuertocorreo the empPuertocorreo to set
     */
    public void setEmpPuertocorreo(String empPuertocorreo) {
        this.empPuertocorreo = empPuertocorreo;
    }

    /**
     * @return the empRutaspdfscorreo
     */
    public String getEmpRutaspdfscorreo() {
        return empRutaspdfscorreo;
    }

    /**
     * @param empRutaspdfscorreo the empRutaspdfscorreo to set
     */
    public void setEmpRutaspdfscorreo(String empRutaspdfscorreo) {
        this.empRutaspdfscorreo = empRutaspdfscorreo;
    }

    /**
     * @return the rfCiudad
     */
    public RfCiudad getRfCiudad() {
        return rfCiudad;
    }

    /**
     * @param rfCiudad the rfCiudad to set
     */
    public void setRfCiudad(RfCiudad rfCiudad) {
        this.rfCiudad = rfCiudad;
    }

    /**
     * @return the empIva
     */
    public BigDecimal getEmpIva() {
        return empIva;
    }

    /**
     * @param empIva the empIva to set
     */
    public void setEmpIva(BigDecimal empIva) {
        this.empIva = empIva;
    }

    public String getEmpDireccion() {
        return empDireccion;
    }

    public void setEmpDireccion(String empDireccion) {
        this.empDireccion = empDireccion;
    }

    public String getEmpTel1() {
        return empTel1;
    }

    public void setEmpTel1(String empTel1) {
        this.empTel1 = empTel1;
    }

    public String getEmpTel2() {
        return empTel2;
    }

    public void setEmpTel2(String empTel2) {
        this.empTel2 = empTel2;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

 

    @XmlTransient
    public List<FinCentrcost> getFinCentrcostList() {
        return finCentrcostList;
    }

    public void setFinCentrcostList(List<FinCentrcost> finCentrcostList) {
        this.finCentrcostList = finCentrcostList;
    }

    @XmlTransient
    public List<AdmSede> getAdmSedeList() {
        return admSedeList;
    }

    public void setAdmSedeList(List<AdmSede> admSedeList) {
        this.admSedeList = admSedeList;
    }
}
