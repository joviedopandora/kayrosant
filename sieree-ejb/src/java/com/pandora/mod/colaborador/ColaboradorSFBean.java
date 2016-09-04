/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.colaborador;
import adm.sys.dao.AdmEstcol;
import adm.sys.dao.AdmAntiguedad;
import adm.sys.dao.AdmCargo;
import com.pandora.adm.rf.dao.RfDep;
import com.pandora.adm.rf.dao.RfCiudad;
import adm.sys.dao.AdmColaborador;
import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.RfSexo;
import adm.sys.dao.RfTipocontrato;
import adm.sys.dao.RfTipodoc;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntRfTipocliente;
import java.util.List;
import javax.annotation.ManagedBean;
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
 * @author patricia
 */
@Stateful
@LocalBean
public class ColaboradorSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }

    /**
     * Cargar lista de ordenes de producción por estado procesado
     *
     * @param pOprProcesado
     * @param oprEstadodespacho
     * @return
     */
    /**
     * Cargar lista colaboradores
     *
     * @param iDcol_cedula
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<adm.sys.dao.AdmColaborador> getLstColaboradores() {
        Query q = em.createNamedQuery("AdmColaborador.findAll");
        return q.getResultList();
    }

    /**
     * Cargar lista de tipo de documento por estado
     *
     * @param pTdcEstado
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfTipodoc> getLstRfTipodoc(boolean pTdcEstado) {
        Query q = em.createNamedQuery("RfTipodoc.findByTdcEstado");
        q.setParameter("tdcEstado", pTdcEstado);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmCargo> getLstAdmCargo(boolean estado) {
        Query q = em.createNamedQuery("AdmCargo.findByCrgEst");
        q.setParameter("crgEst", estado);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AdmColaborador editar(AdmColaborador admColaborador) {
        admColaborador = em.merge(admColaborador);
        return admColaborador;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AdmColxemp editarAdmColxemp(AdmColxemp colemp) {
        colemp = em.merge(colemp);
        return colemp;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AdmCrgxcol editarAdmCrgxcol(AdmCrgxcol colemp) {
        colemp = em.merge(colemp);
        return colemp;
    }

    /**
     * Obtener id tipo de documento
     *
     * @param pTdcId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfTipodoc getRfTipodocXId(String pTdcId) {
        return em.getReference(RfTipodoc.class, pTdcId);
    }

    /**
     * Obtener id tipo de documento
     *
     * @param pTdcId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AdmCargo getAdmCargoXId(String id) {
        return em.getReference(AdmCargo.class, id);
    }

    /**
     * Obtener id sexo cliente
     *
     * @param pSexId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfSexo getRfSexo(String pSexId) {
        return em.getReference(RfSexo.class, pSexId);
    }
    
    
    
     /**
     * Obtener id sexo cliente
     *
     * @param pEstadoCol
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AdmEstcol getAdmEstcol(Integer pEstadoCol) {
        return em.getReference(AdmEstcol.class, pEstadoCol);
    } 
    
    
    
     /**
     * Obtener id sexo cliente
     *
     * @param pCiudadCol
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfCiudad getRfCiudad(long pCiudadCol) {
        return em.find(RfCiudad.class, pCiudadCol);
    } 
    
    /**
     * Obtener id sexo cliente
     *
     * @param pRfDepar 
     * @return
     */
    
    
      @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfDep getRfDepartamento(String pRfDepar) {
        return em.find(RfDep.class, pRfDepar);
    } 
    
    
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AdmAntiguedad getAdmAntiguedad(Integer pAntiguedad) {
        return em.find(AdmAntiguedad.class, pAntiguedad);
    } 
    
    
    

    /**
     * Obtener id tipo cliente
     *
     * @param pTclId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public VntRfTipocliente getVntRfTipoclienteXId(Integer pTclId) {
        return em.find(VntRfTipocliente.class, pTclId);
    }

    /**
     * Cargar lista de tipo de cliente por estado
     *
     * @param pTclEst
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRfTipocliente> getLstVntRfTipocliente(boolean pTclEst) {
        Query q = em.createNamedQuery("VntRfTipocliente.findByTclEst");
        q.setParameter("tclEst", pTclEst);
        return q.getResultList();
    }

    /**
     * Cargar lista de sexo
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfSexo> getLstRfSexo() {
        Query q = em.createNamedQuery("RfSexo.findAll");
        return q.getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmEstcol> getLstEstcols() {
        Query q = em.createNamedQuery("AdmEstcol.findAll");
        return q.getResultList();
    }
    
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfDep> getLstDepartCol() {
        Query q = em.createNamedQuery("RfDep.findAll");
        return q.getResultList();
    }
    
    
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfCiudad> getLstCiudadCol(String pDepId) {
        Query q = em.createNamedQuery("RfCiudad.ciuXDept");
          q.setParameter("depId", pDepId);
        return q.getResultList();
    }
    
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmAntiguedad> getLstAntigu() {
        Query q = em.createNamedQuery("AdmAntiguedad.findAll");
        return q.getResultList();
    }
    
    

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmColaborador> getLstAdmColaboradorXCC(String colCedula) {
        Query q = em.createNamedQuery("AdmColaborador.findByColCedula");
        q.setParameter("colCedula", colCedula);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmColxemp> getLstAdmColxempXColaboradorXEmpresa(String colCedula, Integer empId) {
        Query q = em.createNamedQuery("AdmColxemp.findByEmpresaXColaborador");
        q.setParameter("colCedula", colCedula);
        q.setParameter("empId", empId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmCrgxcol> getLstAdmCrgxcolXColaboradorXEmpresa(Integer cpeId) {
        Query q = em.createNamedQuery("AdmCrgxcol.findByColaborador");
        q.setParameter("cpeId", cpeId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRegistroventa> getLstVntRegistroventaXColaborador(Integer cxcId) {
        Query q = em.createNamedQuery("VntRegistroventa.regVentasXColaborador");
        q.setParameter("cxcId", cxcId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfTipocontrato> getLstRfTipocontrato(boolean tctEstado) {
        Query q = em.createNamedQuery("RfTipocontrato.findByTctEstado");
        q.setParameter("tctEstado", tctEstado);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfTipocontrato getRfTipocontratoXId(Integer pTctId) {
        return em.getReference(RfTipocontrato.class, pTctId);
    }

    /**
     * Validar colaborador
     *
     * @param pColxemp Objeto colaborador
     */
    public List<AdmColxemp> validarUsuarioRepetido(String cpeUsuario) {
        Query q = em.createNamedQuery("AdmColxemp.validarColUsuario");
        q.setParameter("cpeUsuario", cpeUsuario);
        return q.getResultList();


    }
}
