/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procs.comp;

import com.pandora.adm.cmp.CmpSolReqSFBean;
import com.pandora.adm.dao.*;
import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;
import com.pandora.web.procs.comp.cualif.CompSolReqQLF;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.NoSuchEJBException;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author luis
 */
@Named
@CompSolReqQLF
public class CompSolReqJSFBean extends BaseJSFBean implements IPasos {

    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    CmpSolReqSFBean csrsfb;
    @Inject
    PrincipalJSFBean pjsfb;

    private CmpSolReqSFBean lookupCmpSolReqSFBeanBean() {
        try {
            Context c = new InitialContext();
            return (CmpSolReqSFBean) c.lookup("java:global/SIEREE/SIEREE-ejb/CmpSolReqSFBean!com.pandora.adm.cmp.CmpSolReqSFBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    protected List<InvPresentprod> lstPresentprods = new ArrayList<>();
    protected List<InvMarca> lstInvMarcas = new ArrayList<>();
    protected List<InvCatprod> lstInvCatprods = new ArrayList<>();
    protected String txtBuscaProd;
    protected List<TablaProducto> lstTablaPordExist = new ArrayList<>();
    protected List<TablaProdXReq> lstTablaProdSel = new ArrayList<>();
    protected List<TablaProdXReq> lstTablaProdUnico = new ArrayList<>();
    protected TablaProdXReq tablaProdXReqSel = new TablaProdXReq();
    protected boolean blnSelTodoProdExt;
    protected boolean blnSelTodProdSel;
    protected CmpRequiscomp requiscompXCol = new CmpRequiscomp();
    private boolean prdUnico = false;
    private String beanNavProc;
    private String beanAnt = "";
    private String claseNAvLateral = "btnNavLateral";
    protected Integer intInvCatProdSel;
    private Long idIpsSede = -1L;
    private List<SelectItem> lstIpsSede = new ArrayList<>();
    private List<SelectItem> lstItemsInvCatProd = new ArrayList<>();
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos del Bean">

    @Override
    public void init() {
        csrsfb = lookupCmpSolReqSFBeanBean();
        lstTablaPordExist.clear();
        lstTablaProdSel.clear();
        cargarSolicExist();
        cargarCatProducto();
        lstInvMarcas = csrsfb.getLstMarcas();
        lstPresentprods = csrsfb.getLstPresentprods();
//        lstInvCatprods = csrsfb.getLstCatprods();
        intInvCatProdSel = -1;
        idIpsSede = -1L;
    }

    public void cargarCatProducto() {
        lstItemsInvCatProd.clear();
        lstItemsInvCatProd.add(itemSeleccioneInt);
        for (InvCatprod ic : csrsfb.getLstCatprods()) {
            lstItemsInvCatProd.add(new SelectItem(ic.getCpdId(), ic.getCpdNombre()));
        }
    }

    @Override
    public void limpiarVariables() {
        lstTablaPordExist.clear();
        lstTablaProdSel.clear();
        lstTablaPordExist.clear();
        lstTablaProdSel.clear();
        intInvCatProdSel = -1;
        idIpsSede = -1L;

        try {
            csrsfb.remove();
        } catch (NoSuchEJBException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "No se encuentra el SFBean CmpSolReqSFBean", ex);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">

    private void cargarLstProdsDsip() {
        lstTablaPordExist.clear();
        for (InvProducto invProducto : csrsfb.getLstProdXText(eliminarSE(txtBuscaProd), prdUnico)) {
            TablaProducto tp = new TablaProducto(invProducto);
            tp.getLstItemsMarcaProds().clear();
            tp.getLstItemsPresentXProd().clear();
            tp.getLstInvMarcas().clear();
            tp.getLstPresentprod().clear();

            tp.getLstItemsPresentXProd().add(new SelectItem(itemSeleccioneInt));
            for (InvPresntxprod presntxprod : csrsfb.getLstPresntxprod(tp.getInvProducto().getPrdId(), Boolean.TRUE)) {
                tp.getLstItemsPresentXProd().add(new SelectItem(presntxprod.getPspId().getPspId(), presntxprod.getPspId().getPspNombre()));
            }

            tp.getLstItemsMarcaProds().add(new SelectItem(itemSeleccioneInt));
            for (InvMarcxprod marcxprod : csrsfb.getLstMarcxprod(tp.getInvProducto().getPrdId(), Boolean.TRUE)) {
                tp.getLstItemsMarcaProds().add(new SelectItem(marcxprod.getMarId().getMarId(), marcxprod.getMarId().getMarNombre()));
            }

            lstTablaPordExist.add(tp);
        }
    }

    private void cargarSolicExist() {
        if (csrsfb.getCmpRequisXCol(pjsfb.getMssfb().getCrgxcolActual().getCxcId(), true)) {
            requiscompXCol = csrsfb.getCmpRequiscomp();
            lstTablaProdSel.clear();
            for (CmpProdxreq prodxreq : requiscompXCol.getCmpProdxreqList()) {
                TablaProdXReq tablaProdXReq = new TablaProdXReq(prodxreq);

                tablaProdXReq.getLstItemsPresentacionprod().add(new SelectItem(itemSeleccioneInt));
                for (InvPresntxprod presntxprod : csrsfb.getLstPresntxprod(tablaProdXReq.getCmpProdxreq().getPrdId().getPrdId(), Boolean.TRUE)) {
                    tablaProdXReq.getLstItemsPresentacionprod().add(new SelectItem(presntxprod.getPspId().getPspId(), presntxprod.getPspId().getPspNombre()));
                }

                tablaProdXReq.getLstItemsMarcas().add(itemSeleccioneInt);
                for (InvMarcxprod marcxprod : csrsfb.getLstMarcxprod(tablaProdXReq.getCmpProdxreq().getPrdId().getPrdId(), Boolean.TRUE)) {
                    tablaProdXReq.getLstItemsMarcas().add(new SelectItem(new SelectItem(marcxprod.getMarId().getMarId(), marcxprod.getMarId().getMarNombre())));
                }
                tablaProdXReq.setPresentSel(prodxreq.getPspId().getPspId());
                tablaProdXReq.setCantProds(prodxreq.getPxrCantsol());

                lstTablaProdSel.add(tablaProdXReq);
            }
        }
    }

    public void ddlCatProd_ValueChange(ValueChangeEvent vce) {
        intInvCatProdSel = (Integer) vce.getNewValue();
        lstTablaPordExist.clear();
        for (InvProducto invProducto : csrsfb.getLstProdXCat(intInvCatProdSel, prdUnico, true)) {
            TablaProducto tablaProd = new TablaProducto(invProducto);
            lstTablaPordExist.add(tablaProd);
            tablaProd.getLstItemsMarcaProds().clear();
            tablaProd.getLstItemsPresentXProd().clear();
            tablaProd.getLstInvMarcas().clear();
            tablaProd.getLstPresentprod().clear();
            for (InvPresntxprod presntxprod : csrsfb.getLstPresntxprod(tablaProd.getInvProducto().getPrdId(), Boolean.TRUE)) {
                tablaProd.getLstPresentprod().add(presntxprod.getPspId());
            }
            for (InvMarcxprod marcxprod : csrsfb.getLstMarcxprod(tablaProd.getInvProducto().getPrdId(), Boolean.TRUE)) {
                tablaProd.getLstInvMarcas().add(marcxprod.getMarId());
            }
        }
    }

    //</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="Eventos">    
    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        lstTablaPordExist.clear();
//        try {
//            beanNavProc = (String) ae.getComponent().getAttributes().get("jsfBean");
        numPanel = Integer.valueOf((String) ae.getComponent().getAttributes().get("numPanel"));
        if (numPanel == 1) {
            prdUnico = false;
        } else {
            prdUnico = true;
        }
    }
    
    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addProdUnico() {
        List<TablaProducto> lstProds = new ArrayList<>();
        for (TablaProdXReq tpxr : lstTablaProdUnico) {
            TablaProducto tp = new TablaProducto(tpxr.getCmpProdxreq().getPrdId());
            lstProds.add(tp);
        }
        for (TablaProducto tablaProd : lstTablaPordExist) {
            if (tablaProd.isSeleccionado()) {
                if (!lstProds.contains(tablaProd)) {
                    tablaProd.setSeleccionado(false);
                    tablaProd.setPresentSel(-1);
                    tablaProd.setCantProds(0);
                    TablaProdXReq tablaProdXReq = new TablaProdXReq();
                    tablaProdXReq.getCmpProdxreq().setPrdId(tablaProd.getInvProducto());
                    tablaProdXReq.setLstPresentprod(tablaProd.getLstPresentprod());
                    lstTablaProdUnico.add(tablaProdXReq);
                }
            }

        }
        blnSelTodoProdExt = false;
        selTodoLst(lstTablaPordExist, blnSelTodoProdExt);
    }

    public void btnAddProd_ActionEvent() {
        if (numPanel == 1) {
            List<TablaProducto> lstTablaProds = new ArrayList<>();
            for (TablaProdXReq tablaProdXReq : lstTablaProdSel) {
                TablaProducto tablaProd = new TablaProducto(tablaProdXReq.getCmpProdxreq().getPrdId());
                lstTablaProds.add(tablaProd);
            }
            for (TablaProducto tablaProd : lstTablaPordExist) {
                if (tablaProd.isSeleccionado()) {
                    if (!lstTablaProds.contains(tablaProd)) {
                        tablaProd.setSeleccionado(false);
                        tablaProd.setPresentSel(-1);
                        tablaProd.setCantProds(0);
                        TablaProdXReq tablaProdXReq = new TablaProdXReq();
                        tablaProdXReq.getCmpProdxreq().setPrdId(tablaProd.getInvProducto());

                        tablaProdXReq.getLstItemsPresentacionprod().add(itemSeleccioneInt);
                        for (InvPresntxprod presntxprod : csrsfb.getLstPresntxprod(tablaProdXReq.getCmpProdxreq().getPrdId().getPrdId(), Boolean.TRUE)) {
                            tablaProdXReq.getLstItemsPresentacionprod().add(new SelectItem(presntxprod.getPspId().getPspId(), presntxprod.getPspId().getPspNombre()));
                        }
//                        tablaProdXReq.setLstPresentprod(tablaProd.getLstPresentprod());
                        lstTablaProdSel.add(tablaProdXReq);
                    }
                }
            }
            blnSelTodoProdExt = false;
            selTodoLst(lstTablaPordExist, blnSelTodoProdExt);
        } else {
            addProdUnico();
        }
    }

    public void btnBorrarProdSel() {
        List<TablaProdXReq> lstProdBorrar = new ArrayList<>();
        for (TablaProdXReq tablaProdXreq : lstTablaProdSel) {
            if (tablaProdXreq.isSeleccionado()) {
                lstProdBorrar.add(tablaProdXreq);
            }
        }
        lstTablaProdSel.removeAll(lstProdBorrar);
        List<CmpProdxreq> lstCmpProdxreq = new ArrayList<>();
        if (requiscompXCol.getCrqId() != null) {
            for (TablaProdXReq tpxr : lstProdBorrar) {
                if (tpxr.getCmpProdxreq().getPxrId() != null) {
                    lstCmpProdxreq.add(tpxr.getCmpProdxreq());
                }
            }
        }
        csrsfb.borrarProdXReq(lstCmpProdxreq);
    }

    public void grabarSolicitud_Action(ActionEvent ae) {
        if (grabarPaso()) {
            mostrarError("Grabación exitosa...!", 3);
//            resumenMsg = "Solicitud";
//            mensaje = "Solicitud grabada correctamente con ID " + csrsfb.getCmpRequiscomp().getCrqId();
//            cargarMsg(FacesMessage.SEVERITY_INFO);

        }
    }

    public void btnBuscarProdXText_ActionEvent() {
    }

    public void selTodoProdEnc(ValueChangeEvent vce) {
        blnSelTodoProdExt = (boolean) vce.getNewValue();
        selTodoLst(lstTablaPordExist, blnSelTodoProdExt);
    }

    public void selTodoProdSel(ValueChangeEvent vce) {
        blnSelTodProdSel = (boolean) vce.getNewValue();
        selTodoLst(lstTablaProdSel, blnSelTodProdSel);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Funciones heredadas">

    @Override
    public boolean validarForm() {
        for (TablaProdXReq tablaProdXReq : lstTablaProdSel) {
            if (tablaProdXReq.getCantProds().intValue() <= 0 || tablaProdXReq.getPresentSel().equals(-1)) {
                mensaje = "Presentación y cantidad necesarios";
                resumenMsg = "Error de validación";
                cargarMsg(FacesMessage.SEVERITY_ERROR);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean grabarPaso() {
        if (validarForm()) {
            requiscompXCol.setCqrEst(Boolean.TRUE);
            requiscompXCol.setCrqFproc(new Date());
            requiscompXCol.setCrqRevisada(Boolean.FALSE);
            requiscompXCol.setCrqAbierta(Boolean.TRUE);
            requiscompXCol.setCxcId(pjsfb.getMssfb().getCrgxcolActual());
            //  requiscompXCol.setStrId(pjsfb.getMssfbl().getSysSegtareaActual().getStrId().getStrId());

            List<CmpProdxreq> lstProdxreqs = new ArrayList<>();
            for (TablaProdXReq tablaProdXReq : lstTablaProdSel) {
                CmpProdxreq cmpProdxreq = tablaProdXReq.getCmpProdxreq();
                if (cmpProdxreq.getPxrId() == null) {
                    cmpProdxreq.setPrdId(tablaProdXReq.getCmpProdxreq().getPrdId());
                    cmpProdxreq.setCrqId(requiscompXCol);
                }
                cmpProdxreq.setPxrCantsol(tablaProdXReq.getCantProds());
                cmpProdxreq.setPspId(new InvPresentprod(tablaProdXReq.getPresentSel()));
                cmpProdxreq.setPxrRechaza(Boolean.FALSE);
                cmpProdxreq.setIndversion(0);
                lstProdxreqs.add(cmpProdxreq);
            }
            if (requiscompXCol.getCrqId() == null) {
                requiscompXCol.setCmpProdxreqList(lstProdxreqs);
                csrsfb.grabarRequiscomp(requiscompXCol);
                if (csrsfb.getCmpRequiscomp().getCrqId() != null) {
                    return true;
                } else {
                    return false;
                }
            } else {
                csrsfb.editarReqComp(lstProdxreqs);
                cargarSolicExist();
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        cargarLstProdsDsip();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Propiedades">

    /**
     * @return the lstTablaPordExist
     */
    public List<TablaProducto> getLstTablaPordExist() {
        return lstTablaPordExist;
    }

    /**
     * @param lstTablaPordExist the lstTablaPordExist to set
     */
    public void setLstTablaPordExist(List<TablaProducto> lstTablaPordExist) {
        this.lstTablaPordExist = lstTablaPordExist;
    }

    /**
     * @return the txtBuscaProd
     */
    public String getTxtBuscaProd() {
        return txtBuscaProd;
    }

    /**
     * @param txtBuscaProd the txtBuscaProd to set
     */
    public void setTxtBuscaProd(String txtBuscaProd) {
        this.txtBuscaProd = txtBuscaProd;
    }

    /**
     * @return the lstTablaProdSel
     */
    public List<TablaProdXReq> getLstTablaProdSel() {
        return lstTablaProdSel;
    }

    /**
     * @param lstTablaProdSel the lstTablaProdSel to set
     */
    public void setLstTablaProdSel(List<TablaProdXReq> lstTablaProdSel) {
        this.lstTablaProdSel = lstTablaProdSel;
    }

    /**
     * @return the lstPresentprods
     */
    public List<InvPresentprod> getLstPresentprods() {
        return lstPresentprods;
    }

    /**
     * @param lstPresentprods the lstPresentprods to set
     */
    public void setLstPresentprods(List<InvPresentprod> lstPresentprods) {
        this.lstPresentprods = lstPresentprods;
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
     * @return the blnSelTodoProdExt
     */
    public boolean isBlnSelTodoProdExt() {
        return blnSelTodoProdExt;
    }

    /**
     * @param blnSelTodoProdExt the blnSelTodoProdExt to set
     */
    public void setBlnSelTodoProdExt(boolean blnSelTodoProdExt) {
        this.blnSelTodoProdExt = blnSelTodoProdExt;
    }

    /**
     * @return the blnSelTodProdSel
     */
    public boolean isBlnSelTodProdSel() {
        return blnSelTodProdSel;
    }

    /**
     * @param blnSelTodProdSel the blnSelTodProdSel to set
     */
    public void setBlnSelTodProdSel(boolean blnSelTodProdSel) {
        this.blnSelTodProdSel = blnSelTodProdSel;
    }

    /**
     * @return the tablaProdXReqSel
     */
    public TablaProdXReq getTablaProdXReqSel() {
        return tablaProdXReqSel;
    }

    /**
     * @param tablaProdXReqSel the tablaProdXReqSel to set
     */
    public void setTablaProdXReqSel(TablaProdXReq tablaProdXReqSel) {
        this.tablaProdXReqSel = tablaProdXReqSel;
    }

    /**
     * @return the beanNavProc
     */
    public String getBeanNavProc() {
        return beanNavProc;
    }

    /**
     * @param beanNavProc the beanNavProc to set
     */
    public void setBeanNavProc(String beanNavProc) {
        this.beanNavProc = beanNavProc;
    }

    /**
     * @return the claseNAvLateral
     */
    public String getClaseNAvLateral() {
        return claseNAvLateral;
    }

    /**
     * @param claseNAvLateral the claseNAvLateral to set
     */
    public void setClaseNAvLateral(String claseNAvLateral) {
        this.claseNAvLateral = claseNAvLateral;
    }

    /**
     * @return the beanAnt
     */
    public String getBeanAnt() {
        return beanAnt;
    }

    /**
     * @param beanAnt the beanAnt to set
     */
    public void setBeanAnt(String beanAnt) {
        this.beanAnt = beanAnt;
    }

    /**
     * @return the prdUnico
     */
    public boolean isPrdUnico() {
        return prdUnico;
    }

    /**
     * @param prdUnico the prdUnico to set
     */
    public void setPrdUnico(boolean prdUnico) {
        this.prdUnico = prdUnico;
    }

    /**
     * @return the lstInvCatprods
     */
    public List<InvCatprod> getLstInvCatprods() {
        return lstInvCatprods;
    }

    /**
     * @param lstInvCatprods the lstInvCatprods to set
     */
    public void setLstInvCatprods(List<InvCatprod> lstInvCatprods) {
        this.lstInvCatprods = lstInvCatprods;
    }

    public Integer getIntInvCatProdSel() {
        return intInvCatProdSel;
    }

    public void setIntInvCatProdSel(Integer intInvCatProdSel) {
        this.intInvCatProdSel = intInvCatProdSel;
    }

    /**
     * @return the lstTablaProdUnico
     */
    public List<TablaProdXReq> getLstTablaProdUnico() {
        return lstTablaProdUnico;
    }

    /**
     * @param lstTablaProdUnico the lstTablaProdUnico to set
     */
    public void setLstTablaProdUnico(List<TablaProdXReq> lstTablaProdUnico) {
        this.lstTablaProdUnico = lstTablaProdUnico;
    }

    /**
     * @return the idIpsSede
     */
    public Long getIdIpsSede() {
        return idIpsSede;
    }

    /**
     * @param idIpsSede the idIpsSede to set
     */
    public void setIdIpsSede(Long idIpsSede) {
        this.idIpsSede = idIpsSede;
    }

    /**
     * @return the lstIpsSede
     */
    public List<SelectItem> getLstIpsSede() {
        return lstIpsSede;
    }

    /**
     * @param lstIpsSede the lstIpsSede to set
     */
    public void setLstIpsSede(List<SelectItem> lstIpsSede) {
        this.lstIpsSede = lstIpsSede;
    }
    
    /**
     * @return the lstItemsInvCatProd
     */
    public List<SelectItem> getLstItemsInvCatProd() {
        return lstItemsInvCatProd;
    }

    /**
     * @param lstItemsInvCatProd the lstItemsInvCatProd to set
     */
    public void setLstItemsInvCatProd(List<SelectItem> lstItemsInvCatProd) {
        this.lstItemsInvCatProd = lstItemsInvCatProd;
    }
    //</editor-fold>
}
