package com.itsoft.ab.jms;

import com.sun.mail.smtp.SMTPTransport;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 22.01.14
 * Time: 13:32
 */
@MessageDriven(
        //имя topic, на который подписан бин
        mappedName="jms/MailTopic",
        name = "MailMDB")
public class MailMessageListener implements MessageListener {

    //@Resource(name = "mailProps")
    //private Properties mailProps;
    private static final String username = "noreply.musicalwave";
    private static final String password = "f572YB4wdq6C486";

    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            try {
                String toMail = ((MapMessage) message).getString("toMail");
                String title = ((MapMessage) message).getString("title");
                String text = ((MapMessage) message).getString("text");

                this.send(toMail, title, text);

            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            } catch (AddressException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (MessagingException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }

    private void send(String recipientEmail, String title, String message) throws AddressException, MessagingException {
        send(recipientEmail, "", title, message);
    }

    private void send(String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
        //final String username = mailProps.getProperty("username");
        //final String password = mailProps.getProperty("password");

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        /*
        If set to false, the QUIT command is sent and the connection is immediately closed. If set
        to true (the default), causes the transport to wait for the response to the QUIT command.

        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                http://forum.java.sun.com/thread.jspa?threadID=5205249
                smtpsend.java - demo program from javamail
        */
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(username + "@gmail.com"));
        msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(javax.mail.Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        msg.setText(message, "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", username, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }

}
