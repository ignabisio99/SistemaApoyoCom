package dto;

import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import lombok.Getter;

@Getter
public class MiembroPresentacion {
    private int id_miembro;
    private int comunidad_codigo;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String tipoDeMiembro;
    private Boolean esObservador;
    private Boolean esAdmin;
    private String comunidad;

    public MiembroPresentacion(MiembroPorComunidad miembro){
        this.id_miembro=miembro.getMiembro().getMiembro_codigo();
        this.nombre=miembro.getMiembro().getNombre();
        this.apellido=miembro.getMiembro().getApellido();
        this.email=miembro.getMiembro().getEmail();
        this.telefono=miembro.getMiembro().getTelefono();
        this.tipoDeMiembro=miembro.getTipoDeMiembro().getTipoDeMiembro();
        this.esAdmin=miembro.getEsAdmin();
        if(this.tipoDeMiembro.equalsIgnoreCase("observador")){
            this.esObservador=true;
        }
        else{
            this.esObservador=false;
        }
        this.comunidad=miembro.getComunidad().getNombre();
        this.comunidad_codigo=miembro.getComunidad().getComunidad_codigo();
    }
    public MiembroPresentacion(Miembro miembro){
        this.id_miembro= miembro.getMiembro_codigo();
        this.nombre=miembro.getNombre();
        this.apellido=miembro.getApellido();
        this.email=miembro.getEmail();
        this.telefono=miembro.getTelefono();
    }
}
