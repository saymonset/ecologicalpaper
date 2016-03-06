package com.ecological.mailBackend;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestMail {
	public static void main(String[] args) {
        try {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "mail.ecologicalpaper.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "ecolo4");
            props.setProperty("mail.smtp.auth", "true");

            
            
//          props.setProperty("mail.smtp.host", "smtp.gmail.com");
//          props.setProperty("mail.smtp.port", "465");
//          props.setProperty("mail.smtp.user", "oraclefedora@gmail.com");
//          props.setProperty("mail.smtp.auth", "true");
          
//          
//          Here are the settings:
//          	POP server: pop.gmail.com, Port: 995 (SSL)
//          	SMTP: smtp.gmail.com, Port: 465 (SSL)
//          	User name: your Gmail email address.

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props,null);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("yo@yo.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    "saymon_set@hotmail.com"));
            message.setSubject("Hola");
            message.setText("Mensajito con Java Mail" + "de los buenos."
                    + "poque si");

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("ecolo4", "Oirad111");
          //  t.connect("oraclefedora@gmail.com", "12760187lo");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
