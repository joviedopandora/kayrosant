/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.procesos;

import adm.sys.dao.AdmCrgxcol;
import com.icesoft.faces.context.Resource;
import com.pandora.adm.rf.dao.RfEstadofactura;
import com.pandora.bussiness.util.EnEstadoFactura;
import com.pandora.mod.venta.dao.LogFactura;
import com.pandora.mod.venta.dao.LogRemision;
import com.pandora.mod.venta.dao.VntFactura;
import com.pandora.mod.venta.dao.VntRemision;
import com.pandora.web.base.TablaBaseGrid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Garcia Bosso
 */
public class TablaRespuestaFacturaDTO extends TablaBaseGrid {

    private VntFactura factura = null;
    private VntRemision remision = null;
    private Date fechaRadicacion = null;
    private String numeroFactura = null;
    private String tipoCliente = null;//TODO: cargar tipo cliente
    private Date fechaCreacion = null;
    private RfEstadofactura estado = null;
    private com.icesoft.faces.context.Resource resource;

    public class Log {

        private Date fecha;
        private RfEstadofactura estado;
        private AdmCrgxcol userCambio;

        public Log() {
        }

        public Log(Date fecha, RfEstadofactura estado, AdmCrgxcol userCambio) {
            this.fecha = fecha;
            this.estado = estado;
            this.userCambio = userCambio;
        }

        public Date getFecha() {
            return fecha;
        }

        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }


        public AdmCrgxcol getUserCambio() {
            return userCambio;
        }

        public void setUserCambio(AdmCrgxcol userCambio) {
            this.userCambio = userCambio;
        }

        public RfEstadofactura getEstado() {
            return estado;
        }

        public void setEstado(RfEstadofactura estado) {
            this.estado = estado;
        }
        

    }

    public String getMessage() {
        return this.factura == null ? "Remisión" : "Factura";
    }

    private void load(VntFactura fact) {
        this.fechaRadicacion = fact.getVfctfecharadica();
        this.numeroFactura = fact.getVfctNumfactura();
        this.fechaCreacion = fact.getFactsrFecha();
        this.estado = fact.getEftId();

        //carga nombre del reporte
        this.strEstado = "F_" + fact.getVfctNumfactura() + "_"
                + (fact.getRgvtId().getClnId().getClnAlias() == null
                        ? fact.getRgvtId().getClnId().getNombres()
                        : fact.getRgvtId().getClnId().getClnAlias())
//                + "_"
//                + (fact.getRgvtId().getVdeId().getVdeObsr() == null
//                        ? ""
//                        : fact.getRgvtId().getVdeId().getVdeObsr())
                +"_"
                + fact.getEftId().getEftNombre()
                + ".pdf";
    }

    private void load(VntRemision rem) {
        this.fechaRadicacion = rem.getVfctfecharadica();
        this.numeroFactura = rem.getVrmsNumremision();
        this.fechaCreacion = rem.getVrmsFecha();
        this.estado = rem.getEftId();

        // carga nombre reporte
        this.strEstado = "R_" + rem.getVrmsNumremision() + "_"
                + (rem.getRgvtId().getClnId().getClnAlias() == null
                        ? rem.getRgvtId().getClnId().getNombres()
                        : rem.getRgvtId().getClnId().getClnAlias())
                + "_"
//                + (rem.getRgvtId().getVdeId().getVdeObsr() == null
//                        ? ""
//                        : rem.getRgvtId().getVdeId().getVdeObsr())
//                +"_"
                + rem.getEftId().getEftNombre()
                + ".pdf";
    }

    public TablaRespuestaFacturaDTO() {
    }

    public TablaRespuestaFacturaDTO(VntFactura factura) {
        this.factura = factura;
        this.load(this.factura);
    }

    public TablaRespuestaFacturaDTO(VntRemision remision) {
        this.remision = remision;
        this.load(this.remision);
    }

    public VntFactura getFactura() {
        return factura;
    }

    public void setFactura(VntFactura factura) {
        this.factura = factura;
    }

    public VntRemision getRemision() {
        return remision;
    }

    public void setRemision(VntRemision remision) {
        this.remision = remision;
    }

    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public RfEstadofactura getEstado() {
        return estado;
    }

    public void setEstado(RfEstadofactura estado) {
        this.estado = estado;
    }

    public boolean isRenderDateTimeSelecionado() {
        return (estado == null ? false : estado.getEftId().equals(EnEstadoFactura.EMITIDO.getId()));
    }

    public boolean isRenderAnulado() {
        return (estado == null ? false : !(estado.getEftId().equals(EnEstadoFactura.PAGADA.getId()) || estado.getEftId().equals(EnEstadoFactura.ANULADO.getId())));

    }

    public boolean isRenderPagar() {
        return (estado == null ? false : (estado.getEftId().equals(EnEstadoFactura.EMITIDO.getId())? false :!(estado.getEftId().equals(EnEstadoFactura.PAGADA.getId()) || estado.getEftId().equals(EnEstadoFactura.ANULADO.getId()))));

    }

    public boolean isRenderPendientePagar() {
        return (estado == null ? false : estado.getEftId().equals(EnEstadoFactura.VENCIDA.getId()));

    }
    private List<Log> logs = new ArrayList<>();

    public void loadLogFactura(List<LogFactura> logsFactura) {
        logs = new ArrayList<>();
        if (logsFactura != null && !logsFactura.isEmpty()) {
            for (LogFactura l : logsFactura) {
                logs.add(new Log(l.getLfFechaproceso(), l.getRfEstadofactura(), l.getAdmCrgxcol()));
            }
        }
    }

    public void loadLogRemision(List<LogRemision> logsRemison) {
        logs = new ArrayList<>();
        if (logsRemison != null && !logsRemison.isEmpty()) {
            for (LogRemision l : logsRemison) {
                logs.add(new Log(l.getLrFechaproceso(), l.getRfEstadofactura(), l.getAdmCrgxcol()));
            }
        }
    }

    //TODO: load log remision
    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    public Long getNumeroVenta(){
        if(remision!= null){
            return remision.getRgvtId().getRgvtId();
        }
        if(factura != null){
            return factura.getRgvtId().getRgvtId();
        }
        return null;
    }
    public String getNombreCliente(){
        if(remision!= null){
            return remision.getRgvtId().getClnId().getNombres();
        }
        if(factura != null){
            return factura.getRgvtId().getClnId().getNombres();
        }
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.factura == null ? this.remision : this.factura);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaRespuestaFacturaDTO other = (TablaRespuestaFacturaDTO) obj;
        return Objects.equals(this.factura == null ? this.remision : this.factura, other.factura == null ? other.remision : other.factura);
    }

}
