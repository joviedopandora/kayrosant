/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;


import javax.faces.application.FacesMessage;
import javax.swing.ImageIcon;


/**
 *
 * @author JAOVIEDO
 */
public class MensajeError extends FacesMessage  {

  
    private String strError;
    ImageIcon icon = new ImageIcon("/recursos//images/iconos/error.gif");



    // <editor-fold defaultstate="collapsed" desc="Constructor">
    public MensajeError(String strError) {
        this.strError = strError;
        this.setDetail(strError);
        this.setSummary(strError);
        this.setSeverity(FacesMessage.SEVERITY_WARN);


    }
    public MensajeError(){
        
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="metodos de la clase">
    /**
     * @return the strError
     */
    public String getStrError() {
        return strError;
    }

    /**
     * @param strError the strError to set
     */
    public void setStrError(String strError) {
        this.strError = strError;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }// </editor-fold>
}
