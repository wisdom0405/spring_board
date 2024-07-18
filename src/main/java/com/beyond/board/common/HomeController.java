//package com.beyond.board.common;
//
//import com.beyond.board.author.controller.AuthorController;
//import com.beyond.board.author.dto.AuthorDetailDto;
//import com.beyond.board.author.dto.AuthorListResDto;
//import com.beyond.board.author.dto.AuthorSaveReqDto;
//import com.beyond.board.author.service.AuthorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//public class HomeController {
//    private final AuthorService authorService;
//
//    @Autowired
//    public HomeController(AuthorService authorService){
//        this.authorService = authorService;
//    }
//
//    @GetMapping("/")
//    public String home(){
//        return "common/home";
//    }
//
//    @PostMapping("/author/register")
//    public String authorCreatePost(@RequestBody AuthorSaveReqDto dto){
//        authorService.authorCreate(dto);
//        return "redirect:/author/author_list";
//    }
//
//    @GetMapping("/authorlist")
//    public String authorList(Model model){
//        List<AuthorListResDto> authorListResDtoList =  authorService.authorList();
//        model.addAttribute("authorList",authorListResDtoList);
//        return "author/author_list";
//    }
//
//    @GetMapping("/authordetail/{id}")
//    public String authorDetail(@PathVariable Long id, Model model){
//        AuthorDetailDto authorDetailDto = authorService.authorDetail(id);
//        model.addAttribute("author",authorDetailDto);
//        return "author/author_detail";
//    }
//
//}
