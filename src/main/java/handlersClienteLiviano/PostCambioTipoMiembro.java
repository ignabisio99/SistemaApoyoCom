package handlersClienteLiviano;

import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.actores.miembros.TipoDeMiembro;
import domain.entities.incidentes.IncidenteMiembro;
import domain.entities.repositorios.ComunidadesRepo;
import domain.entities.repositorios.RepoMiembros;
import handlers.SesionManager;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import utils.BDUtils;

import javax.persistence.EntityManager;

public class PostCambioTipoMiembro implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String id_sesion=ctx.cookie("id_sesion");
        String tipoUsuario=ctx.formParamAsClass("tipoMiembro",String.class).get();
        System.out.println(tipoUsuario);
        Miembro miembroEjecutor= SesionManager.get().obtenerMiembro(id_sesion);
        int idMiembroACambiar=ctx.pathParamAsClass("id_miembro", int.class).get();
        int idComunidad=ctx.pathParamAsClass("id_comunidad", int.class).get();

        System.out.println("ENTRO A JODER ACA");

        MiembroPorComunidad miembroPorComunidadEjecutor= ComunidadesRepo.getInstance().obtenerMiembroPorComunidad(miembroEjecutor.getMiembro_codigo(),idComunidad);
        MiembroPorComunidad miembroPorComunidadACambiar=ComunidadesRepo.getInstance().obtenerMiembroPorComunidad(idMiembroACambiar,idComunidad);

        EntityManager em = utils.BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);


        MiembroPorComunidad elPosta=em.find(MiembroPorComunidad.class, miembroPorComunidadACambiar.getMiembroPorComunidad_codigo());

        if(miembroPorComunidadEjecutor.getEsAdmin()){
            if(tipoUsuario.equalsIgnoreCase("Observador")){
                TipoDeMiembro nuevoTipo= RepoMiembros.getInstance().buscarTipoDeMiembro("Usuario de Servicio");
                elPosta.setTipoDeMiembro(nuevoTipo);
            }
            else{
                TipoDeMiembro nuevoTipo=RepoMiembros.getInstance().buscarTipoDeMiembro("Observador");
                elPosta.setTipoDeMiembro(nuevoTipo);
            }


            BDUtils.commit(em);
        }
        else{
            BDUtils.rollback(em);
        }

        ctx.redirect("/comunidad/"+idComunidad);
    }
}
