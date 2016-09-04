/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.colaborador;

import adm.sys.dao.AdmCargo;
import adm.sys.dao.AdmColaborador;
import adm.sys.dao.AdmAntiguedad;
import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.AdmEstcol;
import com.pandora.adm.rf.dao.RfDep;
import com.pandora.adm.rf.dao.RfCiudad;
import adm.sys.dao.RfSexo;
import adm.sys.dao.RfTipocontrato;
import adm.sys.dao.RfTipodoc;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.colaborador.ColaboradorSFBean;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntRfTipocliente;
import com.pandora.web.adm.param.TablaAdmColaborador;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.icefaces.ace.component.fileentry.FileEntryStatus;
import utilidades.Seguridad;

/**
 *
 * @author patricia
 */
@Named
@SessionScoped
public class ColaboradorJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    private static final String[] EXTENSION_PERMITIDA = {"jpg", "png", "gif"};
    private static final String EXTENSION_PERMITIDAS = "jpg,png,gif";
    private final String IMAGEN_USUARIO_NO_CARGADO = "usuarioNoRegistrado.png";
    private boolean mostrarCargueArchivo = false;
    @Inject
    PrincipalJSFBean pjsfb;
    ColaboradorSFBean csfb;
    private List<VntRegistroventa> lstVentasXColaborador = new ArrayList<>();
    private List<TablaAdmColaborador> lstTablaColaboradores = new ArrayList<>();
    private AdmColaborador colaborador = new AdmColaborador();
    //colaborador
    private String colSexo = "-1";
    private Integer colEstado = -1;
    private String colDepar = "-1";
    private Long colCiudad = -1L;
     
    private String  colnumerocuenta = null;
    private String colnombrebanco = null;
    private Integer colAntiguedad = -1;
    private Integer colTipoCliente = -1;
    private String coltdcid = "-1";
    private String colcedula = null;
    private String colnombre1 = null;
    private String colnombre2 = null;
    private String colapellido1 = null;
    private String colapellido2 = null;
    private String coldireccion = null;
    private String coltelefono1 = null;
    private String coltelefono2 = null;
    private String celular = null;
    private boolean estado = true;
    private Integer tipoContrato = -1;
    private List<SelectItem> lstTipoContrato = new ArrayList<>();
    private AdmColxemp colXEmpresaUsuario = new AdmColxemp();
    
    public String getCelular() {
        return celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    private String colemail;
    private String coleps;
    private Date colfechanacimiento;
    private Integer coledad;
    private String colbarrio;
    private String coltallazapatos;
    private String colcamisa;
    private String colpantalon;
    private String colrh;
    private String colrefpersonal;
    private Date colfechavinculacion;
    private BigInteger colestatura;
    private String cargxcolaborador = "-1";
    private List<SelectItem> lstTipoDocumento = new ArrayList<>();
    private List<SelectItem> lstCargos = new ArrayList<>();
    private List<SelectItem> lstSexo = new ArrayList<>();
    private List<SelectItem> lstEstado = new ArrayList<>();
    private List<SelectItem> lstDepCol = new ArrayList<>();
    private List<SelectItem> lstCiudCol = new ArrayList<>();
    private List<SelectItem> lstAntiguedad = new ArrayList<>();
    private List<SelectItem> lstTipoCliente = new ArrayList<>();
    private boolean nuevo = true;
    private String foto = "";
    private String rutaFotos = "";
    private String colEmail2;
    private String colUsuario;
    
    public boolean isNuevo() {
        return nuevo;
    }
    
    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    /**
     * @return the lstVentasXColaborador
     */
    public List<VntRegistroventa> getLstVentasXColaborador() {
        return lstVentasXColaborador;
    }

    /**
     * @param lstVentasXColaborador the lstVentasXColaborador to set
     */
    public void setLstVentasXColaborador(List<VntRegistroventa> lstVentasXColaborador) {
        this.lstVentasXColaborador = lstVentasXColaborador;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">

    @Override
    public boolean validarForm() {
        boolean validador = true;
        if (coltdcid == null || coltdcid.equals("-1")) {
            mostrarError("Tipo documento es requerido", 1);
            validador = false;
        }
        if (colcedula == null) {
            
            mostrarError("Tipo documento es requerido", 1);
            validador = false;
        } else {
            colcedula = colcedula.trim();
            if ((colcedula.equals(""))) {
                mostrarError("Documento es requerido", 1);
                validador = false;
            } else if (nuevo) {
                List<AdmColaborador> lst = csfb.getLstAdmColaboradorXCC(colcedula);
                if (lst != null && !lst.isEmpty()) {
                    mostrarError("Documento ya se encuentra en la base de datos", 1);
                    validador = false;
                }
            }
        }
        if (colnombre1 == null) {
            
            mostrarError("Primer Nombre es requerido", 1);
            validador = false;
        } else {
            colnombre1 = colnombre1.trim();
            if ((colnombre1.equals(""))) {
                mostrarError("Primer Nombre es requerido", 1);
                validador = false;
            }
        }
        if (colapellido1 == null) {
            
            mostrarError("Primer Apellido es requerido", 1);
            validador = false;
        } else {
            colapellido1 = colapellido1.trim();
            if ((colapellido1.equals(""))) {
                mostrarError("Primer Apellido es requerido", 1);
                validador = false;
            }
        }
        if (colSexo == null || colSexo.equals("-1")) {
            mostrarError("Sexo es requerido", 1);
            validador = false;
        }
        
        if (colEstado == null || colEstado.equals(-1)) {
            mostrarError("Estado requerido", 1);
            validador = false;
        }
        if (colTipoCliente == null || colTipoCliente.equals(-1)) {
            mostrarError("Tipo cliente que maneja es requerido", 1);
            validador = false;
        }
        
        if (tipoContrato == null || tipoContrato.equals(-1)) {
            mostrarError("Tipo contrato es requerido", 1);
            validador = false;
        }
        
        if (coldireccion == null) {
            
            mostrarError("Dirección es requerido", 1);
            validador = false;
        } else {
            coldireccion = coldireccion.trim();
            if ((coldireccion.equals(""))) {
                mostrarError("Dirección es requerido", 1);
                validador = false;
            }
        }
        if (celular == null) {
            
            mostrarError("Celular es requerido", 1);
            validador = false;
        } else {
            celular = celular.trim();
            if ((celular.equals(""))) {
                mostrarError("Celular es requerido", 1);
                validador = false;
            }
        }
        
        if (colfechanacimiento == null) {
            
            mostrarError("Fecha nacimiento es requerido", 1);
            validador = false;
        } else {
            /* if (this.validarFechaMayorXDia(colfechanacimiento, new Date())) {
             mostrarError("Fecha nacimiento no debe ser mayor que la fecha del sistema", 1);
             validador = false;
             }*/
        }
        
        if (coleps == null) {
            
            mostrarError("EPS es requerido", 1);
            validador = false;
        } else {
            coleps = coleps.trim();
            if (coleps.equals("")) {
                mostrarError("EPS es requerido", 1);
                validador = false;
            }
        }
        if (colrefpersonal == null) {
            
            mostrarError("Referencia Personal es requerido", 1);
            validador = false;
        } else {
            colrefpersonal = colrefpersonal.trim();
            if (colrefpersonal.equals("")) {
                mostrarError("Referencia Personal es requerido", 1);
                validador = false;
            }
        }
        
        if (colbarrio == null) {
            
            mostrarError("Barrio es requerido", 1);
            validador = false;
        } else {
            colbarrio = colbarrio.trim();
            if (colbarrio.equals("")) {
                mostrarError("Barrio es requerido", 1);
                validador = false;
            }
        }
        
        if (coltallazapatos == null) {
            
            mostrarError("Talla Zapatos es requerido", 1);
            validador = false;
        } else {
            coltallazapatos = coltallazapatos.trim();
            if (coltallazapatos.equals("")) {
                mostrarError("Talla Zapatos es requerido", 1);
                validador = false;
            }
        }
        if (colcamisa == null) {
            
            mostrarError("Talla Camisa es requerido", 1);
            validador = false;
        } else {
            colcamisa = colcamisa.trim();
            if (colcamisa.equals("")) {
                mostrarError("Talla Camisa es requerido", 1);
                validador = false;
            }
        }
        if (colpantalon == null) {
            
            mostrarError("Talla Pantalon es requerido", 1);
            validador = false;
        } else {
            colpantalon = colpantalon.trim();
            if (colpantalon.equals("")) {
                mostrarError("Talla Pantalon es requerido", 1);
                validador = false;
            }
        }
        if (colestatura == null) {
            
            mostrarError("Estatura es requerido", 1);
            validador = false;
        }
        
        if (colrh == null) {
            
            mostrarError("RH es requerido", 1);
            validador = false;
        } else {
            colrh = colrh.trim();
            if (colrh.equals("")) {
                mostrarError("RH es requerido", 1);
                validador = false;
            }
        }
        if (colemail == null) {
            
            mostrarError("Email es requerido", 1);
            validador = false;
        } else {
            colemail = colemail.trim();
            if (colemail.equals("")) {
                mostrarError("Email es requerido", 1);
                validador = false;
            }
        }
        if (cargxcolaborador == null || cargxcolaborador.equals("-1")) {
            mostrarError("Cargo es requerido", 1);
            validador = false;
        }
        
        if (colAntiguedad == null || colAntiguedad.equals("-1")) {
            mostrarError(" Definir la antiguedad en la Empresa", 1);
            validador = false;
        }
        
        if (colfechavinculacion == null) {
            mostrarError("Fecha vinculación es requerido", 1);
            validador = false;
        } else {
            /* if (this.validarFechaMayorXDia(colfechavinculacion, new Date())) {
             mostrarError("Fecha vinculación no debe ser mayor que la fecha del sistema", 1);
             validador = false;
             }*/
        }
        if (colXEmpresaUsuario == null) {
            colXEmpresaUsuario = new AdmColxemp();
        }
        if (colUsuario == null) {
            
            mostrarError("Usuario es requerido", 1);
            validador = false;
        } else {
            colUsuario = colUsuario.trim();
            if (colUsuario.equals("")) {
                mostrarError("Usuario es requerido", 1);
                validador = false;
            } else if (colXEmpresaUsuario.getCpeId() == null) {
                if (colUsuario.length() < 8 || colUsuario.length() > 50) {
                    mostrarError("Usuario debe tener mínimo ocho caracteres máximo 50 entre (Letras,Números y caracteres especiales)", 1);
                    validador = false;
                } else {
                    List<AdmColxemp> lst = csfb.validarUsuarioRepetido(colUsuario);
                    if (lst != null && !lst.isEmpty()) {
                        mostrarError("Usuario ya existe en nuestra base de datos", 1);
                        validador = false;
                    }
                    //falta validar que el usuario no exista 
                }
                
            }
        }
        
        return validador;
    }
    
    private void cargarFichero(String fotos) {
        try {
            fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            String ruta = ec.getRealPath("/resources/images/users/");
            
            File file = new File(ruta + File.separator + foto);
            File fileOrig = new File(fotos);
            if (!fileOrig.exists()) {
                fileOrig = new File(ruta + File.separator + IMAGEN_USUARIO_NO_CARGADO);
                
            }
            copiarArchivo(fileOrig, file);
        } catch (IOException ex) {
            Logger.getLogger(ColaboradorJSFBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //Referencia Bean Consultas
    protected ColaboradorSFBean lookupColaboradorSFBean() {
        try {
            Context c = new InitialContext();
            return (ColaboradorSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/ColaboradorSFBean!com.pandora.mod.colaborador.ColaboradorSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private void cargarListaColaboradores() {
        lstTablaColaboradores.clear();
        
        for (AdmColaborador c : csfb.getLstColaboradores()) {
            TablaAdmColaborador tc = new TablaAdmColaborador();
            tc.setAdmColaborador(c);
            lstTablaColaboradores.add(tc);
        }
    }
    
    public void selDepartamento_ValueChangeEvent(ValueChangeEvent vce) {
        
         colDepar = (String) vce.getNewValue();
        cargarListaCiuCol(colDepar);
        
    }
    
    private void limpiarCamposColaborador() {
        coltdcid = "-1";
        colcedula = null;
        colnombre1 = null;
        colnombrebanco = null;
        colnumerocuenta = null;
        colnombre2 = null;
        colapellido1 = null;
        colapellido2 = null;
        
        coldireccion = null;
        coltelefono1 = null;
        coltelefono2 = null;
        celular = null;
        colemail = null;
        coleps = null;
        colfechanacimiento = null;
        coledad = null;
        colbarrio = null;
        coltallazapatos = null;
        colcamisa = null;
        colpantalon = null;
        colrh = null;
        colrefpersonal = null;
        colfechavinculacion = null;
        colestatura = null;
        colSexo = "-1";
        colDepar = "-1";
        colCiudad = -1L;
        colEstado = -1;
        colAntiguedad = -1;
        colTipoCliente = -1;
        colaborador = new AdmColaborador();
        cargxcolaborador = "-1";
        foto = "";
        mostrarCargueArchivo = false;
        estado = true;
        tipoContrato = -1;
        colXEmpresaUsuario = new AdmColxemp();
        colUsuario = null;
         colCiudad =-1L;
         colDepar= "-1";
        
    }
    
    private void cargarVentasXColaborador() {
        lstVentasXColaborador.clear();
        if (colaborador != null && colaborador.getColCedula() != null && !colaborador.getColCedula().isEmpty()) {
            if (colXEmpresaUsuario == null || colXEmpresaUsuario.getCpeId() == null) {
                List<AdmColxemp> lstcolemp = csfb.getLstAdmColxempXColaboradorXEmpresa(colaborador.getColCedula(), getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getEmpId().getEmpId());
                if (!(lstcolemp == null || lstcolemp.isEmpty())) {
                    colXEmpresaUsuario = lstcolemp.get(0);
                    colUsuario = colXEmpresaUsuario.getCpeUsuario();
                } else {
                    colXEmpresaUsuario = new AdmColxemp();
                }
            }
            if (colXEmpresaUsuario != null && colXEmpresaUsuario.getCpeId() != null) {
                List<AdmCrgxcol> lsd = csfb.getLstAdmCrgxcolXColaboradorXEmpresa(colXEmpresaUsuario.getCpeId());
                if (!(lsd == null || lsd.isEmpty())) {
                    AdmCrgxcol cargocol = lsd.get(0);
                    
                    for (VntRegistroventa rt : csfb.getLstVntRegistroventaXColaborador(cargocol.getCxcId())) {
                        lstVentasXColaborador.add(rt);
                    }
                }
            }
            
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Metodos del Bean">

    @Override
    public void init() {
        csfb = lookupColaboradorSFBean();
        numPanel = 1;
        nuevo = true;
        cargarListaColaboradores();
        cargarListaTipoDoc();
        cargarListaCargos();
        cargarListaSexo();
        cargarListaTipoCliente();
        limpiarCamposColaborador();
        cargarListaEstados();
        cargarListaAntiguedad();
        cargarListaDepartCol();
       
        cargarListaCiuCol(null);
         colCiudad =-1L;
        rutaFotos = getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getEmpId().getEmpRutaspdfscorreo()
                + File.separator + "colaboradores" + File.separator;
        try {
            File file = new File(rutaFotos);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
        }
        
        lstVentasXColaborador.clear();
        estado = true;
        tipoContrato = -1;
        lstTipoContrato.clear();
        cargarListaTipoContrato();
        colXEmpresaUsuario = new AdmColxemp();
        colUsuario = null;
    }
    
    private void cargarListaTipoContrato() {
        lstTipoContrato.clear();
        lstTipoContrato.add(itemSeleccioneInt);
        for (RfTipocontrato rt : csfb.getLstRfTipocontrato(true)) {
            lstTipoContrato.add(new SelectItem(rt.getTctId(), rt.getTctDesc()));
        }
    }
    
    private void cargarListaTipoDoc() {
        lstTipoDocumento.clear();
        lstTipoDocumento.add(itemSeleccione);
        for (RfTipodoc rt : csfb.getLstRfTipodoc(true)) {
            lstTipoDocumento.add(new SelectItem(rt.getTdcId(), rt.getTdcNombre()));
        }
    }
    
    private void cargarListaCargos() {
        lstCargos.clear();
        lstCargos.add(itemSeleccione);
        for (AdmCargo rt : csfb.getLstAdmCargo(true)) {
            lstCargos.add(new SelectItem(rt.getCrgId(), rt.getCrgNombre()));
        }
    }
    
    private void cargarListaSexo() {
        lstSexo.clear();
        lstSexo.add(itemSeleccione);
        for (RfSexo rt : csfb.getLstRfSexo()) {
            lstSexo.add(new SelectItem(rt.getSexId(), rt.getSexDesc()));
        }
    }
    
    private void cargarListaAntiguedad() {
        lstAntiguedad.clear();
        lstAntiguedad.add(itemSeleccione);
        for (AdmAntiguedad ec : csfb.getLstAntigu()) {
            getLstAntiguedad().add(new SelectItem(ec.getIdAntiguedad(), ec.getDescAntiguedad()));
        }
    }
    
    private void cargarListaEstados() {
        lstEstado.clear();
        lstEstado.add(itemSeleccione);
        for (AdmEstcol ec : csfb.getLstEstcols()) {
            getLstEstado().add(new SelectItem(ec.getIdEstadocolaborador(), ec.getDescEstadocolaborador()));
        }
    }
    
    private void cargarListaDepartCol() {
        lstDepCol.clear();
        lstDepCol.add(itemSeleccione);
        for (RfDep ec : csfb.getLstDepartCol()) {
            getLstDepCol().add(new SelectItem(ec.getDepId(), ec.getDepDesc()));
        }
    }
    
    private void cargarListaCiuCol(String dptoSel) {
        colCiudad =-1L;
        lstCiudCol.clear();
        lstCiudCol.add(itemSeleccioneLong);
        if (!(dptoSel == null || dptoSel.isEmpty() || dptoSel.equals("-1"))) {
            for (RfCiudad ec : csfb.getLstCiudadCol(dptoSel)) {
                getLstCiudCol().add(new SelectItem(ec.getCiuId(), ec.getCiuDesc()));
            }
        }
    }
    
    private void cargarListaTipoCliente() {
        lstTipoCliente.clear();
        lstTipoCliente.add(itemSeleccioneInt);
        for (VntRfTipocliente vtc : csfb.getLstVntRfTipocliente(true)) {
            lstTipoCliente.add(new SelectItem(vtc.getTclId(), vtc.getTclNombre()));
        }
    }
    
    @Override
    public void limpiarVariables() {
        lstTablaColaboradores.clear();
        limpiarCamposColaborador();
        lstVentasXColaborador.clear();
        csfb.remove();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">
    public AdmColaborador getColaborador() {
        return colaborador;
    }
    
    public void setColaborador(AdmColaborador colaborador) {
        this.colaborador = colaborador;
    }

    /**
     * @return the lstSexo
     */
    public List<SelectItem> getLstSexo() {
        return lstSexo;
    }

    /**
     * @param lstSexo the lstSexo to set
     */
    public void setLstSexo(List<SelectItem> lstSexo) {
        this.lstSexo = lstSexo;
    }

    /**
     * @return the lstTipoCliente
     */
    public List<SelectItem> getLstTipoCliente() {
        return lstTipoCliente;
    }

    /**
     * @param lstTipoCliente the lstTipoCliente to set
     */
    public void setLstTipoCliente(List<SelectItem> lstTipoCliente) {
        this.lstTipoCliente = lstTipoCliente;
    }

    /**
     * @return the coltdoc
     */
    public String getColSexo() {
        return colSexo;
    }

    /**
     * @param coltdoc the coltdoc to set
     */
    public void setColSexo(String colSexo) {
        this.colSexo = colSexo;
    }

    /**
     * @return the colTipoCliente
     */
    public Integer getColTipoCliente() {
        return colTipoCliente;
    }

    /**
     * @param colTipoCliente the colTipoCliente to set
     */
    public void setColTipoCliente(Integer colTipoCliente) {
        this.colTipoCliente = colTipoCliente;
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
     * @return the mostrarCargueArchivo
     */
    public boolean isMostrarCargueArchivo() {
        return mostrarCargueArchivo;
    }

    /**
     * @param mostrarCargueArchivo the mostrarCargueArchivo to set
     */
    public void setMostrarCargueArchivo(boolean mostrarCargueArchivo) {
        this.mostrarCargueArchivo = mostrarCargueArchivo;
    }
    
    public String getColrefpersonal() {
        return this.colrefpersonal;
        
    }
    
    public void setColrefpersonal(String colrefpersonal) {
        this.colrefpersonal = colrefpersonal;
    }
    
    public String getColtdcid() {
        return this.coltdcid;
        
    }
    
    public void setColtdcid(String coltdcid) {
        this.coltdcid = coltdcid;
    }
    
    public String getColcedula() {
        return this.colcedula;
    }
    
    public void setColcedula(String colcedula) {
        this.colcedula = colcedula;
    }
    
    public String getColnombre1() {
        return this.colnombre1;
    }
    
    public void setColnombre1(String colnombre1) {
        this.colnombre1 = colnombre1;
    }
    
    public String getColnombre2() {
        return this.colnombre2;
    }
    
    public void setColnombre2(String colnombre2) {
        this.colnombre2 = colnombre2;
    }
    
    public String getColapellido1() {
        return this.colapellido1;
    }
    
    public void setColapellido1(String colapellido1) {
        this.colapellido1 = colapellido1;
    }
    
    public String getColapellido2() {
        return this.colapellido2;
    }
    
    public void setColapellido2(String colapellido2) {
        this.colapellido2 = colapellido2;
    }
    
    public String getColdireccion() {
        return this.coldireccion;
    }
    
    public void setColdireccion(String coldireccion) {
        this.coldireccion = coldireccion;
    }
    
    public String getColtelefono1() {
        return this.coltelefono1;
    }
    
    public void setColtelefono1(String coltelefono1) {
        this.coltelefono1 = coltelefono1;
    }
    
    public String getColtelefono2() {
        return this.coltelefono2;
    }
    
    public void setColtelefono2(String coltelefono2) {
        this.coltelefono2 = coltelefono2;
    }
    
    public String getColemail() {
        return colemail;
    }
    
    public void setColemail(String colemail) {
        this.colemail = colemail;
    }
    
    public String getColeps() {
        return coleps;
    }
    
    public void setColeps(String coleps) {
        this.coleps = coleps;
    }
    
    public int getColEdad() {
        return this.coledad;
    }
    
    public void setColEdad(int coledad) {
        this.coledad = coledad;
    }
    
    public String getColbarrio() {
        return colbarrio;
    }
    
    public void setColbarrio(String colbarrio) {
        this.colbarrio = colbarrio;
    }
    
    public String getColtallazapatos() {
        return coltallazapatos;
    }
    
    public void setColtallazapatos(String coltallazapatos) {
        this.coltallazapatos = coltallazapatos;
    }
    
    public String getColcamisa() {
        return colcamisa;
    }
    
    public void setColcamisa(String colcamisa) {
        this.colcamisa = colcamisa;
    }
    
    public String getColpantalon() {
        return colpantalon;
    }
    
    public void setColpantalon(String colpantalon) {
        this.colpantalon = colpantalon;
    }
    
    public String getColrh() {
        return colrh;
    }
    
    public void setColrh(String colrh) {
        this.colrh = colrh;
    }
    
    public Date getColfechavinculacion() {
        return colfechavinculacion;
    }
    
    public void setColfechavinculacion(Date colfechavinculacion) {
        this.colfechavinculacion = colfechavinculacion;
    }
    
    public BigInteger getColestatura() {
        return colestatura;
    }
    
    public void setColestatura(BigInteger colestatura) {
        this.colestatura = colestatura;
    }
    
    public String getCargxcolaborador() {
        return cargxcolaborador;
    }
    
    public void setCargxcolaborador(String cargxcolaborador) {
        this.cargxcolaborador = cargxcolaborador;
    }
    
    public AdmColaborador getcolaborador() {
        return colaborador;
    }
    
    public void setcolaborador(AdmColaborador colaboradores) {
        this.colaborador = colaboradores;
    }

    /**
     * @return the lstTablaColaboradores
     */
    public List<TablaAdmColaborador> getLstTablaColaboradores() {
        return lstTablaColaboradores;
    }

    /**
     * @param lstTablaColaboradores the lstTablaColaboradores to set
     */
    public void setLstTablaColaboradores(List<TablaAdmColaborador> lstTablaColaboradores) {
        this.lstTablaColaboradores = lstTablaColaboradores;
    }

    /**
     * @return the lstTipoDocumento
     */
    public List<SelectItem> getLstTipoDocumento() {
        return lstTipoDocumento;
    }

    /**
     * @param lstTipoDocumento the lstTipoDocumento to set
     */
    public void setLstTipoDocumento(List<SelectItem> lstTipoDocumento) {
        this.lstTipoDocumento = lstTipoDocumento;
    }

    /**
     * @return the lstCargos
     */
    public List<SelectItem> getLstCargos() {
        return lstCargos;
    }

    /**
     * @param lstCargos the lstCargos to set
     */
    public void setLstCargos(List<SelectItem> lstCargos) {
        this.lstCargos = lstCargos;
    }

    /**
     * @return the colfechanacimiento
     */
    public Date getColfechanacimiento() {
        return colfechanacimiento;
    }

    /**
     * @param colfechanacimiento the colfechanacimiento to set
     */
    public void setColfechanacimiento(Date colfechanacimiento) {
        this.colfechanacimiento = colfechanacimiento;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    public void cargarListaEventosXColaborador_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        TablaAdmColaborador tabla = (TablaAdmColaborador) map.get("itemcolaborador");
        colaborador = tabla.getAdmColaborador();
        cargarVentasXColaborador();
        
    }
    
    public void cargarListaEventosXColaboradorSeleccionado_ActionEvent(ActionEvent ae) {
        numPanel = 3;
        cargarVentasXColaborador();
        
    }
    
    public void fileEntryAction(FileEntryEvent event) {
        if (colcedula == null || colcedula.equals("")) {
            mostrarError("Antes de cargar el archivo se debe digitar la cedula ", 1);
            return;
        }
        FileEntry fileEntry = (FileEntry) event.getSource();
        FileEntryResults results = fileEntry.getResults();
        if (results.getFiles().size() > 1) {
            throw new IllegalStateException("Multiple files upload not supported");
        }
        FileEntryResults.FileInfo fileInfo = results.getFiles().get(0);
        if (fileInfo.isSaved()) {
            String fileName = fileInfo.getFile().getName();
            int i = fileName.lastIndexOf('.');
            String extension = null;
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }
            if (extension == null || extension.equals("")) {
                extension = "png";
            } else {
                boolean extendEncontrada = false;
                for (String ext : EXTENSION_PERMITIDA) {
                    if (extension.equalsIgnoreCase(ext)) {
                        extendEncontrada = true;
                        break;
                    }
                }
                if (!extendEncontrada) {
                    mostrarError("Solo se permiten imagenes de tipo : " + EXTENSION_PERMITIDAS, 1);
                    return;
                }
            }
            
            try {
                fc = FacesContext.getCurrentInstance();
                ExternalContext ec = fc.getExternalContext();
                String ruta = ec.getRealPath("/resources/images/users/");
                foto = colcedula + "." + extension;
                File file = new File(ruta + File.separator + foto);
                
                copiarArchivo(fileInfo.getFile(), file);
                File fileFinal = new File(rutaFotos + File.separator + foto);
                copiarArchivo(file, fileFinal);
            } catch (IOException ex) {
                Logger.getLogger(ColaboradorJSFBean.class.getName()).log(Level.SEVERE, null, ex);
                mostrarError("No se pudo cargar el archivo ", 1);
            }
        } else {
            //  FileEntryStatus status = fileInfo.getStatus();
            mostrarError("Archivo no ha sido cargado ", 1);
            
        }
        
    }
    
    public void guardar_ActionEvent(ActionEvent ae) {
        if (validarForm()) {
            try {
                
                colaborador.setTdcId(csfb.getRfTipodocXId(coltdcid));
                colaborador.setColCedula(colcedula);
                colaborador.setColNombre1(colnombre1);
                colaborador.setNumerodecuenta(colnumerocuenta);
                colaborador.setNombreDeCuenta(colnombrebanco);
                
                colaborador.setColNombre2(colnombre2);
                colaborador.setColApellido1(colapellido1);
                colaborador.setColApellido2(colapellido2);
                
                colaborador.setColDireccion(coldireccion);
                colaborador.setColTelefono1(coltelefono1);
                colaborador.setColTelefono2(coltelefono2);
                colaborador.setColCelular(celular);
                colaborador.setColEmail(colemail);
                colaborador.setColEmail2(colEmail2);
                colaborador.setColEps(coleps);
                colaborador.setColFechaNacimiento(colfechanacimiento);
                //calcular edad
                coledad = 10;
                // colaborador.setColEdad(coledad);
                colaborador.setColBarrio(colbarrio);
                colaborador.setColTallaZapatos(coltallazapatos);
                colaborador.setColCamisa(colcamisa);
                colaborador.setColPantalon(colpantalon);
                colaborador.setColRh(colrh);
                colaborador.setColRefPersonal(colrefpersonal);
                colaborador.setColFechaVinculacion(colfechavinculacion);
                colaborador.setColEstatura(colestatura);
                colaborador.setRfSexo(csfb.getRfSexo(colSexo));
                colaborador.setColDepart(colDepar == null || colDepar.isEmpty() || colDepar.equals("-1") ? null : csfb.getRfDepartamento(colDepar));
                
                colaborador.setEstadoColaborador(csfb.getAdmEstcol(colEstado));
                colaborador.setColCiudad(colCiudad == null || colCiudad.equals(-1L) ? null :  csfb.getRfCiudad(colCiudad));
                colaborador.setIdAntiguedad(csfb.getAdmAntiguedad(colAntiguedad));
                
                colaborador.setVntRfTipocliente(csfb.getVntRfTipoclienteXId(colTipoCliente));
                
                colaborador.setRfTipocontrato(csfb.getRfTipocontratoXId(tipoContrato));
                colaborador.setColEst(estado);
                colaborador.setFoto(foto);
                colaborador = csfb.editar(colaborador);
                AdmCargo cargo = csfb.getAdmCargoXId(cargxcolaborador);
                String mensajeUsrNuevo = null;
                
                if (colXEmpresaUsuario.getCpeId() == null) {
                    
                    List<AdmColxemp> lstcolemp = csfb.getLstAdmColxempXColaboradorXEmpresa(colcedula, getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getEmpId().getEmpId());
                    
                    if (lstcolemp == null || lstcolemp.isEmpty()) {
                        //colXEmpresaUsuario = new AdmColxemp();
                        colXEmpresaUsuario.setColCedula(colaborador);
                        colXEmpresaUsuario.setEmpId(getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getEmpId());
                        colXEmpresaUsuario.setCpeUsuario(colUsuario);
                        colXEmpresaUsuario.setCpeClave(Seguridad.hashPasswordSha512(colcedula));
                        colXEmpresaUsuario.setCpeEstcop(true);
                        colXEmpresaUsuario.setCpeFcre(new Date());
                        colXEmpresaUsuario.setCpeTel(coltelefono1);
                        colXEmpresaUsuario.setCpeEmail(colemail);
                        colXEmpresaUsuario.setIndversion(0);
                        colXEmpresaUsuario = csfb.editarAdmColxemp(colXEmpresaUsuario);
                        mensajeUsrNuevo = "La Contraseña para el usuario : " + colUsuario + " el su número de identificación, se recomienda cambiarlo.";
                    } else {
                        colXEmpresaUsuario = lstcolemp.get(0);
                    }
                }
                
                List<AdmCrgxcol> lsd = csfb.getLstAdmCrgxcolXColaboradorXEmpresa(colXEmpresaUsuario.getCpeId());
                AdmCrgxcol cargocol = null;
                if (lsd == null || lsd.isEmpty()) {
                    cargocol = new AdmCrgxcol();
                    cargocol.setCpeId(colXEmpresaUsuario);
                    cargocol.setCxcFcre(new Date());
                    cargocol.setCrgId(cargo);
                    cargocol.setIndversion(0);
                } else {
                    cargocol = lsd.get(0);
                    cargocol.setCrgId(cargo);
                    
                }
                cargocol.setCxcEst(true);
                cargocol = csfb.editarAdmCrgxcol(cargocol);
                //falta incluir el cargo
                mostrarError("Datos Guardados exitosamente", 3);
                if (mensajeUsrNuevo != null) {
                    mostrarError(mensajeUsrNuevo, 3);
                }
                nuevo = false;
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
                mostrarError("Ocurrio un error al tratar de grabar el colaborador", 1);
            }
        }
    }
    
    public void rowDtDetalleColaborador_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        TablaAdmColaborador tabla = (TablaAdmColaborador) map.get("itemcolaborador");
        
        limpiarCamposColaborador();
        colaborador = tabla.getAdmColaborador();
        coltdcid = colaborador.getTdcId().getTdcId();
        colcedula = colaborador.getColCedula();
        colnumerocuenta = colaborador.getNumerodecuenta();
        colnombrebanco = colaborador.getNombreDeCuenta();
        colnombre1 = colaborador.getColNombre1();
        colnombre2 = colaborador.getColNombre2();
        colapellido1 = colaborador.getColApellido1();
        colapellido2 = colaborador.getColApellido2();
        
        coldireccion = colaborador.getColDireccion();
        coltelefono1 = colaborador.getColTelefono1();
        coltelefono2 = colaborador.getColTelefono2();
        celular = colaborador.getColCelular();
        colemail = colaborador.getColEmail();
        coleps = colaborador.getColEps();
        colfechanacimiento = colaborador.getColFechaNacimiento();
        coledad = null;
        colbarrio = colaborador.getColBarrio();
        coltallazapatos = colaborador.getColTallaZapatos();
        colcamisa = colaborador.getColCamisa();
        colpantalon = colaborador.getColPantalon();
        colrh = colaborador.getColRh();
        colrefpersonal = colaborador.getColRefPersonal();
        colfechavinculacion = colaborador.getColFechaVinculacion();
        colestatura = colaborador.getColEstatura();
        colSexo = (colaborador.getRfSexo() == null ? "-1" : colaborador.getRfSexo().getSexId());
        colDepar = (colaborador.getColDepart() == null ? "-1" : colaborador.getColDepart().getDepId());
        cargarListaCiuCol(colDepar);
        colEstado = (colaborador.getEstadoColaborador() == null ? -1 : colaborador.getEstadoColaborador().getIdEstadocolaborador());
        colCiudad = (colaborador.getColCiudad() == null ? -1 : colaborador.getColCiudad().getCiuId());
        colAntiguedad = (colaborador.getIdAntiguedad() == null ? -1 : colaborador.getIdAntiguedad().getIdAntiguedad());
        colTipoCliente = (colaborador.getVntRfTipocliente() == null ? -1 : colaborador.getVntRfTipocliente().getTclId());
        
        estado = colaborador.getColEst();
        tipoContrato = (colaborador.getRfTipocontrato() == null ? -1 : colaborador.getRfTipocontrato().getTctId());
        numPanel = 2;
        nuevo = false;
        foto
                = //rutaFotos + File.separator + 
                (colaborador.getFoto() == null ? colaborador.getColCedula() + ".png" : colaborador.getFoto());
        cargarFichero(rutaFotos + File.separator + foto);
        //foto = (colaborador.getFoto() == null ? colaborador.getColCedula() + ".png" : colaborador.getFoto());

        List<AdmColxemp> lstcolemp = csfb.getLstAdmColxempXColaboradorXEmpresa(colcedula, getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getEmpId().getEmpId());
        
        if (!(lstcolemp == null || lstcolemp.isEmpty())) {
            colXEmpresaUsuario = lstcolemp.get(0);
            
            List<AdmCrgxcol> lsd = csfb.getLstAdmCrgxcolXColaboradorXEmpresa(colXEmpresaUsuario.getCpeId());
            if (!(lsd == null || lsd.isEmpty())) {
                AdmCrgxcol cargocol = lsd.get(0);
                cargxcolaborador = cargocol.getCrgId().getCrgId();
            }
        } else {
            colXEmpresaUsuario = new AdmColxemp();
        }
        colUsuario = colXEmpresaUsuario.getCpeUsuario();
        
    }
    
    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        switch (numPanel) {
            case 1:
                cargarListaColaboradores();
                break;
            case 2:
                limpiarCamposColaborador();
                foto = IMAGEN_USUARIO_NO_CARGADO;
                mostrarCargueArchivo = true;
                nuevo = true;
                break;
        }
    }
    
    public void regresar_ActionEvent(ActionEvent ae) {
        switch (numPanel) {
            case 1:
                break;
            case 2:
                numPanel = 1;
                cargarListaColaboradores();
                limpiarCamposColaborador();
                nuevo = true;
                break;
            case 3:
                numPanel = 2;
                break;
        }
        
    }
    
    @Override
    public boolean grabarPaso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //</editor-fold>

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoContrato
     */
    public Integer getTipoContrato() {
        return tipoContrato;
    }

    /**
     * @param tipoContrato the tipoContrato to set
     */
    public void setTipoContrato(Integer tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    /**
     * @return the lstTipoContrato
     */
    public List<SelectItem> getLstTipoContrato() {
        return lstTipoContrato;
    }

    /**
     * @param lstTipoContrato the lstTipoContrato to set
     */
    public void setLstTipoContrato(List<SelectItem> lstTipoContrato) {
        this.lstTipoContrato = lstTipoContrato;
    }

    /**
     * @return the colXEmpresaUsuario
     */
    public AdmColxemp getColXEmpresaUsuario() {
        return colXEmpresaUsuario;
    }

    /**
     * @param colXEmpresaUsuario the colXEmpresaUsuario to set
     */
    public void setColXEmpresaUsuario(AdmColxemp colXEmpresaUsuario) {
        this.colXEmpresaUsuario = colXEmpresaUsuario;
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

    /**
     * @return the colUsuario
     */
    public String getColUsuario() {
        return colUsuario;
    }

    /**
     * @param colUsuario the colUsuario to set
     */
    public void setColUsuario(String colUsuario) {
        this.colUsuario = colUsuario;
    }

    /**
     * @return the lstEstado
     */
    public List<SelectItem> getLstEstado() {
        return lstEstado;
    }

    /**
     * @param lstEstado the lstEstado to set
     */
    public void setLstEstado(List<SelectItem> lstEstado) {
        this.lstEstado = lstEstado;
    }

    /**
     * @return the colEstado
     */
    public Integer getColEstado() {
        return colEstado;
    }

    /**
     * @param colEstado the colEstado to set
     */
    public void setColEstado(Integer colEstado) {
        this.colEstado = colEstado;
    }

    /**
     * @return the lstAntiguedad
     */
    public List<SelectItem> getLstAntiguedad() {
        return lstAntiguedad;
    }

    /**
     * @param lstAntiguedad the lstAntiguedad to set
     */
    public void setLstAntiguedad(List<SelectItem> lstAntiguedad) {
        this.lstAntiguedad = lstAntiguedad;
    }

    /**
     * @return the colAntiguedad
     */
    public Integer getColAntiguedad() {
        return colAntiguedad;
    }

    /**
     * @param colAntiguedad the colAntiguedad to set
     */
    public void setColAntiguedad(Integer colAntiguedad) {
        this.colAntiguedad = colAntiguedad;
    }

    /**
     * @return the lstDepCol
     */
    public List<SelectItem> getLstDepCol() {
        return lstDepCol;
    }

    /**
     * @param lstDepCol the lstDepCol to set
     */
    public void setLstDepCol(List<SelectItem> lstDepCol) {
        this.lstDepCol = lstDepCol;
    }

    /**
     * @return the lstCiudCol
     */
    public List<SelectItem> getLstCiudCol() {
        return lstCiudCol;
    }

    /**
     * @param lstCiudCol the lstCiudCol to set
     */
    public void setLstCiudCol(List<SelectItem> lstCiudCol) {
        this.lstCiudCol = lstCiudCol;
    }

    /**
     * @return the colDepar
     */
    public String getColDepar() {
        return colDepar;
    }

    /**
     * @param colDepar the colDepar to set
     */
    public void setColDepar(String colDepar) {
        this.colDepar = colDepar;
    }

    /**
     * @return the colCiudad
     */
    public Long getColCiudad() {
        return colCiudad;
    }

    /**
     * @param colCiudad the colCiudad to set
     */
    public void setColCiudad(Long colCiudad) {
        this.colCiudad = colCiudad;
    }

    /**
     * @return the colnumerocuenta
     */
    public String getColnumerocuenta() {
        return colnumerocuenta;
    }

    /**
     * @param colnumerocuenta the colnumerocuenta to set
     */
    public void setColnumerocuenta(String colnumerocuenta) {
        this.colnumerocuenta = colnumerocuenta;
    }

    /**
     * @return the colnombrebanco
     */
    public String getColnombrebanco() {
        return colnombrebanco;
    }

    /**
     * @param colnombrebanco the colnombrebanco to set
     */
    public void setColnombrebanco(String colnombrebanco) {
        this.colnombrebanco = colnombrebanco;
    }
}
