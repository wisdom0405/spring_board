package com.beyond.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorResDto {
    private Long id;
    private String name;
    private String email;
    private Enum role;
}
