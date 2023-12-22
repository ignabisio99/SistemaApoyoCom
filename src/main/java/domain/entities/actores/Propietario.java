package domain.entities.actores;

import domain.entities.servicios.ServicioPublico;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Propietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int propietario_codigo;

    @Column
    private String nombre;

    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_codigo", referencedColumnName = "propietario_codigo")
    private ServicioPublico servicioPublico;

    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_codigo", referencedColumnName = "usuario_codigo")
    private Usuario usuario;

    public Propietario(String nombre, ServicioPublico servicioPublico) {
        this.nombre = nombre;
        this.servicioPublico = servicioPublico;
    }

    public Propietario() {

    }
}
