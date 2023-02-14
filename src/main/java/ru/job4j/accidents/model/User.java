package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String password;

    private String username;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Authority.class)
    @JoinColumn(name = "authority_id")
    private Authority authority;

    private boolean enabled;
}
