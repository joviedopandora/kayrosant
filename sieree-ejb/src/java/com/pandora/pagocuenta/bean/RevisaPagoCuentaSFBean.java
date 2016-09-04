/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.pagocuenta.bean;

import com.pandora.adm.dao.CmpConsolcompra;
import com.pandora.pagocuenta.dao.FinAprobado;
import com.pandora.pagocuenta.dao.FinApruebacompra;
import com.pandora.pagocuenta.dao.FinCuenta;
import com.pandora.pagocuenta.dao.FinImpxcta;
import com.pandora.pagocuenta.dao.FinRfImpuesto;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author byrobles
 */
@Stateful
@LocalBean
public class RevisaPagoCuentaSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }

    //<editor-fold defaultstate="collapsed" desc="Compra">
    public List<CmpConsolcompra> cargarComprasAprobadas(Integer paprueba) {
        Query q = em.createNamedQuery("CmpConsolcompra.findByComprasAprovadas");
        q.setParameter("aprueba", paprueba);
        return q.getResultList();
    }

    public List<CmpConsolcompra> getLstconsolaCompra() {
        Query q = em.createNamedQuery("CmpConsolcompra.findAll");
        return q.getResultList();
    }

    public void grabarApruebacompra(FinApruebacompra pApruebacompra) {
        em.merge(pApruebacompra);
    }

    public void grabarLstComprasAprobadas(List<FinApruebacompra> plApruebacompras) {
        for (FinApruebacompra finApruebacompra : plApruebacompras) {
            finApruebacompra = em.merge(finApruebacompra);
        }
    }

    public void grabarLstImpuestosAcordados(List<FinApruebacompra> pLstapruebacompras) {
        for (FinApruebacompra finImpu : pLstapruebacompras) {
            finImpu = em.merge(finImpu);
        }
    }

    public void grabarLstCuentasXOrdenCompra(List<FinCuenta> pLstFinCuentas) {
        for (FinCuenta finCuenta : pLstFinCuentas) {
            
            finCuenta = em.merge(finCuenta);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Revisa pago cuenta">

    //Obtener lista de cuentas activas
    public List<FinCuenta> getLstCuentasActivas() {
        Query q = em.createNamedQuery("FinCuenta.findAll");
        return q.getResultList();
    }

    //Obtener lista de cuentas no revisadas
    public List<FinCuenta> getLstFinCuentaXRevisado(boolean ctaRevisado) {
        Query q = em.createNamedQuery("FinCuenta.findByCtaRevisado");
        q.setParameter("ctaRevisado", ctaRevisado);
        return q.getResultList();
    }
    

    //Obtener lista de impuestos por cuenta
    public List<FinImpxcta> getLstFinImpxctaXCta(Long pCtaId) {
        Query q = em.createNamedQuery("FinImpxcta.impXCta");
        q.setParameter("ctaId", pCtaId);
        return q.getResultList();
    }
    
    //Obtener lista de impuestos por estado
    public List<FinRfImpuesto> getLstFinRfImpuestoXEstado(boolean impEstado) {
        Query q = em.createNamedQuery("FinRfImpuesto.findByImpEstado");
        q.setParameter("impEstado", impEstado);
        return q.getResultList();
    }

    //Obtener lista de impuestos por estado y tipo
    public List<FinRfImpuesto> getLstFinRfImpuestoXEstadoXTipoImp(Long timId, boolean impEstado) {
        Query q = em.createNamedQuery("FinRfImpuesto.findByImpEstadoXTipoImp");
        q.setParameter("timId", timId);
        q.setParameter("impEstado", impEstado);
        return q.getResultList();
    }
    
    //Obtener id impuesto
    public FinRfImpuesto getFinRfImpuestoXId(Long pImpId) {
        try{
            Query q = em.createNamedQuery("FinRfImpuesto.findByImpId");
            q.setParameter("impId", pImpId);
            return (FinRfImpuesto) q.getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }
    
    //Cantidad de facturas revisadas
    public Long getCantFactRevisadasXTarea(boolean ctaRevisado, Long strId){
        Query q = em.createNamedQuery("FinCuenta.findByCantXRevXStrId");
        q.setParameter("ctaRevisado", ctaRevisado);
        q.setParameter("strId", strId);
        return (Long) q.getSingleResult();
    }

    //Grabar impuesto por cuenta
    public void editarImpXCta(List<FinImpxcta> pImpxctas) {
        for (FinImpxcta finImpxcta : pImpxctas) {
            em.merge(finImpxcta);
        }
    }

    /**
     * Grabar revsión pago de cuenta
     *
     * @param pFinCuenta
     */
    public void editarFinCuenta(FinCuenta pFinCuenta) {
        em.merge(pFinCuenta);
    }

    /**
     * Grabar cuentas revisadas y grabar en aprobado para gestionar la
     * aprobación de los pagos
     *
     * @param pLstAprobado
     */
//    @TransactionAttribute(TransactionAttributeType.REQUIRED)
//    public void grabarFinCuentaRevYAprueba(List<FinAprobado> pLstAprobado) {
//        for (FinAprobado finAprobado : pLstAprobado) {
//            FinCuenta fc = finAprobado.getCtaId();
//            finAprobado.setCtaId(em.merge(fc));
//            em.merge(finAprobado);
//        }
//    }
    
    /**
     * Grabar cuentas revisadas
     *
     * @param pFinCuenta
     */ 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void grabarFinCuenta(FinCuenta pFinCuenta) {
        pFinCuenta = em.merge(pFinCuenta);
    }
    
    /**
     * Grabar en aprobación de pagos
     *
     * @param pFinAprobado
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void grabarFinAprobado(FinAprobado pFinAprobado) {
        pFinAprobado = em.merge(pFinAprobado);
    }
    
    /**
     * Grabar impuestos por cuenta
     *
     * @param pImpxcta
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void grabarImpuestoXCuenta(FinImpxcta pImpxcta){
        pImpxcta = em.merge(pImpxcta);
    }

    //</editor-fold>
}
