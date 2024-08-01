package ru.tink.practice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tink.practice.entity.enumeration.Level;
import ru.tink.practice.entity.enumeration.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @Enumerated(EnumType.STRING)
    private Level level;
    private Boolean enabled;
    private Boolean authenticated;

    @OneToMany(mappedBy = "client")
    private List<Portfolio> portfolios;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "client")
    private List<Security> securities;

    @OneToMany(mappedBy = "client")
    private List<PurchaseInfo> purchaseInfos;

    @OneToOne(mappedBy = "client")
    private Wallet wallet;

    @OneToMany(mappedBy = "client")
    private List<OperationInfo> operationInfos;

    public User(String email, String password, String name, Level level, Role role, Boolean enabled) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.role = role;
        this.level = level;
        this.authenticated = false;
    }
}
