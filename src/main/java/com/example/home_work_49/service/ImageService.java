package com.example.home_work_49.service;

import com.example.home_work_49.dto.UserImageDto;
import org.springframework.http.ResponseEntity;

public interface ImageService {

    String saveImage(UserImageDto userImageDto);

    ResponseEntity<?> findByName(String imageName);

    ResponseEntity<?> findById(long imageId);

    ResponseEntity<?> findByUserId(long userId);
}
