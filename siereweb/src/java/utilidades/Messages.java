/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author Garcia Bosso
 */
public final class Messages {

    private static final String BUNDLE_NAME_GENERAL = "com.pandora.web.liquidacion.propiedades"; //$NON-NLS-1$
    private static final ResourceBundle RESOURCE_BUNDLE_GENERAL = ResourceBundle.getBundle(BUNDLE_NAME_GENERAL);

    private Messages() {
    }

    public static String getMessageGeneral(String key) {
        try {
            return RESOURCE_BUNDLE_GENERAL.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

}
