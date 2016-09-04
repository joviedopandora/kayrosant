/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.liquidacion.dao;

import com.pandora.mod.ordenprod.dao.PopCxcevento;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sistemasmaximus
 */
@Entity
@Table(name = "pg_liquidacionxcolaborador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PgLiquidacionxcolaborador.findAll", query = "SELECT p FROM PgLiquidacionxcolaborador p"),
    @NamedQuery(name = "PgLiquidacionxcolaborador.findByPgLiquidacionxcolId", query = "SELECT p FROM PgLiquidacionxcolaborador p WHERE p.pgLiquidacionxcolId = :pgLiquidacionxcolId")})
public class PgLiquidacionxcolaborador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
     @SequenceGenerator(sequenceName = "pg_liquidacionxcolaborador_pg_liquidacionxcol_id_seq", name = "pg_liquidacionxcolaborador_pg_liquidacionxcol_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "pg_liquidacionxcolaborador_pg_liquidacionxcol_id_seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "pg_liquidacionxcol_id")
    private Long pgLiquidacionxcolId;
    @JoinColumn(name = "liquidacion_id", referencedColumnName = "liquidacion_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PgLiquidacion liquidacionId;
    @JoinColumn(name = "cxe_id", referencedColumnName = "cxe_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PopCxcevento cxeId;

    public PgLiquidacionxcolaborador() {
    }

    public PgLiquidacionxcolaborador(Long pgLiquidacionxcolId) {
        this.pgLiquidacionxcolId = pgLiquidacionxcolId;
    }

    public Long getPgLiquidacionxcolId() {
        return pgLiquidacionxcolId;
    }

    public void setPgLiquidacionxcolId(Long pgLiquidacionxcolId) {
        this.pgLiquidacionxcolId = pgLiquidacionxcolId;
    }

    public PgLiquidacion getLiquidacionId() {
        return liquidacionId;
    }

    public void setLiquidacionId(PgLiquidacion liquidacionId) {
        this.liquidacionId = liquidacionId;
    }

    public PopCxcevento getCxeId() {
        return cxeId;
    }

    public void setCxeId(PopCxcevento cxeId) {
        this.cxeId = cxeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pgLiquidacionxcolId != null ? pgLiquidacionxcolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PgLiquidacionxcolaborador)) {
            return false;
        }
        PgLiquidacionxcolaborador other = (PgLiquidacionxcolaborador) object;
        if ((this.pgLiquidacionxcolId == null && other.pgLiquidacionxcolId != null) || (this.pgLiquidacionxcolId != null && !this.pgLiquidacionxcolId.equals(other.pgLiquidacionxcolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wil.PgLiquidacionxcolaborador[ pgLiquidacionxcolId=" + pgLiquidacionxcolId + " ]";
    }
    
}
