package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSaveReqDto {

    private String name;

    private String email;

    private String password;

    private Role role; // 사용자가 String으로 요청해도 Role클래스 자동형변환

    // dto에서 entity로 변환
    // 빌더패턴으로 변환
    public Author toEntity(){
            Author author = Author.builder() //.builder() : static 메서드
                    .password(this.password)
                    .name(this.name)
                    .email(this.email)
                    .posts(new ArrayList<>())
                    .role(this.role).build(); //이런식으로 세팅할 수 있음. 끝에는 .build() 붙여줘야 함.
            return author;
    }
}
