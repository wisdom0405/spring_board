package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
// builder 어노테이션을 통해 매개변수의 순서, 매개변수의 개수 등을 유연하게 세팅하여 생성자로서 활용 (NoArgs, AllArgs 있어야됨)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
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

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

//    @Builder // 매개변수의 개수, 순서 유연하게 해줌
//    public Author(String name, String email, String password, Role role){
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }

    public AuthorDetResDto detFromEntity(){
        LocalDateTime createdTime = this.getCreatedTime();
        String created = createdTime.getYear()+"년 "+createdTime.getMonthValue()+"월 "+createdTime.getDayOfMonth()+"일";

        LocalDateTime updatedTime = this.getUpdateTime();
        String updated = updatedTime.getYear()+"년 "+updatedTime.getMonthValue()+"월 "+updatedTime.getDayOfMonth()+"일";

        return new AuthorDetResDto(this.id, this.name, this.email, this.password, this.role, created, updated);
    }

}
