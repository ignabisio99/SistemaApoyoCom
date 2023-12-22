package handlersClienteLiviano;

import FireBase.ValidadorTokenFireBase;
import domain.entities.actores.Rol;
import domain.entities.actores.miembros.Miembro;
import domain.entities.repositorios.RepoMiembros;
import dto.LoginRequest;
import handlers.SesionManager;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class PostLoginGoogle implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
        LoginRequest loginRequest = context.bodyAsClass(LoginRequest.class);
        System.out.println(loginRequest.toString());
        if(ValidadorTokenFireBase.getInstance().validar(loginRequest.getToken())){
            Miembro miembroObtenido = RepoMiembros.getInstance().buscarMiembroUsuarioGoogle(loginRequest);
            if(miembroObtenido.getMiembro_codigo()!=-1){
                SesionManager sesionManager = SesionManager.get();
                String idSesion = sesionManager.crearSesion("usuario", miembroObtenido);
                sesionManager.agregarAtributo(idSesion, "fechaInicio", LocalDateTime.now());
                sesionManager.agregarAtributo(idSesion, "rol", miembroObtenido.getUsuario().getRol());
                System.out.println("Login: " + loginRequest);
                System.out.println("Login: " + miembroObtenido.getNombre());
                System.out.println("Login: " + idSesion);

                context.cookie("id_sesion",idSesion);
                if(miembroObtenido.getUsuario().getRol().equals(Rol.ADMIN)){
                    context.redirect("/cargaDatos");
                }else{
                    context.redirect("/perfil");
                }
            }
        } else{context.redirect("/loginFail");}
    }
}
