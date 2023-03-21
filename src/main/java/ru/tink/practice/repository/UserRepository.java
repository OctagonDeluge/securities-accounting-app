package ru.tink.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tink.practice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
