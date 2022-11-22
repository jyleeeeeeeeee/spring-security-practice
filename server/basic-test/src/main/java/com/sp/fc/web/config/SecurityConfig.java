package com.sp.fc.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true) // request가 올 때 마다 어떤 filter chain을 통과하는 지 출력
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* application.yaml에는 사용자 한 명만 추가 가능
         그 이상은 여기에 작성
         여기에 authentication을 추가하면
         application.yaml에 있는 설정은 더 이상 이용 불가
         -> application.yml에 작성된 user1 이용 불가
         */
        auth.inMemoryAuthentication()
                .withUser(User.builder().username("user2")
                        .password(passwordEncoder().encode("user2")).roles("USER"))
                .withUser(User.builder().username("admin")
                        .password(passwordEncoder().encode("admin")).roles("ADMIN"));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // spring이 기본으로 사용하고 있는 Encoder
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 어떤 request에 filter를 적용할 건지 설정
        http.antMatcher("/api/**");

        // 누구나 접근할 수 있는 페이지 설정하기
        http.authorizeRequests(
                (request) -> request.antMatchers("/")
                        .permitAll().anyRequest().authenticated());
        http.formLogin().disable();
        http.httpBasic().disable();
    }
}