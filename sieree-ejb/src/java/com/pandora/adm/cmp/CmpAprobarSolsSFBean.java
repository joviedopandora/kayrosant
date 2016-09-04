/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.cmp;
import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.SysSegtarea;
import com.pandora.adm.dao.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.sql.DataSource;

/**
 *
 * @author luis
 */
@Stateful
@LocalBean
public class CmpAprobarSolsSFBean extends CmpProcCompSFBeanBase {

    @Resource(name = "jdbcProcAud")
    private DataSource jdbcProcAud;
    private Connection con = null;

    /**
     * Colaboradores por paso
     *
     * @param pSpsId
     * @return
     */
    public List<AdmColxemp> getLstColXPaso(Long pSpsId) {
        Query q = em.createNamedQuery("AdmColxemp.findByGrupoXPaso");
        q.setParameter("spsId", pSpsId);
        q.setParameter("cpeEstcop", Boolean.TRUE);
        return q.getResultList();
    }

    public SysSegtarea getSysSegTareaXId(Long pSgtId) {
        try {
            return em.getReference(SysSegtarea.class, pSgtId);
        } catch (EntityNotFoundException enfe) {
            return null;

        }

    }

//    public void crearSeguimientoTareaPasoMovimientoInventario(SysTarea pStrId) {
//        SysPropaso propaso = em.getReference(SysPropaso.class, 3L);
//
//        java.util.Date fechaActual = new java.util.Date();
//        java.util.Date fechaVig = fechaActual;
//        Query q = em.createNamedQuery("SysSegtarea.updateXtarea");
//        q.setParameter("strId", pStrId.getStrId());
//        q.setParameter("sgtEstpaso", Boolean.FALSE);
//        q.setParameter("sgtEstpaso1", Boolean.TRUE);
//        q.executeUpdate();
//
//        for (AdmColxemp ce : getLstColXPaso(propaso.getSpsId())) {
//            SysSegtarea segtarea = new SysSegtarea();
//            segtarea.setSgtEstpaso(true);
//            segtarea.setSgtFcre(fechaActual);
//            segtarea.setSgtFinicio(fechaActual);
//            fechaVig = new java.util.Date(fechaActual.getYear(), fechaActual.getMonth(), fechaActual.getDate() + propaso.getSpsVigencia());
//            segtarea.setSgtFfinal(fechaVig);
//            segtarea.setCpeId(ce);
//            segtarea.setSpsId(propaso);
//            segtarea.setStrId(pStrId);
//            em.persist(segtarea);
//
//        }
//    }

    /**
     * Actualizar tarea en la requisición
     *
     * @param pStrId
     * @return
     */
    public void actStrIdRequisicionCerrada(Long pStrId) {
        Query q = em.createNamedQuery("CmpRequiscomp.actualizarStrId");
        q.setParameter("strId", pStrId);
        q.setParameter("crqAbierta", Boolean.FALSE);
        q.executeUpdate();
    }

    public CmpRequiscomp editarRequisicion(CmpRequiscomp pRequiscomp) {
        pRequiscomp = em.merge(pRequiscomp);
        return pRequiscomp;
    }

    /**
     * consolidado compra por tarea
     *
     * @param strId
     * @return
     */
    public List<CmpConsolcompra> getLstCmpConsolcompraXTarea(Long strId) {
        Query q = em.createNamedQuery("CmpConsolcompra.findByStrId");
        q.setParameter("strId", strId);
        return q.getResultList();
    }

    /**
     * cosnlidado pedido por tarea
     *
     * @param pListCmpConsolcompra
     */
    public void grabarConsolidadoCompras(List<CmpConsolcompra> pListCmpConsolcompra) {

        for (CmpConsolcompra cc : pListCmpConsolcompra) {
            em.persist(cc);
        }
    }

    public void grabarFacturaXConsCompraXTarea(Long pStrId, AdmCrgxcol cxcId) {
        Query q = em.createNamedQuery("InvProovedor.provXConsCompraXStrId");
        q.setParameter("strId", pStrId);
        List<InvProovedor> lstProvXConsCompraXStr = q.getResultList();
        for (InvProovedor invProovedor : lstProvXConsCompraXStr) {
            CmpFactura factura = new CmpFactura();
            factura.setFactEst(Boolean.TRUE);
            factura.setFactFechaproceso(new java.util.Date());
            factura.setFactPrefact(Boolean.TRUE);
            factura.setFactProcesadorecibido(Boolean.FALSE);
            factura.setPrvId(invProovedor);
            factura.setStrId(pStrId);
            factura.setCxcId(cxcId);
            factura.setFactProcesadocargainv(Boolean.FALSE);
            factura = em.merge(factura);

            q = em.createNamedQuery("CmpConsolcompra.consXProvXTarea");
            q.setParameter("prvId", invProovedor.getPrvId());
            q.setParameter("strId", pStrId);
            BigDecimal valorBrutoPrefact = new BigDecimal(0);
            for (CmpConsolcompra cc : (List<CmpConsolcompra>) q.getResultList()) {
                CmpDetallefact cmpDetallefact = new CmpDetallefact();
                cmpDetallefact.setCcmId(cc);
                cmpDetallefact.setFactId(factura);
                cmpDetallefact.setDetfEst(Boolean.TRUE);
                cmpDetallefact.setDetfRecibido(Boolean.FALSE);
                cmpDetallefact.setDetfProcesadorecibido(Boolean.FALSE);
                cmpDetallefact.setDetfProcesadocargainv(Boolean.FALSE);
                em.merge(cmpDetallefact);
                valorBrutoPrefact = valorBrutoPrefact.add(cc.getCcmPreciototal());

            }

            factura.setFactPrevalorbruto(valorBrutoPrefact);
            em.merge(factura);

        }
    }

    /**
     * Consolidado pedido sin orden de compra
     *
     * @param strId
     * @return
     */
    public List<CmpConspedido> getLstConspedidosXTarea(Long strId) {
        Query q = em.createNamedQuery("CmpConspedido.findBTareaSinOrdenCompra");
        q.setParameter("strId", strId);
        return q.getResultList();
    }

    /**
     * Elimina los Consolidados del pedido por tarea
     *
     * @param strId
     * @return
     */
    public int eliminarConsolidadoPedido(Long strId) {

        try {
            con = jdbcProcAud.getConnection();
            int exc;
            try (PreparedStatement ps = con.prepareStatement("DELETE  FROM cmp_conspedido  WHERE str_id = ?  ")) {
                ps.setLong(1, strId);
                exc = ps.executeUpdate();
            }
            return exc;

        } catch (SQLException ex) {
            Logger.getLogger(CmpAprobarSolsSFBean.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } finally {
            try {



                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CmpAprobarSolsSFBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Devuelve la cantidad de consolidado de compras por tarea.
     *
     * @param strId
     * @return
     */
    public Long getCantidadConsolidadoCompra(Long strId) {
        Query q = em.createNamedQuery("CmpConsolcompra.findByTareaCount");
        q.setParameter("strId", strId);
        return (Long) q.getSingleResult();
    }

    /**
     * Devuelve la cantidad de consolidado por Tarea
     *
     * @param strId
     * @return
     */
    public Long getCantidadConsolidadoAprobado(Long strId) {
        Query q = em.createNamedQuery("CmpConspedido.findByTareaCount");
        q.setParameter("strId", strId);
        return (Long) q.getSingleResult();
    }

    /**
     * Graba Consolidado de pedido por pendientes
     *
     * @param sql
     * @return
     */
    public int grabarConsolidadoPedido(String sql, Long strId) {

        try {
            con = jdbcProcAud.getConnection();
            Statement statement = con.createStatement();
            return statement.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(CmpAprobarSolsSFBean.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } finally {
            try {


                PreparedStatement ps = con.prepareStatement("UPDATE cmp_conspedido SET ccp_fechaproceso = now() "
                        + " WHERE str_id = ? AND ccp_fechaproceso IS NULL ");

                ps.setLong(1, strId);
                ps.executeUpdate();
                ps.close();

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CmpAprobarSolsSFBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Obtener requisiciones por estado de revisión
     *
     * @param pCrqRevisada
     * @return
     */
    public List<CmpRequiscomp> getLstReqsXEst(Boolean pCrqRevisada) {
        Query q = em.createNamedQuery("CmpRequiscomp.reqNoRevisa");
        q.setParameter("cpeEstcop", Boolean.TRUE);
        q.setParameter("crqRevisada", pCrqRevisada);
        return q.getResultList();
    }

    /**
     * Obtener inventario de un producto con sus marcas y presentaciones
     *
     * @param pPrdId
     * @return
     */
    public List<InvInvent> getLstInvInventXProd(Integer pPrdId) {
        Query q = em.createNamedQuery("InvInvent.invXProd");
        q.setParameter("prdId", pPrdId);
        return q.getResultList();
    }
    
    /**
     * Obtener inventario de un producto con sus presentaciones
     *
     * @return
     */
    public List<Object[]> getLstInvInventXProdXPsp(){
        Query q = em.createNamedQuery("InvInvent.consolidadoInvXPsp");
        return q.getResultList();
    }

    /**
     * Obtener lista de productos aprobados por solicitud y por producto
     * solicitado
     *
     * @param pPxrId
     * @return Lista de productos aprobados por solicitud
     */
    public List<CmpPxraprob> getLstCmpPxraprobs(Long pPxrId) {
        Query q = em.createNamedQuery("CmpPxraprob.praXPxrId");
        q.setParameter("pxrId", pPxrId);
        return q.getResultList();

    }

    /**
     * Obtener lista de productos aprobados por solicitud
     *
     * @param pCrqId
     * @return
     */
    public List<CmpPxraprob> getLstCmpPxraprobsXCrq(Long pCrqId) {
        Query q = em.createNamedQuery("CmpPxraprob.praXCrqId");
        q.setParameter("crqId", pCrqId);
        return q.getResultList();

    }

    public void actualizarPxrRechaza(Long pPxrId, Boolean pPxrRechaza, String obsrRechaza) {

        CmpProdxreq pCmpProdxreq = em.getReference(CmpProdxreq.class, pPxrId);
        pCmpProdxreq.setPxrRechaza(pPxrRechaza);
        pCmpProdxreq.setPxrObsrrechza(obsrRechaza);
        em.merge(pCmpProdxreq);
    }

    public void grabarProdAprob(CmpPxraprob pCmpPxraprobs) {
        try {
            Integer cantNueva = pCmpPxraprobs.getCantAprob();
            Query q = em.createNamedQuery("CmpPxraprob.praXPxrIdXMarXPsp");
            InvPresentprod invPresentprod = em.getReference(InvPresentprod.class, pCmpPxraprobs.getPspId().getPspId());
            InvMarca invMarca = em.getReference(InvMarca.class, pCmpPxraprobs.getMarId().getMarId());
            q.setParameter("pxrId", pCmpPxraprobs.getPxrId().getPxrId());
            q.setParameter("pspId", pCmpPxraprobs.getPspId().getPspId());
            q.setParameter("marId", pCmpPxraprobs.getMarId().getMarId());
            pCmpPxraprobs = (CmpPxraprob) q.getSingleResult();
            pCmpPxraprobs.setCantAprob(cantNueva);
            em.merge(pCmpPxraprobs);
        } catch (NoResultException e) {
            em.merge(pCmpPxraprobs);

        }

    }

    public void borrarProdAprob(List<CmpPxraprob> pListPxraprobs) {
        StringBuilder strBSlq = new StringBuilder();
        strBSlq.append("DELETE FROM CmpPxraprob c WHERE c.praId IN(");
        for (CmpPxraprob cmpPxraprob : pListPxraprobs) {
            strBSlq.append(cmpPxraprob.getPraId());
            strBSlq.append(",");
        }
        strBSlq.replace(strBSlq.length() - 1, strBSlq.length(), ")");
        Query q = em.createQuery(strBSlq.toString());
        q.executeUpdate();
    }

    /**
     * Obtiene la cantidad de asignaciones de productos por solicitud que no
     * estén rechazadas
     *
     * @param pPxrId
     * @return Cantidad de asignaciones, si el valor es 0 y la solicitud no está
     * rechazada, se define como no revisada
     */
    public Long getCantPxrNoAsigNoRech(Long pPxrId) {
        Query q = em.createNamedQuery("CmpProdxreq.cantProdNoAsig");
        q.setParameter("pxrId", pPxrId);
        return (Long) q.getSingleResult();
    }

    public List<CmpPxraprob> getLstAprob() {
        Query q = em.createNamedQuery("CmpPxraprob.findAll");
        return q.getResultList();
    }

    public List<CmpPxraprob> getLstCmpPxraprobXStrId(Long pStrId) {
        Query q = em.createNamedQuery("CmpPxraprob.findByStrId");
        q.setParameter("strId", pStrId);
        return q.getResultList();
    }

    public List<CmpPxraprob> getLstCmpPxraprobXReq(Long pCrqId) {
        Query q = em.createNamedQuery("CmpPxraprob.praXCrqId");
        q.setParameter("crqId", pCrqId);
        return q.getResultList();
    }

    public List<CmpRequiscomp> getLstCmpRequiscompXAbierta(boolean pCrqAbierta) {
        Query q = em.createNamedQuery("CmpRequiscomp.findByCrqAbierta");
        q.setParameter("crqAbierta", pCrqAbierta);
        return q.getResultList();
    }
    
    public List<CmpRequiscomp> getLstCmpRequiscompXAprobadoXTarea(boolean pCrqAprobado, Long pStrId) {
        Query q = em.createNamedQuery("CmpRequiscomp.findByStrId");
        //q.setParameter("crqAprobado", pCrqAprobado);
        q.setParameter("strId", pStrId);
        return q.getResultList();
    }

    public List getLstAprobNativa() {
        Query q = em.createNamedQuery("CmpPxraprob.praConsolidado");
        return q.getResultList();
    }

    public List getLstAprobNativaXSolicitud(Long pCrqId) {
        Query q = em.createNamedQuery("CmpPxraprob.praConsolidadoXSolicitud");
        q.setParameter(1, pCrqId);
        return q.getResultList();
    }
    
     public List getLstConsolidadoProdAprobXStrId(Long pStrId) {
        Query q = em.createNamedQuery("CmpPxraprob.praConsPedidoXStr");
        q.setParameter("strId", pStrId);
        return q.getResultList();
    }
     
      public List getLstConsolidadoProdAprobXCrqId( Long pCqrId) {
        Query q = em.createNamedQuery("CmpPxraprob.praConsPedidoXSolicitud");        
        q.setParameter("crqId", pCqrId);
        return q.getResultList();
    }
     
     public Long cantidadInvXPrdXPspXMar(Integer pPrdId, Integer pPspId, Integer pMarId){
     Query q  = em.createNamedQuery("InvInvent.cantidadInvXPrdXPspXMar");
     q.setParameter("prdId", pPrdId);
     q.setParameter("pspId", pPspId);
     q.setParameter("marId", pMarId);     
     return (Long) q.getSingleResult();
     }

    public List<InvProdxprov> getLstInvProdxprov(Integer pPrdId) {
        Query q = em.createNamedQuery("InvProdxprov.provXprod");
        q.setParameter("prdId", pPrdId);
        return q.getResultList();
    }

    @Override
    public void remove() {
    }
}
