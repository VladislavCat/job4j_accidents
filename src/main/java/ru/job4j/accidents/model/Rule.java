package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "rules")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rules")
    @EqualsAndHashCode.Include
    private int id;
    private String name;
}
