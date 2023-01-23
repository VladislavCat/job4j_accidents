package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccidentService {
    private final AccidentRepository accidentRepository;

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public void save(Accident accident) {
        accidentRepository.addAccident(accident);
    }
}
