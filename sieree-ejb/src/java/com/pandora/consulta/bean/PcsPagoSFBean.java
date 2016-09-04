/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.consulta.bean;

import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.venta.dao.VntDetallefact;
import com.pandora.mod.venta.dao.VntDetallerem;
import com.pandora.mod.venta.dao.VntFactura;
import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntRemision;
import com.pandora.mod.venta.dao.VntServxventa;
import com.pandora.pagocuenta.dao.FinFormapago;
import java.util.ArrayList;
import java.util.List;
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
 * @author sandra
 */
@Stateful
@LocalBean
public class PcsPagoSFBean {
    
    private VntRegistroventa vntRegistroventaSel = new VntRegistroventa();
    
    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {

    }
    
    /**
     * Cargar lista de registros de venta sin confirmar y sin pago
     *
     * @param pEstrvntId 
     * @param pRgvtPagado
     * @param pRgvtEstconfirmada
     * @return
     */
    public List<VntRegistroventa> getLstVntRegistroventaXPagoConf(Integer pEstrvntId, boolean pRgvtPagado, boolean pRgvtEstconfirmada) {
        Query q = em.createNamedQuery("VntRegistroventa.pagoXConf");
        q.setParameter("estrvntId", pEstrvntId);
        q.setParameter("rgvtPagado", pRgvtPagado);
        q.setParameter("rgvtEstconfirmada", pRgvtEstconfirmada);
        return q.getResultList();
    }
    
    /**
     * Cargar lista de forma de pago por estado
     *
     * @param pFpgEst
     * @return
     */
    public List<FinFormapago> getLstFinFormapagoXEstado(boolean pFpgEst) {
        Query q = em.createNamedQuery("FinFormapago.findByFpgEst");
        q.setParameter("fpgEst", pFpgEst);
        return q.getResultList();
    }

    /**
     * Cargar lista de servicios por venta
     *
     * @param pRgvtId
     * @return
     */
    public List<VntServxventa> getLstServxventaXVnt(Long pRgvtId) {
        Query q = em.createNamedQuery("VntServxventa.srvXRegVnt");
        q.setParameter("rgvtId", pRgvtId);
        return q.getResultList();
    }

    /**
     * Obtener id de forma de pago
     *
     * @param pFinFormapago
     * @return
     */
    public FinFormapago getFormapagoXId(Integer pFinFormapago) {
        return em.getReference(FinFormapago.class, pFinFormapago);
    }

    /**
     * Obtener lista de servicios por producto
     *
     * @param pVsrvId 
     * @return
     */
    public List<VntProdxsrv> getLstVntProdxsrvXServicio(Long pVsrvId) {
        Query q = em.createNamedQuery("VntProdxsrv.pxsXServicio");
        q.setParameter("vsrvId", pVsrvId);
        return q.getResultList();
    }

    /**
     * Editar registro de venta
     *
     * @param pVntRegistroventa
     */
    public void editarRegVenta(VntRegistroventa pVntRegistroventa) {
        if (pVntRegistroventa.getVdeId() != null) {
            pVntRegistroventa.getVdeId().setCiuId(em.getReference(RfCiudad.class, pVntRegistroventa.getVdeId().getCiuId().getCiuId()));
        }
        pVntRegistroventa = em.merge(pVntRegistroventa);
        vntRegistroventaSel = pVntRegistroventa;
    }

    /**
     * Grabar orden de producción
     *
     * @param pLstPopOrdenprods 
     * @return 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<PopOrdenprod> grabarLstOrdenprod(List<PopOrdenprod> pLstPopOrdenprods) {
        List<PopOrdenprod> lstPopOrdenprod = new ArrayList<>();
        for (PopOrdenprod po : pLstPopOrdenprods) {
            lstPopOrdenprod.add(em.merge(po));
        }
        return lstPopOrdenprod;
    }
    
    /**
     * Grabar orden de producción
     *
     * @param pPopOrdenprod 
     * @return 
     */
    public PopOrdenprod grabarOrdenProd(PopOrdenprod pPopOrdenprod){
        pPopOrdenprod= em.merge(pPopOrdenprod);
        return pPopOrdenprod;
    }

    /**
     * Grabar factura
     *
     * @param pVntFactura  
     * @return 
     */
    public VntFactura grabarFactura(VntFactura pVntFactura) {
        pVntFactura = em.merge(pVntFactura);
        return pVntFactura;
    }
    
    /**
     * Grabar detalle factura
     *
     * @param pVntDetallefact   
     * @return 
     */
    public VntDetallefact grabarDetalleFact(VntDetallefact pVntDetallefact) {
        pVntDetallefact = em.merge(pVntDetallefact);
        return pVntDetallefact;
    }

    /**
     * Grabar remisión
     *
     * @param pVntRemision    
     * @return 
     */
    public VntRemision grabarRemision(VntRemision pVntRemision) {
        pVntRemision = em.merge(pVntRemision);
        return pVntRemision;
    }

    /**
     * Grabar detalle remisión
     *
     * @param pVntDetallerem     
     * @return 
     */
    public VntDetallerem grabarDetalleRem(VntDetallerem pVntDetallerem) {
        pVntDetallerem = em.merge(pVntDetallerem);
        return pVntDetallerem;
    }

    /**
     * Obtener lista de productos por servicio por op
     *
     * @param pVsrvId   
     * @return 
     */
    public List<PopProdxservxop> getLstPopProdxservxopXServicio(Long pVsrvId) {
        Query q = em.createNamedQuery("PopProdxservxop.servicio");
        q.setParameter("vsrvId", pVsrvId);
        return q.getResultList();
    }
    
    /**
     * @return the vntRegistroventaSel
     */
    public VntRegistroventa getVntRegistroventaSel() {
        return vntRegistroventaSel;
    }

    /**
     * @param vntRegistroventaSel the vntRegistroventaSel to set
     */
    public void setVntRegistroventaSel(VntRegistroventa vntRegistroventaSel) {
        this.vntRegistroventaSel = vntRegistroventaSel;
    }
}
