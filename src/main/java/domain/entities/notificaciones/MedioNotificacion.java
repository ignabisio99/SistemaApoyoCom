package domain.entities.notificaciones;

import domain.entities.actores.miembros.Miembro;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class MedioNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medio_notificacion_codigo;

    @Column
    private String medioNotificacion;

    @Transient
    private EstrategiaNotificar estrategiaNotificar;

    public MedioNotificacion() {

    }

    public void notificar(Notificacion notificacion, Miembro miembro) {
        if(medioNotificacion.equals("email")){
            estrategiaNotificar= new EstrategiaEmail();
        }
        if(medioNotificacion.equals("whatsapp")) {
            estrategiaNotificar = new EstrategiaWhatsapp();
        }

        estrategiaNotificar.notificar(notificacion,miembro);
    }

    public MedioNotificacion(String medioNotificacion) {
        this.medioNotificacion = medioNotificacion;
    }
}
