package comunicaciones;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import persistencia.Persistencia;

import java.io.File;
import java.util.Properties;

import static jakarta.mail.Transport.send;

public class Email {
    private static final String host = "smtp.gmail.com";
    private static final String user = Persistencia.devuelveKeysComunicaciones("gmail.user");
    ;
    private static final String pass = Persistencia.devuelveKeysComunicaciones("gmail.pass");
    ;

    public static boolean enviarMensaje(String destino, String asunto, String mensaje) {

        // Creamos nuestra variable de propiedades con los datos de nuestro servidor de correo
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
        // Obtenemos la sesión en nuestro servidor de correo
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(user, pass);
            }
        });

        try {
            // Creamos un mensaje de correo por defecto
            Message message = new MimeMessage(session);

            // En el mensaje, establecemos el receptor
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));

            // Establecemos el Asunto
            message.setSubject(asunto);

            // Añadimos el contenido del mensaje
            message.setContent(mensaje, "text/html; charset=utf-8");


            // Intenamos mandar el mensaje
            send(message);

        } catch (Exception e) { //Si entra aquí hemos tenido fallo
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean enviarMensajeWeb(String destino, String asunto, String mensaje, String context) {
        String userWeb = Persistencia.devuelveKeysComunicacionesWeb("gmail.user", context);

        String passWeb = Persistencia.devuelveKeysComunicacionesWeb("gmail.pass", context);


        // Creamos nuestra variable de propiedades con los datos de nuestro servidor de correo
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
        // Obtenemos la sesión en nuestro servidor de correo
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(userWeb, passWeb);
            }
        });

        try {
            // Creamos un mensaje de correo por defecto
            Message message = new MimeMessage(session);

            // En el mensaje, establecemos el receptor
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));

            // Establecemos el Asunto
            message.setSubject(asunto);

            // Añadimos el contenido del mensaje
            message.setContent(mensaje, "text/html; charset=utf-8");


            // Intenamos mandar el mensaje
            send(message);

        } catch (Exception e) { //Si entra aquí hemos tenido fallo
            throw new RuntimeException(e);
        }
        return true;
    }

    public static void enviarMensajePdfAdjunto(String destinatario, String asunto,
                                               String mensaje) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            BodyPart texto = new MimeBodyPart();
            texto.setContent(mensaje, "text/html; charset=utf-8");
            BodyPart pdf = new MimeBodyPart();
            pdf.setDataHandler(new DataHandler(new FileDataSource(Persistencia.resumenReserva())));
            MimeMultipart mimu = new MimeMultipart();
            pdf.setFileName("resumenReserva.pdf");
            mimu.addBodyPart(texto);
            mimu.addBodyPart(pdf);
            message.setContent(mimu);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", user, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }

    public static void enviarMensajePdfAdjuntoWeb(String destinatario, String asunto,
                                                  String mensaje, String context) {
        String userWeb = Persistencia.devuelveKeysComunicacionesWeb("gmail.user", context);

        String passWeb = Persistencia.devuelveKeysComunicacionesWeb("gmail.pass", context);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(userWeb));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            BodyPart texto = new MimeBodyPart();
            texto.setContent(mensaje, "text/html; charset=utf-8");
            BodyPart pdf = new MimeBodyPart();
            String rutaPDF = context.replace("\\target\\fernanbnb-1.0-SNAPSHOT\\", "") + File.separator
                    +Persistencia.resumenReservaWeb(context).replace("/","\\");

            pdf.setDataHandler(new DataHandler(new FileDataSource(rutaPDF)));
            MimeMultipart mimu = new MimeMultipart();
            pdf.setFileName("resumenReserva.pdf");
            mimu.addBodyPart(texto);
            mimu.addBodyPart(pdf);
            message.setContent(mimu);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", userWeb, passWeb);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }

    public static void enviarMensajeExcelAdjuntoListadoReservas(String destinatario, String asunto,
                                                                String mensaje) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            BodyPart texto = new MimeBodyPart();
            texto.setContent(mensaje, "text/html; charset=utf-8");
            BodyPart csv = new MimeBodyPart();
            csv.setDataHandler(new DataHandler(new FileDataSource(Persistencia.listadoReservas())));
            MimeMultipart mimu = new MimeMultipart();
            csv.setFileName("listadoReservas.csv");
            mimu.addBodyPart(texto);
            mimu.addBodyPart(csv);
            message.setContent(mimu);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", user, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }
    public static void enviarMensajeExcelAdjuntoListadoReservasWeb(String destinatario, String asunto,
                                                                String mensaje,String context) {

        String userWeb = Persistencia.devuelveKeysComunicacionesWeb("gmail.user", context);

        String passWeb = Persistencia.devuelveKeysComunicacionesWeb("gmail.pass", context);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(userWeb));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            BodyPart texto = new MimeBodyPart();
            texto.setContent(mensaje, "text/html; charset=utf-8");
            BodyPart csv = new MimeBodyPart();
            csv.setDataHandler(new DataHandler(new FileDataSource(Persistencia.listadoReservasWeb(context))));
            MimeMultipart mimu = new MimeMultipart();
            csv.setFileName("listadoReservas.csv");
            mimu.addBodyPart(texto);
            mimu.addBodyPart(csv);
            message.setContent(mimu);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", userWeb, passWeb);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }

    public static void enviarMensajeExcelAdjuntoListadoViviendas(String destinatario, String asunto,
                                                                 String mensaje) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            BodyPart texto = new MimeBodyPart();
            texto.setContent(mensaje, "text/html; charset=utf-8");
            BodyPart csv = new MimeBodyPart();
            csv.setDataHandler(new DataHandler(new FileDataSource(Persistencia.listadoViviendas())));
            MimeMultipart mimu = new MimeMultipart();
            csv.setFileName("listadoViviendas.csv");
            mimu.addBodyPart(texto);
            mimu.addBodyPart(csv);
            message.setContent(mimu);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", user, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }
}
