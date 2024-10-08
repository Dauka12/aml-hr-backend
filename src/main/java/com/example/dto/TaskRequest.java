package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String title;
    private String description;
    private String status;
    private Long employeeId;  // Храним только ID сотрудника


}
