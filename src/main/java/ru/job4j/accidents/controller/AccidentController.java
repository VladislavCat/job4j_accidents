package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@RequiredArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/all_accidents")
    public String all(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "/all_accidents";
    }

    @GetMapping("/create_accidents")
    public String viewCreateAccident(Model model) {
        model.addAttribute("accident", new Accident());
        return "/create_accidents";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/all_accidents";
    }
}
