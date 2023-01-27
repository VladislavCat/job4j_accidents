package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJDBCRepository implements AccidentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String sqlAddAccident = "insert into accidents(name, description, address, type_id) values(?, ?, ?, ?)";
    private final String sqlAddManyToMany = "insert into rules_accidents(accidents_id, rules_id) values(?, ?)";
    private final String findById = "select * from accidents as a join accident_types as at"
            + " on at.id_accident_types = a.id_accidents where a.id_accidents = (?)";
    private final String findRulesById = "select * from rules_accidents as ra join rules"
            + " as r on r.id_rules = ra.rules_id where ra.accidents_id = (?)";
    private final String findAll = "select * from accidents as a join accident_types as at"
            + " on at.id_accident_types = a.type_id order by a.id_accidents";
    private final String updateAccidents = "update accidents set name = (?) where id_accidents = (?)";
    private final String deleteFromAccidentRule = "delete from rules_accidents where accidents_id = (?)";
    private final String addRulesFromAccident = "insert into rules_accidents(accidents_id, rules_id) values((?), (?))";

    @Override
    public boolean addAccident(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(sqlAddAccident, new String[]{"id_accidents"});
                    ps.setString(1, accident.getName());
                    ps.setString(2, accident.getText());
                    ps.setString(3, accident.getAddress());
                    ps.setInt(4, accident.getType().getId());
                    return ps;
                }, keyHolder);
        int i = keyHolder.getKey().intValue();
        for (Rule rule : accident.getRules()) {
            jdbcTemplate.update(sqlAddManyToMany,
                    i, rule.getId());
        }
        return i != 0;
    }

    @Override
    public Optional<Accident> findById(int id) {
        Accident accident = new Accident();
        try {
            accident = jdbcTemplate.queryForObject(findById,
                    createAccidentRowMapper(), id);
            accident.setRules(findRuleById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(accident);
    }

    @Override
    public List<Accident> findAll() {
        List<Accident> accidents = new ArrayList<>();
        try {
            accidents = jdbcTemplate.query(findAll,
                    createAccidentRowMapper());
            for (Accident accident : accidents) {
                accident.setRules(findRuleById(accident.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accidents;
    }

    @Override
    public void update(Accident accident) {
        jdbcTemplate.update(updateAccidents, accident.getName(), accident.getId());
        if (!accident.getRules().equals(findRuleById(accident.getId()))) {
            jdbcTemplate.update(deleteFromAccidentRule, accident.getId());
            for (Rule rule : accident.getRules()) {
                jdbcTemplate.update(addRulesFromAccident, accident.getId(), rule.getId());
            }
        }
    }

    private Set<Rule> findRuleById(int id) {
        return new HashSet<>(jdbcTemplate.query(findRulesById,
                createRuleRowMapper(), id));
    }

    private RowMapper<Accident> createAccidentRowMapper() {
        return (rs, row) -> {
            Accident a = new Accident();
            a.setId(rs.getInt("id_accidents"));
            a.setName(rs.getString("name"));
            a.setText(rs.getString("description"));
            a.setAddress(rs.getString("address"));
            a.setType(new AccidentType(rs.getInt("id_accident_types"), rs.getString("type_name")));
            return a;
        };
    }

    private RowMapper<Rule> createRuleRowMapper() {
        return (rs, row) -> {
            Rule rule = new Rule();
            rule.setId(rs.getInt("id_rules"));
            rule.setName(rs.getString("name"));
            return rule;
        };
    }
}
