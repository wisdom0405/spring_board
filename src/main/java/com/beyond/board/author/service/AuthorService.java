package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.post.domain.Post;
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
//        cascade persist 테스트. remove 테스트 회원삭제로 대체
        // post 할때 author_id가 필요한데 author를 save하기 전까지 id가 안나온다 -> jpa 영속성 context가 알아서 맞춰준다. (선후관계 안따져도 잘 돌아간다)
        author.getPosts().add(Post.builder().title("가입인사").author(author).contents("안녕하세요 "+dto.getName()+" 입니다.").build());
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
        // jpa가 특정 엔티티의 변경을 자동으로 인지하고 변경사항을 DB에 반영하는 것이 dirtychecking(변경감지)
        // Transactional 어노테이션 반드시 필요하다.
        authorRepository.save(author); //.save 안해도 update 상황에서는 save됨 (근데 명시적으로 save해줬음)
    }
}
