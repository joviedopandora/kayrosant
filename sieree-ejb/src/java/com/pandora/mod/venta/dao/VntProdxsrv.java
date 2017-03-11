/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import com.pandora.adm.dao.InvProducto;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fernando
 */
@Entity
@Table(name = "vnt_prodxsrv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VntProdxsrv.findAll", query = "SELECT v FROM VntProdxsrv v"),
    @NamedQuery(name = "VntProdxsrv.findByProdxsrvId", query = "SELECT v FROM VntProdxsrv v WHERE v.prodxsrvId = :prodxsrvId"),
    @NamedQuery(name = "VntProdxsrv.findByProdxsrvCantidad", query = "SELECT v FROM VntProdxsrv v WHERE v.prodxsrvCantidad = :prodxsrvCantidad"),
    @NamedQuery(name = "VntProdxsrv.findByProdxsrvEst", query = "SELECT v FROM VntProdxsrv v WHERE v.prodxsrvEst = :prodxsrvEst"),
    @NamedQuery(name = "VntProdxsrv.findByIndversion", query = "SELECT v FROM VntProdxsrv v WHERE v.indversion = :indversion"),
    //Cargar lista de productos por servicio
    @NamedQuery(name = "VntProdxsrv.pxsXServicio", query = "SELECT v FROM VntProdxsrv v JOIN v.vsrvId s WHERE s.vsrvId = :vsrvId AND v.prodxsrvEst = :prodxsrvEst ORDER BY v.prdId.prdNombre")
})
public class VntProdxsrv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prodxsrv_id")
    private Long prodxsrvId;
    @Column(name = "prodxsrv_cantidad")
    private Integer prodxsrvCantidad;
    @Column(name = "prodxsrv_est")
    private Boolean prodxsrvEst;
    @Column(name = "indversion")
    private Integer indversion;
    @JoinColumn(name = "vsrv_id", referencedColumnName = "vsrv_id")
    @ManyToOne
    private VntServicio vsrvId;
    @JoinColumn(name = "prd_id", referencedColumnName = "prd_id")
    @ManyToOne
    private InvProducto prdId;

    public VntProdxsrv() {
       prodxsrvEst= true; 
    }

    public VntProdxsrv(Long prodxsrvId) {
        this.prodxsrvId = prodxsrvId;
         prodxsrvEst= true; 
    }

    public Long getProdxsrvId() {
        return prodxsrvId;
    }

    public void setProdxsrvId(Long prodxsrvId) {
        this.prodxsrvId = prodxsrvId;
    }

    public Integer getProdxsrvCantidad() {
        return prodxsrvCantidad;
    }

    public void setProdxsrvCantidad(Integer prodxsrvCantidad) {
        this.prodxsrvCantidad = prodxsrvCantidad;
    }

    public Boolean getProdxsrvEst() {
        if(prodxsrvEst== null){
            prodxsrvEst= false;
        }
        return prodxsrvEst;
    }

    public void setProdxsrvEst(Boolean prodxsrvEst) {
        this.prodxsrvEst = prodxsrvEst;
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public VntServicio getVsrvId() {
        return vsrvId;
    }

    public void setVsrvId(VntServicio vsrvId) {
        this.vsrvId = vsrvId;
    }

    public InvProducto getPrdId() {
        return prdId;
    }

    public void setPrdId(InvProducto prdId) {
        this.prdId = prdId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodxsrvId != null ? prodxsrvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
         
        if (!(object instanceof VntProdxsrv)) {
            return false;
        }
        VntProdxsrv other = (VntProdxsrv) object;
        if(this == null  ){
            return false;
        }
        
        if(this.prdId == null ||  other.prdId == null){
             return true;
        }
        if(this.prdId.getPrdId() == null ||  other.prdId.getPrdId()== null){
             return true;
        }
        return (this.prdId.getPrdId().equals( other.prdId.getPrdId()));
            
       
       
    }

    @Override
    public String toString() {
        return "com.pandora.mod.venta.VntProdxsrv[ prodxsrvId=" + prodxsrvId + " ]";
    }

}
