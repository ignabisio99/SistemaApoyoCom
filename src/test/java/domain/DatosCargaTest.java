package domain;

import domain.entities.servicios.Linea;
import domain.entities.servicios.Organizacion;
import domain.entities.servicios.ServicioPublico;
import domain.entities.servicios.TipoDeTransporte;
import org.junit.Test;
import utils.BDUtils;

import javax.persistence.EntityManager;

public class DatosCargaTest {

    @Test
    public void carga(){
        Linea linea1 = new Linea("7", TipoDeTransporte.COLECTIVO);
        Linea linea2 = new Linea("A",TipoDeTransporte.SUBTE);
        Organizacion org1 = new Organizacion("Organizacion1","srl");
        ServicioPublico serv1 = new ServicioPublico(TipoDeTransporte.COLECTIVO);
        ServicioPublico serv2 = new ServicioPublico(TipoDeTransporte.TREN);
        ServicioPublico serv3 = new ServicioPublico(TipoDeTransporte.SUBTE);

        /*
        LineaRepo.getInstance().agregar(linea1);
        LineaRepo.getInstance().agregar(linea2);

        OrganizacionesRepo.getInstance().agregar(org1);

       ServicioPublicoRepo.getInstance().agregarServicio(new ServicioPublico(TipoDeTransporte.COLECTIVO));
       ServicioPublicoRepo.getInstance().agregarServicio(new ServicioPublico(TipoDeTransporte.TREN));
       ServicioPublicoRepo.getInstance().agregarServicio(new ServicioPublico(TipoDeTransporte.SUBTE));
       */
        EntityManager em = utils.BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(linea1);
        em.persist(linea2);
        em.persist(org1);
        em.persist(serv1);
        em.persist(serv2);
        em.persist(serv3);
        BDUtils.commit(em);
    }

}
