/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.imagenes.usuarios;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;


import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Breyner Robles
 */
public class JavaMailMoros {

    public static String TEXTO_MENSAJE_CORREO = "<p ><font style='font-weight: bold;'>Apreciado(a) Cliente :</font> NOMBRECLIENTE </br>"
            + "<font style='font-weight: bold;'>Usted adeuda al Señor Breyner Yesit Robles Caro  La suma de : VALOR</font> </p>"
            + "<p >Este mail ha sido enviado a EMAILUSUARIO </br>"
            + "Por favor cancele lo mas pronto posible. </br>"
            + " Bogot&aacute; - Colombia. </p> ";

    /**
     * @param args the command line arguments
     */
    /*  public static void main(String[] args) {
     envioCorreo(1, "$400.000");
     envioCorreo(2, "$400.000");
     envioCorreo(3, "$4.200.000");
     envioCorreo(4, "$4.200.000");
     }*/
    public static boolean envioCorreo(int c, String valor) {
        if (c == 1) {
            try {
                //sacar los datos de la empresa

                Properties props = new Properties();
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.auth", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", "byrobles2009@gmail.com");


                Session session = Session.getDefaultInstance(props, null);
                // session.setDebug(true);

                // Se compone la parte del texto
                // BodyPart texto = new MimeBodyPart();

                HashMap<String, String> listaN = new HashMap<String, String>();
                listaN.put("Bayron Lara", "balava88@gmail.com");
                listaN.put("Bayron Lara V", "bayron.lara@gonetusa.com");
                listaN.put("Jorge Vizcaino", "jorge.vizcaino@gonetusa.com");
                listaN.put("Jorge Vizcaino A", "jork00231@gmail.com");




                String msj = "<p ><font style='font-weight: bold;'>Apreciado(a) Cliente :</font> Bayron Lara </br>"
                        + "<font style='font-weight: bold;'>Usted adeuda al Señor Breyner Yesit Robles Caro  La suma de : $400.000</font> </p>"
                        + "<p >Este mail ha sido enviado a balava88@gmail.com </br>"
                        + "Por favor cancele lo mas pronto posible. </br>"
                        + " Bogot&aacute; - Colombia. </p> ";
                //    msj = msj.replaceAll("VALOR", valor);
                //  texto.setText(msj, "ISO-8859-1", "html");

                //Se compone el adjunto con PDF

                Address[] addrTo = new Address[1];
                // addrTo[0] = new InternetAddress("elpapis601@hotmail.com");
                addrTo[0] = new InternetAddress("balava88@gmail.com");
                /* addrTo[1] = new InternetAddress("javiergarciaruiz1989@gmail.com");
                 addrTo[2] = new InternetAddress("javieroviedo.it@hotmail.com");*/

                // Se compone el correo, dando to, from, subject y el
                // contenido.
                MimeMessage message = new MimeMessage(session);
                message.setSubject("Pago de deudas Breyner Robles");
                message.setSentDate(new Date());
                message.setFrom(new InternetAddress("byrobles2009@gmail.com"));
                /* message.addRecipient(
                 Message.RecipientType.TO,
                 new InternetAddress("chuidiang@gmail.com"));*/
                message.setText(msj, "ISO-8859-1", "html");

                message.addRecipients(RecipientType.TO, addrTo);


                // Se envia el correo.
                Transport t = session.getTransport("smtp");
                t.connect("byrobles2009@gmail.com", "breyner1043930247");
                t.sendMessage(message, message.getAllRecipients());
                t.close();

            } catch (MessagingException ex) {
                //Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
                //mostrarError("Ocurrio un error al enviar correo ", 3);
            }
        }
        if (c == 2) {
            try {
                //sacar los datos de la empresa

                Properties props = new Properties();
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.auth", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", "byrobles2009@gmail.com");


                Session session = Session.getDefaultInstance(props, null);
                // session.setDebug(true);

                // Se compone la parte del texto
                // BodyPart texto = new MimeBodyPart();

                HashMap<String, String> listaN = new HashMap<String, String>();
                listaN.put("Bayron Lara", "balava88@gmail.com");
                listaN.put("Bayron Lara V", "bayron.lara@gonetusa.com");
                listaN.put("Jorge Vizcaino", "jorge.vizcaino@gonetusa.com");
                listaN.put("Jorge Vizcaino A", "jork00231@gmail.com");



                String msj = "<p ><font style='font-weight: bold;'>Apreciado(a) Cliente :</font> Bayron Lara </br>"
                        + "<font style='font-weight: bold;'>Usted adeuda al Señor Breyner Yesit Robles Caro  La suma de : $400.000</font> </p>"
                        + "<p >Este mail ha sido enviado a bayron.lara@gonetusa.com </br>"
                        + "Por favor cancele lo mas pronto posible. </br>"
                        + " Bogot&aacute; - Colombia. </p> ";

                //Se compone el adjunto con PDF

                Address[] addrTo = new Address[1];
                // addrTo[0] = new InternetAddress("elpapis601@hotmail.com");
                addrTo[0] = new InternetAddress("bayron.lara@gonetusa.com");
                /* addrTo[1] = new InternetAddress("javiergarciaruiz1989@gmail.com");
                 addrTo[2] = new InternetAddress("javieroviedo.it@hotmail.com");*/

                // Se compone el correo, dando to, from, subject y el
                // contenido.
                MimeMessage message = new MimeMessage(session);
                message.setSubject("Pago de deudas Breyner Robles");
                message.setSentDate(new Date());
                message.setFrom(new InternetAddress("byrobles2009@gmail.com"));
                /* message.addRecipient(
                 Message.RecipientType.TO,
                 new InternetAddress("chuidiang@gmail.com"));*/
                message.setText(msj, "ISO-8859-1", "html");

                message.addRecipients(RecipientType.TO, addrTo);


                // Se envia el correo.
                Transport t = session.getTransport("smtp");
                t.connect("byrobles2009@gmail.com", "breyner1043930247");
                t.sendMessage(message, message.getAllRecipients());
                t.close();

            } catch (MessagingException ex) {
                //Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
                //mostrarError("Ocurrio un error al enviar correo ", 3);
            }
        }
        if (c == 3) {
            try {
                //sacar los datos de la empresa

                Properties props = new Properties();
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.auth", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", "byrobles2009@gmail.com");


                Session session = Session.getDefaultInstance(props, null);
                // session.setDebug(true);

                // Se compone la parte del texto
                // BodyPart texto = new MimeBodyPart();

                HashMap<String, String> listaN = new HashMap<String, String>();
                listaN.put("Bayron Lara", "balava88@gmail.com");
                listaN.put("Bayron Lara V", "bayron.lara@gonetusa.com");
                listaN.put("Jorge Vizcaino", "jorge.vizcaino@gonetusa.com");
                listaN.put("Jorge Vizcaino A", "jork00231@gmail.com");


                String msj = "<p ><font style='font-weight: bold;'>Apreciado(a) Cliente :</font> Jorge Vizcaino </br>"
                        + "<font style='font-weight: bold;'>Usted adeuda al Señor Breyner Yesit Robles Caro  La suma de : $4.400.000</font> </p>"
                        + "<p >Este mail ha sido enviado a jorge.vizcaino@gonetusa.com </br>"
                        + "Por favor cancele lo mas pronto posible. </br>"
                        + " Bogot&aacute; - Colombia. </p> ";

                /* String msj = TEXTO_MENSAJE_CORREO.replaceAll("NOMBRECLIENTE", "Jorge Vizcaino ");
                 msj = msj.replaceAll("EMAILUSUARIO", "jorge.vizcaino@gonetusa.com");
                 msj = msj.replaceAll("VALOR", valor);*/


                //Se compone el adjunto con PDF

                Address[] addrTo = new Address[1];
                // addrTo[0] = new InternetAddress("elpapis601@hotmail.com");
                addrTo[0] = new InternetAddress("jorge.vizcaino@gonetusa.com");
                /* addrTo[1] = new InternetAddress("javiergarciaruiz1989@gmail.com");
                 addrTo[2] = new InternetAddress("javieroviedo.it@hotmail.com");*/

                // Se compone el correo, dando to, from, subject y el
                // contenido.
                MimeMessage message = new MimeMessage(session);
                message.setSubject("Pago de deudas Breyner Robles");
                message.setSentDate(new Date());
                message.setFrom(new InternetAddress("byrobles2009@gmail.com"));
                /* message.addRecipient(
                 Message.RecipientType.TO,
                 new InternetAddress("chuidiang@gmail.com"));*/
                message.setText(msj, "ISO-8859-1", "html");

                message.addRecipients(RecipientType.TO, addrTo);


                // Se envia el correo.
                Transport t = session.getTransport("smtp");
                t.connect("byrobles2009@gmail.com", "breyner1043930247");
                t.sendMessage(message, message.getAllRecipients());
                t.close();

            } catch (MessagingException ex) {
                //Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
                //mostrarError("Ocurrio un error al enviar correo ", 3);
            }
        }
        if (c == 4) {
            try {
                //sacar los datos de la empresa

                Properties props = new Properties();
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.auth", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", "byrobles2009@gmail.com");


                Session session = Session.getDefaultInstance(props, null);
                // session.setDebug(true);

                // Se compone la parte del texto
                // BodyPart texto = new MimeBodyPart();

                HashMap<String, String> listaN = new HashMap<String, String>();
                listaN.put("Bayron Lara", "balava88@gmail.com");
                listaN.put("Bayron Lara V", "bayron.lara@gonetusa.com");
                listaN.put("Jorge Vizcaino", "jorge.vizcaino@gonetusa.com");
                listaN.put("Jorge Vizcaino A", "jork00231@gmail.com");




                String msj = "<p ><font style='font-weight: bold;'>Apreciado(a) Cliente :</font> Jorge Vizcaino </br>"
                        + "<font style='font-weight: bold;'>Usted adeuda al Señor Breyner Yesit Robles Caro  La suma de : $4.400.000</font> </p>"
                        + "<p >Este mail ha sido enviado a jork00231@gmail.com </br>"
                        + "Por favor cancele lo mas pronto posible. </br>"
                        + " Bogot&aacute; - Colombia. </p> ";

                //Se compone el adjunto con PDF

                Address[] addrTo = new Address[1];
                // addrTo[0] = new InternetAddress("elpapis601@hotmail.com");
                addrTo[0] = new InternetAddress("jork00231@gmail.com");
                /* addrTo[1] = new InternetAddress("javiergarciaruiz1989@gmail.com");
                 addrTo[2] = new InternetAddress("javieroviedo.it@hotmail.com");*/

                // Se compone el correo, dando to, from, subject y el
                // contenido.
                MimeMessage message = new MimeMessage(session);
                message.setSubject("Pago de deudas Breyner Robles");
                message.setSentDate(new Date());
                message.setFrom(new InternetAddress("byrobles2009@gmail.com"));
                /* message.addRecipient(
                 Message.RecipientType.TO,
                 new InternetAddress("chuidiang@gmail.com"));*/
                message.setText(msj, "ISO-8859-1", "html");

                message.addRecipients(RecipientType.TO, addrTo);


                // Se envia el correo.
                Transport t = session.getTransport("smtp");
                t.connect("byrobles2009@gmail.com", "breyner1043930247");
                t.sendMessage(message, message.getAllRecipients());
                t.close();

            } catch (MessagingException ex) {
                //Logger.getLogger(PcsCotizacionJSFBean.class.getName()).log(Level.SEVERE, null, ex);
                //mostrarError("Ocurrio un error al enviar correo ", 3);
            }
        }
        return true;

    }

    public static void main(String[] args) throws Exception {
        HtmlEmail email = new HtmlEmail();
        String html = null;
        String plain = null;
        StrSubstitutor strSub = null;

        // Para no usar beans u objetos que compliquen el código de este tutorial vamos a poner los valores   
        // de las propiedades que serán más adelante reemplazadas usando la clase java.util.Properties  
        Properties usuario = new Properties();
        usuario.put("nombre", "Breymer Yesit");
        usuario.put("apellidos", "Robles Caro");
        usuario.put("email", "elpapis601@hotmail.com");


        // Leemos el contenido del correo en formato texto plano y reemplazamos las expresiones por su valor  
        // Usamos la clase StrSubstitutor del paquete "Commons Lang" para reemplazar las expresiones por su valor  
        // http://commons.apache.org/lang/apidocs/index.html?org/apache/commons/lang/text/StrSubstitutor.html         
        //  plain  = JavaMailMoros.fileToString(JavaMailMoros.class.getResourceAsStream("/mail.txt"), "utf-8");  
        // File file = new File("C:\\workspace\\prueba\\mail.txt");
        InputStream input = new FileInputStream("C:\\workspace\\prueba\\mail.txt");
        plain = JavaMailMoros.fileToString(input, "utf-8");
        strSub = new StrSubstitutor(usuario);
        plain = strSub.replace(plain);

        // Leemos el contenido del correo en formato HTML  
          InputStream input2 = new FileInputStream("C:\\workspace\\prueba\\mail.html");
        html = JavaMailMoros.fileToString(input2, "utf-8");

        // Embebemos las imágenes que contendrá el correo (Para simplificar, el nombre de la variable   
        // que posteriormente será reemplazada, es igual al nombre de cada imagen real a embeber).  
        String[] images = {"logo1.png"};
        URL url = null;
        String cid = null;
        File img = new File("C:\\workspace\\prueba\\logo1.png");
       
        for (int i = 0; i < images.length; i++) {
            // Obtenemos la URL de la imagen desde el CLASSPATH  
            //   url = JavaMailMoros.class.getResource("/" + images[i]); 
          //  url = new URL("https://ci5.googleusercontent.com/proxy/SZo_9TMEuZVQfUKD4cJminIzrddmmvB0tMujGlHOW6QNGCc54Tq8CNYpH7KIStH4Ljfshhyyfg2wvNMZkM3JzmCj8orzE2fcxNPSUEJX591J4wnNh60NmRKbK4YQSmfSiZ3zUsCRYmqEveEsXvpSa_kvRIo=s0-d-e1-ft#https://d1yoaun8syyxxt.cloudfront.net/indudatainfusion-3d109232-29b4-41ea-a8e1-100f41584630-v2");
            // url =;
          //  cid = email.embed(url, images[i]);
             cid = email.embed(img, images[i]);
            usuario.put(images[i], cid);
        }
        

        // Ahora vamos a embeber una imagen pública de Internet.   
        cid = email.embed(new URL("https://ci5.googleusercontent.com/proxy/SZo_9TMEuZVQfUKD4cJminIzrddmmvB0tMujGlHOW6QNGCc54Tq8CNYpH7KIStH4Ljfshhyyfg2wvNMZkM3JzmCj8orzE2fcxNPSUEJX591J4wnNh60NmRKbK4YQSmfSiZ3zUsCRYmqEveEsXvpSa_kvRIo=s0-d-e1-ft#https://d1yoaun8syyxxt.cloudfront.net/indudatainfusion-3d109232-29b4-41ea-a8e1-100f41584630-v2"), "pie");
        usuario.put("emailFoot", cid);   // Le damos el nombre "emailFoot"  

        // Usamos la clase StrSubstitutor del paquete "Commons Lang" para reemplazar las expresiones por su valor  
        // http://commons.apache.org/lang/apidocs/index.html?org/apache/commons/lang/text/StrSubstitutor.html  
        strSub = new StrSubstitutor(usuario);
        html = strSub.replace(html);


        // Cargamos la configuración del correo desde un archivo localizable via CLASSPATH  
        Properties mailSetup = new Properties();
         InputStream input3 = new FileInputStream("C:\\workspace\\prueba\\mail.properties");
        mailSetup.load(input3);

        // Establecemos la configuracion del correo electrónico  
        email.setHostName(mailSetup.getProperty("mail.server"));
        email.setCharset("UTF-8");
        email.addTo(usuario.getProperty("email"), usuario.getProperty("nombre"));
        email.setFrom(mailSetup.getProperty("from.email"), mailSetup.getProperty("from.name"));
        email.setSubject(mailSetup.getProperty("mail.subject"));
        email.setHtmlMsg(html);
        email.setTextMsg(plain);
        email.setAuthentication("byrobles2009@gmail.com", "breyner1043930247");
        //email.addPart(null);

        // Enviamos el correo electrónico  
        email.send();
    }

    /**
     * Lee el contenido textual desde un stream de entrada
     *
     * @param input Stream de entrada
     * @param encoding Codificación
     * @return El contenido del stream de entrada
     * @throws IOException Cualquier excepción de Entrada/Salida
     */
    private static String fileToString(InputStream input, String encoding) throws IOException {
        StringWriter sw = new StringWriter();
        InputStreamReader in = new InputStreamReader(input, encoding);

        char[] buffer = new char[1024 * 2];
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
            sw.write(buffer, 0, n);
        }
        return sw.toString();
    }
}
