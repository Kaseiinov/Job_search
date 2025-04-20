package com.example.home_work_49.repository;

import com.example.home_work_49.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

    @Query(nativeQuery = true,
        value = "select u.* " +
                "from users u " +
                "join vacancies v on u.id = v.author_id " +
                "where v.name = :vacancyName"
    )
    List<User> findAllByVacancyName(String vacancyName);
}
