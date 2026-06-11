package org.example.project.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* org.example.project.service.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        System.out.println("Method: " + joinPoint.getSignature().getName());
    }
    @Around("execution(* org.example.project.controller.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        System.out.println("Time: " + (end - start) + "ms");

        return result;
    }
}
