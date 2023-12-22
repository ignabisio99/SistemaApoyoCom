package domain.entities.servicios;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ServicioBase extends Servicio{


    @Column
    private Boolean estaHabilitado;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoDeServicio_codigo", referencedColumnName = "tipoDeServicio_codigo")
    private TipoDeServicio tipoDeServicio;

    public ServicioBase(Establecimiento establecimiento,Boolean x, TipoDeServicio tipoDeServicio){
        this.establecimiento = establecimiento;
        this.estaHabilitado= x;
        this.tipoDeServicio =tipoDeServicio;
    }

    public ServicioBase() {

    }
   public String obtenerDescripcion(){
        return this.tipoDeServicio.getAgrupacion().getAgrupacion()+", "+this.tipoDeServicio.getTipoDeServicio();
   }
}
