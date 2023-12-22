package dto;

import domain.entities.actores.Comunidad;
import domain.entities.actores.miembros.MiembroPorComunidad;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Getter
public class ComunidadPresentacion {
    private String nombre;
    private String objetivo;
    private List<MiembroPresentacion> miembros;
    private List<MiembroPresentacion> administradores;
    private Boolean esAdmin;

    public ComunidadPresentacion(Comunidad comunidad, MiembroPorComunidad miembro){
        this.miembros=new ArrayList<>();
        for(MiembroPorComunidad miembroPorComunidad: comunidad.getMiembros()){
            MiembroPresentacion unMiembro = new MiembroPresentacion(miembroPorComunidad);
            this.miembros.add(unMiembro);
        }
        this.administradores=this.miembros.stream().filter(m->m.getEsAdmin()).collect(Collectors.toList());

        this.nombre= comunidad.getNombre();
        this.objetivo= comunidad.getObjetivo();
        this.esAdmin=miembro.getEsAdmin();
    }
}
