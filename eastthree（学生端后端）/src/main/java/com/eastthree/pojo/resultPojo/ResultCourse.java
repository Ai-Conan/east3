package com.eastthree.pojo.resultPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultCourse {
    private Integer id;
    private String courseName;
    private String teacherName;
    private String week;
    private String weekday;
    private String section;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String coursePlace;
    private String status;
}
