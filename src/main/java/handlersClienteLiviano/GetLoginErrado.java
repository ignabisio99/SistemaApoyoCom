package handlersClienteLiviano;

import org.jetbrains.annotations.NotNull;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class GetLoginErrado implements Handler{
    public GetLoginErrado(){
        super();
    }
    @Override
    public void handle (@NotNull Context ctx){
        Map<String,Object> model=new HashMap<>();
        Boolean fail=true;
        System.out.println(fail);
        model.put("fail",fail);
        ctx.render("login.hbs",model);
    }
}