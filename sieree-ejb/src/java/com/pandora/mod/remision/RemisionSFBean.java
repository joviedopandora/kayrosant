/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.remision;


import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.pandora.mod.venta.dao.VntRemision;

/**
 *
 * @author patricia
 */
@Stateful
@LocalBean
public class RemisionSFBean  {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {

    }

    /**
     * Cargar lista de ordenes de producción por estado procesado
     *
     * @param vrms_id
     * @return
     */
    
    public List<VntRemision> getLstRemisionConfirmada (long vrms_id) {
    Query q = em.createNamedQuery("VntRemision.regVentaConf");
    q.setParameter("vrms_id", vrms_id);
    return q.getResultList();
    }
    
    

        
}
