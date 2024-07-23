package com.beyond.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// 세션방식 로그인
@Configuration
@EnableWebSecurity // spring security설정을 customizing 하기 위함.
@EnableGlobalMethodSecurity(prePostEnabled = true) // pre: 사전, post: 사후 인증검사
public class SecurityConfig {
    @Bean // 싱글톤객체 만듦
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity //httpSecurity 객체를 커스텀 (builder패턴이므로 원하는것만 세팅가능)
                .csrf().disable() // csrf공격에 대한 설정은 하지않겠다는 의미

                .build();
    }
}
