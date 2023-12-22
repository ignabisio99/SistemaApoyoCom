package domain.entities.repositorios;

import domain.entities.actores.Comunidad;
import domain.entities.actores.miembros.Miembro;
import domain.entities.incidentes.Incidente;
import domain.entities.incidentes.IncidenteMiembro;
import domain.entities.incidentes.IncidentePropietario;
import domain.entities.servicios.Establecimiento;
import lombok.Getter;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class IncidentesRepo {
    private static IncidentesRepo instance;
    private List<Incidente> incidentes;


    private IncidentesRepo() {
        this.incidentes = new ArrayList<>();
    }

        public static IncidentesRepo getInstance () {
        if (instance == null) {
            instance = new IncidentesRepo();
        }
        return instance;
    }

    public void agregarIncidente(Incidente incidente){this.incidentes.add(incidente);}

    public List<Incidente> buscarIncidentesResueltosSemana(LocalDateTime finSemana){
        LocalDateTime inicioSemana = finSemana.minusWeeks(1);
        return this.incidentes.stream().filter(
                incidente -> incidente.getResuelto().equals(true) &&
                        incidente.getFechaCierre().isBefore(finSemana) &&
                        incidente.getFechaCierre().isAfter(inicioSemana)).collect(Collectors.toList());

    }

    public List<Incidente> buscarIncidentesSemana(LocalDateTime finSemana){
        LocalDateTime inicioSemana = finSemana.minusWeeks(1);
        return this.incidentes.stream().filter(
                incidente ->
                        incidente.getFechaRealizacion().isBefore(finSemana) &&
                        incidente.getFechaRealizacion().isAfter(inicioSemana)).collect(Collectors.toList());
    }
    public void buscarIncidentes() {
        EntityManager em = utils.BDUtils.getEntityManager();
        this.incidentes=em.createQuery("select i from IncidenteMiembro i ", Incidente.class).getResultList();
        return;
    }
    public IncidenteMiembro buscarIncidente(Integer idIncidente) {
        EntityManager em = utils.BDUtils.getEntityManager();
        List<IncidenteMiembro> incidentes = em.createQuery("select i from IncidenteMiembro i WHERE i.id=?1", IncidenteMiembro.class)
                .setParameter(1,idIncidente).getResultList();
        if (incidentes.isEmpty()){return null;}
        return incidentes.get(0);
    }

    public List<IncidenteMiembro> buscarIncidentesPorEstablecimiento(Establecimiento establecimiento){
        EntityManager em = utils.BDUtils.getEntityManager();
        List<IncidenteMiembro> incidentesEstablecimiento = em.createQuery("select i from IncidenteMiembro i WHERE i.establecimiento=?1", IncidenteMiembro.class)
                .setParameter(1,establecimiento).getResultList();
        return incidentesEstablecimiento;

    }

    public List<IncidenteMiembro> buscarIncidentesPorEstablecimientoResuelto(Establecimiento establecimiento){
        List<IncidenteMiembro> incidentesEstablecimiento = buscarIncidentesPorEstablecimiento(establecimiento);
        if(incidentesEstablecimiento!=null){
        return incidentesEstablecimiento.stream().filter(i -> !i.getResuelto()).collect(Collectors.toList());
    }else return null;
    }
    public List<IncidentePropietario> buscarIncidentesPropetarios(){
        EntityManager em = utils.BDUtils.getEntityManager();
        List<IncidentePropietario> incidentePropietarios = em.createQuery("select i from IncidentePropietario i", IncidentePropietario.class).getResultList();
        return incidentePropietarios;
    }
    public List<Incidente> obtenerIncidentesParaMiembro(Miembro miembro){
        List<Comunidad> comunidades= ComunidadesRepo.getInstance().buscarComunidadesMiembro(miembro);

        List<Incidente> incidetes = comunidades.stream()
                .flatMap(comunidad -> comunidad.getIncidentes().stream())
                .collect(Collectors.toList());
        List<IncidentePropietario> incidentePropietarios= this.buscarIncidentesPropetarios();

        incidetes.addAll(incidentePropietarios);
        return  incidetes;

    }
}
