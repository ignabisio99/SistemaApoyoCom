package domain.entities.ranking.Puestos;

import domain.entities.servicios.Entidad;
import domain.entities.servicios.Servicio;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PuestosRankingServicio extends PuestoRanking{
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "servicio_codigo", referencedColumnName = "servicio_codigo")
    private Servicio ocupadoPor;
    public PuestosRankingServicio(int puesto, Servicio ocupadoPor, double motivo){
        super(puesto,motivo);
        this.ocupadoPor=ocupadoPor;
    }

    public PuestosRankingServicio() {

    }
    @Override
    public String ocupadoPor(){
        return this.ocupadoPor.getNombreEstablecimiento();
    }
}
