/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.bean;

import com.pandora.pagocuenta.dao.FinAprobado;
import com.pandora.pagocuenta.dao.FinCronogramapago;
import com.pandora.pagocuenta.dao.FinFormapago;
import com.pandora.pagocuenta.dao.FinFpgxcta;
import com.pandora.pagocuenta.dao.FinSolicitudcheque;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateful
@LocalBean
public class ApruebaPagoCuentaSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }

    //Obtener lista de cuentas revisadas para aprobar
    public List<FinAprobado> getLstFinAprobadoXRevisado(boolean apbAprobado) {
        Query q = em.createNamedQuery("FinAprobado.findByApbAprobado");
        q.setParameter("apbAprobado", apbAprobado);
        return q.getResultList();
    }

    //Obtener lista de formas de pago por estado
    public List<FinFormapago> cargarFormaPagoXEstado(boolean fpgEst) {
        Query q = em.createNamedQuery("FinFormapago.findByFpgEst");
        q.setParameter("fpgEst", fpgEst);
        return q.getResultList();
    }

    //Obtener id de forma de pago
    public FinFormapago getFormapagoXId(Integer fpgId) {
        try {
            Query q = em.createNamedQuery("FinFormapago.findByFpgId");
            q.setParameter("fpgId", fpgId);
            return (FinFormapago) q.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }

    //Grabar aprobación pago de cuenta
    public void grabarFinAprobado(FinAprobado pFinAprobado) {
        pFinAprobado = em.merge(pFinAprobado);
    }

    //Grabar forma de pago por cuenta
    public void grabarFormaPagoXCuenta(FinFpgxcta pFinFpgxcta) {
        pFinFpgxcta = em.merge(pFinFpgxcta);
    }

    //Grabar solicitud cheque
    public void grabarSolicitudCheque(FinSolicitudcheque pFinSolicitudcheque) {
        pFinSolicitudcheque = em.merge(pFinSolicitudcheque);
    }

    //Grabar cronograma pago cheque
    public void grabarCronoPagoCheque(FinCronogramapago pFinCronogramapago) {
        pFinCronogramapago = em.merge(pFinCronogramapago);
    }

    public Long getCantFinSolicitudcheque(Long strId) {
        Query q = em.createNamedQuery("FinSolicitudcheque.cantXStrId");
        q.setParameter("strId", strId);
        return (Long) q.getSingleResult();
    }

    public Long getCantFinCronogramapago(Long strId) {
        Query q = em.createNamedQuery("FinCronogramapago.cantXStrId");
        q.setParameter("strId", strId);
        return (Long) q.getSingleResult();
    }

    public Long getCantFactAprobadasXTarea(boolean apbAprobado, Long strId) {
        Query q = em.createNamedQuery("FinAprobado.findByAprobadoXStrId");
        q.setParameter("apbAprobado", apbAprobado);
        q.setParameter("strId", strId);
        return (Long) q.getSingleResult();
    }

    /**
     * Grabar lista de aprobados y de acuerdo a la forma de pago, grabar en
     * solicitud de cheque y en cronograma.
     *
     * @param pListFinAprobado
     */
    public void grabaAprobadoChequeCronograma(List<FinAprobado> pListFinAprobado) {
        for (FinAprobado finAprobado : pListFinAprobado) {
//            finAprobado.setFpgId(em.getReference(FinFormapago.class, finAprobado.getFpgId().getFpgId()));
            finAprobado = em.merge(finAprobado);
//            if (finAprobado.getFpgId().getFpgId() == 3) {
//                FinSolicitudcheque fs = new FinSolicitudcheque();
//                fs.setApbId(finAprobado);
//                fs.setSolEstado(Boolean.FALSE);
//                em.merge(fs);
//            } else {
//            FinCronogramapago cronogramapago = new FinCronogramapago();
//            cronogramapago.setApbId(finAprobado);
//            em.merge(cronogramapago);
//            }
        }
    }
}
