package com.example.home_work_49.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vacancy")
@RequiredArgsConstructor
public class VacancyController {

    @PostMapping
    public ResponseEntity<?> createJob() {
        return ResponseEntity.ok().build();
    }

    @PutMapping("{vacancyId}")
    public ResponseEntity<?> updateVacancy(@PathVariable("vacancyId") Long vacancyId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping({"vacancyId"})
    public ResponseEntity<?> deleteVacancy(@PathVariable("vacancyId") Long vacancyId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllResumes() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<?> getVacanciesByCategory(@PathVariable("category") String category) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("aplicants/{aplicantId}")
    public ResponseEntity<?> getAplicants(@PathVariable long aplicantId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("seeker/{seekerId}")
    public ResponseEntity<?> getSeekers(@PathVariable long seekerId) {
        return ResponseEntity.ok().build();
    }



}
