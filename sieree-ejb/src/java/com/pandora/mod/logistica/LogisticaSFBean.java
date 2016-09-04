/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.logistica;

import adm.sys.dao.AdmCrgxcol;
import com.pandora.adm.dao.InvInvent;
import com.pandora.adm.dao.InvTipotransc;
import com.pandora.adm.dao.InvTransac;
import com.pandora.mod.logistica.dao.LgtDespachoevento;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.mod.venta.dao.VntDetevento;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
 * @author patricia
 */
@Stateful
@LocalBean
public class LogisticaSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }

    /**
     * registro venta
     *
     * @param rgvtId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntDetevento> getLstVntDetevento(Long rgvtId) {
        Query q = em.createNamedQuery("VntDetevento.detEvtXVenta");
        q.setParameter("rgvtId", rgvtId);
        return q.getResultList();
    }

    /**
     * Cargar lista de ordenes de producción por estado procesado
     *
     * @param estado
     * @param oprEstadodespacho
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PopOrdenprod> getLstOrdenProdXProcesado(Integer estado, Integer oprEstadodespacho) {
        if (oprEstadodespacho == 3) {
            Query q = em.createNamedQuery("PopOrdenprod.findByOprActivas");
            q.setParameter("eopId", estado);
            return q.getResultList();
        } else {
            Query q = em.createNamedQuery("PopOrdenprod.findByOprActivaEstadoLogistica");
            q.setParameter("eopId", estado);
            q.setParameter("oprEstadodespacho", oprEstadodespacho);
            return q.getResultList();
        }
    }

    /**
     * Cargar lista de servicios por orden producción
     *
     * @param pOprId
     * @return
     */
    public List<PopServxop> getLstPopServxopXOrdenProd(Long pOprId) {
        Query q = em.createNamedQuery("PopServxop.ordenProd");
        q.setParameter("oprId", pOprId);
        return q.getResultList();
    }

    /**
     * Cargar lista de productos por servicio
     *
     * @param pVsrvId
     * @return
     */
    public List<PopProdxservxop> getLstPopProdxservxopXServ(Long pVsrvId) {
        Query q = em.createNamedQuery("PopProdxservxop.servicio");
        q.setParameter("vsrvId", pVsrvId);
        return q.getResultList();
    }

    /**
     * Cargar lista de productos por orden de producción
     *
     * @param pOprId
     *
     * @return
     */
    public List<PopProdxservxop> getLstPopProdxservxopXOp(Long pOprId) {
        Query q = em.createNamedQuery("PopProdxservxop.ordenProd");
        q.setParameter("oprId", pOprId);
        
        return q.getResultList();
    }

    /**
     * Editar productos por servicio por orden de producción
     *
     * @param pLstPopProdxservxops
     */
    public void editarProdxServxOp(List<PopProdxservxop> pLstPopProdxservxops, boolean salida, InvTipotransc tipoMov, AdmCrgxcol cxc) {
        for (PopProdxservxop ppxsxo : pLstPopProdxservxops) {

            em.merge(ppxsxo);
            List<InvInvent> lstInv = getLsInvInventXProducto(ppxsxo.getPrdId().getPrdId());

            if (lstInv != null && !lstInv.isEmpty()) {
                if (salida) {
                    Integer cantidadSalida = ppxsxo.getPxsoCantsalida();
                    for (InvInvent i : lstInv) {
                        if (i.getInvCant() < cantidadSalida) {
                            cantidadSalida = cantidadSalida - i.getInvCant();
                            int cantidadMov = i.getInvCant();
                            crearMoviento(tipoMov, cxc, cantidadMov, i,ppxsxo.getSxoId().getOprId());
                            i.setInvCant(0);
                        } else {
                            int cantidadMov = i.getInvCant() - cantidadSalida;
                            crearMoviento(tipoMov, cxc, cantidadSalida, i,ppxsxo.getSxoId().getOprId());
                            i.setInvCant(cantidadMov);

                            break;
                        }

                    }
                } else {
                    Integer cantidadEntrada = ppxsxo.getPxsoCantentrada();
                    InvInvent i = lstInv.get(lstInv.size() - 1);

                    i.setInvCant(i.getInvCant() + cantidadEntrada);
                    crearMoviento(tipoMov, cxc, cantidadEntrada, i,ppxsxo.getSxoId().getOprId());
                    lstInv.clear();
                    lstInv.add(i);

                }
                editarLstInvInvent(lstInv);
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarLstInvInvent(List<InvInvent> lstInvInvents) {
        for (InvInvent s : lstInvInvents) {
            em.merge(s);
        }
    }

    private void crearMoviento(InvTipotransc tipotransc, AdmCrgxcol cxc, Integer cantidaMov, InvInvent inventario,PopOrdenprod op) {
        if (tipotransc != null) {
            InvTransac transaccion = new InvTransac();
            transaccion.setCxcId(cxc);
            transaccion.setIndversion(0);
            transaccion.setInvId(inventario);
            transaccion.setItrAnultr(false);
            transaccion.setItrCant(cantidaMov);
            transaccion.setItrEstado(true);
            transaccion.setItrFecpro(new Date());
            transaccion.setItrIdtranul(BigInteger.ZERO);
            transaccion.setItrKardex("OP_"+op.getOprId());
            transaccion.setItrObsr(tipotransc.getTtrNombre() + " :" + tipotransc.getTtrDesc());
            transaccion.setItrValor(BigDecimal.ZERO);
            transaccion.setTtrId(tipotransc);
            transaccion.setPopOrdenprod(op);
            editarInvTransac(transaccion);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private InvTransac editarInvTransac(InvTransac transac) {

        transac = em.merge(transac);
        //em.flush();
        return transac;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvTipotransc> getLstInvTipotransc(boolean ttrEst, boolean ttrAplicalogistica) {
        Query q = em.createNamedQuery("InvTipotransc.findByLogistica");
        q.setParameter("ttrEst", ttrEst);
        q.setParameter("ttrAplicalogistica", ttrAplicalogistica);
        return q.getResultList();
    }

    /**
     * Grabar Orden de Despacho
     *
     * @param pLgtDespachoevento
     * @return
     */
    public LgtDespachoevento grabarDespachoEvento(LgtDespachoevento pLgtDespachoevento) {
        pLgtDespachoevento = em.merge(pLgtDespachoevento);
        return pLgtDespachoevento;
    }

    /**
     * Grabar Orden Produccion
     *
     * @param pPopOrdenprod
     * @return
     */
    public PopOrdenprod editarPopOrdenprod(PopOrdenprod pPopOrdenprod) {
        pPopOrdenprod = em.merge(pPopOrdenprod);
        return pPopOrdenprod;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvInvent> getLsInvInventXProducto(Integer prdId) {
        Query q = em.createNamedQuery("InvInvent.findByProducto");
        q.setParameter("prdId", prdId);
        return q.getResultList();
    }
}
