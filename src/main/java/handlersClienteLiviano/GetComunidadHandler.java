package handlersClienteLiviano;

import domain.entities.actores.Comunidad;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.repositorios.ComunidadesRepo;
import dto.MiembroPresentacion;
import handlers.SesionManager;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetComunidadHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        String idSesion=context.cookie("id_sesion");
        Miembro miembroActual = SesionManager.get().obtenerMiembro(idSesion);
        Integer idBuscado = context.pathParamAsClass("id", Integer.class).get();
        Comunidad comunidad = ComunidadesRepo.getInstance().buscarComunidadPorId(idBuscado);
        MiembroPorComunidad miembroPorComunidadActual=ComunidadesRepo.getInstance().obtenerMiembroPorComunidad(
                miembroActual.getMiembro_codigo(),comunidad.getComunidad_codigo()
        );
        Boolean esAdmin=miembroPorComunidadActual.getEsAdmin();
        model.put("administrador",esAdmin);

        // Agregar la información relevante al modelo
        model.put("nombreComunidad", comunidad.getNombre());
        model.put("objetivoComunidad", comunidad.getObjetivo());
        model.put("codigoComunidad",idBuscado);

        // Lista de miembros
        List<MiembroPresentacion> miembrosDeComunidad = new ArrayList<>();
        for (MiembroPorComunidad miembro : comunidad.getMiembros()) {
            MiembroPresentacion unMiembro = new MiembroPresentacion(miembro);
            miembrosDeComunidad.add(unMiembro);
        }
        model.put("miembros", miembrosDeComunidad);

        // Lista de administradores
        List<MiembroPresentacion> miembrosAdministradores = new ArrayList<>();
        for (MiembroPorComunidad admin : comunidad.getMiembros().stream().filter(MiembroPorComunidad::getEsAdmin).collect(Collectors.toList())) {
            MiembroPresentacion unMiembro = new MiembroPresentacion(admin);
            miembrosAdministradores.add(unMiembro);
        }
        model.put("miembrosAdministradores", miembrosAdministradores);


        context.render("perfil_comunidad.hbs", model);
    }
}
