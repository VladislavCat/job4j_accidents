package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class RuleJDBCRepository implements RuleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String findAll = "select * from rules";
    private final String findRuleById = "select * from rules where id_rules = (?)";

    @Override
    public List<Rule> findAll() {
        return jdbcTemplate.query(findAll, createRuleRowMapper());
    }

    @Override
    public Rule findRuleById(int id) {
        return jdbcTemplate.queryForObject(findRuleById, createRuleRowMapper(), id);
    }

    public RowMapper<Rule> createRuleRowMapper() {
        return (rs, row) -> {
            Rule rule = new Rule();
            rule.setId(rs.getInt("id_rules"));
            rule.setName(rs.getString("name"));
            return rule;
        };
    }

}
