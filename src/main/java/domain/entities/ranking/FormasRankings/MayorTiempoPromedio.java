package domain.entities.ranking.FormasRankings;

import domain.entities.incidentes.Incidente;
import domain.entities.ranking.Puestos.PuestoRanking;
import domain.entities.ranking.Puestos.PuestosRankingEntidad;
import domain.entities.ranking.Ranking;
import domain.entities.ranking.TipoRanking;
import domain.entities.repositorios.IncidentesRepo;
import domain.entities.repositorios.RankingsRepo;
import domain.entities.servicios.Entidad;

import java.util.ArrayList;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MayorTiempoPromedio extends FormaRanking {
    @Override
    public void generar(LocalDateTime fecha) {
        List<Incidente> listaIncidentes = IncidentesRepo.getInstance().buscarIncidentesResueltosSemana(fecha);

        Map<Entidad, List<Incidente>> incidentesPorEntidad = listaIncidentes.stream()
                .collect(Collectors.groupingBy(incidente -> incidente.getEstablecimiento().getEntidad()));

        Map<Entidad, Double> tiempoPromedioPorEntidad = incidentesPorEntidad.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .mapToDouble(incidente -> Duration.between(incidente.getFechaRealizacion(), incidente.getFechaCierre()).toMinutes())
                                .average()
                                .orElse(0)
                ));

        // Crear una lista de PuestoRanking para mantener el orden y el motivo del ranking
        List<PuestoRanking> puestosRanking = new ArrayList<>();

        // Ordenar las entidades según el tiempo de cierre promedio y crear PuestoRanking
        tiempoPromedioPorEntidad.entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .forEachOrdered(entry -> {
                    Entidad entidad = entry.getKey();
                    Double tiempoPromedio = entry.getValue();
                    // Agregar un nuevo PuestoRanking con el motivo (tiempo promedio) y la entidad correspondiente
                    puestosRanking.add(new PuestosRankingEntidad(0, entidad, tiempoPromedio));
                });

        // Asignar los puestos en el ranking en función de la posición en la lista
        for (int i = 0; i < puestosRanking.size(); i++) {
            puestosRanking.get(i).setPuesto(puestosRanking.size() - i);
        }

        Ranking ranking = new Ranking(puestosRanking, new TipoRanking("Mayor Tiempo Promedio"), fecha);
        ranking.getPuestosRanking().forEach(x->x.setRanking(ranking));
        RankingsRepo.getInstance().agregarRanking(ranking);
    }
}






