//package com.beyond.board.post.service;
//
//import com.beyond.board.post.domain.Post;
//import com.beyond.board.post.repository.PostRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@Component
//public class PostScheduler {
//    private final PostRepository postRepository;
//
//    @Autowired
//    public PostScheduler(PostRepository postRepository){
//        this.postRepository = postRepository;
//    }
//    // 아래 스케쥴의 cron부는 각 자리마다 "초 분 시간 일 월 요일"을 의미
//    // 예를 들어 * * * * * * : 매일 매분 매초마다 시간이 돌아간다
//    // 예를 들어 0 0 * * * * : 매일 0분 0초에(1시간 주기)
//    // 예를 들어 0 0 11 * * * : 매일 11시에
//    // 예를 들어 0 0/1 * * * * : 매일 매시 1분마다 (1시간에 60번 돌아감)
//    // 예를 들어 0 1 * * * * : 매일 매시 1분에(1시간에 1번 돌아감)
//    @Scheduled(cron = "0 0/1 * * * *") // 스케쥴러 쓰겠다는 어노테이션
//    @Transactional
//    public void postSchedule(){
//        System.out.println("===예약글쓰기 스케쥴러 시작===");
//        // 페이징처리 굳이 필요하지 않아서 .unpaged()
//        Page<Post> posts = postRepository.findByAppointment(Pageable.unpaged(),"Y");
//        for(Post p : posts){
//            if(p.getAppointmentTime().isBefore(LocalDateTime.now())){
//                p.updateAppointment("N"); //Transcational의 dirtychecking에 의해서 수정작업은 .save안해줘도 된다.
//            }
//        }
//    }
//}
