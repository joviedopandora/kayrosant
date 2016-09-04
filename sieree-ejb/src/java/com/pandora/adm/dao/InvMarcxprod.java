/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "inv_marcxprod")
@XmlRootElement
@NamedQueries({
   
    @NamedQuery(name = "InvMarcxprod.findByProducto", query = "SELECT i FROM InvMarcxprod i  WHERE i.prdId.prdId = :prdId ORDER BY i.marId.marNombre "),
    @NamedQuery(name = "InvMarcxprod.findAll", query = "SELECT i FROM InvMarcxprod i"),
    @NamedQuery(name = "InvMarcxprod.findByMxpId", query = "SELECT i FROM InvMarcxprod i WHERE i.mxpId = :mxpId"),
    @NamedQuery(name = "InvMarcxprod.findByMxpEst", query = "SELECT i FROM InvMarcxprod i WHERE i.mxpEst = :mxpEst"),
    @NamedQuery(name = "InvMarcxprod.findByIndversion", query = "SELECT i FROM InvMarcxprod i WHERE i.indversion = :indversion"),
    @NamedQuery(name = "InvMarcxprod.marcXProd", query = "SELECT i FROM InvMarcxprod i JOIN i.prdId p WHERE p.prdId = :prdId "
            + "AND i.mxpEst = :mxpEst ORDER BY i.marId.marNombre ")})
public class InvMarcxprod implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "inv_marcxprod_mxp_id_seq", name = "inv_marcxprod_mxp_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "inv_marcxprod_mxp_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mxp_id")
    private Long mxpId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mxp_est")
    private boolean mxpEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @JoinColumn(name = "prd_id", referencedColumnName = "prd_id")
    @ManyToOne
    private InvProducto prdId;
    @JoinColumn(name = "mar_id", referencedColumnName = "mar_id")
    @ManyToOne
    private InvMarca marId;
   /* @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "mxp_codigo")
    private String mxpCodigo;*/

    public InvMarcxprod() {
    }

    public InvMarcxprod(Long mxpId) {
        this.mxpId = mxpId;
    }

    public InvMarcxprod(Long mxpId, boolean mppEst, int indversion) {
        this.mxpId = mxpId;
        this.mxpEst = mppEst;
        this.indversion = indversion;
    }

    public Long getMxpId() {
        return mxpId;
    }

    public void setMxpId(Long mxpId) {
        this.mxpId = mxpId;
    }

    public boolean getMxpEst() {
        return mxpEst;
    }

    public void setMxpEst(boolean mxpEst) {
        this.mxpEst = mxpEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    public InvProducto getPrdId() {
        return prdId;
    }

    public void setPrdId(InvProducto prdId) {
        this.prdId = prdId;
    }

    public InvMarca getMarId() {
        return marId;
    }

    public void setMarId(InvMarca marId) {
        this.marId = marId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mxpId != null ? mxpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvMarcxprod)) {
            return false;
        }
        InvMarcxprod other = (InvMarcxprod) object;
        if ((this.mxpId == null && other.mxpId != null) || (this.mxpId != null && !this.mxpId.equals(other.mxpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.InvMarcxprod[ mxpId=" + mxpId + " ]";
    }

   
}
