/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author breyner.robles
 */
@Entity
@Table(name = "rf_tipocontrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfTipocontrato.findAll", query = "SELECT r FROM RfTipocontrato r"),
    @NamedQuery(name = "RfTipocontrato.findByTctId", query = "SELECT r FROM RfTipocontrato r WHERE r.tctId = :tctId"),
    @NamedQuery(name = "RfTipocontrato.findByTctDesc", query = "SELECT r FROM RfTipocontrato r WHERE r.tctDesc = :tctDesc"),
    @NamedQuery(name = "RfTipocontrato.findByTctEstado", query = "SELECT r FROM RfTipocontrato r WHERE r.tctEstado = :tctEstado")})
public class RfTipocontrato implements Serializable {

    @Column(name = "tct_estado")
    private Boolean tctEstado;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tct_id")
    private Integer tctId;
    @Size(max = 2147483647)
    @Column(name = "tct_desc")
    private String tctDesc;
    @OneToMany(mappedBy = "rfTipocontrato", fetch = FetchType.LAZY)
    private List<AdmColaborador> admColaboradorList;

    public RfTipocontrato() {
    }

    public RfTipocontrato(Integer tctId) {
        this.tctId = tctId;
    }

    public Integer getTctId() {
        return tctId;
    }

    public void setTctId(Integer tctId) {
        this.tctId = tctId;
    }

    public String getTctDesc() {
        return tctDesc;
    }

    public void setTctDesc(String tctDesc) {
        this.tctDesc = tctDesc;
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
        hash += (tctId != null ? tctId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfTipocontrato)) {
            return false;
        }
        RfTipocontrato other = (RfTipocontrato) object;
        if ((this.tctId == null && other.tctId != null) || (this.tctId != null && !this.tctId.equals(other.tctId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.RfTipocontrato[ tctId=" + tctId + " ]";
    }

    public Boolean getTctEstado() {
        return tctEstado;
    }

    public void setTctEstado(Boolean tctEstado) {
        this.tctEstado = tctEstado;
    }
}
