/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.base;

import adm.sys.bean.AdmSysTareaSLBean;
import adm.sys.dao.AdmInforme;
import com.icesoft.faces.context.effects.JavascriptContext;
import com.pandora.jsfbeans.AppBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;
import utilidades.BinarioInforme;
import utilidades.EnFormatDate;

/**
 *
 * @author luis
 */
@SessionScoped
public abstract class BaseJSFBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Constantes">
//    private DataSource getJdbcProcAud() throws NamingException {
//        Context c = new InitialContext();
//        return (DataSource) c.lookup("java:comp/env/jdbcProcAud");
//    }
    @Resource(name = "jdbcSysPandora")
    protected DataSource jdbcProcAud;
    private String ruta_recursos = "/WEB-INF/classes/";
    protected com.icesoft.faces.context.Resource jasperResourceExcel;
    protected com.icesoft.faces.context.Resource jasperResourcePDF;
    protected com.icesoft.faces.context.Resource jrResourceExcel;
    protected com.icesoft.faces.context.Resource jrResourcePDF;
    /**
     * Item seleccione con valor -1
     */
    protected SelectItem itemSeleccione = new SelectItem("-1", "SELECCIONE >>");
    protected SelectItem itemSeleccioneInt = new SelectItem(-1, "SELECCIONE >>");
    protected SelectItem itemSeleccioneLong = new SelectItem(-1L, "SELECCIONE >>");
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Seguridad">

    /**
     * Elimina símbolos especiales que se pueden utilizar para inyección SQL
     *
     * @param cadena Cadena a tratar
     * @return Cadena con símbolos especiales eliminados
     */
    protected String eliminarSE(String cadena) {
        String cadenatratada = cadena;
        cadenatratada = cadenatratada.replace("-", "");
        cadenatratada = cadenatratada.replace("<", "");
        cadenatratada = cadenatratada.replace(">", "");
        cadenatratada = cadenatratada.replace("=", "");
        cadenatratada = cadenatratada.replace("?", "");
        cadenatratada = cadenatratada.replace("/", "");
        cadenatratada = cadenatratada.replace("#", "");
        cadenatratada = cadenatratada.replace("$", "");
        cadenatratada = cadenatratada.replace("%", "");
        cadenatratada = cadenatratada.replace("(", "");
        cadenatratada = cadenatratada.replace(")", "");
        cadenatratada = cadenatratada.replace("\"", "");
        cadenatratada = cadenatratada.replace("'", "");
        cadenatratada = cadenatratada.replace("|", "");
        cadenatratada = cadenatratada.replace("*", "");
        cadenatratada = cadenatratada.replace("\\", "");

        return cadenatratada;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables comunes">
    private String strImgNuevoEdit = "plus_32.png";
    protected boolean blnImgError = false;
    protected String mensaje;
    protected String resumenMsg;
    protected FacesContext fc = FacesContext.getCurrentInstance();
    protected ELContext elc = fc.getELContext();
    protected int numPanel = 1;
    private String strClaseStyleMensajes = "styleMensajeError";
    protected com.icesoft.faces.context.Resource jrResourceCSV;
    /**
     * Manejo de tareas general
     */
    @EJB
    protected AdmSysTareaSLBean astslb;

    /**
     * Bean de administración general
     */
    /**
     * Validar si la fecha inicial está antes de la final
     *
     * @param pFecIni
     * @param pFecFin
     * @return
     */
    public boolean validarFechaMayorXDia(Date pFecIni, Date pFecFin) {
        DateTime jdtFecIni = new DateTime(pFecIni);
        DateTime jdtFecFin = new DateTime(pFecFin);
        return jdtFecIni.toDateMidnight().isBefore(jdtFecFin.toDateMidnight());
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones comunes">

    /**
     * Ir al servlet que de descarga de archivos
     *
     * @param pRecursoDescarga
     */
    protected void irAServletDescarga(RecursoDescarga pRecursoDescarga) {
        try {
            fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            URL url = new URL(ec.getRequestScheme(),
                    ec.getRequestServerName(),
                    ec.getRequestServerPort(),
                    ec.getRequestContextPath());
//            String contextoApp = ec.getApplicationContextPath();
            HttpSession httpSession = (HttpSession) ec.getSession(false);
            httpSession.setAttribute("rd", pRecursoDescarga);
            ec.redirect(url.toString() + "/DescargarArchivoServlet");
        } catch (IOException ex) {
            Logger.getLogger(BaseJSFBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Generar informe
     *
     * @param hmParamInf Parámetros del informe
     * @param pAdmInforme Informe
     * @param tipoExportacion Tipo exportación, 1 para excel, 2 para PDF, 3 para
     * html, 4 para CSV, 5 para xlsx
     * @return Recurso binario resultado de la generación del informe
     */
    protected RecursoDescarga genInfRecurso(HashMap hmParamInf, AdmInforme pAdmInforme, Integer tipoExportacion) {
        try {
            fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            try (Connection con = jdbcProcAud.getConnection()) {
                InputStream inputStream = ec.getResourceAsStream(ruta_recursos + pAdmInforme.getInfJasperruta() + "/" + pAdmInforme.getInfJasper());
                hmParamInf.put("SUBREPORT_DIR", ec.getRealPath(ruta_recursos + pAdmInforme.getInfJasperruta()) + "/");
                JasperPrint jp = JasperFillManager.fillReport(inputStream, hmParamInf, con);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                RecursoDescarga jrResourceRetorna = null;
                switch (tipoExportacion) {
                    case 1:
                        JExcelApiExporter jeae = new JExcelApiExporter();

                        jeae.setParameter(JRXlsExporterParameter.INPUT_STREAM, inputStream);
                        jeae.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
                        jeae.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
                        jeae.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                        jeae.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF8");
                        jeae.exportReport();
                        jrResourceRetorna = new RecursoDescarga(baos.toByteArray(), "application/xls", pAdmInforme.getInfNombre());

                        break;

                    case 2:

                        JasperExportManager.exportReportToPdfStream(jp, baos);

                        jrResourceRetorna = new RecursoDescarga(baos.toByteArray(), "application/pdf", pAdmInforme.getInfNombre());

                        break;
                    case 3:

                        break;
                    case 4:
                        JRCsvExporter exporterCSV = new JRCsvExporter();
                        exporterCSV.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
                        exporterCSV.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
                        exporterCSV.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
                        exporterCSV.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
                        exporterCSV.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, true);
                        exporterCSV.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF8");
                        exporterCSV.exportReport();
                        jrResourceRetorna = new RecursoDescarga(baos.toByteArray(), "application/xls", pAdmInforme.getInfNombre());
                        break;

                    case 5:
                        JRXlsxExporter exporterXLSX = new JRXlsxExporter();
                        exporterXLSX.setParameter(JRXlsExporterParameter.INPUT_STREAM, inputStream);
                        exporterXLSX.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
                        exporterXLSX.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
                        exporterXLSX.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                        exporterXLSX.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF8");
                        exporterXLSX.exportReport();
                        jrResourceRetorna = new RecursoDescarga(baos.toByteArray(), "application/xlsx", pAdmInforme.getInfNombre());
                        break;
                }
                if (jrResourceRetorna != null) {
                    return jrResourceRetorna;
                } else {
                    return null;
                }

            }

        } catch (JRException | SQLException ex) {
            Logger.getLogger(BaseJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Generar informe
     *
     * @param hmParamInf Parámetros del informe
     * @param pAdmInforme Informe
     * @param tipoExportacion Tipo exportación, 1 para excel, 2 para PDF, 3 para
     * html, 4 para CSV
     * @param strParametros Lista de parámetros, se espera, ruta logo
     * obligatorio y opcional el nombre del archivo que será descargado
     * @return Recurso binario resultado de la generación del informe
     */
    protected RecursoDescarga genInfRecurso(HashMap hmParamInf, AdmInforme pAdmInforme, Integer tipoExportacion, String... strParametros) {
        try {
            fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            try (Connection con = jdbcProcAud.getConnection()) {

                InputStream inputStream = ec.getResourceAsStream(getRuta_recursos() + pAdmInforme.getInfJasperruta() + "/" + pAdmInforme.getInfJasper());
                hmParamInf.put("SUBREPORT_DIR", ec.getRealPath(getRuta_recursos() + pAdmInforme.getInfJasperruta()) + "/");
                hmParamInf.put("rutalogo", ec.getRealPath(getRuta_recursos() + strParametros[0]));
                JasperPrint jp = JasperFillManager.fillReport(inputStream, hmParamInf, con);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                RecursoDescarga jrResourceRetorna = null;

                String nombreArchivoSalidaRporte = strParametros.length == 2 ? strParametros[1] : pAdmInforme.getInfNombre();
                nombreArchivoSalidaRporte = nombreArchivoSalidaRporte.length() >= 40 ? nombreArchivoSalidaRporte = nombreArchivoSalidaRporte.substring(0, 39) : nombreArchivoSalidaRporte;
                switch (tipoExportacion) {
                    case 1:
                        JExcelApiExporter jeae = new JExcelApiExporter();

                        jeae.setParameter(JRXlsExporterParameter.INPUT_STREAM, inputStream);
                        jeae.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
                        jeae.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
                        jeae.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                        jeae.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF8");
                        jeae.exportReport();

                        jrResourceRetorna = new RecursoDescarga(baos.toByteArray(), "application/xls", nombreArchivoSalidaRporte + ".xls");
                        break;

                    case 2:

                        JasperExportManager.exportReportToPdfStream(jp, baos);

                        jrResourceRetorna = new RecursoDescarga(baos.toByteArray(), "application/pdf", nombreArchivoSalidaRporte + ".pdf");

                        break;
                    case 3:

                        break;
                    case 4:
                        JRCsvExporter exporterCSV = new JRCsvExporter();
                        exporterCSV.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
                        exporterCSV.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
                        exporterCSV.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
                        exporterCSV.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
                        exporterCSV.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, true);
                        exporterCSV.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF8");
                        exporterCSV.exportReport();
                        jrResourceRetorna = new RecursoDescarga(baos.toByteArray(), "application/xls", nombreArchivoSalidaRporte + ".csv");
                        break;
                }
                if (jrResourceRetorna != null) {
                    return jrResourceRetorna;
                } else {
                    return null;
                }

            }

        } catch (JRException | SQLException ex) {
            Logger.getLogger(BaseJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String getHoraFromDate(Date pFecIni) {
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
        return sd.format(pFecIni);

    }
    protected ByteArrayOutputStream baosManejoZip = new ByteArrayOutputStream();
    private ZipOutputStream zipOutputStream;

    public boolean generarZipLstPdf(List<BinarioInforme> lstBinarioInformes) {

        try {

            zipOutputStream = new ZipOutputStream(baosManejoZip);
            for (BinarioInforme binarioInforme : lstBinarioInformes) {
                // Transfer bytes from the file to the ZIP file
                try {
                    byte[] bs = binarioInforme.getBaos().toByteArray();
                    InputStream inputStream = new ByteArrayInputStream(bs);
                    CRC32 crc = new CRC32();
                    ZipEntry zipEntry = new ZipEntry(binarioInforme.getNombreArchivo());

                    crc.update(bs);
                    zipEntry.setCrc(crc.getValue());
                    zipOutputStream.putNextEntry(zipEntry);

                    int len;
                    while ((len = inputStream.read(bs)) > 0) {
                        zipOutputStream.write(bs, 0, len);
                    }
                } catch (ZipException ze) {
                    continue;
                }
            }
            zipOutputStream.setLevel(6);
            zipOutputStream.finish();
            zipOutputStream.close();

            return true;
        } catch (IOException ex) {
            Logger.getLogger(BaseJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int getDiaDiferencia(Date pFecIni, Date pFecFin) {
        DateTime jdtFecIni = new DateTime(pFecIni);
        DateTime jdtFecFin = new DateTime(pFecFin);
        return Days.daysBetween(jdtFecIni.toDateMidnight(), jdtFecFin.toDateMidnight()).getDays();

    }

    public int getMesDiferencia(Date pFecIni, Date pFecFin) {
        DateTime jdtFecIni = new DateTime(pFecIni);
        DateTime jdtFecFin = new DateTime(pFecFin);
        return Months.monthsBetween(jdtFecIni.toDateMidnight(), jdtFecFin.toDateMidnight()).getMonths();

    }

    public int getAnioDiferencia(Date pFecIni, Date pFecFin) {
        DateTime jdtFecIni = new DateTime(pFecIni);
        DateTime jdtFecFin = new DateTime(pFecFin);
        return Years.yearsBetween(jdtFecIni.toDateMidnight(), jdtFecFin.toDateMidnight()).getYears();

    }

    protected PrincipalJSFBean getPrincipalJSFBean() {
        fc = FacesContext.getCurrentInstance();
        elc = fc.getELContext();
        return (PrincipalJSFBean) elc.getELResolver().getValue(elc, null, "principalJSFBean");
    }

    protected AppBean getAppBean() {
        fc = FacesContext.getCurrentInstance();
        elc = fc.getELContext();
        return (AppBean) elc.getELResolver().getValue(elc, null, "appBean");
    }

    /**
     * Mostrar mensaje de error
     *
     * @param errors
     */
    public void mostrarError(String errors) {
        blnImgError = true;
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), " setTimeout(function(){initMsg();},1);");
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "document.getElementById('masterForm:idmsg').style.visibility = 'visible';");
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), " setTimeout(function(){finMSg();},3000);");
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), " setTimeout(function(){document.getElementById('masterForm:idmsg').style.visibility = 'hidden';},7000);");
        FacesContext context = FacesContext.getCurrentInstance();
        getPrincipalJSFBean().setStrClaseStyleMensajes("styleMensajeError");
        FacesMessage message = new FacesMessage();
        message.setDetail(errors);
        message.setSummary("Error");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);

        final String componentId = "menuBar";
        UIViewRoot root = context.getViewRoot();
        UIComponent c = findComponent(root, componentId);
        context.addMessage(c.getClientId(), message);

        /*FacesContext.getCurrentInstance().addMessage(null, message);
         JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "myNotificacionBar.show();");*/
    }

    public void mostrarError(String set, String errors) {
        blnImgError = true;
        strClaseStyleMensajes = "styleMensajeError";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        message.setDetail(errors);
        message.setSummary("Error");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        final String componentId = "menuBar";
        UIViewRoot root = context.getViewRoot();
        UIComponent c = findComponent(root, componentId);
        context.addMessage(c.getClientId(), message);

        /*  FacesContext.getCurrentInstance().addMessage(set, message);
         JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "myNotificacionBar.show();");*/
    }

    public void mostrarError(String errors, int tipo) {
        blnImgError = (true);
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), " setTimeout(function(){initMsg();},1);");
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), " setTimeout(function(){document.getElementById('masterForm:idmsg').style.visibility = 'visible';},1);");
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), " setTimeout(function(){finMSg();},3000);");
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), " setTimeout(function(){document.getElementById('masterForm:idmsg').style.visibility = 'hidden';},7000);");
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        switch (tipo) {
            case 1:
                getPrincipalJSFBean().setStrClaseStyleMensajes("styleMensajeError");
                message.setDetail(errors);
                message.setSummary("Error");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                break;
            case 2:
                getPrincipalJSFBean().setStrClaseStyleMensajes("styleMensajeAdvertencia");
                message.setDetail(errors);
                message.setSummary("Información");
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                break;
            case 3:
                getPrincipalJSFBean().setStrClaseStyleMensajes("styleMensajeExito");
                message.setDetail(errors);
                message.setSummary("Grabado");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                break;
        }

        final String componentId = "menuBar";
        UIViewRoot root = context.getViewRoot();
        UIComponent c = findComponent(root, componentId);
        context.addMessage(c.getClientId(), message);

        /*      FacesContext.getCurrentInstance().addMessage(null, message);
         JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "myNotificacionBar.show();");*/
    }

    /**
     * Generar informe
     *
     * @param hmParamInf Parámetros del informe
     * @param pAdmInforme Informe
     * @param tipoExportacion Tipo exportación, 1 para excel, 2 para PDF, 3 para
     * html, 4 para CSV
     */
    protected void genInfirme(HashMap hmParamInf, AdmInforme pAdmInforme, Integer tipoExportacion) {
        try {
            fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            try (Connection con = jdbcProcAud.getConnection()) {
                InputStream inputStream = ec.getResourceAsStream(getRuta_recursos() + pAdmInforme.getInfJasperruta() + "/" + pAdmInforme.getInfJasper());
                hmParamInf.put("SUBREPORT_DIR", ec.getRealPath(getRuta_recursos() + pAdmInforme.getInfJasperruta()) + "/");
                JasperPrint jp = JasperFillManager.fillReport(inputStream, hmParamInf, con);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                switch (tipoExportacion) {
                    case 1:
                        JExcelApiExporter jeae = new JExcelApiExporter();

                        jeae.setParameter(JRXlsExporterParameter.INPUT_STREAM, inputStream);
                        jeae.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
                        jeae.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
                        jeae.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                        jeae.exportReport();
                        jrResourceExcel = new RecursosOut(pAdmInforme.getInfNombre(), baos);
                        break;

                    case 2:

                        JasperExportManager.exportReportToPdfStream(jp, baos);

                        jrResourcePDF = new RecursosOut(pAdmInforme.getInfJasper(), baos);

                        break;
                    case 3:

                        break;
                    case 4:
                        JRCsvExporter exporterCSV = new JRCsvExporter();
                        exporterCSV.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
                        exporterCSV.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
                        exporterCSV.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
                        exporterCSV.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
                        exporterCSV.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, true);
//                        exporterCSV.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, true);
                        exporterCSV.exportReport();
                        jrResourceCSV = new RecursosOut(pAdmInforme.getInfJasper(), baos);
                        break;
                }
            }

        } catch (JRException | SQLException ex) {
            Logger.getLogger(BaseJSFBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Eliminar los elementos seleccionados de una tabla
     *
     * @param lst
     * @return Lista de elementos eliminados para hacer las operaciones
     * correspondientes
     */
    protected List retirarElemTabla(List lst) {
        List lstRerirar = new ArrayList();
        for (Object object : lst) {
            try {
                Class claseObjLst = object.getClass();
                Method metodo = claseObjLst.getMethod("isSeleccionado", new Class<?>[0]);
                Object objResultSel = metodo.invoke(object, new Object[0]);
                Boolean blnSel = (Boolean) objResultSel;
                if (blnSel) {
                    lstRerirar.add(object);
                }

            } catch (SecurityException | NoSuchMethodException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(BaseJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        lst.removeAll(lstRerirar);
        return lstRerirar;
    }

    /**
     * Resaltar la fila seleccionada de una tabla, la lista debe ser de objetos
     * que hereden de la clase TablaBaseGrid
     *
     * @param lst
     * @param objSel
     */
    public void resaltarFilaTabla(List lst, Object objSel) {
        for (Object object : lst) {
            if (object.equals(objSel)) {
                try {
                    Class claseObjLst = object.getClass();
                    Class[] argSetSel = {Boolean.TYPE};

                    Method metodo = claseObjLst.getMethod("setSeleccionado", argSetSel);
                    Object[] objParam = {Boolean.TRUE};
                    metodo.invoke(object, objParam);

                    Class[] argSetClaseSel = {String.class};
                    Method setClaseSel = claseObjLst.getMethod("setClaseSel", argSetClaseSel);

                    Object[] objParamClase = {"seleccione"};
                    setClaseSel.invoke(object, objParamClase);

                } catch (IllegalAccessException | NoSuchMethodException | SecurityException
                        | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(BaseJSFBean.class.getName()).log(Level.WARNING, null, ex);
                }

            } else {
                try {
                    Class claseObjLst = object.getClass();
                    Class[] argSetSel = {Boolean.TYPE};

                    Method metodo = claseObjLst.getMethod("setSeleccionado", argSetSel);
                    Object[] objParam = {Boolean.FALSE};
                    metodo.invoke(object, objParam);

                    Class[] argSetClaseSel = {String.class};
                    Method setClaseSel = claseObjLst.getMethod("setClaseSel", argSetClaseSel);

                    Object[] objParamClase = {""};
                    setClaseSel.invoke(object, objParamClase);

                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                    Logger.getLogger(BaseJSFBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    /**
     * Cambiar el estado del campo seleccionado de una lista tipo tabla
     *
     * @param lst Lista
     * @param sel estado del campo
     */
    protected void selTodoLst(List lst, boolean sel) {
        for (Object object : lst) {

            Class claseObjLst = object.getClass();
            try {
                Class[] argSetSel = {Boolean.TYPE};

                Method metodo = claseObjLst.getMethod("setSeleccionado", argSetSel);
                Object[] objParam = {sel};

                metodo.invoke(object, objParam);

            } catch (IllegalAccessException | NoSuchMethodException | SecurityException
                    | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(BaseJSFBean.class.getName()).log(Level.WARNING, null, ex);
            }

        }
    }

    public void resetearInputs() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        PartialViewContext ajaxContext = facesContext.getPartialViewContext();
        UIComponent root = facesContext.getViewRoot();
        Collection<String> renderIds = ajaxContext.getRenderIds();
        for (String renderId : renderIds) {
            UIComponent form = findComponent(root, renderId);
            if (form != null) {
                clearComponentHierarchy(form);
            }
        }
    }

    private static UIComponent findComponent(UIComponent base, String id) {
        if (id.equals(base.getId())) {
            return base;
        }
        UIComponent kid = null;
        UIComponent result = null;
        Iterator kids = base.getFacetsAndChildren();
        while (kids.hasNext() && (result == null)) {
            kid = (UIComponent) kids.next();
            if (id.equals(kid.getId())) {
                result = kid;
                break;
            }
            result = findComponent(kid, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    private void clearComponentHierarchy(UIComponent pComponent) {

        if (pComponent.isRendered()) {

            if (pComponent instanceof EditableValueHolder) {
                EditableValueHolder editableValueHolder = (EditableValueHolder) pComponent;
                editableValueHolder.setSubmittedValue(null);
                editableValueHolder.setValue(null);
                editableValueHolder.setLocalValueSet(false);
                editableValueHolder.setValid(true);
            }

            for (Iterator<UIComponent> iterator = pComponent.getFacetsAndChildren(); iterator.hasNext();) {
                clearComponentHierarchy(iterator.next());
            }

        }
    }

    /**
     * Mostrar mensaje en el growl
     *
     * @param pSeverity
     */
    protected void cargarMsg(FacesMessage.Severity pSeverity) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        message.setDetail(mensaje);
        message.setSummary(resumenMsg);
        message.setSeverity(pSeverity);
        facesContext.addMessage(null, message);
        // JavascriptContext.addJavascriptCall(facesContext, "myNotificacionBar.show();");

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones abstractas">

    public abstract void init();

    public abstract void limpiarVariables();

    /**
     * Función buscar que debe ir en todos los formularios
     */
    public abstract void buscarGen_ActionEvent(ActionEvent ae);

    public abstract void navLateral_ActionEvent(ActionEvent ae);

    //</editor-fold>
    @SuppressWarnings("empty-statement")
    public void copiarArchivo(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();

            // previous code: destination.transferFrom(source, 0, source.size());
            // to avoid infinite loops, should be:
            long count = 0;
            long size = source.size();
            while ((count += destination.transferFrom(source, count, size - count)) < size);
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    protected  boolean esNumero(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public String convertDateToStringFormat(Date date, EnFormatDate formatDate) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sd = new SimpleDateFormat(formatDate.getFormat());
        return sd.format(date);
    }
    //<editor-fold defaultstate="collapsed" desc="Propiedades">

    public com.icesoft.faces.context.Resource getJasperResourceExcel() {
        return jasperResourceExcel;
    }

    public void setJasperResourceExcel(com.icesoft.faces.context.Resource jasperResourceExcel) {
        this.jasperResourceExcel = jasperResourceExcel;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the strImgNuevoEdit
     */
    public String getStrImgNuevoEdit() {
        return strImgNuevoEdit;
    }

    /**
     * @param strImgNuevoEdit the strImgNuevoEdit to set
     */
    public void setStrImgNuevoEdit(String strImgNuevoEdit) {
        this.strImgNuevoEdit = strImgNuevoEdit;
    }

    public String getResumenMsg() {
        return resumenMsg;
    }

    public void setResumenMsg(String resumenMsg) {
        this.resumenMsg = resumenMsg;
    }

    /**
     * @return the numPanel
     */
    public int getNumPanel() {
        return numPanel;
    }

    /**
     * @param numPanel the numPanel to set
     */
    public void setNumPanel(int numPanel) {
        this.numPanel = numPanel;
    }

    /**
     * @return the strClaseStyleMensajes
     */
    public String getStrClaseStyleMensajes() {
        return strClaseStyleMensajes;
    }

    /**
     * @param strClaseStyleMensajes the strClaseStyleMensajes to set
     */
    public void setStrClaseStyleMensajes(String strClaseStyleMensajes) {
        this.strClaseStyleMensajes = strClaseStyleMensajes;
    }

    public com.icesoft.faces.context.Resource getJasperResourcePDF() {
        return jasperResourcePDF;
    }

    public void setJasperResourcePDF(com.icesoft.faces.context.Resource jasperResourcePDF) {
        this.jasperResourcePDF = jasperResourcePDF;
    }
    //</editor-fold>

    public com.icesoft.faces.context.Resource getJrResourceCSV() {
        return jrResourceCSV;
    }

    public void setJrResourceCSV(com.icesoft.faces.context.Resource jrResourceCSV) {
        this.jrResourceCSV = jrResourceCSV;
    }

    public com.icesoft.faces.context.Resource getJrResourceExcel() {
        return jrResourceExcel;
    }

    public void setJrResourceExcel(com.icesoft.faces.context.Resource jrResourceExcel) {
        this.jrResourceExcel = jrResourceExcel;
    }

    public com.icesoft.faces.context.Resource getJrResourcePDF() {
        return jrResourcePDF;
    }

    public void setJrResourcePDF(com.icesoft.faces.context.Resource jrResourcePDF) {
        this.jrResourcePDF = jrResourcePDF;
    }

    /**
     * @return the ruta_recursos
     */
    public String getRuta_recursos() {
        return ruta_recursos;
    }

    /**
     * @param ruta_recursos the ruta_recursos to set
     */
    public void setRuta_recursos(String ruta_recursos) {
        this.ruta_recursos = ruta_recursos;
    }

    /**
     * Lee el contenido textual desde un stream de entrada
     *
     * @param input Stream de entrada
     * @param encoding Codificación
     * @return El contenido del stream de entrada
     * @throws IOException Cualquier excepción de Entrada/Salida
     */
    public static String fileToString(InputStream input, String encoding) throws IOException {
        StringWriter sw = new StringWriter();
        InputStreamReader in = new InputStreamReader(input, encoding);

        char[] buffer = new char[1024 * 2];
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
            sw.write(buffer, 0, n);
        }
        return sw.toString();
    }

}
