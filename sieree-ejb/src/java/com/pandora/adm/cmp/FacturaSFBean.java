/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.adm.cmp;
import adm.sys.dao.AdmCrgxcol;
import com.pandora.adm.dao.CmpDetallefact;
import com.pandora.adm.dao.CmpFactura;
import com.pandora.adm.dao.InvInvent;
import com.pandora.adm.dao.InvTipotransc;
import com.pandora.adm.dao.InvTransac;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
public class FacturaSFBean {

    @PersistenceContext
    EntityManager em;
    private CmpFactura cmpFacturaSel = new CmpFactura();
    private AdmCrgxcol admCrgxcolActual = new AdmCrgxcol();

    @Remove
    public void remove() {
    }

    public List<CmpFactura> getLstCmpFacturasXTarea(Long pStrId) {
        Query q = em.createNamedQuery("CmpFactura.findByStrId");
        q.setParameter("strId", pStrId);
        return q.getResultList();
    }

    public List<CmpFactura> getLstCmpFacturasXTareaXEnvPagoCuenta(Long pStrId, Boolean pFactEnvpagocuenta) {
        Query q = em.createNamedQuery("CmpFactura.findByStrIdXEnvpagocuenta");
        q.setParameter("strId", pStrId);
        q.setParameter("factEnvpagocuenta", pFactEnvpagocuenta);
        return q.getResultList();
    }

    public List<CmpFactura> getLstCmpFacturasXPrefactXTarea(Long pStrId, Boolean pFactPrefact) {
        Query q = em.createNamedQuery("CmpFactura.factrXPrefactXTarea");
        q.setParameter("strId", pStrId);
        q.setParameter("factPrefact", pFactPrefact);
        return q.getResultList();
    }

    public List<CmpFactura> getLstCmpFacturasXPrefactXTareaConDetPendiente(Long pStrId, Boolean pFactPrefact) {
        Query q = em.createNamedQuery("CmpFactura.factrXPrefactXTarea");
        q.setParameter("strId", pStrId);
        q.setParameter("factPrefact", pFactPrefact);
        List<CmpFactura> lstFacturasConDetPendiente = new ArrayList<>();
        for (CmpFactura cmpFactura : (List<CmpFactura>) q.getResultList()) {
            if (getCantDetalleFactXFactXBlnProcesar(cmpFactura.getFactId(), Boolean.FALSE) > 0L) {
                lstFacturasConDetPendiente.add(cmpFactura);
            }
        }
        return lstFacturasConDetPendiente;
    }

    public Long getCantDetalleFactXFactXBlnProcesar(Long pFactId, Boolean pDetfProcesadorecibido) {
        Query q = em.createNamedQuery("CmpDetallefact.cantDetXFactXProcesado");
        q.setParameter("factId", pFactId);
        q.setParameter("detfProcesadorecibido", pDetfProcesadorecibido);
        return (Long) q.getSingleResult();

    }

    public List<CmpDetallefact> getLstCmpDetallefacts(Long pFactId) {
        Query q = em.createNamedQuery("CmpDetallefact.detalleXFactura");
        q.setParameter("factId", pFactId);
        return q.getResultList();
    }

    public List<CmpDetallefact> getLstCmpDetallefactsXTarea(Long pStrId) {
        Query q = em.createNamedQuery("CmpDetallefact.detalleXTarea");
        q.setParameter("strId", pStrId);
        return q.getResultList();
    }

    public List<CmpDetallefact> getLstDetalleFactXTareaXProcesada(Long pStrId, Boolean pFactPrefact) {
        Query q = em.createNamedQuery("CmpDetallefact.detalleXTareaFactProcesada");
        q.setParameter("factPrefact", pFactPrefact);
        q.setParameter("strId", pStrId);
        return q.getResultList();

    }

    public Long getCantPrefactXStrId(Long pStrId) {
        Query q = em.createNamedQuery("CmpFactura.cantFactXStrId");
        q.setParameter("strId", pStrId);

        return (Long) q.getSingleResult();

    }

    public Long getCantPrefactXStrIdXPrefact(Long pStrId, Boolean pFactPrefact) {
        Query q = em.createNamedQuery("CmpFactura.cantPrefactXStrId");
        q.setParameter("strId", pStrId);
        q.setParameter("factPrefact", pFactPrefact);
        return (Long) q.getSingleResult();

    }

    public void editarFactura(CmpFactura pCmpFactura) {
        cmpFacturaSel = em.merge(pCmpFactura);
    }

    public void editarDetalleFactura(CmpDetallefact pCmpDetallefact) {
        em.merge(pCmpDetallefact);
    }

    /**
     * Grabar lista de detalles de factura
     *
     * @param pLstCmpDetallefacts
     */
    public void grabarLstDetalleFactura(List<CmpDetallefact> pLstCmpDetallefacts) {
        for (CmpDetallefact cmpDetallefact : pLstCmpDetallefacts) {
            cmpDetallefact = em.merge(cmpDetallefact);
        }
        if (getCantDetalleFactXFactXBlnProcesar(cmpFacturaSel.getFactId(), Boolean.FALSE) == 0) {
            cmpFacturaSel.setFactPrefact(Boolean.FALSE);
            editarFactura(cmpFacturaSel);
            grabarInventarioDesdeFacturaSel();
        }

    }

    public void grabarLstDetalleFacturaIncompleto(List<CmpDetallefact> pLstCmpDetallefacts) {
        for (CmpDetallefact cmpDetallefact : pLstCmpDetallefacts) {
            cmpDetallefact.setDetfProcesadorecibido(Boolean.FALSE);
            cmpDetallefact.setDetfProcesadocargainv(Boolean.FALSE);
            cmpDetallefact = em.merge(cmpDetallefact);
        }
        if (getCantDetalleFactXFactXBlnProcesar(cmpFacturaSel.getFactId(), Boolean.FALSE) == 0) {
            cmpFacturaSel.setFactPrefact(Boolean.FALSE);
            editarFactura(cmpFacturaSel);
            grabarInventarioDesdeFacturaSel();
        }

    }

    private void grabarInventarioDesdeFacturaSel() {
        for (CmpDetallefact cmpDetallefact : getLstCmpDetallefacts(cmpFacturaSel.getFactId())) {
            if (cmpDetallefact.getDetfProcesadocargainv().equals(Boolean.FALSE)) {
                InvInvent invInvent = new InvInvent();
                invInvent.setInvCant(cmpDetallefact.getDetfCantrecibida());
                invInvent.setInvPrecio(cmpDetallefact.getDetfValorunitario());
                invInvent.setMarId(cmpDetallefact.getCcmId().getMarId());
                invInvent.setPspId(cmpDetallefact.getCcmId().getPspId());
                invInvent.setPrdId(cmpDetallefact.getCcmId().getPrdId());
                invInvent = em.merge(invInvent);
                InvTransac invTransac = new InvTransac();
                invTransac.setInvId(invInvent);
                invTransac.setItrCant(cmpDetallefact.getDetfCantrecibida());
                invTransac.setItrValor(cmpDetallefact.getDetfValorunitario().multiply(BigDecimal.valueOf(cmpDetallefact.getDetfCantrecibida().longValue())));
                invTransac.setItrAnultr(Boolean.FALSE);
                invTransac.setItrObsr("Carga en inventario desde detalle factura");
                invTransac.setTtrId(em.getReference(InvTipotransc.class, 1));
                invTransac.setCxcId(admCrgxcolActual);
                invTransac.setItrFecpro(new Date());
                invTransac.setItrEstado(true);
                invTransac.setStrId(cmpFacturaSel.getStrId());
                invTransac.setFactId(cmpFacturaSel);
                em.merge(invTransac);
                cmpDetallefact.setDetfProcesadocargainv(Boolean.TRUE);
                cmpDetallefact = em.merge(cmpDetallefact);
            }


        }

        cmpFacturaSel.setFactProcesadocargainv(Boolean.TRUE);
        editarFactura(cmpFacturaSel);
    }

    /**
     * @return the cmpFacturaSel
     */
    public CmpFactura getCmpFacturaSel() {
        return cmpFacturaSel;
    }

    /**
     * @param cmpFacturaSel the cmpFacturaSel to set
     */
    public void setCmpFacturaSel(CmpFactura cmpFacturaSel) {
        this.cmpFacturaSel = cmpFacturaSel;
    }

    /**
     * @return the admCrgxcolActual
     */
    public AdmCrgxcol getAdmCrgxcolActual() {
        return admCrgxcolActual;
    }

    /**
     * @param admCrgxcolActual the admCrgxcolActual to set
     */
    public void setAdmCrgxcolActual(AdmCrgxcol admCrgxcolActual) {
        this.admCrgxcolActual = admCrgxcolActual;
    }
}
