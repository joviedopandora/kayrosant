/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.RfSexo;
import com.pandora.adm.rf.dao.RfMotivoevento;
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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Breyner Robles
 */
@Entity
@Table(name = "vnt_registro_llamada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntRegistroLlamada.findByCliente", query = "SELECT v FROM VntRegistroLlamada v WHERE v.vntCliente.clnId = :clnId ORDER BY v.regllamFechaproceso DESC "),
    @NamedQuery(name = "VntRegistroLlamada.findAll", query = "SELECT v FROM VntRegistroLlamada v"),
    @NamedQuery(name = "VntRegistroLlamada.findByRegllamId", query = "SELECT v FROM VntRegistroLlamada v WHERE v.regllamId = :regllamId"),
    @NamedQuery(name = "VntRegistroLlamada.findByRegllamFechaproceso", query = "SELECT v FROM VntRegistroLlamada v WHERE v.regllamFechaproceso = :regllamFechaproceso"),
    @NamedQuery(name = "VntRegistroLlamada.findByRegllamEdad", query = "SELECT v FROM VntRegistroLlamada v WHERE v.regllamEdad = :regllamEdad"),
    @NamedQuery(name = "VntRegistroLlamada.findByRegllamObservacion", query = "SELECT v FROM VntRegistroLlamada v WHERE v.regllamObservacion = :regllamObservacion")})
@NamedNativeQueries({
@NamedNativeQuery(
name = "VntRegistroLlamada.findCountLlamadaXPeriodo", query ="SELECT to_char(regllam_fechaproceso,'YYYYMMDD') periodo, count(regllam_id) cantllamada FROM vnt_registro_llamada,vnt_cliente "
        + " WHERE vnt_cliente.tcl_id= ? AND vnt_cliente.cln_id = vnt_registro_llamada.cln_id AND to_date(to_char(regllam_fechaproceso,'YYYY-MM-DD'),'YYYY-MM-DD')>=? AND to_date(to_char(regllam_fechaproceso,'YYYY-MM-DD'),'YYYY-MM-DD')<=?  GROUP BY to_char(regllam_fechaproceso,'YYYYMMDD') order by 1"),

@NamedNativeQuery(
name = "VntRegistroLlamada.findCountLlamadaXPromedio", query ="SELECT COALESCE(ROUND(AVG(cantidad)),0.0) FROM (SELECT to_char(regllam_fechaproceso,'YYYYMMDD') periodo, count(regllam_id) cantidad FROM vnt_registro_llamada,vnt_cliente "
        + " WHERE vnt_cliente.tcl_id= ? AND vnt_cliente.cln_id = vnt_registro_llamada.cln_id AND to_date(to_char(regllam_fechaproceso,'YYYY-MM-DD'),'YYYY-MM-DD')>=? AND to_date(to_char(regllam_fechaproceso,'YYYY-MM-DD'),'YYYY-MM-DD')<=?  GROUP BY to_char(regllam_fechaproceso,'YYYYMMDD')) aa")

})
public class VntRegistroLlamada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "regllam_id")
    private Long regllamId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "regllam_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regllamFechaproceso;
    @Column(name = "regllam_edad")
    private Integer regllamEdad;
    @Size(max = 2147483647)
    @Column(name = "regllam_observacion")
    private String regllamObservacion;
    @JoinColumn(name = "cln_id", referencedColumnName = "cln_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntCliente vntCliente;
    @JoinColumn(name = "sex_id", referencedColumnName = "sex_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfSexo rfSexo;
    @JoinColumn(name = "cmc_id", referencedColumnName = "cmc_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfComocontacto rfComocontacto;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AdmCrgxcol admCrgxcol;
    @JoinColumn(name = "tipocierre_id", referencedColumnName = "tipocierre_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RfTipocierrellamada rfTipocierrellamada;
    @JoinColumn(name = "mte_id", referencedColumnName = "mte_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfMotivoevento rfMotivoevento;
    @Column(name = "regllam_fechaevento")
    @Temporal(TemporalType.DATE)
    private Date regllamFechaevento;
    @Size(max = 2147483647)
    @Column(name = "regllam_nombrehomenajeado")
    private String regllamNombrehomenajeado;

    public VntRegistroLlamada() {
    }

    public VntRegistroLlamada(Long regllamId) {
        this.regllamId = regllamId;
    }

    public VntRegistroLlamada(Long regllamId, Date regllamFechaproceso) {
        this.regllamId = regllamId;
        this.regllamFechaproceso = regllamFechaproceso;
    }

    public Long getRegllamId() {
        return regllamId;
    }

    public void setRegllamId(Long regllamId) {
        this.regllamId = regllamId;
    }

    public Date getRegllamFechaproceso() {
        return regllamFechaproceso;
    }

    public void setRegllamFechaproceso(Date regllamFechaproceso) {
        this.regllamFechaproceso = regllamFechaproceso;
    }

    public Integer getRegllamEdad() {
        return regllamEdad;
    }

    public void setRegllamEdad(Integer regllamEdad) {
        this.regllamEdad = regllamEdad;
    }

    public String getRegllamObservacion() {
        return regllamObservacion;
    }

    public void setRegllamObservacion(String regllamObservacion) {
        this.regllamObservacion = regllamObservacion;
    }

    public VntCliente getVntCliente() {
        return vntCliente;
    }

    public void setVntCliente(VntCliente vntCliente) {
        this.vntCliente = vntCliente;
    }

    public RfSexo getRfSexo() {
        return rfSexo;
    }

    public void setRfSexo(RfSexo rfSexo) {
        this.rfSexo = rfSexo;
    }

    public RfComocontacto getRfComocontacto() {
        return rfComocontacto;
    }

    public void setRfComocontacto(RfComocontacto rfComocontacto) {
        this.rfComocontacto = rfComocontacto;
    }

    public AdmCrgxcol getAdmCrgxcol() {
        return admCrgxcol;
    }

    public void setAdmCrgxcol(AdmCrgxcol admCrgxcol) {
        this.admCrgxcol = admCrgxcol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regllamId != null ? regllamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntRegistroLlamada)) {
            return false;
        }
        VntRegistroLlamada other = (VntRegistroLlamada) object;
        if ((this.regllamId == null && other.regllamId != null) || (this.regllamId != null && !this.regllamId.equals(other.regllamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.VntRegistroLlamada[ regllamId=" + regllamId + " ]";
    }

    /**
     * @return the rfTipocierrellamada
     */
    public RfTipocierrellamada getRfTipocierrellamada() {
        return rfTipocierrellamada;
    }

    /**
     * @param rfTipocierrellamada the rfTipocierrellamada to set
     */
    public void setRfTipocierrellamada(RfTipocierrellamada rfTipocierrellamada) {
        this.rfTipocierrellamada = rfTipocierrellamada;
    }

    /**
     * @return the rfMotivoevento
     */
    public RfMotivoevento getRfMotivoevento() {
        return rfMotivoevento;
    }

    /**
     * @param rfMotivoevento the rfMotivoevento to set
     */
    public void setRfMotivoevento(RfMotivoevento rfMotivoevento) {
        this.rfMotivoevento = rfMotivoevento;
    }

    /**
     * @return the regllamFechaevento
     */
    public Date getRegllamFechaevento() {
        return regllamFechaevento;
    }

    /**
     * @param regllamFechaevento the regllamFechaevento to set
     */
    public void setRegllamFechaevento(Date regllamFechaevento) {
        this.regllamFechaevento = regllamFechaevento;
    }

    /**
     * @return the regllamNombrehomenajeado
     */
    public String getRegllamNombrehomenajeado() {
        return regllamNombrehomenajeado;
    }

    /**
     * @param regllamNombrehomenajeado the regllamNombrehomenajeado to set
     */
    public void setRegllamNombrehomenajeado(String regllamNombrehomenajeado) {
        this.regllamNombrehomenajeado = regllamNombrehomenajeado;
    }
}
