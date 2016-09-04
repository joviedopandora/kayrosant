/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;


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
 * @author HP
 */
@Entity
@Table(name = "adm_estcol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmEstcol.findAll", query = "SELECT a FROM AdmEstcol a"),
    @NamedQuery(name = "AdmEstcol.findByIdEstadocolaborador", query = "SELECT a FROM AdmEstcol a WHERE a.idEstadocolaborador = :idEstadocolaborador"),
    @NamedQuery(name = "AdmEstcol.findByDescEstadocolaborador", query = "SELECT a FROM AdmEstcol a WHERE a.descEstadocolaborador = :descEstadocolaborador")})
public class AdmEstcol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estadocolaborador")
    private Integer idEstadocolaborador;
    @Size(max = 2147483647)
    @Column(name = "desc_estadocolaborador")
    private String descEstadocolaborador;
     @OneToMany(mappedBy = "estadoColaborador")
    private List<AdmColaborador> admColaboradorList;
  

    public AdmEstcol() {
    }

    public AdmEstcol(Integer idEstadocolaborador) {
        this.idEstadocolaborador = idEstadocolaborador;
    }

    public Integer getIdEstadocolaborador() {
        return idEstadocolaborador;
    }

    public void setIdEstadocolaborador(Integer idEstadocolaborador) {
        this.idEstadocolaborador = idEstadocolaborador;
    }

    public String getDescEstadocolaborador() {
        return descEstadocolaborador;
    }

    public void setDescEstadocolaborador(String descEstadocolaborador) {
        this.descEstadocolaborador = descEstadocolaborador;
    }

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadocolaborador != null ? idEstadocolaborador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmEstcol)) {
            return false;
        }
        AdmEstcol other = (AdmEstcol) object;
        if ((this.idEstadocolaborador == null && other.idEstadocolaborador != null) || (this.idEstadocolaborador != null && !this.idEstadocolaborador.equals(other.idEstadocolaborador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ej.AdmEstcol[ idEstadocolaborador=" + idEstadocolaborador + " ]";
    }
    
    
    
    @XmlTransient
    public List<AdmColaborador> getAdmColaboradorList() {
        return admColaboradorList;
    }

    public void setAdmColaboradorList(List<AdmColaborador> admColaboradorList) {
        this.admColaboradorList = admColaboradorList;
    }
    

}
