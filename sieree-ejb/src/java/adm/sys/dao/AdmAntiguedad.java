/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author sistemasmaximus
 */
@Entity
@Table(name = "adm_antiguedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmAntiguedad.findAll", query = "SELECT a FROM AdmAntiguedad a"),
    @NamedQuery(name = "AdmAntiguedad.findByIdAntiguedad", query = "SELECT a FROM AdmAntiguedad a WHERE a.idAntiguedad = :idAntiguedad"),
    @NamedQuery(name = "AdmAntiguedad.findByDescAntiguedad", query = "SELECT a FROM AdmAntiguedad a WHERE a.descAntiguedad = :descAntiguedad"),
    @NamedQuery(name = "AdmAntiguedad.findByValorAntiguedad", query = "SELECT a FROM AdmAntiguedad a WHERE a.valorAntiguedad = :valorAntiguedad")})
public class AdmAntiguedad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_antiguedad")
    private Integer idAntiguedad;
    @Size(max = 2147483647)
    @Column(name = "desc_antiguedad")
    private String descAntiguedad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_antiguedad")
    private BigDecimal valorAntiguedad;
    @OneToMany(mappedBy = "idAntiguedad", fetch = FetchType.LAZY)
    private List<AdmColaborador> admColaboradorList;

    public AdmAntiguedad() {
    }

    public AdmAntiguedad(Integer idAntiguedad) {
        this.idAntiguedad = idAntiguedad;
    }

    public Integer getIdAntiguedad() {
        return idAntiguedad;
    }

    public void setIdAntiguedad(Integer idAntiguedad) {
        this.idAntiguedad = idAntiguedad;
    }

    public String getDescAntiguedad() {
        return descAntiguedad;
    }

    public void setDescAntiguedad(String descAntiguedad) {
        this.descAntiguedad = descAntiguedad;
    }

    public BigDecimal getValorAntiguedad() {
        return valorAntiguedad;
    }

    public void setValorAntiguedad(BigDecimal valorAntiguedad) {
        this.valorAntiguedad = valorAntiguedad;
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
        hash += (idAntiguedad != null ? idAntiguedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmAntiguedad)) {
            return false;
        }
        AdmAntiguedad other = (AdmAntiguedad) object;
        if ((this.idAntiguedad == null && other.idAntiguedad != null) || (this.idAntiguedad != null && !this.idAntiguedad.equals(other.idAntiguedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiguedad.AdmAntiguedad[ idAntiguedad=" + idAntiguedad + " ]";
    }
    
}
