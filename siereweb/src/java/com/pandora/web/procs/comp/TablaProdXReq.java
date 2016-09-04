/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.dao.CmpProdxreq;
import com.pandora.adm.dao.InvInvent;
import com.pandora.adm.dao.InvMarca;
import com.pandora.adm.dao.InvPresentprod;
import com.pandora.adm.dao.InvProducto;
import com.pandora.web.base.TablaBaseGrid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.model.SelectItem;

/**
 *
 * @author luis
 */
public class TablaProdXReq extends TablaBaseGrid {

    private CmpProdxreq cmpProdxreq = new CmpProdxreq();
    private List<InvPresentprod> lstPresentprod = new ArrayList<>();
    private List<InvMarca> lstInvMarcas = new ArrayList<>();
    private Integer cantProds;
    private Integer presentSel;
    private Integer marcaSel;
//    private String observSol;
    private List<InvInvent> lstInvXProd = new ArrayList<>();
    private Long cantProdInv;
    private List<SelectItem> lstItemsPresentacionprod = new ArrayList<>();
    private List<SelectItem> lstItemsMarcas =  new ArrayList<>();
  

    public TablaProdXReq() {
        this.cmpProdxreq.setPxrObsrsol("");
        this.cmpProdxreq.setPrdId(new InvProducto());
        this.cmpProdxreq.setPspId(new InvPresentprod());
        
    }

    public TablaProdXReq(CmpProdxreq pCmpProdxreq) {
        this.cmpProdxreq = pCmpProdxreq;
    }

    public CmpProdxreq getCmpProdxreq() {
        return cmpProdxreq;
    }

    public void setCmpProdxreq(CmpProdxreq cmpProdxreq) {
        this.cmpProdxreq = cmpProdxreq;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaProdXReq other = (TablaProdXReq) obj;
        if (!Objects.equals(this.cmpProdxreq, other.cmpProdxreq)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.cmpProdxreq);
        return hash;
    }

    /**
     * @return the lstPresentprod
     */
    public List<InvPresentprod> getLstPresentprod() {
        return lstPresentprod;
    }

    /**
     * @param lstPresentprod the lstPresentprod to set
     */
    public void setLstPresentprod(List<InvPresentprod> lstPresentprod) {
        this.lstPresentprod = lstPresentprod;
    }

    /**
     * @return the lstInvMarcas
     */
    public List<InvMarca> getLstInvMarcas() {
        return lstInvMarcas;
    }

    /**
     * @param lstInvMarcas the lstInvMarcas to set
     */
    public void setLstInvMarcas(List<InvMarca> lstInvMarcas) {
        this.lstInvMarcas = lstInvMarcas;
    }

    /**
     * @return the cantProds
     */
    public Integer getCantProds() {
        return cantProds;
    }

    /**
     * @param cantProds the cantProds to set
     */
    public void setCantProds(Integer cantProds) {
        this.cantProds = cantProds;
    }

    /**
     * @return the presentSel
     */
    public Integer getPresentSel() {
        return presentSel;
    }

    /**
     * @param presentSel the presentSel to set
     */
    public void setPresentSel(Integer presentSel) {
        this.presentSel = presentSel;
    }

    /**
     * @return the marcaSel
     */
    public Integer getMarcaSel() {
        return marcaSel;
    }

    /**
     * @param marcaSel the marcaSel to set
     */
    public void setMarcaSel(Integer marcaSel) {
        this.marcaSel = marcaSel;
    }

    /**
     * @return the lstInvXProd
     */
    public List<InvInvent> getLstInvXProd() {
        return lstInvXProd;
    }

    /**
     * @param lstInvXProd the lstInvXProd to set
     */
    public void setLstInvXProd(List<InvInvent> lstInvXProd) {
        this.lstInvXProd = lstInvXProd;
    }

    /**
     * @return the cantProdInv
     */
    public Long getCantProdInv() {
        return cantProdInv;
    }

    /**
     * @param cantProdInv the cantProdInv to set
     */
    public void setCantProdInv(Long cantProdInv) {
        this.cantProdInv = cantProdInv;
    }

    /**
     * @return the lstItemsPresentacionprod
     */
    public List<SelectItem> getLstItemsPresentacionprod() {
        return lstItemsPresentacionprod;
    }

    /**
     * @param lstItemsPresentacionprod the lstItemsPresentacionprod to set
     */
    public void setLstItemsPresentacionprod(List<SelectItem> lstItemsPresentacionprod) {
        this.lstItemsPresentacionprod = lstItemsPresentacionprod;
    }

    /**
     * @return the lstItemsMarcas
     */
    public List<SelectItem> getLstItemsMarcas() {
        return lstItemsMarcas;
    }

    /**
     * @param lstItemsMarcas the lstItemsMarcas to set
     */
    public void setLstItemsMarcas(List<SelectItem> lstItemsMarcas) {
        this.lstItemsMarcas = lstItemsMarcas;
    }
}
