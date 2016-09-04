/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import adm.sys.dao.AdmCrgxcol;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "cmp_requiscomp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmpRequiscomp.findAll", query = "SELECT c FROM CmpRequiscomp c"),
    @NamedQuery(name = "CmpRequiscomp.findByCrqId", query = "SELECT c FROM CmpRequiscomp c WHERE c.crqId = :crqId"),
    @NamedQuery(name = "CmpRequiscomp.findByCxcId", query = "SELECT c FROM CmpRequiscomp c WHERE c.cxcId = :cxcId"),
    @NamedQuery(name = "CmpRequiscomp.findByCrqFproc", query = "SELECT c FROM CmpRequiscomp c WHERE c.crqFproc = :crqFproc"),
    @NamedQuery(name = "CmpRequiscomp.findByCqrEst", query = "SELECT c FROM CmpRequiscomp c WHERE c.cqrEst = :cqrEst"),
    @NamedQuery(name = "CmpRequiscomp.findByIndversion", query = "SELECT c FROM CmpRequiscomp c WHERE c.indversion = :indversion"),
    @NamedQuery(name = "CmpRequiscomp.findByCrqRevisada", query = "SELECT c FROM CmpRequiscomp c WHERE c.crqRevisada = :crqRevisada"),
    @NamedQuery(name = "CmpRequiscomp.findByCrqAbierta", query = "SELECT c FROM CmpRequiscomp c WHERE c.crqAbierta = :crqAbierta"),
    //Requisiciones aprobadas por tarea
    @NamedQuery(name = "CmpRequiscomp.findByAprobadaXTarea", query = "SELECT c FROM CmpRequiscomp c WHERE c.crqAprobado = :crqAprobado AND c.strId = :strId "),
    //Requisición por usuario no confirmada
    @NamedQuery(name = "CmpRequiscomp.reqXColAbierta", query = "SELECT c FROM CmpRequiscomp c "
            + "JOIN c.cxcId cxc WHERE cxc.cxcId = :cxcId AND c.crqAbierta = :crqAbierta "),
    @NamedQuery(name = "CmpRequiscomp.reqNoRevisa", query = "SELECT c FROM CmpRequiscomp c JOIN c.cxcId cxc JOIN cxc.cpeId cpe "
            + "WHERE cpe.cpeEstcop = :cpeEstcop AND c.crqRevisada = :crqRevisada "),
    @NamedQuery(name = "CmpRequiscomp.cantFilasTabla", query = "SELECT COUNT(c.crqId) FROM CmpRequiscomp c"),
    //Requisiciones por tarea
    @NamedQuery(name = "CmpRequiscomp.findByStrId", query = "SELECT c FROM CmpRequiscomp c WHERE c.strId = :strId"),
    //Actualizar tarea de la requisición
    @NamedQuery(name = "CmpRequiscomp.actualizarStrId", query = "UPDATE CmpRequiscomp c SET c.strId = :strId WHERE c.strId IS NULL AND c.crqAbierta = :crqAbierta")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "CmpRequiscomp.cerrarReqAb", query = "UPDATE cmp_requiscomp SET crq_abierta = ? "
            + " WHERE crq_abierta = ? AND date_part('DAY', cast(? as date)) > date_part('DAY', cast(? as date)) "),
    @NamedNativeQuery(name = "CmpRequiscomp.reqsXEstXMes", query = "SELECT count(cmp_requiscomp.crq_id) FROM cmp_requiscomp "
            + " WHERE crq_abierta = ? AND date_part('MONTH', cast(? as date)) = date_part('MONTH', cast(? as date))")
})
public class CmpRequiscomp implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName = "cmp_requiscomp_crq_id_seq", allocationSize = 1, name = "crq_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crq_id_seq")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "crq_id")
    private Long crqId;
    @Column(name = "crq_fproc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date crqFproc;
    @Basic(optional = false)
    @Column(name = "cqr_est")
    private boolean cqrEst;
    @Version
    @NotNull
    @Basic(optional = false)
    @Column(name = "indversion")
    private int indversion;
    @NotNull
    @Basic(optional = false)
    @Column(name = "crq_revisada")
    private boolean crqRevisada;
    @NotNull
    @Basic(optional = false)
    @Column(name = "crq_abierta")
    private boolean crqAbierta;
    @NotNull
    @Basic(optional = false)
    @Column(name = "crq_aprobado")
    private boolean crqAprobado;
    @Column(name = "str_id")
    private Long strId;
    @OneToMany(mappedBy = "crqId")
    private List<CmpProdxreq> cmpProdxreqList;
    @JoinColumn(name = "trq_id", referencedColumnName = "trq_id")
    @ManyToOne
    private CmpRfTiporequisicion trqId;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    public CmpRequiscomp() {
    }

    public CmpRequiscomp(Long crqId) {
        this.crqId = crqId;
    }

    public CmpRequiscomp(Long crqId, boolean cqrEst, int indversion, boolean crqRevisada) {
        this.crqId = crqId;
        this.cqrEst = cqrEst;
        this.indversion = indversion;
        this.crqRevisada = crqRevisada;
    }

    public Long getCrqId() {
        return crqId;
    }

    public void setCrqId(Long crqId) {
        this.crqId = crqId;
    }

    public Date getCrqFproc() {
        return crqFproc;
    }

    public void setCrqFproc(Date crqFproc) {
        this.crqFproc = crqFproc;
    }

    public boolean getCqrEst() {
        return cqrEst;
    }

    public void setCqrEst(boolean cqrEst) {
        this.cqrEst = cqrEst;
    }

    public int getIndversion() {
        return indversion;
    }

    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }

    public boolean getCrqRevisada() {
        return crqRevisada;
    }

    public void setCrqRevisada(boolean crqRevisada) {
        this.crqRevisada = crqRevisada;
    }

    @XmlTransient
    public List<CmpProdxreq> getCmpProdxreqList() {
        return cmpProdxreqList;
    }

    public void setCmpProdxreqList(List<CmpProdxreq> cmpProdxreqList) {
        this.cmpProdxreqList = cmpProdxreqList;
    }

    public CmpRfTiporequisicion getTrqId() {
        return trqId;
    }

    public void setTrqId(CmpRfTiporequisicion trqId) {
        this.trqId = trqId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (crqId != null ? crqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmpRequiscomp)) {
            return false;
        }
        CmpRequiscomp other = (CmpRequiscomp) object;
        if ((this.crqId == null && other.crqId != null) || (this.crqId != null && !this.crqId.equals(other.crqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.CmpRequiscomp[ crqId=" + crqId + " ]";
    }

    /**
     * @return the cxcId
     */
    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    /**
     * @param cxcId the cxcId to set
     */
    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }

    /**
     * @return the crqAbierta
     */
    public boolean isCrqAbierta() {
        return crqAbierta;
    }

    /**
     * @param crqAbierta the crqAbierta to set
     */
    public void setCrqAbierta(boolean crqAbierta) {
        this.crqAbierta = crqAbierta;
    }

    /**
     * @return the strId
     */
    public Long getStrId() {
        return strId;
    }

    /**
     * @param strId the strId to set
     */
    public void setStrId(Long strId) {
        this.strId = strId;
    }

    /**
     * @return the crqAprobado
     */
    public boolean isCrqAprobado() {
        return crqAprobado;
    }

    /**
     * @param crqAprobado the crqAprobado to set
     */
    public void setCrqAprobado(boolean crqAprobado) {
        this.crqAprobado = crqAprobado;
    }
}
