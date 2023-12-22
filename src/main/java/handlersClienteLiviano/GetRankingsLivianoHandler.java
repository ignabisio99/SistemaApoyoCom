package handlersClienteLiviano;

import java.util.*;
import java.util.stream.Collectors;

import domain.entities.ranking.Ranking;
import domain.entities.repositorios.RankingsRepo;
import dto.RankingPresentacion;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

public class GetRankingsLivianoHandler implements Handler {
    private RankingsRepo repo;

    public GetRankingsLivianoHandler() {
        super();
        this.repo = RankingsRepo.getInstance();
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = new HashMap<>();
        List<Ranking> rankings = repo.buscarRankings();
        List<RankingPresentacion> rankingsPresentacion = new ArrayList<>();
        for(Ranking ranking: rankings){
            RankingPresentacion unRanking = new RankingPresentacion(ranking);
            rankingsPresentacion.add(unRanking);
        }
        List<RankingPresentacion> cantidadDeIncidentes = rankingsPresentacion.stream().filter(r-> Objects.equals(r.getTipoRanking(), "Mayor Cantidad de Incidentes")).collect(Collectors.toList());
        List<RankingPresentacion> tiempoPromedio = rankingsPresentacion.stream().filter(r-> Objects.equals(r.getTipoRanking(), "Mayor Tiempo Promedio")).collect(Collectors.toList());


        model.put("cantidadDeIncidentes", cantidadDeIncidentes);
        model.put("tiempoPromedio", tiempoPromedio);
        model.put("rankings", rankingsPresentacion);
        ctx.render("VistasRanking.hbs", model);

    }

}
