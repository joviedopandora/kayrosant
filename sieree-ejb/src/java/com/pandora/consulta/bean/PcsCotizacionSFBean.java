/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.consulta.bean;

import adm.sys.dao.RfParentezco;
import adm.sys.dao.RfSexo;
import adm.sys.dao.RfTipodoc;
import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.adm.rf.dao.RfMotivoevento;
import com.pandora.mod.venta.dao.RfCargocontacto;
import com.pandora.mod.venta.dao.RfComocontacto;
import com.pandora.mod.venta.dao.RfTipocierrellamada;
import com.pandora.mod.venta.dao.ServRfTipocliente;
import com.pandora.mod.venta.dao.VntActeconomica;
import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntColxventa;
import com.pandora.mod.venta.dao.VntDetallecliente;
import com.pandora.mod.venta.dao.VntDetevento;
import com.pandora.mod.venta.dao.VntEstventa;
import com.pandora.mod.venta.dao.VntRangoedadsexo;
import com.pandora.mod.venta.dao.VntRangoedadsexxservicio;
import com.pandora.mod.venta.dao.VntRegistroLlamada;
import com.pandora.mod.venta.dao.VntRegistroventa;
import com.pandora.mod.venta.dao.VntRfTipocliente;
import com.pandora.mod.venta.dao.VntServicio;
import com.pandora.mod.venta.dao.VntServxventa;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sandra
 */
@Stateful
@LocalBean
public class PcsCotizacionSFBean implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }
    private VntRegistroventa vntRegistroventa = new VntRegistroventa();

    //<editor-fold defaultstate="collapsed" desc="Cliente">
    /**
     *
     * @param documento
     * @param idCliente
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntCliente> getLstClienteXIdentificacion(String documento, Long idCliente) {
        Query q = null;
        if (idCliente == null) {
            q = em.createNamedQuery("VntCliente.findByClnIdentificacion");
            q.setParameter("clnIdentificacion", documento);

        } else {
            q = em.createNamedQuery("VntCliente.findByNumIdentXDiferente");
            q.setParameter("clnIdentificacion", documento);
            q.setParameter("clnId", idCliente);
        }

        return q.getResultList();
    }

    /**
     * Buscar cliente por número de identificación o nombre
     *
     * @param txtBuscar
     * @param tclId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntCliente> getLstClienteXTexto(String txtBuscar, Integer tclId) {
        Query q = em.createNamedQuery("VntCliente.clnXTexto");
        q.setParameter("txtBuscar", "%" + txtBuscar + "%");
        q.setParameter("tclId", tclId);
        return q.getResultList();
    }

    /**
     * Buscar cliente por número de identificación o nombre
     *
     * @param txtBuscar
     * @param tclId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntCliente> getLstClienteXTextoKids(String txtBuscar, Integer tclId) {
        Query q = em.createNamedQuery("VntCliente.clnXTextoKids");
        q.setParameter("txtBuscar", "%" + txtBuscar + "%");
        q.setParameter("tclId", tclId);
        return q.getResultList();
    }

    /**
     * Cargar lista de clientes
     *
     * @param ptclId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntCliente> getLstVntCliente(Integer ptclId) {
        Query q = em.createNamedQuery("VntCliente.findByTipoCliente");
        q.setParameter("tclId", ptclId);
        return q.getResultList();
    }

    /**
     * Cargar lista de detalle por cliente
     *
     * @param pClnId
     * @param pDclnEstado
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntDetallecliente> getLstVntDetallecliente(Long pClnId, boolean pDclnEstado) {
        Query q = em.createNamedQuery("VntDetallecliente.detalleXCliente").
        setParameter("clnId", pClnId).
        setParameter("dclnEstado", pDclnEstado);
        return q.getResultList();
    }

    /**
     * Cargar lista de tipo de documento por estado
     *
     * @param pTdcEstado
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfTipodoc> getLstRfTipodoc(boolean pTdcEstado) {
        Query q = em.createNamedQuery("RfTipodoc.findByTdcEstado");
        q.setParameter("tdcEstado", pTdcEstado);
        return q.getResultList();
    }

    /**
     * Cargar lista de sexo
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntActeconomica> getLstVntActeconomicaXEstado(boolean pAteEstado) {
        Query q = em.createNamedQuery("VntActeconomica.findByAteEstado");
        q.setParameter("ateEstado", pAteEstado);
        return q.getResultList();
    }

    /**
     * Cargar lista de parentesco
     *
     * @param pPrtEst
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfParentezco> getLstRfParentesco(boolean pPrtEst) {
        Query q = em.createNamedQuery("RfParentezco.findByPrtEst");
        q.setParameter("prtEst", pPrtEst);
        return q.getResultList();
    }

    /**
     * Obtener id tipo de documento
     *
     * @param pTdcId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfTipodoc getRfTipodocXId(String pTdcId) {
        return em.find(RfTipodoc.class, pTdcId);
    }

    /**
     * Obtener id sexo cliente
     *
     * @param pSexId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfSexo getRfSexo(String pSexId) {
        return em.find(RfSexo.class, pSexId);
    }

    /**
     * Obtener id tipo cliente
     *
     * @param pTclId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public VntRfTipocliente getVntRfTipoclienteXId(Integer pTclId) {
        return em.find(VntRfTipocliente.class, pTclId);
    }

    /**
     * Obtener id actividad económica
     *
     * @param pAteId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public VntActeconomica getVntActeconomicaXId(Integer pAteId) {
        return em.find(VntActeconomica.class, pAteId);
    }

    /**
     * Obtener id parentesco
     *
     * @param pPrtId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfParentezco getRfParentesco(Integer pPrtId) {
        return em.find(RfParentezco.class, pPrtId);
    }

    /**
     * Editar cliente
     *
     * @param pVntCliente
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntCliente editarCliente(VntCliente pVntCliente) {
        pVntCliente = em.merge(pVntCliente);
        return pVntCliente;
    }

    /**
     * Grabar detalle cliente
     *
     * @param pVntDetallecliente
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntDetallecliente editarDetalleCliente(VntDetallecliente pVntDetallecliente) {
        pVntDetallecliente = em.merge(pVntDetallecliente);
        return pVntDetallecliente;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfCargocontacto> getLstRfCargocontactoXEstado(boolean estado) {
        Query q = em.createNamedQuery("RfCargocontacto.findByCargoEst");
        q.setParameter("cargoEst", estado);
        return q.getResultList();
    }
 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfCargocontacto> getLstRfCargocontacto() {
        Query q = em.createNamedQuery("RfCargocontacto.findAll");        
        return q.getResultList();
    }
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntDetevento> getListaVentas(Integer tclId) {
        Query q = em.createNamedQuery("VntDetevento.findListaVentas");
        q.setParameter("tclId", tclId);

        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfCargocontacto getRfCargocontacto(Integer cargoId) {
        return em.find(RfCargocontacto.class, cargoId);
    }
    
    public void desasociarContactoCliente(VntDetallecliente pVntDetalleclient){
    
      
   }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Evento">
    /**
     * Cargar lista de motivo de evento por estado
     *
     * @param pMteEstado
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfMotivoevento> getLstRfMotivoeventoXEstado(boolean pMteEstado) {
        Query q = em.createNamedQuery("RfMotivoevento.findByMteEstado");
        q.setParameter("mteEstado", pMteEstado);
        return q.getResultList();
    }

    /**
     * Consulta los motivos por tipo cliente.
     *
     * @param pMteEstado
     * @param tipoCliente
     * @param pTpcxmEstado
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfMotivoevento> getLstRfMotivoeventoXEstadoXTipoCliente(boolean pMteEstado, Integer tipoCliente, boolean pTpcxmEstado) {
        Query q = em.createNamedQuery("RfMotivoevento.findByMteEstadoXTipoCliente");
        q.setParameter("mteEstado", pMteEstado);
        q.setParameter("tpcxmEstado", pTpcxmEstado);
        q.setParameter("tclId", tipoCliente);

        return q.getResultList();
    }

    /**
     * Obtener id motivo evento
     *
     * @param pMteId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfMotivoevento motivoEventoXId(Integer pMteId) {
        return em.getReference(RfMotivoevento.class, pMteId);
    }

    /**
     * Grabar registro de venta
     *
     * @param pVntRegistroventa
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntRegistroventa editarRegistroVenta(VntRegistroventa pVntRegistroventa) {
        pVntRegistroventa = em.merge(pVntRegistroventa);
        return pVntRegistroventa;
    }

    /**
     * Grabar detalle del evento
     *
     * @param pVntRegistroventa
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntDetevento grabarDetalleEvento(VntDetevento pVntDetevento) {
        pVntDetevento = em.merge(pVntDetevento);
        return pVntDetevento;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Servicio por venta">
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

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntServxventa> getlstTablaVentaServicioXVenta(Long rgvtId) {
        Query q = em.createNamedQuery("VntServxventa.servxventa");
        q.setParameter("rgvtId", rgvtId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfSexo getRfSexoXId(String psexId) {
        return em.find(RfSexo.class, psexId);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ServRfTipocliente> obtenerServRfTipoclientePorTipoCliente(Integer tclId, boolean servtcEst) {
        Query q = em.createNamedQuery("ServRfTipocliente.findByCliente");
        q.setParameter("tclId", tclId);
        q.setParameter("servtcEst", servtcEst);
        return q.getResultList();
    }

    /**
     * Cargar lista de servicio
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntServicio> getLstServicios() {
        Query q = em.createNamedQuery("VntServicio.findAll");
        return q.getResultList();
    }

    /**
     * Cargar lista de servicio por venta
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
     * Grabar servicio por venta
     *
     * @param pVntRegistroventa
     * @param pLstVntServxventas
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
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
        vntRegistroventa = pVntRegistroventa;
    }

    /**
     * Eliminar servicio por venta
     *
     * @param pVntServxventa
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void eliminarServxventa(VntServxventa pVntServxventa) {
        try {
            pVntServxventa = em.find(VntServxventa.class, pVntServxventa.getSrvxventId());
            em.remove(pVntServxventa);
        } catch (EntityNotFoundException enfe) {
        }
    }

    /**
     * Obtener id estado venta
     *
     * @param pEstrvntId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public VntEstventa getEstventaXId(Integer pEstrvntId) {
        return em.find(VntEstventa.class, pEstrvntId);
    }

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
        vntRegistroventa = pVntRegistroventa;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Registro Telefonico">
    /**
     *
     * @param edad
     * @param rgedadsexSex
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRangoedadsexo> getLstVntRangoedadsexo(Integer edad, String rgedadsexSex) {

        Query q = em.createNamedQuery("VntRangoedadsexo.findRangoEdadYSexo");
        q.setParameter("edad", edad);
        q.setParameter("rgedadsexSex", rgedadsexSex);
        return q.getResultList();

    }

    /**
     *
     * @param rgedadsexId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRangoedadsexxservicio> getLstVntRangoedadsexxservicio(Integer rgedadsexId) {

        Query q = em.createNamedQuery("VntRangoedadsexxservicio.findByVntRangoedadsexo");
        q.setParameter("rgedadsexId", rgedadsexId);
        q.setParameter("vsrvEst", true);
        return q.getResultList();

    }

    /**
     *
     * @param rgedadsexId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntServicio> getLstVntServicio(boolean vsrvEst) {

        Query q = em.createNamedQuery("VntServicio.findByArchivo");
        q.setParameter("vsrvEst", vsrvEst);
        return q.getResultList();

    }

    /**
     * Retorna lista de como supo de la empresa
     *
     * @param cmcEstado
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RfComocontacto> getLstRfComocontacto(boolean cmcEstado) {
        Query q = em.createNamedQuery("RfComocontacto.findByCmcEstado");
        q.setParameter("cmcEstado", cmcEstado);
        return q.getResultList();
    }

    /**
     *
     * @param cmcId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfComocontacto getRfComocontacto(Integer cmcId) {
        return em.getReference(RfComocontacto.class, cmcId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntRegistroLlamada editarVntRegistroLlamada(VntRegistroLlamada pVntRegistroLlamada) {
        pVntRegistroLlamada = em.merge(pVntRegistroLlamada);
        return pVntRegistroLlamada;
    }

    /**
     *
     * @param tipocierreId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfTipocierrellamada getRfTipocierrellamada(Integer tipocierreId) {
        return em.find(RfTipocierrellamada.class, tipocierreId);
    }

    /**
     * Retorna lista de como supo de la empresa
     *
     * @param cmcEstado
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRegistroLlamada> getLstVntRegistroLlamada(Long clnId) {
        Query q = em.createNamedQuery("VntRegistroLlamada.findByCliente");
        q.setParameter("clnId", clnId);
        q.setMaxResults(20);
        return q.getResultList();
    }

    /**
     *
     * @param tipocierreId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public RfMotivoevento getRfMotivoeventoXId(Integer idMotivo) {
        return em.find(RfMotivoevento.class, idMotivo);
    }

    /**
     * detalle del evento por detalleCLiente
     *
     * @param dclnId
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntDetevento> getLstVntDeteventoXDetalleCliente(Long dclnId) {
        Query q = em.createNamedQuery("VntDetevento.findByDetalleCliente");
        q.setParameter("dclnId", dclnId);
        q.setMaxResults(20);
        return q.getResultList();

    }
    //</editor-fold>

    /**
     * @return the vntRegistroventa
     */
    public VntRegistroventa getVntRegistroventa() {
        return vntRegistroventa;
    }

    /**
     * @param vntRegistroventa the vntRegistroventa to set
     */
    public void setVntRegistroventa(VntRegistroventa vntRegistroventa) {
        this.vntRegistroventa = vntRegistroventa;
    }

   
}
