package domain;

import domain.entities.actores.miembros.Miembro;
import domain.entities.incidentes.Incidente;
import domain.entities.incidentes.IncidenteMiembro;
import domain.entities.repositorios.RepoMiembros;
import domain.entities.sugerencias.RevisionDeIncidentes;
import org.junit.Assert;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.util.List;

public class SugerenciasTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

    @Test
    public void testSugerencias(){
        //ya tener cargada la bd
        Miembro miembro1 = RepoMiembros.getInstance().buscarMiembro(1);
        List<Incidente> incidentes = RevisionDeIncidentes.getInstance().obtenerIncidentes(miembro1);
        for(Incidente i : incidentes){
            System.out.println(i.getDescripcion());
        }

        Assert.assertEquals(2,incidentes.size());
    }
}
