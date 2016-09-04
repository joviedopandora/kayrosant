/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.bean;

import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.AdmInforme;
import adm.sys.dao.SysColxgrupo;
import adm.sys.dao.SysEsttarea;
import adm.sys.dao.SysProceso;
import adm.sys.dao.SysPropaso;
import adm.sys.dao.SysSegtarea;
import adm.sys.dao.SysTarea;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 *
 * @author luis
 */
@Stateless(name = "AdmSysTareaSLBean")
@LocalBean
public class AdmSysTareaSLBean {

    @PersistenceContext
    EntityManager em;

    /**
     * Colaboradores por paso
     *
     * @param pSpsId
     * @return
     */
    public List<AdmCrgxcol> getLstColXPaso(Long pSpsId) {
        Query q = em.createNamedQuery("AdmCrgxcol.findByGrupoXPaso");
        q.setParameter("spsId", pSpsId);
        q.setParameter("cpeEstcop", Boolean.TRUE);
        return q.getResultList();
    }
    
    public AdmInforme getAdmInformeXId(Integer pInfId){
        return em.find(AdmInforme.class, pInfId);
    }

    public SysTarea getSysTareaXId(Long pStrId) {
        return em.find(SysTarea.class, pStrId);
    }

    public SysPropaso getPasoInicioXProc(String pSprId) {
        try {
            Query q = em.createNamedQuery("SysPropaso.pasosInicioXProc");
            q.setParameter("spsInicio", Boolean.TRUE);
            q.setParameter("sprId", pSprId);
            return (SysPropaso) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<SysTarea> getLstTareasEnejecPasoParaCerrarXFecha(Long pSpsId) {
        Query q = em.createNamedQuery("SysTarea.tareasXPasoParaCerrar");
        q.setParameter("spsId", pSpsId);
        q.setParameter("etrId", 2);
        q.setParameter("sgtEstpaso", Boolean.TRUE);
        return q.getResultList();
    }

    /**
     *
     * @param pFcreTarea
     * @param pEtrId
     * @param pSprId
     * @return
     */
    public Long getCantTareaXEstXMesXProc(Date pFcreTarea, Integer pEtrId, String pSprId) {
        Query q = em.createNamedQuery("SysTarea.tareaXMesXEst");
        Calendar cFcre = Calendar.getInstance();
        cFcre.setTime(pFcreTarea);
        q.setParameter(1, cFcre.get(Calendar.MONTH) + 1);
        q.setParameter(2, cFcre.get(Calendar.YEAR));
        q.setParameter(3, pEtrId);
        q.setParameter(4, pSprId);
        return (Long) q.getSingleResult();
    }

    /**
     * Crear la tarea de inicio de un proceso y colocarla en el paso seguiente
     * al inicial de un proceso
     *
     * @param pSprId Proceso al cual pertenece la tarea
     * @param pEtrId Estado inicial de la tarea
     * @param pSpsId Id del paso que se va a asociar a la tarea en el
     * seguimiento de la tarea
     * @return
     */
    public SysTarea crearTareaInicioXProc(String pSprId, Integer pEtrId, Long pSpsId) {
        SysTarea sysTarea = new SysTarea();
        sysTarea.setSprId(new SysProceso(pSprId));
        sysTarea.setStrFcre(new Date());
        sysTarea.setEtrId(new SysEsttarea(pEtrId));
        sysTarea.setStrNombre("TAREA No. ");
        sysTarea = em.merge(sysTarea);
        SysPropaso sysPropaso = em.find(SysPropaso.class, pSpsId);
        //Query q = em.createNamedQuery("SysGrupo.grupoXPaso");
        //q.setParameter("spsId", sysPropaso.getSgrId().getSgrId());
        //SysGrupo sysGrupo = (SysGrupo) q.getSingleResult();
        Query q = em.createNamedQuery("SysColxgrupo.cxgXGrupo");
        q.setParameter("sgrId", sysPropaso.getSgrId().getSgrId());
        List<SysColxgrupo> getLstColxgrupo = q.getResultList();
        DateTime dtFechaActual = new DateTime(new Date());
        for (SysColxgrupo sysColxgrupo : getLstColxgrupo) {
            SysSegtarea segtarea = new SysSegtarea();
            segtarea.setStrId(sysTarea);
            segtarea.setSpsId(sysPropaso);
            segtarea.setSgtFcre(new Date());
            segtarea.setCxcId(sysColxgrupo.getCxcId());
            segtarea.setSgtEstpaso(Boolean.TRUE);
            segtarea.setSgtFinicio(new Date());
            segtarea.setSgtFfinal(dtFechaActual.plus(Period.days(sysPropaso.getSpsVigencia())).toDate());
            em.merge(segtarea);
        }
        sysTarea.setStrNombre(sysTarea.getStrNombre() + sysTarea.getStrId());
        sysTarea = em.merge(sysTarea);
        return sysTarea;
    }

    /**
     * Crear la tarea de inicio de un proceso y colocarla en el paso seguiente
     * al inicial de un proceso por colaborador
     *
     * @param pSprId
     * @param pEtrId
     * @param pSpsId
     * @param pCxcId
     * @return
     */
    public SysSegtarea crearTareaInicioXProcXCxc(String pSprId, Integer pEtrId, Long pSpsId, AdmCrgxcol pCxcId) {
        SysTarea sysTarea = new SysTarea();
        sysTarea.setSprId(new SysProceso(pSprId));
        sysTarea.setStrFcre(new Date());
        sysTarea.setEtrId(new SysEsttarea(pEtrId));
        sysTarea.setStrNombre("TAREA No. ");
        sysTarea = em.merge(sysTarea);
        SysPropaso sysPropaso = em.find(SysPropaso.class, pSpsId);
        DateTime dtFechaActual = new DateTime(new Date());

        SysSegtarea segtarea = new SysSegtarea();
        segtarea.setStrId(sysTarea);
        segtarea.setSpsId(sysPropaso);
        segtarea.setSgtFcre(new Date());
        segtarea.setCxcId(pCxcId);
        segtarea.setSgtEstpaso(Boolean.TRUE);
        segtarea.setSgtFinicio(new Date());
        segtarea.setSgtFfinal(dtFechaActual.plus(Period.days(sysPropaso.getSpsVigencia())).toDate());
        segtarea = em.merge(segtarea);

        sysTarea.setStrNombre(sysTarea.getStrNombre() + sysTarea.getStrId());
        sysTarea = em.merge(sysTarea);
        return segtarea;
    }

    /**
     * Avanzar de un paso a otro teniendo en cuenta los usuarios del grupo al
     * que está asociado el paso. Esta función solamente cambia de un paso a
     * otro, no de un paso a varios.
     *
     * @param pStrId
     * @param pSpsId
     */
    public void crearSeguimientoTareaPaso(SysTarea pStrId, Long pSpsId) {
        SysPropaso propaso = em.getReference(SysPropaso.class, pSpsId);
        DateTime dtFechaActual = new DateTime(new Date());
        Query q = em.createNamedQuery("SysSegtarea.updateXtarea");
        q.setParameter("strId", pStrId.getStrId());
        q.setParameter("sgtEstpaso", Boolean.FALSE);
        q.setParameter("sgtEstpaso1", Boolean.TRUE);
        q.executeUpdate();

        for (AdmCrgxcol ce : getLstColXPaso(propaso.getSpsId())) {
            SysSegtarea segtarea = new SysSegtarea();
            segtarea.setSgtEstpaso(true);
            segtarea.setSgtFcre(dtFechaActual.toDate());
            segtarea.setSgtFinicio(dtFechaActual.toDate());
            segtarea.setSgtFfinal(dtFechaActual.plus(Period.days(propaso.getSpsVigencia())).toDate());
            segtarea.setCxcId(ce);
            segtarea.setSpsId(propaso);
            segtarea.setStrId(pStrId);
            em.persist(segtarea);
        }
    }

    public SysTarea editarSysTarea(SysTarea pSysTarea) {
        SysEsttarea esttarea = em.getReference(SysEsttarea.class, pSysTarea.getEtrId().getEtrId());
        pSysTarea.setEtrId(esttarea);
        return em.merge(pSysTarea);
    }

    /**
     * Mover de un paso de origen a un paso de destino asociando el grupo a la
     * tarea.
     *
     * @param pStrId
     * @param pSpsIdOrigen
     * @param pSpsIdDest
     */
    public void crearSeguimientoTareaPasoOrigenDest(SysTarea pStrId, Long pSpsIdOrigen, Long pSpsIdDest) {
        SysPropaso propasoDestino = em.getReference(SysPropaso.class, pSpsIdDest);
        DateTime dtFechaActual = new DateTime(new Date());
        Query q = em.createNamedQuery("SysSegtarea.updateXtareaXPaso");
        q.setParameter("strId", pStrId.getStrId());
        q.setParameter("sgtEstpaso", Boolean.FALSE);
        q.setParameter("sgtEstpaso1", Boolean.TRUE);
        q.setParameter("spsId", pSpsIdOrigen);
        q.executeUpdate();

        for (AdmCrgxcol ce : getLstColXPaso(propasoDestino.getSpsId())) {
            SysSegtarea segtarea = new SysSegtarea();
            segtarea.setSgtEstpaso(true);
            segtarea.setSgtFcre(dtFechaActual.toDate());
            segtarea.setSgtFinicio(dtFechaActual.toDate());
            segtarea.setSgtFfinal(dtFechaActual.plus(Period.days(propasoDestino.getSpsVigencia())).toDate());
            segtarea.setCxcId(ce);
            segtarea.setSpsId(propasoDestino);
            segtarea.setStrId(pStrId);
            em.persist(segtarea);
        }
    }    
    
    /**
     * Editar seguimiento de la tarea
     *
     * @param pSysSegtarea 
     * @return 
     */
    public SysSegtarea editarSysSegtareaXTarea(SysSegtarea pSysSegtarea){
        pSysSegtarea.setSgtEstpaso(Boolean.FALSE);
        return em.merge(pSysSegtarea);
    }

    /**
     * Mover una tarea que está en varios pasos a la vez a un destino.
     *
     * @param pStrId
     * @param pLstSpsIdOrigen
     * @param pSpsIdDest
     */
    public void crearSeguimientoTareaPasoVariosOrigenUnDest(SysTarea pStrId, List<Long> pLstSpsIdOrigen, Long pSpsIdDest) {
        SysPropaso propasoDestino = em.getReference(SysPropaso.class, pSpsIdDest);
        DateTime dtFechaActual = new DateTime(new Date());
        for (Long spsOrigen : pLstSpsIdOrigen) {
            Query q = em.createNamedQuery("SysSegtarea.updateXtareaXPaso");
            q.setParameter("strId", pStrId.getStrId());
            q.setParameter("sgtEstpaso", Boolean.FALSE);
            q.setParameter("sgtEstpaso1", Boolean.TRUE);
            q.setParameter("spsId", spsOrigen);
            q.executeUpdate();
        }

        for (AdmCrgxcol ce : getLstColXPaso(propasoDestino.getSpsId())) {
            SysSegtarea segtarea = new SysSegtarea();
            segtarea.setSgtEstpaso(true);
            segtarea.setSgtFcre(dtFechaActual.toDate());
            segtarea.setSgtFinicio(dtFechaActual.toDate());
            segtarea.setSgtFfinal(dtFechaActual.plus(Period.days(propasoDestino.getSpsVigencia())).toDate());
            segtarea.setCxcId(ce);
            segtarea.setSpsId(propasoDestino);
            segtarea.setStrId(pStrId);
            em.persist(segtarea);

        }
    }

    /**
     * Crear tarea para un grupo dinámico con paso de origen y paso de destino
     *
     * @param pStrId
     * @param pSpsIdOrigen
     * @param pSpsIdDest
     * @param pLstAdmCrgxcols
     */
    public void crearSeguimientoTareaPasoOrigenDest(SysTarea pStrId, Long pSpsIdOrigen, Long pSpsIdDest, List<AdmCrgxcol> pLstAdmCrgxcols) {
        SysPropaso propasoDestino = em.getReference(SysPropaso.class, pSpsIdDest);
        DateTime dtFechaActual = new DateTime(new Date());
        Query q = em.createNamedQuery("SysSegtarea.updateXtareaXPaso");
        q.setParameter("strId", pStrId.getStrId());
        q.setParameter("sgtEstpaso", Boolean.FALSE);
        q.setParameter("sgtEstpaso1", Boolean.TRUE);
        q.setParameter("spsId", pSpsIdOrigen);
        q.executeUpdate();

        for (AdmCrgxcol admCrgxcol : pLstAdmCrgxcols) {
            if (admCrgxcol.isCxcEst()) {
                SysSegtarea segtarea = new SysSegtarea();
                segtarea.setSgtEstpaso(true);
                segtarea.setSgtFcre(dtFechaActual.toDate());
                segtarea.setSgtFinicio(dtFechaActual.toDate());
                segtarea.setSgtFfinal(dtFechaActual.plus(Period.days(propasoDestino.getSpsVigencia())).toDate());
                segtarea.setCxcId(admCrgxcol);
                segtarea.setSpsId(propasoDestino);
                segtarea.setStrId(pStrId);
                em.persist(segtarea);
            }
        }

    }

    /**
     * Mover tarea a varios pasos a la vez, con un paso de origen
     *
     * @param pStrId
     * @param pSpsIdOrigen
     * @param pLstSpsIdDest
     */
    public void crearSeguimientoTareaPasoOrigenDestVarios(SysTarea pStrId, Long pSpsIdOrigen, List<Long> pLstSpsIdDest) {
        DateTime dtFechaActual = new DateTime(new Date());

        Query q = em.createNamedQuery("SysSegtarea.updateXtareaXPaso");
        q.setParameter("strId", pStrId.getStrId());
        q.setParameter("sgtEstpaso", Boolean.FALSE);
        q.setParameter("sgtEstpaso1", Boolean.TRUE);
        q.setParameter("spsId", pSpsIdOrigen);
        q.executeUpdate();
        for (Long pSpsIdDest : pLstSpsIdDest) {
            SysPropaso propasoDestino = em.getReference(SysPropaso.class, pSpsIdDest);

            for (AdmCrgxcol ce : getLstColXPaso(propasoDestino.getSpsId())) {
                SysSegtarea segtarea = new SysSegtarea();
                segtarea.setSgtEstpaso(true);
                segtarea.setSgtFcre(dtFechaActual.toDate());
                segtarea.setSgtFinicio(dtFechaActual.toDate());
                segtarea.setSgtFfinal(dtFechaActual.plus(Period.days(propasoDestino.getSpsVigencia())).toDate());
                segtarea.setCxcId(ce);
                segtarea.setSpsId(propasoDestino);
                segtarea.setStrId(pStrId);
                em.persist(segtarea);

            }
        }

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SysPropaso> getLstPasosInicioXUsr(Boolean pSpsInicio, Integer pCxcId) {
        Query q = em.createNamedQuery("SysPropaso.findPasosInicio");
        q.setParameter("spsInicio", pSpsInicio);
        q.setParameter("cxcId", pCxcId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        q.setParameter("diaActual", calendar.get(Calendar.DAY_OF_MONTH));
        return q.getResultList();
    }

    public List<SysSegtarea> getLstTarXUsrActivas(Integer pIntEstTarId, Boolean pBlnEstPaso, Integer pCxcId) {
        Query q = em.createNamedQuery("SysSegtarea.tareaXUsrActPaso");
        q.setParameter("etrId", pIntEstTarId);
        q.setParameter("sgtEstpaso", Boolean.TRUE);
        q.setParameter("cxcId", pCxcId);
        return q.getResultList();
    }

    /**
     * Avanzar de un paso a varios teniendo en cuenta los usuarios del grupo al
     * que está cada paso.
     *
     * @param pStrId
     * @param pLstSpsId
     */
    public void crearSeguimientoTareaPasoVarios(SysTarea pStrId, List<Long> pLstSpsId) {
        DateTime dtFechaActual = new DateTime(new Date());
        Query q = em.createNamedQuery("SysSegtarea.updateXtarea");
        q.setParameter("strId", pStrId.getStrId());
        q.setParameter("sgtEstpaso", Boolean.FALSE);
        q.setParameter("sgtEstpaso1", Boolean.TRUE);
        q.executeUpdate();
        for (Long pspId : pLstSpsId) {
            SysPropaso propaso = em.getReference(SysPropaso.class, pspId);
            for (AdmCrgxcol ce : getLstColXPaso(propaso.getSpsId())) {
                SysSegtarea segtarea = new SysSegtarea();
                segtarea.setSgtEstpaso(true);
                segtarea.setSgtFcre(dtFechaActual.toDate());
                segtarea.setSgtFinicio(dtFechaActual.toDate());
                segtarea.setSgtFfinal(dtFechaActual.plus(Period.days(propaso.getSpsVigencia())).toDate());
                segtarea.setCxcId(ce);
                segtarea.setSpsId(propaso);
                segtarea.setStrId(pStrId);
                em.persist(segtarea);

            }
        }

    }
}
