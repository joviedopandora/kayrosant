/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "inv_presntxprod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvPresntxprod.findByProducto", query = "SELECT i FROM InvPresntxprod i WHERE i.prdId.prdId = :prdId ORDER BY i.pspId.pspNombre"),
    @NamedQuery(name = "InvPresntxprod.findAll", query = "SELECT i FROM InvPresntxprod i"),
    @NamedQuery(name = "InvPresntxprod.findByPxpId", query = "SELECT i FROM InvPresntxprod i WHERE i.pxpId = :pxpId"),
    @NamedQuery(name = "InvPresntxprod.findByPxpEst", query = "SELECT i FROM InvPresntxprod i WHERE i.pxpEst = :pxpEst"),
    @NamedQuery(name = "InvPresntxprod.prestXProd", query = "SELECT i FROM InvPresntxprod i JOIN i.prdId p WHERE "
            + "p.prdId = :prdId AND i.pxpEst = :pxpEst ORDER BY i.pspId.pspNombre ")})
public class InvPresntxprod implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "inv_presntxprod_pxp_id_seq", name = "inv_presntxprod_pxp_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "inv_presntxprod_pxp_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pxp_id")
    private Long pxpId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pxp_est")
    private boolean pxpEst;
    @JoinColumn(name = "prd_id", referencedColumnName = "prd_id")
    @ManyToOne
    private InvProducto prdId;
    @JoinColumn(name = "psp_id", referencedColumnName = "psp_id")
    @ManyToOne
    private InvPresentprod pspId;

    public InvPresntxprod() {
        this.pxpEst = true;
    }

    public InvPresntxprod(Long pxpId) {
        this.pxpId = pxpId;
        this.pxpEst = true;
    }

    public InvPresntxprod(Long pxpId, boolean pxpEst) {
        this.pxpId = pxpId;
        this.pxpEst = pxpEst;
    }

    public Long getPxpId() {
        return pxpId;
    }

    public void setPxpId(Long pxpId) {
        this.pxpId = pxpId;
    }

    public boolean getPxpEst() {
        return pxpEst;
    }

    public void setPxpEst(boolean pxpEst) {
        this.pxpEst = pxpEst;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pxpId != null ? pxpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvPresntxprod)) {
            return false;
        }
        InvPresntxprod other = (InvPresntxprod) object;
        if ((this.pxpId == null && other.pxpId != null) || (this.pxpId != null && !this.pxpId.equals(other.pxpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.InvPresntxprod[ pxpId=" + pxpId + " ]";
    }
}
