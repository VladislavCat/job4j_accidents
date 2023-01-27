package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMemRepository implements AccidentRepository {
    private final ConcurrentMap<Integer, Accident> map = new ConcurrentHashMap<>();
    private final AtomicInteger indexMap = new AtomicInteger(0);

    public boolean addAccident(Accident accident) {
        accident.setId(indexMap.incrementAndGet());
        return map.putIfAbsent(indexMap.get(), accident) == null;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(map.get(id));
    }

    public List<Accident> findAll() {
        return map.values().stream().toList();
    }

    public void update(Accident accident) {
        map.replace(accident.getId(), accident);
    }
}
