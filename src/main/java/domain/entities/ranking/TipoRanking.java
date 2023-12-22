package domain.entities.ranking;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table
@Getter
public class TipoRanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tipoRanking_codigo;
    @Column
    private String nombre;
    public TipoRanking(String nom){this.nombre =nom; }

    public TipoRanking() {

    }
}
