package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentRepository {
    private final ConcurrentMap<Integer, Accident> map = new ConcurrentHashMap<>();
    private final AtomicInteger indexMap = new AtomicInteger(0);

    public void addAccident(Accident accident) {
        map.putIfAbsent(indexMap.getAndIncrement(), accident);
    }

    public Optional<Accident> findById(int id) {
        return map.values().stream().filter(accident -> accident.getId() == id).findFirst();
    }

    public List<Accident> findAll() {
        return map.values().stream().toList();
    }

    public void update(Accident accident) {
        map.replace(accident.getId(), accident);
    }
}
