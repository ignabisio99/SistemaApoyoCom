package domain.entities.validador.validaciones;

import domain.entities.validador.Validacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NoEstaEnElTop10000 implements Validacion {
    private final List<String> contrasenas;

    public NoEstaEnElTop10000(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));

        this.contrasenas = new ArrayList<>();

        while(scanner.hasNextLine())
            this.contrasenas.add(scanner.nextLine());
        scanner.close();
    }

    @Override
    public String mensajeDeError() {
        return "La contraseña está entre las más usadas.";
    }

    @Override
    public boolean esSegura(String contrasena) {
        return !this.contrasenas.contains(contrasena.toLowerCase());
    }

}
