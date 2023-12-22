package domain.entities.actores.miembros;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table
@Getter
public class TipoDeMiembro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tipoDeMiembro_codigo;
    @Column
    private String tipoDeMiembro;

    public TipoDeMiembro(){

    }
    public TipoDeMiembro(String tipo){
        this.tipoDeMiembro=tipo;
    }
}
