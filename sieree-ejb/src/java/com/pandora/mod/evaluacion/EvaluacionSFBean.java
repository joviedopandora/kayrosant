/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.evaluacion;

import adm.sys.dao.AdmCargo;
import adm.sys.dao.AdmCrgxcol;
import com.pandora.bussiness.util.EnEstadoLogistica;
import com.pandora.bussiness.util.EnEstadosOp;
import com.pandora.mod.evaluacion.dao.EvalBonificacion;
import com.pandora.mod.evaluacion.dao.EvalBonificacionColaborador;
import com.pandora.mod.evaluacion.dao.EvalCalificacion;
import com.pandora.mod.evaluacion.dao.EvalCalificacionPago;
import com.pandora.mod.evaluacion.dao.EvalDescuento;
import com.pandora.mod.ordenprod.dao.PopCxcevento;
import com.pandora.mod.ordenprod.dao.PopCxcrol;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.venta.dao.VntDetevento;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
 * @author Breyner Robles
 */
@Stateful
@LocalBean
public class EvaluacionSFBean {

    @PersistenceContext
    EntityManager em;
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EvalDescuento> getLstEvalDescuento(boolean estado) {
        Query q = em.createNamedQuery("EvalDescuento.findByDescuentoEstado");
        q.setParameter("descuentoEstado", estado);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmCargo> getLstAdmCargo(boolean estado) {
        Query q = em.createNamedQuery("AdmCargo.findByCrgEst");
        q.setParameter("crgEst", estado);
        return q.getResultList();
    }

    @Remove
    public void remove() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntDetevento> getLstVntDetevento(Date fechaInicial, Date fechaFinal) {
        Query q = em.createNamedQuery("VntDetevento.findByVentaConOrdenProduccion");
        q.setParameter("fechaInicial", fechaInicial);
        q.setParameter("fechaFinal", fechaFinal);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmCrgxcol> getLstAdmCrgxcolXVenta(Long oprId, boolean cxeEstado) {
        Query q = em.createNamedQuery("PopCxcevento.findByOrdenProduccion");
        q.setParameter("oprId", oprId);
        q.setParameter("cxeEstado", cxeEstado);
        return q.getResultList();
    }

    public List<PopOrdenprod> getLstPopOrdenprodXPendientes(ConsultaOPDTO dto) {
        
      //  StringBuilder sql = new StringBuilder("SELECT p FROM PopOrdenprod p JOIN  p.rgvtId venta JOIN venta.clnId  c JOIN c.tclId tc  WHERE 1=1 ");
//TODO: validar por wilson        
StringBuilder sql = new StringBuilder("SELECT p FROM PopOrdenprod p JOIN  p.rgvtId venta JOIN venta.clnId  c JOIN c.tclId tc  WHERE p.eevId.eevId =:eevId   AND  p.rfEstadoOP.eopId <> :eopId   ");

        if (dto.getTipoCliente() != null) {
            sql.append(" AND tc.tclId= :tclId ");
        }
        if (dto.getIdVenta() != null) {
            sql.append(" AND venta.rgvtId= :rgvtId ");
        }
        if (dto.getIdOp() != null) {
            sql.append(" AND p.oprId= :oprId ");
        }
        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().trim().isEmpty()) {
            sql.append(" AND c.clnIdentificacion= :clnIdentificacion ");
        }
        if (dto.getCantidadEvaluaciones() != null) {
            sql.append(" AND p.oprCantidadEvaluacion < :oprCantidadEvaluacion ");
        }
        if (dto.getFechaOp() != null) {
            sql.append(" AND p.oprFechaevtini = :oprFechaevtini ");
        }

        if (dto.getNombre() != null && !dto.getNombre().trim().isEmpty()) {
            sql.append(" AND c.clnNombres= :clnNombres ");
        }
        sql.append(" ORDER BY p.oprFechaevtini ASC ");
        Query q = em.createQuery(sql.toString());
        // parametros consulta
        //TODO: validar por wilson
        q.setParameter("eevId", EnEstadoLogistica.EVENTO_RECIBIDO.getId());
        q.setParameter("eopId",EnEstadosOp.ANULADO.getId());
        if (dto.getTipoCliente() != null) {
            q.setParameter("tclId", dto.getTipoCliente());
        }
        if (dto.getIdVenta() != null) {
            q.setParameter("rgvtId", dto.getIdVenta());
        }
        if (dto.getIdOp() != null) {
            q.setParameter("oprId", dto.getIdOp());
        }
        if (dto.getFechaOp() != null) {
            q.setParameter("oprFechaevtini", dto.getFechaOp());
        }
        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().trim().isEmpty()) {

            q.setParameter("clnIdentificacion", dto.getNumeroDocumento());
        }
        if (dto.getCantidadEvaluaciones() != null) {
            q.setParameter("oprCantidadEvaluacion", dto.getCantidadEvaluaciones());

        }

        if (dto.getNombre() != null && !dto.getNombre().trim().isEmpty()) {
            q.setParameter("clnNombres", dto.getNombre());

        }
        List<PopOrdenprod> salida = q.getResultList();
        for (PopOrdenprod op : salida) {
            if (op.getRgvtId().getVdeId() == null) {
                Query qs = em.createNamedQuery("VntDetevento.detEvtXVenta");
                qs.setParameter("rgvtId", op.getRgvtId().getRgvtId());
                List<VntDetevento> lst = qs.getResultList();
                if (!lst.isEmpty()) {
                    op.getRgvtId().setVdeId(lst.get(0));
                }

            }
        }
        return salida;
    }

    public List<PopCxcevento> getLstAdmCrgxcol(Long idOP, boolean estado) {
        Query q = em.createNamedQuery("PopCxcevento.findByOrdenProduccionPorPopCxcevento");
        q.setParameter("cxeEstado", estado);
        q.setParameter("oprId", idOP);
        return q.getResultList();
        //

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EvalCalificacion> getLstEvalCalificacion(boolean calificacionEstado) {
        Query q = em.createNamedQuery("EvalCalificacion.findByCalificacionEstado");
        q.setParameter("calificacionEstado", calificacionEstado);

        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EvalCalificacionPago getEvalCalificacionPagoPorCalificacion(Integer calificacionId, String cargo) {
        Query q = em.createNamedQuery("EvalCalificacionPago.findByCalificacion");
        q.setParameter("calificacionId", calificacionId);
        q.setParameter("crgId", cargo);
        List<EvalCalificacionPago> lista = q.getResultList();
        EvalCalificacionPago salida = null;
        if (!lista.isEmpty()) {
            salida = lista.get(0);
        }

        return salida;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EvalBonificacion> getLstEvalBonificacion(boolean estado) {
        Query q = em.createNamedQuery("EvalBonificacion.findByBonificacionEstado");
        q.setParameter("bonificacionEstado", estado);

        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public HashMap<Integer, EvalBonificacionColaborador> getLstEvalBonificacionColaborador(Long cxeId) {
        Query q = em.createNamedQuery("EvalBonificacionColaborador.findByColaborador");
        q.setParameter("cxeId", cxeId);
        HashMap<Integer, EvalBonificacionColaborador> mapa = new HashMap<>();
        List<EvalBonificacionColaborador> lista = q.getResultList();
        for (EvalBonificacionColaborador v : lista) {
            mapa.put(v.getEvalBonificacion().getBonificacionId(), v);
        }
        return mapa;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void eliminarListaEvalBonificacionColaborador(Long cxeId) {
        Query q = em.createNamedQuery("EvalBonificacionColaborador.eliminarByColaborador");
        q.setParameter("cxeId", cxeId);
        q.executeUpdate();

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarListaEvalBonificacionColaborador(List<EvalBonificacionColaborador> lista) {

    }

}
