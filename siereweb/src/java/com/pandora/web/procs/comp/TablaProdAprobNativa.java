/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.web.base.TablaBaseGrid;

/**
 *
 * @author luis
 */
public class TablaProdAprobNativa extends TablaBaseGrid{
    private Integer prdId;
    private String prdNombre;
    private Integer pspId;
    private String pspNombre;
    private Integer marId;
    private String marNombre;
    private Long cantAprob;
    private Long cantInventario;
    private Long cantAComprar;
    private Long praId;

    /**
     * @return the prdId
     */
    public Integer getPrdId() {
        return prdId;
    }

    /**
     * @param prdId the prdId to set
     */
    public void setPrdId(Integer prdId) {
        this.prdId = prdId;
    }

    /**
     * @return the prdNombre
     */
    public String getPrdNombre() {
        return prdNombre;
    }

    /**
     * @param prdNombre the prdNombre to set
     */
    public void setPrdNombre(String prdNombre) {
        this.prdNombre = prdNombre;
    }

    /**
     * @return the pspId
     */
    public Integer getPspId() {
        return pspId;
    }

    /**
     * @param pspId the pspId to set
     */
    public void setPspId(Integer pspId) {
        this.pspId = pspId;
    }

    /**
     * @return the pspNombre
     */
    public String getPspNombre() {
        return pspNombre;
    }

    /**
     * @param pspNombre the pspNombre to set
     */
    public void setPspNombre(String pspNombre) {
        this.pspNombre = pspNombre;
    }

    /**
     * @return the marId
     */
    public Integer getMarId() {
        return marId;
    }

    /**
     * @param marId the marId to set
     */
    public void setMarId(Integer marId) {
        this.marId = marId;
    }

    /**
     * @return the marNombre
     */
    public String getMarNombre() {
        return marNombre;
    }

    /**
     * @param marNombre the marNombre to set
     */
    public void setMarNombre(String marNombre) {
        this.marNombre = marNombre;
    }

    /**
     * @return the cantAprob
     */
    public Long getCantAprob() {
        return cantAprob;
    }

    /**
     * @param cantAprob the cantAprob to set
     */
    public void setCantAprob(Long cantAprob) {
        this.cantAprob = cantAprob;
    }

    /**
     * @return the cantInventario
     */
    public Long getCantInventario() {
        return cantInventario;
    }

    /**
     * @param cantInventario the cantInventario to set
     */
    public void setCantInventario(Long cantInventario) {
        this.cantInventario = cantInventario;
    }

    /**
     * @return the cantAComprar
     */
    public Long getCantAComprar() {
        return cantAComprar;
    }

    /**
     * @param cantAComprar the cantAComprar to set
     */
    public void setCantAComprar(Long cantAComprar) {
        this.cantAComprar = cantAComprar;
    }

    /**
     * @return the praId
     */
    public Long getPraId() {
        return praId;
    }

    /**
     * @param praId the praId to set
     */
    public void setPraId(Long praId) {
        this.praId = praId;
    }
}
