package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final RuleRepository repository;

    public List<Rule> findAll() {
        return repository.findAll();
    }

    public Rule findById(int id) {
        return repository.findRuleById(id);
    }
}
