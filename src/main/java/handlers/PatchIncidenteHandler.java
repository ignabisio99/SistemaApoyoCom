package handlers;

import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.incidentes.IncidenteMiembro;
import domain.entities.repositorios.ComunidadesRepo;
import domain.entities.repositorios.IncidentesRepo;
import dto.IncidentePresentacion;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.BDUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PatchIncidenteHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        String idComunidad = context.pathParamAsClass("idComunidad", String.class).get();
        String idSesion = context.pathParamAsClass("idSesion", String.class).get();

        IncidentePresentacion incidenteACerrar = context.bodyAsClass(IncidentePresentacion.class);

        LocalDateTime fechaCierre=  LocalDateTime.parse(incidenteACerrar.getFechaCierre(), DateTimeFormatter.ISO_DATE_TIME);

        Miembro mimembro=SesionManager.get().obtenerMiembro(idSesion);
        MiembroPorComunidad miembroPorComunidad= ComunidadesRepo.getInstance().obtenerMiembroPorComunidad(mimembro.getMiembro_codigo(), Integer.parseInt(idComunidad));

        EntityManager em = utils.BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        IncidenteMiembro incidente = em.find(IncidenteMiembro.class, incidenteACerrar.getCodigo());
        incidente.cerrarIncidente(fechaCierre,miembroPorComunidad);

        BDUtils.commit(em);

        context.status(202).result("Incidente cerrado con éxito");
    }
}