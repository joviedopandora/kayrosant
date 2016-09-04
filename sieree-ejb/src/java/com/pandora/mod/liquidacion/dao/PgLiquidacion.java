/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.liquidacion.dao;

import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.AdmTipopagoxcol;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sistemasmaximus
 */
@Entity
@Table(name = "pg_liquidacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PgLiquidacion.findAll", query = "SELECT p FROM PgLiquidacion p"),
    @NamedQuery(name = "PgLiquidacion.findByLiquidacionId", query = "SELECT p FROM PgLiquidacion p WHERE p.liquidacionId = :liquidacionId"),
    @NamedQuery(name = "PgLiquidacion.findByLiquidacionValorPagar", query = "SELECT p FROM PgLiquidacion p WHERE p.liquidacionValorPagar = :liquidacionValorPagar"),
    @NamedQuery(name = "PgLiquidacion.findByLiquidacionNumeroAprobacion", query = "SELECT p FROM PgLiquidacion p WHERE p.liquidacionNumeroAprobacion = :liquidacionNumeroAprobacion"),
    @NamedQuery(name = "PgLiquidacion.findByLiquidacionNumeroPin", query = "SELECT p FROM PgLiquidacion p WHERE p.liquidacionNumeroPin = :liquidacionNumeroPin")})
public class PgLiquidacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(sequenceName = "pg_liquidacion_liquidacion_id_seq", name = "pg_liquidacion_liquidacion_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "pg_liquidacion_liquidacion_id_seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "liquidacion_id")
    private Long liquidacionId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "liquidacion_valor_pagar")
    private BigDecimal liquidacionValorPagar;
    @Size(max = 50)
    @Column(name = "liquidacion_numero_aprobacion")
    private String liquidacionNumeroAprobacion;
    @Size(max = 2147483647)
    @Column(name = "liquidacion_numero_pin")
    private String liquidacionNumeroPin;
    @OneToMany(mappedBy = "liquidacionId", fetch = FetchType.LAZY)
    private List<PgLiquidacionxcolaborador> pgLiquidacionxcolaboradorList= new ArrayList<>();
    @JoinColumn(name = "id_tipopago", referencedColumnName = "id_tipopago")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdmTipopagoxcol idTipopago;
    
     @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdmCrgxcol cxcId;

      @JoinColumn(name = "id_liquidaconso", referencedColumnName = "id_liquidaconso")
    @ManyToOne(fetch = FetchType.LAZY)
    private PgLiquidacionconsolidado idLiquidaconso;
      
    public PgLiquidacion() {
    }

    public PgLiquidacion(Long liquidacionId) {
        this.liquidacionId = liquidacionId;
    }

    public PgLiquidacion(Long liquidacionId, BigDecimal liquidacionValorPagar) {
        this.liquidacionId = liquidacionId;
        this.liquidacionValorPagar = liquidacionValorPagar;
    }

    public Long getLiquidacionId() {
        return liquidacionId;
    }

    public void setLiquidacionId(Long liquidacionId) {
        this.liquidacionId = liquidacionId;
    }

    public BigDecimal getLiquidacionValorPagar() {
        return liquidacionValorPagar;
    }

    public void setLiquidacionValorPagar(BigDecimal liquidacionValorPagar) {
        this.liquidacionValorPagar = liquidacionValorPagar;
    }

    public String getLiquidacionNumeroAprobacion() {
        return liquidacionNumeroAprobacion;
    }

    public void setLiquidacionNumeroAprobacion(String liquidacionNumeroAprobacion) {
        this.liquidacionNumeroAprobacion = liquidacionNumeroAprobacion;
    }

    public String getLiquidacionNumeroPin() {
        return liquidacionNumeroPin;
    }

    public void setLiquidacionNumeroPin(String liquidacionNumeroPin) {
        this.liquidacionNumeroPin = liquidacionNumeroPin;
    }

    @XmlTransient
    public List<PgLiquidacionxcolaborador> getPgLiquidacionxcolaboradorList() {
        return pgLiquidacionxcolaboradorList;
    }

    public void setPgLiquidacionxcolaboradorList(List<PgLiquidacionxcolaborador> pgLiquidacionxcolaboradorList) {
        this.pgLiquidacionxcolaboradorList = pgLiquidacionxcolaboradorList;
    }

    public AdmTipopagoxcol getIdTipopago() {
        return idTipopago;
    }

    public void setIdTipopago(AdmTipopagoxcol idTipopago) {
        this.idTipopago = idTipopago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (liquidacionId != null ? liquidacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PgLiquidacion)) {
            return false;
        }
        PgLiquidacion other = (PgLiquidacion) object;
        if ((this.liquidacionId == null && other.liquidacionId != null) || (this.liquidacionId != null && !this.liquidacionId.equals(other.liquidacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wil.PgLiquidacion[ liquidacionId=" + liquidacionId + " ]";
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    public PgLiquidacionconsolidado getIdLiquidaconso() {
        return idLiquidaconso;
    }

    public void setIdLiquidaconso(PgLiquidacionconsolidado idLiquidaconso) {
        this.idLiquidaconso = idLiquidaconso;
    }
    
    
    
    
}
