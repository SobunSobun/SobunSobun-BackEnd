package numble.sobunsobun.security;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.utils.JwtAuthenticationFilter;
import numble.sobunsobun.utils.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenService jwtTokenService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private static final String[] PERMIT_URL_ARRAY={
            "/","/**/*.png","/**/*.jpg","/**/*.js","/**/*.css","/**/*.html","/**/*.gif","/**/*.svg"
            ,"/join", "/join/**", "/login", "/login/**"
    };

    private static final String[] AUTHENTICATED_URL_ARRAY={
            "/test", "/test/**",
            "/post", "/post/**",
            "/myPage", "/myPage/**",
            "/parentComment", "/parentComment/**",
            "/childComment", "/childComment/**",
            "/myPosts", "/myPosts/**",
            "/myInfo", "/myInfo/**"
    };

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.cors();
        http.httpBasic().disable()
                .authorizeRequests()// 요청에 대한 사용권한 체크
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(AUTHENTICATED_URL_ARRAY).authenticated()
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
                .anyRequest().denyAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenService),
                        UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣음
        // + 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
