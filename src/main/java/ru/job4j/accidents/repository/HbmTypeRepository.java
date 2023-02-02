package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class HbmTypeRepository implements AccidentTypeRepository {
    private final HbmCrudCommanderRepository hbmCrudCommanderRepository;

    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return hbmCrudCommanderRepository.optional("from AccidentType where id_accident_types = :fId", AccidentType.class,
                Map.of("fId", id));
    }

    @Override
    public List<AccidentType> findAllType() {
        return hbmCrudCommanderRepository.query("from AccidentType", AccidentType.class);
    }
}
