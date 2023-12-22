package domain.entities.cargaDatos;

import domain.entities.actores.OrganismoDeControl;
import domain.entities.actores.Propietario;
import domain.entities.repositorios.*;
import domain.entities.servicios.Entidad;
import domain.entities.servicios.Organizacion;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Getter
@Setter
public class LecturaCSV {

    Reader reader;

    public LecturaCSV() {
    }

    public void leerArchivo(String rutaArchivo) {
        try {
            reader = Files.newBufferedReader(Paths.get(rutaArchivo));

            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

            for (CSVRecord registro : parser) {
                String nombre = registro.get(0);
                String tipo = registro.get(1);
                String servicio = registro.get(2);
                String tipoServicio = "";

                if (registro.size() > 3) {
                    tipoServicio = registro.get(3);
                }

                if (Objects.equals(tipo, "propietario")) {
                    Propietario entidadPropietaria = new Propietario(nombre, ServicioPublicoRepo.getInstance().buscar(servicio));
                    PropietarioRepo.getInstance().agregarPropietario(entidadPropietaria);
                } else {
                    Entidad ent;
                    if (tipoServicio.equals("organizacion")) {
                        ent = OrganizacionesRepo.getInstance().buscar(servicio);

                    } else {
                        ent = LineaRepo.getInstance().buscar(servicio, tipoServicio);
                    }
                    OrganismoDeControlRepo.getInstance().agregarOrganismo(new OrganismoDeControl(nombre, ent));
                }
            }

            parser.close();

            eliminarArchivo(rutaArchivo);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void eliminarArchivo(String rutaArchivo) {
        try {

            Path path = Paths.get(rutaArchivo);

            Files.delete(path);

            System.out.println("Archivo eliminado correctamente: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("No se pudo eliminar el archivo: " + rutaArchivo);
            e.printStackTrace();
        }
    }
}
