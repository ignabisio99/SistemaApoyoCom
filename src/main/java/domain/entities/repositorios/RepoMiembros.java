package domain.entities.repositorios;

import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.TipoDeMiembro;
import dto.LoginRequest;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;

public class RepoMiembros {
    private static RepoMiembros instance;

    private RepoMiembros() {

    }

    public static RepoMiembros getInstance() {
        if (instance == null) {
            instance = new RepoMiembros();
        }
        return instance;
    }

    public List<Miembro> buscarMiembros(){
        EntityManager em = utils.BDUtils.getEntityManager();
        List<Miembro> miembros = em.createQuery("select m from Miembro m", Miembro.class)
                .getResultList();;
        return miembros;
    }

    public Miembro buscarMiembro(int idBuscado){
        EntityManager em = utils.BDUtils.getEntityManager();
        List<Miembro> miembros = em.createQuery("select m from Miembro m where m.miembro_codigo = ?1", Miembro.class)
                .setParameter(1,idBuscado).getResultList();
        if(miembros.isEmpty()) {return new Miembro(-1);}
        return miembros.stream().findFirst().get();
    }

    public Miembro buscarMiembroUsuario(LoginRequest loginRequest) {
        EntityManager em = utils.BDUtils.getEntityManager();
        List<Miembro> miembros = em.createQuery("select m from Miembro m join Usuario u on u.usuario_codigo = m.usuario.usuario_codigo " +
                        "where u.nombreUsuario = ?1 and u.contrasenia = ?2", Miembro.class)
                .setParameter(1,loginRequest.getEmail())
                .setParameter(2,loginRequest.getPassword())
                .getResultList();
        if(miembros.isEmpty()) {return new Miembro(-1);}
        return miembros.stream().findFirst().get();
    }
    public Miembro buscarMiembroUsuarioGoogle(LoginRequest loginRequest) {
        EntityManager em = utils.BDUtils.getEntityManager();
        List<Miembro> miembros = em.createQuery("select m from Miembro m join Usuario u on u.usuario_codigo = m.usuario.usuario_codigo " +
                        "where u.nombreUsuario = ?1", Miembro.class)
                .setParameter(1,loginRequest.getEmail())
                .getResultList();
        if(miembros.isEmpty()) {return new Miembro(-1);}
        return miembros.stream().findFirst().get();
    }

    public TipoDeMiembro buscarTipoDeMiembro(String tipo){
        EntityManager em = utils.BDUtils.getEntityManager();
        List<TipoDeMiembro> tipoDeMiembros = em.createQuery("SELECT t FROM TipoDeMiembro t WHERE t.tipoDeMiembro=?1", TipoDeMiembro.class)
                .setParameter(1,tipo)
                .getResultList();
        return tipoDeMiembros.get(0);
    }
}
