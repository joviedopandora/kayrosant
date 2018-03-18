/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.param;

import adm.sys.dao.AdmColaborador;
import com.pandora.mod.venta.dao.RfCargocontacto;
import com.pandora.mod.venta.dao.VntCliente;
import com.pandora.mod.venta.dao.VntDetallecliente;
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
        return em.createNamedQuery("VntCliente.clnXTextoGen").
                setParameter("txtBuscar", "%" + txtBuscar + "%").
                getResultList();

    }

    public List<VntCliente> getLstCliente(String txtBuscar) {
        return em.createNamedQuery("VntCliente.findAll").
                setParameter("txtBuscar", "%" + txtBuscar + "%").
                getResultList();

    }

    public List<VntDetallecliente> getLstDetalleClnXCln(Long clnId) {
        return em.createNamedQuery("VntDetallecliente.detalleXClienteGen").
                setParameter("clnId", clnId).
                getResultList();

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntDetallecliente editarDetalleCliente(VntDetallecliente pVntDetallecliente) {
        pVntDetallecliente = em.merge(pVntDetallecliente);
        return pVntDetallecliente;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gestión usuarios">
    public List<AdmColaborador> getLstColaboradorXText(String pTexto){
      return em.createNamedQuery("AdmColaborador.findCedONom").
              setParameter("colCedula", pTexto).
              setParameter("texto", "%" + pTexto + "%").
              getResultList();
    }
//</editor-fold>
}
