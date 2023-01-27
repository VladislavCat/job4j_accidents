package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentJDBCRepository;
import ru.job4j.accidents.repository.AccidentTypeJDBCRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeJDBCRepository accidentTypeRepository;

    public List<AccidentType> findAllType() {
        return accidentTypeRepository.findAllType();
    }

    public Optional<AccidentType> findTypeById(int id) {
        return accidentTypeRepository.findTypeById(id);
    }
}
