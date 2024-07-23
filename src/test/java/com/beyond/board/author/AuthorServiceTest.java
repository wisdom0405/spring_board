package com.beyond.board.author;


import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
//@Rollback
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    // 저장 및 detail 조회
    @Test
    public void saveAndFind(){
        AuthorSaveReqDto authorDto = new AuthorSaveReqDto("hongildong","hongildong@daum.net","12341234", Role.ADMIN);
        Author author = authorService.authorCreate(authorDto); // 내가 지금 만든 것

        Author authorDetail = authorRepository.findById(author.getId()).orElseThrow(()-> new EntityNotFoundException("id에 해당하는 Author가 없습니다."));
        Assertions.assertEquals(authorDetail.getEmail(), authorDto.getEmail()); // dto의 이메일과 비교
    }

    // update 검증
    @Test
    public void updateAndFind(){
        // 객체 Create 저장
        AuthorSaveReqDto authorDto = new AuthorSaveReqDto("hongildong","hogildong@daum.net","12341234", Role.ADMIN);
        Author author = authorService.authorCreate(authorDto);

        // 수정작업(name, password 바꿈)
        authorService.authorUpdate(author.getId(), new AuthorUpdateDto("hong","43214321"));

        // 수정 후 재조회 : name, password가 맞는지 각각 검증(Assertions.asserEquals)
        Author savedAuthor = authorRepository.findById(author.getId()).orElseThrow(()-> new EntityNotFoundException("not found"));
        Assertions.assertEquals("hong", savedAuthor.getName());
        Assertions.assertEquals("43214321", savedAuthor.getPassword());
    }

    // findAll 검증
    @Test
    public void findAllTest(){

        int size = authorService.authorList().size(); // 객체 추가 전 리스트 사이즈

        // 3개 Author 객체 저장 (authorCreate)
        for(int i=0; i<3; i++){
            AuthorSaveReqDto authorDto = new AuthorSaveReqDto("hongildong"+i,"hogildong"+i+"@daum.net","12341234", Role.ADMIN);
            Author author = authorService.authorCreate(authorDto);
        }

        // authorList를 조회한 후 저장한 개수와 저장된 개수 비교
//        int m = authorService.authorList().size(); // 객체 추가 후 리스트 사이즈
//        Assertions.assertEquals(m-n,3);
        Assertions.assertEquals(size+3, authorService.authorList().size());
    }

}
