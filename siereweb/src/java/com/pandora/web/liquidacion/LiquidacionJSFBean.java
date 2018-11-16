/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.liquidacion;

import adm.sys.bean.AdmSysTareaSLBean;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.AdmInforme;
import com.pandora.bussiness.util.EnPasoCalificacion;
import com.pandora.bussiness.util.EnTipoPagoNomina;
import com.pandora.mod.liquidacion.ConsultaNominaDTO;
import com.pandora.mod.liquidacion.LiquidacionSFBean;
import com.pandora.mod.liquidacion.dao.PgLiquidacion;
import com.pandora.mod.liquidacion.dao.PgLiquidacionconsolidado;
import com.pandora.mod.liquidacion.dao.PgLiquidacionxcolaborador;
import com.pandora.mod.ordenprod.dao.PopCxcevento;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.procesos.PcsCotizacionJSFBean;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import utilidades.EnInforme;
import utilidades.GenerateRandomPin;
import utilidades.ManejoFecha;
import utilidades.Messages;

/**
 *
 * @author Garcia Bosso
 */
@Named(value = "liquidacionJSFBean")
@SessionScoped
public class LiquidacionJSFBean extends BaseJSFBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @EJB
    protected AdmSysTareaSLBean admSysTareaSLBean;
    @EJB
    private LiquidacionSFBean liquidacionSFBean;
    private List<TablaOPPendietesColaborador> lstNominaXColaboradores = new ArrayList<>();
    
    private Date fechaInicial = null;
    private Date fechaFinal = null;
    private boolean habilitarFechaIncial;
    private PgLiquidacionconsolidado pgLiquidacionconsolidado = new PgLiquidacionconsolidado();
    private PgLiquidacion pgLiquidacion = new PgLiquidacion();
    private ConsultaNominaDTO consultaNominaDTO = new ConsultaNominaDTO();
    private List<TablaPendientePago> listaPagos = new ArrayList<>();
    private List<SelectItem> listaTiposPago = new ArrayList<>();
    private List<TablaConsolidadoLiquidacion> listaConsolidadoLiquidacion = new ArrayList<>();

//</editor-fold>
    /**
     * Creates a new instance of LiquidacionJSFBean
     */
    public LiquidacionJSFBean() {
    }
    
    @Override
    public void init() {
        this.numPanel = 1;
        consultaNominaDTO = new ConsultaNominaDTO();
        listaPagos.clear();
        loadFechaInicial();
        loadTiposPago();
        listaConsolidadoLiquidacion.clear();
    }
    
    public void generarReporteConsolidadoPorItem(ActionEvent ae) {
        TablaConsolidadoLiquidacion t = (TablaConsolidadoLiquidacion) ae.getComponent().getAttributes().get("tablaConsolidado");
        generarReporte(t.getLiquidacionconsolidado().getIdLiquidaconso());
        //t.setJasperResourcePDF(generarReporte(t.getLiquidacionconsolidado().getIdLiquidaconso()));
    }
    
    private void loadConsolidadoLiquidacion() {
        listaConsolidadoLiquidacion.clear();
        List<PgLiquidacionconsolidado> lista = liquidacionSFBean.consultaLiquidacionConsolidadas();
        for (PgLiquidacionconsolidado p : lista) {
            TablaConsolidadoLiquidacion c = new TablaConsolidadoLiquidacion(p);
            listaConsolidadoLiquidacion.add(c);
        }
        
    }
    
    private void loadTiposPago() {
        listaTiposPago.clear();
        listaTiposPago.add(new SelectItem(getAppBean().getTipoPagoEfectivo().getIdTipopago(), getAppBean().getTipoPagoEfectivo().getDescTipopago()));
        listaTiposPago.add(new SelectItem(getAppBean().getTipoPagoCheque().getIdTipopago(), getAppBean().getTipoPagoCheque().getDescTipopago()));
        listaTiposPago.add(new SelectItem(getAppBean().getTipoPagoTransferencia().getIdTipopago(), getAppBean().getTipoPagoTransferencia().getDescTipopago()));
    }
    
    @Override
    public void limpiarVariables() {
        consultaNominaDTO = new ConsultaNominaDTO();
        listaConsolidadoLiquidacion.clear();
        listaTiposPago.clear();
        listaPagos.clear();
    }
    
    public void guardarLiquidacion(ActionEvent wj) {
        pgLiquidacionconsolidado.setFechapLiquidaconso(new Date());
        pgLiquidacionconsolidado.setCxcId(getPrincipalJSFBean().getAdmCrgxcolActivo());
        pgLiquidacionconsolidado = liquidacionSFBean.saveConsolidadoLiquidacion(pgLiquidacionconsolidado);
        mostrarError("Liquidación de nomina generada exitosamente con el # " + pgLiquidacionconsolidado.getIdLiquidaconso(), 3);
        consultaNominaDTO = new ConsultaNominaDTO();
        
        //this.jasperResourcePDF = 
        generarReporte(pgLiquidacionconsolidado.getIdLiquidaconso());
        for (PgLiquidacion liq : pgLiquidacionconsolidado.getPgLiquidacionList()) {
            if (!liq.getIdTipopago().getIdTipopago().equals(EnTipoPagoNomina.TRANSFERENCIA.getId())) {
                enviarCorreoPinPago(liq);
            }
        }
    }
    
    private void enviarCorreoPinPago(PgLiquidacion p) {
        
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
            usuario.put("colaborador", p.getCxcId().getCpeId().getColCedula().getNombres());
            //  usuario.put("apellidos", "Robles Caro");
            usuario.put("email", p.getCxcId().getCpeId().getColCedula().getColEmail());
            usuario.put("pin", p.getLiquidacionNumeroPin());
            usuario.put("nombreConsolidado", p.getIdLiquidaconso().getNombreLiquidaconso().replaceAll(Messages.getMessageGeneral(ConstantsProperties.LIQUIDACION_NOMINA_UNION), " "));
            
            plain = fileToString(ec.getResourceAsStream(getRuta_recursos() + "com/pandora/web/liquidacion/mail.txt"), "utf-8");
            strSub = new StrSubstitutor(usuario);
            plain = strSub.replace(plain);
            html = fileToString(ec.getResourceAsStream(getRuta_recursos() + "com/pandora/web/liquidacion/mail.html"), "utf-8");
            String cid = null;
            String images = "logo1.png";
            
            File img = new File(ec.getRealPath(getRuta_recursos() + "com/pandora/web/procesos/" + images));
            cid = email.embed(img, images);
            usuario.put(images, cid);
            strSub = new StrSubstitutor(usuario);
            html = strSub.replace(html);
            email.setHostName(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpHostcorreo());
            email.setCharset("UTF-8");
            
            email.addTo(usuario.getProperty("email"), usuario.getProperty("colaborador"));
            
            email.addCc(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpUsuariocorreo());
            email.setFrom(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpUsuariocorreo(), getPrincipalJSFBean().getColxempLog().getEmpId().getEmpNombre());
            email.setSubject(Messages.getMessageGeneral(ConstantsProperties.LIQUIDACION_NOMINA_PIN_MSG));
            email.setHtmlMsg(html);
            email.setTextMsg(plain);
            email.setAuthentication(getPrincipalJSFBean().getColxempLog().getEmpId().getEmpUsuariocorreo(),
                    getPrincipalJSFBean().getColxempLog().getEmpId().getEmpClavecorreo());
            
            email.send();
            
        } catch (IOException ex) {
            Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ocurrio un error al enviar correo ", 3);
        } catch (EmailException ex) {
            Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ocurrio un error al enviar correo ", 3);
        }
        
    }
    
    public void generarReporte(Integer idConsolidado) {
        
        HashMap hmParamInf = new HashMap();
        hmParamInf.put("p_Liquida", idConsolidado);
        
        AdmInforme pAdmInforme = admSysTareaSLBean.getAdmInformeXId(EnInforme.NOMINA.getId());
        String rutaLogo = "reportes/venta/logos/maximus.jpg";
        irAServletDescarga(genInfRecurso(hmParamInf, pAdmInforme, 2, rutaLogo));
        //return genInfRecurso(hmParamInf, pAdmInforme, 2, rutaLogo);
    }
    
    public void seleccionarParaPago(ValueChangeEvent vce) {
        TablaPendientePago tpg = (TablaPendientePago) vce.getComponent().getAttributes().get("tabla");
        boolean selec = (Boolean) vce.getNewValue();
        tpg.setSeleccionado(selec);
        tpg.getLiquidacion().setLiquidacionNumeroAprobacion(null);
        
        if (selec && tpg.getLiquidacion().getIdTipopago().getIdTipopago().equals(EnTipoPagoNomina.TRANSFERENCIA.getId())) {
            tpg.getLiquidacion().setLiquidacionNumeroAprobacion(tpg.getLiquidacion().getLiquidacionNumeroPin());
        }
        
    }
    
    public void vistaPreliminar(ActionEvent wj) {
        boolean validate = true;
        for (TablaOPPendietesColaborador l : lstNominaXColaboradores) {
            if (l.isSeleccionado()) {
                validate = false;
                break;
            }
        }
        if (validate) {
            mostrarError("Debe seleccionar eventos por colaborador.", 1);
            return;
        }
        loadVistaPreliminar();
        numPanel = 3;
    }
    
    private void loadVistaPreliminar() {
        pgLiquidacionconsolidado.setPgLiquidacionList(new ArrayList<PgLiquidacion>());
        HashMap<AdmCrgxcol, PgLiquidacion> liquidaciones = new HashMap<>();
        
        for (TablaOPPendietesColaborador v : lstNominaXColaboradores) {
            if (v.isSeleccionado()) {
                PgLiquidacion pg = liquidaciones.get(v.getPopCxcevento().getCxcId());
                if (pg == null) {
                    pg = new PgLiquidacion();
                    pg.setIdLiquidaconso(pgLiquidacionconsolidado);
                    pg.setCxcId(v.getPopCxcevento().getCxcId());
                    pg.setLiquidacionValorPagar(v.getValorTotalApgar() == null ? BigDecimal.ZERO : v.getValorTotalApgar());
                    PgLiquidacionxcolaborador pgc = new PgLiquidacionxcolaborador();
                    pgc.setCxeId(v.getPopCxcevento());
                    pgc.setLiquidacionId(pg);
                    
                    pg.getPgLiquidacionxcolaboradorList().add(pgc);
                } else {
                    pg.setIdLiquidaconso(pgLiquidacionconsolidado);
                    pg.setCxcId(v.getPopCxcevento().getCxcId());
                    pg.setLiquidacionValorPagar(pg.getLiquidacionValorPagar().add((v.getValorTotalApgar() == null ? BigDecimal.ZERO : v.getValorTotalApgar())));
                    PgLiquidacionxcolaborador pgc = new PgLiquidacionxcolaborador();
                    pgc.setCxeId(v.getPopCxcevento());
                    pgc.setLiquidacionId(pg);
                    
                    pg.getPgLiquidacionxcolaboradorList().add(pgc);
                }
                liquidaciones.put(v.getPopCxcevento().getCxcId(), pg);
            }
            
        }
        
        for (Map.Entry<AdmCrgxcol, PgLiquidacion> entry : liquidaciones.entrySet()) {
            
            PgLiquidacion liq = entry.getValue();
            
            if (liq.getCxcId().getCpeId().getColCedula().getNumerodecuenta() != null
                    && !liq.getCxcId().getCpeId().getColCedula().getNumerodecuenta().isEmpty()) {
                liq.setIdTipopago(getAppBean().getTipoPagoTransferencia());
            } else {
                
                BigDecimal valor = getAppBean().getTipoPagoEfectivo().getMaximovalorTipopago() == null ? BigDecimal.ZERO : getAppBean().getTipoPagoEfectivo().getMaximovalorTipopago();
                if (liq.getLiquidacionValorPagar().compareTo(valor) == 1) {
                    liq.setIdTipopago(getAppBean().getTipoPagoCheque());
                } else {
                    liq.setIdTipopago(getAppBean().getTipoPagoEfectivo());
                }
                liq.setLiquidacionNumeroPin(GenerateRandomPin.generateRandomPin());
            }
            pgLiquidacionconsolidado.getPgLiquidacionList().add(liq);
        }
        
    }
    
    private void loadFechaInicial() {
        habilitarFechaIncial = false;
        fechaInicial = null;
        fechaFinal = null;
        Date fecha = liquidacionSFBean.getMaximaFechaLiquidacion();
        if (fecha != null) {
            fecha.setDate(fecha.getDate() + 1);
            fechaInicial = fecha;
        }
        habilitarFechaIncial = fechaInicial == null;
        lstNominaXColaboradores.clear();
        pgLiquidacionconsolidado = new PgLiquidacionconsolidado();
    }
    
    private void cargarNominaXColaborador() {
        Date consulta = fechaFinal;
        lstNominaXColaboradores.clear();
        List<PopCxcevento> lista = this.liquidacionSFBean.getLstNominaxColaborador(EnPasoCalificacion.FINAL.getId(), consulta);
        for (PopCxcevento p : lista) {
            TablaOPPendietesColaborador t = new TablaOPPendietesColaborador(p);
            t.setSeleccionado(p.getIndversion() != null && p.getIndversion() == 1);
            t.setSeleccionar(p.getIndversion() != null && p.getIndversion() == 1);
            lstNominaXColaboradores.add(t);
        }
    }
    
    public void loadPrenomina_ActionEvent(ActionEvent ae) {
        boolean validate = false;
        if (fechaInicial == null) {
            mostrarError("Debe digitar las fecha inicial", 1);
        }
        if (fechaFinal == null) {
            mostrarError("Debe digitar las fecha final", 1);
        }
        if (validate) {
            return;
        }
        if (ManejoFecha.compararDates(fechaFinal, new Date()) == 1) {
            mostrarError("Fecha final debe ser menor que la fecha actual.", 1);
            return;
        }
        if (ManejoFecha.compararDates(fechaInicial, fechaFinal) == 1) {
            mostrarError("Fecha final debe ser mayor que la inical.", 1);
            return;
        }
        pgLiquidacionconsolidado = new PgLiquidacionconsolidado();
        pgLiquidacionconsolidado.setFechafLiquidaconso(fechaFinal);
        pgLiquidacionconsolidado.setFechaiLiquidaconso(fechaInicial);
        String name = Messages.getMessageGeneral(ConstantsProperties.LIQUIDACION_NOMBRE_NOMINA);
        name += Messages.getMessageGeneral(ConstantsProperties.LIQUIDACION_NOMINA_UNION);
        name += Messages.getMessageGeneral(ConstantsProperties.LIQUIDACION_NOMINA_DE);
        name += Messages.getMessageGeneral(ConstantsProperties.LIQUIDACION_NOMINA_UNION);
        name += ManejoFecha.getFechaFormato("yyyy/MM/dd", fechaInicial);
        name += Messages.getMessageGeneral(ConstantsProperties.LIQUIDACION_NOMINA_UNION);
        name += Messages.getMessageGeneral(ConstantsProperties.LIQUIDACION_NOMINA_HASTA);
        name += Messages.getMessageGeneral(ConstantsProperties.LIQUIDACION_NOMINA_UNION);
        name += ManejoFecha.getFechaFormato("yyyy/MM/dd", fechaFinal);
        pgLiquidacionconsolidado.setNombreLiquidaconso(name);
        cargarNominaXColaborador();
        numPanel = 2;
    }
    
    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        consultaNominaDTO.setLiquidacionConsolidadoId(null);
        consultarPagos();
    }
    
    private void consultarPagos() {
        listaPagos.clear();
        List<PgLiquidacion> data = liquidacionSFBean.consultaLiquidacionByParametros(consultaNominaDTO);
        int contador = 1;
        for (PgLiquidacion p : data) {
            TablaPendientePago t = new TablaPendientePago(p);
            t.setInidceFila(contador);
            
            listaPagos.add(t);
            contador++;
        }
        
    }
    
    public void pagar(ActionEvent ae) {
        boolean error = false;
        boolean entroSeleccionado = false;
        List<PgLiquidacion> listaPagadas = new ArrayList<>();
        for (TablaPendientePago t : listaPagos) {
            if (t.isSeleccionado()) {
                if (t.getLiquidacion().getLiquidacionNumeroAprobacion() == null
                        || t.getLiquidacion().getLiquidacionNumeroAprobacion().trim().isEmpty()) {
                    mostrarError("En la fila # " + t.getInidceFila() + " falta digitar el número del PIN", 1);
                    error = true;
                } else if (!t.getLiquidacion().getLiquidacionNumeroPin().
                        equals(t.getLiquidacion().getLiquidacionNumeroAprobacion())) {
                    mostrarError("En la fila # " + t.getInidceFila() + " el PIN no coincide con el que se envio por correo.", 1);
                    error = true;
                } else {
                    listaPagadas.add(t.getLiquidacion());
                }
                entroSeleccionado = true;
            }
        }
        if (entroSeleccionado) {
            if (error) {
                return;
            }
        } else {
            mostrarError("Debe seleccionar un colaborador a realizar el pago", 1);
            return;
        }
        liquidacionSFBean.saveLiquidacionPago(listaPagadas);
        this.consultarPagos();
        mostrarError("Pago exitoso", 3);
        
    }
    
    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.parseInt((String) ae.getComponent().getAttributes().get("numPanel"));
        consultaNominaDTO = new ConsultaNominaDTO();
        listaPagos.clear();
        listaConsolidadoLiquidacion.clear();
        switch (numPanel) {
            case 1:
                loadFechaInicial();
                
                break;
            
            case 4:
                consultarPagos();
                break;
            case 5:
                loadConsolidadoLiquidacion();
                break;
            
        }
    }
    
    public void editarSelColLiquida_AjaxBehaviorEvent(AjaxBehaviorEvent vce) {
        TablaOPPendietesColaborador topc = (TablaOPPendietesColaborador) vce.getComponent().getAttributes().get("tabla");
        //topc.selecciona = ((Boolean) vce.getNewValue());   
        liquidacionSFBean.editarPopCxcevento(topc.getPopCxcevento());
       // cargarNominaXColaborador();
    }

    /**
     * @return the lstNominaXColaboradores
     */
    public List<TablaOPPendietesColaborador> getLstNominaXColaboradores() {
        return lstNominaXColaboradores;
    }

    /**
     * @param lstNominaXColaboradores the lstNominaXColaboradores to set
     */
    public void setLstNominaXColaboradores(List<TablaOPPendietesColaborador> lstNominaXColaboradores) {
        this.lstNominaXColaboradores = lstNominaXColaboradores;
    }
    
    public Date getFechaInicial() {
        return fechaInicial;
    }
    
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }
    
    public Date getFechaFinal() {
        return fechaFinal;
    }
    
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    
    public boolean isHabilitarFechaIncial() {
        return habilitarFechaIncial;
    }
    
    public void setHabilitarFechaIncial(boolean habilitarFechaIncial) {
        this.habilitarFechaIncial = habilitarFechaIncial;
    }
    
    public PgLiquidacionconsolidado getPgLiquidacionconsolidado() {
        return pgLiquidacionconsolidado;
    }
    
    public void setPgLiquidacionconsolidado(PgLiquidacionconsolidado pgLiquidacionconsolidado) {
        this.pgLiquidacionconsolidado = pgLiquidacionconsolidado;
    }
    
    public ConsultaNominaDTO getConsultaNominaDTO() {
        return consultaNominaDTO;
    }
    
    public void setConsultaNominaDTO(ConsultaNominaDTO consultaNominaDTO) {
        this.consultaNominaDTO = consultaNominaDTO;
    }

    /**
     * @return the pgLiquidacion
     */
    public PgLiquidacion getPgLiquidacion() {
        return pgLiquidacion;
    }

    /**
     * @param pgLiquidacion the pgLiquidacion to set
     */
    public void setPgLiquidacion(PgLiquidacion pgLiquidacion) {
        this.pgLiquidacion = pgLiquidacion;
    }
    
    public List<TablaPendientePago> getListaPagos() {
        return listaPagos;
    }
    
    public void setListaPagos(List<TablaPendientePago> listaPagos) {
        this.listaPagos = listaPagos;
    }
    
    public List<SelectItem> getListaTiposPago() {
        return listaTiposPago;
    }
    
    public void setListaTiposPago(List<SelectItem> listaTiposPago) {
        this.listaTiposPago = listaTiposPago;
    }
    
    public List<TablaConsolidadoLiquidacion> getListaConsolidadoLiquidacion() {
        return listaConsolidadoLiquidacion;
    }
    
    public void setListaConsolidadoLiquidacion(List<TablaConsolidadoLiquidacion> listaConsolidadoLiquidacion) {
        this.listaConsolidadoLiquidacion = listaConsolidadoLiquidacion;
    }
    
}
