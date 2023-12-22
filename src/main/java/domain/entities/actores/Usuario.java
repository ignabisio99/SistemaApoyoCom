package domain.entities.actores;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table
@Getter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usuario_codigo;

    @Column
    private String nombreUsuario;
    @Column
    private String contrasenia;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario(){}
    public Usuario(String nombre, String password, Rol rol1) {
        this.nombreUsuario = nombre;
        this.contrasenia = password;
        this.rol = rol1;
    }
}
