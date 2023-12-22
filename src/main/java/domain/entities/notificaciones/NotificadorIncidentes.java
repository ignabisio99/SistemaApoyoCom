package domain.entities.notificaciones;

import domain.entities.actores.miembros.Miembro;
import lombok.Getter;
import lombok.Setter;
import utils.BDUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Getter
public class NotificadorIncidentes {
    private List<Notificacion> notifcacionesPendientes;
    public void limpiar(){
        notifcacionesPendientes= this.notifcacionesPendientes.stream().filter(n-> !n.estaVacia()).collect(Collectors.toList());

    }
    public void obtenerNotificacionesPendientes(){
        EntityManager em = BDUtils.getEntityManager();
        notifcacionesPendientes = em.createQuery("SELECT n FROM Notificacion n ", Notificacion.class).getResultList();
    }
    public void notificar(){
        this.notifcacionesPendientes.forEach(n -> n.notificar(LocalDateTime.now()));
        limpiar();
    }
}
