package scheduler;

import domain.entities.ranking.FormasRankings.MayorCantidadIncidentes;
import domain.entities.ranking.FormasRankings.MayorTiempoPromedio;
import domain.entities.ranking.GeneradorDeRankings;
import domain.entities.repositorios.IncidentesRepo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

public class JobGenerarRanking implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        GeneradorDeRankings rankings = new GeneradorDeRankings();
        rankings.agregarFormaRanking(new MayorCantidadIncidentes());
        rankings.agregarFormaRanking(new MayorTiempoPromedio());
        IncidentesRepo.getInstance().buscarIncidentes();
        rankings.setFechaRealizacion(LocalDateTime.now());
        rankings.generarRanking();
    }
}
