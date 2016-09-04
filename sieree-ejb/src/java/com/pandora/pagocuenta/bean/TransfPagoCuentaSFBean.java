/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.bean;

import com.pandora.pagocuenta.dao.FinTransferencia;
import com.pandora.pagocuenta.dao.RfBanco;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateful
@LocalBean
public class TransfPagoCuentaSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }

    //Grabar transferencia
    public void grabarTransferencia(FinTransferencia pFinTransferencia) {
        pFinTransferencia = em.merge(pFinTransferencia);
    }

    //Obtener lista de transferencias
    public List<FinTransferencia> getLstFinTransferencia() {
        Query q = em.createNamedQuery("FinTransferencia.findAll");
        return q.getResultList();
    }

    //Obtener lista de bancos por estado
    public List<RfBanco> getLstRfBancoXEstado(boolean bncEstado) {
        Query q = em.createNamedQuery("RfBanco.findByBncEstado");
        q.setParameter("bncEstado", bncEstado);
        return q.getResultList();
    }

    //Obtener banco por id
    public RfBanco getRfBancoXId(Long bncId) {
        return em.getReference(RfBanco.class, bncId);
    }
}
