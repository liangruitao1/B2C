package com.turing.b2c.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("shoplogin");
        registry.addViewController("/admin/index.html").setViewName("admin/index");
        registry.addViewController("/admin/home.html").setViewName("admin/home");
        registry.addViewController("/admin/goodsEdit.html").setViewName("admin/goodsEdit");
        registry.addViewController("/admin/goods.html").setViewName("admin/goods");
        registry.addViewController("/admin/password.html").setViewName("admin/password");
        registry.addViewController("/admin/seller.html").setViewName("admin/seller");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> types = new ArrayList<>();
        types.add(MediaType.APPLICATION_JSON);
        types.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(types);
        converters.add(0, converter);
    }
}
