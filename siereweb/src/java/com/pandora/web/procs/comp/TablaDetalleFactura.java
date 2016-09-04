/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.CmpDetallefact;
import com.pandora.web.base.TablaBaseGrid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaDetalleFactura extends TablaBaseGrid{
    
    private CmpDetallefact cmpDetallefact = new CmpDetallefact();
    private BigDecimal bdValorTotalDetFact =  new BigDecimal(BigInteger.ZERO);

    public TablaDetalleFactura() {
    }
    
    public TablaDetalleFactura(CmpDetallefact pCmpDetallefact){
        this.cmpDetallefact = pCmpDetallefact;
    }

    /**
     * @return the cmpDetallefact
     */
    public CmpDetallefact getCmpDetallefact() {
        return cmpDetallefact;
    }

    /**
     * @param cmpDetallefact the cmpDetallefact to set
     */
    public void setCmpDetallefact(CmpDetallefact cmpDetallefact) {
        this.cmpDetallefact = cmpDetallefact;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.cmpDetallefact);
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
        final TablaDetalleFactura other = (TablaDetalleFactura) obj;
        if (!Objects.equals(this.cmpDetallefact, other.cmpDetallefact)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaDetalleFactura{" + "cmpDetallefact=" + cmpDetallefact + '}';
    }

    /**
     * @return the bdValorTotalDetFact
     */
    public BigDecimal getBdValorTotalDetFact() {
        return bdValorTotalDetFact;
    }

    /**
     * @param bdValorTotalDetFact the bdValorTotalDetFact to set
     */
    public void setBdValorTotalDetFact(BigDecimal bdValorTotalDetFact) {
        this.bdValorTotalDetFact = bdValorTotalDetFact;
    }
}
