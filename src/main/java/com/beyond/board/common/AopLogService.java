package com.beyond.board.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

// @Aspect : 이 클래스가 aop코드임을 명시
@Aspect
@Component // 이 클래스를 스프링 빈으로 등록한다.
@Slf4j
public class AopLogService {

    // aop의 대상(공통화의 대상)이 되는 controller, service등의 위치를 명시
    // Pointcut : Advice가 적용될 Join Point를 지정하는 표현식. 어떤 메소드나 클래스에 Aspect를 적용할지를 정의
    @Pointcut("within(@org.springframework.stereotype.Controller *)") // 기준 : 모든 Controller 어노테이션 대상 (모든 Controller를 대상으로 하겠다.)
    public void controllerPointCut(){

    }

    // 방법1. around를 통해 controller와 걸쳐져 있는 사용 패턴
    // Advice : 특정 JoinPoint에서 수행되는 작업(Advice 유형 : Before, After, Around, AfterReturning, AfterThrowing)
    @Around("controllerPointCut()")
    // jointPoint는 사용자가 실행하려고 하는 코드를 의미하고, 위에서 정의한 PointCut을 의미한다.
    public Object controllerLogger(ProceedingJoinPoint joinPoint){
        // JoinPoint : Aspect가 적용될 수 있는 지점 (ex. 메소드 호출, 예외발생)
        log.info("aop start");
        log.info("Method명 : " +joinPoint.getSignature().getName());

//        직접 HttpServletRequest 객체를 꺼내는 법
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); // Servlet 객체로 형변환
        HttpServletRequest request = attributes.getRequest();

        log.info("HTTP 메서드"+request.getMethod());

        Map<String, String[]> parameterMap = request.getParameterMap();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.valueToTree(parameterMap);
        ObjectNode objectNode = objectMapper.valueToTree(parameterMap);
        log.info("user inputs : "+ objectNode);

        Object result = null;
        try {
//            사용자가 실행하고자 하는 코드 실행부
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
//        코드실행 후 aop end 출력
        log.info("aop end");
        return result;
    }

//    // 사용방법2. Before, After 어노테이션 사용
//    // JoinPoint 이전의
//    @Before("controllerPointCut()")
//    public void beforeController(JoinPoint joinPoint){
//        log.info("aop start");
//        log.info("Method명 : " +joinPoint.getSignature().getName());
//
////        직접 HttpServletRequest 객체를 꺼내는 법
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); // Servlet 객체로 형변환
//        HttpServletRequest request = attributes.getRequest();
//
//        log.info("HTTP 메서드"+request.getMethod());
//    }
//
//    @After("controllerPointCut()")
//    public void afterController(){
//        log.info("aop end");
//    }


}
