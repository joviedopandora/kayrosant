/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

import adm.sys.dao.AdmCrgxcol;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "fin_cronogramapago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinCronogramapago.findAll", query = "SELECT f FROM FinCronogramapago f"),
    @NamedQuery(name = "FinCronogramapago.findByCpgId", query = "SELECT f FROM FinCronogramapago f WHERE f.cpgId = :cpgId"),
    @NamedQuery(name = "FinCronogramapago.findByCpgFechaproceso", query = "SELECT f FROM FinCronogramapago f WHERE f.cpgFechaproceso = :cpgFechaproceso"),
    @NamedQuery(name = "FinCronogramapago.findByCpgEstado", query = "SELECT f FROM FinCronogramapago f WHERE f.cpgEstado = :cpgEstado"),
    @NamedQuery(name = "FinCronogramapago.findByStrId", query = "SELECT f FROM FinCronogramapago f WHERE f.strId = :strId"),
    @NamedQuery(name = "FinCronogramapago.cantXStrId", query = "SELECT COUNT(f.cpgId) FROM FinCronogramapago f WHERE f.strId = :strId"),
    @NamedQuery(name = "FinCronogramapago.cantXEstadoXStrId", query = "SELECT COUNT(f.cpgId) FROM FinCronogramapago f "
        + " WHERE f.cpgEstado = :cpgEstado AND f.strId = :strId")
})
public class FinCronogramapago implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "fin_cronogramapago_cpg_id_seq", name = "fin_cronogramapago_cpg_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "fin_cronogramapago_cpg_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @Column(name = "cpg_id")
    private Long cpgId;
    @Column(name = "cpg_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cpgFechaproceso;
    @Column(name = "cpg_estado")
    private Boolean cpgEstado;
    @Column(name = "str_id")
    private Long strId;
    @JoinColumn(name = "fxc_id", referencedColumnName = "fxc_id")
    @ManyToOne
    private FinFpgxcta fxcId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cpgId")
    private List<FinTransferencia> finTransferenciaList;

    public FinCronogramapago() {
    }

    public FinCronogramapago(Long cpgId) {
        this.cpgId = cpgId;
    }

    public Long getCpgId() {
        return cpgId;
    }

    public void setCpgId(Long cpgId) {
        this.cpgId = cpgId;
    }

    public Date getCpgFechaproceso() {
        return cpgFechaproceso;
    }

    public void setCpgFechaproceso(Date cpgFechaproceso) {
        this.cpgFechaproceso = cpgFechaproceso;
    }

    public Boolean getCpgEstado() {
        return cpgEstado;
    }

    public void setCpgEstado(Boolean cpgEstado) {
        this.cpgEstado = cpgEstado;
    }

    public Long getStrId() {
        return strId;
    }

    public void setStrId(Long strId) {
        this.strId = strId;
    }

    public FinFpgxcta getFxcId() {
        return fxcId;
    }

    public void setFxcId(FinFpgxcta fxcId) {
        this.fxcId = fxcId;
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    @XmlTransient
    public List<FinTransferencia> getFinTransferenciaList() {
        return finTransferenciaList;
    }

    public void setFinTransferenciaList(List<FinTransferencia> finTransferenciaList) {
        this.finTransferenciaList = finTransferenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpgId != null ? cpgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinCronogramapago)) {
            return false;
        }
        FinCronogramapago other = (FinCronogramapago) object;
        if ((this.cpgId == null && other.cpgId != null) || (this.cpgId != null && !this.cpgId.equals(other.cpgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinCronogramapago[ cpgId=" + cpgId + " ]";
    }
    
}
