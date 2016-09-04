/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.indicadores;

import com.pandora.mod.indicadores.IndicadoresDTO;
import com.pandora.mod.indicadores.IndicadoresSFBean;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.venta.TablaVntFactura;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.component.chart.AxisType;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.chart.DragConstraintAxis;
import org.joda.time.DateTime;
import utilidades.ManejoFecha;
import utilidades.ManejoTexto;

/**
 *
 * @author patricia
 */
@Named("indicadoresJSFBean")
@SessionScoped
public class IndicadoresJSFBean extends BaseJSFBean implements Serializable {

    @EJB
    private IndicadoresSFBean indicadoresSFBean;
    private Integer idTipoCliente = 1;
    private Date fechaInicial = null;
    private Date fechaFinal = null;
    private boolean renderGrafic = true;

    @Override
    public void init() {
        renderGrafic = true;
        if (getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente() == null) {
            idTipoCliente = 1;
        } else {
            idTipoCliente = getPrincipalJSFBean().getColxempLog().getColCedula().getVntRfTipocliente().getTclId();
        }
        fechaInicial = null;
        fechaFinal = null;
        fechaInicial = new Date();
        fechaInicial = new Date(fechaInicial.getYear(), fechaInicial.getMonth(), fechaInicial.getDate() - 60);
        fechaFinal = new Date();
        generaInforme();
        generaInformeLLamadas();
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiarVariables() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        boolean error = false;
        if (fechaInicial == null) {
            mostrarError("Fecha Inicial requerida", 1);
            error = true;
        }
        if (fechaFinal == null) {
            mostrarError("Fecha Final requerida", 1);
            error = true;
        }
        if (error) {
            return;
        }
        if (ManejoFecha.compararDates(fechaInicial, fechaFinal) == 1) {
            mostrarError("Fecha Inicial debe ser menor que la Fecha Final", 1);
            error = true;
        }
         if (ManejoFecha.compararDates(fechaInicial, fechaFinal) == 0) {
            mostrarError("Fecha Inicial debe ser igual que la Fecha Final", 1);
            error = true;
        }
        if (ManejoFecha.compararDates(fechaFinal, new Date()) == 1) {
            mostrarError("Fecha Final debe ser menor que la Fecha del día", 1);
            error = true;
        }
        if (error) {
            return;
        }
        renderGrafic = true;
        generaInforme();
        generaInformeLLamadas();
    }

    public void cambioFecha_valueChangeEvent(ValueChangeEvent vce) {
        renderGrafic = false;
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    //public void LstVntFacturaXcountfecha() {
    //}
    private List<CartesianSeries> lstSerieInf = new ArrayList<>();
    private List<CartesianSeries> lstMisIndGrafico1 = new ArrayList<CartesianSeries>() {
        {
            add(new CartesianSeries() {
                {
                    setType(CartesianSeries.CartesianType.LINE);
                    setLabel("Días");
                    setDragable(Boolean.FALSE);
                    setDragConstraintAxis(DragConstraintAxis.Y);
                    add("100", 200);
                }
            });

            add(new CartesianSeries() {
                {
                    setType(CartesianSeries.CartesianType.LINE);
                    setLabel("Promedio");
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
            setLabel("Días");
            setTicks(new String[]{"201401"});
            //axis.setTickAngle(90);

        }
    };

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    private List<IndicadoresDTO> getLstDatos(Date fechaI, Date fechaF,HashMap<String, Long> mapaRtas) {
        Date fecha = fechaI;
        List<IndicadoresDTO> lst = new ArrayList<>();

        while (ManejoFecha.compararDates(fecha, fechaF) == 2) {
            IndicadoresDTO dto = new IndicadoresDTO();
            dto.setPeriodo(ManejoFecha.getFechaFormato("YYYYMMdd", fecha));
            dto.setCantidad(ManejoTexto.getTxtNVL(mapaRtas.get(dto.getPeriodo()), 0L));
            lst.add(dto);
            fecha = ManejoFecha.sumarDias(fecha, 1);

        }
        lst.add(new IndicadoresDTO(ManejoFecha.getFechaFormato("YYYYMMdd", fechaF), 0L));
        return lst;
    }

    public void generaInforme() {
        //  SimpleDateFormat dformat = new SimpleDateFormat("YYYYMM");

        getLstSerieInf().clear();
        CartesianSeries cs1 = lstMisIndGrafico1.get(0);
        cs1.clear();
        CartesianSeries cs2 = lstMisIndGrafico1.get(1);
        cs2.clear();

        List<String> lstEjeX1 = new ArrayList<>();

        cs1.setShow(Boolean.TRUE);
        cs2.setShow(Boolean.TRUE);

        Long promedio = indicadoresSFBean.getVentasXPormedioFecha(new java.sql.Date(fechaInicial.getTime()), idTipoCliente, false, new java.sql.Date(fechaFinal.getTime()));
        HashMap<String, Long> mapaRtas = indicadoresSFBean.getLstVentasXCountFecha(new java.sql.Date(fechaInicial.getTime()), idTipoCliente, false, new java.sql.Date(fechaFinal.getTime()));
        List<IndicadoresDTO> lst = getLstDatos(fechaInicial, fechaFinal,mapaRtas);
        for (IndicadoresDTO idt : lst) {

            cs1.add(idt.getPeriodo(), idt.getCantidad());
            cs2.add(idt.getPeriodo(), promedio);
            //cs2.add(dformat.format(bc.getRegistroind().getRegindPeriodoini()).toString().toUpperCase(), bc.getRegistroind().getIndId().getIndMeta());
            lstEjeX1.add(idt.getPeriodo());

        }

        /* for (IndicadoresDTO idt : indicadoresSFBean.getLstVentasXCountFecha(new java.sql.Date(fechaInicial.getTime()), idTipoCliente, false, new java.sql.Date(fechaFinal.getTime()))) {
            
         cs1.add(idt.getPeriodo(), idt.getCantidad());
         cs2.add(idt.getPeriodo(), promedio);
         //cs2.add(dformat.format(bc.getRegistroind().getRegindPeriodoini()).toString().toUpperCase(), bc.getRegistroind().getIndId().getIndMeta());
         lstEjeX1.add(idt.getPeriodo());
            
         }*/
        //   labels = "Valor por día";
        String[] arrStrTriks1 = lstEjeX1.toArray(new String[0]);

        axis.setTicks(arrStrTriks1);
        axis.setLabel("Días");
        axis.setTickAngle(90);
        cs1.setLabel("Valor");
        cs2.setLabel("Promedio");

    }
    private List<CartesianSeries> lstSerieInfllamda = new ArrayList<>();
    private List<CartesianSeries> lstMisIndGraficollamda = new ArrayList<CartesianSeries>() {
        {
            add(new CartesianSeries() {
                {
                    setType(CartesianSeries.CartesianType.LINE);
                    setLabel("Días");
                    setDragable(Boolean.FALSE);
                    setDragConstraintAxis(DragConstraintAxis.Y);
                    add("100", 200);
                }
            });

            add(new CartesianSeries() {
                {
                    setType(CartesianSeries.CartesianType.LINE);
                    setLabel("Promedio");
                    setDragable(Boolean.FALSE);
                    setDragConstraintAxis(DragConstraintAxis.Y);
                    add("100", 200);
                    setColor("black");
                }
            });

        }
    };
    private Axis axisLlamada = new Axis() {
        {
            setType(AxisType.CATEGORY);
            setLabel("Días");
            setTicks(new String[]{"100"});
            // setTickAngle(90);

        }
    };

    public Axis getAxisLlamada() {
        return axisLlamada;
    }

    public void setAxisLlamada(Axis axisLlamada) {
        this.axisLlamada = axisLlamada;
    }

    public void generaInformeLLamadas() {
        //  SimpleDateFormat dformat = new SimpleDateFormat("YYYYMM");
        //   xaxisTitle = "Periodos";
        //   chartTitle = "Variación Indicador por Periodo";
        lstSerieInfllamda.clear();
        CartesianSeries cs1 = lstMisIndGraficollamda.get(0);
        cs1.clear();
        CartesianSeries cs2 = lstMisIndGraficollamda.get(1);
        cs2.clear();

        List<String> lstEjeX1 = new ArrayList<>();

        cs1.setShow(Boolean.TRUE);
        cs2.setShow(Boolean.TRUE);

        Long promedio = indicadoresSFBean.getLLamadasXPormedioFecha(new java.sql.Date(fechaInicial.getTime()), idTipoCliente, new java.sql.Date(fechaFinal.getTime()));
         HashMap<String, Long> mapaRtas = indicadoresSFBean.getLstIndicadoresLLamadasXMes(new java.sql.Date(fechaInicial.getTime()), idTipoCliente, new java.sql.Date(fechaFinal.getTime()));
        List<IndicadoresDTO> lst = getLstDatos(fechaInicial, fechaFinal,mapaRtas);

        for (IndicadoresDTO td : lst) {

            cs1.add(td.getPeriodo(), td.getCantidad());
            cs2.add(td.getPeriodo(), promedio);

            lstEjeX1.add(td.getPeriodo());

        }

        //  labels = "Valores Periodos";
        String[] arrStrTriks1 = lstEjeX1.toArray(new String[0]);
        axisLlamada = new Axis() {
            {
                setType(AxisType.CATEGORY);
                setLabel("Días");
                setTicks(new String[]{"100"});

            }
        };
        axisLlamada.setTicks(arrStrTriks1);
        axisLlamada.setLabel("Días");
        axisLlamada.setTickAngle(90);
        cs1.setLabel("Valor");
        cs2.setLabel("Promedio");

    }

    /**
     * @return the lstSerieInf
     */
    public List<CartesianSeries> getLstSerieInf() {
        return lstSerieInf;
    }

    /**
     * @param lstSerieInf the lstSerieInf to set
     */
    public void setLstSerieInf(List<CartesianSeries> lstSerieInf) {
        this.lstSerieInf = lstSerieInf;
    }

    /**
     * @return the lstSerieInfllamda
     */
    public List<CartesianSeries> getLstSerieInfllamda() {
        return lstSerieInfllamda;
    }

    /**
     * @param lstSerieInfllamda the lstSerieInfllamda to set
     */
    public void setLstSerieInfllamda(List<CartesianSeries> lstSerieInfllamda) {
        this.lstSerieInfllamda = lstSerieInfllamda;
    }

    /**
     * @return the lstMisIndGraficollamda
     */
    public List<CartesianSeries> getLstMisIndGraficollamda() {
        return lstMisIndGraficollamda;
    }

    /**
     * @param lstMisIndGraficollamda the lstMisIndGraficollamda to set
     */
    public void setLstMisIndGraficollamda(List<CartesianSeries> lstMisIndGraficollamda) {
        this.lstMisIndGraficollamda = lstMisIndGraficollamda;
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
     * @return the renderGrafic
     */
    public boolean isRenderGrafic() {
        return renderGrafic;
    }

    /**
     * @param renderGrafic the renderGrafic to set
     */
    public void setRenderGrafic(boolean renderGrafic) {
        this.renderGrafic = renderGrafic;
    }
}
