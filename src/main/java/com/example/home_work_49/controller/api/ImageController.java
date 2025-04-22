package com.example.home_work_49.controller.api;

import com.example.home_work_49.dto.UserImageDto;
import com.example.home_work_49.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class ImageController {
//    private final ImageService imageService;
//
//    @GetMapping
//    public ResponseEntity<?> getImageById(@RequestParam(name = "id")Long id){
//        return imageService.findById(id);
//    }
//
//    @GetMapping("{userId}")
//    public ResponseEntity<?> getImageByUserid(@PathVariable Long userId){
//        return imageService.findByUserId(userId);
//    }
//
//    @PostMapping
//    public String uploadImage(UserImageDto userImageDto){
//        return imageService.saveImage(userImageDto);
//    }
}
