package domain.entities.repositorios;

import domain.entities.servicios.Organizacion;
import domain.entities.servicios.ServicioPublico;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//es singleton
public class OrganizacionesRepo {
    private List<Organizacion> organizaciones;

    private static OrganizacionesRepo instance;

    private OrganizacionesRepo() {
        this.organizaciones = new ArrayList<>();
    }

    public static OrganizacionesRepo getInstance() {
        if (instance == null) {
            instance = new OrganizacionesRepo();
        }
        return instance;
    }

    public Organizacion buscarMemoria(String nombre){
        Optional<Organizacion> organizacionEncontrada = organizaciones.stream()
                .filter(org -> org.getNombre().equals(nombre)).findFirst();
        return organizacionEncontrada.orElse(null);
    }

    public Organizacion buscar(String nombre){
        EntityManager em = utils.BDUtils.getEntityManager();
        Optional<Organizacion> organizacionEncontrada = em.createQuery("SELECT o from Organizacion  o WHERE o.nombre =?1",Organizacion.class)
                .setParameter(1,nombre).getResultList().stream().findFirst();
        return organizacionEncontrada.orElse(null);
    }


    public void agregar(Organizacion org){this.organizaciones.add(org);}
}
