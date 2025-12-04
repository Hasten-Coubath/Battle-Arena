package com.example.combat_service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Esse ponto diz: "Interceptar TODOS os métodos dentro da pasta service"
    @Before("execution(* com.example.combat_service.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("LOG AUTOMÁTICO - Iniciando método: " + joinPoint.getSignature().getName());
    }

    // Executa depois que o método termina com sucesso
    @AfterReturning(pointcut = "execution(* com.example.combat_service.service.*.*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("LOG AUTOMÁTICO - Método finalizado: " + joinPoint.getSignature().getName());
        logger.info("Resultado: " + result);
    }
}