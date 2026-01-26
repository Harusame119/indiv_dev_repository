package com.expenses.springboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Service配下のすべてのメソッドが対象
    @Before("execution(* com.expenses.springboot..service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {

        // クラス名取得
        String className = joinPoint.getTarget().getClass().getSimpleName();
        // メソッド名取得
        String methodName = joinPoint.getSignature().getName();
        // ログ出力
        log.info("[START] {}.{}", className, methodName);
    }

    // Service配下のすべてのメソッドが対象
    @After("execution(* com.expenses.springboot..service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {

        // クラス名取得
        String className = joinPoint.getTarget().getClass().getSimpleName();
        // メソッド名取得
        String methodName = joinPoint.getSignature().getName();
        // ログ出力
        log.info("[END  ] {}.{}", className, methodName);
    }
}