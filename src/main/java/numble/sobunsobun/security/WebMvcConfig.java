package numble.sobunsobun.security;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedOrigins("http://15.164.112.119:8080/")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                .maxAge(3000);
    }
}
