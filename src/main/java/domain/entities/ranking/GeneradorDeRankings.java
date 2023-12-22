package domain.entities.ranking;

import domain.entities.ranking.FormasRankings.FormaRanking;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class GeneradorDeRankings {

    private List<FormaRanking> formasRanking;
    private LocalDateTime fechaRealizacion;

    public GeneradorDeRankings(){
        this.formasRanking= new ArrayList<>();

    }
    public void generarRanking(){
        for (FormaRanking formaRanking : formasRanking) {
            formaRanking.generar(this.fechaRealizacion);
        }
    }
    public void agregarFormaRanking(FormaRanking formaRanking){
        this.formasRanking.add(formaRanking);
    }
}
