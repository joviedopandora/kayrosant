/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author lfchacon
 */
public class ConvertMayusc implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return value.toUpperCase();
        }
        return "";
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return ((String) value).toUpperCase();
        }
        return "";



    }
}
