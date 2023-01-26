package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
@Repository
public class RuleRepository {
    ConcurrentMap<Integer, Rule> map = new ConcurrentHashMap<>();
    AtomicInteger index = new AtomicInteger();

    public void addRule(Rule rule) {
        rule.setId(index.get());
        map.putIfAbsent(index.getAndIncrement(), rule);
    }

    public List<Rule> findAll() {
        return map.values().stream().toList();
    }

    public Rule findRuleById(int id) {
        return map.get(id);
    }
}
