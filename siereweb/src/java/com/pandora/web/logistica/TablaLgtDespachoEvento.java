/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.logistica;

import com.pandora.mod.logistica.dao.LgtDespachoevento;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author patricia
 */
public class TablaLgtDespachoEvento extends TablaBaseGrid {

    private LgtDespachoevento lgtDespachoevento = new LgtDespachoevento();

    public TablaLgtDespachoEvento() {
    }

    public TablaLgtDespachoEvento(LgtDespachoevento pLgtDespachoevento) {
        this.lgtDespachoevento = pLgtDespachoevento;
    }

    /**
     * @return the lgtDespachoevento
     */
    public LgtDespachoevento getLgtDespachoevento() {
        return lgtDespachoevento;
    }

    /**
     * @param lgtDespachoevento the lgtDespachoevento to set
     */
    public void setLgtDespachoevento(LgtDespachoevento lgtDespachoevento) {
        this.lgtDespachoevento = lgtDespachoevento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.lgtDespachoevento);
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
        final TablaLgtDespachoEvento other = (TablaLgtDespachoEvento) obj;
        if (!Objects.equals(this.lgtDespachoevento, other.lgtDespachoevento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaLgtDespachoEvento{" + "lgtDespachoevento=" + lgtDespachoevento + '}';
    }
}
