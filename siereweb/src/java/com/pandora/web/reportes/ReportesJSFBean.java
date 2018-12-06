/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.reportes;

import adm.sys.dao.AdmInforme;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.RecursoDescarga;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import javax.faces.event.ActionEvent;
import utilidades.EnTipoCliente;
import utilidades.ManejoFecha;

/**
 *
 * @author Javier
 */
@Named(value = "reportesJSFBean")
@SessionScoped
public class ReportesJSFBean extends BaseJSFBean implements Serializable {

    private Date fechaInicial = null;
    private Date fechaFinal = null;
    private String strReporte = null;
    private com.icesoft.faces.context.Resource jaspResource = null;
    private boolean mostrarFactura = true;
    /* Creates a new instance of ReportesJSFBean
     */

    public ReportesJSFBean() {
    }

    @Override
    public void init() {

        numPanel = 1;
        fechaInicial = null;
        fechaFinal = null;
        strReporte = "Facturas por periodo";
        jaspResource = null;
        mostrarFactura = true;
        if (getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getColCedula().getVntRfTipocliente() != null
                && getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getColCedula().getVntRfTipocliente().getTclId().equals(EnTipoCliente.KIDS.getId())) {
            mostrarFactura = false;
        }

    }

    @Override
    public void limpiarVariables() {
        numPanel = 1;
        fechaInicial = null;
        fechaFinal = null;
        strReporte = null;
        jaspResource = null;
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void generarInforme_ActionEvent(ActionEvent ae) {
        if (fechaInicial == null) {
            mostrarError("Fecha Inicial es requerida", 1);
            return;
        }
        if (fechaFinal == null) {
            mostrarError("Fecha Final es requerida", 1);
            return;
        }
        if (ManejoFecha.compararDates(fechaInicial, fechaFinal) == 1) {
            mostrarError("Fecha Inicial debe ser menor que la final", 1);
            return;
        }

        if (ManejoFecha.compararDates(fechaFinal, new Date()) == 1) {
            mostrarError("Fecha Final debe ser menor que la fecha del sistenas ", 1);
            return;
        }
        fechaInicial = ManejoFecha.formatearFecha(fechaInicial);
        fechaFinal = ManejoFecha.formatearFecha(fechaFinal);
        AdmInforme informe = null;
        HashMap hmParametros = new HashMap();
        Integer tipoCliente = EnTipoCliente.COORPORATIVO.getId();
        if (getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getColCedula().getVntRfTipocliente() != null
                && getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getColCedula().getVntRfTipocliente().getTclId().equals(EnTipoCliente.KIDS.getId())) {
            tipoCliente = getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getColCedula().getVntRfTipocliente().getTclId();
        }

        hmParametros.put("pFechaInicial", fechaInicial);
        hmParametros.put("pFechaFinal", fechaFinal);
        hmParametros.put("pTipoCliente", tipoCliente);
        switch (numPanel) {
            case 1:
                informe = astslb.getAdmInformeXId(5);
                break;
            case 2:
                informe = astslb.getAdmInformeXId(6);
                break;
            case 3:
                informe = astslb.getAdmInformeXId(7);
                break;
            case 4:
                informe = astslb.getAdmInformeXId(8);
                break;

        }
       // jaspResource = 
         RecursoDescarga rd =   genInfRecurso(hmParametros, informe, 1);
        if (rd != null) {
            irAServletDescarga(rd);
        } else {
            mostrarError("Error al generar reporte, consulte con el administrador");
        }
              
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        //  fechaInicial = null;
        //fechaFinal = null;
        jaspResource = null;
        switch (numPanel) {
            case 1:
                strReporte = "Facturas por periodo";
                break;
            case 2:
                strReporte = "Remisiones por periodo";
                break;
            case 3:
                strReporte = "Ventas por periodo";
                break;
            case 4:
                strReporte = "Registro de llamadas por periodo";
                break;

        }
    }

    /**
     * @return the fechaInicial
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param fechaInicial the fechaInicial to set
     */
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the strReporte
     */
    public String getStrReporte() {
        return strReporte;
    }

    /**
     * @param strReporte the strReporte to set
     */
    public void setStrReporte(String strReporte) {
        this.strReporte = strReporte;
    }

    /**
     * @return the jaspResource
     */
    public com.icesoft.faces.context.Resource getJaspResource() {
        return jaspResource;
    }

    /**
     * @param jaspResource the jaspResource to set
     */
    public void setJaspResource(com.icesoft.faces.context.Resource jaspResource) {
        this.jaspResource = jaspResource;
    }

    /**
     * @return the mostrarFactura
     */
    public boolean isMostrarFactura() {
        return mostrarFactura;
    }

    /**
     * @param mostrarFactura the mostrarFactura to set
     */
    public void setMostrarFactura(boolean mostrarFactura) {
        this.mostrarFactura = mostrarFactura;
    }
}
