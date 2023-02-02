package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJDBCRepository implements AccidentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowRuleMapper rowRuleMapper = new RowRuleMapper();
    private final AccidentSetExtract accidentSetExtract = new AccidentSetExtract();

    private static final String SQLADDACCIDENT = """
            INSERT INTO accidents(name, description, address, type_id) VALUES(?, ?, ?, ?);
            """;

    private static final String SQLADDMANYTOMANY = """
    INSERT INTO rules_accidents(accidents_id, rules_id) VALUES(?, ?);
    """;
    private static final String FINDBYID = """
            SELECT a.id_accidents, a.name, a.description, a.address, at.id_accident_types,
            at.type_name, r.id_rules, r.name FROM accidents AS a
            JOIN accident_types AS at
            ON at.id_accident_types = a.type_id
            JOIN rules_accidents AS ra
            ON ra.accidents_id = a.id_accidents
            JOIN rules AS r
            ON ra.rules_id = r.id_rules
            where a.id_accidents = (?);
            """;
    private static final String FINDRULESBYID = """
            SELECT * FROM rules_accidents AS ra
            JOIN rule AS r
            ON r.id_rules = ra.rules_id
            WHERE ra.accidents_id = (?)
            """;

    private static final String FINDALL = """
            SELECT a.id_accidents, a.name, a.description, a.address, at.id_accident_types,
            at.type_name, r.id_rules, r.name FROM accidents AS a
            JOIN accident_types AS at
            ON at.id_accident_types = a.type_id
            JOIN rules_accidents AS ra
            ON ra.accidents_id = a.id_accidents
            JOIN rules AS r
            ON ra.rules_id = r.id_rules
            ORDER BY a.id_accidents;
            """;

    private static final String UPDATEACCIDENT = """
    UPDATE accidents SET name = (?) WHERE id_accidents = (?)
    """;

    private static final String DELETEFROMACCIDENTRULES = """
        DELETE FROM rules_accidents WHERE accidents_id = (?)
        """;

    private static final String ADDRULESACCIDENTS = """
    INSERT INTO rules_accidents(accidents_id, rules_id) VALUES((?), (?))
    """;

    @Override
    public boolean addAccident(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(SQLADDACCIDENT, new String[]{"id_accidents"});
                    ps.setString(1, accident.getName());
                    ps.setString(2, accident.getText());
                    ps.setString(3, accident.getAddress());
                    ps.setInt(4, accident.getType().getId());
                    return ps;
                }, keyHolder);
        int i = keyHolder.getKey().intValue();
        for (Rule rule : accident.getRules()) {
            jdbcTemplate.update(SQLADDMANYTOMANY,
                    i, rule.getId());
        }
        return i != 0;
    }

    @Override
    public Optional<Accident> findById(int id) {
        Accident accident = jdbcTemplate.query(FINDBYID, accidentSetExtract, id).get(0);
        return Optional.ofNullable(accident);
    }

    @Override
    public List<Accident> findAll() {
        return jdbcTemplate.query(FINDALL, accidentSetExtract);
    }

    @Override
    public void update(Accident accident) {
        jdbcTemplate.update(UPDATEACCIDENT, accident.getName(), accident.getId());
        jdbcTemplate.update(DELETEFROMACCIDENTRULES, accident.getId());
        for (Rule rule : accident.getRules()) {
            jdbcTemplate.update(ADDRULESACCIDENTS, accident.getId(), rule.getId());
        }
    }

    private Set<Rule> findRuleById(int id) {
        return new HashSet<>(jdbcTemplate.query(FINDRULESBYID,
                rowRuleMapper, id));
    }
}
