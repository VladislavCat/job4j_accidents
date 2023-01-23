package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
@Repository
public class AccidentRepository {
    private final ConcurrentMap<Integer, Accident> map = new ConcurrentHashMap<>();

    public AccidentRepository() {
        addAccident(new Accident(1, "Авария", "Авария двух автомобилей на перекрестке",
                "Салтыковская улица, д30"));
        addAccident(new Accident(2, "Авария", "Пьяный водитель влетел в столб",
                "Минский проспект, д14"));
        addAccident(new Accident(3, "Превышение скорости", "Превышение скоростного режима на 100 км/ч",
                "МКАД, 14 км"));
    }

    public void addAccident(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(map.size());
            map.putIfAbsent(accident.getId(), accident);
        } else {
            map.putIfAbsent(accident.getId(), accident);
        }
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
