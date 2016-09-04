/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.bean;

import adm.sys.dao.AdmColxemp;
import adm.sys.dao.AdmCrgxcol;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis
 */
public abstract class BaseSistemaSFBean {

    protected AdmColxemp colxempLog = new AdmColxemp();
    protected AdmCrgxcol crgxcolActual = new AdmCrgxcol();

    @PersistenceContext()
    protected EntityManager em;

    public AdmColxemp getColxempLog() {
        return colxempLog;
    }

    public void setColxempLog(AdmColxemp colxempLog) {
        this.colxempLog = colxempLog;
    }

    public AdmCrgxcol getCrgxcolActual() {
        return crgxcolActual;
    }

    public void setCrgxcolActual(AdmCrgxcol crgxcolActual) {
        this.crgxcolActual = crgxcolActual;
    }

}
