/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.consulta.bean;

import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.adm.rf.dao.RfEstadofactura;
import com.pandora.bussiness.util.EnEstadoFactura;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.venta.dao.LogFactura;
import com.pandora.mod.venta.dao.LogRemision;
import com.pandora.mod.venta.dao.VntColxventa;
import com.pandora.mod.venta.dao.VntDetallefact;
import com.pandora.mod.venta.dao.VntDetallerem;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.mod.venta.dao.VntFactura;
import com.pandora.mod.venta.dao.VntPagoventa;
import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.mod.venta.dao.VntRangoFacturacion;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntRemision;
import com.pandora.mod.venta.dao.VntServxventa;
import com.pandora.pagocuenta.dao.FinFormapago;
import com.pandora.pagocuenta.dao.RfBanco;
import java.util.ArrayList;
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
 * @author sandra
 */
@Stateful
@LocalBean
public class PcsVentaSFBean {

    private VntRegistroventa vntRegistroventaSel = new VntRegistroventa();
    @PersistenceContext
    EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarRegVentaConServ(List<VntServxventa> pLstVntServxventas) {

        for (VntServxventa vntServxventa : pLstVntServxventas) {

            vntServxventa = em.merge(vntServxventa);

        }

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PopOrdenprod editarPopOrdenprod(PopOrdenprod pPopOrdenprod) {
        pPopOrdenprod = em.merge(pPopOrdenprod);
        return pPopOrdenprod;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfBanco getRfBancoXID(Long idBanco) {
        try {
            return em.find(RfBanco.class, idBanco);
        } catch (Exception e) {
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FinFormapago getFinFormapagoXID(Integer idForma) {
        try {
            return em.find(FinFormapago.class, idForma);
        } catch (Exception e) {
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfBanco> getLstRfBancoXEstado(boolean pEst) {
        Query q = em.createNamedQuery("RfBanco.findByBncEstado");
        q.setParameter("bncEstado", pEst);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntPagoventa> getLstVntPagoventaXVenta(Long rgvtId) {
        Query q = em.createNamedQuery("VntPagoventa.findBYVenta");
        q.setParameter("rgvtId", rgvtId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public VntRangoFacturacion getVntRangoFacturacionXTipoCliente(Integer tclId) {
        try {
            Query q = em.createNamedQuery("VntRangoFacturacion.findByTipoCliente");
            q.setParameter("tclId", tclId);
            return (VntRangoFacturacion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntRangoFacturacion editarVntRangoFacturacion(VntRangoFacturacion vntRangoFacturacion) {
        vntRangoFacturacion = em.merge(vntRangoFacturacion);
        return vntRangoFacturacion;
    }

    @Remove
    public void remove() {
    }
    //<editor-fold defaultstate="collapsed" desc="Pagos venta">

    /**
     * Cargar lista de registros de venta sin confirmar y sin pago
     *
     * @param pEstrvntId
     * @param pRgvtPagado
     * @param pRgvtEstconfirmada
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRegistroventa> getLstVntRegistroventaXPagoConf(Integer pEstrvntId, Integer pEstrvntId2, boolean pRgvtPagado, boolean pRgvtEstconfirmada) {
        Query q = em.createNamedQuery("VntRegistroventa.pagoXConf");
        q.setParameter("estrvntId", pEstrvntId);
        q.setParameter("estrvntId2", pEstrvntId2);

        q.setParameter("rgvtPagado", pRgvtPagado);
        q.setParameter("rgvtEstconfirmada", pRgvtEstconfirmada);
        return q.getResultList();
    }

    /**
     * Cargar lista de todos los registros de venta
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRegistroventa> getLstVntRegistroventa() {
        Query q = em.createNamedQuery("VntRegistroventa.regVentas");
        return q.getResultList();
    }

    /**
     * Cargar lista de forma de pago por estado
     *
     * @param pFpgEst
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public FinFormapago getFormapagoXId(Integer pFinFormapago) {
        return em.getReference(FinFormapago.class, pFinFormapago);
    }

    /**
     * Obtener lista de servicios por producto
     *
     * @param pVsrvId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntProdxsrv> getLstVntProdxsrvXServicio(Long pVsrvId) {
        Query q = em.createNamedQuery("VntProdxsrv.pxsXServicio");
        q.setParameter("vsrvId", pVsrvId);
        return q.getResultList();
    }

    /**
     * Cargar listas de registros de venta
     *
     * @param tipoConsulta
     * @param pEstrvntId
     * @param pRgvtPagado
     * @param pRgvtEstconfirmada
     *
     * @return
     */
//    public List<VntRegistroventa> getLstVntRegistroventa(Integer tipoConsulta, Integer pEstrvntId, boolean pRgvtPagado, boolean pRgvtEstconfirmada) {
//        Query q = null;
//        if (tipoConsulta == 1) {
//            q = em.createNamedQuery("VntRegistroventa.pagoXConf");
//            q.setParameter("estrvntId", pEstrvntId);
//            q.setParameter("rgvtPagado", pRgvtPagado);
//            q.setParameter("rgvtEstconfirmada", pRgvtEstconfirmada);
//        } else {
//            if (tipoConsulta == 2) {
//                q = em.createNamedQuery("VntRegistroventa.pagoXConf");
//                q.setParameter("estrvntId", pEstrvntId);
//                q.setParameter("rgvtPagado", pRgvtPagado);
//                q.setParameter("rgvtEstconfirmada", pRgvtEstconfirmada);
//            } else {
//                q = em.createNamedQuery("VntRegistroventa.findAll");
//            }
//        }
//        return q.getResultList();
//    }
    /**
     * Editar registro de venta
     *
     * @param pVntRegistroventa
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PopOrdenprod grabarOrdenProd(PopOrdenprod pPopOrdenprod) {
        pPopOrdenprod = em.merge(pPopOrdenprod);
        return pPopOrdenprod;
    }

    /**
     * Grabar factura
     *
     * @param pVntFactura
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Factura venta">
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfEstadofactura> consultarRfEstadofactura() {
        Query q = em.createNamedQuery("RfEstadofactura.findAll");

        return q.getResultList();
    }

    /**
     * Cargar lista de resgistros de venta por confirmación de pago
     *
     * @param pRgvtEstconfirmada
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRegistroventa> getLstVntRegistroventaXConf(boolean pRgvtEstconfirmada) {
        Query q = em.createNamedQuery("VntRegistroventa.pagoConfirmado");
        q.setParameter("rgvtEstconfirmada", pRgvtEstconfirmada);
        return q.getResultList();
    }

    /**
     * Cargar lista de facturas por registro de venta confirmada
     *
     * @param pRgvtEstconfirmada
     * @param pEftId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntFactura> getLstVntFacturaXRegVentaConf(boolean pRgvtEstconfirmada, Integer pEftId) {
        Query q = em.createNamedQuery("VntFactura.regVentaConf");
        q.setParameter("rgvtEstconfirmada", pRgvtEstconfirmada);
        q.setParameter("eftId", pEftId);
        return q.getResultList();
    }

    /**
     * Cargar lista de facturas por registro de venta confirmada
     *
     * @param pTclId
     * @param pEstrvntId
     * @param pRgvtEstconfirmada
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRegistroventa> getLstVntRegistroventaXCliente(Integer pTclId, Integer pEstrvntId, boolean pRgvtEstconfirmada) {
        Query q = em.createNamedQuery("VntRegistroventa.clienteXEstXConfVnt");
        q.setParameter("tclId", pTclId);
        q.setParameter("estrvntId", pEstrvntId);
        q.setParameter("estrvntId2", 3);
        q.setParameter("estrvntId3", 5);
        q.setParameter("rgvtEstconfirmada", pRgvtEstconfirmada);
        q.setParameter("rgvtFacturada", false);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRegistroventa> getLstVntRegistroventaXClientePendienteProcesar(Integer pTclId, Integer estado1, Integer estado2) {
        Query q = em.createNamedQuery("VntRegistroventa.clienteXServiciosPendientesFacturar");
        q.setParameter("tclId", pTclId);

        q.setParameter("estado1", estado1);
        q.setParameter("estado2", estado2);

        return q.getResultList();
    }

    /**
     * Cargar lista de servicios de venta por factura
     *
     * @param pVfctId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntServxventa> getLstVntServxventaXFactura(Long pVfctId) {
        Query q = em.createNamedQuery("VntServxventa.regVntXfact");
        q.setParameter("vfctId", pVfctId);
        return q.getResultList();
    }

    /**
     * Editar factura
     *
     * @param pVntFactura
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntFactura editarFactura(VntFactura pVntFactura, boolean saveLog) {
        pVntFactura = em.merge(pVntFactura);
        if (saveLog) {
            saveLogFactura(pVntFactura);
        }

        return pVntFactura;
    }

    public void saveLogFactura(VntFactura pVntFactura) {
        LogFactura log = new LogFactura();
        log.setAdmCrgxcol(pVntFactura.getCxcId());
        log.setLfFechaproceso(new Date());
        log.setRfEstadofactura(pVntFactura.getEftId());
        log.setVntFactura(pVntFactura);
        em.merge(log);
    }

    /**
     * Lista de facturas por tarea
     *
     * @param pStrId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntFactura> getLstVntFacturaXTarea(Long pStrId) {
        Query q = em.createNamedQuery("VntFactura.tareaId");
        q.setParameter("strId", pStrId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntFactura> consultarFacturasPorParemtros(ConsultaFacturaDTO dto) {
        StringBuilder qlString = new StringBuilder("SELECT v FROM VntFactura v WHERE 1=1");
        if (dto.getNombreCliente() != null && !dto.getNombreCliente().trim().isEmpty() && !dto.getNombreCliente().trim().equals("")) {
            qlString.append(" AND v.rgvtId.clnId.clnNombres LIKE :nombre OR v.rgvtId.clnId.clnAlias LIKE :nombre ");
        }
        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().trim().isEmpty() && !dto.getNumeroDocumento().trim().equals("")) {
            qlString.append(" AND v.rgvtId.clnId.clnIdentificacion = :identificacion ");
        }
        if (dto.getNumeroFactura() != null && !dto.getNumeroFactura().trim().isEmpty() && !dto.getNumeroFactura().trim().equals("")) {
            qlString.append(" AND v.vfctNumfactura= :numeroFactura ");
        }
        if (dto.getIdVenta() != null) {
            qlString.append(" AND v.rgvtId.rgvtId= :numeroVenta ");
        }
        if (dto.getFechaCreacion() != null) {
            qlString.append(" AND v.vfctFechafactura = :fechaFactura ");
        }
        if (dto.getNombreEvento() != null && !dto.getNombreEvento().trim().isEmpty() && !dto.getNombreEvento().trim().equals("")) {
            qlString.append(" AND v.rgvtId.vdeId.vdeObsr ILIKE :nombreEvento ");
        }
        if (dto.getEstadoFactura() != null) {
            switch (dto.getEstadoFactura()) {
                case 7://EnEstadoFactura.EN_PROCESO.getId()
                    qlString.append(" AND v.eftId.eftId NOT IN (")
                            .append(EnEstadoFactura.ANULADO.getId())
                            .append(",")
                            .append(EnEstadoFactura.PAGADA.getId())
                            .append(") ");
                    //query.setParameter("estadoFactura", EnEstadoFactura.ANULADO.getId());
                    //query.setParameter("estadoFactura2", EnEstadoFactura.PAGADA.getId());
                    break;
                default:
                    qlString.append(" AND v.eftId.eftId = ").append(dto.getEstadoFactura());
                    break;

            }
        }
        Query query = em.createQuery(qlString.toString());
        if (dto.getNombreCliente() != null && !dto.getNombreCliente().trim().isEmpty() && !dto.getNombreCliente().trim().equals("")) {
            query.setParameter("nombre", "%" + dto.getNombreCliente().trim() + "%");
        }
        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().trim().isEmpty() && !dto.getNumeroDocumento().trim().equals("")) {
            query.setParameter("identificacion", dto.getNumeroDocumento().trim());
        }
        if (dto.getNumeroFactura() != null && !dto.getNumeroFactura().trim().isEmpty() && !dto.getNumeroFactura().trim().equals("")) {
            query.setParameter("numeroFactura", dto.getNumeroFactura().trim());
        }
        if (dto.getIdVenta() != null) {
            query.setParameter("numeroVenta", dto.getIdVenta());
        }
        if (dto.getFechaCreacion() != null) {
            query.setParameter("fechaFactura", dto.getFechaCreacion());
        }
        if (dto.getNombreEvento() != null && !dto.getNombreEvento().trim().isEmpty() && !dto.getNombreEvento().trim().equals("")) {

            query.setParameter("nombreEvento", "%" + dto.getNombreEvento().trim() + "%");
        }

        return query.getResultList();

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRemision> consultarRemisionPorParemtros(ConsultaFacturaDTO dto) {
        StringBuilder qlString = new StringBuilder("SELECT v FROM  VntRemision v WHERE 1=1");
        if (dto.getNombreCliente() != null && !dto.getNombreCliente().trim().isEmpty() && !dto.getNombreCliente().trim().equals("")) {
            qlString.append(" AND v.rgvtId.clnId.clnNombres LIKE :nombre AND v.rgvtId.clnId.clnAlias LIKE :nombre ");
        }
        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().trim().isEmpty() && !dto.getNumeroDocumento().trim().equals("")) {
            qlString.append(" AND v.rgvtId.clnId.clnIdentificacion = :identificacion ");
        }
        if (dto.getNumeroFactura() != null && !dto.getNumeroFactura().trim().isEmpty() && !dto.getNumeroFactura().trim().equals("")) {
            qlString.append(" AND v.vrmsNumremision= :numeroFactura ");
        }
        if (dto.getIdVenta() != null) {
            qlString.append(" AND v.rgvtId.rgvtId= :numeroVenta ");
        }
        if (dto.getFechaCreacion() != null) {
            qlString.append(" AND v.vrmsFecharemision = :fechaFactura ");
        }
        if (dto.getNombreEvento() != null && !dto.getNombreEvento().trim().isEmpty() && !dto.getNombreEvento().trim().equals("")) {
            qlString.append(" AND v.rgvtId.vdeId.vdeObsr LIKE :nombreEvento ");
        }
        if (dto.getEstadoFactura() != null) {
            qlString.append(" AND v.eftId.eftId = :estadoFactura ");
        } else {
            qlString.append(" AND v.eftId.eftId NOT IN (:estadoFactura,:estadoFactura2) ");
        }

        Query query = em.createQuery(qlString.toString());
        if (dto.getNombreCliente() != null && !dto.getNombreCliente().trim().isEmpty() && !dto.getNombreCliente().trim().equals("")) {
            query.setParameter("nombre", "%" + dto.getNombreCliente().trim() + "%");
        }
        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().trim().isEmpty() && !dto.getNumeroDocumento().trim().equals("")) {
            query.setParameter("identificacion", dto.getNumeroDocumento().trim());
        }
        if (dto.getNumeroFactura() != null && !dto.getNumeroFactura().trim().isEmpty() && !dto.getNumeroFactura().trim().equals("")) {
            query.setParameter("numeroFactura", dto.getNumeroFactura().trim());
        }
        if (dto.getIdVenta() != null) {
            query.setParameter("numeroVenta", dto.getIdVenta());
        }
        if (dto.getFechaCreacion() != null) {
            query.setParameter("fechaFactura", dto.getFechaCreacion());
        }
        if (dto.getNombreEvento() != null && !dto.getNombreEvento().trim().isEmpty() && !dto.getNombreEvento().trim().equals("")) {

            query.setParameter("nombreEvento", "%" + dto.getNombreEvento().trim() + "%");
        }
        if (dto.getEstadoFactura() != null) {
            query.setParameter("estadoFactura", dto.getEstadoFactura());
        } else {
            query.setParameter("estadoFactura", EnEstadoFactura.ANULADO.getId());
            query.setParameter("estadoFactura2", EnEstadoFactura.PAGADA.getId());

        }
        return query.getResultList();

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<LogFactura> consultarLogByFactura(Long idFactura) {
        Query query = em.createNamedQuery("LogFactura.findByFactura");
        query.setParameter("vfctId", idFactura);
        return query.getResultList();

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<LogRemision> consultarLogByRemision(Long idRemision) {
        Query query = em.createNamedQuery("LogRemision.findByFactura");
        query.setParameter("vrmsId", idRemision);
        return query.getResultList();

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Remisión venta">
    /**
     * Cargar lista de remisiones por registro de venta confirmada
     *
     * @param pRgvtEstconfirmada
     * @param pEftId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRemision> getLstVntRemisionXRegVentaConf(boolean pRgvtEstconfirmada, Integer pEftId) {
        Query q = em.createNamedQuery("VntRemision.regVentaConf");
        q.setParameter("rgvtEstconfirmada", pRgvtEstconfirmada);
        q.setParameter("eftId", pEftId);
        return q.getResultList();
    }

    /**
     * Cargar lista de servicios de venta por remisión
     *
     * @param pVrmsId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntServxventa> getLstVntServxventaXRemision(Long pVrmsId) {
        Query q = em.createNamedQuery("VntServxventa.regVntXRem");
        q.setParameter("vrmsId", pVrmsId);
        return q.getResultList();
    }

    /**
     * Editar remisión
     *
     * @param pVntRemision
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntRemision editarRemision(VntRemision pVntRemision, boolean saveLog) {
        pVntRemision = em.merge(pVntRemision);
        if (saveLog) {
            saveLogRemision(pVntRemision);
        }
        return pVntRemision;
    }

    public void saveLogRemision(VntRemision pVntRemision) {
        LogRemision log = new LogRemision();
        log.setAdmCrgxcol(pVntRemision.getCxcId());
        log.setLrFechaproceso(new Date());
        log.setRfEstadofactura(pVntRemision.getEftId());
        log.setVntRemision(pVntRemision);
        em.merge(log);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfEstadofactura getRfEstadofacturaXID(Integer id) {
        Query q = em.createNamedQuery("RfEstadofactura.findByEftId");
        q.setParameter("eftId", id);
        return (RfEstadofactura) q.getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntPagoventa editarPago(VntPagoventa pVntPagoventa) {
        pVntPagoventa = em.merge(pVntPagoventa);
        return pVntPagoventa;
    }

    /**
     * lista de facturas por venta
     *
     * @param rgvtId
     * @param eftId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntFactura> getLstVntFacturaXventa(Long rgvtId, Integer eftId) {
        Query q = em.createNamedQuery("VntFactura.findByregVenta");
        q.setParameter("rgvtId", rgvtId);
        q.setParameter("eftId", eftId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRemision> getLstVntRemisionXventa(Long rgvtId, Integer eftId) {
        Query q = em.createNamedQuery("VntRemision.findByRegVenta");
        q.setParameter("rgvtId", rgvtId);
        q.setParameter("eftId", eftId);
        return q.getResultList();
    }
    //</editor-fold>
}
