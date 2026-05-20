package com.futbol.controller;

import com.futbol.entity.Entrenador;
import com.futbol.repository.EntrenadorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        return "entrenadores/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("entrenador", new Entrenador());
        return "entrenadores/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Entrenador entrenador,
                          BindingResult result,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "entrenadores/formulario";
        }
        entrenadorRepository.save(entrenador);
        flash.addFlashAttribute("success", "Entrenador guardado correctamente.");
        return "redirect:/entrenadores";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Entrenador entrenador = entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        model.addAttribute("entrenador", entrenador);
        return "entrenadores/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        entrenadorRepository.deleteById(id);
        flash.addFlashAttribute("success", "Entrenador eliminado.");
        return "redirect:/entrenadores";
    }
}
