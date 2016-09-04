/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.jsfbeans;

import adm.sys.bean.DatosGeneralesSLBean;
import adm.sys.dao.AdmTipopagoxcol;
import com.pandora.adm.rf.dao.RfDep;
import com.pandora.bussiness.util.EnTipoPagoNomina;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author luis
 */
@ApplicationScoped
@Named
public class AppBean {

    @EJB
    DatosGeneralesSLBean dgslb;
    
     private AdmTipopagoxcol tipoPagoEfectivo ;
    private AdmTipopagoxcol tipoPagoTransferencia ;
    private AdmTipopagoxcol tipoPagoCheque ;
   

    public DatosGeneralesSLBean getDgslb() {
        return dgslb;
    }

   private List<SelectItem> lstItemsDepartamento = new ArrayList<>();

   

    @PostConstruct
    public void init() {
        cargarDeps();
        cargarTiposPago();
    }

    public void cargarDeps() {
        lstItemsDepartamento.clear();
        lstItemsDepartamento.add(new SelectItem("-1", "Seleccione>>"));
        for (RfDep rd : dgslb.getLstDeps()) {
            lstItemsDepartamento.add(new SelectItem(rd.getDepId(), rd.getDepDesc()));
        }
    }
    
    private void cargarTiposPago(){
        tipoPagoTransferencia = dgslb.getTipoPago(EnTipoPagoNomina.TRANSFERENCIA.getId());
        tipoPagoEfectivo = dgslb.getTipoPago(EnTipoPagoNomina.EFECTIVO.getId());
        tipoPagoCheque = dgslb.getTipoPago(EnTipoPagoNomina.CHEQUE.getId());
    }
    
     public List<SelectItem> getLstItemsDepartamento() {
        return lstItemsDepartamento;
    }

    public void setLstItemsDepartamento(List<SelectItem> lstItemsDepartamento) {
        this.lstItemsDepartamento = lstItemsDepartamento;
    }

    public AdmTipopagoxcol getTipoPagoEfectivo() {
        return tipoPagoEfectivo;
    }

    public void setTipoPagoEfectivo(AdmTipopagoxcol tipoPagoEfectivo) {
        this.tipoPagoEfectivo = tipoPagoEfectivo;
    }

    public AdmTipopagoxcol getTipoPagoTransferencia() {
        return tipoPagoTransferencia;
    }

    public void setTipoPagoTransferencia(AdmTipopagoxcol tipoPagoTransferencia) {
        this.tipoPagoTransferencia = tipoPagoTransferencia;
    }

    public AdmTipopagoxcol getTipoPagoCheque() {
        return tipoPagoCheque;
    }

    public void setTipoPagoCheque(AdmTipopagoxcol tipoPagoCheque) {
        this.tipoPagoCheque = tipoPagoCheque;
    }
    
    
}
