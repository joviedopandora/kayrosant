/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import com.pandora.mod.venta.dao.VntDetallecliente;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GloriaCristina
 */
@Entity
@Table(name = "rf_parentezco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfParentezco.findAll", query = "SELECT r FROM RfParentezco r"),
    @NamedQuery(name = "RfParentezco.findByPrtId", query = "SELECT r FROM RfParentezco r WHERE r.prtId = :prtId"),
    @NamedQuery(name = "RfParentezco.findByPrtNombre", query = "SELECT r FROM RfParentezco r WHERE r.prtNombre = :prtNombre"),
    @NamedQuery(name = "RfParentezco.findByPrtDesc", query = "SELECT r FROM RfParentezco r WHERE r.prtDesc = :prtDesc"),
    @NamedQuery(name = "RfParentezco.findByPrtEst", query = "SELECT r FROM RfParentezco r WHERE r.prtEst = :prtEst ORDER BY r.prtNombre"),
    @NamedQuery(name = "RfParentezco.findByIndversion", query = "SELECT r FROM RfParentezco r WHERE r.indversion = :indversion")})
public class RfParentezco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "prt_id")
    private Integer prtId;
    @Size(max = 100)
    @Column(name = "prt_nombre")
    private String prtNombre;
    @Size(max = 2147483647)
    @Column(name = "prt_desc")
    private String prtDesc;
    @Column(name = "prt_est")
    private Boolean prtEst;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(mappedBy = "prtId")
    private List<VntDetallecliente> vntDetalleclienteList;
    
    @Column(name = "prt_aplicanit")
    @NotNull
    @Basic(optional = false)
    private boolean prtAplicanit;

    public RfParentezco() {
    }

    public RfParentezco(Integer prtId) {
        this.prtId = prtId;
    }

    public Integer getPrtId() {
        return prtId;
    }

    public void setPrtId(Integer prtId) {
        this.prtId = prtId;
    }

    public String getPrtNombre() {
        return prtNombre;
    }

    public void setPrtNombre(String prtNombre) {
        this.prtNombre = prtNombre;
    }

    public String getPrtDesc() {
        return prtDesc;
    }

    public void setPrtDesc(String prtDesc) {
        this.prtDesc = prtDesc;
    }

    public Boolean getPrtEst() {
        return prtEst;
    }

    public void setPrtEst(Boolean prtEst) {
        this.prtEst = prtEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<VntDetallecliente> getVntDetalleclienteList() {
        return vntDetalleclienteList;
    }

    public void setVntDetalleclienteList(List<VntDetallecliente> vntDetalleclienteList) {
        this.vntDetalleclienteList = vntDetalleclienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prtId != null ? prtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfParentezco)) {
            return false;
        }
        RfParentezco other = (RfParentezco) object;
        if ((this.prtId == null && other.prtId != null) || (this.prtId != null && !this.prtId.equals(other.prtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.siere.venta.dao.RfParentezco[ prtId=" + prtId + " ]";
    }

    /**
     * @return the prtAplicanit
     */
    public boolean isPrtAplicanit() {
        return prtAplicanit;
    }

    /**
     * @param prtAplicanit the prtAplicanit to set
     */
    public void setPrtAplicanit(boolean prtAplicanit) {
        this.prtAplicanit = prtAplicanit;
    }

}
