package com.eastthree.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Counsellor {
    private Integer id;
    private String no;
    private String password;
    private String name;
    private String gender;
    private String college;
}
