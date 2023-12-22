package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.MiembroPorComunidad;
import domain.entities.incidentes.Incidente;
import domain.entities.incidentes.IncidenteMiembro;
import domain.entities.repositorios.ComunidadesRepo;
import domain.entities.repositorios.EstablecimientosRepo;
import domain.entities.repositorios.ServiciosRepo;
import domain.entities.servicios.Establecimiento;
import domain.entities.servicios.Servicio;
import handlers.SesionManager;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class IncidentePresentacion {
    private Integer codigo;
    private Integer idComunidad;
    private  String creador;
    private String establecimiento;
    private  String servicio;
    private  String estado;
    private  String fechaCreacion;
    private  String descripcion;
    private  String fechaCierre;
    private String cerradoPor;

    public IncidentePresentacion(Incidente incidente){
        this.codigo=incidente.getIncidente_codigo();
        this.idComunidad=incidente.getComunidad().getComunidad_codigo();
        this.creador= incidente.obtenerCreador();
        this.establecimiento= incidente.getEstablecimiento().getNombre();
        this.servicio= incidente.getServicio().obtenerDescripcion();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yy");
        if(incidente.getResuelto()){
            this.estado="Resuelto/Cerrado";
            this.fechaCierre=incidente.getFechaCierre().format(formatter);
            this.cerradoPor=incidente.cerradoPor();
        }else{
            this.estado="Abierto";
        }
        this.fechaCreacion=incidente.getFechaRealizacion().format(formatter);
        this.descripcion= incidente.getDescripcion();
    }
    public IncidentePresentacion(){}
    @JsonCreator
    public IncidentePresentacion(@JsonProperty("servicio") String servicio,
                                 @JsonProperty("establecimiento") String establecimiento,
                                 @JsonProperty("descripcion") String descripcion,
                                 @JsonProperty("fechaCreacion") String fechaCreacion){
        this.servicio = servicio;
        this.establecimiento = establecimiento;
        this.descripcion = descripcion;
        this.fechaCreacion= fechaCreacion;
    }
    public IncidenteMiembro generarIncidente(String idComunidad, String idSesion){
        String descripcion = this.descripcion;
        LocalDateTime fechaCreacion=  LocalDateTime.parse(this.fechaCreacion, DateTimeFormatter.ISO_DATE_TIME).minusHours(3);
        Miembro miembro = SesionManager.get().obtenerMiembro(idSesion);
        MiembroPorComunidad creador = ComunidadesRepo.getInstance().obtenerMiembroPorComunidad(miembro.getMiembro_codigo(), Integer.parseInt(idComunidad));
        Establecimiento establecimiento = obtenerEstablecimiento(this.establecimiento);
        Servicio servicio = obtenerServicio(establecimiento, this.servicio);

        IncidenteMiembro incidenteMiembro = new IncidenteMiembro(descripcion,servicio,fechaCreacion,establecimiento,creador);

        return incidenteMiembro;

    }

    public Establecimiento obtenerEstablecimiento(String descripcionEstablecimiento) {
        // Definir el patrón de la expresión regular
        Pattern pattern = Pattern.compile("([^,]+),\\s([^\\s]+)\\s\\(([^)]+)\\)");

        // Crear un objeto Matcher para el input
        Matcher matcher = pattern.matcher(descripcionEstablecimiento);

        // Obtener los grupos de captura
        String nombre = null;
        String tipoEstablecimiento = null;
        String nombreEntidad = null;

        // Verificar si hay coincidencias
        if (matcher.find()) {
            nombre = matcher.group(1);
            tipoEstablecimiento = matcher.group(2);
            nombreEntidad = matcher.group(3);
        }

        // Buscar el establecimiento
        return EstablecimientosRepo.getInstance().buscarEstablecimiento(nombre,tipoEstablecimiento,nombreEntidad);
    }

    private Servicio obtenerServicio(Establecimiento establecimiento, String descripcionServicio){
       return ServiciosRepo.getInstance().buscarServicioBase(establecimiento.getEstablecimiento_codigo(),descripcionServicio);
    }
}
