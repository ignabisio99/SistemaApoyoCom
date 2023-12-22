package handlersClienteLiviano;

import org.jetbrains.annotations.NotNull;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

import java.util.HashMap;

public class GetLoginLiv implements Handler{
    public GetLoginLiv(){

    }
    @Override
    public void handle (@NotNull Context ctx){
        HashMap<String,Object> model=new HashMap<>();
        Boolean fail=false;
        model.put("fallo",fail);
        ctx.render("login.hbs",model);
    }
}
