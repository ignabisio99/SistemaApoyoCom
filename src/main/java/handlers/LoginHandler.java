package handlers;

import domain.entities.actores.miembros.Miembro;
import domain.entities.repositorios.RepoMiembros;
import dto.LoginRequest;
import dto.LoginResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class LoginHandler implements Handler {



    public LoginHandler() {
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        //validamos user/pass y buscamos datos de ese usuario para agregar en la sesión

        LoginRequest loginRequest = context.bodyAsClass(LoginRequest.class);

        //todo: hacer que se puedan logear todos, una clase abstracta "logeable" x ej
        Miembro miembroObtenido = RepoMiembros.getInstance().buscarMiembroUsuario(loginRequest);
        SesionManager sesionManager = SesionManager.get();
        String idSesion = sesionManager.crearSesion("usuario", miembroObtenido);

        sesionManager.agregarAtributo(idSesion, "fechaInicio", LocalDateTime.now());
        sesionManager.agregarAtributo(idSesion, "rol", miembroObtenido.getUsuario().getRol());
        System.out.println("Login: " + loginRequest);
        System.out.println("Login: " + miembroObtenido.getNombre());
        System.out.println("Login: " + idSesion);

        context.json(new LoginResponse(idSesion));

    }

}