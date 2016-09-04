/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import adm.sys.dao.AdmColaborador;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "vnt_rf_tipocliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntRfTipocliente.findAll", query = "SELECT v FROM VntRfTipocliente v"),
    @NamedQuery(name = "VntRfTipocliente.findByTclId", query = "SELECT v FROM VntRfTipocliente v WHERE v.tclId = :tclId"),
    @NamedQuery(name = "VntRfTipocliente.findByTclNombre", query = "SELECT v FROM VntRfTipocliente v WHERE v.tclNombre = :tclNombre"),
    @NamedQuery(name = "VntRfTipocliente.findByTclDesc", query = "SELECT v FROM VntRfTipocliente v WHERE v.tclDesc = :tclDesc"),
    @NamedQuery(name = "VntRfTipocliente.findByTclEst", query = "SELECT v FROM VntRfTipocliente v WHERE v.tclEst = :tclEst"),
    @NamedQuery(name = "VntRfTipocliente.findByIndversion", query = "SELECT v FROM VntRfTipocliente v WHERE v.indversion = :indversion")})
public class VntRfTipocliente implements Serializable {

    @Column(name = "id_grupo")
    private Integer idGrupo;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tcl_id")
    private Integer tclId;
    @Size(max = 100)
    @Column(name = "tcl_nombre")
    private String tclNombre;
    @Size(max = 2147483647)
    @Column(name = "tcl_desc")
    private String tclDesc;
    @Column(name = "tcl_est")
    private Boolean tclEst;
    @Column(name = "indversion")
    private Integer indversion;
    @OneToMany(mappedBy = "tclId")
    private List<VntCliente> vntClienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vntRfTipocliente", fetch = FetchType.LAZY)
    private List<VntTipoclientexmotivoevento> vntTipoclientexmotivoeventoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vntRfTipocliente", fetch = FetchType.LAZY)
    private List<AdmColaborador> admColaboradorList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tcl_logo")
    private String tclLogo;

    public VntRfTipocliente() {
    }

    public VntRfTipocliente(Integer tclId) {
        this.tclId = tclId;
    }

    public Integer getTclId() {
        return tclId;
    }

    public void setTclId(Integer tclId) {
        this.tclId = tclId;
    }

    public String getTclNombre() {
        return tclNombre;
    }

    public void setTclNombre(String tclNombre) {
        this.tclNombre = tclNombre;
    }

    public String getTclDesc() {
        return tclDesc;
    }

    public void setTclDesc(String tclDesc) {
        this.tclDesc = tclDesc;
    }

    public Boolean getTclEst() {
        return tclEst;
    }

    public void setTclEst(Boolean tclEst) {
        this.tclEst = tclEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<VntCliente> getVntClienteList() {
        return vntClienteList;
    }

    public void setVntClienteList(List<VntCliente> vntClienteList) {
        this.vntClienteList = vntClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tclId != null ? tclId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VntRfTipocliente)) {
            return false;
        }
        VntRfTipocliente other = (VntRfTipocliente) object;
        if ((this.tclId == null && other.tclId != null) || (this.tclId != null && !this.tclId.equals(other.tclId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.VntRfTipocliente[ tclId=" + tclId + " ]";
    }

    /**
     * @return the vntTipoclientexmotivoeventoList
     */
    public List<VntTipoclientexmotivoevento> getVntTipoclientexmotivoeventoList() {
        return vntTipoclientexmotivoeventoList;
    }

    /**
     * @param vntTipoclientexmotivoeventoList the
     * vntTipoclientexmotivoeventoList to set
     */
    public void setVntTipoclientexmotivoeventoList(List<VntTipoclientexmotivoevento> vntTipoclientexmotivoeventoList) {
        this.vntTipoclientexmotivoeventoList = vntTipoclientexmotivoeventoList;
    }

    /**
     * @return the admColaboradorList
     */
    public List<AdmColaborador> getAdmColaboradorList() {
        return admColaboradorList;
    }

    /**
     * @param admColaboradorList the admColaboradorList to set
     */
    public void setAdmColaboradorList(List<AdmColaborador> admColaboradorList) {
        this.admColaboradorList = admColaboradorList;
    }

    /**
     * @return the tclLogo
     */
    public String getTclLogo() {
        return tclLogo;
    }

    /**
     * @param tclLogo the tclLogo to set
     */
    public void setTclLogo(String tclLogo) {
        this.tclLogo = tclLogo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }
}
