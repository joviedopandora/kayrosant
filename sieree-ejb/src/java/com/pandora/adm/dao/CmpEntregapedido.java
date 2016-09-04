/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "cmp_entregapedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmpEntregapedido.findAll", query = "SELECT c FROM CmpEntregapedido c"),
    @NamedQuery(name = "CmpEntregapedido.findByEtpId", query = "SELECT c FROM CmpEntregapedido c WHERE c.etpId = :etpId"),
    @NamedQuery(name = "CmpEntregapedido.findByEtpFechaentrega", query = "SELECT c FROM CmpEntregapedido c WHERE c.etpFechaentrega = :etpFechaentrega"),
    @NamedQuery(name = "CmpEntregapedido.findByEtpEstado", query = "SELECT c FROM CmpEntregapedido c WHERE c.etpEstado = :etpEstado"),
    @NamedQuery(name = "CmpEntregapedido.findByEtpFechaproceso", query = "SELECT c FROM CmpEntregapedido c WHERE c.etpFechaproceso = :etpFechaproceso"),
    @NamedQuery(name = "CmpEntregapedido.findByIndversion", query = "SELECT c FROM CmpEntregapedido c WHERE c.indversion = :indversion"),
    @NamedQuery(name = "CmpEntregapedido.findByEtpEntregado", query = "SELECT c FROM CmpEntregapedido c WHERE c.etpEntregado = :etpEntregado"),
    @NamedQuery(name = "CmpEntregapedido.findByEtpObsr", query = "SELECT c FROM CmpEntregapedido c WHERE c.etpObsr = :etpObsr"),
    @NamedQuery(name = "CmpEntregapedido.findByColaboradorXTarea", query = "SELECT c FROM CmpEntregapedido c JOIN c.drmId drm JOIN drm.praId pra JOIN pra.pxrId pxr "
        + " JOIN pxr.crqId crq JOIN crq.cxcId cxc WHERE cxc.cxcId = :cxcId AND c.strId = :strId "),
//    @NamedQuery(name = "CmpEntregapedido.findByColaborador", query = "SELECT c FROM CmpEntregapedido c JOIN c.praId pra JOIN pra.pxrId pxr JOIN pxr.crqId crq JOIN crq.cxcId cxc WHERE cxc.cxcId = :cxcId AND pra.strId = :strId ")
})
public class CmpEntregapedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "cmp_entregapedido_etp_id_seq", allocationSize = 1, name = "cmp_entregapedido_etp_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cmp_entregapedido_etp_id_seq")
    @Id
    @Basic(optional = false)
    @Column(name = "etp_id")
    private Long etpId;
    @Column(name = "etp_fechaentrega")
    @Temporal(TemporalType.DATE)
    private Date etpFechaentrega;
    @Column(name = "etp_estado")
    private Boolean etpEstado;
    @Column(name = "etp_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date etpFechaproceso;
    @Column(name = "indversion")
    @Version
    private Integer indversion;
    @Column(name = "etp_entregado")
    private Boolean etpEntregado;
    @Size(max = 2147483647)
    @Column(name = "etp_obsr")
    private String etpObsr;
    @Column(name = "etp_recibido")
    private Boolean etpRecibido;
    @Column(name = "etp_cantrecibida")
    private Integer etpCantrecibida;
    @Column(name = "etp_fecprocrecepcion")            
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date etpFecprocrecepcion;
    @Column(name = "str_id")
    private Long strId;
    @JoinColumn(name = "drm_id", referencedColumnName = "drm_id")
    @ManyToOne
    private CmpDetremision drmId;

    public CmpEntregapedido() {
    }

    public CmpEntregapedido(Long etpId) {
        this.etpId = etpId;
    }

    public Long getEtpId() {
        return etpId;
    }

    public void setEtpId(Long etpId) {
        this.etpId = etpId;
    }

    public Date getEtpFechaentrega() {
        return etpFechaentrega;
    }

    public void setEtpFechaentrega(Date etpFechaentrega) {
        this.etpFechaentrega = etpFechaentrega;
    }

    public Boolean getEtpEstado() {
        return etpEstado;
    }

    public void setEtpEstado(Boolean etpEstado) {
        this.etpEstado = etpEstado;
    }

    public Date getEtpFechaproceso() {
        return etpFechaproceso;
    }

    public void setEtpFechaproceso(Date etpFechaproceso) {
        this.etpFechaproceso = etpFechaproceso;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public Boolean getEtpEntregado() {
        return etpEntregado;
    }

    public void setEtpEntregado(Boolean etpEntregado) {
        this.etpEntregado = etpEntregado;
    }

    public String getEtpObsr() {
        return etpObsr;
    }

    public void setEtpObsr(String etpObsr) {
        this.etpObsr = etpObsr;
    }

    public CmpDetremision getDrmId() {
        return drmId;
    }

    public void setDrmId(CmpDetremision drmId) {
        this.drmId = drmId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (etpId != null ? etpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmpEntregapedido)) {
            return false;
        }
        CmpEntregapedido other = (CmpEntregapedido) object;
        if ((this.etpId == null && other.etpId != null) || (this.etpId != null && !this.etpId.equals(other.etpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.CmpEntregapedido[ etpId=" + etpId + " ]";
    }

    /**
     * @return the etpRecibido
     */
    public Boolean getEtpRecibido() {
        return etpRecibido;
    }

    /**
     * @param etpRecibido the etpRecibido to set
     */
    public void setEtpRecibido(Boolean etpRecibido) {
        this.etpRecibido = etpRecibido;
    }

    /**
     * @return the etpFecprocrecepcion
     */
    public Date getEtpFecprocrecepcion() {
        return etpFecprocrecepcion;
    }

    /**
     * @param etpFecprocrecepcion the etpFecprocrecepcion to set
     */
    public void setEtpFecprocrecepcion(Date etpFecprocrecepcion) {
        this.etpFecprocrecepcion = etpFecprocrecepcion;
    }

    /**
     * @return the etpCantrecibida
     */
    public Integer getEtpCantrecibida() {
        return etpCantrecibida;
    }

    /**
     * @param etpCantrecibida the etpCantrecibida to set
     */
    public void setEtpCantrecibida(Integer etpCantrecibida) {
        this.etpCantrecibida = etpCantrecibida;
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
}
