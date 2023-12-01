package com.eastthree.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.eastthree.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.eastthree.pojo.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    //前置方法

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle...");
        //1.获取请求url
        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行
        //3.获取请求头中的令牌（token）
        String token=request.getHeader("token");
        log.info("从请求头中获取令牌：{}",token);
        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）
        if(token==null) {
            log.info("Token不存在");
            //创建响应结果对象
            Result responseResult = Result.error("NOT_LOGIN");
            //把Result对象转换为JSON格式的字符串（fastjson)
            String json = JSONObject.toJSONString(responseResult);
            //设置响应头（告知游览器：响应的数据格式为json.响应的数据编码表为utf-8）
            response.setContentType("application/json;charset=utf-8");
            //响应
            response.getWriter().write(json);
            return false;//不放行
        }
        try{
            JwtUtils.parseJwt(token);
        }catch (Exception e){
            log.info("令牌解析失败！");
            //创建响应结果对象
            Result responseResult=Result.error("NOT_LOGIN");
            //把Result转换成JSON格式字符串（fastjson）
            String json=JSONObject.toJSONString(responseResult);
            //设置响应头
            response.setContentType("application/json;charset=utf-8");
            //响应
            response.getWriter().write(json);
            return false;
        }
        //放行
        return true;


    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
