/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.bean;

import com.pandora.pagocuenta.dao.FinCronogramapago;
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
public class CronoPagoCuentaSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }

    //Obtener lista de cronograma pago
    public List<FinCronogramapago> getLstFinCronogramapago() {
        Query q = em.createNamedQuery("FinCronogramapago.findAll");
        return q.getResultList();
    }

    //Obtener lista de bancos
    public List<RfBanco> getLstRfBancoXEstado(boolean bncEstado) {
        Query q = em.createNamedQuery("RfBanco.findByBncEstado");
        q.setParameter("bncEstado", bncEstado);
        return q.getResultList();
    }
    
    //Obtener banco por id
    public RfBanco getRfBancoXId(Long bncId) {
        return em.getReference(RfBanco.class, bncId);
    }

    //Grabar cronograma de pago
    public void grabarFinCronogramapagoYTransferencia(FinCronogramapago pFinCronogramapago) {
        em.merge(pFinCronogramapago);
        for (FinTransferencia ft : pFinCronogramapago.getFinTransferenciaList()) {
            ft.setCpgId(pFinCronogramapago);
            em.merge(ft);
        }
    }
    
    public Long getCantFactCronoActivasXTarea(boolean cpgEstado, Long strId){
        Query q = em.createNamedQuery("FinCronogramapago.cantXEstadoXStrId");
        q.setParameter("cpgEstado", cpgEstado);
        q.setParameter("strId", strId);
        return (Long) q.getSingleResult();
    }
}
