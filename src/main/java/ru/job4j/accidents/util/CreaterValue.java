package ru.job4j.accidents.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;

@Component
@RequiredArgsConstructor
public class CreaterValue {
    private final AccidentTypeRepository typeRepository;
    private final AccidentRepository accidentRepository;

    @Bean
    public void createValueInRepository() {
        typeRepository.addAccidentType(new AccidentType(1, "Два автомобиля"));
        typeRepository.addAccidentType(new AccidentType(2, "Автомобиль и человек"));
        typeRepository.addAccidentType(new AccidentType(3, "Автомобиль и окружение"));
        accidentRepository.addAccident(new Accident(1, "Авария", "Авария двух автомобилей на перекрестке",
                "Салтыковская улица, д30", typeRepository.findTypeById(1)));
        accidentRepository.addAccident(new Accident(2, "Авария", "Пьяный водитель влетел в столб",
                "Минский проспект, д14", typeRepository.findTypeById(3)));
        accidentRepository.addAccident(new Accident(3, "Превышение скорости", "Превышение скоростного режима на 100 км/ч",
                "МКАД, 14 км", typeRepository.findTypeById(2)));
    }
}
