package com.futbol.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Min(value = 1, message = "El numero minimo es 1")
    @Max(value = 99, message = "El numero maximo es 99")
    private int numero;

    @NotBlank(message = "La posicion es obligatoria")
    private String posicion;

    /**
     * Lado inverso de @OneToMany en Club.
     * mappedBy="club" le dice a JPA que Club es el dueno de la FK.
     * @JsonIgnore evita recursion infinita si se serializa a JSON.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @JsonIgnore
    private Club club;

    // Constructors
    public Jugador() {}

    public Jugador(String nombre, String apellido, int numero, String posicion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.posicion = posicion;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }

    public Club getClub() { return club; }
    public void setClub(Club club) { this.club = club; }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (#" + numero + ")";
    }
}
