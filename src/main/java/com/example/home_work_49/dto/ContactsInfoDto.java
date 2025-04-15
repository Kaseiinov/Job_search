package com.example.home_work_49.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactsInfoDto {
    private Long id;
    private Long typeId;
    private Long resumeId;
    private String value;
}
