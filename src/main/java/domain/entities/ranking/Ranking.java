package domain.entities.ranking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import domain.entities.ranking.Puestos.PuestoRanking;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//todo: cuando nos expliquen persistencia no relacional, meterlo en un txt

@Entity
@Table
@Getter
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ranking_codigo;


    @OneToMany(mappedBy = "ranking", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<PuestoRanking> puestosRanking;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "tipoRanking_codigo", referencedColumnName = "tipoRanking_codigo")
    private TipoRanking tipoRanking;
    @Column
    private LocalDateTime fecha;

    public Ranking(List<PuestoRanking> puestosRanking, TipoRanking tipoRanking, LocalDateTime fecha){
        this.puestosRanking = puestosRanking;
        this.tipoRanking = tipoRanking;
        this.fecha = fecha;
    }

    public Ranking() {

    }

    public PuestoRanking obtenerPrimerLugar(){
        return this.puestosRanking.stream().filter(x -> x.getPuesto() == 1).findFirst().get();
    }

    public PuestoRanking obtenerPuesto(int i){
        return this.puestosRanking.stream().filter(x -> x.getPuesto() == i).findFirst().get();
    }


}
