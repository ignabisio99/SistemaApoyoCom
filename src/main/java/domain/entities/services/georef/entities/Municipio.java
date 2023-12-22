package domain.entities.services.georef.entities;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Municipio extends Localizacion {


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "localizacion_codigo", referencedColumnName = "localizacion_codigo",insertable = false,updatable = false)
    private Provincia provincia;
}
