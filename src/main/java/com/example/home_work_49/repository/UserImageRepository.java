package com.example.home_work_49.repository;

import com.example.home_work_49.models.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage,Long> {

    Optional<UserImage> findByUser_Id(Long userId);

    List<UserImage> findByFileName(String fileName);
}
