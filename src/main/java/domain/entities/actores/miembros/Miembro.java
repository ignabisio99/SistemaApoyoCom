package domain.entities.actores.miembros;

import domain.entities.actores.Usuario;
import domain.entities.actores.gradosConfianza.ConfiableNivel1;
import domain.entities.actores.gradosConfianza.GradoConfianza;
import domain.entities.notificaciones.HorarioNotificacion;
import domain.entities.notificaciones.MedioNotificacion;
import domain.entities.notificaciones.Notificacion;
import domain.entities.services.georef.entities.Localizacion;
import domain.entities.servicios.Entidad;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.TransactionScoped;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Miembros")
@Getter
@Setter
public class Miembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int miembro_codigo;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String email;

    @Column
    private String telefono;

    @OneToMany(mappedBy = "miembro", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<MiembroPorComunidad> comunidades;

    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_codigo", referencedColumnName = "usuario_codigo")
    private Usuario usuario;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<Entidad> entidadesDeInteres;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "localizacion_codigo", referencedColumnName = "localizacion_codigo")
    private Localizacion localizacion;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "medio_notificacion_codigo", referencedColumnName = "medio_notificacion_codigo")
    private MedioNotificacion medioNotificacion;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<HorarioNotificacion> horarios;


    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "gradoConfianza_codigo", referencedColumnName = "gradoConfianza_codigo")
    private GradoConfianza gradoConfianza;

    public Miembro(String nombre, String apellido, String email, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.gradoConfianza = new ConfiableNivel1(5.0);
    }

    public Miembro(Integer id){
        this.miembro_codigo = id;
    }
    public Miembro() {

    }

    public boolean tieneRangoHorario(LocalDateTime hora) {
        return this.horarios.stream().anyMatch(r -> r.getHorario().equals(hora));
    }

    public void notificar(Notificacion notificacion){
        this.getMedioNotificacion().notificar(notificacion,this);
    }
}
