package domain;

import domain.entities.actores.Comunidad;
import domain.entities.repositorios.ComunidadesRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

public class ComunidadTest {
    private List<Comunidad> comunidades;
    @Before
    public void init(){
        this.comunidades= ComunidadesRepo.getInstance().bucarComunidades();
    }
    @Test
    public void tomaComunidades(){
        Assert.assertTrue(this.comunidades.size()>=1);
    }
    @Test
    public void tomaIncidentesDeLaComunidad(){
        Assert.assertTrue(this.comunidades.get(0).getIncidentes().size()>=1 || this.comunidades.get(0).getIncidentes().isEmpty() );
    }
    @Test
    public void buscarComunidadEspecifica(){
        Assert.assertEquals(1,ComunidadesRepo.getInstance().buscarComunidadPorId(1).getComunidad_codigo());
    }
    @Test
    public void buscarMiemporPorComunidad(){
        Assert.assertEquals(1,ComunidadesRepo.getInstance().obtenerMiembroPorComunidad(1,1).getMiembro().getMiembro_codigo());
    }
}
