package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class HbmRuleRepository implements RuleRepository {
    private final HbmCrudCommanderRepository hbmCrudCommanderRepository;
    @Override
    public List<Rule> findAll() {
        return hbmCrudCommanderRepository.query("from Rule", Rule.class);
    }

    @Override
    public Rule findRuleById(int id) {
        return hbmCrudCommanderRepository
                .optional("from Rule where id_rules = :fId", Rule.class, Map.of("fId", id)).get();
    }
}
