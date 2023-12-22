package domain.entities.servicios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class TipoDeEstablecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tipoDeEstablecimiento_codigo;

    @Column
    private String tipoEstablecimiento;

    public TipoDeEstablecimiento(String tipo){
        this.tipoEstablecimiento = tipo;
    }

    public TipoDeEstablecimiento() {

    }
}

