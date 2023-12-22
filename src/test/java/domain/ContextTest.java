package domain;

import domain.entities.actores.Comunidad;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.incidentes.IncidenteMiembro;
import domain.entities.servicios.Establecimiento;
import domain.entities.servicios.ServicioBase;
import org.junit.Assert;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import utils.BDUtils;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertNotNull;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
    @Test
    public void contextUp() {
        assertNotNull(entityManager());
    }

    @Test
    public void contextUpWithTransaction() {
        withTransaction(() -> {});
    }


}

