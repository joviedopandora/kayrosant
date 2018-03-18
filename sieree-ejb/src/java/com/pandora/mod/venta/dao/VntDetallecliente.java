/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import adm.sys.dao.RfParentezco;
import adm.sys.dao.RfSexo;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GloriaCristina
 */
@Entity
@Table(name = "vnt_detallecliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntDetallecliente.findAll", query = "SELECT v FROM VntDetallecliente v"),
    @NamedQuery(name = "VntDetallecliente.findByDclnId", query = "SELECT v FROM VntDetallecliente v WHERE v.dclnId = :dclnId"),
    @NamedQuery(name = "VntDetallecliente.findByDclnFechanace", query = "SELECT v FROM VntDetallecliente v WHERE v.dclnFechanace = :dclnFechanace"),
    @NamedQuery(name = "VntDetallecliente.detalleXCliente", query = "SELECT v FROM VntDetallecliente v WHERE v.clnId.clnId = :clnId AND v.dclnEstado = :dclnEstado"),
@NamedQuery(name = "VntDetallecliente.detalleXClienteGen", query = "SELECT v FROM VntDetallecliente v WHERE v.clnId.clnId = :clnId ORDER BY v.dclnNombres")})
public class VntDetallecliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dcln_id")
    private Long dclnId;
    @Size(max = 500)
    @Column(name = "dcln_nombres")
    private String dclnNombres;
    @Column(name = "dcln_fechanace")
    @Temporal(TemporalType.DATE)
    private Date dclnFechanace;
    @JoinColumn(name = "cln_id", referencedColumnName = "cln_id")
    @ManyToOne
    private VntCliente clnId;
    @JoinColumn(name = "sex_id", referencedColumnName = "sex_id")
    @ManyToOne
    private RfSexo sexId;
    @JoinColumn(name = "prt_id", referencedColumnName = "prt_id")
    @ManyToOne
    private RfParentezco prtId;
    @OneToOne(mappedBy = "dclnId")
    private VntDetevento vdeId;
    @Size(max = 2147483647)
    @Column(name = "dcln_direccion")
    private String dclnDireccion;
    @Size(max = 1500)
    @Column(name = "dcln_email")
    private String dclnEmail;
    @Column(name = "dcln_estado")
    private Boolean dclnEstado;

    public Boolean getDclnEstado() {
        return dclnEstado;
    }

    public void setDclnEstado(Boolean dclnEstado) {
        this.dclnEstado = dclnEstado;
    }
    ///
    @JoinColumn(name = "cargo_id", referencedColumnName = "cargo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfCargocontacto rfCargocontacto;
    @Size(max = 20)
    @Column(name = "dcln_celular")
    private String dclnCelular;
    @Size(max = 10)
    @Column(name = "dcln_extension")
    private String dclnExtension;

    public VntDetallecliente() {
    }

    public VntDetallecliente(Long dclnId) {
        this.dclnId = dclnId;
    }

    public Long getDclnId() {
        return dclnId;
    }

    public void setDclnId(Long dclnId) {
        this.dclnId = dclnId;
    }

    public Date getDclnFechanace() {
        return dclnFechanace;
    }

    public void setDclnFechanace(Date dclnFechanace) {
        this.dclnFechanace = dclnFechanace;
    }

    public VntCliente getClnId() {
        return clnId;
    }

    public void setClnId(VntCliente clnId) {
        this.clnId = clnId;
    }

    public RfSexo getSexId() {
        return sexId;
    }

    public void setSexId(RfSexo sexId) {
        this.sexId = sexId;
    }

    public RfParentezco getPrtId() {
        return prtId;
    }

    public void setPrtId(RfParentezco prtId) {
        this.prtId = prtId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dclnId != null ? dclnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntDetallecliente)) {
            return false;
        }
        VntDetallecliente other = (VntDetallecliente) object;
        if ((this.dclnId == null && other.dclnId != null) || (this.dclnId != null && !this.dclnId.equals(other.dclnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.siere.venta.dao.VntDetallecliente[ dclnId=" + dclnId + " ]";
    }

    /**
     * @return the vdeId
     */
    public VntDetevento getVdeId() {
        return vdeId;
    }

    /**
     * @param vdeId the vdeId to set
     */
    public void setVdeId(VntDetevento vdeId) {
        this.vdeId = vdeId;
    }

    public String getNombres() {
        return dclnNombres;

    }

    /**
     * @return the dclnNombres
     */
    public String getDclnNombres() {
        return dclnNombres;
    }

    /**
     * @param dclnNombres the dclnNombres to set
     */
    public void setDclnNombres(String dclnNombres) {
        this.dclnNombres = dclnNombres;
    }

    /**
     * @return the dclnDireccion
     */
    public String getDclnDireccion() {
        return dclnDireccion;
    }

    /**
     * @param dclnDireccion the dclnDireccion to set
     */
    public void setDclnDireccion(String dclnDireccion) {
        this.dclnDireccion = dclnDireccion;
    }

    /**
     * @return the dclnEmail
     */
    public String getDclnEmail() {
        return dclnEmail;
    }

    /**
     * @param dclnEmail the dclnEmail to set
     */
    public void setDclnEmail(String dclnEmail) {
        this.dclnEmail = dclnEmail;
    }

    /**
     * @return the rfCargocontacto
     */
    public RfCargocontacto getRfCargocontacto() {
        return rfCargocontacto;
    }

    /**
     * @param rfCargocontacto the rfCargocontacto to set
     */
    public void setRfCargocontacto(RfCargocontacto rfCargocontacto) {
        this.rfCargocontacto = rfCargocontacto;
    }

    /**
     * @return the dclnCelular
     */
    public String getDclnCelular() {
        return dclnCelular;
    }

    /**
     * @param dclnCelular the dclnCelular to set
     */
    public void setDclnCelular(String dclnCelular) {
        this.dclnCelular = dclnCelular;
    }

    /**
     * @return the dclnExtension
     */
    public String getDclnExtension() {
        return dclnExtension;
    }

    /**
     * @param dclnExtension the dclnExtension to set
     */
    public void setDclnExtension(String dclnExtension) {
        this.dclnExtension = dclnExtension;
    }
}
