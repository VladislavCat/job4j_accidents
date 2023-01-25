package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<Rule> findAllRuleById(HttpServletRequest req) {
        return Arrays.stream(req.getParameterValues("rIds"))
                .mapToInt(Integer::parseInt)
                .mapToObj(repository::findRuleById)
                .collect(Collectors.toSet());
    }
}
