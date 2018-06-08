/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.param;

import adm.sys.dao.AdmCargo;
import adm.sys.dao.AdmColaborador;
import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmCpexsubmodapp;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.AdmEmpresa;
import adm.sys.dao.AdmMenuapp;
import adm.sys.dao.AdmModapp;
import adm.sys.dao.AdmSubmodapp;
import adm.sys.dao.RfTipodoc;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
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
@Stateless
@LocalBean
public class UsuarioSFBean {

    @PersistenceContext
    EntityManager em;

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
     * Cargar lista de colaboradores por empresa
     *
     * @param pEmpId
     * @param texto
     * @return
     */
    public List<AdmColxemp> getLstAdmColxempXtexto(AdmEmpresa pEmpId, String texto) {
        return em.createNamedQuery("AdmColxemp.findCedONomXEmp")
                .setParameter("empId", pEmpId.getEmpId())
                .setParameter("texto", texto).getResultList();

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

    public List<AdmCrgxcol> editarLstCargoXCol(List<AdmCrgxcol> pLstAdmCrgxcols) {
        for (AdmCrgxcol cxg : pLstAdmCrgxcols) {
            cxg.setCpeId(em.getReference(AdmColxemp.class, cxg.getCpeId().getCpeId()));
            cxg.setCrgId(em.getReference(AdmCargo.class, cxg.getCrgId().getCrgId()));
            cxg = em.merge(cxg);
        }
        return pLstAdmCrgxcols;
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
        for (AdmColxemp colxemp : pAdmColaborador.getAdmColxempList()) {
            colxemp = em.merge(colxemp);
        }
        pAdmColaborador = em.merge(pAdmColaborador);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmColxemp> getLstAdmColxempXColaboradorXEmpresa(String colCedula, Integer empId) {
        Query q = em.createNamedQuery("AdmColxemp.findByEmpresaXColaborador");
        q.setParameter("colCedula", colCedula);
        q.setParameter("empId", empId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmMenuapp> getLstAdmMenuapp() {
        return em.createNamedQuery("AdmMenuapp.findAll").getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmModapp> getLstModappXMenu(Integer pMenId) {
        return em.createNamedQuery("AdmModapp.getModappXMenu").setParameter("menId", pMenId).getResultList();
    }

    public List<AdmSubmodapp> getLstSubmodappXModId(Integer pModId) {
        return em.createNamedQuery("AdmSubmodapp.findBySmdXMod").setParameter("modId", pModId).getResultList();
    }

    public List<AdmSubmodapp> getLstSubmodappXCpeId(Integer pCpeId) {
        return em.createNamedQuery("AdmSubmodapp.getSubmodXCpe").
                setParameter("cpeId", pCpeId).
                getResultList();
    }
    
    public void grabarSubmodulosXColaborador(List<AdmCpexsubmodapp> pLstAdmCpexsubmodapps){
        for (AdmCpexsubmodapp ac : pLstAdmCpexsubmodapps) {
          ac= em.merge(ac);
        }
    }
    
    public void eliminargrabarSubmodulosXColaborador(List<AdmCpexsubmodapp> pLstAdmCpexsubmodapps){
      for (AdmCpexsubmodapp ac : pLstAdmCpexsubmodapps) {
          ac= (AdmCpexsubmodapp) em.createNamedQuery("AdmCpexsubmodapp.cxmXSubmodXCxc").
                  setParameter("cxcId", ac.getCxcId().getCxcId()).
                  setParameter("smdId", ac.getSmdId().getSmdId()).getSingleResult();
          em.remove(ac);
        }
    }
}
