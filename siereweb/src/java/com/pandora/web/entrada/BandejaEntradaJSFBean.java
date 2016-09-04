/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.entrada;

import adm.sys.bean.AdmSysTareaSLBean;
import adm.sys.dao.AdmNotificacion;
import adm.sys.dao.SysPropaso;
import adm.sys.dao.SysSegtarea;
import adm.sys.dao.SysTarea;
import com.pandora.mod.venta.VentaSFBean;
import com.pandora.mod.venta.dao.VntEstventa;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.web.base.BaseJSFBean;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author luis
 */
@SessionScoped
@Named
public class BandejaEntradaJSFBean extends BaseJSFBean implements Serializable {

    private String accionTareaPaso = "";
    private List<TablaSegTarea> lstSegtareaXUsr = new ArrayList<>();
    private List<TablaSysPaso> lstTablaSysPasos = new ArrayList<>();
    private List<TablaNotifiacion> lstNotificaciones = new ArrayList<>();
    private Integer idTipoCliente;

    private VentaSFBean lookupVentaSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (VentaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/VentaSFBean!com.pandora.mod.venta.VentaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private VentaSFBean vsfb;
    VntRegistroventa vr = new VntRegistroventa();

    public void accionNotificacion_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        Integer accion = Integer.valueOf((String) ae.getComponent().getAttributes().get("accion"));
        TablaNotifiacion tsp = (TablaNotifiacion) map.get("tabla");
        tsp.getNotificacion().setNotEstado(accion);
        tsp.getNotificacion().setAdmCrgxcol(getPrincipalJSFBean().getAdmCrgxcolActivo());
        tsp.getNotificacion().setNotFechaprocesocierre(new Date());
        tsp.setNotificacion(vsfb.editarAdmNotificacion(tsp.getNotificacion()));
        if (accion != 4) {
            cargarNotificaciones();
            //lstNotificaciones.remove(tsp);
        }

    }

    public void accionXTareaXPaso_ActionEvent(ActionEvent ae) {
        try {
            Map<String, Object> map = ae.getComponent().getAttributes();
            accionTareaPaso = (String) map.get("navegacion");
            String beanpaso = (String) map.get("beanpaso");
            SysSegtarea ss = (SysSegtarea) map.get("segtarea");
            TablaSysPaso tsp = (TablaSysPaso) map.get("paso");

            //Crear la tarea si no existe para el proceso de venta
            if (ss == null) {
                //if (getPrincipalJSFBean().getMssfb().getSysSegtareaActual() == null) {
                if (tsp.getSysPropaso().getSprId().getSprId().equals("MXPGV")) {
                    ss = astslb.crearTareaInicioXProcXCxc("MXPGV", 2, 30L, getPrincipalJSFBean().getAdmCrgxcolActivo());

                    vr.setRgvtFechacre(new Date());
                    vr.setStrId(ss.getStrId().getStrId());
                    vr.setRgvtAnulado(Boolean.FALSE);
                    vr.setRgvtEst(Boolean.TRUE);
                    vr.setRgvtPagado(Boolean.FALSE);
                    //Colocar el estado de la venta en inciada
                    vr.setEstrvntId(vsfb.getEstventaXId(1));
                    if (ss.getStrId().getStrId() != null) {
                        vsfb.editarRegVenta(vr);

                    }
                }
                //
            } else {
                vsfb.getVntRegistroventaXStrId(ss.getStrId().getStrId(), Boolean.TRUE);
            }

            getPrincipalJSFBean().getMssfb().setSysSegtareaActual(ss);
            elc = FacesContext.getCurrentInstance().getELContext();
            Object objDestJSFBean = elc.getELResolver().getValue(elc, null, beanpaso);
            Class cls = objDestJSFBean.getClass();
            Method mthdInit = cls.getDeclaredMethod("init", new Class<?>[0]);
            mthdInit.invoke(objDestJSFBean, new Object[0]);
            Class[] setNumPanel = {Integer.TYPE};
            Method mthdSetNumPanel = cls.getMethod("setNumPanel", setNumPanel);
            Object[] objNumPanel = {1};
            mthdSetNumPanel.invoke(objDestJSFBean, objNumPanel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarPasosInicioXUsr() {
        lstTablaSysPasos.clear();
        for (SysPropaso sp : getPrincipalJSFBean().getMssfb().getLstPasosInicioXUsr(Boolean.TRUE)) {
            TablaSysPaso tsp = new TablaSysPaso(sp);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.DAY_OF_MONTH, sp.getSpsVigencia());
            tsp.setFechaFinPaso(calendar.getTime());
            lstTablaSysPasos.add(tsp);
        }
    }

    private void cargarNotificaciones() {
        lstNotificaciones.clear();
        for (AdmNotificacion sp : vsfb.getLstAdmNotificacion(1, 1, idTipoCliente)) {
            TablaNotifiacion tsp = new TablaNotifiacion();
            tsp.setNotificacion(sp);
            lstNotificaciones.add(tsp);
        }
    }

    private void cargarTareasXUsr() {
        lstSegtareaXUsr.clear();
        for (SysSegtarea st : getPrincipalJSFBean().getMssfb().getLstTarXUsrActivas(2, Boolean.TRUE)) {
            TablaSegTarea tablaSysTarea = new TablaSegTarea(st);
            int diasDif = getDiaDiferencia(new Date(), st.getSgtFfinal());
            if (diasDif <= 2 && diasDif >= 0) {
                tablaSysTarea.setClaseSegTarea("advVencTarea");
            } else if (diasDif < 0) {
                tablaSysTarea.setClaseSegTarea("tareaVencida");
            } else if (diasDif > 2) {
                tablaSysTarea.setClaseSegTarea("tareaBien");
            }
            lstSegtareaXUsr.add(tablaSysTarea);
        }
    }

    public void btnInicioStd_ActionEvent(ActionEvent ae) {
        cargarNotificaciones();
        //  cargarPasosInicioXUsr();
        //  cargarTareasXUsr();
    }

    public void btnInicioBandEntrada(ActionEvent ae) {
        //    cargarPasosInicioXUsr();
        //   cargarTareasXUsr();
        cargarNotificaciones();
    }

    public String accionXTareaXPaso_Action() {
        return accionTareaPaso;
    }

    @Override
    public void init() {
        numPanel = 1;
        vsfb = lookupVentaSFBeanBean();
        /* cargarTareasXUsr();
         cargarPasosInicioXUsr();
         cargarTareasXUsr();*/
        if (getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente() == null) {
            idTipoCliente = 1;
        } else {
            idTipoCliente = getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente().getTclId();
        }
        cargarNotificaciones();

    }

    @Override
    public void limpiarVariables() {
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
    }

    /**
     * @return the accionTareaPaso
     */
    public String getAccionTareaPaso() {
        return accionTareaPaso;
    }

    /**
     * @param accionTareaPaso the accionTareaPaso to set
     */
    public void setAccionTareaPaso(String accionTareaPaso) {
        this.accionTareaPaso = accionTareaPaso;
    }

    public List<TablaSegTarea> getLstSegtareaXUsr() {
        return lstSegtareaXUsr;
    }

    public void setLstSegtareaXUsr(List<TablaSegTarea> lstSegtareaXUsr) {
        this.lstSegtareaXUsr = lstSegtareaXUsr;
    }

    public List<TablaSysPaso> getLstTablaSysPasos() {
        return lstTablaSysPasos;
    }

    public void setLstTablaSysPasos(List<TablaSysPaso> lstTablaSysPasos) {
        this.lstTablaSysPasos = lstTablaSysPasos;
    }

    public VentaSFBean getVsfb() {
        return vsfb;
    }

    /**
     * @return the lstNotificaciones
     */
    public List<TablaNotifiacion> getLstNotificaciones() {
        return lstNotificaciones;
    }

    /**
     * @param lstNotificaciones the lstNotificaciones to set
     */
    public void setLstNotificaciones(List<TablaNotifiacion> lstNotificaciones) {
        this.lstNotificaciones = lstNotificaciones;
    }
}
