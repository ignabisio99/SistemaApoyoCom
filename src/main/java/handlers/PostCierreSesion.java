package handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class PostCierreSesion implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        System.out.println("Se cerro una sesion");
        String idSesion =context.cookie("id_sesion");
        SesionManager.get().eliminar(idSesion);
        context.status(202);
    }
}
