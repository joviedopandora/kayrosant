/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.evaluacion;

import com.pandora.mod.evaluacion.dao.EvalBonificacionColaborador;
import com.pandora.mod.evaluacion.dao.EvalDescuentoColaborador;
import com.pandora.mod.ordenprod.dao.PopCxcevento;
import com.pandora.web.base.TablaBaseGrid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author breyner.robles
 */
public class TablaOrdenProduccionColaborador extends TablaBaseGrid {

    private PopCxcevento popCxcevento = null;
    private Integer rolSeleccionado = -1;
    private String cargoSeleccionado = "-1";
    private String nombreCargo = null;
    private String nombreRol = null;
    private String nombreEvaluacion = null;
    private Integer califacacionSeleccionado = -1;
    private BigDecimal valorBasico = BigDecimal.ZERO;
    private BigDecimal valorBonificacion = BigDecimal.ZERO;
    private BigDecimal valorDescuento = BigDecimal.ZERO;
    private BigDecimal valorPagoFinal = BigDecimal.ZERO;
    private HashMap<Integer, EvalBonificacionColaborador> mapaBonificaciones = new HashMap<>();
    private HashMap<Integer, EvalDescuentoColaborador> mapaDescuento = new HashMap<>();

    public TablaOrdenProduccionColaborador() {
    }

    public TablaOrdenProduccionColaborador(PopCxcevento popCxcevento) {
        this.popCxcevento = popCxcevento;
        if (this.popCxcevento != null) {
            if (this.popCxcevento.getPopCxcrol() != null && this.popCxcevento.getPopCxcrol().getCxrId() != null) {
                this.rolSeleccionado = this.popCxcevento.getPopCxcrol().getCxrId();
                this.nombreRol = this.popCxcevento.getPopCxcrol().getCxrRol();

            }
            if (this.popCxcevento.getCxcId() != null && this.popCxcevento.getCxcId().getCrgId().getCrgId() != null) {
                this.cargoSeleccionado = this.popCxcevento.getCxcId().getCrgId().getCrgId();
                this.nombreCargo = this.popCxcevento.getCxcId().getCrgId().getCrgNombre();
            }
            if (this.popCxcevento.getEvalCalificacion() != null) {
                this.califacacionSeleccionado = this.popCxcevento.getEvalCalificacion().getCalificacionId();
                this.nombreEvaluacion = this.popCxcevento.getEvalCalificacion().getCalificacionDesc();
            }
            if (this.popCxcevento.getAdmCargo() != null && this.popCxcevento.getAdmCargo().getCrgId() != null) {
                this.cargoSeleccionado = this.popCxcevento.getAdmCargo().getCrgId();
                this.nombreCargo = this.popCxcevento.getAdmCargo().getCrgNombre();
            }
            if (this.popCxcevento.getCxeValorPagar() != null) {
                this.valorBasico = this.popCxcevento.getCxeValorPagar();
            }
            if (this.popCxcevento.getCxeValorBonificacion() != null) {
                this.valorBonificacion = this.popCxcevento.getCxeValorBonificacion();
            }

            if (this.popCxcevento.getCxeValorDescuento() != null) {
                this.valorDescuento = this.popCxcevento.getCxeValorDescuento();
            }
            this.calcularValores();
        }
    }

    public void calcularValores() {
        this.valorPagoFinal = this.valorBasico.add(this.valorBonificacion);
        this.valorPagoFinal = this.valorPagoFinal.subtract(this.valorDescuento);

    }

    /**
     * @return the popCxcevento
     */
    public PopCxcevento getPopCxcevento() {
        return popCxcevento;
    }

    /**
     * @param popCxcevento the popCxcevento to set
     */
    public void setPopCxcevento(PopCxcevento popCxcevento) {
        this.popCxcevento = popCxcevento;
    }

    /**
     * @return the rolSeleccionado
     */
    public Integer getRolSeleccionado() {
        return rolSeleccionado;
    }

    /**
     * @param rolSeleccionado the rolSeleccionado to set
     */
    public void setRolSeleccionado(Integer rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    /**
     * @return the cargoSeleccionado
     */
    public String getCargoSeleccionado() {
        return cargoSeleccionado;
    }

    /**
     * @param cargoSeleccionado the cargoSeleccionado to set
     */
    public void setCargoSeleccionado(String cargoSeleccionado) {
        this.cargoSeleccionado = cargoSeleccionado;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public Integer getCalifacacionSeleccionado() {
        return califacacionSeleccionado;
    }

    public void setCalifacacionSeleccionado(Integer califacacionSeleccionado) {
        this.califacacionSeleccionado = califacacionSeleccionado;
    }

    public BigDecimal getValorBasico() {
        return valorBasico;
    }

    public void setValorBasico(BigDecimal valorBasico) {
        this.valorBasico = valorBasico;
    }

    public BigDecimal getValorBonificacion() {
        return valorBonificacion;
    }

    public void setValorBonificacion(BigDecimal valorBonificacion) {
        this.valorBonificacion = valorBonificacion;
    }

    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public BigDecimal getValorPagoFinal() {
        return valorPagoFinal;
    }

    public void setValorPagoFinal(BigDecimal valorPagoFinal) {
        this.valorPagoFinal = valorPagoFinal;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getNombreEvaluacion() {
        return nombreEvaluacion;
    }

    public void setNombreEvaluacion(String nombreEvaluacion) {
        this.nombreEvaluacion = nombreEvaluacion;
    }

    public HashMap<Integer, EvalBonificacionColaborador> getMapaBonificaciones() {
        return mapaBonificaciones;
    }

    public void setMapaBonificaciones(HashMap<Integer, EvalBonificacionColaborador> mapaBonificaciones) {
        this.mapaBonificaciones = mapaBonificaciones;
    }

    public HashMap<Integer, EvalDescuentoColaborador> getMapaDescuento() {
        return mapaDescuento;
    }

    public void setMapaDescuento(HashMap<Integer, EvalDescuentoColaborador> mapaDescuento) {
        this.mapaDescuento = mapaDescuento;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.popCxcevento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaOrdenProduccionColaborador other = (TablaOrdenProduccionColaborador) obj;
        if (this.popCxcevento.getCxcId() == null || other.popCxcevento.getCxcId() == null) {
            return false;
        }
        return Objects.equals(this.popCxcevento.getCxcId(), other.popCxcevento.getCxcId());
    }

}
