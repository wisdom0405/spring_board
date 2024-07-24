package com.beyond.board.post.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AuthorService authorService;

    // service 또는 repository를 autowired할지는 상황에 따라 다르나,
    // service 레벨에서 코드가 고도화되어 있고 코드의 중복이 심할 경우, service레이어를 autowired
    // 그러나, 순환참조가 발생할 경우에는 repository를 autowired

    @Autowired
    public PostService(PostRepository postRepository, AuthorService authorService){
        this.postRepository = postRepository;
        this.authorService = authorService;
    }

    // authorservice에서 author객체를 찾아 post의 toEntity에 넘기고,
    // jpa가 author객체에서 author_id를 찾아 db에는 author_id가 들어감.

    public Post postCreate(PostSaveReqDto dto){
        // 로그인 구현 후 postCreate : SecurityContextHolder의 SecurityContextHolder에 담긴 getName(email)가져옴
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorService.authorFindByEmail(email);

//        Author author = authorService.authorFindByEmail(dto.getEmail());
        String appointment = null;
        LocalDateTime appointmentTime = null;
        if(dto.getAppointment().equals("Y") && !dto.getAppointmentTime().isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            appointmentTime = LocalDateTime.parse(dto.getAppointmentTime(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();

            if(appointmentTime.isBefore(now)){
                throw new IllegalArgumentException("시간입력이 잘못되었습니다.");
            }
        }
        System.out.println(dto);
        Post post = postRepository.save(dto.toEntity(author, appointmentTime));
        return post;
    }

    public Page<PostListResDto> postList(Pageable pageable){
//        List<Post> posts = postRepository.findAllFetch();
//        List<PostListResDto> postListResDtos = new ArrayList<>();
//
//        for(Post p : posts){ // p에 author객체 담겨있음
//            postListResDtos.add(p.listFromEntity()); // listFromEntity에서 .author_email(this.author.getEmail())
//        }
//        Page<Post> posts = postRepository.findAllNo(pageable);
        Page<Post> posts = postRepository.findByAppointment(pageable, "N");
        Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity());
        return postListResDtos;
    }

    public Page<PostListResDto> postListPage(Pageable pageable){
        //postRepository.findAll() 그냥 이렇게 하면 return : List<Post>
        //Page객체로 return 하도록 findAll 메소드 커스텀해줘야 함.
        // (Page 객체가 리스트 형태로 되어있어서 Page객체 안에 List객체를 넣을 필요 없다.
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity());
        return postListResDtos;

    }

    public PostDetResDto postDetail(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("id에 해당하는 게시글이 없습니다."));
        return post.detFromEntity();
    }

    public void postDelete(Long id){
        // 로그인 구현 후 postCreate : SecurityContextHolder의 SecurityContextHolder에 담긴 getName(email)가져옴
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("id에 해당하는 post가 없습니다."));

        if(!post.getAuthor().getEmail().equals(email)){
            throw new IllegalArgumentException("본인의 게시글이 아닙니다.");
        }

        postRepository.delete(post);
    }

    @Transactional
    public void postUpdate(Long id, PostUpdateDto dto){
        // 로그인 구현 후 postCreate : SecurityContextHolder의 SecurityContextHolder에 담긴 getName(email)가져옴
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorService.authorFindByEmail(email); // 해당 email에 해당하는 Author객체 가져옴

        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("id에 해당하는 게시글이 없습니다."));
        if(!post.getAuthor().getId().equals(author.getId())){
            throw new IllegalArgumentException("본인의 게시글이 아닙니다");
        }
        post.updatePost(dto);
        postRepository.save(post);
    }
}
