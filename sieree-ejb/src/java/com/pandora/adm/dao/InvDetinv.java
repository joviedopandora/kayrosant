/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "inv_detinv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvDetinv.findAll", query = "SELECT i FROM InvDetinv i"),
    @NamedQuery(name = "InvDetinv.findByDivId", query = "SELECT i FROM InvDetinv i WHERE i.divId = :divId"),
    @NamedQuery(name = "InvDetinv.findByDivDescripcion", query = "SELECT i FROM InvDetinv i WHERE i.divDescripcion = :divDescripcion"),
    @NamedQuery(name = "InvDetinv.findByDivNumserie", query = "SELECT i FROM InvDetinv i WHERE i.divNumserie = :divNumserie"),
    @NamedQuery(name = "InvDetinv.findByDivLote", query = "SELECT i FROM InvDetinv i WHERE i.divLote = :divLote"),
    @NamedQuery(name = "InvDetinv.findByDivDesc", query = "SELECT i FROM InvDetinv i WHERE i.divDesc = :divDesc"),
    @NamedQuery(name = "InvDetinv.findByIndversion", query = "SELECT i FROM InvDetinv i WHERE i.indversion = :indversion"),
    @NamedQuery(name = "InvDetinv.findByDivNumserialfabricante", query = "SELECT i FROM InvDetinv i WHERE i.divNumserialfabricante = :divNumserialfabricante")})
public class InvDetinv implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "div_id")
    private Integer divId;
    @Size(max = 2147483647)
    @Column(name = "div_Descripcion")
    private String divDescripcion;
    @Size(max = 100)
    @Column(name = "div_numserie")
    private String divNumserie;
    @Size(max = 100)
    @Column(name = "div_lote")
    private String divLote;
    @Size(max = 2147483647)
    @Column(name = "div_desc")
    private String divDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indversion")
    private int indversion;
    @Size(max = 150)
    @Column(name = "div_numserialfabricante")
    private String divNumserialfabricante;
    @OneToOne(mappedBy = "divId")
    private InvInvent invId;

    public InvDetinv() {
    }

    public InvDetinv(Integer divId) {
        this.divId = divId;
    }

    public InvDetinv(Integer divId, int indversion) {
        this.divId = divId;
        this.indversion = indversion;
    }

    public Integer getDivId() {
        return divId;
    }

    public void setDivId(Integer divId) {
        this.divId = divId;
    }

    public String getDivNumserie() {
        return divNumserie;
    }

    public void setDivNumserie(String divNumserie) {
        this.divNumserie = divNumserie;
    }

    public String getDivLote() {
        return divLote;
    }

    public void setDivLote(String divLote) {
        this.divLote = divLote;
    }

    public String getDivDesc() {
        return divDesc;
    }

    public void setDivDesc(String divDesc) {
        this.divDesc = divDesc;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (divId != null ? divId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvDetinv)) {
            return false;
        }
        InvDetinv other = (InvDetinv) object;
        if ((this.divId == null && other.divId != null) || (this.divId != null && !this.divId.equals(other.divId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.InvDetinv[ divId=" + divId + " ]";
    }

    /**
     * @return the divNumserialfabricante
     */
    public String getDivNumserialfabricante() {
        return divNumserialfabricante;
    }

    /**
     * @param divNumserialfabricante the divNumserialfabricante to set
     */
    public void setDivNumserialfabricante(String divNumserialfabricante) {
        this.divNumserialfabricante = divNumserialfabricante;
    }

    /**
     * @return the divDescripcion
     */
    public String getDivDescripcion() {
        return divDescripcion;
    }

    /**
     * @param divDescripcion the divDescripcion to set
     */
    public void setDivDescripcion(String divDescripcion) {
        this.divDescripcion = divDescripcion;
    }

    /**
     * @return the invId
     */
    public InvInvent getInvId() {
        return invId;
    }

    /**
     * @param invId the invId to set
     */
    public void setInvId(InvInvent invId) {
        this.invId = invId;
    }
    
}
