package domain.entities.repositorios;

import domain.entities.ranking.Ranking;
import lombok.Getter;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RankingsRepo {
    private static RankingsRepo instance;
    private List<Ranking> rankings;


    private RankingsRepo() {
        this.rankings = new ArrayList<>();
    }

    public static RankingsRepo getInstance () {
        if (instance == null) {
            instance = new RankingsRepo();
        }
        return instance;
    }

    public void agregarRanking(Ranking ranking){
        this.rankings.add(ranking);

        //que lo persista
        persistirRanking(ranking);
    }

    public void persistirRanking(Ranking ranking){
        EntityManager em = utils.BDUtils.getEntityManager();
        utils.BDUtils.comenzarTransaccion(em);

        em.persist(ranking);

        utils.BDUtils.commit(em);
    }

    public List<Ranking> buscarRankings(){
        EntityManager em = utils.BDUtils.getEntityManager();
        return em.createQuery("select r from Ranking r", Ranking.class).
                getResultList();
    }
    public Ranking obtenerRanking(int posicion){
        return this.rankings.get(posicion);
    }
}
