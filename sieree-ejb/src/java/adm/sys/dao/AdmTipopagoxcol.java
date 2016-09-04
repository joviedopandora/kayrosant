/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import com.pandora.mod.liquidacion.dao.PgLiquidacion;
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
@Table(name = "adm_tipopagoxcol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmTipopagoxcol.findAll", query = "SELECT a FROM AdmTipopagoxcol a"),
    @NamedQuery(name = "AdmTipopagoxcol.findByIdTipopago", query = "SELECT a FROM AdmTipopagoxcol a WHERE a.idTipopago = :idTipopago"),
    @NamedQuery(name = "AdmTipopagoxcol.findByDescTipopago", query = "SELECT a FROM AdmTipopagoxcol a WHERE a.descTipopago = :descTipopago"),
    @NamedQuery(name = "AdmTipopagoxcol.findByMaximovalorTipopago", query = "SELECT a FROM AdmTipopagoxcol a WHERE a.maximovalorTipopago = :maximovalorTipopago")})
public class AdmTipopagoxcol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipopago")
    private Integer idTipopago;
    @Size(max = 2147483647)
    @Column(name = "desc_tipopago")
    private String descTipopago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "maximovalor_tipopago")
    private BigDecimal maximovalorTipopago;
    @OneToMany(mappedBy = "idTipopago", fetch = FetchType.LAZY)
    private List<PgLiquidacion> pgLiquidacionList;

    public AdmTipopagoxcol() {
    }

    public AdmTipopagoxcol(Integer idTipopago) {
        this.idTipopago = idTipopago;
    }

    public Integer getIdTipopago() {
        return idTipopago;
    }

    public void setIdTipopago(Integer idTipopago) {
        this.idTipopago = idTipopago;
    }

    public String getDescTipopago() {
        return descTipopago;
    }

    public void setDescTipopago(String descTipopago) {
        this.descTipopago = descTipopago;
    }

    public BigDecimal getMaximovalorTipopago() {
        return maximovalorTipopago;
    }

    public void setMaximovalorTipopago(BigDecimal maximovalorTipopago) {
        this.maximovalorTipopago = maximovalorTipopago;
    }

    @XmlTransient
    public List<PgLiquidacion> getPgLiquidacionList() {
        return pgLiquidacionList;
    }

    public void setPgLiquidacionList(List<PgLiquidacion> pgLiquidacionList) {
        this.pgLiquidacionList = pgLiquidacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipopago != null ? idTipopago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmTipopagoxcol)) {
            return false;
        }
        AdmTipopagoxcol other = (AdmTipopagoxcol) object;
        if ((this.idTipopago == null && other.idTipopago != null) || (this.idTipopago != null && !this.idTipopago.equals(other.idTipopago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wil.AdmTipopagoxcol[ idTipopago=" + idTipopago + " ]";
    }
    
}
