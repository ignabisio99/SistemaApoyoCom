package handlers;

import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.repositorios.ComunidadesRepo;
import domain.entities.repositorios.RepoMiembros;
import dto.ComunidadPresentacion;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class GetPerfilComunidadHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        String idSesion=context.cookie("id_sesion");
        Integer idComunidad = context.pathParamAsClass("idComunidad", Integer.class).get();
        System.out.println(idSesion);
        System.out.println(idComunidad);
        Miembro miembro = SesionManager.get().obtenerMiembro(idSesion);
        if(miembro!=null){
            MiembroPorComunidad miembroPorComunidad = ComunidadesRepo.getInstance().obtenerMiembroPorComunidad(miembro.getMiembro_codigo(), idComunidad);

            ComunidadPresentacion comunidad = new ComunidadPresentacion(miembroPorComunidad.getComunidad(),miembroPorComunidad);

            context.json(comunidad);
        }else {
            context.json("mensaje: no tienes session valida");
        }

    }
}
