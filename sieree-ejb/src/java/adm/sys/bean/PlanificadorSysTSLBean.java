/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.bean;


import adm.sys.dao.SysPropaso;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class PlanificadorSysTSLBean {

    @PersistenceContext
    EntityManager em;
    @EJB
    AdmSysTareaSLBean astslb;
//    @EJB
//    ConvocatoriaSFBean csfb;
//    @EJB
//    HojaDeVidaSFBean hdvsfb;
//
//    public int getDiaDiferencia(Date pFecIni, Date pFecFin) {
//        DateTime jdtFecIni = new DateTime(pFecIni);
//        DateTime jdtFecFin = new DateTime(pFecFin);
//        return Days.daysBetween(jdtFecIni.toDateMidnight(), jdtFecFin.toDateMidnight()).getDays();
//
//    }
//
    @Schedule(hour = "*", minute = "*", second = "*/8")
    public void planifCompras() {

        cerrarReqComprasXMes();
        if (reqsAbiertasXMes() == 0L) {
            crearTareaRevSol();
        }
    }
//
//    @Schedule(hour = "*", minute = "*", second = "*/8")
//    public void cerrarConvicatoriaPth() {
//        for (SysTarea st : astslb.getLstTareasEnejecPasoParaCerrarXFecha(14L)) {
//            for (PthConvocatoria pthConvocatoria : csfb.getLstPthConvocatoriaProcesadaXTaera(st.getStrId())) {
//                if (getDiaDiferencia(pthConvocatoria.getPcvFehcafin(), new Date()) > 0) {
//                    pthConvocatoria.setPcvEstprocesadarecephv(Boolean.TRUE);
//                    pthConvocatoria = em.merge(pthConvocatoria);
//
//                    for (PthHvxconv hvxconv : pthConvocatoria.getPthHvxconvList()) {
//                        PthCronentrevista pthCronentrevista = new PthCronentrevista();
//                        pthCronentrevista.setHvcId(hvxconv);
//                        pthCronentrevista.setCreEst(Boolean.TRUE);
//                        pthCronentrevista.setCreEstprocesada(Boolean.FALSE);
//                        pthCronentrevista.setStrId(st.getStrId());
//                        pthCronentrevista = em.merge(pthCronentrevista);
//
////                        PthCronpruebaconcocimiento pthCronpruebaconcocimiento = new PthCronpruebaconcocimiento();
////                        pthCronpruebaconcocimiento.setHvcId(hvxconv);
////                        pthCronpruebaconcocimiento.setCrpEst(Boolean.TRUE);
////                        pthCronpruebaconcocimiento.setCrpEstprocesada(Boolean.FALSE);
////                        pthCronpruebaconcocimiento.setStrId(st.getStrId());
////                        pthCronpruebaconcocimiento = em.merge(pthCronpruebaconcocimiento);
//                    }
//                    List<Long> lstPasosDest = new ArrayList<>();
//                    lstPasosDest.add(15L);
////                    lstPasosDest.add(16L);
//                    astslb.crearSeguimientoTareaPasoOrigenDestVarios(st, 14L, lstPasosDest);
//                }
//            }
//        }
//
//    }

//    @Schedule(hour = "*", minute = "*", second = "*/8")
//    public void insertarSolicitudContrato() {
//        //  System.out.println("Grabar solicitud contrato...");
//        List<Long> lstStrId = new ArrayList<>();
//        for (PthHvxconv pthHvxconv : hdvsfb.getLstHvXConvAprobPruebConEntrev(1, 1, 3)) {
//            PthSolicitudcontrato ps = new PthSolicitudcontrato();
//            ps.setHvcId(pthHvxconv);
//            ps.setSphEstado(Boolean.TRUE);
//            ps.setStrId(pthHvxconv.getPcvId().getStrId());
//            ps.setSphEstado(Boolean.TRUE);
//            ps.setSphProcesada(Boolean.FALSE);
//            ps = em.merge(ps);
//            if (!lstStrId.contains(pthHvxconv.getPcvId().getStrId())) {
//                lstStrId.add(pthHvxconv.getPcvId().getStrId());
//            }
//        }
////        astslb.crearSeguimientoTareaPasoVariosOrigenUnDest(null, lstStrId, Long.MIN_VALUE);
//    }

    /**
     * Crear la tarea que se inicia en el paso "Revisar requisiciones" y
     * activarla
     */
    private void crearTareaRevSol() {
        Query q = em.createNamedQuery("CmpRequiscomp.cantFilasTabla");
        Long cantFilas = (Long) q.getSingleResult();
        if (cantFilas > 0L) {
            if (astslb.getCantTareaXEstXMesXProc(new Date(), 2, "PRLOG01") == 0L) {
                astslb.crearTareaInicioXProc("PRLOG01", 2, 2L);
                System.out.println("Tarea creada");
            }
        }




    }

    public Long reqsAbiertasXMes() {
        Query q = em.createNamedQuery("CmpRequiscomp.reqsXEstXMes");
        q.setParameter(1, true);
        q.setParameter(2, new Date());
        q.setParameter(3, new Date());
        return (Long) q.getSingleResult();

    }

    private void cerrarReqComprasXMes() {

        SysPropaso propasoInicioProc = astslb.getPasoInicioXProc("PRLOG01");
        if (propasoInicioProc != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.DAY_OF_MONTH, propasoInicioProc.getSpsDiainicio() + propasoInicioProc.getSpsVigencia());
            Query q = em.createNamedQuery("CmpRequiscomp.cerrarReqAb");
            q.setParameter(1, false);
            q.setParameter(2, true);
            q.setParameter(3, new Date());
            q.setParameter(4, calendar.getTime());           
            q.executeUpdate();
        }
    }
}
