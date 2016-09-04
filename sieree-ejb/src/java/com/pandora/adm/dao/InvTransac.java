/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import adm.sys.dao.AdmCrgxcol;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "inv_transac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvTransac.findAll", query = "SELECT i FROM InvTransac i"),
    @NamedQuery(name = "InvTransac.findByItrId", query = "SELECT i FROM InvTransac i WHERE i.itrId = :itrId"),
    @NamedQuery(name = "InvTransac.findByItrCant", query = "SELECT i FROM InvTransac i WHERE i.itrCant = :itrCant"),
    @NamedQuery(name = "InvTransac.findByItrFecpro", query = "SELECT i FROM InvTransac i WHERE i.itrFecpro = :itrFecpro"),
    @NamedQuery(name = "InvTransac.findByItrObsr", query = "SELECT i FROM InvTransac i WHERE i.itrObsr = :itrObsr"),
    @NamedQuery(name = "InvTransac.findByItrAnultr", query = "SELECT i FROM InvTransac i WHERE i.itrAnultr = :itrAnultr"),
    @NamedQuery(name = "InvTransac.findByItrIdtranul", query = "SELECT i FROM InvTransac i WHERE i.itrIdtranul = :itrIdtranul"),
    @NamedQuery(name = "InvTransac.findByItrEstado", query = "SELECT i FROM InvTransac i WHERE i.itrEstado = :itrEstado"),
    @NamedQuery(name = "InvTransac.findByIndversion", query = "SELECT i FROM InvTransac i WHERE i.indversion = :indversion")})
public class InvTransac implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(allocationSize = 1, sequenceName = "inv_transac_itr_id_seq", name = "inv_transac_itr_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inv_transac_itr_id_seq")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "itr_id")
    private Long itrId;
    @Column(name = "itr_cant")
    private Integer itrCant;
    @Column(name = "itr_fecpro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date itrFecpro;
    @Size(max = 2147483647)
    @Column(name = "itr_obsr")
    private String itrObsr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "itr_anultr")
    private boolean itrAnultr;
    @Column(name = "itr_idtranul")
    private BigInteger itrIdtranul;
    @Basic(optional = false)
    @NotNull
    @Column(name = "itr_estado")
    private boolean itrEstado;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "indversion")
    private int indversion;
    @Column(name = "itr_valor")
    private BigDecimal itrValor;
    @Column(name = "str_id")
    private Long strId;
    @JoinColumn(name = "ttr_id", referencedColumnName = "ttr_id")
    @ManyToOne(optional = false)
    private InvTipotransc ttrId;
    @JoinColumn(name = "inv_id", referencedColumnName = "inv_id")
    @ManyToOne
    private InvInvent invId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;
    @JoinColumn(name = "fact_id", referencedColumnName = "fact_id")
    @ManyToOne
    private CmpFactura factId;
    @JoinColumn(name = "rmi_id", referencedColumnName = "rmi_id")
    @ManyToOne
    private CmpRemisioninv rmiId;
    @OneToMany(mappedBy = "itrId")
    private List<InvGrupotransac> invGrupotransacList;
    @Size(max = 2147483647)
    @Column(name = "itr_kardex")
    private String itrKardex;
    @JoinColumn(name = "opr_id", referencedColumnName = "opr_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PopOrdenprod popOrdenprod;

    public InvTransac() {
    }

    public InvTransac(Long itrId) {
        this.itrId = itrId;
    }

    public InvTransac(Long itrId, boolean itrAnultr, boolean itrEstado, int indversion) {
        this.itrId = itrId;
        this.itrAnultr = itrAnultr;
        this.itrEstado = itrEstado;
        this.indversion = indversion;
    }

    public Long getItrId() {
        return itrId;
    }

    public void setItrId(Long itrId) {
        this.itrId = itrId;
    }

    public Integer getItrCant() {
        return itrCant;
    }

    public void setItrCant(Integer itrCant) {
        this.itrCant = itrCant;
    }

    public Date getItrFecpro() {
        return itrFecpro;
    }

    public void setItrFecpro(Date itrFecpro) {
        this.itrFecpro = itrFecpro;
    }

    public String getItrObsr() {
        return itrObsr;
    }

    public void setItrObsr(String itrObsr) {
        this.itrObsr = itrObsr;
    }

    public boolean getItrAnultr() {
        return itrAnultr;
    }

    public void setItrAnultr(boolean itrAnultr) {
        this.itrAnultr = itrAnultr;
    }

    public BigInteger getItrIdtranul() {
        return itrIdtranul;
    }

    public void setItrIdtranul(BigInteger itrIdtranul) {
        this.itrIdtranul = itrIdtranul;
    }

    public boolean getItrEstado() {
        return itrEstado;
    }

    public void setItrEstado(boolean itrEstado) {
        this.itrEstado = itrEstado;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    public InvTipotransc getTtrId() {
        return ttrId;
    }

    public void setTtrId(InvTipotransc ttrId) {
        this.ttrId = ttrId;
    }

    public InvInvent getInvId() {
        return invId;
    }

    public void setInvId(InvInvent invId) {
        this.invId = invId;
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itrId != null ? itrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvTransac)) {
            return false;
        }
        InvTransac other = (InvTransac) object;
        if ((this.itrId == null && other.itrId != null) || (this.itrId != null && !this.itrId.equals(other.itrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.InvTransac[ itrId=" + itrId + " ]";
    }

    /**
     * @return the itrValor
     */
    public BigDecimal getItrValor() {
        return itrValor;
    }

    /**
     * @param itrValor the itrValor to set
     */
    public void setItrValor(BigDecimal itrValor) {
        this.itrValor = itrValor;
    }

    /**
     * @return the strId
     */
    public Long getStrId() {
        return strId;
    }

    /**
     * @param strId the strId to set
     */
    public void setStrId(Long strId) {
        this.strId = strId;
    }

    /**
     * @return the factId
     */
    public CmpFactura getFactId() {
        return factId;
    }

    /**
     * @param factId the factId to set
     */
    public void setFactId(CmpFactura factId) {
        this.factId = factId;
    }

    /**
     * @return the rmiId
     */
    public CmpRemisioninv getRmiId() {
        return rmiId;
    }

    /**
     * @param rmiId the rmiId to set
     */
    public void setRmiId(CmpRemisioninv rmiId) {
        this.rmiId = rmiId;
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
     * @return the itrKardex
     */
    public String getItrKardex() {
        return itrKardex;
    }

    /**
     * @param itrKardex the itrKardex to set
     */
    public void setItrKardex(String itrKardex) {
        this.itrKardex = itrKardex;
    }

    /**
     * @return the popOrdenprod
     */
    public PopOrdenprod getPopOrdenprod() {
        return popOrdenprod;
    }

    /**
     * @param popOrdenprod the popOrdenprod to set
     */
    public void setPopOrdenprod(PopOrdenprod popOrdenprod) {
        this.popOrdenprod = popOrdenprod;
    }
}
