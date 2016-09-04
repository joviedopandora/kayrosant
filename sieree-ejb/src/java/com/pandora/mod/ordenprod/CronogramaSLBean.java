/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.ordenprod;

import com.pandora.mod.venta.dao.VntCronograma;
import com.pandora.mod.venta.dao.VntRegistroventa;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
@LocalBean
public class CronogramaSLBean implements Serializable {

    @PersistenceContext
    EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarListCronograma(List<VntCronograma> litsa) {
        for (VntCronograma cr : litsa) {
            cr = em.merge(cr);
        }

    }
    
     @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntCronograma editarCronograma(VntCronograma cr) {
        
            cr = em.merge(cr);
            return cr;
        

    }
    
   
      @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntCronograma> getLstVntCronogramaPendienteOP(Integer tipoCliente, boolean rgvtActivarOp) {
        Query q = em.createNamedQuery("VntCronograma.listaCronogramaPendienteOP");
        q.setParameter("tclId", tipoCliente);
        q.setParameter("rgvtActivarOp", rgvtActivarOp);
        return q.getResultList();
    }
    
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntCronograma> getLstVntCronogramaPendienteFactura(Long  idFactura, boolean cronogramaEstado) {
        Query q = em.createNamedQuery("VntCronograma.listaCronogramaPendienteFactura");
        q.setParameter("rgvtId", idFactura);
        q.setParameter("cronogramaEstado", cronogramaEstado);
        return q.getResultList();
    }
    
    
    
}
