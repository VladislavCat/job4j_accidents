package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/accidents")
    public String all(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "/accidents";
    }

    @GetMapping("/create")
    public String viewCreateAccident(Model model) {
        model.addAttribute("accident", new Accident());
        return "/create";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/accidents";
    }

    @GetMapping("/formUpdateAccident")
    public String formUpdate(@RequestParam("id") int id, Model model) {
        Optional<Accident> optional = accidentService.findById(id);
        if (optional.isEmpty()) {
            return "redirect:/404";
        }
        model.addAttribute("accident", optional.get());
        return "/formUpdateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/all_accidents";
    }

    @GetMapping("/404")
    public String error() {
        return "/404";
    }
}
