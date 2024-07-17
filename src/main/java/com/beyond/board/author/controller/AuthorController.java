package com.beyond.board.author.controller;

import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorReqDto;
import com.beyond.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping("/author/create")
    public void authorCreatePost(AuthorReqDto dto){
        authorService.authorCreate(dto);
    }



    @GetMapping("/author/detail/{id}")
    public AuthorDetResDto authorDetail(@PathVariable Long id){
        AuthorDetResDto authorDetResDto = authorService.authorDetail(id);
        return authorDetResDto;
    }
}
