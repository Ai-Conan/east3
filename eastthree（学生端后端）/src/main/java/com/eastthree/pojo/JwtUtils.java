package com.eastthree.pojo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static String signKey="flyingpig";//签名密匙
    private static Long expire=43200000L;//有效时间
    //生成JWT令牌
    public static String generateJwt(Map<String,Object> claims){
        String jwt= Jwts.builder()
                .addClaims(claims)//自定义信息（有效载荷》
                .signWith(SignatureAlgorithm.HS256,signKey)//签名算法（头部）
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .compact();
        return jwt;
    }
    //解析JWT令牌
    public static Claims parseJwt(String jwt){
        Claims claims=Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }


}
