package com.example.home_work_49.repository;

import com.example.home_work_49.models.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage,Long> {
    UserImage findByUser_Id(Long userId);
}
