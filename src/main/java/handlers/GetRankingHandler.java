package handlers;

import domain.entities.ranking.Ranking;
import domain.entities.repositorios.RankingsRepo;
import dto.RankingPresentacion;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class GetRankingHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Integer idBuscado = context.pathParamAsClass("id", Integer.class).get();
        List<Ranking> rankings = RankingsRepo.getInstance().buscarRankings();

        Optional<Ranking> ranking= rankings.stream().filter(r->r.getRanking_codigo()==idBuscado).findFirst();
        if (ranking.isPresent()) {
            context.status(200).json(new RankingPresentacion(ranking.get()));
        } else {
            context.status(404);
        }
    }
}

