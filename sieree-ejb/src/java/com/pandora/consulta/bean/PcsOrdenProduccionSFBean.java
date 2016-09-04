/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.consulta.bean;

import adm.sys.dao.AdmCrgxcol;
import com.pandora.adm.dao.InvProducto;
import com.pandora.mod.ordenprod.dao.PopCxccitacion;
import com.pandora.mod.ordenprod.dao.PopCxcevento;
import com.pandora.adm.rf.dao.RfDep;
import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.mod.logistica.dao.LgtEstadoevento;
import com.pandora.mod.ordenprod.dao.LogOrdenprod;
import com.pandora.mod.ordenprod.dao.PopCxcrespon;
import com.pandora.mod.ordenprod.dao.PopCxcrol;
import com.pandora.mod.ordenprod.dao.PopCxcuniforme;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.mod.ordenprod.dao.RfEstadoOP;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntServicio;
import com.pandora.mod.venta.dao.VntServicioxservicio;
import com.pandora.mod.venta.dao.VntServxventa;
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
public class PcsOrdenProduccionSFBean {

    @PersistenceContext
    EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntRegistroventa editarRegVenta(VntRegistroventa pVntRegistroventa) {

        pVntRegistroventa = em.merge(pVntRegistroventa);
        return pVntRegistroventa;

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarRegVentaConServ(List<VntServxventa> pLstVntServxventas) {

        for (VntServxventa vntServxventa : pLstVntServxventas) {

            vntServxventa = em.merge(vntServxventa);

        }

    }

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
     * Cargar lista de órdenes de producción por procesado y estado de despacho
     *
     * @param pOprProcesado
     * @param pEevId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PopOrdenprod> getLstPopOrdenprodXProcXEstDesp(boolean pOprProcesado, Integer pEevId) {
        Query q = em.createNamedQuery("PopOrdenprod.ordenXProcXEstDesp");
        q.setParameter("oprProcesado", pOprProcesado);
        q.setParameter("eevId", pEevId);
        return q.getResultList();
    }

    /**
     * Cargar lista de órdenes de producción procesadas
     *
     * @param pOprProcesado
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PopOrdenprod> getLstPopOrdenprodXProcesado(boolean pOprProcesado) {
        Query q = em.createNamedQuery("PopOrdenprod.ordenXProcesadas");
        q.setParameter("oprProcesado", pOprProcesado);
        return q.getResultList();
    }

    /**
     * Cargar lista de servicios por orden de producción
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PopOrdenprod> getLstPopOrdenprod() {
        Query q = em.createNamedQuery("PopOrdenprod.ordenesProd");
        return q.getResultList();
    }

    /**
     * Cargar lista de servicios por orden de producción
     *
     * @param pOprId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PopServxop> getLstPopServxOp(Long pOprId) {
        Query q = em.createNamedQuery("PopServxop.ordenProd");
        q.setParameter("oprId", pOprId);
        return q.getResultList();
    }

    /**
     * Cargar lista de productos por orden de producción
     *
     * @param pOprId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PopProdxservxop> getlstPopProdxservxopXOrdenProd(Long pOprId) {
        Query q = em.createNamedQuery("PopProdxservxop.ordenProd");
        q.setParameter("oprId", pOprId);
        return q.getResultList();
    }

    /**
     * Cargar lista de colaboradores por cargo
     *
     * @param pCrgId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvProducto> getLstInvProductoXVarios(boolean pPrdVarios) {
        Query q = em.createNamedQuery("InvProducto.prodVarios");
        q.setParameter("prdVarios", pPrdVarios);
        return q.getResultList();
    }

    /**
     * Grabar servicio por orden de producción
     *
     * @param pPopServxop
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PopServxop editarServXOrdenProd(PopServxop pPopServxop) {
        pPopServxop = em.merge(pPopServxop);
        return pPopServxop;
    }

    /**
     * Grabar lista de productos por servicio por orden de producción
     *
     * @param pLstPopProdxservxop
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<PopProdxservxop> grabarLstProdxservxops(List<PopProdxservxop> pLstPopProdxservxop) {
        for (PopProdxservxop ppxsxo : pLstPopProdxservxop) {
            ppxsxo = em.merge(ppxsxo);
        }
        return pLstPopProdxservxop;
    }

    /**
     * Grabar lista de colaboradores por orden de producción
     *
     * @param pLstPopCxcevento
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<PopCxcevento> grabarLstCxceventos(List<PopCxcevento> pLstPopCxcevento) {
        for (PopCxcevento pcxe : pLstPopCxcevento) {
            pcxe = em.merge(pcxe);
        }
        return pLstPopCxcevento;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void eliminarPopCxceventoPorOp(Long oprId) {
        Query query = em.createNamedQuery("PopCxcevento.findDeleteXOP");
        query.setParameter("oprId", oprId);
        int cantEliminada = query.executeUpdate();
        System.out.println("Cantidad eliminadas" + cantEliminada);
    }

    /**
     * Editar producto por servicio por orden de producción
     *
     * @param pPopProdxservxop
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PopOrdenprod editarOrdenProd(PopOrdenprod pPopOrdenprod) {
//        popOrdenProd = em.merge(pPopOrdenprod);
        pPopOrdenprod = em.merge(pPopOrdenprod);
        return pPopOrdenprod;
    }

    /**
     * Cargar lista de colaboradores por orden de produccion
     *
     * @param oprId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PopCxcevento> getLstAdmCrgxcolXOrdenProduccion(Long oprId) {
        Query q = em.createNamedQuery("PopCxcevento.findByOrdenProd");
        q.setParameter("oprId", oprId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRegistroventa> getLstVntRegistroventaPendienteOP(Integer tipoCliente, boolean rgvtActivarOp, boolean rgvtActCronograma) {
        Query q = em.createNamedQuery("VntRegistroventa.clienteXServiciosPendientesOdenProduccion");
        q.setParameter("tclId", tipoCliente);
        q.setParameter("rgvtActivarOp", rgvtActivarOp);
        q.setParameter("rgvtActCronograma", rgvtActCronograma);
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

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntServicioxservicio> getLstVntServicioxservicio(Long pVsrvId) {
        Query q = em.createNamedQuery("VntServicioxservicio.findByServicios");
        q.setParameter("vsrvId", pVsrvId);
        q.setParameter("vsvxsEstado", true);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PopOrdenprod editarPopOrdenprod(PopOrdenprod pPopOrdenprod) {
        pPopOrdenprod = em.merge(pPopOrdenprod);
        return pPopOrdenprod;
    }

    /**
     * Obtener id sexo cliente
     *
     * @param pRfDepa
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfDep getRfDepartament(String pRfDepa) {
        return em.find(RfDep.class, pRfDepa);
    }

    /**
     * Obtener la ciudad por id
     *
     * @param pCiudadCol
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfCiudad getRfCiudad(long pCiudadCol) {
        return em.find(RfCiudad.class, pCiudadCol);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntServicio consultarServicioPorId(Long idServcio) {

        return em.find(VntServicio.class, idServcio);
    }

    public List<PopCxccitacion> getLstPopCxccitacion(boolean estado) {
        Query q = em.createNamedQuery("PopCxccitacion.findByCxciEstado");
        q.setParameter("cxciEstado", estado);
        return q.getResultList();
    }

    public List<PopCxcrespon> getLstPopPopCxcrespon(boolean estado) {
        Query q = em.createNamedQuery("PopCxcrespon.findByCxreEstado");
        q.setParameter("cxreEstado", estado);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfDep> getLstDepartamentos() {
        Query q = em.createNamedQuery("RfDep.findAll");
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfCiudad> getLstCiudades(String pDepId) {
        Query q = em.createNamedQuery("RfCiudad.ciuXDept");
        q.setParameter("depId", pDepId);
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

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntServxventa editarVntServxventa(VntServxventa pVntServxventa) {
        pVntServxventa = em.merge(pVntServxventa);
        return pVntServxventa;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfEstadoOP> getLstRfEstadoOP(boolean eopEstado) {
        Query q = em.createNamedQuery("RfEstadoOP.findByEstado");
        q.setParameter("eopEstado", eopEstado);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfEstadoOP getRfEstadoOPById(Integer id) {
        return em.find(RfEstadoOP.class, id);
    }

    public List<PopOrdenprod> consultaOPByParametros(ConsultaOPDTO dto) {
        StringBuilder sQuery = new StringBuilder("SELECT op FROM PopOrdenprod op WHERE 1=1 ");
        if (dto.getIdOp() != null) {
            sQuery.append(" AND op.oprId = :oprId ");
        }

        if (dto.getNombreCliente() != null && !dto.getNombreCliente().trim().isEmpty() && !dto.getNombreCliente().trim().equals("")) {
            sQuery.append(" AND op.rgvtId.clnId.clnNombres LIKE :nombre OR op.rgvtId.clnId.clnAlias LIKE :nombre ");
        }
        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().trim().isEmpty() && !dto.getNumeroDocumento().trim().equals("")) {
            sQuery.append(" AND op.rgvtId.clnId.clnIdentificacion = :identificacion ");
        }
        if (dto.getIdVenta() != null) {
            sQuery.append(" AND op.rgvtId.rgvtId= :numeroVenta ");
        }
        if (dto.getFechaCreacion() != null) {
            sQuery.append(" AND op.fechacreacionop = :fechacreacionop ");
        }
        if (dto.getTituloOp() != null && !dto.getTituloOp().trim().isEmpty() && !dto.getTituloOp().trim().equals("")) {
            sQuery.append(" AND op.oprTitulo LIKE :titulo ");
        }
        if (dto.getEstadoOp() != null) {
            sQuery.append(" AND op.rfEstadoOP.eopId = :eopId ");
        }
        if (dto.getEstadoLogistica() != null) {
            sQuery.append(" AND op.eevId.eevId = :eevId ");
        }
        sQuery.append(" ORDER BY op.oprId DESC");
        Query query = em.createQuery(sQuery.toString());

        if (dto.getIdOp() != null) {
            query.setParameter("oprId", dto.getIdOp());
        }

        if (dto.getNombreCliente() != null && !dto.getNombreCliente().trim().isEmpty() && !dto.getNombreCliente().trim().equals("")) {
            query.setParameter("nombre", "%" + dto.getNombreCliente().trim().toUpperCase() + "%");

        }
        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().trim().isEmpty() && !dto.getNumeroDocumento().trim().equals("")) {
            query.setParameter("identificacion", dto.getNumeroDocumento().trim());
        }
        if (dto.getIdVenta() != null) {
            query.setParameter("numeroVenta", dto.getIdVenta());
        }
        if (dto.getFechaCreacion() != null) {
            query.setParameter("fechacreacionop", dto.getFechaCreacion());
        }
        if (dto.getTituloOp() != null && !dto.getTituloOp().trim().isEmpty() && !dto.getTituloOp().trim().equals("")) {

            query.setParameter("titulo", "%" + dto.getTituloOp().trim().toUpperCase() + "%");
        }
        if (dto.getEstadoOp() != null) {
            query.setParameter("eopId", dto.getEstadoOp());
        }
        if (dto.getEstadoLogistica() != null) {
            query.setParameter("eevId", dto.getEstadoLogistica());
        }
        return query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public LgtEstadoevento getLgtEstadoeventoById(Integer id) {
        return em.find(LgtEstadoevento.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<LogOrdenprod> consultarLogByOP(Long idOP) {
        Query query = em.createNamedQuery("LogOrdenprod.findByOprId");
        query.setParameter("oprId", idOP);
        return query.getResultList();

    }

}
