package domain;

import domain.entities.actores.Comunidad;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.incidentes.Incidente;
import domain.entities.incidentes.IncidenteMiembro;
import domain.entities.ranking.FormasRankings.MayorCantidadIncidentes;
import domain.entities.ranking.FormasRankings.MayorTiempoPromedio;
import domain.entities.ranking.GeneradorDeRankings;
import domain.entities.repositorios.IncidentesRepo;
import domain.entities.repositorios.RankingsRepo;
import domain.entities.servicios.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;


public class RankingTest {
    GeneradorDeRankings rankings;
    Linea linea1;
    Linea linea2;
    Establecimiento paradaMedrano;
    Establecimiento paradaCarlos;
    Establecimiento paradaPlata;
    Establecimiento paradaJujuy;
    ServicioBase banioHMedrano;
    ServicioBase banioMCarlos;
    ServicioBase escaleraPlata;
    ServicioBase banioJujuy;
    MiembroPorComunidad juan;


    @Before
    public void init(){

        rankings= new GeneradorDeRankings();
        IncidentesRepo.getInstance().getIncidentes().clear();
        RankingsRepo.getInstance().getRankings().clear();
        //Creo 2 lineas y las agrego al repo
        linea1 = new Linea("Linea B",TipoDeTransporte.SUBTE);
        linea2 = new Linea("Linea E",TipoDeTransporte.SUBTE);

        //Creo 1 tipos de establecimieto
        TipoDeEstablecimiento tipoA = new TipoDeEstablecimiento("Estacion");

        //Creo 4 Establecimientos
         paradaMedrano = new Establecimiento("Medrano",tipoA,linea1);
         paradaCarlos = new Establecimiento("Carlosgardel",tipoA,linea1);
         paradaPlata = new Establecimiento("AvLa plata",tipoA,linea2);
         paradaJujuy = new Establecimiento("Jujuy",tipoA,linea2);

        //Agrego los establecimietos a la entidad correspondiente
        linea1.agregarSucursal(paradaMedrano);
        linea1.agregarSucursal(paradaCarlos);
        linea2.agregarSucursal(paradaPlata);
        linea2.agregarSucursal(paradaJujuy);

        //Creo 2 agrupaciones de servicio
        AgrupacionServicio banio = new AgrupacionServicio("Baño");
        AgrupacionServicio escalera = new AgrupacionServicio("Escalera");

        //Creo 3 tipos de servicio
        TipoDeServicio banioHombres = new TipoDeServicio("Hombre",banio);
        TipoDeServicio banioMujeres = new TipoDeServicio("Mujer",banio);
        TipoDeServicio acceso = new TipoDeServicio("Acceso",escalera);

        //Creo 4 servicios
         banioHMedrano = new ServicioBase(paradaMedrano,Boolean.TRUE,banioHombres);
         banioMCarlos = new ServicioBase(paradaCarlos,Boolean.TRUE,banioMujeres);
         escaleraPlata = new ServicioBase(paradaPlata, Boolean.TRUE,acceso);
         banioJujuy = new ServicioBase(paradaJujuy,Boolean.TRUE,banioMujeres);

        //Agrego los servicios a los establecimientos correspondientes
        paradaMedrano.agregarServicio(banioHMedrano);
        paradaCarlos.agregarServicio(banioMCarlos);
        paradaPlata.agregarServicio(escaleraPlata);
        paradaJujuy.agregarServicio(banioJujuy);

        // seteo la fecha del ranking

        rankings.setFechaRealizacion(LocalDateTime.of(2023,8,27,0,0));

        //Creo un miembro por comunidad
        juan = new MiembroPorComunidad(new Miembro("j","c","d","a"),new Comunidad());

    }
    @Test
    public void inicidenteRepetidoEnElMismoDia(){
        Incidente i1 = new IncidenteMiembro("Incidente e EscaleraPlata 1", escaleraPlata, LocalDateTime.of(2023, 8, 21, 10, 0), paradaPlata, juan);
        Incidente i2 = new IncidenteMiembro("Incidente e EscaleraPlata 1", escaleraPlata, LocalDateTime.of(2023, 8, 21, 0, 0), paradaPlata, juan);

        Assert.assertTrue(i1.esRepetidoEnRango(i2,24));
        Assert.assertTrue(i2.esRepetidoEnRango(i1,24));
    }
    @Test
    public void incidenteRepetidoEnRangoHorario(){
        Incidente i1 = new IncidenteMiembro("Incidente e EscaleraPlata 1", escaleraPlata, LocalDateTime.of(2023, 8, 20, 10, 0), paradaPlata, juan);
        Incidente i2 = new IncidenteMiembro("Incidente e EscaleraPlata 1", escaleraPlata, LocalDateTime.of(2023, 8, 21, 0, 0), paradaPlata, juan);
        Incidente i3 = new IncidenteMiembro("Incidente e EscaleraPlata 1", escaleraPlata, LocalDateTime.of(2023, 8, 19, 11, 0), paradaPlata, juan);

        Assert.assertTrue(i1.esRepetidoEnRango(i2,24));
        Assert.assertTrue(i1.esRepetidoEnRango(i3,24));
        Assert.assertTrue(i2.esRepetidoEnRango(i1,24));
        Assert.assertTrue(i3.esRepetidoEnRango(i1,24));
    }
    @Test
    public void incidenteNoRepetido(){
        Incidente i1 = new IncidenteMiembro("Incidente e EscaleraPlata 1", escaleraPlata, LocalDateTime.of(2023, 8, 21, 0, 0), paradaPlata, juan);
        Incidente i2 = new IncidenteMiembro("Incidente e EscaleraPlata 1", escaleraPlata, LocalDateTime.of(2023, 8, 23, 12, 0), paradaPlata, juan);

        Assert.assertFalse(i1.esRepetidoEnRango(i2,24));
        Assert.assertFalse(i2.esRepetidoEnRango(i1,24));

    }
    @Test
    public void mayorCantidadDeIncidentes(){
        rankings.agregarFormaRanking(new MayorCantidadIncidentes());

        // Crear incidentes sobre el baño de Medrano
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e Medrano", banioHMedrano, LocalDateTime.of(2023, 8, 21, 0, 0), paradaMedrano, juan));
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e Medrano", banioHMedrano, LocalDateTime.of(2023, 8, 23, 0, 0), paradaMedrano, juan));

        // Crear incidentes sobre el baño de Carlos
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e Carlos", banioMCarlos, LocalDateTime.of(2023, 8, 21, 0, 0), paradaCarlos, juan));
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e Carlos", banioMCarlos, LocalDateTime.of(2023, 8, 23, 0, 0), paradaCarlos, juan));
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e Carlos", banioMCarlos, LocalDateTime.of(2023, 8, 25, 0, 0), paradaCarlos, juan));

        // Crear incidentes sobre el baño de Jujuy
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e Jujuy", banioJujuy, LocalDateTime.of(2023, 8, 21, 0, 0), paradaJujuy, juan));
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e Jujuy", banioJujuy, LocalDateTime.of(2023, 8, 23, 0, 0), paradaJujuy, juan));
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e Jujuy", banioJujuy, LocalDateTime.of(2023, 8, 25, 0, 0), paradaJujuy, juan));

        // Crear incidentes sobre escaleraPlata, todos el mismo día
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e EscaleraPlata 1", escaleraPlata, LocalDateTime.of(2023, 8, 21, 0, 0), paradaPlata, juan));
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e EscaleraPlata 2", escaleraPlata, LocalDateTime.of(2023, 8, 21, 1, 0), paradaPlata, juan));
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e EscaleraPlata 3", escaleraPlata, LocalDateTime.of(2023, 8, 21, 2, 0), paradaPlata, juan));
        IncidentesRepo.getInstance().agregarIncidente(new IncidenteMiembro("Incidente e EscaleraPlata 4", escaleraPlata, LocalDateTime.of(2023, 8, 21, 3, 0), paradaPlata, juan));

        rankings.generarRanking();
        //5 incidentes para la linea B y 7 para la linea E de los cuales solo cuentan 4
        Assert.assertEquals("Linea B",RankingsRepo.getInstance().obtenerRanking(0).obtenerPrimerLugar().ocupadoPor());

    }
    @Test
    public void mayorTiempoPromedio(){
        rankings.agregarFormaRanking(new MayorTiempoPromedio());
        // Crear incidentes sobre el baño de Medrano
        Incidente incidenteMedrano1 = new IncidenteMiembro("Incidente e Medrano", banioHMedrano, LocalDateTime.of(2023, 8, 21, 0, 0), paradaMedrano, juan);
        incidenteMedrano1.cerrarIncidente(LocalDateTime.of(2023, 8, 21, 2, 0));

        Incidente incidenteMedrano2 = new IncidenteMiembro("Incidente e Medrano", banioHMedrano, LocalDateTime.of(2023, 8, 22, 0, 0), paradaMedrano, juan);
        incidenteMedrano2.cerrarIncidente(LocalDateTime.of(2023, 8, 22, 2, 0));

        // Crear incidentes sobre el baño de Carlos
        Incidente incidenteCarlos1 = new IncidenteMiembro("Incidente e Carlos", banioMCarlos, LocalDateTime.of(2023, 8, 21, 0, 0), paradaCarlos, juan);
        incidenteCarlos1.cerrarIncidente(LocalDateTime.of(2023, 8, 21, 3, 0));

        Incidente incidenteCarlos2 = new IncidenteMiembro("Incidente e Carlos", banioMCarlos, LocalDateTime.of(2023, 8, 22, 0, 0), paradaCarlos, juan);
        incidenteCarlos2.cerrarIncidente(LocalDateTime.of(2023, 8, 22, 3, 0));

        // Crear incidentes sobre el baño de Jujuy
        Incidente incidenteJujuy1 = new IncidenteMiembro("Incidente e Jujuy", banioJujuy, LocalDateTime.of(2023, 8, 21, 0, 0), paradaJujuy, juan);
        incidenteJujuy1.cerrarIncidente(LocalDateTime.of(2023, 8, 21, 3, 0));

        Incidente incidenteJujuy2 = new IncidenteMiembro("Incidente e Jujuy", banioJujuy, LocalDateTime.of(2023, 8, 22, 0, 0), paradaJujuy, juan);
        incidenteJujuy2.cerrarIncidente(LocalDateTime.of(2023, 8, 22, 3, 0));

        Incidente incidenteJujuy3 = new IncidenteMiembro("Incidente e Jujuy", banioJujuy, LocalDateTime.of(2023, 8, 23, 0, 0), paradaJujuy, juan);
        incidenteJujuy3.cerrarIncidente(LocalDateTime.of(2023, 8, 23, 3, 0));

        // Crear incidente sobre escaleraPlata
        Incidente incidenteEscaleraPlata1 = new IncidenteMiembro("Incidente e EscaleraPlata 1", escaleraPlata, LocalDateTime.of(2023, 8, 21, 0, 0), paradaPlata, juan);
        incidenteEscaleraPlata1.cerrarIncidente(LocalDateTime.of(2023, 8, 21, 5, 0));

        // Agregar los incidentes al repositorio de incidentes
        IncidentesRepo.getInstance().agregarIncidente(incidenteMedrano1);
        IncidentesRepo.getInstance().agregarIncidente(incidenteMedrano2);
        IncidentesRepo.getInstance().agregarIncidente(incidenteCarlos1);
        IncidentesRepo.getInstance().agregarIncidente(incidenteCarlos2);
        IncidentesRepo.getInstance().agregarIncidente(incidenteJujuy1);
        IncidentesRepo.getInstance().agregarIncidente(incidenteJujuy2);
        IncidentesRepo.getInstance().agregarIncidente(incidenteJujuy3);
        IncidentesRepo.getInstance().agregarIncidente(incidenteEscaleraPlata1);

        rankings.generarRanking();
        Assert.assertEquals("Linea E",RankingsRepo.getInstance().obtenerRanking(0).obtenerPrimerLugar().ocupadoPor());
    }

}
