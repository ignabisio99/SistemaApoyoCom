package domain;

import domain.entities.repositorios.EstablecimientosRepo;
import domain.entities.repositorios.ServiciosRepo;
import dto.IncidentePresentacion;
import org.junit.Assert;
import org.junit.Test;

public class ServiciosTest {

    @Test
    public void obtenerServicio(){
        Assert.assertEquals("Baño, Hombre", ServiciosRepo.getInstance().buscarServicioBase(1,"Baño, Hombre").obtenerDescripcion());
    }
    @Test
    public void obtenerEstablecimiento(){
        Assert.assertEquals("Medrano", EstablecimientosRepo.getInstance().buscarEstablecimiento("Medrano","Estacion","Linea B").getNombre());
    }
    @Test
    public void obtenerEstablecimientoPorPatron(){
        Assert.assertEquals("Medrano", new IncidentePresentacion().obtenerEstablecimiento("Medrano, Estacion (Linea B)").getNombre());
    }
}
