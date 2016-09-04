/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.adm.param;

import adm.sys.dao.AdmCargo;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author byrobles
 */
@Stateful
@LocalBean
public class CargoSFBean {
    
    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {

    }
    
    /**
     * Cargar lista de cargos
     *
     * @return
     */
    public List<AdmCargo> getLstAdmCargo(){
        Query q = em.createNamedQuery("AdmCargo.findAll");
        return q.getResultList();
    }
    
    /**
     * Grabar cargo
     *
     * @param pAdmCargo 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarCargo(AdmCargo pAdmCargo){
        pAdmCargo = em.merge(pAdmCargo);
    }
}
