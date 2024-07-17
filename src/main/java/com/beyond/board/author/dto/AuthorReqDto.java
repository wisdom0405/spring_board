package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorReqDto {
    private String name;
    private String email;
    private String password;
    private Enum role;

    // dto에서 entity로 변환
    // 추후에는 빌더패턴으로 변환
    public Author toEntity(){
        Author author = new Author(this.name, this.email, this.password);
        return author;
    }
}
