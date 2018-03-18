/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.param;

import adm.sys.dao.AdmCargo;
import adm.sys.dao.AdmColaborador;
import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.AdmEmpresa;
import adm.sys.dao.RfTipodoc;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author byrobles
 */
@Stateful
@LocalBean
public class UsuarioSFBean {

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
     * Cargar lista de colaboradores
     *
     * @return
     */
    public List<AdmColaborador> getLstAdmColaborador() {
        Query q = em.createNamedQuery("AdmColaborador.findAll");
        return q.getResultList();
    }

    /**
     * Cargar lista de colaboradores por estado
     *
     * @param pColEst
     * @return
     */
    public List<AdmColaborador> getLstAdmColaboradorXEstado(boolean pColEst) {
        Query q = em.createNamedQuery("AdmColaborador.findByColEst");
        q.setParameter("colEst", pColEst);
        return q.getResultList();
    }

    /**
     * Cargar lista de colaboradores por empresa
     *
     * @param pEmpId
     * @return
     */
    public List<AdmColxemp> getLstAdmColxemp(AdmEmpresa pEmpId) {
        Query q = em.createNamedQuery("AdmColxemp.findByEmpresa");
        q.setParameter("empId", pEmpId);
        return q.getResultList();
    }

    /**
     * Cargar lista de cargos por estado
     *
     * @param pCrgEst
     * @return
     */
    public List<AdmCargo> getLstAdmCargoXEstado(boolean pCrgEst) {
        Query q = em.createNamedQuery("AdmCargo.findByCrgEst");
        q.setParameter("crgEst", pCrgEst);
        return q.getResultList();
    }

    /**
     * Cargar lista de cargos por colaborador
     *
     * @param pCpeId
     * @return
     */
    public List<AdmCrgxcol> getLstAdmCrgxcol(Integer pCpeId) {
        Query q = em.createNamedQuery("AdmCrgxcol.findByCrgXCol");
        q.setParameter("cpeId", pCpeId);
        return q.getResultList();
    }
    
    /**
     * Agregar cargo a colaborador
     *
     * @param pAdmCrgxcol
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AdmCrgxcol editarCrgxcol(AdmCrgxcol pAdmCrgxcol) {
        pAdmCrgxcol = em.merge(pAdmCrgxcol);
        return pAdmCrgxcol;
    }
    
    public AdmCrgxcol getAdmCrgxcol(Integer pCrgxcol) {
        return em.getReference(AdmCrgxcol.class, pCrgxcol);
    }

    /**
     * Eliminar un cargo a colaborador
     *
     * @param pAdmCrgxcol
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteCrgXCol(AdmCrgxcol pAdmCrgxcol) {
        try {
            em.setFlushMode(FlushModeType.COMMIT);
            em.remove(em.getReference(AdmCrgxcol.class, pAdmCrgxcol.getCxcId()));
            em.flush();
        } catch (EntityNotFoundException enfe) {
        }
    }

    /**
     * Grabar colaborador
     *
     * @param pAdmColaborador
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarColaborador(AdmColaborador pAdmColaborador) {
        pAdmColaborador = em.merge(pAdmColaborador);
    }
}
