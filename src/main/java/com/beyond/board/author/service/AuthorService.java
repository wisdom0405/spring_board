package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
// 조회 작업 시에 readOnly 설정하면 성능 향상된다.
// 다만, 저장 작업 시에는 별도 Transactional 필요
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public Author authorCreate(AuthorSaveReqDto dto){
        if(authorRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 Email 입니다.");
        }
        Author author = dto.toEntity(); // MemberReqDto 객체 메서드
        Author savedAuthor = authorRepository.save(author); // 저장된 멤버
        return savedAuthor;
    }

    public List<AuthorListResDto> authorList(){
        List<AuthorListResDto> authorListResDtoList = new ArrayList<>();
        List<Author> authors = authorRepository.findAll();
        for(Author a : authors){
            AuthorListResDto authorListResDto = a.fromEntity();
            authorListResDtoList.add(authorListResDto);
        }
        return authorListResDtoList;
    }

    public AuthorDetailDto authorDetail(Long id){
        Author author = authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("id에 해당하는 유저가 없습니다."));
        AuthorDetailDto authorDetailDto = new AuthorDetailDto();

        return authorDetailDto.fromEntity(author);
    }

    public Author authorFindByEmail(String email){
        Author author = authorRepository.findByEmail(email)
                                        .orElseThrow(()->new EntityNotFoundException("해당 email의 사용자는 없습니다."));
        return author;
    }

    public void authorDelete(Long id){
        authorRepository.deleteById(id);
    }

    public void authorUpdate(Long id, AuthorUpdateDto dto){
        Author author = authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("id에 해당하는 유저가 없습니다."));
        author.updateAuthor(dto);
        authorRepository.save(author);
    }
}
