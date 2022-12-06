package numble.sobunsobun.security;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true)
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE", "PATCH")
                .maxAge(3000);
    }

//     @Bean
//     public ServletWebServerFactory servletContainer() {
//         TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//             @Override
//             protected void postProcessContext(Context context) {
//                 SecurityConstraint securityConstraint = new SecurityConstraint();
//                 securityConstraint.setUserConstraint("CONFIDENTIAL");
//                 SecurityCollection collection = new SecurityCollection();
//                 collection.addPattern("/*");
//                 securityConstraint.addCollection(collection);
//                 context.addConstraint(securityConstraint);
//             }
//         };

//         tomcat.addAdditionalTomcatConnectors(createSslConnector());
//         return tomcat;
//     }

//     private Connector createSslConnector() {
//         Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//         connector.setPort(8080);
//         connector.setScheme("http");
//         connector.setSecure(false);
//         connector.setRedirectPort(8443);
//         return connector;
//     }
}
