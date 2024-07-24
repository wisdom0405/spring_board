package com.beyond.board.common;

import com.beyond.board.author.service.LoginSuccessHandler;
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
        // 필터 chain (필터체인 커스텀)
        return httpSecurity //httpSecurity 객체를 커스텀 (builder패턴이므로 원하는것만 세팅가능)
                .csrf().disable() // csrf공격에 대한 설정은 하지않겠다는 의미
                .authorizeRequests()
                    .antMatchers("/", "/author/register", "/author/login-screen") // antMatchers : 인증제외 할 것
                    .permitAll() // permitAll : 그 외에는 모두 인증 필요
                    .anyRequest().authenticated()
                .and()
//                만약 세션로그인이 아니라, 토큰로그인일 경우
//                .sessionManagement(), sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                    .loginPage("/author/login-screen")
                    // post 요청 : doLogin
                .loginProcessingUrl("/doLogin") // doLogin 메서드 : spring에서 미리 구현 해놓은 로그인 기능 (action에 호출거는 url)
                    // 다만, doLogin에 넘겨줄 email, password 속성명은 별도 지정
                    .usernameParameter("email")
                    .passwordParameter("password")
                .successHandler(new LoginSuccessHandler())
                .and()
                    .logout()
                    // security에서 구현된 doLogout기능 그대로 사용
                    .logoutUrl("/doLogout") // doLogout : spring에서 미리 구현해놓은 Logout
                .and()
                .build();
    }
}
