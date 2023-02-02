package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.HbmRuleRepository;
import ru.job4j.accidents.repository.RuleJDBCRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final HbmRuleRepository repository;

    public List<Rule> findAll() {
        return repository.findAll();
    }

    public Set<Rule> findAllRuleById(String[] idRules) {
        List<Integer> indexRules = Arrays.stream(idRules)
                .map(Integer::parseInt).toList();
        return findAll().stream()
                .filter(rule -> indexRules.contains(rule.getId()))
                .collect(Collectors.toSet());
    }
}
