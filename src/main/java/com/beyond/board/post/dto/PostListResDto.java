package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostListResDto {

    private Long id;

    private String title;

//    Author객체 그 자체를 return 하게 되면 Author안에 Post가 재참조되어 순환참조 이슈 발생
//    private Author author;
    private String author_email;

}
