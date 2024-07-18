package com.beyond.board.post.controller;

import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("post/create")
    public String postCreate(@RequestBody PostSaveReqDto dto){
        postService.postCreate(dto);
        return "ok";
    }

    @GetMapping("post/list")
    public List<PostListResDto> postList(){
        return postService.postList();
    }

    @GetMapping("post/detail/{id}")
    public PostDetResDto postDetail(@PathVariable Long id){
        return postService.postDetail(id);
    }

}
