package ru.tink.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tink.practice.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE investments.client SET authenticated = ?1 WHERE email =?2", nativeQuery = true)
    void updateAuthByEmail(Boolean authState, String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE investments.client SET password = ?1 WHERE email =?2", nativeQuery = true)
    void updatePassword(String password, String email);
}
