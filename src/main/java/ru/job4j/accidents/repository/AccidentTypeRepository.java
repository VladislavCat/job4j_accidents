package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeRepository {
    private final ConcurrentMap<Integer, AccidentType> mapType = new ConcurrentHashMap<>();
    private final AtomicInteger indexMapType = new AtomicInteger(0);

    public void addAccidentType(AccidentType accidentType) {
        mapType.putIfAbsent(indexMapType.getAndIncrement(), accidentType);
    }

    public AccidentType findTypeById(int id) {
        return mapType.values().stream().filter(accident -> accident.getId() == id).findFirst().get();
    }

    public List<AccidentType> findAllType() {
        return mapType.values().stream().toList();
    }
}
