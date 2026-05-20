package com.futbol.controller;

import com.futbol.entity.Jugador;
import com.futbol.repository.JugadorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {

    @Autowired
    private JugadorRepository jugadorRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("jugadores", jugadorRepository.findAll());
        return "jugadores/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("jugador", new Jugador());
        return "jugadores/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Jugador jugador,
                          BindingResult result,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "jugadores/formulario";
        }
        jugadorRepository.save(jugador);
        flash.addFlashAttribute("success", "Jugador guardado correctamente.");
        return "redirect:/jugadores";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        model.addAttribute("jugador", jugador);
        return "jugadores/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        jugadorRepository.deleteById(id);
        flash.addFlashAttribute("success", "Jugador eliminado.");
        return "redirect:/jugadores";
    }
}
