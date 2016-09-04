/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.web.ordenprod;

import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author sandra
 */
public class TablaPopProdXServXOp extends TablaBaseGrid{
    
    private PopProdxservxop popProdxservxop = new PopProdxservxop();
    private Integer cantSalida;
    private Integer cantEntrada;
    private boolean estSalida = false;
    private boolean estEntrada;
    private String observSalida;
    private String observEntrada;

    public TablaPopProdXServXOp() {
    }
    
    public TablaPopProdXServXOp(PopProdxservxop pPopProdxservxop){
        this.popProdxservxop = pPopProdxservxop;
    }

    /**
     * @return the popProdxservxop
     */
    public PopProdxservxop getPopProdxservxop() {
        return popProdxservxop;
    }

    /**
     * @param popProdxservxop the popProdxservxop to set
     */
    public void setPopProdxservxop(PopProdxservxop popProdxservxop) {
        this.popProdxservxop = popProdxservxop;
    }

    /**
     * @return the cantSalida
     */
    public Integer getCantSalida() {
        return cantSalida;
    }

    /**
     * @param cantSalida the cantSalida to set
     */
    public void setCantSalida(Integer cantSalida) {
        this.cantSalida = cantSalida;
    }

    /**
     * @return the cantEntrada
     */
    public Integer getCantEntrada() {
        return cantEntrada;
    }

    /**
     * @param cantEntrada the cantEntrada to set
     */
    public void setCantEntrada(Integer cantEntrada) {
        this.cantEntrada = cantEntrada;
    }

    /**
     * @return the estSalida
     */
    public boolean isEstSalida() {
        return estSalida;
    }

    /**
     * @param estSalida the estSalida to set
     */
    public void setEstSalida(boolean estSalida) {
        this.estSalida = estSalida;
    }

    /**
     * @return the estEntrada
     */
    public boolean isEstEntrada() {
        return estEntrada;
    }

    /**
     * @param estEntrada the estEntrada to set
     */
    public void setEstEntrada(boolean estEntrada) {
        this.estEntrada = estEntrada;
    }

    /**
     * @return the observSalida
     */
    public String getObservSalida() {
        return observSalida;
    }

    /**
     * @param observSalida the observSalida to set
     */
    public void setObservSalida(String observSalida) {
        this.observSalida = observSalida;
    }

    /**
     * @return the observEntrada
     */
    public String getObservEntrada() {
        return observEntrada;
    }

    /**
     * @param observEntrada the observEntrada to set
     */
    public void setObservEntrada(String observEntrada) {
        this.observEntrada = observEntrada;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.popProdxservxop);
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
        final TablaPopProdXServXOp other = (TablaPopProdXServXOp) obj;
        if (!Objects.equals(this.popProdxservxop, other.popProdxservxop)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaPopProdXServXOp{" + "popProdxservxop=" + popProdxservxop + '}';
    }
}
