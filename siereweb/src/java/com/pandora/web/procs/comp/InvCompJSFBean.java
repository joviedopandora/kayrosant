/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import adm.sys.bean.ControlBandEntradaSFBean;
import com.pandora.adm.cmp.InvMantSLBean;
import com.pandora.adm.dao.*;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.pagocuenta.dao.FinFormapago;
import com.pandora.web.base.BaseJSFBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author carlos
 */
@Named
public class InvCompJSFBean extends BaseJSFBean {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    @Inject
    PrincipalJSFBean pjsfb;
    @EJB
    InvMantSLBean imslb;
    ControlBandEntradaSFBean cbesfb;
    //<editor-fold defaultstate="collapsed" desc="Variables producto">
    private InvProducto producto = new InvProducto();
    private List<TablaProducto> lstTablaProducto = new ArrayList<>();
    private List<InvCatprod> lstCatProd = new ArrayList<>();
    private boolean blnNuevoProd = false;
    private InvCatprod invCategoriaSel;
    private Integer intCpdId;
    private Integer intProdId;
    private String strProdNombre;
    private String strProdDesc;
    private boolean blnProdEstado = true;
    private boolean blnProdEspecial;
    private boolean blnProdOcacional;
    private boolean blnProdUnico;
    private boolean blnBloqIdProd = false;
    private boolean blnEditProd = false;
    private static final int TODOS = -2;
    private Integer idCategoria = -1;
    private List<SelectItem> lstCategoria = new ArrayList<>();
    private List<TablaPresentProd> lstTablaPresentProd = new ArrayList<>();
    private List<TablaMarca> lstTablaMarca = new ArrayList<>();
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables categoría producto">
    private InvCatprod catprod = new InvCatprod();
    private List<TablaCategoriaProd> lstTablaCatProducto = new ArrayList<>();
    private boolean blnNuevaCatProd = false;
    private Integer intCatId;
    private String strCatNombre;
    private String strCatDesc;
    private boolean blnCatEstado = true;
    private boolean blnBloqIdCatProd = false;
    private boolean blnEditCat = false;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables presentación producto">
    private InvPresentprod presentprod = new InvPresentprod();
    private List<TablaPresentProd> lstTablaPresentProds = new ArrayList<>();
    private boolean blnNuevaPresent = false;
    private Integer intPspId;
    private String strPspNombre;
    private String strPspDesc;
    private boolean blnPspEstado = true;
    private boolean blnBloqIdPsp;
    private boolean blnEditPsp = false;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables presentación por producto">
    private InvPresntxprod presntxprod = new InvPresntxprod();
    private Integer intPxpPrdId;
    private Integer intPxpPspId;
    private boolean blnPxpEst;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables marca">
    private InvMarca marca = new InvMarca();
    private List<TablaMarca> lstTablaMarcas = new ArrayList<>();
    private boolean blnNuevamarca = false;
    private Integer intMarId;
    private String strMarNombre;
    private String strMarDesc;
    private boolean blnMarEstado = true;
    private boolean blnBloqIdMar;
    private boolean blnEditMarca = false;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables marca por producto">
    private InvMarcxprod marcxprod = new InvMarcxprod();
    private TablaMarcaXProd tablaMarcaXProd = new TablaMarcaXProd();
    private List<TablaMarcaXProd> lstTablaMarcaXProd = new ArrayList<>();
    private Integer intMxpPrdId;
    private Integer intMxpMarId;
    private boolean blnMxpEst;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables proveedor">
    private InvProovedor proveedor = new InvProovedor();
    private List<TablaProveedor> lstTablaProveedor = new ArrayList<>();
//    private List<FinFormapago> lstFormaPago = new ArrayList<>();
    private boolean blnNuevoProv = false;
    private FinFormapago finFormaPagoSel;
    private Integer intFpgId;
    private Integer intPrvId;
    private String strPrvNit;
    private Integer intPrvDigVer;
    private String strPrvRazon;
    private String strPrvDesc;
    private String strPrvDir;
    private String strPrvTel1;
    private String strPrvTel2;
    private String strPrvEmail;
    private String strPrvContacto;
    private String strPrvTelContacto;
    private boolean blnPrvEstado;
    private boolean blnBloqIdProv = false;
    private boolean blnEditProv = false;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables forma de pago">
    private List<TablaFormaPago> lstTablaFormaPago = new ArrayList<>();
    private List<SelectItem> lstFormaPago = new ArrayList<>();
    private Integer idFormaPago = -1;
    //</editor-fold>
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">    

    @Override
    public void init() {
        numPanel = 1;
        cbesfb = lookupControlBandEntradaSFBeanBean();
        cbesfb.setAdmColxempLog(pjsfb.getMssfb().getColxempLog());
        lstCatProd = imslb.getLstInvCatprodXEstado(true);
        cargarProductos();
    }

    @Override
    public void limpiarVariables() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">
    //<editor-fold defaultstate="collapsed" desc="Producto">
    private void cargarProductos() {
        lstTablaProducto.clear();
        for (InvProducto ip : imslb.getLstInvProducto()) {
            TablaProducto tp = new TablaProducto(ip);
            lstTablaProducto.add(tp);
        }
    }

    private void cargarProdXCat() {
        lstTablaProducto.clear();
        for (InvProducto ip : imslb.getLstInvProdXCat(intCpdId)) {
            TablaProducto tp = new TablaProducto(ip);
            lstTablaProducto.add(tp);
        }
    }

    private void cargarListaCategoria() {
        lstCategoria.clear();
        lstCategoria.add(new SelectItem(-1, "SELECCIONE>>"));
        for (InvCatprod ic : imslb.getLstInvCatprodXEstado(true)) {
            lstCategoria.add(new SelectItem(ic.getCpdId(), ic.getCpdNombre()));
        }
    }

    private void limpiarCamposProd() {
        intCpdId = -1;
        idCategoria = -1;
        intProdId = null;
        strProdNombre = "";
        strProdDesc = "";
        blnProdEstado = false;
        blnProdEspecial = false;
        blnProdOcacional = false;
        blnProdUnico = false;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Categoría producto">

    private void cargarCategoriaProd() {
        lstTablaCatProducto.clear();
        for (InvCatprod ic : imslb.getLstCatprod()) {
            TablaCategoriaProd tcp = new TablaCategoriaProd(ic);
            lstTablaCatProducto.add(tcp);
        }
    }

    private void limpiarCamposCatProd() {
        intCatId = null;
        strCatNombre = "";
        strCatDesc = "";
        blnCatEstado = false;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Presentación producto">

    private void cargarPresentacionProd() {
        lstTablaPresentProds.clear();
        for (InvPresentprod ip : imslb.getLstInvPresentprod()) {
            TablaPresentProd tpp = new TablaPresentProd(ip);
            lstTablaPresentProds.add(tpp);
        }
    }

    private void cargarPresentXEst() {
        lstTablaPresentProd.clear();
        for (InvPresentprod ipp : imslb.getLstInvPresentprod(true)) {
            TablaPresentProd tpp = new TablaPresentProd(ipp);
            lstTablaPresentProd.add(tpp);
        }
    }

    private void limpiarCamposPresent() {
        intPspId = null;
        strPspNombre = "";
        strPspDesc = "";
        blnPspEstado = false;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Marca">

    private void cargarMarca() {
        lstTablaMarcas.clear();
        for (InvMarca imr : imslb.getLstInvMarca()) {
            TablaMarca tmr = new TablaMarca(imr);
            lstTablaMarcas.add(tmr);
        }
    }

    private void cargarMarcaXEst() {
        lstTablaMarca.clear();
        for (InvMarca im : imslb.getLstInvMarca(true)) {
            TablaMarca tm = new TablaMarca(im);
            lstTablaMarca.add(tm);
        }
    }

    private void limpiarCamposMarca() {
        intMarId = null;
        strMarNombre = "";
        strMarDesc = "";
        blnMarEstado = false;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Proveedor">

    private void cargarListaFormaPago() {
        lstFormaPago.clear();
        lstFormaPago.add(new SelectItem(-1, "SELECIONE >>"));
        for (FinFormapago ff : imslb.getLstFinFormapagoXEstado(true)) {
            lstFormaPago.add(new SelectItem(ff.getFpgId(), ff.getFpgNombre()));
        }
    }

//    private void cargarFormaPago() {
//        lstTablaFormaPago.clear();
//        for (FinFormapago ff : imslb.getLstFinFormapagoXEstado(true)) {
//            TablaFormaPago tfp = new TablaFormaPago(ff);
//            lstTablaFormaPago.add(tfp);
//        }
//    }
    private void cargarProveedores() {
        lstTablaProveedor.clear();
        for (InvProovedor ip : imslb.getLstInvProovedor()) {
            TablaProveedor tp = new TablaProveedor(ip);
            lstTablaProveedor.add(tp);
        }
    }

    private void limpiarCamposProv() {
        idFormaPago = -1;
        intPrvId = null;
        strPrvNit = null;
        intPrvDigVer = null;
        strPrvRazon = "";
        strPrvDesc = "";
        strPrvDir = "";
        strPrvTel1 = "";
        strPrvTel2 = "";
        strPrvEmail = "";
        strPrvContacto = "";
        strPrvTelContacto = "";
        blnPrvEstado = false;
    }
    //</editor-fold>

    private void regresarMant() {
        blnEditProd = false;
        limpiarCamposProd();
        blnEditCat = false;
        limpiarCamposCatProd();
        blnEditMarca = false;
        limpiarCamposMarca();
        blnEditPsp = false;
        limpiarCamposPresent();
        blnEditProv = false;
        limpiarCamposProv();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Eventos">

    public void btnNavInv_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        if (numPanel == 2) {
            cargarCategoriaProd();
        }
        if (numPanel == 3) {
            cargarPresentacionProd();
        }
        if (numPanel == 4) {
            cargarMarca();
        }
        if (numPanel == 5) {
//            lstFormaPago = imslb.getLstFinFormapago(true);
            cargarListaFormaPago();
            cargarProveedores();
        }
        regresarMant();
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //<editor-fold defaultstate="collapsed" desc="Producto">
    public void ddlSelCatProd_ValueChangeEvent(ValueChangeEvent vce) {
        intCpdId = (Integer) vce.getNewValue();
        recargarListaProductos();
    }

    public void recargarListaProductos() {
        if (intCpdId == TODOS) {
            invCategoriaSel = null;
            cargarProductos();
        } else {
            invCategoriaSel = lstCatProd.get(lstCatProd.indexOf(new InvCatprod(intCpdId)));
            cargarProdXCat();
        }
        limpiarCamposProd();
    }

    public void btnNuevoProd_ActionEvent(ActionEvent ae) {
        limpiarCamposProd();
        blnNuevoProd = true;
        blnEditProd = true;
        blnBloqIdProd = false;
        cargarPresentXEst();
        cargarMarcaXEst();
    }

    public void btnGrabEditProd_ActionEvent(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        blnNuevoProd = false;
        if (intCpdId.equals(-1) || intCpdId.equals(-2)) {
            mensaje = "Debe seleccionar la categoría";
            resumenMsg = "Error de validación";
            return;
        }
        if (intProdId == null) {
            mensaje = "Debe escribir el id del producto";
            resumenMsg = "Error de validación";
            return;
        }
        if (strProdNombre == null || strProdNombre.equals("")) {
            mensaje = "Debe escribir el nombre del producto";
            resumenMsg = "Error de validación";
            return;
        } else if (strProdNombre.equals("") || strProdNombre.length() < 4) {
            mensaje = "Debe escribir el nombre del producto";
            resumenMsg = "Error de validación";
            return;
        }
        boolean prod = false;
        if (blnNuevoProd == true) {
            if (imslb.getInvProductoXId(intProdId) == null) {
                mensaje = "El id del producto ya existe";
                resumenMsg = "Error de validación";
                cargarMsg(FacesMessage.SEVERITY_ERROR);
                return;
            }
        }
        if (!prod) {
            producto.setCpdId(imslb.getInvCatprodXId(idCategoria));
            producto.setPrdId(intProdId);
            producto.setPrdNombre(strProdNombre);
            producto.setPrdDesc(strProdDesc);
            producto.setPrdEst(blnProdEstado);
            producto.setPrdEsp(blnProdEspecial);
            producto.setPrdOcasnl(blnProdOcacional);
            producto.setPrdUnico(blnProdUnico);

            List<InvPresntxprod> lstInvPresntxMandar = new ArrayList<>();
            for (TablaPresentProd tablaPresentProd : lstTablaPresentProd) {
                if (tablaPresentProd.isSeleccionado()) {
                    InvPresntxprod presxprod = new InvPresntxprod();
                    presxprod.setPrdId(producto);
                    presxprod.setPspId(tablaPresentProd.getInvPresentprod());
                    presxprod.setPxpEst(Boolean.TRUE);
                    lstInvPresntxMandar.add(presxprod);
                }
            }

            List<InvMarcxprod> lstInvMarcxprodMandar = new ArrayList<>();
            for (TablaMarca tablaMarca : lstTablaMarca) {
                if (tablaMarca.isSeleccionado()) {
                    InvMarcxprod im = new InvMarcxprod();
                    im.setPrdId(producto);
                    im.setMarId(tablaMarca.getInvMarca());
                    im.setMxpEst(Boolean.TRUE);
                    lstInvMarcxprodMandar.add(im);
                }
            }
            producto.setInvPresntxprodList(lstInvPresntxMandar);
            producto.setInvMarcxprodList(lstInvMarcxprodMandar);
            producto = imslb.addInvProducto(producto);

            if (producto != null) {
                resumenMsg = "Actualización producto";
                mensaje = "Producto actualizado correctamente";
            } else {
                resumenMsg = "Actualización producto";
                mensaje = "Error al grabar";
            }
            cargarMsg(FacesMessage.SEVERITY_INFO);
            limpiarCamposProd();
            recargarListaProductos();
            blnNuevoProd = false;
        }
        blnEditProd = false;
    }

    public void dtRowEditProducto_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        cargarListaCategoria();
        TablaProducto filaSel = (TablaProducto) map.get("filaSel");
        blnBloqIdProd = true;
        if (filaSel != null) {
            producto = filaSel.getInvProducto();
//            intCpdId = filaSel.getInvProducto().getCpdId().getCpdId();
            idCategoria = filaSel.getInvProducto().getCpdId().getCpdId();
            intProdId = filaSel.getInvProducto().getPrdId();
            strProdNombre = filaSel.getInvProducto().getPrdNombre();
            strProdDesc = filaSel.getInvProducto().getPrdDesc();
            blnProdEstado = filaSel.getInvProducto().getPrdEst();
            blnProdEspecial = filaSel.getInvProducto().getPrdEsp();
            blnProdOcacional = filaSel.getInvProducto().getPrdOcasnl();
            blnProdUnico = filaSel.getInvProducto().isPrdUnico();
            blnEditProd = true;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Categoría producto">

    public void btnNuevaCatProd_ActionEvent(ActionEvent ae) {
        limpiarCamposCatProd();
        blnNuevaCatProd = true;
        blnEditCat = true;
        blnBloqIdCatProd = false;
    }

    public void btnGrabEditCatProd_ActionEvent(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        blnNuevaCatProd = false;
        blnEditCat = false;
//        if (intCatId == null) {
//            mensaje = "Debe escribir el id de la categoría";
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de validación", mensaje));
//            return;
//        }
//        if (strCatNombre == null || strCatNombre.equals("")) {
//            mensaje = "Debe escribir el nombre de la categoría";
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de validación", mensaje));
//            return;
//        } else if (strCatNombre.equals("") || strCatNombre.length() < 4) {
//            mensaje = "Debe escribir el nombre de la categoría";
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de validación", mensaje));
//            return;
//        }

        catprod.setCpdId(intCatId);
        catprod.setCpdNombre(strCatNombre);
        catprod.setCpdDesc(strCatDesc);
        catprod.setCpdEst(blnCatEstado);
        catprod = imslb.addInvCatprod(catprod);
        cargarCategoriaProd();
        limpiarCamposCatProd();
//        resetearInputs();
        mensaje = "Categoría actualizada correctamente";
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", mensaje));
    }

    public void dtRowEditCatProd_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        TablaCategoriaProd filaSel = (TablaCategoriaProd) map.get("filaSel");
        blnBloqIdCatProd = true;
        if (filaSel != null) {
            catprod = filaSel.getInvCatprod();
            intCatId = filaSel.getInvCatprod().getCpdId();
            strCatNombre = filaSel.getInvCatprod().getCpdNombre();
            strCatDesc = filaSel.getInvCatprod().getCpdDesc();
            blnCatEstado = filaSel.getInvCatprod().getCpdEst();
            blnEditCat = true;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Presentación producto">

    public void btnNuevaPresent_ActionEvent(ActionEvent ae) {
        limpiarCamposPresent();
        blnNuevaPresent = true;
        blnEditPsp = true;
        blnBloqIdPsp = false;
    }

    public void btnGrabEditPresentProd_ActionEvent(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        blnNuevaPresent = false;
        blnEditPsp = false;
        presentprod.setPspId(intPspId);
        presentprod.setPspNombre(strPspNombre);
        presentprod.setPspDesc(strPspDesc);
        presentprod.setPspEst(blnPspEstado);
        presentprod = imslb.addInvPresentprod(presentprod);
        cargarPresentacionProd();
        mensaje = "Presentación actualizada correctamente";
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", mensaje));
    }

    public void dtRowEditPresentacion_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        TablaPresentProd filaSel = (TablaPresentProd) map.get("filaSel");
        blnBloqIdPsp = true;
        if (filaSel != null) {
            presentprod = filaSel.getInvPresentprod();
            intPspId = filaSel.getInvPresentprod().getPspId();
            strPspNombre = filaSel.getInvPresentprod().getPspNombre();
            strPspDesc = filaSel.getInvPresentprod().getPspDesc();
            blnPspEstado = filaSel.getInvPresentprod().getPspEst();
            blnEditPsp = true;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Marca">

    public void btnNuevaMarca_ActionEvent(ActionEvent ae) {
        limpiarCamposMarca();
        blnNuevamarca = true;
        blnEditMarca = true;
        blnBloqIdMar = false;
    }

    public void btnGrabEditMarca_ActionEvent(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        blnNuevamarca = false;
        blnEditMarca = false;
        marca.setMarId(intMarId);
        marca.setMarNombre(strMarNombre);
        marca.setMarDesc(strMarDesc);
        marca.setMarEst(blnMarEstado);
        marca = imslb.addInvMarca(marca);
        cargarMarca();
        mensaje = "Marca actualizada correctamente";
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", mensaje));
    }

    public void dtRowEditMarca_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        TablaMarca filaSel = (TablaMarca) map.get("filaSel");
        blnBloqIdMar = true;
        if (filaSel != null) {
            marca = filaSel.getInvMarca();
            intMarId = filaSel.getInvMarca().getMarId();
            strMarNombre = filaSel.getInvMarca().getMarNombre();
            strMarDesc = filaSel.getInvMarca().getMarDesc();
            blnMarEstado = filaSel.getInvMarca().getMarEst();
            blnEditMarca = true;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Proveedor">

    public void ddlSelFormaPago_ValueChangeEvent(ValueChangeEvent vce) {
        intFpgId = (Integer) vce.getNewValue();
    }

    public void btnNuevoProv_ActionEvent(ActionEvent ae) {
        limpiarCamposProv();
        blnNuevoProv = true;
        blnEditProv = true;
        blnBloqIdProv = false;
    }

    public void btnGrabEditProv_ActionEvent(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        blnNuevoProv = false;
        proveedor.setFpgId(imslb.getFinFormapagoXId(idFormaPago));
        proveedor.setPrvId(intPrvId);
        proveedor.setPrvNit(strPrvNit);
        proveedor.setPrvDigver(intPrvDigVer);
        proveedor.setPrvRazonsoc(strPrvRazon);
        proveedor.setPrvDesc(strPrvDesc);
        proveedor.setPrvDireccion(strPrvDir);
        proveedor.setPrvTel1(strPrvTel1);
        proveedor.setPrvTel2(strPrvTel2);
        proveedor.setPrvEmail(strPrvEmail);
        proveedor.setPrvContacto(strPrvContacto);
        proveedor.setPrvTelcontacto(strPrvTelContacto);
        proveedor.setPrvEstado(blnPrvEstado);
        proveedor = imslb.addInvProovedor(proveedor);
        limpiarCamposProv();
        cargarProveedores();
        blnEditProv = false;
    }

    public void dtRowEditProveedor_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        TablaProveedor filaSel = (TablaProveedor) map.get("filaSel");
        blnBloqIdProv = true;
        if (filaSel != null) {
            proveedor = filaSel.getInvProveedor();
            idFormaPago = filaSel.getInvProveedor().getFpgId().getFpgId();
            intPrvId = filaSel.getInvProveedor().getPrvId();
            strPrvNit = filaSel.getInvProveedor().getPrvNit();
            intPrvDigVer = filaSel.getInvProveedor().getPrvDigver();
            strPrvRazon = filaSel.getInvProveedor().getPrvRazonsoc();
            strPrvDesc = filaSel.getInvProveedor().getPrvDesc();
            strPrvDir = filaSel.getInvProveedor().getPrvDireccion();
            strPrvTel1 = filaSel.getInvProveedor().getPrvTel1();
            strPrvTel2 = filaSel.getInvProveedor().getPrvTel2();
            strPrvEmail = filaSel.getInvProveedor().getPrvEmail();
            strPrvContacto = filaSel.getInvProveedor().getPrvContacto();
            strPrvTelContacto = filaSel.getInvProveedor().getPrvTelcontacto();
            blnPrvEstado = filaSel.getInvProveedor().getPrvEstado();
            blnEditProv = true;
        }
    }
    //</editor-fold>

    public InvCompJSFBean() {
    }

    private ControlBandEntradaSFBean lookupControlBandEntradaSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (ControlBandEntradaSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/ControlBandEntradaSFBean!com.pandora.adm.sys.ControlBandEntradaSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public void btnVolver_ActionEvent(ActionEvent ae) {
        regresarMant();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Referencias a otros Beans">

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">
    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">

    /**
     * @return the producto
     */
    public InvProducto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(InvProducto producto) {
        this.producto = producto;
    }

    /**
     * @return the lstTablaProducto
     */
    public List<TablaProducto> getLstTablaProducto() {
        return lstTablaProducto;
    }

    /**
     * @param lstTablaProducto the lstTablaProducto to set
     */
    public void setLstTablaProducto(List<TablaProducto> lstTablaProducto) {
        this.lstTablaProducto = lstTablaProducto;
    }

    /**
     * @return the lstCatProd
     */
    public List<InvCatprod> getLstCatProd() {
        return lstCatProd;
    }

    /**
     * @param lstCatProd the lstCatProd to set
     */
    public void setLstCatProd(List<InvCatprod> lstCatProd) {
        this.lstCatProd = lstCatProd;
    }

    /**
     * @return the blnNuevoProd
     */
    public boolean isBlnNuevoProd() {
        return blnNuevoProd;
    }

    /**
     * @param blnNuevoProd the blnNuevoProd to set
     */
    public void setBlnNuevoProd(boolean blnNuevoProd) {
        this.blnNuevoProd = blnNuevoProd;
    }

    /**
     * @return the invCategoriaSel
     */
    public InvCatprod getInvCategoriaSel() {
        return invCategoriaSel;
    }

    /**
     * @param invCategoriaSel the invCategoriaSel to set
     */
    public void setInvCategoriaSel(InvCatprod invCategoriaSel) {
        this.invCategoriaSel = invCategoriaSel;
    }

    /**
     * @return the intCpdId
     */
    public Integer getIntCpdId() {
        return intCpdId;
    }

    /**
     * @param intCpdId the intCpdId to set
     */
    public void setIntCpdId(Integer intCpdId) {
        this.intCpdId = intCpdId;
    }

    /**
     * @return the intProdId
     */
    public Integer getIntProdId() {
        return intProdId;
    }

    /**
     * @param intProdId the intProdId to set
     */
    public void setIntProdId(Integer intProdId) {
        this.intProdId = intProdId;
    }

    /**
     * @return the strProdNombre
     */
    public String getStrProdNombre() {
        return strProdNombre;
    }

    /**
     * @param strProdNombre the strProdNombre to set
     */
    public void setStrProdNombre(String strProdNombre) {
        this.strProdNombre = strProdNombre;
    }

    /**
     * @return the strProdDesc
     */
    public String getStrProdDesc() {
        return strProdDesc;
    }

    /**
     * @param strProdDesc the strProdDesc to set
     */
    public void setStrProdDesc(String strProdDesc) {
        this.strProdDesc = strProdDesc;
    }

    /**
     * @return the blnProdEstado
     */
    public boolean isBlnProdEstado() {
        return blnProdEstado;
    }

    /**
     * @param blnProdEstado the blnProdEstado to set
     */
    public void setBlnProdEstado(boolean blnProdEstado) {
        this.blnProdEstado = blnProdEstado;
    }

    /**
     * @return the blnProdEspecial
     */
    public boolean isBlnProdEspecial() {
        return blnProdEspecial;
    }

    /**
     * @param blnProdEspecial the blnProdEspecial to set
     */
    public void setBlnProdEspecial(boolean blnProdEspecial) {
        this.blnProdEspecial = blnProdEspecial;
    }

    /**
     * @return the blnProdOcacional
     */
    public boolean isBlnProdOcacional() {
        return blnProdOcacional;
    }

    /**
     * @param blnProdOcacional the blnProdOcacional to set
     */
    public void setBlnProdOcacional(boolean blnProdOcacional) {
        this.blnProdOcacional = blnProdOcacional;
    }

    /**
     * @return the blnProdUnico
     */
    public boolean isBlnProdUnico() {
        return blnProdUnico;
    }

    /**
     * @param blnProdUnico the blnProdUnico to set
     */
    public void setBlnProdUnico(boolean blnProdUnico) {
        this.blnProdUnico = blnProdUnico;
    }

    /**
     * @return the catprod
     */
    public InvCatprod getCatprod() {
        return catprod;
    }

    /**
     * @param catprod the catprod to set
     */
    public void setCatprod(InvCatprod catprod) {
        this.catprod = catprod;
    }

    /**
     * @return the lstTablaCatProducto
     */
    public List<TablaCategoriaProd> getLstTablaCatProducto() {
        return lstTablaCatProducto;
    }

    /**
     * @param lstTablaCatProducto the lstTablaCatProducto to set
     */
    public void setLstTablaCatProducto(List<TablaCategoriaProd> lstTablaCatProducto) {
        this.lstTablaCatProducto = lstTablaCatProducto;
    }

    /**
     * @return the intCatId
     */
    public Integer getIntCatId() {
        return intCatId;
    }

    /**
     * @param intCatId the intCatId to set
     */
    public void setIntCatId(Integer intCatId) {
        this.intCatId = intCatId;
    }

    /**
     * @return the strCatNombre
     */
    public String getStrCatNombre() {
        return strCatNombre;
    }

    /**
     * @param strCatNombre the strCatNombre to set
     */
    public void setStrCatNombre(String strCatNombre) {
        this.strCatNombre = strCatNombre;
    }

    /**
     * @return the strCatDesc
     */
    public String getStrCatDesc() {
        return strCatDesc;
    }

    /**
     * @param strCatDesc the strCatDesc to set
     */
    public void setStrCatDesc(String strCatDesc) {
        this.strCatDesc = strCatDesc;
    }

    /**
     * @return the blnCatEstado
     */
    public boolean isBlnCatEstado() {
        return blnCatEstado;
    }

    /**
     * @param blnCatEstado the blnCatEstado to set
     */
    public void setBlnCatEstado(boolean blnCatEstado) {
        this.blnCatEstado = blnCatEstado;
    }

    /**
     * @return the blnBloqIdProd
     */
    public boolean isBlnBloqIdProd() {
        return blnBloqIdProd;
    }

    /**
     * @param blnBloqIdProd the blnBloqIdProd to set
     */
    public void setBlnBloqIdProd(boolean blnBloqIdProd) {
        this.blnBloqIdProd = blnBloqIdProd;
    }

    /**
     * @return the blnBloqIdCatProd
     */
    public boolean isBlnBloqIdCatProd() {
        return blnBloqIdCatProd;
    }

    /**
     * @param blnBloqIdCatProd the blnBloqIdCatProd to set
     */
    public void setBlnBloqIdCatProd(boolean blnBloqIdCatProd) {
        this.blnBloqIdCatProd = blnBloqIdCatProd;
    }

    /**
     * @return the blnEditProd
     */
    public boolean isBlnEditProd() {
        return blnEditProd;
    }

    /**
     * @param blnEditProd the blnEditProd to set
     */
    public void setBlnEditProd(boolean blnEditProd) {
        this.blnEditProd = blnEditProd;
    }

    /**
     * @return the lstTablaPresentProd
     */
    public List<TablaPresentProd> getLstTablaPresentProd() {
        return lstTablaPresentProd;
    }

    /**
     * @param lstTablaPresentProd the lstTablaPresentProd to set
     */
    public void setLstTablaPresentProd(List<TablaPresentProd> lstTablaPresentProd) {
        this.lstTablaPresentProd = lstTablaPresentProd;
    }

    /**
     * @return the lstTablaMarca
     */
    public List<TablaMarca> getLstTablaMarca() {
        return lstTablaMarca;
    }

    /**
     * @param lstTablaMarca the lstTablaMarca to set
     */
    public void setLstTablaMarca(List<TablaMarca> lstTablaMarca) {
        this.lstTablaMarca = lstTablaMarca;
    }

    /**
     * @return the intPxpPrdId
     */
    public Integer getIntPxpPrdId() {
        return intPxpPrdId;
    }

    /**
     * @param intPxpPrdId the intPxpPrdId to set
     */
    public void setIntPxpPrdId(Integer intPxpPrdId) {
        this.intPxpPrdId = intPxpPrdId;
    }

    /**
     * @return the intPxpPspId
     */
    public Integer getIntPxpPspId() {
        return intPxpPspId;
    }

    /**
     * @param intPxpPspId the intPxpPspId to set
     */
    public void setIntPxpPspId(Integer intPxpPspId) {
        this.intPxpPspId = intPxpPspId;
    }

    /**
     * @return the blnPxpEst
     */
    public boolean isBlnPxpEst() {
        return blnPxpEst;
    }

    /**
     * @param blnPxpEst the blnPxpEst to set
     */
    public void setBlnPxpEst(boolean blnPxpEst) {
        this.blnPxpEst = blnPxpEst;
    }

    /**
     * @return the intMxpPrdId
     */
    public Integer getIntMxpPrdId() {
        return intMxpPrdId;
    }

    /**
     * @param intMxpPrdId the intMxpPrdId to set
     */
    public void setIntMxpPrdId(Integer intMxpPrdId) {
        this.intMxpPrdId = intMxpPrdId;
    }

    /**
     * @return the intMxpMarId
     */
    public Integer getIntMxpMarId() {
        return intMxpMarId;
    }

    /**
     * @param intMxpMarId the intMxpMarId to set
     */
    public void setIntMxpMarId(Integer intMxpMarId) {
        this.intMxpMarId = intMxpMarId;
    }

    /**
     * @return the blnMxpEst
     */
    public boolean isBlnMxpEst() {
        return blnMxpEst;
    }

    /**
     * @param blnMxpEst the blnMxpEst to set
     */
    public void setBlnMxpEst(boolean blnMxpEst) {
        this.blnMxpEst = blnMxpEst;
    }

    /**
     * @return the presntxprod
     */
    public InvPresntxprod getPresntxprod() {
        return presntxprod;
    }

    /**
     * @param presntxprod the presntxprod to set
     */
    public void setPresntxprod(InvPresntxprod presntxprod) {
        this.presntxprod = presntxprod;
    }

    /**
     * @return the marcxprod
     */
    public InvMarcxprod getMarcxprod() {
        return marcxprod;
    }

    /**
     * @param marcxprod the marcxprod to set
     */
    public void setMarcxprod(InvMarcxprod marcxprod) {
        this.marcxprod = marcxprod;
    }

    /**
     * @return the tablaMarcaXProd
     */
    public TablaMarcaXProd getTablaMarcaXProd() {
        return tablaMarcaXProd;
    }

    /**
     * @param tablaMarcaXProd the tablaMarcaXProd to set
     */
    public void setTablaMarcaXProd(TablaMarcaXProd tablaMarcaXProd) {
        this.tablaMarcaXProd = tablaMarcaXProd;
    }

    /**
     * @return the lstTablaMarcaXProd
     */
    public List<TablaMarcaXProd> getLstTablaMarcaXProd() {
        return lstTablaMarcaXProd;
    }

    /**
     * @param lstTablaMarcaXProd the lstTablaMarcaXProd to set
     */
    public void setLstTablaMarcaXProd(List<TablaMarcaXProd> lstTablaMarcaXProd) {
        this.lstTablaMarcaXProd = lstTablaMarcaXProd;
    }

    /**
     * @return the presentprod
     */
    public InvPresentprod getPresentprod() {
        return presentprod;
    }

    /**
     * @param presentprod the presentprod to set
     */
    public void setPresentprod(InvPresentprod presentprod) {
        this.presentprod = presentprod;
    }

    /**
     * @return the lstTablaPresentProds
     */
    public List<TablaPresentProd> getLstTablaPresentProds() {
        return lstTablaPresentProds;
    }

    /**
     * @param lstTablaPresentProds the lstTablaPresentProds to set
     */
    public void setLstTablaPresentProds(List<TablaPresentProd> lstTablaPresentProds) {
        this.lstTablaPresentProds = lstTablaPresentProds;
    }

    /**
     * @return the intPspId
     */
    public Integer getIntPspId() {
        return intPspId;
    }

    /**
     * @param intPspId the intPspId to set
     */
    public void setIntPspId(Integer intPspId) {
        this.intPspId = intPspId;
    }

    /**
     * @return the strPspNombre
     */
    public String getStrPspNombre() {
        return strPspNombre;
    }

    /**
     * @param strPspNombre the strPspNombre to set
     */
    public void setStrPspNombre(String strPspNombre) {
        this.strPspNombre = strPspNombre;
    }

    /**
     * @return the strPspDesc
     */
    public String getStrPspDesc() {
        return strPspDesc;
    }

    /**
     * @param strPspDesc the strPspDesc to set
     */
    public void setStrPspDesc(String strPspDesc) {
        this.strPspDesc = strPspDesc;
    }

    /**
     * @return the blnPspEstado
     */
    public boolean isBlnPspEstado() {
        return blnPspEstado;
    }

    /**
     * @param blnPspEstado the blnPspEstado to set
     */
    public void setBlnPspEstado(boolean blnPspEstado) {
        this.blnPspEstado = blnPspEstado;
    }

    /**
     * @return the blnBloqIdPsp
     */
    public boolean isBlnBloqIdPsp() {
        return blnBloqIdPsp;
    }

    /**
     * @param blnBloqIdPsp the blnBloqIdPsp to set
     */
    public void setBlnBloqIdPsp(boolean blnBloqIdPsp) {
        this.blnBloqIdPsp = blnBloqIdPsp;
    }

    /**
     * @return the marca
     */
    public InvMarca getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(InvMarca marca) {
        this.marca = marca;
    }

    /**
     * @return the lstTablaMarcas
     */
    public List<TablaMarca> getLstTablaMarcas() {
        return lstTablaMarcas;
    }

    /**
     * @param lstTablaMarcas the lstTablaMarcas to set
     */
    public void setLstTablaMarcas(List<TablaMarca> lstTablaMarcas) {
        this.lstTablaMarcas = lstTablaMarcas;
    }

    /**
     * @return the intMarId
     */
    public Integer getIntMarId() {
        return intMarId;
    }

    /**
     * @param intMarId the intMarId to set
     */
    public void setIntMarId(Integer intMarId) {
        this.intMarId = intMarId;
    }

    /**
     * @return the strMarNombre
     */
    public String getStrMarNombre() {
        return strMarNombre;
    }

    /**
     * @param strMarNombre the strMarNombre to set
     */
    public void setStrMarNombre(String strMarNombre) {
        this.strMarNombre = strMarNombre;
    }

    /**
     * @return the strMarDesc
     */
    public String getStrMarDesc() {
        return strMarDesc;
    }

    /**
     * @param strMarDesc the strMarDesc to set
     */
    public void setStrMarDesc(String strMarDesc) {
        this.strMarDesc = strMarDesc;
    }

    /**
     * @return the blnMarEstado
     */
    public boolean isBlnMarEstado() {
        return blnMarEstado;
    }

    /**
     * @param blnMarEstado the blnMarEstado to set
     */
    public void setBlnMarEstado(boolean blnMarEstado) {
        this.blnMarEstado = blnMarEstado;
    }

    /**
     * @return the blnBloqIdMar
     */
    public boolean isBlnBloqIdMar() {
        return blnBloqIdMar;
    }

    /**
     * @param blnBloqIdMar the blnBloqIdMar to set
     */
    public void setBlnBloqIdMar(boolean blnBloqIdMar) {
        this.blnBloqIdMar = blnBloqIdMar;
    }

    /**
     * @return the blnNuevaCatProd
     */
    public boolean isBlnNuevaCatProd() {
        return blnNuevaCatProd;
    }

    /**
     * @param blnNuevaCatProd the blnNuevaCatProd to set
     */
    public void setBlnNuevaCatProd(boolean blnNuevaCatProd) {
        this.blnNuevaCatProd = blnNuevaCatProd;
    }

    /**
     * @return the blnNuevaPresent
     */
    public boolean isBlnNuevaPresent() {
        return blnNuevaPresent;
    }

    /**
     * @param blnNuevaPresent the blnNuevaPresent to set
     */
    public void setBlnNuevaPresent(boolean blnNuevaPresent) {
        this.blnNuevaPresent = blnNuevaPresent;
    }

    /**
     * @return the blnNuevamarca
     */
    public boolean isBlnNuevamarca() {
        return blnNuevamarca;
    }

    /**
     * @param blnNuevamarca the blnNuevamarca to set
     */
    public void setBlnNuevamarca(boolean blnNuevamarca) {
        this.blnNuevamarca = blnNuevamarca;
    }

    /**
     * @return the blnEditCat
     */
    public boolean isBlnEditCat() {
        return blnEditCat;
    }

    /**
     * @param blnEditCat the blnEditCat to set
     */
    public void setBlnEditCat(boolean blnEditCat) {
        this.blnEditCat = blnEditCat;
    }

    /**
     * @return the blnEditPsp
     */
    public boolean isBlnEditPsp() {
        return blnEditPsp;
    }

    /**
     * @param blnEditPsp the blnEditPsp to set
     */
    public void setBlnEditPsp(boolean blnEditPsp) {
        this.blnEditPsp = blnEditPsp;
    }

    /**
     * @return the blnEditMarca
     */
    public boolean isBlnEditMarca() {
        return blnEditMarca;
    }

    /**
     * @param blnEditMarca the blnEditMarca to set
     */
    public void setBlnEditMarca(boolean blnEditMarca) {
        this.blnEditMarca = blnEditMarca;
    }

    /**
     * @return the proveedor
     */
    public InvProovedor getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(InvProovedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the lstTablaProveedor
     */
    public List<TablaProveedor> getLstTablaProveedor() {
        return lstTablaProveedor;
    }

    /**
     * @param lstTablaProveedor the lstTablaProveedor to set
     */
    public void setLstTablaProveedor(List<TablaProveedor> lstTablaProveedor) {
        this.lstTablaProveedor = lstTablaProveedor;
    }

    /**
     * @return the blnNuevoProv
     */
    public boolean isBlnNuevoProv() {
        return blnNuevoProv;
    }

    /**
     * @param blnNuevoProv the blnNuevoProv to set
     */
    public void setBlnNuevoProv(boolean blnNuevoProv) {
        this.blnNuevoProv = blnNuevoProv;
    }

    /**
     * @return the intFpgId
     */
    public Integer getIntFpgId() {
        return intFpgId;
    }

    /**
     * @param intFpgId the intFpgId to set
     */
    public void setIntFpgId(Integer intFpgId) {
        this.intFpgId = intFpgId;
    }

    /**
     * @return the intPrvId
     */
    public Integer getIntPrvId() {
        return intPrvId;
    }

    /**
     * @param intPrvId the intPrvId to set
     */
    public void setIntPrvId(Integer intPrvId) {
        this.intPrvId = intPrvId;
    }

    /**
     * @return the strPrvNit
     */
    public String getStrPrvNit() {
        return strPrvNit;
    }

    /**
     * @param strPrvNit the strPrvNit to set
     */
    public void setStrPrvNit(String strPrvNit) {
        this.strPrvNit = strPrvNit;
    }

    /**
     * @return the intPrvDigVer
     */
    public Integer getIntPrvDigVer() {
        return intPrvDigVer;
    }

    /**
     * @param intPrvDigVer the intPrvDigVer to set
     */
    public void setIntPrvDigVer(Integer intPrvDigVer) {
        this.intPrvDigVer = intPrvDigVer;
    }

    /**
     * @return the strPrvRazon
     */
    public String getStrPrvRazon() {
        return strPrvRazon;
    }

    /**
     * @param strPrvRazon the strPrvRazon to set
     */
    public void setStrPrvRazon(String strPrvRazon) {
        this.strPrvRazon = strPrvRazon;
    }

    /**
     * @return the strPrvDesc
     */
    public String getStrPrvDesc() {
        return strPrvDesc;
    }

    /**
     * @param strPrvDesc the strPrvDesc to set
     */
    public void setStrPrvDesc(String strPrvDesc) {
        this.strPrvDesc = strPrvDesc;
    }

    /**
     * @return the strPrvDir
     */
    public String getStrPrvDir() {
        return strPrvDir;
    }

    /**
     * @param strPrvDir the strPrvDir to set
     */
    public void setStrPrvDir(String strPrvDir) {
        this.strPrvDir = strPrvDir;
    }

    /**
     * @return the strPrvTel1
     */
    public String getStrPrvTel1() {
        return strPrvTel1;
    }

    /**
     * @param strPrvTel1 the strPrvTel1 to set
     */
    public void setStrPrvTel1(String strPrvTel1) {
        this.strPrvTel1 = strPrvTel1;
    }

    /**
     * @return the strPrvTel2
     */
    public String getStrPrvTel2() {
        return strPrvTel2;
    }

    /**
     * @param strPrvTel2 the strPrvTel2 to set
     */
    public void setStrPrvTel2(String strPrvTel2) {
        this.strPrvTel2 = strPrvTel2;
    }

    /**
     * @return the strPrvEmail
     */
    public String getStrPrvEmail() {
        return strPrvEmail;
    }

    /**
     * @param strPrvEmail the strPrvEmail to set
     */
    public void setStrPrvEmail(String strPrvEmail) {
        this.strPrvEmail = strPrvEmail;
    }

    /**
     * @return the blnBloqIdProv
     */
    public boolean isBlnBloqIdProv() {
        return blnBloqIdProv;
    }

    /**
     * @param blnBloqIdProv the blnBloqIdProv to set
     */
    public void setBlnBloqIdProv(boolean blnBloqIdProv) {
        this.blnBloqIdProv = blnBloqIdProv;
    }

    /**
     * @return the blnEditProv
     */
    public boolean isBlnEditProv() {
        return blnEditProv;
    }

    /**
     * @param blnEditProv the blnEditProv to set
     */
    public void setBlnEditProv(boolean blnEditProv) {
        this.blnEditProv = blnEditProv;
    }

    /**
     * @return the lstTablaFormaPago
     */
    public List<TablaFormaPago> getLstTablaFormaPago() {
        return lstTablaFormaPago;
    }

    /**
     * @param lstTablaFormaPago the lstTablaFormaPago to set
     */
    public void setLstTablaFormaPago(List<TablaFormaPago> lstTablaFormaPago) {
        this.lstTablaFormaPago = lstTablaFormaPago;
    }

    /**
     * @return the finFormaPagoSel
     */
    public FinFormapago getFinFormaPagoSel() {
        return finFormaPagoSel;
    }

    /**
     * @param finFormaPagoSel the finFormaPagoSel to set
     */
    public void setFinFormaPagoSel(FinFormapago finFormaPagoSel) {
        this.finFormaPagoSel = finFormaPagoSel;
    }

    /**
     * @return the blnPrvEstado
     */
    public boolean isBlnPrvEstado() {
        return blnPrvEstado;
    }

    /**
     * @param blnPrvEstado the blnPrvEstado to set
     */
    public void setBlnPrvEstado(boolean blnPrvEstado) {
        this.blnPrvEstado = blnPrvEstado;
    }

    /**
     * @return the strPrvContacto
     */
    public String getStrPrvContacto() {
        return strPrvContacto;
    }

    /**
     * @param strPrvContacto the strPrvContacto to set
     */
    public void setStrPrvContacto(String strPrvContacto) {
        this.strPrvContacto = strPrvContacto;
    }

    /**
     * @return the strPrvTelContacto
     */
    public String getStrPrvTelContacto() {
        return strPrvTelContacto;
    }

    /**
     * @param strPrvTelContacto the strPrvTelContacto to set
     */
    public void setStrPrvTelContacto(String strPrvTelContacto) {
        this.strPrvTelContacto = strPrvTelContacto;
    }

    /**
     * @return the lstFormaPago
     */
    public List<SelectItem> getLstFormaPago() {
        return lstFormaPago;
    }

    /**
     * @param lstFormaPago the lstFormaPago to set
     */
    public void setLstFormaPago(List<SelectItem> lstFormaPago) {
        this.lstFormaPago = lstFormaPago;
    }

    /**
     * @return the idFormaPago
     */
    public Integer getIdFormaPago() {
        return idFormaPago;
    }

    /**
     * @param idFormaPago the idFormaPago to set
     */
    public void setIdFormaPago(Integer idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    /**
     * @return the idCategoria
     */
    public Integer getIdCategoria() {
        return idCategoria;
    }

    /**
     * @param idCategoria the idCategoria to set
     */
    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * @return the lstCategoria
     */
    public List<SelectItem> getLstCategoria() {
        return lstCategoria;
    }

    /**
     * @param lstCategoria the lstCategoria to set
     */
    public void setLstCategoria(List<SelectItem> lstCategoria) {
        this.lstCategoria = lstCategoria;
    }
    //</editor-fold>
}
