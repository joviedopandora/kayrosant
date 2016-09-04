/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "cmp_prodxreq")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmpProdxreq.findAll", query = "SELECT c FROM CmpProdxreq c"),
    @NamedQuery(name = "CmpProdxreq.findByPxrId", query = "SELECT c FROM CmpProdxreq c WHERE c.pxrId = :pxrId"),
    @NamedQuery(name = "CmpProdxreq.findByPxrCantsol", query = "SELECT c FROM CmpProdxreq c WHERE c.pxrCantsol = :pxrCantsol"),
    @NamedQuery(name = "CmpProdxreq.findByPxrRechaza", query = "SELECT c FROM CmpProdxreq c WHERE c.pxrRechaza = :pxrRechaza"),
    @NamedQuery(name = "CmpProdxreq.prodXReq", query = "SELECT c FROM CmpProdxreq c JOIN c.crqId crq "
    + "WHERE crq.crqId = :crqId ORDER BY c.prdId.prdNombre "),
    //Cantidad de productos por requisición sin asignación
    @NamedQuery(name = "CmpProdxreq.cantProdNoAsig", query = "SELECT count(pra.praId) FROM CmpProdxreq p JOIN p.cmpPxraprobList pra "
    + "WHERE p.pxrId = :pxrId  "),
    @NamedQuery(name = "CmpProdxreq.actEstRechaza", query = "UPDATE CmpProdxreq c SET c.pxrRechaza = :pxrRechaza "
    + "WHERE c.pxrId = :pxrId ")
})
public class CmpProdxreq implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "cmp_prodxreq_pxr_id_seq", allocationSize = 1, name = "pxr_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pxr_id_seq")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pxr_id")
    private Long pxrId;
    @Column(name = "pxr_cantsol")
    private Integer pxrCantsol;
    @Column(name = "indversion")
    private Integer indversion;
    @Column(name = "pxr_rechaza")
    @NotNull
    private Boolean pxrRechaza;
    @Column(name="pxr_obsrrechza")
    private String pxrObsrrechza;
    @Column(name="pxr_obsrsol")
    private String pxrObsrsol;
    @JoinColumn(name = "prd_id", referencedColumnName = "prd_id")
    @ManyToOne
    private InvProducto prdId;
    @JoinColumn(name = "psp_id", referencedColumnName = "psp_id")
    @ManyToOne
    private InvPresentprod pspId;
    @JoinColumn(name = "crq_id", referencedColumnName = "crq_id")
    @ManyToOne
    private CmpRequiscomp crqId;
    @OneToMany(mappedBy = "pxrId")
    private List<CmpPxraprob> cmpPxraprobList;

    public CmpProdxreq() {
    }

    public CmpProdxreq(Long pxrId) {
        this.pxrId = pxrId;
    }

    public Long getPxrId() {
        return pxrId;
    }

    public void setPxrId(Long pxrId) {
        this.pxrId = pxrId;
    }

    public Integer getPxrCantsol() {
        return pxrCantsol;
    }

    public void setPxrCantsol(Integer pxrCantsol) {
        this.pxrCantsol = pxrCantsol;
    }

    public InvProducto getPrdId() {
        return prdId;
    }

    public void setPrdId(InvProducto prdId) {
        this.prdId = prdId;
    }

    public InvPresentprod getPspId() {
        return pspId;
    }

    public void setPspId(InvPresentprod pspId) {
        this.pspId = pspId;
    }

    public CmpRequiscomp getCrqId() {
        return crqId;
    }

    public void setCrqId(CmpRequiscomp crqId) {
        this.crqId = crqId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pxrId != null ? pxrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmpProdxreq)) {
            return false;
        }
        CmpProdxreq other = (CmpProdxreq) object;
        if ((this.pxrId == null && other.pxrId != null) || (this.pxrId != null && !this.pxrId.equals(other.pxrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.CmpProdxreq[ pxrId=" + pxrId + " ]";
    }

    /**
     * @return the indversion
     */
    public Integer getIndversion() {
        return indversion;
    }

    /**
     * @param indversion the indversion to set
     */
    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    /**
     * @return the cmpPxraprobList
     */
    public List<CmpPxraprob> getCmpPxraprobList() {
        return cmpPxraprobList;
    }

    /**
     * @param cmpPxraprobList the cmpPxraprobList to set
     */
    public void setCmpPxraprobList(List<CmpPxraprob> cmpPxraprobList) {
        this.cmpPxraprobList = cmpPxraprobList;
    }

    /**
     * @return the pxrRechaza
     */
    public Boolean getPxrRechaza() {
        return pxrRechaza;
    }

    /**
     * @param pxrRechaza the pxrRechaza to set
     */
    public void setPxrRechaza(Boolean pxrRechaza) {
        this.pxrRechaza = pxrRechaza;
    }

    /**
     * @return the pxrObsrrechza
     */
    public String getPxrObsrrechza() {
        return pxrObsrrechza;
    }

    /**
     * @param pxrObsrrechza the pxrObsrrechza to set
     */
    public void setPxrObsrrechza(String pxrObsrrechza) {
        this.pxrObsrrechza = pxrObsrrechza;
    }

    /**
     * @return the pxrObsrsol
     */
    public String getPxrObsrsol() {
        return pxrObsrsol;
    }

    /**
     * @param pxrObsrsol the pxrObsrsol to set
     */
    public void setPxrObsrsol(String pxrObsrsol) {
        this.pxrObsrsol = pxrObsrsol;
    }
}
