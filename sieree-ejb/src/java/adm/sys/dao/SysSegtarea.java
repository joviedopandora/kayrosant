/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adm.sys.dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "sys_segtarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysSegtarea.findAll", query = "SELECT s FROM SysSegtarea s"),
    @NamedQuery(name = "SysSegtarea.findBySgtId", query = "SELECT s FROM SysSegtarea s WHERE s.sgtId = :sgtId"),
    @NamedQuery(name = "SysSegtarea.findBySgtFinicio", query = "SELECT s FROM SysSegtarea s WHERE s.sgtFinicio = :sgtFinicio"),
    @NamedQuery(name = "SysSegtarea.findBySgtFfinal", query = "SELECT s FROM SysSegtarea s WHERE s.sgtFfinal = :sgtFfinal"),
    @NamedQuery(name = "SysSegtarea.findBySgtFcre", query = "SELECT s FROM SysSegtarea s WHERE s.sgtFcre = :sgtFcre"),
    @NamedQuery(name = "SysSegtarea.findBySgtEstpaso", query = "SELECT s FROM SysSegtarea s WHERE s.sgtEstpaso = :sgtEstpaso"),
    @NamedQuery(name = "SysSegtarea.tareaXUsrActPaso", query = "SELECT s FROM SysSegtarea s JOIN s.cxcId cxc JOIN s.strId str "
    + "JOIN str.etrId e WHERE s.sgtEstpaso = :sgtEstpaso "
    + "AND cxc.cxcId = :cxcId AND e.etrId = :etrId "),
   //update seguimiento por tarea para un paso
      @NamedQuery(name = "SysSegtarea.updateXtarea", query = "UPDATE SysSegtarea s  SET s.sgtEstpaso = :sgtEstpaso "
        + "WHERE s.strId.strId = :strId AND s.sgtEstpaso = :sgtEstpaso1 "),
      @NamedQuery(name = "SysSegtarea.updateXtareaXPaso", query = "UPDATE SysSegtarea s   SET s.sgtEstpaso = :sgtEstpaso "
        + "WHERE s.strId.strId = :strId AND s.sgtEstpaso = :sgtEstpaso1 AND s.spsId.spsId = :spsId "),
})
public class SysSegtarea implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(sequenceName="sys_segtarea_sgt_id_seq",name="sys_segtarea_sgt_id_seq",allocationSize=1)
    @GeneratedValue(generator="sys_segtarea_sgt_id_seq",strategy= GenerationType.SEQUENCE)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sgt_id")
    private Long sgtId;
    @Column(name = "sgt_finicio")
    @Temporal(TemporalType.DATE)
    private Date sgtFinicio;
    @Column(name = "sgt_ffinal")
    @Temporal(TemporalType.DATE)
    private Date sgtFfinal;
    @Column(name = "sgt_fcre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sgtFcre;
    @Column(name = "sgt_estpaso")
    private Boolean sgtEstpaso;
    @JoinColumn(name = "str_id", referencedColumnName = "str_id")
    @ManyToOne
    private SysTarea strId;
    @JoinColumn(name = "sps_id", referencedColumnName = "sps_id")
    @ManyToOne
    private SysPropaso spsId;
   @JoinColumn(name = "cxc_id", referencedColumnName = "cxc_id")
    @ManyToOne
    private AdmCrgxcol cxcId;

    public SysSegtarea() {
    }

    public SysSegtarea(Long sgtId) {
        this.sgtId = sgtId;
    }

    public Long getSgtId() {
        return sgtId;
    }

    public void setSgtId(Long sgtId) {
        this.sgtId = sgtId;
    }

    public Date getSgtFinicio() {
        return sgtFinicio;
    }

    public void setSgtFinicio(Date sgtFinicio) {
        this.sgtFinicio = sgtFinicio;
    }

    public Date getSgtFfinal() {
        return sgtFfinal;
    }

    public void setSgtFfinal(Date sgtFfinal) {
        this.sgtFfinal = sgtFfinal;
    }

    public Date getSgtFcre() {
        return sgtFcre;
    }

    public void setSgtFcre(Date sgtFcre) {
        this.sgtFcre = sgtFcre;
    }

    public Boolean getSgtEstpaso() {
        return sgtEstpaso;
    }

    public void setSgtEstpaso(Boolean sgtEstpaso) {
        this.sgtEstpaso = sgtEstpaso;
    }

    public SysTarea getStrId() {
        return strId;
    }

    public void setStrId(SysTarea strId) {
        this.strId = strId;
    }

    public SysPropaso getSpsId() {
        return spsId;
    }

    public void setSpsId(SysPropaso spsId) {
        this.spsId = spsId;
    }

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgtId != null ? sgtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysSegtarea)) {
            return false;
        }
        SysSegtarea other = (SysSegtarea) object;
        if ((this.sgtId == null && other.sgtId != null) || (this.sgtId != null && !this.sgtId.equals(other.sgtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pandora.adm.dao.SysSegtarea[ sgtId=" + sgtId + " ]";
    }

    /**
     * @return the cxcId
     */
    public AdmCrgxcol getCxcId() {
        return cxcId;
    }

    /**
     * @param cxcId the cxcId to set
     */
    public void setCxcId(AdmCrgxcol cxcId) {
        this.cxcId = cxcId;
    }
}
