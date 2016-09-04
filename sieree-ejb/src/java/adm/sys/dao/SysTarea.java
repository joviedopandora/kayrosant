/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "sys_tarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysTarea.findAll", query = "SELECT s FROM SysTarea s"),
    @NamedQuery(name = "SysTarea.findByStrId", query = "SELECT s FROM SysTarea s WHERE s.strId = :strId"),
    @NamedQuery(name = "SysTarea.findByStrNombre", query = "SELECT s FROM SysTarea s WHERE s.strNombre = :strNombre"),
    @NamedQuery(name = "SysTarea.findByStrFcre", query = "SELECT s FROM SysTarea s WHERE s.strFcre = :strFcre"),
    @NamedQuery(name = "SysTarea.tareaXUsrActPaso", query = "SELECT s FROM SysTarea s JOIN s.sysSegtareaList segt "
    + "JOIN segt.cxcId cxc JOIN s.etrId e WHERE e.etrId = :etrId AND segt.sgtEstpaso = :sgtEstpaso AND cxc.cxcId = :cxcId"),
    @NamedQuery(name = "SysTarea.tareasXPasoParaCerrar",query = "SELECT s FROM SysTarea s JOIN s.sysSegtareaList segt JOIN segt.spsId sps  "
    + "JOIN s.etrId e WHERE e.etrId = :etrId AND sps.spsId = :spsId AND segt.sgtEstpaso = :sgtEstpaso")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "SysTarea.tareaXMesXEst", query = "SELECT count(str_id) FROM sys_tarea WHERE"
    + "  date_part('MONTH' ,str_fcre ) = ? AND date_part('YEAR' ,str_fcre ) = ? AND  etr_id = ? AND  spr_id = ?")
})
public class SysTarea implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName="sys_tarea_str_id_seq",name="str_id_seq",allocationSize=1)
    @GeneratedValue(generator="str_id_seq",strategy= GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "str_id")
    private Long strId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "str_nombre")
    private String strNombre;
    @Column(name = "str_fcre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date strFcre;
    @JoinColumn(name = "spr_id", referencedColumnName = "spr_id")
    @ManyToOne
    private SysProceso sprId;
    @JoinColumn(name = "etr_id", referencedColumnName = "etr_id")
    @ManyToOne
    private SysEsttarea etrId;
    @OneToMany(mappedBy = "strId")
    private List<SysSegtarea> sysSegtareaList;

    public SysTarea() {
    }

    public SysTarea(Long strId) {
        this.strId = strId;
    }

    public SysTarea(Long strId, String strNombre) {
        this.strId = strId;
        this.strNombre = strNombre;
    }

    public Long getStrId() {
        return strId;
    }

    public void setStrId(Long strId) {
        this.strId = strId;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public Date getStrFcre() {
        return strFcre;
    }

    public void setStrFcre(Date strFcre) {
        this.strFcre = strFcre;
    }

    public SysProceso getSprId() {
        return sprId;
    }

    public void setSprId(SysProceso sprId) {
        this.sprId = sprId;
    }

    public SysEsttarea getEtrId() {
        return etrId;
    }

    public void setEtrId(SysEsttarea etrId) {
        this.etrId = etrId;
    }

    @XmlTransient
    public List<SysSegtarea> getSysSegtareaList() {
        return sysSegtareaList;
    }

    public void setSysSegtareaList(List<SysSegtarea> sysSegtareaList) {
        this.sysSegtareaList = sysSegtareaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (strId != null ? strId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysTarea)) {
            return false;
        }
        SysTarea other = (SysTarea) object;
        if ((this.strId == null && other.strId != null) || (this.strId != null && !this.strId.equals(other.strId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.padora.dao.SysTarea[ strId=" + strId + " ]";
    }
}
