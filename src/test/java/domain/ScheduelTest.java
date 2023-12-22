package domain;

import domain.entities.actores.Comunidad;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.incidentes.Incidente;
import domain.entities.incidentes.IncidenteMiembro;
import domain.entities.notificaciones.HorarioNotificacion;
import domain.entities.notificaciones.MedioNotificacion;
import domain.entities.notificaciones.Notificacion;
import domain.entities.servicios.Establecimiento;
import domain.entities.servicios.Servicio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import scheduler.GeneradorSchedulerNotificacion;
import utils.BDUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

public class ScheduelTest {
     GeneradorSchedulerNotificacion generador;
     Incidente incidente;
     MiembroPorComunidad miembroPorComunidad1;
     MiembroPorComunidad miembroPorComunidad2;
     Comunidad comunidad;
     Miembro miembro1;
     Miembro miembro2;
     Servicio servicio;
     LocalDateTime fecha = LocalDateTime.of(2023, 9, 27, 15, 00);
     Establecimiento establecimiento;

    @Before
    public void init(){
        generador = new GeneradorSchedulerNotificacion();
        miembro1= new Miembro("Ignacio","Bisio", "ignacio.bisio8780@gmail.com", "+5491131772043");
        miembro2 = new Miembro("Juan","Caceres", "jucaceres@frba.utn.edu.ar", "+5491131772043");
        comunidad=new Comunidad();
        miembroPorComunidad1= new MiembroPorComunidad(miembro1,comunidad);
        miembroPorComunidad2=new MiembroPorComunidad(miembro2,comunidad);

        MedioNotificacion email= new MedioNotificacion("email");
        miembro1.setMedioNotificacion(email);
        miembro2.setMedioNotificacion(email);

        incidente = new IncidenteMiembro("Los baños de la linea A no funcionan",
                servicio,fecha,establecimiento,miembroPorComunidad1);

        Notificacion notificacion= new Notificacion(incidente);

        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(miembro1);
        em.persist(miembro2);
        em.persist(comunidad);
        em.persist(miembroPorComunidad1);
        em.persist(miembroPorComunidad2);
        em.persist(incidente);
        em.persist(notificacion);
        BDUtils.commit(em);
    }
    @Test
    public void testGeneradorSchedulerNotificacion() throws Exception {
        // Configurar un horario de notificación en 1 minuto
        HorarioNotificacion horario = new HorarioNotificacion(LocalDateTime.now().plusMinutes(1));
        generador.obtenerHorario(horario);

        // Iniciar el generador y el scheduler simulado
        generador.comenzar();

        // Esperar un tiempo suficiente para que el trabajo se ejecute (aquí estamos esperando 2 minutos)
        Thread.sleep(120000); // 2 minutos en milisegundos

    }
    @Test
    public void NotificacionConScheduler() throws InterruptedException {
        HorarioNotificacion horario = new HorarioNotificacion(LocalDateTime.now().plusMinutes(1));
        generador.obtenerHorario(horario);
        try {
            generador.comenzar();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        Thread.sleep(120000);
    }
}

