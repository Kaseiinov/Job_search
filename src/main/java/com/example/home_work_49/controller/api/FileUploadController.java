package com.example.home_work_49.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/images")
@RequiredArgsConstructor
public class FileUploadController {
    @GetMapping("imageName")
    public ResponseEntity<String> getImage(@PathVariable String imageName) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(MultipartFile file) {
        return ResponseEntity.ok("File uploaded");
    }
}
