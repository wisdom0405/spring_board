package com.beyond.board.post.domain;

import com.beyond.board.author.domain.Author;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
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

    @ManyToOne
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
}
