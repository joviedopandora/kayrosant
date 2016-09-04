/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.ordenprod;

import adm.sys.dao.AdmCrgxcol;
import com.icesoft.faces.context.Resource;
import com.pandora.bussiness.util.EnEstadoLogistica;
import com.pandora.bussiness.util.EnEstadosOp;
import com.pandora.mod.ordenprod.dao.LogOrdenprod;
import com.pandora.mod.ordenprod.dao.PopOrdenprod;
import com.pandora.mod.ordenprod.dao.RfEstadoOP;
import com.pandora.mod.venta.dao.VntCronograma;
import com.pandora.web.base.TablaBaseGrid;
import com.pandora.web.procesos.TablaRespuestaFacturaDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class TablaPopOrdenProduccion extends TablaBaseGrid {

    private PopOrdenprod popOrdenprod = new PopOrdenprod();
    private VntCronograma cronograma = null;
    private com.icesoft.faces.context.Resource resource = null;

    public class Log {

        private Date fecha;
        private RfEstadoOP estado;
        private AdmCrgxcol userCambio;

        public Log() {
        }

        public Log(Date fecha, RfEstadoOP estado, AdmCrgxcol userCambio) {
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

        public RfEstadoOP getEstado() {
            return estado;
        }

        public void setEstado(RfEstadoOP estado) {
            this.estado = estado;
        }

    }

    private List<Log> logs = new ArrayList<>();

    public void loadLogFactura(List<LogOrdenprod> logsOrdenprod) {
        logs = new ArrayList<>();
        if (logsOrdenprod != null && !logsOrdenprod.isEmpty()) {
            for (LogOrdenprod l : logsOrdenprod) {
                logs.add(new Log(l.getOprlFechaproceso(), l.getEopIdnew(), l.getCxcIdnew()));
            }
        }
    }

    //TODO: load log remision
    public List<TablaPopOrdenProduccion.Log> getLogs() {
        return logs;
    }

    public void setLogs(List<TablaPopOrdenProduccion.Log> logs) {
        this.logs = logs;
    }

    public TablaPopOrdenProduccion() {
    }

    public TablaPopOrdenProduccion(PopOrdenprod pPopOrdenprod) {
        this.popOrdenprod = pPopOrdenprod;

    }

    /**
     * @return the popOrdenprod
     */
    public PopOrdenprod getPopOrdenprod() {
        return popOrdenprod;
    }

    /**
     * @param popOrdenprod the popOrdenprod to set
     */
    public void setPopOrdenprod(PopOrdenprod popOrdenprod) {
        this.popOrdenprod = popOrdenprod;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.popOrdenprod);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TablaPopOrdenProduccion other = (TablaPopOrdenProduccion) obj;
        if (!Objects.equals(this.popOrdenprod, other.popOrdenprod)) {
            return false;
        }
        return true;
    }

    public VntCronograma getCronograma() {
        return cronograma;
    }

    public void setCronograma(VntCronograma cronograma) {
        this.cronograma = cronograma;
    }

    @Override
    public String toString() {
        return "TablaPopOrdenProduccion{" + "popOrdenprod=" + popOrdenprod + '}';
    }

    public boolean isShowAnularButton() {
        if (this.popOrdenprod != null && this.popOrdenprod.getRfEstadoOP() != null
                && !this.popOrdenprod.getRfEstadoOP().getEopId().
                equals(EnEstadosOp.ANULADO.getId())) {
            return !this.popOrdenprod.getEevId().getEevId().
                    equals(EnEstadoLogistica.EVENTO_RECIBIDO.getId());

        }
        return false;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getNameInforme() {
        if (this.popOrdenprod != null && this.popOrdenprod.getOprId() != null) {
            return this.strEstado = "OP_" + this.popOrdenprod.getOprId()
                    + (this.popOrdenprod.getOprTitulo() == null ? "" : "_" + this.popOrdenprod.getOprTitulo().replace(" ", "_"))
                    + ".pdf";
        }
        return "OP.pdf";
    }

}
