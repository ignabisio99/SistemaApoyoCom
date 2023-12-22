package scheduler;

import domain.entities.notificaciones.NotificadorIncidentes;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class JobNotificar implements Job {
    @Override
    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        NotificadorIncidentes notificadorIncidentes = new NotificadorIncidentes();
        System.out.println("Se envio mensaje");
        notificadorIncidentes.obtenerNotificacionesPendientes();
        if(notificadorIncidentes.getNotifcacionesPendientes().isEmpty()){throw new JobExecutionException("JobExecutionException!");}
        notificadorIncidentes.notificar();
    }
}
