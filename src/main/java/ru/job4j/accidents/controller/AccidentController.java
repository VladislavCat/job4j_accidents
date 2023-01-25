package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/accidents")
    public String all(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "/accidents";
    }

    @GetMapping("/create")
    public String viewCreateAccident(Model model) {
        model.addAttribute("accident", new Accident());
        model.addAttribute("types", accidentTypeService.findAllType());
        model.addAttribute("rules", ruleService.findAll());
        return "/create";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        Optional<AccidentType> opt = (accidentTypeService.findTypeById(accident.getType().getId()));
        if (opt.isEmpty()) {
            return "redirect:/404";
        }
        accident.setType(opt.get());
        Set<Rule> setRule = ruleService.findAllRuleById(req);
        if (setRule.isEmpty()) {
            return "redirect:/404";
        }
        accident.setRules(setRule);
        accidentService.save(accident);
        return "redirect:/accidents";
    }

    @GetMapping("/formUpdateAccident")
    public String formUpdate(@RequestParam("id") int id, Model model) {
        Optional<Accident> optional = accidentService.findById(id);
        if (optional.isEmpty()) {
            return "redirect:/404";
        }
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("accident", optional.get());
        return "/formUpdateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        Set<Rule> setRule = ruleService.findAllRuleById(req);
        if (setRule.isEmpty()) {
            return "redirect:/404";
        }
        Optional<AccidentType> optional = accidentTypeService.findTypeById(accident.getId());
        if (optional.isEmpty()) {
            return "redirect:/404";
        }
        accident.setType(optional.get());
        accident.setRules(setRule);
        accidentService.update(accident);
        return "redirect:/accidents";
    }

    @GetMapping("/404")
    public String error() {
        return "/404";
    }
}
