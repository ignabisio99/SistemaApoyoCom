package domain.entities.servicios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Setter
@Entity
public class Linea extends Entidad{
    @Column
    private TipoDeTransporte tipoDeTransporte;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_codigo", referencedColumnName = "establecimiento_codigo")
    private Establecimiento estacionOrigen;
    /*@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_codigo", referencedColumnName = "establecimiento_codigo")
    private Establecimiento estacionFinal;*/

    public Linea(String nombre,TipoDeTransporte tipoDeTransporte) {
        super();
        this.nombre = nombre;
        this.tipoDeTransporte = tipoDeTransporte;
    }

    public Linea() {

    }
}
