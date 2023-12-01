package com.eastthree;

import com.eastthree.mapper.AttendanceMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TliasWebManagemnetApplicationTests{
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Test
    public void genJwt() {
        Map<String,Object> claims=new HashMap<>();
        claims.put("id",1);
        claims.put("username","Tom");
        String jwt= Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,"itheima")//签名算法
                .setExpiration(new Date(System.currentTimeMillis()+24*3600*1000))
                .compact();
        System.out.println(jwt);
    }
    @Test
    public void parseJwt(){
        Claims claims=Jwts.parser()
                .setSigningKey("itheima")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNjgzMTIwMzQ1LCJ1c2VybmFtZSI6IlRvbSJ9.ASzxUChet5FULWmR-wDp84sm9T_n7TnEw1Cz4AT7Jmo")
                .getBody();
        System.out.println(claims);
    }
    @Test
    public  void test(){
        attendanceMapper.getWhoNoCheck(1);
        System.out.println(666);
    }


}

