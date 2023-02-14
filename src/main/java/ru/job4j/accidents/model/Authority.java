package ru.job4j.accidents.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authorities")
@Getter
@Setter
@EqualsAndHashCode
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String authority;
}
