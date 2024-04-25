package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// Aspect 는 AOP 클래스에 필요한 어노테이션, Component 는 빈으로 등록하기 위한 어노테이션
@Aspect
@Component
public class TimeTraceAop {

    // Around는 지정할 타겟 경로
    @Around("execution(* hello.hellospring.service..*(..))")
    // @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long ms = finish - start;

            System.out.println("END: " + joinPoint.toString() + " " + ms + "ms");
        }
    }
}
