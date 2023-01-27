package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentJDBCRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccidentService {
    private final AccidentJDBCRepository accidentMemRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleService ruleService;

    public List<Accident> findAll() {
        return accidentMemRepository.findAll();
    }

    public String save(Accident accident, HttpServletRequest req) {
        if (setValueInAccident(req, accident)) {
            return "redirect:/404";
        }
        accidentMemRepository.addAccident(accident);
        return "redirect:/accidents";
    }

    public Optional<Accident> findById(int id) {
        return accidentMemRepository.findById(id);
    }

    public String update(Accident accident, HttpServletRequest req) {
        if (setValueInAccident(req, accident)) {
            return "redirect:/404";
        }
        accidentMemRepository.update(accident);
        return "redirect:/accidents";
    }

    public boolean setValueInAccident(HttpServletRequest req, Accident accident) {
        Optional<AccidentType> optionalAccidentType = accidentTypeRepository.findTypeById(accident.getType().getId());
        if (optionalAccidentType.isEmpty()) {
            return true;
        }
        accident.setType(optionalAccidentType.get());
        Set<Rule> ruleSet = ruleService.findAllRuleById(req);
        if (ruleSet.isEmpty()) {
            return true;
        }
        accident.setRules(ruleSet);
        return false;
    }
}
