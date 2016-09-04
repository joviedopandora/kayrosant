/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import adm.sys.dao.AdmCrgxcol;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lfchacon
 */
@Entity
@Table(name = "cmp_conspedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmpConspedido.findAll", query = "SELECT c FROM CmpConspedido c"),
    @NamedQuery(name = "CmpConspedido.findByCcpId", query = "SELECT c FROM CmpConspedido c WHERE c.ccpId = :ccpId"),
    @NamedQuery(name = "CmpConspedido.findByStrId", query = "SELECT c FROM CmpConspedido c WHERE c.strId = :strId"),
    @NamedQuery(name = "CmpConspedido.findByCcpCantaprob", query = "SELECT c FROM CmpConspedido c WHERE c.ccpCantaprob = :ccpCantaprob"),
    @NamedQuery(name = "CmpConspedido.findByCcpCantinv", query = "SELECT c FROM CmpConspedido c WHERE c.ccpCantinv = :ccpCantinv"),
    @NamedQuery(name = "CmpConspedido.findByCcpCantcomprar", query = "SELECT c FROM CmpConspedido c WHERE c.ccpCantcomprar = :ccpCantcomprar"),
    @NamedQuery(name = "CmpConspedido.findByCcpFechaproceso", query = "SELECT c FROM CmpConspedido c WHERE c.ccpFechaproceso = :ccpFechaproceso"),
    //cantidad de consolidado por tarea
    @NamedQuery(name = "CmpConspedido.findByTareaCount", query = "SELECT COUNT(c.ccpId ) FROM CmpConspedido c WHERE c.strId = :strId"),
    //conslidado pedido que no tenga orden de compra...
    @NamedQuery(name = "CmpConspedido.findBTareaSinOrdenCompra", query = "SELECT c FROM CmpConspedido c "
        + "WHERE c.strId = :strId AND c.ccpCantcomprar > 0  AND"
    + " c.ccpId NOT IN (SELECT xc.ccpId FROM CmpConsolcompra cc JOIN cc.ccpId xc  WHERE xc.ccpId = c.ccpId )"),
    //Aprobados que están en inventario y tiene orden de compra
     @NamedQuery(name = "CmpConspedido.findByTareaInventario",query = "SELECT c FROM CmpConspedido c WHERE c.strId = :strId AND c.ccpCantinv  <> :ccpCantinv")
})
public class CmpConspedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ccp_id")
    private Long ccpId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "str_id")
    private long strId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ccp_cantaprob")
    private int ccpCantaprob;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ccp_cantinv")
    private int ccpCantinv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ccp_cantcomprar")
    private int ccpCantcomprar;
    @Column(name = "ccp_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ccpFechaproceso;
    @JoinColumn(name = "prd_id", referencedColumnName = "prd_id")
    @ManyToOne(optional = false)
    private InvProducto prdId;
    @JoinColumn(name = "psp_id", referencedColumnName = "psp_id")
    @ManyToOne(optional = false)
    private InvPresentprod pspId;
    @JoinColumn(name = "mar_id", referencedColumnName = "mar_id")
    @ManyToOne(optional = false)
    private InvMarca marId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne(optional = false)
    private AdmCrgxcol cxcId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccpId")
    private List<CmpConsolcompra> cmpConsolcompraList;
   

    public CmpConspedido() {
    }

    public CmpConspedido(Long ccpId) {
        this.ccpId = ccpId;
    }

    public CmpConspedido(Long ccpId, long strId, int ccpCantaprob, int ccpCantinv, int ccpCantcomprar) {
        this.ccpId = ccpId;
        this.strId = strId;
        this.ccpCantaprob = ccpCantaprob;
        this.ccpCantinv = ccpCantinv;
        this.ccpCantcomprar = ccpCantcomprar;
    }

    public Long getCcpId() {
        return ccpId;
    }

    public void setCcpId(Long ccpId) {
        this.ccpId = ccpId;
    }

    public long getStrId() {
        return strId;
    }

    public void setStrId(long strId) {
        this.strId = strId;
    }

    public int getCcpCantaprob() {
        return ccpCantaprob;
    }

    public void setCcpCantaprob(int ccpCantaprob) {
        this.ccpCantaprob = ccpCantaprob;
    }

    public int getCcpCantinv() {
        return ccpCantinv;
    }

    public void setCcpCantinv(int ccpCantinv) {
        this.ccpCantinv = ccpCantinv;
    }

    public int getCcpCantcomprar() {
        return ccpCantcomprar;
    }

    public void setCcpCantcomprar(int ccpCantcomprar) {
        this.ccpCantcomprar = ccpCantcomprar;
    }

    public Date getCcpFechaproceso() {
        return ccpFechaproceso;
    }

    public void setCcpFechaproceso(Date ccpFechaproceso) {
        this.ccpFechaproceso = ccpFechaproceso;
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

    public InvMarca getMarId() {
        return marId;
    }

    public void setMarId(InvMarca marId) {
        this.marId = marId;
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
        hash += (ccpId != null ? ccpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmpConspedido)) {
            return false;
        }
        CmpConspedido other = (CmpConspedido) object;
        if ((this.ccpId == null && other.ccpId != null) || (this.ccpId != null && !this.ccpId.equals(other.ccpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.CmpConspedido[ ccpId=" + ccpId + " ]";
    }

    /**
     * @return the cmpConsolcompraList
     */
    public List<CmpConsolcompra> getCmpConsolcompraList() {
        return cmpConsolcompraList;
    }

    /**
     * @param cmpConsolcompraList the cmpConsolcompraList to set
     */
    public void setCmpConsolcompraList(List<CmpConsolcompra> cmpConsolcompraList) {
        this.cmpConsolcompraList = cmpConsolcompraList;
    }

   
}
