package com.javamentor.developer.social.platform.webapp.configs.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotNull;

/**
 * Аспект - состовляющая программы,
 * которая позволяет реализовать сквозную логику без загрязнения целевого класса.
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
     * Эдвайс логирует возвращаемое значения методов любого контроллера.
     * Устраняет необходимость в локальных логгерах.
     * Проверка предназначена для ResponseEntity<Void>. Сейчас такого нет, если не планируется - от проверки можно избавиться.
     * Обычного void у нас в Rest'ах не должно быть совсем, но Вы это знаете и без меня.
     * Аргумент типа Object в обучающих целях, можно тащить toString прямо из него, если мы не уверены в типе возвращаемого объекта.
     * Так как мы точно знаем, что будет ResponseEntity - от каста можно избавиться и заменить Object.
     * Если будете использовать реактивные эндпоинты, то отдавать потоки лучше в функциональном стиле(route).
     * Если все таки решите отдавать с Рест-контроллеров, то принимать лучше Object и отсеивать instanceOf.
     */
    @AfterReturning(pointcut = "within(@org.springframework.web.bind.annotation.RestController *)", returning = "result")
    public void afterMethodInControllerClass( @NotNull JoinPoint joinPoint , Object result ) {

        ResponseEntity<?> response = (ResponseEntity<?>) result;

        if(response.hasBody()) {

            logger.info("\n\n###### Returning for class : {};\n###### Method : {};\n###### with value : {};\n###### with http status : {};\n\n" ,
                    joinPoint.getTarget().getClass().getSimpleName() ,
                    joinPoint.getSignature().getName() ,
                    response.getBody().toString() ,
                    response.getStatusCode());
        } else {

            logger.info("\n\n###### Returning for class : {};\n###### Method : {};\n###### with null as return value.;\n###### with http status : {};\n\n" ,
                    joinPoint.getTarget().getClass().getSimpleName() ,
                    joinPoint.getSignature().getName(),
                    response.getStatusCode());
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
