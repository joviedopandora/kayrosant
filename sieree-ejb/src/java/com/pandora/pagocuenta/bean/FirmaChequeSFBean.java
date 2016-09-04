/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.bean;

import com.pandora.pagocuenta.dao.FinCronogramapago;
import com.pandora.pagocuenta.dao.FinFirmacheque;
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
public class FirmaChequeSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }

    //Obtener lista de firma cheque
    public List<FinFirmacheque> getLstFinFirmacheque(boolean pFchEntregado) {
        Query q = em.createNamedQuery("FinFirmacheque.findByFchEntregado");
        q.setParameter("fchEntregado", pFchEntregado);
        return q.getResultList();
    }
    
    //Grabar entrega de cheque
    public void grabarEntregaCheque(FinFirmacheque pFinFirmacheque){
        pFinFirmacheque = em.merge(pFinFirmacheque);
    }

    //Grabar firma cheque y cronograma de pago
    public void grabarFirmaChequeYCronogramaPago(List<FinFirmacheque> pLstFinFirmacheques, Long strId) {
        for (FinFirmacheque finFirmacheque : pLstFinFirmacheques) {
            FinCronogramapago cronogramapago = new FinCronogramapago();
//            cronogramapago.setFchId(finFirmacheque);
//            cronogramapago.setCpgFechapago(finFirmacheque.getFchFechareclamarchq());
            cronogramapago.setCpgEstado(Boolean.TRUE);
            cronogramapago.setStrId(strId);
            cronogramapago.setFxcId(null);
//            cronogramapago.setCpgNumcuentaorg(finFirmacheque.getChqId().getChqNumcuenta());
//            finFirmacheque.getFinCronogramapagoList().add(cronogramapago);
            finFirmacheque = em.merge(finFirmacheque);
            em.merge(cronogramapago);
        }
    }
}
