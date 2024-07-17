package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public void authorCreate(AuthorReqDto dto){
        Author author = dto.toEntity(); // MemberReqDto 객체 메서드

        if(authorRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 Email 입니다.");
        }
        authorRepository.save(author); // 저장된 멤버
    }

    public AuthorDetResDto authorDetail(Long id){
        Optional<Author> optAuthor = authorRepository.findById(id);
        Author author = optAuthor.orElseThrow(()->new EntityNotFoundException("id에 해당하는 유저가 없습니다."));

        return author.detFromEntity();
    }

}
