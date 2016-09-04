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
@Table(name = "inv_marca")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvMarca.findAll", query = "SELECT i FROM InvMarca i ORDER BY i.marNombre"),
    @NamedQuery(name = "InvMarca.findByMarId", query = "SELECT i FROM InvMarca i WHERE i.marId = :marId"),
    @NamedQuery(name = "InvMarca.findByMarNombre", query = "SELECT i FROM InvMarca i WHERE i.marNombre = :marNombre"),
    @NamedQuery(name = "InvMarca.findByMarDesc", query = "SELECT i FROM InvMarca i WHERE i.marDesc = :marDesc"),
    @NamedQuery(name = "InvMarca.findByMarEst", query = "SELECT i FROM InvMarca i WHERE i.marEst = :marEst ORDER BY i.marNombre"),
    @NamedQuery(name = "InvMarca.findByIndversion", query = "SELECT i FROM InvMarca i WHERE i.indversion = :indversion")})
public class InvMarca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mar_id")
    private Integer marId;
    @Size(max = 200)
    @Column(name = "mar_nombre")
    private String marNombre;
    @Size(max = 2147483647)
    @Column(name = "mar_desc")
    private String marDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mar_est")
    private boolean marEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @OneToMany(mappedBy = "marId")
    private List<InvInvent> invInventList;
    @OneToMany(mappedBy = "marId")
    private List<InvMarcxprod> invMarcxprodList;
    @OneToMany(mappedBy = "marId")
    private List<InvProdxprov> invProdxprovList;
    @OneToMany(mappedBy = "marId")
    private List<CmpPxraprob> cmpPxraprobList;
    @OneToMany(mappedBy = "marId")
    private List<CmpConsolcompra> cmpConsolcompraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marId")
    private List<CmpConspedido> cmpConspedidoList;

    public InvMarca() {
    }

    public InvMarca(Integer marId) {
        this.marId = marId;
    }

    public InvMarca(Integer marId, boolean marEst, int indversion) {
        this.marId = marId;
        this.marEst = marEst;
        this.indversion = indversion;
    }

    public Integer getMarId() {
        return marId;
    }

    public void setMarId(Integer marId) {
        this.marId = marId;
    }

    public String getMarNombre() {
        return marNombre;
    }

    public void setMarNombre(String marNombre) {
        this.marNombre = marNombre;
    }

    public String getMarDesc() {
        return marDesc;
    }

    public void setMarDesc(String marDesc) {
        this.marDesc = marDesc;
    }

    public boolean getMarEst() {
        return marEst;
    }

    public void setMarEst(boolean marEst) {
        this.marEst = marEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
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
        hash += (marId != null ? marId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvMarca)) {
            return false;
        }
        InvMarca other = (InvMarca) object;
        if ((this.marId == null && other.marId != null) || (this.marId != null && !this.marId.equals(other.marId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.InvMarca[ marId=" + marId + " ]";
    }

    /**
     * @return the invMarcxprodList
     */
    public List<InvMarcxprod> getInvMarcxprodList() {
        return invMarcxprodList;
    }

    /**
     * @param invMarcxprodList the invMarcxprodList to set
     */
    public void setInvMarcxprodList(List<InvMarcxprod> invMarcxprodList) {
        this.invMarcxprodList = invMarcxprodList;
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
