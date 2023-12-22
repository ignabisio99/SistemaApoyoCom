package domain.entities.servicios;

import lombok.Getter;
import lombok.Setter;
import org.intellij.lang.annotations.JdkConstants;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class TipoDeServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tipoDeServicio_codigo;

    @Column
    private String tipoDeServicio;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "agrupacionServicio_codigo", referencedColumnName = "agrupacionServicio_codigo")
    private AgrupacionServicio agrupacion;

    public TipoDeServicio(String tipoDeServicio, AgrupacionServicio agrupacion) {
        this.tipoDeServicio = tipoDeServicio;
        this.agrupacion = agrupacion;
    }

    public TipoDeServicio() {

    }
}
