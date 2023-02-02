package ru.job4j.accidents.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "accidents")
@AllArgsConstructor
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accidents")
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    @Column(name = "description")
    private String text;
    private String address;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private AccidentType type;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "rules_accidents",
        joinColumns = {@JoinColumn(name = "accidents_id")},
        inverseJoinColumns = {@JoinColumn(name = "rules_id")}
    )
    @ToString.Exclude
    private Set<Rule> rules;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Accident accident = (Accident) o;
        return id != 0 && Objects.equals(id, accident.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
