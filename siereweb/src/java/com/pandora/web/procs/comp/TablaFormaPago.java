/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.pagocuenta.dao.FinFormapago;
import com.pandora.web.base.TablaBaseGrid;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author carlos
 */
public class TablaFormaPago extends TablaBaseGrid {

    private FinFormapago finFormapago = new FinFormapago();
    private BigDecimal valor;

    public TablaFormaPago() {
    }

    public TablaFormaPago(FinFormapago pFinFormapago) {
        this.finFormapago = pFinFormapago;
    }

    /**
     * @return the finFormapago
     */
    public FinFormapago getFinFormapago() {
        return finFormapago;
    }

    /**
     * @param finFormapago the finFormapago to set
     */
    public void setFinFormapago(FinFormapago finFormapago) {
        this.finFormapago = finFormapago;
    }

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaFormaPago other = (TablaFormaPago) obj;
        if (!Objects.equals(this.finFormapago, other.finFormapago)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.finFormapago);
        return hash;
    }
}
