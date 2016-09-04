/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.liquidacion;

import com.pandora.mod.ordenprod.dao.PopCxcevento;
import com.pandora.web.base.TablaBaseGrid;
import java.math.BigDecimal;
import java.util.Objects;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Garcia Bosso
 */
public class TablaOPPendietesColaborador extends TablaBaseGrid {

    /**
     * @return the cdi
     */
    public static Long getCdi() {
        return cdi;
    }

    /**
     * @param aCdi the cdi to set
     */
    public static void setCdi(Long aCdi) {
        cdi = aCdi;
    }

    private PopCxcevento popCxcevento = null;
    private Boolean selecciona;
    private static Long cdi;

    public TablaOPPendietesColaborador() {
    }

    public TablaOPPendietesColaborador(PopCxcevento popCxcevento) {
        this.popCxcevento = popCxcevento;
    }

    public PopCxcevento getPopCxcevento() {
        return popCxcevento;
    }

    public void setPopCxcevento(PopCxcevento popCxcevento) {
        this.popCxcevento = popCxcevento;
    }

    public BigDecimal getValorTotalApgar() {
        return (popCxcevento == null ? BigDecimal.ZERO
                : popCxcevento.getCxeValorPagar().add(
                        (popCxcevento.getCxeValorBonificacion() == null ? BigDecimal.ZERO
                                : popCxcevento.getCxeValorBonificacion())
                ).subtract((popCxcevento.getCxeValorDescuento() == null ? BigDecimal.ZERO
                        : popCxcevento.getCxeValorDescuento())));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.popCxcevento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaOPPendietesColaborador other = (TablaOPPendietesColaborador) obj;
        return Objects.equals(this.popCxcevento, other.popCxcevento);
    }

    /**
     * @return the selecciona
     */
    public Boolean getSelecciona() {
        return selecciona;
    }

    public void setSeleccionar(Boolean selecciona) {
        this.selecciona = selecciona;
    }

    public void editarSelColLiquida_ValueChangeEvent(ValueChangeEvent vce) {
        selecciona = ((Boolean) vce.getNewValue());
        seleccionado = ((Boolean) vce.getNewValue());
        popCxcevento.setIndversion(selecciona ? 1 : 0);
        cdi = popCxcevento.getCxeId();
    }

    /**
     * @param selecciona the selecciona to set
     */
    public void setSelecciona(Boolean selecciona) {
        if (Objects.equals(cdi, popCxcevento.getCxeId())) {
            this.selecciona = selecciona;
        }
    }

}
