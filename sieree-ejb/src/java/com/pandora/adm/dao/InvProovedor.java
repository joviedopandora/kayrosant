    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.dao;

import com.pandora.pagocuenta.dao.FinFormapago;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lfchacon
 */
@Entity
@Table(name = "inv_proovedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvProovedor.findAll", query = "SELECT i FROM InvProovedor i ORDER BY i.prvId"),
    @NamedQuery(name = "InvProovedor.findByPrvId", query = "SELECT i FROM InvProovedor i WHERE i.prvId = :prvId"),
    @NamedQuery(name = "InvProovedor.findByPrvNit", query = "SELECT i FROM InvProovedor i WHERE i.prvNit = :prvNit"),
    @NamedQuery(name = "InvProovedor.findByPrvDigVer", query = "SELECT i FROM InvProovedor i WHERE i.prvDigver = :prvDigver"),
    @NamedQuery(name = "InvProovedor.findByPrvRazonsoc", query = "SELECT i FROM InvProovedor i WHERE i.prvRazonsoc = :prvRazonsoc"),
    @NamedQuery(name = "InvProovedor.findByPrvEstado", query = "SELECT i FROM InvProovedor i WHERE i.prvEstado = :prvEstado"),
    @NamedQuery(name = "InvProovedor.findByPrvDesc", query = "SELECT i FROM InvProovedor i WHERE i.prvDesc = :prvDesc"),
    @NamedQuery(name = "InvProovedor.findByPrvTel1", query = "SELECT i FROM InvProovedor i WHERE i.prvTel1 = :prvTel1"),
    @NamedQuery(name = "InvProovedor.findByPrvTel2", query = "SELECT i FROM InvProovedor i WHERE i.prvTel2 = :prvTel2"),
    @NamedQuery(name = "InvProovedor.findByPrvEmail", query = "SELECT i FROM InvProovedor i WHERE i.prvEmail = :prvEmail"),
    @NamedQuery(name = "InvProovedor.findByPrvDireccion", query = "SELECT i FROM InvProovedor i WHERE i.prvDireccion = :prvDireccion"),
    @NamedQuery(name = "InvProovedor.findByIndversion", query = "SELECT i FROM InvProovedor i WHERE i.indversion = :indversion"),
    @NamedQuery(name = "InvProovedor.provXConsCompraXStrId", query = "SELECT prv FROM InvProovedor prv WHERE prv.prvId IN(SELECT DISTINCT i.prvId FROM InvProovedor i JOIN i.cmpConsolcompraList cc WHERE cc.strId = :strId )")
})
public class InvProovedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "prv_id")
    private Integer prvId;
    @Size(max = 2147483647)
    @Column(name = "prv_razonsoc")
    private String prvRazonsoc;
    @Size(max = 2147483647)
    @Column(name = "prv_desc")
    private String prvDesc;
    @Size(max = 50)
    @Column(name = "prv_tel1")
    private String prvTel1;
    @Size(max = 50)
    @Column(name = "prv_tel2")
    private String prvTel2;
    @Size(max = 100)
    @Column(name = "prv_email")
    private String prvEmail;
    @Size(max = 800)
    @Column(name = "prv_direccion")
    private String prvDireccion;
    @Column(name = "prv_digver")
    private Integer prvDigver;
    @Size(max = 25)
    @Column(name = "prv_nit")
    private String prvNit;
    @Size(max = 300)
    @Column(name = "prv_contacto")
    private String prvContacto;
    @Size(max = 20)
    @Column(name = "prv_telcontacto")
    private String prvTelcontacto;
    @Column(name = "prv_estado")
    private boolean prvEstado;
    @Column(name = "indversion")
    private int indversion;
    @OneToMany(mappedBy = "prvId")
    private List<CmpConsolcompra> cmpConsolcompraList;
    @JoinColumn(name = "fpg_id", referencedColumnName = "fpg_id")
    @ManyToOne(optional = false)
    private FinFormapago fpgId;
    @OneToMany(mappedBy = "prvId")
    private List<InvProdxprov> invProdxprovList;
    @OneToMany(mappedBy = "prvId")
    private List<CmpFactura> cmpFacturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prvId")
    private List<InvBancoxprov> invBancoxprovList;
    

    public InvProovedor() {
    }

    public InvProovedor(Integer prvId) {
        this.prvId = prvId;
    }

    public Integer getPrvId() {
        return prvId;
    }

    public void setPrvId(Integer prvId) {
        this.prvId = prvId;
    }

    public String getPrvRazonsoc() {
        return prvRazonsoc;
    }

    public void setPrvRazonsoc(String prvRazonsoc) {
        this.prvRazonsoc = prvRazonsoc;
    }

    public String getPrvDesc() {
        return prvDesc;
    }

    public void setPrvDesc(String prvDesc) {
        this.prvDesc = prvDesc;
    }

    public String getPrvTel1() {
        return prvTel1;
    }

    public void setPrvTel1(String prvTel1) {
        this.prvTel1 = prvTel1;
    }

    public String getPrvTel2() {
        return prvTel2;
    }

    public void setPrvTel2(String prvTel2) {
        this.prvTel2 = prvTel2;
    }

    public String getPrvEmail() {
        return prvEmail;
    }

    public void setPrvEmail(String prvEmail) {
        this.prvEmail = prvEmail;
    }

    public String getPrvDireccion() {
        return prvDireccion;
    }

    public void setPrvDireccion(String prvDireccion) {
        this.prvDireccion = prvDireccion;
    }

    public Integer getPrvDigver() {
        return prvDigver;
    }

    public void setPrvDigver(Integer prvDigver) {
        this.prvDigver = prvDigver;
    }

    public String getPrvNit() {
        return prvNit;
    }

    public void setPrvNit(String prvNit) {
        this.prvNit = prvNit;
    }

    public String getPrvContacto() {
        return prvContacto;
    }

    public void setPrvContacto(String prvContacto) {
        this.prvContacto = prvContacto;
    }

    public String getPrvTelcontacto() {
        return prvTelcontacto;
    }

    public void setPrvTelcontacto(String prvTelcontacto) {
        this.prvTelcontacto = prvTelcontacto;
    }

    @XmlTransient
    public List<CmpConsolcompra> getCmpConsolcompraList() {
        return cmpConsolcompraList;
    }

    public void setCmpConsolcompraList(List<CmpConsolcompra> cmpConsolcompraList) {
        this.cmpConsolcompraList = cmpConsolcompraList;
    }

    public FinFormapago getFpgId() {
        return fpgId;
    }

    public void setFpgId(FinFormapago fpgId) {
        this.fpgId = fpgId;
    }

    @XmlTransient
    public List<InvProdxprov> getInvProdxprovList() {
        return invProdxprovList;
    }

    public void setInvProdxprovList(List<InvProdxprov> invProdxprovList) {
        this.invProdxprovList = invProdxprovList;
    }

    @XmlTransient
    public List<InvBancoxprov> getInvBancoxprovList() {
        return invBancoxprovList;
    }

    public void setInvBancoxprovList(List<InvBancoxprov> invBancoxprovList) {
        this.invBancoxprovList = invBancoxprovList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prvId != null ? prvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvProovedor)) {
            return false;
        }
        InvProovedor other = (InvProovedor) object;
        if ((this.prvId == null && other.prvId != null) || (this.prvId != null && !this.prvId.equals(other.prvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.dao.InvProovedor[ prvId=" + prvId + " ]";
    }

    /**
     * @return the cmpFacturaList
     */
    public List<CmpFactura> getCmpFacturaList() {
        return cmpFacturaList;
    }

    /**
     * @param cmpFacturaList the cmpFacturaList to set
     */
    public void setCmpFacturaList(List<CmpFactura> cmpFacturaList) {
        this.cmpFacturaList = cmpFacturaList;
    }

    /**
     * @return the prvEstado
     */
    public boolean getPrvEstado() {
        return prvEstado;
    }

    /**
     * @param prvEstado the prvEstado to set
     */
    public void setPrvEstado(boolean prvEstado) {
        this.prvEstado = prvEstado;
    }

    /**
     * @return the indversion
     */
    public int getIndversion() {
        return indversion;
    }

    /**
     * @param indversion the indversion to set
     */
    public void setIndversion(int indversion) {
        this.indversion = indversion;
    }
}