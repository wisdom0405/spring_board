package com.beyond.board.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Slf4j 어노테이션을 통한 로거 선언방법
@Slf4j
@RestController
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("log/test1")
    public String logTest1(){
        // 기존의 로그 방식 : System.out.println();
        // 문제점 1. 성능이 좋지 않음 2.로그 분류 작업 불가
        System.out.println("println 로그입니다."); // 로그 항상찍힘
//        logger.debug("debug 로그입니다.");
//        logger.info("info 로그입니다.");
//        logger.error("error 로그입니다.");

        log.info("slfj4를 통한 info로그입니다.");
        log.error("slfj4를 통한 error 로그입니다.");
        // 어떤 상황에만 로그가 남도록 하고싶음
        // 분류작업을 하기위한 라이브러리가 있다.
        return "ok";
    }
}
