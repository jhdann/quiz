package com.futbol.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "asociaciones")
public class Asociacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El país es obligatorio")
    private String pais;

    @NotBlank(message = "El presidente es obligatorio")
    private String presidente;

    // Constructors
    public Asociacion() {}

    public Asociacion(String nombre, String pais, String presidente) {
        this.nombre = nombre;
        this.pais = pais;
        this.presidente = presidente;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getPresidente() { return presidente; }
    public void setPresidente(String presidente) { this.presidente = presidente; }

    @Override
    public String toString() {
        return nombre + " (" + pais + ")";
    }
}
