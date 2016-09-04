/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.adm.rf.dao.RfDep;

import com.pandora.mod.venta.dao.VntRfTipocliente;
import com.pandora.pagocuenta.dao.RfBanco;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "adm_colaborador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmColaborador.findAll", query = "SELECT a FROM AdmColaborador a ORDER BY a.colApellido1"),
    @NamedQuery(name = "AdmColaborador.findByColCedula", query = "SELECT a FROM AdmColaborador a WHERE a.colCedula = :colCedula"),
    @NamedQuery(name = "AdmColaborador.findByColNombre1", query = "SELECT a FROM AdmColaborador a WHERE a.colNombre1 = :colNombre1"),
    @NamedQuery(name = "AdmColaborador.findByColNombre2", query = "SELECT a FROM AdmColaborador a WHERE a.colNombre2 = :colNombre2"),
    @NamedQuery(name = "AdmColaborador.findByColApellido1", query = "SELECT a FROM AdmColaborador a WHERE a.colApellido1 = :colApellido1"),
    @NamedQuery(name = "AdmColaborador.findByColApellido2", query = "SELECT a FROM AdmColaborador a WHERE a.colApellido2 = :colApellido2"),
    @NamedQuery(name = "AdmColaborador.findByColEst", query = "SELECT a FROM AdmColaborador a WHERE a.colEst = :colEst ORDER BY a.colApellido1"),
    @NamedQuery(name = "AdmColaborador.findByIndversion", query = "SELECT a FROM AdmColaborador a WHERE a.indversion = :indversion"),
//Consultas propias
    @NamedQuery(name = "AdmColaborador.findCedONom", query = "SELECT usr FROM AdmColaborador usr WHERE usr.colCedula IN(SELECT  DISTINCT u.colCedula FROM AdmColaborador u "
            + " WHERE (u.colCedula = :colCedula OR u.colNombre1 LIKE :texto OR u.colApellido1 LIKE :texto))"
            + " ORDER BY usr.colApellido1, usr.colNombre1")})
public class AdmColaborador implements Serializable {

    @JoinColumn(name = "estado_colaborador", referencedColumnName = "id_estadocolaborador")
    @ManyToOne
    private AdmEstcol estadoColaborador;

    @Column(name = "col_est")
    private Boolean colEst;
    @Column(name = "indversion")
    private Integer indversion;
    @Size(max = 150)
    @Column(name = "col_eps")
    private String colEps;
    @Column(name = "col_fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date colFechaNacimiento;
    @Column(name = "col_edad")
    private BigInteger colEdad;
    @Size(max = 150)
    @Column(name = "col_barrio")
    private String colBarrio;
    @Size(max = 15)
    @Column(name = "col_talla_zapatos")
    private String colTallaZapatos;
    @Size(max = 50)
    @Column(name = "col_camisa")
    private String colCamisa;
    @Size(max = 50)
    @Column(name = "col_pantalon")
    private String colPantalon;
    @Column(name = "col_estatura")
    private BigInteger colEstatura;
    @Size(max = 15)
    @Column(name = "col_rh")
    private String colRh;
    @Size(max = 150)
    @Column(name = "col_ref_personal")
    private String colRefPersonal;
    @Column(name = "col_fecha_vinculacion")

    @Temporal(TemporalType.DATE)
    private Date colFechaVinculacion;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "col_cedula")
    private String colCedula;
    @Size(max = 50)
    @Column(name = "col_expcedula")
    private String colExpcedula;
    @Size(max = 150)
    @Column(name = "col_nombre1")
    private String colNombre1;
    @Size(max = 150)
    @Column(name = "col_nombre2")
    private String colNombre2;
    @Size(max = 150)
    @Column(name = "col_apellido1")
    private String colApellido1;
    @Size(max = 150)
    @Column(name = "col_apellido2")
    private String colApellido2;
    @Size(max = 400)
    @Column(name = "col_direccion")
    private String colDireccion;
    @Size(max = 20)
    @Column(name = "col_telefono1")
    private String colTelefono1;
    @Size(max = 20)
    @Column(name = "col_telefono2")
    private String colTelefono2;
    @Size(max = 20)
    @Column(name = "col_celular")
    private String colCelular;
    @Size(max = 100)
    @Column(name = "col_email")
    private String colEmail;
    @JoinColumn(name = "tdc_id", referencedColumnName = "tdc_id")
    @ManyToOne
    private RfTipodoc tdcId;
    @OneToMany(mappedBy = "colCedula")
    private List<AdmColxemp> admColxempList;
    @JoinColumn(name = "tcl_id", referencedColumnName = "tcl_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VntRfTipocliente vntRfTipocliente;
    @JoinColumn(name = "sex_id", referencedColumnName = "sex_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public RfSexo rfSexo;

    @Column(name = "fecha_antiguedad")
    @Temporal(TemporalType.DATE)
    private Date fechaAntiguedad;

    @JoinColumn(name = "id_antiguedad", referencedColumnName = "id_antiguedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdmAntiguedad idAntiguedad;

    @JoinColumn(name = "col_ciudad", referencedColumnName = "ciu_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfCiudad colCiudad;
    @JoinColumn(name = "col_depart", referencedColumnName = "dep_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfDep colDepart;

    //nomina
    @Column(name = "observacion")
    private String observacion;
    @Size(max = 2147483647)

    @Column(name = "foto")
    private String foto;

    @JoinColumn(name = "tct_id", referencedColumnName = "tct_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfTipocontrato rfTipocontrato;
    @Size(max = 100)
    @Column(name = "col_email2")
    private String colEmail2;
    @JoinColumn(name = "ciu_id", referencedColumnName = "ciu_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfCiudad rfCiudad;
    @Column(name = "numero_de_cuenta")
    private String numerodecuenta;
    @JoinColumn(name = "bnc_id", referencedColumnName = "bnc_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RfBanco rfBanco;
    @Column(name = "nombre_de_cuenta")
    private String bancoDeCuenta;
    @Column(name = "documento_titular_cuenta")
    private String documentoTitularCuenta;

    public String getIdentificacion() {
        if (tdcId != null) {
            return tdcId.getTdcId() + "-" + colCedula;
        }
        return null;

    }

    public String getNombres() {
        if (colNombre1 != null) {
            return colNombre1 + (colNombre2 == null ? "" : " " + colNombre2) + " "
                    + colApellido1 + (colApellido2 == null ? "" : " " + colApellido2);
        }
        return null;

    }

    public AdmColaborador() {
    }

    public AdmColaborador(String colCedula) {
        this.colCedula = colCedula;
    }

    public AdmColaborador(String colCedula, boolean colEst, int indversion) {
        this.colCedula = colCedula;
        this.colEst = colEst;
        this.indversion = indversion;
    }

    public String getColCedula() {
        return colCedula;
    }

    public void setColCedula(String colCedula) {
        this.colCedula = colCedula;
    }

    public String getColExpcedula() {
        return colExpcedula;
    }

    public void setColExpcedula(String colExpcedula) {
        this.colExpcedula = colExpcedula;
    }

    public String getColNombre1() {
        return colNombre1;
    }

    public void setColNombre1(String colNombre1) {
        this.colNombre1 = colNombre1;
    }

    public String getColNombre2() {
        return colNombre2;
    }

    public void setColNombre2(String colNombre2) {
        this.colNombre2 = colNombre2;
    }

    public String getColApellido1() {
        return colApellido1;
    }

    public void setColApellido1(String colApellido1) {
        this.colApellido1 = colApellido1;
    }

    public String getColApellido2() {
        return colApellido2;
    }

    public void setColApellido2(String colApellido2) {
        this.colApellido2 = colApellido2;
    }

    public String getColDireccion() {
        return colDireccion;
    }

    public void setColDireccion(String colDireccion) {
        this.colDireccion = colDireccion;
    }

    public String getColTelefono1() {
        return colTelefono1;
    }

    public void setColTelefono1(String colTelefono1) {
        this.colTelefono1 = colTelefono1;
    }

    public String getColTelefono2() {
        return colTelefono2;
    }

    public void setColTelefono2(String colTelefono2) {
        this.colTelefono2 = colTelefono2;
    }

    public String getColCelular() {
        return colCelular;
    }

    public void setColCelular(String colCelular) {
        this.colCelular = colCelular;
    }

    public String getColEmail() {
        return colEmail;
    }

    public void setColEmail(String colEmail) {
        this.colEmail = colEmail;
    }

    public boolean getColEst() {
        return colEst;
    }

    public void setColEst(boolean colEst) {
        this.setColEst((Boolean) colEst);
    }

    public RfTipodoc getTdcId() {
        return tdcId;
    }

    public void setTdcId(RfTipodoc tdcId) {
        this.tdcId = tdcId;
    }

    @XmlTransient
    public List<AdmColxemp> getAdmColxempList() {
        return admColxempList;
    }

    public void setAdmColxempList(List<AdmColxemp> admColxempList) {
        this.admColxempList = admColxempList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (colCedula != null ? colCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmColaborador)) {
            return false;
        }
        AdmColaborador other = (AdmColaborador) object;
        if ((this.colCedula == null && other.colCedula != null) || (this.colCedula != null && !this.colCedula.equals(other.colCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.AdmColaborador[ colCedula=" + colCedula + " ]";
    }

    public void setColCedula(AdmColaborador ap) {
    }

    public Integer getIndversion() {
        return indversion;
    }

    public void setIndversion(Integer indversion) {
        this.indversion = indversion;
    }

    public String getColEps() {
        return colEps;
    }

    public void setColEps(String colEps) {
        this.colEps = colEps;
    }

    public Date getColFechaNacimiento() {
        return colFechaNacimiento;
    }

    public void setColFechaNacimiento(Date colFechaNacimiento) {
        this.colFechaNacimiento = colFechaNacimiento;
    }

    public BigInteger getColEdad() {
        return colEdad;
    }

    public void setColEdad(BigInteger colEdad) {
        this.colEdad = colEdad;
    }

    public String getColBarrio() {
        return colBarrio;
    }

    public void setColBarrio(String colBarrio) {
        this.colBarrio = colBarrio;
    }

    public String getColTallaZapatos() {
        return colTallaZapatos;
    }

    public void setColTallaZapatos(String colTallaZapatos) {
        this.colTallaZapatos = colTallaZapatos;
    }

    public String getColCamisa() {
        return colCamisa;
    }

    public void setColCamisa(String colCamisa) {
        this.colCamisa = colCamisa;
    }

    public String getColPantalon() {
        return colPantalon;
    }

    public void setColPantalon(String colPantalon) {
        this.colPantalon = colPantalon;
    }

    public BigInteger getColEstatura() {
        return colEstatura;
    }

    public void setColEstatura(BigInteger colEstatura) {
        this.colEstatura = colEstatura;
    }

    public String getColRh() {
        return colRh;
    }

    public void setColRh(String colRh) {
        this.colRh = colRh;
    }

    public String getColRefPersonal() {
        return colRefPersonal;
    }

    public void setColRefPersonal(String colRefPersonal) {
        this.colRefPersonal = colRefPersonal;
    }

    public Date getColFechaVinculacion() {
        return colFechaVinculacion;
    }

    public void setColFechaVinculacion(Date colFechaVinculacion) {
        this.colFechaVinculacion = colFechaVinculacion;
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

    /**
     * @return the rfSexo
     */
    public RfSexo getRfSexo() {
        return rfSexo;
    }

    /**
     * @param rfSexo the rfSexo to set
     */
    public void setRfSexo(RfSexo rfSexo) {
        this.rfSexo = rfSexo;
    }

    /**
     * @return the foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * @return the rfTipocontrato
     */
    public RfTipocontrato getRfTipocontrato() {
        return rfTipocontrato;
    }

    /**
     * @param rfTipocontrato the rfTipocontrato to set
     */
    public void setRfTipocontrato(RfTipocontrato rfTipocontrato) {
        this.rfTipocontrato = rfTipocontrato;
    }

    /**
     * @return the colEmail2
     */
    public String getColEmail2() {
        return colEmail2;
    }

    /**
     * @param colEmail2 the colEmail2 to set
     */
    public void setColEmail2(String colEmail2) {
        this.colEmail2 = colEmail2;
    }

    public RfCiudad getRfCiudad() {
        return rfCiudad;
    }

    public void setRfCiudad(RfCiudad rfCiudad) {
        this.rfCiudad = rfCiudad;
    }

    /**
     * @return the idAntiguedad
     */
    public AdmAntiguedad getIdAntiguedad() {
        return idAntiguedad;
    }

    /**
     * @param idAntiguedad the idAntiguedad to set
     */
    public void setIdAntiguedad(AdmAntiguedad idAntiguedad) {
        this.idAntiguedad = idAntiguedad;
    }

    /**
     * @return the fechaAntiguedad
     */
    public Date getFechaAntiguedad() {
        return fechaAntiguedad;
    }

    /**
     * @param fechaAntiguedad the fechaAntiguedad to set
     */
    public void setFechaAntiguedad(Date fechaAntiguedad) {
        this.fechaAntiguedad = fechaAntiguedad;
    }

    /**
     * @return the colCiudad
     */
    public RfCiudad getColCiudad() {
        return colCiudad;
    }

    /**
     * @param colCiudad the colCiudad to set
     */
    public void setColCiudad(RfCiudad colCiudad) {
        this.colCiudad = colCiudad;
    }

    /**
     * @return the colDepart
     */
    public RfDep getColDepart() {
        return colDepart;
    }

    /**
     * @param colDepart the colDepart to set
     */
    public void setColDepart(RfDep colDepart) {
        this.colDepart = colDepart;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @param colEst the colEst to set
     */
    public void setColEst(Boolean colEst) {
        this.colEst = colEst;
    }

    /**
     * @return the rfBanco
     */
    public RfBanco getRfBanco() {
        return rfBanco;
    }

    /**
     * @param rfBanco the rfBanco to set
     */
    public void setRfBanco(RfBanco rfBanco) {
        this.rfBanco = rfBanco;
    }

    /**
     * @return the nombreDeCuenta
     */
    public String getNombreDeCuenta() {
        return bancoDeCuenta;
    }

    /**
     * @param nombreDeCuenta the nombreDeCuenta to set
     */
    public void setNombreDeCuenta(String nombreDeCuenta) {
        this.bancoDeCuenta = nombreDeCuenta;
    }

    /**
     * @return the documentoTitularCuenta
     */
    public String getDocumentoTitularCuenta() {
        return documentoTitularCuenta;
    }

    /**
     * @param documentoTitularCuenta the documentoTitularCuenta to set
     */
    public void setDocumentoTitularCuenta(String documentoTitularCuenta) {
        this.documentoTitularCuenta = documentoTitularCuenta;
    }

    /**
     * @return the numerodecuenta
     */
    public String getNumerodecuenta() {
        return numerodecuenta;
    }

    /**
     * @param numerodecuenta the numerodecuenta to set
     */
    public void setNumerodecuenta(String numerodecuenta) {
        this.numerodecuenta = numerodecuenta;
    }

    public AdmEstcol getEstadoColaborador() {
        return estadoColaborador;
    }

    public void setEstadoColaborador(AdmEstcol estadoColaborador) {
        this.estadoColaborador = estadoColaborador;
    }

}
