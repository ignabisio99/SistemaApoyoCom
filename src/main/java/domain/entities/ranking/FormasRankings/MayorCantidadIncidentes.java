package domain.entities.ranking.FormasRankings;

import domain.entities.incidentes.Incidente;
import domain.entities.ranking.Puestos.PuestoRanking;
import domain.entities.ranking.Puestos.PuestosRankingEntidad;
import domain.entities.ranking.Ranking;
import domain.entities.ranking.TipoRanking;
import domain.entities.repositorios.IncidentesRepo;
import domain.entities.repositorios.RankingsRepo;
import domain.entities.servicios.Entidad;
import domain.entities.servicios.Servicio;

import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class MayorCantidadIncidentes extends FormaRanking{
    @Override
    public void generar(LocalDateTime fecha) {
        List<Incidente> listaIncidentes = IncidentesRepo.getInstance().buscarIncidentesSemana(fecha);
        int horasRango = 24;

        // Agrupar los incidentes por entidad y servicio asociado
        Map<Entidad, Map<Servicio, List<Incidente>>> incidentesPorEntidadYServicio = listaIncidentes.stream()
                .filter(incidente -> incidente.getResuelto().equals(false)) // Filtrar solo los incidentes abiertos
                .collect(Collectors.groupingBy(incidente -> incidente.getEstablecimiento().getEntidad(),
                        Collectors.groupingBy(Incidente::getServicio)));

        // Crear una lista de PuestoRanking para mantener el orden y el motivo del ranking
        List<PuestoRanking> puestosRanking = new ArrayList<>();

        // Obtener la cantidad de incidentes por entidad, sin contar los servicios duplicados en un rango de 24 horas
        for (Map.Entry<Entidad, Map<Servicio, List<Incidente>>> entidadEntry : incidentesPorEntidadYServicio.entrySet()) {
            int incidentesContados = 0;
            for (List<Incidente> incidentes : entidadEntry.getValue().values()) {
                if (!incidentes.isEmpty()) {
                    incidentesContados++;
                    Incidente ultimoIncidente = incidentes.get(incidentes.size() - 1);
                    for (int i = incidentes.size() - 2; i >= 0; i--) {
                        if (ultimoIncidente.esRepetidoEnRango(incidentes.get(i), horasRango)) {
                            break;
                        }
                        incidentesContados++;
                        ultimoIncidente = incidentes.get(i);
                    }
                }
            }

            // Agregar un nuevo PuestoRanking con el motivo (cantidad de incidentes) y la entidad correspondiente
            puestosRanking.add(new PuestosRankingEntidad(0, entidadEntry.getKey(), incidentesContados));
        }

        // Ordenar la lista de PuestoRanking por motivo (cantidad de incidentes)
        puestosRanking.sort(Comparator.comparingDouble(PuestoRanking::getPuntaje).reversed());

        // Asignar los puestos en el ranking en función de la posición en la lista
        for (int i = 0; i < puestosRanking.size(); i++) {
            puestosRanking.get(i).setPuesto(i + 1);
        }


        Ranking ranking = new Ranking(puestosRanking,new TipoRanking("Mayor Cantidad de Incidentes"), fecha);
        ranking.getPuestosRanking().forEach(x->x.setRanking(ranking));
        RankingsRepo.getInstance().agregarRanking(ranking);
    }

}
