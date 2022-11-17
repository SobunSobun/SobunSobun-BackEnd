package numble.sobunsobun.security;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
//                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE", "PATCH")
                .maxAge(3000);
    }
}
