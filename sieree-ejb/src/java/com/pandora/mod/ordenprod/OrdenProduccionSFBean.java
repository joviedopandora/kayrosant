/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.ordenprod;

import com.pandora.mod.evaluacion.ConsultaOPDTO;
import adm.sys.dao.AdmCrgxcol;
import com.pandora.adm.dao.InvInvent;
import com.pandora.adm.dao.InvProducto;
import com.pandora.mod.ordenprod.dao.PopCxccitacion;
import com.pandora.mod.ordenprod.dao.PopCxcevento;
import com.pandora.mod.ordenprod.dao.PopCxcrespon;
import com.pandora.mod.ordenprod.dao.PopCxcrol;
import com.pandora.mod.ordenprod.dao.PopCxcuniforme;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntServxventa;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author byrobles
 */
@Stateful
@LocalBean
public class OrdenProduccionSFBean {

    @PersistenceContext
    EntityManager em;

    private PopOrdenprod popOrdenProd = new PopOrdenprod();

    @Remove
    public void remove() {

    }

    /**
     * Cargar lista de servicios por venta
     *
     * @param pOprProcesado
     * @param pStrId
     * @return
     */
    public List<PopOrdenprod> ggetLstPopOrdenprodXProcXStrId(boolean pOprProcesado, Long pStrId) {
        Query q = em.createNamedQuery("PopOrdenprod.findByProcXStrId");
        q.setParameter("oprProcesado", pOprProcesado);
        q.setParameter("strId", pStrId);
        return q.getResultList();
    }

    /**
     * Cargar lista de servicios por venta
     *
     * @param pStrId
     * @return
     */
    public VntRegistroventa getLstVntRegistroventaXStrId(Long pStrId) {
        try {
            Query q = em.createNamedQuery("VntRegistroventa.revVentaXStrId");
            q.setParameter("strId", pStrId);
            return (VntRegistroventa) q.getSingleResult();
        } catch (NoResultException | NonUniqueResultException nre) {
            return null;
        }
    }

    /**
     * Cargar lista de servicios por orden de producción
     *
     * @param pOprId
     * @return
     */
    public List<PopServxop> getLstPopServxOp(Long pOprId) {
        Query q = em.createNamedQuery("PopServxop.ordenProd");
        q.setParameter("oprId", pOprId);
        return q.getResultList();
    }

    /**
     * Cargar lista de servicios por venta
     *
     * @param pRgvtId
     * @return
     */
    public List<VntServxventa> getLstVntServxventa(Long pRgvtId) {
        Query q = em.createNamedQuery("VntServxventa.srvXRegVnt");
        q.setParameter("rgvtId", pRgvtId);
        return q.getResultList();
    }

    /**
     * Cargar lista de inventario
     *
     * @return
     */
    public List<InvInvent> getLstInvInvent() {
        Query q = em.createNamedQuery("InvInvent.findAll");
        return q.getResultList();
    }

    /**
     * Cargar lista de productos por servicio
     *
     * @param pVsrvId
     * @return
     */
    public List<PopProdxservxop> getlstPopProdxservxopXServicio(Long pVsrvId) {
        Query q = em.createNamedQuery("PopProdxservxop.servicio");
        q.setParameter("vsrvId", pVsrvId);
        return q.getResultList();
    }

    /**
     * Cargar lista de productos por orden de producción
     *
     * @param pOprId
     * @param pStrId
   
     * @return
     */
    public List<PopProdxservxop> getlstPopProdxservxopXOrdenProd(Long pOprId, Long pStrId) {
        Query q = em.createNamedQuery("PopProdxservxop.ordenProdXTarea");
        q.setParameter("oprId", pOprId);
        q.setParameter("strId", pStrId);
        
        return q.getResultList();
    }

    /**
     * Cargar lista de productos por servicio por orden de producción
     *
     * @param pSxoId
     * @return
     */
    public List<VntProdxsrv> getLstVntProdXServXOp(Long pSxoId) {
        Query q = em.createNamedQuery("VntProdxsrv.servXOp");
        q.setParameter("sxoId", pSxoId);
        return q.getResultList();
    }

    /**
     * Cargar lista de colaboradores por cargo
     *
     * @param pCrgId
     * @return
     */
    public List<AdmCrgxcol> getLstAdmCrgxcolXEstado(String pCrgId) {
        Query q = em.createNamedQuery("AdmCrgxcol.findByCrgXEstado");
        q.setParameter("crgId", pCrgId);
//        q.setParameter("cxcEst", pCxcEst);
        return q.getResultList();
    }

    /**
     * Cargar lista de colaboradores
     *
     * @param pCxcEst
     * @return
     */
    public List<AdmCrgxcol> getLstAdmCrgxcolXEstado(boolean pCxcEst) {
        Query q = em.createNamedQuery("AdmCrgxcol.findByCrgXActivos");
        q.setParameter("cxcEst", pCxcEst);
        return q.getResultList();
    }

    /**
     * Cargar lista de productos varios
     *
     * @param pPrdVarios
     * @return
     */
    public List<InvProducto> getLstInvProductoXVarios(boolean pPrdVarios) {
        Query q = em.createNamedQuery("InvProducto.prodVarios");
        q.setParameter("prdVarios", pPrdVarios);
        return q.getResultList();
    }

    /**
     * Editar producto por servicio por orden de producción
     *
     * @param pPopProdxservxop
     * @return
     */
    public PopProdxservxop editarProdxservxop(PopProdxservxop pPopProdxservxop) {
        pPopProdxservxop = em.merge(pPopProdxservxop);
        return pPopProdxservxop;
    }

    /**
     * Grabar orden de producción
     *
     * @param pPopOrdenprod
     * @return
     */
    public PopOrdenprod editarOrdenProd(PopOrdenprod pPopOrdenprod) {
//        popOrdenProd = em.merge(pPopOrdenprod);
        pPopOrdenprod = em.merge(pPopOrdenprod);
        return pPopOrdenprod;
    }

    /**
     * Grabar servicio por orden de producción
     *
     * @param pPopServxop
     * @return
     */
    public PopServxop editarServXOrdenProd(PopServxop pPopServxop) {
        pPopServxop = em.merge(pPopServxop);
        return pPopServxop;
    }

    public List<PopProdxservxop> grabarLstProdxservxops(List<PopProdxservxop> pLstPopProdxservxop) {
        for (PopProdxservxop ppxsxo : pLstPopProdxservxop) {
            ppxsxo = em.merge(ppxsxo);
        }
        return pLstPopProdxservxop;
    }

    public List<PopCxcevento> grabarLstCxceventos(List<PopCxcevento> pLstPopCxcevento) {
        for (PopCxcevento pcxe : pLstPopCxcevento) {
            pcxe = em.merge(pcxe);
        }
        return pLstPopCxcevento;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<PopOrdenprod> grabarLstOrdenprod(List<PopOrdenprod> pLstPopOrdenprods) {
        List<PopOrdenprod> lstPopOrdenprod = new ArrayList<>();
        for (PopOrdenprod po : pLstPopOrdenprods) {
            lstPopOrdenprod.add(em.merge(po));
        }
        return lstPopOrdenprod;
    }

    public List<PopCxccitacion> getLstPopCxccitacion(boolean estado) {
        Query q = em.createNamedQuery("PopCxccitacion.findByCxciEstado");
        q.setParameter("cxciEstado", estado);
        return q.getResultList();
    }

    public List<PopCxcrespon> getLstPopPopCxcrespon(boolean estado) {
        Query q = em.createNamedQuery("PopCxcrespon.findByCxreRespon");
        q.setParameter("cxreEstado", estado);
        return q.getResultList();
    }

    public List<PopCxcrol> getLstPopCxcrol(boolean estado) {
        Query q = em.createNamedQuery("PopCxcrol.findByCxrEstado");
        q.setParameter("cxrEstado", estado);
        return q.getResultList();
    }

    public List<PopCxcuniforme> getLstPopCxcuniforme(boolean estado) {
        Query q = em.createNamedQuery("PopCxcuniforme.findByCxuEstado");
        q.setParameter("cxuEstado", estado);
        return q.getResultList();
    }

    /**
     * @return the popOrdenProd
     */
    public PopOrdenprod getPopOrdenProd() {
        return popOrdenProd;
    }

    /**
     * @param popOrdenProd the popOrdenProd to set
     */
    public void setPopOrdenProd(PopOrdenprod popOrdenProd) {
        this.popOrdenProd = popOrdenProd;
    }

  
}
