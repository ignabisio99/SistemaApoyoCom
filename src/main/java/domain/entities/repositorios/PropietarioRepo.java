package domain.entities.repositorios;

import domain.entities.actores.Propietario;
import domain.entities.servicios.ServicioPublico;
import lombok.Getter;
import utils.BDUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PropietarioRepo {

    private List<Propietario> propietarios;

    private static PropietarioRepo instance;

    private PropietarioRepo() {
        this.propietarios= new ArrayList<>();
    }

    public static PropietarioRepo getInstance() {
        if (instance == null) {
            instance = new PropietarioRepo();
        }
        return instance;
    }

    public void agregarPropietario(Propietario propietario){
        EntityManager em = utils.BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        // Verificar si el ServicioPublico está en estado detached
        ServicioPublico servicioPublico = propietario.getServicioPublico();
        propietario.setServicioPublico(null);
        if (!em.contains(servicioPublico)) {
            // Si está detached, cargarlo en el contexto de persistencia
            servicioPublico = em.merge(servicioPublico);
        }
        propietario.setServicioPublico(servicioPublico);
        em.persist(propietario);
        BDUtils.commit(em);
    }

    public List<Propietario> buscarPropiertarios(){
        EntityManager em = utils.BDUtils.getEntityManager();
        List<Propietario> propietariosEncontrados= em.createQuery("SELECT p from Propietario p",Propietario.class).getResultList();
        return propietariosEncontrados;
    }

}
