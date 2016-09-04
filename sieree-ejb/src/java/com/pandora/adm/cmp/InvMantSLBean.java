/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.cmp;

import com.pandora.pagocuenta.dao.FinFormapago;
import com.pandora.adm.dao.*;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author carlos
 */
@Stateless
@LocalBean
public class InvMantSLBean {

    @PersistenceContext
    EntityManager em;

    //<editor-fold defaultstate="collapsed" desc="Producto">
    /**
     * Agregar un producto
     *
     * @param pInvProducto
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InvProducto addInvProducto(InvProducto pInvProducto) {

        for (InvMarcxprod marcxprod : pInvProducto.getInvMarcxprodList()) {
            marcxprod = em.merge(marcxprod);
        }

        for (InvPresntxprod invPresntxprod : pInvProducto.getInvPresntxprodList()) {
            invPresntxprod = em.merge(invPresntxprod);
        }
        pInvProducto = em.merge(pInvProducto);
        return pInvProducto;
    }

    /**
     * Obtener Id de producto
     *
     * @param pPrdId Id del producto
     * @return Id de producto
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InvProducto getInvProductoXId(Integer pPrdId) {
        try {
            return em.getReference(InvProducto.class, pPrdId);
        } catch (EntityNotFoundException exc) {
            return null;
        }
    }

    /**
     * Obtener lista de productos
     *
     * @return Lista de productos
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvProducto> getLstInvProducto() {
        Query q = em.createNamedQuery("InvProducto.findAll");
        return q.getResultList();
    }

    /**
     * Obtener lista de productos por categoría
     *
     * @param pCpdId Id de la categoría
     * @return Lista de productos por categoría
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvProducto> getLstInvProdXCat(Integer pCpdId) {
        Query q = em.createNamedQuery("InvProducto.findByProdXCat");
        q.setParameter("cpdId", pCpdId);
        return q.getResultList();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Categoría">
    /**
     * Agregar una categoría
     *
     * @param pInvCatprod
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InvCatprod addInvCatprod(InvCatprod pInvCatprod) {
        pInvCatprod = em.merge(pInvCatprod);
        return pInvCatprod;
    }
    
    /**
     * Obtener lista de categorías
     *
     * @return Lista de categorías
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvCatprod> getLstCatprod() {
        Query q = em.createNamedQuery("InvCatprod.findAll");
        return q.getResultList();
    }

    /**
     * Obtener lista de categorías por estado
     *
     * @param pCpdEst Estado categoría
     * @return Lista de categorías
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvCatprod> getLstInvCatprodXEstado(boolean pCpdEst) {
        Query q = em.createNamedQuery("InvCatprod.findByCpdEst");
        q.setParameter("cpdEst", pCpdEst);
        return q.getResultList();
    }    
    
    /**
     * Obtener id de categoría
     *
     * @param cpdId id
     * @return id categoría
     */
    public InvCatprod getInvCatprodXId(Integer cpdId){
        return em.getReference(InvCatprod.class, cpdId);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Presentación">
    /**
     * Agregar una presentación
     *
     * @param pInvPresentprod
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InvPresentprod addInvPresentprod(InvPresentprod pInvPresentprod) {
        pInvPresentprod = em.merge(pInvPresentprod);
        return pInvPresentprod;
    }

    /**
     * Obtener lista de presentaciones
     *
     * @return Lista de presentaciones
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvPresentprod> getLstInvPresentprod() {
        Query q = em.createNamedQuery("InvPresentprod.findAll");
        return q.getResultList();
    }

    /**
     * Obtener lista de presentaciones por estado
     *
     * @param pPspEst Estado de la presentación
     * @return Lista de presentaciones por estado
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvPresentprod> getLstInvPresentprod(boolean pPspEst) {
        Query q = em.createNamedQuery("InvPresentprod.findByPspEst");
        q.setParameter("pspEst", pPspEst);
        return q.getResultList();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Presentación por producto">
    /**
     * Agregar presentación por producto
     *
     * @param pInvPresntxprod
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InvPresntxprod addInvPresntxprod(InvPresntxprod pInvPresntxprod) {
        pInvPresntxprod = em.merge(pInvPresntxprod);
        return pInvPresntxprod;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Marca">
    /**
     * Agregar una marca
     *
     * @param pInvMarca
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InvMarca addInvMarca(InvMarca pInvMarca) {
        pInvMarca = em.merge(pInvMarca);
        return pInvMarca;
    }

    /**
     * Obtener lista de marcas
     *
     * @return Lista de marcas
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvMarca> getLstInvMarca() {
        Query q = em.createNamedQuery("InvMarca.findAll");
        return q.getResultList();
    }

    /**
     * Obtener lista de marcas por estado
     *
     * @param pMarEst Estado de la marca
     * @return Lista de marcas por estado
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvMarca> getLstInvMarca(boolean pMarEst) {
        Query q = em.createNamedQuery("InvMarca.findByMarEst");
        q.setParameter("marEst", pMarEst);
        return q.getResultList();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Marca por producto">
    /**
     * Agregar marca por producto
     *
     * @param pInvMarcxprod
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InvMarcxprod addInvMarcxprod(InvMarcxprod pInvMarcxprod) {
        pInvMarcxprod = em.merge(pInvMarcxprod);
        return pInvMarcxprod;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Proveedor">
    /**
     * Agregar un proveedor
     *
     * @param pInvProovedor
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InvProovedor addInvProovedor(InvProovedor pInvProovedor) {
        pInvProovedor = em.merge(pInvProovedor);
        return pInvProovedor;
    }
    
    /**
     * Obtener Id de proveedor
     *
     * @param pPrvId Id del proveedor
     * @return Id de proveedor
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InvProovedor getInvProveedorXId(Integer pPrvId){
        try {
            return em.getReference(InvProovedor.class, pPrvId);
        } catch (EntityNotFoundException exc) {
            return null;
        }
    }            

    /**
     * Obtener lista de proveedores
     *
     * @return Lista de proveedores
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvProovedor> getLstInvProovedor() {
        Query q = em.createNamedQuery("InvProovedor.findAll");
        return q.getResultList();
    }

    //</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="Forma de pago">
    /**
     * Obtener lista de formas de pago
     *
     * @param pFpgEst Estado
     * @return Lista de formas de pago
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<FinFormapago> getLstFinFormapagoXEstado(boolean pFpgEst) {
        Query q = em.createNamedQuery("FinFormapago.findByFpgEst");
        q.setParameter("fpgEst", pFpgEst);
        return q.getResultList();
    }
    
    /**
     * Obtener id de forma de pago
     *
     * @param fpgId id
     * @return id forma de pago
     */
    public FinFormapago getFinFormapagoXId(Integer fpgId) {
        return em.getReference(FinFormapago.class, fpgId);
    }    
    //</editor-fold>
}
