package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HbmAccidentRepository implements AccidentRepository {
    private final HbmCrudCommanderRepository hbmCrudCommanderRepository;


    @Override
    public boolean addAccident(Accident accident) {
        return hbmCrudCommanderRepository.tx(session -> session.save(accident) != null);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return hbmCrudCommanderRepository.optional("select distinct(p) from Accident p join fetch p.rules join fetch p.type where p.id = :fId", Accident.class,
                Map.of("fId", id));
    }

    @Override
    public List<Accident> findAll() {
        return hbmCrudCommanderRepository.query("select distinct(p) from Accident p join fetch p.rules join fetch p.type order by p.id", Accident.class);
    }

    @Override
    public void update(Accident accident) {
        hbmCrudCommanderRepository.run(session -> session.merge(accident));
    }
}
