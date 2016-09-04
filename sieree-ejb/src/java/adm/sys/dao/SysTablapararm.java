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
 * @author lfchacon
 */
@Entity
@Table(name = "sys_tablapararm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysTablapararm.findAll", query = "SELECT s FROM SysTablapararm s"),
    @NamedQuery(name = "SysTablapararm.findBySpmId", query = "SELECT s FROM SysTablapararm s WHERE s.spmId = :spmId"),
    @NamedQuery(name = "SysTablapararm.findBySpmNombre", query = "SELECT s FROM SysTablapararm s WHERE s.spmNombre = :spmNombre"),
    @NamedQuery(name = "SysTablapararm.findBySpmDesc", query = "SELECT s FROM SysTablapararm s WHERE s.spmDesc = :spmDesc"),
    @NamedQuery(name = "SysTablapararm.findBySpmRuta", query = "SELECT s FROM SysTablapararm s WHERE s.spmRuta = :spmRuta"),
    @NamedQuery(name = "SysTablapararm.findBySpmNombremodulo", query = "SELECT s FROM SysTablapararm s WHERE s.spmNombremodulo = :spmNombremodulo"),
    @NamedQuery(name = "SysTablapararm.findBySpmEst", query = "SELECT s FROM SysTablapararm s WHERE s.spmEst = :spmEst"),
    @NamedQuery(name = "SysTablapararm.findByIndversion", query = "SELECT s FROM SysTablapararm s WHERE s.indversion = :indversion")})
public class SysTablapararm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "spm_id")
    private Integer spmId;
    @Size(max = 100)
    @Column(name = "spm_nombre")
    private String spmNombre;
    @Size(max = 2147483647)
    @Column(name = "spm_desc")
    private String spmDesc;
    @Size(max = 256)
    @Column(name = "spm_ruta")
    private String spmRuta;
    @Size(max = 100)
    @Column(name = "spm_nombremodulo")
    private String spmNombremodulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "spm_est")
    private boolean spmEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @Column(name="smp_jsfbean")
    private String smpJsfbean;
    @Column(name="spm_reglanav")
    private String spmReglanav;
    @OneToMany(mappedBy = "spmId")
    private List<SysCpextprm> sysCpextprmList;

    public SysTablapararm() {
    }

    public SysTablapararm(Integer spmId) {
        this.spmId = spmId;
    }

    public SysTablapararm(Integer spmId, boolean spmEst, int indversion) {
        this.spmId = spmId;
        this.spmEst = spmEst;
        this.indversion = indversion;
    }

    public Integer getSpmId() {
        return spmId;
    }

    public void setSpmId(Integer spmId) {
        this.spmId = spmId;
    }

    public String getSpmNombre() {
        return spmNombre;
    }

    public void setSpmNombre(String spmNombre) {
        this.spmNombre = spmNombre;
    }

    public String getSpmDesc() {
        return spmDesc;
    }

    public void setSpmDesc(String spmDesc) {
        this.spmDesc = spmDesc;
    }

    public String getSpmRuta() {
        return spmRuta;
    }

    public void setSpmRuta(String spmRuta) {
        this.spmRuta = spmRuta;
    }

    public String getSpmNombremodulo() {
        return spmNombremodulo;
    }

    public void setSpmNombremodulo(String spmNombremodulo) {
        this.spmNombremodulo = spmNombremodulo;
    }

    public boolean getSpmEst() {
        return spmEst;
    }

    public void setSpmEst(boolean spmEst) {
        this.spmEst = spmEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<SysCpextprm> getSysCpextprmList() {
        return sysCpextprmList;
    }

    public void setSysCpextprmList(List<SysCpextprm> sysCpextprmList) {
        this.sysCpextprmList = sysCpextprmList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spmId != null ? spmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysTablapararm)) {
            return false;
        }
        SysTablapararm other = (SysTablapararm) object;
        if ((this.spmId == null && other.spmId != null) || (this.spmId != null && !this.spmId.equals(other.spmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.SysTablapararm[ spmId=" + spmId + " ]";
    }

    /**
     * @return the smpJsfbean
     */
    public String getSmpJsfbean() {
        return smpJsfbean;
    }

    /**
     * @param smpJsfbean the smpJsfbean to set
     */
    public void setSmpJsfbean(String smpJsfbean) {
        this.smpJsfbean = smpJsfbean;
    }

    /**
     * @return the spmReglanav
     */
    public String getSpmReglanav() {
        return spmReglanav;
    }

    /**
     * @param spmReglanav the spmReglanav to set
     */
    public void setSpmReglanav(String spmReglanav) {
        this.spmReglanav = spmReglanav;
    }
    
}
