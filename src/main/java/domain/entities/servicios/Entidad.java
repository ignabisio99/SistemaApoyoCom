package domain.entities.servicios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.entities.actores.OrganismoDeControl;
import domain.entities.services.georef.entities.Localizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table
@Setter
public abstract class Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int entidad_codigo;

    @OneToOne(mappedBy = "entidad")
    private OrganismoDeControl organismoDeControl;

    @Column
    protected String nombre;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "localizacion_codigo", referencedColumnName = "localizacion_codigo")
    protected Localizacion localizacion;
    @OneToMany(mappedBy = "entidad", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JsonIgnore
    private List<Establecimiento> sucursales;

    public  Entidad(){this.sucursales = new ArrayList<>();};
    public TipoDeTransporte getTipoDeTransporte() {
        return null;
    }

    public void agregarSucursal(Establecimiento nuevo){
        this.sucursales.add(nuevo);
    }
}
