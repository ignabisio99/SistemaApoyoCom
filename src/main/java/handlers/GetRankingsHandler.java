package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.entities.ranking.Ranking;
import domain.entities.repositorios.RankingsRepo;
import dto.RankingPresentacion;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GetRankingsHandler implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
        List<Ranking> rankings = RankingsRepo.getInstance().buscarRankings(); // Método para obtener los datos
        List<RankingPresentacion> rankingsPresentacion= new ArrayList<>();
        for(Ranking ranking :rankings){
            RankingPresentacion unRanking = new RankingPresentacion(ranking);
            rankingsPresentacion.add(unRanking);
        }
        context.json(rankingsPresentacion);
    }
}
