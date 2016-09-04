/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.cmp;

import com.pandora.adm.dao.CmpPxraprob;
import com.pandora.adm.dao.InvInvent;
import com.pandora.adm.dao.InvTransac;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis
 */
@Stateful
@LocalBean
public class TransaccionInvSFBean {

    @PersistenceContext
    EntityManager em;
    
 // public List<CmpPxraprob> getLst

    public void addIngresoInv(InvInvent pInvInvent) {
        pInvInvent = em.merge(pInvInvent);


    }

    public void addTransInv(InvTransac pInvTransac) {
        pInvTransac = em.merge(pInvTransac);
    }

    @Remove
    public void remove() {
    }
}
