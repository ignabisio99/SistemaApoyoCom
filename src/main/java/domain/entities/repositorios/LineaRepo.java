package domain.entities.repositorios;


import domain.entities.servicios.Linea;
import domain.entities.servicios.Organizacion;
import domain.entities.servicios.ServicioPublico;
import domain.entities.servicios.TipoDeTransporte;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LineaRepo {
    private static LineaRepo instance;
    private List<Linea> lineas;

    private LineaRepo() {
        this.lineas = new ArrayList<>();
    }

    public static LineaRepo getInstance() {
        if (instance == null) {
            instance = new LineaRepo();
        }
        return instance;
    }

    public Linea buscarMemoria(String nombre, String tipoTransporte) {
        Optional<Linea> lineaEncontrada = lineas.stream()
                .filter(linea -> linea.getNombre().equals(nombre)
                && linea.getTipoDeTransporte().toString().equals(tipoTransporte)).findFirst();
        return lineaEncontrada.orElse(null);
    }

    public Linea buscar(String nombre, String tipoDeTransporte){
        EntityManager em = utils.BDUtils.getEntityManager();
        Optional<Linea> lineaEncontrada = em.createQuery("SELECT l from Linea l WHERE l.nombre = ?1 and l.tipoDeTransporte = ?2",Linea.class)
                .setParameter(1,nombre)
                .setParameter(2, TipoDeTransporte.valueOf(tipoDeTransporte))
                .getResultList().stream().findFirst();
        return lineaEncontrada.orElse(null);
    }


    public void agregar(Linea linea){
        this.lineas.add(linea);
    }
}
