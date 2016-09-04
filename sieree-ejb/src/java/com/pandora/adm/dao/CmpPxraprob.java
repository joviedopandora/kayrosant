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
@Table(name = "cmp_pxraprob")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmpPxraprob.findAll", query = "SELECT c FROM CmpPxraprob c"),
    @NamedQuery(name = "CmpPxraprob.findByPraId", query = "SELECT c FROM CmpPxraprob c WHERE c.praId = :praId"),
    @NamedQuery(name = "CmpPxraprob.findByCantAprob", query = "SELECT c FROM CmpPxraprob c WHERE c.cantAprob = :cantAprob"),
    @NamedQuery(name = "CmpPxraprob.findByPraEst", query = "SELECT c FROM CmpPxraprob c WHERE c.praEst = :praEst"),
    @NamedQuery(name = "CmpPxraprob.findByIndversion", query = "SELECT c FROM CmpPxraprob c WHERE c.indversion = :indversion"),
    @NamedQuery(name = "CmpPxraprob.findByStrId", query = "SELECT c FROM CmpPxraprob c WHERE c.strId = :strId ORDER BY c.pxrId.crqId.crqId, c.pxrId.prdId.prdId, c.pspId.pspId, c.marId.marId"),
    @NamedQuery(name = "CmpPxraprob.praXPxrId", query = "SELECT c FROM CmpPxraprob c JOIN c.pxrId pxr "
            + "WHERE pxr.pxrId = :pxrId "),
    @NamedQuery(name = "CmpPxraprob.praXPxrIdXMarXPsp", query = "SELECT c FROM CmpPxraprob c JOIN c.pxrId pxr "
            + " JOIN pxr.prdId prd JOIN c.pspId psp JOIN c.marId mar WHERE pxr.pxrId = :pxrId AND psp.pspId = :pspId "
            + "AND mar.marId = :marId "),
    @NamedQuery(name = "CmpPxraprob.praXCrqId",query =  "SELECT c FROM CmpPxraprob c JOIN c.pxrId pxr JOIN pxr.crqId crq WHERE crq.crqId = :crqId"),
    @NamedQuery(name = "CmpPxraprob.praConsPedidoXStr", query = "SELECT prd.prdId, prd.prdNombre, psp.pspId,  psp.pspNombre, "
        + "mar.marId, mar.marNombre,  sum(c.cantAprob) FROM CmpPxraprob c "
        + "JOIN c.pxrId pxr JOIN c.marId mar JOIN c.pspId psp JOIN pxr.prdId prd  "
        + "WHERE c.strId = :strId "
        + "GROUP BY prd.prdId, prd.prdNombre, psp.pspId,  psp.pspNombre, mar.marId, mar.marNombre "
        + "ORDER BY prd.prdNombre, psp.pspNombre, mar.marNombre "),
      @NamedQuery(name = "CmpPxraprob.praConsPedidoXSolicitud", query = "SELECT prd.prdId, prd.prdNombre, psp.pspId,  psp.pspNombre, "
        + "mar.marId, mar.marNombre,  sum(c.cantAprob) FROM CmpPxraprob c "
        + "JOIN c.pxrId pxr JOIN c.marId mar JOIN c.pspId psp JOIN pxr.prdId prd JOIN pxr.crqId crq  "
        + "WHERE  crq.crqId = :crqId "
        + "GROUP BY prd.prdId, prd.prdNombre, psp.pspId,  psp.pspNombre, mar.marId, mar.marNombre "
        + "ORDER BY prd.prdNombre, psp.pspNombre, mar.marNombre ")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "CmpPxraprob.praConsolidado",
            query = "SELECT  DISTINCT consolidado.prd_id, "
            + " consolidado.prd_nombre, "
            + "  consolidado.psp_id, "
            + " consolidado.psp_nombre,  "
            + "consolidado.mar_id, "
            + "consolidado.mar_nombre,  "
            + " consolidado.cant_aprob,"
            + " Coalesce(inventario.canttotal,0)  cantinv FROM (SELECT  "
            + " inv_producto.prd_id, "
            + " inv_producto.prd_nombre, "
            + " inv_presentprod.psp_id, "
            + "  inv_presentprod.psp_nombre,  "
            + " inv_marca.mar_id, "
            + " inv_marca.mar_nombre,  "
            + " sum(cmp_pxraprob.cant_aprob) AS cant_aprob "
            + " FROM "
            + " cmp_pxraprob, "
            + " cmp_prodxreq, "
            + " inv_marca, "
            + " inv_presentprod, "
            + " inv_producto "
            + " WHERE "
            + " cmp_prodxreq.pxr_id = cmp_pxraprob.pxr_id AND "
            + " inv_marca.mar_id = cmp_pxraprob.mar_id AND "
            + " inv_presentprod.psp_id = cmp_pxraprob.psp_id AND "
            + "  inv_producto.prd_id = cmp_prodxreq.prd_id "
            + " GROUP BY "
            + " inv_producto.prd_id, "
            + " inv_producto.prd_nombre, "
            + "  inv_presentprod.psp_id, "
            + "  inv_presentprod.psp_nombre, "
            + " inv_marca.mar_id, "
            + " inv_marca.mar_nombre ) AS consolidado "
            + " LEFT JOIN  (SELECT prd_id, psp_id, mar_id, SUM(inv_cant) AS canttotal "
            + " FROM inv_invent "
            + " WHERE inv_cant >0 "
            + " GROUP BY prd_id, psp_id,mar_id) AS inventario on  consolidado.prd_id = inventario.prd_id "
            + " AND consolidado.psp_id= consolidado.psp_id AND consolidado.mar_id = inventario.mar_id "
            + " ORDER BY  "
            + " consolidado.prd_nombre ASC,  "
            + " consolidado.psp_nombre ASC, "
            + " consolidado.mar_nombre ASC, "
            + " consolidado.cant_aprob DESC"),
      @NamedNativeQuery(name = "CmpPxraprob.praConsolidadoXSolicitud",
            query = "SELECT  consolidado.prd_id, "
            + " consolidado.prd_nombre, "
            + "  consolidado.psp_id, "
            + " consolidado.psp_nombre,  "
            + "consolidado.mar_id, "
            + "consolidado.mar_nombre,  "
            + " consolidado.cant_aprob,"
            + " Coalesce(inventario.canttotal,0)  cantinv FROM (SELECT  "
            + " inv_producto.prd_id, "
            + " inv_producto.prd_nombre, "
            + " inv_presentprod.psp_id, "
            + "  inv_presentprod.psp_nombre,  "
            + " inv_marca.mar_id, "
            + " inv_marca.mar_nombre,  "
            + " sum(cmp_pxraprob.cant_aprob) AS cant_aprob "
            + " FROM "
            + " cmp_pxraprob, "
            + " cmp_prodxreq, "
            + " inv_marca, "
            + " inv_presentprod, "
            + " inv_producto "
            + " WHERE "
            + " cmp_prodxreq.pxr_id = cmp_pxraprob.pxr_id AND "
            + " inv_marca.mar_id = cmp_pxraprob.mar_id AND "
            + " inv_presentprod.psp_id = cmp_pxraprob.psp_id AND "
            + " inv_producto.prd_id = cmp_prodxreq.prd_id AND"
            + " cmp_prodxreq.crq_id = ?"
            + " GROUP BY "
            + " inv_producto.prd_id, "
            + " inv_producto.prd_nombre, "
            + "  inv_presentprod.psp_id, "
            + "  inv_presentprod.psp_nombre, "
            + " inv_marca.mar_id, "
            + " inv_marca.mar_nombre ) AS consolidado "
            + " LEFT JOIN  (SELECT prd_id, psp_id, mar_id, SUM(inv_cant) AS canttotal "
            + " FROM inv_invent "
            + " WHERE inv_cant >0 "
            + " GROUP BY prd_id, psp_id,mar_id) AS inventario on  consolidado.prd_id = inventario.prd_id "
            + " AND consolidado.psp_id= consolidado.psp_id AND consolidado.mar_id = inventario.mar_id "
            + " ORDER BY  "
            + " consolidado.prd_nombre ASC,  "
            + " consolidado.psp_nombre ASC, "
            + " consolidado.mar_nombre ASC, "
            + " consolidado.cant_aprob DESC")
//    @NamedNativeQuery(name = "CmpPxraprob.praConsolidado", query = "SELECT "
//    + "inv_producto.prd_id, "
//    + "inv_producto.prd_nombre, "
//    + "inv_presentprod.psp_id, "
//    + "inv_presentprod.psp_nombre,  "
//    + "inv_marca.mar_id, "
//    + "inv_marca.mar_nombre,  "
//    + "sum(cmp_pxraprob.cant_aprob) AS cant_aprob "
//    + "FROM "
//    + "cmp_pxraprob, "
//    + "cmp_prodxreq, "
//    + "inv_marca, "
//    + "inv_presentprod, "
//    + "inv_producto "
//    + "WHERE "
//    + "cmp_prodxreq.pxr_id = cmp_pxraprob.pxr_id AND "
//    + "inv_marca.mar_id = cmp_pxraprob.mar_id AND "
//    + "inv_presentprod.psp_id = cmp_pxraprob.psp_id AND "
//    + "inv_producto.prd_id = cmp_prodxreq.prd_id "
//    + "GROUP BY "
//    + "inv_producto.prd_id, "
//    + "inv_producto.prd_nombre, "
//    + "inv_presentprod.psp_id, "
//    + "inv_presentprod.psp_nombre, "
//    + "inv_marca.mar_id, "
//    + "inv_marca.mar_nombre "
//    + "ORDER BY "
//    + "inv_producto.prd_nombre ASC, "
//    + "inv_presentprod.psp_nombre ASC, "
//    + "inv_marca.mar_nombre ASC, "
//    + "cant_aprob DESC")
})
public class CmpPxraprob implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "cmp_pxraprob_pra_id_seq", allocationSize = 1, name = "cmp_pxraprob_pra_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cmp_pxraprob_pra_id_seq")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pra_id")
    private Long praId;
    @Column(name = "cant_aprob")
    private Integer cantAprob;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pra_est")
    private boolean praEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @Column(name = "str_id")
    private Long strId;
    @OneToMany(mappedBy = "praId")
    private List<CmpDetremision> cmpDetremisionList;
    @JoinColumn(name = "psp_id", referencedColumnName = "psp_id")
    @ManyToOne
    private InvPresentprod pspId;
    @JoinColumn(name = "mar_id", referencedColumnName = "mar_id")
    @ManyToOne
    private InvMarca marId;
    @JoinColumn(name = "pxr_id", referencedColumnName = "pxr_id")
    @ManyToOne
    private CmpProdxreq pxrId;

    public CmpPxraprob() {
    }

    public CmpPxraprob(Long praId) {
        this.praId = praId;
    }

    public CmpPxraprob(Long praId, boolean praEst, int indversion) {
        this.praId = praId;
        this.praEst = praEst;
        this.indversion = indversion;
    }

    public Long getPraId() {
        return praId;
    }

    public void setPraId(Long praId) {
        this.praId = praId;
    }

    public Integer getCantAprob() {
        return cantAprob;
    }

    public void setCantAprob(Integer cantAprob) {
        this.cantAprob = cantAprob;
    }

    public boolean getPraEst() {
        return praEst;
    }

    public void setPraEst(boolean praEst) {
        this.praEst = praEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
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

    public CmpProdxreq getPxrId() {
        return pxrId;
    }

    public void setPxrId(CmpProdxreq pxrId) {
        this.pxrId = pxrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (praId != null ? praId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmpPxraprob)) {
            return false;
        }
        CmpPxraprob other = (CmpPxraprob) object;
        if ((this.praId == null && other.praId != null) || (this.praId != null && !this.praId.equals(other.praId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.CmpPxraprob[ praId=" + praId + " ]";
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
     * @return the cmpDetremisionList
     */
    public List<CmpDetremision> getCmpDetremisionList() {
        return cmpDetremisionList;
    }

    /**
     * @param cmpDetremisionList the cmpDetremisionList to set
     */
    public void setCmpDetremisionList(List<CmpDetremision> cmpDetremisionList) {
        this.cmpDetremisionList = cmpDetremisionList;
    }
}
