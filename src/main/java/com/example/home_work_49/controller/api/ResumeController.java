package com.example.home_work_49.controller.api;

import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.WorkExperienceInfoDto;
import com.example.home_work_49.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("restResume")
@RequestMapping("api/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping("createWorkExperienceInfo")
    public HttpStatus createWorkExperience(@RequestBody @Valid WorkExperienceInfoDto workExperienceInfoDto) {
        log.info("Creating work experience info");
        resumeService.addWorkExperienceInfo(workExperienceInfoDto);
        return HttpStatus.CREATED;
    }

    @PostMapping("createResume")
    public HttpStatus createResume(@RequestBody @Valid ResumeDto resumeDto) {
        log.info("Creating Resume: {}", resumeDto.getName());
//        resumeService.addResume(resumeDto. "d");
        return HttpStatus.CREATED;
    }

    @GetMapping("{resumeId}")
    public ResumeDto getResumeById(@PathVariable("resumeId") Long resumeId) {
        return resumeService.getResumeById(resumeId);
    }

    @PutMapping("{resumeId}")
    public HttpStatus updateResume(@PathVariable("resumeId") @Valid Long resumeId, @RequestBody ResumeDto resumeDto) {
        resumeService.updateResumeById(resumeId, resumeDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("{resumeId}")
    public HttpStatus deleteResume(@PathVariable("resumeId") Long resumeId) {
        log.warn("Deleting Resume: {}", resumeId);
        resumeService.deleteResumeById(resumeId);
        return HttpStatus.OK;
    }

    @GetMapping("active")
    public List<ResumeDto> getAllActiveResumes() {
        return  resumeService.getAllActiveResumes();
    }

    @GetMapping("category/{resumeCategory}")
    public List<ResumeDto> getResumeByCategory(@PathVariable("resumeCategory") String resumeCategory) {
        return resumeService.getResumeByCategory(resumeCategory);
    }

    @GetMapping("resumes/{userName}")
    public List<ResumeDto> getResumeCreatedByUser(@PathVariable String userName) {
        return resumeService.getResumeByUser(userName);
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
