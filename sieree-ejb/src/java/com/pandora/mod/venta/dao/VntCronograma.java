/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "vnt_cronograma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntCronograma.listaCronogramaPendienteFactura", query = "SELECT cr FROM VntCronograma cr JOIN  cr.vntServxventa.rgvtId  v  WHERE v.rgvtId=:rgvtId  AND cr.cronogramaEstado =:cronogramaEstado  ORDER BY cr.cronogramaFecha ASC"),
    @NamedQuery(name = "VntCronograma.listaCronogramaPendienteOP", query = "SELECT cr FROM VntCronograma cr JOIN  cr.vntServxventa.rgvtId  v JOIN v.clnId c JOIN c.tclId t  WHERE t.tclId = :tclId AND v.rgvtActivarOp =:rgvtActivarOp   AND v.rgvtCantidadservasociadosOp > v.rgvtCantidadservprocesadosOp ORDER BY cr.cronogramaFecha ASC"),
    @NamedQuery(name = "VntCronograma.findAll", query = "SELECT v FROM VntCronograma v"),
    @NamedQuery(name = "VntCronograma.findByCronogramaId", query = "SELECT v FROM VntCronograma v WHERE v.cronogramaId = :cronogramaId"),
    @NamedQuery(name = "VntCronograma.findByCronogramaFecha", query = "SELECT v FROM VntCronograma v WHERE v.cronogramaFecha = :cronogramaFecha"),
    @NamedQuery(name = "VntCronograma.findByCronogramaSemana", query = "SELECT v FROM VntCronograma v WHERE v.cronogramaSemana = :cronogramaSemana"),
    @NamedQuery(name = "VntCronograma.findByCronogramaDistribuidor", query = "SELECT v FROM VntCronograma v WHERE v.cronogramaDistribuidor = :cronogramaDistribuidor")})
public class VntCronograma implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(sequenceName = "vnt_cronograma_cronograma_id_seq", name = "vnt_cronograma_cronograma_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "vnt_cronograma_cronograma_id_seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "cronograma_id")
    private Long cronogramaId;
    @Column(name = "cronograma_fecha")
    @Temporal(TemporalType.DATE)
    private Date cronogramaFecha;
    @Size(max = 20)
    @Column(name = "cronograma_semana")
    private String cronogramaSemana;
    @Size(max = 2147483647)
    @Column(name = "cronograma_distribuidor")
    private String cronogramaDistribuidor;
    @Size(max = 2147483647)
    @Column(name = "cronograma_cop")
    private String cronogramacop;
   
    
    @JoinColumn(name = "srvxvent_id", referencedColumnName = "srvxvent_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntServxventa vntServxventa;
    @JoinColumn(name = "vfct_id", referencedColumnName = "vfct_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VntFactura vntFactura;
    @JoinColumn(name = "opr_id", referencedColumnName = "opr_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PopOrdenprod popOrdenprod;
    
    @NotNull
    @Column(name = "cronograma_estado_factura")
    private boolean cronogramaEstadoFactura;
    
    
    @NotNull
    @Column(name = "cronograma_estado")
    private boolean cronogramaEstado; 

    public VntCronograma() {
        this.cronogramaEstado = true;
        this.cronogramaEstadoFactura = true;
    }

    public VntCronograma(Long cronogramaId) {
        this();
        this.cronogramaId = cronogramaId;
        
    }

    public Long getCronogramaId() {
        return cronogramaId;
    }

    public void setCronogramaId(Long cronogramaId) {
        this.cronogramaId = cronogramaId;
    }

    public Date getCronogramaFecha() {
        return cronogramaFecha;
    }

    public void setCronogramaFecha(Date cronogramaFecha) {
        this.cronogramaFecha = cronogramaFecha;
    }

    public String getCronogramaSemana() {
        return cronogramaSemana;
    }

    public void setCronogramaSemana(String cronogramaSemana) {
        this.cronogramaSemana = cronogramaSemana;
    }

    public String getCronogramaDistribuidor() {
        return cronogramaDistribuidor;
    }

    public void setCronogramaDistribuidor(String cronogramaDistribuidor) {
        this.cronogramaDistribuidor = cronogramaDistribuidor;
    }

    public VntServxventa getVntServxventa() {
        return vntServxventa;
    }

    public void setVntServxventa(VntServxventa vntServxventa) {
        this.vntServxventa = vntServxventa;
    }

    public VntFactura getVntFactura() {
        return vntFactura;
    }

    public void setVntFactura(VntFactura vntFactura) {
        this.vntFactura = vntFactura;
    }

    public PopOrdenprod getPopOrdenprod() {
        return popOrdenprod;
    }

    public void setPopOrdenprod(PopOrdenprod popOrdenprod) {
        this.popOrdenprod = popOrdenprod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cronogramaId != null ? cronogramaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntCronograma)) {
            return false;
        }
        VntCronograma other = (VntCronograma) object;
        if ((this.cronogramaId == null && other.cronogramaId != null) || (this.cronogramaId != null && !this.cronogramaId.equals(other.cronogramaId))) {
            return false;
        }
        return true;
    }

    public String getCronogramacop() {
        return cronogramacop;
    }

    public void setCronogramacop(String cronogramacop) {
        this.cronogramacop = cronogramacop;
    }

    @Override
    public String toString() {
        return "com.VntCronograma[ cronogramaId=" + cronogramaId + " ]";
    }

    public boolean isCronogramaEstado() {
        return cronogramaEstado;
    }

    public void setCronogramaEstado(boolean cronogramaEstado) {
        this.cronogramaEstado = cronogramaEstado;
    }

    public String getNumeroOP() {
        String numOp = null;
        if(this.cronogramaEstado){
        if (this.popOrdenprod != null && this.popOrdenprod.getOprId() != null) {
            numOp = "" + this.popOrdenprod.getOprId();
        }
        }else{
            numOp="Anulado";
        }
        return numOp;
    }

    public boolean isCronogramaEstadoFactura() {
        return cronogramaEstadoFactura;
    }

    public void setCronogramaEstadoFactura(boolean cronogramaEstadoFactura) {
        this.cronogramaEstadoFactura = cronogramaEstadoFactura;
    }
    
    public String getNumeroFcatura() {
        String numOp = null;
        if(this.cronogramaEstadoFactura){
        if (this.vntFactura != null && this.vntFactura.getVfctNumfactura() != null) {
            numOp = "" + this.vntFactura.getVfctNumfactura();
        }
        }
        return numOp;
    }    

}
