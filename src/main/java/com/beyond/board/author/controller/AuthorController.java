package com.beyond.board.author.controller;

import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String home(){
        return "common/home";
    }

    @GetMapping("/author/register")
    public String authorCreateScreen(){
        return "author/author_register";
    }

    @PostMapping("/author/register")
    public String authorCreatePost(@ModelAttribute AuthorSaveReqDto dto){
        authorService.authorCreate(dto);
//        return "ok";
        return "redirect:/";
    }

    @GetMapping("/author/list")
    public String authorList(Model model){
//        return authorService.authorList();
        List<AuthorListResDto> authorListResDtoList =  authorService.authorList();
        model.addAttribute("authorList",authorListResDtoList);
        return "author/author_list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model){
//        return authorService.authorDetail(id);
        AuthorDetailDto authorDetailDto = authorService.authorDetail(id);
        model.addAttribute("author",authorDetailDto);
        return "author/author_detail";
    }
}
