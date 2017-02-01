package com.ancun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebMvc设置
 *
 * @author jarvan [NO.0051]
 * @since 2017年01月20日 10:33
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public AccessTokenVerifyInterceptorConfig tokenVerifyInterceptor() {
        return new AccessTokenVerifyInterceptorConfig();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenVerifyInterceptor()).addPathPatterns("/checkUserTelOnline");
        registry.addInterceptor(tokenVerifyInterceptor()).addPathPatterns("/checkPhpSessionIdOnline");
        registry.addInterceptor(tokenVerifyInterceptor()).addPathPatterns("/removeUserOnline");
        super.addInterceptors(registry);
    }

}
