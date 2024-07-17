package com.beyond.board.post.domain;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.dto.PostResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

//    public PostResDto fromEntity(){
//        return new PostResDto(this.title, this.contents);
//    }
}
