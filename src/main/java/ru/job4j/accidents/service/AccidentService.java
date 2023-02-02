package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.*;
import ru.job4j.accidents.repository.DataAccidentRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {
    @Autowired
    private final DataAccidentRepository accidentMemRepository;
    private final HbmTypeRepository accidentTypeRepository;
    private final RuleService ruleService;
    @Transactional
    public List<Accident> findAll() {
        List<Accident> accidents = new ArrayList<>();
        accidentMemRepository.findAll().iterator().forEachRemaining(accidents::add);
        accidents.sort(Comparator.comparingInt(Accident::getId));
        return accidents;
    }

    public boolean save(Accident accident, String[] idRules) {
        if (setValueInAccident(accident, idRules)) {
            return false;
        }
        accidentMemRepository.save(accident);
        return true;
    }

    public Optional<Accident> findById(int id) {
        return accidentMemRepository.findById(id);
    }

    public boolean update(Accident accident, String[] idRules) {
        if (setValueInAccident(accident, idRules)) {
            return false;
        }
        accidentMemRepository.save(accident);
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
