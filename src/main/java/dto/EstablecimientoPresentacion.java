package dto;

import domain.entities.servicios.Establecimiento;
import domain.entities.servicios.Servicio;
import lombok.Getter;
import java.util.List;
import java.util.ArrayList;
@Getter
public class EstablecimientoPresentacion {
    String descripcion;

    List<ServicioPresentacion> servicios;

    public EstablecimientoPresentacion(Establecimiento establecimiento){
        this.descripcion=establecimiento.getNombre() + ", " + establecimiento.getTipoDeEstablecimiento().getTipoEstablecimiento() +" (" + establecimiento.getEntidad().getNombre()+")";
        this.servicios=new ArrayList<>();
        for(Servicio servicio: establecimiento.getServicios()){
            ServicioPresentacion unServicio = new ServicioPresentacion(servicio);
            this.servicios.add(unServicio);
        }
    }

}
