package com.beyond.board.post.service;

import com.beyond.board.post.domain.Post;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

// batch 작업 목록 정의
@Configuration
public class PostJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PostRepository postRepository;

    //전체 작업 정의
    // 스케쥴러에서는 executeJob()만 실행한다.
    public Job executeJob(){
        return jobBuilderFactory.get("executeJob")
                .start(firstStep())
                .next(secondStep())
                .build();
    }

    @Bean // 싱글톤으로 정의, 상단 클래스에 Configuration
    public Step firstStep(){
        return stepBuilderFactory.get("firstStep")
                .tasklet((stepContribution, chunkContext) -> {
                System.out.println("===예약글쓰기 batch task1 시작===");
                // 페이징처리 굳이 필요하지 않아서 .unpaged()
                Page<Post> posts = postRepository.findByAppointment(Pageable.unpaged(),"Y");
                for(Post p : posts){
                    if(p.getAppointmentTime().isBefore(LocalDateTime.now())){
                        p.updateAppointment("N"); //Transcational의 dirtychecking에 의해서 수정작업은 .save안해줘도 된다.
                    }
                }
                System.out.println("===예약글쓰기 task1 종료");
                return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step secondStep(){
        return stepBuilderFactory.get("secondStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("===예약글쓰기 batch task2 시작===");
                    // 페이징처리 굳이 필요하지 않아서 .unpaged()
                    System.out.println("===예약글쓰기 task2 종료");
                    return RepeatStatus.FINISHED;
                }).build();
    }



}
