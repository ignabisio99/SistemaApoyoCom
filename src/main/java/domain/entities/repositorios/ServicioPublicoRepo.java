package domain.entities.repositorios;

import domain.entities.servicios.ServicioPublico;
import domain.entities.servicios.TipoDeTransporte;
import lombok.Getter;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class ServicioPublicoRepo {
    private List<ServicioPublico> serviciosPublicos;

    private static ServicioPublicoRepo instance;

    private ServicioPublicoRepo() {
        this.serviciosPublicos = new ArrayList<>();
    }

    public static ServicioPublicoRepo getInstance() {
        if (instance == null) {
            instance = new ServicioPublicoRepo();
        }
        return instance;
    }

    public ServicioPublico buscarMemoria(String tipoDeTransporte){

        Optional<ServicioPublico> servicioEncontrado = serviciosPublicos.stream()
                .filter(servicio -> servicio.getTipoDeTransporte()
                        .toString().equals(tipoDeTransporte)).findFirst();
        return servicioEncontrado.orElse(null);
    }

    public ServicioPublico buscar(String tipoDeTransporte){
        EntityManager em = utils.BDUtils.getEntityManager();
        Optional<ServicioPublico> servicioEncontrado = em.createQuery("SELECT s from ServicioPublico s WHERE s.tipoDeTransporte = ?1",ServicioPublico.class)
                .setParameter(1, TipoDeTransporte.valueOf(tipoDeTransporte)).getResultList().stream().findFirst();
        return servicioEncontrado.orElse(null);
    }

    public void agregarServicio(ServicioPublico servicio) {
        serviciosPublicos.add(servicio);
    }
}
