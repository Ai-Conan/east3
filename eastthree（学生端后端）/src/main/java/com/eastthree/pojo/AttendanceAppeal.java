package com.eastthree.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceAppeal {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private String reason;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appealBeginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appealEndTime;
    private String status;
    private String leaveCourseName;

    @Override
    public String toString() {
        return "AttendanceAppeal{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", reason='" + reason + '\'' +
                ", appealBeginTime=" + appealBeginTime +
                ", appealEndTime=" + appealEndTime +
                ", status='" + status + '\'' +
                ", leaveCourseName='" + leaveCourseName + '\'' +
                '}';
    }
}
