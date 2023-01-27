package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeMemRepository implements AccidentTypeRepository {
    private final ConcurrentMap<Integer, AccidentType> mapType = new ConcurrentHashMap<>();
    private final AtomicInteger indexMapType = new AtomicInteger(0);

    public boolean addAccidentType(AccidentType accidentType) {
        accidentType.setId(indexMapType.incrementAndGet());
        return mapType.putIfAbsent(indexMapType.get(), accidentType) == null;
    }
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return Optional.of(mapType.get(id));
    }
    @Override
    public List<AccidentType> findAllType() {
        return mapType.values().stream().toList();
    }
}
