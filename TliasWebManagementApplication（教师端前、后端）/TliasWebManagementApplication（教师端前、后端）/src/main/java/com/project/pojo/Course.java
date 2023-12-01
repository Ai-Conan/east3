package com.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer id;
    private String courseName;
    private Integer courseTeacher;
    private String teacherName;
    private Integer week;
    private String section;
    private Integer weekday;
    private Integer studentId;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String coursePlace;

}
