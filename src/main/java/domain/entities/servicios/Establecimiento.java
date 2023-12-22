package domain.entities.servicios;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import domain.entities.services.georef.entities.Localizacion;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table
@Getter
public class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int establecimiento_codigo;
    @Column
    private String nombre;
    @OneToMany(mappedBy = "establecimiento", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private List<Servicio> servicios;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoDeEstablecimiento_codigo", referencedColumnName = "tipoDeEstablecimiento_codigo")
    private TipoDeEstablecimiento tipoDeEstablecimiento;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "localizacion_codigo", referencedColumnName = "localizacion_codigo")
    private Localizacion localizacion;


    @Getter
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "entidad_codigo", referencedColumnName = "entidad_codigo")
    private Entidad entidad;

    public Establecimiento(String nombre, TipoDeEstablecimiento tipoDeEstablecimiento, Entidad entidad){
        this.servicios = new ArrayList<>();
        this.nombre = nombre;
        this.tipoDeEstablecimiento= tipoDeEstablecimiento;
        this.entidad = entidad;
    }

    public Establecimiento() {

    }
    public Establecimiento(String nombre){
        this.servicios = new ArrayList<>();
        this.nombre = nombre;
    }

    public void agregarServicio(Servicio servicio){
        this.servicios.add(servicio);
    }
}
