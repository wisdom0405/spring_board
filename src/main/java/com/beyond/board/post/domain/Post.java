package com.beyond.board.post.domain;

import com.beyond.board.author.domain.Author;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostUpdateDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 3000)
    private String contents;

    // 연관관계의 주인은 fk가 있는 post (fk를 관리함) - 연관관계의 자식테이블
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public PostListResDto listFromEntity(){
        PostListResDto postListResDto = PostListResDto.builder()
                .id(this.id)
                .title(this.title)
//                .author(this.author) // 이렇게 하면 순환참조 발생
                .author_email(this.author.getEmail())
                .build();

        return postListResDto;
    }

    public PostDetResDto detFromEntity(){
        return PostDetResDto.builder()
                .id(this.id)
                .title(this.title)
                .contents(this.contents)
                .author_email(this.author.getEmail())
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdatedTime())
                .build();
    }

    public void updatePost(PostUpdateDto dto){
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }
}
