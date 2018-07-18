/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.jsfbeans;

import adm.sys.bean.ManejoSessionSFBean;
import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.AdmMenuapp;
import adm.sys.dao.AdmModapp;
import adm.sys.dao.AdmSubmodapp;
import com.icesoft.faces.context.effects.JavascriptContext;
import com.pandora.web.base.BaseJSFBean;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.NoSuchEJBException;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import org.icefaces.ace.component.ajax.AjaxBehavior;
import org.icefaces.ace.component.menuitem.MenuItem;
import org.icefaces.ace.component.submenu.Submenu;
import org.icefaces.ace.model.DefaultMenuModel;
import org.icefaces.ace.model.MenuModel;
import utilidades.Seguridad;

/**
 *
 * @author luis
 */
@SessionScoped
@Named
public class PrincipalJSFBean extends BaseJSFBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private String strUsuario;
    private String strClave;
    private String strTituloPag;
    private AdmColxemp colxempLog = new AdmColxemp();
    private AdmCrgxcol admCrgxcolActivo = new AdmCrgxcol();
    boolean renderNavegacion = false;
    private String navegacion;
    private String beanDest = "";
    private String beanAnt = "";
    private MenuModel menuModel;
    private String imagenLogo = "";
    // public static final String DERECHA ="derecha",DERECHA_ABIERTA="derecha_abierta";
    private String styleDerecha = "derecha";
    private String styleContenido = "contenido_0";

    private ManejoSessionSFBean lookupManejoSessionSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (ManejoSessionSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/ManejoSessionSFBean!adm.sys.bean.ManejoSessionSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private ManejoSessionSFBean mssfb;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones base">

    @Override
    public void init() {
    }

    @Override
    public void limpiarVariables() {
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        if (styleDerecha.equals("derecha")) {
            styleDerecha = "derecha_abierta";
            styleContenido = "contenido_1";
        } else {
            styleDerecha = "derecha";
            styleContenido = "contenido_0";
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructor">   
    public PrincipalJSFBean() {
    }

    private void addAjaxBehaviorTo(MenuItem menuItem) {
        AjaxBehavior ajaxBehavior = new AjaxBehavior();
        ajaxBehavior.setExecute("@form");
        ajaxBehavior.setRender("@form");
        menuItem.addClientBehavior("activate", ajaxBehavior);
    }

    /**
     * Cargar la barra de navegación
     */
    public void cargarMenuNav() {
//        menuBar.setOrientation("Horizaontal");

        //Vertical --Opción para el menu en posición Vertical
        //menu 
        menuModel = new DefaultMenuModel();
        ExpressionFactory factory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
        MethodExpression methodsexpression = factory.createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{principalJSFBean.navRiesgos_ActionEvent}", null, new Class[]{ActionEvent.class});
        MethodExpressionActionListener actionListener = new MethodExpressionActionListener(methodsexpression);

        for (AdmMenuapp menuapp : mssfb.getLstMenuapXUsr(mssfb.getCrgxcolActual().getCxcId())) {
//            Column column = new Column();
            //column.setStyleClass("estiloMenu");
            Submenu menuItemApp = new Submenu();

            //Agregar menu de aplicación a la barra de menu
            menuItemApp.setId("miMenu" + menuapp.getMenId());
            menuItemApp.setLabel(menuapp.getMenNombre());
            menuItemApp.setTransient(true);
            menuItemApp.setStyleClass("letraMenuSubMenu");
            menuItemApp.setStyle("font-style: normal;font-weight: 500; color: #FF8000 !important;text-decoration:none; z-index: 35;");

//            menuItemApp.getChildren().add(column);
            //Consultar la lista de modulos por menu
            menuapp.setAdmModappList(mssfb.getLstModappXMenu(menuapp.getMenId()));
            for (AdmModapp modapp : menuapp.getAdmModappList()) {
                Submenu menuItemMod = new Submenu();
                menuItemMod.setId("miMod" + modapp.getModId());
                menuItemMod.setLabel(modapp.getModNombre());
                menuItemMod.setStyleClass("letraMenuSubMenu");
                menuItemMod.setStyle("font-style: normal;font-weight: normal; color: #FF8000 !important;text-decoration:none; z-index: 35;");
                //Consultar la lista de submódulos por modulo
                modapp.setAdmSubmodappList(mssfb.getLstSubmodappXModXCpe(mssfb.getCrgxcolActual().getCxcId(), modapp.getModId()));
//                column.getChildren().add(menuItemMod);

                
                //agregar el modulo al menu

                for (AdmSubmodapp admSubmodapp : modapp.getAdmSubmodappList()) {
                    MenuItem menuItemSubMod = new MenuItem();
                    menuItemSubMod.setId("miSubMod" + admSubmodapp.getSmdId());
                    menuItemSubMod.setValue(admSubmodapp.getSmdNombre());
                    menuItemSubMod.setStyleClass("letraMenu");
                    menuItemSubMod.setStyle("  font-style: normal;font-weight: bold;color: #FF8000;text-decoration: underline; z-index: 35;");
                    menuItemSubMod.setActionExpression(factory.createMethodExpression(elc,
                            admSubmodapp.getSmdReglanav(), null, new Class<?>[0]));
                    menuItemSubMod.addActionListener(actionListener);
                    menuItemSubMod.getAttributes().put("navegacion", admSubmodapp.getSmdReglanav());
                    menuItemSubMod.getAttributes().put("jsfbean", admSubmodapp.getSmdJsfbean());
                    //menuItemSubMod.set(false);
                    menuItemSubMod.setImmediate(true);
                    boolean existeSubmod= false;
                    for(UIComponent mism : menuItemMod.getChildren()){
                        if(mism.getId().equals(menuItemSubMod.getId())){
                        existeSubmod=true;
                        break;
                        }
                    }
                    if(!existeSubmod){
                     menuItemMod.getChildren().add(menuItemSubMod);
                      addAjaxBehaviorTo(menuItemSubMod);
                    }
                   
                   
                }
                if (menuItemMod.getChildren() != null && !menuItemMod.getChildren().isEmpty()) {
                   menuItemApp.getChildren().add(menuItemMod); 
                }
            }
            if (menuItemApp.getChildren() != null && !menuItemApp.getChildren().isEmpty()) {
                menuModel.addSubmenu(menuItemApp);
            }

        }

        /*
         MenuItem miSalir = new MenuItem();
         miSalir.setId("miSalir");
         miSalir.setStyle("font-weight: bold !important; color:  black;font-size: 14px !important;text-decoration: underline;");
         miSalir.setActionExpression(factory.createMethodExpression(elc,
         "#{principalJSFBean.navMenu_Action}", null, new Class<?>[0]));
         miSalir.addActionListener(actionListener);
        
         miSalir.getAttributes().put("navegacion", "salir");
         miSalir.setValue("Salir");
         //miSalir.setOnclick("window.location = \"http://www.elyoneym.com.co/PandoraAudIntegral-war/Salida.jsp\"");

         //        miSalir.setAjax(true);
         //miSalir.set("@this");
         //        miSalir.setIcon("ui-icon-close");
         miSalir.setImmediate(true);
         menuModel.addMenuItem(miSalir);
         */
        /*
         * agregar acerca de al menu
         */
        /*
         MenuItem miAcerca = new MenuItem();
         miAcerca.setId("miAcerca");
         miAcerca.setStyle("font-weight: bold !important; color:  black;font-size: 14px !important;text-decoration: underline;");
         miAcerca.setActionExpression(factory.createMethodExpression(elc,
         "#{principalJSFBean.navMenu_Action}", null, new Class<?>[0]));
         miAcerca.addActionListener(actionListener);
         miAcerca.getAttributes().put("navegacion", "Acerca");
         miAcerca.setValue("Acerca de");
         //        miAcerca.setIcon("ui-icon-help");
         //miAcerca.set("pnlgMenubar");
         menuModel.addMenuItem(miAcerca);
         */
        //Agregar el panel al eventPahse
//        eventPhase.getChildren().add(pnlgNav);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">
    public String btnIngresar_Action() {
        if (validarColaborador()) {
            try {
                fc = FacesContext.getCurrentInstance();
                elc = fc.getELContext();
                renderNavegacion = true;
                String beanBandEntrada = "bandejaEntradaJSFBean";
                Object objDestJSFBean = elc.getELResolver().getValue(elc, null, beanBandEntrada);
                Class cls = objDestJSFBean.getClass();//
//                if (!beanAnt.isEmpty()) {
//                    Object objAntJSFBean = elc.getELResolver().getValue(elc, null, beanBandEntrada);
//                    Class clsAnt = objAntJSFBean.getClass();
//                    Method mthdLimpiarVar = clsAnt.getDeclaredMethod("limpiarVariables", new Class<?>[0]);
//                    mthdLimpiarVar.invoke(objAntJSFBean, new Object[0]);
//                }

                Method mthdInit = cls.getDeclaredMethod("init", new Class<?>[0]);
                mthdInit.invoke(objDestJSFBean, new Object[0]);
                cargarMenuNav();
//                adjsfb.init();
//                cpjsfb.init();
//                RequestContext context = RequestContext.getCurrentInstance();
//                context.execute("openWindow();");

                imagenLogo = (colxempLog.getColCedula().getVntRfTipocliente() == null ? "logo1" : colxempLog.getColCedula().getVntRfTipocliente().getTclLogo());
                return "ingresar";
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(PrincipalJSFBean.class.getName()).log(Level.SEVERE, null, ex);
                mensaje = "Error al iniciar la bandeja de entrada";
               
                cargarMsg(FacesMessage.SEVERITY_ERROR);
                return "";
            }
        } else {
             cargarMsg(FacesMessage.SEVERITY_ERROR);
            renderNavegacion = false;
            return "";
        }

    }

    /**
     * Evento Action que devuelve la regla de navegaci�n
     *
     * @return
     */
    public String navMenu_Action() {
        return navegacion;
    }

    /**
     * ActionListener para la barra de menu principal
     *
     * @param ae
     */
    public void navRiesgos_ActionEvent(ActionEvent ae) {
        Map<String, Object> map = ae.getComponent().getAttributes();
        navegacion = (String) map.get("navegacion");
        switch (navegacion) {
            case "salir":
                renderNavegacion = false;
                beanAnt = beanDest;
               beanDest = (String) map.get("jsfbean");
                elc = FacesContext.getCurrentInstance().getELContext();
                if (beanAnt != null) {

                    if (!beanAnt.isEmpty()) {
                        try {
                            Object objAntJSFBean = elc.getELResolver().getValue(elc, null, beanAnt);
                            Class clsAnt = objAntJSFBean.getClass();
                            Method mthdLimpiarVar = clsAnt.getDeclaredMethod("limpiarVariables", new Class<?>[0]);
                            mthdLimpiarVar.invoke(objAntJSFBean, new Object[0]);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                            Logger.getLogger(PrincipalJSFBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                //mssfb.remove();
                break;
            case "acercaDe":
                //FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "acerca/acercaDeKAYYROS.xhtml");
                //mostrarError("Accion en construcción", 1);
                break;
         //   case "cambiarClave":
            // mostrarError("Accion en construcción", 1);
            //   break;
            case "manualApp":
                mostrarError("Accion en construcción", 1);
                break;

            default:
                try {
                    beanAnt = beanDest;
                    beanDest = (String) map.get("jsfbean");
                    elc = FacesContext.getCurrentInstance().getELContext();
                    Object objDestJSFBean = elc.getELResolver().getValue(elc, null, beanDest);
                    Class cls = objDestJSFBean.getClass();

                    if (beanAnt != null && !beanAnt.isEmpty()) {
                        Object objAntJSFBean = elc.getELResolver().getValue(elc, null, beanAnt);
                        Class clsAnt = objAntJSFBean.getClass();
                        Method mthdLimpiarVar = clsAnt.getDeclaredMethod("limpiarVariables", new Class<?>[0]);
                        mthdLimpiarVar.invoke(objAntJSFBean, new Object[0]);
                    }

                    Method mthdInit = cls.getDeclaredMethod("init", new Class<?>[0]);
                    mthdInit.invoke(objDestJSFBean, new Object[0]);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException |
                        NoSuchMethodException | SecurityException ex) {
                    Logger.getLogger(PrincipalJSFBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        /*    if (navegacion.equals("salir")) {
         renderNavegacion = false;
         beanAnt = beanDest;
         beanDest = (String) map.get("jsfbean");
         elc = FacesContext.getCurrentInstance().getELContext();
         if (beanAnt != null) {

         if (!beanAnt.isEmpty()) {
         try {
         Object objAntJSFBean = elc.getELResolver().getValue(elc, null, beanAnt);
         Class clsAnt = objAntJSFBean.getClass();
         Method mthdLimpiarVar = clsAnt.getDeclaredMethod("limpiarVariables", new Class<?>[0]);
         mthdLimpiarVar.invoke(objAntJSFBean, new Object[0]);
         } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
         Logger.getLogger(PrincipalJSFBean.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         }

         mssfb.remove();
         } else {
         try {
         beanAnt = beanDest;
         beanDest = (String) map.get("jsfbean");
         elc = FacesContext.getCurrentInstance().getELContext();
         Object objDestJSFBean = elc.getELResolver().getValue(elc, null, beanDest);
         Class cls = objDestJSFBean.getClass();

         if (beanAnt != null && !beanAnt.isEmpty()) {
         Object objAntJSFBean = elc.getELResolver().getValue(elc, null, beanAnt);
         Class clsAnt = objAntJSFBean.getClass();
         Method mthdLimpiarVar = clsAnt.getDeclaredMethod("limpiarVariables", new Class<?>[0]);
         mthdLimpiarVar.invoke(objAntJSFBean, new Object[0]);
         }

         Method mthdInit = cls.getDeclaredMethod("init", new Class<?>[0]);
         mthdInit.invoke(objDestJSFBean, new Object[0]);
         } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException |
         NoSuchMethodException | SecurityException ex) {
         Logger.getLogger(PrincipalJSFBean.class.getName()).log(Level.SEVERE, null, ex);
         }
         }*/
    }

    public void btnIngresar_Action(ActionEvent ae) {
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones">

    public boolean validarColaborador() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        mensaje = "";
        mssfb = lookupManejoSessionSFBeanBean();
        colxempLog.setCpeUsuario(strUsuario);
        colxempLog.setCpeClave(Seguridad.hashPasswordSha512(strClave));
        mssfb.validarCol(colxempLog);
        colxempLog = mssfb.getColxempLog();
        if (colxempLog.getCpeId() == null) {
            mensaje = "Usuario o clave incorrecto";

            // cargarMsg();
            try {
                mssfb.remove();
            } catch (NoSuchEJBException e) {
                mensaje = "Error al liberar el recurso";
            }
            if (httpSession != null) {
                httpSession.removeAttribute("usrLog");
            }

            return false;
        } else {

            if (mssfb.setCrgXColActual(Boolean.TRUE)) {
                httpSession.setAttribute("usrLog", colxempLog);
                admCrgxcolActivo = mssfb.getCrgxcolActual();
                mensaje = "";
                return true;
            } else {
                try {
                    mssfb.remove();
                } catch (NoSuchEJBException e) {
                    mensaje = "Error al liberar el recurso";
                }
                if (httpSession != null) {
                    httpSession.removeAttribute("usrLog");
                }
                mensaje = "Usuario o clave incorrecto";
                return false;
            }

        }

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">

    /**
     * @return the navegacion
     */
    public String getNavegacion() {
        return navegacion;
    }

    /**
     * @param navegacion the navegacion to set
     */
    public void setNavegacion(String navegacion) {
        this.navegacion = navegacion;
    }

    public String getBeanDest() {
        return beanDest;
    }

    public void setBeanDest(String beanDest) {
        this.beanDest = beanDest;
    }

    /**
     * @return the strUsuario
     */
    public String getStrUsuario() {
        return strUsuario;
    }

    /**
     * @param strUsuario the strUsuario to set
     */
    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }

    /**
     * @return the strClave
     */
    public String getStrClave() {
        return strClave;
    }

    /**
     * @param strClave the strClave to set
     */
    public void setStrClave(String strClave) {
        this.strClave = strClave;
    }

    /**
     * @return the strTituloPag
     */
    public String getStrTituloPag() {
        return strTituloPag;
    }

    /**
     * @param strTituloPag the strTituloPag to set
     */
    public void setStrTituloPag(String strTituloPag) {
        this.strTituloPag = strTituloPag;
    }

    /**
     * @return the mssfb
     */
    public ManejoSessionSFBean getMssfb() {
        return mssfb;
    }

    public AdmCrgxcol getAdmCrgxcolActivo() {
        return admCrgxcolActivo;
    }

    public void setAdmCrgxcolActivo(AdmCrgxcol admCrgxcolActivo) {
        this.admCrgxcolActivo = admCrgxcolActivo;
    }

    public AdmColxemp getColxempLog() {
        return colxempLog;
    }

    public void setColxempLog(AdmColxemp colxempLog) {
        this.colxempLog = colxempLog;
    }

    public boolean isRenderNavegacion() {
        return renderNavegacion;
    }

    public void setRenderNavegacion(boolean renderNavegacion) {
        this.renderNavegacion = renderNavegacion;
    }

    /**
     * @return the menuModel
     */
    public MenuModel getMenuModel() {
        return menuModel;
    }

    /**
     * @param menuModel the menuModel to set
     */
    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }
    //</editor-fold>

    /**
     * @return the styleDerecha
     */
    public String getStyleDerecha() {
        return styleDerecha;
    }

    /**
     * @param styleDerecha the styleDerecha to set
     */
    public void setStyleDerecha(String styleDerecha) {
        this.styleDerecha = styleDerecha;
    }

    /**
     * @return the styleContenido
     */
    public String getStyleContenido() {
        return styleContenido;
    }

    /**
     * @param styleContenido the styleContenido to set
     */
    public void setStyleContenido(String styleContenido) {
        this.styleContenido = styleContenido;
    }

    /**
     * @return the imagenLogo
     */
    public String getImagenLogo() {
        return imagenLogo;
    }

    /**
     * @param imagenLogo the imagenLogo to set
     */
    public void setImagenLogo(String imagenLogo) {
        this.imagenLogo = imagenLogo;
    }
}
