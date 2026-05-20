package com.futbol.controller;

import com.futbol.entity.Asociacion;
import com.futbol.repository.AsociacionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/asociaciones")
public class AsociacionController {

    @Autowired
    private AsociacionRepository asociacionRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        return "asociaciones/lista";
    }

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("asociacion", new Asociacion());
        return "asociaciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Asociacion asociacion,
                          BindingResult result,
                          Model model,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "asociaciones/formulario";
        }
        asociacionRepository.save(asociacion);
        flash.addFlashAttribute("success", "Asociación guardada correctamente.");
        return "redirect:/asociaciones";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Asociacion asociacion = asociacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asociación no encontrada"));
        model.addAttribute("asociacion", asociacion);
        return "asociaciones/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        asociacionRepository.deleteById(id);
        flash.addFlashAttribute("success", "Asociación eliminada.");
        return "redirect:/asociaciones";
    }
}
