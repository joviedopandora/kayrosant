/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.cmp;

import com.pandora.adm.dao.InvCatprod;
import com.pandora.adm.dao.InvMarca;
import com.pandora.adm.dao.InvMarcxprod;
import com.pandora.adm.dao.InvPresentprod;
import com.pandora.adm.dao.InvPresntxprod;
import com.pandora.adm.dao.InvProducto;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Operaciones generales al proceso de compras
 *
 * @author luis
 */
public abstract class CmpProcCompSFBeanBase {

    @PersistenceContext
    protected EntityManager em;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvMarca> getLstMarcas() {
        Query q = em.createNamedQuery("InvMarca.findAll");
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvPresentprod> getLstPresentprods() {
        Query q = em.createNamedQuery("InvPresentprod.findAll");
        return q.getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvCatprod> getLstCatprods(){
        Query q = em.createNamedQuery("InvCatprod.findAll");
        return q.getResultList();
    }

    /**
     * Cantidad de productos en inventario por presetanción y por id de producto
     * @return
     */
    public Long getCantProdXPrdIdXPsp(Integer pPrdId, Integer pPspId) {
        Query q = em.createNamedQuery("InvInvent.cantProdXprest");
        q.setParameter("prdId", pPrdId);
        q.setParameter("pspId", pPspId);
        return (Long) q.getSingleResult();
    }
    
     /**
     * Cantidad de productos en inventario por id de producto
     * @return
     */
    public Long getCantProdXPrdId(Integer pPrdId) {
        Query q = em.createNamedQuery("InvInvent.cantProd");
        q.setParameter("prdId", pPrdId);        
        return (Long) q.getSingleResult();
    }

    /**
     * Obtener lista de productos por nombre o descripción
     * @param pPrdNombre
     * @return Lista de productos por nombre o descripción
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvProducto> getLstProdXText(String pPrdNombre,Boolean pPrdUnico) {
        Query q = em.createNamedQuery("InvProducto.prodXNombDesc");
        q.setParameter("prdNombre", "%" + pPrdNombre.toUpperCase() + "%");
        q.setParameter("prdDesc", "%" + pPrdNombre.toUpperCase() + "%");
        q.setParameter("prdUnico", pPrdUnico);
        return q.getResultList();
    }

 
    /**
     * Obtener productos por categoría
     * @param pIntCatprod
     * @return Lista de productos por categoría
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvProducto> getLstProdXCat(Integer pIntCatprod, Boolean pPrdUnico, boolean pPrdEst) {
        Query q = em.createNamedQuery("InvProducto.prodXCat");
        q.setParameter("cpdId", pIntCatprod);
        q.setParameter("prdUnico", pPrdUnico);
        q.setParameter("prdEst", pPrdEst);
        return q.getResultList();
    }

    /**
     * Obtener lista de marcas posibles por producto
     * @return Lista de marcas pro producto
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvMarcxprod> getLstMarcxprod(Integer pPrdId, Boolean pBlnEst) {
        Query q = em.createNamedQuery("InvMarcxprod.marcXProd");
        q.setParameter("prdId", pPrdId);
        q.setParameter("mxpEst", pBlnEst);
        return q.getResultList();
    }

    /**
     * Obtener lista de marcas posibles por producto
     * @return Lista de marcas pro producto
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvPresntxprod> getLstPresntxprod(Integer pPrdId, Boolean pBlnEst) {
        Query q = em.createNamedQuery("InvPresntxprod.prestXProd");
        q.setParameter("prdId", pPrdId);
        q.setParameter("pxpEst", pBlnEst);
        return q.getResultList();
    }

    public abstract void remove();
}
