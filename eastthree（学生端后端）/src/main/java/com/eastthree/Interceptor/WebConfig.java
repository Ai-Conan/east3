//package com.flyingpig.Interceptor;
//
//import com.flyingpig.Interceptor.LoginCheckInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    //拦截器对象
//    @Autowired
//    private LoginCheckInterceptor loginCheckInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //注册自定义拦截器对象
//        registry.addInterceptor(loginCheckInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/loginStudent")
//                .excludePathPatterns("/loginTeacher")
//                .excludePathPatterns("/loginCounsellor");
//    }
//}
