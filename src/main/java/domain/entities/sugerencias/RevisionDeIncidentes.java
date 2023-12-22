package domain.entities.sugerencias;


import domain.entities.actores.miembros.Miembro;

import domain.entities.incidentes.Incidente;
import domain.entities.incidentes.IncidenteMiembro;
import domain.entities.repositorios.EstablecimientosRepo;
import domain.entities.repositorios.IncidentesRepo;
import domain.entities.servicios.Establecimiento;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RevisionDeIncidentes {
    private static RevisionDeIncidentes instance;

    private RevisionDeIncidentes() {

    }
    public static RevisionDeIncidentes getInstance() {
        if(instance==null){
            instance=new RevisionDeIncidentes();
        }
        return instance;
    }
    //todo: usar incidentes y no incidentemiembro maybe
    public List<Incidente> obtenerIncidentes(Miembro miembro){
        List<Establecimiento> establecimientos = estaCercaDe(miembro);
        List<Incidente>  incidentes = IncidentesRepo.getInstance().obtenerIncidentesParaMiembro(miembro);
        if (establecimientos != null && !establecimientos.isEmpty()) {
            // Filtrar los incidentes que tienen un establecimiento cuyo código está en la lista de establecimientos
            incidentes = incidentes.stream()
                    .filter(incidente -> establecimientos.stream()
                            .anyMatch(establecimiento -> establecimiento.getEstablecimiento_codigo() == incidente.getEstablecimiento().getEstablecimiento_codigo()))
                    .collect(Collectors.toList());
        }
        incidentes= incidentes.stream().filter(i-> !i.getResuelto()).collect(Collectors.toList());
        return incidentes;
    }

    private List<Establecimiento> estaCercaDe(Miembro miembro){
        //esto ponele que encuentra los establecimientos
        return EstablecimientosRepo.getInstance().buscarEstablecimientos();
    }

}
