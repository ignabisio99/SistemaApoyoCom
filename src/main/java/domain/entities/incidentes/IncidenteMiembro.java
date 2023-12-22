package domain.entities.incidentes;

import domain.entities.actores.Comunidad;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.servicios.Establecimiento;
import domain.entities.servicios.Servicio;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@Getter
public class IncidenteMiembro extends Incidente{
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int incidenteMiembro_codigo;*/

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_creacion_codigo", referencedColumnName = "miembroPorComunidad_codigo")
    private MiembroPorComunidad miembro;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_cierre_codigo", referencedColumnName = "miembroPorComunidad_codigo")
    private MiembroPorComunidad miembroCierre;

    public IncidenteMiembro(String descripcion, Servicio servicio, LocalDateTime fechaRealizacion, Establecimiento establecimiento, MiembroPorComunidad miembro) {
        super(descripcion,servicio, fechaRealizacion,establecimiento);
        this.miembro = miembro;
        this.comunidad=miembro.getComunidad();
    }

    public IncidenteMiembro() {

    }

    @Override
    public List<Miembro> obtenerContactos() {
        return miembro.getComunidad().getMiembros().stream().map(m->m.getMiembro()).collect(Collectors.toList());
    }

    @Override
    public void notificar() {

    }
    public String obtenerCreador(){
        return this.miembro.getMiembro().getNombre() + ", "+ this.miembro.getMiembro().getApellido();
    }

    public void cerrarIncidente(LocalDateTime fehcaCierre,MiembroPorComunidad miembro){
        this.cerrarIncidente(fehcaCierre);
        this.miembroCierre=miembro;
    }
    public String cerradoPor(){
        return this.miembroCierre.getMiembro().getNombre() + ", " + this.miembroCierre.getMiembro().getApellido();
    }
}
