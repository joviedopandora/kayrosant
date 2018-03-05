/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.param;

import com.pandora.mod.venta.dao.RfCargocontacto;
import com.pandora.mod.venta.dao.VntCliente;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Luis Fernando
 */
@Stateless
@LocalBean
public class AdmParametrizacionSLBean {

    @PersistenceContext
    EntityManager em;

    //<editor-fold defaultstate="collapsed" desc="CargosCleintes">
    public RfCargocontacto grabarCargoCliente(RfCargocontacto pCargocontacto) {
        return em.merge(pCargocontacto);
    }

    public List<RfCargocontacto> getLstCargocontactos() {
        return em.createNamedQuery("RfCargocontacto.findAll").getResultList();
    }

    public List<RfCargocontacto> getLstCargocontactosXEstado(Boolean pCargoEst) {
        return em.createNamedQuery("RfCargocontacto.findByCargoEst").setParameter("cargoEst", pCargoEst).getResultList();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gestiòn clientes">
    public List<VntCliente> getLstClienteXTexto(String txtBuscar) {
       return em.createNamedQuery("VntCliente.clnXTexto").
               setParameter("txtBuscar", "%" + txtBuscar + "%").
               getResultList();
      
    }
//</editor-fold>
}
