package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
// builder 어노테이션을 통해 매개변수의 순서, 매개변수의 개수 등을 유연하게 세팅하여 생성자로서 활용 (NoArgs, AllArgs 있어야됨)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author extends BaseTimeEntity {
//    도메인 : Author (id, name, email, password, role(Enum: ADMIN, USER), createdTime, updatedTime)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 일반적으로 부모엔티티에 cascade 옵션을 설정한다.
    // 부모엔티티 : 자식객체에 영향을 끼칠 수 있는 엔티티
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

//    @Builder // 매개변수의 개수, 순서 유연하게 해줌
//    public Author(String name, String email, String password, Role role){
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }
    public AuthorListResDto fromEntity(){
        AuthorListResDto authorListResDto = AuthorListResDto.builder()
                                        .id(this.id)
                                        .name(this.name)
                                        .email(this.email)
                                        .build();
        return authorListResDto;
    }

    public AuthorDetailDto fromDetEntity(){
        AuthorDetailDto authorDetailDto = AuthorDetailDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .createdTime(this.getCreatedTime())
                .build();

        return authorDetailDto;
    }

    public void updateAuthor(AuthorUpdateDto dto){
        this.name = dto.getName();
        this.password = dto.getPassword();
    }

}
