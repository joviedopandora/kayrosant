/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.SysEsttarea;
import adm.sys.dao.SysTarea;
import com.icesoft.faces.context.Resource;
import com.pandora.adm.cmp.CmpAprobarSolsSFBean;
import com.pandora.adm.cmp.CmpRecepPedidoSFBean;
import com.pandora.adm.dao.CmpDetremision;
import com.pandora.adm.dao.CmpEntregapedido;
import com.pandora.adm.dao.CmpPxraprob;
import com.pandora.adm.dao.CmpRemisioninv;
import com.pandora.adm.dao.CmpRequiscomp;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.base.RecursosOut;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import utilidades.BinarioInforme;

/**
 * Implementar el paso de recepción de pedido de elementos entregados por el
 * proveedor y cargarlos en el inventario.
 *
 * @author lfchacon
 */
@SessionScoped
@Named
public class RecepcionPedidoJSFBean extends BaseJSFBean implements IPasos, Serializable {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    private com.icesoft.faces.context.Resource jasperResourceZip;

    private CmpAprobarSolsSFBean lookupCmpAprobarSolsSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (CmpAprobarSolsSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/CmpAprobarSolsSFBean!com.pandora.adm.cmp.CmpAprobarSolsSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private CmpAprobarSolsSFBean cassfb;
    CmpRecepPedidoSFBean crpsfb;

    private CmpRecepPedidoSFBean lookupCmpRecepPedidoSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (CmpRecepPedidoSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/CmpRecepPedidoSFBean!com.pandora.adm.cmp.CmpRecepPedidoSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private CmpEntregapedido cmpEntregapedido = new CmpEntregapedido();
    private CmpPxraprob cmpPxraprob = new CmpPxraprob();
    private List<TablaEntregaPedido> lstTablaEntregaPedido = new ArrayList<>();
    private List<TablaRemision> lstTablaRemision = new ArrayList<>();
    private String strCmpObservacion;
    private Date datCmpEntregapedido;
    private List<CmpPxraprob> cmpPxraprobs = new ArrayList<>();
    private List<TablaProdAprob> lstTablaProdAprobs = new ArrayList<>();
    private List<TablaDetalleRemision> lstTablaDetalleRemision = new ArrayList<>();
    private List<TablaReqComp> lstTablaReqComp = new ArrayList<>();
    private TablaReqComp tablaReqCompSel = new TablaReqComp();
    private boolean blnMostrarPanel;
    private boolean blnMostrarInf;
    private boolean blnMostrarCerrarProceso = false;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">

    @Override
    public void init() {
        crpsfb = lookupCmpRecepPedidoSFBeanBean();
        cassfb = lookupCmpAprobarSolsSFBeanBean();
        crpsfb.setAdmCrgxcolLog(pjsfb.getAdmCrgxcolActivo());
        crpsfb.setSysTareaActual(pjsfb.getMssfb().getSysSegtareaActual().getStrId());
        blnMostrarPanel = false;
        blnMostrarInf = false;
        blnMostrarCerrarProceso = false;
//        cargarProdXAprob();
        cargarRequisiciones();
        cargarListaEntregaPedido();

    }

    @Override
    public void limpiarVariables() {
        crpsfb.remove();
        cassfb.remove();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">

    private void cargarListaEntregaPedido() {
        lstTablaEntregaPedido.clear();
        for (CmpEntregapedido entregapedido : crpsfb.getLstCmpEntregapedidosXUsuarioXTarea(pjsfb.getAdmCrgxcolActivo().getCxcId(), pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId())) {
            TablaEntregaPedido tep = new TablaEntregaPedido();
            tep.setCmpEntregapedido(entregapedido);
            lstTablaEntregaPedido.add(tep);
        }
    }

    private void cargarProdXAprob() {
        lstTablaProdAprobs.clear();
        for (CmpPxraprob cp : cassfb.getLstCmpPxraprobXStrId(pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId())) {
            TablaProdAprob tablaProdAprob = new TablaProdAprob(cp);
            lstTablaProdAprobs.add(tablaProdAprob);
        }
    }

    private void cargarRequisiciones() {
        lstTablaReqComp.clear();
        for (CmpRequiscomp requiscomp : cassfb.getLstCmpRequiscompXAbierta(false)) {
            TablaReqComp trc = new TablaReqComp();
            trc.setCmpRequiscomp(requiscomp);
            trc.setLstCmpPxraprobsXCrq(cassfb.getLstCmpPxraprobsXCrq(requiscomp.getCrqId()));
            lstTablaReqComp.add(trc);
        }
    }

    private void grabarRecepcionPedido() {
        List<CmpEntregapedido> lstCmpEntregapedidosGrabar = new ArrayList<>();
        for (TablaEntregaPedido tep : lstTablaEntregaPedido) {
            CmpEntregapedido ce = tep.getCmpEntregapedido();
            ce.setEtpRecibido(Boolean.TRUE);
            ce.setEtpFecprocrecepcion(new Date());
            ce.setEtpObsr(tep.getStrObservacion());
            ce.setEtpCantrecibida(tep.getIntCantRecibida());
            lstCmpEntregapedidosGrabar.add(ce);
        }
        crpsfb.grabarEntregaPedido(lstCmpEntregapedidosGrabar);
        mostrarError("Grabación exitosa...!", 3);
        SysTarea sysTarea = pjsfb.getMssfb().getSysSegtareaActual().getStrId();
        sysTarea.setEtrId(new SysEsttarea(3));
        sysTarea = astslb.editarSysTarea(sysTarea);

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">   

    public void btnGrabarCerrarProceso_ActionEvent(ActionEvent ae) {

        List<AdmCrgxcol> lstAdmCrgxcolDestino = new ArrayList<>();
        for (CmpRequiscomp cr : cassfb.getLstCmpRequiscompXAprobadoXTarea(Boolean.TRUE, pjsfb.getMssfb().getSysSegtareaActual().getStrId().getStrId())) {
            AdmCrgxcol admCrgxcol = cr.getCxcId();
            lstAdmCrgxcolDestino.add(admCrgxcol);
        }

        mostrarError("Grabación exitosa...!", 3);
        astslb.crearSeguimientoTareaPasoOrigenDest(pjsfb.getMssfb().getSysSegtareaActual().getStrId(), 3L, 24L, lstAdmCrgxcolDestino);
    }

    public void btnGenInfRemision_ACtionEvent(ActionEvent ae) {
        try {
            SysTarea sysTarea = pjsfb.getMssfb().getSysSegtareaActual().getStrId();
            List<BinarioInforme> lstBinarioInformes = new ArrayList<>();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            try (Connection con = jdbcProcAud.getConnection()) {
                for (CmpRemisioninv cr : crpsfb.getLstCmpRemisioninvXStr(sysTarea.getStrId())) {
                    HashMap hmParametros = new HashMap();
                    hmParametros.put("p_str_id", sysTarea.getStrId());
                    hmParametros.put("p_rmi_id", cr.getRmiId());
                    hmParametros.put("SUBREPORT_DIR", ec.getRealPath("/WEB-INF/classes/")
                            + File.separator + "reportes" + File.separator + "cmp" + File.separator);

                    InputStream reporteStreamFer = ec.getResourceAsStream("/WEB-INF/classes/"
                            + "reportes" + File.separator + "cmp" + File.separator + "RemisionInventario.jasper");

                    JasperPrint jp = JasperFillManager.fillReport(reporteStreamFer, hmParametros, con);
                    ByteArrayOutputStream outputStreamfer = new ByteArrayOutputStream();
                    JasperExportManager.exportReportToPdfStream(jp, outputStreamfer);
                    BinarioInforme binarioInforme = new BinarioInforme(outputStreamfer, cr.getRmiId() + ".pdf");
                    lstBinarioInformes.add(binarioInforme);
                }
                if (generarZipLstPdf(lstBinarioInformes)) {
                    jasperResourceZip = new RecursosOut("ÓrdenesCompra", baosManejoZip);
                    blnMostrarCerrarProceso = true;
                }
            }
        } catch (SQLException | JRException ex) {
            Logger.getLogger(CompAprbSolsJSFBean.class.getName()).log(Level.SEVERE, null, ex);
            blnMostrarCerrarProceso = false;
        }
    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
    }
    
    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    public void rowDtRequisiones_ActionEvent(ActionEvent ae) {
//        Map map = ae.getComponent().getAttributes();
//        tablaReqCompSel = (TablaReqComp) map.get("trc");
//        blnMostrarPanel = true;
//    }
    private void cargarLstXProdAprobXreq() {
        for (CmpPxraprob pxraprob : cassfb.getLstAprob()) {
        }
    }

    public void btnGrabarEntregaPedido_ActionEvent(ActionEvent ae) {
        SysTarea sysTarea = pjsfb.getMssfb().getSysSegtareaActual().getStrId();       
        List<CmpEntregapedido> lstCmpEntregapedidoGrabar = new ArrayList<>();
        for (TablaReqComp trc : lstTablaReqComp) {
            CmpRemisioninv cr = new CmpRemisioninv();
            cr.setRmiFechaproceso(new Date());
            cr.setRmiFechaentrega(trc.getDatFechaEntrega());
            cr.setRmiObsrentrega(trc.getStrObservacion());
            cr.setRmiEst(Boolean.TRUE);
            cr.setCxcIdorigen(pjsfb.getAdmCrgxcolActivo());
            cr.setCxcIddestino(trc.getCmpRequiscomp().getCxcId());
            cr.setStrId(sysTarea.getStrId());
            cr = crpsfb.grabarRemision(cr);

            for (CmpPxraprob cp : trc.getLstCmpPxraprobsXCrq()) {
                CmpDetremision cd = new CmpDetremision();
                cd.setRmiId(cr);
                cd.setPraId(cp);
                cd.setDrmEst(Boolean.TRUE);
                cd.setDrmProcentregado(Boolean.FALSE);
                cd.setDrmProcinventario(Boolean.FALSE);
                cd = crpsfb.grabarDetalleRemision(cd);

                CmpEntregapedido ce = new CmpEntregapedido();
                ce.setDrmId(cd);
                ce.setEtpFechaentrega(cd.getRmiId().getRmiFechaentrega());
                ce.setEtpEstado(Boolean.TRUE);
                ce.setEtpFechaproceso(new Date());
                ce.setEtpEntregado(Boolean.TRUE);
                ce.setEtpRecibido(Boolean.FALSE);
                ce.setStrId(sysTarea.getStrId());
                lstCmpEntregapedidoGrabar.add(ce);
            }
        }
        crpsfb.grabarEntregaPedido(lstCmpEntregapedidoGrabar);
//        List<CmpEntregapedido> lstCmpEntregapedidos = new ArrayList<>();
//        for (TablaProdAprob tpa : lstTablaProdAprobs) {
//            CmpEntregapedido ce = new CmpEntregapedido();
//            ce.setPraId(tpa.getCmpPxraprob());
//            ce.setEtpFechaproceso(new Date());
//            ce.setEtpObsr(tpa.getCmpPxraprob().getPraObsrentrega());
//            ce.setEtpEntregado(Boolean.FALSE);
//            ce.setEtpEstado(Boolean.TRUE);
//            ce.setEtpFechaentrega(tpa.getCmpPxraprob().getPraFecentregapedido());
//            lstCmpEntregapedidos.add(ce);
//        }
//        crpsfb.grabarEntregaPedido(lstCmpEntregapedidos);

//        for (TablaEntregaPedido tep : lstTablaEntregaPedido) {
//            tep.getCmpEntregapedido().setEtpObsr(strCmpObservacion);
//            tep.getCmpEntregapedido().setEtpFechaentrega(datCmpEntregapedido);
//            tep.getCmpEntregapedido().setEtpEntregado(blnCmpEntregado);
//            tep.getCmpEntregapedido().setEtpEstado(Boolean.TRUE);
//            tep.getCmpEntregapedido().setEtpFechaproceso(new Date());
//            crpsfb.grabarEntregaPedido((List<CmpEntregapedido>) tep.getCmpEntregapedido());
//        }
//        for (CmpRequiscomp cr : ) {
//            
//        }       
        mostrarError("Grabación exitosa...!", 3);
        blnMostrarInf = true;
        blnMostrarCerrarProceso = false;
        //astslb.crearSeguimientoTareaPasoOrigenDest(pjsfb.getMssfbl().getSysSegtareaActual().getStrId(), 3L, 24L, lstAdmCrgxcolDestino);
    }

    public void btnGrabarRecepcionPedido_ActionEvent(ActionEvent ae) {
        grabarRecepcionPedido();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">    
    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        
    }
    
    @Override
    public boolean grabarPaso() {
        return true;
    }

    @Override
    public boolean validarForm() {
        return true;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">

    public Resource getJasperResourceZip() {
        return jasperResourceZip;
    }

    public void setJasperResourceZip(Resource jasperResourceZip) {
        this.jasperResourceZip = jasperResourceZip;
    }

    /**
     * @return the cmpEntregapedido
     */
    public CmpEntregapedido getCmpEntregapedido() {
        return cmpEntregapedido;
    }

    /**
     * @param cmpEntregapedido the cmpEntregapedido to set
     */
    public void setCmpEntregapedido(CmpEntregapedido cmpEntregapedido) {
        this.cmpEntregapedido = cmpEntregapedido;
    }

    /**
     * @return the lstTablaEntregaPedido
     */
    public List<TablaEntregaPedido> getLstTablaEntregaPedido() {
        return lstTablaEntregaPedido;
    }

    /**
     * @param lstTablaEntregaPedido the lstTablaEntregaPedido to set
     */
    public void setLstTablaEntregaPedido(List<TablaEntregaPedido> lstTablaEntregaPedido) {
        this.lstTablaEntregaPedido = lstTablaEntregaPedido;
    }

    /**
     * @return the datCmpEntregapedido
     */
    public Date getDatCmpEntregapedido() {
        return datCmpEntregapedido;
    }

    /**
     * @param datCmpEntregapedido the datCmpEntregapedido to set
     */
    public void setDatCmpEntregapedido(Date datCmpEntregapedido) {
        this.datCmpEntregapedido = datCmpEntregapedido;
    }

    /**
     * @return the strCmpObservacion
     */
    public String getStrCmpObservacion() {
        return strCmpObservacion;
    }

    /**
     * @param strCmpObservacion the strCmpObservacion to set
     */
    public void setStrCmpObservacion(String strCmpObservacion) {
        this.strCmpObservacion = strCmpObservacion;
    }

    /**
     * @return the cmpPxraprobs
     */
    public List<CmpPxraprob> getCmpPxraprobs() {
        return cmpPxraprobs;
    }

    /**
     * @param cmpPxraprobs the cmpPxraprobs to set
     */
    public void setCmpPxraprobs(List<CmpPxraprob> cmpPxraprobs) {
        this.cmpPxraprobs = cmpPxraprobs;
    }

    /**
     * @return the lstTablaProdAprobs
     */
    public List<TablaProdAprob> getLstTablaProdAprobs() {
        return lstTablaProdAprobs;
    }

    /**
     * @param lstTablaProdAprobs the lstTablaProdAprobs to set
     */
    public void setLstTablaProdAprobs(List<TablaProdAprob> lstTablaProdAprobs) {
        this.lstTablaProdAprobs = lstTablaProdAprobs;
    }

    /**
     * @return the lstTablaReqComp
     */
    public List<TablaReqComp> getLstTablaReqComp() {
        return lstTablaReqComp;
    }

    /**
     * @param lstTablaReqComp the lstTablaReqComp to set
     */
    public void setLstTablaReqComp(List<TablaReqComp> lstTablaReqComp) {
        this.lstTablaReqComp = lstTablaReqComp;
    }

    /**
     * @return the cmpPxraprob
     */
    public CmpPxraprob getCmpPxraprob() {
        return cmpPxraprob;
    }

    /**
     * @param cmpPxraprob the cmpPxraprob to set
     */
    public void setCmpPxraprob(CmpPxraprob cmpPxraprob) {
        this.cmpPxraprob = cmpPxraprob;
    }

    /**
     * @return the tablaReqCompSel
     */
    public TablaReqComp getTablaReqCompSel() {
        return tablaReqCompSel;
    }

    /**
     * @param tablaReqCompSel the tablaReqCompSel to set
     */
    public void setTablaReqCompSel(TablaReqComp tablaReqCompSel) {
        this.tablaReqCompSel = tablaReqCompSel;
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
     * @return the lstTablaRemision
     */
    public List<TablaRemision> getLstTablaRemision() {
        return lstTablaRemision;
    }

    /**
     * @param lstTablaRemision the lstTablaRemision to set
     */
    public void setLstTablaRemision(List<TablaRemision> lstTablaRemision) {
        this.lstTablaRemision = lstTablaRemision;
    }

    /**
     * @return the lstTablaDetalleRemision
     */
    public List<TablaDetalleRemision> getLstTablaDetalleRemision() {
        return lstTablaDetalleRemision;
    }

    /**
     * @param lstTablaDetalleRemision the lstTablaDetalleRemision to set
     */
    public void setLstTablaDetalleRemision(List<TablaDetalleRemision> lstTablaDetalleRemision) {
        this.lstTablaDetalleRemision = lstTablaDetalleRemision;
    }

    public boolean isBlnMostrarCerrarProceso() {
        return blnMostrarCerrarProceso;
    }

    public void setBlnMostrarCerrarProceso(boolean blnMostrarCerrarProceso) {
        this.blnMostrarCerrarProceso = blnMostrarCerrarProceso;
    }
    
    /**
     * @return the blnMostrarInf
     */
    public boolean isBlnMostrarInf() {
        return blnMostrarInf;
    }

    /**
     * @param blnMostrarInf the blnMostrarInf to set
     */
    public void setBlnMostrarInf(boolean blnMostrarInf) {
        this.blnMostrarInf = blnMostrarInf;
    }
    //</editor-fold>
}
