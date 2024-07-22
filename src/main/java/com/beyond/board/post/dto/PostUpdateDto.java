package com.beyond.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {
    private String title;
    private String contents;
    private String appointment;
    private String appointmentTime;
}
