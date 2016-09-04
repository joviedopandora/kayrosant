/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Clase que contiene los eventos de la session http, realizar aquí los procesos
 * de liberación de recursos cuando se termina la session http
 *
 * @author Luis Fernando Chacón
 */
public class LimpiarSession implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    /**
     * Evento ejecutado por el servidor de aplicaciones justo antes de
     * destruirse la session http
     *
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession().getServletContext().getRequestDispatcher(null);
//        RequestDispatcher dispatcher = se.getSession().getServletContext().getRequestDispatcher("/Index.jsf");
        //dispatcher.forward(, null);

        //String str = se.getSession().getServletContext().getContextPath();
        //RequestDispatcher rd=  se.getSession().getServletContext().getContext(str).getRequestDispatcher("Salida.jsp");
        //TODO: Validar esta operación, recoger la ruta del parámetro
//        String rutaGral = File.separator + "home" + File.separator
//                + "archpg" + File.separator;
//        String rutaDest = rutaGral + "rips_" + se.getSession().getId();
//        String rutaDestFact = rutaGral + "CargaMasivaFact_" + se.getSession().getId();
//        File fileRutaDestSession = new File(rutaDest);
//
//        if (fileRutaDestSession.exists()) {
//            if (fileRutaDestSession.isDirectory()) {
//                for (File file : fileRutaDestSession.listFiles()) {
//                    if (file.exists()) {
//                        file.delete();
//                    }
//
//                }
//                fileRutaDestSession.delete();
//
//            }
//        }

//        File fileRutaDestSessionToF = new File(rutaDestFact);
//
//        if (fileRutaDestSessionToF.exists()) {
//            if (fileRutaDestSessionToF.isDirectory()) {
//                for (File file : fileRutaDestSessionToF.listFiles()) {
//                    if (file.exists()) {
//                        file.delete();
//                    }
//
//                }
//                fileRutaDestSessionToF.delete();
//
//            }
//        }

//        File fileRutaDestAut = new File(rutaGral);
//
//        if (fileRutaDestAut.exists()) {
//            if (fileRutaDestAut.isDirectory()) {
//                for (File file : fileRutaDestAut.listFiles()) {
//                    if (file.exists()) {
//                        file.delete();
//                    }
//
//                }
//                fileRutaDestSession.delete();
//
//            }
//        }


    }
}
