package handlersClienteLiviano;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GetCargaDatosHandler implements Handler {

    public GetCargaDatosHandler(){

    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {

        HashMap<String,Object> model=new HashMap<>();
        Boolean fail=false;
        model.put("fallo",fail);
        ctx.render("cargaDatos.hbs",model);

    }
}
