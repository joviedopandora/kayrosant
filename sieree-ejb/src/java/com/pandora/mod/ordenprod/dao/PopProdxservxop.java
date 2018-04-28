/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.mod.ordenprod.dao;

import com.pandora.adm.dao.InvGrupotransac;
import com.pandora.adm.dao.InvProducto;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "pop_prodxservxop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PopProdxservxop.findAll", query = "SELECT p FROM PopProdxservxop p"),
    @NamedQuery(name = "PopProdxservxop.findByPxsoId", query = "SELECT p FROM PopProdxservxop p WHERE p.pxsoId = :pxsoId"),
    @NamedQuery(name = "PopProdxservxop.findByPxsoObservacion", query = "SELECT p FROM PopProdxservxop p WHERE p.pxsoObservacion = :pxsoObservacion"),
    @NamedQuery(name = "PopProdxservxop.findByPxsoFechaproceso", query = "SELECT p FROM PopProdxservxop p WHERE p.pxsoFechaproceso = :pxsoFechaproceso"),
    @NamedQuery(name = "PopProdxservxop.findByPxsoEstado", query = "SELECT p FROM PopProdxservxop p WHERE p.pxsoEstado = :pxsoEstado"),
    @NamedQuery(name = "PopProdxservxop.findByPxsoCantsalida", query = "SELECT p FROM PopProdxservxop p WHERE p.pxsoCantsalida = :pxsoCantsalida"),
    @NamedQuery(name = "PopProdxservxop.findByPxsoEstadosalida", query = "SELECT p FROM PopProdxservxop p WHERE p.pxsoEstadosalida = :pxsoEstadosalida"),
    @NamedQuery(name = "PopProdxservxop.findByPxsoObservsalida", query = "SELECT p FROM PopProdxservxop p WHERE p.pxsoObservsalida = :pxsoObservsalida"),
    @NamedQuery(name = "PopProdxservxop.findByPxsoCantentrada", query = "SELECT p FROM PopProdxservxop p WHERE p.pxsoCantentrada = :pxsoCantentrada"),
    @NamedQuery(name = "PopProdxservxop.findByPxsoEstadoentrada", query = "SELECT p FROM PopProdxservxop p WHERE p.pxsoEstadoentrada = :pxsoEstadoentrada"),
    @NamedQuery(name = "PopProdxservxop.findByPxsoObserventrada", query = "SELECT p FROM PopProdxservxop p WHERE p.pxsoObserventrada = :pxsoObserventrada"),
    @NamedQuery(name = "PopProdxservxop.findByIndversion", query = "SELECT p FROM PopProdxservxop p WHERE p.indversion = :indversion"),
    @NamedQuery(name = "PopProdxservxop.findByStrId", query = "SELECT p FROM PopProdxservxop p WHERE p.strId = :strId"),
    //Lista de productos por servicio
    @NamedQuery(name = "PopProdxservxop.servicio", query = "SELECT p FROM PopProdxservxop p JOIN p.sxoId s JOIN s.vsrvId v WHERE v.vsrvId = :vsrvId"),
    //Lista de productos por orden de producción
    @NamedQuery(name = "PopProdxservxop.ordenProd", query = "SELECT p FROM PopProdxservxop p JOIN p.sxoId sxo JOIN sxo.oprId op WHERE op.oprId = :oprId  and p.pxsoEstado=true ORDER BY p.prdId.prdNombre"),
    //Lista de productos por orden de producción por tarea
    @NamedQuery(name = "PopProdxservxop.ordenProdXTarea", query = "SELECT p FROM PopProdxservxop p JOIN p.sxoId sxo JOIN sxo.oprId op WHERE op.oprId = :oprId AND p.strId = :strId")
})
public class PopProdxservxop implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pxso_id")
    private Long pxsoId;
    @Size(max = 2147483647)
    @Column(name = "pxso_observacion")
    private String pxsoObservacion;
    @Column(name = "pxso_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pxsoFechaproceso;
    @Column(name = "pxso_estado")
    private Boolean pxsoEstado;
    @Column(name = "indversion")
    private Integer indversion;
    @Column(name = "str_id")
    private Long strId;
    @Column(name = "pxso_cantprod")
    private Integer pxsoCantprod;
    @Column(name = "pxso_cantsalida")
    private Integer pxsoCantsalida ;
    @Column(name = "pxso_estadosalida")
    private Boolean pxsoEstadosalida ;
    @Column(name = "pxso_observsalida")
    private String pxsoObservsalida ;
    @Column(name = "pxso_cantentrada")
    private Integer pxsoCantentrada;
    @Column(name = "pxso_estadoentrada")
    private Boolean pxsoEstadoentrada;
    @Column(name = "pxso_observentrada")
    private String pxsoObserventrada;
    @Column(name = "pxso_cantprodfija")
    private Integer pxsoCantprodfija;
    @JoinColumn(name = "sxo_id", referencedColumnName = "sxo_id")
    @ManyToOne
    private PopServxop sxoId;
    @JoinColumn(name = "prd_id", referencedColumnName = "prd_id")
    @ManyToOne
    private InvProducto prdId;
    @JoinColumn(name = "gtr_id", referencedColumnName = "gtr_id")
    @ManyToOne
    private InvGrupotransac gtrId;
    //Servicio al q pertenece el producto si es combo
     @Column(name = "vsrv_id")
    private Long servicioAsociadoId;
    @Column(name = "vsrv_desc")
    private String servicioAsociadoDesc;
    
    
      
    public PopProdxservxop() {
    }

    public PopProdxservxop(Long pxsoId) {
        this.pxsoId = pxsoId;
    }

    public Long getPxsoId() {
        return pxsoId;
    }

    public void setPxsoId(Long pxsoId) {
        this.pxsoId = pxsoId;
    }

    public String getPxsoObservacion() {
        return pxsoObservacion;
    }

    public void setPxsoObservacion(String pxsoObservacion) {
        this.pxsoObservacion = pxsoObservacion;
    }

    public Date getPxsoFechaproceso() {
        return pxsoFechaproceso;
    }

    public void setPxsoFechaproceso(Date pxsoFechaproceso) {
        this.pxsoFechaproceso = pxsoFechaproceso;
    }

    public Boolean getPxsoEstado() {
        return pxsoEstado;
    }

    public void setPxsoEstado(Boolean pxsoEstado) {
        this.pxsoEstado = pxsoEstado;
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

    public PopServxop getSxoId() {
        return sxoId;
    }

    public void setSxoId(PopServxop sxoId) {
        this.sxoId = sxoId;
    }

    public InvGrupotransac getGtrId() {
        return gtrId;
    }

    public void setGtrId(InvGrupotransac gtrId) {
        this.gtrId = gtrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pxsoId != null ? pxsoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PopProdxservxop)) {
            return false;
        }
        PopProdxservxop other = (PopProdxservxop) object;
        if ((this.pxsoId == null && other.pxsoId != null) || (this.pxsoId != null && !this.pxsoId.equals(other.pxsoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.PopProdxservxop[ pxsoId=" + pxsoId + " ]";
    }

    /**
     * @return the pxsoCantprod
     */
    public Integer getPxsoCantprod() {
        return pxsoCantprod;
    }

    /**
     * @param pxsoCantprod the pxsoCantprod to set
     */
    public void setPxsoCantprod(Integer pxsoCantprod) {
        this.pxsoCantprod = pxsoCantprod;
    }

    /**
     * @return the prdId
     */
    public InvProducto getPrdId() {
        return prdId;
    }

    /**
     * @param prdId the prdId to set
     */
    public void setPrdId(InvProducto prdId) {
        this.prdId = prdId;
    }

    /**
     * @return the pxsoCantsalida
     */
    public Integer getPxsoCantsalida() {
        return pxsoCantsalida;
    }

    /**
     * @param pxsoCantsalida the pxsoCantsalida to set
     */
    public void setPxsoCantsalida(Integer pxsoCantsalida) {
        this.pxsoCantsalida = pxsoCantsalida;
    }

    /**
     * @return the pxsoEstadosalida
     */
    public Boolean getPxsoEstadosalida() {
        return pxsoEstadosalida;
    }

    /**
     * @param pxsoEstadosalida the pxsoEstadosalida to set
     */
    public void setPxsoEstadosalida(Boolean pxsoEstadosalida) {
        this.pxsoEstadosalida = pxsoEstadosalida;
    }

    /**
     * @return the pxsoObservsalida
     */
    public String getPxsoObservsalida() {
        return pxsoObservsalida;
    }

    /**
     * @param pxsoObservsalida the pxsoObservsalida to set
     */
    public void setPxsoObservsalida(String pxsoObservsalida) {
        this.pxsoObservsalida = pxsoObservsalida;
    }

    /**
     * @return the pxsoCantentrada
     */
    public Integer getPxsoCantentrada() {
        return pxsoCantentrada;
    }

    /**
     * @param pxsoCantentrada the pxsoCantentrada to set
     */
    public void setPxsoCantentrada(Integer pxsoCantentrada) {
        this.pxsoCantentrada = pxsoCantentrada;
    }

    /**
     * @return the pxsoEstadoentrada
     */
    public Boolean getPxsoEstadoentrada() {
        return pxsoEstadoentrada;
    }

    /**
     * @param pxsoEstadoentrada the pxsoEstadoentrada to set
     */
    public void setPxsoEstadoentrada(Boolean pxsoEstadoentrada) {
        this.pxsoEstadoentrada = pxsoEstadoentrada;
    }

    /**
     * @return the pxsoObserventrada
     */
    public String getPxsoObserventrada() {
        return pxsoObserventrada;
    }

    /**
     * @param pxsoObserventrada the pxsoObserventrada to set
     */
    public void setPxsoObserventrada(String pxsoObserventrada) {
        this.pxsoObserventrada = pxsoObserventrada;
    }

    public Long getServicioAsociadoId() {
        return servicioAsociadoId;
    }

    public void setServicioAsociadoId(Long servicioAsociadoId) {
        this.servicioAsociadoId = servicioAsociadoId;
    }

    public String getServicioAsociadoDesc() {
        return servicioAsociadoDesc;
    }

    public void setServicioAsociadoDesc(String servicioAsociadoDesc) {
        this.servicioAsociadoDesc = servicioAsociadoDesc;
    }

    /**
     * @return the pxsoCantprodfija
     */
    public Integer getPxsoCantprodfija() {
        return pxsoCantprodfija;
    }

    /**
     * @param pxsoCantprodfija the pxsoCantprodfija to set
     */
    public void setPxsoCantprodfija(Integer pxsoCantprodfija) {
        this.pxsoCantprodfija = pxsoCantprodfija;
    }
  
    
}
