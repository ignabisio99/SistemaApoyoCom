package domain;

import domain.entities.notificaciones.HorarioNotificacion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import scheduler.GeneradorSchedulerNotificacion;

import java.time.LocalDateTime;

public class CronoTest {
    GeneradorSchedulerNotificacion generador;

    @Before
    public void init(){
        this.generador = new GeneradorSchedulerNotificacion();
        HorarioNotificacion h1 = new HorarioNotificacion(LocalDateTime.of(2023, 8, 21, 12, 0));
        HorarioNotificacion h2= new HorarioNotificacion(LocalDateTime.of(2023, 8, 21, 14, 15));
        HorarioNotificacion h3= new HorarioNotificacion(LocalDateTime.of(2023, 8, 21, 15, 30));

        generador.obtenerMinuto(h1);
        generador.obtenerMinuto(h2);
        generador.obtenerMinuto(h2);//Lo pongo duplicado pero no deberia aparecer duplicado
        generador.obtenerMinuto(h3);

        generador.obtenerHora(h1);
        generador.obtenerHora(h2);
        generador.obtenerHora(h3);
    }
    @Test
    public void armadoDeCron(){

        String cron =generador.armarCron();

        Assert.assertEquals("0 0,15,30 12,14,15 1/1 * ? *",cron);

    }
}

