/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import adm.sys.dao.RfSexo;
import adm.sys.dao.RfTipodoc;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GloriaCristina
 */
@Entity
@Table(name = "vnt_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntCliente.findByTipoCliente", query = "SELECT v FROM VntCliente v WHERE v.tclId.tclId = :tclId"),
    @NamedQuery(name = "VntCliente.findByNumIdentXDiferente", query = "SELECT v FROM VntCliente v WHERE v.clnIdentificacion = :clnIdentificacion AND v.clnId <> :clnId"),
    @NamedQuery(name = "VntCliente.findAll", query = "SELECT v FROM VntCliente v "),
    @NamedQuery(name = "VntCliente.findByClnId", query = "SELECT v FROM VntCliente v WHERE v.clnId = :clnId"),
    @NamedQuery(name = "VntCliente.findByClnIdentificacion", query = "SELECT v FROM VntCliente v WHERE v.clnIdentificacion = :clnIdentificacion"),
    @NamedQuery(name = "VntCliente.findByClnNombres", query = "SELECT v FROM VntCliente v WHERE v.clnNombres = :clnNombres"),
    @NamedQuery(name = "VntCliente.findByClnContacto", query = "SELECT v FROM VntCliente v WHERE v.clnContacto = :clnContacto"),
    @NamedQuery(name = "VntCliente.findByClnCorreoe", query = "SELECT v FROM VntCliente v WHERE v.clnCorreoe = :clnCorreoe"),
    @NamedQuery(name = "VntCliente.findByClnNuevo", query = "SELECT v FROM VntCliente v WHERE v.clnNuevo = :clnNuevo"),
    @NamedQuery(name = "VntCliente.findByClnDiereccion", query = "SELECT v FROM VntCliente v WHERE v.clnDiereccion = :clnDiereccion"),
    @NamedQuery(name = "VntCliente.findByClnCelular", query = "SELECT v FROM VntCliente v WHERE v.clnCelular = :clnCelular"),
    @NamedQuery(name = "VntCliente.findByClnFijo", query = "SELECT v FROM VntCliente v WHERE v.clnFijo = :clnFijo"),
    @NamedQuery(name = "VntCliente.findByClnEstado", query = "SELECT v FROM VntCliente v WHERE v.clnEstado = :clnEstado"),
    @NamedQuery(name = "VntCliente.findByClnFechaproceso", query = "SELECT v FROM VntCliente v WHERE v.clnFechaproceso = :clnFechaproceso"),
    @NamedQuery(name = "VntCliente.findByClnFechanace", query = "SELECT v FROM VntCliente v WHERE v.clnFechanace = :clnFechanace"),
    @NamedQuery(name = "VntCliente.findByClnNumhijos", query = "SELECT v FROM VntCliente v WHERE v.clnNumhijos = :clnNumhijos"),
    @NamedQuery(name = "VntCliente.clnXTextoKids", query = "SELECT v FROM VntCliente v WHERE  (v.clnNombres LIKE :txtBuscar OR v.clnIdentificacion LIKE :txtBuscar OR v.clnAlias LIKE :txtBuscar) AND v.tclId.tclId = :tclId"),
    @NamedQuery(name = "VntCliente.clnXTexto", query = "SELECT v FROM VntCliente v WHERE  (v.clnNombres LIKE :txtBuscar OR v.clnIdentificacion LIKE :txtBuscar OR v.clnAlias LIKE :txtBuscar) AND v.tclId.tclId <> :tclId")
})
public class VntCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cln_id")
    private Long clnId;
    @Size(max = 25)
    @Column(name = "cln_identificacion")
    private String clnIdentificacion;
    @Size(max = 300)
    @Column(name = "cln_nombres")
    private String clnNombres;
    @Size(max = 300)
    @Column(name = "cln_contacto")
    private String clnContacto;
    @Size(max = 200)
    @Column(name = "cln_correoe")
    private String clnCorreoe;
    @Column(name = "cln_nuevo")
    private Boolean clnNuevo;
    @Size(max = 500)
    @Column(name = "cln_diereccion")
    private String clnDiereccion;
    @Size(max = 30)
    @Column(name = "cln_celular")
    private String clnCelular;
    @Size(max = 30)
    @Column(name = "cln_fijo")
    private String clnFijo;
    @Column(name = "cln_estado")
    private Boolean clnEstado;
    @Column(name = "cln_fechaproceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date clnFechaproceso;
    @Column(name = "cln_fechanace")
    @Temporal(TemporalType.DATE)
    private Date clnFechanace;
    @Column(name = "cln_numhijos")
    private Integer clnNumhijos;
    @OneToMany(mappedBy = "clnId")
    private List<VntRegistroventa> vntRegistroventaList;
    @JoinColumn(name = "tcl_id", referencedColumnName = "tcl_id")
    @ManyToOne
    private VntRfTipocliente tclId;
    @JoinColumn(name = "ate_id", referencedColumnName = "ate_id")
    @ManyToOne
    private VntActeconomica ateId;
    @JoinColumn(name = "tdc_id", referencedColumnName = "tdc_id")
    @ManyToOne
    private RfTipodoc tdcId;
    @JoinColumn(name = "sex_id", referencedColumnName = "sex_id")
    @ManyToOne
    private RfSexo sexId;
    @OneToMany(mappedBy = "clnId")
    private List<VntDetallecliente> vntDetalleclienteList;
    @OneToMany(mappedBy = "vntCliente", fetch = FetchType.LAZY)
    private List<VntRegistroLlamada> vntRegistroLlamadaList;
    @Column(name = "cln_alias")
    private String clnAlias;

    public VntCliente() {
    }

    public VntCliente(Long clnId) {
        this.clnId = clnId;
    }

    public Long getClnId() {
        return clnId;
    }

    public void setClnId(Long clnId) {
        this.clnId = clnId;
    }

    public String getClnIdentificacion() {
        return clnIdentificacion;
    }

    public void setClnIdentificacion(String clnIdentificacion) {
        this.clnIdentificacion = clnIdentificacion;
    }

    public String getClnNombres() {
        return clnNombres;
    }

    public void setClnNombres(String clnNombres) {
        this.clnNombres = clnNombres;
    }

    public String getClnContacto() {
        return clnContacto;
    }

    public void setClnContacto(String clnContacto) {
        this.clnContacto = clnContacto;
    }

    public String getClnCorreoe() {
        return clnCorreoe;
    }

    public void setClnCorreoe(String clnCorreoe) {
        this.clnCorreoe = clnCorreoe;
    }

    public Boolean getClnNuevo() {
        return clnNuevo;
    }

    public void setClnNuevo(Boolean clnNuevo) {
        this.clnNuevo = clnNuevo;
    }

    public String getClnDiereccion() {
        return clnDiereccion;
    }

    public void setClnDiereccion(String clnDiereccion) {
        this.clnDiereccion = clnDiereccion;
    }

    public String getClnCelular() {
        return clnCelular;
    }

    public void setClnCelular(String clnCelular) {
        this.clnCelular = clnCelular;
    }

    public String getClnFijo() {
        return clnFijo;
    }

    public void setClnFijo(String clnFijo) {
        this.clnFijo = clnFijo;
    }

    public Boolean getClnEstado() {
        return clnEstado;
    }

    public void setClnEstado(Boolean clnEstado) {
        this.clnEstado = clnEstado;
    }

    public Date getClnFechaproceso() {
        return clnFechaproceso;
    }

    public void setClnFechaproceso(Date clnFechaproceso) {
        this.clnFechaproceso = clnFechaproceso;
    }

    public Date getClnFechanace() {
        return clnFechanace;
    }

    public void setClnFechanace(Date clnFechanace) {
        this.clnFechanace = clnFechanace;
    }

    public Integer getClnNumhijos() {
        return clnNumhijos;
    }

    public void setClnNumhijos(Integer clnNumhijos) {
        this.clnNumhijos = clnNumhijos;
    }

    @XmlTransient
    public List<VntRegistroventa> getVntRegistroventaList() {
        return vntRegistroventaList;
    }

    public void setVntRegistroventaList(List<VntRegistroventa> vntRegistroventaList) {
        this.vntRegistroventaList = vntRegistroventaList;
    }

    public VntRfTipocliente getTclId() {
        return tclId;
    }

    public void setTclId(VntRfTipocliente tclId) {
        this.tclId = tclId;
    }

    public VntActeconomica getAteId() {
        return ateId;
    }

    public void setAteId(VntActeconomica ateId) {
        this.ateId = ateId;
    }

    public RfTipodoc getTdcId() {
        return tdcId;
    }

    public void setTdcId(RfTipodoc tdcId) {
        this.tdcId = tdcId;
    }

    public RfSexo getSexId() {
        return sexId;
    }

    public void setSexId(RfSexo sexId) {
        this.sexId = sexId;
    }

    @XmlTransient
    public List<VntDetallecliente> getVntDetalleclienteList() {
        return vntDetalleclienteList;
    }

    public void setVntDetalleclienteList(List<VntDetallecliente> vntDetalleclienteList) {
        this.vntDetalleclienteList = vntDetalleclienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clnId != null ? clnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntCliente)) {
            return false;
        }
        VntCliente other = (VntCliente) object;
        if ((this.clnId == null && other.clnId != null) || (this.clnId != null && !this.clnId.equals(other.clnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.siere.venta.dao.VntCliente[ clnId=" + clnId + " ]";
    }

    /**
     * @return the vntRegistroLlamadaList
     */
    public List<VntRegistroLlamada> getVntRegistroLlamadaList() {
        return vntRegistroLlamadaList;
    }

    /**
     * @param vntRegistroLlamadaList the vntRegistroLlamadaList to set
     */
    public void setVntRegistroLlamadaList(List<VntRegistroLlamada> vntRegistroLlamadaList) {
        this.vntRegistroLlamadaList = vntRegistroLlamadaList;
    }

    public String getNombres() {
        if (clnNombres != null) {
            return clnNombres;
        }
        return null;

    }

    public String getIdentificacion() {
        if (tdcId != null) {
            return tdcId.getTdcId() + "-" + clnIdentificacion;
        }
        return null;

    }

    /**
     * @return the clnAlias
     */
    public String getClnAlias() {
        return clnAlias;
    }

    /**
     * @param clnAlias the clnAlias to set
     */
    public void setClnAlias(String clnAlias) {
        this.clnAlias = clnAlias;
    }
}
