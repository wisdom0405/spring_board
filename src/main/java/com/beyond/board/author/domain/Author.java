package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorDetResDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@Entity
public class Author {
//    도메인 : Author (id, name, email, password, role(Enum: ADMIN, USER), createdTime, updatedTime)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Enum role;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    public Author(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public AuthorDetResDto detFromEntity(){
        LocalDateTime createdTime = this.getCreatedTime();
        String created = createdTime.getYear()+"년 "+createdTime.getMonthValue()+"월 "+createdTime.getDayOfMonth()+"일";

        LocalDateTime updatedTime = this.getUpdateTime();
        String updated = updatedTime.getYear()+"년 "+updatedTime.getMonthValue()+"월 "+updatedTime.getDayOfMonth()+"일";

        return new AuthorDetResDto(this.id, this.name, this.email, this.password, this.role, created, updated);
    }

}
