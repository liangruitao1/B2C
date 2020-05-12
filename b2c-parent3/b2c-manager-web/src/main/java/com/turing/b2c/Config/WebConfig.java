package com.turing.b2c.Config;

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
        registry.addViewController("/admin/index.html").setViewName("admin/index");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/admin/home.html").setViewName("admin/home");
        registry.addViewController("/admin/brand.html").setViewName("admin/brand");
        registry.addViewController("/admin/specification.html").setViewName("admin/specification");
        registry.addViewController("/admin/typeTemplate.html").setViewName("admin/typeTemplate");
        registry.addViewController("admin/item_cat.html").setViewName("admin/item_cat");
        registry.addViewController("/admin/goods.html").setViewName("admin/goods");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> types = new ArrayList<>();
        types.add(MediaType.APPLICATION_JSON);
        types.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(types);
        converters.add(0, converter);
    }
}
