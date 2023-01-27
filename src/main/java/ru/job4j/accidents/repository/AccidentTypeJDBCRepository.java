package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class AccidentTypeJDBCRepository implements AccidentTypeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<AccidentType> findTypeById(int id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("select * from accident_types where id_accident_types = (?)", createTypeRowMapper(), id));
    }

    @Override
    public List<AccidentType> findAllType() {
        return jdbcTemplate.query("select * from accident_types", createTypeRowMapper());
    }

    public RowMapper<AccidentType> createTypeRowMapper() {
        return (rs, row) -> {
            AccidentType at = new AccidentType();
            at.setId(rs.getInt("id_accident_types"));
            at.setName(rs.getString("type_name"));
            return at;
        };
    }
}
