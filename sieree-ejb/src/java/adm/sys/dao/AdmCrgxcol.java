/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import com.pandora.adm.dao.CmpConsolcompra;
import com.pandora.adm.dao.CmpConspedido;
import com.pandora.adm.dao.CmpFactura;
import com.pandora.adm.dao.InvGrupotransac;
import com.pandora.mod.ordenprod.dao.LogOrdenprod;
import com.pandora.mod.ordenprod.dao.PopCxcevento;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.venta.dao.VntRegistroLlamada;
import com.pandora.mod.venta.dao.VntRemision;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "adm_crgxcol")
@XmlRootElement
@NamedQueries({
     @NamedQuery(name = "AdmCrgxcol.findByColaborador", query = "SELECT a FROM AdmCrgxcol a WHERE a.cpeId.cpeId = :cpeId"),
    @NamedQuery(name = "AdmCrgxcol.findAll", query = "SELECT a FROM AdmCrgxcol a"),
    @NamedQuery(name = "AdmCrgxcol.findByCxcId", query = "SELECT a FROM AdmCrgxcol a WHERE a.cxcId = :cxcId"),
    //Cargos por colaborador por estado por texto
    @NamedQuery(name = "AdmCrgxcol.crgXColXEstXTexto", query = "SELECT a FROM AdmCrgxcol a JOIN a.cpeId cpe "
            + "WHERE  a.cxcEst = :cxcEst AND (cpe.colCedula.colCedula = :colCedula OR cpe.colCedula.colNombre1 LIKE :texto OR cpe.colCedula.colApellido1 LIKE :texto) "
            + "ORDER BY cpe.colCedula.colApellido1, cpe.colCedula.colNombre1"),
    //Cargos por colaborador por estado
    @NamedQuery(name = "AdmCrgxcol.crgXColXEst", query = "SELECT a FROM AdmCrgxcol a JOIN a.cpeId cpe  WHERE cpe.cpeId = :cpeId AND a.cxcEst = :cxcEst AND a.cxcPrincipal = :cxcPrincipal"),
    //Cargos por colaborador
    @NamedQuery(name = "AdmCrgxcol.findByCrgXCol", query = "SELECT a FROM AdmCrgxcol a JOIN a.cpeId cpe  WHERE cpe.cpeId = :cpeId"),
    @NamedQuery(name = "AdmCrgxcol.findByCrgXColCedu", query = "SELECT a FROM AdmCrgxcol a JOIN a.cpeId cpe JOIN cpe.colCedula cedula"
            + " WHERE cedula.colCedula = :colCedula"),
    //Cargos por colaborador por estado 1
    @NamedQuery(name = "AdmCrgxcol.findByCargoXEstado", query = "SELECT a FROM AdmCrgxcol a JOIN a.crgId crg WHERE crg.crgId = :crgId AND a.cxcEst = :cxcEst"),
    @NamedQuery(name = "AdmCrgxcol.findByGrupoXPaso", query = "SELECT a FROM AdmCrgxcol a JOIN a.sysColxgrupoList cg JOIN cg.sgrId sg JOIN sg.sysPropasoList paso  WHERE paso.spsId = :spsId AND a.cpeId.cpeEstcop = :cpeEstcop"),
    //Cargo por colaborador por estado
    @NamedQuery(name = "AdmCrgxcol.findByCrgXEstado", query = "SELECT a FROM AdmCrgxcol a JOIN a.cpeId cpe JOIN cpe.colCedula col WHERE a.crgId.crgId = :crgId ORDER BY col.colApellido1"),
    @NamedQuery(name = "AdmCrgxcol.findByCrgXActivos", query = "SELECT a FROM AdmCrgxcol a JOIN a.cpeId cpe JOIN cpe.colCedula col WHERE a.cxcEst=:cxcEst AND a.cpeId.colCedula.estadoColaborador.idEstadocolaborador = 1 ORDER BY col.colApellido1")})
public class AdmCrgxcol implements Serializable {

    /**
     * @return the cxcPrincipal
     */
    public boolean isCxcPrincipal() {
        return cxcPrincipal;
    }

    /**
     * @param cxcPrincipal the cxcPrincipal to set
     */
    public void setCxcPrincipal(boolean cxcPrincipal) {
        this.cxcPrincipal = cxcPrincipal;
    }

    @OneToMany(mappedBy = "cxcIdnew")
    private List<LogOrdenprod> logOrdenprodList;

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "crgxcol_cxc_id_seq", name = "crgxcol_cxc_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "crgxcol_cxc_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cxc_id")
    private Integer cxcId;
    @Column(name = "cxc_est")
    @NotNull
    private boolean cxcEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @Column(name = "cxc_fcre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cxcFcre;
     @Column(name = "cxc_principal")
    private boolean cxcPrincipal;
    @OneToMany(mappedBy = "cxcId")
    private List<AdmIpssedexcol> admIpssedexcolList;
    @JoinColumn(name = "cpe_id", referencedColumnName = "cpe_id")
    @ManyToOne
    private AdmColxemp cpeId;
    @JoinColumn(name = "crg_id", referencedColumnName = "crg_id")
    @ManyToOne
    private AdmCargo crgId;
    @OneToMany(mappedBy = "cxcId")
    private List<SysSegtarea> sysSegtareaList;
    @OneToMany(mappedBy = "cxcId")
    private List<SysColxgrupo> sysColxgrupoList;
    @OneToMany(mappedBy = "cxcId")
    private List<AdmCpexsubmodapp> admCpexsubmodappList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cxcId")
    private List<CmpConspedido> cmpConspedidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cxcId")
    private List<CmpConsolcompra> cmpConsolcompraList;
    @OneToMany(mappedBy = "cxcId")
    private List<InvGrupotransac> invGrupotransacList;
    @OneToMany(mappedBy = "cxcId")
    private List<PopOrdenprod> popOrdenprodList;
    @OneToMany(mappedBy = "cxcId")
    private List<PopCxcevento> popCxceventoList;
    @OneToMany(mappedBy = "cxcId")
    private List<CmpFactura> cmpFacturaList;
    @OneToMany(mappedBy = "cxcId")
    private List<VntRemision> vntRemisionList;
    
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "admCrgxcol", fetch = FetchType.LAZY)
    private List<VntRegistroLlamada> vntRegistroLlamadaList;

    public AdmCrgxcol() {
    }

    public AdmCrgxcol(Integer cxcId) {
        this.cxcId = cxcId;
    }

    public Integer getCxcId() {
        return cxcId;
    }

    public void setCxcId(Integer cxcId) {
        this.cxcId = cxcId;
    }

    public AdmColxemp getCpeId() {
        return cpeId;
    }

    public void setCpeId(AdmColxemp cpeId) {
        this.cpeId = cpeId;
    }

    public AdmCargo getCrgId() {
        return crgId;
    }

    public void setCrgId(AdmCargo crgId) {
        this.crgId = crgId;
    }

    public boolean isCxcEst() {
        return cxcEst;
    }

    public void setCxcEst(boolean cxcEst) {
        this.cxcEst = cxcEst;
    }

    public Date getCxcFcre() {
        return cxcFcre;
    }

    public void setCxcFcre(Date cxcFcre) {
        this.cxcFcre = cxcFcre;
    }

    public List<AdmIpssedexcol> getAdmIpssedexcolList() {
        return admIpssedexcolList;
    }

    public void setAdmIpssedexcolList(List<AdmIpssedexcol> admIpssedexcolList) {
        this.admIpssedexcolList = admIpssedexcolList;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxcId != null ? cxcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmCrgxcol)) {
            return false;
        }
        AdmCrgxcol other = (AdmCrgxcol) object;
        if ((this.cxcId == null && other.cxcId != null) || (this.cxcId != null && !this.cxcId.equals(other.cxcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmCrgxcol[ cxcId=" + cxcId + " ]";
    }

    /**
     * @return the sysSegtareaList
     */
    public List<SysSegtarea> getSysSegtareaList() {
        return sysSegtareaList;
    }

    /**
     * @param sysSegtareaList the sysSegtareaList to set
     */
    public void setSysSegtareaList(List<SysSegtarea> sysSegtareaList) {
        this.sysSegtareaList = sysSegtareaList;
    }

    /**
     * @return the sysColxgrupoList
     */
    public List<SysColxgrupo> getSysColxgrupoList() {
        return sysColxgrupoList;
    }

    /**
     * @param sysColxgrupoList the sysColxgrupoList to set
     */
    public void setSysColxgrupoList(List<SysColxgrupo> sysColxgrupoList) {
        this.sysColxgrupoList = sysColxgrupoList;
    }

    /**
     * @return the admCpexsubmodappList
     */
    public List<AdmCpexsubmodapp> getAdmCpexsubmodappList() {
        return admCpexsubmodappList;
    }

    /**
     * @param admCpexsubmodappList the admCpexsubmodappList to set
     */
    public void setAdmCpexsubmodappList(List<AdmCpexsubmodapp> admCpexsubmodappList) {
        this.admCpexsubmodappList = admCpexsubmodappList;
    }

    /**
     * @return the cmpConspedidoList
     */
    public List<CmpConspedido> getCmpConspedidoList() {
        return cmpConspedidoList;
    }

    /**
     * @param cmpConspedidoList the cmpConspedidoList to set
     */
    public void setCmpConspedidoList(List<CmpConspedido> cmpConspedidoList) {
        this.cmpConspedidoList = cmpConspedidoList;
    }

    /**
     * @return the cmpConsolcompraList
     */
    public List<CmpConsolcompra> getCmpConsolcompraList() {
        return cmpConsolcompraList;
    }

    /**
     * @param cmpConsolcompraList the cmpConsolcompraList to set
     */
    public void setCmpConsolcompraList(List<CmpConsolcompra> cmpConsolcompraList) {
        this.cmpConsolcompraList = cmpConsolcompraList;
    }

    /**
     * @return the invGrupotransacList
     */
    public List<InvGrupotransac> getInvGrupotransacList() {
        return invGrupotransacList;
    }

    /**
     * @param invGrupotransacList the invGrupotransacList to set
     */
    public void setInvGrupotransacList(List<InvGrupotransac> invGrupotransacList) {
        this.invGrupotransacList = invGrupotransacList;
    }

    /**
     * @return the popOrdenprodList
     */
    public List<PopOrdenprod> getPopOrdenprodList() {
        return popOrdenprodList;
    }

    /**
     * @param popOrdenprodList the popOrdenprodList to set
     */
    public void setPopOrdenprodList(List<PopOrdenprod> popOrdenprodList) {
        this.popOrdenprodList = popOrdenprodList;
    }

    /**
     * @return the popCxceventoList
     */
    public List<PopCxcevento> getPopCxceventoList() {
        return popCxceventoList;
    }

    /**
     * @param popCxceventoList the popCxceventoList to set
     */
    public void setPopCxceventoList(List<PopCxcevento> popCxceventoList) {
        this.popCxceventoList = popCxceventoList;
    }

    /**
     * @return the cmpFacturaList
     */
    public List<CmpFactura> getCmpFacturaList() {
        return cmpFacturaList;
    }

    /**
     * @param cmpFacturaList the cmpFacturaList to set
     */
    public void setCmpFacturaList(List<CmpFactura> cmpFacturaList) {
        this.cmpFacturaList = cmpFacturaList;
    }

    /**
     * @return the vntRemisionList
     */
    public List<VntRemision> getVntRemisionList() {
        return vntRemisionList;
    }

    /**
     * @param vntRemisionList the vntRemisionList to set
     */
    public void setVntRemisionList(List<VntRemision> vntRemisionList) {
        this.vntRemisionList = vntRemisionList;
    }    

    /**
     * @return the vntRegistroLlamadaList
     */
    public List<VntRegistroLlamada> getVntRegistroLlamadaList() {
        return vntRegistroLlamadaList;
    }

    /**
     * @param vntRegistroLlamadaList the vntRegistroLlamadaList to set
     */
    public void setVntRegistroLlamadaList(List<VntRegistroLlamada> vntRegistroLlamadaList) {
        this.vntRegistroLlamadaList = vntRegistroLlamadaList;
    }

    @XmlTransient
    public List<LogOrdenprod> getLogOrdenprodList() {
        return logOrdenprodList;
    }

    public void setLogOrdenprodList(List<LogOrdenprod> logOrdenprodList) {
        this.logOrdenprodList = logOrdenprodList;
    }
}
