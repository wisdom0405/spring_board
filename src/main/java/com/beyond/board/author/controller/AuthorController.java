package com.beyond.board.author.controller;

import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        return "redirect:/author/list";
    }

    @GetMapping("/author/list")
    public String authorList(Model model){
//        return authorService.authorList();
        List<AuthorListResDto> authorListResDtoList =  authorService.authorList();
        model.addAttribute("authorList",authorListResDtoList);
        return "author/author_list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model) {
//        log.info("get요청이고, parameter는 " + id);
//        log.info("method명: authorDetail");

        AuthorDetailDto authorDetailDto = authorService.authorDetail(id);
        model.addAttribute("author", authorDetailDto);
        return "author/author_detail";
    }

//        try {
//        model.addAttribute("author",authorDetailDto);
//
//        }catch (IllegalArgumentException e){
//            e.printStackTrace();
//            log.error(id); // 이런식으로 try catch 부에서 log.error 해서 로그남김
//        }


    @GetMapping("author/delete/{id}")
    public String authorDelete(@PathVariable Long id){
        authorService.authorDelete(id);
        return "redirect:/author/list";
    }

    @PostMapping("author/update/{id}")
    public String authorUpdate(@PathVariable Long id,@ModelAttribute AuthorUpdateDto authorUpdateDto){
        authorService.authorUpdate(id, authorUpdateDto);
        return "redirect:/author/detail/"+id;
    }


}
