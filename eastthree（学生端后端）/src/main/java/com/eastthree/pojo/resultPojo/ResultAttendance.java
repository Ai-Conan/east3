package com.eastthree.pojo.resultPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultAttendance {
    private Integer id;
    private Integer studentId;
    private String studentNo;
    private String studentName;
    private Integer courseId;
    private String status;

}