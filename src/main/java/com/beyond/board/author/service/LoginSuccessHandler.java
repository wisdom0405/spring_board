package com.beyond.board.author.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Authentication 객체  : 인증 요청 성공시 해당객체에 사용자의 인증정보 보관
        HttpSession httpSession = request.getSession(); // 사용자의 Session 꺼냄

        // 방법 1. authentication 인증객체에서 email 정보 추출
//        httpSession.setAttribute("email", authentication.getName()); // email 세팅, .getName()은 진짜 이름이 아니라 email값 의미

        // 방법 2. SecurityContextHolder객체에서 authentication객체 꺼낸 뒤 email정보 추출
        httpSession.setAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        response.sendRedirect("/"); // 로그인 성공하면 home으로 이동
    }
}
