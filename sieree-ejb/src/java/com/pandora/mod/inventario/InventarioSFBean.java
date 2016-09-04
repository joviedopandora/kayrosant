/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.inventario;

import com.pandora.adm.dao.InvCatprod;
import com.pandora.adm.dao.InvInvent;
import com.pandora.adm.dao.InvMarca;
import com.pandora.adm.dao.InvMarcxprod;
import com.pandora.adm.dao.InvPresentprod;
import com.pandora.adm.dao.InvPresntxprod;
import com.pandora.adm.dao.InvProducto;
import com.pandora.adm.dao.InvTipotransc;
import com.pandora.adm.dao.InvTransac;
import com.pandora.mod.venta.dao.VntFactura;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Javier
 */
@Stateless
@LocalBean
public class InventarioSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }

    /**
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvInvent> getInventarios() {
        Query q = em.createNamedQuery("InvInvent.findAll");
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InvProducto editarProducto(InvProducto producto) {
        if (producto.getPrdId() == null) {
            producto.setPrdId(getMaxIdProducto() + 1);
        }
        producto = em.merge(producto);
        //em.flush();
        return producto;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvProducto> getLstInvProducto() {
        Query q = em.createNamedQuery("InvProducto.findAll");
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvCatprod> getLstInvCatprod(boolean cpdEst) {
        Query q = em.createNamedQuery("InvCatprod.findByCpdEst");
        q.setParameter("cpdEst", cpdEst);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InvCatprod getInvCatprodXId(Integer idCategoria) {
        return em.find(InvCatprod.class, idCategoria);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InvCatprod getInvestado(Integer idestado) {
        return em.find(InvCatprod.class, idestado);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Integer getMaxIdProducto() {
        try {
            Query q = em.createNamedQuery("InvProducto.findMaxId");
            Integer numero = (Integer) q.getSingleResult();
            return (numero == null ? 0 : numero);
        } catch (EJBException e) {
            return 0;
        }
    }

    /**
     * Lista de marcas activas
     *
     * @param marEst
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvMarca> getLstInvMarcaXEstado(boolean marEst) {
        Query q = em.createNamedQuery("InvMarca.findByMarEst");
        q.setParameter("marEst", marEst);
        return q.getResultList();
    }

    /**
     * consulta marcas asociadas al producto
     *
     * @param prdId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvMarcxprod> getLstInvMarcxprodXProducto(Integer prdId) {
        Query q = em.createNamedQuery("InvMarcxprod.findByProducto");
        q.setParameter("prdId", prdId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InvMarca getInvMarcaXId(Integer marId) {
        return em.find(InvMarca.class, marId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarLstInvMarcxprod(List<InvMarcxprod> lstInvMarcxprods) {
        for (InvMarcxprod s : lstInvMarcxprods) {
            em.merge(s);
        }
    }

    /*  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
     public List<InvMarcxprod> getLstInvMarcxprodXProductoXCodigo(Long mxpId, String codigo) {
     StringBuilder sql = new StringBuilder("SELECT i FROM InvMarcxprod i WHERE i.mxpCodigo = :codigo ");
     if (mxpId != null) {
     sql.append(" AND i.mxpId <>:mxpId ");
     }
     Query q = em.createQuery(sql.toString());
     q.setParameter("codigo", codigo);
     if (mxpId != null) {
     q.setParameter("mxpId", mxpId);
     }
     return q.getResultList();
     }
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvMarcxprod> getLstInvMarcxprodXProductoXParametros(String codigo, String nombre, String descripcion) {
        StringBuilder sql = new StringBuilder("SELECT i FROM InvMarcxprod i  WHERE i.mxpEst = :mxpEst  ");
        if (codigo != null) {
            sql.append(" AND UPPER(i.mxpCodigo) LIKE :codigo ");
        }
        if (nombre != null) {
            sql.append(" AND UPPER(i.prdId.prdNombre) LIKE :nombre ");
        }
        if (descripcion != null) {
            sql.append(" AND UPPER(i.prdId.prdDesc) LIKE :descripcion ");
        }
        sql.append(" ORDER BY i.prdId.prdNombre ASC,i.marId.marNombre ASC ");
        Query q = em.createQuery(sql.toString());
        q.setParameter("mxpEst", true);
        if (codigo != null) {
            codigo = codigo.toUpperCase();
            q.setParameter("codigo", "%" + codigo + "%");
        }
        if (nombre != null) {
            nombre = nombre.toUpperCase();
            q.setParameter("nombre", "%" + nombre + "%");
        }
        if (descripcion != null) {
            descripcion = descripcion.toUpperCase();
            q.setParameter("descripcion", "%" + descripcion + "%");
        }
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvInvent> getLstInvInventXProductoYMarcaYPresentacion(Integer prdId, Integer marId, Integer pspId) {
        Query q = em.createNamedQuery("InvInvent.findByProductoYMarcaYPresentacion");
        q.setParameter("prdId", prdId);
        q.setParameter("marId", marId);
        q.setParameter("pspId", pspId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvTipotransc> getLstInvTipotransc(boolean ttrEst) {
        Query q = em.createNamedQuery("InvTipotransc.findByTtrEst");
        q.setParameter("ttrEst", ttrEst);

        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InvInvent editarInvInvent(InvInvent invent) {

        invent = em.merge(invent);
        //em.flush();
        return invent;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public InvTransac editarInvTransac(InvTransac transac) {

        transac = em.merge(transac);
        //em.flush();
        return transac;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InvTipotransc getInvTipotranscXId(Integer idtipo) {
        return em.find(InvTipotransc.class, idtipo);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvPresntxprod> getLstInvPresntxprodXProducto(Integer prdId) {
        Query q = em.createNamedQuery("InvPresntxprod.findByProducto");
        q.setParameter("prdId", prdId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvPresentprod> getLstInvPresentprodXEstado(boolean pspEst) {
        Query q = em.createNamedQuery("InvPresentprod.findByPspEst");
        q.setParameter("pspEst", pspEst);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InvPresentprod getInvPresentprodaXId(Integer marId) {
        return em.find(InvPresentprod.class, marId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarLstInvPresntxprod(List<InvPresntxprod> lstInvPresntxprods) {
        for (InvPresntxprod s : lstInvPresntxprods) {
            em.merge(s);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvInvent> getLstInvInventXParametros(TablaConsultaInventario tci) {
        StringBuilder sql = new StringBuilder("SELECT  i FROM InvInvent i WHERE i.prdId.prdEst = :pspEst ");
        if (tci.getCodigoBarras() != null && !tci.getCodigoBarras().isEmpty()) {
            sql.append(" AND  UPPER(i.invCodigobarras) LIKE UPPER(:invCodigobarras) ");
        }
        if (tci.getIdProducto() != null) {
            sql.append(" AND i.prdId.prdId = :prdId ");
        }
        if (tci.getNombreProducto() != null && !tci.getNombreProducto().isEmpty()) {
            sql.append(" AND  UPPER(i.prdId.prdNombre) LIKE UPPER(:prdNombre) ");
        }
        if (tci.getMarca() != null && tci.getMarca() != -1) {
            sql.append(" AND i.marId.marId = :marId ");
        }

        if (tci.getPresentacion() != null && tci.getPresentacion() != -1) {
            sql.append(" AND i.pspId.pspId = :pspId ");
        }

        sql.append("  ORDER BY i.prdId.prdNombre ");
        Query q = em.createQuery(sql.toString());
        q.setParameter("pspEst", true);
        if (tci.getCodigoBarras() != null && !tci.getCodigoBarras().isEmpty()) {
            q.setParameter("invCodigobarras", "%" + tci.getCodigoBarras() + "%");
        }
        if (tci.getIdProducto() != null) {
            q.setParameter("prdId", tci.getIdProducto());
        }
        if (tci.getNombreProducto() != null && !tci.getNombreProducto().isEmpty()) {
            q.setParameter("prdNombre", "%" + tci.getNombreProducto() + "%");
        }
        if (tci.getMarca() != null && tci.getMarca() != -1) {
            q.setParameter("marId", tci.getMarca());
        }

        if (tci.getPresentacion() != null && tci.getPresentacion() != -1) {
            q.setParameter("pspId", tci.getPresentacion());
        }
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvProducto> getLstInvProductoXParametros(TablaConsultaInventario tci) {
        StringBuilder sql = new StringBuilder("SELECT  i FROM InvProducto i WHERE i.prdEst = :pspEst ");

        if (tci.getIdProducto() != null) {
            sql.append(" AND i.prdId = :prdId ");
        }
        if (tci.getNombreProducto() != null && !tci.getNombreProducto().isEmpty()) {
            sql.append(" AND UPPER(i.prdNombre) LIKE UPPER(:prdNombre) ");
        }
        if (tci.getMarca() != null && tci.getMarca() != -1) {
            sql.append(" AND i.invMarcxprodList.marId = :marId ");
        }

        if (tci.getPresentacion() != null && tci.getPresentacion() != -1) {
            sql.append(" AND i.invPresntxprodList.pspId= :pspId ");
        }

        sql.append("  ORDER BY i.prdNombre ");
        Query q = em.createQuery(sql.toString());
        q.setParameter("pspEst", true);

        if (tci.getIdProducto() != null) {
            q.setParameter("prdId", tci.getIdProducto());
        }
        if (tci.getNombreProducto() != null && !tci.getNombreProducto().isEmpty()) {
            q.setParameter("prdNombre", "%" + tci.getNombreProducto() + "%");
        }
        if (tci.getMarca() != null && tci.getMarca() != -1) {
            q.setParameter("marId", tci.getMarca());
        }

        if (tci.getPresentacion() != null && tci.getPresentacion() != -1) {
            q.setParameter("pspId", tci.getPresentacion());
        }
        return q.getResultList();
    }
    
    
     public List<VntFactura> getLstVntFacturaXcountfecha() {
        Query q = em.createNamedQuery("VntFactura.countfactfecha");
        return q.getResultList();
    }
}
