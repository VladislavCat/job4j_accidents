package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public void update(Accident accident) {
        accidentRepository.update(accident);
    }
}
