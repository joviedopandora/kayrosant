/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "inv_presentprod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvPresentprod.findAll", query = "SELECT i FROM InvPresentprod i ORDER BY i.pspNombre"),
    @NamedQuery(name = "InvPresentprod.findByPspId", query = "SELECT i FROM InvPresentprod i WHERE i.pspId = :pspId"),
    @NamedQuery(name = "InvPresentprod.findByPspNombre", query = "SELECT i FROM InvPresentprod i WHERE i.pspNombre = :pspNombre"),
    @NamedQuery(name = "InvPresentprod.findByPspDesc", query = "SELECT i FROM InvPresentprod i WHERE i.pspDesc = :pspDesc"),
    @NamedQuery(name = "InvPresentprod.findByPspEst", query = "SELECT i FROM InvPresentprod i WHERE i.pspEst = :pspEst ORDER BY i.pspNombre"),
    @NamedQuery(name = "InvPresentprod.findByIndVersion", query = "SELECT i FROM InvPresentprod i WHERE i.indVersion = :indVersion")
})
public class InvPresentprod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "psp_id")
    private Integer pspId;
    @Size(max = 200)
    @Column(name = "psp_nombre")
    private String pspNombre;
    @Size(max = 2147483647)
    @Column(name = "psp_desc")
    private String pspDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "psp_est")
    private boolean pspEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ind_version")
    private int indVersion;
    @OneToMany(mappedBy = "pspId")
    private List<CmpProdxreq> cmpProdxreqList;
    @OneToMany(mappedBy = "pspId")
    private List<InvInvent> invInventList;
    @OneToMany(mappedBy = "pspId")
    private List<InvPresntxprod> invPresntxprodList;
    @OneToMany(mappedBy = "pspId")
    private List<InvProdxprov> invProdxprovList;
    @OneToMany(mappedBy = "pspId")
    private List<CmpPxraprob> cmpPxraprobList;
    @OneToMany(mappedBy = "pspId")
    private List<CmpConsolcompra> cmpConsolcompraList;
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "pspId")
    private List<CmpConspedido> cmpConspedidoList;
    

    public InvPresentprod() {
    }

    public InvPresentprod(Integer pspId) {
        this.pspId = pspId;
    }

    public InvPresentprod(Integer pspId, boolean pspEst, int indVersion) {
        this.pspId = pspId;
        this.pspEst = pspEst;
        this.indVersion = indVersion;
    }

    public Integer getPspId() {
        return pspId;
    }

    public void setPspId(Integer pspId) {
        this.pspId = pspId;
    }

    public String getPspNombre() {
        return pspNombre;
    }

    public void setPspNombre(String pspNombre) {
        this.pspNombre = pspNombre;
    }

    public String getPspDesc() {
        return pspDesc;
    }

    public void setPspDesc(String pspDesc) {
        this.pspDesc = pspDesc;
    }

    public boolean getPspEst() {
        return pspEst;
    }

    public void setPspEst(boolean pspEst) {
        this.pspEst = pspEst;
    }

    public int getIndVersion() {
        return indVersion;
    }

    public void setIndVersion(int indVersion) {
        this.indVersion = indVersion;
    }

    @XmlTransient
    public List<CmpProdxreq> getCmpProdxreqList() {
        return cmpProdxreqList;
    }

    public void setCmpProdxreqList(List<CmpProdxreq> cmpProdxreqList) {
        this.cmpProdxreqList = cmpProdxreqList;
    }

    @XmlTransient
    public List<InvInvent> getInvInventList() {
        return invInventList;
    }

    public void setInvInventList(List<InvInvent> invInventList) {
        this.invInventList = invInventList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pspId != null ? pspId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvPresentprod)) {
            return false;
        }
        InvPresentprod other = (InvPresentprod) object;
        if ((this.pspId == null && other.pspId != null) || (this.pspId != null && !this.pspId.equals(other.pspId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.InvPresentprod[ pspId=" + pspId + " ]";
    }

    /**
     * @return the invPresntxprodList
     */
    public List<InvPresntxprod> getInvPresntxprodList() {
        return invPresntxprodList;
    }

    /**
     * @param invPresntxprodList the invPresntxprodList to set
     */
    public void setInvPresntxprodList(List<InvPresntxprod> invPresntxprodList) {
        this.invPresntxprodList = invPresntxprodList;
    }

    /**
     * @return the invProdxprovList
     */
    public List<InvProdxprov> getInvProdxprovList() {
        return invProdxprovList;
    }

    /**
     * @param invProdxprovList the invProdxprovList to set
     */
    public void setInvProdxprovList(List<InvProdxprov> invProdxprovList) {
        this.invProdxprovList = invProdxprovList;
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

    /**
     * @return the cmpConspedidoList
     */
    public List<CmpConspedido> getCmpConspedidoList() {
        return cmpConspedidoList;
    }

    /**
     * @param cmpConspedidoList the cmpConspedidoList to set
     */
    public void setCmpConspedidoList(List<CmpConspedido> cmpConspedidoList) {
        this.cmpConspedidoList = cmpConspedidoList;
    }
}
