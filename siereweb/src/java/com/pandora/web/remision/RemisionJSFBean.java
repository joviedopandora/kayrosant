/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.remision;

import com.pandora.jsfbeans.PrincipalJSFBean;
import com.pandora.mod.logistica.LogisticaSFBean;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.web.base.BaseJSFBean;
import com.pandora.web.base.IPasos;

import com.pandora.web.venta.TablaVntRegistroventa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

/**
 *
 * @author sandra
 */
@Named
@SessionScoped
public class RemisionJSFBean extends BaseJSFBean implements Serializable, IPasos {

    @Inject
    PrincipalJSFBean pjsfb;
    private LogisticaSFBean lsfb;
    private Integer valorRadioconsulta = 1;
    private final List<tablaremision> lstTablaremision = new ArrayList<>();
    private Object em;
    
    
     /**
     * Cargar las  remisiones po id
     public List<vnt_detallerem> getLstVntRemision(long vrms_id) {
        Query q = em.createNamedQuery("VntDetallerem.findAll");
        q.setParameter("vrms_id", vrms_id);
        return q.getResultList();
     * @param vrms_id
     * @return
     *  
    }
     */
    
    
   
            

    /**
     *private String factcl_apellidos;
    private String factcl_tipodocuemento;
    private String factcl_identificacion;
    private String factcl_diereccion;
    private Integer factcl_celular;
    private Integer factcl_fijo;
    private String factcl_email;
    private String  factcl_formapago;
    private String factsr_detalle;
    private String actsr_protagonista;
    private Date factsr_fecha;
    private String factsr_direccionevento;
    private String factsr_motivo;
    private Date  factsr_hora;
    private String factdc_descripcion;
    private String  factdc_observacion;
    private int vdft_id;
    private boolean vdft_estado;
    private char  vfct_id;
    private char  vdft_servico;
    private int fvdft_cantidad;
    private int vdft_costounitario;
    private int vdft_costototal;
    private int vdft_descuento;
    private int vdft_subtotal;
    private int vdft_iva;
    private int  dft_ivaporcerntaje;
    private int  vdft_total;
    private int vdft_valor_anticipo;
    private int  vdft_saldo;
 
     */
    public RemisionJSFBean() {
    }

    @Override
    public void init() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiarVariables() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscarGen_ActionEvent(ActionEvent ae) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void navLateral_ActionEvent(ActionEvent ae) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean grabarPaso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validarForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void navegacionLateral_ActionEvent(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the valorRadioconsulta
     */
    public Integer getValorRadioconsulta() {
        return valorRadioconsulta;
    }

    /**
     * @param valorRadioconsulta the valorRadioconsulta to set
     */
    public void setValorRadioconsulta(Integer valorRadioconsulta) {
        this.valorRadioconsulta = valorRadioconsulta;
    }

    private static class vnt_remision {

        public vnt_remision() {
        }
    }

}



    //<editor-fold defaultstate="collapsed" desc="Variables y constantes">
    

    //</editor-fold>


//<editor-fold defaultstate="collapsed" desc="metodos del bena">
    

    //</editor-fold>


//<editor-fold defaultstate="collapsed" desc="Procedimientos y funciones">


