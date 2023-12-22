package handlersClienteLiviano;

import domain.entities.actores.Rol;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.actores.miembros.TipoDeMiembro;
import domain.entities.incidentes.Incidente;
import domain.entities.repositorios.ComunidadesRepo;
import domain.entities.repositorios.RepoMiembros;
import domain.entities.sugerencias.RevisionDeIncidentes;
import dto.IncidentePresentacion;
import dto.MiembroPresentacion;
import handlers.SesionManager;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.BDUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatchTipoDeMiembroHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        String tipoDeMiembro=context.formParamAsClass("tipoMiembro",String.class).get();
        int idComunidad=context.formParamAsClass("idComunidad", int.class).get();
        String id_sesion=context.cookie("id_sesion");
        Miembro miembro= SesionManager.get().obtenerMiembro(id_sesion);
        System.out.println("Se manda el form");
        System.out.println(tipoDeMiembro);

        MiembroPorComunidad miembroPorComunidad = ComunidadesRepo.getInstance().obtenerMiembroPorComunidad(miembro.getMiembro_codigo(),idComunidad);
        EntityManager em = utils.BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        MiembroPorComunidad miembroACambiar = em.find(MiembroPorComunidad.class,miembroPorComunidad.getMiembroPorComunidad_codigo());

        if(tipoDeMiembro.equalsIgnoreCase("Observador")){
            TipoDeMiembro nuevoTipo= RepoMiembros.getInstance().buscarTipoDeMiembro("Observador");
            miembroACambiar.setTipoDeMiembro(nuevoTipo);
        }
        else{
            TipoDeMiembro nuevoTipo=RepoMiembros.getInstance().buscarTipoDeMiembro("Usuario de Servicio");
            miembroACambiar.setTipoDeMiembro(nuevoTipo);
        }
        BDUtils.commit(em);

        Map<String, Object> model = new HashMap<>();
        //String idSesion = context.pathParamAsClass("idSesion", String.class).get();
        if(miembro!= null){
            //List<Comunidad> comunidades = ComunidadesRepo.getInstance().bucarComunidadesMimebro(idSesion);
            List<MiembroPresentacion> comunidades = new ArrayList<>();
            for(MiembroPorComunidad m: miembro.getComunidades()){
                MiembroPresentacion unMiembro = new MiembroPresentacion(m);
                comunidades.add(unMiembro);
            }
            model.put("comunidades",comunidades);

            MiembroPresentacion miembroPresentacion= new MiembroPresentacion(miembro);
            model.put("miembro",miembroPresentacion);

            // Agregar el rol del usuario al modelo
            model.put("esAdmin", Rol.ADMIN.equals(miembro.getUsuario().getRol()));

            List<Incidente> incidentesMiembro = RevisionDeIncidentes.getInstance().obtenerIncidentes(miembro);
            List<IncidentePresentacion> incidentes=new ArrayList<>();
            for(Incidente incidente:incidentesMiembro){
                IncidentePresentacion unIncidente = new IncidentePresentacion(incidente);
                incidentes.add(unIncidente);
            }

            model.put("incidentes", incidentes);
            Boolean exito=true;
            model.put("exito",true);

            context.render("perfil.hbs",model);
        }else {
            context.status(404).json("No tenes una sesion valida");
        }
    }
}
