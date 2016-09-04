/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.bean;


import adm.sys.dao.SysTablapararm;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lfchacon
 */
@Stateless
@LocalBean
public class ParametrizacionSLBean {

    @PersistenceContext
    EntityManager em;

    public List<SysTablapararm> getLstSysTablapararms() {
        Query q = em.createNamedQuery("SysTablapararm.findAll");
        return q.getResultList();
    }
}
