package domain.entities.comparadores;

import domain.entities.ranking.Puestos.PuestoRanking;

import java.util.Comparator;

public class ComparaPuestoRanking implements Comparator<PuestoRanking> {
    @Override public int compare(PuestoRanking a, PuestoRanking b){
        return a.getPuesto()-b.getPuesto();
    }
}
