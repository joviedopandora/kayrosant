/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import com.pandora.mod.evaluacion.dao.EvalCalificacionPago;
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
@Table(name = "adm_cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmCargo.findAll", query = "SELECT a FROM AdmCargo a ORDER BY a.crgNombre")
    ,
    @NamedQuery(name = "AdmCargo.findByCrgId", query = "SELECT a FROM AdmCargo a WHERE a.crgId = :crgId")
    ,
    @NamedQuery(name = "AdmCargo.findByCrgNombre", query = "SELECT a FROM AdmCargo a WHERE a.crgNombre = :crgNombre")
    ,
    @NamedQuery(name = "AdmCargo.findByCrgDesc", query = "SELECT a FROM AdmCargo a WHERE a.crgDesc = :crgDesc")
    ,
    @NamedQuery(name = "AdmCargo.findByCrgEst", query = "SELECT a FROM AdmCargo a WHERE a.crgEst = :crgEst ORDER BY a.crgNombre")
    ,
    @NamedQuery(name = "AdmCargo.findByIndversion", query = "SELECT a FROM AdmCargo a WHERE a.indversion = :indversion")
    ,
    //Cargos activos
    @NamedQuery(name = "AdmCargo.findByCrgXEst", query = "SELECT a FROM AdmCargo a WHERE a.crgEst = :crgEst")
    ,
    @NamedQuery(name = "AdmCargo.cargoXProceso", query = "SELECT a FROM AdmCargo a WHERE a.crgId IN(SELECT DISTINCT crg.crgId FROM AdmCargo crg JOIN crg.admActxcargoList axc JOIN axc.actId act JOIN act.pcdId pdc JOIN pdc.proId pro WHERE pro.proId = :proId ) "
            + "ORDER BY a.crgNombre ")
})
public class AdmCargo implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admCargo", fetch = FetchType.LAZY)
    private List<EvalCalificacionPago> evalCalificacionPagoList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "crg_id")
    private String crgId;
    @Size(max = 2000)
    @Column(name = "crg_nombre")
    private String crgNombre;
    @Size(max = 2147483647)
    @Column(name = "crg_desc")
    private String crgDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "crg_est")
    private boolean crgEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @Column(name = "crg_aplica_comision")
    private boolean crgAplicaComision;
    @Column(name = "crg_tipo")
    private String crgTipo;
    @Column(name = "crg_editable")
    private boolean crgEditable;
    @OneToMany(mappedBy = "crgId")
    private List<AdmActxcargo> admActxcargoList;
    @OneToMany(mappedBy = "crgId")
    private List<AdmCrgxcol> admCrgxcolList;

    public AdmCargo() {
    }

    public AdmCargo(String crgId) {
        this.crgId = crgId;
    }

    public AdmCargo(String crgId, boolean crgEst, int indversion) {
        this.crgId = crgId;
        this.crgEst = crgEst;
        this.indversion = indversion;
    }

    public String getCrgId() {
        return crgId;
    }

    public void setCrgId(String crgId) {
        this.crgId = crgId;
    }

    public String getCrgNombre() {
        return crgNombre;
    }

    public void setCrgNombre(String crgNombre) {
        this.crgNombre = crgNombre;
    }

    public String getCrgDesc() {
        return crgDesc;
    }

    public void setCrgDesc(String crgDesc) {
        this.crgDesc = crgDesc;
    }

    public boolean getCrgEst() {
        return crgEst;
    }

    public void setCrgEst(boolean crgEst) {
        this.crgEst = crgEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<AdmActxcargo> getAdmActxcargoList() {
        return admActxcargoList;
    }

    public void setAdmActxcargoList(List<AdmActxcargo> admActxcargoList) {
        this.admActxcargoList = admActxcargoList;
    }

    @XmlTransient
    public List<AdmCrgxcol> getAdmCrgxcolList() {
        return admCrgxcolList;
    }

    public void setAdmCrgxcolList(List<AdmCrgxcol> admCrgxcolList) {
        this.admCrgxcolList = admCrgxcolList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (crgId != null ? crgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmCargo)) {
            return false;
        }
        AdmCargo other = (AdmCargo) object;
        if ((this.crgId == null && other.crgId != null) || (this.crgId != null && !this.crgId.equals(other.crgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmCargo[ crgId=" + crgId + " ]";
    }

    @XmlTransient
    public List<EvalCalificacionPago> getEvalCalificacionPagoList() {
        return evalCalificacionPagoList;
    }

    public void setEvalCalificacionPagoList(List<EvalCalificacionPago> evalCalificacionPagoList) {
        this.evalCalificacionPagoList = evalCalificacionPagoList;
    }

    public boolean isCrgAplicaComision() {
        return crgAplicaComision;
    }

    public void setCrgAplicaComision(boolean crgAplicaComision) {
        this.crgAplicaComision = crgAplicaComision;
    }

    /**
     * @return the crgTipo
     */
    public String getCrgTipo() {
        return crgTipo;
    }

    /**
     * @param crgTipo the crgTipo to set
     */
    public void setCrgTipo(String crgTipo) {
        this.crgTipo = crgTipo;
    }

    /**
     * @return the crgEditable
     */
    public boolean isCrgEditable() {
        return crgEditable;
    }

    /**
     * @param crgEditable the crgEditable to set
     */
    public void setCrgEditable(boolean crgEditable) {
        this.crgEditable = crgEditable;
    }

}
