package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
@Repository
public class RuleMemRepository implements RuleRepository {
    ConcurrentMap<Integer, Rule> map = new ConcurrentHashMap<>();
    AtomicInteger index = new AtomicInteger();

    public void add(Rule rule) {
        rule.setId(index.getAndIncrement());
        map.putIfAbsent(index.get(), rule);
    }
    @Override
    public List<Rule> findAll() {
        return map.values().stream().toList();
    }

    @Override
    public Rule findRuleById(int id) {
        return map.get(id);
    }
}
