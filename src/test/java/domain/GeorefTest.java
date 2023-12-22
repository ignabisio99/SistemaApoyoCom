package domain;

import domain.entities.services.georef.ServicioGeoref;
import domain.entities.services.georef.entities.*;
import domain.entities.servicios.Organizacion;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class GeorefTest {


    @Test
    public void localizacionProvincia() throws IOException {

        ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
        ListadoDeProvincias listadoDeProvincias = servicioGeoref.listadoDeProvincias();
        ListadoDeDepartamentos listadoDeDepartamentos = servicioGeoref.listadoDeDepartamentos(6);
        ListadoDeMunicipios listadoDeMunicipios = servicioGeoref.listadoDeMunicipios(10);

        Organizacion organizacion1 = new Organizacion("Argenchino","Supermercado");
        Organizacion organizacion2 = new Organizacion("Argenchino2","Supermercado2");
        Organizacion organizacion3 = new Organizacion("Argenchino3","Supermercado3");

        organizacion1.setLocalizacion(listadoDeProvincias.provincias.stream().findFirst().get());
        organizacion2.setLocalizacion(listadoDeDepartamentos.departamentos.stream().findFirst().get());
        organizacion3.setLocalizacion(listadoDeMunicipios.municipios.stream().findFirst().get());

        System.out.print(organizacion1.getLocalizacion().getNombre());
        System.out.print("\n");
        System.out.print("-------------------------");
        System.out.print("\n");
        System.out.println(organizacion2.getLocalizacion().getNombre());
        System.out.println(((Departamento) organizacion2.getLocalizacion()).getProvincia().getNombre());
        System.out.print("-------------------------");
        System.out.print("\n");
        System.out.println(organizacion3.getLocalizacion().getNombre());
        System.out.println(((Municipio) organizacion3.getLocalizacion()).getProvincia().getNombre());

    }
}
