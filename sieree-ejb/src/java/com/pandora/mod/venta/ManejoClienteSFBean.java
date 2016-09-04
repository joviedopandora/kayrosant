/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta;

import adm.sys.dao.RfParentezco;
import adm.sys.dao.RfSexo;
import adm.sys.dao.RfTipodoc;
import com.pandora.mod.venta.dao.VntActeconomica;
import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntDetallecliente;
import com.pandora.mod.venta.dao.VntRfTipocliente;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

/**
 *
 * @author byrobles
 */
@Stateful
@LocalBean
public class ManejoClienteSFBean extends BaseVentaSFBean {

    private VntCliente vntClienteSel = new VntCliente();
    private VntDetallecliente vntDetalleclienteSel = new VntDetallecliente();

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
     * Cargar lista de género por estado
     *
     * @param pTdcEstado
     * @return
     */
    public List<RfSexo> getLstRfSexo() {
        Query q = em.createNamedQuery("RfSexo.findAll");
        return q.getResultList();
    }

    /**
     * Cargar lista de tipo de cliente por estado
     *
     * @param pTclEst
     * @return
     */
    public List<VntRfTipocliente> getLstVntRfTipocliente(boolean pTclEst) {
        Query q = em.createNamedQuery("VntRfTipocliente.findByTclEst");
        q.setParameter("tclEst", pTclEst);
        return q.getResultList();
    }

    /**
     * Cargar lista de actividad económica por estado
     *
     * @param pAteEstado
     * @return
     */
    public List<VntActeconomica> getLstVntActeconomicaXEstado(boolean pAteEstado) {
        Query q = em.createNamedQuery("VntActeconomica.findByAteEstado");
        q.setParameter("ateEstado", pAteEstado);
        return q.getResultList();
    }

    /**
     * Cargar lista de clientes
     *
     * @return
     */
    public List<VntCliente> getLstVntCliente() {
        Query q = em.createNamedQuery("VntCliente.findAll");
        return q.getResultList();
    }

    /**
     * Cargar lista de detalle de cliente
     *
     * @param pClnId
     * @return
     */
    public List<VntDetallecliente> getLstVntDetallecliente(Long pClnId) {
        Query q = em.createNamedQuery("VntDetallecliente.findByAllXCliente");
        q.setParameter("clnId", pClnId);
        return q.getResultList();
    }

    /**
     * Cargar lista de parentesco
     *
     * @param pPrtEst 
     * @return
     */
    public List<RfParentezco> getLstRfParentesco(boolean pPrtEst) {
        Query q = em.createNamedQuery("RfParentezco.findByPrtEst");
        q.setParameter("prtEst", pPrtEst);
        return q.getResultList();
    }

    //Obtener id tipo documento
    public RfTipodoc getRfTipodocXId(String tdcId) {
        return em.getReference(RfTipodoc.class, tdcId);
    }

    //Obtener id sexo
    public RfSexo getRfSexo(String sexId) {
        return em.getReference(RfSexo.class, sexId);
    }

    //Obtener id parentezco
    public RfParentezco getRfParentesco(Integer idParentezco) {
        return em.getReference(RfParentezco.class, idParentezco);
    }

    //Obtener id tipo cliente
    public VntRfTipocliente getVntRfTipoclienteXId(Integer tclId) {
        return em.getReference(VntRfTipocliente.class, tclId);
    }
    
    //Obtener id actividad económica
    public VntActeconomica getVntActeconomicaXId(Integer ateId){
        return em.getReference(VntActeconomica.class, ateId);
    }

    //Grabar cliente nuevo
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void grabarCliente(VntCliente pVntCliente) {
        pVntCliente = em.merge(pVntCliente);
        vntClienteSel = pVntCliente;
    }
    
    //Grabar detalle cliente
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void grabarDetalleCliente(VntDetallecliente pVntDetallecliente) {
        pVntDetallecliente = em.merge(pVntDetallecliente);
        vntDetalleclienteSel = pVntDetallecliente;
    }

    //Editar cliente
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntCliente editarCliente(VntCliente pVntCliente) {
        pVntCliente = em.merge(pVntCliente);
        return pVntCliente;
    }
    
    //Editar detalle cliente
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntDetallecliente editarDetalleCliente(VntDetallecliente pVntDetallecliente){
        pVntDetallecliente = em.merge(pVntDetallecliente);
        return pVntDetallecliente;
    }


    @Remove
    public void remove() {
    }

    /**
     * @return the vntClienteSel
     */
    public VntCliente getVntClienteSel() {
        return vntClienteSel;
    }

    /**
     * @param vntClienteSel the vntClienteSel to set
     */
    public void setVntClienteSel(VntCliente vntClienteSel) {
        this.vntClienteSel = vntClienteSel;
    }

    /**
     * @return the vntDetalleclienteSel
     */
    public VntDetallecliente getVntDetalleclienteSel() {
        return vntDetalleclienteSel;
    }

    /**
     * @param vntDetalleclienteSel the vntDetalleclienteSel to set
     */
    public void setVntDetalleclienteSel(VntDetallecliente vntDetalleclienteSel) {
        this.vntDetalleclienteSel = vntDetalleclienteSel;
    }
}
