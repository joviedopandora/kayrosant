/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.File;

/**
 *
 * @author breyner
 */
public class ManejoCarpeta {

    public static void borrarCarpetas(String rutaDest) {
        File fileRutaDestSessionToF = new File(rutaDest);

        if (fileRutaDestSessionToF.exists()) {
            if (fileRutaDestSessionToF.isDirectory()) {
                for (File file : fileRutaDestSessionToF.listFiles()) {
                    if (file.exists()) {
                        file.delete();
                    }

                }
                fileRutaDestSessionToF.delete();

            } else {
                fileRutaDestSessionToF.delete();
            }
        }
    }
}
