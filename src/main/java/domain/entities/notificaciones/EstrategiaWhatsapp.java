package domain.entities.notificaciones;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import domain.entities.actores.miembros.Miembro;
import domain.entities.notificaciones.Notificacion;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

public class EstrategiaWhatsapp extends EstrategiaNotificar {

    String rutaProperties = "src/resources/tokenwpp.properties";

    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC7ab011e93d3f166ac58005ec281f0999";
    public static final String AUTH_TOKEN = ""; //TODO: Como se usan los .properties

    public EstrategiaWhatsapp() {
    }

    public void notificar(Notificacion notificacion, Miembro miembro) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:".concat(miembro.getTelefono())), // Receptor
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),   // Mensajero
                notificacion.getIncidente().getDescripcion())          // Mensaje
                .create();
        System.out.println(message.getSid());
    }
}