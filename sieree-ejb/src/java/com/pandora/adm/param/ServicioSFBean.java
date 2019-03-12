/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.param;

import com.pandora.adm.dao.InvCatprod;
import com.pandora.adm.dao.InvProducto;
import com.pandora.mod.venta.dao.ServRfTipocliente;
import com.pandora.mod.venta.dao.VntProdxsrv;
import com.pandora.mod.venta.dao.VntRfTipocliente;
import com.pandora.mod.venta.dao.VntRfTiposervicio;
import com.pandora.mod.venta.dao.VntServicio;
import com.pandora.mod.venta.dao.VntServicioxservicio;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Javier
 */
@Stateful
@LocalBean
public class ServicioSFBean implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Remove
    public void remove() {

    }

    /**
     * Cargar lista de cargos
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntServicio> getLstAllServicios() {
        Query q = em.createNamedQuery("VntServicio.findAll");
        return q.getResultList();
    }

     /**
     * Cargar lista de cargos     
     * @param pSrvEst
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntServicio> getLstServiciosXEst(Boolean pSrvEst) {
        Query q = em.createNamedQuery("VntServicio.findByVsrvEst").
                setParameter("vsrvEst", pSrvEst);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntServicio> consultarServiciosPendientesAsociar(Long vsrvId, boolean vsrvEst) {
        Query q = null;
        if (vsrvId == null) {
            q = em.createNamedQuery("VntServicio.servXServicoHijos");
            
        }else{
            q = em.createNamedQuery("VntServicio.servXServicoHijosSinId");
            q.setParameter("vsrvId", vsrvId);
        }
        q.setParameter("vsrvEst", vsrvEst);
        return q.getResultList();
    }
    
     @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntServicioxservicio> consultarServiciosAsosiados(Long vsrvId) {
        Query q= em.createNamedQuery("VntServicioxservicio.findByServiciosAsociados");
        q.setParameter("vsrvId", vsrvId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRfTiposervicio> getLstVntRfTiposervicio(boolean tsrvEst) {
        Query q = em.createNamedQuery("VntRfTiposervicio.findByTsrvEst");
        q.setParameter("tsrvEst", tsrvEst);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public VntRfTiposervicio getVntRfTiposervicioXId(Integer tsrvId) {
        return em.getReference(VntRfTiposervicio.class, tsrvId);
    }
   @TransactionAttribute(TransactionAttributeType.REQUIRED) 
    public void elimiarPrdXServicio(VntProdxsrv pVntProdxsrv){
        VntProdxsrv vntProdxsrv = em.getReference(VntProdxsrv.class, pVntProdxsrv.getProdxsrvId());
        em.remove(vntProdxsrv);
        
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VntServicio editarVntServicio(VntServicio servicio) {
        servicio = em.merge(servicio);
        return servicio;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntProdxsrv> getLstVntProdxsrvXServicio(Long vsrvId) {
        Query q = em.createNamedQuery("VntProdxsrv.pxsXServicio");
        q.setParameter("vsrvId", vsrvId);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvCatprod> getLstInvCatprod(boolean cpdEst) {
        Query q = em.createNamedQuery("InvCatprod.findByCpdEst");
        q.setParameter("cpdEst", cpdEst);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InvProducto> getLstAllInvProductos(TablaConsultaDTO dto) {
        StringBuilder consulta = new StringBuilder("SELECT p FROM InvProducto p WHERE 1=1 ");
        if (dto.getIdProducto() != null) {
            consulta.append(" AND p.prdId = :prdId ");
        }
        if (dto.getCategoria() != null) {
            consulta.append(" AND p.cpdId.cpdId = :cpdId ");
        }
        if (dto.getNombreProducto() != null) {
            String v = dto.getNombreProducto().trim();
            if (!v.equals("")) {
                consulta.append(" AND p.prdNombre LIKE :prdNombre ");
            }
        }
        if (dto.getDescProducto() != null) {
            String v = dto.getDescProducto().trim();
            if (!v.equals("")) {
                consulta.append(" AND p.prdDesc LIKE :prdDesc ");
            }
        }
        consulta.append(" ORDER BY p.prdDesc, p.prdNombre");
        Query q = em.createQuery(consulta.toString());
        if (dto.getIdProducto() != null) {
            q.setParameter("prdId", dto.getIdProducto());
        }
        if (dto.getCategoria() != null) {
            q.setParameter("cpdId", dto.getCategoria());

        }
        if (dto.getNombreProducto() != null) {
            String v = dto.getNombreProducto().trim();
            if (!v.equals("")) {
                q.setParameter("prdNombre", "%" + v + "%");

            }
        }
        if (dto.getDescProducto() != null) {
            String v = dto.getDescProducto().trim();
            if (!v.equals("")) {
                q.setParameter("prdDesc", "%" + v + "%");
            }
        }
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarListaServicioXProducto(List<VntProdxsrv> lst) {
        for (VntProdxsrv v : lst) {
            em.merge(v);
        }
    }

    /**
     * Cargar lista de tipo de cliente por estado
     *
     * @param pTclEst
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<VntRfTipocliente> getLstVntRfTipocliente(boolean pTclEst) {
        Query q = em.createNamedQuery("VntRfTipocliente.findByTclEst");
        q.setParameter("tclEst", pTclEst);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public HashMap<Integer, ServRfTipocliente> obtenerServRfTipoclientePorServicio(Long vsrvId) {
        Query q = em.createNamedQuery("ServRfTipocliente.findByServicio");
        q.setParameter("vsrvId", vsrvId);
        List<ServRfTipocliente> resultado = q.getResultList();
        HashMap<Integer, ServRfTipocliente> salida = new HashMap<>();
        for (ServRfTipocliente s : resultado) {
            salida.put(s.getVntRfTipocliente().getTclId(), s);
        }
        return salida;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarServRfTipocliente(List<ServRfTipocliente> lista) {
        for (ServRfTipocliente s : lista) {
            s = em.merge(s);
        }

    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editarListaServicioXServicio(List<VntServicioxservicio> lst) {
        for (VntServicioxservicio v : lst) {
            em.merge(v);
        }
    }

}
