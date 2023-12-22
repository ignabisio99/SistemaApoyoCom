package domain.entities.repositorios;

import domain.entities.servicios.ServicioBase;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServiciosRepo {
    private static ServiciosRepo instance;

    private  ServiciosRepo(){
    }

    public static ServiciosRepo getInstance() {
        if(instance == null){
            instance=new ServiciosRepo();
        }
        return instance;
    }

    public ServicioBase buscarServicioBase(int idEstablecimiento, String descripcionServicio){
        EntityManager em = utils.BDUtils.getEntityManager();
        List<ServicioBase> servicios = em.createQuery("SELECT s FROM ServicioBase s WHERE s.establecimiento.establecimiento_codigo =?1",ServicioBase.class)
                .setParameter(1,idEstablecimiento).getResultList();
        ServicioBase servicioBuscado = servicios.stream().filter(s-> Objects.equals(s.obtenerDescripcion(), descripcionServicio)).collect(Collectors.toList()).get(0);
        return servicioBuscado;
    }
}
