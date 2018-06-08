/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.adm.param;

import adm.sys.dao.AdmSubmodapp;
import com.pandora.web.base.TablaBaseGrid;
import java.util.Objects;

/**
 *
 * @author Luis Fernando
 */
public class TablaAdmSubmodapp extends TablaBaseGrid {

    private AdmSubmodapp admSubmodapp = new AdmSubmodapp();

    public TablaAdmSubmodapp() {
    }

    public TablaAdmSubmodapp(AdmSubmodapp pAdmSubmodapp) {
        this.admSubmodapp = pAdmSubmodapp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.admSubmodapp);
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
        final TablaAdmSubmodapp other = (TablaAdmSubmodapp) obj;
        if (!Objects.equals(this.admSubmodapp, other.admSubmodapp)) {
            return false;
        }
        return true;
    }

    public AdmSubmodapp getAdmSubmodapp() {
        return admSubmodapp;
    }

    public void setAdmSubmodapp(AdmSubmodapp admSubmodapp) {
        this.admSubmodapp = admSubmodapp;
    }
}
