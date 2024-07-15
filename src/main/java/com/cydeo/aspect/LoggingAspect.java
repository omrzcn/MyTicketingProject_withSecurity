package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.MemberSubstitution;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j // this is the shortcut of --->    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
public class LoggingAspect {

//    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private String getUsername(){ // username i almak icin method olusturduk
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount details = (SimpleKeycloakAccount) authentication.getDetails();
        String username = details.getKeycloakSecurityContext().getToken().getPreferredUsername();

        return username;
    }


    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || execution(* com.cydeo.controller.TaskController.*(..))")
    public void anyProjectAndTaskControllerPC(){}




    @Before("anyProjectAndTaskControllerPC()")
    public void anyProjectAndTaskControllerAdvice(JoinPoint joinPoint){
        log.info("Before--> Method : {} , User: {} ",joinPoint.getSignature().toShortString(), getUsername()); // getUsername methodu olusturacagiz, kimin girdigini gormek icin
    }




    @AfterReturning(pointcut ="anyProjectAndTaskControllerPC()",returning = "results")
    public void afterAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint ,Object results){
        log.info("After Returning ---> Method : {}, User : {}",joinPoint.getSignature().toShortString(),results.toString());

    }




    @AfterThrowing(pointcut ="anyProjectAndTaskControllerPC()",throwing = "exception")
    public void afterAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint ,Exception exception){
        log.info("After Returning ---> Method : {}, User : {} , Result : {}",joinPoint.getSignature().toShortString(),getUsername(),exception.getMessage());

    }











}
