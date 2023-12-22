package domain.entities.actores;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.incidentes.Incidente;
import javassist.expr.NewArray;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
public class Comunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comunidad_codigo;

    @OneToMany(mappedBy = "comunidad", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @Getter
    @JsonIgnore
    private List<MiembroPorComunidad> miembros;
    @Column
    private String nombre;
    @Column
    private String objetivo;
    @OneToMany(mappedBy = "comunidad", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JsonIgnore
    private List<Incidente> incidentes;

    @Column
    private Double puntaje;

    public Comunidad(){
        miembros= new ArrayList<>();
    }
    public Comunidad(String nombre,String objetivo){
        miembros= new ArrayList<>();
        this.nombre=nombre;
        this.objetivo=objetivo;

    }


}
