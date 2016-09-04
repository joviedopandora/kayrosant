/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "regapp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Regapp.findAll", query = "SELECT r FROM Regapp r"),
    @NamedQuery(name = "Regapp.findByRegId", query = "SELECT r FROM Regapp r WHERE r.regId = :regId"),
    @NamedQuery(name = "Regapp.findByRegMensaje", query = "SELECT r FROM Regapp r WHERE r.regMensaje = :regMensaje"),
    @NamedQuery(name = "Regapp.findByRegFcre", query = "SELECT r FROM Regapp r WHERE r.regFcre = :regFcre"),
    @NamedQuery(name = "Regapp.findByRegClase", query = "SELECT r FROM Regapp r WHERE r.regClase = :regClase"),
    @NamedQuery(name = "Regapp.findByUsrId", query = "SELECT r FROM Regapp r WHERE r.usrId = :usrId"),
    @NamedQuery(name = "Regapp.findByRegNivel", query = "SELECT r FROM Regapp r WHERE r.regNivel = :regNivel"),
    @NamedQuery(name = "Regapp.findByRegSessionid", query = "SELECT r FROM Regapp r WHERE r.regSessionid = :regSessionid"),
    @NamedQuery(name = "Regapp.findByRegEstoper", query = "SELECT r FROM Regapp r WHERE r.regEstoper = :regEstoper"),
    @NamedQuery(name = "Regapp.findByRegNomboper", query = "SELECT r FROM Regapp r WHERE r.regNomboper = :regNomboper"),
    @NamedQuery(name = "Regapp.findByRegTiempooper", query = "SELECT r FROM Regapp r WHERE r.regTiempooper = :regTiempooper"),
    @NamedQuery(name = "Regapp.findByRegPrioridad", query = "SELECT r FROM Regapp r WHERE r.regPrioridad = :regPrioridad")})
public class Regapp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reg_id")
    private Long regId;
    @Size(max = 2147483647)
    @Column(name = "reg_mensaje")
    private String regMensaje;
    @Column(name = "reg_fcre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regFcre;
    @Size(max = 255)
    @Column(name = "reg_clase")
    private String regClase;
    @Size(max = 255)
    @Column(name = "usr_id")
    private String usrId;
    @Size(max = 50)
    @Column(name = "reg_nivel")
    private String regNivel;
    @Size(max = 500)
    @Column(name = "reg_sessionid")
    private String regSessionid;
    @Size(max = 50)
    @Column(name = "reg_estoper")
    private String regEstoper;
    @Size(max = 150)
    @Column(name = "reg_nomboper")
    private String regNomboper;
    @Column(name = "reg_tiempooper")
    private BigInteger regTiempooper;
    @Size(max = 50)
    @Column(name = "reg_prioridad")
    private String regPrioridad;

    public Regapp() {
    }

    public Regapp(Long regId) {
        this.regId = regId;
    }

    public Long getRegId() {
        return regId;
    }

    public void setRegId(Long regId) {
        this.regId = regId;
    }

    public String getRegMensaje() {
        return regMensaje;
    }

    public void setRegMensaje(String regMensaje) {
        this.regMensaje = regMensaje;
    }

    public Date getRegFcre() {
        return regFcre;
    }

    public void setRegFcre(Date regFcre) {
        this.regFcre = regFcre;
    }

    public String getRegClase() {
        return regClase;
    }

    public void setRegClase(String regClase) {
        this.regClase = regClase;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getRegNivel() {
        return regNivel;
    }

    public void setRegNivel(String regNivel) {
        this.regNivel = regNivel;
    }

    public String getRegSessionid() {
        return regSessionid;
    }

    public void setRegSessionid(String regSessionid) {
        this.regSessionid = regSessionid;
    }

    public String getRegEstoper() {
        return regEstoper;
    }

    public void setRegEstoper(String regEstoper) {
        this.regEstoper = regEstoper;
    }

    public String getRegNomboper() {
        return regNomboper;
    }

    public void setRegNomboper(String regNomboper) {
        this.regNomboper = regNomboper;
    }

    public BigInteger getRegTiempooper() {
        return regTiempooper;
    }

    public void setRegTiempooper(BigInteger regTiempooper) {
        this.regTiempooper = regTiempooper;
    }

    public String getRegPrioridad() {
        return regPrioridad;
    }

    public void setRegPrioridad(String regPrioridad) {
        this.regPrioridad = regPrioridad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regId != null ? regId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regapp)) {
            return false;
        }
        Regapp other = (Regapp) object;
        if ((this.regId == null && other.regId != null) || (this.regId != null && !this.regId.equals(other.regId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.Regapp[ regId=" + regId + " ]";
    }
    
}
