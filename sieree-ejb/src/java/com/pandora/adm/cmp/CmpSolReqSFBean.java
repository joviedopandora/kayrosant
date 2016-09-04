/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.cmp;

import com.pandora.adm.dao.CmpProdxreq;
import com.pandora.adm.dao.CmpRequiscomp;
import java.util.List;
import javax.ejb.*;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author luis
 */
@Stateful
@LocalBean
public class CmpSolReqSFBean extends CmpProcCompSFBeanBase {

    private CmpRequiscomp cmpRequiscomp = new CmpRequiscomp();

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void grabarRequiscomp(CmpRequiscomp pCmpRequiscomp) {
        List<CmpProdxreq> lstCmpProdxreqs = pCmpRequiscomp.getCmpProdxreqList();
            cmpRequiscomp = em.merge(pCmpRequiscomp);
        for (CmpProdxreq prodxreq : lstCmpProdxreqs) {
            prodxreq.setCrqId(cmpRequiscomp);
            prodxreq = em.merge(prodxreq);
        }
    }

    public void borrarProdXReq(List<CmpProdxreq> pListCmpProdxreqs) {
        for (CmpProdxreq cmpProdxreq : pListCmpProdxreqs) {
            if (cmpProdxreq.getPxrId() != null) {
                cmpProdxreq = em.getReference(CmpProdxreq.class, cmpProdxreq.getPxrId());
                em.remove(cmpProdxreq);
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarReqComp(List<CmpProdxreq> pListProdxreqs) {
        Query q = em.createNamedQuery("CmpProdxreq.prodXReq");
        q.setParameter("crqId", cmpRequiscomp.getCrqId());
        List<CmpProdxreq> lstProdXReq = q.getResultList();
        try {
            for (CmpProdxreq cmpProdxreq : pListProdxreqs) {
                if (cmpProdxreq.getPxrId() != null) {
                    cmpProdxreq = em.merge(pListProdxreqs.get(lstProdXReq.indexOf(cmpProdxreq)));
                } else {
                    cmpProdxreq.setCrqId(cmpRequiscomp);
                    cmpProdxreq.setPxrRechaza(Boolean.FALSE);
                    cmpProdxreq = em.merge(cmpProdxreq);
                }
            }
        } catch (ConstraintViolationException e) {
            System.out.println(e);
        }
    }

    public List<CmpProdxreq> getLstCmpProdxreq(Long pCrqId) {
        Query q = em.createNamedQuery("CmpProdxreq.prodXReq");
        q.setParameter("crqId", pCrqId);
        return q.getResultList();
    }

    /**
     * Valida si un colaborador tiene una solicitud de compra abierta, si la
     * tiene, la carga con sus productos asociados
     *
     * @param pCxcId
     * @param pCrqAbierta
     * @return true si el colaborador tiene una solicitud abierta
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean getCmpRequisXCol(Integer pCxcId, boolean pCrqAbierta) {
        try {
            Query q = em.createNamedQuery("CmpRequiscomp.reqXColAbierta");
            q.setParameter("cxcId", pCxcId);
            q.setParameter("crqAbierta", pCrqAbierta);
            cmpRequiscomp = (CmpRequiscomp) q.getSingleResult();
            q = em.createNamedQuery("CmpProdxreq.prodXReq");
            q.setParameter("crqId", cmpRequiscomp.getCrqId());
            List<CmpProdxreq> cmpProdxreqs = q.getResultList();
            cmpRequiscomp.setCmpProdxreqList(cmpProdxreqs);
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    public CmpRequiscomp getCmpRequiscomp() {
        return cmpRequiscomp;
    }
    
    /**
     * Obtener id ips sede
     *
     * @param pIseId
     */
//    public PrIpsxsede getPrIpsxsedeXId(Long pIseId) {
//        try {
//            Query q = em.createNamedQuery("PrIpsxsede.findByIseId");
//            q.setParameter("iseId", pIseId);
//            return (PrIpsxsede) q.getSingleResult();
//        } catch (NoResultException ex) {
//            return null;
//        }
//    }

    @Override
    @Remove
    public void remove() {
    }
}
