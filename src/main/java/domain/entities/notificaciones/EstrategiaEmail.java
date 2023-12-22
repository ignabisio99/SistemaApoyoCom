package domain.entities.notificaciones;

import domain.entities.actores.miembros.Miembro;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EstrategiaEmail extends EstrategiaNotificar{

    Properties props;
    Session session;
    MimeMessage message;

    String username = "ignacio.bisio8780@gmail.com";
    String password = "fvztifumthqaujlm"; // TODO: Cargar con un .properties

    public EstrategiaEmail() {
    }

    public void notificar(Notificacion notificacion, Miembro miembro){

        props = new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
       session = Session.getInstance(props, new Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication(){
               return new PasswordAuthentication(username,
                                                password);
           }
       });

        try {

            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(miembro.getEmail()));
            message.setSubject("Nuevo incidente reportado");
            message.setText(notificacion.getIncidente().getDescripcion());


            Transport mTransport = session.getTransport("smtp");
            mTransport.connect(username,password);
            mTransport.send((message));
            mTransport.close();
            System.out.println("Email enviado correctamente!");

        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
