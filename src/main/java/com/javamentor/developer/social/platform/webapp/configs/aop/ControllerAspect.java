package com.javamentor.developer.social.platform.webapp.configs.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotNull;

/**
 * Аспект - состовляющая программы которая позволяет реализовать сквозную логику без загрязнения целевого класса.
 * Спринг умеет подкладывать прокси только под свои бины и компоненты.
 * Позволяет JoinPoint только на исполнении метода.
 * Joinpoint -конкретное место внедрения  - метод, конструктор, геттер/сеттер и тд.
 * Pointcut - регулярное выражениее под которое могут подпадать несколько Joinpoint.
 * Advice - действие исполняемое аспектом в конкретном JoinPoint.
 * Говоря короче, - Где? Как найти это место? Что нужно сделать?
 */
@Aspect
@Configuration
@EnableAspectJAutoProxy
public class ControllerAspect {

    public Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    /**
     * Данный Эдвайс предназначен для логирования результатов выполнения методов любого контроллера.
     * Устраняет необходимость в локальных логгерах.
     * Проверка предназначена для ResponseEntity<Void>. Если его не будет - можно убрать.
     * Обычного void у нас в Rest'ах не должно быть совсем, но Вы это знаете и без меня.
     */
    @AfterReturning(pointcut = "within(@org.springframework.web.bind.annotation.RestController *)", returning = "result")
    public void afterMethodInControllerClass( @NotNull JoinPoint joinPoint , Object result ) {
        ResponseEntity<?> response = (ResponseEntity<?>) result;
        if(response.hasBody()) {
            String value = response.getBody().toString();
            HttpStatus status = response.getStatusCode();
            logger.info("\n\n###### Returning for class : {};\n###### Method : {};\n###### with value : {};\n###### with http status : {}; \n\n" ,
                    joinPoint.getTarget().getClass().getSimpleName() ,
                    joinPoint.getSignature().getName() ,
                    value ,
                    status);
        } else {
            logger.info("\n\n###### Returning for class : {};\n###### Method : {};\n###### with null as return value. \n\n" ,
                    joinPoint.getTarget().getClass().getSimpleName() ,
                    joinPoint.getSignature().getName());
        }

    }

    /**
     * Раскоммментируйте, чтобы узнать время выполнения любого вызванного метода любого контроллера.
     * Пример результата:
     * 'Execution time of AuthenticationController.createAuthenticationToken :: 589 ms'
     * Немного поменяв логику можно адаптировать для сбора этой и другой статистики у любого компонента.
     */
//    @Around("execution(* com.javamentor.developer.social.platform.webapp.controllers..*(..)))")
//    public Object profileAllMethods( ProceedingJoinPoint joinPoint) throws Throwable {
//
//        String className = joinPoint.getTarget().getClass().getSimpleName();
//        String methodName = joinPoint.getSignature().getName();
//
//        final StopWatch stopWatch = new StopWatch();
//
//        stopWatch.start();
//        Object result = joinPoint.proceed();
//        stopWatch.stop();
//
//        logger.info("\n\nExecution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms\n\n");
//
//        return result;
//    }
}
