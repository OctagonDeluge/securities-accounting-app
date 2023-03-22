package ru.tink.practice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<Portfolio> portfolios;


    public User(String email, String password, String name, Boolean enabled) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
    }
}
