package scheduler;

import domain.entities.notificaciones.HorarioNotificacion;
import lombok.Getter;
import org.quartz.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GeneradorSchedulerNotificacion {
    private List<Integer> minutosNotificacion;
    private  List<Integer> horasNotificacion;
    public GeneradorSchedulerNotificacion(){
        this.minutosNotificacion=new ArrayList<>();
        this.horasNotificacion=new ArrayList<>();
       // HorarioNotificacionRepo.getInstance().getHorarios().forEach(this::obtenerHorarios);
    }

    public void obtenerHorario(HorarioNotificacion horario){
        obtenerMinuto(horario);
        obtenerHora(horario);
    }
    public void obtenerMinuto(HorarioNotificacion horario){

        if(minutosNotificacion.contains(horario.getHorario().getMinute())){
            return;
        }
        minutosNotificacion.add(horario.getHorario().getMinute());
    }
    public void obtenerHora(HorarioNotificacion horario){
        if(horasNotificacion.contains(horario.getHorario().getHour())){
            return;
        }
        horasNotificacion.add(horario.getHorario().getHour());
    }
    public String armarCron(){
        String minutos = minutosNotificacion.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        String horas = horasNotificacion.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        return "0 " + minutos +" "+ horas+" 1/1 * ? *";
    }

    public void comenzar() throws SchedulerException {
        // Creacion del scheduler
        SchedulerFactory schedFactory = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedFactory.getScheduler();

        //Creacion del Trabajo a realizar
        JobBuilder jobBuilder = JobBuilder.newJob(JobNotificar.class);
        JobDetail jobDetail = jobBuilder.withIdentity("JobNotificar").build();

        //Creacion del triger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("TemporizadorNotificaciones")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(armarCron()))
                .build();

        //Asigno el trabajo y el trigger al schedueler
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();

    }
}
