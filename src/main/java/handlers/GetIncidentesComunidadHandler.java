package handlers;

import domain.entities.actores.Comunidad;
import domain.entities.incidentes.Incidente;
import domain.entities.incidentes.IncidenteMiembro;
import domain.entities.repositorios.ComunidadesRepo;
import domain.entities.repositorios.IncidentesRepo;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import dto.IncidentePresentacion;

import java.util.ArrayList;
import java.util.List;

public class GetIncidentesComunidadHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Integer idBuscado = context.pathParamAsClass("id", Integer.class).get();
        Comunidad comunidad = ComunidadesRepo.getInstance().buscarComunidadPorId(idBuscado);
        List<Incidente> incidentesMiembro = comunidad.getIncidentes();
        List<IncidentePresentacion> incidentes=new ArrayList<>();
        for(Incidente incidente : incidentesMiembro ){
            IncidentePresentacion unIncidente = new IncidentePresentacion(incidente);
            incidentes.add(unIncidente);
        }
        System.out.println("Incidentes encontrados: "+ incidentesMiembro.size());
        context.json(incidentes);
    }
}
