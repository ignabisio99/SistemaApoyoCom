package handlersClienteLiviano;

import domain.entities.cargaDatos.LecturaCSV;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class PostCargarDatosHandler implements Handler {


    private static final String UPLOAD_DIR = "src/main/java/domain/entities/cargaDatos/";

    @Override
    public void handle(Context ctx) throws Exception {
        List<UploadedFile> files = ctx.uploadedFiles("archivo");

        if (files != null && !files.isEmpty()) {
            UploadedFile file = files.get(0);
            String fileName = file.filename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);


            Files.createDirectories(filePath.getParent());


            Files.copy(file.content(), filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Archivo guardado correctamente en: " + filePath);
            LecturaCSV lectorCSV = new LecturaCSV();
            lectorCSV.leerArchivo(filePath.toString());
            ctx.status(200);
        } else {
            System.err.println("No se recibieron archivos en la solicitud");
            ctx.status(404);
        }
    }
}
