package domain.entities.actores;

import domain.entities.servicios.Entidad;
import domain.entities.servicios.TipoDeServicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class OrganismoDeControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organismoDeControl_codigo;

    @Column
    private String nombre;

    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "organismoDeControl_codigo", referencedColumnName = "organismoDeControl_codigo")
    private Entidad entidad;

    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_codigo", referencedColumnName = "usuario_codigo")
    private Usuario usuario;

    public OrganismoDeControl(String nombre, Entidad entidad) {
        this.nombre = nombre;
        this.entidad = entidad;
    }

    public OrganismoDeControl() {

    }
}
