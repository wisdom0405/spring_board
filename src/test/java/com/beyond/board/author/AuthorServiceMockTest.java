package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

// 가짜 객체를 만들어서 환경변수를 통제
@SpringBootTest
@Transactional
public class AuthorServiceMockTest {

    @Autowired
    private AuthorService authorService;

//    @Autowired
//    private AuthorRepository authorRepository;

    // 가짜 객체를 만드는 작업을 목킹이라고 한다.
    // 가짜 Repository 객체를 만들어서 일관된 응답을 받을 수 있도록 @MockBean 설정
    @MockBean
    private AuthorRepository authorRepository;

    // 저장 및 detail 조회
    @Test
    public void findAuthorDetailTest(){
        AuthorSaveReqDto dto = new AuthorSaveReqDto("test1", "test1@google.com","12341234", Role.ADMIN);
        Author author1 = authorService.authorCreate(dto); // 새로운 Author 객체 생성
//        Author mockAuthor = Author.builder().id(1L).name("test").email("test@daum.net").build(); // 1번 아이디로 객체 생성

        AuthorDetailDto authorDetailDto = authorService.authorDetail(author1.getId());
//        Author savedAuthor = authorRepository.findById(author1.getId()).orElseThrow(()->new EntityNotFoundException("id에 해당하는 Author가 없습니다."));

        // 만약 사용자가 authorRepository.findById를 하면 author1를 리턴해줘야 한다.
        Mockito.when(authorRepository.findById(author1.getId())).thenReturn(Optional.of(author1)); //Optional.of : null값 받지 않는다.
//        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(mockAuthor)); // 1번 id를 찾으면 myAuthor를 리턴할 것이다.

        Author savedAuthor = authorRepository.findById(author1.getId()).orElseThrow(()->new EntityNotFoundException("id에 해당하는 Author가 없습니다."));
        Assertions.assertEquals(authorDetailDto.getEmail(), savedAuthor.getEmail());
    }

    // update 검증


    // findAll 검증

}
