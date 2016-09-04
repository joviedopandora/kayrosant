/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author luis
 */
public class ReceptorMensajes implements MessageListener {
    private List<EvtInformeListener> _listeners = new ArrayList();
    public synchronized void addEventListener(EvtInformeListener listener) {
        _listeners.add(listener);
    }

    public synchronized void removeEventListener(EvtInformeListener listener) {
        _listeners.remove(listener);
    }

    // call this method whenever you want to notify
    //the event listeners of the particular event
    private synchronized void fireEvent() {
//        EvtInforme event = new EvtInforme(this);
//        Iterator<EvtInformeListener> i = _listeners.iterator();
//        while (i.hasNext()) {            
//            i.next().handleSolInformeClassEvent(event);
//        }
    }
    private ByteArrayInputStream arrayInputStream = null;
    
    public ReceptorMensajes() {
//        try {
//            Context c = new InitialContext();
//            ConnectionFactory cf = (ConnectionFactory) c.lookup("java:comp/env/jms/InfAdmProcesoFactory");
//            Topic queue = (Topic) c.lookup("jms/topicoInforme");
//            Connection connection = cf.createConnection();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);;
//            MessageConsumer consumer = session.createConsumer(queue);
//            consumer.setMessageListener(this);
//            connection.start();
//        } catch (NamingException ex) {
//            Logger.getLogger(ReceptorMensajes.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JMSException ex) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void onMessage(Message message) {
//        if (message instanceof BytesMessage) {
//            try {
//                BytesMessage bm = (BytesMessage) message;
//                byte[] bs = new byte[(int) bm.getBodyLength()];
//                bm.readBytes(bs);
//                setArrayInputStream(new ByteArrayInputStream(bs));
//                //iajsfb.setContent(new DefaultStreamedContent(arrayInputStream, "application/pdf", "Informe"));
//                fireEvent();
//            } catch (JMSException ex) {
//                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    /**
     * @return the arrayInputStream
     */
    public ByteArrayInputStream getArrayInputStream() {
        return arrayInputStream;
    }

    /**
     * @param arrayInputStream the arrayInputStream to set
     */
    public void setArrayInputStream(ByteArrayInputStream arrayInputStream) {
        this.arrayInputStream = arrayInputStream;
    }
}
