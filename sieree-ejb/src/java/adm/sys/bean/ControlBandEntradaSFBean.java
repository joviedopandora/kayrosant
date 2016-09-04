/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.bean;

import adm.sys.dao.AdmColxemp;
import adm.sys.dao.SysSegtarea;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateful
@LocalBean
public class ControlBandEntradaSFBean {

    @PersistenceContext
    EntityManager em;
    private AdmColxemp admColxempLog = new AdmColxemp();

    public List<SysSegtarea> getLstTarXUsrActivas(Integer pIntEstTarId, Boolean pBlnEstPaso) {
        Query q = em.createNamedQuery("SysSegtarea.tareaXUsrActPaso");
        q.setParameter("etrId", pIntEstTarId);
        q.setParameter("sgtEstpaso", Boolean.TRUE);
        q.setParameter("cpeId", admColxempLog.getCpeId());
        return q.getResultList();
    }

    @Remove
    public void remove() {
    }

    /**
     * @return the admColxempLog
     */
    public AdmColxemp getAdmColxempLog() {
        return admColxempLog;
    }

    /**
     * @param admColxempLog the admColxempLog to set
     */
    public void setAdmColxempLog(AdmColxemp admColxempLog) {
        this.admColxempLog = admColxempLog;
    }
}
