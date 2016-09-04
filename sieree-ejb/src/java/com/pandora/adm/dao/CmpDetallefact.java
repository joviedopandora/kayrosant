/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "cmp_detallefact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmpDetallefact.findAll", query = "SELECT c FROM CmpDetallefact c"),
    @NamedQuery(name = "CmpDetallefact.findByDetfId", query = "SELECT c FROM CmpDetallefact c WHERE c.detfId = :detfId"),
    @NamedQuery(name = "CmpDetallefact.findByDetfCantrecibida", query = "SELECT c FROM CmpDetallefact c WHERE c.detfCantrecibida = :detfCantrecibida"),
    @NamedQuery(name = "CmpDetallefact.findByDetfRecibido", query = "SELECT c FROM CmpDetallefact c WHERE c.detfRecibido = :detfRecibido"),
    @NamedQuery(name = "CmpDetallefact.findByDetfEst", query = "SELECT c FROM CmpDetallefact c WHERE c.detfEst = :detfEst"),
    @NamedQuery(name = "CmpDetallefact.findByIndversion", query = "SELECT c FROM CmpDetallefact c WHERE c.indversion = :indversion"),
    @NamedQuery(name = "CmpDetallefact.findByDetfValorunitario", query = "SELECT c FROM CmpDetallefact c WHERE c.detfValorunitario = :detfValorunitario"),
    @NamedQuery(name = "CmpDetallefact.detalleXFactura", query = "SELECT c FROM CmpDetallefact c JOIN c.factId fact WHERE fact.factId = :factId"),
    @NamedQuery(name = "CmpDetallefact.detalleXTarea", query = "SELECT c FROM CmpDetallefact c JOIN c.factId fact WHERE fact.strId = :strId ORDER BY fact.factId ASC"),
    @NamedQuery(name = "CmpDetallefact.detalleXTareaFactProcesada", query = "SELECT c FROM CmpDetallefact c JOIN c.factId fact WHERE fact.strId = :strId AND fact.factPrefact = :factPrefact ORDER BY fact.factId ASC"),
    @NamedQuery(name = "CmpDetallefact.cantDetXFactXProcesado", query = "SELECT count(c.detfId) FROM CmpDetallefact c JOIN c.factId fact WHERE fact.factId = :factId AND c.detfProcesadorecibido = :detfProcesadorecibido")
})
public class CmpDetallefact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "detf_id")
    private Long detfId;
    @Column(name = "detf_cantrecibida")
    private Integer detfCantrecibida;
    @Column(name = "detf_recibido")
    private Boolean detfRecibido;
    @Column(name = "detf_est")
    private Boolean detfEst;
    @Column(name = "indversion")
    @Version
    private Integer indversion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "detf_valorunitario")
    private BigDecimal detfValorunitario;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "detf_fecharecibido")
    private Date detfFecharecibido;
    @Column(name = "detf_procesadorecibido")
    private Boolean detfProcesadorecibido;
    @Column(name = "detf_procesadocargainv")
    private Boolean detfProcesadocargainv;
    @Column(name = "detf_devolucion")
    private Boolean detfDevolucion;
    @Size(max = 2147483647)
    @Column(name = "detf_observacion")
    private String detfObservacion;
    @JoinColumn(name = "fact_id", referencedColumnName = "fact_id")
    @ManyToOne
    private CmpFactura factId;
    @JoinColumn(name = "ccm_id", referencedColumnName = "ccm_id")
    @ManyToOne
    private CmpConsolcompra ccmId;

    public CmpDetallefact() {
    }

    public CmpDetallefact(Long detfId) {
        this.detfId = detfId;
    }

    public Long getDetfId() {
        return detfId;
    }

    public void setDetfId(Long detfId) {
        this.detfId = detfId;
    }

    public Integer getDetfCantrecibida() {
        return detfCantrecibida;
    }

    public void setDetfCantrecibida(Integer detfCantrecibida) {
        this.detfCantrecibida = detfCantrecibida;
    }

    public Boolean getDetfRecibido() {
        return detfRecibido;
    }

    public void setDetfRecibido(Boolean detfRecibido) {
        this.detfRecibido = detfRecibido;
    }

    public Boolean getDetfEst() {
        return detfEst;
    }

    public void setDetfEst(Boolean detfEst) {
        this.detfEst = detfEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public BigDecimal getDetfValorunitario() {
        return detfValorunitario;
    }

    public void setDetfValorunitario(BigDecimal detfValorunitario) {
        this.detfValorunitario = detfValorunitario;
    }

    public CmpFactura getFactId() {
        return factId;
    }

    public void setFactId(CmpFactura factId) {
        this.factId = factId;
    }

    public CmpConsolcompra getCcmId() {
        return ccmId;
    }

    public void setCcmId(CmpConsolcompra ccmId) {
        this.ccmId = ccmId;
    }

    
    public Date getDetfFecharecibido() {
        return detfFecharecibido;
    }

    
    public void setDetfFecharecibido(Date detfFecharecibido) {
        this.detfFecharecibido = detfFecharecibido;
    }

    
    public Boolean getDetfProcesadorecibido() {
        return detfProcesadorecibido;
    }

    
    public void setDetfProcesadorecibido(Boolean detfProcesadorecibido) {
        this.detfProcesadorecibido = detfProcesadorecibido;
    }
    
    public Boolean getDetfProcesadocargainv() {
        return detfProcesadocargainv;
    }

    public void setDetfProcesadocargainv(Boolean detfProcesadocargainv) {
        this.detfProcesadocargainv = detfProcesadocargainv;
    }
    
    public Boolean getDetfDevolucion() {
        return detfDevolucion;
    }

    public void setDetfDevolucion(Boolean detfDevolucion) {
        this.detfDevolucion = detfDevolucion;
    }

    public String getDetfObservacion() {
        return detfObservacion;
    }

    public void setDetfObservacion(String detfObservacion) {
        this.detfObservacion = detfObservacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detfId != null ? detfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmpDetallefact)) {
            return false;
        }
        CmpDetallefact other = (CmpDetallefact) object;
        if ((this.detfId == null && other.detfId != null) || (this.detfId != null && !this.detfId.equals(other.detfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.CmpDetallefact[ detfId=" + detfId + " ]";
    }
}
