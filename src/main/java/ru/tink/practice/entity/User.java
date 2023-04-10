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
@Table(name = "client")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean enabled;
    private Boolean authenticated;
    @OneToMany(mappedBy = "client")
    private List<Portfolio> portfolios;
    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
    private RefreshToken refreshToken;

    public User(String email, String password, String name, Role role, Boolean enabled) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.role = role;
        this.authenticated = false;
    }
}
