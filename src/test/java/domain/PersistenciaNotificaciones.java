package domain;


import domain.entities.notificaciones.Notificacion;
import domain.entities.notificaciones.NotificadorIncidentes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.BDUtils;

import javax.persistence.EntityManager;

public class PersistenciaNotificaciones {
    EntityManager em;

    @Before
    public void init(){
        em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        Notificacion n1 = new Notificacion();
        Notificacion n2 = new Notificacion();

        em.persist(n1);
        em.persist(n2);
        BDUtils.commit(em);

    }
    @Test
    public void recuperarNotificaciones(){
        NotificadorIncidentes nfi = new NotificadorIncidentes();

        nfi.obtenerNotificacionesPendientes();
        Assert.assertEquals(2,nfi.getNotifcacionesPendientes().size());

    }
}
