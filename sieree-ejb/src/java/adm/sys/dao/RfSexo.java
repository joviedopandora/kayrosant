/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adm.sys.dao;

import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntDetallecliente;
import com.pandora.mod.venta.dao.VntRegistroLlamada;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "rf_sexo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RfSexo.findAll", query = "SELECT r FROM RfSexo r ORDER BY r.sexDesc"),
    @NamedQuery(name = "RfSexo.findBySexId", query = "SELECT r FROM RfSexo r WHERE r.sexId = :sexId"),
    @NamedQuery(name = "RfSexo.findBySexDesc", query = "SELECT r FROM RfSexo r WHERE r.sexDesc = :sexDesc")})
public class RfSexo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "sex_id")
    private String sexId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "sex_desc")
    private String sexDesc;
    @OneToMany(mappedBy = "sexId")
    private List<VntCliente> vntClienteList;
    
    
    @OneToMany(mappedBy = "sexId")
    private List<VntDetallecliente> vntDetalleclienteList;
     @OneToMany(mappedBy = "rfSexo", fetch = FetchType.LAZY)
    private List<VntRegistroLlamada> vntRegistroLlamadaList;
     
     @OneToMany(mappedBy = "rfSexo", fetch = FetchType.LAZY)
    public List<AdmColaborador> admColaboradorList;

    public RfSexo() {
    }

    public RfSexo(String sexId) {
        this.sexId = sexId;
    }

    public RfSexo(String sexId, String sexDesc) {
        this.sexId = sexId;
        this.sexDesc = sexDesc;
    }

    public String getSexId() {
        return sexId;
    }

    public void setSexId(String sexId) {
        this.sexId = sexId;
    }

    public String getSexDesc() {
        return sexDesc;
    }

    public void setSexDesc(String sexDesc) {
        this.sexDesc = sexDesc;
    }

    @XmlTransient
    public List<VntCliente> getVntClienteList() {
        return vntClienteList;
    }

    public void setVntClienteList(List<VntCliente> vntClienteList) {
        this.vntClienteList = vntClienteList;
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
        hash += (sexId != null ? sexId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RfSexo)) {
            return false;
        }
        RfSexo other = (RfSexo) object;
        if ((this.sexId == null && other.sexId != null) || (this.sexId != null && !this.sexId.equals(other.sexId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.siere.venta.dao.RfSexo[ sexId=" + sexId + " ]";
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

    /**
     * @return the admColaboradorList
     */
    public List<AdmColaborador> getAdmColaboradorList() {
        return admColaboradorList;
    }

    /**
     * @param admColaboradorList the admColaboradorList to set
     */
    public void setAdmColaboradorList(List<AdmColaborador> admColaboradorList) {
        this.admColaboradorList = admColaboradorList;
    }
    
}
