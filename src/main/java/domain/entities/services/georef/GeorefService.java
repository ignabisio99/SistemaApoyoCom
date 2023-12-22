package domain.entities.services.georef;

import domain.entities.services.georef.entities.ListadoDeDepartamentos;
import domain.entities.services.georef.entities.ListadoDeMunicipios;
import domain.entities.services.georef.entities.ListadoDeProvincias;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeorefService {
    @GET("provincias")
    Call<ListadoDeProvincias> provincias();

    @GET("provincias")
    Call<ListadoDeProvincias> provincias(@Query("campos") String campos);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia,@Query("campos") String campos);

    @GET("departamentos")
    Call<ListadoDeDepartamentos> departamentos(@Query("provincia") int idProvincia);

    @GET("departamentos")
    Call<ListadoDeDepartamentos> departamentos(@Query("provincia") int idProvincia,@Query("campos") String campos);

}
