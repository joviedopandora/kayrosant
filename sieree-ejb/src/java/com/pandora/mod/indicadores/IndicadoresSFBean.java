/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.mod.indicadores;

import com.pandora.mod.venta.dao.VntRegistroLlamada;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Javier
 */
@Stateless
@LocalBean
public class IndicadoresSFBean {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {
    }

    /**
     * Consulta de ventas por periodos de fecha
     *
     * @param fechaInicial
     * @param tipoCliente
     * @param anulado
     * @param fechaFinal
     * @return
     */
    public HashMap<String, Long> getLstVentasXCountFecha(Date fechaInicial, Integer tipoCliente, boolean anulado, Date fechaFinal) {
        Query q = em.createNamedQuery("VntRegistroventa.findCountVentasXPeriodo");
        q.setParameter(1, tipoCliente);
        q.setParameter(2, anulado);
        q.setParameter(3, fechaInicial);
        q.setParameter(4, fechaFinal);
        List<Object> lst = q.getResultList();
        HashMap<String, Long> salida = new HashMap<>();
        for (Object o : lst) {

            Object[] x = (Object[]) o;
            salida.put((String) x[0], (Long) x[1]);

        }
        return salida;
    }
    /*public List<IndicadoresDTO> getLstVentasXCountFecha(Date fechaInicial, Integer tipoCliente, boolean anulado,Date fechaFinal) {
     Query q = em.createNamedQuery("VntRegistroventa.findCountVentasXPeriodo");
     q.setParameter(1, tipoCliente);
     q.setParameter(2, anulado);
     q.setParameter(3, fechaInicial);
     q.setParameter(4, fechaFinal);
     List<Object> lst = q.getResultList();
     List<IndicadoresDTO> salida = new ArrayList<>();
     for (Object o : lst) {
     IndicadoresDTO t = new IndicadoresDTO();
     Object[] x = (Object[]) o;
     t.setPeriodo((String) x[0]);
     t.setCantidad((Long) x[1]);
     salida.add(t);
     }
     return salida;
     }*/

    /**
     * Promedio de fechas por periodos de fechas
     *
     * @param fechaInicial
     * @param tipoCliente
     * @param anulado
     * @param fechaFinal
     * @return
     */
    public Long getVentasXPormedioFecha(Date fechaInicial, Integer tipoCliente, boolean anulado, Date fechaFinal) {
        Query q = em.createNamedQuery("VntRegistroventa.findCountVentasXPromedio");
        q.setParameter(1, tipoCliente);
        q.setParameter(2, anulado);
        q.setParameter(3, fechaInicial);
        q.setParameter(4, fechaFinal);
        BigDecimal d = (BigDecimal) q.getSingleResult();
        return d.longValue();
        /* List<Object> lst = q.getResultList();
         List<IndicadoresDTO> salida = new ArrayList<>();
         for (Object o : lst) {
         IndicadoresDTO t = new IndicadoresDTO();
         Object[] x = (Object[]) o;
         t.setPeriodo((String) x[0]);
         t.setCantidad((Long) x[1]);
         salida.add(t);
         }
         return salida;*/
    }

    /**
     * Consulta de registros de llamadas por fecha
     *
     * @param fechaInicial
     * @param tipoCliente
     * @param fechaFinal
     * @return Lista para el grafico
     */
    public HashMap<String, Long> getLstIndicadoresLLamadasXMes(Date fechaInicial, Integer tipoCliente, Date fechaFinal) {
        Query q = em.createNamedQuery("VntRegistroLlamada.findCountLlamadaXPeriodo");
        q.setParameter(1, tipoCliente);
        q.setParameter(2, fechaInicial);
        q.setParameter(3, fechaFinal);
        List<Object> lst = q.getResultList();
        HashMap<String, Long> salida = new HashMap<>();
        for (Object o : lst) {
            Object[] x = (Object[]) o;
            salida.put((String) x[0], (Long) x[1]);

        }
        return salida;
    }

    /**
     * Consulta de promedio de llamadas por periodo de fechas
     *
     * @param fechaInicial
     * @param tipoCliente
     * @param fechaFinal
     * @return
     */
    public Long getLLamadasXPormedioFecha(Date fechaInicial, Integer tipoCliente, Date fechaFinal) {
        Query q = em.createNamedQuery("VntRegistroLlamada.findCountLlamadaXPromedio");
        q.setParameter(1, tipoCliente);
        q.setParameter(2, fechaInicial);
        q.setParameter(3, fechaFinal);
        BigDecimal d = (BigDecimal) q.getSingleResult();
        return d.longValue();

    }
}
