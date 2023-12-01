package com.eastthree.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private String no;
    private String password;
    private String name;
    private String gender;
    private String grade;
    private String clasS;
    private String major;
    private String college;
}
