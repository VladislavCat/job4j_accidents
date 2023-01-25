package ru.job4j.accidents.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreaterValue {
    private final AccidentTypeRepository typeRepository;
    private final AccidentRepository accidentRepository;
    private final RuleRepository ruleRepository;

    @Bean
    public void createValueInRepository() {
        typeRepository.addAccidentType(new AccidentType(1, "Два автомобиля"));
        typeRepository.addAccidentType(new AccidentType(2, "Автомобиль и человек"));
        typeRepository.addAccidentType(new AccidentType(3, "Автомобиль и окружение"));
        ruleRepository.addRule(new Rule(1, "234 статья"));
        ruleRepository.addRule(new Rule(2, "455 статья"));
        ruleRepository.addRule(new Rule(3, "111 статья"));
        accidentRepository.addAccident(new Accident(1, "Авария", "Авария двух автомобилей на перекрестке",
                "Салтыковская улица, д30",
                typeRepository.findTypeById(1).get(), Set.of(ruleRepository.findRuleById(1))));
        accidentRepository.addAccident(new Accident(2, "Авария", "Пьяный водитель влетел в столб",
                "Минский проспект, д14", typeRepository.findTypeById(3).get(),
                Set.of(ruleRepository.findRuleById(1), ruleRepository.findRuleById(2))));
        accidentRepository.addAccident(new Accident(3, "Превышение скорости", "Превышение скоростного режима на 100 км/ч",
                "МКАД, 14 км", typeRepository.findTypeById(2).get(),
                Set.of(ruleRepository.findRuleById(3))));
    }
}
