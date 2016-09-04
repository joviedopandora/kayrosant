/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "inv_invent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvInvent.findByProducto", query = "SELECT i FROM InvInvent i WHERE i.prdId.prdId = :prdId  ORDER BY i.invCant DESC "),
    @NamedQuery(name = "InvInvent.findByProductoYMarcaYPresentacion", query = "SELECT i FROM InvInvent i WHERE i.marId.marId = :marId AND i.prdId.prdId = :prdId AND i.pspId.pspId =:pspId ORDER BY i.prdId.prdId "),
    @NamedQuery(name = "InvInvent.findAll", query = "SELECT i FROM InvInvent i"),
    @NamedQuery(name = "InvInvent.findByInvId", query = "SELECT i FROM InvInvent i WHERE i.invId = :invId"),
    @NamedQuery(name = "InvInvent.findByInvPrecio", query = "SELECT i FROM InvInvent i WHERE i.invPrecio = :invPrecio"),
    @NamedQuery(name = "InvInvent.findByInvCant", query = "SELECT i FROM InvInvent i WHERE i.invCant = :invCant"),
    @NamedQuery(name = "InvInvent.invXProd", query = "SELECT i FROM InvInvent i JOIN i.prdId p WHERE p.prdId = :prdId "
            + "ORDER BY  i.marId.marNombre, i.pspId.pspNombre"),
    @NamedQuery(name = "InvInvent.cantProdXprest", query = "SELECT sum(i.invCant) FROM InvInvent i JOIN i.prdId prd JOIN i.pspId psp "
            + "WHERE prd.prdId = :prdId AND psp.pspId = :pspId "),
    @NamedQuery(name = "InvInvent.cantProd", query = "SELECT sum(i.invCant) FROM InvInvent i JOIN i.prdId prd JOIN i.pspId psp "
            + "WHERE prd.prdId = :prdId "),
    @NamedQuery(name = "InvInvent.InvXPrdXPspXMar", query = "SELECT i FROM InvInvent i WHERE i.prdId.prdId = :prdId AND i.pspId.pspId = :pspId AND i.marId.marId = :marId "
            + " ORDER BY i.prdId.prdId, i.pspId.pspId, i.marId.marId, i.invFechaingreso"),
    @NamedQuery(name = "InvInvent.cantidadInvXPrdXPspXMar", query = "SELECT sum(i.invCant) FROM InvInvent i WHERE i.prdId.prdId = :prdId AND i.pspId.pspId = :pspId AND i.marId.marId = :marId "),
    //Cargar inventario de productos por presentación
    @NamedQuery(name = "InvInvent.consolidadoInvXPsp", query = "SELECT i.prdId.prdNombre, i.pspId.pspNombre, sum(i.invCant) FROM InvInvent i GROUP BY i.prdId.prdNombre, i.pspId.pspNombre ORDER BY i.prdId.prdNombre")
})
public class InvInvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "inv_invent_inv_id_seq", name = "inv_invent_inv_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "inv_invent_inv_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "inv_id")
    private Long invId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "inv_precio")
    private BigDecimal invPrecio;
    @Column(name = "inv_cant")
    private Integer invCant;
    @Column(name = "inv_fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date invFechaingreso;
    @Column(name = "inv_fechaegreso")
    @Temporal(TemporalType.DATE)
    private Date invFechaegreso;
    @Column(name = "indversion")
    @Version
    private Integer indversion;
    @JoinColumn(name = "prd_id", referencedColumnName = "prd_id")
    @ManyToOne
    private InvProducto prdId;
    @JoinColumn(name = "psp_id", referencedColumnName = "psp_id")
    @ManyToOne
    private InvPresentprod pspId;
    @JoinColumn(name = "mar_id", referencedColumnName = "mar_id")
    @ManyToOne
    private InvMarca marId;
    @OneToMany(mappedBy = "invId")
    private List<InvTransac> invTransacList;
    @JoinColumn(name = "div_id", referencedColumnName = "div_id")
    @OneToOne
    private InvDetinv divId;
    @Column(name = "inv_codigobarras")
    private String invCodigobarras;

    public InvInvent() {
        invCant = 0;
    }

    public InvInvent(Long invId) {
        this.invId = invId;
    }

    public Long getInvId() {
        return invId;
    }

    public void setInvId(Long invId) {
        this.invId = invId;
    }

    public BigDecimal getInvPrecio() {
        return invPrecio;
    }

    public void setInvPrecio(BigDecimal invPrecio) {
        this.invPrecio = invPrecio;
    }

    public Integer getInvCant() {
        return invCant;
    }

    public void setInvCant(Integer invCant) {
        this.invCant = invCant;
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

    @XmlTransient
    public List<InvTransac> getInvTransacList() {
        return invTransacList;
    }

    public void setInvTransacList(List<InvTransac> invTransacList) {
        this.invTransacList = invTransacList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invId != null ? invId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvInvent)) {
            return false;
        }
        InvInvent other = (InvInvent) object;
        if ((this.invId == null && other.invId != null) || (this.invId != null && !this.invId.equals(other.invId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.InvInvent[ invId=" + invId + " ]";
    }

    /**
     * @return the divId
     */
    public InvDetinv getDivId() {
        return divId;
    }

    /**
     * @param divId the divId to set
     */
    public void setDivId(InvDetinv divId) {
        this.divId = divId;
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
     * @return the invFechaingreso
     */
    public Date getInvFechaingreso() {
        return invFechaingreso;
    }

    /**
     * @param invFechaingreso the invFechaingreso to set
     */
    public void setInvFechaingreso(Date invFechaingreso) {
        this.invFechaingreso = invFechaingreso;
    }

    /**
     * @return the invFechaegreso
     */
    public Date getInvFechaegreso() {
        return invFechaegreso;
    }

    /**
     * @param invFechaegreso the invFechaegreso to set
     */
    public void setInvFechaegreso(Date invFechaegreso) {
        this.invFechaegreso = invFechaegreso;
    }

    /**
     * @return the invCodigobarras
     */
    public String getInvCodigobarras() {
        return invCodigobarras;
    }

    /**
     * @param invCodigobarras the invCodigobarras to set
     */
    public void setInvCodigobarras(String invCodigobarras) {
        this.invCodigobarras = invCodigobarras;
    }
}
