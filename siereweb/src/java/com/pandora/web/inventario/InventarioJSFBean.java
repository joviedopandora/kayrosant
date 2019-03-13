/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.inventario;

import com.pandora.adm.dao.InvCatprod;
import com.pandora.adm.dao.InvInvent;
import com.pandora.adm.dao.InvMarca;
import com.pandora.adm.dao.InvMarcxprod;
import com.pandora.adm.dao.InvPresentprod;
import com.pandora.adm.dao.InvPresntxprod;
import com.pandora.adm.dao.InvProducto;
import com.pandora.adm.dao.InvTipotransc;
import com.pandora.adm.dao.InvTransac;
import com.pandora.mod.inventario.InventarioSFBean;
import com.pandora.mod.inventario.ConsultaInventarioDTO;
import com.pandora.mod.venta.dao.VntServicio;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.venta.TablaVntFactura;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.component.chart.AxisType;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.chart.DragConstraintAxis;

/**
 *
 * @author Javier
 */
@Named(value = "inventarioJSFBean")
@SessionScoped
public class InventarioJSFBean extends BaseJSFBean implements Serializable, IPasos {

    @EJB
    private InventarioSFBean inventarioSFBean;
    private List<InvInvent> lstInventario = new ArrayList<>();
    private InvProducto producto = new InvProducto();
    private List<InvProducto> lstInvProducto = new ArrayList<>();
    private List<SelectItem> lstInvCatprod = new ArrayList<>();
    private Integer catProducto = -1;
    private List<SelectItem> lstInvestado = new ArrayList<>();
    private boolean mostrarPopUp = false;
    private String codigoProducto = null;
    private Integer marca = -1;
    private boolean estadoMarcaXprod = true;
    private List<SelectItem> lstMarcasActivas = new ArrayList<>();
    private List<SelectItem> lstPresentacionesActivas = new ArrayList<>();
    private InvMarcxprod marcaxproducto = new InvMarcxprod();
    private List<SelectItem> lstTipoTransaccion = new ArrayList<>();
    private InvPresntxprod presntxprod = new InvPresntxprod();
    private Integer presentacion = -1;
    private List<SelectItem> lstMarcasActivasXProducto = new ArrayList<>();
    private List<SelectItem> lstPresentacionesActivasXProducto = new ArrayList<>();
    private boolean blnPrdEst;

    public InvMarcxprod getMarcaxproducto() {
        return marcaxproducto;
    }

    public void setMarcaxproducto(InvMarcxprod marcaxproducto) {
        this.marcaxproducto = marcaxproducto;
    }
    private List<InvMarcxprod> lstInvMarcxprods = new ArrayList<>();
    private List<InvPresntxprod> lstInvPresntxprods = new ArrayList<>();
    //variables para moviemtos del inventario
    private String codigoConsulta = null;
    private String nombreConsulta = null;
    private String descConsulta = null;
    private InvInvent inventenatio = new InvInvent();
    private Integer cantidadMovimiento = 1;
    private String kardes = null;
    private Integer tipoTransac = -1;
    private Integer idProducto = null;
    private boolean verPanelEditarInvent = false;

    private InventarioSFBean lookupInventarioSFBean() {
        try {
            Context c = new InitialContext();
            return (InventarioSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/InventarioSFBean!com.pandora.mod.inventario.InventarioSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * Creates a new instance of InventarioJSFBean
     */
    public InventarioJSFBean() {
    }

    public boolean grabarPaso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean validarForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the lstInventario
     */
    public List<InvInvent> getLstInventario() {
        return lstInventario;
    }

    /**
     * @param lstInventario the lstTablaColaboradores to set
     */
    public void setLstInventario(List<InvInvent> lstInventario) {
        this.lstInventario = lstInventario;
    }

    private void cargarInventario() {
        lstInventario.clear();

        for (InvInvent p : inventarioSFBean.getInventarios()) {

            lstInventario.add(p);
        }
    }

    private void cargarProductos() {
        lstInvProducto.clear();
        for (InvProducto p : inventarioSFBean.getLstInvProducto()) {
            lstInvProducto.add(p);
        }
    }

     private void cargarProductosxEst() {
        lstInvProducto.clear();
        for (InvProducto p : inventarioSFBean.getLstInvProductoxEst(blnPrdEst)) {
            lstInvProducto.add(p);
        }
    }

    
    @Override
    public void init() {
        inventarioSFBean = lookupInventarioSFBean();
        LstVntFacturaXcountfecha();
        lstInvProducto.clear();
        cargarInventario();
        numPanel = 1;
        producto = new InvProducto();
        codigoProducto = null;
        codigoConsulta = null;
        nombreConsulta = null;
        descConsulta = null;
        inventenatio = new InvInvent();
        cargarProductos();
        cantidadMovimiento = 1;
        tipoTransac = -1;
        kardes = null;
        catProducto = -1;
        mostrarPopUp = false;
        lstInvestado.add(new SelectItem(-1, "seleccione>>"));
        lstInvCatprod.add(new SelectItem(-1, "Seleccione>>"));
        for (InvCatprod c : inventarioSFBean.getLstInvCatprod(true)) {
            lstInvCatprod.add(new SelectItem(c.getCpdId(), c.getCpdNombre(), c.getCpdDesc()));
        }
        cargarMarcas();
        cargarPresentaciones();
        cargarTipoTransaccion(false);
        presntxprod = new InvPresntxprod();
        lstMarcasActivasXProducto.clear();
        lstPresentacionesActivasXProducto.clear();

    }

    @Override
    public void limpiarVariables() {
        lstInventario.clear();
        lstInvProducto.clear();
        producto = null;
        mostrarPopUp = false;
        marcaxproducto = new InvMarcxprod();
        inventenatio = new InvInvent();
        lstTipoTransaccion.clear();
        lstInvMarcxprods.clear();
        lstInvPresntxprods.clear();
        presntxprod = new InvPresntxprod();
    }

    private void cargarDatosXProducto(Integer idProducto) {
        marca = -1;
        presentacion = -1;
        tipoTransac = -1;
        lstMarcasActivasXProducto.clear();
        lstPresentacionesActivasXProducto.clear();
        lstMarcasActivasXProducto.add(itemSeleccioneInt);
        lstPresentacionesActivasXProducto.add(itemSeleccioneInt);
        for (InvMarcxprod mxp : inventarioSFBean.getLstInvMarcxprodXProducto(idProducto)) {
            if (mxp.getMxpEst()) {
                lstMarcasActivasXProducto.add(new SelectItem(mxp.getMarId().getMarId(), mxp.getMarId().getMarNombre()));
            }
        }
        for (InvPresntxprod pxp : inventarioSFBean.getLstInvPresntxprodXProducto(idProducto)) {
            if (pxp.getPxpEst()) {
                lstPresentacionesActivasXProducto.add(new SelectItem(pxp.getPspId().getPspId(), pxp.getPspId().getPspNombre()));
            }
        }

    }

    public void rowDtProducto_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();

        producto = (InvProducto) map.get("producto");

        if (numPanel == 1) {
            this.catProducto = (producto.getCpdId() == null ? -1 : producto.getCpdId().getCpdId());
            cargarProductoXMarca();
            cargarProductoXPresentacion();
        } else if (numPanel == 3) {
            verPanelEditarInvent = true;
            inventenatio = new InvInvent();
            inventenatio.setPrdId(producto);
            cargarDatosXProducto(producto.getPrdId());
            cargarTipoTransaccion(true);
        }

    }

    public void rowDtInventario_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        verPanelEditarInvent = true;
        inventenatio = (InvInvent) map.get("invent");
        cargarDatosXProducto(inventenatio.getPrdId().getPrdId());
        cargarTipoTransaccion(true);

    }

    public void rowDtMarcaXProducto_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        marcaxproducto = (InvMarcxprod) map.get("mxprd");
        //  inventenatio = new InvInvent();

        // codigoProducto = marcaxproducto.getMxpCodigo();
        marca = (marcaxproducto.getMarId() == null ? -1 : marcaxproducto.getMarId().getMarId());
        estadoMarcaXprod = marcaxproducto.getMxpEst();

    }

    private void cargarTipoTransaccion(boolean cargar) {
        lstTipoTransaccion.clear();
        lstTipoTransaccion.add(itemSeleccioneInt);
        if (cargar) {
            if (inventenatio.getInvId() == null || inventenatio.getInvCant() == 0) {
                for (InvTipotransc t : inventarioSFBean.getLstInvTipotransc(true)) {
                    if (!t.isTtrAplicalogistica() && t.isTtrEntradainicial()) {
                        lstTipoTransaccion.add(new SelectItem(t.getTtrId(), t.getTtrNombre(),
                                (t.getTtrDesc() == null ? t.getTtrNombre() : t.getTtrDesc())));
                    }
                }
            } else {
                for (InvTipotransc t : inventarioSFBean.getLstInvTipotransc(true)) {
                    if (!t.isTtrAplicalogistica()) {
                        lstTipoTransaccion.add(new SelectItem(t.getTtrId(), t.getTtrNombre(),
                                (t.getTtrDesc() == null ? t.getTtrNombre() : t.getTtrDesc())));
                    }
                }
            }
        }
    }

    public void mostarPopUpMarcas_ActionEvent(ActionEvent ae) {
        mostrarPopUp = true;

        //cargarMarcas();
        limpiarAsociarMarcas();
        // lstInvMarcxprods.clear();
        if (producto == null) {
            producto = new InvProducto();
        }

    }

    public void crearMarcaXProducto_ActionEvent(ActionEvent ae) {

        limpiarAsociarMarcas();
    }

    public void asociarMarcaXProducto_ActionEvent(ActionEvent ae) {
        boolean validador = false;
        if (marca == -1) {
            mostrarError("Marca es requerida", 1);
            validador = true;
        }

        /* if (codigoProducto == null) {
         mostrarError("Código de barras es requerido", 1);
         validador = true;
         } else {
         codigoProducto = codigoProducto.trim();
         if (codigoProducto.equals("")) {
         mostrarError("Código de barras es requerido", 1);
         validador = true;
         }
         }*/
        if (validador) {
            return;
        }

        LinkedHashMap<Integer, InvMarcxprod> lst = new LinkedHashMap<>();
        for (InvMarcxprod m : lstInvMarcxprods) {
            lst.put(m.getMarId().getMarId(), m);
        }
        /*  for (Map.Entry<Integer, InvMarcxprod> e : lst.entrySet()) {
         if (marcaxproducto.getMarId() != null && marcaxproducto.getMarId().equals(e.getValue().getMarId())) {
         continue;
         }

         if (e.getValue().getMxpCodigo().equals(codigoProducto)) {
         mostrarError("Código de barras que intenta asociar ya se encuentra en la base de datos", 1);
         validador = true;
         }


         }*/
        if (validador) {
            return;
        }
        /*  List<InvMarcxprod> ltins = inventarioSFBean.getLstInvMarcxprodXProductoXCodigo(marcaxproducto.getMxpId(), codigoProducto);
         if (ltins != null && !ltins.isEmpty()) {
         mostrarError("Código de barras que intenta asociar ya se encuentra en la base de datos", 1);
         return;
         }*/
        InvMarcxprod mxp = lst.get(marca);
        if (mxp != null) {
            marcaxproducto = mxp;
        }

        //  marcaxproducto.setMxpCodigo(codigoProducto);
        marcaxproducto.setMxpEst(estadoMarcaXprod);
        marcaxproducto.setMarId(inventarioSFBean.getInvMarcaXId(marca));
        lstInvMarcxprods.clear();
        lstInvMarcxprods.add(marcaxproducto);
        for (Map.Entry<Integer, InvMarcxprod> e : lst.entrySet()) {
            if (marcaxproducto.getMarId().equals(e.getValue().getMarId())) {
                continue;
            }
            lstInvMarcxprods.add(e.getValue());
        }
        limpiarAsociarMarcas();

    }

    public void mostarPopUpPresentacion_ActionEvent(ActionEvent ae) {
        limpiarAsociarPresentacion();
        if (producto == null) {
            producto = new InvProducto();
        }
    }

    public void crearPresentacionXProducto_ActionEvent(ActionEvent ae) {
        limpiarAsociarPresentacion();
    }

    public void asociarPresentacionXProducto_ActionEvent(ActionEvent ae) {

        if (presentacion == -1) {
            mostrarError("Presentación es requerida", 1);
            return;
        }

        LinkedHashMap<Integer, InvPresntxprod> lst = new LinkedHashMap<>();
        for (InvPresntxprod m : lstInvPresntxprods) {
            lst.put(m.getPspId().getPspId(), m);
        }


        /*  List<InvMarcxprod> ltins = inventarioSFBean.getLstInvMarcxprodXProductoXCodigo(marcaxproducto.getMxpId(), codigoProducto);
         if (ltins != null && !ltins.isEmpty()) {
         mostrarError("Código de barras que intenta asociar ya se encuentra en la base de datos", 1);
         return;
         }*/
        InvPresntxprod mxp = lst.get(presentacion);
        if (mxp != null) {
            presntxprod = mxp;
        }

        //  marcaxproducto.setMxpCodigo(codigoProducto);
        presntxprod.setPspId(inventarioSFBean.getInvPresentprodaXId(presentacion));
        lstInvPresntxprods.clear();
        lstInvPresntxprods.add(presntxprod);
        for (Map.Entry<Integer, InvPresntxprod> e : lst.entrySet()) {
            if (presntxprod.getPspId().equals(e.getValue().getPspId())) {
                continue;
            }
            lstInvPresntxprods.add(e.getValue());
        }
        limpiarAsociarMarcas();

    }

    private void limpiarAsociarMarcas() {
        marca = -1;
        estadoMarcaXprod = true;
        codigoProducto = null;
        // cargarMarcas();
        codigoConsulta = null;
        nombreConsulta = null;
        descConsulta = null;
        marcaxproducto = new InvMarcxprod();
        inventenatio = new InvInvent();
        cargarTipoTransaccion(false);
        cantidadMovimiento = 1;
        tipoTransac = -1;
        kardes = null;
    }

    private void limpiarAsociarPresentacion() {
        presentacion = -1;
        presntxprod = new InvPresntxprod();
    }

    private void cargarMarcas() {
        lstMarcasActivas.clear();
        lstMarcasActivas.add(itemSeleccioneInt);
        for (InvMarca m : inventarioSFBean.getLstInvMarcaXEstado(true)) {
            lstMarcasActivas.add(new SelectItem(m.getMarId(), m.getMarNombre(), (m.getMarDesc() == null ? m.getMarNombre() : m.getMarDesc())));
        }
    }

    private void cargarPresentaciones() {
        lstPresentacionesActivas.clear();
        lstPresentacionesActivas.add(itemSeleccioneInt);
        for (InvPresentprod m : inventarioSFBean.getLstInvPresentprodXEstado(true)) {
            lstPresentacionesActivas.add(new SelectItem(m.getPspId(), m.getPspNombre(), (m.getPspDesc() == null ? m.getPspNombre() : m.getPspDesc())));
        }
    }

    public void guardarProducto_ActionEvent(ActionEvent ae) {
        // validaciones
        try {
            boolean valida = false;
            if (producto == null) {
                producto = new InvProducto();
            }
            if (producto.getPrdNombre() == null) {
                mostrarError("Nombre Producto es requerido", 1);
                valida = true;
            } else {
                producto.setPrdNombre(producto.getPrdNombre().trim());
                if (producto.getPrdNombre().equals("")) {
                    mostrarError("Nombre Producto es requerido", 1);
                    valida = true;
                }

            }
            /*   if (producto.getPrdCodigo() == null) {
             mostrarError("Código Producto es requerido", 1);
             valida = true;
             } else {
             producto.setPrdCodigo(producto.getPrdCodigo().trim());
             if (producto.getPrdCodigo().equals("")) {
             mostrarError("Código Producto es requerido", 1);
             valida = true;
             }

             }*/
            if (catProducto == null || catProducto == -1) {
                mostrarError("Categoría de Producto es requerido", 1);
                valida = true;
            }
            if (lstInvMarcxprods.isEmpty()) {
                mostrarError("Debe asociar mínimo una marca", 1);
                valida = true;
            }
            if (lstInvPresntxprods.isEmpty()) {
                mostrarError("Debe asociar mínimo una presentación", 1);
                valida = true;
            }
            if (valida) {
                return;
            }
            // primero seteo de variables
            producto.setCpdId(inventarioSFBean.getInvCatprodXId(catProducto));
            producto = inventarioSFBean.editarProducto(producto);
            for (InvMarcxprod s : lstInvMarcxprods) {
                s.setIndversion(s.getIndversion() + 1);
                s.setPrdId(producto);
            }
            for (InvPresntxprod s : lstInvPresntxprods) {
                s.setPrdId(producto);
            }
            inventarioSFBean.editarLstInvMarcxprod(lstInvMarcxprods);
            inventarioSFBean.editarLstInvPresntxprod(lstInvPresntxprods);
            cargarProductoXMarca();
            cargarProductoXPresentacion();
            mostrarError("Producto guardado exitosamente", 3);
        } catch (EJBException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void crearProducto_ActionEvent(ActionEvent ae) {
        producto = new InvProducto();
        lstInvMarcxprods.clear();
        limpiarAsociarMarcas();

        lstInvPresntxprods.clear();
        limpiarAsociarPresentacion();
        //metodo para limpiar variables de estado
    }

    public void guardarTransaccionInventario_ActionEvent(ActionEvent ae) {
        // validaciones
        try {
            boolean valida = false;
            if (inventenatio == null) {
                inventenatio = new InvInvent();
            }
            if (inventenatio.getInvId() == null) {
                if (marca == -1) {
                    mostrarError("Debe Seleccionar Marca a realizar movimiento", 1);
                    valida = true;
                }
                if (presentacion == -1) {
                    mostrarError("Debe Seleccionar Presentación a realizar movimiento", 1);
                    valida = true;
                }
            }

            if (inventenatio.getInvCodigobarras() == null) {
                mostrarError("Código de barras es requerido", 1);
                valida = true;
            } else {
                inventenatio.setInvCodigobarras(inventenatio.getInvCodigobarras().trim());
                if (inventenatio.getInvCodigobarras().equals("")) {
                    mostrarError("Código de barras es requerido", 1);
                    valida = true;
                } else {
                    //TODO: validar codigos de barras con respecto a la  BD.
                }
            }
            /*  if (inventenatio.getMarId() == null) {
             mostrarError("Debe Seleccionar el producto marca a realizar movimiento", 1);
             valida = true;
             }*/

            if (tipoTransac == null || tipoTransac == -1) {
                mostrarError("Tipo Transaccion es requerido", 1);
                valida = true;
            }
            if (cantidadMovimiento == null || cantidadMovimiento <= 0) {
                mostrarError("Cantidad de Moviemiento no deber ser nula o menor igual a cero", 1);
                valida = true;
            }
            if (valida) {
                return;
            }
            if (inventenatio.getInvId() == null) {

                for (InvInvent i : inventarioSFBean.getLstInvInventXProductoYMarcaYPresentacion(inventenatio.getPrdId().getPrdId(), marca, presentacion)) {
                    mostrarError("Inventario con esos parametros ya se encuentra en la Base de Datos, validar la información y volver a presionar Guardar ", numPanel);
                    inventenatio = i;
                    valida = true;
                    break;
                }
                inventenatio.setMarId(inventarioSFBean.getInvMarcaXId(marca));
                inventenatio.setPspId(inventarioSFBean.getInvPresentprodaXId(presentacion));

            }
            if (valida) {
                return;
            }

            InvTipotransc tipotransc = inventarioSFBean.getInvTipotranscXId(tipoTransac);
            Integer cantidadMover = inventenatio.getInvCant() + (cantidadMovimiento * tipotransc.getTtrMultiplica());

            if (cantidadMover < 0) {
                mostrarError("La cantidad a retirar del inventario no puede ser mayor a la cantidad actual", 1);
                return;

            }
            // primero seteo de variables
            if (inventenatio.getIndversion() == null) {
                inventenatio.setIndversion(0);
            }
            if (inventenatio.getInvPrecio() == null) {
                inventenatio.setInvPrecio(BigDecimal.ZERO);
            }
            if (inventenatio.getInvFechaingreso() == null) {
                inventenatio.setInvFechaingreso(new Date());
            }

            inventenatio.setInvCant(cantidadMover);

            inventenatio = inventarioSFBean.editarInvInvent(inventenatio);
            InvTransac transaccion = new InvTransac();
            transaccion.setCxcId(getPrincipalJSFBean().getAdmCrgxcolActivo());
            transaccion.setIndversion(0);
            transaccion.setInvId(inventenatio);
            transaccion.setItrAnultr(false);
            transaccion.setItrCant(cantidadMovimiento);
            transaccion.setItrEstado(true);
            transaccion.setItrFecpro(new Date());
            transaccion.setItrIdtranul(BigInteger.ZERO);
            transaccion.setItrKardex(kardes);
            transaccion.setItrObsr(tipotransc.getTtrNombre() + " :" + tipotransc.getTtrDesc());
            transaccion.setItrValor(BigDecimal.ZERO);
            transaccion.setTtrId(tipotransc);
            transaccion = inventarioSFBean.editarInvTransac(transaccion);
            limpiarAsociarMarcas();
            lstInvMarcxprods.clear();
            limpiarAsociarPresentacion();
            lstInvPresntxprods.clear();
            mostrarError("Movimiento guardado exitosamente", 3);
            verPanelEditarInvent = false;
            inventenatio = new InvInvent();
            limpiarAsociarMarcas();
            limpiarAsociarPresentacion();
            lstInventario.clear();
            lstInvProducto.clear();
        } catch (EJBException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        consultar();

        /*  lstInvMarcxprods.clear();
         marcaxproducto = new InvMarcxprod();
         inventenatio = new InvInvent();
         if (codigoConsulta != null) {
         codigoConsulta = codigoConsulta.trim();
         if (codigoConsulta.isEmpty() || codigoConsulta.equals("")) {
         codigoConsulta = null;
         }
         }
         if (nombreConsulta != null) {
         nombreConsulta = nombreConsulta.trim();
         if (nombreConsulta.isEmpty() || nombreConsulta.equals("")) {
         nombreConsulta = null;
         }
         }

         if (descConsulta != null) {
         descConsulta = descConsulta.trim();
         if (descConsulta.isEmpty() || descConsulta.equals("")) {
         descConsulta = null;
         }
         }
         for (InvMarcxprod m : inventarioSFBean.getLstInvMarcxprodXProductoXParametros(codigoConsulta, nombreConsulta, descConsulta)) {
         lstInvMarcxprods.add(m);
         }
         cargarTipoTransaccion(false);
         if (lstInvMarcxprods.isEmpty()) {
         mostrarError("No productos asociados con las marcar que coincidan con los criterios de busqueda.", 1);

         //  JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "dialogoBuscar.hide();");
         } else {
         if (lstInvMarcxprods.size() == 1) {
         marcaxproducto = lstInvMarcxprods.get(0);
         inventenatio = getInvInventXMarcaProducto(marcaxproducto);
         lstInvMarcxprods.clear();
         cargarTipoTransaccion(false);
         cantidadMovimiento = 1;
         tipoTransac = -1;
         kardes = null;
         //  JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "dialogoBuscar.hide();");
         } else {
         JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "dialogoBuscar.show();");
         }
         }*/
    }

    public void limpiarConsulta_ActionEvent(ActionEvent ae) {
        lstInventario.clear();
        lstInvProducto.clear();
        idProducto = null;
        codigoConsulta = null;
        nombreConsulta = null;
        marca = -1;
        presentacion = -1;

    }

    private void consultar() {
        ConsultaInventarioDTO tci = new ConsultaInventarioDTO();
        lstInventario.clear();
        lstInvProducto.clear();
        tci.setIdProducto(idProducto);
        tci.setCodigoBarras((codigoConsulta == null ? codigoConsulta : codigoConsulta.trim()));
        tci.setNombreProducto((nombreConsulta == null ? nombreConsulta : nombreConsulta.trim()));
        tci.setMarNombre(marca);
        tci.setPspNombre(presentacion);
        for (InvInvent i : inventarioSFBean.getLstInvInventXParametros(tci)) {
            lstInventario.add(i);
        }
        if (numPanel == 3) {
            for (InvProducto i : inventarioSFBean.getLstInvProductoXParametros(tci)) {
                lstInvProducto.add(i);
            }
        }

    }

    private void cargarProductoXMarca() {
        lstInvMarcxprods.clear();
        if (producto.getPrdId() != null) {
            for (InvMarcxprod m : inventarioSFBean.getLstInvMarcxprodXProducto(producto.getPrdId())) {
                lstInvMarcxprods.add(m);
            }
        }
    }

    private void cargarProductoXPresentacion() {
        lstInvPresntxprods.clear();
        if (producto.getPrdId() != null) {
            for (InvPresntxprod m : inventarioSFBean.getLstInvPresntxprodXProducto(producto.getPrdId())) {
                lstInvPresntxprods.add(m);
            }
        }
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        limpiarAsociarMarcas();
        lstInvMarcxprods.clear();
        lstInvPresntxprods.clear();
        limpiarAsociarPresentacion();
        idProducto = null;
        producto = new InvProducto();
        inventenatio = new InvInvent();
        lstInventario.clear();
        lstInvProducto.clear();
        switch (numPanel) {
            case 1:
                //   numPanel = 1;
                cargarProductos();
                producto = new InvProducto();
                mostrarPopUp = false;
                catProducto = -1;

                //cargarInventario();
                break;
            case 2:
                break;
            case 3:

                verPanelEditarInvent = false;
                break;

        }
    }

    public void regresar_ActionEvent(ActionEvent ae) {
        verPanelEditarInvent = false;

    }

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
     * @return the lstInvProducto
     */
    public List<InvProducto> getLstInvProducto() {
        return lstInvProducto;
    }

    /**
     * @param lstInvProducto the lstInvProducto to set
     */
    public void setLstInvProducto(List<InvProducto> lstInvProducto) {
        this.lstInvProducto = lstInvProducto;
    }

    /**
     * @return the lstInvCatprod
     */
    public List<SelectItem> getLstInvCatprod() {
        return lstInvCatprod;
    }

    /**
     * @param lstInvCatprod the lstInvCatprod to set
     */
    public void setLstInvCatprod(List<SelectItem> lstInvCatprod) {
        this.lstInvCatprod = lstInvCatprod;
    }

    /**
     * @return the catProducto
     */
    public Integer getCatProducto() {
        return catProducto;
    }

    /**
     * @param catProducto the catProducto to set
     */
    public void setCatProducto(Integer catProducto) {
        this.catProducto = catProducto;
    }

    /**
     * @return the lstInvestado
     */
    public List<SelectItem> getLstInvestado() {
        return lstInvestado;
    }

    /**
     * @param lstInvestado the lstInvestado to set
     */
    public void setLstInvestado(List<SelectItem> lstInvestado) {
        this.lstInvestado = lstInvestado;
    }

    /**
     * @return the codigoProducto
     */
    public String getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * @param codigoProducto the codigoProducto to set
     */
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    /**
     * @return the mostrarPopUp
     */
    public boolean isMostrarPopUp() {
        return mostrarPopUp;
    }

    /**
     * @param mostrarPopUp the mostrarPopUp to set
     */
    public void setMostrarPopUp(boolean mostrarPopUp) {
        this.mostrarPopUp = mostrarPopUp;
    }

    /**
     * @return the marca
     */
    public Integer getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(Integer marca) {
        this.marca = marca;
    }

    /**
     * @return the estadoMarcaXprod
     */
    public boolean isEstadoMarcaXprod() {
        return estadoMarcaXprod;
    }

    /**
     * @param estadoMarcaXprod the estadoMarcaXprod to set
     */
    public void setEstadoMarcaXprod(boolean estadoMarcaXprod) {
        this.estadoMarcaXprod = estadoMarcaXprod;
    }

    /**
     * @return the lstMarcasActivas
     */
    public List<SelectItem> getLstMarcasActivas() {
        return lstMarcasActivas;
    }

    /**
     * @param lstMarcasActivas the lstMarcasActivas to set
     */
    public void setLstMarcasActivas(List<SelectItem> lstMarcasActivas) {
        this.lstMarcasActivas = lstMarcasActivas;
    }

    /**
     * @return the lstInvMarcxprods
     */
    public List<InvMarcxprod> getLstInvMarcxprods() {
        return lstInvMarcxprods;
    }

    /**
     * @param lstInvMarcxprods the lstInvMarcxprods to set
     */
    public void setLstInvMarcxprods(List<InvMarcxprod> lstInvMarcxprods) {
        this.lstInvMarcxprods = lstInvMarcxprods;
    }

    /**
     * @return the codigoConsulta
     */
    public String getCodigoConsulta() {
        return codigoConsulta;
    }

    /**
     * @param codigoConsulta the codigoConsulta to set
     */
    public void setCodigoConsulta(String codigoConsulta) {
        this.codigoConsulta = codigoConsulta;
    }

    /**
     * @return the nombreConsulta
     */
    public String getNombreConsulta() {
        return nombreConsulta;
    }

    /**
     * @param nombreConsulta the nombreConsulta to set
     */
    public void setNombreConsulta(String nombreConsulta) {
        this.nombreConsulta = nombreConsulta;
    }

    /**
     * @return the descConsulta
     */
    public String getDescConsulta() {
        return descConsulta;
    }

    /**
     * @param descConsulta the descConsulta to set
     */
    public void setDescConsulta(String descConsulta) {
        this.descConsulta = descConsulta;
    }

    /**
     * @return the inventenatio
     */
    public InvInvent getInventenatio() {
        return inventenatio;
    }

    /**
     * @param inventenatio the inventenatio to set
     */
    public void setInventenatio(InvInvent inventenatio) {
        this.inventenatio = inventenatio;
    }

    /**
     * @return the lstTipoTransaccion
     */
    public List<SelectItem> getLstTipoTransaccion() {
        return lstTipoTransaccion;
    }

    /**
     * @param lstTipoTransaccion the lstTipoTransaccion to set
     */
    public void setLstTipoTransaccion(List<SelectItem> lstTipoTransaccion) {
        this.lstTipoTransaccion = lstTipoTransaccion;
    }

    /**
     * @return the cantidadMovimiento
     */
    public Integer getCantidadMovimiento() {
        return cantidadMovimiento;
    }

    /**
     * @param cantidadMovimiento the cantidadMovimiento to set
     */
    public void setCantidadMovimiento(Integer cantidadMovimiento) {
        this.cantidadMovimiento = cantidadMovimiento;
    }

    /**
     * @return the kardes
     */
    public String getKardes() {
        return kardes;
    }

    /**
     * @param kardes the kardes to set
     */
    public void setKardes(String kardes) {
        this.kardes = kardes;
    }

    /**
     * @return the tipoTransac
     */
    public Integer getTipoTransac() {
        return tipoTransac;
    }

    /**
     * @param tipoTransac the tipoTransac to set
     */
    public void setTipoTransac(Integer tipoTransac) {
        this.tipoTransac = tipoTransac;
    }

    /**
     * @return the lstInvPresntxprods
     */
    public List<InvPresntxprod> getLstInvPresntxprods() {
        return lstInvPresntxprods;
    }

    /**
     * @param lstInvPresntxprods the lstInvPresntxprods to set
     */
    public void setLstInvPresntxprods(List<InvPresntxprod> lstInvPresntxprods) {
        this.lstInvPresntxprods = lstInvPresntxprods;
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
     * @return the lstPresentacionesActivas
     */
    public List<SelectItem> getLstPresentacionesActivas() {
        return lstPresentacionesActivas;
    }

    /**
     * @param lstPresentacionesActivas the lstPresentacionesActivas to set
     */
    public void setLstPresentacionesActivas(List<SelectItem> lstPresentacionesActivas) {
        this.lstPresentacionesActivas = lstPresentacionesActivas;
    }

    /**
     * @return the presentacion
     */
    public Integer getPresentacion() {
        return presentacion;
    }

    /**
     * @param presentacion the presentacion to set
     */
    public void setPresentacion(Integer presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * @return the idProducto
     */
    public Integer getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }
    
    
  

    /**
     * @return the verPanelEditarInvent
     */
    public boolean isVerPanelEditarInvent() {
        return verPanelEditarInvent;
    }

    /**
     * @param verPanelEditarInvent the verPanelEditarInvent to set
     */
    public void setVerPanelEditarInvent(boolean verPanelEditarInvent) {
        this.verPanelEditarInvent = verPanelEditarInvent;
    }

    /**
     * @return the lstMarcasActivasXProducto
     */
    public List<SelectItem> getLstMarcasActivasXProducto() {
        return lstMarcasActivasXProducto;
    }

    /**
     * @param lstMarcasActivasXProducto the lstMarcasActivasXProducto to set
     */
    public void setLstMarcasActivasXProducto(List<SelectItem> lstMarcasActivasXProducto) {
        this.lstMarcasActivasXProducto = lstMarcasActivasXProducto;
    }

    /**
     * @return the lstPresentacionesActivasXProducto
     */
    public List<SelectItem> getLstPresentacionesActivasXProducto() {
        return lstPresentacionesActivasXProducto;
    }

    /**
     * @param lstPresentacionesActivasXProducto the
     * lstPresentacionesActivasXProducto to set
     */
    public void setLstPresentacionesActivasXProducto(List<SelectItem> lstPresentacionesActivasXProducto) {
        this.lstPresentacionesActivasXProducto = lstPresentacionesActivasXProducto;
    }

    public void LstVntFacturaXcountfecha() {
        generaInforme();
    }

    private String chartTitle = "Seleccione el Indicador", xaxisTitle = "1",
            xaxisLabels = "1", labels = "1", data = "1";

    private List<CartesianSeries> lstSerieInf = new ArrayList<>();

    private List<CartesianSeries> lstMisIndGrafico1 = new ArrayList<CartesianSeries>() {
        {
            add(new CartesianSeries() {
                {
                    setType(CartesianSeries.CartesianType.LINE);
                    setLabel("Periodos");
                    setDragable(Boolean.FALSE);
                    setDragConstraintAxis(DragConstraintAxis.Y);
                    add("100", 200);
                }
            });

            add(new CartesianSeries() {
                {
                    setType(CartesianSeries.CartesianType.LINE);
                    setLabel("Meta");
                    setDragable(Boolean.FALSE);
                    setDragConstraintAxis(DragConstraintAxis.Y);
                    add("100", 200);
                    setColor("black");
                }
            });

        }
    };

    public List<CartesianSeries> getLstMisIndGrafico1() {
        return lstMisIndGrafico1;
    }

    public void setLstMisIndGrafico1(List<CartesianSeries> lstMisIndGrafico1) {
        this.lstMisIndGrafico1 = lstMisIndGrafico1;
    }

    private Axis axis = new Axis() {
        {
            setType(AxisType.CATEGORY);
            setLabel("Periodos");
            setTicks(new String[]{"100"});

        }
    };

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    public void generaInforme() {
      //  SimpleDateFormat dformat = new SimpleDateFormat("YYYYMM");
        xaxisTitle = "Periodos";
        chartTitle = "Variación Indicador por Periodo";
        lstSerieInf.clear();
        CartesianSeries cs1 = lstMisIndGrafico1.get(0);
        cs1.clear();
        CartesianSeries cs2 = lstMisIndGrafico1.get(1);
        cs2.clear();

        List<String> lstEjeX1 = new ArrayList<>();

        cs1.setShow(Boolean.TRUE);
        cs2.setShow(Boolean.TRUE);

        for (Object facturas : inventarioSFBean.getLstVntFacturaXcountfecha()) {
            Object[] o = (Object[]) facturas;
            TablaVntFactura t = new TablaVntFactura();
            t.setVtnFecFactura((String) o[0]);
            t.setCountFactura((Long) o[1]);

            cs1.add(t.getVtnFecFactura().toString().toUpperCase(), t.getCountFactura());
            //cs2.add(dformat.format(bc.getRegistroind().getRegindPeriodoini()).toString().toUpperCase(), bc.getRegistroind().getIndId().getIndMeta());
            lstEjeX1.add(t.getVtnFecFactura().toString().toUpperCase());

        }

        labels = "Valores Periodos";
        String[] arrStrTriks1 = lstEjeX1.toArray(new String[0]);
        axis.setTicks(arrStrTriks1);
        axis.setLabel("Periodos");
        cs1.setLabel("Valores Periodos");
        cs2.setLabel("Meta");

    }
    
     /** JAOR 28/04/2018
     * @return blnProdEst
     */
    public boolean isBlnPrdEst() {
        return blnPrdEst;
    }

    public void filtrarXEstadoProd_VCE() {
        cargarProductosxEst();
    }
}
