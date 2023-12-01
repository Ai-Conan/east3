package com.eastthree.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private LocalDateTime time;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", time=" + time +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                '}';
    }
}
