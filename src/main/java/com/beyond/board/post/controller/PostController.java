package com.beyond.board.post.controller;

import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("post/create")
    public String postCreateScreen(){
        return "post/post_register";
    }

    @PostMapping("post/create")
    public String postCreate(PostSaveReqDto dto){
        postService.postCreate(dto);
        return "redirect:/post/list";
    }

    @GetMapping("post/list")
    public String postList(Model model){
        model.addAttribute("postList", postService.postList());
        return "post/post_list";
    }

    @GetMapping("post/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.postDetail(id));
        return "post/post_detail";
    }

    @GetMapping("post/delete/{id}")
    public String postDelete(@PathVariable Long id){
        postService.postDelete(id);
        return "redirect:/post/list";
    }

    @PostMapping("post/update/{id}")
    public String postUpdate(@PathVariable Long id, @ModelAttribute PostUpdateDto postUpdateDto){
        postService.postUpdate(id, postUpdateDto);
        return "redirect:/post/detail/"+id;
    }
}
