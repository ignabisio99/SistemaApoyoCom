package domain.entities.servicios;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class ServicioCompuesto extends Servicio {



    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "agrupacionServicio_codigo", referencedColumnName = "agrupacionServicio_codigo")
    private AgrupacionServicio agrupacionServicio;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "servicio_codigo", referencedColumnName = "servicio_codigo")
    private List<ServicioBase> servicios;

    public ServicioCompuesto(Establecimiento establecimiento,AgrupacionServicio agrupacionServicio){
        this.establecimiento = establecimiento;
        this.agrupacionServicio =  agrupacionServicio;
    }

    public ServicioCompuesto() {

    }
    public String obtenerDescripcion(){
        return this.agrupacionServicio.getAgrupacion();
    }
}
