package com.crt.messageconverter.conf;

import com.crt.messageconverter.convert.MyMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Reed
 * @date 2020/8/26 10:51 上午
 */
@Configuration
public class MyWebMVCConf implements WebMvcConfigurer {
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(myMessageConverter());
    }

    @Bean
    public MyMessageConverter myMessageConverter(){
        return new MyMessageConverter();
    }
}
