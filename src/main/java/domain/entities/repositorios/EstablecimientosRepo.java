package domain.entities.repositorios;

import domain.entities.servicios.Establecimiento;
import lombok.Getter;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
@Getter
public class EstablecimientosRepo {
    private static EstablecimientosRepo instance;
    private List<Establecimiento> estalecimientos;

    private EstablecimientosRepo(){this.estalecimientos=new ArrayList<>();}

    public static EstablecimientosRepo getInstance() {
        if (instance == null) {
            instance = new EstablecimientosRepo();
        }
        return instance;
    }

    public List<Establecimiento> buscarEstablecimientos(){
        EntityManager em = utils.BDUtils.getEntityManager();
        this.estalecimientos = em.createQuery("select e from Establecimiento e", Establecimiento.class).
                getResultList();
        return this.estalecimientos;
    }

    public Establecimiento buscarEstablecimiento(String nombre, String tipoEstablecimiento, String nombreEntidad){
        EntityManager em = utils.BDUtils.getEntityManager();
        List<Establecimiento> establecimientos = em.createQuery("select e from Establecimiento e WHERE e.nombre=?1 AND e.tipoDeEstablecimiento.tipoEstablecimiento=?2 AND e.entidad.nombre=?3", Establecimiento.class)
                .setParameter(1,nombre)
                .setParameter(2,tipoEstablecimiento)
                .setParameter(3,nombreEntidad).getResultList();
        if (establecimientos.isEmpty()){return null;}
        return establecimientos.get(0);
    }
}
