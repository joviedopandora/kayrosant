/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.param;

import adm.sys.dao.RfTipodoc;
import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntRfTipocliente;
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
public class ClienteSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {

    }

    /**
     * Cargar lista de tipo de documento por estado
     *
     * @param pTdcEstado
     * @return
     */
    public List<RfTipodoc> getLstRfTipodoc(boolean pTdcEstado) {
        Query q = em.createNamedQuery("RfTipodoc.findByTdcEstado");
        q.setParameter("tdcEstado", pTdcEstado);
        return q.getResultList();
    }

    /**
     * Obtener id de tipo de documento
     *
     * @param tdcId
     * @return
     */
    public RfTipodoc getRfTipodocXId(String tdcId) {
        return em.getReference(RfTipodoc.class, tdcId);
    }

    /**
     * Cargar lista de tipo de clientes por estado
     *
     * @param pTclEst
     * @return
     */
    public List<VntRfTipocliente> getLstVntRfTipocliente(boolean pTclEst) {
        Query q = em.createNamedQuery("VntRfTipocliente.findByTclEst");
        q.setParameter("tclEst", pTclEst);
        return q.getResultList();
    }
    
    /**
     * Obtener id de tipo de cliente
     *
     * @param tclId
     * @return
     */
    public VntRfTipocliente getVntRfTipocliente(Integer tclId){
        return em.getReference(VntRfTipocliente.class, tclId);
    }

    /**
     * Cargar lista de clientes
     *
     * @return
     */
    public List<VntCliente> getLstAdmCliente() {
        Query q = em.createNamedQuery("VntCliente.findAll");
        return q.getResultList();
    }

    /**
     * Grabar cliente
     *
     * @param pVntCliente
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarCliente(VntCliente pVntCliente) {
        pVntCliente = em.merge(pVntCliente);
    }
}
