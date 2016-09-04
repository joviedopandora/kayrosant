/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adm.sys.bean;

import adm.sys.dao.AdmTipopagoxcol;
import com.pandora.adm.rf.dao.RfCiudad;
import com.pandora.adm.rf.dao.RfDep;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class DatosGeneralesSLBean {
    
    @PersistenceContext
    EntityManager em;
    
    public List<RfDep> getLstDeps(){
        Query q = em.createNamedQuery("RfDep.findAll");
        return q.getResultList();
    }
    
    public List<RfCiudad> getLstCiuXDep(String pDepId){
        Query q = em.createNamedQuery("RfCiudad.ciuXDept");
        q.setParameter("depId", pDepId);
        return q.getResultList();
    }
    
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
     public AdmTipopagoxcol getTipoPago(Integer id){
         return em.find(AdmTipopagoxcol.class, id);
     }
 }
