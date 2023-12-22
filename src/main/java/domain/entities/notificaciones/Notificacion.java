package domain.entities.notificaciones;


import domain.entities.actores.miembros.Miembro;
import domain.entities.incidentes.Incidente;
import domain.entities.incidentes.IncidenteMiembro;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Entity
@Table
@Getter
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificacion_codigo;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "incidente_codigo", referencedColumnName = "incidente_codigo")
    private Incidente incidente;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<Miembro> miembros;

    public Notificacion(Incidente incidente){
        this.incidente = incidente;
        this.miembros = incidente.obtenerContactos();
    }
    public Notificacion(){

    }

    public void notificar(LocalDateTime hora){
        List<Miembro> miembrosANotificar = miembros.stream().filter(m -> m.tieneRangoHorario(hora)).collect(Collectors.toList());

        miembrosANotificar.forEach(m->m.notificar(this));
        miembros.removeAll(miembrosANotificar);
    }

    public Boolean estaVacia(){
        return this.miembros.isEmpty();
    }
}
