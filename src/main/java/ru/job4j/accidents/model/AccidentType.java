package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "accident_types")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accident_types")
    @EqualsAndHashCode.Include
    private int id;
    @Column(name = "type_name")
    private String name;
}
