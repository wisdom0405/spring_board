package com.beyond.board.author.dto;

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
    private Enum role;
    private String createdTime;
    private String updateTime;

}
