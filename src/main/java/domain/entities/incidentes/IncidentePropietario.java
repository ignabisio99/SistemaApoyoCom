package domain.entities.incidentes;

import domain.entities.actores.Propietario;
import domain.entities.actores.miembros.Miembro;
import domain.entities.servicios.Establecimiento;
import domain.entities.servicios.Servicio;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class IncidentePropietario extends Incidente {

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int incidentePropietario_codigo;*/

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_codigo", referencedColumnName = "propietario_codigo")
    private Propietario propietario;

    public IncidentePropietario(String descripcion, Servicio servicio, LocalDateTime fechaRealizacion, Establecimiento establecimiento) {
        super(descripcion, servicio,fechaRealizacion, establecimiento);
    }

    public IncidentePropietario() {

    }

    //todo: ver a quien se notifica esto
    @Override
    public List<Miembro> obtenerContactos() {
        return null;
    }

    @Override
    public void notificar() {

    }
    public String obtenerCreador(){
        return this.propietario.getNombre();
    }
}
