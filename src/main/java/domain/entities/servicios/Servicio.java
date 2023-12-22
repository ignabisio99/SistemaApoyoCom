package domain.entities.servicios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public abstract class Servicio{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int servicio_codigo;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_codigo", referencedColumnName = "establecimiento_codigo")
    protected Establecimiento establecimiento;


    public String getNombreEstablecimiento () {
        return establecimiento.getNombre();
    }

    public Servicio(){}

    public String obtenerDescripcion(){return "";}
}
