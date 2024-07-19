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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
        Author author = authorService.authorFindByEmail(dto.getEmail());
        Post post = postRepository.save(dto.toEntity(author));
        return post;
    }

    public List<PostListResDto> postList(){
        List<Post> posts = postRepository.findAllFetch();
        List<PostListResDto> postListResDtos = new ArrayList<>();

        for(Post p : posts){ // p에 author객체 담겨있음
            postListResDtos.add(p.listFromEntity()); // listFromEntity에서 .author_email(this.author.getEmail())
        }
        return postListResDtos;
    }

    public PostDetResDto postDetail(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("id에 해당하는 게시글이 없습니다."));
        return post.detFromEntity();
    }

    public void postDelete(Long id){
        postRepository.deleteById(id);
    }

    @Transactional
    public void postUpdate(Long id, PostUpdateDto dto){
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("id에 해당하는 게시글이 없습니다."));
        post.updatePost(dto);
        postRepository.save(post);
    }

}
