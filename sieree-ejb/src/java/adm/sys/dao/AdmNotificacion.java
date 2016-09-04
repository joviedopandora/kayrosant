/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntDetallecliente;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntRfTipocliente;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author breyner.robles
 */
@Entity
@Table(name = "adm_notificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmNotificacion.findAll", query = "SELECT a FROM AdmNotificacion a"),
    @NamedQuery(name = "AdmNotificacion.findByNotId", query = "SELECT a FROM AdmNotificacion a WHERE a.notId = :notId"),
    @NamedQuery(name = "AdmNotificacion.findByNotDescipcion", query = "SELECT a FROM AdmNotificacion a WHERE a.notDescipcion = :notDescipcion"),
    @NamedQuery(name = "AdmNotificacion.findByNotFechacreacion", query = "SELECT a FROM AdmNotificacion a WHERE a.notFechacreacion = :notFechacreacion"),
    @NamedQuery(name = "AdmNotificacion.findByNotFechaprocesocierre", query = "SELECT a FROM AdmNotificacion a WHERE a.notFechaprocesocierre = :notFechaprocesocierre"),
    @NamedQuery(name = "AdmNotificacion.findByNotEstado", query = "SELECT a FROM AdmNotificacion a WHERE a.notEstado = :notEstado"),
    @NamedQuery(name = "AdmNotificacion.findByNotEstadoPendientes", query = "SELECT a FROM AdmNotificacion a WHERE a.notEstado IN( :notEstado1,:notEstado2) AND a.vntRfTipocliente.tclId = :tclId ORDER BY a.notFechacreacion DESC,a.notEstado")})
public class AdmNotificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "not_id")
    private Long notId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "not_descipcion")
    private String notDescipcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "not_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notFechacreacion;
    @Column(name = "not_fechaprocesocierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notFechaprocesocierre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "not_estado")
    private int notEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "not_fechaevento")
    @Temporal(TemporalType.DATE)
    private Date notFechaevento;
    @JoinColumn(name = "rgvt_id", referencedColumnName = "rgvt_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VntRegistroventa vntRegistroventa;
    @JoinColumn(name = "vde_id", referencedColumnName = "vde_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VntDetevento vntDetevento;
    @JoinColumn(name = "dcln_id", referencedColumnName = "dcln_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VntDetallecliente vntDetallecliente;
    @JoinColumn(name = "cln_id", referencedColumnName = "cln_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VntCliente vntCliente;
    @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdmCrgxcol admCrgxcol;
    @JoinColumn(name = "tcl_id", referencedColumnName = "tcl_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntRfTipocliente vntRfTipocliente;

    public AdmNotificacion() {
    }

    public AdmNotificacion(Long notId) {
        this.notId = notId;
    }

    public AdmNotificacion(Long notId, String notDescipcion, Date notFechacreacion, int notEstado) {
        this.notId = notId;
        this.notDescipcion = notDescipcion;
        this.notFechacreacion = notFechacreacion;
        this.notEstado = notEstado;
    }

    public Long getNotId() {
        return notId;
    }

    public void setNotId(Long notId) {
        this.notId = notId;
    }

    public String getNotDescipcion() {
        return notDescipcion;
    }

    public void setNotDescipcion(String notDescipcion) {
        this.notDescipcion = notDescipcion;
    }

    public Date getNotFechacreacion() {
        return notFechacreacion;
    }

    public void setNotFechacreacion(Date notFechacreacion) {
        this.notFechacreacion = notFechacreacion;
    }

    public Date getNotFechaprocesocierre() {
        return notFechaprocesocierre;
    }

    public void setNotFechaprocesocierre(Date notFechaprocesocierre) {
        this.notFechaprocesocierre = notFechaprocesocierre;
    }

    public int getNotEstado() {
        return notEstado;
    }

    public void setNotEstado(int notEstado) {
        this.notEstado = notEstado;
    }

    public VntRegistroventa getVntRegistroventa() {
        return vntRegistroventa;
    }

    public void setVntRegistroventa(VntRegistroventa vntRegistroventa) {
        this.vntRegistroventa = vntRegistroventa;
    }

    public VntDetevento getVntDetevento() {
        return vntDetevento;
    }

    public void setVntDetevento(VntDetevento vntDetevento) {
        this.vntDetevento = vntDetevento;
    }

    public VntDetallecliente getVntDetallecliente() {
        return vntDetallecliente;
    }

    public void setVntDetallecliente(VntDetallecliente vntDetallecliente) {
        this.vntDetallecliente = vntDetallecliente;
    }

    public VntCliente getVntCliente() {
        return vntCliente;
    }

    public void setVntCliente(VntCliente vntCliente) {
        this.vntCliente = vntCliente;
    }

    public AdmCrgxcol getAdmCrgxcol() {
        return admCrgxcol;
    }

    public void setAdmCrgxcol(AdmCrgxcol admCrgxcol) {
        this.admCrgxcol = admCrgxcol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notId != null ? notId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmNotificacion)) {
            return false;
        }
        AdmNotificacion other = (AdmNotificacion) object;
        if ((this.notId == null && other.notId != null) || (this.notId != null && !this.notId.equals(other.notId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.AdmNotificacion[ notId=" + notId + " ]";
    }

    /**
     * @return the notFechaevento
     */
    public Date getNotFechaevento() {
        return notFechaevento;
    }

    /**
     * @param notFechaevento the notFechaevento to set
     */
    public void setNotFechaevento(Date notFechaevento) {
        this.notFechaevento = notFechaevento;
    }

    /**
     * @return the vntRfTipocliente
     */
    public VntRfTipocliente getVntRfTipocliente() {
        return vntRfTipocliente;
    }

    /**
     * @param vntRfTipocliente the vntRfTipocliente to set
     */
    public void setVntRfTipocliente(VntRfTipocliente vntRfTipocliente) {
        this.vntRfTipocliente = vntRfTipocliente;
    }
}
