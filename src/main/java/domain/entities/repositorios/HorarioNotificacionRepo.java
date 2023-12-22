package domain.entities.repositorios;

import domain.entities.notificaciones.HorarioNotificacion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HorarioNotificacionRepo {
    private static HorarioNotificacionRepo instance;
    private List<HorarioNotificacion> horarios;

    private HorarioNotificacionRepo (){this.horarios = new ArrayList<>();}

    public static HorarioNotificacionRepo getInstance() {
        if (instance == null) {
            instance = new HorarioNotificacionRepo();
        }
        return  instance;
    }

    public void agregarHorario(HorarioNotificacion horario){
        this.horarios.add(horario);
    }
}
