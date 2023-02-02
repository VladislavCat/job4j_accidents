package ru.job4j.accidents.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AccidentSetExtract implements ResultSetExtractor<List<Accident>> {
    @Override
    public List<Accident> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Accident> accidents = new ArrayList<>();
        while (rs.next()) {
            Accident accident = new Accident();
            accident.setId(rs.getInt("id_accidents"));
            accident.setName(rs.getString(2));
            accident.setText(rs.getString("description"));
            accident.setAddress(rs.getString("address"));
            accident.setType(new AccidentType(rs.getInt("id_accident_types"),
                    rs.getString("type_name")));
            accident.setRules(new HashSet<>());
            accident.getRules().add(new Rule(rs.getInt("id_rules"), rs.getString(8)));
            if (accidents.contains(accident)) {
                accidents.stream()
                        .filter(a -> a.getId() == accident.getId()).findFirst()
                        .get().getRules()
                        .add(new Rule(rs.getInt("id_rules"), rs.getString(8)));
                continue;
            }
            accidents.add(accident);
        }
        return accidents;
    }
}
