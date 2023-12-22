package domain.entities.services.georef.entities;

import lombok.Getter;

import java.util.List;

public class ListadoDeProvincias {
    public int cantidad;
    public int inicio;
    public int total;
    @Getter
    public List<Provincia> provincias;
    public Parametro parametros;

    private class Parametro{
        public List<String> campos;
    }
}
