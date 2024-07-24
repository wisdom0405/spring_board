package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {
    // UserDetailsService를 상속하면 loadUserByUsername메소드 오버라이딩 해줘야 함.
    // doLogin 실행하면 UserDetailsService를 상속한 클래스를 찾음 -> loadUserByUsername 메소드 실행

    @Autowired
    private AuthorService authorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = authorService.authorFindByEmail(username); // username : email
        List<GrantedAuthority> authorities = new ArrayList<>(); // 권한리스트 (GrantedAuthority : 인터페이스-> 구현체 필요)
        authorities.add(new SimpleGrantedAuthority("ROLE_" + author.getRole().toString())); //ROLE_ADMIND이런식으로 전달
        return new User(author.getEmail(), author.getPassword(),authorities); // Role설계 안했으면 authorities 자리에 null 넣어주면 됨
    }

}
