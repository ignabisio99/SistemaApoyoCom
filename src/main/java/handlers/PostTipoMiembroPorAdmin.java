package handlers;

import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.actores.miembros.TipoDeMiembro;
import domain.entities.repositorios.ComunidadesRepo;
import domain.entities.repositorios.RepoMiembros;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.BDUtils;

import javax.persistence.EntityManager;

public class PostTipoMiembroPorAdmin implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        try {
            System.out.println("Entro al handler pesado");


            PatchTipoMiembroResponse info = context.bodyAsClass(PatchTipoMiembroResponse.class);

            MiembroPorComunidad miembroPorComunidad = ComunidadesRepo.getInstance().obtenerMiembroPorComunidad(info.getMiembroId(),Integer.parseInt(info.getComunidadId()));
            EntityManager em = BDUtils.getEntityManager();
            BDUtils.comenzarTransaccion(em);
            MiembroPorComunidad miembroACambiar = em.find(MiembroPorComunidad.class, miembroPorComunidad.getMiembroPorComunidad_codigo());

            TipoDeMiembro tipoACambiar;
            if (info.getNuevoTipo().equalsIgnoreCase("Observador")) {
                tipoACambiar = RepoMiembros.getInstance().buscarTipoDeMiembro("Observador");
            } else {
                tipoACambiar = RepoMiembros.getInstance().buscarTipoDeMiembro("Usuario de Servicio");
            }

            miembroACambiar.setTipoDeMiembro(tipoACambiar);
            BDUtils.commit(em);
            System.out.println("Tipo encontrado: " + tipoACambiar.getTipoDeMiembro());

            context.status(200).result("Tipo de miembro actualizado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            context.status(500).result("Error interno del servidor al actualizar el tipo de miembro.");
        }
    }
}
