package domain.entities.servicios;

import domain.entities.services.georef.entities.Localizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Setter
@Getter
public class Organizacion extends Entidad {
    @Column
    private String tipoOrg;


    public Organizacion(String nombre, String tipoOrg) {
        this.nombre = nombre;
        this.tipoOrg = tipoOrg;
    }

    public Organizacion() {

    }
}
