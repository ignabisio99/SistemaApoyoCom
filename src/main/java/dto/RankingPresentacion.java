package dto;

import domain.entities.ranking.Puestos.PuestoRanking;
import domain.entities.ranking.Ranking;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Getter
public class RankingPresentacion {
    private Integer codigo;
    private List<PuestoRankingPresentacion> puestosRanking;
    private String tipoRanking;
    private String fecha;

    public RankingPresentacion(Ranking ranking){
        this.puestosRanking=this.ObtenerPuestosRanking(ranking.getPuestosRanking());
        Collections.sort(this.puestosRanking, Comparator.comparingInt(PuestoRankingPresentacion::getPuesto));
        this.tipoRanking= ranking.getTipoRanking().getNombre();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        this.fecha= ranking.getFecha().format(formatter);
        this.codigo=ranking.getRanking_codigo();
    }

    private List<PuestoRankingPresentacion>ObtenerPuestosRanking(List<PuestoRanking> puestos){
        List<PuestoRankingPresentacion> resultado= new ArrayList<>();
        for(PuestoRanking puestoRanking:puestos){
            PuestoRankingPresentacion unPuesto = new PuestoRankingPresentacion(puestoRanking);
            resultado.add(unPuesto);
        }
        return resultado;
    }

}
