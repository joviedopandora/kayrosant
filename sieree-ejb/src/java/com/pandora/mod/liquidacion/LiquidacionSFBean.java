/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.liquidacion;

import com.pandora.mod.liquidacion.dao.PgLiquidacion;
import com.pandora.mod.liquidacion.dao.PgLiquidacionconsolidado;
import com.pandora.mod.ordenprod.dao.PopCxcevento;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Javier
 */
@Stateless
@LocalBean
public class LiquidacionSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {

    }

    /**
     * Cargar Nomina por colaboradores
     *
     *
     * @param calificacionId
     * @param oprFechaevtini
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PopCxcevento> getLstNominaxColaborador(Integer calificacionId, Date oprFechaevtini) {
        Query q = em.createNamedQuery("PopCxcevento.findByLiquidacion");
        q.setParameter("oprFechaevtini", oprFechaevtini);
        q.setParameter("calificacionId", calificacionId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PopCxcevento editarPopCxcevento(PopCxcevento cxcevento) {
        return em.merge(cxcevento);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PgLiquidacionconsolidado saveConsolidadoLiquidacion(PgLiquidacionconsolidado pg) {
        pg = em.merge(pg);
        return pg;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Date getMaximaFechaLiquidacion() {
        Date out = null;
        Query q = em.createNamedQuery("PgLiquidacionconsolidado.findByMacFecha");
        List<Date> lista = q.getResultList();
        if (lista != null && !lista.isEmpty()) {
            out = lista.get(0);
        }
        return out;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PgLiquidacion> consultaLiquidacionByParametros(ConsultaNominaDTO dto) {
        StringBuilder sql = new StringBuilder(" SELECT m  FROM PgLiquidacion m WHERE m.liquidacionNumeroAprobacion IS NULL ");
        if (dto.getLiquidacionConsolidadoId() != null) {
            sql.append(" AND m.idLiquidaconso.idLiquidaconso=:idLiquidaconso ");
        }
        if (dto.getFechaInicialNomina() != null) {
            sql.append(" AND m.idLiquidaconso.fechaiLiquidaconso >=:fechaiLiquidaconso ");
        }

        if (dto.getFechaFinal() != null) {
            sql.append(" AND m.idLiquidaconso.fechafLiquidaconso <=:fechafLiquidaconso ");
        }
        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().trim().isEmpty()) {
            sql.append(" AND m.cxcId.cpeId.colCedula.colCedula=:colCedula ");
        }
        if (dto.getTipoPago() != null && dto.getTipoPago() > 0) {
            sql.append(" AND m.idTipopago.idTipopago=:idTipopago ");
        }
        sql.append(" ORDER BY m.idLiquidaconso.fechaiLiquidaconso,");
        sql.append("m.cxcId.cpeId.colCedula.colApellido1,");
        sql.append("m.cxcId.cpeId.colCedula.colApellido2,");
        sql.append(" m.cxcId.cpeId.colCedula.colNombre1,");
        sql.append("m.cxcId.cpeId.colCedula.colNombre2");
        Query query = em.createQuery(sql.toString());
        if (dto.getLiquidacionConsolidadoId() != null) {
            query.setParameter("idLiquidaconso", dto.getLiquidacionConsolidadoId());
        }
        if (dto.getFechaInicialNomina() != null) {
            query.setParameter("fechaiLiquidaconso", dto.getFechaInicialNomina());

        }

        if (dto.getFechaFinal() != null) {
            query.setParameter("fechafLiquidaconso", dto.getFechaFinal());

        }
        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().trim().isEmpty()) {
            query.setParameter("colCedula", dto.getNumeroDocumento());
        }
        if (dto.getTipoPago() != null && dto.getTipoPago() > 0) {
            query.setParameter("idTipopago", dto.getTipoPago());
        }
        return query.getResultList();

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveLiquidacionPago(List<PgLiquidacion> lista) {
        for (PgLiquidacion pg : lista) {
            pg = em.merge(pg);
        }

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PgLiquidacionconsolidado> consultaLiquidacionConsolidadas() {
        return em.createNamedQuery("PgLiquidacionconsolidado.findAll").getResultList();
    }

}
