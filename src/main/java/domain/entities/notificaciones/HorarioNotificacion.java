package domain.entities.notificaciones;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Entity
public class HorarioNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int horario_codigo;
    @Column
    private LocalDateTime horario;

    public HorarioNotificacion(){
    }
    public HorarioNotificacion(LocalDateTime horario){
        this.horario=horario;
    }
}
