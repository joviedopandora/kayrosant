/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pandora.web.adm.param;

import adm.sys.dao.AdmColxemp;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author byrobles
 */
public class TablaAdmColXEmp extends TablaBaseGrid{
    
    private AdmColxemp admColxemp = new AdmColxemp();

    public TablaAdmColXEmp() {
    }
    
    public TablaAdmColXEmp(AdmColxemp pAdmColxemp){
        this.admColxemp = pAdmColxemp;
    }

    /**
     * @return the admColxemp
     */
    public AdmColxemp getAdmColxemp() {
        return admColxemp;
    }

    /**
     * @param admColxemp the admColxemp to set
     */
    public void setAdmColxemp(AdmColxemp admColxemp) {
        this.admColxemp = admColxemp;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.admColxemp);
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
        final TablaAdmColXEmp other = (TablaAdmColXEmp) obj;
        if (!Objects.equals(this.admColxemp, other.admColxemp)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TablaAdmColXEmp{" + "admColxemp=" + admColxemp + '}';
    }
}
