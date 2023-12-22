package domain.entities.incidentes;

import domain.entities.actores.Comunidad;
import domain.entities.actores.miembros.Miembro;
import domain.entities.servicios.Establecimiento;
import domain.entities.servicios.Servicio;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table
public abstract class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int incidente_codigo;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "establecimiento_codigo", referencedColumnName = "establecimiento_codigo")
    private Establecimiento establecimiento;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "servicio_codigo", referencedColumnName = "servicio_codigo")
    private Servicio servicio;
    @Column
    private String descripcion;
    @Column
    private Boolean resuelto;
    @Column
    private LocalDateTime fechaRealizacion;
    @Column
    private LocalDateTime fechaCierre;
    @ManyToOne
    @JoinColumn(name = "comunidad_codigo", referencedColumnName = "comunidad_codigo")
    protected Comunidad comunidad;


    public Incidente(String descripcion, Servicio servicio, LocalDateTime fechaRealizacion, Establecimiento establecimiento) {
        this.descripcion = descripcion;
        this.servicio = servicio;
        this.fechaRealizacion = fechaRealizacion;
        this.establecimiento = establecimiento;
        this.resuelto = Boolean.FALSE;
    }

    public Incidente() {

    }

    public boolean esRepetidoEnRango(Incidente otroIncidente, int horasRango) {
        long horasEntreIncidentes = Math.abs(ChronoUnit.HOURS.between(this.getFechaRealizacion(), otroIncidente.getFechaRealizacion()));

        return this.getServicio().equals(otroIncidente.getServicio()) && horasEntreIncidentes <= horasRango;
    }

    public void cerrarIncidente(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
        this.resuelto=Boolean.TRUE;

    }

    public abstract List<Miembro> obtenerContactos();

    public abstract void notificar();

    public String obtenerCreador(){return "";}
    public String cerradoPor(){return "";}

}
