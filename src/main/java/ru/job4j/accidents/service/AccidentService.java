package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccidentService {
    private final HbmAccidentRepository accidentMemRepository;
    private final HbmTypeRepository accidentTypeRepository;
    private final RuleService ruleService;

    public List<Accident> findAll() {
        return accidentMemRepository.findAll();
    }

    public boolean save(Accident accident, String[] idRules) {
        if (setValueInAccident(accident, idRules)) {
            return false;
        }
        accidentMemRepository.addAccident(accident);
        return true;
    }

    public Optional<Accident> findById(int id) {
        return accidentMemRepository.findById(id);
    }

    public boolean update(Accident accident, String[] idRules) {
        if (setValueInAccident(accident, idRules)) {
            return false;
        }
        accidentMemRepository.update(accident);
        return true;
    }

    public boolean setValueInAccident(Accident accident, String[] idRules) {
        Optional<AccidentType> optionalAccidentType = accidentTypeRepository.findTypeById(accident.getType().getId());
        if (optionalAccidentType.isEmpty()) {
            return true;
        }
        accident.setType(optionalAccidentType.get());
        Set<Rule> ruleSet = ruleService.findAllRuleById(idRules);
        if (ruleSet.isEmpty()) {
            return true;
        }
        accident.setRules(ruleSet);
        return false;
    }
}
