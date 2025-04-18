package com.example.home_work_49.repository;

import com.example.home_work_49.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
