/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import com.pandora.mod.venta.dao.VntCliente;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author luis
 */
@Entity
@Table(name = "rf_tipodoc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfTipodoc.findAll", query = "SELECT r FROM RfTipodoc r"),
    @NamedQuery(name = "RfTipodoc.findByTdcId", query = "SELECT r FROM RfTipodoc r WHERE r.tdcId = :tdcId"),
    @NamedQuery(name = "RfTipodoc.findByTdcNombre", query = "SELECT r FROM RfTipodoc r WHERE r.tdcNombre = :tdcNombre"),
    @NamedQuery(name = "RfTipodoc.findByTdcEstado", query = "SELECT r FROM RfTipodoc r WHERE r.tdcEstado = :tdcEstado ORDER BY r.tdcNombre"),
    @NamedQuery(name = "RfTipodoc.findByIndversion", query = "SELECT r FROM RfTipodoc r WHERE r.indversion = :indversion")})
public class RfTipodoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "tdc_id")
    private String tdcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "tdc_nombre")
    private String tdcNombre;
    @Column(name = "tdc_estado")
    private Boolean tdcEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tdcId")
    private List<AdmColaborador> admColaboradorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tdcId")
    private List<VntCliente> vntClienteList;
    
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tdc_esnit")
    private boolean tdcEsnit;
    

    public RfTipodoc() {
        this.tdcEsnit = false;
    }

    public RfTipodoc(String tdcId) {
        this.tdcId = tdcId;
    }

    public RfTipodoc(String tdcId, String tdcNombre, int indversion) {
        this.tdcId = tdcId;
        this.tdcNombre = tdcNombre;
        this.indversion = indversion;
    }

    public String getTdcId() {
        return tdcId;
    }

    public void setTdcId(String tdcId) {
        this.tdcId = tdcId;
    }

    public String getTdcNombre() {
        return tdcNombre;
    }

    public void setTdcNombre(String tdcNombre) {
        this.tdcNombre = tdcNombre;
    }

    public Boolean getTdcEstado() {
        return tdcEstado;
    }

    public void setTdcEstado(Boolean tdcEstado) {
        this.tdcEstado = tdcEstado;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<AdmColaborador> getAdmColaboradorList() {
        return admColaboradorList;
    }

    public void setAdmColaboradorList(List<AdmColaborador> admColaboradorList) {
        this.admColaboradorList = admColaboradorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdcId != null ? tdcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfTipodoc)) {
            return false;
        }
        RfTipodoc other = (RfTipodoc) object;
        if ((this.tdcId == null && other.tdcId != null) || (this.tdcId != null && !this.tdcId.equals(other.tdcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.RfTipodoc[ tdcId=" + tdcId + " ]";
    }

    /**
     * @return the vntClienteList
     */
    public List<VntCliente> getVntClienteList() {
        return vntClienteList;
    }

    /**
     * @param vntClienteList the vntClienteList to set
     */
    public void setVntClienteList(List<VntCliente> vntClienteList) {
        this.vntClienteList = vntClienteList;
    }

    /**
     * @return the tdcEsnit
     */
    public boolean isTdcEsnit() {
        return tdcEsnit;
    }

    /**
     * @param tdcEsnit the tdcEsnit to set
     */
    public void setTdcEsnit(boolean tdcEsnit) {
        this.tdcEsnit = tdcEsnit;
    }

}
