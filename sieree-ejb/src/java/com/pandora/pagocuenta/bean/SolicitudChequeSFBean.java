/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.bean;

import com.pandora.pagocuenta.dao.FinCheque;
import com.pandora.pagocuenta.dao.FinFirmacheque;
import com.pandora.pagocuenta.dao.FinSolicitudcheque;
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
public class SolicitudChequeSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }

    //<editor-fold defaultstate="collapsed" desc="Solicitud cheque">
    //Obtener lista de solicitudes por forma de pago
    public List<FinSolicitudcheque> getLstFinSolicitudchequeXFormaPagoXEstado(boolean solEstado, Integer fpgId) {
        Query q = em.createNamedQuery("FinSolicitudcheque.findAllXFormaPagoXEstado");
        q.setParameter("fpgId", fpgId);
        q.setParameter("solEstado", solEstado);
        return q.getResultList();
    }
    
    //Obtener lista de solicitudes por forma de pago
    public List<FinSolicitudcheque> getLstFinSolicitudchequePendienteXFormaPagoXEstado(boolean solEstado, Integer fpgId) {
        Query q = em.createNamedQuery("FinSolicitudcheque.FinSolicitudcheque.FormaPagoXEstado");
        q.setParameter("fpgId", fpgId);
        q.setParameter("solEstado", solEstado);
        return q.getResultList();
    }

    /**
     * Grabar solicitud de cheque
     *
     * @param pFinSolicitudcheque
     */
    public void editarFinSolicitudCheque(FinSolicitudcheque pFinSolicitudcheque) {
        em.merge(pFinSolicitudcheque);
    }

    /**
     * Grabar solicitud cheque y cheque.
     *
     * @param pFinSolicitudcheque
     */
    public void grabarSolchequeYFirmaChequeYFirma(FinSolicitudcheque pFinSolicitudcheque, Long pStrId) {
        pFinSolicitudcheque = em.merge(pFinSolicitudcheque);
        for (FinCheque finCheque : pFinSolicitudcheque.getFinChequeList()) {
            finCheque.setBncId(em.getReference(RfBanco.class, finCheque.getBncId().getBncId()));
            finCheque = em.merge(finCheque);
            if (finCheque.getFinFirmachequeList().isEmpty()) {
                FinFirmacheque finFirmacheque = new FinFirmacheque();
                finFirmacheque.setChqId(finCheque);
                finFirmacheque.setFchEstado(Boolean.FALSE);
                finFirmacheque.setFchEntregado(Boolean.FALSE);
                finFirmacheque.setStrId(pStrId);
                em.merge(finFirmacheque);
            }
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Cheque">

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

    //Grabar cheque
    public void editarFinCheque(FinCheque pFinCheque) {
        em.merge(pFinCheque);
    }
    //</editor-fold>
}
