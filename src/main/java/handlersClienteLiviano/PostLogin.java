package handlersClienteLiviano;

import FireBase.ValidadorTokenFireBase;
import domain.entities.actores.Rol;
import domain.entities.actores.miembros.Miembro;
import domain.entities.repositorios.RepoMiembros;
import dto.LoginRequest;
import handlers.SesionManager;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class PostLogin implements Handler {

    public PostLogin(){}
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        //validamos user/pass y buscamos datos de ese usuario para agregar en la sesión
        LoginRequest loginRequest = ctx.bodyAsClass(LoginRequest.class);
        System.out.println(loginRequest);
        System.out.println(loginRequest.getToken());
        if(ValidadorTokenFireBase.getInstance().validar(loginRequest.getToken())){
            Miembro miembroObtenido = RepoMiembros.getInstance().buscarMiembroUsuario(loginRequest);
            if(miembroObtenido.getMiembro_codigo()!=-1){
                SesionManager sesionManager = SesionManager.get();
                String idSesion = sesionManager.crearSesion("usuario", miembroObtenido);
                sesionManager.agregarAtributo(idSesion, "fechaInicio", LocalDateTime.now());
                sesionManager.agregarAtributo(idSesion, "rol", miembroObtenido.getUsuario().getRol());
                System.out.println("Login: " + loginRequest);
                System.out.println("Login: " + miembroObtenido.getNombre());
                System.out.println("Login: " + idSesion);

                ctx.cookie("id_sesion",idSesion);
                if(miembroObtenido.getUsuario().getRol().equals(Rol.ADMIN)){
                    ctx.redirect("/cargaDatos");
                }else{
                    ctx.redirect("/perfil");
                }
            }
        } else{ctx.redirect("/loginFail");}





    }
}
