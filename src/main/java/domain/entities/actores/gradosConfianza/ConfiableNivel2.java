package domain.entities.actores.gradosConfianza;

import domain.entities.actores.miembros.Miembro;

import javax.persistence.Entity;

@Entity
public class ConfiableNivel2 extends GradoConfianza{
    public ConfiableNivel2(Double puntajeNuevo) {
        super(puntajeNuevo);
    }

    public ConfiableNivel2() {

    }

    @Override
    public void verificar(Miembro miembro, double puntaje) {
        if (puntaje < 5){
            miembro.setGradoConfianza(new ConfiableNivel1(puntaje));
        }
    }
}
