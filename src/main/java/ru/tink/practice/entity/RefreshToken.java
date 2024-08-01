package ru.tink.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Long expirationTime;
    @OneToOne
    private User client;

    public RefreshToken(String token, Long expirationTime, User user) {
        this.token = token;
        this.expirationTime = expirationTime;
        this.client = user;
    }
}
