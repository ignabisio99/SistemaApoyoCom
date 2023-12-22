package domain.entities.repositorios;

import domain.entities.actores.OrganismoDeControl;
import domain.entities.actores.Propietario;
import domain.entities.servicios.Entidad;
import domain.entities.servicios.ServicioPublico;
import lombok.Getter;
import utils.BDUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrganismoDeControlRepo {
    private List<OrganismoDeControl> organismosDeControl;

    private static OrganismoDeControlRepo instance;

    private OrganismoDeControlRepo() {
        this.organismosDeControl= new ArrayList<>();
    }

    public static OrganismoDeControlRepo getInstance() {
        if (instance == null) {
            instance = new OrganismoDeControlRepo();
        }
        return instance;
    }

    public void agregarOrganismo(OrganismoDeControl org){
        EntityManager em = utils.BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        // Verificar si el ServicioPublico está en estado detached
        Entidad entidad = org.getEntidad();
        org.setEntidad(null);
        if (!em.contains(entidad)) {
            // Si está detached, cargarlo en el contexto de persistencia
            entidad = em.merge(entidad);
        }
        org.setEntidad(entidad);
        em.persist(org);
        BDUtils.commit(em);
    }

    public List<OrganismoDeControl> buscarOrganismos(){
        EntityManager em = utils.BDUtils.getEntityManager();
        List<OrganismoDeControl> organismosDeControlEncontrados= em.createQuery("SELECT o from OrganismoDeControl o",OrganismoDeControl.class).getResultList();
        return organismosDeControlEncontrados;
    }

}
