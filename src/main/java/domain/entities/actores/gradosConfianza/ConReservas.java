package domain.entities.actores.gradosConfianza;

import domain.entities.actores.miembros.Miembro;

import javax.persistence.Entity;

@Entity
public class ConReservas extends GradoConfianza{
    public ConReservas(Double puntajeNuevo) {
        super(puntajeNuevo);
    }

    public ConReservas() {

    }

    @Override
    public void verificar(Miembro miembro, double puntaje) {
        if (puntaje >3){
            miembro.setGradoConfianza(new ConfiableNivel1(puntaje));
        }
        if (puntaje < 2) {
            miembro.setGradoConfianza(new NoConfiable(puntaje));
        }

    }
}
