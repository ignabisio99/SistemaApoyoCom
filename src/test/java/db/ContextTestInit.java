package db;

import domain.entities.actores.Comunidad;
import domain.entities.actores.Rol;
import domain.entities.actores.Usuario;
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

public class ContextTestInit extends AbstractPersistenceTest implements WithGlobalEntityManager {
    @Test
    public void init(){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        Usuario usuario1 = new Usuario("jorge","123", Rol.MIEMBRO);
        em.persist(usuario1);
        Miembro miembro1 = new Miembro("Jorge","Perez","hola@gmail.com","123");
        Comunidad comunidad1 = new Comunidad();
        em.persist(comunidad1);
        MiembroPorComunidad miembroPorComunidad1 = new MiembroPorComunidad(miembro1, comunidad1);
        em.persist(miembroPorComunidad1);
        miembro1.setComunidades(Collections.singletonList(miembroPorComunidad1));
        miembro1.setUsuario(usuario1);
        em.persist(miembro1);
        ServicioBase servicioBase1 = new ServicioBase();
        em.persist(servicioBase1);
        Establecimiento establecimiento1 = new Establecimiento("Chau");
        establecimiento1.agregarServicio(servicioBase1);
        em.persist(establecimiento1);
        IncidenteMiembro incidenteMiembro1 =
                new IncidenteMiembro("incidente 1", servicioBase1, LocalDateTime.now(),establecimiento1,miembroPorComunidad1);
        em.persist(incidenteMiembro1);

        List<Miembro> miembros = em.createQuery("select m from Miembro m where m.apellido = ?1", Miembro.class)
                .setParameter(1,"Perez").getResultList();

        Assert.assertEquals(miembros.stream().findFirst().get().getApellido(),"Perez");


        BDUtils.commit(em);
    }

}
