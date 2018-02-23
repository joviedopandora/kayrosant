/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.evaluacion;

import adm.sys.dao.AdmCargo;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.AdmEmpresa;
import com.pandora.bussiness.util.EnEstadosOp;
import com.pandora.bussiness.util.EnPasoCalificacion;
import com.pandora.consulta.bean.PcsOrdenProduccionSFBean;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.evaluacion.ConsultaOPDTO;
import com.pandora.mod.evaluacion.EvaluacionSFBean;
import com.pandora.mod.evaluacion.dao.EvalBonificacion;
import com.pandora.mod.evaluacion.dao.EvalBonificacionColaborador;
import com.pandora.mod.evaluacion.dao.EvalCalificacion;
import com.pandora.mod.evaluacion.dao.EvalCalificacionPago;
import com.pandora.mod.evaluacion.dao.EvalDescuento;
import com.pandora.mod.evaluacion.dao.EvalDescuentoColaborador;
import com.pandora.mod.ordenprod.dao.PopCxcevento;
import com.pandora.mod.ordenprod.dao.PopCxcrol;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.web.adm.param.TablaAdmCrgXCol;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.venta.TablaVntRegistroventa;
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
import javax.enterprise.context.SessionScoped;
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
 * @author Breyner Robles
 */
@Named
@SessionScoped
public class EvaluacionJSFBean extends BaseJSFBean implements Serializable, IPasos {

    @Inject
    PrincipalJSFBean pjsfb;
    @EJB
    private EvaluacionSFBean evaluacionSFBean;
    @EJB
    private PcsOrdenProduccionSFBean ordenProduccionSFBean;

    private Integer numPanelInterno = 1;

    private Date fechaInicial = null;
    private Date fechaFinal = null;
    private List<TablaVntRegistroventa> lstTablaVntRegistroventa = new ArrayList<>();
    private TablaVntRegistroventa tablaVntRegistroventaSel = new TablaVntRegistroventa();

    private AdmCrgxcol cargoColEval = null;

    private AdmEmpresa empresa = null;
    private Date fechaHoy;
    private List<TablaOrdenProduccionColaborador> listaColaboradores = new ArrayList<>();

    private List<SelectItem> lstRoles = new ArrayList<>();
    private List<SelectItem> lstCargos = new ArrayList<>();
    private List<SelectItem> lstCalificacion = new ArrayList<>();
    private List<TablaBonificacion> lstBonificacion = new ArrayList<>();
    private List<TablaDescuento> lstDescuento = new ArrayList<>();
    private HashMap<Integer, PopCxcrol> mapRoles = new HashMap<>();

    private HashMap<String, AdmCargo> mapCargo = new HashMap<>();
    private HashMap<Integer, EvalCalificacion> mapCalificacion = new HashMap<>();
    // parametros de la busqueda..
    private ConsultaOPDTO parametrosConsulta;
    private List<TablaAdmCrgXCol> lstTablaAdmCrgXCol = new ArrayList<>();
    private TablaOrdenProduccionColaborador tablaOrdenProduccionColaboradorAsociado;

    public void asociarDescuento(ActionEvent ae) {
        tablaOrdenProduccionColaboradorAsociado = null;
        tablaOrdenProduccionColaboradorAsociado = (TablaOrdenProduccionColaborador) ae.getComponent().getAttributes().get("tabla");

        for (TablaDescuento t : lstDescuento) {
            //t.setSeleccionado(false);
            t.setSeleccionado((tablaOrdenProduccionColaboradorAsociado.getMapaDescuento().get(t.getDescuento().getDescuentoId()) != null));

        }
    }

    public void agregarDescuento(ActionEvent ae) {
        // tablaOrdenProduccionColaboradorAsociado = null;
        tablaOrdenProduccionColaboradorAsociado.getMapaDescuento().clear();
        BigDecimal bonos = BigDecimal.ZERO;
        for (TablaDescuento t : lstDescuento) {
            if (t.isSeleccionado()) {
                if (t.getCantidad() != null && t.getCantidad() > 0) {
                    EvalDescuentoColaborador v = new EvalDescuentoColaborador();
                    v.setEvalDescuento(t.getDescuento());
                    v.setPopCxcevento(tablaOrdenProduccionColaboradorAsociado.getPopCxcevento());
                    tablaOrdenProduccionColaboradorAsociado.getMapaDescuento().put(t.getDescuento().getDescuentoId(), v);

                    if (t.getDescuento().isDescuentaMultiplicable()) {
                        bonos = bonos.add(t.getDescuento().getDescuentoValor().multiply(BigDecimal.valueOf(t.getCantidad().doubleValue())));
                    } else {
                        bonos = bonos.add(t.getDescuento().getDescuentoValor());
                    }
                }
            }
        }

        tablaOrdenProduccionColaboradorAsociado.getPopCxcevento().setCxeValorDescuento(bonos);
        tablaOrdenProduccionColaboradorAsociado.setValorDescuento(bonos);
        tablaOrdenProduccionColaboradorAsociado.calcularValores();

    }

    public void asociarBonifiacion(ActionEvent ae) {
        tablaOrdenProduccionColaboradorAsociado = null;
        tablaOrdenProduccionColaboradorAsociado = (TablaOrdenProduccionColaborador) ae.getComponent().getAttributes().get("tabla");

        for (TablaBonificacion t : lstBonificacion) {
            t.setSeleccionado(false);
            t.setSeleccionado((tablaOrdenProduccionColaboradorAsociado.getMapaBonificaciones().get(t.getBonificacion().getBonificacionId()) != null));

            if (t.getBonificacion().isBonificacionTipoAntiguedad()) {
                if (tablaOrdenProduccionColaboradorAsociado.getPopCxcevento().
                        getCxcId().getCpeId().getColCedula().getIdAntiguedad() != null
                        && tablaOrdenProduccionColaboradorAsociado.getPopCxcevento().getCxcId().getCpeId().getColCedula().getIdAntiguedad().getValorAntiguedad() != null) {
                    t.getBonificacion().setBonificacionValor(tablaOrdenProduccionColaboradorAsociado.getPopCxcevento().getCxcId().getCpeId().getColCedula().getIdAntiguedad().getValorAntiguedad());
                    if (tablaOrdenProduccionColaboradorAsociado.getMapaBonificaciones().isEmpty()) {
                        t.setSeleccionado(true);
                    }
                }

            }

        }
    }

    public void agregarBonifiacion(ActionEvent ae) {
        // tablaOrdenProduccionColaboradorAsociado = null;
        tablaOrdenProduccionColaboradorAsociado.getMapaBonificaciones().clear();
        BigDecimal bonos = BigDecimal.ZERO;

        /* if (listaColaboradores.contains(tablaOrdenProduccionColaboradorAsociado)) {
         tablaOrdenProduccionColaboradorAsociado = listaColaboradores.get(listaColaboradores.indexOf(tablaOrdenProduccionColaboradorAsociado));
         }*/
        for (TablaBonificacion t : lstBonificacion) {
            if (t.isSeleccionado()) {
                if (t.getCantidad() != null && t.getCantidad() > 0) {
                    EvalBonificacionColaborador v = new EvalBonificacionColaborador();
                    v.setEvalBonificacion(t.getBonificacion());
                    v.setPopCxcevento(tablaOrdenProduccionColaboradorAsociado.getPopCxcevento());
                    tablaOrdenProduccionColaboradorAsociado.getMapaBonificaciones().put(t.getBonificacion().getBonificacionId(), v);
                    if (t.getBonificacion().isBonificacionMultiplcable()) {
                        bonos = bonos.add(t.getBonificacion().getBonificacionValor().multiply(BigDecimal.valueOf(t.getCantidad().doubleValue())));
                    } else {
                        bonos = bonos.add(t.getBonificacion().getBonificacionValor());
                    }
                }

            }
        }

        tablaOrdenProduccionColaboradorAsociado.getPopCxcevento().setCxeValorBonificacion(bonos);
        tablaOrdenProduccionColaboradorAsociado.setValorBonificacion(bonos);
        tablaOrdenProduccionColaboradorAsociado.calcularValores();

    }

    public void asociarColaboradores(ActionEvent ae) {
        for (TablaAdmCrgXCol ts : lstTablaAdmCrgXCol) {
            if (ts.isSeleccionado()) {
                PopCxcevento p = new PopCxcevento();
                p.setCxcId(ts.getAdmCrgxcol());
                p.setCxeFechaproceso(new Date());
                p.setCxeValorPagar(BigDecimal.ZERO);
                p.setOprId(tablaVntRegistroventaSel.getOrdenprod());

                TablaOrdenProduccionColaborador tc = new TablaOrdenProduccionColaborador(p);
                if (!listaColaboradores.contains(tc)) {
                    tc.setInidceFila(listaColaboradores.size() + 1);
                    listaColaboradores.add(tc);
                    ts.setSeleccionado(false);
                }
            }
        }
    }

    public void seleccionarEvaluacion(ValueChangeEvent vc) {
        TablaOrdenProduccionColaborador tc = (TablaOrdenProduccionColaborador) vc.getComponent().getAttributes().get("tabla");
        tc.setCalifacacionSeleccionado((Integer) vc.getNewValue());
        if (tc.getCalifacacionSeleccionado() != null && tc.getCalifacacionSeleccionado() != -1) {
            tc.getPopCxcevento().setEvalCalificacion(mapCalificacion.get(tc.getCalifacacionSeleccionado()));
            EvalCalificacionPago pago = evaluacionSFBean.
                    getEvalCalificacionPagoPorCalificacion(tc.getCalifacacionSeleccionado(), tc.getCargoSeleccionado());
            tc.getPopCxcevento().setCxeValorPagar(BigDecimal.ZERO);
            if (pago != null) {
                tc.getPopCxcevento().setCxeValorPagar(pago.getPagoValorLocal());
                if (tablaVntRegistroventaSel.getOrdenprod().getOprDepart() != null
                        && tablaVntRegistroventaSel.getOrdenprod().getOprDepart().getDepId() != null
                        && tc.getPopCxcevento().getCxcId().
                                getCpeId().getColCedula().getColDepart() != null
                        && tc.getPopCxcevento().getCxcId().
                                getCpeId().getColCedula().getColDepart().getDepId() != null
                        && !tablaVntRegistroventaSel.getOrdenprod().getOprDepart().equals(tc.getPopCxcevento().getCxcId().
                                getCpeId().getColCedula().getColDepart())) {
                    tc.getPopCxcevento().setCxeValorPagar(pago.getPagoValorVisitante());

                }
            }
            tc.setValorBasico(tc.getPopCxcevento().getCxeValorPagar());
            tc.setValorPagoFinal(tc.getPopCxcevento().getCxeValorPagar());

        }
    }

    private void cargarListaColXCrgXEstado() {
        lstTablaAdmCrgXCol.clear();
        for (AdmCrgxcol crgxcol : ordenProduccionSFBean.getLstAdmCrgxcolXEstado(true)) {
            TablaAdmCrgXCol tacxc = new TablaAdmCrgXCol(crgxcol);
            lstTablaAdmCrgXCol.add(tacxc);
        }
    }

    public void rowDtOrdenProduccionCargarColaboradores_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        tablaVntRegistroventaSel = ((TablaVntRegistroventa) map.get("trvs"));
        numPanel = 2;
        cargarColaboradoresPorOp();

    }

    private void cargarColaboradoresPorOp() {
        this.listaColaboradores.clear();
        for (PopCxcevento p : evaluacionSFBean.getLstAdmCrgxcol(tablaVntRegistroventaSel.getOrdenprod().getOprId(), true)) {
            TablaOrdenProduccionColaborador t = new TablaOrdenProduccionColaborador(p);
            t.setInidceFila(this.listaColaboradores.size() + 1);
            t.setMapaBonificaciones(evaluacionSFBean.getLstEvalBonificacionColaborador(p.getCxeId()));
            this.listaColaboradores.add(t);
        }
    }

    private void cargarParametrosXColaborador() {

        mapRoles.clear();
        lstRoles.clear();
        lstRoles.add(itemSeleccioneInt);
        for (PopCxcrol c : ordenProduccionSFBean.getLstPopCxcrol(true)) {
            lstRoles.add(new SelectItem(c.getCxrId(), c.getCxrRol()));
            mapRoles.put(c.getCxrId(), c);
        }

        lstCargos.clear();
        mapCargo.clear();
        lstCargos.add(itemSeleccione);
        for (AdmCargo rt : evaluacionSFBean.getLstAdmCargo(true)) {
            lstCargos.add(new SelectItem(rt.getCrgId(), rt.getCrgNombre()));
            mapCargo.put(rt.getCrgId(), rt);
        }

        lstCalificacion.clear();
        mapCalificacion.clear();
        lstCalificacion.add(itemSeleccioneInt);
        for (EvalCalificacion rt : evaluacionSFBean.getLstEvalCalificacion(true)) {
            lstCalificacion.add(new SelectItem(rt.getCalificacionId(), rt.getCalificacionDesc()));
            mapCalificacion.put(rt.getCalificacionId(), rt);
        }

        lstBonificacion.clear();

        for (EvalBonificacion c : evaluacionSFBean.getLstEvalBonificacion(true)) {
            TablaBonificacion tb = new TablaBonificacion(c);
            tb.setCantidad(!c.isBonificacionMultiplcable() ? 1 : 0);
            lstBonificacion.add(tb);
        }

        lstDescuento.clear();
        for (EvalDescuento c : evaluacionSFBean.getLstEvalDescuento(true)) {
            TablaDescuento tb = new TablaDescuento(c);
            tb.setCantidad(!c.isDescuentaMultiplicable() ? 1 : 0);
            lstDescuento.add(tb);
        }

    }

    public void buscarOp_ActionEvent(ActionEvent ae) {
        parametrosConsulta.setTipoCliente(getPrincipalJSFBean().getColxempLog().
                getColCedula().getVntRfTipocliente() == null ? 1
                        : getPrincipalJSFBean().getColxempLog().getColCedula()
                                .getVntRfTipocliente().getTclId());
        parametrosConsulta.setCantidadEvaluaciones(EnPasoCalificacion.FINAL.getId());
        lstTablaVntRegistroventa.clear();
        /*if(parametrosConsulta.getFechaOp() == null){
            
         }*/
        List<PopOrdenprod> lista = evaluacionSFBean.getLstPopOrdenprodXPendientes(parametrosConsulta);
        for (PopOrdenprod op : lista) {
            lstTablaVntRegistroventa.add(new TablaVntRegistroventa(op));

        }
    }

    public void grabarListaColaboradoresPorEvento() {
        List<PopCxcevento> lista = new ArrayList<>();
        for (TablaOrdenProduccionColaborador t : listaColaboradores) {
            PopCxcevento p = t.getPopCxcevento();
            p.setCxeValorPagar(t.getValorBasico());
            p.setCxeValorBonificacion(t.getValorBonificacion());
            p.setEvalBonificacionColaboradorList(new ArrayList<EvalBonificacionColaborador>());
            p.setEvalDescuentoColaboradorList(new ArrayList<EvalDescuentoColaborador>());
            if (t.getMapaBonificaciones() != null && !t.getMapaBonificaciones().isEmpty()) {
                for (Map.Entry<Integer, EvalBonificacionColaborador> entrySet : t.getMapaBonificaciones().entrySet()) {

                    EvalBonificacionColaborador value = entrySet.getValue();
                    value.setPopCxcevento(p);
                    p.getEvalBonificacionColaboradorList().add(value);
                }

            }
            if (t.getMapaDescuento() != null && !t.getMapaDescuento().isEmpty()) {
                for (Map.Entry<Integer, EvalDescuentoColaborador> entrySet : t.getMapaDescuento().entrySet()) {

                    EvalDescuentoColaborador value = entrySet.getValue();
                    value.setPopCxcevento(p);
                    p.getEvalDescuentoColaboradorList().add(value);
                }

            }
            lista.add(p);
        }
        ordenProduccionSFBean.grabarLstCxceventos(lista);
    }

    public void rowDtGenerarFormatoPago_ActionEvent(ActionEvent ae) {
        Map map = ae.getComponent().getAttributes();
        TablaVntRegistroventa trv = ((TablaVntRegistroventa) map.get("trvs"));
        generarExcelEvaluacionXEvento(trv);
    }

    private void generarExcelEvaluacionXEvento(TablaVntRegistroventa trv) {

    }

    private EvaluacionSFBean lookupEvaluacionSFBean() {
        try {
            Context c = new InitialContext();
            return (EvaluacionSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/EvaluacionSFBean!com.pandora.mod.evaluacion.EvaluacionSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PcsOrdenProduccionSFBean lookupPcsOrdenProduccionSFBean() {
        try {
            Context c = new InitialContext();
            return (PcsOrdenProduccionSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/PcsOrdenProduccionSFBean!com.pandora.consulta.bean.PcsOrdenProduccionSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    @Override
    public void init() {
        evaluacionSFBean = lookupEvaluacionSFBean();
        ordenProduccionSFBean = lookupPcsOrdenProduccionSFBean();
        cargarParametrosXColaborador();

        fechaInicial = null;
        fechaFinal = null;
        lstTablaVntRegistroventa.clear();
        cargoColEval = null;
        parametrosConsulta = new ConsultaOPDTO();

        tablaVntRegistroventaSel = null;
        empresa = getPrincipalJSFBean().getColxempLog().getEmpId();
        fechaHoy = new Date();
        numPanel = 1;
        numPanelInterno = 1;
        cargarListaColXCrgXEstado();

    }

    @Override
    public void limpiarVariables() {

        fechaInicial = null;
        fechaFinal = null;
        lstTablaVntRegistroventa.clear();
        tablaVntRegistroventaSel = null;
    }

    public void eliminarColaborador(ActionEvent ae) {
        tablaOrdenProduccionColaboradorAsociado.getPopCxcevento().setCxeEstado(false);
        List<PopCxcevento> lista = new ArrayList<>();
        lista.add(tablaOrdenProduccionColaboradorAsociado.getPopCxcevento());

        ordenProduccionSFBean.grabarLstCxceventos(lista);
        this.listaColaboradores.remove(tablaOrdenProduccionColaboradorAsociado);
        // cargarColaboradoresPorOp();
    }

    public void grabarInicial_ActionEvent(ActionEvent ae) {

        if (tablaVntRegistroventaSel.getOrdenprod().getOprCantidadEvaluacion().equals(EnPasoCalificacion.INICIAL.getId())) {
            if (validarForm()) {
                tablaVntRegistroventaSel.getOrdenprod().setOprCantidadEvaluacion(EnPasoCalificacion.PARCIAL_CARGO.getId());
                tablaVntRegistroventaSel.setOrdenprod(ordenProduccionSFBean.editarOrdenProd(tablaVntRegistroventaSel.getOrdenprod()));
                grabarListaColaboradoresPorEvento();
                cargarColaboradoresPorOp();
                mostrarError("Datos iniciales guardados con exito.", 3);
            }
        } else if (tablaVntRegistroventaSel.getOrdenprod().
                getOprCantidadEvaluacion().equals(EnPasoCalificacion.PARCIAL_CARGO.getId())) {
            if (validarFormularioEvaluacion()) {
                tablaVntRegistroventaSel.getOrdenprod().setOprCantidadEvaluacion(EnPasoCalificacion.PARCIAL_CALIFICACION.getId());
                tablaVntRegistroventaSel.setOrdenprod(ordenProduccionSFBean.editarOrdenProd(tablaVntRegistroventaSel.getOrdenprod()));
                grabarListaColaboradoresPorEvento();
                cargarColaboradoresPorOp();
                mostrarError("Evaluación guardada con exito.", 3);
            }
        } else if (tablaVntRegistroventaSel.getOrdenprod().getOprCantidadEvaluacion().equals(EnPasoCalificacion.PARCIAL_CALIFICACION.getId())) {

            tablaVntRegistroventaSel.getOrdenprod().setOprCantidadEvaluacion(EnPasoCalificacion.PARCIAL_BONIFICACION.getId());
            tablaVntRegistroventaSel.setOrdenprod(ordenProduccionSFBean.editarOrdenProd(tablaVntRegistroventaSel.getOrdenprod()));
            grabarListaColaboradoresPorEvento();
            cargarColaboradoresPorOp();
            mostrarError("Bonificación guardada con exito.", 3);

        } else if (tablaVntRegistroventaSel.getOrdenprod().getOprCantidadEvaluacion().equals(EnPasoCalificacion.PARCIAL_BONIFICACION.getId())) {

            tablaVntRegistroventaSel.getOrdenprod().setOprCantidadEvaluacion(EnPasoCalificacion.FINAL.getId());
            tablaVntRegistroventaSel.setOrdenprod(ordenProduccionSFBean.editarOrdenProd(tablaVntRegistroventaSel.getOrdenprod()));
            grabarListaColaboradoresPorEvento();
            cargarColaboradoresPorOp();
            mostrarError("Calificación Adminsitrativa guardada con exito.", 3);

        }

    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {

    }

    public void regresar_ActionEvent(ActionEvent ae) {

    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        parametrosConsulta = new ConsultaOPDTO();
        numPanel = Integer.parseInt((String) ae.getComponent().getAttributes().get("numPanel"));
        listaColaboradores.clear();
        lstTablaVntRegistroventa.clear();

    }

    @Override
    public boolean grabarPaso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validarForm() {
        boolean validar = true;
        for (TablaOrdenProduccionColaborador t : listaColaboradores) {
            if (t.getRolSeleccionado() == null || t.getRolSeleccionado() == -1) {
                this.mostrarError("En la Fila " + " # " + t.getInidceFila()
                        + " hace falta asociar el rol para el colaborador" + t.getPopCxcevento().getCxcId().getCpeId().getColCedula().getNombres(), 1);
                validar = false;
            } else {
                t.getPopCxcevento().setPopCxcrol(mapRoles.get(t.getRolSeleccionado()));
            }
            if (t.getCargoSeleccionado() == null || t.getCargoSeleccionado().equals("") || t.getCargoSeleccionado().equals("-1")) {
                this.mostrarError("En la Fila " + " # " + t.getInidceFila()
                        + " hace falta asociar el cargo con el que asistio al evento para el colaborador" + t.getPopCxcevento().getCxcId().getCpeId().getColCedula().getNombres(), 1);
                validar = false;
            } else {
                t.getPopCxcevento().setAdmCargo(mapCargo.get(t.getCargoSeleccionado()));
            }
        }
        return validar;
    }

    public boolean validarFormularioEvaluacion() {
        boolean validar = true;
        for (TablaOrdenProduccionColaborador t : listaColaboradores) {
            if (t.getCalifacacionSeleccionado() == null || t.getCalifacacionSeleccionado() == -1) {
                this.mostrarError("En la Fila " + " # " + t.getInidceFila()
                        + " hace falta asociar la evaluación para el colaborador" + t.getPopCxcevento().getCxcId().getCpeId().getColCedula().getNombres(), 1);
                validar = false;
            } else {
                t.getPopCxcevento().setEvalCalificacion(mapCalificacion.get(t.getCalifacacionSeleccionado()));
                t.getPopCxcevento().setCxeValorPagar(t.getValorBasico());

            }

        }
        return validar;
    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the numPanelInterno
     */
    public Integer getNumPanelInterno() {
        return numPanelInterno;
    }

    /**
     * @param numPanelInterno the numPanelInterno to set
     */
    public void setNumPanelInterno(Integer numPanelInterno) {
        this.numPanelInterno = numPanelInterno;
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
     * @return the lstTablaVntRegistroventa
     */
    public List<TablaVntRegistroventa> getLstTablaVntRegistroventa() {
        return lstTablaVntRegistroventa;
    }

    /**
     * @param lstTablaVntRegistroventa the lstTablaVntRegistroventa to set
     */
    public void setLstTablaVntRegistroventa(List<TablaVntRegistroventa> lstTablaVntRegistroventa) {
        this.lstTablaVntRegistroventa = lstTablaVntRegistroventa;
    }

    /**
     * @return the tablaVntRegistroventaSel
     */
    public TablaVntRegistroventa getTablaVntRegistroventaSel() {
        return tablaVntRegistroventaSel;
    }

    /**
     * @param tablaVntRegistroventaSel the tablaVntRegistroventaSel to set
     */
    public void setTablaVntRegistroventaSel(TablaVntRegistroventa tablaVntRegistroventaSel) {
        this.tablaVntRegistroventaSel = tablaVntRegistroventaSel;
    }

    /**
     * @return the cargoColEval
     */
    public AdmCrgxcol getCargoColEval() {
        return cargoColEval;
    }

    /**
     * @param cargoColEval the cargoColEval to set
     */
    public void setCargoColEval(AdmCrgxcol cargoColEval) {
        this.cargoColEval = cargoColEval;
    }

    public ConsultaOPDTO getParametrosConsulta() {
        return parametrosConsulta;
    }

    public void setParametrosConsulta(ConsultaOPDTO parametrosConsulta) {
        this.parametrosConsulta = parametrosConsulta;
    }

    public Date getFechaHoy() {
        return fechaHoy;
    }

    public void setFechaHoy(Date fechaHoy) {
        this.fechaHoy = fechaHoy;
    }

    public List<TablaOrdenProduccionColaborador> getListaColaboradores() {
        return listaColaboradores;
    }

    public void setListaColaboradores(List<TablaOrdenProduccionColaborador> listaColaboradores) {
        this.listaColaboradores = listaColaboradores;
    }

    public List<SelectItem> getLstRoles() {
        return lstRoles;
    }

    public void setLstRoles(List<SelectItem> lstRoles) {
        this.lstRoles = lstRoles;
    }

    public List<SelectItem> getLstCargos() {
        return lstCargos;
    }

    public void setLstCargos(List<SelectItem> lstCargos) {
        this.lstCargos = lstCargos;
    }

    public List<SelectItem> getLstCalificacion() {
        return lstCalificacion;
    }

    public void setLstCalificacion(List<SelectItem> lstCalificacion) {
        this.lstCalificacion = lstCalificacion;
    }

    public boolean isRenderCalificacion() {
        return tablaVntRegistroventaSel != null
                && tablaVntRegistroventaSel.getOrdenprod() != null
                && tablaVntRegistroventaSel.getOrdenprod().getOprId() != null
                && tablaVntRegistroventaSel.getOrdenprod().getOprCantidadEvaluacion().equals(EnPasoCalificacion.PARCIAL_CARGO.getId());
    }

    public boolean isRenderCargo() {
        return tablaVntRegistroventaSel != null
                && tablaVntRegistroventaSel.getOrdenprod() != null
                && tablaVntRegistroventaSel.getOrdenprod().getOprId() != null
                && tablaVntRegistroventaSel.getOrdenprod().getOprCantidadEvaluacion().equals(EnPasoCalificacion.INICIAL.getId());
    }

    public boolean isRenderBonificacion() {
        return tablaVntRegistroventaSel != null
                && tablaVntRegistroventaSel.getOrdenprod() != null
                && tablaVntRegistroventaSel.getOrdenprod().getOprId() != null
                && tablaVntRegistroventaSel.getOrdenprod().getOprCantidadEvaluacion().equals(EnPasoCalificacion.PARCIAL_CALIFICACION.getId());
    }

    public boolean isRenderAdministrativa() {
        return tablaVntRegistroventaSel != null
                && tablaVntRegistroventaSel.getOrdenprod() != null
                && tablaVntRegistroventaSel.getOrdenprod().getOprId() != null
                && tablaVntRegistroventaSel.getOrdenprod().getOprCantidadEvaluacion().equals(EnPasoCalificacion.PARCIAL_BONIFICACION.getId());
    }

    public boolean isRenderEliminar() {
        return tablaVntRegistroventaSel != null
                && tablaVntRegistroventaSel.getOrdenprod() != null
                && tablaVntRegistroventaSel.getOrdenprod().getOprId() != null
                && tablaVntRegistroventaSel.getOrdenprod().getOprCantidadEvaluacion().equals(EnPasoCalificacion.INICIAL.getId());
    }

    public List<TablaAdmCrgXCol> getLstTablaAdmCrgXCol() {
        return lstTablaAdmCrgXCol;
    }

    public void setLstTablaAdmCrgXCol(List<TablaAdmCrgXCol> lstTablaAdmCrgXCol) {
        this.lstTablaAdmCrgXCol = lstTablaAdmCrgXCol;
    }

    public List<TablaBonificacion> getLstBonificacion() {
        return lstBonificacion;
    }

    public void setLstBonificacion(List<TablaBonificacion> lstBonificacion) {
        this.lstBonificacion = lstBonificacion;
    }

    public List<TablaDescuento> getLstDescuento() {
        return lstDescuento;
    }

    public void setLstDescuento(List<TablaDescuento> lstDescuento) {
        this.lstDescuento = lstDescuento;
    }

    public TablaOrdenProduccionColaborador getTablaOrdenProduccionColaboradorAsociado() {
        return tablaOrdenProduccionColaboradorAsociado;
    }

    public void setTablaOrdenProduccionColaboradorAsociado(TablaOrdenProduccionColaborador tablaOrdenProduccionColaboradorAsociado) {
        this.tablaOrdenProduccionColaboradorAsociado = tablaOrdenProduccionColaboradorAsociado;
    }

}
