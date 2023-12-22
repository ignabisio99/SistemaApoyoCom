package domain.entities.servicios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class AgrupacionServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int agrupacionServicio_codigo;

    @Column
    private String agrupacion;

    public AgrupacionServicio(String agrupacion) {
        this.agrupacion = agrupacion;
    }

    public AgrupacionServicio() {

    }
}
