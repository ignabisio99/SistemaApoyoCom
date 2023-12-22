package handlers;

import domain.entities.repositorios.EstablecimientosRepo;
import domain.entities.servicios.Establecimiento;
import dto.EstablecimientoPresentacion;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GetEstablecimientosHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {

        List<EstablecimientoPresentacion> establecimientos = new ArrayList<>();

        for(Establecimiento establecimiento:EstablecimientosRepo.getInstance().buscarEstablecimientos()){
            EstablecimientoPresentacion unEstablecimiento = new EstablecimientoPresentacion(establecimiento);
            establecimientos.add(unEstablecimiento);
        }

        context.json(establecimientos);
    }
}
