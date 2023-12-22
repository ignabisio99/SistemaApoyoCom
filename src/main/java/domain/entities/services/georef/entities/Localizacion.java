package domain.entities.services.georef.entities;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table
public abstract class Localizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int localizacion_codigo;
    @Column
    protected String nombre;

}
