package com.futbol.controller;

import com.futbol.entity.Competicion;
import com.futbol.repository.CompeticionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/competiciones")
public class CompeticionController {

    @Autowired
    private CompeticionRepository competicionRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("competiciones", competicionRepository.findAll());
        return "competiciones/lista";
    }

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("competicion", new Competicion());
        return "competiciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Competicion competicion,
                          BindingResult result,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "competiciones/formulario";
        }
        competicionRepository.save(competicion);
        flash.addFlashAttribute("success", "Competición guardada correctamente.");
        return "redirect:/competiciones";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Competicion competicion = competicionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Competición no encontrada"));
        model.addAttribute("competicion", competicion);
        return "competiciones/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        competicionRepository.deleteById(id);
        flash.addFlashAttribute("success", "Competición eliminada.");
        return "redirect:/competiciones";
    }
}
