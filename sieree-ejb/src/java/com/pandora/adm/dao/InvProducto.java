/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.venta.dao.VntProdxsrv;
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
@Table(name = "inv_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvProducto.findMaxId", query = "SELECT MAX(i.prdId) FROM InvProducto i"),
    @NamedQuery(name = "InvProducto.findAll", query = "SELECT i FROM InvProducto i ORDER BY i.prdNombre"),
    @NamedQuery(name = "InvProducto.findByPrdId", query = "SELECT i FROM InvProducto i WHERE i.prdId = :prdId"),
    @NamedQuery(name = "InvProducto.findByPrdNombre", query = "SELECT i FROM InvProducto i WHERE i.prdNombre = :prdNombre"),
    @NamedQuery(name = "InvProducto.findByPrdDesc", query = "SELECT i FROM InvProducto i WHERE i.prdDesc = :prdDesc"),
    @NamedQuery(name = "InvProducto.findByPrdEst", query = "SELECT i FROM InvProducto i WHERE i.prdEst = :prdEst"),
    @NamedQuery(name = "InvProducto.findByIndversion", query = "SELECT i FROM InvProducto i WHERE i.indversion = :indversion"),
    @NamedQuery(name = "InvProducto.findByPrdEsp", query = "SELECT i FROM InvProducto i WHERE i.prdEsp = :prdEsp"),
    @NamedQuery(name = "InvProducto.findByPrdOcasnl", query = "SELECT i FROM InvProducto i WHERE i.prdOcasnl = :prdOcasnl"),
    @NamedQuery(name = "InvProducto.prodXCat", query = "SELECT i FROM InvProducto i JOIN i.cpdId cat "
        + " WHERE cat.cpdId = :cpdId AND i.prdUnico = :prdUnico AND i.prdEst = :prdEst ORDER BY i.prdNombre "),
    @NamedQuery(name = "InvProducto.prodXNombDesc", query = "SELECT i FROM InvProducto i "
    + " WHERE (i.prdNombre LIKE :prdNombre OR i.prdDesc LIKE :prdDesc) AND i.prdUnico = :prdUnico ORDER BY i.prdNombre "),
    //Productos por categoria
    @NamedQuery(name = "InvProducto.findByProdXCat", query = "SELECT i FROM InvProducto i JOIN i.cpdId cat "
    + "WHERE cat.cpdId = :cpdId ORDER BY i.prdNombre"),
    //Lista de productos varios
    @NamedQuery(name = "InvProducto.prodVarios", query = "SELECT i FROM InvProducto i WHERE i.prdVarios = :prdVarios")
})
public class InvProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "prd_id")
    private Integer prdId;
    @Size(max = 500)
    @Column(name = "prd_nombre")
    private String prdNombre;
    @Size(max = 2147483647)
    @Column(name = "prd_desc")
    private String prdDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prd_est")
    private boolean prdEst;
    @Basic(optional = false)
    @Version
    @Column(name = "indversion")
    private int indversion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prd_esp")
    private boolean prdEsp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prd_ocasnl")
    private boolean prdOcasnl;
    @NotNull
    @Column(name = "prd_unico")
    private boolean prdUnico;
    @Column(name = "prd_varios")
    private boolean prdVarios;
    @OneToMany(mappedBy = "prdId")
    private List<CmpProdxreq> cmpProdxreqList;
    @JoinColumn(name = "cpd_id", referencedColumnName = "cpd_id")
    @ManyToOne
    private InvCatprod cpdId;
    @OneToMany(mappedBy = "prdId")
    private List<InvInvent> invInventList;
    @OneToMany(mappedBy = "prdId")
    private List<InvMarcxprod> invMarcxprodList;
    @OneToMany(mappedBy = "prdId")
    private List<InvPresntxprod> invPresntxprodList;
    @OneToMany(mappedBy = "prdId")
    private List<InvProdxprov> invProdxprovList;
    @OneToMany(mappedBy = "prdId")
    private List<CmpConsolcompra> cmpConsolcompraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prdId")
    private List<CmpConspedido> cmpConspedidoList;
      @OneToMany(mappedBy = "prdId")
    private List<VntProdxsrv> vntProdxsrvList;
    @OneToMany(mappedBy = "prdId")
    private List<PopProdxservxop> popProdxservxopList;
   /* @Size(max = 50)
    @Column(name = "prd_codigo")
    private String prdCodigo;*/
    

    public InvProducto() {
    }

    public InvProducto(Integer prdId) {
        this.prdId = prdId;
    }

    public InvProducto(Integer prdId, boolean prdEst, int indversion, boolean prdEsp, boolean prdOcasnl) {
        this.prdId = prdId;
        this.prdEst = prdEst;
        this.indversion = indversion;
        this.prdEsp = prdEsp;
        this.prdOcasnl = prdOcasnl;
    }

    public Integer getPrdId() {
        return prdId;
    }

    public void setPrdId(Integer prdId) {
        this.prdId = prdId;
    }

    public String getPrdNombre() {
        return prdNombre;
    }

    public void setPrdNombre(String prdNombre) {
        this.prdNombre = prdNombre;
    }

    public String getPrdDesc() {
        return prdDesc;
    }

    public void setPrdDesc(String prdDesc) {
        this.prdDesc = prdDesc;
    }

    public boolean getPrdEst() {
        return prdEst;
    }

    public void setPrdEst(boolean prdEst) {
        this.prdEst = prdEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    public boolean getPrdEsp() {
        return prdEsp;
    }

    public void setPrdEsp(boolean prdEsp) {
        this.prdEsp = prdEsp;
    }

    public boolean getPrdOcasnl() {
        return prdOcasnl;
    }

    public void setPrdOcasnl(boolean prdOcasnl) {
        this.prdOcasnl = prdOcasnl;
    }

    @XmlTransient
    public List<CmpProdxreq> getCmpProdxreqList() {
        return cmpProdxreqList;
    }

    public void setCmpProdxreqList(List<CmpProdxreq> cmpProdxreqList) {
        this.cmpProdxreqList = cmpProdxreqList;
    }

    public InvCatprod getCpdId() {
        return cpdId;
    }

    public void setCpdId(InvCatprod cpdId) {
        this.cpdId = cpdId;
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
        hash += (prdId != null ? prdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvProducto)) {
            return false;
        }
        InvProducto other = (InvProducto) object;
        if ((this.prdId == null && other.prdId != null) || (this.prdId != null && !this.prdId.equals(other.prdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.InvProducto[ prdId=" + prdId + " ]";
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

    /**
     * @return the vntProdxsrvList
     */
    public List<VntProdxsrv> getVntProdxsrvList() {
        return vntProdxsrvList;
    }

    /**
     * @param vntProdxsrvList the vntProdxsrvList to set
     */
    public void setVntProdxsrvList(List<VntProdxsrv> vntProdxsrvList) {
        this.vntProdxsrvList = vntProdxsrvList;
    }

    /**
     * @return the popProdxservxopList
     */
    public List<PopProdxservxop> getPopProdxservxopList() {
        return popProdxservxopList;
    }

    /**
     * @param popProdxservxopList the popProdxservxopList to set
     */
    public void setPopProdxservxopList(List<PopProdxservxop> popProdxservxopList) {
        this.popProdxservxopList = popProdxservxopList;
    }

    /**
     * @return the prdVarios
     */
    public Boolean getPrdVarios() {
        return prdVarios;
    }

    /**
     * @param prdVarios the prdVarios to set
     */
    public void setPrdVarios(Boolean prdVarios) {
        this.prdVarios = prdVarios;
    }

    /**
     * @return the prdUnico
     */
    public boolean isPrdUnico() {
        return prdUnico;
    }

    /**
     * @param prdUnico the prdUnico to set
     */
    public void setPrdUnico(boolean prdUnico) {
        this.prdUnico = prdUnico;
    }

  
}
