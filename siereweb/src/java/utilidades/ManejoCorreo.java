/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;


import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPAddressSucceededException;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 * Clase que encapsula la función de enviar correo
 * @author extmchlfchacon
 */
public class ManejoCorreo {
// <editor-fold defaultstate="collapsed" desc="Variables y constantes">
    /**
     *Destinatario
     */
    private String to;
    /**
     * Remitente
     */
    private String from;
    /**
     * Asunto
     */
    private String subject;
    /**
     * Mensaje
     */
    private String message;
    /**
     * Servidor de correo
     */
    private String mailhost;
    /**
     * Usuario
     */
    private String user;
    /**
     * Clave
     */
    private String password;

    private /**
            ¿* El servidor necesita autenticación?
             */
            boolean auth = true;
    /**
     * ¿Usar SSL?
     */
    private boolean ssl = false;

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Funciones de clase">
    /**
     * Función para enviar mensaje de correo una vez configurados los
     * parámetros
     */
    public void enviarMensaje() {


        Properties props = System.getProperties();

        if (getMailhost() != null) {
            props.put("mail.smtp.host", getMailhost());
        }
        if (isAuth()) {
            props.put("mail.smtp.auth", "true");
        }
props.put("mail.smtp.port", "25");
       
        
        // Get a Session object
        javax.mail.Session session = javax.mail.Session.getInstance(props, null);

        // Construct the message
        javax.mail.Message msg = new MimeMessage(session);

        try {
            // Set message details
            msg.setFrom(new InternetAddress(getFrom()));
            msg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(getTo()));
            msg.setSubject(getSubject());
            msg.setSentDate(new Date());
            msg.setText(getMessage());

            // Send the thing off
            SMTPTransport t = (SMTPTransport) session.getTransport(isSsl() ? "smtps" : "smtp");
            try {
                if (isAuth()) {
                    t.connect(getMailhost(), getUser(), getPassword());
                } else {
                    t.connect();
                }
                t.sendMessage(msg, msg.getAllRecipients());
            } finally {
                t.close();
            }
            Logger.getLogger(ManejoCorreo.class.getName()).log(Level.INFO, null, "Correo enviado correctamente.");

        } catch (Exception e) {
            if (e instanceof SendFailedException) {
                MessagingException sfe = (MessagingException) e;
                if (sfe instanceof SMTPSendFailedException) {
                    SMTPSendFailedException ssfe = (SMTPSendFailedException) sfe;
                    Logger.getLogger(ManejoCorreo.class.getName()).log(Level.SEVERE, null, "Falló smtp");

                }
                Exception ne;
                while ((ne = sfe.getNextException()) != null && ne instanceof MessagingException) {
                    sfe = (MessagingException) ne;
                    if (sfe instanceof SMTPAddressFailedException) {
                        SMTPAddressFailedException ssfe = (SMTPAddressFailedException) sfe;
                        StringBuilder logmensaje = new StringBuilder();
                        logmensaje.append("Error con la direccion: ");
                        logmensaje.append(ssfe.toString());
                        logmensaje.append("\n Direccion: ");
                        logmensaje.append(ssfe.getAddress());
                        logmensaje.append("\n Comando: ");
                        logmensaje.append(ssfe.getCommand());
                        logmensaje.append("\n Código de retorno: ");
                        logmensaje.append(ssfe.getReturnCode());
                        logmensaje.append("\n Respuesta: ");
                        logmensaje.append(ssfe.getMessage());
                        Logger.getLogger(ManejoCorreo.class.getName()).log(Level.SEVERE, null, logmensaje.toString());



                    } else if (sfe instanceof SMTPAddressSucceededException) {
                        Logger.getLogger(ManejoCorreo.class.getName()).log(Level.INFO, null, "Dirección conseguida");

                        SMTPAddressSucceededException ssfe = (SMTPAddressSucceededException) sfe;
                    }
                }
            } else {
                Logger.getLogger(ManejoCorreo.class.getName()).log(Level.INFO, null, e);

            }
        }
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Propiedades">

    /**
     * Destinatario
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * Destinatario
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Remitente
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * Remitente
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Asunto
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Asunto
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Mensaje
     * @return the message
     */
    public /**
             * Mensaje
             */
            String getMessage() {
        return message;
    }

    /**
     * Mensaje
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Servidor de correo
     * @return the mailhost
     */
    public String getMailhost() {
        return mailhost;
    }

    /**
     * Servidor de correo
     * @param mailhost the mailhost to set
     */
    public void setMailhost(String mailhost) {
        this.mailhost = mailhost;
    }

    /**
     * Usuario
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Usuario
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Clave
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Clave
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the auth
     */
    public /**
            ¿* El servidor necesita autenticación?
             */
            boolean isAuth() {
        return auth;
    }

    /**
     * @param auth the auth to set
     */
    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    /**
     * ¿Usar SSL?
     * @return the ssl
     */
    public boolean isSsl() {
        return ssl;
    }

    /**
     * ¿Usar SSL?
     * @param ssl the ssl to set
     */
    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

   

  

  
// </editor-fold>
}
