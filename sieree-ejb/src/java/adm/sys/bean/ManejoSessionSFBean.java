/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.bean;

import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmCrgxcol;
import adm.sys.dao.AdmMenuapp;
import adm.sys.dao.AdmModapp;
import adm.sys.dao.AdmSubmodapp;
import adm.sys.dao.SysPropaso;
import adm.sys.dao.SysSegtarea;
import com.pandora.adm.dao.CmpRequiscomp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateful
@LocalBean
public class ManejoSessionSFBean extends BaseSistemaSFBean {

    private SysSegtarea sysSegtareaActual = new SysSegtarea();

    /**
     * Validar colaborador
     *
     * @param pColxemp Objeto colaborador
     */
    public void validarCol(AdmColxemp pColxemp) {
        Query q = em.createNamedQuery("AdmColxemp.validarCol");
        q.setParameter("cpeUsuario", pColxemp.getCpeUsuario());
        q.setParameter("cpeClave", pColxemp.getCpeClave());
        try {
            colxempLog = (AdmColxemp) q.getSingleResult();
        } catch (NoResultException e) {
            Logger.getLogger(ManejoSessionSFBean.class.getName()).log(Level.WARNING,
                    "Usuario o clave incorrecto para " + pColxemp.getCpeUsuario(), e);
        }

    }

    /**
     * Establecer el cargo actual del colaborador
     *
     * @param pCxcEst
     * @return erdadero si el cargo está correctamente asignado, falso si no.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean setCrgXColActual(Boolean pCxcEst) {
        try {
            Query q = em.createNamedQuery("AdmCrgxcol.crgXColXEst");
            q.setParameter("cpeId", colxempLog.getCpeId());
            q.setParameter("cxcEst", pCxcEst);
            q.setParameter("cxcPrincipal", true);

            crgxcolActual = (AdmCrgxcol) q.getSingleResult();
            return true;
        } catch (NoResultException ex) {
            Logger.getLogger(ManejoSessionSFBean.class.getName()).log(Level.WARNING,
                    "Error de asignación de cargo para  " + colxempLog.getColCedula(), ex);
            return false;
        }

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SysPropaso> getLstPasosInicioXUsr(Boolean pSpsInicio) {
        Query q = em.createNamedQuery("SysPropaso.findPasosInicio");
        q.setParameter("spsInicio", pSpsInicio);
        q.setParameter("cxcId", crgxcolActual.getCxcId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        q.setParameter("diaActual", calendar.get(Calendar.DAY_OF_MONTH));
        return q.getResultList();
    }

    /**
     * Obtener menu aplicacion por colaborador
     *
     * @param pCxcId
     * @return
     */
    public List<AdmMenuapp> getLstMenuapXUsr(Integer pCxcId) {
        Query q = em.createNamedQuery("AdmMenuapp.menuAppXCol");
        q.setParameter("cxcId", pCxcId);
        return q.getResultList();
    }

    /**
     * Obtener modulos por menu
     *
     * @param pMenId Id del menu
     * @return lista de modulos por menu
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdmModapp> getLstModappXMenu(Integer pMenId) {
        Query q = em.createNamedQuery("AdmModapp.getModappXMenu");
        q.setParameter("menId", pMenId);
        return q.getResultList();
    }

    /**
     * Obtener submódulos por colaborador por módulo
     *
     * @param pCxcId
     * @param pModId
     * @return Lista de submódlos por colaborador por módulo
     */
    public List<AdmSubmodapp> getLstSubmodappXModXCpe(Integer pCxcId, Integer pModId) {
        Query q = em.createNamedQuery("AdmSubmodapp.getSubmodXModXCpe");
        q.setParameter("cxcId", pCxcId);
        q.setParameter("modId", pModId);
        q.setParameter("cxcPrincipal", true);
        return q.getResultList();
    }

    public List<SysSegtarea> getLstTarXUsrActivas(Integer pIntEstTarId, Boolean pBlnEstPaso) {
        Query q = em.createNamedQuery("SysSegtarea.tareaXUsrActPaso");
        q.setParameter("etrId", pIntEstTarId);
        q.setParameter("sgtEstpaso", Boolean.TRUE);
        q.setParameter("cxcId", crgxcolActual.getCxcId());
        return q.getResultList();
    }

    @Remove
    public void remove() {
    }

    /**
     * @return the sysSegtareaActual
     */
    public SysSegtarea getSysSegtareaActual() {
        return sysSegtareaActual;
    }

    /**
     * @param sysSegtareaActual the sysSegtareaActual to set
     */
    public void setSysSegtareaActual(SysSegtarea sysSegtareaActual) {
        this.sysSegtareaActual = sysSegtareaActual;
    }

    public AdmColxemp editarAdmColxemp(AdmColxemp pColxemp) {
        pColxemp = em.merge(pColxemp);
        return pColxemp;
    }

}
