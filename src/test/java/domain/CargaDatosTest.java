package domain;

import domain.entities.actores.OrganismoDeControl;
import domain.entities.actores.Propietario;
import domain.entities.cargaDatos.LecturaCSV;
import domain.entities.repositorios.*;
import domain.entities.servicios.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.BDUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CargaDatosTest {

    private LecturaCSV lectorCSV;

    @Test
    public void carga(){


        lectorCSV = new LecturaCSV();
        lectorCSV.leerArchivo("src/main/java/domain/entities/cargaDatos/datos.csv");

        List<Propietario> propietarios =PropietarioRepo.getInstance().buscarPropiertarios();
        List<OrganismoDeControl> organismos = OrganismoDeControlRepo.getInstance().buscarOrganismos();

        for (Propietario p : propietarios) {
            System.out.println(p.getNombre());
            System.out.println(p.getServicioPublico().getTipoDeTransporte());
        }
        for (OrganismoDeControl org : organismos) {
            System.out.println(org.getNombre());
            System.out.println(org.getEntidad().getNombre());
            System.out.println(org.getEntidad().getTipoDeTransporte());
        }

        Assert.assertEquals(propietarios.size(),3);
        Assert.assertEquals(organismos.size(),3);
    }
}
