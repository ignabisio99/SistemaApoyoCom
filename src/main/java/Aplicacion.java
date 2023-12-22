import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import handlers.*;
import handlersClienteLiviano.*;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import org.quartz.SchedulerException;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;

import java.io.FileInputStream;
import java.io.IOException;


public class Aplicacion {
    public static void main(String[] args) throws SchedulerException, IOException {
        initTemplateEngine();
        Javalin app = Javalin.create(javalinConfig -> {
                    javalinConfig.plugins.enableCors(cors -> {cors.add(it -> it.anyHost());}); // para poder hacer requests de un dominio a otro
                    javalinConfig.staticFiles.add("/"); // recursos estáticos (HTML, CSS, JS, IMG)
                    javalinConfig.jsonMapper(new JavalinJackson().updateMapper(mapper ->
                            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)));
                }
                ).get("/", new GetLoginLiv())
                .start(4567);
        initializeFireBase();

        // Handlers
        // Cliente Pesado
        app.get("/api/incidentes/comunidad/{id}", new GetIncidentesComunidadHandler());
        app.get("/api/rankings", new GetRankingsHandler());
        app.post("/api/agregarIncidente/{idComunidad}/{idSesion}", new PostIncidenteHandler());
        app.get("/api/ranking/{id}",new GetRankingHandler());
        app.get("/api/establecimientos", new GetEstablecimientosHandler());
        app.post("/api/login", new LoginHandler());
        app.patch("/api/cerrarIncidente/{idComunidad}/{idSesion}", new PatchIncidenteHandler());
        app.get("/api/comunidad/{idComunidad}/{idSesion}",new GetPerfilComunidadHandler());
        app.post("/perfil",new PatchTipoDeMiembroHandler());
        app.post("/api/admin/cambiarTipoMiembro",new PostTipoMiembroPorAdmin());

        //Cliente Liviano
        app.get("/rankingsLiviano",new GetRankingsLivianoHandler());
        app.get("/rankingLiviano/{id}",new GetRankingLivianoHandler());
        app.get("/comunidad/{id}",new GetComunidadHandler());
        app.post("/comunidad/cambiar/{id_comunidad}/{id_miembro}",new PostCambioTipoMiembro());
        app.get("/perfil",new GetPerfilMiembroHandler());
        app.get("/login",new GetLoginLiv());
        app.post("/loginLiv",new PostLogin());
        app.get("/loginFail", new GetLoginErrado());
        app.get("/cargaDatos", new GetCargaDatosHandler());
        app.post("/cargarDatos", new PostCargarDatosHandler());
        app.post("/login/google", new PostLoginGoogle());
        app.post("api/logout", new PostCierreSesion());
    }
    private static void initTemplateEngine() {
        JavalinRenderer.register(
                (path, model, context) -> { // Función que renderiza el template
                    Handlebars handlebars = new Handlebars();
                    Template template = null;
                    try {
                        template = handlebars.compile("templates/" + path.replace(".hbs", ""));
                        return template.apply(model);
                    } catch (IOException e) {
                        //
                        e.printStackTrace();
                        context.status(HttpStatus.NOT_FOUND);
                        return "No se encuentra la página indicada...";
                    }
                }, ".hbs" // Extensión del archivo de template
        );
    }
    public static void initializeFireBase() {
        try {
            // Reemplaza "path/to/your/serviceAccountKey.json" con la ruta correcta a tu archivo JSON
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/Fire-Base/fireBaseCredentials.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
