/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

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
public class TablaProducto extends TablaBaseGrid {
   

    private InvProducto invProducto = new InvProducto();
    private Integer cantProds;
    private Integer presentSel;
    private Integer marcaSel;
    private List<SelectItem> lstItemsPresentXProd = new ArrayList<>();
    private List<SelectItem> lstItemsMarcaProds = new ArrayList<>();
    private InvMarca objMarcaSel = new InvMarca();
    private InvPresentprod objInvPresentprod = new InvPresentprod();

    private List<InvPresentprod> lstPresentprod = new ArrayList<>();
    private List<InvMarca> lstInvMarcas = new ArrayList<>();
    public TablaProducto() {
    }

    private SelectItem itemPresentSel = new SelectItem();
    public TablaProducto(InvProducto pInvProducto) {
        this.invProducto = pInvProducto;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaProducto other = (TablaProducto) obj;
        if (!Objects.equals(this.invProducto, other.invProducto)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.invProducto);
        return hash;
    }

    /**
     * @return the invProducto
     */
    public InvProducto getInvProducto() {
        return invProducto;
    }

    /**
     * @param invProducto the invProducto to set
     */
    public void setInvProducto(InvProducto invProducto) {
        this.invProducto = invProducto;
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
     * @return the lstItemsPresentXProd
     */
    public List<SelectItem> getLstItemsPresentXProd() {
        return lstItemsPresentXProd;
    }

    /**
     * @param lstItemsPresentXProd the lstItemsPresentXProd to set
     */
    public void setLstItemsPresentXProd(List<SelectItem> lstItemsPresentXProd) {
        this.lstItemsPresentXProd = lstItemsPresentXProd;
    }

    /**
     * @return the lstItemsMarcaProds
     */
    public List<SelectItem> getLstItemsMarcaProds() {
        return lstItemsMarcaProds;
    }

    /**
     * @param lstItemsMarcaProds the lstItemsMarcaProds to set
     */
    public void setLstItemsMarcaProds(List<SelectItem> lstItemsMarcaProds) {
        this.lstItemsMarcaProds = lstItemsMarcaProds;
    }

    /**
     * @return the objMarcaSel
     */
    public InvMarca getObjMarcaSel() {
        return objMarcaSel;
    }

    /**
     * @param objMarcaSel the objMarcaSel to set
     */
    public void setObjMarcaSel(InvMarca objMarcaSel) {
        this.objMarcaSel = objMarcaSel;
    }

    /**
     * @return the objInvPresentprod
     */
    public InvPresentprod getObjInvPresentprod() {
        return objInvPresentprod;
    }

    /**
     * @param objInvPresentprod the objInvPresentprod to set
     */
    public void setObjInvPresentprod(InvPresentprod objInvPresentprod) {
        this.objInvPresentprod = objInvPresentprod;
    }

    /**
     * @return the itemPresentSel
     */
    public SelectItem getItemPresentSel() {
        return itemPresentSel;
    }

    /**
     * @param itemPresentSel the itemPresentSel to set
     */
    public void setItemPresentSel(SelectItem itemPresentSel) {
        this.itemPresentSel = itemPresentSel;
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

    
}
