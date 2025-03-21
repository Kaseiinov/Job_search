package com.example.home_work_49.controller;

import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.models.Resume;
import com.example.home_work_49.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping
    public HttpStatus createResume(@RequestBody ResumeDto resumeDto) {
        resumeService.addResume(resumeDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("{resumeId}")
    public ResponseEntity<?> updateResume(@PathVariable("resumeId") Long resumeId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{resumeId}")
    public ResponseEntity<?> deleteResume(@PathVariable("resumeId") Long resumeId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllActiveResumes() {
        return  ResponseEntity.ok().build();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<?> getVacancyByCategory(@PathVariable("category") String category) {
        return  ResponseEntity.ok().build();
    }

//    @PostMapping("apply/{applyId}")
//    public ResponseEntity<?> applyForJob(@PathVariable Long applyid) {
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("employer/{employerId}")
//    public ResponseEntity<?> getEmployer(@PathVariable Long employerId) {
//        return ResponseEntity.ok().build();
//    }
}
