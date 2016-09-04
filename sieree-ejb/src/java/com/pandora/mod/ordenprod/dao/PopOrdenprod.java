/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.ordenprod.dao;

import adm.sys.dao.AdmCrgxcol;
import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.adm.rf.dao.RfDep;
import com.pandora.bussiness.util.EnPasoCalificacion;
import com.pandora.mod.logistica.dao.LgtEstadoevento;
import com.pandora.mod.venta.dao.VntRegistroventa;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "pop_ordenprod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PopOrdenprod.findAll", query = "SELECT p FROM PopOrdenprod p "),
    @NamedQuery(name = "PopOrdenprod.findByOprId", query = "SELECT p FROM PopOrdenprod p WHERE p.oprId = :oprId"),
    @NamedQuery(name = "PopOrdenprod.findByOprDescripcion", query = "SELECT p FROM PopOrdenprod p WHERE p.oprDescripcion = :oprDescripcion"),
    @NamedQuery(name = "PopOrdenprod.findByOprFechaproceso", query = "SELECT p FROM PopOrdenprod p WHERE p.oprFechaproceso = :oprFechaproceso"),
    @NamedQuery(name = "PopOrdenprod.findByOprEstado", query = "SELECT p FROM PopOrdenprod p WHERE p.oprEstado = :oprEstado"),
    @NamedQuery(name = "PopOrdenprod.findByOprProcesado", query = "SELECT p FROM PopOrdenprod p WHERE p.oprProcesado = :oprProcesado AND p.oprEstadodespacho = :oprEstadodespacho ORDER BY p.rgvtId.vdeId.vdeFechaevt DESC"),
    @NamedQuery(name = "PopOrdenprod.findByIndversion", query = "SELECT p FROM PopOrdenprod p WHERE p.indversion = :indversion"),
    @NamedQuery(name = "PopOrdenprod.findByStrId", query = "SELECT p FROM PopOrdenprod p WHERE p.strId = :strId"),
    //Lista de órdenes de producción por procesado y tarea
    @NamedQuery(name = "PopOrdenprod.findByProcXStrId", query = "SELECT p FROM PopOrdenprod p WHERE p.oprProcesado = :oprProcesado AND p.strId = :strId ORDER BY p.rgvtId.vdeId.vdeFechaevt ASC"),
    //Lista de órdenes de producción por procesado y estado de despacho
    @NamedQuery(name = "PopOrdenprod.ordenXProcXEstDesp", query = "SELECT p FROM PopOrdenprod p JOIN p.eevId e WHERE p.oprProcesado = :oprProcesado AND e.eevId = :eevId ORDER BY p.rgvtId.vdeId.vdeFechaevt ASC"),
    //Lista de órdenes de producción procesadas
    @NamedQuery(name = "PopOrdenprod.ordenXProcesadas", query = "SELECT p FROM PopOrdenprod p WHERE p.oprProcesado = :oprProcesado ORDER BY p.rgvtId.vdeId.vdeFechaevt ASC"),
    //Lista de todas las órdenes de producción
    @NamedQuery(name = "PopOrdenprod.ordenesProd", query = "SELECT p FROM PopOrdenprod p ORDER BY p.rgvtId.vdeId.vdeFechaevt ASC"),
    @NamedQuery(name = "PopOrdenprod.findByOprProcesadoXTodas", query = "SELECT p FROM PopOrdenprod p WHERE p.oprProcesado = :oprProcesado ORDER BY p.rgvtId.vdeId.vdeFechaevt ASC"),
    @NamedQuery(name = "PopOrdenprod.findByOprActivas", query = "SELECT p FROM PopOrdenprod p WHERE p.rfEstadoOP.eopId <> :eopId ORDER BY p.oprFechaevtini ASC"),
    @NamedQuery(name = "PopOrdenprod.findByOprActivaEstadoLogistica", query = "SELECT p FROM PopOrdenprod p WHERE p.rfEstadoOP.eopId <> :eopId AND p.oprEstadodespacho = :oprEstadodespacho ORDER BY p.oprFechaevtini ASC")
})
public class PopOrdenprod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(sequenceName = "pop_ordenprod_opr_id_seq", name = "pop_ordenprod_opr_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "pop_ordenprod_opr_id_seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "opr_id")
    private Long oprId;
    @Size(max = 2147483647)
    @Column(name = "opr_descripcion")
    private String oprDescripcion;
    @Column(name = "opr_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oprFechaproceso;
    @Column(name = "opr_estado")
    private Boolean oprEstado;
    @Column(name = "opr_procesado")
    private Boolean oprProcesado;
    @Column(name = "indversion")
    private Integer indversion;
    @Column(name = "str_id")
    private Long strId;
    @OneToMany(mappedBy = "oprId")
    private List<PopServxop> popServxopList;
    @OneToMany(mappedBy = "oprId")
    private List<PopCxcevento> popCxceventoList;
    @JoinColumn(name = "rgvt_id", referencedColumnName = "rgvt_id")
    @ManyToOne
    private VntRegistroventa rgvtId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;
    @JoinColumn(name = "eev_id", referencedColumnName = "eev_id")
    @ManyToOne
    private LgtEstadoevento eevId;
    @Column(name = "opr_estadodespacho")
    private Integer oprEstadodespacho;

    @Size(max = 300)
    @Column(name = "opr_oc")
    private String oprOc;

    @Size(max = 500)
    @Column(name = "opr_direccionevento")
    private String oprDireccionevento;

    @Size(max = 700)
    @Column(name = "opr_indicacionllegada")
    private String oprIndicacionllegada;

    @Column(name = "opr_fechaevtini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oprFechaevtini;

    @Column(name = "opr_fechacreacionop")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacionop;

    @Column(name = "opr_fechaevtfin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oprFechaevtfin;

    @Size(max = 500)
    @Column(name = "opr_contactoevento")
    private String oprContactoevento;

    @Size(max = 25)
    @Column(name = "opr_celularcontacto")
    private String oprCelularcontacto;

    @Size(max = 200)
    @Column(name = "opr_cargocontacto")
    private String oprCargocontacto;

    @Size(max = 200)
    @Column(name = "opr_areacontacto")
    private String oprAreacontacto;

    @Column(name = "opr_fechacitaini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oprFechacitaini;

    @Column(name = "opr_fechacitafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oprFechacitafin;

    @Column(name = "opr_fechasal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oprFechasal;

    @Column(name = "opr_fechamonini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oprFechamonini;

    @Column(name = "opr_fechamonfin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oprFechamonfin;

    @Size(max = 200)
    @Column(name = "opr_transporte")
    private String oprTransporte;

    @Size(max = 2147483647)
    @Column(name = "opr_titulo")
    private String oprTitulo;

    @Size(max = 2147483647)
    @Column(name = "opr_descripc")
    private String oprDescripc;
    @NotNull
    @Column(name = "opr_cantidad_evaluacion")
    private Integer oprCantidadEvaluacion;

    @JoinColumn(name = "opr_ciudad", referencedColumnName = "ciu_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfCiudad oprCiudad;
    @JoinColumn(name = "opr_depart", referencedColumnName = "dep_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfDep oprDepart;
    @JoinColumn(name = "eop_id", referencedColumnName = "eop_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfEstadoOP rfEstadoOP;

    public PopOrdenprod() {
        if (oprCantidadEvaluacion == null) {
            oprCantidadEvaluacion = EnPasoCalificacion.INICIAL.getId();
        }
    }

    public PopOrdenprod(Long oprId) {
        this.oprId = oprId;
        if (oprCantidadEvaluacion == null) {
            oprCantidadEvaluacion = 0;
        }
    }

    public Long getOprId() {
        return oprId;
    }

    public void setOprId(Long oprId) {
        this.oprId = oprId;
    }

    public String getOprDescripcion() {
        return oprDescripcion;
    }

    public void setOprDescripcion(String oprDescripcion) {
        this.oprDescripcion = oprDescripcion;
    }

    public Date getOprFechaproceso() {
        return oprFechaproceso;
    }

    public void setOprFechaproceso(Date oprFechaproceso) {
        this.oprFechaproceso = oprFechaproceso;
    }

    public Boolean getOprEstado() {
        return oprEstado;
    }

    public void setOprEstado(Boolean oprEstado) {
        this.oprEstado = oprEstado;
    }

    public Date getFechacreacionop() {
        return fechacreacionop;
    }

    public void setFechacreacionop(Date fechacreacionop) {
        this.fechacreacionop = fechacreacionop;
    }

    public Boolean getOprProcesado() {
        return oprProcesado;
    }

    public void setOprProcesado(Boolean oprProcesado) {
        this.oprProcesado = oprProcesado;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public Long getStrId() {
        return strId;
    }

    public void setStrId(Long strId) {
        this.strId = strId;
    }

    @XmlTransient
    public List<PopServxop> getPopServxopList() {
        return popServxopList;
    }

    public void setPopServxopList(List<PopServxop> popServxopList) {
        this.popServxopList = popServxopList;
    }

    public VntRegistroventa getRgvtId() {
        return rgvtId;
    }

    public void setRgvtId(VntRegistroventa rgvtId) {
        this.rgvtId = rgvtId;
    }

    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    public LgtEstadoevento getEevId() {
        return eevId;
    }

    public void setEevId(LgtEstadoevento eevId) {
        this.eevId = eevId;
    }

    @XmlTransient
    public List<PopCxcevento> getPopCxceventoList() {
        return popCxceventoList;
    }

    public void setPopCxceventoList(List<PopCxcevento> popCxceventoList) {
        this.popCxceventoList = popCxceventoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oprId != null ? oprId.hashCode() : 0);
        return hash;
    }

    public String getOprOc() {
        return oprOc;
    }

    public void setOprOc(String oprOc) {
        this.oprOc = oprOc;
    }

    public String getOprDireccionevento() {
        return oprDireccionevento;
    }

    public void setOprDireccionevento(String oprDireccionevento) {
        this.oprDireccionevento = oprDireccionevento;
    }

    public String getOprIndicacionllegada() {
        return oprIndicacionllegada;
    }

    public void setOprIndicacionllegada(String oprIndicacionllegada) {
        this.oprIndicacionllegada = oprIndicacionllegada;
    }

    public Date getOprFechaevtini() {
        return oprFechaevtini;
    }

    public void setOprFechaevtini(Date oprFechaevtini) {
        this.oprFechaevtini = oprFechaevtini;
    }

    public Date getOprFechaevtfin() {
        return oprFechaevtfin;
    }

    public void setOprFechaevtfin(Date oprFechaevtfin) {
        this.oprFechaevtfin = oprFechaevtfin;
    }

    public String getOprContactoevento() {
        return oprContactoevento;
    }

    public void setOprContactoevento(String oprContactoevento) {
        this.oprContactoevento = oprContactoevento;
    }

    public String getOprCelularcontacto() {
        return oprCelularcontacto;
    }

    public void setOprCelularcontacto(String oprCelularcontacto) {
        this.oprCelularcontacto = oprCelularcontacto;
    }

    public String getOprCargocontacto() {
        return oprCargocontacto;
    }

    public void setOprCargocontacto(String oprCargocontacto) {
        this.oprCargocontacto = oprCargocontacto;
    }

    public String getOprAreacontacto() {
        return oprAreacontacto;
    }

    public void setOprAreacontacto(String oprAreacontacto) {
        this.oprAreacontacto = oprAreacontacto;
    }

    public Date getOprFechacitaini() {
        return oprFechacitaini;
    }

    public void setOprFechacitaini(Date oprFechacitaini) {
        this.oprFechacitaini = oprFechacitaini;
    }

    public Date getOprFechacitafin() {
        return oprFechacitafin;
    }

    public void setOprFechacitafin(Date oprFechacitafin) {
        this.oprFechacitafin = oprFechacitafin;
    }

    public Date getOprFechamonini() {
        return oprFechamonini;
    }

    public void setOprFechamonini(Date oprFechamonini) {
        this.oprFechamonini = oprFechamonini;
    }

    public Date getOprFechamonfin() {
        return oprFechamonfin;
    }

    public void setOprFechamonfin(Date oprFechamonfin) {
        this.oprFechamonfin = oprFechamonfin;
    }

    public String getOprTransporte() {
        return oprTransporte;
    }

    public void setOprTransporte(String oprTransporte) {
        this.oprTransporte = oprTransporte;
    }

    public String getOprTitulo() {
        return oprTitulo;
    }

    public void setOprTitulo(String oprTitulo) {
        this.oprTitulo = oprTitulo;
    }

    public String getOprDescripc() {
        return oprDescripc;
    }

    public void setOprDescripc(String oprDescripc) {
        this.oprDescripc = oprDescripc;
    }

    public Date getOprFechasal() {
        return oprFechasal;
    }

    public void setOprFechasal(Date oprFechasal) {
        this.oprFechasal = oprFechasal;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PopOrdenprod)) {
            return false;
        }
        PopOrdenprod other = (PopOrdenprod) object;
        if ((this.oprId == null && other.oprId != null) || (this.oprId != null && !this.oprId.equals(other.oprId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.PopOrdenprod[ oprId=" + oprId + " ]";
    }

    /**
     * @return the oprEstadodespacho
     */
    public Integer getOprEstadodespacho() {
        return oprEstadodespacho;
    }

    /**
     * @param oprEstadodespacho the oprEstadodespacho to set
     */
    public void setOprEstadodespacho(Integer oprEstadodespacho) {
        this.oprEstadodespacho = oprEstadodespacho;
    }

    public Integer getOprCantidadEvaluacion() {
        return oprCantidadEvaluacion;
    }

    public void setOprCantidadEvaluacion(Integer oprCantidadEvaluacion) {
        this.oprCantidadEvaluacion = oprCantidadEvaluacion;
    }

    /**
     * @return the oprCiudad
     */
    public RfCiudad getOprCiudad() {
        return oprCiudad;
    }

    /**
     * @param oprCiudad the oprCiudad to set
     */
    public void setOprCiudad(RfCiudad oprCiudad) {
        this.oprCiudad = oprCiudad;
    }

    /**
     * @return the oprDepart
     */
    public RfDep getOprDepart() {
        return oprDepart;
    }

    /**
     * @param oprDepart the oprDepart to set
     */
    public void setOprDepart(RfDep oprDepart) {
        this.oprDepart = oprDepart;
    }

    public RfEstadoOP getRfEstadoOP() {
        return rfEstadoOP;
    }

    public void setRfEstadoOP(RfEstadoOP rfEstadoOP) {
        this.rfEstadoOP = rfEstadoOP;
    }
    
    

}
