/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.dao;

import com.pandora.adm.dao.CmpConsolcompra;
import com.pandora.adm.dao.InvProovedor;
import com.pandora.mod.venta.dao.VntPagoventa;
import com.pandora.mod.venta.dao.VntRegistroventa;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "fin_formapago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinFormapago.findAll", query = "SELECT f FROM FinFormapago f"),
    @NamedQuery(name = "FinFormapago.findByFpgId", query = "SELECT f FROM FinFormapago f WHERE f.fpgId = :fpgId"),
    @NamedQuery(name = "FinFormapago.findByFpgNombre", query = "SELECT f FROM FinFormapago f WHERE f.fpgNombre = :fpgNombre"),
    @NamedQuery(name = "FinFormapago.findByFpgDesc", query = "SELECT f FROM FinFormapago f WHERE f.fpgDesc = :fpgDesc"),
    @NamedQuery(name = "FinFormapago.findByFpgEst", query = "SELECT f FROM FinFormapago f WHERE f.fpgEst = :fpgEst ORDER BY f.fpgNombre"),
    @NamedQuery(name = "FinFormapago.findByIndversion", query = "SELECT f FROM FinFormapago f WHERE f.indversion = :indversion")})
public class FinFormapago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "fpg_id")
    private Integer fpgId;
    @Size(max = 100)
    @Column(name = "fpg_nombre")
    private String fpgNombre;
    @Size(max = 2147483647)
    @Column(name = "fpg_desc")
    private String fpgDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fpg_est")
    private boolean fpgEst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @OneToMany(mappedBy = "fpgId")
    private List<VntRegistroventa> vntRegistroventaList;
    @OneToMany(mappedBy = "fpgId")
    private List<CmpConsolcompra> cmpConsolcompraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fpgId")
    private List<InvProovedor> invProovedorList;
    @OneToMany(mappedBy = "fpgId")
    private List<FinFpgxcta> finFpgxctaList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fpg_aplicabanco")
    private boolean fpgAplicabanco;
    @OneToMany(mappedBy = "finFormapago")
    private List<VntPagoventa> vntPagoventaList;

    public FinFormapago() {
    }

    public FinFormapago(Integer fpgId) {
        this.fpgId = fpgId;
    }

    public FinFormapago(Integer fpgId, boolean fpgEst, int indversion) {
        this.fpgId = fpgId;
        this.fpgEst = fpgEst;
        this.indversion = indversion;
    }

    public Integer getFpgId() {
        return fpgId;
    }

    public void setFpgId(Integer fpgId) {
        this.fpgId = fpgId;
    }

    public String getFpgNombre() {
        return fpgNombre;
    }

    public void setFpgNombre(String fpgNombre) {
        this.fpgNombre = fpgNombre;
    }

    public String getFpgDesc() {
        return fpgDesc;
    }

    public void setFpgDesc(String fpgDesc) {
        this.fpgDesc = fpgDesc;
    }

    public boolean getFpgEst() {
        return fpgEst;
    }

    public void setFpgEst(boolean fpgEst) {
        this.fpgEst = fpgEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    @XmlTransient
    public List<VntRegistroventa> getVntRegistroventaList() {
        return vntRegistroventaList;
    }

    public void setVntRegistroventaList(List<VntRegistroventa> vntRegistroventaList) {
        this.vntRegistroventaList = vntRegistroventaList;
    }

    @XmlTransient
    public List<CmpConsolcompra> getCmpConsolcompraList() {
        return cmpConsolcompraList;
    }

    public void setCmpConsolcompraList(List<CmpConsolcompra> cmpConsolcompraList) {
        this.cmpConsolcompraList = cmpConsolcompraList;
    }

    @XmlTransient
    public List<InvProovedor> getInvProovedorList() {
        return invProovedorList;
    }

    public void setInvProovedorList(List<InvProovedor> invProovedorList) {
        this.invProovedorList = invProovedorList;
    }

    @XmlTransient
    public List<FinFpgxcta> getFinFpgxctaList() {
        return finFpgxctaList;
    }

    public void setFinFpgxctaList(List<FinFpgxcta> finFpgxctaList) {
        this.finFpgxctaList = finFpgxctaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fpgId != null ? fpgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinFormapago)) {
            return false;
        }
        FinFormapago other = (FinFormapago) object;
        if ((this.fpgId == null && other.fpgId != null) || (this.fpgId != null && !this.fpgId.equals(other.fpgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.FinFormapago[ fpgId=" + fpgId + " ]";
    }

    /**
     * @return the fpgAplicabanco
     */
    public boolean isFpgAplicabanco() {
        return fpgAplicabanco;
    }

    /**
     * @param fpgAplicabanco the fpgAplicabanco to set
     */
    public void setFpgAplicabanco(boolean fpgAplicabanco) {
        this.fpgAplicabanco = fpgAplicabanco;
    }

    /**
     * @return the vntPagoventaList
     */
    public List<VntPagoventa> getVntPagoventaList() {
        return vntPagoventaList;
    }

    /**
     * @param vntPagoventaList the vntPagoventaList to set
     */
    public void setVntPagoventaList(List<VntPagoventa> vntPagoventaList) {
        this.vntPagoventaList = vntPagoventaList;
    }
}
