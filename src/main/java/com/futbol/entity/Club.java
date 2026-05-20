package com.futbol.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clubes")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del club es obligatorio")
    private String nombre;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    private int anioFundacion;

    // ==================== RELACIONES JPA ====================

    /**
     * @OneToOne - Un Club tiene UN Entrenador.
     * FK: entrenador_id en tabla clubes.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador;

    /**
     * @OneToMany - Un Club tiene MUCHOS Jugadores.
     * mappedBy apunta al campo "club" en Jugador.
     * cascade ALL: al guardar/eliminar club se propaga a jugadores.
     */
    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jugador> jugadores = new ArrayList<>();

    /**
     * @ManyToOne - Muchos Clubes pertenecen a UNA Asociacion.
     * FK: asociacion_id en tabla clubes.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asociacion_id")
    private Asociacion asociacion;

    /**
     * @ManyToMany - Un Club participa en MUCHAS Competiciones.
     * Tabla intermedia: clubes_competiciones.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "clubes_competiciones",
        joinColumns = @JoinColumn(name = "club_id"),
        inverseJoinColumns = @JoinColumn(name = "competicion_id")
    )
    private List<Competicion> competiciones = new ArrayList<>();

    // Constructors
    public Club() {}

    public Club(String nombre, String ciudad, int anioFundacion) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.anioFundacion = anioFundacion;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public int getAnioFundacion() { return anioFundacion; }
    public void setAnioFundacion(int anioFundacion) { this.anioFundacion = anioFundacion; }

    public Entrenador getEntrenador() { return entrenador; }
    public void setEntrenador(Entrenador entrenador) { this.entrenador = entrenador; }

    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }

    public Asociacion getAsociacion() { return asociacion; }
    public void setAsociacion(Asociacion asociacion) { this.asociacion = asociacion; }

    public List<Competicion> getCompeticiones() { return competiciones; }
    public void setCompeticiones(List<Competicion> competiciones) { this.competiciones = competiciones; }

    @Override
    public String toString() {
        return nombre + " (" + ciudad + ")";
    }
}
