/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;

/**
 *
 * @author carlos
 */
public class ServletListenerClase implements javax.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        Logger.getLogger(ServletListenerClase.class.getName()).log(Level.SEVERE, null, "Timer Envio de Correo Contexto inicializado....");
        System.out.println("Contexto inicializado para envio de correo....");

//Crear carpeta de normatividad si no existe
//        System.out.println("Contexto inicializado, crear carpeta contrato si no existe....");
//        String homeusuarioSO = System.getProperty("user.home");
//        String carpetaContrato = "contrato";
//        String rutaContrato = homeusuarioSO + File.separator + carpetaContrato;
//        File f = new File(rutaContrato);
//        if (f.exists()) {
//            if (!f.isDirectory()) {
//                f.delete();
//                f.mkdir();
//            }
//        } else {
//            f.mkdir();
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        Logger.getLogger(ServletListenerClase.class.getName()).log(Level.SEVERE, null, "Servlet Context is destroyed.... timer destruidos");
        System.out.println("Servlet Context is destroyed....");

    }
}
