package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDetResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private String createdTime;
    private String updateTime;

}
