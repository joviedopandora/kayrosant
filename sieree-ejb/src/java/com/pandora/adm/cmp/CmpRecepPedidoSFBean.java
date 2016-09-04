/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.cmp;

import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.SysTarea;
import com.pandora.adm.dao.CmpConspedido;
import com.pandora.adm.dao.CmpDetremision;
import com.pandora.adm.dao.CmpEntregapedido;
import com.pandora.adm.dao.CmpRemisioninv;
import com.pandora.adm.dao.InvInvent;
import com.pandora.adm.dao.InvMarca;
import com.pandora.adm.dao.InvPresentprod;
import com.pandora.adm.dao.InvProducto;
import com.pandora.adm.dao.InvTipotransc;
import com.pandora.adm.dao.InvTransac;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateful
@LocalBean
public class CmpRecepPedidoSFBean {

    @PersistenceContext
    EntityManager em;
    List<CmpEntregapedido> lstCmpEntregapedidosInventario = new ArrayList<>();
    AdmCrgxcol admCrgxcolLog = new AdmCrgxcol();
    SysTarea sysTareaActual = new SysTarea();

    public void setSysTareaActual(SysTarea sysTareaActual) {
        this.sysTareaActual = sysTareaActual;
    }

    public void setAdmCrgxcolLog(AdmCrgxcol admCrgxcolLog) {
        this.admCrgxcolLog = admCrgxcolLog;
    }

    public List<CmpEntregapedido> getLstCmpEntregapedidos() {
        Query q = em.createNamedQuery("CmpEntregapedido.findAll");
        return q.getResultList();

    }

    public List<CmpEntregapedido> getLstCmpEntregapedidosXUsuarioXTarea(Integer pCxcId, Long pStrId) {
        Query q = em.createNamedQuery("CmpEntregapedido.findByColaboradorXTarea");
        q.setParameter("cxcId", pCxcId);
        q.setParameter("strId", pStrId);
        return q.getResultList();

    }

    public List<CmpRemisioninv> getLstCmpRemisioninvXStr(Long pStrId) {
        Query q = em.createNamedQuery("CmpRemisioninv.findByStrId");
        q.setParameter("strId", pStrId);
        return q.getResultList();
    }

    public CmpRemisioninv grabarRemision(CmpRemisioninv pCmpRemisioninv) {
        return em.merge(pCmpRemisioninv);
    }

    public CmpDetremision grabarDetalleRemision(CmpDetremision pCmpDetremision) {

        pCmpDetremision = em.merge(pCmpDetremision);
        InvTipotransc invTipotransc = em.getReference(InvTipotransc.class, 2);
        InvPresentprod invPresentprod = pCmpDetremision.getPraId().getPspId();
        InvMarca invMarca = pCmpDetremision.getPraId().getMarId();
        InvProducto invProducto = pCmpDetremision.getPraId().getPxrId().getPrdId();
        Query q = em.createNamedQuery("InvInvent.InvXPrdXPspXMar");
        //prdId AND i.pspId.pspId = :pspId AND i.marId.marId = :marId "
        q.setParameter("prdId", invProducto.getPrdId());
        q.setParameter("pspId", invPresentprod.getPspId());
        q.setParameter("marId", invMarca.getMarId());
        List<InvInvent> lstInvInvent = q.getResultList();
        Integer cantRestar = pCmpDetremision.getPraId().getCantAprob();
        for (InvInvent invInvent : lstInvInvent) {
            Integer cantInv = invInvent.getInvCant();
            InvTransac invTransac = new InvTransac();
            invTransac.setCxcId(admCrgxcolLog);
            invTransac.setRmiId(pCmpDetremision.getRmiId());
            invTransac.setItrCant(cantInv - cantRestar);
            invTransac.setItrEstado(true);
            invTransac.setItrFecpro(new Date());
            invTransac.setItrObsr("Retiro inventario desde remisión");
            invTransac.setTtrId(invTipotransc);
            


            invTransac.setStrId(sysTareaActual.getStrId());


            if (pCmpDetremision.getPraId().getCantAprob() <= invInvent.getInvCant()) {
                invInvent.setInvCant(cantInv - cantRestar);
                invTransac.setItrValor(invInvent.getInvPrecio().multiply(new BigDecimal(cantInv - cantRestar)));
                invInvent = em.merge(invInvent);
                invTransac.setInvId(invInvent);


                break;
            } else {
                invInvent.setInvCant(0);
                invInvent = em.merge(invInvent);
                invTransac.setInvId(invInvent);
                cantRestar = cantInv;
            }

            invTransac = em.merge(invTransac);

        }
        return pCmpDetremision;
    }

    public void grabarEntregaPedido(List<CmpEntregapedido> pLstCmpEntregapedidos) {
        for (CmpEntregapedido ce : pLstCmpEntregapedidos) {
            em.merge(ce);
        }
        lstCmpEntregapedidosInventario = pLstCmpEntregapedidos;
    }

    private void retirarDeInventario() {
        for (CmpEntregapedido cmpEntregapedido : lstCmpEntregapedidosInventario) {
            InvTransac invTransac = new InvTransac();
            //invTransac.setCxcId();
        }
    }

    public List<CmpEntregapedido> getLstEntregaPedidoXUColXTarea(Integer pCxcId, Long pStrId) {
        Query q = em.createNamedQuery("CmpEntregapedido.findByColaborador");
        q.setParameter("cxcId", pCxcId);
        q.setParameter("strId", pStrId);
        return q.getResultList();
    }

    /**
     * Grabar entrega pedido
     */
    public void grabarEntregaPedidoXTarea(Long pStrId) {
        Query q = em.createNamedQuery("CmpConspedido.findByTareaInventario");
        q.setParameter("strId", pStrId);
        q.setParameter("ccpCantinv", 0);

        List<CmpConspedido> cmpConspedidos = q.getResultList();
        List<CmpEntregapedido> lstCmpEntregapedidos = new ArrayList<>();
        for (CmpConspedido cc : cmpConspedidos) {
            CmpEntregapedido cmpEntregapedido = new CmpEntregapedido();
            // cmpEntregapedido.setCcpId(cc);
            cmpEntregapedido.setEtpEntregado(false);
            cmpEntregapedido.setEtpFechaproceso(new java.util.Date());
            lstCmpEntregapedidos.add(cmpEntregapedido);

        }

        grabarEntregaPedido(lstCmpEntregapedidos);

    }

    @Remove
    public void remove() {
    }
}
