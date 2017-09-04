/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.parametrizacion;

import com.icesoft.faces.context.effects.JavascriptContext;
import com.pandora.adm.dao.InvCatprod;
import com.pandora.adm.dao.InvProducto;
import com.pandora.adm.param.ServicioSFBean;
import com.pandora.adm.param.TablaConsultaDTO;
import com.pandora.mod.venta.dao.ServRfTipocliente;
import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.mod.venta.dao.VntRfTipocliente;
import com.pandora.mod.venta.dao.VntRfTiposervicio;
import com.pandora.mod.venta.dao.VntServicio;
import com.pandora.mod.venta.dao.VntServicioxservicio;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.colaborador.ColaboradorJSFBean;
import com.pandora.web.venta.TablaVntProdxsrv;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import utilidades.EnTipoCliente;

/**
 *
 * @author Javier
 */
@Named(value = "parametrizacionJSFBean")
@SessionScoped
public class ParametrizacionJSFBean extends BaseJSFBean implements Serializable {

    @EJB
    private ServicioSFBean servicioSFBean;

    private ServicioSFBean lookupServicioSFBean() {
        try {
            Context c = new InitialContext();
            return (ServicioSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/ServicioSFBean!com.pandora.adm.param.ServicioSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private VntServicio servicio = new VntServicio();
    private List<VntServicio> lstServicios = new ArrayList<>();
    private List<TablaServicio> lstServiciosHijos = new ArrayList<>();
    private List<VntServicioxservicio> lstServiciosAsociados = new ArrayList<>();
    private String servNombre = null;
    private String servDesc = null;
    private BigDecimal porcentajeiva = new BigDecimal(0);
    private BigDecimal servVlrEmpresa = new BigDecimal(0);
    private BigDecimal servVlrpublico = new BigDecimal(0);
    private boolean servEstado = true;
    private String servObservacion = null;
    private Integer servTipoServicio = -1;
    private String servArchivo = null;
    private List<SelectItem> lstTiposServicios = new ArrayList<>();
    private String rutaArchivos = "";
   
    private List<TablaVntProdxsrv> lstProductosXServ = new ArrayList<>();
    private List<ServRfTipocliente> listaServiciosPorTipoCliente = new ArrayList<>();
    //Consultas de productos
    private Long idProducto = null;
    private String nombreProducto = null;
    private String descProducto = null;
    private Integer categoria = -1;
    private List<TablaProducto> lstProductos = new ArrayList<>();
    private List<SelectItem> lstCategorias = new ArrayList<>();
    private HashMap<Integer, TablaProducto> mapProductosSeleccionados = new HashMap<>();
    private HashMap<Long, TablaServicio> mapServiciosSeleccionados = new HashMap<>();
    private int cantProdSel = 0;
    private int cantServiciosSel = 0;
    private List<VntRfTipocliente> lstTipoCliente = new ArrayList<>();
    private boolean blnSrvEst;

    public void cargarListaTipoCliente() {
        lstTipoCliente.clear();

        for (VntRfTipocliente vtc : servicioSFBean.getLstVntRfTipocliente(true)) {
            lstTipoCliente.add(vtc);
        }
    }

    @Override
    public void init() {
        blnSrvEst = true;
        servicioSFBean = lookupServicioSFBean();
        numPanel = 1;
        limpiar();
        cargarServicios();

        cargarTiposServicios();
        cargarCategorias();
        rutaArchivos = getPrincipalJSFBean().getAdmCrgxcolActivo().getCpeId().getEmpId().getEmpRutaspdfscorreo()
                + File.separator;
        try {
            File file = new File(rutaArchivos);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
        }
        lstProductosXServ.clear();
        cargarListaTipoCliente();
    }

    private void cargarCategorias() {
        lstCategorias.clear();
        lstCategorias.add(itemSeleccioneInt);
        for (InvCatprod c : servicioSFBean.getLstInvCatprod(true)) {
            lstCategorias.add(new SelectItem(c.getCpdId(), c.getCpdNombre()));
        }
    }

    private void cargarProductoXServicio() {
        lstProductosXServ.clear();
        for (VntProdxsrv p : servicioSFBean.getLstVntProdxsrvXServicio(servicio.getVsrvId())) {
            
            lstProductosXServ.add(new TablaVntProdxsrv(p));
        }

    }

    private void cargarTiposServicios() {
        lstTiposServicios.clear();
        lstTiposServicios.add(itemSeleccioneInt);
        for (VntRfTiposervicio t : servicioSFBean.getLstVntRfTiposervicio(true)) {
            lstTiposServicios.add(new SelectItem(t.getTsrvId(), t.getTsrvNombre(), t.getTsrvDesc()));
        }

    }

    private void limpiar() {

        lstServicios.clear();
        lstServiciosHijos.clear();
        servNombre = null;
        servDesc = null;
        porcentajeiva = new BigDecimal(0);
        servVlrEmpresa = new BigDecimal(0);
        servVlrpublico = new BigDecimal(0);
        servEstado = true;
        servObservacion = null;
        servTipoServicio = -1;
        servArchivo = null;
        servicio = null;
        servicio = new VntServicio();
        lstProductosXServ.clear();
        lstProductos.clear();
        idProducto = null;
        nombreProducto = null;
        descProducto = null;
        categoria = -1;

    }

    private void cargarServicios() {
        lstServicios.clear();
        for (VntServicio s : servicioSFBean.getLstServiciosXEst(blnSrvEst)) {
            lstServicios.add(s);
        }
    }

    public void filtrarXEstado_VCE(ValueChangeEvent vce) {
        cargarServicios();
    }

    private void cargarServiciosHijos() {

        lstServiciosHijos.clear();
        for (VntServicio s : servicioSFBean.consultarServiciosPendientesAsociar(servicio.getVsrvId(), true)) {
            TablaServicio sr = new TablaServicio();
            sr.setSeleccionado(false);
            sr.setServicio(s);
            lstServiciosHijos.add(sr);
        }
    }

    private void cargarServiciosAsociados() {

        lstServiciosAsociados.clear();
        for (VntServicioxservicio s : servicioSFBean.consultarServiciosAsosiados(servicio.getVsrvId())) {
            lstServiciosAsociados.add(s);
        }
    }

    @Override
    public void limpiarVariables() {
        servicioSFBean.remove();
        limpiar();

    }

    public void rowDtEliminarProductoXServ_ActionEvent(ActionEvent ae) {
        
        TablaVntProdxsrv pxs = (TablaVntProdxsrv) ae.getComponent().getAttributes().get("prodxServ");
        
        servicioSFBean.elimiarPrdXServicio(pxs.getVntProdxsrv());
        cargarProductoXServicio();
        
        
    }

    public void rowDtServicio_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        servNombre = null;
        servDesc = null;
        porcentajeiva = new BigDecimal(0);
        servVlrEmpresa = new BigDecimal(0);
        servVlrpublico = new BigDecimal(0);
        servEstado = true;
        servObservacion = null;
        servTipoServicio = -1;
        servArchivo = null;
        servicio = null;
        servicio = new VntServicio();
        servicio = (VntServicio) map.get("servicio");
        servNombre = servicio.getVsrvNombre();
        servDesc = servicio.getVsrvDesc();
        porcentajeiva = servicio.getVsrvPorcentajeiva();
        servVlrEmpresa = servicio.getVsrvValunitempresa();
        servVlrpublico = servicio.getVsrvValunitcliente();
        servEstado = (servicio.getVsrvEst() == null ? false : servicio.getVsrvEst());
        servObservacion = servicio.getVsrvObsr();
        servArchivo = servicio.getVsrvArchivo();
        servTipoServicio = servicio.getTsrvId().getTsrvId();
        numPanel = 2;
        cargarTipoClienteServicio();

    }

    private void cargarTipoClienteServicio() {
        listaServiciosPorTipoCliente.clear();
        if (servicio != null && servicio.getVsrvId() != null) {
            HashMap<Integer, ServRfTipocliente> anteriores = servicioSFBean.obtenerServRfTipoclientePorServicio(servicio.getVsrvId());
            for (VntRfTipocliente tc : lstTipoCliente) {
                ServRfTipocliente r = anteriores.get(tc.getTclId());
                if (r == null) {
                    r = new ServRfTipocliente();
                    r.setServtcEst(false);
                    r.setVntRfTipocliente(tc);
                    r.setVntServicio(servicio);
                }
                listaServiciosPorTipoCliente.add(r);

            }
        } else {
            for (VntRfTipocliente tc : lstTipoCliente) {
                ServRfTipocliente r = new ServRfTipocliente();
                r.setServtcEst(false);
                r.setVntRfTipocliente(tc);
                r.setVntServicio(servicio);
                listaServiciosPorTipoCliente.add(r);
            }
        }

    }

    public void fileEntryAction(FileEntryEvent event) {
        System.out.println("fileEntryAction");
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
            /*  if (extension == null || extension.equals("")) {
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
             }*/

            try {
                fc = FacesContext.getCurrentInstance();
                ExternalContext ec = fc.getExternalContext();

                servArchivo = fileName;
                File file = new File(rutaArchivos + File.separator + fileName);

                copiarArchivo(fileInfo.getFile(), file);

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
        boolean validar = false;
        if (servNombre == null) {
            mostrarError("Nombre del servicio es requerido", 1);
            validar = true;
        } else {
            servNombre = servNombre.trim();
            if (servNombre.equals("")) {
                mostrarError("Nombre del servicio es requerido", 1);
                validar = true;
            }
        }

        if (servDesc == null) {
            mostrarError("Descripción del servicio es requerido", 1);
            validar = true;
        } else {
            servDesc = servDesc.trim();
            if (servDesc.equals("")) {
                mostrarError("Descripción del servicio es requerido", 1);
                validar = true;
            }
        }
        if (servVlrEmpresa == null) {
            mostrarError("Costo del servicio es requerido", 1);
            validar = true;
        }
        if (servVlrpublico == null) {
            mostrarError("Valor a los clientes del servicio es requerido", 1);
            validar = true;
        }

        if (porcentajeiva == null) {
            mostrarError("Costo de iva Obligatorio", 1);
            validar = true;
        }

        if (validar) {
            return;
        }
        if (servVlrEmpresa.compareTo(servVlrpublico) == 1) {
            mostrarError("Valor a los clientes debe ser mayor que el costo del servicio", 1);
            return;
        }
        try {
            servicio.setVsrvNombre(servNombre);
            servicio.setVsrvDesc(servDesc);
            servicio.setVsrvEst(servEstado);
            if (servicio.getVsrvFechacre() == null) {
                servicio.setVsrvFechacre(new Date());
            }
            servicio.setVsrvPorcentajeiva(porcentajeiva);
            servicio.setVsrvValunitempresa(servVlrEmpresa);
            servicio.setVsrvValunitcliente(servVlrpublico);
            servicio.setVsrvPorcentajeiva(getPorcentajeiva());
            servicio.setVsrvObsr(servDesc);
            servicio.setVsrvArchivo(servArchivo);
            servicio.setTsrvId(servicioSFBean.getVntRfTiposervicioXId(servTipoServicio));
            servicio = servicioSFBean.editarVntServicio(servicio);
            for (ServRfTipocliente s : listaServiciosPorTipoCliente) {
                s.setVntServicio(servicio);
            }
            servicioSFBean.editarServRfTipocliente(listaServiciosPorTipoCliente);
            cargarTipoClienteServicio();
            mostrarError("Servicio grabado exitosamente", 3);

        } catch (EJBException e) {
            mostrarError("Error al grabar el servicio", 1);
            servicioSFBean = lookupServicioSFBean();
        }
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        TablaConsultaDTO c = new TablaConsultaDTO();
        lstProductos.clear();
        if (categoria != null && !categoria.equals(-1)) {
            c.setCategoria(categoria);
        }
        c.setDescProducto(descProducto);
        c.setIdProducto(idProducto);
        c.setNombreProducto(nombreProducto);
        for (InvProducto p : servicioSFBean.getLstAllInvProductos(c)) {

            TablaProducto t = new TablaProducto();
            t.setProducto(p);

            t.setSeleccionado((mapProductosSeleccionados.get(p.getPrdId()) == null ? false : true));
            lstProductos.add(t);
        }

    }

    public void guardarProductoXServicio_ActionEvent(ActionEvent ae) {
        boolean error = false;
        if (lstProductosXServ == null || lstProductosXServ.isEmpty()) {
            lstProductosXServ = new ArrayList<>();
            mostrarError("Debe asociar algún producto", 1);
        } else {
            for (TablaVntProdxsrv s : lstProductosXServ) {
                if (s.getVntProdxsrv().getProdxsrvCantidad() == null || s.getVntProdxsrv().getProdxsrvCantidad() <= 0) {
                    mostrarError("El Producto " + s.getVntProdxsrv().getPrdId().getPrdNombre() + " tiene canridad nula o menor igual que cero", 1);
                    error = true;
                }
            }
        }
        if (error) {
            return;
        }
        // se guarda
        List<VntProdxsrv> lstPrdXServ=new ArrayList<>();
        for (TablaVntProdxsrv tvp : lstProductosXServ) {
            lstPrdXServ.add(tvp.getVntProdxsrv());
        }
        servicioSFBean.editarListaServicioXProducto(lstPrdXServ);
        lstProductosXServ.clear();

        cargarProductoXServicio();
        mostrarError("Productos asociados exitosamente", 3);
    }

    public void aceptarProducto_ActionEvent(ActionEvent ae) {
        for (TablaVntProdxsrv s : lstProductosXServ) {
            TablaProducto t = mapProductosSeleccionados.get(s.getVntProdxsrv().getPrdId().getPrdId());
            if (t != null) {
                s.getVntProdxsrv().setProdxsrvEst(true);
                mapProductosSeleccionados.remove(t);
            }
        }
        for (Map.Entry<Integer, TablaProducto> entry : mapProductosSeleccionados.entrySet()) {
            VntProdxsrv t = new VntProdxsrv();
            t.setPrdId(entry.getValue().getProducto());
            t.setIndversion(0);
            t.setProdxsrvEst(true);
            t.setVsrvId(servicio);
            t.setProdxsrvCantidad(1);
            lstProductosXServ.add(new TablaVntProdxsrv(t));
        }

    }

    public void aceptarServicio_ActionEvent(ActionEvent ae) {
        for (VntServicioxservicio s : lstServiciosAsociados) {
            TablaServicio t = mapServiciosSeleccionados.get(s.getVntServicioHijo().getVsrvId());
            if (t != null) {
                s.setVsvxsEstado(true);
                mapServiciosSeleccionados.remove(s.getVntServicioHijo().getVsrvId());
            }
        }
        for (Map.Entry<Long, TablaServicio> entry : mapServiciosSeleccionados.entrySet()) {
            VntServicioxservicio v = new VntServicioxservicio();
            v.setVntServicioHijo(entry.getValue().getServicio());
            v.setVntServicioPadre(servicio);
            v.setVsvxsEstado(true);
            lstServiciosAsociados.add(v);
        }
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "dialogPresentacionServicios.hide();");
        mapServiciosSeleccionados.clear();
        lstServiciosHijos.clear();
    }

    public void selServicio_ValueChangeEvent(ValueChangeEvent vce) {
        Map map = vce.getComponent().getAttributes();
        TablaServicio prod = (TablaServicio) map.get("servicio");
        boolean sel = (Boolean) vce.getNewValue();
        if (sel) {
            mapServiciosSeleccionados.put(prod.getServicio().getVsrvId(), prod);
        } else {
            mapServiciosSeleccionados.remove(prod.getServicio().getVsrvId());
        }
        cantServiciosSel = mapServiciosSeleccionados.size();

    }

    public void selProducto_ValueChangeEvent(ValueChangeEvent vce) {
        Map map = vce.getComponent().getAttributes();
        TablaProducto prod = (TablaProducto) map.get("producto");
        boolean sel = (Boolean) vce.getNewValue();
        if (sel) {
            mapProductosSeleccionados.put(prod.getProducto().getPrdId(), prod);
        } else {
            mapProductosSeleccionados.remove(prod.getProducto().getPrdId());
        }
        cantProdSel = mapProductosSeleccionados.size();

    }

    public void guardarServicioXServicio_ActionEvent(ActionEvent ae) {
        boolean error = false;
        if (lstServiciosAsociados == null || lstServiciosAsociados.isEmpty()) {
            lstServiciosAsociados = new ArrayList<>();
            mostrarError("Debe asociar algún Servicio", 1);
        }
        // se guarda
        servicioSFBean.editarListaServicioXServicio(lstServiciosAsociados);
        lstServiciosAsociados.clear();

        cargarServiciosAsociados();
        mostrarError("Servicios asociados exitosamente", 3);
    }

    public void rowDtEliminarServiciosXServicios_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        VntServicioxservicio pxs = (VntServicioxservicio) map.get("sxserv");
        lstServiciosAsociados.remove(pxs);
    }

    public void cambioEstadoServicioaXServ_ValueChangeEvent(ValueChangeEvent vce) {
        Map map = vce.getComponent().getAttributes();
        VntServicioxservicio prod = (VntServicioxservicio) map.get("sxserv");
        prod.setVsvxsEstado((Boolean) vce.getNewValue());

    }

    public void mostrarProductos_ActionEvent(ActionEvent ae) {
        idProducto = null;
        nombreProducto = null;
        descProducto = null;
        categoria = -1;
        mapProductosSeleccionados.clear();
        lstProductos.clear();
    }

    public void mostrarServicios_ActionEvent(ActionEvent ae) {

        mapServiciosSeleccionados.clear();
        cargarServiciosHijos();
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        int panel = numPanel;
        cantProdSel = 0;
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        lstProductos.clear();
        mapProductosSeleccionados.clear();
        mapServiciosSeleccionados.clear();
        switch (numPanel) {
            case 1:
                /*  if (panel > numPanel) {
                    numPanel = panel;

                } else {
                    limpiar();
                }*/
                limpiar();

                cargarServicios();

                break;
            case 2:
                limpiar();
                cargarTipoClienteServicio();
                setearClientesDefecto();
                break;
            case 3:
                idProducto = null;
                nombreProducto = null;
                descProducto = null;
                categoria = -1;
                if (servicio.getVsrvId() == null) {
                    mostrarError("Primero se debe guardar o seleccionar el servicio.", 1);
                    numPanel = panel;
                } else {
                    //Cargar los productos por servicio

                    cargarProductoXServicio();
                }
                break;
            case 4:

                if (servicio.getVsrvId() == null) {
                    mostrarError("Primero se debe guardar o seleccionar el servicio.", 1);
                    numPanel = panel;
                } else {
                    cargarServiciosAsociados();

                }

                break;
            case 5:

                break;

        }
    }

    private void setearClientesDefecto() {
        Integer tipoCliente = null;
        if (getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente() != null) {
            tipoCliente = getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente().getTclId();
        }
        if (tipoCliente != null && servicio != null && servicio.getVsrvId() == null) {
            for (ServRfTipocliente s : listaServiciosPorTipoCliente) {
                if (tipoCliente.equals(EnTipoCliente.KIDS.getId())) {
                    if (EnTipoCliente.KIDS.getId().equals(s.getVntRfTipocliente().getTclId())) {
                        s.setServtcEst(true);
                    }
                } else if (!EnTipoCliente.KIDS.getId().equals(s.getVntRfTipocliente().getTclId())) {
                    s.setServtcEst(true);
                }
            }
        }
        //&& getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente().getTclId().equals(EnTipoCliente.KIDS.getId())
    }

    public void cambioEstadoProductoXServ_ValueChangeEvent(ValueChangeEvent vce) {
        
        TablaVntProdxsrv prod = (TablaVntProdxsrv) vce.getComponent().getAttributes().get("prodXServ");
        prod.getVntProdxsrv().setProdxsrvEst((Boolean) vce.getNewValue());

    }

    /**
     * @return the servicio
     */
    public VntServicio getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(VntServicio servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the lstServicios
     */
    public List<VntServicio> getLstServicios() {
        return lstServicios;
    }

    /**
     * @param lstServicios the lstServicios to set
     */
    public void setLstServicios(List<VntServicio> lstServicios) {
        this.lstServicios = lstServicios;
    }

    /**
     * @return the servNombre
     */
    public String getServNombre() {
        return servNombre;
    }

    /**
     * @param servNombre the servNombre to set
     */
    public void setServNombre(String servNombre) {
        this.servNombre = servNombre;
    }

    /**
     * @return the lstServiciosHijos
     */
    public List<TablaServicio> getLstServiciosHijos() {
        return lstServiciosHijos;
    }

    /**
     * @param lstServiciosHijos the lstServicios to set
     */
    public void setLstServiciosHijos(List<TablaServicio> lstServiciosHijos) {
        this.lstServiciosHijos = lstServiciosHijos;
    }

    /**
     * @return the servDesc
     */
    public String getServDesc() {
        return servDesc;
    }

    /**
     * @param servDesc the servDesc to set
     */
    public void setServDesc(String servDesc) {
        this.servDesc = servDesc;
    }

    /**
     * @return the servVlrEmpresa
     */
    public BigDecimal getServVlrEmpresa() {
        return servVlrEmpresa;
    }

    /**
     * @param servVlrEmpresa the servVlrEmpresa to set
     */
    public void setServVlrEmpresa(BigDecimal servVlrEmpresa) {
        this.servVlrEmpresa = servVlrEmpresa;
    }

    /**
     * @return the servVlrpublico
     */
    public BigDecimal getServVlrpublico() {
        return servVlrpublico;
    }

    /**
     * @param servVlrpublico the servVlrpublico to set
     */
    public void setServVlrpublico(BigDecimal servVlrpublico) {
        this.servVlrpublico = servVlrpublico;
    }

    /**
     * @return the servEstado
     */
    public boolean isServEstado() {
        return servEstado;
    }

    /**
     * @param servEstado the servEstado to set
     */
    public void setServEstado(boolean servEstado) {
        this.servEstado = servEstado;
    }

    /**
     * @return the servObservacion
     */
    public String getServObservacion() {
        return servObservacion;
    }

    /**
     * @param servObservacion the servObservacion to set
     */
    public void setServObservacion(String servObservacion) {
        this.servObservacion = servObservacion;
    }

    /**
     * @return the servTipoServicio
     */
    public Integer getServTipoServicio() {
        return servTipoServicio;
    }

    /**
     * @param servTipoServicio the servTipoServicio to set
     */
    public void setServTipoServicio(Integer servTipoServicio) {
        this.servTipoServicio = servTipoServicio;
    }

    /**
     * @return the servArchivo
     */
    public String getServArchivo() {
        return servArchivo;
    }

    /**
     * @param servArchivo the servArchivo to set
     */
    public void setServArchivo(String servArchivo) {
        this.servArchivo = servArchivo;
    }

    /**
     * @return the lstTiposServicios
     */
    public List<SelectItem> getLstTiposServicios() {
        return lstTiposServicios;
    }

    /**
     * @param lstTiposServicios the lstTiposServicios to set
     */
    public void setLstTiposServicios(List<SelectItem> lstTiposServicios) {
        this.lstTiposServicios = lstTiposServicios;
    }

    /**
     * @return the lstProductosXServ
     */
    public List<TablaVntProdxsrv> getLstProductosXServ() {
        return lstProductosXServ;
    }

    /**
     * @param lstProductosXServ the lstProductosXServ to set
     */
    public void setLstProductosXServ(List<TablaVntProdxsrv> lstProductosXServ) {
        this.lstProductosXServ = lstProductosXServ;
    }

    /**
     * @return the lstProductos
     */
    public List<TablaProducto> getLstProductos() {
        return lstProductos;
    }

    /**
     * @param lstProductos the lstProductos to set
     */
    public void setLstProductos(List<TablaProducto> lstProductos) {
        this.lstProductos = lstProductos;
    }

    /**
     * @return the idProducto
     */
    public Long getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the nombreProducto
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * @param nombreProducto the nombreProducto to set
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * @return the descProducto
     */
    public String getDescProducto() {
        return descProducto;
    }

    /**
     * @param descProducto the descProducto to set
     */
    public void setDescProducto(String descProducto) {
        this.descProducto = descProducto;
    }

    /**
     * @return the categoria
     */
    public Integer getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the lstCategorias
     */
    public List<SelectItem> getLstCategorias() {
        return lstCategorias;
    }

    /**
     * @param lstCategorias the lstCategorias to set
     */
    public void setLstCategorias(List<SelectItem> lstCategorias) {
        this.lstCategorias = lstCategorias;
    }

    /**
     * @return the cantProdSel
     */
    public int getCantProdSel() {
        return cantProdSel;
    }

    /**
     * @param cantProdSel the cantProdSel to set
     */
    public void setCantProdSel(int cantProdSel) {
        this.cantProdSel = cantProdSel;
    }

    /**
     * @return the porcentajeiva
     */
    public BigDecimal getPorcentajeiva() {
        return porcentajeiva;
    }

    /**
     * @param porcentajeiva the porcentajeiva to set
     */
    public void setPorcentajeiva(BigDecimal porcentajeiva) {
        this.porcentajeiva = porcentajeiva;
    }

    public List<ServRfTipocliente> getListaServiciosPorTipoCliente() {
        return listaServiciosPorTipoCliente;
    }

    public void setListaServiciosPorTipoCliente(List<ServRfTipocliente> listaServiciosPorTipoCliente) {
        this.listaServiciosPorTipoCliente = listaServiciosPorTipoCliente;
    }

    public List<VntServicioxservicio> getLstServiciosAsociados() {
        return lstServiciosAsociados;
    }

    public void setLstServiciosAsociados(List<VntServicioxservicio> lstServiciosAsociados) {
        this.lstServiciosAsociados = lstServiciosAsociados;
    }

    public int getCantServiciosSel() {
        return cantServiciosSel;
    }

    public void setCantServiciosSel(int cantServiciosSel) {
        this.cantServiciosSel = cantServiciosSel;
    }

    /**
     * @return the blnSrvEst
     */
    public boolean isBlnSrvEst() {
        return blnSrvEst;
    }

    /**
     * @param blnSrvEst the blnSrvEst to set
     */
    public void setBlnSrvEst(boolean blnSrvEst) {
        this.blnSrvEst = blnSrvEst;
    }

}
