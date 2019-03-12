
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.venta;

import com.pandora.consulta.bean.ConsultaFacturaDTO;
import adm.sys.dao.AdmNotificacion;
import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.adm.rf.dao.RfMotivoevento;
import com.pandora.bussiness.util.EnEstadoFactura;
import com.pandora.mod.ordenprod.dao.PopProdxservxop;
import com.pandora.mod.ordenprod.dao.PopServxop;
import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntColxventa;
import com.pandora.mod.venta.dao.VntDetallefact;
import com.pandora.mod.venta.dao.VntDetallerem;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.mod.venta.dao.VntEstventa;
import com.pandora.mod.venta.dao.VntFactura;
import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntRemision;
import com.pandora.mod.venta.dao.VntServicio;
import com.pandora.mod.venta.dao.VntServxventa;
import com.pandora.pagocuenta.dao.FinFormapago;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateful
@LocalBean
public class VentaSFBean extends BaseVentaSFBean {

    private VntRegistroventa vntRegistroventaSel = new VntRegistroventa();
    private VntCliente vntClienteSel = new VntCliente();

    public List<VntCliente> getLstClienteXEst() {
        Query q = em.createNamedQuery("VntCliente.findAll");
        return q.getResultList();
    }

    public VntCliente getVntClienteXId(Long pClnId) {
        return em.find(VntCliente.class, pClnId);
    }

    public List<VntCliente> getLstClienteXTexto(String txtBuscar) {
        Query q = em.createNamedQuery("VntCliente.clnXTexto");
        q.setParameter("txtBuscar", "%" + txtBuscar + "%");
        return q.getResultList();
    }

    public List<VntRegistroventa> getLstRegistroventaXPasoXEstVenta(Long pPspId, Boolean pSgtEstpaso, Integer pEstrvntId) {
        Query q = em.createNamedQuery("VntRegistroventa.revVentaXPspIdXEstventa");
        q.setParameter("spsId", pPspId);
        q.setParameter("sgtEstpaso", pSgtEstpaso);
        q.setParameter("estrvntId", pEstrvntId);
        return q.getResultList();
    }

    public List<FinFormapago> getLstFinFormapagoXEstado(boolean pFpgEst) {
        Query q = em.createNamedQuery("FinFormapago.findByFpgEst");
        q.setParameter("fpgEst", pFpgEst);
        return q.getResultList();
    }

    public FinFormapago getFormapagoXId(Integer pFinFormapago) {
        return em.getReference(FinFormapago.class, pFinFormapago);
    }

    public VntRegistroventa getVntRegistroventaXStrId(Long pStrId, Boolean pRgvtAnulado) {
        try {
            Query q = em.createNamedQuery("VntRegistroventa.revVentaXStrIdXEstAnulado");
            q.setParameter("strId", pStrId);
            q.setParameter("rgvtAnulado", pRgvtAnulado);
            vntRegistroventaSel = (VntRegistroventa) q.getSingleResult();
            return vntRegistroventaSel;
        } catch (NoResultException | NonUniqueResultException ex) {
            return null;
        }
    }

    public List<VntRegistroventa> getLstVntRegistroventaXCliente(Long pClnId, Integer pEstrvntId) {
        Query q = em.createNamedQuery("VntRegistroventa.revVentaXClienteXEstventa");
        q.setParameter("clnId", pClnId);
        q.setParameter("estrvntId", pEstrvntId);
        return q.getResultList();
    }

    public List<VntServicio> getLstVntServicioXCliente(Long pRgvtId) {
        Query q = em.createNamedQuery("VntServicio.servXCliente");
        q.setParameter("rgvtId", pRgvtId);
        return q.getResultList();
    }

    public List<VntServicio> getLstServicios() {
        Query q = em.createNamedQuery("VntServicio.findAll");
        return q.getResultList();
    }

    public VntDetevento getDeteventoXRegVenta(Long pRgvtId) {
        try {
            Query q = em.createNamedQuery("VntDetevento.detEvtXVenta");
            q.setParameter("rgvtId", pRgvtId);
            return (VntDetevento) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
//    
//    public VntRegistroventa getVntRegistroventaXColXEstXPaso(Boolean pEstPaso, Integer pCxcId, Long pSpsId ){
//    Query q = em
//        return null;

    public void editarRegVenta(VntRegistroventa pVntRegistroventa) {
        if (pVntRegistroventa.getVdeId() != null) {
            pVntRegistroventa.getVdeId().setCiuId(em.getReference(RfCiudad.class, pVntRegistroventa.getVdeId().getCiuId().getCiuId()));
        }
        pVntRegistroventa = em.merge(pVntRegistroventa);
        vntRegistroventaSel = pVntRegistroventa;
    }

    //Obetener lista de productos por servicio
    public List<VntProdxsrv> getLstVntProdxsrvXServicio(Long pVsrvId) {
        Query q = em.createNamedQuery("VntProdxsrv.pxsXServicio");
        q.setParameter("vsrvId", pVsrvId);
        return q.getResultList();
    }

    //Obtener lista de productos por servicio por op
    public List<PopProdxservxop> getLstPopProdxservxopXServicio(Long pVsrvId) {
        Query q = em.createNamedQuery("PopProdxservxop.servicio");
        q.setParameter("vsrvId", pVsrvId);
        return q.getResultList();
    }

    //Grabar servicio por orden de producción
    public PopServxop grabarServXOrdenProd(PopServxop pLstPopServxop) {
        pLstPopServxop = em.merge(pLstPopServxop);
        return pLstPopServxop;
    }

    public List<PopServxop> getLstPopServxopXRe() {
        Query q = em.createNamedQuery("");
        q.setParameter("", q);
        return q.getResultList();
    }

    public VntFactura grabarFactura(VntFactura pVntFactura) {
        pVntFactura = em.merge(pVntFactura);
        return pVntFactura;
    }

    public VntDetallefact grabarDetalleFact(VntDetallefact pVntDetallefact) {
        pVntDetallefact = em.merge(pVntDetallefact);
        return pVntDetallefact;
    }

    public VntRemision grabarRemision(VntRemision pVntRemision) {
        pVntRemision = em.merge(pVntRemision);
        return pVntRemision;
    }

    public VntDetallerem grabarDetalleRem(VntDetallerem pVntDetallerem) {
        pVntDetallerem = em.merge(pVntDetallerem);
        return pVntDetallerem;
    }

    public List<RfMotivoevento> getLstRfMotivoeventoXEstado(boolean pMteEstado) {
        Query q = em.createNamedQuery("RfMotivoevento.findByMteEstado");
        q.setParameter("mteEstado", pMteEstado);
        return q.getResultList();
    }

    public RfMotivoevento motivoEventoXId(Integer pMteId) {
        return em.getReference(RfMotivoevento.class, pMteId);
    }

    public VntEstventa getEstventaXId(Integer pEstrvntId) {
        return em.find(VntEstventa.class, pEstrvntId);
    }

    public void editarRegVentaConServ(VntRegistroventa pVntRegistroventa, List<VntServxventa> pLstVntServxventas) {
        pVntRegistroventa = em.merge(pVntRegistroventa);
        for (VntServxventa vntServxventa : pLstVntServxventas) {
            vntServxventa.setRgvtId(pVntRegistroventa);
            vntServxventa = em.merge(vntServxventa);
            pVntRegistroventa.getVntServxventaList().add(vntServxventa);
        }
        for (VntColxventa colxventa : pVntRegistroventa.getVntColxventaList()) {
            colxventa = em.merge(colxventa);
        }
        vntRegistroventaSel = pVntRegistroventa;
    }

    public List<VntServxventa> getLstServxventaXVnt(Long pRgvtId) {
        Query q = em.createNamedQuery("VntServxventa.srvXRegVnt");
        q.setParameter("rgvtId", pRgvtId);
        return q.getResultList();
    }

    public List<VntServxventa> getLstVntServxventaXCteYEstV(Long pClnId, Integer pEstrvntId) {
        Query q = em.createNamedQuery("VntServxventa.srvXCteYEstV");
        q.setParameter("clnId", pClnId);
        q.setParameter("estrvntId", pEstrvntId);
        return q.getResultList();
    }

    public List<VntRegistroventa> getLstVntRegistroventaXCteYEstV(Long pClnId, Integer pEstrvntId) {
        Query q = em.createNamedQuery("VntRegistroventa.clienteXEstVnt");
        q.setParameter("clnId", pClnId);
        q.setParameter("estrvntId", pEstrvntId);
        return q.getResultList();
    }

    public void editarLstRegVenta(List<VntRegistroventa> pVntRegistroventas) {
        for (VntRegistroventa vntRegistroventa : pVntRegistroventas) {
            vntRegistroventa = em.merge(vntRegistroventa);
        }
    }

    public List<VntRegistroventa> getVntRegistroventasXStrId(Long pStrId) {
        Query q = em.createNamedQuery("VntRegistroventa.findAll");
        q.setParameter("strId", pStrId);
        return q.getResultList();
    }

    @Remove
    public void remove() {
    }

    /**
     * @return the vntRegistroventaSel
     */
    public VntRegistroventa getVntRegistroventaSel() {
        return vntRegistroventaSel;
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

    public void setVntRegistroventaSel(VntRegistroventa vntRegistroventaSel) {
        this.vntRegistroventaSel = vntRegistroventaSel;
    }

    public List<AdmNotificacion> getLstAdmNotificacion(int estado1, int estado2, Integer tclId) {
        Query q = em.createNamedQuery("AdmNotificacion.findByNotEstadoPendientes");
        q.setParameter("notEstado1", estado1);
        q.setParameter("notEstado2", estado2);
        q.setParameter("tclId", tclId);
        return q.getResultList();
    }

    public AdmNotificacion editarAdmNotificacion(AdmNotificacion notificacion) {

        notificacion = em.merge(notificacion);
        return notificacion;

    }

    public List<VntFactura> consultarFacturasPorParemtros(ConsultaFacturaDTO dto) {
        StringBuilder qlString = new StringBuilder("SELECT v FROM VntFactura v ");
        if (dto.getNombreCliente() != null && !dto.getNombreCliente().trim().isEmpty() && !dto.getNombreCliente().trim().equals("")) {
            qlString.append(" AND v.rgvtId.clnId.clnNombres LIKE :nombre AND v.rgvtId.clnId.clnAlias LIKE :nombre ");
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

}
