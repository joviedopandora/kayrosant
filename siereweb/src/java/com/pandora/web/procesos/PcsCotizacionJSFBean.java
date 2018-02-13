/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procesos;

import adm.sys.dao.AdmInforme;
import adm.sys.dao.RfParentezco;
import adm.sys.dao.RfSexo;
import adm.sys.dao.RfTipodoc;
import com.icesoft.faces.context.effects.JavascriptContext;
import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.adm.rf.dao.RfMotivoevento;
import com.pandora.bussiness.util.EnEstadosVentaOp;
import com.pandora.consulta.bean.PcsCotizacionSFBean;
import com.pandora.consulta.bean.PcsOrdenProduccionSFBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.ordenprod.CronogramaSLBean;
import com.pandora.mod.venta.dao.RfCargocontacto;
import com.pandora.mod.venta.dao.RfComocontacto;
import com.pandora.mod.venta.dao.ServRfTipocliente;
import com.pandora.mod.venta.dao.VntActeconomica;
import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntColxventa;
import com.pandora.mod.venta.dao.VntCronograma;
import com.pandora.mod.venta.dao.VntDetallecliente;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.mod.venta.dao.VntEstventa;
import com.pandora.mod.venta.dao.VntRangoedadsexo;
import com.pandora.mod.venta.dao.VntRangoedadsexxservicio;
import com.pandora.mod.venta.dao.VntRegistroLlamada;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntRfTipocliente;
import com.pandora.mod.venta.dao.VntServicio;
import com.pandora.mod.venta.dao.VntServxventa;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.venta.TablaVntCliente;
import com.pandora.web.venta.TablaVntDetalleCliente;
import com.pandora.web.venta.TablaVntServicio;
import com.pandora.web.venta.TablaVntSrvXVenta;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import utilidades.Constantes;
import utilidades.EnInforme;
import utilidades.EnTipoCliente;
import utilidades.ManejoFecha;

/**
 *
 * @author sandra
 */
@Named
@SessionScoped
public class PcsCotizacionJSFBean extends BaseJSFBean implements Serializable, IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    private PrincipalJSFBean pjsfb;
    @EJB
    private PcsCotizacionSFBean pcsfb;
    @EJB
    private CronogramaSLBean cronogramaSLBean;
    @EJB
    private PcsOrdenProduccionSFBean pcsOrdenProduccionSFBean;

    private boolean blnVisibleEdaYSexo = false;
    private HashMap<Long, TablaVntServicio> mapaServciosSel = new HashMap<>();
    private BigDecimal valorTotalVenta = null;
    private BigDecimal valoDescVenta = null;
    private BigDecimal valSubtotal = null;
    private Integer cantidadServiciosSeleccionados = 0;

    private PcsCotizacionSFBean lookupPcsCotizacionSFBean() {
        try {
            Context c = new InitialContext();
            return (PcsCotizacionSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/PcsCotizacionSFBean!com.pandora.consulta.bean.PcsCotizacionSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private static final int PANEL_BUSCAR_CLIENTE = 1;
    private static final int PANEL_CLIENTE = 2;
    private static final int PANEL_EVENTO = 3;
    private static final int PANEL_SERVICIO_VENTA = 4;
    private static final int PANEL_LLAMADA = 5;
    private boolean blnMostrarPanel;
    private boolean blnNuevo;
    private String strBuscarCln;
    //Cliente
    private VntCliente vntCliente = new VntCliente();
    private List<TablaVntCliente> lstTablaVntCliente = new ArrayList<>();
    private TablaVntCliente tablaVntClienteSel = new TablaVntCliente();
    private List<SelectItem> lstTipoDocumento = new ArrayList<>();
    private List<SelectItem> lstSexo = new ArrayList<>();
    private List<SelectItem> lstTipoCliente = new ArrayList<>();
    private List<SelectItem> lstActividadEconomica = new ArrayList<>();
    private String idTipoDoc;
    private String strCltDocumento;
    private String strCltNombres;
    private String idSexo;
    private Integer intCltNumHijos;
    private Date datCltFechaNac;

    private String strCltDireccion;
    private String dcln_direccion;
    private String strCltTelefono;
    private String strCltCelular;
    private String strCltCorreo;
    private String strCltContacto;
    private Integer idTipoCliente;
    private Integer idActEconomica;
    private boolean esNit = false;
    private String strCltAlias;
    //Detalle cliente
    private VntDetallecliente vntDetallecliente = new VntDetallecliente();
    private List<TablaVntDetalleCliente> lstTablaVntDetalleCliente = new ArrayList<>();
    private TablaVntDetalleCliente tablaVntDetalleClienteSel = new TablaVntDetalleCliente();
    private List<SelectItem> lstSexoDetClt = new ArrayList<>();
    private List<SelectItem> lstParentesco = new ArrayList<>();
    private String strDetNombre1;
    private String strDetDireccion;
    private String idSexoDetClt;
    private Integer idParentesco;
    private Date datDetFechaNac;
    private String strDetCorreo;
    private String detCelular;
    private String detExtension;
    private Integer detCargo = -1;
    private boolean blnEnviarCorreoAPrincipal = false;
    //Evento
    private VntRegistroventa vntRegistroventa = new VntRegistroventa();
    private VntDetevento vntDetevento = new VntDetevento();
    private List<SelectItem> lstItemsCiudadXDep = new ArrayList<>();
    private List<SelectItem> lstMotivoEvento = new ArrayList<>();
    private LinkedHashMap<Integer, LinkedHashMap<Integer, RfParentezco>> parametrosParentezco = new LinkedHashMap<>();
    private String depId;
    private Long ciuId;
    private String strDevNombres;
    private String strDevApellidos;
    private Integer idMotivoEvento;
    private Integer idMotivoEventoDefecto;
    private String strDevDireccion;
    private Date datDevFecha;
    private Date datDevHoraInicio;
    private Date datDevHoraFinal;
    private String strDevTelefono1;
    private String strDevTelefono2;
    private String strDevCelular1;
    private Integer strDetNumDias;
    private String strDevCelular2;
    private String strDevObservacion;
    //Servicio por registro de venta
    private VntServxventa vntServxventa = new VntServxventa();
    private VntColxventa vntColxventa = new VntColxventa();
    private List<TablaVntServicio> lstTablaVntServicio = new ArrayList<>();
    private List<TablaVntSrvXVenta> lstTablaVntSrvXVenta = new ArrayList<>();
    private List<TablaVntSrvXVenta> lstTablaVentaServicioXVenta = new ArrayList<>();
    private TablaVntSrvXVenta tablaVntSrvXVentaSel = new TablaVntSrvXVenta();

    private List<SelectItem> lstCargosContacto = new ArrayList<>();
    //<editor-fold defaultstate="collapsed" desc="Variables para registro llamada">
    private Integer idMotivoEventoLLamada = -1;
    private Date fechaEventoLLamada = null;
    private String homenajeadoLLamada = null;
    private Integer comoNosConoce = 1;
    private Integer comoNosConoceDefecto;
    private Integer edad = null;
    private String idSexoHomenajeado = "-1";
    private List<SelectItem> lstComoContacto = new ArrayList<>();
    private List<VntRegistroLlamada> lstVntRegistroLlamada = new ArrayList<>();
    private List<TablaVntServicio> lstServiciosXParametros = new ArrayList<>();
    FileEntryResults.FileInfo fileInfo = null;
    private static final String[] EXTENSION_PERMITIDA = {"xls", "xlsx"};
    private static final String EXTENSION_PERMITIDAS = "xls,xlsx";
    private String mensajeExcel = null;
    private String asuntoMensajeExcel = null;
    private List<TablaDetalleVentaDTO> lstVentasXDetalle = new ArrayList<>();
    private List<TablaDetalleVentaDTO> lstVentas = new ArrayList<>();
    private String registroLlamadaObservacion;

    //<editor-fold defaultstate="collapsed" desc="Cargar multiples archivos">
    private List<TablaArchivosAdjunto> lstArchivosCargar = new ArrayList<>();
    private TablaArchivosAdjunto archivosAdjuntoSel;
//</editor-fold>

    public String getNombreaAchivoSeleccionado() {
        if (fileInfo != null && fileInfo.isSaved()) {
            return "Archivo cargado :" + fileInfo.getFileName();

        }
        return null;
    }

    public void lnkAsuntoCotManual_AE(ActionEvent ae){
     asuntoMensajeExcel = vntDetevento.getRgvtId().getRgvtId() +  "_cotización " +   vntDetevento.getVdeObsr();
    }
    public void fileEntryAction(FileEntryEvent event) {
        fileInfo = null;
        FileEntry fileEntry = (FileEntry) event.getSource();
        FileEntryResults results = fileEntry.getResults();
        if (results.getFiles().size() > 1) {
            throw new IllegalStateException("Multiple files upload not supported");
        }
        fileInfo = results.getFiles().get(0);
        if (fileInfo.isSaved()) {
            String fileName = fileInfo.getFile().getName();
           
           
            int i = fileName.lastIndexOf('.');
            String extension = null;
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }
            if (extension == null || extension.equals("")) {
                mostrarError("Error Archivo sin extensión ", 1);
                //return;
            }
            TablaArchivosAdjunto taa = new TablaArchivosAdjunto(fileInfo.getFile(), fileInfo.getFile().getName());
            if (!lstArchivosCargar.contains(taa)) {
                lstArchivosCargar.add(taa);
            }

        } else {
            mostrarError("Archivo no ha sido cargado ", 1);

        }

    }

    //</editor-fold>
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">
    private void loadCiudad() {
        ciuId = Constantes.CIUDAD_DEFECTO;
        RfCiudad ciudad = pcsOrdenProduccionSFBean.getRfCiudad(ciuId);
        if (ciudad != null) {
            depId = ciudad.getDepId().toString();
            cargarCiuXDept();
        }
    }

    @Override
    public void init() {
        pcsfb = lookupPcsCotizacionSFBean();
        numPanel = PANEL_BUSCAR_CLIENTE;
        blnMostrarPanel = false;
        blnNuevo = true;
        idTipoDoc = "-1";
        esNit = false;
        idSexo = "-1";
        valoDescVenta = null;
        //idTipoCliente = -1;
        idActEconomica = -1;
        idSexoDetClt = "-1";
        idParentesco = -1;
        loadCiudad();

        cantidadServiciosSeleccionados = null;

        idMotivoEvento = -1;
        lstVntRegistroLlamada.clear();
        if (getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente() == null) {
            idTipoCliente = 1;
        } else {
            idTipoCliente = getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente().getTclId();
        }
        mapaServciosSel.clear();
        lstServiciosXParametros.clear();
        // idMotivoEventoLLamada = -1;

        fechaEventoLLamada = null;
        homenajeadoLLamada = null;
        cargarListaMotivoEvento();
        idMotivoEventoLLamada = idMotivoEventoDefecto;
        //  limpiarFacesMessage();
        lstVentasXDetalle.clear();
        lstVentas.clear();

        detCelular = null;
        detExtension = null;
        detCargo = -1;
        cargarCargos();
        lstTablaVntCliente.clear();
        registroLlamadaObservacion = null;
    }

    @Override
    public void limpiarVariables() {
        lstVntRegistroLlamada.clear();
        pcsfb.remove();
        mapaServciosSel.clear();
        idMotivoEventoLLamada = -1;
        fechaEventoLLamada = null;
        homenajeadoLLamada = null;
        lstMotivoEvento.clear();
        lstVentasXDetalle.clear();
        lstVentas.clear();
        registroLlamadaObservacion = null;
        //limpiarFacesMessage();
    }

    private void limpiarCamposBuscar() {
        strBuscarCln = "";
    }

    private void limpiarCamposCliente() {
        idTipoDoc = "-1";
        esNit = false;
        strCltDocumento = "";
        strCltNombres = "";
        strCltAlias = "";
        idSexo = "-1";
        intCltNumHijos = null;
        datCltFechaNac = null;
        strCltDireccion = "";
        strCltTelefono = "";
        strCltCelular = "";
        strCltCorreo = "";
        strCltContacto = "";
        // idTipoCliente = -1;
        idActEconomica = -1;
        mapaServciosSel.clear();
        idMotivoEventoLLamada = -1;
        // idMotivoEventoLLamada = idMotivoEventoDefecto.intValue();
        fechaEventoLLamada = null;
        homenajeadoLLamada = null;
        registroLlamadaObservacion = null;
    }

    private void limpiarCamposDetalleCliente() {
        strDetNombre1 = "";
        dcln_direccion = null;
        idParentesco = -1;
        idSexoDetClt = "-1";
        datDetFechaNac = null;
        lstTablaVntDetalleCliente.clear();
        detCelular = null;
        detExtension = null;
        detCargo = -1;
    }

    private void limpiarCamposDetalleEvento() {
        loadCiudad();
        strDevNombres = "";
        strDevApellidos = "";
        idMotivoEvento = -1;
        strDevDireccion = "";
        datDevFecha = null;
        datDevHoraInicio = null;
        datDevHoraFinal = null;
        strDevTelefono1 = "";
        strDevTelefono2 = "";
        strDevCelular1 = "";
        strDetNumDias = -1;
        strDevCelular2 = "";
        strDevObservacion = "";
        cargarCiuXDept();
    }

    private void limpiarCamposServicioXVenta() {
        lstTablaVntSrvXVenta.clear();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones"> 
    //<editor-fold defaultstate="collapsed" desc="Cliente">
    private void cargarClienteXDocumento(String strCC, Long idCliente) {
//        esNit = false;
        for (VntCliente c : pcsfb.getLstClienteXIdentificacion(strCC, idCliente)) {
            vntCliente = c;
            tablaVntClienteSel.setVntCliente(c);
            idTipoDoc = c.getTdcId().getTdcId();
            esNit = c.getTdcId().isTdcEsnit();
            strCltDocumento = c.getClnIdentificacion();
            strCltNombres = c.getClnNombres();
            strCltAlias = c.getClnAlias();
            idSexo = "-1";
            if (c.getSexId() != null) {
                idSexo = c.getSexId().getSexId();
            }

            intCltNumHijos = c.getClnNumhijos();
            datCltFechaNac = c.getClnFechanace();
            strCltDireccion = c.getClnDiereccion();
            strCltTelefono = c.getClnFijo();
            strCltCelular = c.getClnCelular();
            strCltCorreo = c.getClnCorreoe();
            strCltContacto = c.getClnContacto();
            //  idTipoCliente = c.getTclId().getTclId();
            idActEconomica = -1;
            if (c.getAteId() != null) {
                idActEconomica = c.getAteId().getAteId();
            }
            cargarListaRegistroLLamadas();
            break;

        }
    }

    public void cargarListaRegistroLLamadas() {
        lstVntRegistroLlamada.clear();
        for (VntRegistroLlamada vc : pcsfb.getLstVntRegistroLlamada(tablaVntClienteSel.getVntCliente().getClnId())) {

            lstVntRegistroLlamada.add(vc);
        }
    }

    public void cargarListaClientes() {
        lstTablaVntCliente.clear();
        for (VntCliente vc : pcsfb.getLstVntCliente(this.idTipoCliente)) {
            TablaVntCliente tvc = new TablaVntCliente();
            tvc.setVntCliente(vc);
            lstTablaVntCliente.add(tvc);
        }
    }

    public void cargarListaTipoDoc() {
        lstTipoDocumento.clear();
        lstTipoDocumento.add(itemSeleccione);
        for (RfTipodoc rt : pcsfb.getLstRfTipodoc(true)) {
            lstTipoDocumento.add(new SelectItem(rt.getTdcId(), rt.getTdcNombre()));
        }
    }

    private void cargarListaComoContacto() {
        lstComoContacto.clear();
        lstComoContacto.add(itemSeleccione);
        comoNosConoceDefecto = null;
        for (RfComocontacto rt : pcsfb.getLstRfComocontacto(true)) {
            lstComoContacto.add(new SelectItem(rt.getCmcId(), rt.getCmcNombre()));
            if (comoNosConoceDefecto == null) {
                if (rt.isCmcDefecto()) {
                    comoNosConoceDefecto = rt.getCmcId();
                }
            }
        }
        if (comoNosConoceDefecto == null) {
            comoNosConoceDefecto = -1;
        }
    }

    public void cargarListaSexoClt() {
        lstSexo.clear();
        lstSexo.add(itemSeleccione);
        for (RfSexo rt : pcsfb.getLstRfSexo()) {
            lstSexo.add(new SelectItem(rt.getSexId(), rt.getSexDesc()));
        }
    }

    public void cargarListaTipoCliente() {
        lstTipoCliente.clear();
        lstTipoCliente.add(itemSeleccioneInt);
        for (VntRfTipocliente vtc : pcsfb.getLstVntRfTipocliente(true)) {
            lstTipoCliente.add(new SelectItem(vtc.getTclId(), vtc.getTclNombre()));
        }
    }

    public void cargarListaActividadEconomica() {
        lstActividadEconomica.clear();
        lstActividadEconomica.add(new SelectItem(-1, "SELECCIONE >>"));
        for (VntActeconomica vae : pcsfb.getLstVntActeconomicaXEstado(true)) {
            lstActividadEconomica.add(new SelectItem(vae.getAteId(), vae.getAteNombre()));
        }
    }

    private void grabarCliente() {
        if (validarForm()) {
            //if (blnNuevo == true) {
            // vntCliente = new VntCliente();
            vntCliente.setTdcId(pcsfb.getRfTipodocXId(idTipoDoc));
            vntCliente.setClnIdentificacion(strCltDocumento);
            vntCliente.setClnNombres(strCltNombres);
            if (idSexo == null || idSexo.isEmpty() || idSexo.equals("-1")) {
                vntCliente.setSexId(null);
            } else {
                vntCliente.setSexId(pcsfb.getRfSexo(idSexo));
            }

            vntCliente.setClnNumhijos(intCltNumHijos);
            vntCliente.setClnFechanace(datCltFechaNac);
            vntCliente.setClnDiereccion(strCltDireccion);
            vntCliente.setClnFijo(strCltTelefono);
            vntCliente.setClnCelular(strCltCelular);
            vntCliente.setClnCorreoe(strCltCorreo);
            vntCliente.setClnContacto(strCltContacto);
            vntCliente.setTclId(pcsfb.getVntRfTipoclienteXId(idTipoCliente));
            vntCliente.setAteId(pcsfb.getVntActeconomicaXId(idActEconomica));
            vntCliente.setClnEstado(Boolean.TRUE);
            vntCliente.setClnFechaproceso(new Date());
            vntCliente.setClnAlias(strCltAlias);
            vntCliente = pcsfb.editarCliente(vntCliente);
            tablaVntClienteSel.setVntCliente(vntCliente);
            for (TablaVntDetalleCliente tvdc : lstTablaVntDetalleCliente) {
                if (tvdc.getVntDetallecliente().getClnId() == null) {
                    tvdc.getVntDetallecliente().setClnId(vntCliente);
                }
                tvdc.setVntDetallecliente(pcsfb.editarDetalleCliente(tvdc.getVntDetallecliente()));
            }


            /* for (TablaVntDetalleCliente tvdc : lstTablaVntDetalleCliente) {
             if (tvdc.getVntDetallecliente().getClnId() == null) {
             tvdc.getVntDetallecliente().setClnId(vntCliente);
             }
             tvdc.setVntDetallecliente(pcsfb.editarDetalleCliente(tvdc.getVntDetallecliente()));
             }*/
            mostrarError("Grabación exitosa...!", 3);
        }
    }

    public void cargarListaSexoDetClt() {
        lstSexoDetClt.clear();
        lstSexoDetClt.add(itemSeleccione);
        for (RfSexo rt : pcsfb.getLstRfSexo()) {
            lstSexoDetClt.add(new SelectItem(rt.getSexId(), rt.getSexDesc()));
        }
    }

    public void cargarListaParentesco() {
        lstParentesco.clear();
        lstParentesco.add(itemSeleccioneInt);
        parametrosParentezco.clear();
        //1 es para los que no aplican nit
        //2 es para los que  aplican nit
        parametrosParentezco.put(1, new LinkedHashMap<Integer, RfParentezco>());
        parametrosParentezco.put(2, new LinkedHashMap<Integer, RfParentezco>());
        for (RfParentezco par : pcsfb.getLstRfParentesco(true)) {
            if (par.isPrtAplicanit()) {
                parametrosParentezco.get(2).put(par.getPrtId(), par);
            } else {
                parametrosParentezco.get(1).put(par.getPrtId(), par);
            }
            //  lstParentesco.add(new SelectItem(par.getPrtId(), par.getPrtNombre()));
        }
    }

    public void cargarListaDetalleClienteNuevo() {
        lstTablaVntDetalleCliente.clear();
        for (VntDetallecliente vdc : pcsfb.getLstVntDetallecliente(vntCliente.getClnId(), true)) {
            TablaVntDetalleCliente tvdc = new TablaVntDetalleCliente();
            tvdc.setVntDetallecliente(vdc);
            tvdc.setEdad(null);
            if (vdc.getDclnFechanace() != null) {
                tvdc.setEdad(getAnioDiferencia(tvdc.getVntDetallecliente().getDclnFechanace(), new Date()));
            }
            lstTablaVntDetalleCliente.add(tvdc);
        }
    }

    public void cargarListaDetalleClienteExistente() {
        lstTablaVntDetalleCliente.clear();
        for (VntDetallecliente vdc : pcsfb.getLstVntDetallecliente(tablaVntClienteSel.getVntCliente().getClnId(), true)) {
            TablaVntDetalleCliente tvdc = new TablaVntDetalleCliente();
            tvdc.setVntDetallecliente(vdc);
            tvdc.setEdad(null);
            tvdc.setSeleccionado(false);
            if (vdc.getDclnFechanace() != null) {
                tvdc.setEdad(getAnioDiferencia(tvdc.getVntDetallecliente().getDclnFechanace(), new Date()));
            }
            lstTablaVntDetalleCliente.add(tvdc);
        }
    }

    private void grabarDetalleCliente() {
        if (validaFormDetalleCliente()) {
            vntDetallecliente = new VntDetallecliente();

            vntDetallecliente.setDclnNombres(strDetNombre1);
            vntDetallecliente.setDclnDireccion(dcln_direccion);
            vntDetallecliente.setSexId(null);
            if (!esNit) {
                if (idSexoDetClt == null || idSexoDetClt.equals("-1")) {
                    vntDetallecliente.setSexId(null);
                } else {
                    vntDetallecliente.setSexId(pcsfb.getRfSexo(idSexoDetClt));
                }
            }
            vntDetallecliente.setPrtId(pcsfb.getRfParentesco(idParentesco));
            vntDetallecliente.setDclnFechanace(datDetFechaNac);
            vntDetallecliente.setDclnEmail(strDetCorreo);
            vntDetallecliente.setDclnCelular(detCelular);
            vntDetallecliente.setDclnExtension(detExtension);
            if (detCargo == null | detCargo == -1) {
                vntDetallecliente.setRfCargocontacto(null);
            } else {
                vntDetallecliente.setRfCargocontacto(pcsfb.getRfCargocontacto(detCargo));
            }

        } else {
            return;

        }

        strDetNombre1 = "";
        strDetDireccion = null;
        idParentesco = -1;
        idSexoDetClt = "-1";
        datDetFechaNac = null;
        strDetCorreo = null;
        detCelular = null;
        detExtension = null;
        detCargo = -1;
        TablaVntDetalleCliente tvdc = new TablaVntDetalleCliente();
        tvdc.setVntDetallecliente(vntDetallecliente);
        tvdc.setEdad(null);
        if (!esNit) {
            tvdc.setEdad(getAnioDiferencia(tvdc.getVntDetallecliente().getDclnFechanace(), new Date()));
        }
        lstTablaVntDetalleCliente.add(tvdc);

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Evento">
    private void cargarCiuXDept() {
        lstItemsCiudadXDep.clear();
        lstItemsCiudadXDep.add(itemSeleccioneLong);
        if (depId != null && !depId.isEmpty()) {
            for (RfCiudad rc : getAppBean().getDgslb().getLstCiuXDep(depId)) {
                lstItemsCiudadXDep.add(new SelectItem(rc.getCiuId(), rc.getCiuDesc()));
            }
        }
    }

    private void cargarListaMotivoEvento() {
        lstMotivoEvento.clear();
        lstMotivoEvento.add(new SelectItem(-1, "SELECCIONE >>"));
        idMotivoEventoDefecto = null;

        for (RfMotivoevento rme : pcsfb.getLstRfMotivoeventoXEstadoXTipoCliente(true, idTipoCliente, true)) {
            lstMotivoEvento.add(new SelectItem(rme.getMteId(), rme.getMteNombre()));
            if (idMotivoEventoDefecto == null) {
                if (rme.isMteDefecto()) {
                    idMotivoEventoDefecto = rme.getMteId();
                }
            }
        }
        if (idMotivoEventoDefecto == null) {
            idMotivoEventoDefecto = -1;
        }
        // for (RfMotivoevento rme : pcsfb.getLstRfMotivoeventoXEstado(true)) {
        //   lstMotivoEvento.add(new SelectItem(rme.getMteId(), rme.getMteNombre()));
        //}
    }

    private void grabarDetalleEvento() {
        if (validaFormEvento()) {
            vntRegistroventa = new VntRegistroventa();
            vntRegistroventa.setClnId(tablaVntDetalleClienteSel.getVntDetallecliente().getClnId());
            vntRegistroventa.setRgvtEst(Boolean.TRUE);
            vntRegistroventa.setRgvtAnulado(Boolean.FALSE);
            vntRegistroventa.setEstrvntId(new VntEstventa(2));
            vntRegistroventa.setRgvtPagado(Boolean.FALSE);
            vntRegistroventa.setRgvtEstconfirmada(Boolean.FALSE);
            vntRegistroventa.setRgvtFechacre(new Date());
            vntRegistroventa = pcsfb.editarRegistroVenta(vntRegistroventa);

            vntDetevento = new VntDetevento();
            vntDetevento.setDclnId(tablaVntDetalleClienteSel.getVntDetallecliente());

            StringBuilder strBConCopia = new StringBuilder();
            int cont = 0;
            for (TablaVntDetalleCliente tvdc : lstTablaVntDetalleCliente) {
                if (tvdc.isContactoCopia() && !tvdc.equals(tablaVntDetalleClienteSel) && tvdc.getVntDetallecliente().getDclnEmail() != null) {
                    strBConCopia.append(tvdc.getVntDetallecliente().getDclnEmail().trim());
                    strBConCopia.append(";");
                    cont++;

                }
            }
            if (cont > 0) {
                strBConCopia.delete(strBConCopia.length() - 1, strBConCopia.length());
                vntDetevento.setVdeContcopia(strBConCopia.toString());
            }

            vntDetevento.setRgvtId(vntRegistroventa);
            vntDetevento.setCiuId(ciuId == null || ciuId.equals(-1l) ? null : pcsOrdenProduccionSFBean.getRfCiudad(ciuId));
            vntDetevento.setVdeNombrescontacto(strDevNombres);
            vntDetevento.setVdeApellidoscontacto(strDevApellidos);
            vntDetevento.setMteId(pcsfb.motivoEventoXId(idMotivoEvento));
            vntDetevento.setVdeDireccionevt(strDevDireccion);
            vntDetevento.setVdeFechaevt(datDevFecha);
            vntDetevento.setVdeHorainicio(datDevHoraInicio);
            vntDetevento.setVdeHorafinal(datDevHoraFinal);
            vntDetevento.setVdeTelefono1(strDevTelefono1);
            vntDetevento.setVdeTelefono2(strDevTelefono2);
            vntDetevento.setVdeCelular1(strDevCelular1);
            vntDetevento.setVdeNumdias(strDetNumDias);
            vntDetevento.setVdeCelular2(strDevCelular2);
            vntDetevento.setVdeObsr(strDevObservacion);
            vntDetevento = pcsfb.grabarDetalleEvento(vntDetevento);
            vntRegistroventa.setVdeId(vntDetevento);
            vntRegistroventa = pcsfb.editarRegistroVenta(vntRegistroventa);

            numPanel = PANEL_SERVICIO_VENTA;
        }
    }

    public void cargarListaServiciosPorVentaSel_ActionEvent(ActionEvent ae) {
        VntRegistroventa sel = (VntRegistroventa) ae.getComponent().getAttributes().get("venta");
        HashMap<Long, TablaVntServicio> mapaServciosSelPopUp = new HashMap<>();
        for (VntServxventa v : pcsfb.getlstTablaVentaServicioXVenta(sel.getRgvtId())) {
            TablaVntServicio ts = new TablaVntServicio();
            ts.setVntServicio(v.getVsrvId());
            ts.setBigdPrecioCliente(v.getSrvxventPrecioventa());
            ts.setCantidadSrv(v.getSrvxventCantidad());
            
            mapaServciosSelPopUp.put(v.getVsrvId().getVsrvId(), ts);
        }
        if (mapaServciosSelPopUp.isEmpty()) {
            mostrarError("La venta seleccionada no tiene servicios asociados.", 1);
            return;
        }

        List<VntServxventa> lstVntServxventasGrabar = new ArrayList<>();
        vntColxventa = new VntColxventa();
        vntColxventa.setRgvtId(vntRegistroventa);
        vntColxventa.setCxcId(pjsfb.getAdmCrgxcolActivo());
        vntColxventa.setColxvDechaproc(new Date());
        if (vntRegistroventa.getVntColxventaList() != null) {
            vntRegistroventa.getVntColxventaList().add(vntColxventa);
        } else {
            vntRegistroventa.setVntColxventaList(new ArrayList<VntColxventa>());
            vntRegistroventa.getVntColxventaList().add(vntColxventa);
        }

        HashMap<Long, TablaVntServicio> mapaFinal = new HashMap<>();

        for (TablaVntSrvXVenta tvsxv : lstTablaVntSrvXVenta) {
            TablaVntServicio s = mapaServciosSelPopUp.get(tvsxv.getVntServxventa().getVsrvId().getVsrvId());

            if (s != null) {

                tvsxv.getVntServxventa().setSrvxventCantidad(s.getCantidadSrv());
                tvsxv.getVntServxventa().setVsrvId(s.getVntServicio());
                tvsxv.getVntServxventa().setSrvxventEst(Boolean.TRUE);
                tvsxv.getVntServxventa().setSrvxventPrecioventa(s.getBigdPrecioCliente());
                tvsxv.getVntServxventa().setSrvxventValtotalclnt(s.getBigdPrecioCliente().multiply(new BigDecimal(s.getCantidadSrv())));
                tvsxv.getVntServxventa().setSrvxventPorcentajeDesc(s.getVntServicio().getVsrvPorcentajeiva());
                lstVntServxventasGrabar.add(tvsxv.getVntServxventa());
                mapaFinal.put(tvsxv.getVntServxventa().getVsrvId().getVsrvId(), s);

            }
        }
        Iterator iter = mapaServciosSelPopUp.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry e = (Map.Entry) iter.next();

            TablaVntServicio tvs = mapaFinal.get(e.getKey());
            if (tvs == null) {
                tvs = (TablaVntServicio) e.getValue();
                vntServxventa = new VntServxventa();
                vntServxventa.setRgvtId(vntRegistroventa);
                vntServxventa.setSrvxventCantidad(tvs.getCantidadSrv());
                vntServxventa.setVsrvId(tvs.getVntServicio());
                vntServxventa.setSrvxventEst(Boolean.TRUE);
                vntServxventa.setSrvxventPrecioventa(tvs.getBigdPrecioCliente());
                vntServxventa.setSrvxventValtotalclnt(tvs.getBigdPrecioCliente().multiply(new BigDecimal(tvs.getCantidadSrv())));
                lstVntServxventasGrabar.add(vntServxventa);
            }
        }
        pcsfb.editarRegVentaConServ(vntRegistroventa, lstVntServxventasGrabar);

        vntRegistroventa.setRgvtFechaconfirma(new Date());
        vntRegistroventa.setRgvtEstconfirmada(true);
        pcsfb.editarRegVenta(vntRegistroventa);
        vntRegistroventa.setRgvtId(vntRegistroventa.getRgvtId());
        cargarListaServicioXVenta();
        Boolean bln = false;
        selTodoLst(lstTablaVntServicio, bln);
        //mapaServciosSel.clear();
        mostrarError("Se asocio el servicio correctamente", 3);
        cargarListaServicio();
        valoDescVenta = null;
        cargarTotales();
        lstVentas.clear();

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Servicio por venta">    
    private void cargarListaServicio() {
        lstTablaVntServicio.clear();
        mapaServciosSel.clear();
        for (ServRfTipocliente d : pcsfb.obtenerServRfTipoclientePorTipoCliente(idTipoCliente, true)) {
            TablaVntServicio tvs = new TablaVntServicio(d.getVntServicio());
            tvs.setBigdPrecioCliente(tvs.getVntServicio().getVsrvValunitcliente());
            tvs.setCantidadSrv(1);
            lstTablaVntServicio.add(tvs);
        }
    }

    private void cargarListaServicioXVenta() {
        lstTablaVntSrvXVenta.clear();
        for (VntServxventa vs : pcsfb.getLstServxventaXVnt(vntRegistroventa.getRgvtId())) {
            TablaVntSrvXVenta tsxv = new TablaVntSrvXVenta(vs);
            lstTablaVntSrvXVenta.add(tsxv);
        }
    }

    private void grabarServicioXVenta() {
        if (validaFormServicio()) {

            List<VntServxventa> lstVntServxventasGrabar = new ArrayList<>();
            vntColxventa = new VntColxventa();
            vntColxventa.setRgvtId(vntRegistroventa);
            vntColxventa.setCxcId(pjsfb.getAdmCrgxcolActivo());
            vntColxventa.setColxvDechaproc(new Date());
            if (vntRegistroventa.getVntColxventaList() != null) {
                vntRegistroventa.getVntColxventaList().add(vntColxventa);
            } else {
                vntRegistroventa.setVntColxventaList(new ArrayList<VntColxventa>());
                vntRegistroventa.getVntColxventaList().add(vntColxventa);
            }

            HashMap<Long, TablaVntServicio> mapaFinal = new HashMap<>();

            for (TablaVntSrvXVenta tvsxv : lstTablaVntSrvXVenta) {
                TablaVntServicio s = mapaServciosSel.get(tvsxv.getVntServxventa().getVsrvId().getVsrvId());

                if (s != null) {

                    tvsxv.getVntServxventa().setSrvxventCantidad(s.getCantidadSrv());
                    tvsxv.getVntServxventa().setVsrvId(s.getVntServicio());
                    tvsxv.getVntServxventa().setSrvxventEst(Boolean.TRUE);
                    tvsxv.getVntServxventa().setSrvxventPrecioventa(s.getBigdPrecioCliente());
                    tvsxv.getVntServxventa().setSrvxventValtotalclnt(s.getBigdPrecioCliente().multiply(new BigDecimal(s.getCantidadSrv())));
                    lstVntServxventasGrabar.add(tvsxv.getVntServxventa());
                    mapaFinal.put(tvsxv.getVntServxventa().getVsrvId().getVsrvId(), s);

                }
            }
            Iterator iter = mapaServciosSel.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry e = (Map.Entry) iter.next();

                TablaVntServicio tvs = mapaFinal.get(e.getKey());
                if (tvs == null) {
                    tvs = (TablaVntServicio) e.getValue();
                    vntServxventa = new VntServxventa();
                    vntServxventa.setRgvtId(vntRegistroventa);
                    vntServxventa.setSrvxventCantidad(tvs.getCantidadSrv());
                    vntServxventa.setVsrvId(tvs.getVntServicio());
                    vntServxventa.setSrvxventEst(Boolean.TRUE);
                    vntServxventa.setSrvxventPrecioventa(tvs.getBigdPrecioCliente());
                    vntServxventa.setSrvxventValtotalclnt(tvs.getBigdPrecioCliente().multiply(new BigDecimal(tvs.getCantidadSrv())));
                    lstVntServxventasGrabar.add(vntServxventa);
                }
            }
            pcsfb.editarRegVentaConServ(vntRegistroventa, lstVntServxventasGrabar);

            vntRegistroventa.setRgvtFechaconfirma(new Date());
            vntRegistroventa.setRgvtEstconfirmada(true);
            pcsfb.editarRegVenta(vntRegistroventa);
            vntRegistroventa.setRgvtId(vntRegistroventa.getRgvtId());
            cargarListaServicioXVenta();
            Boolean bln = false;
            selTodoLst(lstTablaVntServicio, bln);
            mapaServciosSel.clear();
            mostrarError("Se asocio el servicio correctamente", 3);
            cargarListaServicio();
            valoDescVenta = null;
            cargarTotales();

        }

    }

    private String getNombreArchivoPorEvento(VntDetevento d) {
        String subCliente = (d.getVdeObsr() == null ? "" : "_" + d.getVdeObsr().replace(" ", "_"));
        String nombre = (d.getRgvtId().getClnId().getClnAlias() == null ? d.getRgvtId().getClnId().getClnNombres().replace(" ", "_") : d.getRgvtId().getClnId().getClnAlias().replace(" ", "_"));
        if (idTipoCliente.equals(EnTipoCliente.KIDS.getId())) {
            nombre = d.getRgvtId().getClnId().getClnNombres().replace(" ", "_");
            subCliente = "_" + d.getDclnId().getNombres().replace(" ", "_");
            subCliente += "_" + d.getMteId().getMteNombre().replace(" ", "_");
            subCliente += "_" + d.getDclnId().getDclnDireccion().replace(" ", "_");
            subCliente += "_" + (d.getVdeObsr() == null ? "" : "_" + d.getVdeObsr().replace(" ", "_"));
        }
        return d.getRgvtId().getRgvtId() + "_" + nombre + subCliente + ".xls";

    }

    private void cerrarVenta() {

        if (!lstTablaVntSrvXVenta.isEmpty()) {
            if (vntRegistroventa.getEstrvntId().getEstrvntId() == 2) {
                vntRegistroventa.setEstrvntId(pcsfb.getEstventaXId(3));

                //cargarListaServicioXVenta();
                List<VntServxventa> lstVntServxventasGrabar = new ArrayList<>();
                int cantidad = 0;
                for (TablaVntSrvXVenta vs : lstTablaVntSrvXVenta) {

                    cantidad += vs.getVntServxventa().getSrvxventCantidad();
                    vs.getVntServxventa().setSrvxventaProcesada(0);
                    vs.getVntServxventa().setSrvxventaProcesadaOP(0);
                    if (vs.getVntServxventa().getSrvxventDescuento() == null) {
                        vs.getVntServxventa().setSrvxventDescuento(BigDecimal.ZERO);
                        vs.getVntServxventa().setSrvxventPorcentajeDesc(BigDecimal.ZERO);
                    }
                    lstVntServxventasGrabar.add(vs.getVntServxventa());
                }
                pcsfb.editarRegVentaConServ(vntRegistroventa, lstVntServxventasGrabar);
                vntRegistroventa.setRgvtCantserviciosAsociados(cantidad);
                vntRegistroventa.setRgvtCantserviciosProcesados(0);
                vntRegistroventa.setRgvtCantidadservasociadosOp(cantidad);
                vntRegistroventa.setRgvtCantidadservprocesadosOp(0);
                vntRegistroventa.setRgvtActivarOp(false);

                pcsfb.editarRegVenta(vntRegistroventa);
                /**
                 * Proceso navegación a llamada
                 *
                 */
                tablaVntClienteSel = new TablaVntCliente();
                tablaVntClienteSel.setVntCliente(vntRegistroventa.getClnId());

                cargarDatosCliente();
                numPanel = PANEL_LLAMADA;
                cargarListaRegistroLLamadas();
                cargarListaComoContacto();
                // cargarListaMotivoEvento();
                idSexoHomenajeado = "-1";
                fileInfo = null;
                mensajeExcel = null;
                asuntoMensajeExcel = null;
                edad = null;
                //comoNosConoce = -1;
                comoNosConoce = comoNosConoceDefecto.intValue();
                //  idMotivoEventoLLamada = -1;
                idMotivoEventoLLamada = idMotivoEventoDefecto.intValue();
                fechaEventoLLamada = null;
                homenajeadoLLamada = null;
                blnEnviarCorreoAPrincipal = false;
                cargarListaDetalleClienteExistente();
                //cargar datos y levantarPopUp

                List<VntDetevento> lst = pcsfb.getLstVntDetevento(vntRegistroventa.getRgvtId());
                for (VntDetevento d : lst) {
                    vntRegistroventa.setVdeId(d);
                    break;
                }

                for (TablaVntDetalleCliente vdc : lstTablaVntDetalleCliente) {
                    if (vntRegistroventa.getVdeId().getDclnId().equals(vdc.getVntDetallecliente())) {
                        lstVentasXDetalle.clear();

                        for (VntDetevento d : pcsfb.getLstVntDeteventoXDetalleCliente(vdc.getVntDetallecliente().getDclnId())) {
                            TablaDetalleVentaDTO p = new TablaDetalleVentaDTO();
                            p.setDetevento(d);

                            p.setNombreArchivo(getNombreArchivoPorEvento(d));
                            lstVentasXDetalle.add(p);
                            if (d.getRgvtId().equals(vntRegistroventa)) {
                                vdc.setSeleccionado(true);
                            }
                        }

                        break;
                    }

                }
                //Setear el detalle del cliente (Contacto)al que se le realizo el evento

                JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "dialogCotizacion.show();");

                // fin nuevo cambio
            }
            // JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "dialogDescuento.hide();");
            mostrarError("Cotización finalizada, ir a venta confirmación de pago...!", 3);

        } else {
            mostrarError("Esta cotización no tiene servicios asociados");
        }
    }

    public void cargarEventosGenerales_ActionEvent(ActionEvent ae) {
        cargarEventosGenerales();
    }

    private void cargarEventosGenerales() {
        lstVentas.clear();

        for (VntDetevento d : pcsfb.getListaVentas(idTipoCliente)) {
            if (vntRegistroventa != null && vntRegistroventa.getRgvtId() != null && vntRegistroventa.getRgvtId().equals(d.getRgvtId().getRgvtId())) {
                continue;
            }
            TablaDetalleVentaDTO p = new TablaDetalleVentaDTO();
            p.setDetevento(d);

            p.setNombreArchivo(getNombreArchivoPorEvento(d));
            lstVentas.add(p);

        }

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Registro Llamada">
    public void activarGeneracionCronograma_actionEvent(ValueChangeEvent vce) {
        //  limpiarFacesMessage();

        VntRegistroventa rv = (VntRegistroventa) vce.getComponent().getAttributes().get("venta");
        rv.setRgvtActCronograma((Boolean) vce.getNewValue());
        rv = pcsfb.editarRegistroVenta(rv);

        List<VntServxventa> lista = pcsfb.getLstServxventaXVnt(rv.getRgvtId());
        List<VntCronograma> listCronograma = new ArrayList<>();
        for (VntServxventa ob : lista) {
            for (int i = 1; i <= ob.getSrvxventCantidad(); i++) {
                VntCronograma cr = new VntCronograma();
                cr.setVntServxventa(ob);
                listCronograma.add(cr);
            }
        }
        cronogramaSLBean.editarListCronograma(listCronograma);
    }

    public void activarGeneracionOrdenProducion_ValueChangeEvent(ValueChangeEvent vce) {
        //  limpiarFacesMessage();
        Boolean variable = (Boolean) vce.getNewValue();
        VntRegistroventa rv = (VntRegistroventa) vce.getComponent().getAttributes().get("venta");
        rv.setRgvtActivarOp(variable);
        rv = pcsfb.editarRegistroVenta(rv);

    }

    public void activarGeneracionOrdenProducion_actionEvent(ActionEvent vce) {
        //  limpiarFacesMessage();

        VntRegistroventa rv = (VntRegistroventa) vce.getComponent().getAttributes().get("venta");
        Integer estado = Integer.parseInt((String) vce.getComponent().getAttributes().get("estado"));
        if (rv.getRgvtEstados() != EnEstadosVentaOp.FACTURAR_Y_OP.getId()) {
            switch (estado) {
                case 3:
                    if (rv.getRgvtEstados() == EnEstadosVentaOp.ACTIVOOP.getId()) {
                        rv.setRgvtEstados(EnEstadosVentaOp.FACTURAR_Y_OP.getId());
                    } else {
                        rv.setRgvtEstados(EnEstadosVentaOp.ACTIVO.getId());
                    }

                    break;
                case 4:
                    if (rv.getRgvtEstados() == EnEstadosVentaOp.ACTIVO.getId()) {
                        rv.setRgvtEstados(EnEstadosVentaOp.FACTURAR_Y_OP.getId());
                    } else {
                        rv.setRgvtEstados(EnEstadosVentaOp.ACTIVOOP.getId());
                    }
                    break;
            }
        }
        rv.setRgvtActivarOp(false);
        if (rv.getRgvtEstados() == EnEstadosVentaOp.ACTIVOOP.getId() || rv.getRgvtEstados() == EnEstadosVentaOp.FACTURAR_Y_OP.getId()) {
            rv.setRgvtActivarOp(true);
        }

        rv = pcsfb.editarRegistroVenta(rv);

    }

    public void anularGeneracionOrdenProducion_actionEvent(ActionEvent vce) {
        //  limpiarFacesMessage();

        VntRegistroventa rv = (VntRegistroventa) vce.getComponent().getAttributes().get("venta");

        rv.setRgvtActivarOp(false);
        rv.setRgvtEstados(EnEstadosVentaOp.ANULADO.getId());
        rv = pcsfb.editarRegistroVenta(rv);

    }

    public void inactivarGeneracionOrdenProducion_actionEvent(ActionEvent vce) {
        //  limpiarFacesMessage();

        VntRegistroventa rv = (VntRegistroventa) vce.getComponent().getAttributes().get("venta");

        rv.setRgvtActivarOp(false);
        rv.setRgvtEstados(EnEstadosVentaOp.PENDIENTE.getId());
        rv = pcsfb.editarRegistroVenta(rv);

    }

    private boolean envioCorreoExcel(List<TablaVntDetalleCliente> seleccionados, List<String> pLstConCopia) {

        try {
            fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            HtmlEmail email = new HtmlEmail();
            String html = null;
            String plain = null;
            StrSubstitutor strSub = null;

            // Para no usar beans u objetos que compliquen el código de este tutorial vamos a poner los valores   
            // de las propiedades que serán más adelante reemplazadas usando la clase java.util.Properties  
            Properties usuario = new Properties();
            usuario.put("nombre", vntCliente.getClnNombres());
            //  usuario.put("apellidos", "Robles Caro");
            usuario.put("email", vntCliente.getClnCorreoe());
            usuario.put("mensajeExcel", mensajeExcel);

            plain = fileToString(ec.getResourceAsStream(getRuta_recursos() + "com/pandora/web/procesos/mailExcel.txt"), "utf-8");
            strSub = new StrSubstitutor(usuario);
            plain = strSub.replace(plain);
            html = fileToString(ec.getResourceAsStream(getRuta_recursos() + "com/pandora/web/procesos/mailExcel.html"), "utf-8");
            String cid = null;
            String images = "logop.png";
            if (idTipoCliente.equals(EnTipoCliente.KIDS.getId())) {
                images = "logok.png";
            }
            File img = new File(ec.getRealPath(getRuta_recursos() + "com/pandora/web/procesos/" + images));
            cid = email.embed(img, images);
            usuario.put(images, cid);
            strSub = new StrSubstitutor(usuario);
            html = strSub.replace(html);
            email.setHostName(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpHostcorreo());
            email.setCharset("UTF-8");

            if (blnEnviarCorreoAPrincipal) {
                email.addTo(usuario.getProperty("email"), usuario.getProperty("nombre"));

            }
            if (!seleccionados.isEmpty()) {
                for (TablaVntDetalleCliente s : seleccionados) {
                    email.addTo(s.getVntDetallecliente().getDclnEmail(), s.getVntDetallecliente().getDclnNombres());
                }
            }
            email.addCc(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpUsuariocorreo());
            for (String contactoCopia : pLstConCopia) {
                email.addCc(contactoCopia);
            }
            //email.addTo(usuario.getProperty("email"), usuario.getProperty("nombre"));
            email.setFrom(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpUsuariocorreo(), getPrincipalJSFBean().getColxempLog().getEmpId().getEmpNombre());
            email.setSubject(asuntoMensajeExcel);
            email.setHtmlMsg(html);
            email.setTextMsg(plain);
            email.setAuthentication(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpUsuariocorreo(),
                    getPrincipalJSFBean().getColxempLog().getEmpId().getEmpClavecorreo());
            MimeMultipart multiParte = new MimeMultipart();
            for (TablaArchivosAdjunto archivoAdjunto : lstArchivosCargar) {
                BodyPart adjunto = new MimeBodyPart();
                //   pdf = "HV_BREYMER_ROBLES_CARO.pdf";
                adjunto.setDataHandler(new DataHandler(new FileDataSource(archivoAdjunto.getArchivo())));
                //adjunto.setDataHandler(new DataHandler(new FileDataSource("C:\\workspace\\" + pdf)));

                adjunto.setFileName(archivoAdjunto.getNombreArchivo());
                multiParte.addBodyPart(adjunto);
            }
//            if (fileInfo.getFile() != null && fileInfo.getFile().exists()) {
//
//                BodyPart adjunto = new MimeBodyPart();
//                //   pdf = "HV_BREYMER_ROBLES_CARO.pdf";
//                adjunto.setDataHandler(new DataHandler(new FileDataSource(fileInfo.getFile())));
//                //adjunto.setDataHandler(new DataHandler(new FileDataSource("C:\\workspace\\" + pdf)));
//
//                adjunto.setFileName(fileInfo.getFile().getName());
//                multiParte.addBodyPart(adjunto);
//            }

            email.addPart(multiParte);
            email.send();
            lstArchivosCargar.clear();

            // } catch (MessagingException ex) {
            //   Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            // mostrarError("Ocurrio un error al enviar correo ", 3);
        } catch (IOException ex) {
            Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ocurrio un error al enviar correo ", 3);
        } catch (EmailException ex) {
            Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ocurrio un error al enviar correo ", 3);
        } catch (MessagingException ex) {
            Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ocurrio un error al enviar correo ", 3);
        }
//            } else {
//                mostrarError("No se envió correo por que el archivo asociado no existe", 3);
//            }

        return true;

    }

    private boolean envioCorreo(List<String> pdfs, List<TablaVntDetalleCliente> seleccionados) {

        try {
            fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            HtmlEmail email = new HtmlEmail();
            String html = null;
            String plain = null;
            StrSubstitutor strSub = null;

            // Para no usar beans u objetos que compliquen el código de este tutorial vamos a poner los valores   
            // de las propiedades que serán más adelante reemplazadas usando la clase java.util.Properties  
            Properties usuario = new Properties();
            usuario.put("nombre", vntCliente.getClnNombres());
            //  usuario.put("apellidos", "Robles Caro");
            usuario.put("email", vntCliente.getClnCorreoe());

            plain = fileToString(ec.getResourceAsStream(getRuta_recursos() + "com/pandora/web/procesos/mail.txt"), "utf-8");
            strSub = new StrSubstitutor(usuario);
            plain = strSub.replace(plain);
            html = fileToString(ec.getResourceAsStream(getRuta_recursos() + "com/pandora/web/procesos/mail.html"), "utf-8");
            String cid = null;
            String images = "logop.png";
            if (idTipoCliente.equals(EnTipoCliente.KIDS.getId())) {
                images = "logok.png";
            }
            File img = new File(ec.getRealPath(getRuta_recursos() + "com/pandora/web/procesos/" + images));
            cid = email.embed(img, images);
            usuario.put(images, cid);
            strSub = new StrSubstitutor(usuario);
            html = strSub.replace(html);

//                    props.setProperty("mail.smtp.port", getPrincipalJSFBean().getColxempLog().getEmpId().getEmpPuertocorreo());
//                    props.setProperty("mail.smtp.user", getPrincipalJSFBean().getColxempLog().getEmpId().getEmpUsuariocorreo());
            email.setHostName(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpHostcorreo());
            email.setCharset("UTF-8");
            if (blnEnviarCorreoAPrincipal) {
                email.addTo(usuario.getProperty("email"), usuario.getProperty("nombre"));
            }
            if (!seleccionados.isEmpty()) {
                for (TablaVntDetalleCliente s : seleccionados) {
                    email.addTo(s.getVntDetallecliente().getDclnEmail(), s.getVntDetallecliente().getDclnNombres());
                }
            }

            //email.addTo(usuario.getProperty("email"), usuario.getProperty("nombre"));
            email.setFrom(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpUsuariocorreo(), getPrincipalJSFBean().getColxempLog().getEmpId().getEmpNombre());
            email.setSubject("Cotización Servicios");
            email.setHtmlMsg(html);
            email.setTextMsg(plain);
            email.setAuthentication(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpUsuariocorreo(),
                    getPrincipalJSFBean().getColxempLog().getEmpId().getEmpClavecorreo());
            MimeMultipart multiParte = new MimeMultipart();
            for (String pdf : pdfs) {
                File file = new File(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpRutaspdfscorreo() + "/" + pdf);
                if (file != null && file.exists()) {
                    BodyPart adjunto = new MimeBodyPart();
                    //   pdf = "HV_BREYMER_ROBLES_CARO.pdf";
                    adjunto.setDataHandler(new DataHandler(new FileDataSource(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpRutaspdfscorreo() + "/" + pdf)));
                    //adjunto.setDataHandler(new DataHandler(new FileDataSource("C:\\workspace\\" + pdf)));

                    adjunto.setFileName(pdf);
                    multiParte.addBodyPart(adjunto);
                }
            }

            email.addPart(multiParte);
            email.send();

            // } catch (MessagingException ex) {
            //   Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            // mostrarError("Ocurrio un error al enviar correo ", 3);
        } catch (IOException ex) {
            Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ocurrio un error al enviar correo ", 3);
        } catch (EmailException ex) {
            Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ocurrio un error al enviar correo ", 3);
        } catch (MessagingException ex) {
            Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ocurrio un error al enviar correo ", 3);
        }
//            } else {
//                mostrarError("No se envió correo por que el archivo asociado no existe", 3);
//            }

        return true;

    }

//</editor-fold>
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    public void generarReporteCotizacionXventa_ActionEvent(ActionEvent ae) {
        TablaDetalleVentaDTO tabla = (TablaDetalleVentaDTO) ae.getComponent().getAttributes().get("tabla");
        HashMap hmParametros = new HashMap();
//        hmParametros.put("p_str_id", sysTarea.getStrId());
        hmParametros.put("j_Cotizacion", tabla.getDetevento().getRgvtId().getRgvtId());
        AdmInforme informe = astslb.getAdmInformeXId(EnInforme.COTIZACION_POR_DETALLE_CLIENTE.getId());
        String rutaLogo = informe.getInfJasperruta() + "/logos/maximus.jpg";
        if (idTipoCliente.equals(EnTipoCliente.KIDS.getId())) {
            rutaLogo = informe.getInfJasperruta() + "/logos/maximus_kids.jpg";
        }

        tabla.setJaspResource(genInfRecurso(hmParametros, informe, 1, rutaLogo));

    }

    public void buscarCotizacionXDetalleCliente_ActionEvent(ActionEvent ae) {
        TablaVntDetalleCliente detalleCliente = (TablaVntDetalleCliente) ae.getComponent().getAttributes().get("detalleCliente");
        lstVentasXDetalle.clear();
        if (detalleCliente.getVntDetallecliente().getDclnId() != null) {
            for (VntDetevento d : pcsfb.getLstVntDeteventoXDetalleCliente(detalleCliente.getVntDetallecliente().getDclnId())) {
                TablaDetalleVentaDTO p = new TablaDetalleVentaDTO();
                p.setDetevento(d);

                p.setNombreArchivo(getNombreArchivoPorEvento(d));
                lstVentasXDetalle.add(p);
            }
        }

    }

    public void buscarServiciosXEdadYSexo(ActionEvent ae) {
        //limpiarFacesMessage();
        lstServiciosXParametros.clear();
        HashMap<Long, TablaVntServicio> mapaServciosAnt = new HashMap<>();
        if (edad == null && idSexoHomenajeado != null && idSexoHomenajeado.equals("-1")) {
            for (VntServicio s : pcsfb.getLstVntServicio(true)) {
                TablaVntServicio tvs = new TablaVntServicio();
                tvs.setVntServicio(s);
                tvs.setSeleccionado(false);
                TablaVntServicio td = mapaServciosSel.get(s.getVsrvId());
                if (td != null) {
                    tvs.setSeleccionado(true);
                    mapaServciosAnt.put(s.getVsrvId(), tvs);
                }
                lstServiciosXParametros.add(tvs);
            }
        } else if (edad != null && idSexoHomenajeado != null && !idSexoHomenajeado.equals("-1")) {
            for (VntRangoedadsexo d : pcsfb.getLstVntRangoedadsexo(edad, idSexoHomenajeado)) {
                for (VntRangoedadsexxservicio s : pcsfb.getLstVntRangoedadsexxservicio(d.getRgedadsexId())) {
                    TablaVntServicio tvs = new TablaVntServicio();
                    tvs.setVntServicio(s.getVntServicio());
                    tvs.setSeleccionado(false);
                    TablaVntServicio td = mapaServciosSel.get(s.getVntServicio().getVsrvId());
                    if (td != null) {
                        tvs.setSeleccionado(true);
                        mapaServciosAnt.put(s.getVntServicio().getVsrvId(), tvs);
                    }
                    lstServiciosXParametros.add(tvs);
                }
                break;
            }
        } else {
            mostrarError("Para consultar por grupos etarios debe digitar edad y sexo", 1);
        }
        mapaServciosSel.clear();
        mapaServciosSel = mapaServciosAnt;

    }

    public void cambioDocumento_ValueChangeEvent(ValueChangeEvent vce) {
        //  limpiarFacesMessage();
        strCltDocumento = (String) vce.getNewValue();
        cargarClienteXDocumento(strCltDocumento, (vntCliente.getClnId()));
    }

    private boolean validarCliente() {
        boolean validar = true;
        if (idTipoDoc.equals("-1")) {
            mostrarError("Seleccione el tipo de documento", 1);
            validar = false;
        }
        if (strCltDocumento == null || strCltDocumento.equals("")) {
            mostrarError("Ingrese el número de identificación", 1);
            validar = false;
        } else {
            for (VntCliente c : pcsfb.getLstClienteXIdentificacion(strCltDocumento, vntCliente.getClnId())) {
                vntCliente = c;
                break;
            }
        }
        ///validar tipo cliente
        if (strCltNombres == null || strCltNombres.equals("")) {
            mostrarError("Ingrese los nombres del cliente", 1);
            validar = false;
        }
        if (!esNit) {
            if (idSexo.equals("-1")) {
                mostrarError("Seleccione el sexo del cliente", 1);
                validar = false;
            }
        }

        if (idTipoCliente.equals(-1)) {
            mostrarError("Seleccione un tipo de cliente", 1);
            validar = false;
        }

        if (idTipoCliente.equals(1)) {
            if (idActEconomica != -1) {
                if (idActEconomica.equals(5)) {
                    mostrarError("El tipo de cliente es Corporativo, por lo cual debe seleccionar la actividad económica que aplique", 1);
                    validar = false;
                }
            }
        }

        if (comoNosConoce == null || comoNosConoce == -1) {
            mostrarError("Como supo de nosotros es requerido", 1);
            validar = false;
        }
        /*  if (idMotivoEventoLLamada == null || idMotivoEventoLLamada == -1) {
         mostrarError("Motivo evento es requerido", 1);
         validar = false;
         }*/
        if (fechaEventoLLamada != null) {
            if (ManejoFecha.compararDates(fechaEventoLLamada, new Date()) == 2) {
                mostrarError("Fecha del evento no debe ser menor que la del sistema", 1);
                validar = false;
            }
        }
        return validar;
    }

    public void grabarCierreLLamadaPorPendienteConfirmacion_ActionEvent(ActionEvent ae) {
// limpiarFacesMessage();
        if (validarForm()) {
            if (comoNosConoce == null || comoNosConoce == -1) {
                mostrarError("Como supo de nosotros es requerido", 1);
                return;
            }
            vntCliente.setTdcId(pcsfb.getRfTipodocXId(idTipoDoc));
            vntCliente.setClnIdentificacion(strCltDocumento);
            vntCliente.setClnNombres(strCltNombres);
            if (esNit) {
                vntCliente.setSexId(null);
                vntCliente.setClnNumhijos(null);
            } else {
                vntCliente.setSexId(pcsfb.getRfSexo(idSexo));
                vntCliente.setClnNumhijos(intCltNumHijos);
            }
            vntCliente.setClnFechanace(datCltFechaNac);
            vntCliente.setClnDiereccion(strCltDireccion);
            vntCliente.setClnFijo(strCltTelefono);
            vntCliente.setClnCelular(strCltCelular);
            vntCliente.setClnCorreoe(strCltCorreo);
            vntCliente.setClnContacto(strCltContacto);
            vntCliente.setClnAlias(strCltAlias);
            vntCliente.setTclId(pcsfb.getVntRfTipoclienteXId(idTipoCliente));
            if (idActEconomica == null || idActEconomica == -1) {
                vntCliente.setAteId(null);
            } else {
                vntCliente.setAteId(pcsfb.getVntActeconomicaXId(idActEconomica));
            }

            vntCliente.setClnEstado(Boolean.TRUE);
            if (vntCliente.getClnFechaproceso() == null) {
                vntCliente.setClnFechaproceso(new Date());
            }
            vntCliente = pcsfb.editarCliente(vntCliente);
            VntRegistroLlamada v = new VntRegistroLlamada();
            v.setAdmCrgxcol(getPrincipalJSFBean().getAdmCrgxcolActivo());
            v.setRegllamEdad(edad);
            v.setRegllamFechaproceso(new Date());
            v.setRegllamObservacion(registroLlamadaObservacion);
            if (idMotivoEventoLLamada == null || idMotivoEventoLLamada.equals("-1")) {
                v.setRfMotivoevento(null);
            } else {
                v.setRfMotivoevento(pcsfb.getRfMotivoeventoXId(idMotivoEventoLLamada));
            }
            v.setRegllamFechaevento(fechaEventoLLamada);
            v.setRegllamNombrehomenajeado(homenajeadoLLamada);

            v.setRfComocontacto(pcsfb.getRfComocontacto(comoNosConoce));
            if (idSexoHomenajeado == null || idSexoHomenajeado.equals("-1")) {
                v.setRfSexo(null);
            } else {
                v.setRfSexo(pcsfb.getRfSexo(idSexoHomenajeado));
            }

            v.setVntCliente(vntCliente);
            v.setRfTipocierrellamada(pcsfb.getRfTipocierrellamada(1));
            v = pcsfb.editarVntRegistroLlamada(v);
            tablaVntClienteSel.setVntCliente(vntCliente);
            mostrarError("Por favor hacer peticion del cliente...!", 3);
            // numPanel = PANEL_CLIENTE;
            cargarDatosCliente();
            numPanel = PANEL_CLIENTE;
            cargarListaDetalleClienteExistente();

        }

    }

    public void grabarCierreLLamadaPorEnvioCorreo_ActionEvent(ActionEvent ae) {

        if (validarCliente()) {

            boolean validar = true;
            boolean ejecutarExcel = true;
            if (strCltCorreo == null || strCltCorreo.equals("")) {
                mostrarError("Para esta acción es requerido en correo electronico", 1);
                validar = false;
            }
            if ((asuntoMensajeExcel == null || asuntoMensajeExcel.trim().equals("")) && (mensajeExcel == null || mensajeExcel.trim().equals("")) && fileInfo == null) {
                if (!validaFormServicio()) {
                    validar = false;
                } else {
                    ejecutarExcel = false;
                }
            } else {
                if (asuntoMensajeExcel == null || asuntoMensajeExcel.trim().equals("")) {
                    mostrarError("Asunto mensaje es requerido. ", 1);
                    validar = false;
                    ejecutarExcel = false;
                }
                if (mensajeExcel == null || mensajeExcel.trim().equals("")) {
                    mostrarError("Mensaje es requerido. ", 1);
                    validar = false;
                    ejecutarExcel = false;
                }
                if (fileInfo == null) {
                    mostrarError("Archivo es requerido. ", 1);
                    validar = false;
                    ejecutarExcel = false;
                }
            }

            if (validar) {
                vntCliente.setTdcId(pcsfb.getRfTipodocXId(idTipoDoc));
                vntCliente.setClnIdentificacion(strCltDocumento);
                vntCliente.setClnNombres(strCltNombres);
                if (esNit) {
                    vntCliente.setSexId(null);
                    vntCliente.setClnNumhijos(null);
                } else {
                    vntCliente.setSexId(pcsfb.getRfSexo(idSexo));
                    vntCliente.setClnNumhijos(intCltNumHijos);
                }
                vntCliente.setClnFechanace(datCltFechaNac);
                vntCliente.setClnDiereccion(strCltDireccion);
                vntCliente.setClnFijo(strCltTelefono);
                vntCliente.setClnCelular(strCltCelular);
                vntCliente.setClnCorreoe(strCltCorreo);
                vntCliente.setClnContacto(strCltContacto);
                vntCliente.setClnAlias(strCltAlias);
                vntCliente.setTclId(pcsfb.getVntRfTipoclienteXId(idTipoCliente));
                if (idActEconomica == null || idActEconomica == -1) {
                    vntCliente.setAteId(null);
                } else {
                    vntCliente.setAteId(pcsfb.getVntActeconomicaXId(idActEconomica));
                }

                vntCliente.setClnEstado(Boolean.TRUE);
                if (vntCliente.getClnFechaproceso() == null) {
                    vntCliente.setClnFechaproceso(new Date());
                }
                vntCliente = pcsfb.editarCliente(vntCliente);
                List<TablaVntDetalleCliente> seleccionados = new ArrayList<>();
                for (TablaVntDetalleCliente tvdc : lstTablaVntDetalleCliente) {
                    if (tvdc.getVntDetallecliente().getClnId() == null) {
                        tvdc.getVntDetallecliente().setClnId(vntCliente);
                    }
                    tvdc.setVntDetallecliente(pcsfb.editarDetalleCliente(tvdc.getVntDetallecliente()));
                    if (tvdc.isSeleccionado()) {
                        seleccionados.add(tvdc);
                    }
                }
                if (seleccionados.isEmpty() && !blnEnviarCorreoAPrincipal) {
                    mostrarError("Debe seleccionar algún contacto para enviar correo", 1);
                    return;
                }

                VntRegistroLlamada v = new VntRegistroLlamada();
                v.setAdmCrgxcol(getPrincipalJSFBean().getAdmCrgxcolActivo());
                v.setRegllamEdad(edad);
                v.setRegllamFechaproceso(new Date());
                if (idMotivoEventoLLamada == null || idMotivoEventoLLamada.equals(-1)) {
                    v.setRfMotivoevento(null);
                } else {
                    v.setRfMotivoevento(pcsfb.getRfMotivoeventoXId(idMotivoEventoLLamada));
                }
                v.setRegllamFechaevento(fechaEventoLLamada);
                v.setRegllamNombrehomenajeado(homenajeadoLLamada);
                v.setRegllamObservacion(registroLlamadaObservacion);

                List<String> pdfs = new ArrayList<>();
                v.setRfComocontacto(pcsfb.getRfComocontacto(comoNosConoce));
                if (idSexoHomenajeado == null || idSexoHomenajeado.equals("-1")) {
                    v.setRfSexo(null);
                } else {
                    v.setRfSexo(pcsfb.getRfSexo(idSexoHomenajeado));
                }

                v.setVntCliente(vntCliente);
                v.setRfTipocierrellamada(pcsfb.getRfTipocierrellamada(2));
                v = pcsfb.editarVntRegistroLlamada(v);
                tablaVntClienteSel.setVntCliente(vntCliente);
                mostrarError("Grabación exitosa...!", 3);
                numPanel = PANEL_BUSCAR_CLIENTE;
                if (!pdfs.isEmpty()) {
                    envioCorreo(pdfs, seleccionados);
                }
                if (ejecutarExcel) {
//                    vntDetevento.getVdeContcopia();
                    List<String> lstContactoCopia = new ArrayList();
                    if (vntDetevento.getVdeContcopia() != null) {
                        lstContactoCopia = Arrays.asList(vntDetevento.getVdeContcopia().split(";", -1));
                    }
                    envioCorreoExcel(seleccionados, lstContactoCopia);
                }
                buscarGen_ActionEvent(ae);

            }
            //proceso de envio de correo electronico 

            //pendiente
        }

    }

    public void grabarCierreLLamadaPorLlamadaPerdida_ActionEvent(ActionEvent ae) {
// limpiarFacesMessage();
        if (validarCliente()) {

            vntCliente.setTdcId(pcsfb.getRfTipodocXId(idTipoDoc));
            vntCliente.setClnIdentificacion(strCltDocumento);
            vntCliente.setClnNombres(strCltNombres);
            if (esNit) {
                vntCliente.setSexId(null);
                vntCliente.setClnNumhijos(null);
            } else {
                vntCliente.setSexId(pcsfb.getRfSexo(idSexo));
                vntCliente.setClnNumhijos(intCltNumHijos);
            }
            vntCliente.setClnFechanace(datCltFechaNac);
            vntCliente.setClnDiereccion(strCltDireccion);
            vntCliente.setClnFijo(strCltTelefono);
            vntCliente.setClnCelular(strCltCelular);
            vntCliente.setClnCorreoe(strCltCorreo);
            vntCliente.setClnContacto(strCltContacto);
            vntCliente.setClnAlias(strCltAlias);
            vntCliente.setTclId(pcsfb.getVntRfTipoclienteXId(idTipoCliente));
            if (idActEconomica == null || idActEconomica == -1) {
                vntCliente.setAteId(null);
            } else {
                vntCliente.setAteId(pcsfb.getVntActeconomicaXId(idActEconomica));
            }

            vntCliente.setClnEstado(Boolean.TRUE);
            if (vntCliente.getClnFechaproceso() == null) {
                vntCliente.setClnFechaproceso(new Date());
            }
            vntCliente = pcsfb.editarCliente(vntCliente);
            VntRegistroLlamada v = new VntRegistroLlamada();
            v.setAdmCrgxcol(getPrincipalJSFBean().getAdmCrgxcolActivo());
            v.setRegllamEdad(edad);
            v.setRegllamFechaproceso(new Date());
            v.setRegllamObservacion(registroLlamadaObservacion);

            if (idMotivoEventoLLamada == null || idMotivoEventoLLamada.equals("-1")) {
                v.setRfMotivoevento(null);
            } else {
                v.setRfMotivoevento(pcsfb.getRfMotivoeventoXId(idMotivoEventoLLamada));
            }
            v.setRegllamFechaevento(fechaEventoLLamada);
            v.setRegllamNombrehomenajeado(homenajeadoLLamada);
            v.setRfComocontacto(pcsfb.getRfComocontacto(comoNosConoce));
            if (idSexoHomenajeado == null || idSexoHomenajeado.equals("-1")) {
                v.setRfSexo(null);
            } else {
                v.setRfSexo(pcsfb.getRfSexo(idSexoHomenajeado));
            }

            v.setVntCliente(vntCliente);
            v.setRfTipocierrellamada(pcsfb.getRfTipocierrellamada(3));
            v = pcsfb.editarVntRegistroLlamada(v);
            tablaVntClienteSel.setVntCliente(vntCliente);
            mostrarError("Grabación exitosa...!", 3);
            numPanel = PANEL_BUSCAR_CLIENTE;
            buscarGen_ActionEvent(ae);
        }

    }

    public void grabarCierreLLamadaPorNuevaCotizacion_ActionEvent(ActionEvent ae) {
// limpiarFacesMessage();
        if (validarForm()) {
            if (comoNosConoce == null || comoNosConoce == -1) {
                mostrarError("Como supo de nosotros es requerido", 1);
                return;
            }
            vntCliente.setTdcId(pcsfb.getRfTipodocXId(idTipoDoc));
            vntCliente.setClnIdentificacion(strCltDocumento);
            vntCliente.setClnNombres(strCltNombres);
            if (esNit) {
                vntCliente.setSexId(null);
                vntCliente.setClnNumhijos(null);
            } else {
                vntCliente.setSexId(pcsfb.getRfSexo(idSexo));
                vntCliente.setClnNumhijos(intCltNumHijos);
            }
            vntCliente.setClnFechanace(datCltFechaNac);
            vntCliente.setClnDiereccion(strCltDireccion);
            vntCliente.setClnFijo(strCltTelefono);
            vntCliente.setClnCelular(strCltCelular);
            vntCliente.setClnCorreoe(strCltCorreo);
            vntCliente.setClnContacto(strCltContacto);
            vntCliente.setClnAlias(strCltAlias);
            vntCliente.setTclId(pcsfb.getVntRfTipoclienteXId(idTipoCliente));
            if (idActEconomica == null || idActEconomica == -1) {
                vntCliente.setAteId(null);
            } else {
                vntCliente.setAteId(pcsfb.getVntActeconomicaXId(idActEconomica));
            }

            vntCliente.setClnEstado(Boolean.TRUE);
            if (vntCliente.getClnFechaproceso() == null) {
                vntCliente.setClnFechaproceso(new Date());
            }
            vntCliente = pcsfb.editarCliente(vntCliente);
            VntRegistroLlamada v = new VntRegistroLlamada();
            v.setAdmCrgxcol(getPrincipalJSFBean().getAdmCrgxcolActivo());
            v.setRegllamEdad(edad);
            v.setRegllamFechaproceso(new Date());
            v.setRegllamObservacion(registroLlamadaObservacion);
            if (idMotivoEventoLLamada == null || idMotivoEventoLLamada.equals("-1")) {
                v.setRfMotivoevento(null);
            } else {
                v.setRfMotivoevento(pcsfb.getRfMotivoeventoXId(idMotivoEventoLLamada));
            }
            v.setRegllamFechaevento(fechaEventoLLamada);
            v.setRegllamNombrehomenajeado(homenajeadoLLamada);

            v.setRfComocontacto(pcsfb.getRfComocontacto(comoNosConoce));
            if (idSexoHomenajeado == null || idSexoHomenajeado.equals("-1")) {
                v.setRfSexo(null);
            } else {
                v.setRfSexo(pcsfb.getRfSexo(idSexoHomenajeado));
            }

            v.setVntCliente(vntCliente);
            v.setRfTipocierrellamada(pcsfb.getRfTipocierrellamada(4));
            v = pcsfb.editarVntRegistroLlamada(v);
            tablaVntClienteSel.setVntCliente(vntCliente);
            mostrarError("Grabación exitosa...!", 4);
            // numPanel = PANEL_CLIENTE;
            cargarDatosCliente();
            numPanel = PANEL_CLIENTE;
            cargarListaDetalleClienteExistente();

        }

    }

    public void selTodoServicioLLamadas_ValueChange(ValueChangeEvent vce) {
        // limpiarFacesMessage();
        Boolean bln = (Boolean) vce.getNewValue();
        // selTodoLst(lstTablaVntServicio, bln);
        if (!bln.booleanValue()) {
            mapaServciosSel.clear();
            selTodoLst(lstServiciosXParametros, bln);
        } else {
            for (TablaVntServicio s : lstServiciosXParametros) {
                s.setSeleccionado(bln);
                if (bln.booleanValue()) {
                    mapaServciosSel.put(s.getVntServicio().getVsrvId(), s);
                } else {
                    mapaServciosSel.remove(s.getVntServicio().getVsrvId());
                }
            }
        }
    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        // limpiarFacesMessage();
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        blnMostrarPanel = false;
        limpiarCamposBuscar();
        limpiarCamposCliente();
        limpiarCamposDetalleCliente();
        limpiarCamposDetalleEvento();
        limpiarCamposServicioXVenta();
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
    }

    public void selServicio_ValueChange(ValueChangeEvent vce) {
        // limpiarFacesMessage();
        Boolean bln = (Boolean) vce.getNewValue();
        Map map = vce.getComponent().getAttributes();
        TablaVntServicio s = (TablaVntServicio) map.get("tsxvs");
        s.setSeleccionado(bln);
        if (bln.booleanValue()) {
            mapaServciosSel.put(s.getVntServicio().getVsrvId(), s);
        } else {
            mapaServciosSel.remove(s.getVntServicio().getVsrvId());
        }

    }

    public void cambioPrecioServicio_ValueChange(ValueChangeEvent vce) {
        // limpiarFacesMessage();
        BigDecimal bln = (BigDecimal) vce.getNewValue();
        Map map = vce.getComponent().getAttributes();
        TablaVntServicio s = (TablaVntServicio) map.get("tsxvs");
        s.setBigdPrecioCliente(bln);
        if (s.isSeleccionado()) {
            mapaServciosSel.put(s.getVntServicio().getVsrvId(), s);
        } else {
            mapaServciosSel.remove(s.getVntServicio().getVsrvId());
        }

    }

    public void cambioCantidadServicio_ValueChange(ValueChangeEvent vce) {
        Map map = vce.getComponent().getAttributes();
        TablaVntServicio s = (TablaVntServicio) map.get("tsxvs");
        try {
            Integer bln = (Integer) vce.getNewValue();

            s.setCantidadSrv(bln);
        } catch (ClassCastException cce) {
            Long bln = (Long) vce.getNewValue();

            s.setCantidadSrv(bln.intValue());
        }
        if (s.isSeleccionado()) {
            mapaServciosSel.put(s.getVntServicio().getVsrvId(), s);
        } else {
            mapaServciosSel.remove(s.getVntServicio().getVsrvId());
        }

    }
    //<editor-fold defaultstate="collapsed" desc="Cliente">

    private void cargarDatosCliente() {
        cargarListaTipoDoc();
        cargarListaSexoClt();
        cargarListaTipoCliente();
        cargarListaActividadEconomica();
        cargarListaSexoDetClt();
        cargarListaParentesco();
        limpiarCamposBuscar();
        limpiarCamposCliente();
        limpiarCamposDetalleCliente();
        limpiarCamposDetalleEvento();
        limpiarCamposServicioXVenta();
        vntCliente = tablaVntClienteSel.getVntCliente();
        idTipoDoc = tablaVntClienteSel.getVntCliente().getTdcId().getTdcId();
        esNit = tablaVntClienteSel.getVntCliente().getTdcId().isTdcEsnit();
        strCltDocumento = tablaVntClienteSel.getVntCliente().getClnIdentificacion();
        strCltNombres = tablaVntClienteSel.getVntCliente().getClnNombres();
        strCltAlias = tablaVntClienteSel.getVntCliente().getClnAlias();
        idSexo = "-1";
        if (tablaVntClienteSel.getVntCliente().getSexId() != null) {
            idSexo = tablaVntClienteSel.getVntCliente().getSexId().getSexId();
        }

        intCltNumHijos = tablaVntClienteSel.getVntCliente().getClnNumhijos();
        datCltFechaNac = tablaVntClienteSel.getVntCliente().getClnFechanace();
        strCltDireccion = tablaVntClienteSel.getVntCliente().getClnDiereccion();
        strCltTelefono = tablaVntClienteSel.getVntCliente().getClnFijo();
        strCltCelular = tablaVntClienteSel.getVntCliente().getClnCelular();
        strCltCorreo = tablaVntClienteSel.getVntCliente().getClnCorreoe();
        strCltContacto = tablaVntClienteSel.getVntCliente().getClnContacto();
        //idTipoCliente = tablaVntClienteSel.getVntCliente().getTclId().getTclId();

        idActEconomica = -1;
        if (tablaVntClienteSel.getVntCliente().getAteId() != null) {
            idActEconomica = tablaVntClienteSel.getVntCliente().getAteId().getAteId();
        }
        this.cambioTipoDoc();
        blnNuevo = false;

    }

    public void btnNuevoCliente_ActionEvent(ActionEvent ae) {
        // limpiarFacesMessage();
        numPanel = PANEL_CLIENTE;
        blnNuevo = true;
        vntCliente = new VntCliente();
        limpiarCamposBuscar();
        limpiarCamposCliente();
        limpiarCamposDetalleCliente();
        limpiarCamposDetalleEvento();
        limpiarCamposServicioXVenta();
        cargarListaTipoDoc();
        cargarListaSexoClt();
        cargarListaTipoCliente();
        cargarListaActividadEconomica();
        cargarListaSexoDetClt();
        cargarListaParentesco();
    }

     public void rowDtEliminarArchivo_AE(){
      
       lstArchivosCargar.remove(archivosAdjuntoSel);
     }
    public void btnNuevoLamada_ActionEvent(ActionEvent ae) {
        //  limpiarFacesMessage();
        numPanel = PANEL_LLAMADA;
        blnNuevo = true;
        vntCliente = new VntCliente();
        // comoNosConoce = -1;
        comoNosConoce = comoNosConoceDefecto;
        edad = null;
        idSexoHomenajeado = "-1";
        // idMotivoEventoLLamada = -1;
        idMotivoEventoLLamada = idMotivoEventoDefecto;
        fechaEventoLLamada = null;
        homenajeadoLLamada = null;
        fileInfo = null;
        mensajeExcel = null;
        asuntoMensajeExcel = null;
        blnEnviarCorreoAPrincipal = false;
        limpiarCamposBuscar();
        limpiarCamposCliente();
        limpiarCamposDetalleCliente();
        limpiarCamposDetalleEvento();
        limpiarCamposServicioXVenta();
        cargarListaTipoDoc();
        cargarListaSexoClt();
        cargarListaTipoCliente();
        cargarListaActividadEconomica();
        cargarListaComoContacto();
        // cargarListaMotivoEvento();
        //cargarListaSexoDetClt();
        //cargarListaParentesco();
    }

    public void rowDtSelCliente_ActionEvent(ActionEvent ae) {
        //limpiarFacesMessage();
        Map map = ae.getComponent().getAttributes();
        tablaVntClienteSel = (TablaVntCliente) map.get("tcs");
        cargarDatosCliente();
        numPanel = PANEL_CLIENTE;
        cargarListaDetalleClienteExistente();
    }

    public void rowDtSelClienteLLamada_ActionEvent(ActionEvent ae) {
        // limpiarFacesMessage();
        Map map = ae.getComponent().getAttributes();
        tablaVntClienteSel = (TablaVntCliente) map.get("tcs");
        cargarDatosCliente();
        numPanel = PANEL_LLAMADA;
        cargarListaRegistroLLamadas();
        cargarListaComoContacto();
        // cargarListaMotivoEvento();
        idSexoHomenajeado = "-1";
        fileInfo = null;
        mensajeExcel = null;
        asuntoMensajeExcel = null;
        edad = null;
        //  comoNosConoce = -1;
        comoNosConoce = comoNosConoceDefecto.intValue();
        //idMotivoEventoLLamada = -1;
        idMotivoEventoLLamada = idMotivoEventoDefecto.intValue();
        fechaEventoLLamada = null;
        homenajeadoLLamada = null;
        blnEnviarCorreoAPrincipal = false;
        cargarListaDetalleClienteExistente();
    }

    public void btnGrabarCliente_ActionEvent(ActionEvent ae) {
        // limpiarFacesMessage();
        grabarCliente();
        cargarListaClientes();
    }

    public void btnRegresarBuscarClt_ActionEvent(ActionEvent ae) {
        //  limpiarFacesMessage();
        numPanel = PANEL_BUSCAR_CLIENTE;
        vntCliente = new VntCliente();
        limpiarCamposCliente();
        limpiarCamposDetalleCliente();

        lstTablaVntCliente.clear();
    }

    public void btnGrabarDetalleCliente_ActionEvent(ActionEvent ae) {
        // limpiarFacesMessage();
        grabarDetalleCliente();
    }
    public void rowDtDetalleClienteBorrar_ActionEvent(ActionEvent ae){
       Map map = ae.getComponent().getAttributes();
        tablaVntDetalleClienteSel = (TablaVntDetalleCliente) map.get("tdcs");
         numPanel = PANEL_EVENTO;
         VntCliente clienteSel = tablaVntDetalleClienteSel.getVntDetallecliente().getClnId();
        
    }
    public void rowDtDetalleCliente_ActionEvent(ActionEvent ae) {
        //limpiarFacesMessage();
        Map map = ae.getComponent().getAttributes();
        tablaVntDetalleClienteSel = (TablaVntDetalleCliente) map.get("tdcs");
        numPanel = PANEL_EVENTO;
        cargarCiuXDept();

        String[] salida = tablaVntDetalleClienteSel.getVntDetallecliente().getClnId().getClnNombres().split(" ");
        switch (salida.length) {
            case 0:
                strDevNombres = null;
                strDevApellidos = null;
                break;
            case 1:
                strDevNombres = salida[0];
                strDevApellidos = null;
                break;
            case 2:
                strDevNombres = salida[0];
                strDevApellidos = salida[1];
                break;
            case 3:
                strDevNombres = salida[0] + " " + salida[1];
                strDevApellidos = salida[2];
                break;
            default:
                strDevNombres = salida[0] + " " + salida[1];
                strDevApellidos = salida[2] + " " + salida[3];
                break;
        }

        strDevCelular1 = tablaVntDetalleClienteSel.getVntDetallecliente().getClnId().getClnCelular();

        strDevTelefono1 = tablaVntDetalleClienteSel.getVntDetallecliente().getClnId().getClnFijo();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Evento">    

    public void selTipoDoc_ValueChangeEvent(ValueChangeEvent vce) {

        idTipoDoc = (String) vce.getNewValue();

        cambioTipoDoc();

    }

    private void cambioTipoDoc() {
        esNit = false;
        lstParentesco.clear();
        lstParentesco.add(itemSeleccioneInt);

        if (!idTipoDoc.equals("-1")) {
            RfTipodoc sex = pcsfb.getRfTipodocXId(idTipoDoc);
            esNit = sex.isTdcEsnit();
            int tipo = 1;
            if (esNit) {
                tipo = 2;
            }
            if (idParentesco != -1) {
                if (parametrosParentezco.get(tipo).get(idParentesco) == null) {
                    idParentesco = -1;
                }

            }
            for (Map.Entry<Integer, RfParentezco> par : parametrosParentezco.get(tipo).entrySet()) {
                lstParentesco.add(new SelectItem(par.getKey(), par.getValue().getPrtNombre()));
            }
        }
    }

    public void selDepartamento_ValueChangeEvent(ValueChangeEvent vce) {

        depId = (String) vce.getNewValue();
        ciuId = -1L;
        cargarCiuXDept();
    }

    public void btnGrabarDetalleEvento_ActionEvent(ActionEvent ae) {

        grabarDetalleEvento();
        cargarListaServicio();
    }

    public void btnRegresarCliente_ActionEvent(ActionEvent ae) {

        numPanel = PANEL_CLIENTE;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Servicio por venta">   
    public void selTodoServicio_ValueChange(ValueChangeEvent vce) {

        Boolean bln = (Boolean) vce.getNewValue();
        // selTodoLst(lstTablaVntServicio, bln);
        if (!bln.booleanValue()) {
            mapaServciosSel.clear();
            selTodoLst(lstTablaVntServicio, bln);
        } else {
            for (TablaVntServicio s : lstTablaVntServicio) {
                s.setSeleccionado(bln);
                if (bln) {
                    mapaServciosSel.put(s.getVntServicio().getVsrvId(), s);
                } else {
                    mapaServciosSel.remove(s.getVntServicio().getVsrvId());
                }
            }
        }
    }

    public void btnGrabarServicioXVenta_ActionEvent(ActionEvent ae) {
        grabarServicioXVenta();
    }

    public void btnEliminarServicioXVenta_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntSrvXVentaSel = (TablaVntSrvXVenta) map.get("tsxvs");
        try {
            pcsfb.eliminarServxventa(tablaVntSrvXVentaSel.getVntServxventa());
            cargarListaServicioXVenta();
        } catch (EJBException ejbe) {
            mostrarError("No se pudo eliminar el servicio", 1);
        }
        cargarTotales();
    }

    public void cargarDatosPopUpDescuento_ActionEvent(ActionEvent ae) {
        ///cerrarVenta();
        if (lstTablaVntSrvXVenta.isEmpty()) {
            JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "dialogCotizacion.hide();");
            mostrarError("Esta cotización no tiene servicios asociados");
            return;
        }
        valoDescVenta = null;
        valorTotalVenta = BigDecimal.ZERO;
        for (VntServxventa vs : pcsfb.getLstServxventaXVnt(vntRegistroventa.getRgvtId())) {
            valorTotalVenta = valorTotalVenta.add(vs.getSrvxventValtotalclnt());
        }
        vntRegistroventa.setRgvtValorventa(valorTotalVenta);

    }

    public void cambioDescuento(ValueChangeEvent vce) {
        valoDescVenta = ((BigDecimal) vce.getNewValue());
        //vntRegistroventa.getRgvtDescuento()

        if (valoDescVenta == null) {
            vntRegistroventa.setRgvtValorventa(valorTotalVenta);
            vntRegistroventa.setRgvtDescuento(BigDecimal.ZERO);
        } else {
            BigDecimal cien = new BigDecimal(100);
            if (valoDescVenta.compareTo(cien) == 1) {
                valoDescVenta = ((BigDecimal) vce.getOldValue());

            } else {
                BigDecimal valor = valorTotalVenta.multiply(valoDescVenta).divide(cien);
                vntRegistroventa.setRgvtDescuento(valor);
                vntRegistroventa.setRgvtValorventa(valorTotalVenta.subtract(valor));
            }
        }

    }

    public void cambioDescuentoAplicarTodos(ValueChangeEvent vce) {
        valoDescVenta = ((BigDecimal) vce.getNewValue());
        //vntRegistroventa.getRgvtDescuento()

        if (valoDescVenta != null) {
            BigDecimal cien = new BigDecimal(100);
            if (valoDescVenta.compareTo(cien) == 1) {
                valoDescVenta = ((BigDecimal) vce.getOldValue());
            } else {
                vntRegistroventa.setRgvtDescuento(BigDecimal.ZERO);
                for (TablaVntSrvXVenta t : lstTablaVntSrvXVenta) {
                    BigDecimal valor = t.getVntServxventa().getSrvxventValtotalclnt().multiply(valoDescVenta).divide(cien);
                    t.getVntServxventa().setSrvxventDescuento(valor);
                    t.getVntServxventa().setSrvxventPorcentajeDesc(valoDescVenta);
                    if (t.getVntServxventa().getSrvxventDescuento() != null) {
                        vntRegistroventa.setRgvtDescuento(vntRegistroventa.getRgvtDescuento().add(t.getVntServxventa().getSrvxventDescuento()));
                    }

                }
                valSubtotal = vntRegistroventa.getRgvtValorventa().subtract(vntRegistroventa.getRgvtDescuento());
            }

        }

    }

    public void cambioDescuentoXServicio(ValueChangeEvent vce) {
        TablaVntSrvXVenta tsxvs = (TablaVntSrvXVenta) vce.getComponent().getAttributes().get("tsxvs");
        tsxvs.getVntServxventa().setSrvxventPorcentajeDesc(((BigDecimal) vce.getNewValue()));
        //vntRegistroventa.getRgvtDescuento()

        if (tsxvs.getVntServxventa().getSrvxventPorcentajeDesc() == null) {
            tsxvs.getVntServxventa().setSrvxventDescuento(BigDecimal.ZERO);
        } else {
            BigDecimal cien = new BigDecimal(100);
            if (tsxvs.getVntServxventa().getSrvxventPorcentajeDesc().compareTo(cien) == 1) {
                tsxvs.getVntServxventa().setSrvxventPorcentajeDesc((BigDecimal) vce.getOldValue());

            } else {
                BigDecimal valor = tsxvs.getVntServxventa().getSrvxventValtotalclnt().multiply(tsxvs.getVntServxventa().getSrvxventPorcentajeDesc()).divide(cien);
                tsxvs.getVntServxventa().setSrvxventDescuento(valor);
            }
        }
        cargarTotales();

    }

    private void cargarTotales() {
        vntRegistroventa.setRgvtValorventa(BigDecimal.ZERO);
        vntRegistroventa.setRgvtDescuento(BigDecimal.ZERO);
        valSubtotal = null;
        cantidadServiciosSeleccionados = 0;
        for (TablaVntSrvXVenta t : lstTablaVntSrvXVenta) {
            cantidadServiciosSeleccionados += t.getVntServxventa().getSrvxventCantidad();
            vntRegistroventa.setRgvtValorventa(vntRegistroventa.getRgvtValorventa().add(t.getVntServxventa().getSrvxventValtotalclnt()));
            if (t.getVntServxventa().getSrvxventDescuento() != null) {
                vntRegistroventa.setRgvtDescuento(vntRegistroventa.getRgvtDescuento().add(t.getVntServxventa().getSrvxventDescuento()));
            }

        }

        valSubtotal = vntRegistroventa.getRgvtValorventa().subtract(vntRegistroventa.getRgvtDescuento());
    }

    public void btnCerrarVenta_ActionEvent(ActionEvent ae) {
        cerrarVenta();
    }

    //</editor-fold>
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">
    private void cargarCargos() {
        lstCargosContacto.clear();
        lstCargosContacto.add(itemSeleccioneInt);
        for (RfCargocontacto c : pcsfb.getLstRfCargocontactoXEstado(true)) {
            lstCargosContacto.add(new SelectItem(c.getCargoId(), c.getCargoDesc()));
        }
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        lstTablaVntCliente.clear();
        List<VntCliente> listaConsulta;
        if (idTipoCliente.equals(EnTipoCliente.KIDS.getId())) {
            listaConsulta = pcsfb.getLstClienteXTextoKids(strBuscarCln.toUpperCase(), EnTipoCliente.KIDS.getId());
        } else {
            listaConsulta = pcsfb.getLstClienteXTexto(strBuscarCln.toUpperCase(), EnTipoCliente.KIDS.getId());
        }
        for (VntCliente cliente : listaConsulta) {
            lstTablaVntCliente.add(new TablaVntCliente(cliente));
        }
    }

    @Override
    public boolean grabarPaso() {
        return false;
    }

    @Override
    public boolean validarForm() {
        if (idTipoDoc.equals("-1")) {
            mostrarError("Seleccione el tipo de documento", 1);
            return false;
        }
        if (strCltDocumento == null || strCltDocumento.equals("")) {
            mostrarError("Ingrese el número de identificación", 1);
            return false;
        } else {
            for (VntCliente c : pcsfb.getLstClienteXIdentificacion(strCltDocumento, vntCliente.getClnId())) {
                vntCliente = c;
                break;
            }
        }

        if (strCltNombres == null || strCltNombres.equals("")) {
            mostrarError("Ingrese los nombres del cliente", 1);
            return false;
        }
        if (!esNit) {
            if (idSexo.equals("-1")) {
                mostrarError("Seleccione el sexo del cliente", 1);
                return false;
            }
        }
        if (strCltDireccion == null || strCltDireccion.equals("")) {
            mostrarError("Ingrese la dirección", 1);
            return false;
        }
        if (strCltCelular == null || strCltCelular.equals("")) {
            mostrarError("Ingrese un número celular", 1);
            return false;
        }
        if (idTipoCliente.equals(-1)) {
            mostrarError("Seleccione un tipo de cliente", 1);
            return false;
        }

        if (idActEconomica.equals(5)) {
            mostrarError("El tipo de cliente es Corporativo o SAS, por lo cual debe seleccionar la actividad económica que aplique", 1);
            return false;
        }

        return true;
    }

    private boolean validaFormDetalleCliente() {
        boolean error = false;

        if (strDetNombre1 == null || strDetNombre1.equals("")) {
            mostrarError("Ingrese Nombre", 1);
            error = true;
        }

        if (idParentesco.equals(-1)) {
            mostrarError("Seleccione el " + (this.esNit ? "Area" : "parentesco"), 1);
            error = true;
        }
        if (!this.esNit) {
            if (idSexoDetClt.equals("-1")) {
                mostrarError("Seleccione el sexo", 1);
                error = true;
            }
        }
        if (datDetFechaNac == null) {
            mostrarError("Ingrese la fecha de " + (this.esNit ? "Nacimiento" : "nacimiento"), 1);
            error = true;
        }
        if (dcln_direccion == null || dcln_direccion.equals("")) {
            mostrarError("Ingrese la dirección", 1);
            error = true;
        }
        if (numPanel == PANEL_LLAMADA) {

        }
        //  if (!(strDetCorreo == null || strDetCorreo.equals(""))) {
        //  if(ValidarEmail.)
        //}
        return !error;
    }

    private boolean validaFormEvento() {
        boolean error = false;
        if (ciuId.equals(-1L)) {
            mostrarError("Seleccione la ciudad", 1);
            error = true;
        }
        if (strDevNombres == null || strDevNombres.equals("")) {
            mostrarError("Ingrese el nombre de contacto", 1);
            error = true;
        }
        if (strDevApellidos == null || strDevApellidos.equals("")) {
            mostrarError("Ingrese el apellido del contacto", 1);
            error = true;
        }
        if (idMotivoEvento.equals(-1)) {
            mostrarError("Seleccione el motivo del evento", 1);
            error = true;
        }
        if (strDevDireccion == null || strDevDireccion.equals("")) {
            mostrarError("Ingrese la dirección del evento", 1);
            error = true;
        }
        if (datDevFecha == null) {
            mostrarError("Ingrese la fecha el evento", 1);
            error = true;
        }

        if (validarFechaMayorXDia(datDevFecha, new Date())) {
            mostrarError("La fecha del evento no debe ser menor a la fecha actual", 1);
            return false;
        }
        if (datDevHoraInicio == null) {
            mostrarError("Ingrese la hora inicial del evento", 1);
            error = true;
        }
        if (strDevTelefono1 == null || strDevTelefono1.equals("")) {
            mostrarError("Ingrese un número de teléfono", 1);
            error = true;
        }

        return !error;
    }

    private boolean validaFormServicio() {
        if (mapaServciosSel == null) {
            mapaServciosSel = new HashMap<>();
        }
        if (mapaServciosSel.isEmpty()) {
            mostrarError("Debe seleccionar mínimo un servicio.", 1);
            return false;
        }
        return true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Propiedades">   
    /**
     * @return the comoNosConoce
     */
    public Integer getComoNosConoce() {
        return comoNosConoce;
    }

    /**
     * @param comoNosConoce the comoNosConoce to set
     */
    public void setComoNosConoce(Integer comoNosConoce) {
        this.comoNosConoce = comoNosConoce;
    }

    /**
     * @return the edad
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    /**
     * @return the idSexoHomenajeado
     */
    public String getIdSexoHomenajeado() {
        return idSexoHomenajeado;
    }

    /**
     * @param idSexoHomenajeado the idSexoHomenajeado to set
     */
    public void setIdSexoHomenajeado(String idSexoHomenajeado) {
        this.idSexoHomenajeado = idSexoHomenajeado;
    }

    /**
     * @return the lstComoContacto
     */
    public List<SelectItem> getLstComoContacto() {
        return lstComoContacto;
    }

    /**
     * @param lstComoContacto the lstComoContacto to set
     */
    public void setLstComoContacto(List<SelectItem> lstComoContacto) {
        this.lstComoContacto = lstComoContacto;
    }

    /**
     * @return the lstVntRegistroLlamada
     */
    public List<VntRegistroLlamada> getLstVntRegistroLlamada() {
        return lstVntRegistroLlamada;
    }

    /**
     * @param lstVntRegistroLlamada the lstVntRegistroLlamada to set
     */
    public void setLstVntRegistroLlamada(List<VntRegistroLlamada> lstVntRegistroLlamada) {
        this.lstVntRegistroLlamada = lstVntRegistroLlamada;
    }

    /**
     * @return the lstServiciosXParametros
     */
    public List<TablaVntServicio> getLstServiciosXParametros() {
        return lstServiciosXParametros;
    }

    /**
     * @param lstServiciosXParametros the lstServiciosXParametros to set
     */
    public void setLstServiciosXParametros(List<TablaVntServicio> lstServiciosXParametros) {
        this.lstServiciosXParametros = lstServiciosXParametros;
    }

    /**
     * @return the blnVisibleEdaYSexo
     */
    public boolean isBlnVisibleEdaYSexo() {
        return blnVisibleEdaYSexo;
    }

    /**
     * @param blnVisibleEdaYSexo the blnVisibleEdaYSexo to set
     */
    public void setBlnVisibleEdaYSexo(boolean blnVisibleEdaYSexo) {
        this.blnVisibleEdaYSexo = blnVisibleEdaYSexo;
    }

    /**
     * @return the idMotivoEventoLLamada
     */
    public Integer getIdMotivoEventoLLamada() {
        return idMotivoEventoLLamada;
    }

    /**
     * @param idMotivoEventoLLamada the idMotivoEventoLLamada to set
     */
    public void setIdMotivoEventoLLamada(Integer idMotivoEventoLLamada) {
        this.idMotivoEventoLLamada = idMotivoEventoLLamada;
    }

    /**
     * @return the fechaEventoLLamada
     */
    public Date getFechaEventoLLamada() {
        return fechaEventoLLamada;
    }

    /**
     * @param fechaEventoLLamada the fechaEventoLLamada to set
     */
    public void setFechaEventoLLamada(Date fechaEventoLLamada) {
        this.fechaEventoLLamada = fechaEventoLLamada;
    }

    /**
     * @return the homenajeadoLLamada
     */
    public String getHomenajeadoLLamada() {
        return homenajeadoLLamada;
    }

    /**
     * @param homenajeadoLLamada the homenajeadoLLamada to set
     */
    public void setHomenajeadoLLamada(String homenajeadoLLamada) {
        this.homenajeadoLLamada = homenajeadoLLamada;
    }

    /**
     * @return the mensajeExcel
     */
    public String getMensajeExcel() {
        return mensajeExcel;
    }

    /**
     * @param mensajeExcel the mensajeExcel to set
     */
    public void setMensajeExcel(String mensajeExcel) {
        this.mensajeExcel = mensajeExcel;
    }

    /**
     * @return the asuntoMensajeExcel
     */
    public String getAsuntoMensajeExcel() {
        return asuntoMensajeExcel;
    }

    /**
     * @param asuntoMensajeExcel the asuntoMensajeExcel to set
     */
    public void setAsuntoMensajeExcel(String asuntoMensajeExcel) {
        this.asuntoMensajeExcel = asuntoMensajeExcel;
    }

    /**
     * @return the esNit
     */
    public boolean isEsNit() {
        return esNit;
    }

    /**
     * @param esNit the esNit to set
     */
    public void setEsNit(boolean esNit) {
        this.esNit = esNit;
    }

    /**
     * @return the blnMostrarPanel
     */
    public boolean isBlnMostrarPanel() {
        return blnMostrarPanel;
    }

    /**
     * @param blnMostrarPanel the blnMostrarPanel to set
     */
    public void setBlnMostrarPanel(boolean blnMostrarPanel) {
        this.blnMostrarPanel = blnMostrarPanel;
    }

    /**
     * @return the blnNuevo
     */
    public boolean isBlnNuevo() {
        return blnNuevo;
    }

    /**
     * @param blnNuevo the blnNuevo to set
     */
    public void setBlnNuevo(boolean blnNuevo) {
        this.blnNuevo = blnNuevo;
    }

    /**
     * @return the strBuscarCln
     */
    public String getStrBuscarCln() {
        return strBuscarCln;
    }

    /**
     * @param strBuscarCln the strBuscarCln to set
     */
    public void setStrBuscarCln(String strBuscarCln) {
        this.strBuscarCln = strBuscarCln;
    }

    /**
     * @return the vntCliente
     */
    public VntCliente getVntCliente() {
        return vntCliente;
    }

    /**
     * @param vntCliente the vntCliente to set
     */
    public void setVntCliente(VntCliente vntCliente) {
        this.vntCliente = vntCliente;
    }

    /**
     * @return the lstTablaVntCliente
     */
    public List<TablaVntCliente> getLstTablaVntCliente() {
        return lstTablaVntCliente;
    }

    /**
     * @param lstTablaVntCliente the lstTablaVntCliente to set
     */
    public void setLstTablaVntCliente(List<TablaVntCliente> lstTablaVntCliente) {
        this.lstTablaVntCliente = lstTablaVntCliente;
    }

    /**
     * @return the tablaVntClienteSel
     */
    public TablaVntCliente getTablaVntClienteSel() {
        return tablaVntClienteSel;
    }

    /**
     * @param tablaVntClienteSel the tablaVntClienteSel to set
     */
    public void setTablaVntClienteSel(TablaVntCliente tablaVntClienteSel) {
        this.tablaVntClienteSel = tablaVntClienteSel;
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
     * @return the lstActividadEconomica
     */
    public List<SelectItem> getLstActividadEconomica() {
        return lstActividadEconomica;
    }

    /**
     * @param lstActividadEconomica the lstActividadEconomica to set
     */
    public void setLstActividadEconomica(List<SelectItem> lstActividadEconomica) {
        this.lstActividadEconomica = lstActividadEconomica;
    }

    /**
     * @return the idTipoDoc
     */
    public String getIdTipoDoc() {
        return idTipoDoc;
    }

    /**
     * @param idTipoDoc the idTipoDoc to set
     */
    public void setIdTipoDoc(String idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    /**
     * @return the strCltDocumento
     */
    public String getStrCltDocumento() {
        return strCltDocumento;
    }

    /**
     * @param strCltDocumento the strCltDocumento to set
     */
    public void setStrCltDocumento(String strCltDocumento) {
        this.strCltDocumento = strCltDocumento;
    }

    /**
     * @return the strCltNombres
     */
    public String getStrCltNombres() {
        return strCltNombres;
    }

    /**
     * @param strCltNombres the strCltNombres to set
     */
    public void setStrCltNombres(String strCltNombres) {
        this.strCltNombres = strCltNombres;
    }

    /**
     * @return the idSexo
     */
    public String getIdSexo() {
        return idSexo;
    }

    /**
     * @param idSexo the idSexo to set
     */
    public void setIdSexo(String idSexo) {
        this.idSexo = idSexo;
    }

    /**
     * @return the intCltNumHijos
     */
    public Integer getIntCltNumHijos() {
        return intCltNumHijos;
    }

    /**
     * @param intCltNumHijos the intCltNumHijos to set
     */
    public void setIntCltNumHijos(Integer intCltNumHijos) {
        this.intCltNumHijos = intCltNumHijos;
    }

    /**
     * @return the datCltFechaNac
     */
    public Date getDatCltFechaNac() {
        return datCltFechaNac;
    }

    /**
     * @param datCltFechaNac the datCltFechaNac to set
     */
    public void setDatCltFechaNac(Date datCltFechaNac) {
        this.datCltFechaNac = datCltFechaNac;
    }

    /**
     * @return the strCltDireccion
     */
    public String getStrCltDireccion() {
        return strCltDireccion;
    }

    /**
     * @param strCltDireccion the strCltDireccion to set
     */
    public void setStrCltDireccion(String strCltDireccion) {
        this.strCltDireccion = strCltDireccion;
    }

    /**
     * @return the strCltTelefono
     */
    public String getStrCltTelefono() {
        return strCltTelefono;
    }

    /**
     * @param strCltTelefono the strCltTelefono to set
     */
    public void setStrCltTelefono(String strCltTelefono) {
        this.strCltTelefono = strCltTelefono;
    }

    /**
     * @return the strCltCelular
     */
    public String getStrCltCelular() {
        return strCltCelular;
    }

    /**
     * @param strCltCelular the strCltCelular to set
     */
    public void setStrCltCelular(String strCltCelular) {
        this.strCltCelular = strCltCelular;
    }

    /**
     * @return the strCltCorreo
     */
    public String getStrCltCorreo() {
        return strCltCorreo;
    }

    /**
     * @param strCltCorreo the strCltCorreo to set
     */
    public void setStrCltCorreo(String strCltCorreo) {
        this.strCltCorreo = strCltCorreo;
    }

    /**
     * @return the strCltContacto
     */
    public String getStrCltContacto() {
        return strCltContacto;
    }

    /**
     * @param strCltContacto the strCltContacto to set
     */
    public void setStrCltContacto(String strCltContacto) {
        this.strCltContacto = strCltContacto;
    }

    /**
     * @return the idTipoCliente
     */
    public Integer getIdTipoCliente() {
        return idTipoCliente;
    }

    /**
     * @param idTipoCliente the idTipoCliente to set
     */
    public void setIdTipoCliente(Integer idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    /**
     * @return the idActEconomica
     */
    public Integer getIdActEconomica() {
        return idActEconomica;
    }

    /**
     * @param idActEconomica the idActEconomica to set
     */
    public void setIdActEconomica(Integer idActEconomica) {
        this.idActEconomica = idActEconomica;
    }

    /**
     * @return the vntDetallecliente
     */
    public VntDetallecliente getVntDetallecliente() {
        return vntDetallecliente;
    }

    /**
     * @param vntDetallecliente the vntDetallecliente to set
     */
    public void setVntDetallecliente(VntDetallecliente vntDetallecliente) {
        this.vntDetallecliente = vntDetallecliente;
    }

    /**
     * @return the lstTablaVntDetalleCliente
     */
    public List<TablaVntDetalleCliente> getLstTablaVntDetalleCliente() {
        return lstTablaVntDetalleCliente;
    }

    /**
     * @param lstTablaVntDetalleCliente the lstTablaVntDetalleCliente to set
     */
    public void setLstTablaVntDetalleCliente(List<TablaVntDetalleCliente> lstTablaVntDetalleCliente) {
        this.lstTablaVntDetalleCliente = lstTablaVntDetalleCliente;
    }

    /**
     * @return the tablaVntDetalleClienteSel
     */
    public TablaVntDetalleCliente getTablaVntDetalleClienteSel() {
        return tablaVntDetalleClienteSel;
    }

    /**
     * @param tablaVntDetalleClienteSel the tablaVntDetalleClienteSel to set
     */
    public void setTablaVntDetalleClienteSel(TablaVntDetalleCliente tablaVntDetalleClienteSel) {
        this.tablaVntDetalleClienteSel = tablaVntDetalleClienteSel;
    }

    /**
     * @return the lstSexoDetClt
     */
    public List<SelectItem> getLstSexoDetClt() {
        return lstSexoDetClt;
    }

    /**
     * @param lstSexoDetClt the lstSexoDetClt to set
     */
    public void setLstSexoDetClt(List<SelectItem> lstSexoDetClt) {
        this.lstSexoDetClt = lstSexoDetClt;
    }

    /**
     * @return the lstParentesco
     */
    public List<SelectItem> getLstParentesco() {
        return lstParentesco;
    }

    /**
     * @param lstParentesco the lstParentesco to set
     */
    public void setLstParentesco(List<SelectItem> lstParentesco) {
        this.lstParentesco = lstParentesco;
    }

    /**
     * @return the strDetNombre1
     */
    public String getStrDetNombre1() {
        return strDetNombre1;
    }

    /**
     * @param strDetNombre1 the strDetNombre1 to set
     */
    public void setStrDetNombre1(String strDetNombre1) {
        this.strDetNombre1 = strDetNombre1;
    }

    /**
     * @return the idSexoDetClt
     */
    public String getIdSexoDetClt() {
        return idSexoDetClt;
    }

    /**
     * @param idSexoDetClt the idSexoDetClt to set
     */
    public void setIdSexoDetClt(String idSexoDetClt) {
        this.idSexoDetClt = idSexoDetClt;
    }

    /**
     * @return the idParentesco
     */
    public Integer getIdParentesco() {
        return idParentesco;
    }

    /**
     * @param idParentesco the idParentesco to set
     */
    public void setIdParentesco(Integer idParentesco) {
        this.idParentesco = idParentesco;
    }

    /**
     * @return the datDetFechaNac
     */
    public Date getDatDetFechaNac() {
        return datDetFechaNac;
    }

    /**
     * @param datDetFechaNac the datDetFechaNac to set
     */
    public void setDatDetFechaNac(Date datDetFechaNac) {
        this.datDetFechaNac = datDetFechaNac;
    }

    /**
     * @return the vntRegistroventa
     */
    public VntRegistroventa getVntRegistroventa() {
        return vntRegistroventa;
    }

    /**
     * @param vntRegistroventa the vntRegistroventa to set
     */
    public void setVntRegistroventa(VntRegistroventa vntRegistroventa) {
        this.vntRegistroventa = vntRegistroventa;
    }

    /**
     * @return the vntDetevento
     */
    public VntDetevento getVntDetevento() {
        return vntDetevento;
    }

    /**
     * @param vntDetevento the vntDetevento to set
     */
    public void setVntDetevento(VntDetevento vntDetevento) {
        this.vntDetevento = vntDetevento;
    }

    /**
     * @return the lstItemsCiudadXDep
     */
    public List<SelectItem> getLstItemsCiudadXDep() {
        return lstItemsCiudadXDep;
    }

    /**
     * @param lstItemsCiudadXDep the lstItemsCiudadXDep to set
     */
    public void setLstItemsCiudadXDep(List<SelectItem> lstItemsCiudadXDep) {
        this.lstItemsCiudadXDep = lstItemsCiudadXDep;
    }

    /**
     * @return the lstMotivoEvento
     */
    public List<SelectItem> getLstMotivoEvento() {
        return lstMotivoEvento;
    }

    /**
     * @param lstMotivoEvento the lstMotivoEvento to set
     */
    public void setLstMotivoEvento(List<SelectItem> lstMotivoEvento) {
        this.lstMotivoEvento = lstMotivoEvento;
    }

    /**
     * @return the depId
     */
    public String getDepId() {
        return depId;
    }

    /**
     * @param depId the depId to set
     */
    public void setDepId(String depId) {
        this.depId = depId;
    }

    /**
     * @return the ciuId
     */
    public Long getCiuId() {
        return ciuId;
    }

    /**
     * @param ciuId the ciuId to set
     */
    public void setCiuId(Long ciuId) {
        this.ciuId = ciuId;
    }

    /**
     * @return the strDevNombres
     */
    public String getStrDevNombres() {
        return strDevNombres;
    }

    /**
     * @param strDevNombres the strDevNombres to set
     */
    public void setStrDevNombres(String strDevNombres) {
        this.strDevNombres = strDevNombres;
    }

    /**
     * @return the strDevApellidos
     */
    public String getStrDevApellidos() {
        return strDevApellidos;
    }

    /**
     * @param strDevApellidos the strDevApellidos to set
     */
    public void setStrDevApellidos(String strDevApellidos) {
        this.strDevApellidos = strDevApellidos;
    }

    /**
     * @return the idMotivoEvento
     */
    public Integer getIdMotivoEvento() {
        return idMotivoEvento;
    }

    /**
     * @param idMotivoEvento the idMotivoEvento to set
     */
    public void setIdMotivoEvento(Integer idMotivoEvento) {
        this.idMotivoEvento = idMotivoEvento;
    }

    /**
     * @return the strDevDireccion
     */
    public String getStrDevDireccion() {
        return strDevDireccion;
    }

    /**
     * @param strDevDireccion the strDevDireccion to set
     */
    public void setStrDevDireccion(String strDevDireccion) {
        this.strDevDireccion = strDevDireccion;
    }

    /**
     * @return the datDevFecha
     */
    public Date getDatDevFecha() {
        return datDevFecha;
    }

    /**
     * @param datDevFecha the datDevFecha to set
     */
    public void setDatDevFecha(Date datDevFecha) {
        this.datDevFecha = datDevFecha;
    }

    /**
     * @return the datDevHoraInicio
     */
    public Date getDatDevHoraInicio() {
        return datDevHoraInicio;
    }

    /**
     * @param datDevHoraInicio the datDevHoraInicio to set
     */
    public void setDatDevHoraInicio(Date datDevHoraInicio) {
        this.datDevHoraInicio = datDevHoraInicio;
    }

    /**
     * @return the datDevHoraFinal
     */
    public Date getDatDevHoraFinal() {
        return datDevHoraFinal;
    }

    /**
     * @param datDevHoraFinal the datDevHoraFinal to set
     */
    public void setDatDevHoraFinal(Date datDevHoraFinal) {
        this.datDevHoraFinal = datDevHoraFinal;
    }

    /**
     * @return the strDevTelefono1
     */
    public String getStrDevTelefono1() {
        return strDevTelefono1;
    }

    /**
     * @param strDevTelefono1 the strDevTelefono1 to set
     */
    public void setStrDevTelefono1(String strDevTelefono1) {
        this.strDevTelefono1 = strDevTelefono1;
    }

    /**
     * @return the strDevTelefono2
     */
    public String getStrDevTelefono2() {
        return strDevTelefono2;
    }

    /**
     * @param strDevTelefono2 the strDevTelefono2 to set
     */
    public void setStrDevTelefono2(String strDevTelefono2) {
        this.strDevTelefono2 = strDevTelefono2;
    }

    /**
     * @return the strDevCelular1
     */
    public String getStrDevCelular1() {
        return strDevCelular1;
    }

    /**
     * @param strDevCelular1 the strDevCelular1 to set
     */
    public void setStrDevCelular1(String strDevCelular1) {
        this.strDevCelular1 = strDevCelular1;
    }

    /**
     * @return the strDevCelular2
     */
    public String getStrDevCelular2() {
        return strDevCelular2;
    }

    /**
     * @param strDevCelular2 the strDevCelular2 to set
     */
    public void setStrDevCelular2(String strDevCelular2) {
        this.strDevCelular2 = strDevCelular2;
    }

    /**
     * @return the strDevObservacion
     */
    public String getStrDevObservacion() {
        return strDevObservacion;
    }

    /**
     * @param strDevObservacion the strDevObservacion to set
     */
    public void setStrDevObservacion(String strDevObservacion) {
        this.strDevObservacion = strDevObservacion;
    }

    /**
     * @return the vntServxventa
     */
    public VntServxventa getVntServxventa() {
        return vntServxventa;
    }

    /**
     * @param vntServxventa the vntServxventa to set
     */
    public void setVntServxventa(VntServxventa vntServxventa) {
        this.vntServxventa = vntServxventa;
    }

    /**
     * @return the vntColxventa
     */
    public VntColxventa getVntColxventa() {
        return vntColxventa;
    }

    /**
     * @param vntColxventa the vntColxventa to set
     */
    public void setVntColxventa(VntColxventa vntColxventa) {
        this.vntColxventa = vntColxventa;
    }

    /**
     * @return the lstTablaVntServicio
     */
    public List<TablaVntServicio> getLstTablaVntServicio() {
        return lstTablaVntServicio;
    }

    /**
     * @param lstTablaVntServicio the lstTablaVntServicio to set
     */
    public void setLstTablaVntServicio(List<TablaVntServicio> lstTablaVntServicio) {
        this.lstTablaVntServicio = lstTablaVntServicio;
    }

    /**
     * @return the lstTablaVntSrvXVenta
     */
    public List<TablaVntSrvXVenta> getLstTablaVntSrvXVenta() {
        return lstTablaVntSrvXVenta;
    }

    /**
     * @param lstTablaVntSrvXVenta the lstTablaVntSrvXVenta to set
     */
    public void setLstTablaVntSrvXVenta(List<TablaVntSrvXVenta> lstTablaVntSrvXVenta) {
        this.lstTablaVntSrvXVenta = lstTablaVntSrvXVenta;
    }

    /**
     * @return the tablaVntSrvXVentaSel
     */
    public TablaVntSrvXVenta getTablaVntSrvXVentaSel() {
        return tablaVntSrvXVentaSel;
    }

    /**
     * @param tablaVntSrvXVentaSel the tablaVntSrvXVentaSel to set
     */
    public void setTablaVntSrvXVentaSel(TablaVntSrvXVenta tablaVntSrvXVentaSel) {
        this.tablaVntSrvXVentaSel = tablaVntSrvXVentaSel;
    }
    //</editor-fold>

    /**
     * @return the strDetDireccion
     */
    public String getStrDetDireccion() {
        return strDetDireccion;
    }

    /**
     * @param strDetDireccion the strDetDireccion to set
     */
    public void setStrDetDireccion(String strDetDireccion) {
        this.strDetDireccion = strDetDireccion;
    }

    /**
     * @return the strDetCorreo
     */
    public String getStrDetCorreo() {
        return strDetCorreo;
    }

    /**
     * @param strDetCorreo the strDetCorreo to set
     */
    public void setStrDetCorreo(String strDetCorreo) {
        this.strDetCorreo = strDetCorreo;
    }

    /**
     * @return the blnEnviarCorreoAPrincipal
     */
    public boolean isBlnEnviarCorreoAPrincipal() {
        return blnEnviarCorreoAPrincipal;
    }

    /**
     * @param blnEnviarCorreoAPrincipal the blnEnviarCorreoAPrincipal to set
     */
    public void setBlnEnviarCorreoAPrincipal(boolean blnEnviarCorreoAPrincipal) {
        this.blnEnviarCorreoAPrincipal = blnEnviarCorreoAPrincipal;
    }

    /**
     * @return the lstVentasXDetalle
     */
    public List<TablaDetalleVentaDTO> getLstVentasXDetalle() {
        return lstVentasXDetalle;
    }

    /**
     * @param lstVentasXDetalle the lstVentasXDetalle to set
     */
    public void setLstVentasXDetalle(List<TablaDetalleVentaDTO> lstVentasXDetalle) {
        this.lstVentasXDetalle = lstVentasXDetalle;
    }

    /**
     * @return the lstCargosContacto
     */
    public List<SelectItem> getLstCargosContacto() {
        return lstCargosContacto;
    }

    /**
     * @param lstCargosContacto the lstCargosContacto to set
     */
    public void setLstCargosContacto(List<SelectItem> lstCargosContacto) {
        this.lstCargosContacto = lstCargosContacto;
    }

    /**
     * @return the detCelular
     */
    public String getDetCelular() {
        return detCelular;
    }

    /**
     * @param detCelular the detCelular to set
     */
    public void setDetCelular(String detCelular) {
        this.detCelular = detCelular;
    }

    /**
     * @return the detExtension
     */
    public String getDetExtension() {
        return detExtension;
    }

    /**
     * @param detExtension the detExtension to set
     */
    public void setDetExtension(String detExtension) {
        this.detExtension = detExtension;
    }

    /**
     * @return the detCargo
     */
    public Integer getDetCargo() {
        return detCargo;
    }

    /**
     * @param detCargo the detCargo to set
     */
    public void setDetCargo(Integer detCargo) {
        this.detCargo = detCargo;
    }

    /**
     * @return the strCltAlias
     */
    public String getStrCltAlias() {
        return strCltAlias;
    }

    /**
     * @param strCltAlias the strCltAlias to set
     */
    public void setStrCltAlias(String strCltAlias) {
        this.strCltAlias = strCltAlias;
    }

    /**
     * @return the valorTotalVenta
     */
    public BigDecimal getValorTotalVenta() {
        return valorTotalVenta;
    }

    /**
     * @param valorTotalVenta the valorTotalVenta to set
     */
    public void setValorTotalVenta(BigDecimal valorTotalVenta) {
        this.valorTotalVenta = valorTotalVenta;
    }

    /**
     * @return the valoDescVenta
     */
    public BigDecimal getValoDescVenta() {
        return valoDescVenta;
    }

    /**
     * @param valoDescVenta the valoDescVenta to set
     */
    public void setValoDescVenta(BigDecimal valoDescVenta) {
        this.valoDescVenta = valoDescVenta;
    }

    public Integer getCantidadServiciosSeleccionados() {
        return cantidadServiciosSeleccionados;
    }

    public void setCantidadServiciosSeleccionados(Integer cantidadServiciosSeleccionados) {
        this.cantidadServiciosSeleccionados = cantidadServiciosSeleccionados;
    }

    /**
     * @return the valSubtotal
     */
    public BigDecimal getValSubtotal() {
        return valSubtotal;
    }

    /**
     * @param valSubtotal the valSubtotal to set
     */
    public void setValSubtotal(BigDecimal valSubtotal) {
        this.valSubtotal = valSubtotal;
    }

    /**
     * @return the lstVentas
     */
    public List<TablaDetalleVentaDTO> getLstVentas() {
        return lstVentas;
    }

    /**
     * @param lstVentas the lstVentas to set
     */
    public void setLstVentas(List<TablaDetalleVentaDTO> lstVentas) {
        this.lstVentas = lstVentas;
    }

    /**
     * @return the lstTablaVentaServicioXVenta
     */
    public List<TablaVntSrvXVenta> getLstTablaVentaServicioXVenta() {
        return lstTablaVentaServicioXVenta;
    }

    /**
     * @param lstTablaVentaServicioXVenta the lstTablaVentaServicioXVenta to set
     */
    public void setLstTablaVentaServicioXVenta(List<TablaVntSrvXVenta> lstTablaVentaServicioXVenta) {
        this.lstTablaVentaServicioXVenta = lstTablaVentaServicioXVenta;
    }

    public String getRegistroLlamadaObservacion() {
        return registroLlamadaObservacion;
    }

    public void setRegistroLlamadaObservacion(String registroLlamadaObservacion) {
        this.registroLlamadaObservacion = registroLlamadaObservacion;
    }

    /**
     * /
     *
     **
     * @return the dcln_direccion
     */
    public String getDcln_direccion() {
        return dcln_direccion;
    }

    /**
     * @param dcln_direccion the dcln_direccion to set
     */
    public void setDcln_direccion(String dcln_direccion) {
        this.dcln_direccion = dcln_direccion;
    }

    /**
     * @return the strDetNumDias
     */
    public Integer getStrDetNumDias() {
        return strDetNumDias;
    }

    /**
     * @param strDetNumDias the strDetNumDias to set
     */
    public void setStrDetNumDias(Integer strDetNumDias) {
        this.strDetNumDias = strDetNumDias;
    }

    public List<TablaArchivosAdjunto> getLstArchivosCargar() {
        return lstArchivosCargar;
    }

    public void setLstArchivosCargar(List<TablaArchivosAdjunto> lstArchivosCargar) {
        this.lstArchivosCargar = lstArchivosCargar;
    }

    /**
     * @return the archivosAdjuntoSel
     */
    public TablaArchivosAdjunto getArchivosAdjuntoSel() {
        return archivosAdjuntoSel;
    }

    /**
     * @param archivosAdjuntoSel the archivosAdjuntoSel to set
     */
    public void setArchivosAdjuntoSel(TablaArchivosAdjunto archivosAdjuntoSel) {
        this.archivosAdjuntoSel = archivosAdjuntoSel;
    }
}
