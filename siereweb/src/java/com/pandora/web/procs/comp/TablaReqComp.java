/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.CmpPxraprob;
import com.pandora.adm.dao.CmpRequiscomp;
import com.pandora.web.base.TablaBaseGrid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaReqComp extends TablaBaseGrid {

    private CmpRequiscomp cmpRequiscomp = new CmpRequiscomp();    
    private List<CmpPxraprob> lstCmpPxraprobsXCrq = new ArrayList<>();
    private String strObservacion;
    private Date datFechaEntrega;

    public TablaReqComp() {
    }

    public TablaReqComp(CmpRequiscomp pCmpRequiscomp) {
        this.cmpRequiscomp = pCmpRequiscomp;
    }

    /**
     * @return the cmpRequiscomp
     */
    public CmpRequiscomp getCmpRequiscomp() {
        return cmpRequiscomp;
    }

    /**
     * @param cmpRequiscomp the cmpRequiscomp to set
     */
    public void setCmpRequiscomp(CmpRequiscomp cmpRequiscomp) {
        this.cmpRequiscomp = cmpRequiscomp;
    }

    /**
     * @return the lstCmpPxraprobsXCrq
     */
    public List<CmpPxraprob> getLstCmpPxraprobsXCrq() {
        return lstCmpPxraprobsXCrq;
    }

    /**
     * @param lstCmpPxraprobsXCrq the lstCmpPxraprobsXCrq to set
     */
    public void setLstCmpPxraprobsXCrq(List<CmpPxraprob> lstCmpPxraprobsXCrq) {
        this.lstCmpPxraprobsXCrq = lstCmpPxraprobsXCrq;
    }

    /**
     * @return the strObservacion
     */
    public String getStrObservacion() {
        return strObservacion;
    }

    /**
     * @param strObservacion the strObservacion to set
     */
    public void setStrObservacion(String strObservacion) {
        this.strObservacion = strObservacion;
    }

    /**
     * @return the datFechaEntrega
     */
    public Date getDatFechaEntrega() {
        return datFechaEntrega;
    }

    /**
     * @param datFechaEntrega the datFechaEntrega to set
     */
    public void setDatFechaEntrega(Date datFechaEntrega) {
        this.datFechaEntrega = datFechaEntrega;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaReqComp other = (TablaReqComp) obj;
        if (!Objects.equals(this.cmpRequiscomp, other.cmpRequiscomp)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.cmpRequiscomp);
        return hash;
    }
}
