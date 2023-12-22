package handlers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PatchTipoMiembroResponse {

    private Integer miembroId;
    private String comunidadId;
    private String nuevoTipo;

    @JsonCreator
    public PatchTipoMiembroResponse(
            @JsonProperty("miembroId") Integer miembroId,
            @JsonProperty("comunidadId") String comunidadId,
            @JsonProperty("esObservador") String esObservador) {
        this.miembroId = miembroId;
        this.comunidadId = comunidadId;
        this.nuevoTipo = esObservador;
    }

    @Override
    public String toString() {
        return "PatchTipoMiembroResponse{" +
                "miembroId=" + miembroId +
                ", comunidadId='" + comunidadId + '\'' +
                ", esObservador=" + nuevoTipo +
                '}';
    }
}
