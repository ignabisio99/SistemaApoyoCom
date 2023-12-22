package domain.entities.services.georef.entities;

import java.util.List;

public class ListadoDeDepartamentos {
    public int cantidad;
    public int inicio;
    public int total;
    public List<Departamento> departamentos;

    private class Parametro{
        public List<String> campos;
        public int max;
        public List<String> provincia;

    }
}
