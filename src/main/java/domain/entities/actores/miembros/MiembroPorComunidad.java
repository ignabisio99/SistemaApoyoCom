package domain.entities.actores.miembros;

import domain.entities.actores.Comunidad;
import domain.entities.actores.miembros.Miembro;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
public class MiembroPorComunidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int miembroPorComunidad_codigo;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_codigo", referencedColumnName = "miembro_codigo")
    private Miembro miembro;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "comunidad_codigo", referencedColumnName = "comunidad_codigo")
    private Comunidad comunidad;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoDeMiembro_codigo", referencedColumnName = "tipoDeMiembro_codigo")
    @Setter
    private TipoDeMiembro tipoDeMiembro;

    @Column
    private Boolean esAdmin;

    public MiembroPorComunidad(Miembro miembro,Comunidad comunidad) {
        this.miembro = miembro;
        this.comunidad = comunidad;
        this.esAdmin=false;

        if(!comunidad.getMiembros().contains(this)){
            comunidad.getMiembros().add(this);
        }
    }
    public MiembroPorComunidad(Miembro miembro,Comunidad comunidad,Boolean admin) {
        this.miembro = miembro;
        this.comunidad = comunidad;
        this.esAdmin=admin;

        if(!comunidad.getMiembros().contains(this)){
            comunidad.getMiembros().add(this);
        }
    }

    public MiembroPorComunidad() {

    }
/* public Boolean esAfectado(){

    }

    */
}
