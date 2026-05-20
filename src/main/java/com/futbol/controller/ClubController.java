package com.futbol.controller;

import com.futbol.entity.Club;
import com.futbol.entity.Jugador;
import com.futbol.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clubes")
public class ClubController {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private AsociacionRepository asociacionRepository;

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private CompeticionRepository competicionRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clubes", clubRepository.findAll());
        return "clubes/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        Club club = new Club();
        model.addAttribute("club", club);
        cargarDatosFormulario(model, club);
        return "clubes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Club club,
                          BindingResult result,
                          @RequestParam(value = "jugadorIds", required = false) List<Long> jugadorIds,
                          @RequestParam(value = "competicionIds", required = false) List<Long> competicionIds,
                          Model model,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            cargarDatosFormulario(model, club);
            return "clubes/formulario";
        }

        // --- Paso 1: guardar el club SIN jugadores primero ---
        // Si es edicion, cargar desde BD para preservar la coleccion
        Club clubFinal;
        if (club.getId() != null) {
            clubFinal = clubRepository.findById(club.getId())
                    .orElseThrow(() -> new RuntimeException("Club no encontrado"));
            clubFinal.setNombre(club.getNombre());
            clubFinal.setCiudad(club.getCiudad());
            clubFinal.setAnioFundacion(club.getAnioFundacion());
            clubFinal.setAsociacion(club.getAsociacion());
            clubFinal.setEntrenador(club.getEntrenador());
        } else {
            // Club nuevo: limpiar jugadores para que no genere FK antes de tener ID
            club.getJugadores().clear();
            clubFinal = club;
        }

        // --- Paso 2: competiciones (@ManyToMany) ---
        clubFinal.getCompeticiones().clear();
        if (competicionIds != null && !competicionIds.isEmpty()) {
            clubFinal.getCompeticiones().addAll(competicionRepository.findAllById(competicionIds));
        }

        // --- Paso 3: guardar club (ya tiene ID tras este save) ---
        Club clubGuardado = clubRepository.save(clubFinal);

        // --- Paso 4: manejar jugadores DESDE el lado Jugador (no desde Club) ---
        List<Long> nuevosIds = (jugadorIds != null) ? jugadorIds : List.of();

        // Desasignar jugadores que ya no pertenecen a este club
        jugadorRepository.findAll().stream()
                .filter(j -> clubGuardado.getId().equals(
                        j.getClub() != null ? j.getClub().getId() : null))
                .filter(j -> !nuevosIds.contains(j.getId()))
                .forEach(j -> {
                    j.setClub(null);
                    jugadorRepository.save(j);
                });

        // Asignar los nuevos jugadores seleccionados
        if (!nuevosIds.isEmpty()) {
            jugadorRepository.findAllById(nuevosIds).forEach(j -> {
                j.setClub(clubGuardado);
                jugadorRepository.save(j);
            });
        }

        flash.addFlashAttribute("success", "Club guardado correctamente.");
        return "redirect:/clubes";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club no encontrado"));
        model.addAttribute("club", club);
        cargarDatosFormulario(model, club);
        return "clubes/formulario";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club no encontrado"));
        model.addAttribute("club", club);
        return "clubes/detalle";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club no encontrado"));
        // Desasignar jugadores antes de eliminar
        jugadorRepository.findAll().stream()
                .filter(j -> club.getId().equals(
                        j.getClub() != null ? j.getClub().getId() : null))
                .forEach(j -> {
                    j.setClub(null);
                    jugadorRepository.save(j);
                });
        club.getJugadores().clear();
        clubRepository.save(club);
        clubRepository.deleteById(id);
        flash.addFlashAttribute("success", "Club eliminado.");
        return "redirect:/clubes";
    }

    private void cargarDatosFormulario(Model model, Club clubActual) {
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        model.addAttribute("jugadoresDisponibles", jugadorRepository.findAll());
        model.addAttribute("competicionesDisponibles", competicionRepository.findAll());

        // Solo entrenadores sin club asignado, o el que ya tiene este club
        List<Long> entrenadoresTomados = clubRepository.findAll()
                .stream()
                .filter(c -> c.getEntrenador() != null)
                .filter(c -> clubActual.getId() == null || !c.getId().equals(clubActual.getId()))
                .map(c -> c.getEntrenador().getId())
                .toList();

        model.addAttribute("entrenadores", entrenadorRepository.findAll()
                .stream()
                .filter(e -> !entrenadoresTomados.contains(e.getId()))
                .toList());
    }
}
