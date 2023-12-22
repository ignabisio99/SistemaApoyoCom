package domain.entities.actores.gradosConfianza;

import domain.entities.actores.miembros.Miembro;

import javax.persistence.*;

@Table
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class GradoConfianza {

    @Column
    private double puntaje;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gradoConfianza_codigo;

    public GradoConfianza(Double puntajeNuevo){
        this.puntaje = puntajeNuevo;
    }

    public GradoConfianza() {

    }

    public void actualizar(Miembro miembro, double puntajeNuevo){
        puntaje += puntajeNuevo;
        verificar(miembro, puntaje);
    }

    public abstract void verificar(Miembro miembro, double puntaje);


    public void setGradoConfianza_codigo(int gradoConfianzaCodigo) {
        this.gradoConfianza_codigo = gradoConfianzaCodigo;
    }


}
