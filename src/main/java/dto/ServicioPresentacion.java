package dto;

import domain.entities.servicios.Servicio;
import lombok.Getter;

@Getter
public class ServicioPresentacion {
    String descripcion;

    ServicioPresentacion(Servicio servicio){
        this.descripcion=servicio.obtenerDescripcion();
    }
}
