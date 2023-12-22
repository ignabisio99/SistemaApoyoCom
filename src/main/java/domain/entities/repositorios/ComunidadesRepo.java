package domain.entities.repositorios;

import domain.entities.actores.Comunidad;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.incidentes.IncidenteMiembro;
import handlers.SesionManager;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ComunidadesRepo {
    private static ComunidadesRepo instance;
    private List<Comunidad> comunidades;

    private ComunidadesRepo(){
        this.comunidades=new ArrayList<>();
    }
    public static ComunidadesRepo getInstance() {
        if(instance==null){
            instance=new ComunidadesRepo();
        }
        return instance;
    }
    public List<Comunidad>bucarComunidades(){
        EntityManager em = utils.BDUtils.getEntityManager();
        return em.createQuery("select c from Comunidad c ", Comunidad.class).getResultList();
    }
    public Comunidad buscarComunidadPorId(int codigoComunidad) {
        EntityManager em = utils.BDUtils.getEntityManager();
        List<Comunidad> comunidades = em.createQuery("SELECT c FROM Comunidad c WHERE c.comunidad_codigo = :codigoComunidad", Comunidad.class)
                .setParameter("codigoComunidad", codigoComunidad)
                .getResultList();
        return comunidades.get(0);
    }
    public List<Comunidad> bucarComunidadesMimebro(String idSesion){
       List<Comunidad> comunidades = new ArrayList<>();
        Miembro miembro= SesionManager.get().obtenerMiembro(idSesion);
        if(miembro != null){
            for(MiembroPorComunidad miembroPorComunidad: miembro.getComunidades() ){
                comunidades.add(miembroPorComunidad.getComunidad());
            }
        }
       return comunidades;
    }
    public MiembroPorComunidad obtenerMiembroPorComunidad(int idMiembro,int idComunidad){
        EntityManager em = utils.BDUtils.getEntityManager();
        List<MiembroPorComunidad> miembros = em.createQuery("select m from MiembroPorComunidad m where m.miembro.id = ?1 AND m.comunidad.id=?2", MiembroPorComunidad.class)
                .setParameter(1,idMiembro)
                .setParameter(2,idComunidad)
                .getResultList();
        if(miembros.isEmpty()) {return null;}
        return miembros.stream().findFirst().get();
    }
    public List<Comunidad> buscarComunidadesMiembro(Miembro miembro){
        List<Comunidad> comunidades = new ArrayList<>();
        for(MiembroPorComunidad miembroPorComunidad: miembro.getComunidades() ){
            comunidades.add(miembroPorComunidad.getComunidad());
        }
        return comunidades;
    }

}
