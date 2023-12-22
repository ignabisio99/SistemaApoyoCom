package dto;

import domain.entities.ranking.Puestos.PuestoRanking;
import lombok.Getter;

@Getter
public class PuestoRankingPresentacion {
    private int puesto;
    private double puntaje;
    private  String ocupadoPor;

   public PuestoRankingPresentacion(PuestoRanking puestoRanking){
       this.puesto=puestoRanking.getPuesto();
       this.puntaje=puestoRanking.getPuntaje();
       this.ocupadoPor= puestoRanking.ocupadoPor();
   }
}
