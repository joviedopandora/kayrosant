/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "sys_propaso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysPropaso.findAll", query = "SELECT s FROM SysPropaso s"),
    @NamedQuery(name = "SysPropaso.findBySpsId", query = "SELECT s FROM SysPropaso s WHERE s.spsId = :spsId"),
    @NamedQuery(name = "SysPropaso.findBySpsNombre", query = "SELECT s FROM SysPropaso s WHERE s.spsNombre = :spsNombre"),
    @NamedQuery(name = "SysPropaso.findBySpsDesc", query = "SELECT s FROM SysPropaso s WHERE s.spsDesc = :spsDesc"),
    @NamedQuery(name = "SysPropaso.findBySpsEst", query = "SELECT s FROM SysPropaso s WHERE s.spsEst = :spsEst"),
    @NamedQuery(name = "SysPropaso.findByIndversion", query = "SELECT s FROM SysPropaso s WHERE s.indversion = :indversion"),
    @NamedQuery(name = "SysPropaso.findBySpsAplgrupo", query = "SELECT s FROM SysPropaso s WHERE s.spsAplgrupo = :spsAplgrupo"),
    @NamedQuery(name = "SysPropaso.findBySpsSistema", query = "SELECT s FROM SysPropaso s WHERE s.spsSistema = :spsSistema"),
    @NamedQuery(name = "SysPropaso.findBySpsVigencia", query = "SELECT s FROM SysPropaso s WHERE s.spsVigencia = :spsVigencia"),
    @NamedQuery(name = "SysPropaso.findPasosInicio", query = "SELECT s FROM SysPropaso s "
    + "JOIN s.sprId spr JOIN s.sgrId sgr  WHERE  sgr.sgrId IN("
    + "SELECT DISTINCT grupo.sgrId from SysGrupo grupo JOIN grupo.sysColxgrupoList cxg JOIN cxg.cxcId cxc "
    + "WHERE cxc.cxcId = :cxcId ) AND s.spsInicio = :spsInicio  AND (:diaActual >= s.spsDiainicio AND "
    + "(:diaActual <= (s.spsDiainicio + s.spsVigencia - 1)) )  ORDER BY spr.sprNombre ASC"),
    @NamedQuery(name = "SysPropaso.pasosInicioXProc", query = "SELECT s FROM SysPropaso s JOIN s.sprId spr "
    + "WHERE s.spsInicio = :spsInicio AND spr.sprId = :sprId ")})
public class SysPropaso implements Serializable {

    private static final long serialVersionUID = 1L;   
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sps_id")
    private Long spsId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "sps_nombre")
    private String spsNombre;
    @Size(max = 2147483647)
    @Column(name = "sps_desc")
    private String spsDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sps_est")
    private boolean spsEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sps_aplgrupo")
    private boolean spsAplgrupo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sps_sistema")
    private boolean spsSistema;
    @Column(name = "sps_vigencia")
    private Integer spsVigencia;
    @Column(name = "sps_form")
    private String spsForm;
    @Column(name = "sps_inicio")
    private Boolean spsInicio;
    @Column(name = "sps_reglanav")
    private String spsReglanav;
    @Column(name = "sps_jsfbean")
    private String spsJsfbean;
    @Column(name = "sps_diainicio")
    @Min(1)
    @Max(31)
    private Integer spsDiainicio;
    @Column(name="sps_ordenproc")
    private Integer spsOrdenproc;
    @JoinColumn(name = "spr_id", referencedColumnName = "spr_id")
    @ManyToOne
    private SysProceso sprId;
    @JoinColumn(name = "sgr_id", referencedColumnName = "sgr_id")
    @ManyToOne
    private SysGrupo sgrId;
    @OneToMany(mappedBy = "spsId")
    private List<SysSegtarea> sysSegtareaList;

    public SysPropaso() {
    }

    public SysPropaso(Long spsId) {
        this.spsId = spsId;
    }

    public SysPropaso(Long spsId, String spsNombre, boolean spsEst, int indversion, boolean spsAplgrupo, boolean spsSistema) {
        this.spsId = spsId;
        this.spsNombre = spsNombre;
        this.spsEst = spsEst;
        this.indversion = indversion;
        this.spsAplgrupo = spsAplgrupo;
        this.spsSistema = spsSistema;
    }

    public Long getSpsId() {
        return spsId;
    }

    public void setSpsId(Long spsId) {
        this.spsId = spsId;
    }

    public String getSpsNombre() {
        return spsNombre;
    }

    public void setSpsNombre(String spsNombre) {
        this.spsNombre = spsNombre;
    }

    public String getSpsDesc() {
        return spsDesc;
    }

    public void setSpsDesc(String spsDesc) {
        this.spsDesc = spsDesc;
    }

    public boolean getSpsEst() {
        return spsEst;
    }

    public void setSpsEst(boolean spsEst) {
        this.spsEst = spsEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    public boolean getSpsAplgrupo() {
        return spsAplgrupo;
    }

    public void setSpsAplgrupo(boolean spsAplgrupo) {
        this.spsAplgrupo = spsAplgrupo;
    }

    public boolean getSpsSistema() {
        return spsSistema;
    }

    public void setSpsSistema(boolean spsSistema) {
        this.spsSistema = spsSistema;
    }

    public Integer getSpsVigencia() {
        return spsVigencia;
    }

    public void setSpsVigencia(Integer spsVigencia) {
        this.spsVigencia = spsVigencia;
    }

    public SysProceso getSprId() {
        return sprId;
    }

    public void setSprId(SysProceso sprId) {
        this.sprId = sprId;
    }

    public SysGrupo getSgrId() {
        return sgrId;
    }

    public void setSgrId(SysGrupo sgrId) {
        this.sgrId = sgrId;
    }

    @XmlTransient
    public List<SysSegtarea> getSysSegtareaList() {
        return sysSegtareaList;
    }

    public void setSysSegtareaList(List<SysSegtarea> sysSegtareaList) {
        this.sysSegtareaList = sysSegtareaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spsId != null ? spsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysPropaso)) {
            return false;
        }
        SysPropaso other = (SysPropaso) object;
        if ((this.spsId == null && other.spsId != null) || (this.spsId != null && !this.spsId.equals(other.spsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.SysPropaso[ spsId=" + spsId + " ]";
    }

    /**
     * @return the spsForm
     */
    public String getSpsForm() {
        return spsForm;
    }

    /**
     * @param spsForm the spsForm to set
     */
    public void setSpsForm(String spsForm) {
        this.spsForm = spsForm;
    }

    /**
     * @return the spsInicio
     */
    public Boolean getSpsInicio() {
        return spsInicio;
    }

    /**
     * @param spsInicio the spsInicio to set
     */
    public void setSpsInicio(Boolean spsInicio) {
        this.spsInicio = spsInicio;
    }

    /**
     * @return the spsReglanav
     */
    public String getSpsReglanav() {
        return spsReglanav;
    }

    /**
     * @param spsReglanav the spsReglanav to set
     */
    public void setSpsReglanav(String spsReglanav) {
        this.spsReglanav = spsReglanav;
    }

    /**
     * @return the spsJsfbean
     */
    public String getSpsJsfbean() {
        return spsJsfbean;
    }

    /**
     * @param spsJsfbean the spsJsfbean to set
     */
    public void setSpsJsfbean(String spsJsfbean) {
        this.spsJsfbean = spsJsfbean;
    }

    /**
     * @return the spsDiainicio
     */
    public Integer getSpsDiainicio() {
        return spsDiainicio;
    }

    /**
     * @param spsDiainicio the spsDiainicio to set
     */
    public void setSpsDiainicio(Integer spsDiainicio) {
        this.spsDiainicio = spsDiainicio;
    }

    /**
     * @return the spsOrdenproc
     */
    public Integer getSpsOrdenproc() {
        return spsOrdenproc;
    }

    /**
     * @param spsOrdenproc the spsOrdenproc to set
     */
    public void setSpsOrdenproc(Integer spsOrdenproc) {
        this.spsOrdenproc = spsOrdenproc;
    }
}
